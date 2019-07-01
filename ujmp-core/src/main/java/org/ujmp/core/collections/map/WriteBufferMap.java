package org.ujmp.core.collections.map;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WriteBufferMap<K, V> extends AbstractMap<K, V> implements Closeable {

    private final Map<K, V> writeTarget;
    private final Map<K, V> writeBuffer;
    private final WriteThread writeThread;
    private int commitInterval = 1000;
    private int maxWriteBufferSize = 100000;
    private int maxBatchSize = 10000;
    private boolean isClosed = false;

    public WriteBufferMap(Map<K, V> writeTarget, Map<K, V> writeBuffer) {
        this.writeTarget = writeTarget;
        this.writeBuffer = writeBuffer;
        this.writeThread = new WriteThread(this, writeTarget, writeBuffer);
    }


    public WriteBufferMap(Map<K, V> map) {
        this(map, new HashMap<>());
    }

    @Override
    public void clear() {
        synchronized (writeBuffer) {
            writeBuffer.clear();
            writeTarget.clear();
        }
    }

    public void setMaxBatchSize(int maxBatchSize) {
        this.maxBatchSize = maxBatchSize;
    }

    public void setMaxWriteBufferSize(int maxWriteBufferSize) {
        this.maxWriteBufferSize = maxWriteBufferSize;
    }

    public void setCommitInterval(int commitInterval) {
        this.commitInterval = commitInterval;
    }


    @Override
    public V get(Object key) {
        V value1 = writeBuffer.get(key);
        V value2 = writeTarget.get(key);
        if (value1 != null) {
            return value1;
        } else {
            return value2;
        }
    }

    @Override
    public Set<K> keySet() {
        return writeTarget.keySet();
    }

    @Override
    public V put(K key, V value) {
        while (writeBuffer.size() >= maxWriteBufferSize) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }
        }
        synchronized (writeBuffer) {
            return writeBuffer.put(key, value);
        }
    }

    @Override
    public V remove(Object key) {
        V value1;
        synchronized (writeBuffer) {
            value1 = writeBuffer.remove(key);
        }
        V value2 = writeTarget.remove(key);
        if (value1 != null) {
            return value1;
        } else {
            return value2;
        }
    }

    @Override
    public int size() {
        return writeBuffer.size() + writeTarget.size();
    }

    public int getCommitInterval() {
        return commitInterval;
    }

    public int getMaxWriteBufferSize() {
        return maxWriteBufferSize;
    }

    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public void close() throws IOException {
        if (writeTarget instanceof Closeable) {
            ((Closeable) writeTarget).close();
        }
        isClosed = true;
    }

    public int getMaxBatchSize() {
        return maxBatchSize;
    }
}

class WriteThread<K, V> extends Thread {

    private final WriteBufferMap<K, V> writeBufferMap;
    private final Map<K, V> writeBuffer;
    private final Map<K, V> writeTarget;
    private long lastRunTime = 0;
    private final Map<K, V> currentBatch = new HashMap<>();


    public WriteThread(WriteBufferMap<K, V> writeBufferMap, Map<K, V> writeTarget, Map<K, V> writeBuffer) {
        this.writeBufferMap = writeBufferMap;
        this.writeTarget = writeTarget;
        this.writeBuffer = writeBuffer;
        this.start();
    }

    public void run() {
        while (!writeBufferMap.isClosed()) {
            while (System.currentTimeMillis() - lastRunTime < writeBufferMap.getCommitInterval() && writeBuffer.size() < writeBufferMap.getMaxWriteBufferSize()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lastRunTime = System.currentTimeMillis();
            while (!writeBuffer.isEmpty() && currentBatch.size() < writeBufferMap.getMaxBatchSize()) {
                K key;
                V value;
                synchronized (writeBuffer) {
                    key = writeBuffer.keySet().iterator().next();
                    value = writeBuffer.remove(key);
                }
                currentBatch.put(key, value);
            }
            if (!currentBatch.isEmpty()) {
                writeTarget.putAll(currentBatch);
                currentBatch.clear();
            }
        }
    }
}

/*
 * Copyright (C) 2008-2016 by Holger Arndt
 *
 * This file is part of the Universal Java Matrix Package (UJMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * UJMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * UJMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with UJMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.ujmp.core.collections.map;

import java.io.*;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class CachedMap<K, V> extends AbstractMap<K, V> implements Flushable, Closeable {
    private static final long serialVersionUID = 1383398694858918398L;

    private Map<K, V> source;

    private transient Map<K, V> cache;

    public CachedMap(Map<K, V> source) {
        this(source, new SoftHashMap<>());
    }

    public CachedMap(Map<K, V> source, Map<K, V> cache) {
        this.source = source;
        this.cache = cache;
    }

    public void clear() {
        cache.clear();
        source.clear();
    }

    public boolean containsKey(Object key) {
        if (cache.containsKey(key)) {
            return true;
        } else {
            return source.containsKey(key);
        }
    }

    public boolean containsValue(Object value) {
        if (cache.containsValue(value)) {
            return true;
        } else {
            return source.containsValue(value);
        }
    }

    @SuppressWarnings("unchecked")
    public V get(Object key) {
        V value = cache.get(key);
        if (value == null) {
            value = source.get(key);
            if (value != null) {
                cache.put((K) key, value);
            }
        }
        return value;
    }

    public boolean isEmpty() {
        if (!cache.isEmpty()) {
            return false;
        } else {
            return source.isEmpty();
        }
    }

    public Set<K> keySet() {
        return source.keySet();
    }

    public V put(K key, V value) {
        cache.put(key, value);
        return source.put(key, value);
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        for (K k : m.keySet()) {
            put(k, m.get(k));
        }
    }

    public V remove(Object key) {
        cache.remove(key);
        return source.remove(key);
    }

    public int size() {
        return source.size();
    }


    public void close() throws IOException {
        if (source instanceof Closeable) {
            ((Closeable) source).close();
        }
        if (cache instanceof Closeable) {
            ((Closeable) cache).close();
        }
    }

    public void flush() throws IOException {
        if (source instanceof Flushable) {
            ((Flushable) source).flush();
        }
        if (cache instanceof Flushable) {
            ((Flushable) cache).flush();
        }
    }


    protected void beforeWriteObject(ObjectOutputStream s) throws IOException {
        s.writeObject(source);
    }

    protected void beforeReadObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        cache = new WeakHashMap<>();
        source = (Map<K, V>) s.readObject();
    }

}


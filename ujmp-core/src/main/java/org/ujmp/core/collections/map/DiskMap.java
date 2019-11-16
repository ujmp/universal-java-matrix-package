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

import org.ujmp.core.collections.set.AbstractSet;
import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.util.HashUtil;
import org.ujmp.core.util.SerializationUtil;
import org.ujmp.core.util.StringUtil;
import org.ujmp.core.util.io.FileUtil;
import org.ujmp.core.util.io.GZIPOutputStreamWithCompressionLevel;

import java.io.*;
import java.lang.reflect.Constructor;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import static org.ujmp.core.util.VerifyUtil.between;
import static org.ujmp.core.util.VerifyUtil.notNull;

public class DiskMap<K, V> extends AbstractMap<K, V> implements Erasable {
    private static final long serialVersionUID = -8615077389159395747L;

    public static final int COMPRESSION_LEVEL_DEFAULT = 6;

    private File path;
    private String additionalFileExtension;
    private int maxDirectoryDepth = 255;
    private int maxDirectoryLength = 255;


    private Constructor<? extends OutputStream> outputStreamConstructor;
    private Constructor<? extends InputStream> inputStreamConstructor;

    private Compression compression;
    private int compressionLevel = COMPRESSION_LEVEL_DEFAULT;
    private Object xzOptions;

    public enum Compression {
        NO_COMPRESSION(".dat"), GZIP_COMPRESSION(".gz"), BZIP2_COMPRESSION(".bz2"), XZ_COMPRESSION(".xz");

        private final String fileExtension;

        Compression(String fileExtension) {
            this.fileExtension = fileExtension;
        }
    }

    private ContentEncoding contentEncoding;

    public enum ContentEncoding {
        TEXT, BINARY, SERIALIZED_OBJECT;
    }

    private FilenameEncoding filenameEncoding;

    public enum FilenameEncoding {
        NONE, BASE64, SHA128, SHA256, SHA512;
    }

    @SuppressWarnings("unchecked")
    public DiskMap(File path, String fileExtension, FilenameEncoding filenameEncoding, ContentEncoding contentEncoding, Compression compression) throws IOException {
        this.path = notNull(path);
        this.additionalFileExtension = fileExtension;
        this.filenameEncoding = filenameEncoding;
        this.contentEncoding = contentEncoding;
        this.compression = compression;
        switch (this.compression) {
            case GZIP_COMPRESSION:
                try {
                    outputStreamConstructor = GZIPOutputStreamWithCompressionLevel.class.
                            getConstructor(OutputStream.class, int.class);
                    inputStreamConstructor = GZIPInputStream.class.getConstructor(InputStream.class);
                } catch (Exception e) {
                    throw new IOException("GZIP compression format not supported", e);
                }
                break;
            case BZIP2_COMPRESSION:
                try {
                    outputStreamConstructor = (Constructor<OutputStream>)
                            Class.forName("org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream")
                                    .getConstructor(OutputStream.class, int.class);
                    inputStreamConstructor = (Constructor<InputStream>)
                            Class.forName("org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream")
                                    .getConstructor(InputStream.class);
                } catch (Exception e) {
                    throw new IOException("BZIP2 compression format not supported, add org.apache.commons.compress to classpath", e);
                }
                break;
            case XZ_COMPRESSION:
                try {
                    outputStreamConstructor = (Constructor<OutputStream>)
                            Class.forName("org.tukaani.xz.XZOutputStream").getConstructor(OutputStream.class,
                                    Class.forName("org.tukaani.xz.FilterOptions"));
                    xzOptions = Class.forName("org.tukaani.xz.LZMA2Options")
                            .getConstructor(int.class).newInstance(compressionLevel);
                    inputStreamConstructor = (Constructor<InputStream>)
                            Class.forName("org.tukaani.xz.XZInputStream").getConstructor(InputStream.class);
                } catch (Exception e) {
                    throw new IOException("XZ compression format not supported, add org.tukaani.xz to classpath", e);
                }
                break;
            default:
                break;
        }
    }

    public DiskMap(File path, FilenameEncoding filenameEncoding, ContentEncoding contentEncoding, Compression compression) throws IOException {
        this(path, "", filenameEncoding, contentEncoding, compression);
    }

    public DiskMap(File path, Compression compression) throws IOException {
        this(path, ContentEncoding.SERIALIZED_OBJECT, compression);
    }

    public DiskMap(File path, ContentEncoding contentEncoding, Compression compression) throws IOException {
        this(path, "", FilenameEncoding.NONE, contentEncoding, compression);
    }

    public DiskMap(File path, ContentEncoding contentEncoding) throws IOException {
        this(path, contentEncoding, Compression.NO_COMPRESSION);
    }

    public DiskMap(File path) throws IOException {
        this(path, Compression.NO_COMPRESSION);
    }

    public DiskMap() throws IOException {
        this(createRandomPath());
    }

    public int getCompressionLevel() {
        return compressionLevel;
    }

    public void setCompressionLevel(int compressionLevel) {
        this.compressionLevel = between(compressionLevel, 1, 11);
    }

    public int getMaxDirectoryDepth() {
        return maxDirectoryDepth;
    }

    public void setMaxDirectoryDepth(int maxDirectoryDepth) {
        this.maxDirectoryDepth = maxDirectoryDepth;
    }

    public int getMaxDirectoryLength() {
        return maxDirectoryLength;
    }

    public void setMaxDirectoryLength(int maxDirectoryLength) {
        this.maxDirectoryLength = maxDirectoryLength;
    }

    private static File createRandomPath() throws IOException {
        File path = File.createTempFile("diskmap" + System.nanoTime(), "");
        path.delete();
        if (!path.exists()) {
            path.mkdirs();
        }
        return path;
    }

    public final File getPath() {
        return path;
    }

    public synchronized final int size() {
        try {
            return (int) Files.walk(path.toPath()).parallel().filter(Files::isRegularFile).count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File getFileForKey(Object o) throws IOException {
        if (o == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        String key;
        if (o instanceof String) {
            key = (String) o;
        } else if (o instanceof Integer) {
            key = String.valueOf(o);
        } else if (o instanceof Long) {
            key = String.valueOf(o);
        } else if (o instanceof Float) {
            key = String.valueOf(o);
        } else if (o instanceof Double) {
            key = String.valueOf(o);
        } else {
            byte[] data = SerializationUtil.serialize((Serializable) o);
            key = HashUtil.getSHA256String(data);
        }

        String suffix;
        if (additionalFileExtension != null && !"".equals(additionalFileExtension)) {
            suffix = additionalFileExtension + compression.fileExtension;
        } else {
            suffix = compression.fileExtension;
        }

        StringBuilder filename = new StringBuilder();
        filename.append(path.getAbsolutePath());
        filename.append(File.separator);
        for (int i = 0; i < maxDirectoryDepth && i * maxDirectoryLength + maxDirectoryLength <= key.length(); i++) {
            String dir = key.substring(i * maxDirectoryLength, i * maxDirectoryLength + maxDirectoryLength);
            filename.append(dir);
            filename.append(File.separator);
        }
        filename.append(key);
        filename.append(suffix);
        return new File(filename.toString());
    }

    private static boolean isEmpty(File dir) throws IOException {
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(dir.toPath())) {
            return !dirStream.iterator().hasNext();
        }
    }

    private void deleteParentsIfEmpty(File file) {
        File parent = file.getParentFile();
        if (parent == null || parent.equals(path)) {
            return;
        }
        try {
            if (isEmpty(parent)) {
                parent.delete();
                deleteParentsIfEmpty(parent);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized final V remove(Object key) {
        try {
            File file = getFileForKey(key);
            if (file.exists()) {
                file.delete();
                deleteParentsIfEmpty(file);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized final boolean containsKey(Object key) {
        try {
            File file = getFileForKey(key);
            if (file == null) {
                return false;
            }
            return file.exists();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public final Set<K> keySet() {
        if (path == null || !path.exists()) {
            return Collections.emptySet();
        }
        return new FileIteratorSet<>(this);
    }


    @SuppressWarnings("unchecked")
    K getKeyForFile(File file) {
        try {

            String filename = file.getAbsolutePath();

            // remove compression
            if (compression == Compression.GZIP_COMPRESSION && filename.endsWith(".gz")) {
                filename = filename.substring(0, filename.length() - 3);
            } else if (compression == Compression.BZIP2_COMPRESSION && filename.endsWith(".bz2")) {
                filename = filename.substring(0, filename.length() - 4);
            } else if (compression == Compression.XZ_COMPRESSION && filename.endsWith(".xz")) {
                filename = filename.substring(0, filename.length() - 3);
            }

            // remove additional extension
            if (additionalFileExtension != null && !additionalFileExtension.isEmpty() && filename.endsWith(additionalFileExtension)) {
                filename = filename.substring(0, filename.length() - additionalFileExtension.length());
            }

            if (filenameEncoding == FilenameEncoding.NONE) {
                filename = filename.substring(path.getAbsolutePath().length());
                return (K) filename;
            } else if (filenameEncoding == FilenameEncoding.BASE64) {
                filename = filename.substring(0, filename.length() - 4);
                filename = StringUtil.reverse(filename);
                return (K) SerializationUtil.deserialize(StringUtil.decodeFromHex(filename));
            } else {
                return (K) filename;
            }

        } catch (Exception e) {
            throw new RuntimeException("cannot convert filename: " + file, e);
        }
    }

    public final synchronized void clear() {
        try {
            erase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void erase() {
        FileUtil.deleteRecursive(path);
    }

    public final synchronized V put(K key, V value) {
        try {
            if (key == null) {
                return null;
            }

            File file = getFileForKey(key);

            if (value == null && file.exists()) {
                file.delete();
                return null;
            }

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            OutputStream os;
            switch (compression) {
                case GZIP_COMPRESSION:
                case BZIP2_COMPRESSION:
                    os = outputStreamConstructor.newInstance(bos, compressionLevel);
                    break;
                case XZ_COMPRESSION:
                    os = outputStreamConstructor.newInstance(bos, xzOptions);
                    break;
                default:
                    os = bos;
            }

            switch (contentEncoding) {
                case TEXT:
                    try (OutputStreamWriter w = new OutputStreamWriter(os)) {
                        w.write(String.valueOf(value));
                    }
                    break;
                case BINARY:
                    os.write((byte[]) value);
                    break;
                default:
                    try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
                        oos.writeObject(value);
                    }
                    break;
            }

            Files.write(Paths.get(file.toURI()), bos.toByteArray());

            return null;
        } catch (Exception e) {
            throw new RuntimeException("could not put object " + key, e);
        }
    }

    @SuppressWarnings("unchecked")
    public final synchronized V get(Object key) {
        try {
            File file = getFileForKey(key);
            if (file == null || !file.exists()) {
                return null;
            }

            V o;

            byte[] data = Files.readAllBytes(Paths.get(file.toURI()));
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            InputStream is;
            if (inputStreamConstructor != null) {
                is = inputStreamConstructor.newInstance(bis);
            } else {
                is = bis;
            }

            switch (contentEncoding) {
                case TEXT:
                    ByteArrayOutputStream buf = new ByteArrayOutputStream(data.length);
                    int result = is.read();
                    while (result != -1) {
                        buf.write((byte) result);
                        result = is.read();
                    }
                    return (V) buf.toString("UTF-8");
                default:
                    try (ObjectInputStream ois = new ObjectInputStream(is)) {
                        o = (V) ois.readObject();
                    }
            }

            return o;
        } catch (Exception e) {
            throw new RuntimeException("could not get object " + key, e);
        }
    }


    protected void beforeWriteObject(ObjectOutputStream s) throws IOException {
        s.writeObject(path);
        s.writeInt(maxDirectoryDepth);
        s.writeInt(maxDirectoryLength);
        s.writeObject(compression);
    }

    protected void beforeReadObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        path = (File) s.readObject();
        maxDirectoryDepth = s.readInt();
        maxDirectoryLength = s.readInt();
        compression = (Compression) s.readObject();
    }

}

class FileIteratorSet<K, V> extends AbstractSet<K> {
    private static final long serialVersionUID = 7149853430066720153L;

    private final DiskMap<K, V> diskMap;

    FileIteratorSet(DiskMap<K, V> diskMap) {
        super();
        this.diskMap = diskMap;
    }

    @Override
    public boolean add(K value) {
        throw new RuntimeException("not allowed");
    }

    @Override
    public boolean remove(Object o) {
        throw new RuntimeException("not allowed");
    }

    @Override
    public void clear() {
        throw new RuntimeException("not allowed");
    }

    @Override
    public Iterator<K> iterator() {
        return new FileWalkIterator<>(diskMap, diskMap.getPath());
        //return new FileIterator<>(diskMap, diskMap.getPath());
    }

    @Override
    public int size() {
        return diskMap.size();
    }

    @Override
    public boolean contains(Object o) {
        return diskMap.containsKey(o);
    }
}

class FileWalkIterator<K, V> implements Iterator<K> {

    private final DiskMap<K, V> diskMap;
    private final Iterator<Path> iterator;

    FileWalkIterator(DiskMap<K, V> diskMap, File path) {
        this.diskMap = diskMap;
        try {
            this.iterator = Files.walk(path.toPath()).filter(p -> p.toFile().isFile()).iterator();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public K next() {
        return diskMap.getKeyForFile(iterator.next().toFile());
    }
}

class FileIterator<K, V> implements Iterator<K> {

    private final DiskMap<K, V> diskMap;

    private final File[] files;
    private int positon = -1;
    private FileIterator<K, V> childIterator = null;
    private K nextItem = null;

    FileIterator(DiskMap<K, V> diskMap, File path) {
        this.diskMap = diskMap;
        files = path.listFiles();
        if (files == null || files.length == 0) {
            return;
        }

        while (positon++ < files.length) {
            if (files[positon].isFile()) {
                nextItem = diskMap.getKeyForFile(files[positon]);
                break;
            }
            if (files[positon].isDirectory()) {
                childIterator = new FileIterator<>(diskMap, files[positon]);
                if (childIterator.hasNext()) {
                    nextItem = childIterator.next();
                    break;
                }
            }
        }
    }

    @Override
    public boolean hasNext() {
        return nextItem != null;
    }

    @Override
    public K next() {
        // reached the end
        if (nextItem == null) {
            throw new RuntimeException("no more files");
        }

        K currentItem = nextItem;

        // get file from child iterator
        if (childIterator != null && childIterator.hasNext()) {
            nextItem = childIterator.next();
            return currentItem;
        }

        // child iterator is finished here, so delete it
        childIterator = null;

        // move forward
        while (++positon < files.length) {
            if (files[positon].isFile()) {
                nextItem = diskMap.getKeyForFile(files[positon]);
                return currentItem;
            }
            if (files[positon].isDirectory()) {
                childIterator = new FileIterator<>(diskMap, files[positon]);
                if (childIterator.hasNext()) {
                    nextItem = childIterator.next();
                    return currentItem;
                }
            }
        }

        // no more files
        nextItem = null;

        return currentItem;
    }

}

/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

package org.ujmp.core.collections;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.util.SerializationUtil;
import org.ujmp.core.util.StringUtil;
import org.ujmp.core.util.io.FileUtil;

public abstract class AbstractDiskMap<K, V> extends AbstractMap<K, V> implements Erasable {
	private static final long serialVersionUID = -8615077389159395747L;

	private File path = null;

	private boolean useGZip = true;

	private int maxDepth = 5;

	public AbstractDiskMap(File path, boolean useGZip) throws IOException {
		this.useGZip = useGZip;
		this.path = path;
	}

	public final File getPath() {
		if (path == null) {
			try {
				path = File.createTempFile("diskmap" + System.nanoTime(), "");
				path.delete();
				if (!path.exists()) {
					path.mkdirs();
				}
			} catch (Exception e) {
				throw new MatrixException(e);
			}
		}
		return path;
	}

	public synchronized final int size() {
		return FileUtil.countFiles(getPath());
	}

	private final File getFileNameForKey(Object o) throws IOException {
		String key;
		String suffix = ".dat";
		if (useGZip) {
			suffix += ".gz";
		}
		if (o instanceof String && StringUtil.isAlphanumeric((String) o)) {
			key = (String) o;
		} else {
			suffix = ".obj" + suffix;
			key = StringUtil.reverse(StringUtil.encodeToHex((Serializable) o));
		}
		StringBuilder result = new StringBuilder();
		result.append(getPath().getAbsolutePath());
		result.append(File.separator);
		for (int i = 0; i < maxDepth && i < key.length() - 1; i++) {
			char c = key.charAt(i);
			result.append(c);
			result.append(File.separator);
		}
		result.append(key);
		result.append(suffix);
		return new File(result.toString());
	}

	public synchronized final V remove(Object key) {
		try {
			V v = get(key);
			File file = getFileNameForKey(key);
			if (file.exists()) {
				file.delete();
			}
			return v;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized final boolean containsKey(Object key) {
		try {
			File file = getFileNameForKey(key);
			if (file == null) {
				return false;
			}
			return file.exists();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// TODO: better with an Iterator
	public final Set<K> keySet() {
		try {
			Set<K> set = new HashSet<K>();
			listFilesToSet(getPath(), set);
			return set;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void listFilesToSet(File path, Set<K> set) throws ClassNotFoundException, IOException {
		File[] files = path.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					listFilesToSet(f, set);
				} else {
					set.add(getKeyForFile(f));
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private K getKeyForFile(File file) throws ClassNotFoundException, IOException {
		String filename = file.getName();
		if (filename.endsWith(".gz")) {
			filename = filename.substring(0, filename.length() - 3);
		}
		if (filename.endsWith(".dat")) {
			filename = filename.substring(0, filename.length() - 4);
		}
		if (filename.endsWith(".obj")) {
			filename = filename.substring(0, filename.length() - 4);
			filename = StringUtil.reverse(filename);
			return (K) SerializationUtil.deserialize(StringUtil.decodeFromHex(filename));
		} else {
			return (K) filename;
		}
	}

	public final synchronized void clear() {
		try {
			erase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final void erase() throws IOException {
		FileUtil.deleteRecursive(path);
	}

	public final void setPath(File path) {
		this.path = path;
	}

	public final synchronized V put(K key, V value) {
		try {
			if (key == null) {
				return null;
			}

			File file = getFileNameForKey(key);

			if (value == null && file.exists()) {
				file.delete();
				return null;
			}

			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}

			FileOutputStream fo = new FileOutputStream(file);

			OutputStream bo = new BufferedOutputStream(fo);
			if (useGZip) {
				bo = new GZIPOutputStream(bo, 8192);
			}

			writeValue(bo, value);

			bo.close();
			fo.close();

			return null;
		} catch (Exception e) {
			throw new MatrixException("could not put object " + key, e);
		}
	}

	public final synchronized V get(Object key) {
		try {
			File file = getFileNameForKey(key);
			if (file == null || !file.exists()) {
				return null;
			}

			V o = null;

			FileInputStream fi = new FileInputStream(file);
			InputStream bi = new BufferedInputStream(fi);
			if (useGZip) {
				bi = new GZIPInputStream(bi, 8192);
			}

			o = readValue(bi);

			bi.close();
			fi.close();

			return o;
		} catch (Exception e) {
			throw new MatrixException("could not get object " + key, e);
		}
	}

	public abstract void writeValue(OutputStream os, V value);

	public abstract V readValue(InputStream is);

}

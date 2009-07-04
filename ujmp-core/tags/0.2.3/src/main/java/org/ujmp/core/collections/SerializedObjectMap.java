/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.util.Base64;
import org.ujmp.core.util.SerializationUtil;
import org.ujmp.core.util.io.FileUtil;

public class SerializedObjectMap<K, V> implements Map<K, V>, Erasable {

	private boolean useGZip = true;

	private File path = null;

	public SerializedObjectMap() throws IOException {
		this((File) null, true);
	}

	public SerializedObjectMap(String path) throws IOException {
		this(new File(path), true);
	}

	public SerializedObjectMap(boolean useGZip) throws IOException {
		this((File) null, useGZip);
	}

	public SerializedObjectMap(String path, boolean useGZip) throws IOException {
		this(new File(path), useGZip);
	}

	public SerializedObjectMap(File path) throws IOException {
		this(path, true);
	}

	public SerializedObjectMap(File path, boolean useGZip) throws IOException {
		this.useGZip = useGZip;
		if (path == null) {
			path = File.createTempFile("serializedmap" + System.nanoTime(), "");
			path.delete();
			path.deleteOnExit();
		}
		this.path = path;
		if (!path.exists()) {
			path.mkdirs();
		}
	}

	public synchronized void clear() {
		throw new MatrixException("not implemented");
	}

	public File getPath() {
		return path;
	}

	public synchronized boolean containsKey(Object key) {
		File file = getFileNameForKey(key);
		if (file == null) {
			return false;
		}
		return file.exists();
	}

	public synchronized boolean containsValue(Object value) {
		throw new MatrixException("not implemented");
	}

	public synchronized Set<java.util.Map.Entry<K, V>> entrySet() {
		throw new MatrixException("not implemented");
	}

	@SuppressWarnings("unchecked")
	public synchronized V get(Object key) {
		try {
			File file = getFileNameForKey(key);
			if (file == null || !file.exists()) {
				return null;
			}

			Object o = null;

			FileInputStream fi = new FileInputStream(file);
			InputStream bi = new BufferedInputStream(fi);
			if (useGZip) {
				bi = new GZIPInputStream(bi);
			}

			Object[] os = (Object[]) SerializationUtil.deserialize(bi);
			o = os[1];

			bi.close();
			fi.close();

			return (V) o;
		} catch (Exception e) {
			throw new MatrixException("could not get object " + key, e);
		}
	}

	public synchronized boolean isEmpty() {
		return size() == 0;
	}

	@SuppressWarnings("unchecked")
	private void listFilesToSet(File path, Set<K> set) {
		Boolean stringKey = null;
		for (File f : path.listFiles()) {
			if (f.isDirectory()) {
				listFilesToSet(f, set);
			} else {
				String filename = f.getName();
				Object o = filename;
				try {
					if ((stringKey == null || stringKey == false) && filename.length() >= 4) {
						o = Base64.decodeToObject(filename);
						stringKey = false;
					}
				} catch (Exception e) {
					stringKey = true;
				}
				set.add((K) o);
			}
		}
	}

	public synchronized V put(K key, V value) {
		try {
			if (value == null || key == null) {
				return null;
			}

			File file = getFileNameForKey(key);

			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}

			FileOutputStream fo = new FileOutputStream(file);

			OutputStream bo = new BufferedOutputStream(fo);
			if (useGZip) {
				bo = new GZIPOutputStream(bo);
			}

			SerializationUtil.serialize(new Object[] { key, value }, bo);

			bo.close();
			fo.close();

			return null;
		} catch (Exception e) {
			throw new MatrixException("could not put object " + key, e);
		}
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		for (K key : m.keySet()) {
			put(key, m.get(key));
		}
	}

	public synchronized V remove(Object key) {
		V v = get(key);
		File file = getFileNameForKey(key);
		if (file.exists()) {
			file.delete();
		}
		return v;
	}

	public synchronized int size() {
		return countFiles(path);
	}

	private static int countFiles(File path) {
		int count = 0;
		File[] files = path.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				count += countFiles(f);
			} else {
				count++;
			}
		}
		return count;
	}

	private File getFileNameForKey(Object key) {
		String s = null;
		if (key instanceof String) {
			s = (String) key;
		} else {
			try {
				s = Base64.encodeObject((Serializable) key);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		String result = path.getAbsolutePath() + File.separator;
		for (int i = 0; i < s.length() - 1; i++) {
			result += s.charAt(i) + File.separator;
		}
		result += s + ".dat";
		if (useGZip) {
			result += ".gz";
		}
		return new File(result);
	}

	public synchronized Collection<V> values() {
		// TODO: this is not the best way to do it:
		List<V> list = new ArrayList<V>();
		for (K key : keySet()) {
			list.add(get(key));
		}
		return list;
	}

	public void setPath(File path) {
		this.path = path;
	}

	@Override
	public void erase() throws IOException {
		FileUtil.deleteRecursive(path);
	}

	@Override
	public Set<K> keySet() {
		Set<K> set = new HashSet<K>();
		listFilesToSet(path, set);
		return set;
	}

}

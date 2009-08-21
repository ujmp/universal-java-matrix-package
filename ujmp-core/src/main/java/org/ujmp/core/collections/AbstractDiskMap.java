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
import org.ujmp.core.util.io.FileUtil;

public abstract class AbstractDiskMap<V> implements Map<String, V>, Erasable {

	private File path = null;

	private boolean useGZip = true;

	private int maxDepth = 20;

	public AbstractDiskMap(File path, boolean useGZip) throws IOException {
		this.useGZip = useGZip;
		if (path == null) {
			path = File.createTempFile("diskmap" + System.nanoTime(), "");
			path.delete();
			path.deleteOnExit();
		}
		this.path = path;
		if (!path.exists()) {
			path.mkdirs();
		}
	}

	public synchronized final boolean isEmpty() {
		return size() == 0;
	}

	public synchronized final int size() {
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

	private static final String convertKey(String key) {
		String result = "";
		for (int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);
			if ((c < 48) || (c > 122) || ((c > 90) && (c < 97)) || ((c > 57) && (c < 65))) {
				c = '_';
			}
			result += c;
		}
		return result;
	}

	private final File getFileNameForKey(String key) {
		key = convertKey(key);
		String result = path.getAbsolutePath() + File.separator;
		for (int i = 0; i < maxDepth && i < key.length() - 1; i++) {
			char c = key.charAt(i);
			result += c + File.separator;
		}
		result += key + ".dat";
		if (useGZip) {
			result += ".gz";
		}
		return new File(result);
	}

	public synchronized final V remove(Object key) {
		V v = get(key);
		File file = getFileNameForKey((String) key);
		if (file.exists()) {
			file.delete();
		}
		return v;
	}

	public synchronized final boolean containsKey(Object key) {
		File file = getFileNameForKey((String) key);
		if (file == null) {
			return false;
		}
		return file.exists();
	}

	public final File getPath() {
		return path;
	}

	
	public final Set<String> keySet() {
		Set<String> set = new HashSet<String>();
		listFilesToSet(path, set);
		return set;
	}

	private void listFilesToSet(File path, Set<String> set) {
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
				set.add((String) o);
			}
		}
	}

	public final synchronized void clear() {
		try {
			erase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final synchronized boolean containsValue(Object value) {
		throw new MatrixException("not implemented");
	}

	public final synchronized Set<java.util.Map.Entry<String, V>> entrySet() {
		throw new MatrixException("not implemented");
	}

	
	public final void erase() throws IOException {
		FileUtil.deleteRecursive(path);
	}

	public final void setPath(File path) {
		this.path = path;
	}

	public final synchronized Collection<V> values() {
		// TODO: this is not the best way to do it:
		List<V> list = new ArrayList<V>();
		for (String key : keySet()) {
			list.add(get(key));
		}
		return list;
	}

	public final void putAll(Map<? extends String, ? extends V> m) {
		for (String key : m.keySet()) {
			put(key, m.get(key));
		}
	}

	public final synchronized V put(String key, V value) {
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

			write(bo, value);

			bo.close();
			fo.close();

			return null;
		} catch (Exception e) {
			throw new MatrixException("could not put object " + key, e);
		}
	}

	public final synchronized V get(Object key) {
		try {
			File file = getFileNameForKey((String) key);
			if (file == null || !file.exists()) {
				return null;
			}

			V o = null;

			FileInputStream fi = new FileInputStream(file);
			InputStream bi = new BufferedInputStream(fi);
			if (useGZip) {
				bi = new GZIPInputStream(bi, 8192);
			}

			o = read(bi);

			bi.close();
			fi.close();

			return o;
		} catch (Exception e) {
			throw new MatrixException("could not get object " + key, e);
		}
	}

	public abstract void write(OutputStream os, V value);

	public abstract V read(InputStream is);

}

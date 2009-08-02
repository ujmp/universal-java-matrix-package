/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

package org.ujmp.core.collections.serializedmap;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ujmp.core.util.SerializationUtil;

public class SerializedObjectMap<K, V> implements Map<K, V> {

	private static final Logger logger = Logger.getLogger(SerializedObjectMap.class.getName());

	public static final int SERIALIZE = 0;

	public static final int XMLENCODER = 1;

	private FileNameConverter fileNameConverter = null;

	private File path = null;

	private boolean temporary = false;

	private int method = SERIALIZE;

	public SerializedObjectMap() {
		this((File) null);
	}

	public SerializedObjectMap(String path) {
		this(new File(path));
	}

	public SerializedObjectMap(File path) {
		try {
			if (path == null) {
				path = File.createTempFile("serializedmap" + System.nanoTime(), "");
				path.delete();
				path.deleteOnExit();
				temporary = true;
			}
			this.path = path;
			if (!path.exists()) {
				path.mkdirs();
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not create serialized map", e);
		}
	}

	public synchronized void clear() {
		logger.log(Level.WARNING, "not implemented");
	}

	public FileNameConverter getFileNameConverter() {
		if (fileNameConverter == null) {
			fileNameConverter = new DefaultFileNameConverter();
		}
		return fileNameConverter;
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
		logger.log(Level.WARNING, "not implemented");
		return false;
	}

	public synchronized Set<java.util.Map.Entry<K, V>> entrySet() {
		logger.log(Level.WARNING, "not implemented");
		return null;
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
			BufferedInputStream bi = new BufferedInputStream(fi);

			switch (method) {
			case SERIALIZE:
				o = SerializationUtil.deserialize(bi);
				break;
			case XMLENCODER:
				XMLDecoder decoder = new XMLDecoder(bi);
				o = decoder.readObject();
				decoder.close();
				break;
			}

			bi.close();
			fi.close();

			return (V) o;
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not get object " + key, e);
			return null;
		}
	}

	public synchronized boolean isEmpty() {
		return false;
	}

	public synchronized Set<K> keySet() {
		logger.log(Level.WARNING, "not implemented");
		return null;
	}

	public synchronized V put(K key, V value) {
		try {
			if (value == null) {
				return null;
			}

			File file = getFileNameForKey(key);
			if (file == null) {
				return null;
			}

			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
				if (temporary) {
					file.getParentFile().deleteOnExit();
				}
			}

			FileOutputStream fo = new FileOutputStream(file);
			BufferedOutputStream bo = new BufferedOutputStream(fo);

			switch (method) {
			case SERIALIZE:
				SerializationUtil.serialize((Serializable) value, bo);
				break;
			case XMLENCODER:
				XMLEncoder encoder = new XMLEncoder(bo);
				encoder.writeObject(value);
				encoder.close();
				break;
			}

			bo.close();
			fo.close();

			if (temporary) {
				file.deleteOnExit();
			}

			return null;
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not put object " + key, e);
			return null;
		}
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		for (K key : m.keySet()) {
			put(key, m.get(key));
		}
	}

	public synchronized V remove(Object key) {
		return null;
	}

	public synchronized int size() {
		int count = 0;
		for (File dir : getAllDirs()) {
			if (dir.exists()) {
				count += dir.listFiles().length;
			}
		}
		return count;
	}

	public List<File> getAllDirs() {
		return getFileNameConverter().getAllDirs(path);
	}

	public File getFileNameForKey(Object key) {
		File file = getFileNameConverter().getFileNameForKey(key);
		if (file == null) {
			return null;
		}
		file = new File(path + File.separator + file);
		return file;
	}

	public synchronized Collection<V> values() {
		logger.log(Level.WARNING, "not implemented");
		return null;
	}

	public void setFileNameConverter(FileNameConverter fileNameConverter) {
		this.fileNameConverter = fileNameConverter;
	}

	public void setPath(File path) {
		this.path = path;
	}

	public void setMethod(int method) {
		this.method = method;
	}

}

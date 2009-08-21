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

package org.ujmp.hadoop;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.Text;
import org.ujmp.core.util.ReflectionUtil;
import org.ujmp.core.util.SerializationUtil;

public class HadoopMap<K, V> implements Map<K, V>, Closeable {

	private MapFile.Reader reader = null;
	private MapFile.Writer writer = null;

	private Configuration conf = null;
	private Path dirName = null;
	private FileSystem fs = null;
	private Path qualifiedDirName = null;

	public HadoopMap() throws IOException {
		conf = new Configuration();
		dirName = new Path("/tmp/test.hadoop");
		fs = FileSystem.getLocal(conf);
		qualifiedDirName = fs.makeQualified(dirName);
		MapFile.Writer.setIndexInterval(conf, 3);
	}

	
	public void clear() {
		// TODO Auto-generated method stub
	}

	private void prepareReader() throws IOException {
		if (writer != null) {
			writer.close();
			writer = null;
		}
		if (reader == null) {
			reader = new MapFile.Reader(fs, qualifiedDirName.toString(), conf);
		}
	}

	private void prepareWriter() throws IOException {
		if (reader != null) {
			reader.close();
			reader = null;
		}
		if (writer == null) {
			writer = new MapFile.Writer(conf, fs, qualifiedDirName.toString(),
					Text.class, Text.class);
		}
	}

	
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public V get(Object key) {
		try {
			prepareReader();
			Text k = new Text(SerializationUtil.serialize((Serializable) key));
			Text v = new Text();
			Text t = (Text) reader.get(k, v);
			if (t == null || t.getBytes() == null || t.getBytes().length == 0) {
				return null;
			}
			return (V) SerializationUtil.deserialize(t.getBytes());
		} catch (Exception e) {
			throw new RuntimeException("could not get value for key: " + key, e);
		}
	}

	
	public boolean isEmpty() {
		return size() == 0;
	}

	
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public V put(K key, V value) {
		try {
			prepareWriter();
			Text k = new Text(SerializationUtil.serialize((Serializable) key));
			Text v = new Text(SerializationUtil.serialize((Serializable) value));
			writer.append(k, v);
			return null;
		} catch (Exception e) {
			throw new RuntimeException("could not store value: " + key + ", "
					+ value, e);
		}
	}

	
	public void putAll(Map<? extends K, ? extends V> m) {
		for (K k : m.keySet()) {
			put(k, m.get(k));
		}

	}

	
	public V remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public int size() {
		try {
			prepareWriter();
			return (int) (long) (Long) ReflectionUtil.extractPrivateField(
					MapFile.Writer.class, writer, "size");
		} catch (Exception e) {
			throw new RuntimeException("could not query size", e);
		}
	}

	
	public Collection<V> values() {
		return null;
	}

	
	public void close() throws IOException {
		if (reader != null) {
			reader.close();
		}
		if (writer != null) {
			writer.close();
		}
	}

	public static void main(String[] args) throws Exception {
		Map<Object, Object> map = new HadoopMap<Object, Object>();
		System.out.println(map.size());
		map.put("test", "test");
		System.out.println(map.size());
		map.put("a", "a");
		System.out.println(map.get("test"));
		System.out.println(map.get("test2"));
	}

}

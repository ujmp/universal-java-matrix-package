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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.ujmp.core.exceptions.MatrixException;

public class LazyMap<K, V> implements Map<K, V> {
	private static final long serialVersionUID = -5970508001189550492L;

	private Map<K, Callable<V>> map = null;

	public LazyMap() {
		this.map = new HashMap<K, Callable<V>>();
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		throw new MatrixException("not implemented");
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		throw new MatrixException("not implemented");
	}

	public V get(Object key) {
		Callable<V> cv = map.get(key);
		if (cv == null) {
			return null;
		}
		try {
			return cv.call();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public void put(K key, Callable<V> value) {
		map.put(key, value);
	}

	public V put(K key, final V value) {
		put(key, new Callable<V>() {

			public V call() throws Exception {
				return value;
			}
		});
		return null;
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		for (K key : m.keySet()) {
			V v = m.get(key);
			put(key, v);
		}
	}

	public V remove(Object key) {
		map.remove(key);
		return null;
	}

	public int size() {
		return map.size();
	}

	public Collection<V> values() {
		throw new MatrixException("not implemented");
	}

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		while (true) {
			try {
				K k = (K) s.readObject();
				V v = (V) s.readObject();
				put(k, v);
			} catch (OptionalDataException e) {
				return;
			}
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException, MatrixException {
		s.defaultWriteObject();
		for (Object k : keySet()) {
			Object v = get(k);
			s.writeObject(k);
			s.writeObject(v);
		}
	}

	public static void main(String[] args) throws Exception {
		Map<String, String> map = new LazyMap<String, String>();
		map.put("test", "test");
		System.out.println(map.get("test"));
	}

}

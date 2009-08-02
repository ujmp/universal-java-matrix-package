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

package org.ujmp.core.collections;

import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SoftHashMap<K, V> implements Map<K, V> {

	private static final Logger logger = Logger.getLogger(SoftHashMap.class.getName());

	private transient Map<K, SoftReference<V>> map = null;

	public SoftHashMap() {
		map = new HashMap<K, SoftReference<V>>();
	}

	public SoftHashMap(Map<? extends K, ? extends V> map) {
		this();
		putAll(map);
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@SuppressWarnings("unchecked")
	public boolean containsValue(Object value) {
		return map.containsValue(new SoftReference(value));
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		logger.log(Level.WARNING, "cannot get entryset");
		return null;
	}

	public V get(Object key) {
		SoftReference<V> v = map.get(key);
		return v == null ? null : v.get();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public V put(K key, V value) {
		SoftReference<V> v = map.put(key, new SoftReference<V>(value));
		return v == null ? null : v.get();
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		for (K key : m.keySet()) {
			put(key, m.get(key));
		}
	}

	public V remove(Object key) {
		SoftReference<V> v = map.remove(key);
		return v == null ? null : v.get();
	}

	public int size() {
		return map.size();
	}

	public Collection<V> values() {
		logger.log(Level.WARNING, "cannot get values");
		return null;
	}

}

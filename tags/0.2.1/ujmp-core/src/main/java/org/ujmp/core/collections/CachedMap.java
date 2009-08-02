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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

public class CachedMap<K, V> implements Wrapper<Map<K, V>>, Map<K, V> {

	private Map<K, V> source = null;

	private Map<K, V> cache = null;

	public CachedMap(Map<K, V> source) {
		setWrappedObject(source);
		setCache(new SoftHashMap<K, V>());
	}

	public CachedMap(Map<K, V> source, Map<K, V> cache) {
		setWrappedObject(source);
		setCache(cache);
	}

	public void clear() {
		cache.clear();
		source.clear();
	}

	public boolean containsKey(Object key) {
		if (cache.containsKey(key)) {
			return true;
		}
		return source.containsKey(key);
	}

	public boolean containsValue(Object value) {
		if (cache.containsValue(value)) {
			return true;
		}
		return source.containsValue(value);
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		throw new MatrixException("not implemented");
	}

	@SuppressWarnings("unchecked")
	public V get(Object key) {
		V value = cache.get(key);
		if (value == null) {
			value = source.get(key);
			cache.put((K) key, value);
		}
		return value;
	}

	public boolean isEmpty() {
		if (!cache.isEmpty()) {
			return false;
		}
		return source.isEmpty();
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

	public Collection<V> values() {
		throw new MatrixException("not implemented");
	}

	public Map<K, V> getWrappedObject() {
		return source;
	}

	public void setWrappedObject(Map<K, V> object) {
		if (cache != null) {
			cache.clear();
		}
		this.source = object;
	}

	public Map<K, V> getCache() {
		return cache;
	}

	public void setCache(Map<K, V> cache) {
		this.cache = cache;
	}

}

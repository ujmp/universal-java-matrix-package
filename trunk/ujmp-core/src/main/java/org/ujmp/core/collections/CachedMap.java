/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.ujmp.core.interfaces.Wrapper;

public class CachedMap<K, V> extends AbstractMap<K, V> implements Wrapper<Map<K, V>> {
	private static final long serialVersionUID = 1383398694858918398L;

	private transient Map<K, V> source = null;

	private transient Map<K, V> cache = null;

	public CachedMap(Map<K, V> source) {
		setWrappedObject(source);
	}

	public CachedMap(Map<K, V> source, Map<K, V> cache) {
		setWrappedObject(source);
		this.cache = cache;
	}

	public void clear() {
		getCache().clear();
		getWrappedObject().clear();
	}

	public boolean containsKey(Object key) {
		if (getCache().containsKey(key)) {
			return true;
		}
		return getWrappedObject().containsKey(key);
	}

	public boolean containsValue(Object value) {
		if (getCache().containsValue(value)) {
			return true;
		}
		return getWrappedObject().containsValue(value);
	}

	@SuppressWarnings("unchecked")
	public V get(Object key) {
		V value = getCache().get(key);
		if (value == null) {
			value = getWrappedObject().get(key);
			getCache().put((K) key, value);
		}
		return value;
	}

	public boolean isEmpty() {
		if (!getCache().isEmpty()) {
			return false;
		}
		return getWrappedObject().isEmpty();
	}

	public Set<K> keySet() {
		return getWrappedObject().keySet();
	}

	public V put(K key, V value) {
		getCache().put(key, value);
		return getWrappedObject().put(key, value);
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		for (K k : m.keySet()) {
			put(k, m.get(k));
		}
	}

	public V remove(Object key) {
		getCache().remove(key);
		return getWrappedObject().remove(key);
	}

	public int size() {
		return getWrappedObject().size();
	}

	public Map<K, V> getWrappedObject() {
		if (source == null) {
			source = new HashMap<K, V>();
		}
		return source;
	}

	public void setWrappedObject(Map<K, V> object) {
		getCache().clear();
		this.source = object;
	}

	public Map<K, V> getCache() {
		if (cache == null) {
			cache = new SoftHashMap<K, V>();
		}
		return cache;
	}

}

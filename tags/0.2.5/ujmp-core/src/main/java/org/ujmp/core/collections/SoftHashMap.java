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

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SoftHashMap<K, V> extends AbstractMap<K, V> {
	private static final long serialVersionUID = 6784629567536455622L;

	private transient Map<K, SoftReference<V>> map = null;

	public SoftHashMap() {
	}

	public SoftHashMap(Map<? extends K, ? extends V> map) {
		this();
		putAll(map);
	}

	private Map<K, SoftReference<V>> getMap() {
		if (map == null) {
			map = new HashMap<K, SoftReference<V>>();
		}
		return map;
	}

	public void clear() {
		getMap().clear();
	}

	public V get(Object key) {
		SoftReference<V> v = getMap().get(key);
		return v == null ? null : v.get();
	}

	public Set<K> keySet() {
		return getMap().keySet();
	}

	public V put(K key, V value) {
		do {
			try {
				SoftReference<V> v = getMap().put(key, new SoftReference<V>(value));
				return v == null ? null : v.get();
			} catch (OutOfMemoryError e) {
				getMap().remove(getMap().keySet().iterator().next());
			}
		} while (!getMap().isEmpty());
		throw new OutOfMemoryError("removing all entries from Map could not avoid OutOfMemoryError");
	}

	public V remove(Object key) {
		SoftReference<V> v = getMap().remove(key);
		return v == null ? null : v.get();
	}

	public int size() {
		return getMap().size();
	}

}

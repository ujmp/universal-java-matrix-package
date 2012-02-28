/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

package org.ujmp.core.collections.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;


public class LazyMap<K, V> extends AbstractMap<K, V> {
	private static final long serialVersionUID = -5970508001189550492L;

	private transient Map<K, Callable<V>> map = null;

	private boolean useCache = true;

	public LazyMap() {
	}

	private Map<K, Callable<V>> getMap() {
		if (map == null) {
			map = new HashMap<K, Callable<V>>();
		}
		return map;
	}

	public void clear() {
		getMap().clear();
	}

	public V get(Object key) {
		Callable<V> cv = getMap().get(key);
		if (cv == null) {
			return null;
		}
		try {
			final V v = cv.call();
			if (useCache) {
				Callable<V> c = new Callable<V>() {
					public V call() throws Exception {
						return v;
					}
				};
				getMap().put((K) key, c);
			}
			return v;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Set<K> keySet() {
		return getMap().keySet();
	}

	public void put(K key, Callable<V> value) {
		getMap().put(key, value);
	}

	public V put(K key, final V value) {
		put(key, new Callable<V>() {

			public V call() throws Exception {
				return value;
			}
		});
		return null;
	}

	public V remove(Object key) {
		getMap().remove(key);
		return null;
	}

	public int size() {
		return getMap().size();
	}

}

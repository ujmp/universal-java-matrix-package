/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

import java.io.Flushable;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.ujmp.core.interfaces.Wrapper;

public class BufferedWriteMap<K, V> extends AbstractMap<K, V> implements Wrapper<Map<K, V>>,
		Flushable {
	private static final long serialVersionUID = -5656442600680089505L;

	private Map<K, V> map = null;

	private Map<K, V> writeBuffer = Collections.synchronizedMap(new HashMap<K, V>());

	public BufferedWriteMap(Map<K, V> map) {
		this.map = map;
	}

	@Override
	public synchronized void clear() {
		writeBuffer.clear();
		map.clear();
	}

	@Override
	public synchronized V get(Object key) {
		V v = writeBuffer.get(key);
		if (v == null) {
			v = map.get(key);
		}
		return v;
	}

	@Override
	public synchronized Set<K> keySet() {
		Set<K> keySet = new HashSet<K>();
		keySet.addAll(writeBuffer.keySet());
		keySet.addAll(map.keySet());
		return keySet;
	}

	@Override
	public synchronized V put(K key, V value) {
		V oldValue = get(key);
		writeBuffer.put(key, value);
		return oldValue;
	}

	@Override
	public synchronized V remove(Object key) {
		V oldValue = writeBuffer.remove(key);
		if (oldValue == null) {
			oldValue = map.remove(key);
		} else {
			map.remove(key);
		}
		return oldValue;
	}

	@Override
	public synchronized int size() {
		try {
			flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return map.size();
	}

	public synchronized Map<K, V> getWrappedObject() {
		return map;
	}

	public synchronized void setWrappedObject(Map<K, V> object) {
		try {
			flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.map = object;
	}

	public synchronized void flush() throws IOException {

	}

}

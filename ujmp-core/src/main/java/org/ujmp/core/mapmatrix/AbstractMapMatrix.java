/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

package org.ujmp.core.mapmatrix;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ujmp.core.objectmatrix.stub.AbstractDenseObjectMatrix2D;

public abstract class AbstractMapMatrix<K, V> extends AbstractDenseObjectMatrix2D implements
		MapMatrix<K, V> {
	private static final long serialVersionUID = 5571429371462164416L;

	public abstract Map<K, V> getMap();

	public final long[] getSize() {
		return new long[] { size(), 2 };
	}

	public final Object getObject(long row, long column) {
		return getObject((int) row, (int) column);
	}

	public final Object getObject(int row, int column) {
		Object mapKey = getKey(row);
		if (column == 0) {
			return mapKey;
		} else if (column == 1) {
			return (mapKey == null ? null : getMap().get(mapKey));
		} else {
			return null;
		}
	}

	public final void setObject(Object key, long row, long column) {
	}

	public final void setObject(Object key, int row, int column) {
	}

	public abstract MapMatrix<K, V> copy();

	// TODO: concurrentmodification exceptions can come from here
	@SuppressWarnings("rawtypes")
	private final Object getKey(int index) {
		if (getMap() instanceof List) {
			return ((List) getMap()).get(index);
		}
		Iterator<K> it = keySet().iterator();
		for (int i = 0; it.hasNext() && i < index; i++) {
			it.next();
		}
		return it.hasNext() ? it.next() : null;
	}

	public final boolean containsKey(Object key) {
		return getMap().containsKey(key);
	}

	public final boolean containsValue(Object value) {
		return getMap().containsValue(value);
	}

	public final Set<java.util.Map.Entry<K, V>> entrySet() {
		return getMap().entrySet();
	}

	public final V get(Object key) {
		return getMap().get(key);
	}

	public final boolean isEmpty() {
		return getMap().isEmpty();
	}

	public final Set<K> keySet() {
		return getMap().keySet();
	}

	public final V put(K key, V value) {
		V v = getMap().put(key, value);
		notifyGUIObject();
		return v;
	}

	public final void putAll(Map<? extends K, ? extends V> m) {
		getMap().putAll(m);
		notifyGUIObject();
	}

	public final V remove(Object key) {
		V v = getMap().remove(key);
		notifyGUIObject();
		return v;
	}

	public final int size() {
		return getMap().size();
	}

	public final Collection<V> values() {
		return getMap().values();
	}

	public final StorageType getStorageType() {
		return StorageType.MAP;
	}

	public final void clear() {
		getMap().clear();
	}

}

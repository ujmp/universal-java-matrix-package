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

package org.ujmp.core.collections;

import java.util.List;

import org.ujmp.core.genericmatrix.stub.AbstractDenseGenericMatrix2D;
import org.ujmp.core.mapmatrix.MapMatrix;

public abstract class AbstractMapMatrixList<K, V> extends AbstractDenseGenericMatrix2D<V> {
	private static final long serialVersionUID = -6522807188407425255L;

	private SortedListSet<K> keys = null;

	public AbstractMapMatrixList() {
	}

	private SortedListSet<K> getKeys() {
		if (keys == null) {
			keys = new SortedListSet<K>();
			for (MapMatrix<K, V> map : getList()) {
				keys.addAll(map.keySet());
			}
			for (int i = 0; i < keys.size(); i++) {
				setColumnLabelObject(i, keys.get(i));
			}
		}
		return keys;
	}

	public abstract List<MapMatrix<K, V>> getList();

	public long[] getSize() {
		return new long[] { getList().size(), getKeys().size() };
	}

	public V getObject(long row, long column) {
		return getObject((int) row, (int) column);
	}

	public V getObject(int row, int column) {
		MapMatrix<K, V> map = getList().get(row);
		K key = getKeys().get(column);
		return map.get(key);
	}

	public void setObject(V value, long row, long column) {
		setObject(value, (int) row, (int) column);
	}

	public void setObject(V value, int row, int column) {
		MapMatrix<K, V> map = getList().get(row);
		K key = getKeys().get(column);
		map.put(key, value);
	}

}

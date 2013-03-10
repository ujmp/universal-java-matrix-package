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

import java.util.HashMap;
import java.util.Map;

import org.ujmp.core.Matrix;

public class DefaultMapMatrix<K, V> extends AbstractMapMatrix<K, V> {
	private static final long serialVersionUID = -1764575977190231155L;

	private Map<K, V> map = null;

	public DefaultMapMatrix() {
		this.map = new HashMap<K, V>();
	}

	public DefaultMapMatrix(Map<K, V> map) {
		this.map = map;
	}

	@SuppressWarnings("unchecked")
	public DefaultMapMatrix(Matrix m) {
		this();
		if (m.getColumnCount() != 2) {
			throw new IllegalArgumentException("matrix must have two columns: key and value");
		}
		for (long row = m.getRowCount(); --row != -1;) {
			K key = (K) m.getAsObject(row, 0);
			if (key == null) {
				throw new IllegalArgumentException("key cell in row " + row
						+ " must not contain null");
			}
			put(key, (V) m.getAsObject(row, 1));
		}
	}

	public Map<K, V> getMap() {
		return map;
	}

	@Override
	public MapMatrix<K, V> clone() {
		MapMatrix<K, V> ret = new DefaultMapMatrix<K, V>();
		ret.putAll(map);
		return ret;
	}

}

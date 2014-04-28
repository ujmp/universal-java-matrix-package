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

package org.ujmp.core.mapmatrix;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.ujmp.core.Matrix;
import org.ujmp.core.matrix.factory.BaseMatrixFactory;

public class DefaultMapMatrix<K, V> extends AbstractMapMatrix<K, V> {
	private static final long serialVersionUID = -1764575977190231155L;

	private final Map<K, V> map;

	public DefaultMapMatrix() {
		this.map = new HashMap<K, V>();
	}

	public DefaultMapMatrix(Map<K, V> map) {
		this.map = map;
	}

	public final BaseMatrixFactory<? extends Matrix> getFactory() {
		throw new RuntimeException("not implemented");
	}

	public int size() {
		return map.size();
	}

	public V get(Object key) {
		return map.get(key);
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	protected void clearMap() {
		map.clear();
	}

	@Override
	protected V removeFromMap(Object key) {
		return map.remove(key);
	}

	@Override
	protected V putIntoMap(K key, V value) {
		return map.put(key, value);
	}

}

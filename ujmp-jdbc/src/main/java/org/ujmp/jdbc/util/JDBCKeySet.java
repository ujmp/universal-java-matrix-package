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

package org.ujmp.jdbc.util;

import java.sql.ResultSet;
import java.util.Iterator;

import org.ujmp.core.collections.set.AbstractSet;
import org.ujmp.jdbc.map.JDBCMapMatrix;

public class JDBCKeySet<K> extends AbstractSet<K> {
	private static final long serialVersionUID = 1429255834963921385L;

	private final JDBCMapMatrix<K, ?> map;
	private final ResultSet rs;
	private final Class<?> keyClass;

	public JDBCKeySet(JDBCMapMatrix<K, ?> map, ResultSet rs, Class<?> keyClass) {
		this.map = map;
		this.rs = rs;
		this.keyClass = keyClass;
	}

	@Override
	public boolean add(K value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(Object o) {
		return map.containsKey(o);
	}

	@Override
	public Iterator<K> iterator() {
		return new JDBCKeyIterator<K>(rs, keyClass);
	}

	@Override
	public int size() {
		return map.size();
	}
}

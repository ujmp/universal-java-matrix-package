/*
 * Copyright (C) 2008-2015 by Holger Arndt
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
import java.sql.SQLException;
import java.util.Iterator;

public class JDBCKeyIterator<K> implements Iterator<K> {

	private final ResultSet rs;
	private final Class<?> keyClass;
	private K currentKey = null;

	@SuppressWarnings("unchecked")
	public JDBCKeyIterator(ResultSet rs, Class<?> keyClass) {
		this.rs = rs;
		this.keyClass = keyClass;
		try {
			if (rs.next()) {
				currentKey = (K) SQLUtil.getObject(rs, 1, keyClass);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean hasNext() {
		return currentKey != null;
	}

	@SuppressWarnings("unchecked")
	public K next() {
		try {
			K lastKey = currentKey;
			if (rs.next()) {
				currentKey = (K) SQLUtil.getObject(rs, 1, keyClass);
			} else {
				currentKey = null;
			}
			return lastKey;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}
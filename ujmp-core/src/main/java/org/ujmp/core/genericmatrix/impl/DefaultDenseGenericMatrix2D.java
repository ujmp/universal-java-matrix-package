/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

package org.ujmp.core.genericmatrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.genericmatrix.stub.AbstractDenseGenericMatrix2D;

public class DefaultDenseGenericMatrix2D<A> extends AbstractDenseGenericMatrix2D<A> {
	private static final long serialVersionUID = 3132491298449205914L;

	private final Object[][] values;

	public DefaultDenseGenericMatrix2D(Matrix m) {
		super(m.getRowCount(), m.getColumnCount());
		values = new Object[(int) m.getRowCount()][(int) m.getColumnCount()];
		for (long[] c : m.allCoordinates()) {
			setAsObject(m.getAsObject(c), c);
		}
	}

	public DefaultDenseGenericMatrix2D(A[][] values) {
		super(values.length, values[0].length);
		this.values = values;
	}

	public DefaultDenseGenericMatrix2D(int rows, int columns) {
		super(rows, columns);
		values = new Object[rows][columns];
	}

	@SuppressWarnings("unchecked")
	public A getObject(long row, long column) {
		return (A) values[(int) row][(int) column];
	}

	public void setObject(Object value, long row, long column) {
		values[(int) row][(int) column] = value;
	}

	@SuppressWarnings("unchecked")
	public A getObject(int row, int column) {
		return (A) values[row][column];
	}

	public void setObject(A value, int row, int column) {
		values[row][column] = value;
	}

	public ValueType getValueType() {
		return ValueType.OBJECT;
	}

}

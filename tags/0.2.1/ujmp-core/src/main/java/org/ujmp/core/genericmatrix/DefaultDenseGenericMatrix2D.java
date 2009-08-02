/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.ujmp.core.genericmatrix;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;

public class DefaultDenseGenericMatrix2D<A> extends AbstractDenseGenericMatrix2D<A> {
	private static final long serialVersionUID = 3132491298449205914L;

	private Object[][] values = null;

	public DefaultDenseGenericMatrix2D(Matrix m) throws MatrixException {
		values = new Object[(int) m.getRowCount()][(int) m.getColumnCount()];
		for (long[] c : m.allCoordinates()) {
			setObject(m.getAsObject(c), c);
		}
	}

	public DefaultDenseGenericMatrix2D(A[][] values) throws MatrixException {
		this.values = values;
	}

	public DefaultDenseGenericMatrix2D(long... size) {
		values = new Object[(int) size[ROW]][(int) size[COLUMN]];
	}

	public long[] getSize() {
		return new long[] { values.length, values.length == 0 ? 0 : values[0].length };
	}

	@Override
	public long getRowCount() {
		return values.length;
	}

	@Override
	public long getColumnCount() {
		return values.length == 0 ? 0 : values[0].length;
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

	@Override
	public ValueType getValueType() {
		return ValueType.OBJECT;
	}

}

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

package org.ujmp.core.objectmatrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.HasObjectArray;
import org.ujmp.core.objectmatrix.stub.AbstractDenseObjectMatrix2D;

public class DefaultDenseObjectMatrix2D extends AbstractDenseObjectMatrix2D implements
		HasObjectArray {
	private static final long serialVersionUID = 8929799323811301397L;

	private final Object[] values;
	private final int rows;
	private final int cols;

	public DefaultDenseObjectMatrix2D(Matrix m) {
		super(m.getRowCount(), m.getColumnCount());
		this.rows = (int) m.getRowCount();
		this.cols = (int) m.getColumnCount();
		if (m instanceof DefaultDenseObjectMatrix2D) {
			Object[] v = ((DefaultDenseObjectMatrix2D) m).values;
			this.values = new Object[v.length];
			System.arraycopy(v, 0, this.values, 0, v.length);
		} else {
			this.values = new Object[rows * cols];
			for (long[] c : m.allCoordinates()) {
				setObject(m.getAsObject(c), c);
			}
		}
		if (m.getMetaData() != null) {
			setMetaData(m.getMetaData().clone());
		}
	}

	public DefaultDenseObjectMatrix2D(int rows, int columns) {
		super(rows, columns);
		this.rows = rows;
		this.cols = columns;
		this.values = new Object[rows * columns];
	}

	public DefaultDenseObjectMatrix2D(Object[] v, int rows, int cols) {
		super(rows, cols);
		this.rows = rows;
		this.cols = cols;
		this.values = v;
	}

	public Object getObject(long row, long column) {
		return values[(int) (column * rows + row)];
	}

	public void setObject(Object value, long row, long column) {
		values[(int) (column * rows + row)] = value;
	}

	public Object getObject(int row, int column) {
		return values[column * rows + row];
	}

	public void setObject(Object value, int row, int column) {
		values[column * rows + row] = value;
	}

	public final Matrix copy() {
		Object[] result = new Object[values.length];
		System.arraycopy(values, 0, result, 0, values.length);
		Matrix m = new DefaultDenseObjectMatrix2D(result, rows, cols);
		if (getMetaData() != null) {
			m.setMetaData(getMetaData().clone());
		}
		return m;
	}

	public final Matrix transpose() {
		final Object[] result = new Object[cols * rows];
		for (int c = rows; --c != -1;) {
			for (int r = cols; --r != -1;) {
				result[c * cols + r] = values[r * rows + c];
			}
		}
		return new DefaultDenseObjectMatrix2D(result, cols, rows);
	}

	public Object[] getObjectArray() {
		return values;
	}

}

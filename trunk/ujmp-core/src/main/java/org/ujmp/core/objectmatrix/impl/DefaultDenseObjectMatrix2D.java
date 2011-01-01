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

package org.ujmp.core.objectmatrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasObjectArray;
import org.ujmp.core.objectmatrix.stub.AbstractDenseObjectMatrix2D;

public class DefaultDenseObjectMatrix2D extends AbstractDenseObjectMatrix2D implements
		HasObjectArray {
	private static final long serialVersionUID = 8929799323811301397L;

	private Object[] values = null;

	private long[] size = null;

	private int rows = 0;

	private int cols = 0;

	public DefaultDenseObjectMatrix2D(Matrix m) throws MatrixException {
		super(m);
		this.rows = (int) m.getRowCount();
		this.cols = (int) m.getColumnCount();
		this.size = new long[] { rows, cols };
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
	}

	public DefaultDenseObjectMatrix2D(long... size) {
		super(size);
		this.rows = (int) size[ROW];
		this.cols = (int) size[COLUMN];
		this.size = new long[] { rows, cols };
		this.values = new Object[rows * cols];
	}

	public DefaultDenseObjectMatrix2D(Object[] v, int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.size = new long[] { rows, cols };
		this.values = v;
	}

	public long[] getSize() {
		return size;
	}

	public long getRowCount() {
		return rows;
	}

	public long getColumnCount() {
		return cols;
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

	public final Matrix copy() throws MatrixException {
		Object[] result = new Object[values.length];
		System.arraycopy(values, 0, result, 0, values.length);
		Matrix m = new DefaultDenseObjectMatrix2D(result, rows, cols);
		if (getAnnotation() != null) {
			m.setAnnotation(getAnnotation().clone());
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

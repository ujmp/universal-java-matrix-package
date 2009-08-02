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

package org.ujmp.core.longmatrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasLongArray;
import org.ujmp.core.longmatrix.stub.AbstractDenseLongMatrix2D;

public class DefaultDenseLongMatrix2D extends AbstractDenseLongMatrix2D implements HasLongArray {
	private static final long serialVersionUID = 3453563407578179206L;

	private long[] values = null;

	private long[] size = null;

	private int rows = 0;

	private int cols = 0;

	public DefaultDenseLongMatrix2D(Matrix m) throws MatrixException {
		this.rows = (int) m.getRowCount();
		this.cols = (int) m.getColumnCount();
		this.size = new long[] { rows, cols };
		if (m instanceof DefaultDenseLongMatrix2D) {
			long[] v = ((DefaultDenseLongMatrix2D) m).values;
			this.values = new long[v.length];
			System.arraycopy(v, 0, this.values, 0, v.length);
		} else {
			this.values = new long[rows * cols];
			for (long[] c : m.allCoordinates()) {
				setLong(m.getAsLong(c), c);
			}
		}
	}

	public DefaultDenseLongMatrix2D(long... size) {
		this.rows = (int) size[ROW];
		this.cols = (int) size[COLUMN];
		this.size = new long[] { rows, cols };
		this.values = new long[rows * cols];
	}

	public DefaultDenseLongMatrix2D(long[] v, int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.size = new long[] { rows, cols };
		this.values = v;
	}

	public long[] getSize() {
		return size;
	}

	@Override
	public long getRowCount() {
		return rows;
	}

	@Override
	public long getColumnCount() {
		return cols;
	}

	public long getLong(long row, long column) {
		return values[(int) (column * rows + row)];
	}

	public void setLong(long value, long row, long column) {
		values[(int) (column * rows + row)] = value;
	}

	public long getLong(int row, int column) {
		return values[column * rows + row];
	}

	public void setLong(long value, int row, int column) {
		values[column * rows + row] = value;
	}

	@Override
	public final Matrix plus(double v) {
		double[] result = new double[values.length];
		for (int i = result.length; --i != -1;) {
			result[i] = values[i] + v;
		}
		return new DefaultDenseDoubleMatrix2D(result, rows, cols);
	}

	@Override
	public final Matrix minus(double v) {
		double[] result = new double[values.length];
		for (int i = result.length; --i != -1;) {
			result[i] = values[i] - v;
		}
		return new DefaultDenseDoubleMatrix2D(result, rows, cols);
	}

	@Override
	public final Matrix times(double v) {
		double[] result = new double[values.length];
		for (int i = result.length; --i != -1;) {
			result[i] = values[i] * v;
		}
		return new DefaultDenseDoubleMatrix2D(result, rows, cols);
	}

	@Override
	public final Matrix divide(double v) {
		double[] result = new double[values.length];
		for (int i = result.length; --i != -1;) {
			result[i] = values[i] / v;
		}
		return new DefaultDenseDoubleMatrix2D(result, rows, cols);
	}

	@Override
	public final Matrix copy() throws MatrixException {
		long[] result = new long[values.length];
		System.arraycopy(values, 0, result, 0, values.length);
		Matrix m = new DefaultDenseLongMatrix2D(result, rows, cols);
		if (getAnnotation() != null) {
			m.setAnnotation(getAnnotation().clone());
		}
		return m;
	}

	@Override
	public final Matrix transpose() {
		final long[] result = new long[cols * rows];
		for (int c = rows; --c != -1;) {
			for (int r = cols; --r != -1;) {
				result[c * cols + r] = values[r * rows + c];
			}
		}
		return new DefaultDenseLongMatrix2D(result, cols, rows);
	}

	@Override
	public long[] getLongArray() {
		return values;
	}

}

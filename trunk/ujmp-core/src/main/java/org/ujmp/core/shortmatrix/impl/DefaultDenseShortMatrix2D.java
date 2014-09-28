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

package org.ujmp.core.shortmatrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.interfaces.HasShortArray;
import org.ujmp.core.shortmatrix.stub.AbstractDenseShortMatrix2D;

public class DefaultDenseShortMatrix2D extends AbstractDenseShortMatrix2D implements HasShortArray {
	private static final long serialVersionUID = 3387495964006716189L;

	private final short[] values;
	private final int rows;
	private final int cols;

	public DefaultDenseShortMatrix2D(Matrix m) {
		super(m.getSize());
		this.rows = (int) m.getRowCount();
		this.cols = (int) m.getColumnCount();
		if (m instanceof DefaultDenseShortMatrix2D) {
			short[] v = ((DefaultDenseShortMatrix2D) m).values;
			this.values = new short[v.length];
			System.arraycopy(v, 0, this.values, 0, v.length);
		} else {
			this.values = new short[rows * cols];
			for (long[] c : m.allCoordinates()) {
				setShort(m.getAsShort(c), c);
			}
		}
	}

	public DefaultDenseShortMatrix2D(long... size) {
		super(size);
		this.rows = (int) size[ROW];
		this.cols = (int) size[COLUMN];
		this.values = new short[rows * cols];
	}

	public DefaultDenseShortMatrix2D(short[] v, int rows, int cols) {
		super(new long[] { rows, cols });
		this.rows = rows;
		this.cols = cols;
		this.values = v;
	}

	public short getShort(long row, long column) {
		return values[(int) (column * rows + row)];
	}

	public void setShort(short value, long row, long column) {
		values[(int) (column * rows + row)] = value;
	}

	public short getShort(int row, int column) {
		return values[column * rows + row];
	}

	public void setShort(short value, int row, int column) {
		values[column * rows + row] = value;
	}

	public final Matrix plus(double v) {
		double[] result = new double[values.length];
		for (int i = result.length; --i != -1;) {
			result[i] = values[i] + v;
		}
		return new DefaultDenseDoubleMatrix2D(result, rows, cols);
	}

	public final Matrix minus(double v) {
		double[] result = new double[values.length];
		for (int i = result.length; --i != -1;) {
			result[i] = values[i] - v;
		}
		return new DefaultDenseDoubleMatrix2D(result, rows, cols);
	}

	public final Matrix times(double v) {
		double[] result = new double[values.length];
		for (int i = result.length; --i != -1;) {
			result[i] = values[i] * v;
		}
		return new DefaultDenseDoubleMatrix2D(result, rows, cols);
	}

	public final Matrix divide(double v) {
		double[] result = new double[values.length];
		for (int i = result.length; --i != -1;) {
			result[i] = values[i] / v;
		}
		return new DefaultDenseDoubleMatrix2D(result, rows, cols);
	}

	public final Matrix copy() {
		short[] result = new short[values.length];
		System.arraycopy(values, 0, result, 0, values.length);
		Matrix m = new DefaultDenseShortMatrix2D(result, rows, cols);
		if (getMetaData() != null) {
			m.setMetaData(getMetaData().clone());
		}
		return m;
	}

	public final Matrix transpose() {
		final short[] result = new short[cols * rows];
		for (int c = rows; --c != -1;) {
			for (int r = cols; --r != -1;) {
				result[c * cols + r] = values[r * rows + c];
			}
		}
		return new DefaultDenseShortMatrix2D(result, cols, rows);
	}

	public short[] getShortArray() {
		return values;
	}

}

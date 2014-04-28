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

package org.ujmp.core.charmatrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.charmatrix.stub.AbstractDenseCharMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.interfaces.HasColumnMajorCharArray1D;
import org.ujmp.core.matrix.factory.BaseMatrixFactory;

public class DefaultDenseCharMatrix2D extends AbstractDenseCharMatrix2D implements
		HasColumnMajorCharArray1D {
	private static final long serialVersionUID = 5579846181111172177L;

	private final char[] values;
	private final long[] size;
	private final int rows;
	private final int cols;

	public DefaultDenseCharMatrix2D(Matrix m) {
		super(m.getRowCount(), m.getColumnCount());
		this.rows = (int) m.getRowCount();
		this.cols = (int) m.getColumnCount();
		this.size = new long[] { rows, cols };
		if (m instanceof DefaultDenseCharMatrix2D) {
			char[] v = ((DefaultDenseCharMatrix2D) m).values;
			this.values = new char[v.length];
			System.arraycopy(v, 0, this.values, 0, v.length);
		} else {
			this.values = new char[rows * cols];
			for (long[] c : m.allCoordinates()) {
				setChar(m.getAsChar(c), c);
			}
		}
	}

	public DefaultDenseCharMatrix2D(int rows, int columns) {
		super(rows, columns);
		this.rows = rows;
		this.cols = columns;
		this.size = new long[] { rows, cols };
		this.values = new char[rows * cols];
	}

	public DefaultDenseCharMatrix2D(char[] v, int rows, int cols) {
		super(rows, cols);
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

	public char getChar(long row, long column) {
		return values[(int) (column * rows + row)];
	}

	public void setChar(char value, long row, long column) {
		values[(int) (column * rows + row)] = value;
	}

	public char getChar(int row, int column) {
		return values[column * rows + row];
	}

	public void setChar(char value, int row, int column) {
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
		char[] result = new char[values.length];
		System.arraycopy(values, 0, result, 0, values.length);
		Matrix m = new DefaultDenseCharMatrix2D(result, rows, cols);
		if (getMetaData() != null) {
			m.setMetaData(getMetaData().clone());
		}
		return m;
	}

	public final Matrix transpose() {
		final char[] result = new char[cols * rows];
		for (int c = rows; --c != -1;) {
			for (int r = cols; --r != -1;) {
				result[c * cols + r] = values[r * rows + c];
			}
		}
		return new DefaultDenseCharMatrix2D(result, cols, rows);
	}

	public char[] getColumnMajorCharArray1D() {
		return values;
	}

	public BaseMatrixFactory<? extends Matrix> getFactory() {
		throw new RuntimeException("not implemented");
	}

}

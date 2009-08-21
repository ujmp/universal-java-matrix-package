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

package org.ujmp.core.intmatrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasIntArray;
import org.ujmp.core.intmatrix.stub.AbstractDenseIntMatrix2D;

public class DefaultDenseIntMatrix2D extends AbstractDenseIntMatrix2D implements HasIntArray {
	private static final long serialVersionUID = 2911903176935762073L;

	private int[] values = null;

	private long[] size = null;

	private int rows = 0;

	private int cols = 0;

	public DefaultDenseIntMatrix2D(Matrix m) throws MatrixException {
		this.rows = (int) m.getRowCount();
		this.cols = (int) m.getColumnCount();
		this.size = new long[] { rows, cols };
		if (m instanceof DefaultDenseIntMatrix2D) {
			int[] v = ((DefaultDenseIntMatrix2D) m).values;
			this.values = new int[v.length];
			System.arraycopy(v, 0, this.values, 0, v.length);
		} else {
			this.values = new int[rows * cols];
			for (long[] c : m.allCoordinates()) {
				setInt(m.getAsInt(c), c);
			}
		}
	}

	public DefaultDenseIntMatrix2D(long... size) {
		this.rows = (int) size[ROW];
		this.cols = (int) size[COLUMN];
		this.size = new long[] { rows, cols };
		this.values = new int[rows * cols];
	}

	public DefaultDenseIntMatrix2D(int[] v, int rows, int cols) {
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

	public int getInt(long row, long column) {
		return values[(int) (column * rows + row)];
	}

	public void setInt(int value, long row, long column) {
		values[(int) (column * rows + row)] = value;
	}

	public int getInt(int row, int column) {
		return values[column * rows + row];
	}

	public void setInt(int value, int row, int column) {
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

	
	public final Matrix copy() throws MatrixException {
		int[] result = new int[values.length];
		System.arraycopy(values, 0, result, 0, values.length);
		Matrix m = new DefaultDenseIntMatrix2D(result, rows, cols);
		if (getAnnotation() != null) {
			m.setAnnotation(getAnnotation().clone());
		}
		return m;
	}

	
	public final Matrix transpose() {
		final int[] result = new int[cols * rows];
		for (int c = rows; --c != -1;) {
			for (int r = cols; --r != -1;) {
				result[c * cols + r] = values[r * rows + c];
			}
		}
		return new DefaultDenseIntMatrix2D(result, cols, rows);
	}

	
	public int[] getIntArray() {
		return values;
	}

}

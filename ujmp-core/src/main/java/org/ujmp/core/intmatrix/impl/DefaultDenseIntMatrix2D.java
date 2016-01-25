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

package org.ujmp.core.intmatrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.interfaces.HasIntArray;
import org.ujmp.core.intmatrix.stub.AbstractDenseIntMatrix2D;

public class DefaultDenseIntMatrix2D extends AbstractDenseIntMatrix2D implements HasIntArray {
	private static final long serialVersionUID = 2911903176935762073L;

	private final int[] values;
	private final int rows;
	private final int cols;

	public DefaultDenseIntMatrix2D(int rows, int cols) {
		super(rows, cols);
		this.rows = rows;
		this.cols = cols;
		this.values = new int[rows * cols];
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

	public final Matrix copy() {
		DefaultDenseIntMatrix2D m = new DefaultDenseIntMatrix2D(rows, cols);
		int[] result = m.values;
		System.arraycopy(values, 0, result, 0, values.length);
		if (getMetaData() != null) {
			m.setMetaData(getMetaData().clone());
		}
		return m;
	}

	public final Matrix transpose() {
		DefaultDenseIntMatrix2D m = new DefaultDenseIntMatrix2D(cols, rows);
		final int[] result = m.values;
		for (int c = rows; --c != -1;) {
			for (int r = cols; --r != -1;) {
				result[c * cols + r] = values[r * rows + c];
			}
		}
		return m;
	}

	public int[] getIntArray() {
		return values;
	}

}

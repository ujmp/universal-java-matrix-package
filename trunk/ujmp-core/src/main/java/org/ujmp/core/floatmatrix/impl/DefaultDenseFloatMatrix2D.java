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

package org.ujmp.core.floatmatrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.floatmatrix.stub.AbstractDenseFloatMatrix2D;
import org.ujmp.core.interfaces.HasFloatArray;

public class DefaultDenseFloatMatrix2D extends AbstractDenseFloatMatrix2D implements HasFloatArray {
	private static final long serialVersionUID = -5449462775185759895L;

	private final float[] values;
	private final int rows;
	private final int cols;

	public DefaultDenseFloatMatrix2D(int rows, int columns) {
		super(rows, columns);
		this.rows = rows;
		this.cols = columns;
		this.values = new float[rows * cols];
	}

	public float getFloat(long row, long column) {
		return values[(int) (column * rows + row)];
	}

	public void setFloat(float value, long row, long column) {
		values[(int) (column * rows + row)] = value;
	}

	public float getFloat(int row, int column) {
		return values[column * rows + row];
	}

	public void setFloat(float value, int row, int column) {
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
		DefaultDenseFloatMatrix2D m = new DefaultDenseFloatMatrix2D(rows, cols);
		float[] result = m.values;
		System.arraycopy(values, 0, result, 0, values.length);
		if (getMetaData() != null) {
			m.setMetaData(getMetaData().clone());
		}
		return m;
	}

	public final Matrix transpose() {
		DefaultDenseFloatMatrix2D m = new DefaultDenseFloatMatrix2D(cols, rows);
		final float[] result = m.values;
		for (int c = rows; --c != -1;) {
			for (int r = cols; --r != -1;) {
				result[c * cols + r] = values[r * rows + c];
			}
		}
		return m;
	}

	public float[] getFloatArray() {
		return values;
	}

}

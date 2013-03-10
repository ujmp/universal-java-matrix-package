/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

package org.ujmp.core.doublematrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasColumnMajorDoubleArray1D;

public class DefaultDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D implements
		HasColumnMajorDoubleArray1D {
	private static final long serialVersionUID = -3605416349143850650L;

	private final double[] values;

	private final long[] size;

	private final int rows;

	private final int cols;

	public DefaultDenseDoubleMatrix2D(Matrix m) throws MatrixException {
		super(m);
		this.rows = (int) m.getRowCount();
		this.cols = (int) m.getColumnCount();
		this.size = new long[] { rows, cols };
		if (m instanceof DefaultDenseDoubleMatrix2D) {
			double[] v = ((DefaultDenseDoubleMatrix2D) m).values;
			this.values = new double[v.length];
			System.arraycopy(v, 0, this.values, 0, v.length);
		} else {
			this.values = new double[rows * cols];
			for (long[] c : m.allCoordinates()) {
				setDouble(m.getAsDouble(c), c);
			}
		}
	}

	public DefaultDenseDoubleMatrix2D(long... size) {
		super(size);
		this.rows = (int) size[ROW];
		this.cols = (int) size[COLUMN];
		this.size = new long[] { rows, cols };
		this.values = new double[rows * cols];
	}

	public DefaultDenseDoubleMatrix2D(long rows, long cols) {
		this.rows = (int) rows;
		this.cols = (int) cols;
		this.size = new long[] { rows, cols };
		this.values = new double[this.rows * this.cols];
	}

	public DefaultDenseDoubleMatrix2D(double[] v, int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.size = new long[] { rows, cols };
		this.values = v;
	}

	public final long[] getSize() {
		return size;
	}

	public final long getRowCount() {
		return rows;
	}

	public final long getColumnCount() {
		return cols;
	}

	public final double getDouble(long row, long column) {
		return values[(int) (column * rows + row)];
	}

	public final double getAsDouble(long row, long column) {
		return values[(int) (column * rows + row)];
	}

	public final double getAsDouble(int row, int column) {
		return values[(column * rows + row)];
	}

	public final void setDouble(double value, long row, long column) {
		values[(int) (column * rows + row)] = value;
	}

	public final void setAsDouble(double value, long row, long column) {
		values[(int) (column * rows + row)] = value;
	}

	public final double getDouble(int row, int column) {
		return values[column * rows + row];
	}

	public final void setDouble(double value, int row, int column) {
		values[column * rows + row] = value;
	}

	public final void setAsDouble(double value, int row, int column) {
		values[column * rows + row] = value;
	}

	public final Matrix copy() throws MatrixException {
		double[] result = new double[values.length];
		System.arraycopy(values, 0, result, 0, values.length);
		Matrix m = new DefaultDenseDoubleMatrix2D(result, rows, cols);
		if (getAnnotation() != null) {
			m.setAnnotation(getAnnotation().clone());
		}
		return m;
	}

	public final double[] getColumnMajorDoubleArray1D() {
		return values;
	}

}

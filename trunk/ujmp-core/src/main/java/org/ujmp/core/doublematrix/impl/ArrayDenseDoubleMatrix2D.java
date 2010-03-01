/*
 * Copyright (C) 2008-2010 by Holger Arndt
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
import org.ujmp.core.interfaces.HasRowMajorDoubleArray2D;

public class ArrayDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D implements
		HasRowMajorDoubleArray2D {
	private static final long serialVersionUID = 3132491298449205914L;

	private double[][] values = null;

	public ArrayDenseDoubleMatrix2D(Matrix m) throws MatrixException {
		if (m instanceof ArrayDenseDoubleMatrix2D) {
			double[][] v = ((ArrayDenseDoubleMatrix2D) m).values;
			this.values = new double[v.length][v[0].length];
			for (int r = v.length; --r != -1;) {
				for (int c = v[0].length; --c != -1;) {
					values[r][c] = v[r][c];
				}
			}
		} else {
			values = new double[(int) m.getRowCount()][(int) m.getColumnCount()];
			for (long[] c : m.allCoordinates()) {
				setAsDouble(m.getAsDouble(c), c);
			}
		}
	}

	public ArrayDenseDoubleMatrix2D(double[]... v) {
		this.values = v;
	}

	public ArrayDenseDoubleMatrix2D(long... size) {
		values = new double[(int) size[ROW]][(int) size[COLUMN]];
	}

	public ArrayDenseDoubleMatrix2D(long rows, long cols) {
		values = new double[(int) rows][(int) cols];
	}

	public ArrayDenseDoubleMatrix2D(double[] v) {
		this.values = new double[v.length][1];
		for (int r = v.length; --r != -1;) {
			values[r][0] = v[r];
		}
	}

	public long[] getSize() {
		return new long[] { values.length, values.length == 0 ? 0 : values[0].length };
	}

	public long getRowCount() {
		return values.length;
	}

	public long getColumnCount() {
		return values.length == 0 ? 0 : values[0].length;
	}

	public double getDouble(long row, long column) {
		return values[(int) row][(int) column];
	}

	public void setDouble(double value, long row, long column) {
		values[(int) row][(int) column] = value;
	}

	public double getDouble(int row, int column) {
		return values[row][column];
	}

	public void setDouble(double value, int row, int column) {
		values[row][column] = value;
	}

	public final Matrix copy() throws MatrixException {
		double[][] result = new double[values.length][values[0].length];
		for (int r = result.length; --r != -1;) {
			for (int c = result[0].length; --c != -1;) {
				result[r][c] = values[r][c];
			}
		}
		Matrix m = new ArrayDenseDoubleMatrix2D(result);
		if (getAnnotation() != null) {
			m.setAnnotation(getAnnotation().clone());
		}
		return m;
	}

	public boolean containsNaN() {
		for (int r = values.length; --r != -1;) {
			for (int c = values[0].length; --c != -1;) {
				if (Double.isNaN(values[r][c])) {
					return true;
				}
			}
		}
		return false;
	}

	public double[][] getRowMajorDoubleArray2D() {
		return values;
	}

}

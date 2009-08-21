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

package org.ujmp.core.floatmatrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.floatmatrix.stub.AbstractDenseFloatMatrix2D;

public class ArrayDenseFloatMatrix2D extends AbstractDenseFloatMatrix2D {
	private static final long serialVersionUID = 6599658021180126741L;

	private float[][] values = null;

	public ArrayDenseFloatMatrix2D(Matrix m) throws MatrixException {
		if (m instanceof ArrayDenseFloatMatrix2D) {
			float[][] v = ((ArrayDenseFloatMatrix2D) m).values;
			this.values = new float[v.length][v[0].length];
			for (int r = v.length; --r >= 0;) {
				for (int c = v[0].length; --c >= 0;) {
					values[r][c] = v[r][c];
				}
			}
		} else {
			values = new float[(int) m.getRowCount()][(int) m.getColumnCount()];
			for (long[] c : m.allCoordinates()) {
				setAsFloat(m.getAsFloat(c), c);
			}
		}
	}

	public ArrayDenseFloatMatrix2D(float[]... v) {
		this.values = v;
	}

	public ArrayDenseFloatMatrix2D(long... size) {
		values = new float[(int) size[ROW]][(int) size[COLUMN]];
	}

	public ArrayDenseFloatMatrix2D(float[] v) {
		this.values = new float[v.length][1];
		for (int r = v.length; --r >= 0;) {
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

	public float getFloat(long row, long column) {
		return values[(int) row][(int) column];
	}

	public void setFloat(float value, long row, long column) {
		values[(int) row][(int) column] = value;
	}

	public float getFloat(int row, int column) {
		return values[row][column];
	}

	public void setFloat(float value, int row, int column) {
		values[row][column] = value;
	}

	
	public final Matrix transpose() {
		float[][] result = new float[values[0].length][values.length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[c][r];
			}
		}
		return new ArrayDenseFloatMatrix2D(result);
	}

}

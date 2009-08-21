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
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.intmatrix.IntMatrix;
import org.ujmp.core.intmatrix.stub.AbstractDenseIntMatrix2D;

public class SimpleDenseIntMatrix2D extends AbstractDenseIntMatrix2D {
	private static final long serialVersionUID = -3331102463077119410L;

	private int[][] values = null;

	public SimpleDenseIntMatrix2D(Matrix m) throws MatrixException {
		if (m instanceof SimpleDenseIntMatrix2D) {
			int[][] v = ((SimpleDenseIntMatrix2D) m).values;
			this.values = new int[v.length][v[0].length];
			for (int r = v.length; --r >= 0;) {
				for (int c = v[0].length; --c >= 0;) {
					values[r][c] = v[r][c];
				}
			}
		} else {
			values = new int[(int) m.getRowCount()][(int) m.getColumnCount()];
			for (long[] c : m.allCoordinates()) {
				setAsDouble(m.getAsDouble(c), c);
			}
		}
	}

	public SimpleDenseIntMatrix2D(int[]... v) {
		this.values = v;
	}

	public SimpleDenseIntMatrix2D(long... size) {
		values = new int[(int) size[ROW]][(int) size[COLUMN]];
	}

	public SimpleDenseIntMatrix2D(int[] v) {
		this.values = new int[v.length][1];
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

	public int getInt(long row, long column) {
		return values[(int) row][(int) column];
	}

	public void setInt(int value, long row, long column) {
		values[(int) row][(int) column] = value;
	}

	public int getInt(int row, int column) {
		return values[row][column];
	}

	public void setInt(int value, int row, int column) {
		values[row][column] = value;
	}

	
	public final IntMatrix transpose() {
		int[][] result = new int[values[0].length][values.length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[c][r];
			}
		}
		return new SimpleDenseIntMatrix2D(result);
	}

	
	public final IntMatrix plus(double v) {
		int vInt = (int) v;
		int[][] result = new int[values.length][values[0].length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[r][c] + vInt;
			}
		}
		return new SimpleDenseIntMatrix2D(result);
	}

	
	public final IntMatrix minus(double v) {
		int vInt = (int) v;
		int[][] result = new int[values.length][values[0].length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[r][c] - vInt;
			}
		}
		return new SimpleDenseIntMatrix2D(result);
	}

	
	public final IntMatrix times(double v) {
		int vInt = (int) v;
		int[][] result = new int[values.length][values[0].length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[r][c] * vInt;
			}
		}
		return new SimpleDenseIntMatrix2D(result);
	}

	
	public final IntMatrix divide(double v) {
		int vInt = (int) v;
		int[][] result = new int[values.length][values[0].length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[r][c] / vInt;
			}
		}
		return new SimpleDenseIntMatrix2D(result);
	}

	public final IntMatrix plus(IntMatrix m2) throws MatrixException {
		int[][] result = new int[values.length][values[0].length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[r][c] + m2.getAsInt(r, c);
			}
		}
		return new SimpleDenseIntMatrix2D(result);
	}

	public final IntMatrix minus(IntMatrix m2) throws MatrixException {
		int[][] result = new int[values.length][values[0].length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[r][c] - m2.getAsInt(r, c);
			}
		}
		return new SimpleDenseIntMatrix2D(result);
	}

	public final IntMatrix times(IntMatrix m2) throws MatrixException {
		int[][] result = new int[values.length][values[0].length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[r][c] * m2.getAsInt(r, c);
			}
		}
		return new SimpleDenseIntMatrix2D(result);
	}

	public final IntMatrix divide(IntMatrix m2) throws MatrixException {
		int[][] result = new int[values.length][values[0].length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[r][c] / m2.getAsInt(r, c);
			}
		}
		return new SimpleDenseIntMatrix2D(result);
	}

	public IntMatrix mtimes(IntMatrix matrix) throws MatrixException {
		if (values[0].length != matrix.getRowCount()) {
			throw new MatrixException("matrices have wrong size: "
					+ Coordinates.toString(getSize()) + " and "
					+ Coordinates.toString(matrix.getSize()));
		}

		int i, j, k;
		int sum;
		int[][] ret = new int[values.length][(int) matrix.getColumnCount()];

		for (i = values.length; --i >= 0;) {
			for (j = ret[0].length; --j >= 0;) {
				sum = 0;
				for (k = values[0].length; --k >= 0;) {
					sum += values[i][k] * matrix.getAsDouble(k, j);
				}
				ret[i][j] = sum;
			}
		}

		return new SimpleDenseIntMatrix2D(ret);
	}

	public boolean containsNaN() {
		for (int r = values.length; --r >= 0;) {
			for (int c = values[0].length; --c >= 0;) {
				if (Double.isNaN(values[r][c])) {
					return true;
				}
			}
		}
		return false;
	}

}

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

package org.ujmp.core.booleanmatrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.booleanmatrix.stub.AbstractDenseBooleanMatrix2D;

public class ArrayDenseBooleanMatrix2D extends AbstractDenseBooleanMatrix2D {
	private static final long serialVersionUID = -4334380160318525360L;

	private final boolean[][] values;

	public ArrayDenseBooleanMatrix2D(Matrix m) {
		super(m.getRowCount(), m.getColumnCount());
		if (m instanceof ArrayDenseBooleanMatrix2D) {
			boolean[][] v = ((ArrayDenseBooleanMatrix2D) m).values;
			this.values = new boolean[v.length][v[0].length];
			for (int r = v.length; --r >= 0;) {
				for (int c = v[0].length; --c >= 0;) {
					values[r][c] = v[r][c];
				}
			}
		} else {
			values = new boolean[(int) m.getRowCount()][(int) m.getColumnCount()];
			for (long[] c : m.allCoordinates()) {
				setAsBoolean(m.getAsBoolean(c), c);
			}
		}
	}

	public ArrayDenseBooleanMatrix2D(boolean[]... v) {
		super(v.length, v[0].length);
		this.values = v;
	}

	public ArrayDenseBooleanMatrix2D(int rows, int columns) {
		super(rows, columns);
		values = new boolean[rows][columns];
	}

	public ArrayDenseBooleanMatrix2D(boolean... v) {
		super(v.length, 1);
		this.values = new boolean[v.length][1];
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

	public boolean getBoolean(long row, long column) {
		return values[(int) row][(int) column];
	}

	public void setBoolean(boolean value, long row, long column) {
		values[(int) row][(int) column] = value;
	}

	public boolean getBoolean(int row, int column) {
		return values[row][column];
	}

	public void setBoolean(boolean value, int row, int column) {
		values[row][column] = value;
	}

	public final Matrix transpose() {
		boolean[][] result = new boolean[values[0].length][values.length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[c][r];
			}
		}
		return new ArrayDenseBooleanMatrix2D(result);
	}

}

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

public class ArrayDenseCharMatrix2D extends AbstractDenseCharMatrix2D {
	private static final long serialVersionUID = -172129670809500830L;

	private final char[][] values;

	public ArrayDenseCharMatrix2D(Matrix m) {
		super(m.getRowCount(), m.getColumnCount());
		if (m instanceof ArrayDenseCharMatrix2D) {
			char[][] v = ((ArrayDenseCharMatrix2D) m).values;
			this.values = new char[v.length][v[0].length];
			for (int r = v.length; --r >= 0;) {
				for (int c = v[0].length; --c >= 0;) {
					values[r][c] = v[r][c];
				}
			}
		} else {
			values = new char[(int) m.getRowCount()][(int) m.getColumnCount()];
			for (long[] c : m.allCoordinates()) {
				setAsChar(m.getAsChar(c), c);
			}
		}
	}

	public ArrayDenseCharMatrix2D(char[]... v) {
		super(v.length, v[0].length);
		this.values = v;
	}

	public ArrayDenseCharMatrix2D(int rows, int columns) {
		super(rows, columns);
		values = new char[rows][columns];
	}

	public ArrayDenseCharMatrix2D(char[] v) {
		super(v.length, 1);
		this.values = new char[v.length][1];
		for (int r = v.length; --r >= 0;) {
			values[r][0] = v[r];
		}
	}

	public char getChar(long row, long column) {
		return values[(int) row][(int) column];
	}

	public void setChar(char value, long row, long column) {
		values[(int) row][(int) column] = value;
	}

	public char getChar(int row, int column) {
		return values[row][column];
	}

	public void setChar(char value, int row, int column) {
		values[row][column] = value;
	}

	public final Matrix transpose() {
		char[][] result = new char[values[0].length][values.length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[c][r];
			}
		}
		return new ArrayDenseCharMatrix2D(result);
	}

}

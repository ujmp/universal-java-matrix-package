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

package org.ujmp.core.bytematrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.bytematrix.stub.AbstractDenseByteMatrix2D;

public class ArrayDenseByteMatrix2D extends AbstractDenseByteMatrix2D {
	private static final long serialVersionUID = 1111734188254187991L;

	private final byte[][] values;

	public ArrayDenseByteMatrix2D(byte[]... v) {
		super(v.length, v[0].length);
		this.values = v;
	}

	public ArrayDenseByteMatrix2D(int rows, int columns) {
		super(rows, columns);
		values = new byte[rows][columns];
	}

	public ArrayDenseByteMatrix2D(byte[] v) {
		super(v.length, 1);
		this.values = new byte[v.length][1];
		for (int r = v.length; --r >= 0;) {
			values[r][0] = v[r];
		}
	}

	public byte getByte(long row, long column) {
		return values[(int) row][(int) column];
	}

	public void setByte(byte value, long row, long column) {
		values[(int) row][(int) column] = value;
	}

	public byte getByte(int row, int column) {
		return values[row][column];
	}

	public void setByte(byte value, int row, int column) {
		values[row][column] = value;
	}

	public final Matrix transpose() {
		byte[][] result = new byte[values[0].length][values.length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[c][r];
			}
		}
		return new ArrayDenseByteMatrix2D(result);
	}

}

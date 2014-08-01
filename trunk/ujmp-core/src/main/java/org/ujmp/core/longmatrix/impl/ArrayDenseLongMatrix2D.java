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

package org.ujmp.core.longmatrix.impl;

import org.ujmp.core.DenseMatrix2D;
import org.ujmp.core.Matrix;
import org.ujmp.core.longmatrix.stub.AbstractDenseLongMatrix2D;
import org.ujmp.core.matrix.factory.DenseMatrix2DFactory;

public class ArrayDenseLongMatrix2D extends AbstractDenseLongMatrix2D {
	private static final long serialVersionUID = 2888746188860361425L;

	private long[][] values = null;

	public ArrayDenseLongMatrix2D(Matrix m) {
		super(m.getSize());
		if (m instanceof ArrayDenseLongMatrix2D) {
			long[][] v = ((ArrayDenseLongMatrix2D) m).values;
			this.values = new long[v.length][v[0].length];
			for (int r = v.length; --r >= 0;) {
				for (int c = v[0].length; --c >= 0;) {
					values[r][c] = v[r][c];
				}
			}
		} else {
			values = new long[(int) m.getRowCount()][(int) m.getColumnCount()];
			for (long[] c : m.allCoordinates()) {
				setAsLong(m.getAsLong(c), c);
			}
		}
	}

	public ArrayDenseLongMatrix2D(long[]... v) {
		super(new long[] { v.length, v[0].length });
		this.values = v;
	}

	public ArrayDenseLongMatrix2D(long... size) {
		super(size);
		values = new long[(int) size[ROW]][(int) size[COLUMN]];
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

	public long getLong(long row, long column) {
		return values[(int) row][(int) column];
	}

	public void setLong(long value, long row, long column) {
		values[(int) row][(int) column] = value;
	}

	public long getLong(int row, int column) {
		return values[row][column];
	}

	public void setLong(long value, int row, int column) {
		values[row][column] = value;
	}

	public final Matrix transpose() {
		long[][] result = new long[values[0].length][values.length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[c][r];
			}
		}
		return new ArrayDenseLongMatrix2D(result);
	}

	public DenseMatrix2DFactory<? extends DenseMatrix2D> getFactory() {
		throw new RuntimeException("not implemented");
	}

}

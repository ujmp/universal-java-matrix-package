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

package org.ujmp.core.shortmatrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.shortmatrix.stub.AbstractDenseShortMatrix2D;

public class SimpleDenseShortMatrix2D extends AbstractDenseShortMatrix2D {
	private static final long serialVersionUID = 4034565357457805099L;

	private short[][] values = null;

	public SimpleDenseShortMatrix2D(Matrix m) throws MatrixException {
		super(m);
		if (m instanceof SimpleDenseShortMatrix2D) {
			short[][] v = ((SimpleDenseShortMatrix2D) m).values;
			this.values = new short[v.length][v[0].length];
			for (int r = v.length; --r >= 0;) {
				for (int c = v[0].length; --c >= 0;) {
					values[r][c] = v[r][c];
				}
			}
		} else {
			values = new short[(int) m.getRowCount()][(int) m.getColumnCount()];
			for (long[] c : m.allCoordinates()) {
				setAsShort(m.getAsShort(c), c);
			}
		}
	}

	public SimpleDenseShortMatrix2D(short[]... v) {
		this.values = v;
	}

	public SimpleDenseShortMatrix2D(long... size) {
		super(size);
		values = new short[(int) size[ROW]][(int) size[COLUMN]];
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

	public short getShort(long row, long column) {
		return values[(int) row][(int) column];
	}

	public void setShort(short value, long row, long column) {
		values[(int) row][(int) column] = value;
	}

	public final Matrix transpose() {
		short[][] result = new short[values[0].length][values.length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[c][r];
			}
		}
		return new SimpleDenseShortMatrix2D(result);
	}

}

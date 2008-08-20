/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

package org.ujmp.core.shortmatrix;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public class DefaultDenseShortMatrix2D extends AbstractDenseShortMatrix2D {
	private static final long serialVersionUID = 4034565357457805099L;

	private short[][] values = null;

	public DefaultDenseShortMatrix2D(Matrix m) throws MatrixException {
		if (m instanceof DefaultDenseShortMatrix2D) {
			short[][] v = ((DefaultDenseShortMatrix2D) m).values;
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

	public DefaultDenseShortMatrix2D(short[]... v) {
		this.values = v;
	}

	public DefaultDenseShortMatrix2D(long... size) {
		values = new short[(int) size[ROW]][(int) size[COLUMN]];
	}

	public long[] getSize() {
		return new long[] { values.length, values[0].length };
	}

	@Override
	public long getRowCount() {
		return values.length;
	}

	@Override
	public long getColumnCount() {
		return values[0].length;
	}

	public short getShort(long row, long column) {
		return values[(int) row][(int) column];
	}

	public void setShort(short value, long row, long column) {
		values[(int) row][(int) column] = value;
	}

	@Override
	public final Matrix transpose() {
		short[][] result = new short[values[0].length][values.length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[c][r];
			}
		}
		return new DefaultDenseShortMatrix2D(result);
	}

}

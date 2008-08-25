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

package org.ujmp.core.datematrix;

import java.util.Date;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public class DefaultDenseDateMatrix2D extends AbstractDenseDateMatrix2D {
	private static final long serialVersionUID = -3791410138052404116L;

	private Date[][] values = null;

	public DefaultDenseDateMatrix2D(Matrix m) throws MatrixException {
		if (m instanceof DefaultDenseDateMatrix2D) {
			Date[][] v = ((DefaultDenseDateMatrix2D) m).values;
			this.values = new Date[v.length][v[0].length];
			for (int r = v.length; --r >= 0;) {
				for (int c = v[0].length; --c >= 0;) {
					values[r][c] = v[r][c];
				}
			}
		} else {
			values = new Date[(int) m.getRowCount()][(int) m.getColumnCount()];
			for (long[] c : m.allCoordinates()) {
				setAsDate(m.getAsDate(c), c);
			}
		}
	}

	public DefaultDenseDateMatrix2D(Date[]... v) {
		this.values = v;
	}

	public DefaultDenseDateMatrix2D(long... size) {
		values = new Date[(int) size[ROW]][(int) size[COLUMN]];
	}

	public DefaultDenseDateMatrix2D(Date[] v) {
		this.values = new Date[v.length][1];
		for (int r = v.length; --r >= 0;) {
			values[r][0] = v[r];
		}
	}

	public long[] getSize() {
		return new long[] { values.length, values.length == 0 ? 0 : values[0].length };
	}

	@Override
	public long getRowCount() {
		return values.length;
	}

	@Override
	public long getColumnCount() {
		return values.length == 0 ? 0 : values[0].length;
	}

	public Date getDate(long row, long column) {
		return values[(int) row][(int) column];
	}

	public void setDate(Date value, long row, long column) {
		values[(int) row][(int) column] = value;
	}

	@Override
	public final Matrix transpose() {
		Date[][] result = new Date[values[0].length][values.length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[c][r];
			}
		}
		return new DefaultDenseDateMatrix2D(result);
	}

}

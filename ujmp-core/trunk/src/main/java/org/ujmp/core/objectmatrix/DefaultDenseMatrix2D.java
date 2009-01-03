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

package org.ujmp.core.objectmatrix;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public class DefaultDenseMatrix2D extends AbstractDenseMatrix2D {
	private static final long serialVersionUID = 7405594663844848420L;

	private Object[][] values = null;

	public DefaultDenseMatrix2D(Object[]... values) {
		this.values = values;
	}

	public DefaultDenseMatrix2D(long... size) {
		values = new Object[(int) size[ROW]][(int) size[COLUMN]];
	}

	public DefaultDenseMatrix2D(Matrix m) throws MatrixException {
		if (m instanceof DefaultDenseMatrix2D) {
			Object[][] v = ((DefaultDenseMatrix2D) m).values;
			this.values = new Object[v.length][v[0].length];
			for (int r = v.length; --r >= 0;) {
				for (int c = v[0].length; --c >= 0;) {
					values[r][c] = v[r][c];
				}
			}
		} else {
			values = new Object[(int) m.getRowCount()][(int) m.getColumnCount()];
			for (long[] c : m.allCoordinates()) {
				setObject(m.getObject(c), c);
			}
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

	public Object getObject(long row, long column) {
		return values[(int) row][(int) column];
	}

	public void setObject(Object value, long row, long column) {
		values[(int) row][(int) column] = value;
	}

}

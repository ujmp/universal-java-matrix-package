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

package org.ujmp.core.stringmatrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasStringArray;
import org.ujmp.core.stringmatrix.stub.AbstractDenseStringMatrix2D;

public class DefaultDenseStringMatrix2D extends AbstractDenseStringMatrix2D implements
		HasStringArray {
	private static final long serialVersionUID = 1643931435178952984L;

	private String[] values = null;

	private long[] size = null;

	private int rows = 0;

	private int cols = 0;

	public DefaultDenseStringMatrix2D(Matrix m) throws MatrixException {
		this.rows = (int) m.getRowCount();
		this.cols = (int) m.getColumnCount();
		this.size = new long[] { rows, cols };
		if (m instanceof DefaultDenseStringMatrix2D) {
			String[] v = ((DefaultDenseStringMatrix2D) m).values;
			this.values = new String[v.length];
			System.arraycopy(v, 0, this.values, 0, v.length);
		} else {
			this.values = new String[rows * cols];
			for (long[] c : m.allCoordinates()) {
				setString(m.getAsString(c), c);
			}
		}
	}

	public DefaultDenseStringMatrix2D(long... size) {
		this.rows = (int) size[ROW];
		this.cols = (int) size[COLUMN];
		this.size = new long[] { rows, cols };
		this.values = new String[rows * cols];
	}

	public DefaultDenseStringMatrix2D(String[] v, int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.size = new long[] { rows, cols };
		this.values = v;
	}

	public long[] getSize() {
		return size;
	}

	
	public long getRowCount() {
		return rows;
	}

	
	public long getColumnCount() {
		return cols;
	}

	public String getString(long row, long column) {
		return values[(int) (column * rows + row)];
	}

	public void setString(String value, long row, long column) {
		values[(int) (column * rows + row)] = value;
	}

	public String getString(int row, int column) {
		return values[column * rows + row];
	}

	public void setString(String value, int row, int column) {
		values[column * rows + row] = value;
	}

	
	public final Matrix copy() throws MatrixException {
		String[] result = new String[values.length];
		System.arraycopy(values, 0, result, 0, values.length);
		Matrix m = new DefaultDenseStringMatrix2D(result, rows, cols);
		if (getAnnotation() != null) {
			m.setAnnotation(getAnnotation().clone());
		}
		return m;
	}

	
	public final Matrix transpose() {
		final String[] result = new String[cols * rows];
		for (int c = rows; --c != -1;) {
			for (int r = cols; --r != -1;) {
				result[c * cols + r] = values[r * rows + c];
			}
		}
		return new DefaultDenseStringMatrix2D(result, cols, rows);
	}

	
	public String[] getStringArray() {
		return values;
	}

}

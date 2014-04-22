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

package org.ujmp.core.stringmatrix.impl;

import org.ujmp.core.DenseMatrix2D;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.HasStringArray;
import org.ujmp.core.matrix.factory.DenseMatrix2DFactory;
import org.ujmp.core.stringmatrix.stub.AbstractDenseStringMatrix2D;
import org.ujmp.core.util.MathUtil;

public class DefaultDenseStringMatrix2D extends AbstractDenseStringMatrix2D implements
		HasStringArray {
	private static final long serialVersionUID = 1643931435178952984L;

	private final String[] values;
	private final long[] size;
	private final int rows;
	private final int cols;

	public DefaultDenseStringMatrix2D(Matrix m) {
		super(m.getRowCount(), m.getColumnCount());
		this.rows = MathUtil.longToInt(m.getRowCount());
		this.cols = MathUtil.longToInt(m.getColumnCount());
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
		if (m.getAnnotation() != null) {
			setAnnotation(m.getAnnotation().clone());
		}
	}

	public DefaultDenseStringMatrix2D(int rows, int columns) {
		super(rows, columns);
		this.rows = rows;
		this.cols = columns;
		this.size = new long[] { rows, columns };
		this.values = new String[rows * columns];
	}

	public DefaultDenseStringMatrix2D(String[] v, int rows, int cols) {
		super(rows, cols);
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

	public final Matrix copy() {
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

	public DenseMatrix2DFactory<DenseMatrix2D> getFactory() {
		return new DenseMatrix2DFactory<DenseMatrix2D>() {

			public DenseMatrix2D zeros(long rows, long cols) {
				return new DefaultDenseStringMatrix2D(MathUtil.longToInt(rows),
						MathUtil.longToInt(cols));
			}

			public DenseMatrix2D zeros(long... size) {
				return new DefaultDenseStringMatrix2D(MathUtil.longToInt(size[ROW]),
						MathUtil.longToInt(size[COLUMN]));
			}
		};
	}

}

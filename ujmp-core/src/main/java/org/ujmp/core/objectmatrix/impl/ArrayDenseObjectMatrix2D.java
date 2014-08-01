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

package org.ujmp.core.objectmatrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.objectmatrix.DenseObjectMatrix2D;
import org.ujmp.core.objectmatrix.factory.DenseObjectMatrix2DFactory;
import org.ujmp.core.objectmatrix.stub.AbstractDenseObjectMatrix2D;

public class ArrayDenseObjectMatrix2D extends AbstractDenseObjectMatrix2D {
	private static final long serialVersionUID = -7051381548902586972L;

	private Object[][] values = null;

	public ArrayDenseObjectMatrix2D(Object[]... values) {
		super(values.length, values[0].length);
		this.values = values;
	}

	public ArrayDenseObjectMatrix2D(int rows, int columns) {
		super(rows, columns);
		values = new Object[rows][columns];
	}

	public ArrayDenseObjectMatrix2D(Matrix m) {
		super(m.getRowCount(), m.getColumnCount());
		if (m instanceof ArrayDenseObjectMatrix2D) {
			Object[][] v = ((ArrayDenseObjectMatrix2D) m).values;
			this.values = new Object[v.length][v[0].length];
			for (int r = v.length; --r >= 0;) {
				for (int c = v[0].length; --c >= 0;) {
					values[r][c] = v[r][c];
				}
			}
		} else {
			values = new Object[(int) m.getRowCount()][(int) m.getColumnCount()];
			for (long[] c : m.allCoordinates()) {
				setObject(m.getAsObject(c), c);
			}
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

	public Object getObject(long row, long column) {
		return values[(int) row][(int) column];
	}

	public void setObject(Object value, long row, long column) {
		values[(int) row][(int) column] = value;
	}

	public Object getObject(int row, int column) {
		return values[row][column];
	}

	public void setObject(Object value, int row, int column) {
		values[row][column] = value;
	}

	public DenseObjectMatrix2DFactory<? extends DenseObjectMatrix2D> getFactory() {
		throw new RuntimeException("not implemented");
	}

}

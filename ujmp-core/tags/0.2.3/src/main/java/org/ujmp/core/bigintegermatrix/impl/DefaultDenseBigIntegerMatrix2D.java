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

package org.ujmp.core.bigintegermatrix.impl;

import java.math.BigInteger;

import org.ujmp.core.Matrix;
import org.ujmp.core.bigintegermatrix.stub.AbstractDenseBigIntegerMatrix2D;
import org.ujmp.core.exceptions.MatrixException;

public class DefaultDenseBigIntegerMatrix2D extends AbstractDenseBigIntegerMatrix2D {
	private static final long serialVersionUID = 443424906231157395L;

	private BigInteger[] values = null;

	private long[] size = null;

	private int rows = 0;

	private int cols = 0;

	public DefaultDenseBigIntegerMatrix2D(Matrix m) throws MatrixException {
		this.rows = (int) m.getRowCount();
		this.cols = (int) m.getColumnCount();
		this.size = new long[] { rows, cols };
		if (m instanceof DefaultDenseBigIntegerMatrix2D) {
			BigInteger[] v = ((DefaultDenseBigIntegerMatrix2D) m).values;
			this.values = new BigInteger[v.length];
			System.arraycopy(v, 0, this.values, 0, v.length);
		} else {
			this.values = new BigInteger[rows * cols];
			for (long[] c : m.allCoordinates()) {
				setBigInteger(m.getAsBigInteger(c), c);
			}
		}
	}

	public DefaultDenseBigIntegerMatrix2D(long... size) {
		this.rows = (int) size[ROW];
		this.cols = (int) size[COLUMN];
		this.size = new long[] { rows, cols };
		this.values = new BigInteger[rows * cols];
	}

	public DefaultDenseBigIntegerMatrix2D(BigInteger[] v, int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.size = new long[] { rows, cols };
		this.values = v;
	}

	public long[] getSize() {
		return size;
	}

	@Override
	public long getRowCount() {
		return rows;
	}

	@Override
	public long getColumnCount() {
		return cols;
	}

	public BigInteger getBigInteger(long row, long column) {
		return values[(int) (column * rows + row)];
	}

	public void setBigInteger(BigInteger value, long row, long column) {
		values[(int) (column * rows + row)] = value;
	}

	public BigInteger getBigInteger(int row, int column) {
		return values[column * rows + row];
	}

	public void setBigInteger(BigInteger value, int row, int column) {
		values[column * rows + row] = value;
	}

}

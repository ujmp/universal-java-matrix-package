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

package org.ujmp.core.bigintegermatrix.impl;

import java.math.BigInteger;
import java.util.Arrays;

import org.ujmp.core.Matrix;
import org.ujmp.core.bigintegermatrix.stub.AbstractDenseBigIntegerMatrix2D;

public class DefaultDenseBigIntegerMatrix2D extends AbstractDenseBigIntegerMatrix2D {
	private static final long serialVersionUID = 443424906231157395L;

	private final BigInteger[] values;
	private final long[] size;
	private final int rows;
	private final int cols;

	public DefaultDenseBigIntegerMatrix2D(Matrix m) {
		super(m);
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

	public DefaultDenseBigIntegerMatrix2D(int rows, int cols) {
		super(rows, cols);
		this.rows = rows;
		this.cols = cols;
		this.size = new long[] { rows, cols };
		this.values = new BigInteger[rows * cols];
		Arrays.fill(values, BigInteger.ZERO);
	}

	public DefaultDenseBigIntegerMatrix2D(BigInteger[] v, int rows, int cols) {
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

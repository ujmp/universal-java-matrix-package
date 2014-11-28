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

import org.ujmp.core.bigintegermatrix.stub.AbstractDenseBigIntegerMatrix2D;

public class ArrayDenseBigIntegerMatrix2D extends AbstractDenseBigIntegerMatrix2D {
	private static final long serialVersionUID = 3110279640095711135L;

	private final BigInteger[][] values;

	public ArrayDenseBigIntegerMatrix2D(BigInteger[]... v) {
		super(v.length, v[0].length);
		this.values = v;
	}

	public ArrayDenseBigIntegerMatrix2D(int rows, int columns) {
		super(rows, columns);
		values = new BigInteger[rows][columns];
		for (int r = values.length; --r != -1;) {
			Arrays.fill(values[r], BigInteger.ZERO);
		}
	}

	public ArrayDenseBigIntegerMatrix2D(BigInteger... v) {
		super(v.length, 1);
		this.values = new BigInteger[v.length][1];
		for (int r = v.length; --r >= 0;) {
			values[r][0] = v[r];
		}
	}

	public BigInteger getBigInteger(long row, long column) {
		return values[(int) row][(int) column];
	}

	public void setBigInteger(BigInteger value, long row, long column) {
		values[(int) row][(int) column] = value;
	}

	public BigInteger getBigInteger(int row, int column) {
		return values[row][column];
	}

	public void setBigInteger(BigInteger value, int row, int column) {
		values[row][column] = value;
	}

}

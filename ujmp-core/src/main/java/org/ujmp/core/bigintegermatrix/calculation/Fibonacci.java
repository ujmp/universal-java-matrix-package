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

package org.ujmp.core.bigintegermatrix.calculation;

import java.math.BigInteger;

import org.ujmp.core.Matrix;

public class Fibonacci extends AbstractBigIntegerCalculation {
	private static final long serialVersionUID = 2890785080437683248L;

	private BigInteger[] data = null;

	public Fibonacci(int count) {
		data = new BigInteger[count];
		data[0] = BigInteger.ZERO;
		data[1] = BigInteger.ONE;
		for (int i = 2; i < data.length; i++) {
			data[i] = data[i - 1].add(data[i - 2]);
		}
	}

	public BigInteger getBigInteger(long... coordinates) {
		return data[(int) coordinates[ROW]];
	}

	public long[] getSize() {
		return new long[] { data.length, 1 };
	}

	public static Matrix calc(int count) {
		return new Fibonacci(count).calcNew();
	}

}

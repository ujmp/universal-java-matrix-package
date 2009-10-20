/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

package org.ujmp.core.util;

import java.lang.reflect.Method;

import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;

public abstract class BLAS {

	private static Method dgemm = null;

	static {
		try {
			Class<?> c = Class.forName("org.netlib.blas.Dgemm");
			dgemm = c.getMethod("dgemm", String.class, String.class, Integer.TYPE, Integer.TYPE,
					Integer.TYPE, Double.TYPE, double[].class, Integer.TYPE, Integer.TYPE,
					double[].class, Integer.TYPE, Integer.TYPE, Double.TYPE, double[].class,
					Integer.TYPE, Integer.TYPE);
		} catch (Throwable e) {
			System.out.println("arpack-combo.jar not found, cannot use BLAS");
		}
	}

	public static synchronized void dgemm(int rows, int retcols, int cols, int i, double[] values,
			int j, int rows2, double[] m2, int k, int l, int m, double[] ret, int n, int rows3) {
		try {
			dgemm.invoke(null, "N", "N", rows, retcols, cols, i, values, j, rows2, m2, k, l, m,
					ret, n, rows3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isAvailable() {
		return dgemm != null;
	}

	public static final DenseDoubleMatrix2D mtimes(DefaultDenseDoubleMatrix2D A,
			DefaultDenseDoubleMatrix2D B) {
		if (A.getColumnCount() != B.getRowCount()) {
			throw new MatrixException("matrices have wrong size: "
					+ Coordinates.toString(A.getSize()) + " and "
					+ Coordinates.toString(B.getSize()));
		}
		final int alpha = 1;
		final int beta = 1;
		final int acols = (int) A.getColumnCount();
		final int arows = (int) A.getRowCount();
		final int bcols = (int) B.getColumnCount();
		final int brows = (int) B.getRowCount();
		final double[] avalues = A.getDoubleArray();
		final double[] bvalues = B.getDoubleArray();
		final double[] cvalues = new double[arows * bcols];
		BLAS.dgemm(arows, bcols, acols, alpha, avalues, 0, arows, bvalues, 0, brows, beta, cvalues,
				0, arows);
		final DefaultDenseDoubleMatrix2D c = new DefaultDenseDoubleMatrix2D(cvalues, arows, bcols);
		return c;
	}

}

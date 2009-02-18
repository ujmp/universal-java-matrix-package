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

package org.ujmp.core;

import java.lang.reflect.Method;

public abstract class BLAS {

	private static Method dgemm = null;

	static {
		try {
			Class<?> c = Class.forName("org.netlib.blas.Dgemm");
			dgemm = c.getMethod("dgemm", String.class, String.class, Integer.TYPE, Integer.TYPE,
					Integer.TYPE, Double.TYPE, double[].class, Integer.TYPE, Integer.TYPE,
					double[].class, Integer.TYPE, Integer.TYPE, Double.TYPE, double[].class,
					Integer.TYPE, Integer.TYPE);
		} catch (Exception e) {
		}
	}

	public static synchronized void dgemm(Object object, String string, String string2, int rows,
			int retcols, int cols, int i, double[] values, int j, int rows2, double[] m2, int k,
			int l, int m, double[] ret, int n, int rows3) {
		try {
			dgemm.invoke(object, string, string2, rows, retcols, cols, i, values, j, rows2, m2, k,
					l, m, ret, n, rows3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

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

package org.ujmp.jblas.calculation;

import org.jblas.DoubleMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.HasColumnMajorDoubleArray1D;
import org.ujmp.jblas.JBlasDenseDoubleMatrix2D;

public class Solve
		implements
		org.ujmp.core.doublematrix.calculation.general.decomposition.Solve<Matrix> {

	public static Solve INSTANCE = new Solve();

	public Matrix calc(Matrix a, Matrix b) {
		final DoubleMatrix a2;
		final DoubleMatrix b2;
		if (a instanceof JBlasDenseDoubleMatrix2D) {
			a2 = ((JBlasDenseDoubleMatrix2D) a).getWrappedObject();
		} else if (a instanceof HasColumnMajorDoubleArray1D) {
			a2 = new JBlasDenseDoubleMatrix2D(a.getRowCount(), a
					.getColumnCount(), ((HasColumnMajorDoubleArray1D) a)
					.getColumnMajorDoubleArray1D()).getWrappedObject();
		} else {
			a2 = new JBlasDenseDoubleMatrix2D(a).getWrappedObject();
		}
		if (b instanceof JBlasDenseDoubleMatrix2D) {
			b2 = ((JBlasDenseDoubleMatrix2D) b).getWrappedObject();
		} else if (b instanceof HasColumnMajorDoubleArray1D) {
			b2 = new JBlasDenseDoubleMatrix2D(b.getRowCount(), b
					.getColumnCount(), ((HasColumnMajorDoubleArray1D) b)
					.getColumnMajorDoubleArray1D()).getWrappedObject();
		} else {
			b2 = new JBlasDenseDoubleMatrix2D(b).getWrappedObject();
		}
		try {
			final DoubleMatrix x = org.jblas.Solve.solve(a2, b2);
			return new JBlasDenseDoubleMatrix2D(x);
		} catch (final Throwable t) {
			return org.ujmp.core.doublematrix.calculation.general.decomposition.Solve.UJMPSQUARE
					.calc(a, b);
		}
	}

}

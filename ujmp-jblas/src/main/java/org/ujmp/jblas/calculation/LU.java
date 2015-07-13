/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

import org.jblas.Decompose;
import org.jblas.DoubleMatrix;
import org.jblas.Solve;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.HasColumnMajorDoubleArray1D;
import org.ujmp.core.util.MathUtil;
import org.ujmp.jblas.JBlasDenseDoubleMatrix2D;

public class LU implements org.ujmp.core.doublematrix.calculation.general.decomposition.LU<Matrix> {

	public static LU INSTANCE = new LU();

	public Matrix[] calc(Matrix source) {
		final DoubleMatrix matrix;
		if (source instanceof JBlasDenseDoubleMatrix2D) {
			matrix = ((JBlasDenseDoubleMatrix2D) source).getWrappedObject();
		} else if (source instanceof HasColumnMajorDoubleArray1D) {
			matrix = new JBlasDenseDoubleMatrix2D(MathUtil.longToInt(source.getRowCount()), MathUtil.longToInt(source
					.getColumnCount()), ((HasColumnMajorDoubleArray1D) source).getColumnMajorDoubleArray1D())
					.getWrappedObject();
		} else {
			matrix = new JBlasDenseDoubleMatrix2D(source).getWrappedObject();
		}
		final Decompose.LUDecomposition<DoubleMatrix> lu = Decompose.lu(matrix);
		final Matrix l = new JBlasDenseDoubleMatrix2D(lu.l);
		final Matrix u = new JBlasDenseDoubleMatrix2D(lu.u);
		final Matrix p = new JBlasDenseDoubleMatrix2D(lu.p.transpose());
		return new Matrix[] { l, u, p };
	}

	public Matrix solve(Matrix a, Matrix b) {
		final DoubleMatrix a2;
		final DoubleMatrix b2;
		if (a instanceof JBlasDenseDoubleMatrix2D) {
			a2 = ((JBlasDenseDoubleMatrix2D) a).getWrappedObject();
		} else if (a instanceof HasColumnMajorDoubleArray1D) {
			a2 = new JBlasDenseDoubleMatrix2D(MathUtil.longToInt(a.getRowCount()), MathUtil.longToInt(a
					.getColumnCount()), ((HasColumnMajorDoubleArray1D) a).getColumnMajorDoubleArray1D())
					.getWrappedObject();
		} else {
			a2 = new JBlasDenseDoubleMatrix2D(a).getWrappedObject();
		}
		if (b instanceof JBlasDenseDoubleMatrix2D) {
			b2 = ((JBlasDenseDoubleMatrix2D) b).getWrappedObject();
		} else if (b instanceof HasColumnMajorDoubleArray1D) {
			b2 = new JBlasDenseDoubleMatrix2D(MathUtil.longToInt(b.getRowCount()), MathUtil.longToInt(b
					.getColumnCount()), ((HasColumnMajorDoubleArray1D) b).getColumnMajorDoubleArray1D())
					.getWrappedObject();
		} else {
			b2 = new JBlasDenseDoubleMatrix2D(b).getWrappedObject();
		}

		final DoubleMatrix x = Solve.solve(a2, b2);
		return new JBlasDenseDoubleMatrix2D(x);
	}

}

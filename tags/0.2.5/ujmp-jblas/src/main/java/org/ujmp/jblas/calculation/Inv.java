/*
 * Copyright (C) 2008-2010 by Holger Arndt
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
import org.jblas.Solve;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.HasColumnMajorDoubleArray1D;
import org.ujmp.jblas.JBlasDenseDoubleMatrix2D;

public class Inv
		implements
		org.ujmp.core.doublematrix.calculation.general.decomposition.Inv<Matrix> {

	public static final Inv INSTANCE = new Inv();

	public Matrix calc(Matrix source) {
		final DoubleMatrix m1;
		if (source instanceof JBlasDenseDoubleMatrix2D) {
			m1 = ((JBlasDenseDoubleMatrix2D) source).getWrappedObject();
		} else if (source instanceof HasColumnMajorDoubleArray1D) {
			m1 = new JBlasDenseDoubleMatrix2D(source.getRowCount(), source
					.getColumnCount(), ((HasColumnMajorDoubleArray1D) source)
					.getColumnMajorDoubleArray1D()).getWrappedObject();
		} else {
			m1 = new JBlasDenseDoubleMatrix2D(source).getWrappedObject();
		}

		final DoubleMatrix I = DoubleMatrix.eye(m1.getRows());
		final DoubleMatrix result = Solve.solve(m1, I);
		return new JBlasDenseDoubleMatrix2D(result);
	}
}

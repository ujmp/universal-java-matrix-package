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
import org.jblas.Eigen;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.HasColumnMajorDoubleArray1D;
import org.ujmp.jblas.JBlasDenseDoubleMatrix2D;

public class Eig
		implements
		org.ujmp.core.doublematrix.calculation.general.decomposition.Eig<Matrix> {

	public static Eig INSTANCE = new Eig();

	public Matrix[] calc(Matrix source) {
		final DoubleMatrix matrix;
		if (source instanceof JBlasDenseDoubleMatrix2D) {
			matrix = ((JBlasDenseDoubleMatrix2D) source).getWrappedObject();
		} else if (source instanceof HasColumnMajorDoubleArray1D) {
			matrix = new JBlasDenseDoubleMatrix2D(source.getRowCount(), source
					.getColumnCount(), ((HasColumnMajorDoubleArray1D) source)
					.getColumnMajorDoubleArray1D()).getWrappedObject();
		} else {
			matrix = new JBlasDenseDoubleMatrix2D(source).getWrappedObject();
		}
		final DoubleMatrix[] eig = Eigen.symmetricEigenvectors(matrix);
		final Matrix e = new JBlasDenseDoubleMatrix2D(eig[0]);
		final Matrix d = new JBlasDenseDoubleMatrix2D(eig[1]);
		return new Matrix[] { e, d };
	}

}

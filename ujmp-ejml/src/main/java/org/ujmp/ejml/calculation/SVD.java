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

package org.ujmp.ejml.calculation;

import org.ejml.alg.dense.decomposition.svd.SvdNumericalRecipes;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.ejml.EJMLDenseDoubleMatrix2D;

public class SVD
		implements
		org.ujmp.core.doublematrix.calculation.general.decomposition.SVD<Matrix> {

	public static SVD INSTANCE = new SVD();

	@Override
	public Matrix[] calc(Matrix source) {
		SvdNumericalRecipes svd = new SvdNumericalRecipes();
		if (source instanceof EJMLDenseDoubleMatrix2D) {
			svd
					.decompose(((EJMLDenseDoubleMatrix2D) source)
							.getWrappedObject());
		} else {
			svd.decompose(new EJMLDenseDoubleMatrix2D(source)
					.getWrappedObject());
		}
		Matrix u = new EJMLDenseDoubleMatrix2D(svd.getU());
		Matrix v = new EJMLDenseDoubleMatrix2D(svd.getV());
		double[] svs = svd.getW();
		Matrix s = MatrixFactory.sparse(u.getColumnCount(), v.getColumnCount());
		for (int i = (int) Math.min(s.getRowCount(), s.getColumnCount()); --i >= 0;) {
			s.setAsDouble(svs[i], i, i);
		}
		return new Matrix[] { u, s, v };
	}

}

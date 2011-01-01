/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

package org.ujmp.ojalgo.calculation;

import org.ojalgo.matrix.decomposition.SingularValue;
import org.ojalgo.matrix.decomposition.SingularValueDecomposition;
import org.ujmp.core.Matrix;
import org.ujmp.ojalgo.OjalgoDenseDoubleMatrix2D;

public class SVD
		implements
		org.ujmp.core.doublematrix.calculation.general.decomposition.SVD<Matrix> {

	public static SVD INSTANCE = new SVD();

	public Matrix[] calc(Matrix source) {
		final SingularValue<Double> svd = SingularValueDecomposition
				.makePrimitive();
		if (source instanceof OjalgoDenseDoubleMatrix2D) {
			svd
					.compute(((OjalgoDenseDoubleMatrix2D) source)
							.getWrappedObject());
		} else {
			svd.compute(new OjalgoDenseDoubleMatrix2D(source)
					.getWrappedObject());
		}
		final Matrix u = new OjalgoDenseDoubleMatrix2D(svd.getQ1());
		final Matrix s = new OjalgoDenseDoubleMatrix2D(svd.getD());
		final Matrix v = new OjalgoDenseDoubleMatrix2D(svd.getQ2());
		return new Matrix[] { u, s, v };
	}

}

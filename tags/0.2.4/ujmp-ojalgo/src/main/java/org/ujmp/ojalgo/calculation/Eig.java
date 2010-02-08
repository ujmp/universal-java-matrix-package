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

package org.ujmp.ojalgo.calculation;

import org.ojalgo.matrix.decomposition.Eigenvalue;
import org.ojalgo.matrix.decomposition.EigenvalueDecomposition;
import org.ojalgo.matrix.store.PrimitiveDenseStore;
import org.ujmp.core.Matrix;
import org.ujmp.ojalgo.OjalgoDenseDoubleMatrix2D;

public class Eig
		implements
		org.ujmp.core.doublematrix.calculation.general.decomposition.Eig<Matrix> {

	public static Eig INSTANCE = new Eig();

	public Matrix[] calc(Matrix source) {
		try {
			final Eigenvalue<Double> evd = EigenvalueDecomposition
					.makePrimitive();
			PrimitiveDenseStore matrix = null;
			if (source instanceof OjalgoDenseDoubleMatrix2D) {
				matrix = ((OjalgoDenseDoubleMatrix2D) source)
						.getWrappedObject();
			} else {
				matrix = new OjalgoDenseDoubleMatrix2D(source)
						.getWrappedObject();
			}
			evd.compute(matrix);
			final Matrix v = new OjalgoDenseDoubleMatrix2D(evd.getV());
			final Matrix d = new OjalgoDenseDoubleMatrix2D(evd.getD());
			return new Matrix[] { v, d };
		} catch (Throwable t) {
			return org.ujmp.core.doublematrix.calculation.general.decomposition.Eig.UJMP
					.calc(source);
		}
	}
}

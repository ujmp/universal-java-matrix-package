/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;
import org.ujmp.core.Matrix;
import org.ujmp.ejml.EJMLDenseDoubleMatrix2D;

public class Solve
		implements
		org.ujmp.core.doublematrix.calculation.general.decomposition.Solve<Matrix> {

	public static Solve INSTANCE = new Solve();

	public Matrix calc(Matrix a, Matrix b) {
		DenseMatrix64F a2 = null;
		DenseMatrix64F b2 = null;
		if (a instanceof EJMLDenseDoubleMatrix2D) {
			a2 = ((EJMLDenseDoubleMatrix2D) a).getWrappedObject();
		} else {
			a2 = new EJMLDenseDoubleMatrix2D(a).getWrappedObject();
		}
		if (b instanceof EJMLDenseDoubleMatrix2D) {
			b2 = ((EJMLDenseDoubleMatrix2D) b).getWrappedObject();
		} else {
			b2 = new EJMLDenseDoubleMatrix2D(b).getWrappedObject();
		}
		DenseMatrix64F x = new DenseMatrix64F(a2.numCols, b2.numCols);
		CommonOps.solve(a2, b2, x);
		return new EJMLDenseDoubleMatrix2D(x);
	}
}

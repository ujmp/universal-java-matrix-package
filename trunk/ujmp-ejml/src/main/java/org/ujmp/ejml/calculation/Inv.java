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

package org.ujmp.ejml.calculation;

import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;
import org.ujmp.core.Matrix;
import org.ujmp.ejml.EJMLDenseDoubleMatrix2D;

public class Inv
		implements
		org.ujmp.core.doublematrix.calculation.general.decomposition.Inv<Matrix> {

	public static Inv INSTANCE = new Inv();

	public Matrix calc(Matrix source) {
		DenseMatrix64F matrix = null;
		if (source instanceof EJMLDenseDoubleMatrix2D) {
			matrix = ((EJMLDenseDoubleMatrix2D) source).getWrappedObject();
		} else {
			matrix = new EJMLDenseDoubleMatrix2D(source).getWrappedObject();
		}
		DenseMatrix64F ret = new DenseMatrix64F(matrix.numRows, matrix.numCols);
		CommonOps.invert(matrix, ret);
		return new EJMLDenseDoubleMatrix2D(ret);
	}

}

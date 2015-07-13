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

package org.ujmp.commonsmath;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.factory.AbstractDenseDoubleMatrix2DFactory;

public class CommonsMathDenseDoubleMatrix2DFactory extends
		AbstractDenseDoubleMatrix2DFactory<CommonsMathArrayDenseDoubleMatrix2D> {

	public static final CommonsMathDenseDoubleMatrix2DFactory INSTANCE = new CommonsMathDenseDoubleMatrix2DFactory();

	public CommonsMathArrayDenseDoubleMatrix2D zeros(long rows, long columns) {
		return new CommonsMathArrayDenseDoubleMatrix2D(rows, columns);
	}

	public DenseDoubleMatrix2D dense(Array2DRowRealMatrix matrix) {
		return new CommonsMathArrayDenseDoubleMatrix2D(matrix);
	}

	public DenseDoubleMatrix2D dense(BlockRealMatrix matrix) {
		return new CommonsMathBlockDenseDoubleMatrix2D(matrix);
	}

	public Matrix dense(RealMatrix matrix) {
		if (matrix == null) {
			throw new RuntimeException("matrix is null");
		} else if (matrix instanceof BlockRealMatrix) {
			return dense((BlockRealMatrix) matrix);
		} else if (matrix instanceof Array2DRowRealMatrix) {
			return dense((Array2DRowRealMatrix) matrix);
		} else {
			throw new RuntimeException("implementation not available: " + matrix.getClass());
		}
	}

}

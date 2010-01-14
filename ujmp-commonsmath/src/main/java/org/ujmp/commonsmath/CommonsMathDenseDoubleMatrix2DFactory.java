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

package org.ujmp.commonsmath;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.BlockRealMatrix;
import org.apache.commons.math.linear.RealMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.factory.AbstractDoubleMatrix2DFactory;
import org.ujmp.core.exceptions.MatrixException;

public class CommonsMathDenseDoubleMatrix2DFactory extends
		AbstractDoubleMatrix2DFactory {
	private static final long serialVersionUID = -4938756141859017575L;

	public static final CommonsMathDenseDoubleMatrix2DFactory INSTANCE = new CommonsMathDenseDoubleMatrix2DFactory();

	public DenseDoubleMatrix2D dense(long rows, long columns)
			throws MatrixException {
		return new CommonsMathArrayDenseDoubleMatrix2D(rows, columns);
	}

	public DenseDoubleMatrix2D dense(Array2DRowRealMatrix matrix)
			throws MatrixException {
		return new CommonsMathArrayDenseDoubleMatrix2D(matrix);
	}

	public DenseDoubleMatrix2D dense(BlockRealMatrix matrix)
			throws MatrixException {
		return new CommonsMathBlockDenseDoubleMatrix2D(matrix);
	}

	public Matrix dense(RealMatrix matrix) {
		if (matrix instanceof BlockRealMatrix) {
			return dense((BlockRealMatrix) matrix);
		} else if (matrix instanceof Array2DRowRealMatrix) {
			return dense((Array2DRowRealMatrix) matrix);
		} else {
			throw new MatrixException("not available");
		}
	}

}

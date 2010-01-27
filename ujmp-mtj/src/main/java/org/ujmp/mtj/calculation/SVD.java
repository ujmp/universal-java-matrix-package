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

package org.ujmp.mtj.calculation;

import no.uib.cipr.matrix.DenseMatrix;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.mtj.MTJDenseDoubleMatrix2D;

public class SVD
		implements
		org.ujmp.core.doublematrix.calculation.general.decomposition.SVD<Matrix> {

	public static org.ujmp.core.doublematrix.calculation.general.decomposition.SVD<Matrix> INSTANCE = new SVD();

	public Matrix[] calc(Matrix source) {
		try {
			DenseMatrix m = null;
			if (source instanceof MTJDenseDoubleMatrix2D) {
				m = ((MTJDenseDoubleMatrix2D) source).getWrappedObject();
			} else {
				m = new MTJDenseDoubleMatrix2D(source).getWrappedObject();
			}
			no.uib.cipr.matrix.SVD svd = no.uib.cipr.matrix.SVD.factorize(m);
			Matrix u = new MTJDenseDoubleMatrix2D(svd.getU());
			Matrix v = new MTJDenseDoubleMatrix2D(svd.getVt()).transpose();
			double[] svs = svd.getS();
			Matrix s = MatrixFactory.sparse(source.getSize());
			for (int i = (int) Math.min(s.getRowCount(), s.getColumnCount()); --i >= 0;) {
				s.setAsDouble(svs[i], i, i);
			}
			return new Matrix[] { u, s, v };
		} catch (Exception e) {
			throw new MatrixException(e);
		}

	}
}

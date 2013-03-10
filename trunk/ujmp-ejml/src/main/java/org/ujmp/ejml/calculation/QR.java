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

import org.ejml.alg.dense.linsol.qr.LinearSolverQrHouseCol;
import org.ejml.data.DenseMatrix64F;
import org.ejml.factory.DecompositionFactory;
import org.ejml.factory.LinearSolver;
import org.ejml.factory.QRDecomposition;
import org.ujmp.core.Matrix;
import org.ujmp.ejml.EJMLDenseDoubleMatrix2D;

public class QR implements
		org.ujmp.core.doublematrix.calculation.general.decomposition.QR<Matrix> {

	public static QR INSTANCE = new QR();

	public Matrix[] calc(Matrix source) {
		QRDecomposition<DenseMatrix64F> qr = DecompositionFactory.qr(
				(int) source.getRowCount(), (int) source.getColumnCount());
		DenseMatrix64F matrix = null;
		if (source instanceof EJMLDenseDoubleMatrix2D) {
			matrix = ((EJMLDenseDoubleMatrix2D) source).getWrappedObject();
		} else {
			matrix = new EJMLDenseDoubleMatrix2D(source).getWrappedObject();
		}
		qr.decompose(matrix);
		DenseMatrix64F qm = qr.getQ(null, true);
		DenseMatrix64F rm = qr.getR(null, true);
		Matrix q = new EJMLDenseDoubleMatrix2D(qm);
		Matrix r = new EJMLDenseDoubleMatrix2D(rm);
		return new Matrix[] { q, r };
	}

	public Matrix solve(Matrix a, Matrix b) {
		LinearSolver<DenseMatrix64F> solver = new LinearSolverQrHouseCol();
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
		solver.setA(a2);
		DenseMatrix64F x = new DenseMatrix64F(a2.numCols, b2.numCols);
		solver.solve(b2, x);
		return new EJMLDenseDoubleMatrix2D(x);
	}

}

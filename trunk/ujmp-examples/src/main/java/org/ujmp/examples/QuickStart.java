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

package org.ujmp.examples;

import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;

public class QuickStart {

	public static void main(String[] args) throws Exception {

		// create a dense empty matrix with 4 rows and 4 columns
		Matrix dense = DenseMatrix.Factory.zeros(4, 4);

		// set entry at row 2 and column 3 to the value 5.0
		dense.setAsDouble(5.0, 2, 3);

		// set some other values
		dense.setAsDouble(1.0, 0, 0);
		dense.setAsDouble(3.0, 1, 1);
		dense.setAsDouble(4.0, 2, 2);
		dense.setAsDouble(-2.0, 3, 3);
		dense.setAsDouble(-2.0, 1, 3);

		// print the final matrix on the console
		System.out.println(dense);

		// create a sparse empty matrix with 4 rows and 4 columns
		Matrix sparse = SparseMatrix.Factory.zeros(4, 4);
		sparse.setAsDouble(2.0, 0, 0);

		// basic calculations
		Matrix transpose = dense.transpose();
		Matrix sum = dense.plus(sparse);
		Matrix difference = dense.minus(sparse);
		Matrix matrixProduct = dense.mtimes(sparse);
		Matrix scaled = dense.times(2.0);

		Matrix inverse = dense.inv();
		Matrix pseudoInverse = dense.pinv();
		double determinant = dense.det();

		Matrix[] singularValueDecomposition = dense.svd();
		Matrix[] eigenValueDecomposition = dense.eig();
		Matrix[] luDecomposition = dense.lu();
		Matrix[] qrDecomposition = dense.qr();
		Matrix choleskyDecomposition = dense.chol();

	}

}

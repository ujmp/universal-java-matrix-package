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

import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;
import org.ujmp.core.util.MathUtil;

public class BigSparseMatrixExample {

	public static void main(String[] args) throws Exception {

		// create a very large sparse matrix
		SparseMatrix m1 = SparseMatrix.Factory.zeros(1000000, 500000);

		// set some values
		m1.setAsDouble(MathUtil.nextGaussian(), 0, 0);
		m1.setAsDouble(MathUtil.nextGaussian(), 1, 1);
		for (int i = 0; i < 10000; i++) {
			m1.setAsDouble(MathUtil.nextGaussian(), MathUtil.nextInteger(0, 1000), MathUtil.nextInteger(0, 1000));
		}

		// show on screen
		m1.showGUI();

		// create another matrix
		SparseMatrix m2 = SparseMatrix.Factory.zeros(3000000, 500000);

		// set some values
		m2.setAsDouble(MathUtil.nextGaussian(), 0, 0);
		m2.setAsDouble(MathUtil.nextGaussian(), 1, 1);
		for (int i = 0; i < 10000; i++) {
			m2.setAsDouble(MathUtil.nextGaussian(), MathUtil.nextInteger(0, 1000), MathUtil.nextInteger(0, 1000));
		}

		// show on screen
		m2.showGUI();

		// do some operations
		Matrix m3 = m1.mtimes(m2.transpose());

		// show on screen
		m3.showGUI();

	}

}

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

package org.ujmp.core.doublematrix.calculation.general.decomposition;

import org.ujmp.core.Matrix;
import org.ujmp.core.util.DecompositionOps;
import org.ujmp.core.util.UJMPSettings;

public interface SolveSymm<T> extends Solve<T> {

	public static int SQUARETHRESHOLD = 100;

	public T calc(T a, T b);

	public static final SolveSymm<Matrix> MATRIX = new SolveSymm<Matrix>() {

		public final Matrix calc(Matrix a, Matrix b) {
			if (UJMPSettings.getNumberOfThreads() == 1) {
				if (a.getRowCount() >= SQUARETHRESHOLD && a.getColumnCount() >= SQUARETHRESHOLD) {
					return MATRIXSQUARELARGESINGLETHREADED.calc(a, b);
				} else {
					return MATRIXSQUARESMALLSINGLETHREADED.calc(a, b);
				}
			} else {
				if (a.getRowCount() >= SQUARETHRESHOLD && a.getColumnCount() >= SQUARETHRESHOLD) {
					return MATRIXSQUARELARGEMULTITHREADED.calc(a, b);
				} else {
					return MATRIXSQUARESMALLMULTITHREADED.calc(a, b);
				}
			}
		}
	};

	public static final SolveSymm<Matrix> INSTANCE = MATRIX;

	public static final SolveSymm<Matrix> UJMPSQUARE = new SolveSymm<Matrix>() {

		public final Matrix calc(Matrix a, Matrix b) {
			return LU.INSTANCE.solve(a, b);
		}
	};

	public static final SolveSymm<Matrix> MATRIXSQUARESMALLSINGLETHREADED = UJMPSQUARE;

	public static final SolveSymm<Matrix> MATRIXSQUARELARGESINGLETHREADED = new SolveSymm<Matrix>() {
		public final Matrix calc(Matrix a, Matrix b) {
			// no special implementation for symmetric matrices
			Solve<Matrix> solve = null;
			if (UJMPSettings.isUseJBlas()) {
				solve = DecompositionOps.SOLVE_JBLAS;
			}
			if (solve == null && UJMPSettings.isUseOjalgo()) {
				solve = DecompositionOps.SOLVE_OJALGO;
			}
			if (solve == null && UJMPSettings.isUseEJML()) {
				solve = DecompositionOps.SOLVE_EJML;
			}
			if (solve == null && UJMPSettings.isUseMTJ()) {
				solve = DecompositionOps.SOLVE_MTJ;
			}
			if (solve == null) {
				solve = UJMPSQUARE;
			}
			return solve.calc(a, b);
		}
	};

	public static final SolveSymm<Matrix> MATRIXSQUARELARGEMULTITHREADED = new SolveSymm<Matrix>() {
		public Matrix calc(Matrix a, Matrix b) {
			// no special implementation for symmetric matrices
			Solve<Matrix> solve = null;
			if (UJMPSettings.isUseJBlas()) {
				solve = DecompositionOps.SOLVE_JBLAS;
			}
			if (solve == null && UJMPSettings.isUseOjalgo()) {
				solve = DecompositionOps.SOLVE_OJALGO;
			}
			if (solve == null && UJMPSettings.isUseEJML()) {
				solve = DecompositionOps.SOLVE_EJML;
			}
			if (solve == null) {
				solve = UJMPSQUARE;
			}
			return solve.calc(a, b);
		}
	};

	public static final SolveSymm<Matrix> MATRIXSQUARESMALLMULTITHREADED = UJMPSQUARE;

}

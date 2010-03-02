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

package org.ujmp.core.doublematrix.calculation.general.decomposition;

import org.ujmp.core.Matrix;
import org.ujmp.core.util.DecompositionOps;
import org.ujmp.core.util.UJMPSettings;

public interface Solve<T> {

	public static int SQUARETHRESHOLD = 100;

	public static int TALLTHRESHOLD = 100;

	public T calc(T a, T b);

	public static final Solve<Matrix> MATRIX = new Solve<Matrix>() {

		public final Matrix calc(Matrix a, Matrix b) {
			if (a.isSquare()) {
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
			} else {
				if (UJMPSettings.getNumberOfThreads() == 1) {
					if (a.getRowCount() >= TALLTHRESHOLD && a.getColumnCount() >= TALLTHRESHOLD) {
						return MATRIXTALLLARGESINGLETHREADED.calc(a, b);
					} else {
						return MATRIXTALLSMALLSINGLETHREADED.calc(a, b);
					}
				} else {
					if (a.getRowCount() >= TALLTHRESHOLD && a.getColumnCount() >= TALLTHRESHOLD) {
						return MATRIXTALLLARGEMULTITHREADED.calc(a, b);
					} else {
						return MATRIXTALLSMALLMULTITHREADED.calc(a, b);
					}
				}
			}
		}
	};

	public static final Solve<Matrix> INSTANCE = MATRIX;

	public static final Solve<Matrix> UJMPSQUARE = new Solve<Matrix>() {

		public final Matrix calc(Matrix a, Matrix b) {
			return LU.INSTANCE.solve(a, b);
		}
	};

	public static final Solve<Matrix> UJMPTALL = new Solve<Matrix>() {

		public final Matrix calc(Matrix a, Matrix b) {
			return QR.INSTANCE.solve(a, b);
		}
	};

	public static final Solve<Matrix> MATRIXSQUARESMALLSINGLETHREADED = UJMPSQUARE;

	public static final Solve<Matrix> MATRIXTALLSMALLSINGLETHREADED = UJMPTALL;

	public static final Solve<Matrix> MATRIXSQUARELARGESINGLETHREADED = new Solve<Matrix>() {
		public final Matrix calc(Matrix a, Matrix b) {
			Solve<Matrix> solve = DecompositionOps.SOLVE_JBLAS;
			if (solve == null) {
				solve = DecompositionOps.SOLVE_OJALGO;
			}
			if (solve == null) {
				solve = DecompositionOps.SOLVE_EJML;
			}
			if (solve == null) {
				solve = DecompositionOps.SOLVE_MTJ;
			}
			if (solve == null) {
				solve = UJMPSQUARE;
			}
			return solve.calc(a, b);
		}
	};

	public static final Solve<Matrix> MATRIXTALLLARGESINGLETHREADED = new Solve<Matrix>() {
		public final Matrix calc(Matrix a, Matrix b) {
			Solve<Matrix> solve = DecompositionOps.SOLVE_OJALGO;
			if (solve == null) {
				solve = DecompositionOps.SOLVE_EJML;
			}
			if (solve == null) {
				solve = DecompositionOps.SOLVE_MTJ;
			}
			if (solve == null) {
				solve = UJMPTALL;
			}
			return solve.calc(a, b);
		}
	};

	public static final Solve<Matrix> MATRIXSQUARELARGEMULTITHREADED = new Solve<Matrix>() {
		public Matrix calc(Matrix a, Matrix b) {
			Solve<Matrix> solve = DecompositionOps.SOLVE_JBLAS;
			if (solve == null) {
				solve = DecompositionOps.SOLVE_OJALGO;
			}
			if (solve == null) {
				solve = DecompositionOps.SOLVE_EJML;
			}
			if (solve == null) {
				solve = UJMPSQUARE;
			}
			return solve.calc(a, b);
		}
	};

	public static final Solve<Matrix> MATRIXTALLLARGEMULTITHREADED = new Solve<Matrix>() {
		public Matrix calc(Matrix a, Matrix b) {
			Solve<Matrix> solve = DecompositionOps.SOLVE_OJALGO;
			if (solve == null) {
				solve = DecompositionOps.SOLVE_EJML;
			}
			if (solve == null) {
				solve = UJMPTALL;
			}
			return solve.calc(a, b);
		}
	};

	public static final Solve<Matrix> MATRIXSQUARESMALLMULTITHREADED = UJMPSQUARE;

	public static final Solve<Matrix> MATRIXTALLSMALLMULTITHREADED = UJMPTALL;
}

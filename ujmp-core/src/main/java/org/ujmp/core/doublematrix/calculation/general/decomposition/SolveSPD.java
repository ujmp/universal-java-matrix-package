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

package org.ujmp.core.doublematrix.calculation.general.decomposition;

import org.ujmp.core.Matrix;
import org.ujmp.core.util.DecompositionOps;
import org.ujmp.core.util.UJMPSettings;

public interface SolveSPD<T> extends Solve<T> {

	public static int SQUARETHRESHOLD = 100;

	public T calc(T a, T b);

	public static final SolveSPD<Matrix> MATRIX = new SolveSPD<Matrix>() {

		public final Matrix calc(Matrix a, Matrix b) {
			if (UJMPSettings.getInstance().getNumberOfThreads() == 1) {
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

	public static final SolveSPD<Matrix> INSTANCE = MATRIX;

	public static final SolveSPD<Matrix> UJMPSQUARE = new SolveSPD<Matrix>() {

		public final Matrix calc(Matrix a, Matrix b) {
			return Chol.INSTANCE.solve(a, b);
		}
	};

	public static final SolveSPD<Matrix> MATRIXSQUARESMALLSINGLETHREADED = UJMPSQUARE;

	public static final SolveSPD<Matrix> MATRIXSQUARELARGESINGLETHREADED = new SolveSPD<Matrix>() {
		public final Matrix calc(Matrix a, Matrix b) {
			SolveSPD<Matrix> solve = null;
			if (UJMPSettings.getInstance().isUseJBlas()) {
				solve = DecompositionOps.SOLVESPD_JBLAS;
			}
			if (solve == null && UJMPSettings.getInstance().isUseOjalgo()) {
				solve = DecompositionOps.SOLVESPD_OJALGO;
			}
			if (solve == null && UJMPSettings.getInstance().isUseEJML()) {
				solve = DecompositionOps.SOLVESPD_EJML;
			}
			if (solve == null && UJMPSettings.getInstance().isUseMTJ()) {
				solve = DecompositionOps.SOLVESPD_MTJ;
			}
			if (solve == null) {
				solve = UJMPSQUARE;
			}
			return solve.calc(a, b);
		}
	};

	public static final SolveSPD<Matrix> MATRIXSQUARELARGEMULTITHREADED = new SolveSPD<Matrix>() {
		public Matrix calc(Matrix a, Matrix b) {
			SolveSPD<Matrix> solve = null;
			if (UJMPSettings.getInstance().isUseJBlas()) {
				solve = DecompositionOps.SOLVESPD_JBLAS;
			}
			if (solve == null && UJMPSettings.getInstance().isUseOjalgo()) {
				solve = DecompositionOps.SOLVESPD_OJALGO;
			}
			if (solve == null && UJMPSettings.getInstance().isUseEJML()) {
				solve = DecompositionOps.SOLVESPD_EJML;
			}
			if (solve == null) {
				solve = UJMPSQUARE;
			}
			return solve.calc(a, b);
		}
	};

	public static final SolveSPD<Matrix> MATRIXSQUARESMALLMULTITHREADED = UJMPSQUARE;

}

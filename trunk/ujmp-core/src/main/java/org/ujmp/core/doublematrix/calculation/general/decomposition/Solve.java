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

	public static int THRESHOLD = 100;

	public T calc(T a, T b);

	public static final Solve<Matrix> MATRIX = new Solve<Matrix>() {

		public final Matrix calc(Matrix a, Matrix b) {
			if (UJMPSettings.getNumberOfThreads() == 1) {
				if (a.getRowCount() >= THRESHOLD && a.getColumnCount() >= THRESHOLD) {
					return MATRIXLARGESINGLETHREADED.calc(a, b);
				} else {
					return MATRIXSMALLSINGLETHREADED.calc(a, b);
				}
			} else {
				if (a.getRowCount() >= THRESHOLD && a.getColumnCount() >= THRESHOLD) {
					return MATRIXLARGEMULTITHREADED.calc(a, b);
				} else {
					return MATRIXSMALLMULTITHREADED.calc(a, b);
				}
			}
		}
	};

	public static final Solve<Matrix> INSTANCE = MATRIX;

	public static final Solve<Matrix> UJMP = new Solve<Matrix>() {

		public final Matrix calc(Matrix a, Matrix b) {
			return a.isSquare() ? LU.INSTANCE.solve(a, b) : QR.INSTANCE.solve(a, b);
		}
	};

	public static final Solve<Matrix> MATRIXSMALLSINGLETHREADED = UJMP;

	public static final Solve<Matrix> MATRIXLARGESINGLETHREADED = new Solve<Matrix>() {
		public final Matrix calc(Matrix a, Matrix b) {
			Solve<Matrix> solve = DecompositionOps.SOLVE_OJALGO;
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
				solve = UJMP;
			}
			return solve.calc(a, b);
		}
	};

	public static final Solve<Matrix> MATRIXLARGEMULTITHREADED = new Solve<Matrix>() {
		public Matrix calc(Matrix a, Matrix b) {
			Solve<Matrix> solve = DecompositionOps.SOLVE_OJALGO;
			if (solve == null) {
				solve = DecompositionOps.SOLVE_OJALGO;
			}
			if (solve == null) {
				solve = DecompositionOps.SOLVE_EJML;
			}
			if (solve == null) {
				solve = UJMP;
			}
			return solve.calc(a, b);
		}
	};

	public static final Solve<Matrix> MATRIXSMALLMULTITHREADED = UJMP;
}

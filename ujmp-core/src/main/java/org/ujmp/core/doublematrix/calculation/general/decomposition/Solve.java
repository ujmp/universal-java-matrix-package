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
import org.ujmp.core.util.UJMPSettings;

public interface Solve<T> {

	public T calc(T a, T b);

	public static Solve<Matrix> MATRIX = new Solve<Matrix>() {

		public Matrix calc(Matrix a, Matrix b) {
			if (UJMPSettings.getNumberOfThreads() == 1) {
				if (a.getRowCount() >= 100 && a.getColumnCount() >= 100) {
					return MATRIXLARGESINGLETHREADED.calc(a, b);
				} else {
					return MATRIXSMALLSINGLETHREADED.calc(a, b);
				}
			} else {
				if (a.getRowCount() >= 100 && a.getColumnCount() >= 100) {
					return MATRIXLARGEMULTITHREADED.calc(a, b);
				} else {
					return MATRIXSMALLMULTITHREADED.calc(a, b);
				}
			}
		}
	};

	public static Solve<Matrix> INSTANCE = MATRIX;

	public static Solve<Matrix> UJMP = new Solve<Matrix>() {

		public Matrix calc(Matrix a, Matrix b) {
			return a.isSquare() ? (new LU(a)).solve(b) : (new QR(a)).solve(b);
		}
	};

	public static Solve<Matrix> MATRIXSMALLSINGLETHREADED = new Solve<Matrix>() {

		@SuppressWarnings("unchecked")
		public Matrix calc(Matrix a, Matrix b) {
			Solve<Matrix> solve = null;

			try {
				solve = (Solve<Matrix>) Class.forName("org.ujmp.ejml.calculation.Solve")
						.newInstance();
			} catch (Throwable e) {
			}

			if (solve == null) {
				try {
					solve = (Solve<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.Solve")
							.newInstance();
				} catch (Throwable e) {
				}
			}

			if (solve == null) {
				solve = UJMP;
			}
			return solve.calc(a, b);
		}
	};

	public static Solve<Matrix> MATRIXLARGESINGLETHREADED = new Solve<Matrix>() {

		@SuppressWarnings("unchecked")
		public Matrix calc(Matrix a, Matrix b) {
			Solve<Matrix> solve = null;

			try {
				solve = (Solve<Matrix>) Class.forName("org.ujmp.ejml.calculation.Solve")
						.newInstance();
			} catch (Throwable e) {
			}

			if (solve == null) {
				try {
					solve = (Solve<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.Solve")
							.newInstance();
				} catch (Throwable e) {
				}
			}

			if (solve == null) {
				solve = UJMP;
			}
			return solve.calc(a, b);
		}
	};

	public static Solve<Matrix> MATRIXLARGEMULTITHREADED = new Solve<Matrix>() {

		@SuppressWarnings("unchecked")
		public Matrix calc(Matrix a, Matrix b) {
			Solve<Matrix> solve = null;

			try {
				solve = (Solve<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.Solve")
						.newInstance();
			} catch (Throwable e) {
			}

			if (solve == null) {
				try {
					solve = (Solve<Matrix>) Class.forName("org.ujmp.ejml.calculation.Solve")
							.newInstance();
				} catch (Throwable e) {
				}
			}

			if (solve == null) {
				solve = UJMP;
			}
			return solve.calc(a, b);
		}
	};

	public static Solve<Matrix> MATRIXSMALLMULTITHREADED = new Solve<Matrix>() {

		@SuppressWarnings("unchecked")
		public Matrix calc(Matrix a, Matrix b) {
			Solve<Matrix> solve = null;

			try {
				solve = (Solve<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.Solve")
						.newInstance();
			} catch (Throwable e) {
			}

			if (solve == null) {
				try {
					solve = (Solve<Matrix>) Class.forName("org.ujmp.ejml.calculation.Solve")
							.newInstance();
				} catch (Throwable e) {
				}
			}

			if (solve == null) {
				solve = UJMP;
			}
			return solve.calc(a, b);
		}
	};

}

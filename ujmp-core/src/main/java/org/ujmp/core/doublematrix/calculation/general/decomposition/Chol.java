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
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.util.DecompositionOps;
import org.ujmp.core.util.UJMPSettings;

/**
 * Cholesky Decomposition.
 * <P>
 * For a symmetric, positive definite matrix A, the Cholesky decomposition is an
 * lower triangular matrix L so that A = L*L'.
 * <P>
 * If the matrix is not symmetric or positive definite, the constructor returns
 * a partial decomposition and sets an internal flag that may be queried by the
 * isSPD() method.
 */

public interface Chol<T> {

	public T calc(T source);

	public T solve(T source, T b);

	public static int THRESHOLD = 100;

	public static final Chol<Matrix> MATRIX = new Chol<Matrix>() {

		public final Matrix calc(Matrix source) {
			if (UJMPSettings.getNumberOfThreads() == 1) {
				if (source.getRowCount() >= THRESHOLD && source.getColumnCount() >= THRESHOLD) {
					return MATRIXLARGESINGLETHREADED.calc(source);
				} else {
					return MATRIXSMALLSINGLETHREADED.calc(source);
				}
			} else {
				if (source.getRowCount() >= THRESHOLD && source.getColumnCount() >= THRESHOLD) {
					return MATRIXLARGEMULTITHREADED.calc(source);
				} else {
					return MATRIXSMALLMULTITHREADED.calc(source);
				}
			}
		}

		public final Matrix solve(Matrix source, Matrix b) {
			if (UJMPSettings.getNumberOfThreads() == 1) {
				if (source.getRowCount() >= THRESHOLD && source.getColumnCount() >= THRESHOLD) {
					return MATRIXLARGESINGLETHREADED.solve(source, b);
				} else {
					return MATRIXSMALLSINGLETHREADED.solve(source, b);
				}
			} else {
				if (source.getRowCount() >= THRESHOLD && source.getColumnCount() >= THRESHOLD) {
					return MATRIXLARGEMULTITHREADED.solve(source, b);
				} else {
					return MATRIXSMALLMULTITHREADED.solve(source, b);
				}
			}
		}
	};

	public static final Chol<Matrix> INSTANCE = MATRIX;

	public static final Chol<Matrix> UJMP = new Chol<Matrix>() {

		public final Matrix calc(Matrix source) {
			CholMatrix chol = new CholMatrix(source);
			return chol.getL();
		}

		public final Matrix solve(Matrix source, Matrix b) {
			CholMatrix chol = new CholMatrix(source);
			return chol.solve(b);
		}
	};

	public static final Chol<Matrix> MATRIXSMALLMULTITHREADED = UJMP;

	public static final Chol<Matrix> MATRIXSMALLSINGLETHREADED = UJMP;

	public static final Chol<Matrix> MATRIXLARGESINGLETHREADED = new Chol<Matrix>() {
		public Matrix calc(Matrix source) {
			Chol<Matrix> chol = DecompositionOps.CHOL_JBLAS;
			if (chol == null) {
				chol = UJMP;
			}
			return chol.calc(source);
		}

		public final Matrix solve(Matrix source, Matrix b) {
			Chol<Matrix> chol = DecompositionOps.CHOL_JBLAS;
			if (chol == null) {
				chol = UJMP;
			}
			return chol.solve(source, b);
		}
	};

	public static final Chol<Matrix> MATRIXLARGEMULTITHREADED = new Chol<Matrix>() {
		public Matrix calc(Matrix source) {
			Chol<Matrix> chol = DecompositionOps.CHOL_JBLAS;
			if (chol == null) {
				chol = DecompositionOps.CHOL_OJALGO;
			}
			if (chol == null) {
				chol = UJMP;
			}
			return chol.calc(source);
		}

		public final Matrix solve(Matrix source, Matrix b) {
			Chol<Matrix> chol = DecompositionOps.CHOL_JBLAS;
			if (chol == null) {
				chol = DecompositionOps.CHOL_OJALGO;
			}
			if (chol == null) {
				chol = UJMP;
			}
			return chol.solve(source, b);
		}
	};

	final class CholMatrix {
		private static final long serialVersionUID = 400514872358216115L;

		/**
		 * Array for internal storage of decomposition.
		 * 
		 * @serial internal array storage.
		 */
		private final double[][] L;

		/**
		 * Row and column dimension (square matrix).
		 * 
		 * @serial matrix dimension.
		 */
		private final int n;

		/**
		 * Symmetric and positive definite flag.
		 * 
		 * @serial is symmetric and positive definite flag.
		 */
		private boolean isspd;

		/*
		 * ------------------------ Constructor ------------------------
		 */

		/**
		 * Cholesky algorithm for symmetric and positive definite matrix.
		 * 
		 * @param A
		 *            Square, symmetric matrix.
		 * @return Structure to access L and isspd flag.
		 */

		public CholMatrix(Matrix Arg) {
			final double[][] A = Arg.toDoubleArray();
			n = (int) Arg.getRowCount();
			L = new double[n][n];
			isspd = (Arg.getColumnCount() == n);
			// Main loop.
			double[] Lrowj = null;
			double[] Lrowk = null;
			double[] Aj = null;
			for (int j = 0; j < n; j++) {
				Lrowj = L[j];
				Aj = A[j];
				double d = 0.0;
				for (int k = 0; k < j; k++) {
					Lrowk = L[k];
					double s = 0.0;
					for (int i = 0; i < k; i++) {
						s += Lrowk[i] * Lrowj[i];
					}
					Lrowj[k] = s = (Aj[k] - s) / Lrowk[k];
					d = d + s * s;
					isspd = isspd & (A[k][j] == Aj[k]);
				}
				d = Aj[j] - d;
				isspd = isspd & (d > 0.0);
				Lrowj[j] = StrictMath.sqrt(StrictMath.max(d, 0.0));
				for (int k = j + 1; k < n; k++) {
					Lrowj[k] = 0.0;
				}
			}
		}

		/*
		 * ------------------------ Temporary, experimental code.
		 * ------------------------ *\
		 * 
		 * \** Right Triangular Cholesky Decomposition. <P> For a symmetric,
		 * positive definite matrix A, the Right Cholesky decomposition is an
		 * upper triangular matrix R so that A = R'*R. This constructor computes
		 * R with the Fortran inspired column oriented algorithm used in LINPACK
		 * and MATLAB. In Java, we suspect a row oriented, lower triangular
		 * decomposition is faster. We have temporarily included this
		 * constructor here until timing experiments confirm this suspicion.\
		 * 
		 * \** Array for internal storage of right triangular decomposition. **\
		 * private transient double[][] R;
		 * 
		 * \** Cholesky algorithm for symmetric and positive definite matrix.
		 * 
		 * @param A Square, symmetric matrix.
		 * 
		 * @param rightflag Actual value ignored.
		 * 
		 * @return Structure to access R and isspd flag.\
		 * 
		 * public CholeskyDecomposition (Matrix Arg, int rightflag) { //
		 * Initialize. double[][] A = Arg.getArray(); n =
		 * Arg.getColumnDimension(); R = new double[n][n]; isspd =
		 * (Arg.getColumnDimension() == n); // Main loop. for (int j = 0; j < n;
		 * j++) { double d = 0.0; for (int k = 0; k < j; k++) { double s =
		 * A[k][j]; for (int i = 0; i < k; i++) { s = s - R[i][k]*R[i][j]; }
		 * R[k][j] = s = s/R[k][k]; d = d + s*s; isspd = isspd & (A[k][j] ==
		 * A[j][k]); } d = A[j][j] - d; isspd = isspd & (d > 0.0); R[j][j] =
		 * Math.sqrt(Math.max(d,0.0)); for (int k = j+1; k < n; k++) { R[k][j] =
		 * 0.0; } } }
		 * 
		 * \** Return upper triangular factor.
		 * 
		 * @return R\
		 * 
		 * public Matrix getR () { return new Matrix(R,n,n); }
		 * 
		 * \* ------------------------ End of temporary code.
		 * ------------------------
		 */

		/*
		 * ------------------------ Public Methods ------------------------
		 */

		/**
		 * Is the matrix symmetric and positive definite?
		 * 
		 * @return true if A is symmetric and positive definite.
		 */

		public final boolean isSPD() {
			return isspd;
		}

		/**
		 * Return triangular factor.
		 * 
		 * @return L
		 */

		public final Matrix getL() {
			return MatrixFactory.linkToArray(L);
		}

		/**
		 * Solve A*X = B
		 * 
		 * @param B
		 *            A Matrix with as many rows as A and any number of columns.
		 * @return X so that L*L'*X = B
		 * @exception IllegalArgumentException
		 *                Matrix row dimensions must agree.
		 * @exception RuntimeException
		 *                Matrix is not symmetric positive definite.
		 */

		public final Matrix solve(Matrix B) {
			if (B.getRowCount() != n) {
				throw new IllegalArgumentException("Matrix row dimensions must agree.");
			}
			if (!isspd) {
				throw new RuntimeException("Matrix is not symmetric positive definite.");
			}

			// Copy right hand side.
			final double[][] X = B.toDoubleArray();
			final int nx = (int) B.getColumnCount();

			// Solve L*Y = B;
			for (int k = 0; k < n; k++) {
				for (int j = 0; j < nx; j++) {
					for (int i = 0; i < k; i++) {
						X[k][j] -= X[i][j] * L[k][i];
					}
					X[k][j] /= L[k][k];
				}
			}

			// Solve L'*X = Y;
			for (int k = n - 1; k >= 0; k--) {
				for (int j = 0; j < nx; j++) {
					for (int i = k + 1; i < n; i++) {
						X[k][j] -= X[i][j] * L[i][k];
					}
					X[k][j] /= L[k][k];
				}
			}

			return MatrixFactory.linkToArray(X);
		}

	}
}

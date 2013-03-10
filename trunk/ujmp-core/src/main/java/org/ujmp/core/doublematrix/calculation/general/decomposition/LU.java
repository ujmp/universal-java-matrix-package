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
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.util.DecompositionOps;
import org.ujmp.core.util.UJMPSettings;

/**
 * LU Decomposition.
 * <P>
 * For an m-by-n matrix A with m >= n, the LU decomposition is an m-by-n unit
 * lower triangular matrix L, an n-by-n upper triangular matrix U, and a
 * permutation vector piv of length m so that A(piv,:) = L*U. If m < n, then L
 * is m-by-m and U is m-by-n.
 * <P>
 * The LU decompostion with pivoting always exists, even if the matrix is
 * singular, so the constructor will never fail. The primary use of the LU
 * decomposition is in the solution of square systems of simultaneous linear
 * equations. This will fail if isNonsingular() returns false.
 */

public interface LU<T> {

	public T[] calc(T source);

	public T solve(T source, T b);

	public static int THRESHOLD = 100;

	public static final LU<Matrix> MATRIX = new LU<Matrix>() {

		public final Matrix[] calc(Matrix source) {
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

	public static final LU<Matrix> INSTANCE = MATRIX;

	public static final LU<Matrix> UJMP = new LU<Matrix>() {

		public final Matrix[] calc(Matrix source) {
			LUMatrix lu = new LUMatrix(source);
			return new Matrix[] { lu.getL(), lu.getU(), lu.getP() };
		}

		public final Matrix solve(Matrix source, Matrix b) {
			LUMatrix lu = new LUMatrix(source);
			return lu.solve(b);
		}
	};

	public static final LU<Matrix> MATRIXSMALLMULTITHREADED = UJMP;

	public static final LU<Matrix> MATRIXSMALLSINGLETHREADED = UJMP;

	public static final LU<Matrix> MATRIXLARGESINGLETHREADED = new LU<Matrix>() {
		public final Matrix[] calc(Matrix source) {
			LU<Matrix> lu = null;
			if (UJMPSettings.isUseJBlas()) {
				lu = DecompositionOps.LU_JBLAS;
			}
			if (lu == null) {
				lu = UJMP;
			}
			return lu.calc(source);
		}

		public final Matrix solve(Matrix source, Matrix b) {
			LU<Matrix> lu = null;
			if (UJMPSettings.isUseJBlas()) {
				lu = DecompositionOps.LU_JBLAS;
			}
			if (lu == null && UJMPSettings.isUseOjalgo()) {
				lu = DecompositionOps.LU_OJALGO;
			}
			if (lu == null) {
				lu = UJMP;
			}
			return lu.solve(source, b);
		}
	};

	public static final LU<Matrix> MATRIXLARGEMULTITHREADED = new LU<Matrix>() {
		public final Matrix[] calc(Matrix source) {
			LU<Matrix> lu = null;
			if (UJMPSettings.isUseJBlas()) {
				lu = DecompositionOps.LU_JBLAS;
			}
			if (lu == null && UJMPSettings.isUseOjalgo()) {
				lu = DecompositionOps.LU_OJALGO;
			}
			if (lu == null) {
				lu = UJMP;
			}
			return lu.calc(source);
		}

		public final Matrix solve(Matrix source, Matrix b) {
			LU<Matrix> lu = null;
			if (UJMPSettings.isUseJBlas()) {
				lu = DecompositionOps.LU_JBLAS;
			}
			if (lu == null && UJMPSettings.isUseOjalgo()) {
				lu = DecompositionOps.LU_OJALGO;
			}
			if (lu == null) {
				lu = UJMP;
			}
			return lu.solve(source, b);
		}
	};

	final class LUMatrix {
		/**
		 * Array for internal storage of decomposition.
		 * 
		 * @serial internal array storage.
		 */
		private final double[][] LU;

		/**
		 * Row and column dimensions, and pivot sign.
		 * 
		 * @serial column dimension.
		 * @serial row dimension.
		 * @serial pivot sign.
		 */
		private final int m, n;
		private int pivsign;

		/**
		 * Internal storage of pivot vector.
		 * 
		 * @serial pivot vector.
		 */
		private final int[] piv;

		/*
		 * ------------------------ Constructor ------------------------
		 */

		/**
		 * LU Decomposition
		 * 
		 * @param A
		 *            Rectangular matrix
		 * @return Structure to access L, U and piv.
		 */
		public LUMatrix(Matrix A) {

			// Use a "left-looking", dot-product, Crout/Doolittle algorithm.

			LU = A.toDoubleArray();
			m = (int) A.getRowCount();
			n = (int) A.getColumnCount();
			piv = new int[m];
			for (int i = 0; i < m; i++) {
				piv[i] = i;
			}
			pivsign = 1;

			final double[] LUcolj = new double[m];
			double[] LUrowi = null;

			// Outer loop.

			for (int j = 0; j < n; j++) {

				// Make a copy of the j-th column to localize references.
				for (int i = 0; i < m; i++) {
					LUcolj[i] = LU[i][j];
				}

				// Apply previous transformations.

				for (int i = 0; i < m; i++) {
					LUrowi = LU[i];

					// Most of the time is spent in the following dot product.

					final int kmax = Math.min(i, j);
					double s = 0.0;
					for (int k = 0; k < kmax; k++) {
						s += LUrowi[k] * LUcolj[k];
					}

					LUrowi[j] = LUcolj[i] -= s;
				}

				int p = j;
				for (int i = j + 1; i < m; i++) {
					if (Math.abs(LUcolj[i]) > Math.abs(LUcolj[p])) {
						p = i;
					}
				}
				if (p != j) {
					for (int k = 0; k < n; k++) {
						double t = LU[p][k];
						LU[p][k] = LU[j][k];
						LU[j][k] = t;
					}
					int k = piv[p];
					piv[p] = piv[j];
					piv[j] = k;
					pivsign = -pivsign;
				}

				// Compute multipliers.

				// http://cio.nist.gov/esd/emaildir/lists/jama/msg01498.html
				if (j < m && LU[j][j] != 0.0) {
					for (int i = j + 1; i < m; i++) {
						LU[i][j] /= LU[j][j];
					}
				}
			}
		}

		/*
		 * ------------------------ Temporary, experimental code.
		 * ------------------------ *\
		 * 
		 * \** LU Decomposition, computed by Gaussian elimination. <P> This
		 * constructor computes L and U with the "daxpy"-based elimination
		 * algorithm used in LINPACK and MATLAB. In Java, we suspect the
		 * dot-product, Crout algorithm will be faster. We have temporarily
		 * included this constructor until timing experiments confirm this
		 * suspicion. <P>
		 * 
		 * @param A Rectangular matrix
		 * 
		 * @param linpackflag Use Gaussian elimination. Actual value ignored.
		 * 
		 * @return Structure to access L, U and piv.\
		 * 
		 * public LUDecomposition (Matrix A, int linpackflag) { // Initialize.
		 * LU = A.getArrayCopy(); m = A.getRowDimension(); n =
		 * A.getColumnDimension(); piv = new int[m]; for (int i = 0; i < m; i++)
		 * { piv[i] = i; } pivsign = 1; // Main loop. for (int k = 0; k < n;
		 * k++) { // Find pivot. int p = k; for (int i = k+1; i < m; i++) { if
		 * (Math.abs(LU[i][k]) > Math.abs(LU[p][k])) { p = i; } } // Exchange if
		 * necessary. if (p != k) { for (int j = 0; j < n; j++) { double t =
		 * LU[p][j]; LU[p][j] = LU[k][j]; LU[k][j] = t; } int t = piv[p]; piv[p]
		 * = piv[k]; piv[k] = t; pivsign = -pivsign; } // Compute multipliers
		 * and eliminate k-th column. if (LU[k][k] != 0.0) { for (int i = k+1; i
		 * < m; i++) { LU[i][k] /= LU[k][k]; for (int j = k+1; j < n; j++) {
		 * LU[i][j] -= LU[i][k]*LU[k][j]; } } } } }
		 * 
		 * \* ------------------------ End of temporary code.
		 * ------------------------
		 */

		/*
		 * ------------------------ Public Methods ------------------------
		 */

		/**
		 * Is the matrix nonsingular?
		 * 
		 * @return true if U, and hence A, is nonsingular.
		 */

		public final boolean isNonsingular() {
			for (int j = 0; j < n; j++) {
				if (LU[j][j] == 0)
					return false;
			}
			return true;
		}

		/**
		 * Return lower triangular factor
		 * 
		 * @return L
		 */

		public final DenseDoubleMatrix2D getL() {
			final int min = Math.min(m, n);
			final double[][] L = new double[m][min];
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < min; j++) {
					if (i > j) {
						L[i][j] = LU[i][j];
					} else if (i == j) {
						L[i][j] = 1.0;
					}
				}
			}
			return Matrix.Factory.linkToArray(L);
		}

		/**
		 * Return upper triangular factor
		 * 
		 * @return U
		 */

		public final DenseDoubleMatrix2D getU() {
			final int min = Math.min(m, n);
			final double[][] U = new double[min][n];
			for (int i = 0; i < min; i++) {
				for (int j = 0; j < n; j++) {
					if (i <= j) {
						U[i][j] = LU[i][j];
					}
				}
			}
			return Matrix.Factory.linkToArray(U);
		}

		/**
		 * Return pivot permutation vector
		 * 
		 * @return piv
		 */

		public final int[] getPivot() {
			final int[] p = new int[m];
			for (int i = 0; i < m; i++) {
				p[i] = piv[i];
			}
			return p;
		}

		public final Matrix getP() {
			final DenseDoubleMatrix2D p = DenseDoubleMatrix2D.Factory.zeros(m, m);
			for (int i = 0; i < m; i++) {
				p.setDouble(1, i, piv[i]);
			}
			return p;
		}

		/**
		 * Return pivot permutation vector as a one-dimensional double array
		 * 
		 * @return (double) piv
		 */

		public final double[] getDoublePivot() {
			final double[] vals = new double[m];
			for (int i = 0; i < m; i++) {
				vals[i] = (double) piv[i];
			}
			return vals;
		}

		/**
		 * Determinant
		 * 
		 * @return det(A)
		 * @exception IllegalArgumentException
		 *                Matrix must be square
		 */

		public final double det() {
			if (m != n) {
				throw new IllegalArgumentException("Matrix must be square.");
			}
			double d = (double) pivsign;
			for (int j = 0; j < n; j++) {
				d *= LU[j][j];
			}
			return d;
		}

		/**
		 * Solve A*X = B
		 * 
		 * @param B
		 *            A Matrix with as many rows as A and any number of columns.
		 * @return X so that L*U*X = B(piv,:)
		 * @exception IllegalArgumentException
		 *                Matrix row dimensions must agree.
		 * @exception RuntimeException
		 *                Matrix is singular.
		 */

		public final DenseDoubleMatrix2D solve(Matrix B) {
			if (B.getRowCount() != m) {
				throw new IllegalArgumentException("Matrix row dimensions must agree.");
			}
			if (!this.isNonsingular()) {
				throw new RuntimeException("Matrix is singular.");
			}

			// Copy right hand side with pivoting
			final int nx = (int) B.getColumnCount();
			// final Matrix Xmat = B.selectRows(Ret.NEW,
			// MathUtil.toLongArray(piv));

			final double[][] X = new double[piv.length][(int) B.getColumnCount()];
			if (B instanceof DenseDoubleMatrix2D) {
				DenseDoubleMatrix2D m = (DenseDoubleMatrix2D) B;
				for (int c = (int) B.getColumnCount(); --c >= 0;) {
					for (int r = piv.length; --r >= 0;) {
						X[r][c] = m.getDouble(piv[r], c);
					}
				}
			} else {
				for (int c = (int) B.getColumnCount(); --c >= 0;) {
					for (int r = piv.length; --r >= 0;) {
						X[r][c] = B.getAsDouble(piv[r], c);
					}
				}
			}

			// Solve L*Y = B(piv,:)
			for (int k = 0; k < n; k++) {
				for (int i = k + 1; i < n; i++) {
					for (int j = 0; j < nx; j++) {
						X[i][j] -= X[k][j] * LU[i][k];
					}
				}
			}

			for (int k = n - 1; k >= 0; k--) {
				for (int j = 0; j < nx; j++) {
					X[k][j] /= LU[k][k];
				}
				for (int i = 0; i < k; i++) {
					for (int j = 0; j < nx; j++) {
						X[i][j] -= X[k][j] * LU[i][k];
					}
				}
			}
			return Matrix.Factory.linkToArray(X);
		}

	};
}

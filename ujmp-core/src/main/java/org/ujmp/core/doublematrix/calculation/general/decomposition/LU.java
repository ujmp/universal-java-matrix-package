package org.ujmp.core.doublematrix.calculation.general.decomposition;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.util.MathUtil;
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

	public static LU<Matrix> MATRIX = new LU<Matrix>() {

		public Matrix[] calc(Matrix source) {
			if (UJMPSettings.getNumberOfThreads() == 1) {
				if (source.getRowCount() >= 100 && source.getColumnCount() >= 100) {
					return MATRIXLARGESINGLETHREADED.calc(source);
				} else {
					return MATRIXSMALLSINGLETHREADED.calc(source);
				}
			} else {
				if (source.getRowCount() >= 100 && source.getColumnCount() >= 100) {
					return MATRIXLARGEMULTITHREADED.calc(source);
				} else {
					return MATRIXSMALLMULTITHREADED.calc(source);
				}
			}
		}

		public Matrix solve(Matrix source, Matrix b) {
			if (UJMPSettings.getNumberOfThreads() == 1) {
				if (source.getRowCount() >= 100 && source.getColumnCount() >= 100) {
					return MATRIXLARGESINGLETHREADED.solve(source, b);
				} else {
					return MATRIXSMALLSINGLETHREADED.solve(source, b);
				}
			} else {
				if (source.getRowCount() >= 100 && source.getColumnCount() >= 100) {
					return MATRIXLARGEMULTITHREADED.solve(source, b);
				} else {
					return MATRIXSMALLMULTITHREADED.solve(source, b);
				}
			}
		}
	};

	public static LU<Matrix> MATRIXSMALLSINGLETHREADED = new LU<Matrix>() {

		@SuppressWarnings("unchecked")
		public Matrix[] calc(Matrix source) {
			LU<Matrix> lu = null;

			try {
				lu = (LU<Matrix>) Class.forName("org.ujmp.ejml.calculation.LU").newInstance();
			} catch (Throwable e) {
			}

			if (lu == null) {
				try {
					lu = (LU<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.LU").newInstance();
				} catch (Throwable e) {
				}
			}

			if (lu == null) {
				lu = UJMP;
			}
			return lu.calc(source);
		}

		@SuppressWarnings("unchecked")
		public Matrix solve(Matrix source, Matrix b) {
			LU<Matrix> lu = null;

			try {
				lu = (LU<Matrix>) Class.forName("org.ujmp.ejml.calculation.LU").newInstance();
			} catch (Throwable e) {
			}

			if (lu == null) {
				try {
					lu = (LU<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.LU").newInstance();
				} catch (Throwable e) {
				}
			}

			if (lu == null) {
				lu = UJMP;
			}
			return lu.solve(source, b);
		}
	};

	public static LU<Matrix> MATRIXLARGESINGLETHREADED = new LU<Matrix>() {

		@SuppressWarnings("unchecked")
		public Matrix[] calc(Matrix source) {
			LU<Matrix> lu = null;

			try {
				lu = (LU<Matrix>) Class.forName("org.ujmp.ejml.calculation.LU").newInstance();
			} catch (Throwable e) {
			}

			if (lu == null) {
				try {
					lu = (LU<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.LU").newInstance();
				} catch (Throwable e) {
				}
			}

			if (lu == null) {
				lu = UJMP;
			}
			return lu.calc(source);
		}

		@SuppressWarnings("unchecked")
		public Matrix solve(Matrix source, Matrix b) {
			LU<Matrix> lu = null;

			try {
				lu = (LU<Matrix>) Class.forName("org.ujmp.ejml.calculation.LU").newInstance();
			} catch (Throwable e) {
			}

			if (lu == null) {
				try {
					lu = (LU<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.LU").newInstance();
				} catch (Throwable e) {
				}
			}

			if (lu == null) {
				lu = UJMP;
			}
			return lu.solve(source, b);
		}
	};

	public static LU<Matrix> MATRIXLARGEMULTITHREADED = new LU<Matrix>() {

		@SuppressWarnings("unchecked")
		public Matrix[] calc(Matrix source) {
			LU<Matrix> lu = null;

			try {
				lu = (LU<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.LU").newInstance();
			} catch (Throwable e) {
			}

			if (lu == null) {
				try {
					lu = (LU<Matrix>) Class.forName("org.ujmp.ejml.calculation.LU").newInstance();
				} catch (Throwable e) {
				}
			}

			if (lu == null) {
				lu = UJMP;
			}
			return lu.calc(source);
		}

		@SuppressWarnings("unchecked")
		public Matrix solve(Matrix source, Matrix b) {
			LU<Matrix> lu = null;

			try {
				lu = (LU<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.LU").newInstance();
			} catch (Throwable e) {
			}

			if (lu == null) {
				try {
					lu = (LU<Matrix>) Class.forName("org.ujmp.ejml.calculation.LU").newInstance();
				} catch (Throwable e) {
				}
			}

			if (lu == null) {
				lu = UJMP;
			}
			return lu.solve(source, b);
		}
	};

	public static LU<Matrix> MATRIXSMALLMULTITHREADED = new LU<Matrix>() {

		@SuppressWarnings("unchecked")
		public Matrix[] calc(Matrix source) {
			LU<Matrix> lu = null;

			try {
				lu = (LU<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.LU").newInstance();
			} catch (Throwable e) {
			}

			if (lu == null) {
				try {
					lu = (LU<Matrix>) Class.forName("org.ujmp.ejml.calculation.LU").newInstance();
				} catch (Throwable e) {
				}
			}

			if (lu == null) {
				lu = UJMP;
			}
			return lu.calc(source);
		}

		@SuppressWarnings("unchecked")
		public Matrix solve(Matrix source, Matrix b) {
			LU<Matrix> lu = null;

			try {
				lu = (LU<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.LU").newInstance();
			} catch (Throwable e) {
			}

			if (lu == null) {
				try {
					lu = (LU<Matrix>) Class.forName("org.ujmp.ejml.calculation.LU").newInstance();
				} catch (Throwable e) {
				}
			}

			if (lu == null) {
				lu = UJMP;
			}
			return lu.solve(source, b);
		}
	};

	public static LU<Matrix> INSTANCE = MATRIX;

	public static LU<Matrix> UJMP = new LU<Matrix>() {

		public Matrix[] calc(Matrix source) {
			LUMatrix lu = new LUMatrix(source);
			return new Matrix[] { lu.getL(), lu.getU(), lu.getP() };
		}

		public Matrix solve(Matrix source, Matrix b) {
			LUMatrix lu = new LUMatrix(source);
			return lu.solve(b);
		}
	};

	public class LUMatrix {

		/*
		 * ------------------------ Class variables ------------------------
		 */

		/**
		 * Array for internal storage of decomposition.
		 * 
		 * @serial internal array storage.
		 */
		private double[][] LU;

		/**
		 * Row and column dimensions, and pivot sign.
		 * 
		 * @serial column dimension.
		 * @serial row dimension.
		 * @serial pivot sign.
		 */
		private int m, n, pivsign;

		/**
		 * Internal storage of pivot vector.
		 * 
		 * @serial pivot vector.
		 */
		private int[] piv;

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

			LU = A.toDoubleArray().clone();
			m = (int) A.getRowCount();
			n = (int) A.getColumnCount();
			piv = new int[m];
			for (int i = 0; i < m; i++) {
				piv[i] = i;
			}
			pivsign = 1;

			final double[] LUcolj = new double[m];

			// Outer loop.

			for (int j = 0; j < n; j++) {

				// Make a copy of the j-th column to localize references.

				for (int i = 0; i < m; i++) {
					LUcolj[i] = LU[i][j];
				}

				// Apply previous transformations.

				for (int i = 0; i < m; i++) {
					double[] LUrowi = LU[i];
					LUrowi = LU[i];

					// Most of the time is spent in the following dot product.

					int kmax = Math.min(i, j);
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

		public boolean isNonsingular() {
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

		public Matrix getL() {
			final int min = Math.min(m, n);
			double[][] L = new double[m][min];
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < min; j++) {
					if (i > j) {
						L[i][j] = LU[i][j];
					} else if (i == j) {
						L[i][j] = 1.0;
					} else {
						L[i][j] = 0.0;
					}
				}
			}
			return MatrixFactory.linkToArray(L);
		}

		/**
		 * Return upper triangular factor
		 * 
		 * @return U
		 */

		public Matrix getU() {
			final int min = Math.min(m, n);
			double[][] U = new double[min][n];
			for (int i = 0; i < min; i++) {
				for (int j = 0; j < n; j++) {
					if (i <= j) {
						U[i][j] = LU[i][j];
					} else {
						U[i][j] = 0.0;
					}
				}
			}
			return MatrixFactory.linkToArray(U);
		}

		/**
		 * Return pivot permutation vector
		 * 
		 * @return piv
		 */

		public int[] getPivot() {
			int[] p = new int[m];
			for (int i = 0; i < m; i++) {
				p[i] = piv[i];
			}
			return p;
		}

		public Matrix getP() {
			Matrix p = MatrixFactory.dense(m, m);
			for (int i = 0; i < m; i++) {
				p.setAsDouble(1, i, piv[i]);
			}
			return p;
		}

		/**
		 * Return pivot permutation vector as a one-dimensional double array
		 * 
		 * @return (double) piv
		 */

		public double[] getDoublePivot() {
			double[] vals = new double[m];
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

		public double det() {
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

		public Matrix solve(Matrix B) {
			if (B.getRowCount() != m) {
				throw new IllegalArgumentException("Matrix row dimensions must agree.");
			}
			if (!this.isNonsingular()) {
				throw new RuntimeException("Matrix is singular.");
			}

			// Copy right hand side with pivoting
			final int nx = (int) B.getColumnCount();
			Matrix Xmat = B.selectRows(Ret.NEW, MathUtil.toLongArray(piv));
			final double[][] X = Xmat.toDoubleArray();

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
			return MatrixFactory.linkToArray(X);
		}

		// public static Matrix[] calcNew(Matrix m) throws MatrixException {
		// LU lu = new LU(m);
		// return new Matrix[] { lu.getL(), lu.getU(), lu.getP() };
		// }
	};
}

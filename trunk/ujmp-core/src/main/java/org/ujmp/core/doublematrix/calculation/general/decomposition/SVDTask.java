package org.ujmp.core.doublematrix.calculation.general.decomposition;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.task.AbstractTask;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.matrices.MatrixLibraries.MatrixLibrary;

public class SVDTask extends AbstractTask<MapMatrix<String, Matrix>> {

	private final Matrix source;
	private MatrixLibrary matrixLibrary = MatrixLibrary.UJMP;
	private boolean wantU = true;
	private boolean wantV = true;
	private boolean thin = true;
	private int numSV = -1;

	public SVDTask(Matrix source) {
		this.source = source;
		new SVDTask(null).setWantU(false).executeInBackground();
	}

	public SVDTask setTruncated(int numSV) {
		this.numSV = numSV;
		return this;
	}

	public SVDTask setWantU(boolean wantU) {
		this.wantU = wantU;
		return this;
	}

	public SVDTask setMatrixLibrary(MatrixLibrary matrixLibrary) {
		switch (matrixLibrary) {
		case UJMP: {
			this.matrixLibrary = matrixLibrary;
			return this;
		}
		default:
			throw new RuntimeException("SVD not supported for this matrix libary");
		}

	}

	public SVDTask setWantV(boolean wantV) {
		this.wantV = wantV;
		return this;
	}

	public SVDTask setThin(boolean thin) {
		this.thin = thin;
		return this;
	}

	public MapMatrix<String, Matrix> call() throws Exception {
		SVDMatrix svd = new SVDMatrix(source, thin, wantU, wantV);
		MapMatrix<String, Matrix> result = new DefaultMapMatrix<String, Matrix>();

		switch (matrixLibrary) {
		case UJMP: {
			result.put("S", svd.getS());
			if (wantU) {
				result.put("U", svd.getU());
			}
			if (wantV) {
				result.put("V", svd.getV());
			}
			return result;
		}
		default:
			throw new RuntimeException("SVD not supported for this matrix libary");
		}
	}

	private final class SVDMatrix {
		private final double EPSILON = Math.pow(2.0, -52.0);
		private final double TINY = Math.pow(2.0, -966.0);

		/**
		 * Arrays for internal storage of U and V.
		 * 
		 * @serial internal storage of U.
		 * @serial internal storage of V.
		 */
		private final double[][] U, V;

		/**
		 * Array for internal storage of singular values.
		 * 
		 * @serial internal storage of singular values.
		 */
		private final double[] s;

		/**
		 * Row and column dimensions.
		 * 
		 * @serial row dimension.
		 * @serial column dimension.
		 * @serial U column dimension.
		 */
		private final int m, n, ncu;

		/**
		 * Column specification of matrix U
		 * 
		 * @serial U column dimension toggle
		 */

		private final boolean thin;

		/*
		 * ------------------------ Constructor ------------------------
		 */

		/**
		 * Construct the singular value decomposition
		 * 
		 * @param Arg
		 *            Rectangular matrix
		 * @param thin
		 *            If true U is economy sized
		 * @param wantu
		 *            If true generate the U matrix
		 * @param wantv
		 *            If true generate the V matrix
		 */

		public SVDMatrix(Matrix Arg, boolean thin, boolean wantu, boolean wantv) {

			// Derived from LINPACK code.
			// Initialize.
			final double[][] A = Arg.toDoubleArray();
			m = (int) Arg.getRowCount();
			n = (int) Arg.getColumnCount();
			this.thin = thin;

			ncu = thin ? Math.min(m, n) : m;
			s = new double[Math.min(m + 1, n)];
			U = new double[m][ncu];
			V = new double[n][n];
			final double[] e = new double[n];
			final double[] work = new double[m];

			// Reduce A to bidiagonal form, storing the diagonal elements
			// in s and the super-diagonal elements in e.

			final int nct = Math.min(m - 1, n);
			final int nrt = Math.max(0, Math.min(n - 2, m));
			final int lu = Math.max(nct, nrt);
			for (int k = 0; k < lu; k++) {
				if (k < nct) {

					// Compute the transformation for the k-th column and
					// place the k-th diagonal in s[k].
					// Compute 2-norm of k-th column without under/overflow.
					s[k] = 0;
					for (int i = k; i < m; i++) {
						s[k] = MathUtil.hypot(s[k], A[i][k]);
					}
					if (s[k] != 0.0) {
						if (A[k][k] < 0.0) {
							s[k] = -s[k];
						}
						for (int i = k; i < m; i++) {
							A[i][k] /= s[k];
						}
						A[k][k] += 1.0;
					}
					s[k] = -s[k];
				}
				for (int j = k + 1; j < n; j++) {
					if ((k < nct) & (s[k] != 0.0)) {

						// Apply the transformation.

						double t = 0;
						for (int i = k; i < m; i++) {
							t += A[i][k] * A[i][j];
						}
						t = -t / A[k][k];
						for (int i = k; i < m; i++) {
							A[i][j] += t * A[i][k];
						}
					}

					// Place the k-th row of A into e for the
					// subsequent calculation of the row transformation.

					e[j] = A[k][j];
				}
				if (wantu & (k < nct)) {

					// Place the transformation in U for subsequent back
					// multiplication.

					for (int i = k; i < m; i++) {
						U[i][k] = A[i][k];
					}
				}
				if (k < nrt) {

					// Compute the k-th row transformation and place the
					// k-th super-diagonal in e[k].
					// Compute 2-norm without under/overflow.
					e[k] = 0;
					for (int i = k + 1; i < n; i++) {
						e[k] = MathUtil.hypot(e[k], e[i]);
					}
					if (e[k] != 0.0) {
						if (e[k + 1] < 0.0) {
							e[k] = -e[k];
						}
						for (int i = k + 1; i < n; i++) {
							e[i] /= e[k];
						}
						e[k + 1] += 1.0;
					}
					e[k] = -e[k];
					if ((k + 1 < m) & (e[k] != 0.0)) {

						// Apply the transformation.

						for (int i = k + 1; i < m; i++) {
							work[i] = 0.0;
						}
						for (int j = k + 1; j < n; j++) {
							for (int i = k + 1; i < m; i++) {
								work[i] += e[j] * A[i][j];
							}
						}
						for (int j = k + 1; j < n; j++) {
							double t = -e[j] / e[k + 1];
							for (int i = k + 1; i < m; i++) {
								A[i][j] += t * work[i];
							}
						}
					}
					if (wantv) {

						// Place the transformation in V for subsequent
						// back multiplication.

						for (int i = k + 1; i < n; i++) {
							V[i][k] = e[i];
						}
					}
				}
			}

			// Set up the final bidiagonal matrix or order p.
			int p = Math.min(n, m + 1);
			if (nct < n) {
				s[nct] = A[nct][nct];
			}
			if (m < p) {
				s[p - 1] = 0.0;
			}
			if (nrt + 1 < p) {
				e[nrt] = A[nrt][p - 1];
			}
			e[p - 1] = 0.0;

			// If required, generate U.
			if (wantu) {
				for (int j = nct; j < ncu; j++) {
					for (int i = 0; i < m; i++) {
						U[i][j] = 0.0;
					}
					U[j][j] = 1.0;
				}
				for (int k = nct - 1; k >= 0; k--) {
					if (s[k] != 0.0) {
						for (int j = k + 1; j < ncu; j++) {
							double t = 0;
							for (int i = k; i < m; i++) {
								t += U[i][k] * U[i][j];
							}
							t = -t / U[k][k];
							for (int i = k; i < m; i++) {
								U[i][j] += t * U[i][k];
							}
						}
						for (int i = k; i < m; i++) {
							U[i][k] = -U[i][k];
						}
						U[k][k] += 1.0;
						for (int i = 0; i < k - 1; i++) {
							U[i][k] = 0.0;
						}
					} else {
						for (int i = 0; i < m; i++) {
							U[i][k] = 0.0;
						}
						U[k][k] = 1.0;
					}
				}
			}

			// If required, generate V.
			if (wantv) {
				for (int k = n - 1; k >= 0; k--) {
					if ((k < nrt) & (e[k] != 0.0)) {
						for (int j = k + 1; j < n; j++) {
							double t = 0;
							for (int i = k + 1; i < n; i++) {
								t += V[i][k] * V[i][j];
							}
							t = -t / V[k + 1][k];
							for (int i = k + 1; i < n; i++) {
								V[i][j] += t * V[i][k];
							}
						}
					}
					for (int i = 0; i < n; i++) {
						V[i][k] = 0.0;
					}
					V[k][k] = 1.0;
				}
			}

			// Main iteration loop for the singular values.

			final int pp = p - 1;

			while (p > 0) {
				int k, kase;

				// Here is where a test for too many iterations would go.

				// This section of the program inspects for
				// negligible elements in the s and e arrays. On
				// completion the variables kase and k are set as follows.

				// kase = 1 if s(p) and e[k-1] are negligible and k<p
				// kase = 2 if s(k) is negligible and k<p
				// kase = 3 if e[k-1] is negligible, k<p, and
				// s(k), ..., s(p) are not negligible (qr step).
				// kase = 4 if e(p-1) is negligible (convergence).

				for (k = p - 2; k >= -1; k--) {
					if (k == -1) {
						break;
					}
					if (Math.abs(e[k]) <= TINY + EPSILON * (Math.abs(s[k]) + Math.abs(s[k + 1]))) {
						e[k] = 0.0;
						break;
					}
				}
				if (k == p - 2) {
					kase = 4;
				} else {
					int ks;
					for (ks = p - 1; ks >= k; ks--) {
						if (ks == k) {
							break;
						}
						double t = (ks != p ? Math.abs(e[ks]) : 0.)
								+ (ks != k + 1 ? Math.abs(e[ks - 1]) : 0.);
						if (Math.abs(s[ks]) <= TINY + EPSILON * t) {
							s[ks] = 0.0;
							break;
						}
					}
					if (ks == k) {
						kase = 3;
					} else if (ks == p - 1) {
						kase = 1;
					} else {
						kase = 2;
						k = ks;
					}
				}
				k++;

				// Perform the task indicated by kase.

				switch (kase) {

				// Deflate negligible s(p).

				case 1: {
					double f = e[p - 2];
					e[p - 2] = 0.0;
					for (int j = p - 2; j >= k; j--) {
						double t = MathUtil.hypot(s[j], f);
						double cs = s[j] / t;
						double sn = f / t;
						s[j] = t;
						if (j != k) {
							f = -sn * e[j - 1];
							e[j - 1] = cs * e[j - 1];
						}
						if (wantv) {
							for (int i = 0; i < n; i++) {
								t = cs * V[i][j] + sn * V[i][p - 1];
								V[i][p - 1] = -sn * V[i][j] + cs * V[i][p - 1];
								V[i][j] = t;
							}
						}
					}
				}
					break;

				// Split at negligible s(k).

				case 2: {
					double f = e[k - 1];
					e[k - 1] = 0.0;
					for (int j = k; j < p; j++) {
						double t = MathUtil.hypot(s[j], f);
						double cs = s[j] / t;
						double sn = f / t;
						s[j] = t;
						f = -sn * e[j];
						e[j] = cs * e[j];
						if (wantu) {
							for (int i = 0; i < m; i++) {
								t = cs * U[i][j] + sn * U[i][k - 1];
								U[i][k - 1] = -sn * U[i][j] + cs * U[i][k - 1];
								U[i][j] = t;
							}
						}
					}
				}
					break;

				// Perform one qr step.

				case 3: {

					// Calculate the shift.

					final double scale = Math.max(
							Math.max(
									Math.max(Math.max(Math.abs(s[p - 1]), Math.abs(s[p - 2])),
											Math.abs(e[p - 2])), Math.abs(s[k])), Math.abs(e[k]));
					final double sp = s[p - 1] / scale;
					final double spm1 = s[p - 2] / scale;
					final double epm1 = e[p - 2] / scale;
					final double sk = s[k] / scale;
					final double ek = e[k] / scale;
					final double b = ((spm1 + sp) * (spm1 - sp) + epm1 * epm1) / 2.0;
					final double c = (sp * epm1) * (sp * epm1);
					double shift = 0.0;
					if ((b != 0.0) | (c != 0.0)) {
						shift = Math.sqrt(b * b + c);
						if (b < 0.0) {
							shift = -shift;
						}
						shift = c / (b + shift);
					}
					double f = (sk + sp) * (sk - sp) + shift;
					double g = sk * ek;

					// Chase zeros.

					for (int j = k; j < p - 1; j++) {
						double t = MathUtil.hypot(f, g);
						double cs = f / t;
						double sn = g / t;
						if (j != k) {
							e[j - 1] = t;
						}
						f = cs * s[j] + sn * e[j];
						e[j] = cs * e[j] - sn * s[j];
						g = sn * s[j + 1];
						s[j + 1] = cs * s[j + 1];
						if (wantv) {
							for (int i = 0; i < n; i++) {
								t = cs * V[i][j] + sn * V[i][j + 1];
								V[i][j + 1] = -sn * V[i][j] + cs * V[i][j + 1];
								V[i][j] = t;
							}
						}
						t = MathUtil.hypot(f, g);
						cs = f / t;
						sn = g / t;
						s[j] = t;
						f = cs * e[j] + sn * s[j + 1];
						s[j + 1] = -sn * e[j] + cs * s[j + 1];
						g = sn * e[j + 1];
						e[j + 1] = cs * e[j + 1];
						if (wantu && (j < m - 1)) {
							for (int i = 0; i < m; i++) {
								t = cs * U[i][j] + sn * U[i][j + 1];
								U[i][j + 1] = -sn * U[i][j] + cs * U[i][j + 1];
								U[i][j] = t;
							}
						}
					}
					e[p - 2] = f;
				}
					break;

				// Convergence.

				case 4: {

					// Make the singular values positive.

					if (s[k] <= 0.0) {
						s[k] = (s[k] < 0.0 ? -s[k] : 0.0);
						if (wantv) {
							for (int i = 0; i < n; i++) {
								V[i][k] = -V[i][k];
							}
						}
					}

					// Order the singular values.

					while (k < pp) {
						if (s[k] >= s[k + 1]) {
							break;
						}
						double t = s[k];
						s[k] = s[k + 1];
						s[k + 1] = t;
						if (wantv && (k < n - 1)) {
							for (int i = 0; i < n; i++) {
								t = V[i][k + 1];
								V[i][k + 1] = V[i][k];
								V[i][k] = t;
							}
						}
						if (wantu && (k < m - 1)) {
							for (int i = 0; i < m; i++) {
								t = U[i][k + 1];
								U[i][k + 1] = U[i][k];
								U[i][k] = t;
							}
						}
						k++;
					}
					p--;
				}
					break;
				}
			}
		}

		/*
		 * ------------------------ Public Methods ------------------------
		 */

		/**
		 * Return the left singular vectors
		 * 
		 * @return U
		 */

		public final DenseDoubleMatrix2D getU() {
			final double[][] x = new double[m][m >= n ? (thin ? Math.min(m + 1, n) : ncu) : ncu];

			for (int r = 0; r < m; r++) {
				for (int c = x[0].length; --c >= 0;) {
					x[r][c] = U[r][c];
				}
			}

			return Matrix.Factory.linkToArray(x);
		}

		/**
		 * Return the right singular vectors
		 * 
		 * @return V
		 */

		public final DenseDoubleMatrix2D getV() {
			return V == null ? null : Matrix.Factory.linkToArray(V);
		}

		/**
		 * Return the one-dimensional array of singular values
		 * 
		 * @return diagonal of S.
		 */

		public final double[] getSingularValues() {
			return s;
		}

		/**
		 * Return the diagonal matrix of singular values
		 * 
		 * @return S
		 */

		public final DenseDoubleMatrix2D getS() {
			final double[][] X = new double[m >= n ? (thin ? n : ncu) : ncu][n];
			for (int i = Math.min(m, n); --i >= 0;)
				X[i][i] = s[i];
			return Matrix.Factory.linkToArray(X);
		}

		/**
		 * Return the diagonal matrix of the reciprocals of the singular values
		 * 
		 * @return S+
		 */

		public final DenseDoubleMatrix2D getreciprocalS() {
			final double[][] X = new double[n][m >= n ? (thin ? n : ncu) : ncu];
			for (int i = Math.min(m, n) - 1; i >= 0; i--)
				X[i][i] = s[i] == 0.0 ? 0.0 : 1.0 / s[i];
			return Matrix.Factory.linkToArray(X);
		}

		/**
		 * Return the Moore-Penrose (generalized) inverse Slightly modified
		 * version of Kim van der Linde's code
		 * 
		 * @param omit
		 *            if true tolerance based omitting of negligible singular
		 *            values
		 * @return A+
		 */

		public final DenseDoubleMatrix2D inverse(boolean omit) {
			final double[][] inverse = new double[n][m];
			if (rank() > 0) {
				final double[] reciprocalS = new double[s.length];
				if (omit) {
					double tol = Math.max(m, n) * s[0] * EPSILON;
					for (int i = s.length - 1; i >= 0; i--)
						reciprocalS[i] = Math.abs(s[i]) < tol ? 0.0 : 1.0 / s[i];
				} else
					for (int i = s.length - 1; i >= 0; i--)
						reciprocalS[i] = s[i] == 0.0 ? 0.0 : 1.0 / s[i];
				int min = Math.min(n, ncu);
				for (int i = n - 1; i >= 0; i--)
					for (int j = m - 1; j >= 0; j--)
						for (int k = min - 1; k >= 0; k--)
							inverse[i][j] += V[i][k] * reciprocalS[k] * U[j][k];
			}
			return Matrix.Factory.linkToArray(inverse);
		}

		/**
		 * Two norm
		 * 
		 * @return max(S)
		 */

		public final double norm2() {
			return s[0];
		}

		/**
		 * Two norm condition number
		 * 
		 * @return max(S)/min(S)
		 */

		public final double cond() {
			return s[0] / s[Math.min(m, n) - 1];
		}

		/**
		 * Effective numerical matrix rank
		 * 
		 * @return Number of nonnegligible singular values.
		 */

		public final int rank() {
			final double tol = Math.max(m, n) * s[0] * EPSILON;
			int r = 0;
			for (int i = 0; i < s.length; i++) {
				if (s[i] > tol) {
					r++;
				}
			}
			return r;
		}

	}

}

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

package org.ujmp.core.calculation;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasDoubleArray;
import org.ujmp.core.interfaces.HasDoubleArray2D;
import org.ujmp.core.matrix.DenseMatrix;
import org.ujmp.core.matrix.DenseMatrix2D;
import org.ujmp.core.matrix.SparseMatrix;
import org.ujmp.core.util.concurrent.PFor;

public interface Mtimes<T> {

	public static Mtimes<Matrix> INSTANCE = new Mtimes<Matrix>() {

		public void calc(final Matrix source1, final Matrix source2, final Matrix target) {
			if (source1 instanceof DenseMatrix && source2 instanceof DenseMatrix
					&& target instanceof DenseMatrix) {
				Mtimes.DENSEMATRIX.calc((DenseMatrix) source1, (DenseMatrix) source2,
						(DenseMatrix) target);
			} else if (source1 instanceof SparseMatrix && source2 instanceof SparseMatrix
					&& target instanceof SparseMatrix) {
				Mtimes.SPARSEMATRIX.calc((SparseMatrix) source1, (SparseMatrix) source2,
						(SparseMatrix) target);
			} else {
				gemm(1.0, source1, 1.0, source2, target);
			}
		}

		private void gemm(final double alpha, final Matrix A, final double beta, final Matrix B,
				final Matrix C) {
			final int m1RowCount = (int) A.getRowCount();
			final int m1ColumnCount = (int) A.getColumnCount();
			final int m2RowCount = (int) B.getRowCount();
			final int m2ColumnCount = (int) B.getColumnCount();

			if (m1ColumnCount != m2RowCount) {
				throw new MatrixException("matrices have wrong size");
			}

			if (alpha == 0 || beta == 0) {
				return;
			}

			if (C.getRowCount() >= 100 && C.getColumnCount() >= 100) {
				new PFor(0, m2ColumnCount - 1) {

					@Override
					public void step(int i) {
						if (beta != 1.0) {
							for (int irow = 0; irow < m1RowCount; ++irow) {
								C.setAsDouble(C.getAsDouble(irow, i) * beta, irow, i);
							}
						}
						for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
							double temp = alpha * B.getAsDouble(lcol, i);
							if (temp != 0.0) {
								for (int irow = 0; irow < m1RowCount; ++irow) {
									C.setAsDouble(C.getAsDouble(irow, i)
											+ A.getAsDouble(irow, lcol) * temp, irow, i);
								}
							}
						}
					}
				};
			} else {
				for (int i = 0; i < m2ColumnCount; i++) {
					if (beta != 1.0) {
						for (int irow = 0; irow < m1RowCount; ++irow) {
							C.setAsDouble(C.getAsDouble(irow, i) * beta, irow, i);
						}
					}
					for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
						double temp = alpha * B.getAsDouble(lcol, i);
						if (temp != 0.0) {
							for (int irow = 0; irow < m1RowCount; ++irow) {
								C.setAsDouble(C.getAsDouble(irow, i) + A.getAsDouble(irow, lcol)
										* temp, irow, i);
							}
						}
					}
				}
			}
		}
	};

	public static Mtimes<DenseMatrix> DENSEMATRIX = new Mtimes<DenseMatrix>() {

		public void calc(final DenseMatrix source1, final DenseMatrix source2,
				final DenseMatrix target) {
			if (source1 instanceof DenseMatrix2D && source2 instanceof DenseMatrix2D
					&& target instanceof DenseMatrix2D) {
				Mtimes.DENSEMATRIX2D.calc((DenseMatrix2D) source1, (DenseMatrix2D) source2,
						(DenseMatrix2D) target);
			} else {
				gemm(1.0, source1, 1.0, source2, target);
			}
		}

		private void gemm(final double alpha, final DenseMatrix A, final double beta,
				final DenseMatrix B, final DenseMatrix C) {
			final int m1RowCount = (int) A.getRowCount();
			final int m1ColumnCount = (int) A.getColumnCount();
			final int m2RowCount = (int) B.getRowCount();
			final int m2ColumnCount = (int) B.getColumnCount();

			if (m1ColumnCount != m2RowCount) {
				throw new MatrixException("matrices have wrong size");
			}

			if (alpha == 0 || beta == 0) {
				return;
			}

			if (C.getRowCount() >= 100 && C.getColumnCount() >= 100) {
				new PFor(0, m2ColumnCount - 1) {

					@Override
					public void step(int i) {
						if (beta != 1.0) {
							for (int irow = 0; irow < m1RowCount; ++irow) {
								C.setAsDouble(C.getAsDouble(irow, i) * beta, irow, i);
							}
						}
						for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
							double temp = alpha * B.getAsDouble(lcol, i);
							if (temp != 0.0) {
								for (int irow = 0; irow < m1RowCount; ++irow) {
									C.setAsDouble(C.getAsDouble(irow, i)
											+ A.getAsDouble(irow, lcol) * temp, irow, i);
								}
							}
						}
					}
				};
			} else {
				for (int i = 0; i < m2ColumnCount; i++) {
					if (beta != 1.0) {
						for (int irow = 0; irow < m1RowCount; ++irow) {
							C.setAsDouble(C.getAsDouble(irow, i) * beta, irow, i);
						}
					}
					for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
						double temp = alpha * B.getAsDouble(lcol, i);
						if (temp != 0.0) {
							for (int irow = 0; irow < m1RowCount; ++irow) {
								C.setAsDouble(C.getAsDouble(irow, i) + A.getAsDouble(irow, lcol)
										* temp, irow, i);
							}
						}
					}
				}
			}
		}
	};

	public static Mtimes<SparseMatrix> SPARSEMATRIX = new Mtimes<SparseMatrix>() {

		public void calc(final SparseMatrix source1, final SparseMatrix source2,
				final SparseMatrix target) {
			gemm(1.0, source1, 1.0, source2, target);
		}

		private void gemm(final double alpha, final SparseMatrix A, final double beta,
				final SparseMatrix B, final SparseMatrix C) {
			final int m1RowCount = (int) A.getRowCount();
			final int m1ColumnCount = (int) A.getColumnCount();
			final int m2RowCount = (int) B.getRowCount();
			final int m2ColumnCount = (int) B.getColumnCount();

			if (m1ColumnCount != m2RowCount) {
				throw new MatrixException("matrices have wrong size");
			}

			if (alpha == 0 || beta == 0) {
				return;
			}

			if (C.getRowCount() >= 100 && C.getColumnCount() >= 100) {
				new PFor(0, m2ColumnCount - 1) {

					@Override
					public void step(int i) {
						if (beta != 1.0) {
							for (int irow = 0; irow < m1RowCount; ++irow) {
								C.setAsDouble(C.getAsDouble(irow, i) * beta, irow, i);
							}
						}
						for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
							double temp = alpha * B.getAsDouble(lcol, i);
							if (temp != 0.0) {
								for (int irow = 0; irow < m1RowCount; ++irow) {
									C.setAsDouble(C.getAsDouble(irow, i)
											+ A.getAsDouble(irow, lcol) * temp, irow, i);
								}
							}
						}
					}
				};
			} else {
				for (int i = 0; i < m2ColumnCount; i++) {
					if (beta != 1.0) {
						for (int irow = 0; irow < m1RowCount; ++irow) {
							C.setAsDouble(C.getAsDouble(irow, i) * beta, irow, i);
						}
					}
					for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
						double temp = alpha * B.getAsDouble(lcol, i);
						if (temp != 0.0) {
							for (int irow = 0; irow < m1RowCount; ++irow) {
								C.setAsDouble(C.getAsDouble(irow, i) + A.getAsDouble(irow, lcol)
										* temp, irow, i);
							}
						}
					}
				}
			}
		}

	};

	public static Mtimes<DenseMatrix2D> DENSEMATRIX2D = new Mtimes<DenseMatrix2D>() {

		public void calc(final DenseMatrix2D source1, final DenseMatrix2D source2,
				final DenseMatrix2D target) {
			if (source1 instanceof DenseDoubleMatrix2D && source2 instanceof DenseDoubleMatrix2D
					&& target instanceof DenseDoubleMatrix2D) {
				Mtimes.DENSEDOUBLEMATRIX2D.calc((DenseDoubleMatrix2D) source1,
						(DenseDoubleMatrix2D) source2, (DenseDoubleMatrix2D) target);
			} else {
				gemm(1.0, source1, 1.0, source2, target);
			}
		}

		private void gemm(final double alpha, final DenseMatrix2D A, final double beta,
				final DenseMatrix2D B, final DenseMatrix2D C) {
			final int m1RowCount = (int) A.getRowCount();
			final int m1ColumnCount = (int) A.getColumnCount();
			final int m2RowCount = (int) B.getRowCount();
			final int m2ColumnCount = (int) B.getColumnCount();

			if (m1ColumnCount != m2RowCount) {
				throw new MatrixException("matrices have wrong size");
			}

			if (alpha == 0 || beta == 0) {
				return;
			}

			if (C.getRowCount() >= 100 && C.getColumnCount() >= 100) {
				new PFor(0, m2ColumnCount - 1) {

					@Override
					public void step(int i) {
						if (beta != 1.0) {
							for (int irow = 0; irow < m1RowCount; ++irow) {
								C.setAsDouble(C.getAsDouble(irow, i) * beta, irow, i);
							}
						}
						for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
							double temp = alpha * B.getAsDouble(lcol, i);
							if (temp != 0.0) {
								for (int irow = 0; irow < m1RowCount; ++irow) {
									C.setAsDouble(C.getAsDouble(irow, i)
											+ A.getAsDouble(irow, lcol) * temp, irow, i);
								}
							}
						}
					}
				};
			} else {
				for (int i = 0; i < m2ColumnCount; i++) {
					if (beta != 1.0) {
						for (int irow = 0; irow < m1RowCount; ++irow) {
							C.setAsDouble(C.getAsDouble(irow, i) * beta, irow, i);
						}
					}
					for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
						double temp = alpha * B.getAsDouble(lcol, i);
						if (temp != 0.0) {
							for (int irow = 0; irow < m1RowCount; ++irow) {
								C.setAsDouble(C.getAsDouble(irow, i) + A.getAsDouble(irow, lcol)
										* temp, irow, i);
							}
						}
					}
				}
			}
		}
	};

	public static Mtimes<DenseDoubleMatrix2D> DENSEDOUBLEMATRIX2D = new Mtimes<DenseDoubleMatrix2D>() {

		public void calc(final DenseDoubleMatrix2D source1, final DenseDoubleMatrix2D source2,
				final DenseDoubleMatrix2D target) {
			if (source1 instanceof HasDoubleArray2D && source2 instanceof HasDoubleArray2D
					&& target instanceof HasDoubleArray2D) {
				calcDoubleArray2D(((HasDoubleArray2D) source1).getDoubleArray2D(),
						((HasDoubleArray2D) source2).getDoubleArray2D(),
						((HasDoubleArray2D) target).getDoubleArray2D());
			} else if (source1 instanceof HasDoubleArray && source2 instanceof HasDoubleArray
					&& target instanceof HasDoubleArray) {
				gemmDoubleArrayParallel(1.0, ((HasDoubleArray) source1).getDoubleArray(),
						(int) source1.getRowCount(), (int) source1.getColumnCount(), 1.0,
						((HasDoubleArray) source2).getDoubleArray(), (int) source2.getRowCount(),
						(int) source2.getColumnCount(), ((HasDoubleArray) target).getDoubleArray());
			} else {
				gemmDenseDoubleMatrix2D(1.0, source1, 1.0, source2, target);
			}
		}

		private void gemmDoubleArrayParallel(final double alpha, final double[] A,
				final int m1RowCount, final int m1ColumnCount, final double beta, final double[] B,
				final int m2RowCount, final int m2ColumnCount, final double[] C) {
			if (m1ColumnCount != m2RowCount) {
				throw new MatrixException("matrices have wrong size");
			}

			if (alpha == 0 || beta == 0) {
				return;
			}

			if (C.length >= 10000) {
				new PFor(0, m2ColumnCount - 1) {

					@Override
					public void step(int i) {
						final int jcolTimesM1RowCount = i * m1RowCount;
						final int jcolTimesM1ColumnCount = i * m1ColumnCount;
						if (beta != 1.0) {
							for (int irow = 0; irow < m1RowCount; ++irow) {
								C[irow + jcolTimesM1RowCount] *= beta;
							}
						}
						for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
							double temp = alpha * B[lcol + jcolTimesM1ColumnCount];
							if (temp != 0.0) {
								final int lcolTimesM1RowCount = lcol * m1RowCount;
								for (int irow = 0; irow < m1RowCount; ++irow) {
									C[irow + jcolTimesM1RowCount] += A[irow + lcolTimesM1RowCount]
											* temp;
								}
							}
						}

					}
				};
			} else {
				for (int i = 0; i < m2ColumnCount; i++) {
					final int jcolTimesM1RowCount = i * m1RowCount;
					final int jcolTimesM1ColumnCount = i * m1ColumnCount;
					if (beta != 1.0) {
						for (int irow = 0; irow < m1RowCount; ++irow) {
							C[irow + jcolTimesM1RowCount] *= beta;
						}
					}
					for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
						double temp = alpha * B[lcol + jcolTimesM1ColumnCount];
						if (temp != 0.0) {
							final int lcolTimesM1RowCount = lcol * m1RowCount;
							for (int irow = 0; irow < m1RowCount; ++irow) {
								C[irow + jcolTimesM1RowCount] += A[irow + lcolTimesM1RowCount]
										* temp;
							}
						}
					}
				}
			}
		}

		private void calcDoubleArray2D(final double[][] m1, final double[][] m2,
				final double[][] ret) {
			final int rowCount = m1.length;
			final int columnCount = m1[0].length;
			final int retColumnCount = m2[0].length;

			if (columnCount != m2.length) {
				throw new MatrixException("matrices have wrong size");
			}

			if (rowCount * retColumnCount >= 500) {
				new PFor(0, retColumnCount - 1) {
					@Override
					public void step(int i) {
						final double[] columns = new double[columnCount];
						for (int k = columnCount; --k != -1;) {
							columns[k] = m2[k][i];
						}
						for (int r = rowCount; --r != -1;) {
							double sum = 0;
							final double[] row = m1[r];
							for (int k = columnCount; --k != -1;) {
								sum += row[k] * columns[k];
							}
							ret[r][i] = sum;
						}
					}
				};
			} else {
				final double[] columns = new double[columnCount];
				for (int c = retColumnCount; --c != -1;) {
					for (int k = columnCount; --k != -1;) {
						columns[k] = m2[k][c];
					}
					for (int r = rowCount; --r != -1;) {
						double sum = 0;
						final double[] row = m1[r];
						for (int k = columnCount; --k != -1;) {
							sum += row[k] * columns[k];
						}
						ret[r][c] = sum;
					}
				}
			}
		}

		private void gemmDenseDoubleMatrix2D(final double alpha, final DenseDoubleMatrix2D A,
				final double beta, final DenseDoubleMatrix2D B, final DenseDoubleMatrix2D C) {
			final int m1RowCount = (int) A.getRowCount();
			final int m1ColumnCount = (int) A.getColumnCount();
			final int m2RowCount = (int) B.getRowCount();
			final int m2ColumnCount = (int) B.getColumnCount();

			if (m1ColumnCount != m2RowCount) {
				throw new MatrixException("matrices have wrong size");
			}

			if (alpha == 0 || beta == 0) {
				return;
			}

			if (C.getRowCount() >= 100 && C.getColumnCount() >= 100) {
				new PFor(0, m2ColumnCount - 1) {

					@Override
					public void step(int i) {
						if (beta != 1.0) {
							for (int irow = 0; irow < m1RowCount; ++irow) {
								C.setDouble(C.getDouble(irow, i) * beta, irow, i);
							}
						}
						for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
							double temp = alpha * B.getDouble(lcol, i);
							if (temp != 0.0) {
								for (int irow = 0; irow < m1RowCount; ++irow) {
									C.setDouble(C.getDouble(irow, i) + A.getDouble(irow, lcol)
											* temp, irow, i);
								}
							}
						}
					}
				};
			} else {
				for (int i = 0; i < m2ColumnCount; i++) {
					if (beta != 1.0) {
						for (int irow = 0; irow < m1RowCount; ++irow) {
							C.setDouble(C.getDouble(irow, i) * beta, irow, i);
						}
					}
					for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
						double temp = alpha * B.getDouble(lcol, i);
						if (temp != 0.0) {
							for (int irow = 0; irow < m1RowCount; ++irow) {
								C.setDouble(C.getDouble(irow, i) + A.getDouble(irow, lcol) * temp,
										irow, i);
							}
						}
					}
				}
			}
		}

	};

	public void calc(T source1, T source2, T target);

}

/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.ujmp.core.doublematrix.calculation.basic;

import org.ujmp.core.Matrix;
import org.ujmp.core.bytematrix.ByteMatrix2D;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.DoubleMatrix2D;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.doublematrix.impl.ArrayDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasByteArray;
import org.ujmp.core.interfaces.HasDoubleArray;
import org.ujmp.core.interfaces.HasDoubleArray2D;
import org.ujmp.core.util.MathUtil;

public class Mtimes extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 4170937261358240120L;

	private boolean ignoreNaN = false;

	private long[] size = null;

	public Mtimes(boolean ignoreNaN, Matrix m1, Matrix m2) {
		super(m1, m2);
		this.ignoreNaN = ignoreNaN;
		this.size = new long[] { m1.getRowCount(), m2.getColumnCount() };
	}

	public Mtimes() {
		super();
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		Matrix m1 = getSources()[0];
		Matrix m2 = getSources()[1];

		long row = coordinates[ROW];
		long col = coordinates[COLUMN];

		double sum = 0.0;
		if (ignoreNaN) {
			for (long k = m1.getColumnCount(); --k >= 0;) {
				sum += MathUtil.ignoreNaN(m1.getAsDouble(row, k))
						* MathUtil.ignoreNaN(m2.getAsDouble(k, col));
			}
		} else {
			for (long k = m1.getColumnCount(); --k >= 0;) {
				sum += m1.getAsDouble(row, k) * m2.getAsDouble(k, col);
			}
		}

		return sum;
	}

	// this is old and slower, maybe delete?
	@Deprecated
	private Matrix calcOld(boolean ignoreNaN, Matrix m1, Matrix m2) throws MatrixException {
		if (m1.isScalar() || m2.isScalar()) {
			return Times.calc(ignoreNaN, m1, m2);
		}

		if (m1.getColumnCount() != m2.getRowCount()) {
			throw new MatrixException("Matrices have wrong size: "
					+ Coordinates.toString(m1.getSize()) + " and "
					+ Coordinates.toString(m2.getSize()));
		}

		int i, j, k;
		double sum;
		final double[][] ret = new double[(int) m1.getRowCount()][(int) m2.getColumnCount()];

		if (ignoreNaN) {
			for (i = (int) m1.getRowCount(); --i >= 0;) {
				for (j = ret[0].length; --j >= 0;) {
					sum = 0.0;
					for (k = (int) m1.getColumnCount(); --k >= 0;) {
						sum += MathUtil.ignoreNaN(m1.getAsDouble(i, k))
								* MathUtil.ignoreNaN(m2.getAsDouble(k, j));
					}
					ret[i][j] = sum;
				}
			}
		} else {
			for (i = (int) m1.getRowCount(); --i >= 0;) {
				for (j = ret[0].length; --j >= 0;) {
					sum = 0.0;
					for (k = (int) m1.getColumnCount(); --k >= 0;) {
						sum += m1.getAsDouble(i, k) * m2.getAsDouble(k, j);
					}
					ret[i][j] = sum;
				}
			}
		}

		return new ArrayDenseDoubleMatrix2D(ret);
	}

	@Override
	public long[] getSize() {
		return size;
	}

	public Matrix calcByteMatrix2D(boolean ignoreNaN, ByteMatrix2D m1, ByteMatrix2D m2) {
		if (m1 instanceof HasByteArray && m2 instanceof HasByteArray) {
			return calcByteArray(((HasByteArray) m1).getByteArray(), (int) m1.getRowCount(),
					(int) m1.getColumnCount(), ((HasByteArray) m2).getByteArray(), (int) m2
							.getRowCount(), (int) m2.getColumnCount());
		} else {
			return gemmMatrix(1.0, m1, 1.0, m2, ignoreNaN);
		}
	}

	public Matrix calcDoubleMatrix2D(boolean ignoreNaN, DoubleMatrix2D m1, DoubleMatrix2D m2) {
		if (m1 instanceof HasDoubleArray2D && m2 instanceof HasDoubleArray2D) {
			return calcDoubleArray2D(((HasDoubleArray2D) m1).getDoubleArray2D(),
					((HasDoubleArray2D) m2).getDoubleArray2D());
		} else if (m1 instanceof HasDoubleArray && m2 instanceof HasDoubleArray) {
			return calcDoubleArray(((HasDoubleArray) m1).getDoubleArray(), (int) m1.getRowCount(),
					(int) m1.getColumnCount(), ((HasDoubleArray) m2).getDoubleArray(), (int) m2
							.getRowCount(), (int) m2.getColumnCount());
		} else {
			final int rowCount = (int) m1.getRowCount();
			final int columnCount = (int) m1.getColumnCount();
			final int retColumnCount = (int) m2.getColumnCount();

			if (columnCount != m2.getRowCount()) {
				throw new MatrixException("matrices have wrong size: "
						+ Coordinates.toString(m1.getSize()) + " and "
						+ Coordinates.toString(m2.getSize()));
			}

			final double[][] ret = new double[rowCount][retColumnCount];
			final double[] columns = new double[columnCount];

			if (ignoreNaN) {
				for (int c = retColumnCount; --c != -1;) {
					for (int k = columnCount; --k != -1;) {
						columns[k] = MathUtil.ignoreNaN(m2.getDouble(k, c));
					}
					for (int r = rowCount; --r != -1;) {
						double sum = 0;
						for (int k = columnCount; --k != -1;) {
							sum += MathUtil.ignoreNaN(m1.getDouble(r, k)) * columns[k];
						}
						ret[r][c] = sum;
					}
				}
			} else {
				for (int c = retColumnCount; --c != -1;) {
					for (int k = columnCount; --k != -1;) {
						columns[k] = m2.getDouble(k, c);
					}
					for (int r = rowCount; --r != -1;) {
						double sum = 0;
						for (int k = columnCount; --k != -1;) {
							sum += m1.getDouble(r, k) * columns[k];
						}
						ret[r][c] = sum;
					}
				}
			}

			return new ArrayDenseDoubleMatrix2D(ret);
		}
	}

	public Matrix calcDoubleArray(double[] A, int m1RowCount, int m1ColumnCount, double[] B,
			int m2RowCount, int m2ColumnCount) {
		return gemmDoubleArray(1.0, A, m1RowCount, m1ColumnCount, 1.0, B, m2RowCount, m2ColumnCount);
	}

	public Matrix calcByteArray(byte[] A, int m1RowCount, int m1ColumnCount, byte[] B,
			int m2RowCount, int m2ColumnCount) {
		return gemmByteArray(1.0, A, m1RowCount, m1ColumnCount, 1.0, B, m2RowCount, m2ColumnCount);
	}

	public DenseDoubleMatrix2D calcDoubleArray2D(double[][] m1, double[][] m2) {
		final int rowCount = m1.length;
		final int columnCount = m1[0].length;
		final int retColumnCount = m2[0].length;

		if (columnCount != m2.length) {
			throw new MatrixException("matrices have wrong size");
		}

		final double[][] ret = new double[rowCount][retColumnCount];
		final double[] columns = new double[columnCount];
		for (int c = retColumnCount; --c != -1;) {
			for (int k = columnCount; --k != -1;) {
				columns[k] = m2[k][c];
			}
			for (int r = rowCount; --r != -1;) {
				double sum = 0;
				double[] row = m1[r];
				for (int k = columnCount; --k != -1;) {
					sum += row[k] * columns[k];
				}
				ret[r][c] = sum;
			}
		}
		return new ArrayDenseDoubleMatrix2D(ret);
	}

	@Deprecated
	private DenseDoubleMatrix2D calcOld(double[] m1, int m1RowCount, int m1ColumnCount,
			double[] m2, int m2RowCount, int m2ColumnCount) {
		if (m1ColumnCount != m2RowCount) {
			throw new MatrixException("matrices have wrong size");
		}

		final double[] ret = new double[m1RowCount * m2ColumnCount];
		final double[] columns = new double[m1ColumnCount];
		for (int c = m2ColumnCount; --c != -1;) {
			for (int k = m1ColumnCount; --k != -1;) {
				columns[k] = m2[c * m2RowCount + k];
			}
			for (int r = m1RowCount; --r != -1;) {
				double sum = 0;
				for (int k = m1ColumnCount; --k != -1;) {
					sum += m1[k * m1RowCount + r] * columns[k];
				}
				ret[c * m1RowCount + r] = sum;
			}
		}
		return new DefaultDenseDoubleMatrix2D(ret, m1RowCount, m2ColumnCount);
	}

	public DenseDoubleMatrix2D gemmDoubleArray(final double alpha, final double[] A,
			final int m1RowCount, final int m1ColumnCount, final double beta, final double[] B,
			final int m2RowCount, final int m2ColumnCount) {
		if (m1ColumnCount != m2RowCount) {
			throw new MatrixException("matrices have wrong size");
		}

		final double[] C = new double[m1RowCount * m2ColumnCount];

		if (alpha == 0 || beta == 0) {
			return new DefaultDenseDoubleMatrix2D(C, m1RowCount, m2ColumnCount);
		}

		for (int jcol = 0; jcol < m2ColumnCount; ++jcol) {
			final int jcolTimesM1RowCount = jcol * m1RowCount;
			final int jcolTimesM1ColumnCount = jcol * m1ColumnCount;
			for (int irow = 0; irow < m1RowCount; ++irow) {
				C[irow + jcolTimesM1RowCount] *= beta;
			}
			for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
				double temp = alpha * B[lcol + jcolTimesM1ColumnCount];
				if (temp != 0.0) {
					final int lcolTimesM1RowCount = lcol * m1RowCount;
					for (int irow = 0; irow < m1RowCount; ++irow) {
						C[irow + jcolTimesM1RowCount] += A[irow + lcolTimesM1RowCount] * temp;
					}
				}
			}
		}
		return new DefaultDenseDoubleMatrix2D(C, m1RowCount, m2ColumnCount);
	}

	public DenseDoubleMatrix2D gemmByteArray(final double alpha, final byte[] A,
			final int m1RowCount, final int m1ColumnCount, final double beta, final byte[] B,
			final int m2RowCount, final int m2ColumnCount) {
		if (m1ColumnCount != m2RowCount) {
			throw new MatrixException("matrices have wrong size");
		}

		final double[] C = new double[m1RowCount * m2ColumnCount];

		if (alpha == 0 || beta == 0) {
			return new DefaultDenseDoubleMatrix2D(C, m1RowCount, m2ColumnCount);
		}

		for (int jcol = 0; jcol < m2ColumnCount; ++jcol) {
			final int jcolTimesM1RowCount = jcol * m1RowCount;
			final int jcolTimesM1ColumnCount = jcol * m1ColumnCount;
			for (int irow = 0; irow < m1RowCount; ++irow) {
				C[irow + jcolTimesM1RowCount] *= beta;
			}
			for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
				double temp = alpha * B[lcol + jcolTimesM1ColumnCount];
				if (temp != 0.0) {
					final int lcolTimesM1RowCount = lcol * m1RowCount;
					for (int irow = 0; irow < m1RowCount; ++irow) {
						C[irow + jcolTimesM1RowCount] += A[irow + lcolTimesM1RowCount] * temp;
					}
				}
			}
		}
		return new DefaultDenseDoubleMatrix2D(C, m1RowCount, m2ColumnCount);
	}

	public DenseDoubleMatrix2D gemmMatrix(final double alpha, final Matrix A, final double beta,
			final Matrix B, boolean ignoreNaN) {
		if (ignoreNaN) {
			return gemmMatrixIgnoreNaN(alpha, A, beta, B);
		} else {
			return gemmMatrixNaN(alpha, A, beta, B);
		}
	}

	public DenseDoubleMatrix2D gemmMatrixNaN(final double alpha, final Matrix A, final double beta,
			final Matrix B) {
		final int m1RowCount = (int) A.getRowCount();
		final int m1ColumnCount = (int) A.getColumnCount();
		final int m2RowCount = (int) B.getRowCount();
		final int m2ColumnCount = (int) B.getColumnCount();

		if (m1ColumnCount != m2RowCount) {
			throw new MatrixException("matrices have wrong size");
		}

		final double[] C = new double[m1RowCount * m2ColumnCount];

		if (alpha == 0 || beta == 0) {
			return new DefaultDenseDoubleMatrix2D(C, m1RowCount, m2ColumnCount);
		}

		for (int jcol = 0; jcol < m2ColumnCount; ++jcol) {
			final int jcolTimesM1RowCount = jcol * m1RowCount;
			if (beta != 1.0) {
				for (int irow = 0; irow < m1RowCount; ++irow) {
					C[irow + jcolTimesM1RowCount] *= beta;
				}
			}
			for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
				double temp = alpha * B.getAsDouble(lcol, jcol);
				if (temp != 0.0) {
					for (int irow = 0; irow < m1RowCount; ++irow) {
						C[irow + jcolTimesM1RowCount] += A.getAsDouble(irow, lcol) * temp;
					}
				}
			}
		}
		return new DefaultDenseDoubleMatrix2D(C, m1RowCount, m2ColumnCount);
	}

	public DenseDoubleMatrix2D gemmMatrixIgnoreNaN(final double alpha, final Matrix A,
			final double beta, final Matrix B) {
		final int m1RowCount = (int) A.getRowCount();
		final int m1ColumnCount = (int) A.getColumnCount();
		final int m2RowCount = (int) B.getRowCount();
		final int m2ColumnCount = (int) B.getColumnCount();

		if (m1ColumnCount != m2RowCount) {
			throw new MatrixException("matrices have wrong size");
		}

		final double[] C = new double[m1RowCount * m2ColumnCount];

		if (alpha == 0 || beta == 0) {
			return new DefaultDenseDoubleMatrix2D(C, m1RowCount, m2ColumnCount);
		}

		for (int jcol = 0; jcol < m2ColumnCount; ++jcol) {
			final int jcolTimesM1RowCount = jcol * m1RowCount;
			for (int irow = 0; irow < m1RowCount; ++irow) {
				C[irow + jcolTimesM1RowCount] *= beta;
			}
			for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
				double temp = alpha * MathUtil.ignoreNaN(B.getAsDouble(lcol, jcol));
				if (temp != 0.0) {
					for (int irow = 0; irow < m1RowCount; ++irow) {
						C[irow + jcolTimesM1RowCount] += MathUtil.ignoreNaN(A.getAsDouble(irow,
								lcol))
								* temp;
					}
				}
			}
		}
		return new DefaultDenseDoubleMatrix2D(C, m1RowCount, m2ColumnCount);
	}

	public Matrix calc(Matrix m1, Matrix m2) {
		return calc(false, m1, m2);
	}

	@Deprecated
	private Matrix calcMatrixOld(boolean ignoreNaN, Matrix m1, Matrix m2) {
		final int rowCount = (int) m1.getRowCount();
		final int columnCount = (int) m1.getColumnCount();
		final int retColumnCount = (int) m2.getColumnCount();

		if (columnCount != m2.getRowCount()) {
			throw new MatrixException("matrices have wrong size: "
					+ Coordinates.toString(m1.getSize()) + " and "
					+ Coordinates.toString(m2.getSize()));
		}

		final double[][] ret = new double[rowCount][retColumnCount];
		final double[] columns = new double[columnCount];

		if (ignoreNaN) {
			for (int c = retColumnCount; --c != -1;) {
				for (int k = columnCount; --k != -1;) {
					columns[k] = MathUtil.ignoreNaN(m2.getAsDouble(k, c));
				}
				for (int r = rowCount; --r != -1;) {
					double sum = 0;
					for (int k = columnCount; --k != -1;) {
						sum += MathUtil.ignoreNaN(m1.getAsDouble(r, k)) * columns[k];
					}
					ret[r][c] = sum;
				}
			}
		} else {
			for (int c = retColumnCount; --c != -1;) {
				for (int k = columnCount; --k != -1;) {
					columns[k] = m2.getAsDouble(k, c);
				}
				for (int r = rowCount; --r != -1;) {
					double sum = 0;
					for (int k = columnCount; --k != -1;) {
						sum += m1.getAsDouble(r, k) * columns[k];
					}
					ret[r][c] = sum;
				}
			}
		}

		return new ArrayDenseDoubleMatrix2D(ret);
	}

	public Matrix calc(boolean ignoreNaN, Matrix m1, Matrix m2) {
		if (m1 instanceof DoubleMatrix2D && m2 instanceof DoubleMatrix2D) {
			return calcDoubleMatrix2D(ignoreNaN, (DoubleMatrix2D) m1, (DoubleMatrix2D) m2);
		} else if (m1 instanceof ByteMatrix2D && m2 instanceof ByteMatrix2D) {
			return calcByteMatrix2D(ignoreNaN, (ByteMatrix2D) m1, (ByteMatrix2D) m2);
		} else {
			return gemmMatrix(1.0, m1, 1.0, m2, ignoreNaN);
		}
	}
}

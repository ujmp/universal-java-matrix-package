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
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.DoubleMatrix2D;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.doublematrix.impl.ArrayDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasRowMajorDoubleArray2D;
import org.ujmp.core.util.UJMPSettings;

/**
 * <p>
 * This class implements some matrix operations that I need for other things I'm
 * doing. Why not use existing packages? Well, I've tried, but they seem to not
 * have simple linear algebra things like inverting singular or non-square
 * inverses. Maybe I've just been looking in the wrong places.
 * </p>
 * <p>
 * Anyway I wrote this algorithm in 1989 using Ada and Fortran77. In 1992 I
 * converted it to C++, and in 2003 I made it a C++ template to be used for
 * absolutely anything that you'd want.
 * </p>
 * <p>
 * I attempted to convert this to a Java template, but I need to make objects of
 * the parameter type, and without doing some inspection or copy tricks, I can't
 * get there from here. I only use this for doubles anyway, so here's the
 * version that I use. The C++ version is still available.
 * <p>
 * </p>
 * The matrix inversion was in from the start, as the only really useful part of
 * the Class. I added the bandwidth reduction routine in 1991 - I was stuck in
 * SOS (USAF school) at the time and was thinking about optimizing the bandwidth
 * of a matrix made from a finite-element grid by renumbering the nodes of the
 * grid. </p>
 * <p>
 * Changes by Holger Arndt: The original code has been adapted for the Universal
 * Java Matrix Package. Methods for different matrix implementations have been
 * added.
 * </p>
 * 
 * @author Rand Huso
 * @author Holger Arndt
 */
public class Ginv extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 1087531579133023922L;

	public Ginv(Matrix source) {
		super(source);
	}

	/**
	 * This routine performs the matrix multiplication. The final matrix size is
	 * taken from the rows of the left matrix and the columns of the right
	 * matrix. The timesInner is the minimum of the left columns and the right
	 * rows.
	 * 
	 * @param matrix1
	 *            the first matrix
	 * @param matrix2
	 *            the second matrix
	 * @param timesInner
	 *            number of rows/columns to process
	 * @return product of the two matrices
	 */
	public static DenseDoubleMatrix2D times(DenseDoubleMatrix2D matrix1,
			DenseDoubleMatrix2D matrix2, long timesInner) {
		long timesRows = matrix1.getRowCount();
		long timesCols = matrix2.getColumnCount();
		DenseDoubleMatrix2D response = DoubleMatrix2D.factory.dense(timesRows, timesCols);
		for (long row = 0; row < timesRows; row++) {
			for (long col = 0; col < timesCols; col++) {
				for (long inner = 0; inner < timesInner; inner++) {
					response.setDouble(matrix1.getAsDouble(row, inner)
							* matrix2.getDouble(inner, col) + response.getDouble(row, col), row,
							col);
				}
			}
		}
		return response;
	}

	/**
	 * This routine performs the matrix multiplication. The final matrix size is
	 * taken from the rows of the left matrix and the columns of the right
	 * matrix. The timesInner is the minimum of the left columns and the right
	 * rows.
	 * 
	 * @param matrix1
	 *            the first matrix
	 * @param matrix2
	 *            the second matrix
	 * @param timesInner
	 *            number of rows/columns to process
	 * @return product of the two matrices
	 */
	public static double[][] times(double[][] matrix1, double[][] matrix2, int timesInner) {
		int timesRows = matrix1.length;
		int timesCols = matrix2[0].length;
		double[][] response = new double[timesRows][timesCols];
		for (int row = 0; row < timesRows; row++) {
			for (int col = 0; col < timesCols; col++) {
				for (int inner = 0; inner < timesInner; inner++) {
					response[row][col] = matrix1[row][inner] * matrix2[inner][col]
							+ response[row][col];
				}
			}
		}
		return response;
	}

	/**
	 * Swap components in the two columns.
	 * 
	 * @param matrix
	 *            the matrix to modify
	 * @param col1
	 *            the first row
	 * @param col2
	 *            the second row
	 */
	public static void swapCols(Matrix matrix, long col1, long col2) {
		double temp = 0;
		long rows = matrix.getRowCount();
		for (long row = 0; row < rows; row++) {
			temp = matrix.getAsDouble(row, col1);
			matrix.setAsDouble(matrix.getAsDouble(row, col2), row, col1);
			matrix.setAsDouble(temp, row, col2);
		}
	}

	/**
	 * Swap components in the two columns.
	 * 
	 * @param matrix
	 *            the matrix to modify
	 * @param col1
	 *            the first row
	 * @param col2
	 *            the second row
	 */
	public static void swapCols(DenseDoubleMatrix2D matrix, long col1, long col2) {
		double temp = 0;
		long rows = matrix.getRowCount();
		for (long row = 0; row < rows; row++) {
			temp = matrix.getDouble(row, col1);
			matrix.setDouble(matrix.getDouble(row, col2), row, col1);
			matrix.setDouble(temp, row, col2);
		}
	}

	/**
	 * Swap components in the two columns.
	 * 
	 * @param matrix
	 *            the matrix to modify
	 * @param col1
	 *            the first row
	 * @param col2
	 *            the second row
	 */
	public static void swapCols(double[][] matrix, int col1, int col2) {
		double temp = 0;
		int rows = matrix.length;
		double[] r = null;
		for (int row = 0; row < rows; row++) {
			r = matrix[row];
			temp = r[col1];
			r[col1] = r[col2];
			r[col2] = temp;
		}
	}

	/**
	 * Swap components in the two rows.
	 * 
	 * @param matrix
	 *            the matrix to modify
	 * @param row1
	 *            the first row
	 * @param row2
	 *            the second row
	 */
	public static void swapRows(Matrix matrix, long row1, long row2) {
		double temp = 0;
		long cols = matrix.getColumnCount();
		for (long col = 0; col < cols; col++) {
			temp = matrix.getAsDouble(row1, col);
			matrix.setAsDouble(matrix.getAsDouble(row2, col), row1, col);
			matrix.setAsDouble(temp, row2, col);
		}
	}

	/**
	 * Swap components in the two rows.
	 * 
	 * @param matrix
	 *            the matrix to modify
	 * @param row1
	 *            the first row
	 * @param row2
	 *            the second row
	 */
	public static void swapRows(DenseDoubleMatrix2D matrix, long row1, long row2) {
		double temp = 0;
		long cols = matrix.getColumnCount();
		for (long col = 0; col < cols; col++) {
			temp = matrix.getDouble(row1, col);
			matrix.setDouble(matrix.getDouble(row2, col), row1, col);
			matrix.setDouble(temp, row2, col);
		}
	}

	/**
	 * Swap components in the two rows.
	 * 
	 * @param matrix
	 *            the matrix to modify
	 * @param row1
	 *            the first row
	 * @param row2
	 *            the second row
	 */
	public static void swapRows(double[][] matrix, int row1, int row2) {
		double[] temp = matrix[row1];
		matrix[row1] = matrix[row2];
		matrix[row2] = temp;
	}

	/**
	 * <p>
	 * Matrix inversion is the reason this Class exists - this method creates a
	 * generalized matrix inverse of the current matrix. The result is returned
	 * in a new matrix.
	 * </p>
	 * <p>
	 * Matrices may be square, non-square, or singular. The operations will be
	 * identical. If the matrix has a single possible inverse, there will be no
	 * arbitrariness to the solution. The method here was suggested to me by
	 * John Jones Jr. at AFIT in the 1980s, and this algorithm is an original
	 * creation of mine to implement his method.
	 * </p>
	 * <p>
	 * A matrix inverse has some properties described here. Let
	 * <code><b>A</b></code> be the original matrix. Let the inverse, as
	 * calculated here be <code><b>A12</b></code> (an inverse with properties 1
	 * and 2 - being left side inverse and right side inverse). An inverse times
	 * the original matrix should yield an identity matrix.
	 * <code><b>A x = b</b></code> is the usual equation where
	 * <code><b>A</b></code> is the matrix, <code><b>x</b></code> is a vector of
	 * the unknowns and <code><b>b</b></code> is a vector of the constant
	 * values:
	 * </p>
	 * <p>
	 * Given these equations:
	 * <code><b>C x + D y + E z = b1 F x + G y + H z = b1</b></code>
	 * </p>
	 * <p>
	 * (The usual programs available don't handle more unknowns than equations,
	 * and will stop at this point)
	 * </p>
	 * <p>
	 * The <code><b>A</b></code> matrix is:
	 * <code><b>| C D E | | F G H |</b></code>
	 * </p>
	 * <p>
	 * The <code><b>x</b></code> vector is:
	 * <code><b>| x | | y | | z |</b></code>
	 * </p>
	 * <p>
	 * The <code><b>b</b></code> vector is: <code><b>| b1 | | b2 |</b></code>
	 * </p>
	 * <p>
	 * <code><b>A * x = b</b></code>
	 * </p>
	 * <p>
	 * The generalized inverse <code><b>A12</b></code> in this case will be of
	 * size (3,2): <code><b>| J K | | L M | | N P |</b></code>
	 * </p>
	 * <p>
	 * The left-hand inverse is defined such that the product of the
	 * (generalized) inverse times the original matrix times the (generalized)
	 * inverse will yield the (generalized) inverse again:
	 * <code><b>A12 * (A * A12) = A12</b></code>
	 * </p>
	 * <p>
	 * The right-hand inverse is defined similarly:
	 * <code><b>(A * A12) * A = A</b></code>
	 * </p>
	 * <p>
	 * If a matrix (<code><b>A12</b></code>) meets these criteria, it's
	 * considered to be a generalized inverse of the <code><b>A</b></code>
	 * matrix, even though it may not be square, or the product of
	 * <code><b>A * A12</b></code> or <code><b>A12 * A</b></code> may not be the
	 * identity matrix! (Won't be if the input <code><b>A</b></code> matrix is
	 * non-square or singular)
	 * </p>
	 * <p>
	 * In the case of <code><b>(A * A12)</b></code> being the identity matrix,
	 * the product of <code><b>(A12 *
	 * A)</b></code> will also be the identity matrix, and the solution will be
	 * unique: <code><b>A12</b></code> will be the exact and only solution to
	 * the equation.
	 * </p>
	 * <p>
	 * Refer to {@link http://mjollnir.com/matrix/} for the best description.
	 * </p>
	 * 
	 * @param matrix
	 *            matrix to invert
	 * @return a generalized matrix inverse (possibly not unique)
	 */
	public static DenseDoubleMatrix2D inverse(Matrix matrix) {
		double epsilon = UJMPSettings.getTolerance();
		long rows = matrix.getRowCount();
		long cols = matrix.getColumnCount();
		DenseDoubleMatrix2D s = DoubleMatrix2D.factory.dense(cols, cols);
		s.eye(Ret.ORIG);
		DenseDoubleMatrix2D t = DoubleMatrix2D.factory.dense(rows, rows);
		t.eye(Ret.ORIG);
		long maxDiag = Math.min(rows, cols);

		int diag = 0;
		for (; diag < maxDiag; diag++) {

			// get the largest value for the pivot
			swapPivot(matrix, diag, s, t);

			if (matrix.getAsDouble(diag, diag) == 0.0) {
				break;
			}

			// divide through to make pivot identity
			double divisor = matrix.getAsDouble(diag, diag);
			if (Math.abs(divisor) < epsilon) {
				matrix.setAsDouble(0.0, diag, diag);
				break;
			}

			divideRowBy(matrix, diag, diag, divisor);
			divideRowBy(t, diag, 0, divisor);
			matrix.setAsDouble(1.0, diag, diag);

			// remove values down remaining rows
			for (long row = diag + 1; row < rows; row++) {
				double factor = matrix.getAsDouble(row, diag);
				if (factor != 0.0) {
					addRowTimes(matrix, diag, diag, row, factor);
					addRowTimes(t, diag, 0, row, factor);
					matrix.setAsDouble(0.0, row, diag);
				}
			}

			// remove values across remaining cols - some optimization could
			// be done here because the changes to the original matrix at this
			// point only touch the current diag column
			for (long col = diag + 1; col < cols; col++) {
				double factor = matrix.getAsDouble(diag, col);
				if (factor != 0.0) {
					addColTimes(matrix, diag, diag, col, factor);
					addColTimes(s, diag, 0, col, factor);
					matrix.setAsDouble(0.0, diag, col);
				}
			}
		}

		return times(s, t, diag);
	}

	/**
	 * Same as {@link inverse(Matrix)} but optimized for 2D dense double
	 * matrices
	 * 
	 * @param matrix
	 *            the matrix to invert
	 * @return generalized matrix inverse
	 */
	public static DenseDoubleMatrix2D inverse(DenseDoubleMatrix2D matrix) {
		double epsilon = UJMPSettings.getTolerance();
		long rows = matrix.getRowCount();
		long cols = matrix.getColumnCount();
		DenseDoubleMatrix2D s = DoubleMatrix2D.factory.dense(cols, cols);
		s.eye(Ret.ORIG);
		DenseDoubleMatrix2D t = DoubleMatrix2D.factory.dense(rows, rows);
		t.eye(Ret.ORIG);
		long maxDiag = Math.min(rows, cols);

		int diag = 0;
		for (; diag < maxDiag; diag++) {

			// get the largest value for the pivot
			swapPivot(matrix, diag, s, t);

			if (matrix.getAsDouble(diag, diag) == 0.0) {
				break;
			}

			// divide through to make pivot identity
			double divisor = matrix.getAsDouble(diag, diag);
			if (Math.abs(divisor) < epsilon) {
				matrix.setDouble(0.0, diag, diag);
				break;
			}

			divideRowBy(matrix, diag, diag, divisor);
			divideRowBy(t, diag, 0, divisor);
			matrix.setDouble(1.0, diag, diag);

			// remove values down remaining rows
			for (long row = diag + 1; row < rows; row++) {
				double factor = matrix.getDouble(row, diag);
				if (factor != 0.0) {
					addRowTimes(matrix, diag, diag, row, factor);
					addRowTimes(t, diag, 0, row, factor);
					matrix.setDouble(0.0, row, diag);
				}
			}

			// remove values across remaining cols - some optimization could
			// be done here because the changes to the original matrix at this
			// point only touch the current diag column
			for (long col = diag + 1; col < cols; col++) {
				double factor = matrix.getDouble(diag, col);
				if (factor != 0.0) {
					addColTimes(matrix, diag, diag, col, factor);
					addColTimes(s, diag, 0, col, factor);
					matrix.setDouble(0.0, diag, col);
				}
			}
		}

		return times(s, t, diag);
	}

	/**
	 * Same as {@link inverse(Matrix)} but optimized for 2D double arrays
	 * 
	 * @param matrix
	 *            the matrix to invert
	 * @return generalized matrix inverse
	 */
	public static DenseDoubleMatrix2D inverse(final double[][] matrix) {
		double epsilon = UJMPSettings.getTolerance();
		int rows = matrix.length;
		int cols = matrix[0].length;
		double[][] s = new double[cols][cols];
		for (int c = 0; c < cols; c++) {
			s[c][c] = 1.0;
		}
		final double[][] t = new double[rows][rows];
		for (int r = 0; r < rows; r++) {
			t[r][r] = 1.0;
		}
		int maxDiag = Math.min(rows, cols);

		int diag = 0;
		for (; diag < maxDiag; diag++) {

			// get the largest value for the pivot
			swapPivot(matrix, diag, s, t);

			if (matrix[diag][diag] == 0.0) {
				break;
			}

			// divide through to make pivot identity
			double divisor = matrix[diag][diag];
			if (Math.abs(divisor) < epsilon) {
				matrix[diag][diag] = 0.0;
				break;
			}

			divideRowBy(matrix, diag, diag, divisor);
			divideRowBy(t, diag, 0, divisor);
			matrix[diag][diag] = 1.0;

			// remove values down remaining rows
			for (int row = diag + 1; row < rows; row++) {
				double factor = matrix[row][diag];
				if (factor != 0.0) {
					addRowTimes(matrix, diag, diag, row, factor);
					addRowTimes(t, diag, 0, row, factor);
					matrix[row][diag] = 0.0;
				}
			}

			// remove values across remaining cols - some optimization could
			// be done here because the changes to the original matrix at this
			// point only touch the current diag column
			for (int col = diag + 1; col < cols; col++) {
				double factor = matrix[diag][col];
				if (factor != 0.0) {
					addColTimes(matrix, diag, diag, col, factor);
					addColTimes(s, diag, 0, col, factor);
					matrix[diag][col] = 0.0;
				}
			}
		}

		double[][] result = times(s, t, diag);
		return new ArrayDenseDoubleMatrix2D(result);
	}

	/**
	 * Add a factor times one column to another column
	 * 
	 * @param matrix
	 *            the matrix to modify
	 * @param diag
	 *            coordinate on the diagonal
	 * @param fromRow
	 *            first row to process
	 * @param col
	 *            column to process
	 * @param factor
	 *            factor to multiply
	 */
	public static void addColTimes(Matrix matrix, long diag, long fromRow, long col, double factor) {
		long rows = matrix.getRowCount();
		for (long row = fromRow; row < rows; row++) {
			matrix.setAsDouble(matrix.getAsDouble(row, col) - factor
					* matrix.getAsDouble(row, diag), row, col);
		}
	}

	/**
	 * Add a factor times one column to another column
	 * 
	 * @param matrix
	 *            the matrix to modify
	 * @param diag
	 *            coordinate on the diagonal
	 * @param fromRow
	 *            first row to process
	 * @param col
	 *            column to process
	 * @param factor
	 *            factor to multiply
	 */
	public static void addColTimes(double[][] matrix, int diag, int fromRow, int col, double factor) {
		int rows = matrix.length;
		double[] r = null;
		for (int row = fromRow; row < rows; row++) {
			r = matrix[row];
			r[col] -= factor * r[diag];
		}
	}

	/**
	 * Add a factor times one column to another column
	 * 
	 * @param matrix
	 *            the matrix to modify
	 * @param diag
	 *            coordinate on the diagonal
	 * @param fromRow
	 *            first row to process
	 * @param col
	 *            column to process
	 * @param factor
	 *            factor to multiply
	 */
	public static void addColTimes(DenseDoubleMatrix2D matrix, long diag, long fromRow, long col,
			double factor) {
		long rows = matrix.getRowCount();
		for (long row = fromRow; row < rows; row++) {
			matrix.setDouble(matrix.getDouble(row, col) - factor * matrix.getDouble(row, diag),
					row, col);
		}
	}

	/**
	 * Add a factor times one row to another row
	 * 
	 * @param matrix
	 *            the matrix to modify
	 * @param diag
	 *            coordinate on the diagonal
	 * @param fromCol
	 *            first column to process
	 * @param row
	 *            row to process
	 * @param factor
	 *            factor to multiply
	 */
	public static void addRowTimes(DenseDoubleMatrix2D matrix, long diag, long fromCol, long row,
			double factor) {
		long cols = matrix.getColumnCount();
		for (long col = fromCol; col < cols; col++) {
			matrix.setDouble(matrix.getDouble(row, col) - factor * matrix.getDouble(diag, col),
					row, col);
		}
	}

	/**
	 * Add a factor times one row to another row
	 * 
	 * @param matrix
	 *            the matrix to modify
	 * @param diag
	 *            coordinate on the diagonal
	 * @param fromCol
	 *            first column to process
	 * @param row
	 *            row to process
	 * @param factor
	 *            factor to multiply
	 */
	public static void addRowTimes(double[][] matrix, int diag, int fromCol, int row, double factor) {
		int cols = matrix[0].length;
		double[] d = matrix[diag];
		double[] r = matrix[row];
		for (int col = fromCol; col < cols; col++) {
			r[col] -= factor * d[col];
		}
	}

	/**
	 * Add a factor times one row to another row
	 * 
	 * @param matrix
	 *            the matrix to modify
	 * @param diag
	 *            coordinate on the diagonal
	 * @param fromCol
	 *            first column to process
	 * @param row
	 *            row to process
	 * @param factor
	 *            factor to multiply
	 */
	public static void addRowTimes(Matrix matrix, long diag, long fromCol, long row, double factor) {
		long cols = matrix.getColumnCount();
		for (long col = fromCol; col < cols; col++) {
			matrix.setAsDouble(matrix.getAsDouble(row, col) - factor
					* matrix.getAsDouble(diag, col), row, col);
		}
	}

	/**
	 * Divide the row from this column position by this value
	 * 
	 * @param matrix
	 *            the matrix to modify
	 * @param aRow
	 *            the row to process
	 * @param fromCol
	 *            starting from column
	 * @param value
	 *            the value to divide
	 */
	public static void divideRowBy(Matrix matrix, long aRow, long fromCol, double value) {
		long cols = matrix.getColumnCount();
		for (long col = fromCol; col < cols; col++) {
			matrix.setAsDouble(matrix.getAsDouble(aRow, col) / value, aRow, col);
		}
	}

	/**
	 * Divide the row from this column position by this value
	 * 
	 * @param matrix
	 *            the matrix to modify
	 * @param aRow
	 *            the row to process
	 * @param fromCol
	 *            starting from column
	 * @param value
	 *            the value to divide
	 */
	public static void divideRowBy(DenseDoubleMatrix2D matrix, long aRow, long fromCol, double value) {
		long cols = matrix.getColumnCount();
		for (long col = fromCol; col < cols; col++) {
			matrix.setDouble(matrix.getDouble(aRow, col) / value, aRow, col);
		}
	}

	/**
	 * Divide the row from this column position by this value
	 * 
	 * @param matrix
	 *            the matrix to modify
	 * @param aRow
	 *            the row to process
	 * @param fromCol
	 *            starting from column
	 * @param value
	 *            the value to divide
	 */
	public static void divideRowBy(double[][] matrix, int aRow, int fromCol, double value) {
		int cols = matrix[0].length;
		double[] r = matrix[aRow];
		for (int col = fromCol; col < cols; col++) {
			r[col] /= value;
		}
	}

	/**
	 * Swap the matrices so that the largest value is on the pivot
	 * 
	 * @param source
	 *            the matrix to modify
	 * @param diag
	 *            the position on the diagonal
	 * @param s
	 *            the matrix s
	 * @param t
	 *            the matrix t
	 */
	public static void swapPivot(Matrix source, long diag, Matrix s, Matrix t) {
		// get swap row and col
		long swapRow = diag;
		long swapCol = diag;
		double maxValue = Math.abs(source.getAsDouble(diag, diag));
		long rows = source.getRowCount();
		long cols = source.getColumnCount();
		double abs = 0;

		for (long row = diag; row < rows; row++) {
			for (long col = diag; col < cols; col++) {
				abs = Math.abs(source.getAsDouble(row, col));
				if (abs > maxValue) {
					maxValue = abs;
					swapRow = row;
					swapCol = col;
				}
			}
		}

		// swap rows and columns
		if (swapRow != diag) {
			swapRows(source, swapRow, diag);
			swapRows(t, swapRow, diag);
		}
		if (swapCol != diag) {
			swapCols(source, swapCol, diag);
			swapCols(s, swapCol, diag);
		}
	}

	/**
	 * Swap the matrices so that the largest value is on the pivot
	 * 
	 * @param source
	 *            the matrix to modify
	 * @param diag
	 *            the position on the diagonal
	 * @param s
	 *            the matrix s
	 * @param t
	 *            the matrix t
	 */
	public static void swapPivot(DenseDoubleMatrix2D source, long diag, DenseDoubleMatrix2D s,
			DenseDoubleMatrix2D t) {
		// get swap row and col
		long swapRow = diag;
		long swapCol = diag;
		double maxValue = Math.abs(source.getDouble(diag, diag));
		long rows = source.getRowCount();
		long cols = source.getColumnCount();
		double abs = 0;
		for (long row = diag; row < rows; row++) {
			for (long col = diag; col < cols; col++) {
				abs = Math.abs(source.getDouble(row, col));
				if (abs > maxValue) {
					maxValue = abs;
					swapRow = row;
					swapCol = col;
				}
			}
		}

		// swap rows and columns
		if (swapRow != diag) {
			swapRows(source, swapRow, diag);
			swapRows(t, swapRow, diag);
		}
		if (swapCol != diag) {
			swapCols(source, swapCol, diag);
			swapCols(s, swapCol, diag);
		}
	}

	/**
	 * Swap the matrices so that the largest value is on the pivot
	 * 
	 * @param source
	 *            the matrix to modify
	 * @param diag
	 *            the position on the diagonal
	 * @param s
	 *            the matrix s
	 * @param t
	 *            the matrix t
	 */
	public static void swapPivot(double[][] source, int diag, double[][] s, double[][] t) {
		// get swap row and col
		int swapRow = diag;
		int swapCol = diag;
		double maxValue = Math.abs(source[diag][diag]);
		int rows = source.length;
		int cols = source[0].length;
		double abs = 0;
		double[] r = null;
		for (int row = diag; row < rows; row++) {
			r = source[row];
			for (int col = diag; col < cols; col++) {
				abs = Math.abs(r[col]);
				if (abs > maxValue) {
					maxValue = abs;
					swapRow = row;
					swapCol = col;
				}
			}
		}

		// swap rows and columns
		if (swapRow != diag) {
			swapRows(source, swapRow, diag);
			swapRows(t, swapRow, diag);
		}
		if (swapCol != diag) {
			swapCols(source, swapCol, diag);
			swapCols(s, swapCol, diag);
		}
	}

	/**
	 * Check to see if a non-zero and a zero value in the rows leading up to
	 * this column can be swapped. This is part of the bandwidth reduction
	 * algorithm.
	 * 
	 * @param matrix
	 *            the matrix to check
	 * @param row1
	 *            the first row
	 * @param row2
	 *            the second row
	 * @param col1
	 *            the column
	 * @return true if the rows can be swapped
	 */
	public static boolean canSwapRows(Matrix matrix, int row1, int row2, int col1) {
		boolean response = true;
		for (int col = 0; col < col1; ++col) {
			if (0 == matrix.getAsDouble(row1, col)) {
				if (0 != matrix.getAsDouble(row2, col)) {
					response = false;
					break;
				}
			}
		}
		return response;
	}

	/**
	 * Check to see if columns can be swapped - part of the bandwidth reduction
	 * algorithm.
	 * 
	 * @param matrix
	 *            the matrix to check
	 * @param col1
	 *            the first column
	 * @param col2
	 *            the second column
	 * @param row1
	 *            the row
	 * @return true if the columns can be swapped
	 */
	public static boolean canSwapCols(Matrix matrix, int col1, int col2, int row1) {
		boolean response = true;
		for (int row = row1 + 1; row < matrix.getRowCount(); ++row) {
			if (0 == matrix.getAsDouble(row, col1)) {
				if (0 != matrix.getAsDouble(row, col2)) {
					response = false;
					break;
				}
			}
		}
		return response;
	}

	public static Matrix reduce(Matrix source, Matrix response) {
		if (source.getRowCount() == source.getColumnCount()) {
			// pass one (descending the diagonal):
			for (int col = 0; col < source.getColumnCount() - 1; ++col) {
				for (int rowData = (int) source.getRowCount() - 1; rowData > col; --rowData) {
					if (0 != source.getAsDouble(rowData, col)) {
						for (int rowEmpty = rowData - 1; rowEmpty > col; --rowEmpty) {
							if (0 == source.getAsDouble(rowEmpty, col)) {
								if (Ginv.canSwapRows(source, rowData, rowEmpty, col)) {
									Ginv.swapRows(source, rowData, rowEmpty);
									Ginv.swapCols(source, rowData, rowEmpty);
									Ginv.swapRows(response, rowData, rowEmpty);
									break;
								}
							}
						}
					}
				}
			}
			// second pass (ascending the diagonal):
			for (int row = (int) source.getRowCount() - 1; row > 0; --row) {
				for (int colData = 0; colData < row - 1; ++colData) {
					if (0 != source.getAsDouble(row, colData)) {
						for (int colEmpty = colData + 1; colEmpty < row; ++colEmpty) {
							if (0 == source.getAsDouble(row, colEmpty)) {
								if (Ginv.canSwapCols(source, colData, colEmpty, row)) {
									Ginv.swapRows(source, colData, colEmpty);
									Ginv.swapCols(source, colData, colEmpty);
									Ginv.swapRows(response, colData, colEmpty);
									break;
								}
							}
						}
					}
				}
			}
		}
		return response;
	}

	/**
	 * Mathematical operator to reduce the bandwidth of a HusoMatrix. The
	 * HusoMatrix must be a square HusoMatrix or no operations are performed.
	 * 
	 * This method reduces a sparse HusoMatrix and returns the numbering of
	 * nodes to achieve this banding. It may be advantageous to run this twice,
	 * though sample cases haven't shown the need. Rows are numbered beginning
	 * with 0. The return HusoMatrix is a vector with what should be used as the
	 * new numbering to achieve minimum banding.
	 * 
	 * Each node in a typical finite-element grid is connected to surrounding
	 * nodes which are connected back to this node. This routine was designed
	 * with this requirement in mind. It may work where a node is connected to
	 * an adjacent node that is not connected back to this node... and this is
	 * quite possible when the grid is on a sphere and the connections are
	 * determined based on initial headings or bearings.
	 * 
	 * @return the vector indicating the numbering required to achieve a minimum
	 *         banding
	 */
	public static Matrix reduce(Matrix source) {
		Matrix response = Matrix.factory.dense(source.getRowCount(), 1);
		for (int row = 0; row < source.getRowCount(); ++row) {
			response.setAsDouble(row, row, 0);
		}
		return source.getRowCount() == source.getColumnCount() ? Ginv.reduce(source, response)
				: response;
	}

	/**
	 * Calculate the arbitrariness of the solution. This is a way to find out
	 * how unique the existing inverse is. The equation is here: A * x = b And
	 * the solution is: x = A12 * b + an arbitrariness which could be infinite,
	 * but will follow a general pattern. For instance, if the solution is a
	 * line, it could be anchored in the Y at any arbitrary distance. If the
	 * solution is a plane it could be arbitrarily set to any place in perhaps
	 * two different dimensions.
	 * 
	 * The arbitrariness is calculated by taking the difference between the
	 * complete inverse times the original and subtracting the generalized
	 * inverse times the original matrix. That's the idea, here's the formula:
	 * 
	 * x = A12 * b + (I - (A12 * A)) * z The z is a completely arbitrary vector
	 * (you decide that one). The product (A12 * A) could be the Identity
	 * HusoMatrix, if the solution is unique, in which case the right side drops
	 * out: (I - I) * z
	 * 
	 * Again, it's a lot easier to refer to the http://aktzin.com/ site for the
	 * description and a different way to get this information.
	 * 
	 * @return the matrix (I - (A12 * A))
	 */
	public static Matrix arbitrariness(Matrix source, Matrix inverse) {
		Matrix intermediate = inverse.mtimes(source);
		return MatrixFactory.eye(intermediate.getSize()).minus(intermediate);
	}

	public double getDouble(long... coordinates) throws MatrixException {
		throw new MatrixException("this method should never be called: LINK not possible");
	}

	public DenseDoubleMatrix2D calcLink() throws MatrixException {
		throw new MatrixException("linking not possible, use ORIG or NEW");
	}

	public DenseDoubleMatrix2D calcNew() throws MatrixException {
		Matrix source = getSource();
		ArrayDenseDoubleMatrix2D matrix = new ArrayDenseDoubleMatrix2D(source);
		return inverse(matrix.getRowMajorDoubleArray2D());
	}

	public DenseDoubleMatrix2D calcOrig() throws MatrixException {
		Matrix source = getSource();
		if (!source.isSquare()) {
			throw new MatrixException("ORIG only possible for square matrices");
		}

		if (source instanceof HasRowMajorDoubleArray2D) {
			return inverse(((HasRowMajorDoubleArray2D) source).getRowMajorDoubleArray2D());
		} else if (source instanceof DenseDoubleMatrix2D) {
			return inverse((DenseDoubleMatrix2D) source);
		} else {
			return inverse(source);
		}
	}

}

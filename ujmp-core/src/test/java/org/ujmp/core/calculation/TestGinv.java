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

package org.ujmp.core.calculation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Ginv;

/**
 * This class is used for testing the generalized inverse calculation.
 * 
 * @author Rand Huso
 * @author Holger Arndt
 */
public class TestGinv {
	private static final int M1ROWS = 2;
	private static final int M1COLS = 3;
	private static final int M2ROWS = 3;
	private static final int M2COLS = 4;
	private static final double PRODUCT = 2.54;
	private static final double TOLERANCE = 1e-7;

	private void setValues(Matrix matrix) {
		for (int row = 0; row < matrix.getRowCount(); ++row) {
			for (int col = 0; col < matrix.getColumnCount(); ++col) {
				matrix.setAsDouble(3 * row + col, row, col);
			}
		}
	}

	@Test
	public void testSet() {
		Matrix matrix = Matrix.Factory.zeros(M1ROWS, M1COLS);
		setValues(matrix);
	}

	@Test
	public void testGet() {
		Matrix matrix = Matrix.Factory.zeros(M1ROWS, M1COLS);
		setValues(matrix);
		for (int row = 0; row < M1ROWS; ++row) {
			for (int col = 0; col < M1COLS; ++col) {
				matrix.setAsDouble(row + col, row, col);
				assertEquals(row + col, (int) matrix.getAsDouble(row, col));
			}
		}
	}

	@Test
	public void testIdentity() {
		Matrix matrix = Matrix.Factory.zeros(M2ROWS, M2COLS);
		for (int i = 0; i < M2ROWS; i++) {
			matrix.setAsDouble(1, i, i);
		}
		assertNotNull(matrix);
		for (int row = 0; row < matrix.getRowCount(); ++row) {
			for (int col = 0; col < matrix.getColumnCount(); ++col) {
				if (row == col) {
					assertEquals(1.0, matrix.getAsDouble(row, col), TOLERANCE);
				} else {
					assertEquals(0.0, matrix.getAsDouble(row, col), TOLERANCE);
				}
			}
		}
	}

	@Test
	public void testEquals() {
		Matrix matrix1 = Matrix.Factory.zeros(M1ROWS, M1COLS);
		setValues(matrix1);
		Matrix matrix2 = Matrix.Factory.zeros(M1ROWS, M1COLS);
		setValues(matrix2);
		boolean result = matrix1.equals(matrix2);
		assertTrue(result);
	}

	@Test
	public void testNotEqualsSize() {
		Matrix matrix1 = Matrix.Factory.zeros(M2ROWS, M1COLS);
		setValues(matrix1);
		Matrix matrix2 = Matrix.Factory.zeros(M1ROWS, M1COLS);
		setValues(matrix2);
		boolean result = matrix1.equals(matrix2);
		assertFalse(result);
	}

	@Test
	public void testNotEqualsValue() {
		Matrix matrix1 = Matrix.Factory.zeros(M1ROWS, M1COLS);
		setValues(matrix1);
		Matrix matrix2 = Matrix.Factory.zeros(M1ROWS, M1COLS);
		setValues(matrix2);
		matrix2.setAsDouble(1.00001 * matrix2.getAsDouble(1, 1), 1, 1);
		boolean result = matrix1.equals(matrix2);
		assertFalse(result);
	}

	@Test
	public void testMatrixDot() {
		Matrix matrix1 = Matrix.Factory.zeros(M1COLS, M1COLS);
		for (int i = 0; i < M1COLS; i++) {
			matrix1.setAsDouble(1, i, i);
		}
		Matrix matrix2 = Matrix.Factory.zeros(M1COLS, M1COLS);
		for (int i = 0; i < M1COLS; i++) {
			matrix2.setAsDouble(1, i, i);
		}
		double result = matrix1.mtimes(matrix2).getValueSum();
		assertEquals(M1COLS, result, 0.0001);
	}

	@Test
	public void testPlusScalar() {
		Matrix matrix1 = Matrix.Factory.zeros(M2ROWS, M2ROWS);
		for (int i = 0; i < M2ROWS; i++) {
			matrix1.setAsDouble(1, i, i);
		}
		Matrix result = matrix1.plus(PRODUCT);
		for (int row = 0; row < result.getRowCount(); ++row) {
			for (int col = 0; col < result.getColumnCount(); ++col) {
				if (row == col) {
					assertEquals(1 + PRODUCT, result.getAsDouble(row, col), 0.001);
				} else {
					assertEquals(PRODUCT, result.getAsDouble(row, col), 0.001);
				}
			}
		}
	}

	@Test
	public void testTimesScalar() {
		Matrix matrix1 = Matrix.Factory.zeros(M1ROWS, M1ROWS);
		for (int i = 0; i < M1ROWS; i++) {
			matrix1.setAsDouble(1, i, i);
		}
		Matrix result = matrix1.times(PRODUCT);
		for (int row = 0; row < result.getRowCount(); ++row) {
			for (int col = 0; col < result.getColumnCount(); ++col) {
				if (row == col) {
					assertEquals(PRODUCT, result.getAsDouble(row, col), 0.001);
				} else {
					assertEquals(0.0, result.getAsDouble(row, col), 0.001);
				}
			}
		}
	}

	private void setIdentity(Matrix matrix) {
		for (int row = 0; row < matrix.getRowCount(); ++row) {
			for (int col = 0; col < matrix.getColumnCount(); ++col) {
				matrix.setAsDouble(0.0, row, col);
			}
		}
		int inner = (int) Math.min(matrix.getRowCount(), matrix.getColumnCount());
		for (int diag = 0; diag < inner; ++diag) {
			matrix.setAsDouble(1.0, diag, diag);
		}
	}

	@Test
	public void testMultiply() {
		Matrix matrix1 = Matrix.Factory.zeros(M1ROWS, M1COLS);
		setIdentity(matrix1);
		Matrix matrix2 = Matrix.Factory.zeros(M2ROWS, M2COLS);
		setIdentity(matrix2);
		Matrix result = matrix1.mtimes(matrix2);
		Matrix desire = Matrix.Factory.zeros(M1ROWS, M2COLS);
		setIdentity(desire);
		assertTrue(result.equals(desire));
	}

	@Test
	public void testCopy() {
		Matrix matrix1 = Matrix.Factory.zeros(M1ROWS, M1COLS);
		setValues(matrix1);
		Matrix matrix2 = matrix1.clone();
		assertTrue(matrix1.equals(matrix2));
	}

	/**
	 * This routine is a bit tough to test because there may be more than one
	 * solution. This is a canned test.
	 * 
	 */
	@Test
	public void testReduce() {
		Matrix a = Matrix.Factory.zeros(5, 5); // must be a square HusoMatrix
		a.setAsDouble(1, 0, 0);
		a.setAsDouble(1, 0, 1);
		a.setAsDouble(1, 0, 2);
		a.setAsDouble(1, 0, 3);
		a.setAsDouble(1, 0, 4);
		a.setAsDouble(1, 1, 0);
		a.setAsDouble(1, 1, 4);
		a.setAsDouble(1, 1, 1);
		a.setAsDouble(1, 2, 0);
		a.setAsDouble(1, 2, 2);
		a.setAsDouble(1, 2, 3);
		a.setAsDouble(1, 3, 0);
		a.setAsDouble(1, 3, 2);
		a.setAsDouble(1, 3, 3);
		a.setAsDouble(1, 3, 4);
		a.setAsDouble(1, 4, 0);
		a.setAsDouble(1, 4, 1);
		a.setAsDouble(1, 4, 3);
		a.setAsDouble(1, 4, 4);
		Matrix response = Ginv.reduce(a);
		assertEquals(1.0, response.getAsDouble(0, 0), TOLERANCE);
		assertEquals(4.0, response.getAsDouble(1, 0), TOLERANCE);
		assertEquals(0.0, response.getAsDouble(2, 0), TOLERANCE);
		assertEquals(3.0, response.getAsDouble(3, 0), TOLERANCE);
		assertEquals(2.0, response.getAsDouble(4, 0), TOLERANCE);
	}

	@Test
	public void testSquareInverse() {
		Matrix a = Matrix.Factory.zeros(3, 3); // example from ucla.edu
		a.setAsDouble(4, 0, 0);
		a.setAsDouble(2, 0, 1);
		a.setAsDouble(2, 0, 2);
		a.setAsDouble(4, 1, 0);
		a.setAsDouble(6, 1, 1);
		a.setAsDouble(8, 1, 2);
		a.setAsDouble(-2, 2, 0);
		a.setAsDouble(2, 2, 1);
		a.setAsDouble(4, 2, 2);
		Matrix a12 = a.ginv();
		Matrix a12a = a12.mtimes(a);
		for (int row = 0; row < a12a.getRowCount(); ++row) {
			for (int col = 0; col < a12a.getColumnCount(); ++col) {
				if (row == col) {
					assertEquals(1.0, a12a.getAsDouble(row, col), 0.001);
				} else {
					assertEquals(0.0, a12a.getAsDouble(row, col), 0.001);
				}
			}
		}
	}

	@Test
	public void testSquareArbitrariness() {
		Matrix a = Matrix.Factory.zeros(3, 3); // example from ucla.edu
		a.setAsDouble(4, 0, 0);
		a.setAsDouble(2, 0, 1);
		a.setAsDouble(2, 0, 2);
		a.setAsDouble(4, 1, 0);
		a.setAsDouble(6, 1, 1);
		a.setAsDouble(8, 1, 2);
		a.setAsDouble(-2, 2, 0);
		a.setAsDouble(2, 2, 1);
		a.setAsDouble(4, 2, 2);
		Matrix a12 = a.ginv();
		Matrix ima12a = Ginv.arbitrariness(a, a12);
		for (int row = 0; row < ima12a.getRowCount(); ++row) {
			for (int col = 0; col < ima12a.getColumnCount(); ++col) {
				assertEquals(0.0, ima12a.getAsDouble(row, col), 0.001);
			}
		}
	}

	@Test
	public void testNonSquareInverse() {
		Matrix a = Matrix.Factory.zeros(3, 4);
		a.setAsDouble(4, 0, 0);
		a.setAsDouble(2, 0, 1);
		a.setAsDouble(2, 0, 2);
		a.setAsDouble(2, 0, 3);
		a.setAsDouble(4, 1, 0);
		a.setAsDouble(6, 1, 1);
		a.setAsDouble(8, 1, 2);
		a.setAsDouble(8, 1, 3);
		a.setAsDouble(-2, 2, 0);
		a.setAsDouble(2, 2, 1);
		a.setAsDouble(4, 2, 2);
		a.setAsDouble(4, 2, 3);
		Matrix a12 = a.ginv();
		Matrix ima12a = Ginv.arbitrariness(a, a12);
		for (int row = 0; row < ima12a.getRowCount(); ++row) {
			for (int col = 0; col < ima12a.getColumnCount(); ++col) {
				if (col == 3 && (row == 2 || row == 3)) {
					if (row == 2) {
						assertEquals(-1.0, ima12a.getAsDouble(row, col), 0.001);
					}
					if (row == 3) {
						assertEquals(1.0, ima12a.getAsDouble(row, col), 0.001);
					}
				} else {
					assertEquals(0.0, ima12a.getAsDouble(row, col), 0.001);
				}
			}
		}
	}

	@Test
	public void testNonSquareInverse2() {
		Matrix a = Matrix.Factory.zeros(3, 3);
		a.setAsDouble(4, 0, 0);
		a.setAsDouble(2, 0, 1);
		a.setAsDouble(2, 0, 2);
		a.setAsDouble(-2, 1, 0);
		a.setAsDouble(2, 1, 1);
		a.setAsDouble(4, 1, 2);
		Matrix a12 = a.ginv();
		Matrix a12a = a12.mtimes(a);
		Matrix a12aa12 = a12a.mtimes(a12);
		Matrix aa12a = a.mtimes(a12a);
		for (int row = 0; row < a12aa12.getRowCount(); ++row) {
			for (int col = 0; col < a12aa12.getColumnCount(); ++col) {
				assertEquals(a12.getAsDouble(row, col), a12aa12.getAsDouble(row, col), 0.001);
			}
		}
		for (int row = 0; row < aa12a.getRowCount(); ++row) {
			for (int col = 0; col < aa12a.getColumnCount(); ++col) {
				assertEquals(a.getAsDouble(row, col), aa12a.getAsDouble(row, col), 0.001);
			}
		}
	}

	@Test
	public void testNonSquareArbitrariness() {
		Matrix a = Matrix.Factory.zeros(2, 3);
		a.setAsDouble(2, 0, 0);
		a.setAsDouble(1, 0, 1);
		a.setAsDouble(-1, 0, 2);
		a.setAsDouble(1, 1, 0);
		a.setAsDouble(2, 1, 1);
		a.setAsDouble(-1, 1, 2);
		Matrix a12 = a.ginv();
		Matrix a12a = a12.mtimes(a);
		assertTrue(a12.equals(a12a.mtimes(a12)));
		assertTrue(a.equals(a.mtimes(a12a)));
	}

	@Test
	public void testRandomInverses() {
		Random rand = new Random(System.currentTimeMillis());
		for (int count = 0; count < 10000; ++count) {
			Matrix a = Matrix.Factory.zeros(1 + Math.abs(rand.nextInt()) % 6, 1 + Math.abs(rand
					.nextInt()) % 6);
			for (int row = 0; row < a.getRowCount(); ++row) {
				for (int col = 0; col < a.getColumnCount(); ++col) {
					a.setAsDouble(rand.nextDouble(), row, col);
				}
			}
			Matrix a12 = a.ginv();
			Matrix a12a = a12.mtimes(a);
			Matrix a12aa12 = a12a.mtimes(a12);
			Matrix aa12a = a.mtimes(a12a);
			// confirm left and right side inverses:
			for (int row = 0; row < a12aa12.getRowCount(); ++row) {
				for (int col = 0; col < a12aa12.getColumnCount(); ++col) {
					assertEquals(a12.getAsDouble(row, col), a12aa12.getAsDouble(row, col), 0.001);
				}
			}
			for (int row = 0; row < aa12a.getRowCount(); ++row) {
				for (int col = 0; col < aa12a.getColumnCount(); ++col) {
					assertEquals(a.getAsDouble(row, col), aa12a.getAsDouble(row, col), 0.001);
				}
			}
		}
	}

	@Test
	public void testSquareInverse2() {
		Matrix a = Matrix.Factory.zeros(3, 3);
		a.setAsDouble(2, 0, 0);
		a.setAsDouble(2, 0, 1);
		a.setAsDouble(4, 0, 2);
		a.setAsDouble(2, 1, 0);
		a.setAsDouble(4, 1, 1);
		a.setAsDouble(6, 1, 2);
		a.setAsDouble(-2, 2, 0);
		a.setAsDouble(-2, 2, 1);
		a.setAsDouble(-4, 2, 2);
		Matrix a12 = a.ginv();
		Matrix a12a = a12.mtimes(a);
		Matrix a12aa12 = a12a.mtimes(a12);
		Matrix aa12a = a.mtimes(a12a);
		for (int row = 0; row < a12aa12.getRowCount(); ++row) {
			for (int col = 0; col < a12aa12.getColumnCount(); ++col) {
				assertEquals(a12.getAsDouble(row, col), a12aa12.getAsDouble(row, col), 0.001);
			}
		}
		for (int row = 0; row < aa12a.getRowCount(); ++row) {
			for (int col = 0; col < aa12a.getColumnCount(); ++col) {
				assertEquals(a.getAsDouble(row, col), aa12a.getAsDouble(row, col), 0.001);
			}
		}
	}
}

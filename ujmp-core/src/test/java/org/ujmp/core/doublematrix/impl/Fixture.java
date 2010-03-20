/*
 * Copyright (C) 2010 by Frode Carlsen
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
package org.ujmp.core.doublematrix.impl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Callable;

import org.ujmp.core.Matrix;

/**
 * Test Fixture
 */
public class Fixture {

	public static final double[][] MAT_A = new double[][] { { 0, 1, 2, 0.5, 1.5, 2.5, 3.5 }, //
			{ 3, 4, 5, 3.5, 4.5, 5.5, 6.5 }, //
			{ 6, 7, 8, 6.5, 7.5, 8.5, 9.5 }, //
			{ 9, 10, 11, 9.5, 10.5, 11.5, 12.5 }, //
			{ 12, 13, 14, 12.5, 13.5, 14.5, 15.5 }, //
			{ 15, 16, 17, 15.5, 16.5, 17.5, 18.5 }, { 19, 20, 21, 22, 23, 24, 24.5 } };

	/** MAT_C = MAT_A * MAT_A */
	public static final double[][] MAT_C = new double[][] {
			{ 141.5000, 152.5000, 163.5000, 155.7500, 166.7500, 177.7500, 187.0000, 0 },
			{ 333.5000, 365.5000, 397.5000, 365.7500, 397.7500, 429.7500, 458.5000, 0 },
			{ 525.5000, 578.5000, 631.5000, 575.7500, 628.7500, 681.7500, 730.0000, 0 },
			{ 717.5000, 791.5000, 865.5000, 785.7500, 859.7500, 933.7500, 1001.5000, 0 },
			{ 909.5000, 1004.5000, 1099.5000, 995.7500, 1090.7500, 1185.7500, 1273.0000, 0 },
			{ 1101.5000, 1217.5000, 1333.5000, 1205.7500, 1321.7500, 1437.7500, 1544.5000, 0 },
			{ 1485.5000, 1639.0000, 1792.5000, 1623.5000, 1777.0000, 1930.5000, 2071.7500, 0 },// 
			{ 0, 0, 0, 0, 0, 0, 0, 0 } };

	public static final double[][] MAT_A1 = new double[][] { { 1, 2, 3 }, { 4, 5, 6 } };
	public static final double[][] MAT_B1 = new double[][] { { 1, 2 }, { 4, 5 }, { 7, 8 } };
	public static final double[][] MAT_C1 = new double[][] { { 30, 36 }, { 66, 81 } };

	public static final double[][] createLargeMatrix(final int rows, final int cols) {
		return createArrayMatrixWithRandomData(rows, cols, 31);
	}

	public static final double[][] createArrayMatrixWithRandomData(final int rows, final int cols, final int seed) {
		Random rnd = new Random(seed);
		double[][] lgData = new double[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				lgData[i][j] = ((int) (100 * rnd.nextDouble())) / 10;
			}
		}
		return lgData;
	}

	public static void assertEquals2DIntArray(int[][] arr1, int[][] arr2) {

		if (arr1 == null || arr2 == null || arr1.length != arr2.length) {
			throw new AssertionError("arrays are different");
		}
		for (int i = 0; i < arr1.length; i++) {
			int[] row1 = arr1[i];
			int[] row2 = arr2[i];
			if (!Arrays.equals(row1, row2)) {
				throw new AssertionError("row #" + i + " mismatch :");
			}
		}
	}

	public static void assertMatrixEquals2DIntArray(BlockDenseDoubleMatrix2D mat, double[][] arr2) {
		if (mat == null || arr2 == null) {
			throw new AssertionError("arrays are different");
		}
		for (int i = 0; i < arr2.length; i++) {
			double[] row2 = arr2[i];
			for (int j = 0; j < arr2[0].length; j++) {
				assertEquals(row2[j], mat.getDouble(i, j));
			}
		}
	}

	public static void toString(int[][] matrix) {
		StringBuilder b = new StringBuilder();
		assertTrue("matrix is null", matrix != null);
		for (int i = 0; i < matrix.length; i++) {
			int[] row = matrix[i];
			b.append("\n");
			b.append(Arrays.toString(row));
		}

		System.out.println(b);
	}

	protected static BlockDenseDoubleMatrix2D createBlockRowLayout(final double[][] myData, final int blockSize,
			final BlockOrder blockOrder) {
		assert myData != null && myData.length > 0 : "Invalid matrix; must have myData.length>0";
		BlockDenseDoubleMatrix2D mat = new BlockDenseDoubleMatrix2D(myData, blockSize, blockOrder);
		return mat;
	}

	protected static int BLOCKSIZE = 200;

	protected static int LARGE_N = 2000;

	protected static int M = 1000;

	protected static int N = 1200;

	protected static int SEED = 33;

	public static BlockDenseDoubleMatrix2D createBlockRowLayoutWithGeneratedData(int i, int j, int blockSize,
			final BlockOrder blockOrder) {
		return createBlockRowLayout(createArrayMatrixWithRandomData(i, j, SEED), blockSize, blockOrder);
	}

	public static Matrix createDenseMatrixWithGeneratedData(int i, int j) {
		return createDenseMatrix(createArrayMatrixWithRandomData(i, j, SEED));
	}

	public static Matrix createDenseMatrix(double[][] data) {
		long rows = (long) data.length;
		long cols = (long) data[0].length;
		DefaultDenseDoubleMatrix2D mat = new DefaultDenseDoubleMatrix2D(rows, cols);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				mat.setDouble(data[i][j], i, j);
			}
		}
		return mat;
	}

	public static void compare(Matrix a, Matrix b) {
		int rows = (int) a.getRowCount();
		int cols = (int) a.getColumnCount();
		for (int j = 0; j < cols; j++) {
			for (int i = 0; i < rows; i++) {
				if (a.getAsDouble(i, j) != b.getAsDouble(i, j)) {
					assertTrue(String.format("(i, j)=(%s, %s): expected [%s], was [%s]", i, j, a.getAsDouble(i, j), b.getAsDouble(
							i, j)), false);
				}

			}
		}
		if (b.getRowCount() > a.getRowCount() || b.getColumnCount() > a.getColumnCount()) {
			System.out.format("\n\tMatrix (2) has been padded.  (1)[%s, %s] , (2)[%s, %s] \n", rows, cols, b.getRowCount(), b
					.getColumnCount());
		}
	}

	public static Callable<BlockDenseDoubleMatrix2D> createBlockMultiplier(final BlockDenseDoubleMatrix2D a,
			final BlockDenseDoubleMatrix2D b) {
		Callable<BlockDenseDoubleMatrix2D> run = createMultiplier(a, b);
		Callable<BlockDenseDoubleMatrix2D> multiplyTimer = new TimerDecorator<BlockDenseDoubleMatrix2D>(a.getRowCount(), a
				.getColumnCount(), b.getColumnCount(), run);
		return multiplyTimer;
	}

	public static <M extends Matrix> Callable<M> createBlockMultiplier(final M a, final M b) {
		Callable<M> run = createMultiplier(a, b);
		Callable<M> multiplyTimer = new TimerDecorator<M>(a.getRowCount(), a.getColumnCount(), b.getColumnCount(), run);
		return multiplyTimer;
	}

	public static <M extends Matrix> Callable<M> createMultiplier(final M a, final M b) {
		final Callable<M> run = new Callable<M>() {
			public M call() throws Exception {
				return (M) a.mtimes(b);
			};
		};
		return run;
	}

	public static Callable<BlockDenseDoubleMatrix2D> createMultiplier(final BlockDenseDoubleMatrix2D a,
			final BlockDenseDoubleMatrix2D b) {
		final Callable<BlockDenseDoubleMatrix2D> run = new Callable<BlockDenseDoubleMatrix2D>() {
			public BlockDenseDoubleMatrix2D call() throws Exception {
				return a.mtimes(b);
			};
		};
		return run;
	}

	static final double[][] C_2x3_RESULT = new double[][]//
	{ { 30, 36 }, { 66, 81 } };

}

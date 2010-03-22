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

import static org.ujmp.core.util.VerifyUtil.verify;

import java.util.concurrent.Callable;

/**
 * Multiply blocks of A and B in the specified range(fromM->toM, fromN->toN,
 * fromK->toK), <br>
 * and add to matrix C.
 * <p>
 * <code>
 * C(fromM->toM, fromK->toK) += <br>&nbsp&nbsp&nbsp;&nbsp&nbsp&nbsp;
 * A(fromM->toM, fromN->toN) x B(fromN->toN, fromK->toK)
 * </code>
 * <p>
 * All blocks must be square blocks of the same size, with length of one side =
 * {@link #blockStripeSize}
 * 
 * @author Frode Carlsen
 */
class BlockMultiply implements Callable<Void> {

	/** Length of one side of a block of data. */
	private final int blockStripeSize;

	/** range of data in matrix to be processed by this instance. */
	private final int fromM, toM, fromN, toN, fromK, toK;

	/** Source matrices to be processed. */
	private final BlockDenseDoubleMatrix2D matrixA, matrixB, matrixC;

	/**
	 * Constructor taking the two matrices being multiplied, the target matrix C
	 * and the range of rows and columns to multiply.
	 * 
	 * @param a
	 *            - matrix A, size (M, N)
	 * @param b
	 *            - matrix B, size (N, K)
	 * @param c
	 *            - result matrix C, size (M, K)
	 * @param fromM
	 *            - start row M in matrix A
	 * @param toM
	 *            - end row M in A
	 * @param fromN
	 *            - start column N in A (or start row N in B)
	 * @param toN
	 *            - end row N
	 * @param fromK
	 *            - start column K in B
	 * @param toK
	 *            - end column K in B
	 */
	protected BlockMultiply(final BlockDenseDoubleMatrix2D a, final BlockDenseDoubleMatrix2D b,
			final BlockDenseDoubleMatrix2D c, final int fromM, final int toM, final int fromN,
			final int toN, final int fromK, final int toK) {
		super();

		verifyInput(a, b, c, fromM, toM, fromN, toN, fromK, toK);

		this.matrixA = a;
		this.matrixB = b;
		this.matrixC = c;
		this.fromM = fromM;
		this.toM = toM;
		this.fromN = fromN;
		this.toN = toN;
		this.fromK = fromK;
		this.toK = toK;

		this.blockStripeSize = a.layout.blockStripe;
	}

	public Void call() {
		multiply();
		return null;
	}

	/**
	 * Multiply blocks of two matrices A,B and add to C.
	 * <p>
	 * Blocks of Matrix B are transformed to column-major layout (if not
	 * already) to facilitate multiplication.<br>
	 * (If matrices have been created optimally, B should already be
	 * column-major)
	 */
	protected final void multiply() {
		final int step = blockStripeSize, blockSize = blockStripeSize * blockStripeSize;

		for (int m = fromM; m < toM; m += step) {

			for (int k = fromK; k < toK; k += step) {

				double[] cBlock = new double[blockSize];

				for (int n = fromN; n < toN; n += step) {

					// ensure a and b are in optimal block order before
					// multiplication
					double[] aBlock = matrixA.layout.toRowMajorBlock(matrixA, m, n);
					double[] bBlock = matrixB.layout.toColMajorBlock(matrixB, n, k);

					if (aBlock != null && bBlock != null) {
						multiplyAxB(aBlock, bBlock, cBlock, step);
					}
				}

				matrixC.addBlockData(m, k, cBlock);
			}
		}
	}

	/**
	 * Multiply row-major block (a) x column-major block (b), and add to block
	 * c.
	 * 
	 * @param a
	 *            - block from {@link #matrixA}
	 * @param b
	 *            - block from {@link #matrixB}
	 * @param c
	 *            - block from result matrix {@link #matrixC}
	 */
	private static void multiplyAxB(final double[] aBlock, final double[] bBlock,
			final double[] cBlock, final int step) {
		final int blockStripeMini = step % 3;
		final int blockStripeMaxi = step / 3;
		final int blockArea = step * step;

		for (int iL = 0; iL < blockArea; iL += step) {
			int rc = iL;

			for (int kL = 0; kL < blockArea; kL += step) {
				int ra = iL;
				int rb = kL;
				double sum = 0.0d;

				for (int jL = blockStripeMini; --jL >= 0;) {
					sum += aBlock[ra++] * bBlock[rb++];
				}

				// loop unrolling
				for (int jL = blockStripeMaxi; --jL >= 0;) {
					sum += aBlock[ra++] * bBlock[rb++] //
							+ aBlock[ra++] * bBlock[rb++] //
							+ aBlock[ra++] * bBlock[rb++];
				}

				cBlock[rc++] += sum;
			}
		}
	}

	private static void verifyInput(final BlockDenseDoubleMatrix2D a,
			final BlockDenseDoubleMatrix2D b, final BlockDenseDoubleMatrix2D c, final int fromM,
			final int toM, final int fromN, final int toN, final int fromK, final int toK) {
		verify(a != null, "a cannot be null");
		verify(b != null, "b cannot be null");
		verify(c != null, "c cannot be null");
		verify(fromM <= a.getPaddedRowCount() && fromM >= 0, "Invalid argument : fromM");
		verify(toM <= a.getPaddedRowCount() && toM >= fromM, "Invalid argument : fromM/toM");
		verify(fromN <= a.getPaddedColumnCount() && fromN >= 0, "Invalid argument : fromN");
		verify(toN <= a.getPaddedColumnCount() && toN >= fromN, "Invalid argument : fromN/toN");
		verify(fromK <= b.getPaddedColumnCount() && fromK >= 0, "Invalid argument : fromK");
		verify(toK <= b.getPaddedColumnCount() && toK >= fromK, "Invalid argument : fromK/toK");
		verify(a.getPaddedColumnCount() == b.getPaddedRowCount(),
				"Invalid argument : a.columns != b.rows");
		verify(a.getPaddedRowCount() == c.getPaddedRowCount(),
				"Invalid argument : a.rows != c.rows");
		verify(b.getPaddedColumnCount() == c.getPaddedColumnCount(),
				"Invalid argument : b.columns != c.columns");
	}

}
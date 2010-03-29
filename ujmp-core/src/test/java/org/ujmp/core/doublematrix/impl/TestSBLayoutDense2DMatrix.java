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

import java.util.Arrays;

import junit.framework.TestCase;

import org.junit.Test;
import org.ujmp.core.doublematrix.impl.BlockMatrixLayout.BlockOrder;

@SuppressWarnings("unused")
public class TestSBLayoutDense2DMatrix extends TestCase {

	private static final int[][] LAYOUT_2x2 = new int[][]//
	{ { 0, 2 }, { 1, 3 } };
	private static final int[][] LAYOUT_2x3 = new int[][]//
	{ { 0, 3, 4 }, { 1, 2, 5 } };
	private static final int[][] LAYOUT_3x2 = new int[][]//
	{ { 0, 3 }, { 1, 4 }, { 2, 5 } };
	private static final int[][] LAYOUT_3x3 = new int[][]//
	{ { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 } };
	private static final int[][] LAYOUT_5x5 = new int[][]//
	{ { 0, 5, 10, 15, 20 } //
			, { 1, 6, 11, 16, 21 } //
			, { 2, 7, 12, 17, 22 } //
			, { 3, 8, 13, 18, 23 } //
			, { 4, 9, 14, 19, 24 } //
	};

	private void assertEqualsBlockLayout(BlockMatrixLayout blockLayout, int[][] arrLayout) {
		if (arrLayout == null || blockLayout == null) {
			throw new AssertionError("blockLayout and arrLayout are different");
		}
		int aRows = arrLayout.length, aCols = arrLayout[0].length;
		for (int i = 0; i < aRows; i++) {
			for (int j = 0; j < aCols; j++) {
				assertEquals(String.format("(i, j) = (%s, %s) : ", i, j), arrLayout[i][j],
						blockLayout.getBlockIndexByColumn(i, j, aRows, aCols));
			}
		}
	}

	private BlockMatrixLayout getBlockLayout(int blockSide, int rows, int cols) {
		return new BlockMatrixLayout(rows, cols, blockSide, BlockOrder.ROWMAJOR);
	}

	/**
	 * does not work?
	 */
	// public void testThatBlockIndexOfRectangular2x3BlockGivesCorrectIndex()
	// throws Exception {
	// int blockSide = 2, rows = 20, cols = 15;
	// BlockMatrixLayout layout = getBlockLayout(blockSide, rows, cols);
	// assertEqualsBlockLayout(getBlockLayout(blockSide, rows, cols),
	// LAYOUT_2x3);
	// }

	@Test
	public void testThatCreateLargeBlockSucceeds() throws Exception {
		int blockStripe = 10000;
		BlockMatrixLayout layout = new BlockMatrixLayout(10000, 10000, blockStripe,
				BlockOrder.ROWMAJOR);
		int index = layout.getBlockIndexByRow(9998, 9999, blockStripe, blockStripe);

		assertEquals(99989999, index);

		System.out.println("\n\nindex: blocksize 10000^2:" + index);
	}

	@Test
	public void testThatLayout3x2RectangularBlockGivesCorrectLayout() throws Exception {
		int blockSide = 3, rows = 3, cols = 2;
		assertEqualsBlockLayout(getBlockLayout(blockSide, rows, cols), LAYOUT_3x2);
	}

	@Test
	public void testThatLayout3x3BlockGivesCorrectLayout() throws Exception {
		int blockSide = 3, rows = 3, cols = 3;
		assertEqualsBlockLayout(getBlockLayout(blockSide, rows, cols), LAYOUT_3x3);
	}

	@Test
	public void testThatLayout5x5BlockGivesCorrectLayout() throws Exception {
		int blockSide = 5, rows = 5, cols = 5;
		assertEqualsBlockLayout(getBlockLayout(blockSide, rows, cols), LAYOUT_5x5);
	}

	@Test
	public void testThatLayoutMatrixWith2x2BlockGivesCorrectLayout() throws Exception {
		int blockSide = 2, rows = 2, cols = 2;
		assertEqualsBlockLayout(getBlockLayout(blockSide, rows, cols), LAYOUT_2x2);
	}

	private static final double[] rowMajor3x5D1 = new double[] { 1, 2, 3, 4, 5,//
			6, 7, 8, 9, 10,//
			11, 12, 13, 14, 15 };

	@Test
	public void testThatGetIndexInBlockReturnsCorrectResult() throws Exception {
		double[] a = rowMajor3x5D1;
		BlockMatrixLayout bml = new BlockMatrixLayout(3, 5, 3, BlockOrder.ROWMAJOR);

		// test entries in square block
		assertEquals(0, bml.getIndexInBlock(0, 0));
		assertEquals(1, bml.getIndexInBlock(0, 1));
		assertEquals(2, bml.getIndexInBlock(0, 2));
		assertEquals(3, bml.getIndexInBlock(1, 0));
		assertEquals(4, bml.getIndexInBlock(1, 1));
		assertEquals(5, bml.getIndexInBlock(1, 2));

		assertEquals(6, bml.getIndexInBlock(2, 0));
		assertEquals(7, bml.getIndexInBlock(2, 1));
		assertEquals(8, bml.getIndexInBlock(2, 2));

		// test entries in remainder of block
		assertEquals(0, bml.getIndexInBlock(0, 3));
		assertEquals(1, bml.getIndexInBlock(0, 4));
		assertEquals(2, bml.getIndexInBlock(1, 3));
		assertEquals(3, bml.getIndexInBlock(1, 4));
		assertEquals(4, bml.getIndexInBlock(2, 3));
		assertEquals(5, bml.getIndexInBlock(2, 4));

		System.out.println(Arrays.toString(a));
	}

	@Test
	public void testThatGetBlockNumberReturnsCorrectResult() throws Exception {
		double[] a = rowMajor3x5D1;
		BlockMatrixLayout bml = new BlockMatrixLayout(3, 5, 3, BlockOrder.ROWMAJOR);

		// test entries in square block
		assertEquals(0, bml.getBlockNumber(0, 0));
		assertEquals(0, bml.getBlockNumber(0, 1));
		assertEquals(0, bml.getBlockNumber(0, 2));
		assertEquals(0, bml.getBlockNumber(1, 0));
		assertEquals(0, bml.getBlockNumber(1, 1));
		assertEquals(0, bml.getBlockNumber(1, 2));

		assertEquals(0, bml.getBlockNumber(2, 0));
		assertEquals(0, bml.getBlockNumber(2, 1));
		assertEquals(0, bml.getBlockNumber(2, 2));

		// test entries in remainder of block
		assertEquals(1, bml.getBlockNumber(0, 3));
		assertEquals(1, bml.getBlockNumber(0, 4));
		assertEquals(1, bml.getBlockNumber(1, 3));
		assertEquals(1, bml.getBlockNumber(1, 4));
		assertEquals(1, bml.getBlockNumber(2, 3));
		assertEquals(1, bml.getBlockNumber(2, 4));

		System.out.println(Arrays.toString(a));
	}

}

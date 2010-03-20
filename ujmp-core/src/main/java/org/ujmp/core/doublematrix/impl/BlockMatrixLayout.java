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

import java.io.Serializable;
import java.util.Arrays;

/**
 * This class describes the layout (size, order) of a square block of data
 * within a {@link BlockDenseDoubleMatrix2D block matrix}.
 * 
 * @author Frode Carlsen
 */
public final class BlockMatrixLayout implements Serializable {
	private static final long serialVersionUID = -8859436115773664956L;

	/** Total size of a block (area). */
	final int blockArea;

	/** Length of one side (stripe) of a square block. */
	final int blockStripe;

	/** Number of columns of matrix. */
	final int columns;

	/**
	 * Whether this block is laid out in row-major (true) or column-major
	 * (false) order.
	 */
	final boolean rowMajor;

	/** Number of rows of matrix. */
	final int rows;

	BlockMatrixLayout(final int rows, final int columns, final int blockStripe,
			final BlockOrder blockOrder) {

		this.blockStripe = Math.min(rows, blockStripe);

		if (rows <= 0 || columns <= 0 || blockStripe <= 0) {
			throw new IllegalArgumentException(String.format(
					"One or more invalid values: rows=%s, columns=%s, blockSize=%s", rows, columns,
					blockStripe));
		}

		this.blockArea = blockStripe * blockStripe;
		this.rows = rows;
		this.columns = columns;
		this.rowMajor = (BlockOrder.ROWMAJOR == blockOrder);
	}

	/**
	 * Get the block which contains the specified row, column
	 * 
	 * @param matrix
	 *            to get block from
	 * @param row
	 * @param column
	 * @return block containing given row, column
	 */
	final double[] getBlock(BlockDenseDoubleMatrix2D matrix, int row, int column) {
		return matrix.getBlockData(row, column);
	}

	final int getBlockIndexByColumn(final int lrow, final int lcol) {
		return rowMajor ? (lcol * blockStripe + lrow) : (lrow * blockStripe + lcol);
	}

	final int getBlockIndexByRow(final int lrow, final int lcol) {
		return rowMajor ? (lrow * blockStripe + lcol) : (lcol * blockStripe + lrow);
	}

	final int getBlockNumber(int row, int col) {
		return (col / blockStripe) + (row / blockStripe) * (columns / blockStripe);
	}

	final int getIndexInBlock(int row, int col) {
		return getBlockIndexByRow(row % blockStripe, col % blockStripe);
	}

	final double[] toColMajorBlock(BlockDenseDoubleMatrix2D matrix, final int rowStart, int colStart) {
		double[] block = getBlock(matrix, rowStart, colStart);
		if (!rowMajor) {
			return block;
		}

		double[] targetBlock = new double[blockArea];
		// transpose block
		for (int i = 0; i < blockStripe; i++) {
			for (int j = 0; j < blockStripe; j++) {
				int tij = getBlockIndexByRow(i, j);
				int bij = getBlockIndexByColumn(i, j);
				targetBlock[tij] = block[bij];
			}
		}
		return targetBlock;
	}

	final double[] toRowMajorBlock(final BlockDenseDoubleMatrix2D matrix, final int rowStart,
			int colStart) {
		double[] block = getBlock(matrix, rowStart, colStart);
		if (rowMajor) {
			return block;
		}

		double[] targetBlock = new double[blockArea];
		// transpose block
		for (int i = 0; i < blockStripe; i++) {
			for (int j = 0; j < blockStripe; j++) {
				int bij = getBlockIndexByRow(i, j);
				int tij = getBlockIndexByColumn(i, j);
				targetBlock[tij] = block[bij];
			}
		}
		return targetBlock;
	}

	@Override
	public String toString() {
		int[] rowLayout = new int[blockStripe];
		StringBuilder b = new StringBuilder(blockArea * 4 + 40);
		String msg = "\n(rows=%s, columns=%s, blockSize=%s):\n";
		b.append(String.format(msg, rows, columns, blockStripe));

		for (int i = 0; i < blockStripe; i++) {
			for (int j = 0; j < blockStripe; j++) {
				rowLayout[j] = getBlockIndexByRow(i, j);
			}
			b.append(Arrays.toString(rowLayout)).append("\n");
		}

		return b.toString();
	}
}

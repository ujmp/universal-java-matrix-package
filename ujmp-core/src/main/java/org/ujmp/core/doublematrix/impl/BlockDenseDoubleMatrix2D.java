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
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicStampedReference;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.objectmatrix.calculation.Transpose;
import org.ujmp.core.util.concurrent.UJMPThreadPoolExecutor;

/**
 * A dense 2D matrix with square block layout. The data in the matrix is
 * re-organised as a block, tiled layout, which on modern CPUs with multiple
 * caches reduces the number of cache misses, by providing better cache locality
 * and cache temporality.
 * 
 * <h4>Block Layout (example)</h4>
 * <p>
 * Example: a 4x4 matrix with block size 2x2 is internally re-organised into 4
 * separate blocks :
 * <p>
 * <code>
 * |(0,0) , (0, 1) | (0, 2) , (0, 3) |<br>
 * |(1,0) , (1, 1) | (1, 2) , (1, 3) |<br>
 * |----------------------------------<br>
 * |(2,0) , (2, 1) | (2, 2) , (2, 3) |<br>
 * |(3,0) , (3, 1) | (3, 2) , (3, 3) |<br>
 * </code>
 * <p>
 * This can be described as a matrix of the following blocks: <br>
 * <p>
 * <code>
 * | A | B |<br>
 * | C | D |<br>
 * </code>
 * <p>
 * Each block is mapped to a separate one-dimensional array. For example block
 * A:
 * <p>
 * <code>
 * [ (0,0), (1,0), (0, 1), (1, 1)]
 * </code>
 * <p>
 * <p>
 * This layout is similar to what's described in [ref: II fig. 4b].
 * 
 * <h4>Choice of block size:</h4>
 * <p>
 * The blocks may be square and can be configured by the user at runtime.
 * However, matrix multiplication performance will be sensitive to the choice of
 * block size, depending on the amount of CPU cache available on the system. In
 * addition, should the given matrix not exactly fit within a multiple of the
 * block size, then the matrix will be padded out with zeros. Thus, if the block
 * size is chosen poorly, additional effort will be incurred.
 * <p>
 * As a starting point, a square block with a stripe size of 200 may be a
 * reasonable choice.
 * <p>
 * 
 * @see I. 'Efficient Matrix Multiplication Using Cache Conscious Data Layouts';
 *      Neungsoo Park, Wenheng Liuy, Viktor K. Prasanna, Cauligi Raghavendra;
 *      Department of Electrical Engineering{Systems), University of Southern
 *      California
 * @see II. 'Tiling, Block Data Layout, and Memory Hierarchy Performance';
 *      Neungsoo Park, Bo Hong, and Viktor K. Prasanna
 * 
 * @author Frode Carlsen
 */
public class BlockDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D {

	/**
	 * Creates a cached Executor for default use (if user hasn't specified
	 * anything else. The number of threads varies in line with the number of
	 * available processors to the Java VM (which may be changing at runtime).
	 */
	private static class DefaultExecutorReference {
		private static final ThreadFactory DAEMON_THREAD_FACTORY = new ThreadFactory() {
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setDaemon(true);
				return t;
			}
		};
		private static final Object LOCKOBJ = new Object();

		/**
		 * Reference default executor. Number of processors available to java
		 * may change during runtime, so keep the stamp as a reminder of the
		 * number of processors the executor was created with, and re-create if
		 * this changes.
		 */
		private final AtomicStampedReference<ExecutorService> defaultExecutorService = getExecutorReference();

		private AtomicStampedReference<ExecutorService> getExecutorReference() {
			int processors = Runtime.getRuntime().availableProcessors();
			int currentExecutorProcessors = 0;
			if (null != defaultExecutorService) {
				currentExecutorProcessors = defaultExecutorService.getStamp();
				if (processors == currentExecutorProcessors) {
					return defaultExecutorService;
				}
			}
			synchronized (LOCKOBJ) {
				return new AtomicStampedReference<ExecutorService>(Executors.newFixedThreadPool(
						processors, DAEMON_THREAD_FACTORY), processors);
			}
		}

		ExecutorService getExecutorService() {
			return getExecutorReference().getReference();
		}

	}

	/** default executor for multi-threaded multiplication. */
	private static final DefaultExecutorReference defaultExecutorReference = new DefaultExecutorReference();

	private static final long serialVersionUID = -5131649082019624021L;

	private static int deriveDefaultBlockStripeSize(int rows, int cols) {
		// TODO pick a suitable size
		return Math.min(150, Math.min(rows, cols));
	}

	/** Pad out the given dimension to fit with the blockStripeSize. */
	private static int pad(final int i, final int blockStripeSize) {
		return (i % blockStripeSize) > 0.0d ? (i / blockStripeSize) * blockStripeSize
				+ blockStripeSize : i;
	}

	/**
	 * Adjust the targetSize to better fit as a multiple of the dimension.
	 * 
	 * @param dim
	 *            - the given dimension size (number of rows, or number of
	 *            columns)
	 * @param targetSize
	 *            - the target block stripe size to fit.
	 * @return new adjusted targetSize to be used instead, to reduce need for
	 *         padding the matrix.
	 */
	public static int padBlockStripeSize(final int dim, final int targetSize) {
		verify(dim > 0, "dim<=0");
		verify(targetSize > 0, "targetSize<=0");

		int newSize;
		int fit = dim / targetSize; // does dim fit to targetSize
		int remain = dim % targetSize; // remainder after fitting

		if (fit == 0) {
			return dim;
		} else if (remain == 0) {
			return targetSize;
		}

		// add or subtract from target size:
		int adjCoeff = 1;
		double remainPct = ((double) remain) / ((double) targetSize);
		if (remainPct > 0.5) {
			adjCoeff = -1;
			remain = (targetSize - remain);
			fit += 1; // increase with one block;
		}

		newSize = targetSize + adjCoeff * (remain / fit + (remain % fit) > 0 ? 1 : 0);

		return newSize;

	}

	/**
	 * Derive the best fit block stripe size based on the given dimensions of
	 * two matrices mxn and nxk.
	 * 
	 * @param m
	 *            - rows of first matrix
	 * @param n
	 *            - columns of first matrix and rows of second matrix
	 * @param k
	 *            - columns of second matrix
	 * @param targetSize
	 *            - target block size
	 * @param x
	 * @return best fit block stripe size for the 2 matrices with specified
	 *         dimensions.
	 */
	public static int selectBlockStripeSize(int m, int n, int k) {
		final int targetBlockSize = Math.min(Math.min(Math.min(m, k), n), 200);
		// naive
		int m1 = padBlockStripeSize(m, targetBlockSize);
		int n1 = padBlockStripeSize(n, targetBlockSize);
		int k1 = padBlockStripeSize(k, targetBlockSize);
		int blSize = Math.min(Math.min(m1, k1), n1);
		return blSize;
	}

	private static void verify(boolean test, String message, Object... messageArgs) {
		if (!test) {
			String text = (messageArgs == null || messageArgs.length == 0) ? message : String
					.format(message, messageArgs);
			throw new IllegalArgumentException(text);
		}
	}

	/** Matrix data by block number. */
	double[][] data;

	/** Layout of matrix and blocks. */
	BlockMatrixLayout layout;

	/** Dimensions of the matrix. */
	private final long[] size;

	/** Dimensions of the matrix with padding. */
	private final int paddedRows;
	private final int paddedCols;

	public BlockDenseDoubleMatrix2D(final double[][] values) {
		this(values.length, values[0].length, BlockOrder.ROWMAJOR);
		fill(values);
	}

	/**
	 * Create a block matrix from a jagged array. <br>
	 * All rows of the values array must have the same length.
	 * 
	 * @param values
	 *            - the data to populate the matrix with.
	 * @param blockStripeSize
	 *            - length of one side of a block
	 * @param blockOrder
	 *            - see {@link BlockOrder}.
	 * @throws NullPointerException
	 *             if values is null, or values[0] is null.
	 * @throws ArrayIndexOutOfBoundsException
	 *             if any row is shorter than the first row.
	 */
	public BlockDenseDoubleMatrix2D(final double[][] values, final int blockStripeSize,
			final BlockOrder blockOrder) {
		this(values.length, values[0].length, blockStripeSize, blockOrder);
		fill(values);
	}

	/**
	 * Create a new matrix with the specified size, and specified block stripe
	 * size.
	 * 
	 * @param rows
	 *            - number of rows of the matrix.
	 * @param cols
	 *            - number of columns of the matrix.
	 * @param blockStripeSize
	 *            - length of one side of a square block.
	 */
	public BlockDenseDoubleMatrix2D(final int rows, final int cols, final int blockStripeSize,
			final BlockOrder blockOrder) {
		verify(rows > 0, "rows<=0");
		verify(cols > 0, "cols<=0");
		verify(blockStripeSize > 0, "blockStripeSize<=0");
		verify(blockOrder != null, "blockOrder == null");

		// now pad matrix size
		paddedRows = pad(rows, blockStripeSize);
		paddedCols = pad(cols, blockStripeSize);

		verify(paddedRows > 0, "dimRows<=0");
		verify(paddedCols > 0, "dimCols<=0");

		this.size = new long[] { rows, cols };

		// layout structure for the blocks in the matrix
		this.layout = new BlockMatrixLayout(paddedRows, paddedCols, blockStripeSize, blockOrder);

		// pad out matrix size to match block size
		// TODO try eliminate padding??

		int numberOfBlocks = paddedRows / blockStripeSize * paddedCols / blockStripeSize;
		this.data = new double[numberOfBlocks][];
	}

	/**
	 * Create a new matrix with the given size (rows, cols) and block layout.
	 * 
	 * @see #BlockMatrix(int, int)
	 */
	public BlockDenseDoubleMatrix2D(final long rows, final long cols, final BlockOrder blockOrder) {
		this((int) rows, (int) cols, deriveDefaultBlockStripeSize((int) rows, (int) cols),
				blockOrder);
	}

	/**
	 * Create a new matrix with the given size (rows, cols) and default block
	 * layout.
	 */
	public BlockDenseDoubleMatrix2D(final long... size) {
		this(size[ROW], size[COLUMN], BlockOrder.ROWMAJOR);
	}

	protected final int getPaddedColumnCount() {
		return paddedCols;
	}

	protected final int getPaddedRowCount() {
		return paddedRows;
	}

	/**
	 * Constructor which takes an existing Matrix to copy data and structure
	 * from. <br>
	 * Block stripe size will be defaulted internally.
	 * 
	 * @param m
	 *            - matrix to copy data from.
	 */
	public BlockDenseDoubleMatrix2D(Matrix m) {
		this(m, deriveDefaultBlockStripeSize((int) m.getRowCount(), (int) m.getColumnCount()));
	}

	/**
	 * Constructor which takes a Matrix and a proposed default block stripe
	 * size.
	 * 
	 * @param m
	 *            - matrix containing existing values.
	 * @param blockStripeSize
	 *            - proposed default block size.
	 */
	public BlockDenseDoubleMatrix2D(Matrix m, int blockStripeSize) {
		this((int) m.getRowCount(), (int) m.getColumnCount(), blockStripeSize, BlockOrder.ROWMAJOR);

		if (m instanceof DefaultDenseDoubleMatrix2D) {
			DefaultDenseDoubleMatrix2D mDense = (DefaultDenseDoubleMatrix2D) m;
			int mRows = (int) mDense.getRowCount(), mColumns = (int) mDense.getColumnCount();
			for (int i = 0; i < mRows; i++) {
				for (int j = 0; j < mColumns; j++) {
					setDouble(mDense.getDouble(i, j), i, j);
				}
			}
		} else {
			for (long[] c : m.allCoordinates()) {
				setDouble(m.getAsDouble(c), c);
			}
		}
	}

	void addBlockData(int row, int column, double[] newData) {
		int blockNumber = layout.getBlockNumber(row, column);

		if (null == data[blockNumber]) {
			// init first block
			synchronized (data) {
				if (null == data[blockNumber]) {
					data[blockNumber] = newData;
					return;
				}
			}
		}

		double[] block = data[blockNumber];
		synchronized (block) {
			for (int i = block.length; --i >= 0;) {
				block[i] += newData[i];
			}
		}

	}

	/**
	 * Submit a number of tasks and wait for completion.
	 * 
	 * @param tasks
	 *            - to submit to executor
	 * @param executor
	 *            - to use
	 */
	private void executeWait(final List<Callable<Void>> tasks, final ExecutorService executor) {
		try {
			for (Future<Void> f : executor.invokeAll(tasks)) {
				f.get();
			}
		} catch (ExecutionException e) {
			String msg = "Execution exception - while awaiting completion of matrix multiplication.";
			throw new RuntimeException(msg, e);
		} catch (final InterruptedException e) {
			String msg = "Interrupted - while awaiting completion of matrix multiplication.";
			throw new RuntimeException(msg, e);
		}
	}

	/**
	 * @see #fill(double[][], int, int)
	 */
	public void fill(final double[][] data) {
		fill(data, 0, 0);
	}

	/**
	 * Populate matrix with given data.
	 * 
	 * @param data
	 *            - to fill into matrix
	 * @param startRow
	 *            - row to start filling in data at
	 * @param startCol
	 *            - col to start at
	 */
	public void fill(final double[][] data, final int startRow, final int startCol) {
		int rows = data.length;
		int cols = data[0].length;

		verify(startRow < rows && startRow < getRowCount(), "illegal startRow: %s", startRow);
		verify(startCol < cols && startCol < getColumnCount(), "illegal startCol: %s", startCol);
		verify(rows <= getRowCount(), "too many rows in input: %s: max allowed = %s", rows,
				getRowCount());
		verify(cols <= getColumnCount(), "too many columns in input: %s: max allowed = %s", cols,
				getColumnCount());

		for (int i = startRow; i < rows; i++) {
			for (int j = startCol; j < cols; j++) {
				setDouble(data[i][j], i, j);
			}
		}
	}

	/**
	 * Get block holding the specified row and column. If none exist, create
	 * one.
	 * 
	 * @param row
	 *            - in matrix
	 * @param column
	 *            - in matrix
	 * @return double[] block where the given row,column is held.
	 */
	double[] getBlockData(int row, int column) {
		int blockNumber = layout.getBlockNumber(row, column);
		double[] block = data[blockNumber];

		if (null == block) {
			block = new double[layout.blockArea];
			data[blockNumber] = block;
		}
		return block;
	}

	/**
	 * @return {@link BlockMatrixLayout} of this matrix.
	 */
	BlockMatrixLayout getBlockLayout() {
		return this.layout;
	}

	/** @return blockSize of this matrix. */
	public int getBlockStripeSize() {
		return layout.blockStripe;
	}

	public double getDouble(int row, int col) {
		double[] block = data[layout.getBlockNumber(row, col)];
		if (null == block) {
			return 0.0d;
		}
		return block[layout.getIndexInBlock(row, col)];
	}

	public double getDouble(long row, long column) {
		return this.getDouble((int) row, (int) column);
	}

	public long[] getSize() {
		return this.size;
	}

	/**
	 * Multiply two BlockMatrices
	 * 
	 * @param b
	 *            - rhs of the multiplication
	 * @return c - a new matrix
	 */
	public BlockDenseDoubleMatrix2D mtimes(final BlockDenseDoubleMatrix2D b) {
		if (2 * this.layout.rows * b.layout.rows * b.layout.columns < 100000) {
			return multiplySingleThread(b);
		}
		return mtimes(b, defaultExecutorReference.getExecutorService());
	}

	/**
	 * Multiply two matrices concurrently with the given Executor to handle
	 * parallell tasks.
	 * 
	 * @param b
	 *            - matrix to multiply this with.
	 * @param executorService
	 *            - to handle concurrent multiplication tasks.
	 * @return new matrix C containing result of matrix multiplication C = A x
	 *         B.
	 */
	public BlockDenseDoubleMatrix2D mtimes(final BlockDenseDoubleMatrix2D b,
			final ExecutorService executorService) {
		verify(b);

		final BlockDenseDoubleMatrix2D a = this, c = newMatrix(this.layout.rows, b.layout.columns);
		final List<Callable<Void>> tasks = new LinkedList<Callable<Void>>();

		final int kMax = (int) b.getColumnCount();
		final int jMax = (int) a.getColumnCount();
		final int iMax = (int) a.getRowCount();

		final int bColSlice = Math.min(layout.blockStripe, kMax);
		final int aColSlice = Math.min(layout.blockStripe, jMax);
		final int aRowSlice = Math.min(layout.blockStripe, iMax);

		// Number of blocks to take for each concurrent task.
		final int blocksPerTask = 2;

		for (int k = 0, kStride; k < kMax; k += kStride) {
			kStride = Math.min(blocksPerTask * bColSlice, kMax - k);

			for (int j = 0, jStride; j < jMax; j += jStride) {
				jStride = Math.min(blocksPerTask * aColSlice, jMax - j);

				for (int i = 0, iStride; i < iMax; i += iStride) {
					iStride = Math.min(blocksPerTask * aRowSlice, iMax - i);

					tasks.add(new BlockMultiply(a, b, c, i, (i + iStride), j, (j + jStride), k,
							(k + kStride)));
				}

			}
		}

		// wait for all tasks to complete.
		executeWait(tasks, executorService);

		return c;
	}

	private BlockDenseDoubleMatrix2D multiplySingleThread(final BlockDenseDoubleMatrix2D b) {

		verify(b);

		BlockDenseDoubleMatrix2D a = this;
		BlockDenseDoubleMatrix2D c = newMatrix(this.layout.rows, b.layout.columns);

		new BlockMultiply(a, b, c, 0, a.layout.rows, 0, a.layout.columns, 0, b.layout.columns)
				.multiply();

		return c;
	}

	protected BlockDenseDoubleMatrix2D newMatrix(final int rows, final int cols) {
		return new BlockDenseDoubleMatrix2D(rows, cols, layout.blockStripe, BlockOrder.ROWMAJOR);
	}

	public void setDouble(double value, int row, int column) {
		double[] block = getBlockData(row, column);
		block[layout.getIndexInBlock(row, column)] = value;
	}

	public void setDouble(double value, long row, long column) {
		setDouble(value, (int) row, (int) column);
	}

	/**
	 * Returns the transpose of the current matrix.
	 * 
	 * @return transpose of this matrix.
	 */
	@Override
	public Matrix transpose() throws MatrixException {
		return transpose(Ret.NEW);
	}

	@Override
	public Matrix transpose(final Ret returnType) {
		// swap rows, column dimensions and block order
		final BlockOrder transOrder = (layout.rowMajor ? BlockOrder.COLUMNMAJOR
				: BlockOrder.ROWMAJOR);
		final int step = layout.blockStripe;
		final BlockDenseDoubleMatrix2D transMat = new BlockDenseDoubleMatrix2D(
				(int) getColumnCount(), (int) getRowCount(), step, transOrder);

		for (int i = 0; i < layout.rows; i += step) {
			for (int j = 0; j < layout.columns; j += step) {
				// shuffle blocks to new position
				int blockNumberA = layout.getBlockNumber(i, j);
				double[] block = this.data[blockNumberA];

				if (returnType == Ret.NEW && null != block) {
					block = Arrays.copyOf(block, block.length);
				}

				int blockNumberB = transMat.layout.getBlockNumber(j, i);
				transMat.data[blockNumberB] = block;
			}
		}

		if (returnType == Ret.ORIG) {
			this.layout = transMat.layout;
			this.data = transMat.data;
			System.arraycopy(transMat.size, 0, this.size, 0, transMat.size.length);
			if (getAnnotation() != null) {
				setAnnotation(Transpose.transposeAnnotation(getAnnotation(), Coordinates
						.transpose(getSize())));
			}
			return this;
		} else {
			if (getAnnotation() != null) {
				transMat.setAnnotation(Transpose.transposeAnnotation(getAnnotation(), Coordinates
						.transpose(getSize())));
			}
			return transMat;
		}
	}

	private void verify(final BlockDenseDoubleMatrix2D b) {
		BlockMatrixLayout al = this.layout, bl = b.layout;
		verify(al.columns == bl.rows, "b.rows != this.columns");
		verify(al.blockStripe == bl.blockStripe, "block sizes differ: %s != %s", al.blockStripe,
				bl.blockStripe);
	}

}

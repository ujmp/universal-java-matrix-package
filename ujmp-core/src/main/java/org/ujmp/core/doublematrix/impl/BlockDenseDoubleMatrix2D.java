/*
 * Copyright (C) 2010 by Frode Carlsen, Holger Arndt
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

import java.util.Arrays;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.calculation.Mtimes;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.BlockMatrixLayout.BlockOrder;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasBlockDoubleArray2D;
import org.ujmp.core.objectmatrix.calculation.Transpose;
import org.ujmp.core.util.concurrent.PFor;

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
 * block size, depending on the amount of CPU cache available on the system.
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
 * @author Holger Arndt
 */
public class BlockDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D implements
		HasBlockDoubleArray2D {

	private static final long serialVersionUID = -5131649082019624021L;

	public static int DEFAULT_BLOCK_SIZE = 100;

	private static int deriveDefaultBlockStripeSize(int rows, int cols) {
		// TODO pick a suitable size
		return (rows < DEFAULT_BLOCK_SIZE && cols < DEFAULT_BLOCK_SIZE) ? 50 : DEFAULT_BLOCK_SIZE;
	}

	/** Matrix data by block number. */
	private double[][] data;

	/** Layout of matrix and blocks. */
	protected BlockMatrixLayout layout;

	/** Dimensions of the matrix. */
	private final long[] size;

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
	 * @throws IllegalArgumentException
	 *             if rows, cols or blockStripeSize are 0 or less, or blockOrder
	 *             is null.
	 */
	public BlockDenseDoubleMatrix2D(final int rows, final int cols, final int blockStripeSize,
			final BlockOrder blockOrder) {
		verify(rows > 0, "rows<=0");
		verify(cols > 0, "cols<=0");
		verify(blockStripeSize > 0, "blockStripeSize<=0");
		verify(blockOrder != null, "blockOrder == null");

		this.size = new long[] { rows, cols };

		// layout structure for the blocks in the matrix
		this.layout = new BlockMatrixLayout(rows, cols, blockStripeSize, blockOrder);

		this.data = new double[this.layout.numberOfBlocks][];
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
	 * Create a new matrix with the specified size, and specified block stripe
	 * size.
	 * 
	 * @param rows
	 *            - number of rows of the matrix.
	 * @param cols
	 *            - number of columns of the matrix.
	 * @throws IllegalArgumentException
	 *             if rows, cols are 0 or less.
	 */
	public BlockDenseDoubleMatrix2D(final int rows, final int cols) {
		this(rows, cols, deriveDefaultBlockStripeSize(rows, cols), BlockOrder.ROWMAJOR);
	}

	/**
	 * Create a new matrix with the given size (rows, cols) and default block
	 * layout.
	 */
	public BlockDenseDoubleMatrix2D(final long... size) {
		this(size[ROW], size[COLUMN], BlockOrder.ROWMAJOR);
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
	 * Constructor which takes an existing BlockMatrix to copy data and
	 * structure from. <br>
	 */
	public BlockDenseDoubleMatrix2D(final BlockDenseDoubleMatrix2D m) {
		this((int) m.size[ROW], (int) m.size[COLUMN], m.layout.blockStripe, m.layout.blockOrder);
		for (int i = m.layout.numberOfBlocks; --i != -1;) {
			final double[] block = m.data[i];
			if (block != null) {
				// cannot use Arrays.copyOf(): not supported in Java 5
				this.data[i] = new double[block.length];
				System.arraycopy(block, 0, this.data[i], 0, block.length);
			}
		}
		Annotation a = m.getAnnotation();
		if (a != null) {
			setAnnotation(a.clone());
		}
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
		this(m, blockStripeSize, BlockOrder.ROWMAJOR);
	}

	/**
	 * Constructor which takes a Matrix and a proposed default block stripe
	 * size.
	 * 
	 * @param m
	 *            - matrix containing existing values.
	 * @param blockStripeSize
	 *            - proposed default block size.
	 * @param blockOrder
	 *            row major or column major
	 */
	public BlockDenseDoubleMatrix2D(Matrix m, int blockStripeSize, BlockOrder blockOrder) {
		this((int) m.getRowCount(), (int) m.getColumnCount(), blockStripeSize, blockOrder);

		if (m instanceof DenseDoubleMatrix2D) {
			final DenseDoubleMatrix2D mDense = (DenseDoubleMatrix2D) m;
			final int mRows = (int) mDense.getRowCount(), mColumns = (int) mDense.getColumnCount();
			for (int j = 0; j < mColumns; j++) {
				for (int i = 0; i < mRows; i++) {
					setDouble(mDense.getDouble(i, j), i, j);
				}
			}
		} else {
			for (long[] c : m.availableCoordinates()) {
				setDouble(m.getAsDouble(c), c);
			}
		}
	}

	protected void addBlockData(final int row, final int column, final double[] newData) {
		int blockNumber = layout.getBlockNumber(row, column);

		if (null == data[blockNumber]) {
			// init first block
			synchronized (data) {
				data[blockNumber] = newData;
				return;
			}
		}

		final double[] block = data[blockNumber];
		synchronized (block) {
			for (int i = newData.length; --i >= 0;) {
				block[i] += newData[i];
			}
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
		final int rows = data.length;
		final int cols = data[0].length;

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
			block = new double[layout.getBlockSize(row, column)];
			data[blockNumber] = block;
		}
		return data[blockNumber];
	}

	/**
	 * @return {@link BlockMatrixLayout} of this matrix.
	 */
	public final BlockMatrixLayout getBlockLayout() {
		return this.layout;
	}

	/** @return blockSize of this matrix. */
	public final int getBlockStripeSize() {
		return layout.blockStripe;
	}

	public double getDouble(final int row, final int col) {
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
	 * Shortcut to create a BlockMatrix for target
	 */
	public Matrix mtimes(Matrix m2) {
		if (m2 instanceof DenseDoubleMatrix2D) {
			final DenseDoubleMatrix2D result = new BlockDenseDoubleMatrix2D((int) getRowCount(),
					(int) m2.getColumnCount(), layout.blockStripe, BlockOrder.ROWMAJOR);
			Mtimes.DENSEDOUBLEMATRIX2D.calc(this, (DenseDoubleMatrix2D) m2, result);
			return result;
		} else {
			return super.mtimes(m2);
		}
	}

	public void setDouble(double value, int row, int column) {
		final double[] block = getBlockData(row, column);
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
	public BlockDenseDoubleMatrix2D clone() {
		return new BlockDenseDoubleMatrix2D(this);
	}

	@Override
	public Matrix transpose(final Ret returnType) {
		// swap rows, column dimensions and block order
		final BlockOrder transOrder = (BlockOrder.ROWMAJOR == layout.blockOrder ? BlockOrder.COLUMNMAJOR
				: BlockOrder.ROWMAJOR);

		final int step = layout.blockStripe;
		final BlockDenseDoubleMatrix2D transMat = new BlockDenseDoubleMatrix2D(
				(int) getColumnCount(), (int) getRowCount(), step, transOrder);

		double[] block;

		for (int i = 0; i < layout.rows; i += step) {
			for (int j = 0; j < layout.columns; j += step) {
				// shuffle blocks to new position
				final int blockNumberA = layout.getBlockNumber(i, j);
				block = this.data[blockNumberA];

				if (returnType == Ret.NEW && null != block) {
					// cannot use Arrays.copyOf(): not supported in Java 5
					final double[] newBlock = new double[block.length];
					System.arraycopy(block, 0, newBlock, 0, block.length);
					block = newBlock;
				}

				final int blockNumberB = transMat.layout.getBlockNumber(j, i);
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
		} else if (returnType == Ret.LINK) {
			return super.transpose(Ret.LINK);
		} else {
			if (getAnnotation() != null) {
				transMat.setAnnotation(Transpose.transposeAnnotation(getAnnotation(), Coordinates
						.transpose(getSize())));
			}
			return transMat;
		}
	}

	public final double[][] getBlockDoubleArray2D() {
		return data;
	}

	public Matrix plus(final double value) {
		final BlockDenseDoubleMatrix2D result = new BlockDenseDoubleMatrix2D(this);
		if (result.data.length < 100) {
			for (int i = result.data.length; --i != -1;) {
				double[] block = result.data[i];
				if (block == null) {
					block = new double[layout.blockArea];
				}
				for (int j = block.length; --j != -1;) {
					block[j] += value;
				}
			}
		} else {
			new PFor(0, result.data.length) {
				public void step(int i) {
					double[] block = result.data[i];
					if (block == null) {
						block = new double[layout.blockArea];
					}
					for (int j = block.length; --j != -1;) {
						block[j] += value;
					}
				}
			};
		}
		return result;
	}

	public Matrix plus(Matrix value) {
		if (value instanceof BlockDenseDoubleMatrix2D) {
			final BlockDenseDoubleMatrix2D b = (BlockDenseDoubleMatrix2D) value;
			if (b.layout.rows == layout.rows && b.layout.columns == layout.columns
					&& b.layout.blockOrder == layout.blockOrder
					&& b.layout.blockStripe == layout.blockStripe) {
				final BlockDenseDoubleMatrix2D result = new BlockDenseDoubleMatrix2D(this);
				if (result.data.length < 100) {
					for (int i = result.data.length; --i != -1;) {
						final double[] block2 = b.data[i];
						if (block2 == null) {
							continue;
						}
						if (result.data[i] == null) {
							result.data[i] = new double[b.data[i].length];
						}
						final double[] block = result.data[i];
						for (int j = block.length; --j != -1;) {
							block[j] += block2[j];
						}
					}
				} else {
					new PFor(0, result.data.length - 1) {
						public void step(int i) {
							final double[] block2 = b.data[i];
							if (block2 == null) {
								return;
							}
							if (result.data[i] == null) {
								result.data[i] = new double[b.data[i].length];
							}
							final double[] block = result.data[i];
							for (int j = block.length; --j != -1;) {
								block[j] += block2[j];
							}
						}
					};
				}
				return result;
			}
		}
		return super.plus(value);
	}

	public Matrix minus(Matrix value) {
		if (value instanceof BlockDenseDoubleMatrix2D) {
			final BlockDenseDoubleMatrix2D b = (BlockDenseDoubleMatrix2D) value;
			if (b.layout.rows == layout.rows && b.layout.columns == layout.columns
					&& b.layout.blockOrder == layout.blockOrder
					&& b.layout.blockStripe == layout.blockStripe) {
				final BlockDenseDoubleMatrix2D result = new BlockDenseDoubleMatrix2D(this);
				if (result.data.length < 100) {
					for (int i = data.length; --i != -1;) {
						final double[] block2 = b.data[i];
						if (block2 == null) {
							continue;
						}
						if (result.data[i] == null) {
							result.data[i] = new double[b.data[i].length];
						}
						final double[] block = result.data[i];
						for (int j = block.length; --j != -1;) {
							block[j] -= block2[j];
						}
					}
				} else {
					new PFor(0, result.data.length - 1) {
						public void step(int i) {
							final double[] block2 = b.data[i];
							if (block2 == null) {
								return;
							}
							if (result.data[i] == null) {
								result.data[i] = new double[b.data[i].length];
							}
							final double[] block = result.data[i];
							for (int j = block.length; --j != -1;) {
								block[j] -= block2[j];
							}
						}
					};
				}
				return result;
			}
		}
		return super.minus(value);
	}

	public Matrix times(Matrix value) {
		if (value instanceof BlockDenseDoubleMatrix2D) {
			final BlockDenseDoubleMatrix2D b = (BlockDenseDoubleMatrix2D) value;
			if (b.layout.rows == layout.rows && b.layout.columns == layout.columns
					&& b.layout.blockOrder == layout.blockOrder
					&& b.layout.blockStripe == layout.blockStripe) {
				final BlockDenseDoubleMatrix2D result = new BlockDenseDoubleMatrix2D(this);
				if (result.data.length < 100) {
					for (int i = result.data.length; --i != -1;) {
						final double[] block2 = b.data[i];
						if (block2 == null) {
							// multiply with 0.0, clear block in result
							result.data[i] = null;
						} else {
							final double[] block = result.data[i];
							if (block == null) {
								// multiply with 0.0
								continue;
							}
							for (int j = block.length; --j != -1;) {
								block[j] *= block2[j];
							}
						}
					}
				} else {
					new PFor(0, result.data.length - 1) {
						public void step(int i) {
							final double[] block2 = b.data[i];
							if (block2 == null) {
								// multiply with 0.0, clear block in result
								result.data[i] = null;
							} else {
								final double[] block = result.data[i];
								if (block == null) {
									// multiply with 0.0
									return;
								}
								for (int j = block.length; --j != -1;) {
									block[j] *= block2[j];
								}
							}
						}
					};
				}
				return result;
			}
		}
		return super.times(value);
	}

	public Matrix divide(Matrix value) {
		if (value instanceof BlockDenseDoubleMatrix2D) {
			final BlockDenseDoubleMatrix2D b = (BlockDenseDoubleMatrix2D) value;
			if (b.layout.rows == layout.rows && b.layout.columns == layout.columns
					&& b.layout.blockOrder == layout.blockOrder
					&& b.layout.blockStripe == layout.blockStripe) {
				final BlockDenseDoubleMatrix2D result = new BlockDenseDoubleMatrix2D(this);
				if (result.data.length < 100) {
					for (int i = result.data.length; --i != -1;) {
						final double[] block2 = b.data[i];
						if (block2 == null) {
							// divide by 0.0, fill block with NaN
							if (result.data[i] == null) {
								result.data[i] = new double[layout.blockArea];
							}
							Arrays.fill(result.data[i], Double.NaN);
						} else {
							final double[] block = result.data[i];
							if (block == null) {
								// divide 0.0 by x = 0.0: nothing to do
								continue;
							}
							for (int j = block.length; --j != -1;) {
								block[j] /= block2[j];
							}
						}
					}
				} else {
					new PFor(0, result.data.length - 1) {
						public void step(int i) {
							final double[] block2 = b.data[i];
							if (block2 == null) {
								// divide by 0.0, fill block with NaN
								if (result.data[i] == null) {
									result.data[i] = new double[layout.blockArea];
								}
								Arrays.fill(result.data[i], Double.NaN);
							} else {
								final double[] block = result.data[i];
								if (block == null) {
									// divide 0.0 by x = 0.0: nothing to do
									return;
								}
								for (int j = block.length; --j != -1;) {
									block[j] /= block2[j];
								}
							}
						}
					};
				}

				return result;
			}
		}
		return super.times(value);
	}

	public Matrix minus(final double value) {
		final BlockDenseDoubleMatrix2D result = new BlockDenseDoubleMatrix2D(this);
		if (result.data.length < 100) {
			for (int i = result.data.length; --i != -1;) {
				double[] block = result.data[i];
				if (block == null) {
					block = new double[layout.blockArea];
				}
				for (int j = block.length; --j != -1;) {
					block[j] -= value;
				}
			}
		} else {
			new PFor(0, result.data.length - 1) {
				public void step(int i) {
					double[] block = result.data[i];
					if (block == null) {
						block = new double[layout.blockArea];
					}
					for (int j = block.length; --j != -1;) {
						block[j] -= value;
					}
				}
			};
		}
		return result;
	}

	public Matrix times(final double value) {
		final BlockDenseDoubleMatrix2D result = new BlockDenseDoubleMatrix2D(this);
		if (result.data.length < 100) {
			for (int i = result.data.length; --i != -1;) {
				final double[] block = result.data[i];
				if (block != null) {
					for (int j = block.length; --j != -1;) {
						block[j] *= value;
					}
				}
			}
		} else {
			new PFor(0, result.data.length - 1) {
				public void step(int i) {
					final double[] block = result.data[i];
					if (block != null) {
						for (int j = block.length; --j != -1;) {
							block[j] *= value;
						}
					}
				}
			};
		}
		return result;
	}

	public Matrix divide(final double value) {
		final BlockDenseDoubleMatrix2D result = new BlockDenseDoubleMatrix2D(this);
		if (result.data.length < 100) {
			for (int i = result.data.length; --i != -1;) {
				double[] block = result.data[i];
				if (block == null) {
					block = new double[layout.blockArea];
				}
				for (int j = block.length; --j != -1;) {
					block[j] /= value;
				}
			}
		} else {
			new PFor(0, result.data.length - 1) {
				public void step(int i) {
					double[] block = result.data[i];
					if (block == null) {
						block = new double[layout.blockArea];
					}
					for (int j = block.length; --j != -1;) {
						block[j] /= value;
					}
				}
			};
		}
		return result;
	}

	/**
	 * Change layout of blocks in this matrix (e.g. switch form rowmajor to
	 * columnmajor).
	 * 
	 * @param order
	 *            - new block layout order.
	 * @return old BlockOrder.
	 */
	public BlockOrder setBlockOrder(BlockOrder order) {
		verify(order != null, "block order cannot be null");

		if (order == layout.blockOrder) {
			return order; // quick exit, already same
		}

		BlockMatrixLayout newLayout = new BlockMatrixLayout(layout.rows, layout.columns,
				layout.blockStripe, order);

		// swap order of each block
		for (int r = 0; r < layout.rows; r += layout.blockStripe) {
			for (int c = 0; c < layout.columns; c += layout.blockStripe) {

				int blockNumber = layout.getBlockNumber(r, c);
				if (data[blockNumber] == null) {
					continue;
				} else if (order == BlockOrder.ROWMAJOR) {
					data[blockNumber] = layout.toRowMajorBlock(data[blockNumber], r, c);
				} else {
					data[blockNumber] = layout.toColMajorBlock(data[blockNumber], r, c);
				}

			}
		}

		BlockOrder oldLayout = this.layout.blockOrder;
		this.layout = newLayout;
		return oldLayout;

	}

};

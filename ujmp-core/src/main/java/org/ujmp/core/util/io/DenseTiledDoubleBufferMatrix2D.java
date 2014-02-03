/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.ujmp.core.util.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.Arrays;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.util.MathUtil;

public class DenseTiledDoubleBufferMatrix2D extends AbstractDenseDoubleMatrix2D {
	private static final long serialVersionUID = -3084994009736435751L;

	public static final int[] DEFAULT_TILE_SIZE = new int[] { 128, 128 };

	private final boolean isDirect = false;

	private final int[] tileSize;
	private final int bufferLength;
	private final int[] bufferCounts;
	private final int byteCount = 8;
	private int blockCount;

	private final long[] size;

	private final DoubleBuffer[] bufferArray;

	public DenseTiledDoubleBufferMatrix2D(long... size) throws IOException {
		this(DEFAULT_TILE_SIZE, createBufferArray(DEFAULT_TILE_SIZE, size), size);
	}

	private static final DoubleBuffer[] createBufferArray(int[] bufferSize, long... size) {
		int count = 1;
		for (int i = 0; i < bufferSize.length; i++) {
			count *= (int) Math.ceil((double) size[i] / (double) bufferSize[i]);
		}
		final DoubleBuffer[] byteBuffers = new DoubleBuffer[count];
		return byteBuffers;
	}

	public DenseTiledDoubleBufferMatrix2D(int[] bufferSize, DoubleBuffer[] bufferArray,
			long... size) throws IOException {
		if (size.length < 2) {
			throw new IllegalArgumentException("size must be at least 2-dimensional");
		}
		if (size.length != bufferSize.length) {
			throw new IllegalArgumentException("size and buffer size must have same dimensionality");
		}

		this.size = Coordinates.copyOf(size);
		this.tileSize = bufferSize;
		this.bufferArray = bufferArray;
		this.bufferLength = MathUtil.longToInt(Coordinates.product(bufferSize));

		this.bufferCounts = new int[bufferSize.length];
		this.blockCount = 1;
		for (int i = 0; i < bufferSize.length; i++) {
			bufferCounts[i] = (int) Math.ceil((double) size[i] / (double) bufferSize[i]);
			this.blockCount *= bufferCounts[i];
		}
	}

	public double getDouble(long row, long column) {
		final int rowIndex = (int) (row / tileSize[ROW]);
		final int columnIndex = (int) (column / tileSize[COLUMN]);
		final int index = columnIndex * bufferCounts[COLUMN] + rowIndex;

		final DoubleBuffer db = bufferArray[index];
		if (db == null) {
			return 0.0;
		} else {
			final int rowOffset = (int) (row - (long) rowIndex * (long) tileSize[ROW]);
			final int columnOffset = (int) (column - (long) columnIndex * (long) tileSize[COLUMN]);
			final int offset = rowOffset + columnOffset * tileSize[COLUMN];
			return db.get(offset);
		}
	}

	public void setDouble(double value, long row, long column) {
		final int rowIndex = (int) (row / tileSize[ROW]);
		final int columnIndex = (int) (column / tileSize[COLUMN]);
		final int index = columnIndex * bufferCounts[COLUMN] + rowIndex;

		DoubleBuffer db = bufferArray[index];
		if (db == null) {
			synchronized (bufferArray) {
				db = bufferArray[index];
				if (db == null) {
					if (isDirect) {
						db = ByteBuffer.allocateDirect(bufferLength * byteCount).asDoubleBuffer();
					} else {
						db = DoubleBuffer.allocate(bufferLength);
					}
					bufferArray[index] = db;
				}
			}
		}

		final int rowOffset = (int) (row - (long) rowIndex * (long) tileSize[ROW]);
		final int columnOffset = (int) (column - (long) columnIndex * (long) tileSize[COLUMN]);
		final int offset = rowOffset + columnOffset * tileSize[COLUMN];

		db.put(offset, value);
	}

	public double getDouble(int row, int column) {
		return getDouble((long) row, (long) column);
	}

	public void setDouble(double value, int row, int column) {
		setDouble(value, (long) row, (long) column);
	}

	public long[] getSize() {
		return size;
	}

	private Matrix mtimes(final DenseTiledDoubleBufferMatrix2D matrix) {
		try {
			final DenseTiledDoubleBufferMatrix2D resultMatrix = new DenseTiledDoubleBufferMatrix2D(
					size[ROW], matrix.size[COLUMN]);

			final int resultMatrixblockCount = resultMatrix.blockCount;

			for (int index = 0; index < resultMatrixblockCount; index++) {
				if (resultMatrix.isDirect) {
					resultMatrix.bufferArray[index] = ByteBuffer.allocateDirect(
							bufferLength * byteCount).asDoubleBuffer();
				} else {
					resultMatrix.bufferArray[index] = DoubleBuffer.allocate(bufferLength);
				}
			}

			double[] source1 = null;
			if (isDirect) {
				source1 = new double[bufferLength];
			}

			double[] doubleResult = null;

			for (int index = 0, rowBlockId = 0, colBlockId = 0; index < resultMatrixblockCount; index++) {

				// System.out.println(rowBlockId + "," + colBlockId);

				final DoubleBuffer db = resultMatrix.bufferArray[index];
				if (db.hasArray()) {
					doubleResult = db.array();
				} else if (doubleResult == null) {
					doubleResult = new double[bufferLength];
				} else if (index > 0) {
					Arrays.fill(doubleResult, 0.0);
					// System.arraycopy(empty, 0, doubleResult, 0,
					// bufferLength);
				}

				// System.out.println();

				final int m2ColumnCount = matrix.tileSize[COLUMN];
				final int m1RowCount = tileSize[ROW];
				final int m1ColumnCount = tileSize[COLUMN];

				for (int i = 0; i < bufferCounts[COLUMN]; i++) {

					// System.out.println("+ " + rowBlockId + "," + i + "*" + i
					// + "," + colBlockId);
					final DoubleBuffer s1 = bufferArray[rowBlockId + i * bufferCounts[ROW]];
					final DoubleBuffer s2 = matrix.bufferArray[i + colBlockId
							* matrix.bufferCounts[ROW]];

					// System.out.println(s1.get(0) + " " + s2.get(0));
					if (s1.hasArray()) {
						source1 = s1.array();
					} else {
						synchronized (s1) {
							s1.rewind();
							s1.get(source1);
						}
					}

					for (int j = 0; j < m2ColumnCount; j++) {
						final int jcolTimesM1RowCount = j * m1RowCount;
						final int jcolTimesM1ColumnCount = j * m1ColumnCount;

						for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
							final double temp = s2.get(lcol + jcolTimesM1ColumnCount);
							if (temp != 0.0d) {
								calcOneColumn(temp, source1, doubleResult, m1RowCount,
										jcolTimesM1RowCount, lcol * m1RowCount);
							}
						}
					}

				}

				if (!db.hasArray()) {
					db.put(doubleResult);
				}

				rowBlockId++;
				if (rowBlockId >= resultMatrix.bufferCounts[ROW]) {
					rowBlockId = 0;
					colBlockId++;
				}

			}

			return resultMatrix;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private final static void calcOneColumn(final double temp, final double[] A, final double[] C,
			final int m1RowCount, int index1, int index2) {
		for (int irow = 0; irow < m1RowCount; ++irow) {
			C[index1++] += A[index2++] * temp;
		}
	}

	private final static void calcOneColumn(final double temp, final DoubleBuffer A,
			final double[] C, final int m1RowCount, int index1, int index2) {
		for (int irow = 0; irow < m1RowCount; ++irow) {
			C[index1++] += A.get(index2++) * temp;
		}
	}

	public Matrix mtimes(Matrix matrix) {
		if (matrix instanceof DenseTiledDoubleBufferMatrix2D
				&& Coordinates.equals(((DenseTiledDoubleBufferMatrix2D) matrix).tileSize, tileSize)) {
			return mtimes((DenseTiledDoubleBufferMatrix2D) matrix);
		} else {
			return super.mtimes(matrix);
		}
	}

	
}

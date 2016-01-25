/*
 * Copyright (C) 2008-2016 by Holger Arndt
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.ujmp.core.util;

import java.util.Arrays;
import java.util.Iterator;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractSparseDoubleMatrix2D;

public class DefaultSparseDoubleVector1D extends AbstractSparseDoubleMatrix2D {
	private static final long serialVersionUID = -2990811989700739834L;

	private static final int initialCapacity = 8;
	private static final double growFactor = 1.5;

	private long[] indices;
	private double[] values;
	private int capacity;
	private int valueCount;
	private boolean transposed;

	public DefaultSparseDoubleVector1D(DefaultSparseDoubleVector1D source) {
		super(source.getRowCount(), source.getColumnCount());
		this.valueCount = source.valueCount;
		this.capacity = source.capacity;
		this.indices = Arrays.copyOf(source.indices, source.indices.length);
		this.values = Arrays.copyOf(source.values, source.values.length);
		this.transposed = getColumnCount() > getRowCount();
	}

	public DefaultSparseDoubleVector1D(long rows, long columns) {
		super(rows, columns);
		VerifyUtil.verifyTrue(rows == 1 || columns == 1, "not a vector");
		this.valueCount = 0;
		this.capacity = initialCapacity;
		this.indices = new long[initialCapacity];
		this.values = new double[initialCapacity];
		this.transposed = columns > rows;
	}

	public final void clear() {
		valueCount = 0;
	}

	public double getDouble(long row, long column) {
		long pos;
		if (transposed) {
			VerifyUtil.verifyEquals(row, 0, "row must be 0");
			pos = column;
		} else {
			VerifyUtil.verifyEquals(column, 0, "column must be 0");
			pos = row;
		}

		int index = MathUtil.search(indices, 0, valueCount, pos);
		if (index >= 0) {
			return values[index];
		} else {
			return 0.0;
		}
	}

	public void setDouble(double value, long row, long column) {
		long pos;
		if (transposed) {
			VerifyUtil.verifyEquals(row, 0, "row must be 0");
			pos = column;
		} else {
			VerifyUtil.verifyEquals(column, 0, "column must be 0");
			pos = row;
		}

		synchronized (indices) {
			int index = MathUtil.search(indices, 0, valueCount, pos);
			if (index >= 0) {
				// value exists
				if (value == 0.0) {
					// delete old value
					System.arraycopy(indices, index + 1, indices, index, valueCount - index - 1);
					System.arraycopy(values, index + 1, values, index, valueCount - index - 1);
					valueCount--;
				} else {
					// update existing value
					values[index] = value;
				}
			} else {
				// value does not exist
				if (value != 0.0) {
					if (capacity == valueCount) {
						// expand arrays if necessary
						capacity = (int) (capacity * growFactor);
						indices = Arrays.copyOf(indices, capacity);
						values = Arrays.copyOf(values, capacity);
					}
					index = findInsertPosition(indices, 0, valueCount, pos);
					if (index != capacity) {
						// make space in the middle
						System.arraycopy(indices, index, indices, index + 1, valueCount - index);
						System.arraycopy(values, index, values, index + 1, valueCount - index);
					}
					indices[index] = pos;
					values[index] = value;
					valueCount++;
				}
			}
		}
	}

	public static final int findInsertPosition(final long[] values, int fromIndex, int toIndex,
			final long key) {
		toIndex--;
		while (fromIndex <= toIndex) {
			int mid = (fromIndex + toIndex) >>> 1;
			long midVal = values[mid];
			if (midVal < key) {
				fromIndex = mid + 1;
			} else if (midVal > key) {
				toIndex = mid - 1;
			} else {
				return mid;
			}
		}
		return fromIndex;
	}

	public double getDouble(int row, int column) {
		return getDouble((long) row, (long) column);
	}

	public void setDouble(double value, int row, int column) {
		setDouble(value, (long) row, (long) column);
	}

	public boolean containsCoordinates(long... coordinates) {
		return getDouble(coordinates) != 0.0;
	}

	public Matrix divide(double value) {
		DefaultSparseDoubleVector1D result = new DefaultSparseDoubleVector1D(this);
		final double[] resultValues = result.values;
		for (int i = resultValues.length; --i != -1;) {
			resultValues[i] /= value;
		}
		return result;
	}

	public Matrix times(double value) {
		DefaultSparseDoubleVector1D result = new DefaultSparseDoubleVector1D(this);
		final double[] resultValues = result.values;
		for (int i = resultValues.length; --i != -1;) {
			resultValues[i] *= value;
		}
		return result;
	}

	public Matrix times(Matrix matrix) {
		DefaultSparseDoubleVector1D result = new DefaultSparseDoubleVector1D(this);
		final double[] resultValues = result.values;
		for (int i = resultValues.length; --i != -1;) {
			resultValues[i] *= matrix.getAsDouble(indices[i], 0);
		}
		return result;
	}

	public Matrix divide(Matrix matrix) {
		DefaultSparseDoubleVector1D result = new DefaultSparseDoubleVector1D(this);
		final double[] resultValues = result.values;
		for (int i = resultValues.length; --i != -1;) {
			resultValues[i] /= matrix.getAsDouble(indices[i], 0);
		}
		return result;
	}

	public Iterable<long[]> availableCoordinates() {
		return new NonZeroIterable(indices, valueCount);
	}

}

class NonZeroIterable implements Iterable<long[]> {

	private final long[] indices;
	private final int valueCount;

	public NonZeroIterable(long[] indices, int valueCount) {
		this.indices = indices;
		this.valueCount = valueCount;
	}

	public Iterator<long[]> iterator() {
		return new NonZeroIterator(indices, valueCount);
	}

}

class NonZeroIterator implements Iterator<long[]> {

	private final long[] indices;
	private final int valueCount;
	private final long[] coordinates = new long[] { -1, 0 };
	private int currentPos = -1;

	public NonZeroIterator(long[] indices, int valueCount) {
		this.indices = indices;
		this.valueCount = valueCount;
		if (valueCount > 0) {
			currentPos = 0;
		}
	}

	public boolean hasNext() {
		return currentPos >= 0 && currentPos < valueCount;
	}

	public long[] next() {
		coordinates[Matrix.ROW] = indices[currentPos];
		currentPos++;
		return coordinates;
	}

	public void remove() {
		throw new RuntimeException("cannot modify matrix");
	}

}

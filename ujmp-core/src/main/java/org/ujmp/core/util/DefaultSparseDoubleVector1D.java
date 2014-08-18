package org.ujmp.core.util;

import java.util.Arrays;

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

	private long[] size;

	public DefaultSparseDoubleVector1D(DefaultSparseDoubleVector1D source) {
		super(source.getRowCount(), source.getColumnCount());
		this.size = new long[] { source.getRowCount(), source.getColumnCount() };
		this.valueCount = source.valueCount;
		this.capacity = source.capacity;
		this.indices = Arrays.copyOf(source.indices, source.indices.length);
		this.values = Arrays.copyOf(source.values, source.values.length);
	}

	public DefaultSparseDoubleVector1D(long rows, long cols) {
		super(rows, 1);
		this.size = new long[] { rows, 1 };
		this.valueCount = 0;
		this.capacity = initialCapacity;
		this.indices = new long[initialCapacity];
		this.values = new double[initialCapacity];
	}

	public double getDouble(long row, long column) {
		if (column != 0) {
			return 0.0;
		} else {
			int index = binarySearch(indices, 0, valueCount, row);
			if (index >= 0) {
				return values[index];
			} else {
				return 0.0;
			}
		}
	}

	public void setDouble(double value, long row, long column) {
		VerifyUtil.verifyEquals(column, 0, "must be 0");
		synchronized (indices) {
			int index = binarySearch(indices, 0, valueCount, row);
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
					index = findInsertPosition(indices, 0, valueCount, row);
					if (index != capacity) {
						// make space in the middle
						System.arraycopy(indices, index, indices, index + 1, valueCount - index);
						System.arraycopy(values, index, values, index + 1, valueCount - index);
					}
					indices[index] = row;
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

	private static final int binarySearch(final long[] values, int fromIndex, int toIndex,
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
		return -(fromIndex + 1);
	}

	public double getDouble(int row, int column) {
		return getDouble((long) row, (long) column);
	}

	public void setDouble(double value, int row, int column) {
		setDouble(value, (long) row, (long) column);
	}

	public boolean contains(long... coordinates) {
		return getDouble(coordinates) != 0.0;
	}

	public long[] getSize() {
		return size;
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
			resultValues[i] *= matrix.getAsDouble(i, 0);
		}
		return result;
	}

	public Matrix divide(Matrix matrix) {
		DefaultSparseDoubleVector1D result = new DefaultSparseDoubleVector1D(this);
		final double[] resultValues = result.values;
		for (int i = resultValues.length; --i != -1;) {
			resultValues[i] /= matrix.getAsDouble(i, 0);
		}
		return result;
	}

}

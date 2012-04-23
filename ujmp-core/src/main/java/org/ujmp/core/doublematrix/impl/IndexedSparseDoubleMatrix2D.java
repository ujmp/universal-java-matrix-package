package org.ujmp.core.doublematrix.impl;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractSparseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;

public class IndexedSparseDoubleMatrix2D extends AbstractSparseDoubleMatrix2D {
	private static final long serialVersionUID = -8455710302874238051L;

	private long[] data;

	private long[] size;

	private int entryCount = 0;

	public IndexedSparseDoubleMatrix2D(Matrix m) {
		this(m.getSize());
	}

	public IndexedSparseDoubleMatrix2D(long... size) {
		this.size = Coordinates.copyOf(size);
		this.data = new long[300];
	}

	public double getDouble(long row, long column) {
		if (entryCount == 0) {
			return 0.0;
		}
		int stepSize = entryCount / 4 + 1;
		int pos = entryCount / 2;
		do {
			if (data[pos * 3] == row) {
				if (data[pos * 3 + 1] == column) {
					return Double.longBitsToDouble(data[pos + 2]);
				} else if (data[pos * 3 + 1] < column) {
					pos += stepSize;
				} else {
					pos -= stepSize;
				}
			} else if (data[pos * 3] < row) {
				pos += stepSize;
			} else {
				pos -= stepSize;
			}
			stepSize /= 2;
		} while (stepSize > 0);
		return 0.0;
	}

	public void setDouble(double value, long row, long column) {
		if (entryCount * 3 == data.length) {
			grow();
		}
		if (entryCount == 0) {
			data[2] = Double.doubleToLongBits(value);
			entryCount++;
			return;
		}
		int stepSize = entryCount / 4 + 1;
		int pos = entryCount / 2;
		do {
			if (data[pos * 3] == row) {
				if (data[pos * 3 + 1] == column) {
					data[pos * 3 + 2] = Double.doubleToLongBits(value);
					return;
				} else if (data[pos * 3 + 1] < column) {
					pos += stepSize;
				} else {
					pos -= stepSize;
				}
			} else if (data[pos * 3] < row) {
				pos += stepSize;
			} else {
				pos -= stepSize;
			}
			stepSize /= 2;
		} while (stepSize > 0);
		data[pos * 3] = row;
		data[pos * 3 + 1] = column;
		data[pos * 3 + 2] = Double.doubleToLongBits(value);
		entryCount++;
	}

	private void grow() {
	}

	public static void main(String[] args) throws Exception {
		IndexedSparseDoubleMatrix2D m = new IndexedSparseDoubleMatrix2D(5, 5);

		m.setAsDouble(1, 0, 0);
		m.setAsDouble(2, 2, 0);
		System.out.println(m.getAsDouble(0, 0));
		System.out.println(m.getAsDouble(2, 0));
	}

	private int getPos(long row, long column) {
		int stepSize = entryCount / 2;
		int pos = stepSize * 3;
		while (stepSize > 1) {
			if (data[pos] == row) {
				if (data[pos + 1] == column) {
					return pos;
				} else if (data[pos + 1] < column) {
					stepSize /= 2;
					pos -= stepSize;
				} else {
					stepSize /= 2;
					pos += stepSize;
				}
			} else if (data[pos] < row) {
				stepSize /= 2;
				pos -= stepSize;
			} else {
				stepSize /= 2;
				pos += stepSize;
			}
		}
		return -1;
	}

	public double getDouble(int row, int column) {
		return getDouble((long) row, (long) column);
	}

	public void setDouble(double value, int row, int column) {
		setDouble(value, (long) row, (long) column);
	}

	public boolean contains(long... coordinates) throws MatrixException {
		return getDouble(coordinates) == 0.0;
	}

	public long[] getSize() {
		return size;
	}

}

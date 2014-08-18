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

package org.ujmp.core.doublematrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractSparseDoubleMatrix2D;

public class IndexedSparseDoubleMatrix2D extends AbstractSparseDoubleMatrix2D {
	private static final long serialVersionUID = -8455710302874238051L;

	private final long[] data;
	private final long[] size;
	private int entryCount = 0;

	public IndexedSparseDoubleMatrix2D(Matrix m) {
		this(m.getRowCount(), m.getColumnCount());
	}

	public IndexedSparseDoubleMatrix2D(long rows, long columns) {
		super(rows, columns);
		this.size = new long[] { rows, columns };
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

	public boolean contains(long... coordinates) {
		return getDouble(coordinates) == 0.0;
	}

	public long[] getSize() {
		return size;
	}

}

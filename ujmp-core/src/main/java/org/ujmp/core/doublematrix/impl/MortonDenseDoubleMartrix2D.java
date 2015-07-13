/*
 * Copyright (C) 2008-2015 by Holger Arndt
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
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.util.MathUtil;

public class MortonDenseDoubleMartrix2D extends AbstractDenseDoubleMatrix2D {
	private static final long serialVersionUID = -1951357825977485935L;

	public static final int ONES0 = 0xaaaaaaaa;
	public static final int ONES1 = 0x55555555;
	public static final int ONES0P1 = ONES0 + 1;
	public static final int ONES1P1 = ONES1 + 1;
	public static final int[] TABLE0 = new int[32767];
	public static final int[] TABLE1 = new int[32767];

	private double[] values;
	private int rows;
	private int cols;

	static {
		init();
	}

	public MortonDenseDoubleMartrix2D(Matrix m) {
		super(m.getRowCount(), m.getColumnCount());
		this.rows = MathUtil.longToInt(m.getRowCount());
		this.cols = MathUtil.longToInt(m.getColumnCount());
		int length = (int) Math.pow(Math.pow(2, Math.ceil(MathUtil.log2(Math.max(rows, cols)))), 2);
		if (m instanceof MortonDenseDoubleMartrix2D) {
			double[] v = ((MortonDenseDoubleMartrix2D) m).values;
			this.values = new double[length];
			System.arraycopy(v, 0, this.values, 0, v.length);
		} else {
			this.values = new double[length];
			for (long[] c : m.allCoordinates()) {
				setDouble(m.getAsDouble(c), c);
			}
		}
		if (m.getMetaData() != null) {
			setMetaData(m.getMetaData().clone());
		}
	}

	public MortonDenseDoubleMartrix2D(int rows, int columns) {
		super(rows, columns);
		this.rows = rows;
		this.cols = columns;
		this.size = new long[] { rows, columns };
		int length = (int) Math.pow(Math.pow(2, Math.ceil(MathUtil.log2(Math.max(rows, columns)))),
				2);
		this.values = new double[length];
	}

	public MortonDenseDoubleMartrix2D(double[] v, int rows, int cols) {
		super(rows, cols);
		this.rows = rows;
		this.cols = cols;
		this.size = new long[] { rows, cols };
		this.values = v;
	}

	public final double getDouble(long row, long column) {
		return values[TABLE1[(int) row] + TABLE0[(int) column]];
	}

	public final double getAsDouble(long row, long column) {
		return values[TABLE1[(int) row] + TABLE0[(int) column]];
	}

	public final double getAsDouble(int row, int column) {
		return values[TABLE1[row] + TABLE0[column]];
	}

	public final void setDouble(double value, long row, long column) {
		values[TABLE1[(int) row] + TABLE0[(int) column]] = value;
	}

	public final void setAsDouble(double value, long row, long column) {
		values[TABLE1[(int) row] + TABLE0[(int) column]] = value;
	}

	public final double getDouble(int row, int column) {
		return values[TABLE1[row] + TABLE0[column]];
	}

	public final void setDouble(double value, int row, int column) {
		values[TABLE1[row] + TABLE0[column]] = value;
	}

	public final void setAsDouble(double value, int row, int column) {
		values[TABLE1[row] + TABLE0[column]] = value;
	}

	public final Matrix copy() {
		double[] result = new double[values.length];
		System.arraycopy(values, 0, result, 0, values.length);
		Matrix m = new MortonDenseDoubleMartrix2D(result, rows, cols);
		if (getMetaData() != null) {
			m.setMetaData(getMetaData().clone());
		}
		return m;
	}

	public Matrix mtimes(Matrix m2) {
		if (m2 instanceof MortonDenseDoubleMartrix2D) {
			final MortonDenseDoubleMartrix2D ret = new MortonDenseDoubleMartrix2D(
					MathUtil.longToInt(getRowCount()), MathUtil.longToInt(m2.getColumnCount()));
			final double[] c = ret.values;
			final double[] b = ((MortonDenseDoubleMartrix2D) m2).values;
			final int retcols = ret.cols;
			final int table1rows = TABLE1[rows];
			final int table0cols = TABLE0[cols];
			final int table0retcols = TABLE0[retcols];

			for (int i = 0; i < table1rows; i = i + ONES1P1 & ONES0) {
				for (int k0 = 0; k0 < table0cols; k0 = k0 + ONES0P1 & ONES1) {
					final int k1 = k0 << 1;
					final double r = values[i + k0];
					for (int j = 0; j < table0retcols; j = j + ONES0P1 & ONES1) {
						c[i + j] += r * b[k1 + j];
					}
				}
			}

			return ret;
		} else {
			return super.mtimes(m2);
		}
	}

	public static final void init() {
		int v = 0;
		int length = TABLE0.length;
		for (int i = 0; i < length; i++) {
			TABLE0[i] = v;
			v = (v + ONES0P1) & ONES1;
		}

		v = 0;
		length = TABLE1.length;
		for (int i = 0; i < length; i++) {
			TABLE1[i] = v;
			v = (v + ONES1P1) & ONES0;
		}
	}

}

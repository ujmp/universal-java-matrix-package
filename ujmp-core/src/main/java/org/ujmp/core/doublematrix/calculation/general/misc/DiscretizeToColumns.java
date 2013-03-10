/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

package org.ujmp.core.doublematrix.calculation.general.misc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class DiscretizeToColumns extends AbstractDoubleCalculation {
	private static final long serialVersionUID = -3606534079672701424L;

	private long column = 0;

	private List<Object> values = null;

	private boolean ignoreNaN = false;

	public DiscretizeToColumns(Matrix matrix, boolean ignoreNaN, long column) {
		super(matrix);
		this.column = column;
		this.ignoreNaN = ignoreNaN;
	}

	
	public long[] getSize() {
		try {
			countValues();
		} catch (MatrixException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long[] size = getSource().getSize();
		size[COLUMN] += values.size() - 1;
		return size;
	}

	
	public double getDouble(long... coordinates) throws MatrixException {
		countValues();
		if (coordinates[COLUMN] < column) {
			return getSource().getAsDouble(coordinates);
		} else if (coordinates[COLUMN] >= column + values.size()) {
			long col = coordinates[COLUMN] - values.size() + 1;
			return getSource().getAsDouble(coordinates[ROW], col);
		} else {
			Object o = getSource().getAsObject(coordinates[ROW], column);
			if (ignoreNaN) {
				if (MathUtil.isNaNOrInfinite(o)) {
					return 0.0;
				} else {
					int index = values.indexOf(o);
					long col = coordinates[COLUMN] - column;
					if (index == col) {
						return 1.0;
					} else {
						return 0.0;
					}
				}
			} else {
				if (MathUtil.isNaNOrInfinite(o)) {
					return Double.NaN;
				} else {
					int index = values.indexOf(o);
					long col = coordinates[COLUMN] - column;
					if (index == col) {
						return 1.0;
					} else {
						return 0.0;
					}
				}
			}

		}
	}

	private void countValues() throws MatrixException {
		if (values == null) {
			Set<Object> set = new HashSet<Object>();
			for (long row = getSource().getRowCount(); --row >= 0;) {
				Object o = getSource().getAsObject(row, column);
				if (ignoreNaN) {
					if (MathUtil.isNaNOrInfinite(o)) {
						set.add(Double.valueOf(0.0));
					} else {
						set.add(o);
					}
				} else {
					if (!MathUtil.isNaNOrInfinite(o)) {
						set.add(o);
					}
				}

			}
			values = new ArrayList<Object>(set);
		}
	}

}

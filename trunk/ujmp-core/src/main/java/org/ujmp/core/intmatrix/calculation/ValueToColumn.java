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

package org.ujmp.core.intmatrix.calculation;

import org.ujmp.core.Matrix;

public class ValueToColumn extends AbstractIntCalculation {
	private static final long serialVersionUID = 1294551769508034360L;

	private int max;

	private final long[] size;

	public ValueToColumn(Matrix matrix, int max) {
		super(matrix);
		if (matrix.getColumnCount() != 1) {
			throw new RuntimeException("matrix must have one column");
		}
		this.max = max;
		this.size = new long[] { getSource().getRowCount(), max + 1 };
	}

	public ValueToColumn(Matrix matrix) {
		super(matrix);
		if (matrix.getColumnCount() != 1) {
			throw new RuntimeException("matrix must have one column");
		}
		for (long[] c : getSource().availableCoordinates()) {
			max = Math.max(max, getSource().getAsInt(c));
		}
		this.size = new long[] { getSource().getRowCount(), max + 1 };
	}

	public final long[] getSize() {
		return size;
	}

	public int getInt(long... coordinates) {
		int col = getSource().getAsInt(coordinates[ROW], 0);
		if (col == coordinates[COLUMN]) {
			return 1;
		} else {
			return 0;
		}
	}

}

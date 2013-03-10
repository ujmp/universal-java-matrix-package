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

package org.ujmp.core.doublematrix.calculation.general.statistical;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class Diff extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 7565251430714359410L;

	private boolean ignoreNaN = true;

	public Diff(int dimension, boolean ignoreNaN, Matrix source) {
		super(dimension, source);
		this.ignoreNaN = ignoreNaN;
	}

	
	public double getDouble(long... coordinates) throws MatrixException {
		double v1 = 0.0;
		double v2 = 0.0;
		switch (getDimension()) {
		case ROW:
			v1 = getSource().getAsDouble(coordinates[ROW], coordinates[COLUMN]);
			v2 = getSource().getAsDouble(coordinates[ROW] + 1, coordinates[COLUMN]);
			break;
		case COLUMN:
			v1 = getSource().getAsDouble(coordinates[ROW], coordinates[COLUMN]);
			v2 = getSource().getAsDouble(coordinates[ROW], coordinates[COLUMN] + 1);
			break;
		default:
			throw new MatrixException("not possible for this dimension");
		}
		if (ignoreNaN && (MathUtil.isNaNOrInfinite(v1) || MathUtil.isNaNOrInfinite(v2))) {
			return 0.0;
		} else {
			return v2 - v1;
		}
	}

	public long[] getSize() {
		switch (getDimension()) {
		case ROW:
			return new long[] { getSource().getRowCount() - 1, getSource().getColumnCount() };
		case COLUMN:
			return new long[] { getSource().getRowCount(), getSource().getColumnCount() - 1 };
		default:
			throw new MatrixException("not possible for this dimension");
		}
	}

}

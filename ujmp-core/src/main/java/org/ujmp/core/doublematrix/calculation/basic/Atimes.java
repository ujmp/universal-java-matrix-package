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

package org.ujmp.core.doublematrix.calculation.basic;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.util.MathUtil;

public class Atimes extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 4170937261358240120L;

	private final boolean ignoreNaN;

	private final long[] size;

	public Atimes(boolean ignoreNaN, Matrix m1, Matrix m2) {
		super(m1, m2);
		this.ignoreNaN = ignoreNaN;
		this.size = new long[] { m1.getRowCount(), m2.getColumnCount() };
	}

	public final double getDouble(final long... coordinates) {
		final Matrix m1 = getSources()[0];
		final Matrix m2 = getSources()[1];

		final long row = coordinates[ROW];
		final long col = coordinates[COLUMN];

		double sum = 0.0;
		double count = 0;
		if (ignoreNaN) {
			for (long k = m1.getColumnCount(); --k >= 0;) {
				double v1 = m1.getAsDouble(row, k);
				double v2 = m2.getAsDouble(k, col);
				if (!MathUtil.isNaNOrInfinite(v1) && !MathUtil.isNaNOrInfinite(v2)) {
					sum += v1 * v2;
					count++;
				}
			}
		} else {
			for (long k = m1.getColumnCount(); --k >= 0;) {
				double v1 = m1.getAsDouble(row, k);
				double v2 = m2.getAsDouble(k, col);
				sum += v1 * v2;
				count++;
			}
		}

		return sum / count;
	}

	public long[] getSize() {
		return size;
	}

}

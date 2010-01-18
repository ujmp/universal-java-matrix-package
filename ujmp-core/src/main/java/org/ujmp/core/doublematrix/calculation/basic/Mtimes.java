/*
 * Copyright (C) 2008-2010 by Holger Arndt
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
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class Mtimes extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 4170937261358240120L;

	private boolean ignoreNaN = false;

	private long[] size = null;

	public Mtimes(boolean ignoreNaN, Matrix m1, Matrix m2) {
		super(m1, m2);
		this.ignoreNaN = ignoreNaN;
		this.size = new long[] { m1.getRowCount(), m2.getColumnCount() };
	}

	public Mtimes() {
		super();
	}

	public double getDouble(long... coordinates) throws MatrixException {
		final Matrix m1 = getSources()[0];
		final Matrix m2 = getSources()[1];

		final long row = coordinates[ROW];
		final long col = coordinates[COLUMN];

		double sum = 0.0;
		if (ignoreNaN) {
			for (long k = m1.getColumnCount(); --k >= 0;) {
				sum += MathUtil.ignoreNaN(m1.getAsDouble(row, k))
						* MathUtil.ignoreNaN(m2.getAsDouble(k, col));
			}
		} else {
			for (long k = m1.getColumnCount(); --k >= 0;) {
				sum += m1.getAsDouble(row, k) * m2.getAsDouble(k, col);
			}
		}

		return sum;
	}

	public long[] getSize() {
		return size;
	}

}

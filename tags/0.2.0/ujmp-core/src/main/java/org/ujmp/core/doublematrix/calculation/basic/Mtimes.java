/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class Mtimes extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 4170937261358240120L;

	private boolean ignoreNaN = false;

	private Matrix result = null;

	public Mtimes(boolean ignoreNaN, Matrix m1, Matrix m2) {
		super(m1, m2);
		this.ignoreNaN = ignoreNaN;
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		if (result == null) {
			result = calc(ignoreNaN, getSources()[0], getSources()[1]);
		}
		return result.getAsDouble(coordinates);
	}

	public static Matrix calc(boolean ignoreNaN, Matrix m1, Matrix m2) throws MatrixException {
		if (m1.isScalar() || m2.isScalar()) {
			return Times.calc(ignoreNaN, m1, m2);
		}

		if (m1.getColumnCount() != m2.getRowCount()) {
			throw new MatrixException("Matrices have wrong size: "
					+ Coordinates.toString(m1.getSize()) + " and "
					+ Coordinates.toString(m2.getSize()));
		}

		int i, j, k;
		double sum;
		double[][] ret = new double[(int) m1.getRowCount()][(int) m2.getColumnCount()];

		if (ignoreNaN) {
			for (i = (int) m1.getRowCount(); --i >= 0;) {
				for (j = ret[0].length; --j >= 0;) {
					sum = 0.0;
					for (k = (int) m1.getColumnCount(); --k >= 0;) {
						sum += MathUtil.ignoreNaN(m1.getAsDouble(i, k))
								* MathUtil.ignoreNaN(m2.getAsDouble(k, j));
					}
					ret[i][j] = sum;
				}
			}
		} else {
			for (i = (int) m1.getRowCount(); --i >= 0;) {
				for (j = ret[0].length; --j >= 0;) {
					sum = 0.0;
					for (k = (int) m1.getColumnCount(); --k >= 0;) {
						sum += m1.getAsDouble(i, k) * m2.getAsDouble(k, j);
					}
					ret[i][j] = sum;
				}
			}
		}

		return new DefaultDenseDoubleMatrix2D(ret);
	}

	@Override
	public long[] getSize() {
		if (result == null) {
			result = calc(ignoreNaN, getSources()[0], getSources()[1]);
		}
		return result.getSize();
	}

}

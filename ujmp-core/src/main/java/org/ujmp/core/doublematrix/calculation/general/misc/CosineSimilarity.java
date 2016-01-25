/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.VerifyUtil;

public class CosineSimilarity extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 9144182368353349242L;

	private final long size[];

	private final boolean ignoreNaN;

	public CosineSimilarity(Matrix matrix, boolean ignoreNaN) {
		super(matrix);
		this.size = new long[] { matrix.getRowCount(), matrix.getRowCount() };
		this.ignoreNaN = ignoreNaN;
	}

	public double getDouble(long... coordinates) {
		Matrix m1 = getSource().selectRows(Ret.LINK, coordinates[ROW]);
		Matrix m2 = getSource().selectRows(Ret.LINK, coordinates[COLUMN]);
		double aiSum = 0;
		double a2Sum = 0;
		double b2Sum = 0;
		for (long i = 0; i < m1.getColumnCount(); i++) {
			double a = m1.getAsDouble(0, i);
			double b = m2.getAsDouble(0, i);
			if (ignoreNaN) {
				if (!MathUtil.isNaNOrInfinite(a) && !MathUtil.isNaNOrInfinite(b)) {
					aiSum += a * b;
					a2Sum += a * a;
					b2Sum += b * b;
				}
			} else {
				aiSum += a * b;
				a2Sum += a * a;
				b2Sum += b * b;
			}
		}
		return aiSum / (Math.sqrt(a2Sum) * Math.sqrt(b2Sum));
	}

	public long[] getSize() {
		return size;
	}

	public static double getCosineSimilartiy(Matrix m1, Matrix m2, boolean ignoreNaN) {
		VerifyUtil.verifySameSize(m1, m2);
		double aiSum = 0;
		double a2Sum = 0;
		double b2Sum = 0;
		for (long[] c : m1.allCoordinates()) {
			double a = m1.getAsDouble(c);
			double b = m2.getAsDouble(c);
			if (ignoreNaN) {
				if (!MathUtil.isNaNOrInfinite(a) && !MathUtil.isNaNOrInfinite(b)) {
					aiSum += a * b;
					a2Sum += a * a;
					b2Sum += b * b;
				}
			} else {
				aiSum += a * b;
				a2Sum += a * a;
				b2Sum += b * b;
			}
		}
		return aiSum / (Math.sqrt(a2Sum) * Math.sqrt(b2Sum));
	}

}

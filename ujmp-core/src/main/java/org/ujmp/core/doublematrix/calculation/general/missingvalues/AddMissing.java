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

package org.ujmp.core.doublematrix.calculation.general.missingvalues;

import java.util.Arrays;

import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.matrix.SparseMatrix;
import org.ujmp.core.util.MathUtil;

public class AddMissing extends AbstractDoubleCalculation {
	private static final long serialVersionUID = -8211102877475985639L;

	private double[] percentMissing = null;

	private Matrix missingValues = null;

	public AddMissing(int dimension, Matrix source, double... percentMissing) {
		super(dimension, source);
		if (percentMissing.length == 1 && dimension != ALL) {
			this.percentMissing = new double[(int) getSource().getSize(dimension)];
			Arrays.fill(this.percentMissing, percentMissing[0]);
		} else {
			this.percentMissing = percentMissing;
		}
	}

	public double getDouble(long... coordinates) throws MatrixException {
		if (missingValues == null) {
			missingValues = SparseMatrix.factory.create(getSource().getSize());

			switch (getDimension()) {
			case ALL:
				int count = (int) (percentMissing[0] * Coordinates.product(getSource().getSize()));
				for (int i = 0; i < count; i++) {
					double v = 0.0;
					int r = 0;
					int c = 0;
					do {
						r = MathUtil.nextInteger(0, (int) getSource().getRowCount() - 1);
						c = MathUtil.nextInteger(0, (int) getSource().getColumnCount() - 1);
						v = missingValues.getAsDouble(r, c);
					} while (MathUtil.isNaNOrInfinite(v));
					missingValues.setAsDouble(Double.NaN, r, c);
				}
				break;
			case COLUMN:
				int missingCount = (int) (getSource().getColumnCount() * percentMissing[0]);
				for (long r = getSource().getRowCount() - 1; r != -1; r--) {
					for (int i = 0; i < missingCount; i++) {
						double v = 0.0;
						int c = 0;
						do {
							c = MathUtil.nextInteger(0, (int) getSource().getColumnCount() - 1);
							v = missingValues.getAsDouble(r, c);
						} while (MathUtil.isNaNOrInfinite(v));
						missingValues.setAsDouble(Double.NaN, r, c);
					}
				}
			}
		}
		if (missingValues.contains(coordinates)) {
			return Double.NaN;
		} else {
			return getSource().getAsDouble(coordinates);
		}
	}

}

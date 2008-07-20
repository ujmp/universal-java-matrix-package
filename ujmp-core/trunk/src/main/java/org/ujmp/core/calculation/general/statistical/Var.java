/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

package org.ujmp.core.calculation.general.statistical;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.AbstractDoubleCalculation;
import org.ujmp.core.calculation.general.missingvalues.CountMissing;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class Var extends AbstractDoubleCalculation {
	private static final long serialVersionUID = -6376910948253374396L;

	private Matrix mean = null;

	private Matrix missingCount = null;

	private boolean ignoreNaN = false;

	public Var(int dimension, boolean ignoreNaN, Matrix matrix) {
		super(dimension, matrix);
		this.ignoreNaN = ignoreNaN;
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		if (mean == null) {
			mean = getSource().calcNew(new Mean(getDimension(), ignoreNaN, getSource()));
		}
		if (missingCount == null) {
			missingCount = getSource().calcNew(new CountMissing(getDimension(), getSource()));
		}

		double sum = 0;

		if (ignoreNaN) {

			switch (getDimension()) {
			case ROW:
				for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
					sum += Math.pow(MathUtil.ignoreNaN(getSource().getAsDouble(r, coordinates[COLUMN]))
							- mean.getAsDouble(0, coordinates[COLUMN]), 2.0);
				}
				return sum / (getSource().getRowCount() - missingCount.getAsDouble(0, coordinates[COLUMN]));
			case COLUMN:
				for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
					sum += Math.pow(MathUtil.ignoreNaN(getSource().getAsDouble(coordinates[ROW], c))
							- mean.getAsDouble(coordinates[ROW], 0), 2.0);
				}
				return sum / (getSource().getColumnCount() - missingCount.getAsDouble(coordinates[ROW], 0));
			case ALL:
				for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
					for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
						sum += Math
								.pow(MathUtil.ignoreNaN(getSource().getAsDouble(r, c)) - mean.getAsDouble(0, 0), 2.0);
					}
				}
				return sum / (Coordinates.product(getSource().getSize()) - missingCount.getAsDouble(0, 0));
			default:
				return 0.0;
			}
		} else {
			switch (getDimension()) {
			case ROW:
				for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
					sum += Math.pow((getSource().getAsDouble(r, coordinates[COLUMN]))
							- mean.getAsDouble(0, coordinates[COLUMN]), 2.0);
				}
				return sum / (getSource().getRowCount() - missingCount.getAsDouble(0, coordinates[COLUMN]));
			case COLUMN:
				for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
					sum += Math.pow((getSource().getAsDouble(coordinates[ROW], c))
							- mean.getAsDouble(coordinates[ROW], 0), 2.0);
				}
				return sum / (getSource().getColumnCount() - missingCount.getAsDouble(coordinates[ROW], 0));
			case ALL:
				for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
					for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
						sum += Math.pow((getSource().getAsDouble(r, c)) - mean.getAsDouble(0, 0), 2.0);
					}
				}
				return sum / (Coordinates.product(getSource().getSize()) - missingCount.getAsDouble(0, 0));
			default:
				return 0.0;
			}

		}

	}

	@Override
	public long[] getSize() {
		switch (getDimension()) {
		case ROW:
			return new long[] { 1, getSource().getSize()[COLUMN] };
		case COLUMN:
			return new long[] { getSource().getSize()[ROW], 1 };
		case ALL:
			return new long[] { 1, 1 };
		}
		return null;
	}

}

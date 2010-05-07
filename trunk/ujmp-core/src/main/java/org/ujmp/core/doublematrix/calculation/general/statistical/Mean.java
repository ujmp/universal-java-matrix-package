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

package org.ujmp.core.doublematrix.calculation.general.statistical;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.annotation.DefaultAnnotation;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.doublematrix.calculation.general.missingvalues.CountMissing;
import org.ujmp.core.exceptions.MatrixException;

public class Mean extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 4116408128004680574L;

	private Matrix sum = null;

	private Matrix missingCount = null;

	private boolean ignoreNaN = false;

	public Mean(int dimension, boolean ignoreNaN, Matrix matrix) {
		super(dimension, matrix);
		this.ignoreNaN = ignoreNaN;
		Annotation aold = matrix.getAnnotation();
		if (aold != null) {
			Annotation a = new DefaultAnnotation(getSize());
			a.setMatrixAnnotation(aold.getMatrixAnnotation());
			if (dimension == ROW) {
				a.setDimensionMatrix(ROW, aold.getDimensionMatrix(ROW));
			} else if (dimension == COLUMN) {
				a.setDimensionMatrix(COLUMN, aold.getDimensionMatrix(COLUMN));
			}
			setAnnotation(a);
		}
	}

	public double getDouble(long... coordinates) throws MatrixException {
		if (sum == null) {
			sum = new Sum(getDimension(), ignoreNaN, getSource()).calcNew();
		}
		if (ignoreNaN && missingCount == null) {
			missingCount = new CountMissing(getDimension(), getSource()).calcNew();
		}

		if (ignoreNaN) {
			switch (getDimension()) {
			case ALL:
				return sum.getAsDouble(0, 0)
						/ (Coordinates.product(getSource().getSize()) - missingCount.getAsDouble(0,
								0));
			case ROW:
				return sum.getAsDouble(0, coordinates[COLUMN])
						/ (getSource().getRowCount() - missingCount.getAsDouble(0,
								coordinates[COLUMN]));
			case COLUMN:
				return sum.getAsDouble(coordinates[ROW], 0)
						/ (getSource().getColumnCount() - missingCount.getAsDouble(
								coordinates[ROW], 0));
			default:
				return Double.NaN;
			}

		} else {

			switch (getDimension()) {
			case ALL:
				return sum.getAsDouble(0, 0) / (Coordinates.product(getSource().getSize()));
			case ROW:
				return sum.getAsDouble(0, coordinates[COLUMN]) / (getSource().getRowCount());
			case COLUMN:
				return sum.getAsDouble(coordinates[ROW], 0) / (getSource().getColumnCount());
			default:
				return Double.NaN;
			}

		}
	}

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

	public static double calc(Matrix m) throws MatrixException {
		double sum = 0.0;
		for (long[] c : m.availableCoordinates()) {
			sum += m.getAsDouble(c);
		}
		return sum / m.getValueCount();
	}

}

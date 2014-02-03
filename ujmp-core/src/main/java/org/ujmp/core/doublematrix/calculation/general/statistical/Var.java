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

package org.ujmp.core.doublematrix.calculation.general.statistical;

import org.ujmp.core.Matrix;
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.annotation.DefaultAnnotation;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.util.MathUtil;

public class Var extends AbstractDoubleCalculation {
	private static final long serialVersionUID = -6376910948253374396L;

	private Matrix mean = null;

	private boolean ignoreNaN = false;

	private boolean besselsCorrection = false;

	public Var(int dimension, boolean ignoreNaN, Matrix matrix, boolean besselsCorrection) {
		super(dimension, matrix);
		this.ignoreNaN = ignoreNaN;
		this.besselsCorrection = besselsCorrection;
		Annotation aold = matrix.getAnnotation();
		if (aold != null) {
			Annotation a = new DefaultAnnotation(getSize().length);
			a.setLabelObject(aold.getLabelObject());
			if (dimension == ROW) {
				a.setDimensionMatrix(ROW, aold.getDimensionMatrix(ROW));
			} else if (dimension == COLUMN) {
				a.setDimensionMatrix(COLUMN, aold.getDimensionMatrix(COLUMN));
			}
			setAnnotation(a);
		}
	}

	public double getDouble(long... coordinates)  {
		if (mean == null) {
			mean = new Mean(getDimension(), ignoreNaN, getSource()).calcNew();
		}

		double sum = 0;

		if (ignoreNaN) {
			double count = 0;
			double m = 0, v = 0;
			switch (getDimension()) {
			case ROW:
				m = mean.getAsDouble(0, coordinates[COLUMN]);
				for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
					v = getSource().getAsDouble(r, coordinates[COLUMN]);
					if (!MathUtil.isNaNOrInfinite(v)) {
						sum += Math.pow(v - m, 2.0);
						count++;
					}
				}
				count = besselsCorrection ? count - 1 : count;
				count = count == 0 ? 1 : count;
				return sum / count;
			case COLUMN:
				m = mean.getAsDouble(coordinates[ROW], 0);
				for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
					v = getSource().getAsDouble(coordinates[ROW], c);
					if (!MathUtil.isNaNOrInfinite(v)) {
						sum += Math.pow(v - m, 2.0);
						count++;
					}
				}
				count = besselsCorrection ? count - 1 : count;
				count = count == 0 ? 1 : count;
				return sum / count;
			case ALL:
				m = mean.getAsDouble(0, 0);
				for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
					for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
						v = getSource().getAsDouble(r, c);
						if (!MathUtil.isNaNOrInfinite(v)) {
							sum += Math.pow(v - m, 2.0);
							count++;
						}
					}
				}
				count = besselsCorrection ? count - 1 : count;
				count = count == 0 ? 1 : count;
				return sum / count;
			default:
				return 0.0;
			}
		} else {
			double count = 0;
			switch (getDimension()) {
			case ROW:
				for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
					sum += Math.pow((getSource().getAsDouble(r, coordinates[COLUMN]))
							- mean.getAsDouble(0, coordinates[COLUMN]), 2.0);
					count++;
				}
				count = besselsCorrection ? count - 1 : count;
				count = count == 0 ? 1 : count;
				return sum / count;
			case COLUMN:
				for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
					sum += Math.pow((getSource().getAsDouble(coordinates[ROW], c))
							- mean.getAsDouble(coordinates[ROW], 0), 2.0);
					count++;
				}
				count = besselsCorrection ? count - 1 : count;
				count = count == 0 ? 1 : count;
				return sum / count;
			case ALL:
				for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
					for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
						sum += Math.pow((getSource().getAsDouble(r, c)) - mean.getAsDouble(0, 0),
								2.0);
						count++;
					}
				}
				count = besselsCorrection ? count - 1 : count;
				count = count == 0 ? 1 : count;
				return sum / count;
			default:
				return 0.0;
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

}

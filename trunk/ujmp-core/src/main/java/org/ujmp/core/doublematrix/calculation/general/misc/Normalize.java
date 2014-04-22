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

package org.ujmp.core.doublematrix.calculation.general.misc;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.doublematrix.calculation.general.statistical.Max;
import org.ujmp.core.doublematrix.calculation.general.statistical.Min;

public class Normalize extends AbstractDoubleCalculation {
	private static final long serialVersionUID = -1898669824045141350L;

	private Matrix ret = null;

	public Normalize(int dimension, Matrix matrix) {
		super(dimension, matrix);
	}

	public double getDouble(long... coordinates) {
		if (ret == null) {
			Matrix max = new Max(getDimension(), getSource()).calcNew();
			Matrix min = new Min(getDimension(), getSource()).calcNew();
			Matrix range = max.minus(min);
			Matrix diff = null;
			switch (getDimension()) {
			case ROW:
				diff = getSource().minus(Matrix.Factory.vertCat(min, getSource().getRowCount()));
				break;
			case COLUMN:
				diff = getSource().minus(Matrix.Factory.horCat(min, getSource().getColumnCount()));
				break;
			default:
				diff = getSource().minus(min);
				break;
			}
			switch (getDimension()) {
			case ROW:
				ret = diff.divide(Matrix.Factory.vertCat(range, getSource().getRowCount()));
				break;
			case COLUMN:
				ret = diff.divide(Matrix.Factory.horCat(range, getSource().getColumnCount()));
				break;
			default:
				ret = diff.divide(range);
				break;
			}
		}

		return ret.getAsDouble(coordinates);
	}

}

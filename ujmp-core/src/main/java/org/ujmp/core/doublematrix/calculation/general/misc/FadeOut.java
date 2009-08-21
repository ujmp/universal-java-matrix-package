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

package org.ujmp.core.doublematrix.calculation.general.misc;

import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.exceptions.MatrixException;

public class FadeOut extends AbstractDoubleCalculation {
	private static final long serialVersionUID = -2366094848097068297L;

	public FadeOut(int dimension, Matrix matrix) {
		super(dimension, matrix);
	}

	
	public double getDouble(long... coordinates) throws MatrixException {
		double factor = 0.0;
		switch (getDimension()) {
		case Matrix.COLUMN:
			factor = 1.0 - (double) coordinates[COLUMN]
					/ (double) (getSource().getColumnCount() - 1);
			break;
		case Matrix.ROW:
			factor = 1.0 - (double) coordinates[ROW] / (double) (getSource().getRowCount() - 1);
			break;
		default:
			factor = 1.0 - (double) Coordinates.product(coordinates)
					/ (double) (getSource().getRowCount() - 1);
			break;
		}
		return getSource().getAsDouble(coordinates) * factor;
	}
}

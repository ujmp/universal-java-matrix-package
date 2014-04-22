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

package org.ujmp.core.doublematrix.calculation.entrywise.basic;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;

public class Log10 extends AbstractDoubleCalculation {
	private static final long serialVersionUID = -5673588058854751554L;

	public Log10(Matrix matrix) {
		super(matrix);
	}

	public double getDouble(long... coordinates) {
		double v = getSource().getAsDouble(coordinates);
		return Math.log(v) / Math.log(10.0);
	}

	public static Matrix calc(Matrix source) {
		Matrix ret = Matrix.Factory.zeros(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			double v = source.getAsDouble(c);
			ret.setAsDouble(Math.log(v) / Math.log(10.0), c);
		}
		return ret;
	}

}

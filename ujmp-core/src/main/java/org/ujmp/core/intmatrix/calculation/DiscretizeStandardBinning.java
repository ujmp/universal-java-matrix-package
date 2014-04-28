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

package org.ujmp.core.intmatrix.calculation;

import org.ujmp.core.Matrix;
import org.ujmp.core.util.UJMPSettings;

public class DiscretizeStandardBinning extends AbstractIntCalculation {
	private static final long serialVersionUID = -2045926868254834270L;

	private Matrix min = null;

	private Matrix max = null;

	private int numberOfBins = 3;

	public DiscretizeStandardBinning(int dimension, Matrix matrix, int numberOfBins) {
		super(dimension, matrix);
		this.numberOfBins = numberOfBins;
	}

	public int getInt(long... coordinates) {
		if (min == null || max == null) {
			calculate();
		}
		double v = getSource().getAsDouble(coordinates);
		double mi = 0;
		double ma = 0;

		switch (getDimension()) {
		case Matrix.ROW:
			mi = min.getAsDouble(0, coordinates[COLUMN]);
			ma = max.getAsDouble(0, coordinates[COLUMN]) + UJMPSettings.getInstance().getTolerance();
			break;
		case Matrix.COLUMN:
			mi = min.getAsDouble(coordinates[ROW], 0);
			ma = max.getAsDouble(coordinates[ROW], 0) + UJMPSettings.getInstance().getTolerance();
			break;
		default:
			mi = min.getAsDouble(0, 0);
			ma = max.getAsDouble(0, 0) + UJMPSettings.getInstance().getTolerance();
			break;
		}

		double bs = (ma - mi) / numberOfBins;
		int i = (int) Math.floor((v - mi) / bs);
		return i;
	}

	private void calculate() {
		min = getSource().min(Ret.NEW, getDimension());
		max = getSource().max(Ret.NEW, getDimension());
	}

}

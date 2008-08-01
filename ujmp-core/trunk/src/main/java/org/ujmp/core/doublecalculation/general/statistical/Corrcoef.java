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

package org.ujmp.core.doublecalculation.general.statistical;

import java.util.logging.Level;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublecalculation.AbstractDoubleCalculation;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class Corrcoef extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 879653499852789474L;

	private Matrix mean = null;

	private boolean ignoreNaN = false;

	public Corrcoef(boolean ignoreNaN, Matrix matrix) {
		super(matrix);
		this.ignoreNaN = ignoreNaN;
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		double sumSqX = 0.0;
		double sumSqY = 0.0;
		double sumProd = 0.0;
		double deltaX = 0.0;
		double deltaY = 0.0;
		long rows = getSource().getRowCount();
		long N = 0;

		if (ignoreNaN && coordinates[ROW] == coordinates[COLUMN]) {
			return 1.0;
		}

		if (mean == null) {
			try {
				mean = new Mean(ROW, ignoreNaN, getSource()).calc(Ret.NEW);
			} catch (MatrixException e) {
				logger.log(Level.WARNING, "could not calculate Matrix", e);
			}
		}

		if (ignoreNaN) {

			for (int i = 0; i < rows; i++) {
				deltaX = getSource().getAsDouble(i, coordinates[ROW]) - mean.getAsDouble(0, coordinates[ROW]);
				deltaY = getSource().getAsDouble(i, coordinates[COLUMN]) - mean.getAsDouble(0, coordinates[COLUMN]);

				if (!MathUtil.isNaNOrInfinite(deltaX) && !MathUtil.isNaNOrInfinite(deltaY)) {
					N++;
					sumSqX += deltaX * deltaX;
					sumSqY += deltaY * deltaY;
					sumProd += deltaX * deltaY;
				}

			}

		} else {

			N = rows;
			for (int i = 0; i < rows; i++) {
				deltaX = getSource().getAsDouble(i, coordinates[ROW]) - mean.getAsDouble(0, coordinates[ROW]);
				deltaY = getSource().getAsDouble(i, coordinates[COLUMN]) - mean.getAsDouble(0, coordinates[COLUMN]);
				sumSqX += deltaX * deltaX;
				sumSqY += deltaY * deltaY;
				sumProd += deltaX * deltaY;
			}

		}

		double sdX = Math.sqrt(sumSqX / (N - 1));
		double sdY = Math.sqrt(sumSqY / (N - 1));
		double cov = sumProd / (N - 1);
		double corr = cov / (sdX * sdY);

		return corr;
	}

	@Override
	public long[] getSize() {
		return new long[] { getSource().getColumnCount(), getSource().getColumnCount() };
	}

}

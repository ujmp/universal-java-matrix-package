/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

package org.ujmp.core.doublematrix.calculation.entrywise.creators;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.concurrent.PFor;

public class Rand extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 1482175899706574542L;

	private double min = 0.0;

	private double max = 1.0;

	public Rand(Matrix matrix) {
		super(matrix);
	}

	public Rand(Matrix matrix, double min, double max) {
		super(matrix);
		this.min = min;
		this.max = max;
	}

	public double getDouble(long... coordinates) {
		return MathUtil.nextDouble(min, max);
	}

	public static Matrix calc(long... size) throws MatrixException {
		return calc(ValueType.DOUBLE, size);
	}

	public static Matrix calc(Matrix source, double min, double max) throws MatrixException {
		Matrix ret = Matrix.factory.zeros(source.getSize());
		for (long[] c : source.allCoordinates()) {
			ret.setAsDouble(MathUtil.nextDouble(min, max), c);
		}
		return ret;
	}

	public static Matrix calc(ValueType valueType, long... size) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(valueType, size);
		for (long[] c : ret.allCoordinates()) {
			ret.setAsDouble(MathUtil.nextDouble(), c);
		}
		return ret;
	}

	public Matrix calcOrig() throws MatrixException {
		if (getSource() instanceof DenseDoubleMatrix2D) {
			final DenseDoubleMatrix2D source = (DenseDoubleMatrix2D) getSource();

			new PFor(0, (int) source.getRowCount() - 1) {
				public void step(int row) {
					for (long col = source.getColumnCount(); --col != -1;) {
						source.setDouble(MathUtil.nextDouble(), row, col);
					}
				}
			};

			getSource().notifyGUIObject();
			return getSource();
		} else {
			return super.calcOrig();
		}
	}

}

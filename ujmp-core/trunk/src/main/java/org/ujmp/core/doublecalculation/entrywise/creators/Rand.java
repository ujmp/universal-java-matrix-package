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

package org.ujmp.core.doublecalculation.entrywise.creators;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.Matrix.EntryType;
import org.ujmp.core.doublecalculation.AbstractDoubleCalculation;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

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

	@Override
	public double getDouble(long... coordinates) {
		return MathUtil.nextUniform(min, max);
	}

	public static Matrix calc(long... size) throws MatrixException {
		return calc(EntryType.DOUBLE, size);
	}

	public static Matrix calc(Matrix source) throws MatrixException {
		return calc(source, 0.0, 1.0);
	}

	public static Matrix calc(Matrix source, double min, double max) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(source.getSize());
		for (long[] c : source.allCoordinates()) {
			ret.setAsDouble(MathUtil.nextUniform(min, max), c);
		}
		return ret;
	}

	public static Matrix calc(EntryType entryType, long... size) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(entryType, size);
		for (long[] c : ret.allCoordinates()) {
			ret.setAsDouble(MathUtil.nextUniform(0.0, 1.0), c);
		}
		return ret;
	}

}

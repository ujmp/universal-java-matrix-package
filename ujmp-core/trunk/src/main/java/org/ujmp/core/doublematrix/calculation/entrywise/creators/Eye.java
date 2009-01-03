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

package org.ujmp.core.doublematrix.calculation.entrywise.creators;

import java.util.Arrays;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;

public class Eye extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 2547827499345834225L;

	public Eye(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) {
		return coordinates[ROW] == coordinates[COLUMN] ? 1.0 : 0.0;
	}

	public static Matrix calc(Matrix source) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(source.getSize());
		long[] c = Coordinates.copyOf(source.getSize());
		for (int i = 0; Coordinates.isSmallerThan(c, source.getSize()); i++) {
			Arrays.fill(c, i);
			ret.setAsDouble(1.0, c);
		}
		return ret;
	}

	public static Matrix calc(long... size) throws MatrixException {
		return calc(ValueType.DOUBLE, size);
	}

	public static Matrix calc(ValueType valueType, long... size) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(valueType, size);
		long[] c = new long[size.length];
		for (int i = 0; Coordinates.isSmallerThan(c, size); i++) {
			ret.setAsDouble(1.0, c);
			Arrays.fill(c, i + 1);
		}
		return ret;
	}
}

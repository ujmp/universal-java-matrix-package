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

package org.ujmp.core.objectmatrix.calculation;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public class Flipdim extends AbstractObjectCalculation {
	private static final long serialVersionUID = -4669640817211533725L;

	public Flipdim(int dimension, Matrix m) {
		super(dimension, m);
	}

	
	public Object getObject(long... coordinates) throws MatrixException {
		coordinates = Coordinates.copyOf(coordinates);
		long newValue = getSize()[getDimension()] - coordinates[getDimension()] - 1;
		coordinates[getDimension()] = newValue;
		return getSource().getAsObject(coordinates);
	}

	public final Matrix calcOrig() throws MatrixException {
		long size = getSize()[getDimension()];
		long max = size / 2;
		Matrix m = getSource();
		for (long i = 0; i < max; i++) {
			m.swap(Ret.ORIG, getDimension(), i, size - i - 1);
		}
		return getSource();
	}
}

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

package org.ujmp.core.doublematrix.calculation.basic;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class Plus extends AbstractDoubleCalculation {
	private static final long serialVersionUID = -2670849261944345597L;

	private boolean ignoreNaN = false;

	public Plus(boolean ignoreNaN, Matrix m1, Matrix m2) {
		super(m1, m2);
		this.ignoreNaN = ignoreNaN;
		if (m2.isScalar() && !Coordinates.equals(m1.getSize(), m2.getSize())) {
			getSources()[1] = MatrixFactory.fill(m2.getAsDouble(0, 0), m1.getSize());
		} else if (m1.isScalar() && !Coordinates.equals(m1.getSize(), m2.getSize())) {
			getSources()[0] = MatrixFactory.fill(m1.getAsDouble(0, 0), m2.getSize());
		}
	}

	public Plus(Matrix m1, Matrix m2) {
		this(true, m1, m2);
	}

	public Plus(boolean ignoreNaN, Matrix m1, double v2) throws MatrixException {
		this(m1, MatrixFactory.fill(v2, m1.getSize()));
	}

	public Plus() {
		super();
	}

	public double getDouble(long... coordinates) throws MatrixException {
		return ignoreNaN ? MathUtil.ignoreNaN(getSources()[0].getAsDouble(coordinates))
				+ MathUtil.ignoreNaN(getSources()[1].getAsDouble(coordinates)) : getSources()[0]
				.getAsDouble(coordinates)
				+ getSources()[1].getAsDouble(coordinates);
	}

}

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

package org.ujmp.core.doublematrix.calculation.basic;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.util.MathUtil;

public class DivideMatrix extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 7228531369984022350L;

	private final boolean ignoreNaN;

	public DivideMatrix(Matrix m1, Matrix m2) {
		this(true, m1, m2);
	}

	public DivideMatrix(boolean ignoreNaN, Matrix m1, Matrix m2) {
		super(m1, m2);
		this.ignoreNaN = ignoreNaN;
	}

	public double getDouble(long... coordinates) {
		return ignoreNaN ? MathUtil.ignoreNaN(getSources()[0].getAsDouble(coordinates))
				/ MathUtil.ignoreNaN(getSources()[1].getAsDouble(coordinates)) : getSources()[0]
				.getAsDouble(coordinates) / getSources()[1].getAsDouble(coordinates);
	}

}

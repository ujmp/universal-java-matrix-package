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

public class PlusScalar extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 4728324680973127164L;

	private final boolean ignoreNaN;

	private final double value;

	public PlusScalar(Matrix m1, double v) {
		this(true, m1, v);
	}

	public PlusScalar(boolean ignoreNaN, Matrix m1, double v) {
		super(m1);
		this.ignoreNaN = ignoreNaN;
		this.value = ignoreNaN ? MathUtil.ignoreNaN(v) : v;
	}

	public double getDouble(long... coordinates) {
		return ignoreNaN ? MathUtil.ignoreNaN(getSources()[0].getAsDouble(coordinates)) + value
				: getSources()[0].getAsDouble(coordinates) + value;
	}

}

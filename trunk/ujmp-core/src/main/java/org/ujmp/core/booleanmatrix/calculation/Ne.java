/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

package org.ujmp.core.booleanmatrix.calculation;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.MathUtil;

public class Ne extends AbstractBooleanCalculation {
	private static final long serialVersionUID = 845058714025562635L;

	public Ne(Matrix m1, Matrix m2) {
		super(m1, m2);
		if (m2.isScalar() && !Coordinates.equals(m1.getSize(), m2.getSize())) {
			getSources()[1] = Matrix.Factory.fill(m2.getAsObject(0, 0), m1.getSize());
		} else if (m1.isScalar() && !Coordinates.equals(m1.getSize(), m2.getSize())) {
			getSources()[0] = Matrix.Factory.fill(m1.getAsObject(0, 0), m2.getSize());
		}
	}

	public Ne(Matrix m1, Object v2) {
		this(m1, Matrix.Factory.fill(v2, m1.getSize()));
	}

	public Ne(Object v1, Matrix m2) {
		this(Matrix.Factory.fill(v1, m2.getSize()), m2);
	}

	public boolean getBoolean(long... coordinates) {
		Object o1 = getSource().getAsObject(coordinates);
		Object o2 = getSources()[1].getAsObject(coordinates);
		return !MathUtil.equals(o1, o2);
	}
}

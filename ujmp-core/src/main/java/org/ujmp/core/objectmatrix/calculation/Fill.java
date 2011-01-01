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

package org.ujmp.core.objectmatrix.calculation;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;

public class Fill extends AbstractObjectCalculation {
	private static final long serialVersionUID = -3477957135967841340L;

	private Object fill = null;

	public Fill(Matrix matrix, Object value) {
		super(matrix);
		this.fill = value;
	}

	
	public Object getObject(long... coordinates) {
		return fill;
	}

	public static Matrix calc(Matrix source, Object fill) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(source.getValueType(), source.getSize());
		for (long[] c : source.allCoordinates()) {
			ret.setAsObject(fill, c);
		}
		return ret;
	}

	public static Matrix calc(Object fill, long... size) throws MatrixException {
		Matrix ret = null;
		if (fill instanceof Number) {
			ret = MatrixFactory.zeros(ValueType.DOUBLE, size);
		} else if (fill instanceof String) {
			ret = MatrixFactory.zeros(ValueType.STRING, size);
		} else {
			ret = MatrixFactory.zeros(ValueType.OBJECT, size);
		}
		for (long[] c : ret.allCoordinates()) {
			ret.setAsObject(fill, c);
		}
		return ret;
	}
}

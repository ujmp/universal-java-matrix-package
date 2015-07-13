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

package org.ujmp.core.shortmatrix.stub;

import static org.ujmp.core.util.VerifyUtil.verifyTrue;

import org.ujmp.core.enums.ValueType;
import org.ujmp.core.numbermatrix.stub.AbstractNumberMatrix;
import org.ujmp.core.shortmatrix.ShortMatrix;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractShortMatrix extends AbstractNumberMatrix<Short> implements
		ShortMatrix {
	private static final long serialVersionUID = 34811716349836913L;

	public AbstractShortMatrix(long... size) {
		super(size);
	}

	public Short getNumber(long... coordinates) {
		return getShort(coordinates);
	}

	public void setNumber(Short value, long... coordinates) {
		setShort(value, coordinates);
	}

	public final Short getObject(long... coordinates) {
		return getShort(coordinates);
	}

	public final void setObject(Short o, long... coordinates) {
		setShort(o, coordinates);
	}

	public final short getAsShort(long... coordinates) {
		return getShort(coordinates);
	}

	public final void setAsShort(short value, long... coordinates) {
		setShort(value, coordinates);
	}

	public final double getAsDouble(long... coordinates) {
		return getShort(coordinates);
	}

	public final void setAsDouble(double value, long... coordinates) {
		verifyTrue(!MathUtil.isNaNOrInfinite(value), "Nan, Inf and -Inf not allowed in this matrix");
		setShort((short) value, coordinates);
	}

	public final ValueType getValueType() {
		return ValueType.SHORT;
	}

}

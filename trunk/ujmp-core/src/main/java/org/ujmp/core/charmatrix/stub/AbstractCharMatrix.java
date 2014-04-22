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

package org.ujmp.core.charmatrix.stub;

import static org.ujmp.core.util.VerifyUtil.verifyTrue;

import org.ujmp.core.charmatrix.CharMatrix;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.genericmatrix.stub.AbstractGenericMatrix;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractCharMatrix extends AbstractGenericMatrix<Character> implements
		CharMatrix {
	private static final long serialVersionUID = 1967840166659276033L;

	public AbstractCharMatrix(long[] size) {
		super(size);
	}

	public final Character getObject(long... coordinates) {
		return getChar(coordinates);
	}

	public final void setObject(Character o, long... coordinates) {
		setChar(o, coordinates);
	}

	public final char getAsChar(long... coordinates) {
		return getChar(coordinates);
	}

	public final void setAsChar(char value, long... coordinates) {
		setChar(value, coordinates);
	}

	public final double getAsDouble(long... coordinates) {
		return getChar(coordinates);
	}

	public final void setAsDouble(double value, long... coordinates) {
		verifyTrue(!MathUtil.isNaNOrInfinite(value), "Nan, Inf and -Inf not allowed in this matrix");
		setChar((char) value, coordinates);
	}

	public final ValueType getValueType() {
		return ValueType.CHAR;
	}

}

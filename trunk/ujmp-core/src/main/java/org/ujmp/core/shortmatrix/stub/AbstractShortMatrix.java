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

package org.ujmp.core.shortmatrix.stub;

import static org.ujmp.core.util.VerifyUtil.assertTrue;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.genericmatrix.stub.AbstractGenericMatrix;
import org.ujmp.core.shortmatrix.ShortMatrix;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractShortMatrix extends AbstractGenericMatrix<Short> implements
		ShortMatrix {
	private static final long serialVersionUID = 34811716349836913L;

	public AbstractShortMatrix() {
		super();
	}

	public AbstractShortMatrix(Matrix m) {
		super(m);
	}

	public AbstractShortMatrix(long... size) {
		super(size);
	}

	public final Short getObject(long... coordinates) throws MatrixException {
		return getShort(coordinates);
	}

	public final void setObject(Short o, long... coordinates) throws MatrixException {
		setShort(o, coordinates);
	}

	public final short getAsShort(long... coordinates) throws MatrixException {
		return getShort(coordinates);
	}

	public final void setAsShort(short value, long... coordinates) throws MatrixException {
		setShort(value, coordinates);
	}

	public final double getAsDouble(long... coordinates) throws MatrixException {
		return getShort(coordinates);
	}

	public final void setAsDouble(double value, long... coordinates) throws MatrixException {
		assertTrue(!MathUtil.isNaNOrInfinite(value), "Nan, Inf and -Inf not allowed in this matrix");
		setShort((short) value, coordinates);
	}

	public final ValueType getValueType() {
		return ValueType.SHORT;
	}

}

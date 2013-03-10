/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

package org.ujmp.core.datematrix.stub;

import java.util.Date;

import org.ujmp.core.Matrix;
import org.ujmp.core.datematrix.DateMatrix;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.genericmatrix.stub.AbstractGenericMatrix;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractDateMatrix extends AbstractGenericMatrix<Date> implements DateMatrix {
	private static final long serialVersionUID = -7141035755618677879L;

	public AbstractDateMatrix() {
		super();
	}

	public AbstractDateMatrix(Matrix m) {
		super(m);
	}

	public AbstractDateMatrix(long... size) {
		super(size);
	}

	public final Date getObject(long... coordinates) throws MatrixException {
		return getDate(coordinates);
	}

	public final void setObject(Date o, long... coordinates) throws MatrixException {
		setDate(o, coordinates);
	}

	public final Date getAsDate(long... coordinates) throws MatrixException {
		return getDate(coordinates);
	}

	public final void setAsDate(Date value, long... coordinates) throws MatrixException {
		setDate(value, coordinates);
	}

	public final double getAsDouble(long... coordinates) throws MatrixException {
		return getDate(coordinates).getTime();
	}

	public final void setAsDouble(double value, long... coordinates) throws MatrixException {
		if (MathUtil.isNaNOrInfinite(value)) {
			setDate(null, coordinates);
		} else {
			setDate(new Date((long) value), coordinates);
		}
	}

	public final ValueType getValueType() {
		return ValueType.DATE;
	}

}

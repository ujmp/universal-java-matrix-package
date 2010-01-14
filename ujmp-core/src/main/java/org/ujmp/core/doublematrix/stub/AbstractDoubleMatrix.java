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

package org.ujmp.core.doublematrix.stub;

import org.ujmp.core.doublematrix.DoubleMatrix;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.genericmatrix.stub.AbstractGenericMatrix;

public abstract class AbstractDoubleMatrix extends AbstractGenericMatrix<Double> implements
		DoubleMatrix {

	private static final long serialVersionUID = 8234957657491264944L;

	public final Double getObject(long... coordinates) throws MatrixException {
		return getAsDouble(coordinates);
	}

	public final void setObject(Double o, long... coordinates) throws MatrixException {
		setAsDouble(o, coordinates);
	}

	public final double getAsDouble(long... coordinates) throws MatrixException {
		return getDouble(coordinates);
	}

	public final void setAsDouble(double value, long... coordinates) throws MatrixException {
		setDouble(value, coordinates);
	}

	public final ValueType getValueType() {
		return ValueType.DOUBLE;
	}

}

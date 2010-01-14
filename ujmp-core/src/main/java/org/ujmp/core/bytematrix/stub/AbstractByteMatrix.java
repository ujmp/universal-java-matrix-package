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

package org.ujmp.core.bytematrix.stub;

import org.ujmp.core.bytematrix.ByteMatrix;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.genericmatrix.stub.AbstractGenericMatrix;

public abstract class AbstractByteMatrix extends AbstractGenericMatrix<Byte> implements ByteMatrix {
	private static final long serialVersionUID = 701344082157040644L;

	
	public final Byte getObject(long... coordinates) throws MatrixException {
		return getByte(coordinates);
	}

	
	public final void setObject(Byte o, long... coordinates) throws MatrixException {
		setByte(o, coordinates);
	}

	
	public final byte getAsByte(long... coordinates) throws MatrixException {
		return getByte(coordinates);
	}

	
	public final void setAsByte(byte value, long... coordinates) throws MatrixException {
		setByte(value, coordinates);
	}

	
	public final double getAsDouble(long... coordinates) throws MatrixException {
		return getByte(coordinates);
	}

	
	public final void setAsDouble(double value, long... coordinates) throws MatrixException {
		setByte((byte) value, coordinates);
	}

	
	public final ValueType getValueType() {
		return ValueType.BYTE;
	}

}

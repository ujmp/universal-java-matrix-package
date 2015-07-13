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

package org.ujmp.core.bytearraymatrix.stub;

import org.ujmp.core.bytearraymatrix.ByteArrayMatrix;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.genericmatrix.stub.AbstractGenericMatrix;

public abstract class AbstractByteArrayMatrix extends AbstractGenericMatrix<byte[]> implements
		ByteArrayMatrix {
	private static final long serialVersionUID = 7605165569237745514L;

	public AbstractByteArrayMatrix(long... size) {
		super(size);
	}

	public final byte[] getObject(long... coordinates) {
		return getByteArray(coordinates);
	}

	public final void setObject(byte[] o, long... coordinates) {
		setByteArray(o, coordinates);
	}

	public final byte[] getAsByteArray(long... coordinates) {
		return getByteArray(coordinates);
	}

	public final void setAsByteArray(byte[] value, long... coordinates) {
		setByteArray(value, coordinates);
	}

	public final ValueType getValueType() {
		return ValueType.BYTEARRAY;
	}

}

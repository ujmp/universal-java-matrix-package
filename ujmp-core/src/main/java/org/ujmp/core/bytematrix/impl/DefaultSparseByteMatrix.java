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

package org.ujmp.core.bytematrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.bytematrix.ByteMatrix;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.genericmatrix.impl.DefaultSparseGenericMatrix;
import org.ujmp.core.util.MathUtil;

public class DefaultSparseByteMatrix extends DefaultSparseGenericMatrix<Byte> implements ByteMatrix {
	private static final long serialVersionUID = 765072872179964518L;

	public DefaultSparseByteMatrix(Matrix m) throws MatrixException {
		super(m, -1);
	}

	public DefaultSparseByteMatrix(Matrix m, int maximumNumberOfEntries) throws MatrixException {
		super(m, maximumNumberOfEntries);
	}

	public DefaultSparseByteMatrix(long... size) {
		super(size);
	}

	public DefaultSparseByteMatrix(int maximumNumberOfEntries, long... size) {
		super(maximumNumberOfEntries, size);
	}

	public final ValueType getValueType() {
		return ValueType.BYTE;
	}

	public byte getByte(long... coordinates) throws MatrixException {
		return MathUtil.getByte(getObject(coordinates));
	}

	public void setByte(byte value, long... coordinates) throws MatrixException {
		setObject(value, coordinates);
	}

}

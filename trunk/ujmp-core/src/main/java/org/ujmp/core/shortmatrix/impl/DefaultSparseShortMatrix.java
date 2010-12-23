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

package org.ujmp.core.shortmatrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.genericmatrix.impl.DefaultSparseGenericMatrix;
import org.ujmp.core.shortmatrix.ShortMatrix;
import org.ujmp.core.util.MathUtil;

public class DefaultSparseShortMatrix extends DefaultSparseGenericMatrix<Short> implements
		ShortMatrix {
	private static final long serialVersionUID = -8927932400907638460L;

	public DefaultSparseShortMatrix(Matrix m) throws MatrixException {
		super(m, -1);
	}

	public DefaultSparseShortMatrix(Matrix m, int maximumNumberOfEntries) throws MatrixException {
		super(m, maximumNumberOfEntries);
	}

	public DefaultSparseShortMatrix(long... size) {
		super(size);
	}

	public DefaultSparseShortMatrix(int maximumNumberOfEntries, long... size) {
		super(maximumNumberOfEntries, size);
	}

	public final ValueType getValueType() {
		return ValueType.SHORT;
	}

	public short getShort(long... coordinates) throws MatrixException {
		return MathUtil.getShort(getObject(coordinates));
	}

	public void setShort(short value, long... coordinates) throws MatrixException {
		setObject(value, coordinates);
	}

}

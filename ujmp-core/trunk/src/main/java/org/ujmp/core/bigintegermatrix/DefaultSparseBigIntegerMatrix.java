/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.ujmp.core.bigintegermatrix;

import java.math.BigInteger;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.genericmatrix.DefaultSparseGenericMatrix;
import org.ujmp.core.util.MathUtil;

public class DefaultSparseBigIntegerMatrix extends DefaultSparseGenericMatrix<BigInteger> implements
		BigIntegerMatrix {
	private static final long serialVersionUID = -7680422900220897521L;

	public DefaultSparseBigIntegerMatrix(Matrix m) throws MatrixException {
		super(m, -1);
	}

	public DefaultSparseBigIntegerMatrix(Matrix m, int maximumNumberOfEntries)
			throws MatrixException {
		super(m, maximumNumberOfEntries);
	}

	public DefaultSparseBigIntegerMatrix(long... size) {
		super(size);
	}

	public DefaultSparseBigIntegerMatrix(int maximumNumberOfEntries, long... size) {
		super(maximumNumberOfEntries, size);
	}

	@Override
	public final ValueType getValueType() {
		return ValueType.BIGINTEGER;
	}

	@Override
	public BigInteger getBigInteger(long... coordinates) throws MatrixException {
		return MathUtil.getBigInteger(getObject(coordinates));
	}

	@Override
	public void setBigInteger(BigInteger value, long... coordinates) throws MatrixException {
		setObject(value, coordinates);
	}

}

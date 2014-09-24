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

package org.ujmp.core.objectmatrix.impl;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.genericmatrix.impl.DefaultSparseGenericMatrix;
import org.ujmp.core.objectmatrix.SparseObjectMatrix;

public class DefaultSparseObjectMatrix extends DefaultSparseGenericMatrix<Object> implements
		SparseObjectMatrix {
	private static final long serialVersionUID = -1130331544425728230L;

	public DefaultSparseObjectMatrix(Matrix m) {
		super(m, -1);
	}

	public DefaultSparseObjectMatrix(Matrix m, int maximumNumberOfEntries) {
		super(m, maximumNumberOfEntries);
	}

	public DefaultSparseObjectMatrix(long... size) {
		super(size);
	}

	public final ValueType getValueType() {
		return ValueType.OBJECT;
	}

	public static DefaultSparseObjectMatrix fromNonZeros(Matrix nonZeros) {
		final Matrix max = nonZeros.max(Ret.NEW, Matrix.ROW);

		final long valueCount = nonZeros.getRowCount();
		final long rowCount = max.getAsLong(0, 0);
		final long columnCount = max.getAsLong(0, 1);

		DefaultSparseObjectMatrix m = new DefaultSparseObjectMatrix(rowCount, columnCount);

		for (int r = 0; r < valueCount; r++) {
			long[] c = new long[2];
			c[0] = nonZeros.getAsLong(r, 0);
			c[1] = nonZeros.getAsLong(r, 1);
			Coordinates co = Coordinates.wrap(c);
			Object val = nonZeros.getAsObject(r, 2);
			m.values.put(co, val);
		}

		return m;
	}

}

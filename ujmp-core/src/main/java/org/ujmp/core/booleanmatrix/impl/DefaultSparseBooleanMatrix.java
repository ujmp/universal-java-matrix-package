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

package org.ujmp.core.booleanmatrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.booleanmatrix.BooleanMatrix;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.genericmatrix.impl.DefaultSparseGenericMatrix;
import org.ujmp.core.util.MathUtil;

public class DefaultSparseBooleanMatrix extends DefaultSparseGenericMatrix<Boolean> implements
		BooleanMatrix {
	private static final long serialVersionUID = 8467706530550022243L;

	public DefaultSparseBooleanMatrix(Matrix m) {
		super(m, -1);
	}

	public DefaultSparseBooleanMatrix(Matrix m, int maximumNumberOfEntries) {
		super(m, maximumNumberOfEntries);
	}

	public DefaultSparseBooleanMatrix(long... size) {
		super(size);
	}

	public DefaultSparseBooleanMatrix(int maximumNumberOfEntries, long... size) {
		super(maximumNumberOfEntries, size);
	}

	public final ValueType getValueType() {
		return ValueType.BOOLEAN;
	}

	public boolean getBoolean(long... coordinates) {
		return MathUtil.getBoolean(getObject(coordinates));
	}

	public void setBoolean(boolean value, long... coordinates) {
		setObject(value, coordinates);
	}

}

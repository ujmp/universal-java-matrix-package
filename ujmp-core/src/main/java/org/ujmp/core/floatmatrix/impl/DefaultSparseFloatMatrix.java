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

package org.ujmp.core.floatmatrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.floatmatrix.FloatMatrix;
import org.ujmp.core.genericmatrix.impl.DefaultSparseGenericMatrix;
import org.ujmp.core.util.MathUtil;

public class DefaultSparseFloatMatrix extends DefaultSparseGenericMatrix<Float> implements
		FloatMatrix {
	private static final long serialVersionUID = -3321607593194609473L;

	public DefaultSparseFloatMatrix(Matrix m)  {
		super(m, -1);
	}

	public DefaultSparseFloatMatrix(Matrix m, int maximumNumberOfEntries)  {
		super(m, maximumNumberOfEntries);
	}

	public DefaultSparseFloatMatrix(long... size) {
		super(size);
	}

	public DefaultSparseFloatMatrix(int maximumNumberOfEntries, long... size) {
		super(maximumNumberOfEntries, size);
	}

	public final ValueType getValueType() {
		return ValueType.FLOAT;
	}

	public float getFloat(long... coordinates)  {
		return MathUtil.getFloat(getObject(coordinates));
	}

	public void setFloat(float value, long... coordinates)  {
		setObject(value, coordinates);
	}

}

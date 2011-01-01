/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

package org.ujmp.core.objectmatrix.stub;

import org.ujmp.core.Matrix;
import org.ujmp.core.objectmatrix.SparseObjectMatrix2D;
import org.ujmp.core.objectmatrix.factory.DefaultSparseObjectMatrix2DFactory;
import org.ujmp.core.objectmatrix.factory.SparseObjectMatrix2DFactory;
import org.ujmp.core.util.CoordinateIterator2D;
import org.ujmp.core.util.VerifyUtil;

public abstract class AbstractSparseObjectMatrix2D extends AbstractSparseObjectMatrix implements
		SparseObjectMatrix2D {
	private static final long serialVersionUID = -3775915270985688066L;

	public static SparseObjectMatrix2DFactory factory = new DefaultSparseObjectMatrix2DFactory();

	public AbstractSparseObjectMatrix2D() {
		super();
	}

	public AbstractSparseObjectMatrix2D(Matrix m) {
		super(m);
	}

	public AbstractSparseObjectMatrix2D(long... size) {
		super(size);
		VerifyUtil.assert2D(size);
	}

	public final Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	public final Object getObject(long... coordinates) {
		return getObject(coordinates[ROW], coordinates[COLUMN]);
	}

	public final void setObject(Object value, long... coordinates) {
		setObject(value, coordinates[ROW], coordinates[COLUMN]);
	}

	public SparseObjectMatrix2DFactory getFactory() {
		return factory;
	}

	public final int getDimensionCount() {
		return 2;
	}

}

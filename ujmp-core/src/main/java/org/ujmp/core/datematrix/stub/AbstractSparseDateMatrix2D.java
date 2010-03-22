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

package org.ujmp.core.datematrix.stub;

import java.util.Date;

import org.ujmp.core.coordinates.CoordinateIterator2D;
import org.ujmp.core.datematrix.DateMatrix2D;
import org.ujmp.core.objectmatrix.factory.DefaultSparseObjectMatrix2DFactory;
import org.ujmp.core.objectmatrix.factory.SparseObjectMatrix2DFactory;

public abstract class AbstractSparseDateMatrix2D extends AbstractSparseDateMatrix implements
		DateMatrix2D {
	private static final long serialVersionUID = 2181329606929093563L;

	public static SparseObjectMatrix2DFactory factory = new DefaultSparseObjectMatrix2DFactory();

	public final Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	public final Date getDate(long... coordinates) {
		return getDate(coordinates[ROW], coordinates[COLUMN]);
	}

	public final void setDate(Date value, long... coordinates) {
		setDate(value, coordinates[ROW], coordinates[COLUMN]);
	}

	public Date getObject(long row, long column) {
		return getDate(row, column);
	}

	public Date getObject(int row, int column) {
		return getDate(row, column);
	}

	public void setObject(Date value, long row, long column) {
		setDate(value, row, column);
	}

	public void setObject(Date value, int row, int column) {
		setDate(value, row, column);
	}

	public SparseObjectMatrix2DFactory getFactory() {
		return factory;
	}
}

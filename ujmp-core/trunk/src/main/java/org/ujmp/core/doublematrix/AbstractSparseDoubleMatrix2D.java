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

package org.ujmp.core.doublematrix;

import org.ujmp.core.coordinates.CoordinateIterator2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractSparseDoubleMatrix2D extends AbstractSparseDoubleMatrix implements
		SparseDoubleMatrix2D {

	private static final long serialVersionUID = 2930732801501704674L;

	public final Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	public final double getDouble(long... coordinates) {
		return getDouble(coordinates[ROW], coordinates[COLUMN]);
	}

	public final void setDouble(double value, long... coordinates) {
		setDouble(value, coordinates[ROW], coordinates[COLUMN]);
	}

	public final Double getObject(long row, long column) throws MatrixException {
		return getDouble(row, column);
	}

	public final void setObject(Object o, long row, long column) throws MatrixException {
		setDouble(MathUtil.getDouble(o), row, column);
	}

}

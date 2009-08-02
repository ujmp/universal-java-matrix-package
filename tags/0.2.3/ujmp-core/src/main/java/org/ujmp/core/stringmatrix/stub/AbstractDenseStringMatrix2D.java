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

package org.ujmp.core.stringmatrix.stub;

import org.ujmp.core.coordinates.CoordinateIterator2D;
import org.ujmp.core.stringmatrix.DenseStringMatrix2D;

public abstract class AbstractDenseStringMatrix2D extends AbstractDenseStringMatrix implements
		DenseStringMatrix2D {

	private static final long serialVersionUID = -8819833075778572302L;

	public Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	public final String getString(long... coordinates) {
		return getString(coordinates[ROW], coordinates[COLUMN]);
	}

	public final void setString(String value, long... coordinates) {
		setString(value, coordinates[ROW], coordinates[COLUMN]);
	}

	@Override
	public final String getObject(long row, long column) {
		return getString(row, column);
	}

	@Override
	public final String getObject(int row, int column) {
		return getString(row, column);
	}

	@Override
	public final void setObject(String value, long row, long column) {
		setString(value, row, column);
	}

	@Override
	public final void setObject(String value, int row, int column) {
		setString(value, row, column);
	}
}

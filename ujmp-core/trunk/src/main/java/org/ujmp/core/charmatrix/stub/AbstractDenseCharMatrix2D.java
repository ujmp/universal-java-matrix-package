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

package org.ujmp.core.charmatrix.stub;

import org.ujmp.core.charmatrix.DenseCharMatrix2D;
import org.ujmp.core.coordinates.CoordinateIterator2D;

public abstract class AbstractDenseCharMatrix2D extends AbstractDenseCharMatrix implements
		DenseCharMatrix2D {

	private static final long serialVersionUID = -261545946520505256L;

	public final Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	public final char getChar(long... coordinates) {
		return getChar(coordinates[ROW], coordinates[COLUMN]);
	}

	public final void setChar(char value, long... coordinates) {
		setChar(value, coordinates[ROW], coordinates[COLUMN]);
	}

	@Override
	public final Character getObject(long row, long column) {
		return getChar(row, column);
	}

	@Override
	public final Character getObject(int row, int column) {
		return getChar(row, column);
	}

	@Override
	public final void setObject(Character value, long row, long column) {
		setChar(value, row, column);
	}

	@Override
	public final void setObject(Character value, int row, int column) {
		setChar(value, row, column);
	}

}

/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

package org.ujmp.core.longmatrix.stub;

import org.ujmp.core.Matrix;
import org.ujmp.core.longmatrix.DenseLongMatrix2D;
import org.ujmp.core.util.CoordinateIterator2D;
import org.ujmp.core.util.VerifyUtil;

public abstract class AbstractDenseLongMatrix2D extends AbstractDenseLongMatrix implements
		DenseLongMatrix2D {
	private static final long serialVersionUID = 3504437963719013875L;

	public AbstractDenseLongMatrix2D() {
		super();
	}

	public AbstractDenseLongMatrix2D(Matrix m) {
		super(m);
	}

	public AbstractDenseLongMatrix2D(long... size) {
		super(size);
		VerifyUtil.assert2D(size);
	}

	public final Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	public final long getLong(long... coordinates) {
		return getLong(coordinates[ROW], coordinates[COLUMN]);
	}

	public final void setLong(long value, long... coordinates) {
		setLong(value, coordinates[ROW], coordinates[COLUMN]);
	}

	public final Long getObject(long row, long column) {
		return getLong(row, column);
	}

	public final Long getObject(int row, int column) {
		return getLong(row, column);
	}

	public final void setObject(Long value, long row, long column) {
		setLong(value, row, column);
	}

	public final void setObject(Long value, int row, int column) {
		setLong(value, row, column);
	}

	public final int getDimensionCount() {
		return 2;
	}
}

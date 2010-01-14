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

package org.ujmp.core.bigdecimalmatrix.stub;

import java.math.BigDecimal;

import org.ujmp.core.bigdecimalmatrix.SparseBigDecimalMatrix2D;
import org.ujmp.core.coordinates.CoordinateIterator2D;
import org.ujmp.core.exceptions.MatrixException;

public abstract class AbstractSparseBigDecimalMatrix2D extends AbstractSparseBigDecimalMatrix
		implements SparseBigDecimalMatrix2D {
	private static final long serialVersionUID = 1272704420975497158L;

	public final Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	public final BigDecimal getBigDecimal(long... coordinates) {
		return getBigDecimal(coordinates[ROW], coordinates[COLUMN]);
	}

	public final void setBigDecimal(BigDecimal value, long... coordinates) {
		setBigDecimal(value, coordinates[ROW], coordinates[COLUMN]);
	}

	public final BigDecimal getObject(long row, long column) throws MatrixException {
		return getBigDecimal(row, column);
	}

	public final void setObject(BigDecimal o, long row, long column) throws MatrixException {
		setBigDecimal(o, row, column);
	}

	public final BigDecimal getObject(int row, int column) throws MatrixException {
		return getBigDecimal(row, column);
	}

	public final void setObject(BigDecimal o, int row, int column) throws MatrixException {
		setBigDecimal(o, row, column);
	}

}

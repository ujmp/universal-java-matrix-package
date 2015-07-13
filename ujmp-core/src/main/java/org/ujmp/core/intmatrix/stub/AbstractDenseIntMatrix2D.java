/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

package org.ujmp.core.intmatrix.stub;

import org.ujmp.core.intmatrix.DenseIntMatrix2D;

public abstract class AbstractDenseIntMatrix2D extends AbstractDenseIntMatrix implements
		DenseIntMatrix2D {
	private static final long serialVersionUID = -8351776461068550919L;

	public AbstractDenseIntMatrix2D(long rows, long columns) {
		super(rows, columns);
	}

	public final int getInt(long... coordinates) {
		return getInt(coordinates[ROW], coordinates[COLUMN]);
	}

	public final void setInt(int value, long... coordinates) {
		setInt(value, coordinates[ROW], coordinates[COLUMN]);
	}

	public final Integer getObject(long row, long column) {
		return getInt(row, column);
	}

	public final Integer getObject(int row, int column) {
		return getInt(row, column);
	}

	public final void setObject(Integer value, long row, long column) {
		setInt(value, row, column);
	}

	public final void setObject(Integer value, int row, int column) {
		setInt(value, row, column);
	}

	public final int getDimensionCount() {
		return 2;
	}

}

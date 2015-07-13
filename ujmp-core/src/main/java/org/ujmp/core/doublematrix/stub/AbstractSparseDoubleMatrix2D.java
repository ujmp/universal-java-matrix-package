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

package org.ujmp.core.doublematrix.stub;

import org.ujmp.core.doublematrix.SparseDoubleMatrix2D;

public abstract class AbstractSparseDoubleMatrix2D extends AbstractDoubleMatrix2D implements
		SparseDoubleMatrix2D {
	private static final long serialVersionUID = 2930732801501704674L;

	public AbstractSparseDoubleMatrix2D(long rows, long columns) {
		super(rows, columns);
	}

	public final Double getObject(long row, long column) {
		return getDouble(row, column);
	}

	public final void setObject(Double o, long row, long column) {
		setDouble(o, row, column);
	}

	public final Double getObject(int row, int column) {
		return getDouble(row, column);
	}

	public final void setObject(Double o, int row, int column) {
		setDouble(o, row, column);
	}

	public final int getDimensionCount() {
		return 2;
	}

	public final boolean isSparse() {
		return true;
	}

	public abstract Iterable<long[]> availableCoordinates();

}

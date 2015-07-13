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

package org.ujmp.core.floatmatrix.stub;

import org.ujmp.core.floatmatrix.DenseFloatMatrix2D;

public abstract class AbstractDenseFloatMatrix2D extends AbstractDenseFloatMatrix implements
		DenseFloatMatrix2D {
	private static final long serialVersionUID = 4140127470234900713L;

	public AbstractDenseFloatMatrix2D(long rows, long columns) {
		super(rows, columns);
	}

	public final float getFloat(long... coordinates) {
		return getFloat(coordinates[ROW], coordinates[COLUMN]);
	}

	public final void setFloat(float value, long... coordinates) {
		setFloat(value, coordinates[ROW], coordinates[COLUMN]);
	}

	public final Float getObject(long row, long column) {
		return getFloat(row, column);
	}

	public final Float getObject(int row, int column) {
		return getFloat(row, column);
	}

	public final void setObject(Float value, long row, long column) {
		setFloat(value, row, column);
	}

	public final void setObject(Float value, int row, int column) {
		setFloat(value, row, column);
	}

	public final int getDimensionCount() {
		return 2;
	}

}

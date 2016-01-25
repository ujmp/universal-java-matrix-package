/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

package org.ujmp.core.bytematrix.stub;

import org.ujmp.core.bytematrix.DenseByteMatrix2D;

public abstract class AbstractDenseByteMatrix2D extends AbstractDenseByteMatrix implements
		DenseByteMatrix2D {
	private static final long serialVersionUID = -9072937987755366928L;

	public AbstractDenseByteMatrix2D(long rows, long columns) {
		super(rows, columns);
	}

	public final byte getByte(long... coordinates) {
		return getByte(coordinates[ROW], coordinates[COLUMN]);
	}

	public final void setByte(byte value, long... coordinates) {
		setByte(value, coordinates[ROW], coordinates[COLUMN]);
	}

	public final Byte getObject(long row, long column) {
		return getByte(row, column);
	}

	public final Byte getObject(int row, int column) {
		return getByte(row, column);
	}

	public final void setObject(Byte value, long row, long column) {
		setByte(value, row, column);
	}

	public final void setObject(Byte value, int row, int column) {
		setByte(value, row, column);
	}

	public final int getDimensionCount() {
		return 2;
	}

}

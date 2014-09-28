/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.ujmp.core.booleanmatrix.impl;

import java.util.BitSet;

import org.ujmp.core.booleanmatrix.stub.AbstractDenseBooleanMatrix2D;

public class BitSetDenseBooleanMatrix2D extends AbstractDenseBooleanMatrix2D {
	private static final long serialVersionUID = -6441956386757378833L;

	private final BitSet values;
	private final long rows;
	private final long columns;

	public BitSetDenseBooleanMatrix2D(int rows, int columns) {
		super(rows, columns);
		this.rows = rows;
		this.columns = columns;
		values = new BitSet(rows * columns);
	}

	public boolean getBoolean(long row, long column) {
		return getBoolean((int) row, (int) column);
	}

	public boolean getBoolean(int row, int column) {
		return values.get((int) (column * rows + row));
	}

	public void setBoolean(boolean value, long row, long column) {
		setBoolean(value, (int) row, (int) column);
	}

	public void setBoolean(boolean value, int row, int column) {
		values.set((int) (column * rows + row), value);
	}

}

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

package org.ujmp.core.objectmatrix.calculation;

import java.util.Arrays;
import java.util.Collection;

import org.ujmp.core.Matrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;

public class Deletion extends AbstractObjectCalculation {
	private static final long serialVersionUID = 3714270132906708701L;

	private final long[][] deletion;

	private final long[] size;

	public Deletion(Matrix m, String deletionString) {
		this(m, StringUtil.parseSelection(deletionString, m.getSize()));
	}

	public Deletion(Matrix m, Collection<? extends Number>... del) {
		super(m);
		deletion = new long[2][];
		deletion[ROW] = MathUtil.collectionToLong(del[ROW]);
		deletion[COLUMN] = MathUtil.collectionToLong(del[COLUMN]);
		Arrays.sort(deletion[ROW]);
		Arrays.sort(deletion[COLUMN]);
		size = new long[] { getSource().getRowCount() - deletion[ROW].length,
				getSource().getColumnCount() - deletion[COLUMN].length };
	}

	public Deletion(Matrix m, long[]... del) {
		super(m);
		deletion = del;
		Arrays.sort(deletion[ROW]);
		Arrays.sort(deletion[COLUMN]);
		size = new long[] { getSource().getRowCount() - deletion[ROW].length,
				getSource().getColumnCount() - deletion[COLUMN].length };
	}

	public Object getObject(long... coordinates) {
		long row = coordinates[ROW];
		long col = coordinates[COLUMN];
		int rowOffset = 0;
		int colOffset = 0;
		for (; rowOffset < deletion[ROW].length && row >= deletion[ROW][rowOffset]; rowOffset++) {
		}
		for (; colOffset < deletion[COLUMN].length && col >= deletion[COLUMN][colOffset]; colOffset++) {
		}
		return getSource().getAsObject(row + rowOffset, col + colOffset);
	}

	public long[] getSize() {
		return size;
	}

}

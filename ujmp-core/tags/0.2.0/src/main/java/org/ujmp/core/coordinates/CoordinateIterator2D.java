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

package org.ujmp.core.coordinates;

import java.util.Iterator;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public class CoordinateIterator2D implements Iterable<long[]> {

	private static final int ROW = Matrix.ROW;

	private static final int COLUMN = Matrix.COLUMN;

	private long[] size = null;

	public CoordinateIterator2D(long... size) {
		this.size = size;
		if (size.length > 2) {
			new Exception(
					"warning: using a 2d iterator on a matrix or calculation that has more than 2 dimensions, results may be wrong")
					.printStackTrace();
		}
	}

	public Iterator<long[]> iterator() {
		return new Iterator<long[]>() {

			long[] cursor = new long[] { 0, -1 };

			long columnCount = size[COLUMN];

			long rowCount = size[ROW];

			public boolean hasNext() {
				return (cursor[ROW] != rowCount - 1 || cursor[COLUMN] != columnCount - 1) && columnCount != 0
						&& rowCount != 0;
			}

			public long[] next() {
				return ++cursor[COLUMN] == columnCount && (cursor[COLUMN] = 0) == ++cursor[ROW] ? cursor : cursor;
			}

			public void remove() {
				throw new MatrixException("not implemented");
			}
		};
	}

}

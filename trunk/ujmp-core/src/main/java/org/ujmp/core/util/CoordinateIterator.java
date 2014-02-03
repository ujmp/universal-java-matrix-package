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

package org.ujmp.core.util;

import java.util.Iterator;

import org.ujmp.core.Coordinates;

public class CoordinateIterator implements Iterable<long[]> {

	private long[] size = null;

	public CoordinateIterator(long... size) {
		this.size = size;
	}

	public Iterator<long[]> iterator() {
		return new It(size);
	}

	class It implements Iterator<long[]> {
		long[] cursor = null;

		long[] size = null;

		long[] lastEntry = null;

		boolean isNotEmpty = false;

		public It(long... size) {
			this.size = size;
			this.lastEntry = Coordinates.minus(size, 1);
			this.cursor = new long[size.length];
			cursor[cursor.length - 1]--;
			isNotEmpty = Coordinates.product(size) != 0;
		}

		public boolean hasNext() {
			return !Coordinates.equals(lastEntry, cursor) && isNotEmpty;
		}

		public long[] next() {
			increment(cursor.length - 1);
			return cursor;
		}

		private void increment(int dim) {
			cursor[dim]++;
			if (cursor[dim] == size[dim]) {
				cursor[dim] = 0;
				increment(dim - 1);
			}
		}

		public void remove() {
			throw new RuntimeException("not implemented");
		}
	}

}

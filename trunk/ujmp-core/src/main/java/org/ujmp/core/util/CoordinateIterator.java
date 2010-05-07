package org.ujmp.core.util;

import java.util.Iterator;

import org.ujmp.core.Coordinates;
import org.ujmp.core.exceptions.MatrixException;

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
			this.lastEntry = Coordinates.minusOne(size);
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
			throw new MatrixException("not implemented");
		}
	}

}

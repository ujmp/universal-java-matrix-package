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

package org.ujmp.core.objectmatrix.calculation;

import java.util.Iterator;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.exceptions.MatrixException;

public class Transpose extends AbstractObjectCalculation {
	private static final long serialVersionUID = -2749226948849267413L;

	private int swap1 = ROW;

	private int swap2 = COLUMN;

	public Transpose(Matrix m) {
		super(m);
	}

	public Transpose(Matrix m, int swap1, int swap2) {
		super(m);
		this.swap1 = swap1;
		this.swap2 = swap2;
	}

	@Override
	public Object getObject(long... coordinates) throws MatrixException {
		return getSource().getAsObject(Coordinates.transpose(coordinates, swap1, swap2));
	}

	@Override
	public long[] getSize() {
		return Coordinates.transpose(getSource().getSize(), swap1, swap2);
	}

	@Override
	public boolean contains(long... coordinates) {
		return getSource().contains(Coordinates.transpose(coordinates, swap1, swap2));
	}

	@Override
	public boolean isSparse() {
		return getSource().isSparse();
	}

	@Override
	public long getValueCount() {
		return getSource().getValueCount();
	}

	@Override
	public Iterable<long[]> availableCoordinates() {
		return new Iterable<long[]>() {

			public Iterator<long[]> iterator() {
				return new TransposedIterator();
			}
		};
	}

	class TransposedIterator implements Iterator<long[]> {
		private final Iterator<long[]> iterator = getSource().availableCoordinates().iterator();

		public boolean hasNext() {
			return iterator.hasNext();
		}

		public long[] next() {
			return Coordinates.transpose(iterator.next(), swap1, swap2);
		}

		public void remove() {
		}
	}

	public static Matrix calc(Matrix m) throws MatrixException {
		Matrix ret = null;
		if (m.isSparse()) {
			ret = MatrixFactory.sparse(m.getValueType(), Coordinates.transpose(m.getSize()));
		} else {
			ret = MatrixFactory.zeros(m.getValueType(), Coordinates.transpose(m.getSize()));
		}
		for (long[] c : m.availableCoordinates()) {
			ret.setAsObject(m.getAsObject(c), Coordinates.transpose(c));
		}
		return ret;
	}
}

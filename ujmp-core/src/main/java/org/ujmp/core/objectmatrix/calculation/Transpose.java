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

import java.util.Iterator;
import java.util.TreeMap;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;

public class Transpose extends AbstractObjectCalculation {
	private static final long serialVersionUID = -2749226948849267413L;

	private int swap1 = ROW;
	private int swap2 = COLUMN;
	private final long[] size;

	public Transpose(Matrix m) {
		this(m, ROW, COLUMN);
	}

	public Transpose(Matrix m, int swap1, int swap2) {
		super(m);
		this.swap1 = swap1;
		this.swap2 = swap2;
		this.size = Coordinates.transpose(getSource().getSize(), swap1, swap2);
		if (getMetaData() != null) {
			setMetaData(transposeAnnotation(m.getMetaData(), size, swap1, swap2));
		}
	}

	public static MapMatrix<String, Object> transposeAnnotation(MapMatrix<String, Object> aorig,
			long[] newSize, int swap1, int swap2) {

		if (aorig != null) {
			MapMatrix<String, Object> a = new DefaultMapMatrix<String, Object>(
					new TreeMap<String, Object>());
			a.put(Matrix.LABEL, aorig.get(Matrix.LABEL));
			for (int i = 0; i < newSize.length; i++) {
				Matrix am = (Matrix) aorig.get(Matrix.DIMENSIONMETADATA + i);
				if (am != null) {
					am = am.transpose(Ret.NEW, swap1, swap2);
					if (i == swap1) {
						a.put(Matrix.DIMENSIONMETADATA + swap2, am);
					} else if (i == swap2) {
						a.put(Matrix.DIMENSIONMETADATA + swap1, am);
					} else {
						a.put(Matrix.DIMENSIONMETADATA + i, am);
					}
				}
			}
			return a;
		}
		return null;
	}

	public Object getObject(long... coordinates) {
		return getSource().getAsObject(Coordinates.transpose(coordinates, swap1, swap2));
	}

	public long[] getSize() {
		return size;
	}

	public boolean containsCoordinates(long... coordinates) {
		return getSource().containsCoordinates(Coordinates.transpose(coordinates, swap1, swap2));
	}

	public boolean isSparse() {
		return getSource().isSparse();
	}

	public long getValueCount() {
		return getSource().getValueCount();
	}

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

	public static MapMatrix<String, Object> transposeAnnotation(
			MapMatrix<String, Object> annotation, long[] newSize) {
		return transposeAnnotation(annotation, newSize, ROW, COLUMN);
	}

}

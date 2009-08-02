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

package org.ujmp.core.objectmatrix;

import java.util.Iterator;

import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.exceptions.MatrixException;

public class ReshapedObjectMatrix extends AbstractObjectMatrix {
	private static final long serialVersionUID = -4298270756453090584L;

	private Matrix source = null;

	private long[] newSize = null;

	private long[] oldSize = null;

	public ReshapedObjectMatrix(Matrix source, long... newSize) {
		this.source = source;
		this.newSize = newSize;
		this.oldSize = source.getSize();
	}

	public boolean contains(long... coordinates) {
		return false;
	}

	public Iterable<long[]> allCoordinates() {
		return new CoordinateIterable();
	}

	private class CoordinateIterable implements Iterable<long[]> {

		public Iterator<long[]> iterator() {
			return new CoordinateIterator();
		}

	}

	private class CoordinateIterator implements Iterator<long[]> {

		private final Iterator<long[]> iterator = source.allCoordinates().iterator();

		public boolean hasNext() {
			return iterator.hasNext();
		}

		public long[] next() {
			return getNewCoordinates(iterator.next());
		}

		public void remove() {
		}

	}

	private long[] getOldCoordinates(long[] newCoordinates) {
		long[] oldCoordinates = Coordinates.copyOf(newCoordinates);
		long valueNumber = newCoordinates[ROW] * newSize[COLUMN] + newCoordinates[COLUMN];
		oldCoordinates[ROW] = valueNumber / oldSize[COLUMN];
		oldCoordinates[COLUMN] = valueNumber % oldSize[COLUMN];
		return oldCoordinates;
	}

	private long[] getNewCoordinates(long[] oldCoordinates) {
		long[] newCoordinates = Coordinates.copyOf(oldCoordinates);
		long valueNumber = oldCoordinates[ROW] * oldSize[COLUMN] + oldCoordinates[COLUMN];
		newCoordinates[ROW] = (valueNumber / newSize[COLUMN]);
		newCoordinates[COLUMN] = (valueNumber % newSize[COLUMN]);
		return newCoordinates;
	}

	public long[] getSize() {
		return newSize;
	}

	@Override
	public double getAsDouble(long... coordinates) throws MatrixException {
		return source.getAsDouble(getOldCoordinates(coordinates));
	}

	@Override
	public Object getObject(long... coordinates) throws MatrixException {
		return source.getAsObject(getOldCoordinates(coordinates));
	}

	@Override
	public long getValueCount() {
		return source.getValueCount();
	}

	@Override
	public boolean isReadOnly() {
		return source.isReadOnly();
	}

	public boolean isSparse() {
		return source.isSparse();
	}

	@Override
	public void setAsDouble(double value, long... coordinates) throws MatrixException {
		source.setAsDouble(value, getOldCoordinates(coordinates));
	}

	public void setObject(Object value, long... coordinates) throws MatrixException {
		source.setAsObject(value, getOldCoordinates(coordinates));
	}

}

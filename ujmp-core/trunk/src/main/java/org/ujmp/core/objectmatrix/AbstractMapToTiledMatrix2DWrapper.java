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

import java.util.Map;

import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.CoordinateIterator2D;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractMapToTiledMatrix2DWrapper extends AbstractDenseMatrix implements
		Wrapper<Map<Coordinates, ObjectMatrix2D>> {

	private long tileSize = 100;

	private long[] size = null;

	public AbstractMapToTiledMatrix2DWrapper(Matrix m) {
		this.size = Coordinates.copyOf(m.getSize());
		for (long[] c : m.allCoordinates()) {
			setObject(m.getObject(c), c);
		}
	}

	public AbstractMapToTiledMatrix2DWrapper(Matrix m, int maximumNumberOfEntries2) {
		this.size = Coordinates.copyOf(m.getSize());
		for (long[] c : m.allCoordinates()) {
			setObject(m.getObject(c), c);
		}
	}

	public AbstractMapToTiledMatrix2DWrapper(long... size) {
		this.size = Coordinates.copyOf(size);
	}

	@Override
	public Object getObject(long... coordinates) throws MatrixException {
		Coordinates c = new Coordinates(coordinates[ROW] / tileSize, coordinates[COLUMN] / tileSize);
		Matrix m = getMap().get(c);
		if (m == null) {
			return null;
		} else {
			return m.getObject(coordinates[ROW] % tileSize, coordinates[COLUMN] % tileSize);
		}
	}

	public Map<Coordinates, ObjectMatrix2D> getWrappedObject() {
		return getMap();
	}

	public abstract Map<Coordinates, ObjectMatrix2D> getMap();

	public void setWrappedObject(Map<Coordinates, ObjectMatrix2D> object) {
		setMap(object);
	}

	public abstract void setMap(Map<Coordinates, ObjectMatrix2D> map);

	public Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	public final double getAsDouble(long... coordinates) throws MatrixException {
		return MathUtil.getDouble(getObject(coordinates));
	}

	public final void setAsDouble(double v, long... coordinates) throws MatrixException {
		setObject(v, coordinates);
	}

	public void setObject(Object o, long... coordinates) throws MatrixException {
		Coordinates c = new Coordinates(coordinates[ROW] / tileSize, coordinates[COLUMN] / tileSize);
		ObjectMatrix2D m = getMap().get(c);
		if (m == null) {
			m = new DefaultDenseMatrix2D(tileSize, tileSize);
		}
		m.setObject(o, coordinates[ROW] % tileSize, coordinates[COLUMN] % tileSize);
		getMap().put(c, m);
	}

	public ValueType getValueType() {
		return ValueType.GENERIC;
	}

	public long[] getSize() {
		return size;
	}

	public long getTileSize() {
		return tileSize;
	}

	public void setTileSize(long tileSize) {
		this.tileSize = tileSize;
	}

}

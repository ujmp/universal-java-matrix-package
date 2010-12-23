/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

package org.ujmp.core.objectmatrix.stub;

import java.util.Map;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.objectmatrix.ObjectMatrix2D;
import org.ujmp.core.objectmatrix.impl.DefaultDenseObjectMatrix2D;
import org.ujmp.core.util.CoordinateIterator2D;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractMapToTiledMatrix2DWrapper extends AbstractDenseObjectMatrix2D
		implements ObjectMatrix2D, Wrapper<Map<Coordinates, ObjectMatrix2D>> {

	private static final long serialVersionUID = -7464578359102479614L;

	private final long[] tileSize = new long[] { 50, 50 };

	private long[] size = null;

	private final Map<Coordinates, ObjectMatrix2D> values;

	public AbstractMapToTiledMatrix2DWrapper(Map<Coordinates, ObjectMatrix2D> map, long... size) {
		super(size);
		this.values = map;
		this.size = Coordinates.copyOf(size);
	}

	public AbstractMapToTiledMatrix2DWrapper(Map<Coordinates, ObjectMatrix2D> map, Matrix source) {
		this(map, source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setObject(source.getAsObject(c), c);
		}
		Annotation a = source.getAnnotation();
		if (a != null) {
			setAnnotation(a.clone());
		}
	}

	public synchronized Object getObject(int row, int column) throws MatrixException {
		return getObject((long) row, (long) column);
	}

	public final Map<Coordinates, ObjectMatrix2D> getMap() {
		return values;
	}

	public synchronized Object getObject(long row, long column) throws MatrixException {
		Coordinates c = new Coordinates(row / tileSize[ROW], column / tileSize[COLUMN]);
		Matrix m = getMap().get(c);
		if (m == null) {
			return null;
		} else {
			return m.getAsObject(row % tileSize[ROW], column % tileSize[COLUMN]);
		}
	}

	public final Map<Coordinates, ObjectMatrix2D> getWrappedObject() {
		return getMap();
	}

	public final void setWrappedObject(Map<Coordinates, ObjectMatrix2D> object) {
		throw new MatrixException("cannot change map");
	}

	public Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	public synchronized final double getAsDouble(long... coordinates) throws MatrixException {
		return MathUtil.getDouble(getObject(coordinates));
	}

	public synchronized final void setAsDouble(double v, long... coordinates)
			throws MatrixException {
		setObject(v, coordinates);
	}

	public synchronized void setObject(Object o, int row, int column) throws MatrixException {
		setObject(o, (long) row, (long) column);
	}

	public synchronized void setObject(Object o, long row, long column) throws MatrixException {
		Coordinates c = new Coordinates(row / tileSize[ROW], column / tileSize[COLUMN]);
		ObjectMatrix2D m = getMap().get(c);
		if (m == null) {
			m = new DefaultDenseObjectMatrix2D(tileSize[ROW], tileSize[COLUMN]);
			getMap().put(c, m);
		}
		m.setObject(o, row % tileSize[ROW], column % tileSize[COLUMN]);
	}

	public final long[] getSize() {
		return size;
	}

	public final long[] getTileSize() {
		return tileSize;
	}

}

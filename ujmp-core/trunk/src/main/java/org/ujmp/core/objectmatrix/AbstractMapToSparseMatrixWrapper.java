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
import org.ujmp.core.coordinates.CoordinateSetToLongWrapper;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractMapToSparseMatrixWrapper extends AbstractSparseObjectMatrix implements
		Wrapper<Map<Coordinates, Object>> {

	private static final long serialVersionUID = -6292034262789053069L;

	private final Object defaultValue = null;

	private int maximumNumberOfEntries = -1;

	private long[] size = null;

	public AbstractMapToSparseMatrixWrapper(Matrix m) {
		this.size = Coordinates.copyOf(m.getSize());
		for (long[] c : m.allCoordinates()) {
			setObject(m.getAsObject(c), c);
		}
	}

	public AbstractMapToSparseMatrixWrapper(Matrix m, int maximumNumberOfEntries2) {
		this.size = Coordinates.copyOf(m.getSize());
		setMaximumNumberOfEntries(maximumNumberOfEntries);
		for (long[] c : m.allCoordinates()) {
			setObject(m.getAsObject(c), c);
		}
	}

	public AbstractMapToSparseMatrixWrapper(long... size) {
		this.size = Coordinates.copyOf(size);
	}

	public abstract Map<Coordinates, Object> getMap();

	public abstract void setMap(Map<Coordinates, Object> map);

	public final long[] getSize() {
		return size;
	}

	public final Map<Coordinates, Object> getWrappedObject() {
		return getMap();
	}

	public final void setWrappedObject(Map<Coordinates, Object> object) {
		setMap(object);
	}

	@Override
	public final Object getObject(long... coordinates) throws MatrixException {
		Object v = getMap().get(new Coordinates(coordinates));
		return v == null ? defaultValue : v;
	}

	public Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	public final boolean contains(long... coordinates) {
		return getMap().containsKey(new Coordinates(coordinates));
	}

	@Override
	public final double getAsDouble(long... coordinates) throws MatrixException {
		return MathUtil.getDouble(getObject(coordinates));
	}

	@Override
	public final void setAsDouble(double v, long... coordinates) throws MatrixException {
		setObject(v, coordinates);
	}

	public final void setObject(Object o, long... coordinates) throws MatrixException {
		while (maximumNumberOfEntries > 0 && getMap().size() > maximumNumberOfEntries) {
			getMap().remove(getMap().keySet().iterator().next());
		}
		if (Coordinates.isSmallerThan(coordinates, getSize())) {
			getMap().put(new Coordinates(coordinates), o);
		}
	}

	public final int getMaximumNumberOfEntries() {
		return maximumNumberOfEntries;
	}

	@Override
	public final long getValueCount() {
		return getMap().size();
	}

	@Override
	public final Iterable<long[]> availableCoordinates() {
		return new CoordinateSetToLongWrapper(getMap().keySet());
	}

	public final void setMaximumNumberOfEntries(int maximumNumberOfEntries) {
		this.maximumNumberOfEntries = maximumNumberOfEntries;
	}

}

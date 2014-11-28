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

package org.ujmp.core.objectmatrix.impl;

import java.util.Map;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.collections.map.SoftHashMap;
import org.ujmp.core.objectmatrix.stub.AbstractSparseObjectMatrix;
import org.ujmp.core.util.CoordinateSetToLongWrapper;

public class VolatileSparseObjectMatrix extends AbstractSparseObjectMatrix {
	private static final long serialVersionUID = 392817709394048419L;

	private final Map<Coordinates, Object> values = new SoftHashMap<Coordinates, Object>();

	public VolatileSparseObjectMatrix(Matrix m) {
		super(m.getSize());
		this.size = Coordinates.copyOf(m.getSize());
		for (long[] c : m.allCoordinates()) {
			setAsDouble(m.getAsDouble(c), c);
		}
	}

	public boolean isTransient() {
		return true;
	}

	public Iterable<long[]> availableCoordinates() {
		throw new RuntimeException("not implemented");
	}

	public VolatileSparseObjectMatrix(long... size) {
		super(size);
		this.size = Coordinates.copyOf(size);
	}

	public final void clear() {
		values.clear();
	}

	public Object getObject(long... coordinates) {
		return values.get(Coordinates.wrap(coordinates));
	}

	public long getValueCount() {
		return values.size();
	}

	public void setObject(Object value, long... coordinates) {
		values.put(Coordinates.wrap(coordinates).clone(), value);
	}

	public Iterable<long[]> entries() {
		return new CoordinateSetToLongWrapper(values.keySet());
	}

	public boolean containsCoordinates(long... coordinates) {
		return values.containsKey(Coordinates.wrap(coordinates));
	}

}

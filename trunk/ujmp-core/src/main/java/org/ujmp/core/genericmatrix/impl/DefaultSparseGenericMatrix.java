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

package org.ujmp.core.genericmatrix.impl;

import java.util.HashMap;
import java.util.Map;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.genericmatrix.stub.AbstractSparseGenericMatrix;
import org.ujmp.core.matrix.factory.BaseMatrixFactory;
import org.ujmp.core.util.CoordinateSetToLongWrapper;
import org.ujmp.core.util.MathUtil;

public class DefaultSparseGenericMatrix<A> extends AbstractSparseGenericMatrix<A> {
	private static final long serialVersionUID = -7139128532871448340L;

	private final Map<Coordinates, A> values = new HashMap<Coordinates, A>();

	private long[] size;

	private int maximumNumberOfEntries = -1;

	public DefaultSparseGenericMatrix(Matrix m) {
		this(m, -1);
	}

	public DefaultSparseGenericMatrix(Matrix m, int maximumNumberOfEntries) {
		super(m.getSize());
		this.size = Coordinates.copyOf(m.getSize());
		this.maximumNumberOfEntries = maximumNumberOfEntries;
		for (long[] c : m.availableCoordinates()) {
			setObject(m.getAsObject(c), c);
		}
		if (m.getMetaData() != null) {
			setMetaData(m.getMetaData().clone());
		}
	}

	public DefaultSparseGenericMatrix(long... size) {
		super(size);
		this.size = Coordinates.copyOf(size);
	}

	public DefaultSparseGenericMatrix(int maximumNumberOfEntries, long... size) {
		super(size);
		this.size = Coordinates.copyOf(size);
		this.maximumNumberOfEntries = maximumNumberOfEntries;
	}

	public long[] getSize() {
		return size;
	}

	public void setSize(long... size) {
		this.size = size;
	}

	public A getObject(long... coordinates) {
		return values.get(Coordinates.wrap(coordinates));
	}

	public long getValueCount() {
		return values.size();
	}

	@SuppressWarnings("unchecked")
	public void setObject(Object value, long... coordinates) {
		while (maximumNumberOfEntries > 0 && values.size() > maximumNumberOfEntries) {
			values.remove(values.keySet().iterator().next());
		}
		if (Coordinates.isSmallerThan(coordinates, size)) {
			if (MathUtil.isNull(value)) {
				values.remove(Coordinates.wrap(coordinates));
			} else {
				values.put(Coordinates.wrap(coordinates).clone(), (A) value);
			}
		}
	}

	public Iterable<long[]> availableCoordinates() {
		return new CoordinateSetToLongWrapper(values.keySet());
	}

	public boolean contains(long... coordinates) {
		return values.containsKey(Coordinates.wrap(coordinates).clone());
	}

	public double getAsDouble(long... coordinates) {
		return MathUtil.getDouble(getObject(coordinates));
	}

	public void setAsDouble(double value, long... coordinates) {
		setObject(value, coordinates);
	}

	public ValueType getValueType() {
		return ValueType.OBJECT;
	}

	public final void clear() {
		values.clear();
	}

	public BaseMatrixFactory<? extends Matrix> getFactory() {
		throw new RuntimeException("not implemented");
	}

}

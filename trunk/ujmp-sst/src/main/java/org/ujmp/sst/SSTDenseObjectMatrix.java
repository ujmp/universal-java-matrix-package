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

package org.ujmp.sst;

import org.ujmp.core.Matrix;
import org.ujmp.core.genericmatrix.stub.AbstractDenseGenericMatrix;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.util.CoordinateIterator;
import org.ujmp.core.util.MathUtil;

import shared.array.ObjectArray;

public class SSTDenseObjectMatrix extends AbstractDenseGenericMatrix<Object> implements Wrapper<ObjectArray<Object>> {
	private static final long serialVersionUID = 2319673263310965476L;

	private transient ObjectArray<Object> data;

	public SSTDenseObjectMatrix(ObjectArray<Object> data) {
		super(MathUtil.toLongArray(data.dims()));
		this.data = data;
	}

	public SSTDenseObjectMatrix(long... size) {
		super(size);
		data = new ObjectArray<Object>(Object.class, MathUtil.toIntArray(size));
	}

	public SSTDenseObjectMatrix(Matrix source) {
		super(source.getSize());
		data = new ObjectArray<Object>(Object.class, MathUtil.toIntArray(source.getSize()));
		for (long[] c : source.availableCoordinates()) {
			setObject(source.getAsObject(c), c);
		}
	}

	public Object getObject(long... coordinates) {
		return data.get(MathUtil.toIntArray(coordinates));
	}

	public void setObject(Object value, long... coordinates) {
		data.set(value, MathUtil.toIntArray(coordinates));

	}

	public Iterable<long[]> allCoordinates() {
		return new CoordinateIterator(this.getSize());
	}

	public ObjectArray<Object> getWrappedObject() {
		return data;
	}

}

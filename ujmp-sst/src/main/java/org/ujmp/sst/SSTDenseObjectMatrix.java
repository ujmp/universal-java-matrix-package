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

package org.ujmp.sst;

import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.CoordinateIterator;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.genericmatrix.AbstractDenseGenericMatrix;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.util.MathUtil;

import shared.array.ObjectArray;

public class SSTDenseObjectMatrix<A> extends AbstractDenseGenericMatrix<A>
		implements Wrapper<ObjectArray<A>> {
	private static final long serialVersionUID = 2319673263310965476L;

	private transient ObjectArray<A> data = null;

	public SSTDenseObjectMatrix(ObjectArray<A> data) {
		this.data = data;
	}

	public SSTDenseObjectMatrix(long... size) {
		data = new ObjectArray<A>(MathUtil.toIntArray(size));
	}

	@SuppressWarnings("unchecked")
	public SSTDenseObjectMatrix(Matrix source) {
		data = new ObjectArray<A>(MathUtil.toIntArray(source.getSize()));
		for (long[] c : source.availableCoordinates()) {
			setObject((A) source.getAsObject(c), c);
		}
	}

	@Override
	public A getObject(long... coordinates) throws MatrixException {
		return data.get(MathUtil.toIntArray(coordinates));
	}

	public void setObject(A value, long... coordinates) throws MatrixException {
		data.set(value, MathUtil.toIntArray(coordinates));

	}

	public long[] getSize() {
		return MathUtil.toLongArray(data.dimensions());
	}

	@Override
	public Iterable<long[]> allCoordinates() throws MatrixException {
		return new CoordinateIterator(this.getSize());
	}

	@Override
	public ObjectArray<A> getWrappedObject() {
		return data;
	}

	@Override
	public void setWrappedObject(ObjectArray<A> object) {
		this.data = object;
	}

}

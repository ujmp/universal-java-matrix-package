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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.CoordinateIterator;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.intmatrix.stub.AbstractDenseIntMatrix;
import org.ujmp.core.util.MathUtil;

import shared.array.IntegerArray;

public class SSTDenseIntMatrix extends AbstractDenseIntMatrix implements
		Wrapper<IntegerArray> {
	private static final long serialVersionUID = 2319673263310965476L;

	private transient IntegerArray data = null;

	public SSTDenseIntMatrix(IntegerArray data) {
		this.data = data;
	}

	public SSTDenseIntMatrix(long... size) {
		data = new IntegerArray(MathUtil.toIntArray(size));
	}

	public SSTDenseIntMatrix(Matrix source) {
		data = new IntegerArray(MathUtil.toIntArray(source.getSize()));
		for (long[] c : source.availableCoordinates()) {
			setInt(source.getAsInt(c), c);
		}
	}

	public int getInt(long... coordinates) throws MatrixException {
		return data.get(MathUtil.toIntArray(coordinates));
	}

	public void setInt(int value, long... coordinates) throws MatrixException {
		data.set(value, MathUtil.toIntArray(coordinates));

	}

	public long[] getSize() {
		return MathUtil.toLongArray(data.dimensions());
	}

	
	public Iterable<long[]> allCoordinates() throws MatrixException {
		return new CoordinateIterator(this.getSize());
	}

	private void readObject(ObjectInputStream s) throws IOException,
			ClassNotFoundException {
		s.defaultReadObject();
		byte[] bytes = (byte[]) s.readObject();
		data = IntegerArray.parse(bytes);
	}

	private void writeObject(ObjectOutputStream s) throws IOException,
			MatrixException {
		s.defaultWriteObject();
		s.writeObject(data.getBytes());
	}

	
	public IntegerArray getWrappedObject() {
		return data;
	}

	
	public void setWrappedObject(IntegerArray object) {
		this.data = object;
	}

}

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
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.util.MathUtil;

import shared.array.RealArray;

public class SSTDenseDoubleMatrix extends AbstractDenseDoubleMatrix implements
		Wrapper<RealArray> {
	private static final long serialVersionUID = 2319673263310965476L;

	private transient RealArray data = null;

	public SSTDenseDoubleMatrix(RealArray data) {
		this.data = data;
	}

	public SSTDenseDoubleMatrix(long... size) {
		data = new RealArray(MathUtil.toIntArray(size));
	}

	public SSTDenseDoubleMatrix(Matrix source) {
		data = new RealArray(MathUtil.toIntArray(source.getSize()));
		for (long[] c : source.availableCoordinates()) {
			setDouble(source.getAsDouble(c), c);
		}
	}

	public double getDouble(long... coordinates) throws MatrixException {
		return data.get(MathUtil.toIntArray(coordinates));
	}

	public void setDouble(double value, long... coordinates)
			throws MatrixException {
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
		data = RealArray.parse(bytes);
	}

	private void writeObject(ObjectOutputStream s) throws IOException,
			MatrixException {
		s.defaultWriteObject();
		s.writeObject(data.getBytes());
	}

	public RealArray getWrappedObject() {
		return data;
	}

	public void setWrappedObject(RealArray object) {
		this.data = object;
	}

	public Matrix transpose() {
		return new SSTDenseDoubleMatrix(data.clone().mTranspose());
	}

	public Matrix mtimes(Matrix m) {
		if (m instanceof SSTDenseDoubleMatrix) {
			return new SSTDenseDoubleMatrix(data
					.mMul(((SSTDenseDoubleMatrix) m).getWrappedObject()));
		} else {
			return super.mtimes(m);
		}
	}

	public Matrix plus(double v) {
		return new SSTDenseDoubleMatrix(data.clone().uAdd(v));
	}

	public Matrix minus(double v) {
		return new SSTDenseDoubleMatrix(data.clone().uAdd(-v));
	}

	public Matrix times(double v) {
		return new SSTDenseDoubleMatrix(data.clone().uMul(v));
	}

	public Matrix divide(double v) {
		return new SSTDenseDoubleMatrix(data.clone().uMul(1 / v));
	}

}

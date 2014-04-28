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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.MathUtil;

import shared.array.RealArray;

public class SSTDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D implements Wrapper<RealArray> {
	private static final long serialVersionUID = -9002457298955206969L;

	public static final SSTDenseDoubleMatrix2DFactory Factory = new SSTDenseDoubleMatrix2DFactory();

	private transient RealArray data = null;

	public SSTDenseDoubleMatrix2D(RealArray data) {
		super(data.dims()[0], data.dims()[1]);
		this.data = data;
	}

	public SSTDenseDoubleMatrix2D(int rows, int columns) {
		super(rows, columns);
		data = new RealArray(rows, columns);
	}

	public SSTDenseDoubleMatrix2D(Matrix source) {
		super(source.getRowCount(), source.getColumnCount());
		data = new RealArray(MathUtil.toIntArray(source.getSize()));
		for (long[] c : source.availableCoordinates()) {
			setDouble(source.getAsDouble(c), c);
		}
		if (source.getMetaData() != null) {
			setMetaData(source.getMetaData().clone());
		}
	}

	public double getDouble(long rows, long columns) {
		return data.get((int) rows, (int) columns);
	}

	public double getDouble(int rows, int columns) {
		return data.get(rows, columns);
	}

	public void setDouble(double value, long rows, long columns) {
		data.set(value, (int) rows, (int) columns);
	}

	public void setDouble(double value, int rows, int columns) {
		data.set(value, rows, columns);
	}

	public long[] getSize() {
		return MathUtil.toLongArray(data.dims());
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		byte[] bytes = (byte[]) s.readObject();
		data = RealArray.parse(bytes);
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeObject(data.getBytes());
	}

	public RealArray getWrappedObject() {
		return data;
	}

	public Matrix transpose() {
		return new SSTDenseDoubleMatrix2D(data.mTranspose());
	}

	public Matrix inv() {
		return new SSTDenseDoubleMatrix2D(data.mInvert());
	}

	public Matrix[] eig() {
		RealArray[] eig = data.mEigs();
		Matrix v = new SSTDenseDoubleMatrix2D(eig[0]);
		Matrix d = new SSTDenseDoubleMatrix2D(eig[1]);
		return new Matrix[] { v, d };
	}

	public Matrix[] svd() {
		RealArray[] svd = data.mSVD();
		Matrix u = new SSTDenseDoubleMatrix2D(svd[0]);
		Matrix s = new SSTDenseDoubleMatrix2D(svd[1]);
		Matrix v = new SSTDenseDoubleMatrix2D(svd[2]);
		return new Matrix[] { u, s, v };
	}

	public Matrix mtimes(Matrix m) {
		if (m instanceof SSTDenseDoubleMatrix2D) {
			return new SSTDenseDoubleMatrix2D(data.mMul(((SSTDenseDoubleMatrix2D) m).getWrappedObject()));
		} else {
			return super.mtimes(m);
		}
	}

	public Matrix plus(double v) {
		Matrix result = new SSTDenseDoubleMatrix2D(data.clone().uAdd(v));
		MapMatrix<Object, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
	}

	public Matrix plus(Matrix m) {
		if (m instanceof SSTDenseDoubleMatrix2D) {
			Matrix result = new SSTDenseDoubleMatrix2D(data.clone().eAdd(
					((SSTDenseDoubleMatrix2D) m).getWrappedObject()));
			MapMatrix<Object, Object> a = getMetaData();
			if (a != null) {
				result.setMetaData(a.clone());
			}
			return result;
		} else {
			return super.plus(m);
		}
	}

	public Matrix minus(Matrix m) {
		if (m instanceof SSTDenseDoubleMatrix2D) {
			Matrix result = new SSTDenseDoubleMatrix2D(data.clone().eSub(
					((SSTDenseDoubleMatrix2D) m).getWrappedObject()));
			MapMatrix<Object, Object> a = getMetaData();
			if (a != null) {
				result.setMetaData(a.clone());
			}
			return result;
		} else {
			return super.plus(m);
		}
	}

	public Matrix minus(double v) {
		Matrix result = new SSTDenseDoubleMatrix2D(data.clone().uAdd(-v));
		MapMatrix<Object, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
	}

	public Matrix times(double v) {
		Matrix result = new SSTDenseDoubleMatrix2D(data.clone().uMul(v));
		MapMatrix<Object, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
	}

	public Matrix divide(double v) {
		Matrix result = new SSTDenseDoubleMatrix2D(data.clone().uMul(1 / v));
		MapMatrix<Object, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
	}

	public SSTDenseDoubleMatrix2DFactory getFactory() {
		return Factory;
	}

}

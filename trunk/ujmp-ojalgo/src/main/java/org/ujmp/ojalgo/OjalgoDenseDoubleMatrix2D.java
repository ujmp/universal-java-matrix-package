/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

package org.ujmp.ojalgo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.ojalgo.access.Access2D;
import org.ojalgo.array.ArrayUtils;
import org.ojalgo.function.implementation.PrimitiveFunction;
import org.ojalgo.matrix.BasicMatrix;
import org.ojalgo.matrix.PrimitiveMatrix;
import org.ojalgo.matrix.decomposition.LUDecomposition;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.OjalgoUtil;
import org.ojalgo.matrix.store.PhysicalStore;
import org.ojalgo.matrix.store.PrimitiveDenseStore;
import org.ujmp.core.Matrix;
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasColumnMajorDoubleArray1D;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.ojalgo.calculation.Chol;
import org.ujmp.ojalgo.calculation.Eig;
import org.ujmp.ojalgo.calculation.Inv;
import org.ujmp.ojalgo.calculation.InvSPD;
import org.ujmp.ojalgo.calculation.LU;
import org.ujmp.ojalgo.calculation.QR;
import org.ujmp.ojalgo.calculation.SVD;
import org.ujmp.ojalgo.calculation.Solve;

public class OjalgoDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D
		implements Wrapper<MatrixStore<Double>> {

	private static final long serialVersionUID = 6628172130438716653L;

	private transient PrimitiveDenseStore matrix;

	public OjalgoDenseDoubleMatrix2D(final long... size) {
		matrix = (PrimitiveDenseStore) PrimitiveDenseStore.FACTORY.makeZero(
				(int) size[ROW], (int) size[COLUMN]);
	}

	public OjalgoDenseDoubleMatrix2D(final Matrix m) {
		super(m);
		if (m instanceof HasColumnMajorDoubleArray1D) {
			final double[] data = ((HasColumnMajorDoubleArray1D) m)
					.getColumnMajorDoubleArray1D();
			this.matrix = OjalgoUtil.linkToArray((int) m.getRowCount(), (int) m
					.getColumnCount(), data);
		} else if (m instanceof DenseDoubleMatrix2D) {
			this.matrix = (PrimitiveDenseStore) PrimitiveDenseStore.FACTORY
					.makeZero((int) m.getRowCount(), (int) m.getColumnCount());
			final DenseDoubleMatrix2D m2 = (DenseDoubleMatrix2D) m;
			for (int r = (int) m.getRowCount(); --r >= 0;) {
				for (int c = (int) m.getColumnCount(); --c >= 0;) {
					matrix.set(r, c, m2.getDouble(r, c));
				}
			}
		} else {
			this.matrix = (PrimitiveDenseStore) PrimitiveDenseStore.FACTORY
					.makeZero((int) m.getRowCount(), (int) m.getColumnCount());
			for (final long[] c : m.availableCoordinates()) {
				this.setDouble(m.getAsDouble(c), c);
			}
		}
	}

	public OjalgoDenseDoubleMatrix2D(final MatrixStore<Double> m) {
		this.setWrappedObject(m);
	}

	@Override
	public Matrix chol() {
		return Chol.INSTANCE.calc(this);
	}

	@Override
	public Matrix divide(final double factor) throws MatrixException {
		final PhysicalStore<Double> retVal = PrimitiveDenseStore.FACTORY
				.makeZero((int) this.getRowCount(), (int) this.getColumnCount());

		retVal.fillMatching(matrix, PrimitiveFunction.DIVIDE, factor);

		Matrix result = new OjalgoDenseDoubleMatrix2D(retVal);
		Annotation a = getAnnotation();
		if (a != null) {
			result.setAnnotation(a.clone());
		}
		return result;
	}

	@Override
	public Matrix divide(final Matrix m) throws MatrixException {
		if (m instanceof OjalgoDenseDoubleMatrix2D) {

			final PrimitiveDenseStore tmpArg = ((OjalgoDenseDoubleMatrix2D) m)
					.getWrappedObject();

			final PhysicalStore<Double> retVal = PrimitiveDenseStore.FACTORY
					.makeZero((int) this.getRowCount(), (int) this
							.getColumnCount());

			retVal.fillMatching(matrix, PrimitiveFunction.DIVIDE, tmpArg);

			Matrix result = new OjalgoDenseDoubleMatrix2D(retVal);
			Annotation a = getAnnotation();
			if (a != null) {
				result.setAnnotation(a.clone());
			}
			return result;

		} else {

			return super.divide(m);
		}
	}

	@Override
	public Matrix[] eig() {
		return Eig.INSTANCE.calc(this);
	}

	public final BasicMatrix getBasicMatrix() {
		return new PrimitiveMatrix(matrix);
	}

	public double getDouble(final int row, final int column) {
		return matrix.doubleValue(row, column);
	}

	public double getDouble(final long row, final long column) {
		return matrix.doubleValue((int) row, (int) column);
	}

	public long[] getSize() {
		return new long[] { matrix.getRowDim(), matrix.getColDim() };
	}

	public PrimitiveDenseStore getWrappedObject() {
		return matrix;
	}

	@Override
	public Matrix inv() {
		return Inv.INSTANCE.calc(this);
	}

	@Override
	public Matrix invSPD() {
		return InvSPD.INSTANCE.calc(this);
	}

	public double det() {
		final org.ojalgo.matrix.decomposition.LU<Double> lu = LUDecomposition
				.makePrimitive();
		lu.compute(matrix);
		return lu.getDeterminant();
	}

	@Override
	public Matrix[] lu() {
		return LU.INSTANCE.calc(this);
	}

	@Override
	public Matrix minus(final double factor) throws MatrixException {
		final PhysicalStore<Double> retVal = PrimitiveDenseStore.FACTORY
				.makeZero((int) this.getRowCount(), (int) this.getColumnCount());

		retVal.fillMatching(matrix, PrimitiveFunction.SUBTRACT, factor);

		return new OjalgoDenseDoubleMatrix2D(retVal);
	}

	@Override
	public Matrix minus(final Matrix m) throws MatrixException {
		if (m instanceof OjalgoDenseDoubleMatrix2D) {

			final PrimitiveDenseStore tmpArg = ((OjalgoDenseDoubleMatrix2D) m)
					.getWrappedObject();

			final PhysicalStore<Double> retVal = PrimitiveDenseStore.FACTORY
					.makeZero((int) this.getRowCount(), (int) this
							.getColumnCount());

			retVal.fillMatching(matrix, PrimitiveFunction.SUBTRACT, tmpArg);

			Matrix result = new OjalgoDenseDoubleMatrix2D(retVal);
			Annotation a = getAnnotation();
			if (a != null) {
				result.setAnnotation(a.clone());
			}
			return result;

		} else {

			return super.minus(m);
		}
	}

	@Override
	public Matrix mtimes(final Matrix m) {
		if (m instanceof OjalgoDenseDoubleMatrix2D) {
			final PrimitiveDenseStore mo = ((OjalgoDenseDoubleMatrix2D) m)
					.getWrappedObject();
			final PrimitiveDenseStore result = (PrimitiveDenseStore) matrix
					.multiplyRight(mo);
			return new OjalgoDenseDoubleMatrix2D(result);
		} else {
			return super.mtimes(m);
		}
	}

	@Override
	public Matrix plus(final double factor) throws MatrixException {
		final PhysicalStore<Double> retVal = PrimitiveDenseStore.FACTORY
				.makeZero((int) this.getRowCount(), (int) this.getColumnCount());

		retVal.fillMatching(matrix, PrimitiveFunction.ADD, factor);

		Matrix result = new OjalgoDenseDoubleMatrix2D(retVal);
		Annotation a = getAnnotation();
		if (a != null) {
			result.setAnnotation(a.clone());
		}
		return result;
	}

	@Override
	public Matrix plus(final Matrix m) throws MatrixException {
		if (m instanceof OjalgoDenseDoubleMatrix2D) {

			final PrimitiveDenseStore tmpArg = ((OjalgoDenseDoubleMatrix2D) m)
					.getWrappedObject();

			final PhysicalStore<Double> retVal = PrimitiveDenseStore.FACTORY
					.makeZero((int) this.getRowCount(), (int) this
							.getColumnCount());

			retVal.fillMatching(matrix, PrimitiveFunction.ADD, tmpArg);

			Matrix result = new OjalgoDenseDoubleMatrix2D(retVal);
			Annotation a = getAnnotation();
			if (a != null) {
				result.setAnnotation(a.clone());
			}
			return result;

		} else {

			return super.plus(m);
		}
	}

	@Override
	public Matrix[] qr() {
		return QR.INSTANCE.calc(this);
	}

	public void setDouble(final double value, final int row, final int column) {
		matrix.set(row, column, value);
	}

	public void setDouble(final double value, final long row, final long column) {
		matrix.set((int) row, (int) column, value);
	}

	public void setWrappedObject(final MatrixStore<Double> object) {
		if (object instanceof PrimitiveDenseStore) {
			matrix = (PrimitiveDenseStore) object;
		} else {
			matrix = (PrimitiveDenseStore) PrimitiveDenseStore.FACTORY
					.copy(object);
		}
	}

	@Override
	public Matrix solve(final Matrix b) {
		return Solve.INSTANCE.calc(this, b);
	}

	@Override
	public Matrix[] svd() {
		return SVD.INSTANCE.calc(this);
	}

	@Override
	public Matrix times(final double factor) throws MatrixException {
		final PhysicalStore<Double> retVal = PrimitiveDenseStore.FACTORY
				.makeZero((int) this.getRowCount(), (int) this.getColumnCount());

		retVal.fillMatching(matrix, PrimitiveFunction.MULTIPLY, factor);

		Matrix result = new OjalgoDenseDoubleMatrix2D(retVal);
		Annotation a = getAnnotation();
		if (a != null) {
			result.setAnnotation(a.clone());
		}
		return result;
	}

	@Override
	public Matrix times(final Matrix m) throws MatrixException {
		if (m instanceof OjalgoDenseDoubleMatrix2D) {

			final PrimitiveDenseStore tmpArg = ((OjalgoDenseDoubleMatrix2D) m)
					.getWrappedObject();

			final PhysicalStore<Double> retVal = PrimitiveDenseStore.FACTORY
					.makeZero((int) this.getRowCount(), (int) this
							.getColumnCount());

			retVal.fillMatching(matrix, PrimitiveFunction.MULTIPLY, tmpArg);

			Matrix result = new OjalgoDenseDoubleMatrix2D(retVal);
			Annotation a = getAnnotation();
			if (a != null) {
				result.setAnnotation(a.clone());
			}
			return result;

		} else {

			return super.times(m);
		}
	}

	@Override
	public double[][] toDoubleArray() throws MatrixException {
		return ArrayUtils.toRawCopyOf((Access2D<Double>) matrix);
	}

	@Override
	public Matrix transpose() {
		return new OjalgoDenseDoubleMatrix2D(matrix.transpose());
	}

	private void readObject(final ObjectInputStream s) throws IOException,
			ClassNotFoundException {
		s.defaultReadObject();
		final double[][] data = (double[][]) s.readObject();
		matrix = (PrimitiveDenseStore) PrimitiveDenseStore.FACTORY
				.copy(ArrayUtils.wrapAccess2D(data));
	}

	private void writeObject(final ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeObject(this.toDoubleArray());
	}

}

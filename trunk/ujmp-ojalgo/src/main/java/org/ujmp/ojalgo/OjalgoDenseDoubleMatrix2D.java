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

package org.ujmp.ojalgo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.ojalgo.matrix.BasicMatrix;
import org.ojalgo.matrix.PrimitiveMatrix;
import org.ojalgo.matrix.decomposition.Cholesky;
import org.ojalgo.matrix.decomposition.CholeskyDecomposition;
import org.ojalgo.matrix.decomposition.Eigenvalue;
import org.ojalgo.matrix.decomposition.EigenvalueDecomposition;
import org.ojalgo.matrix.decomposition.LU;
import org.ojalgo.matrix.decomposition.LUDecomposition;
import org.ojalgo.matrix.decomposition.QR;
import org.ojalgo.matrix.decomposition.QRDecomposition;
import org.ojalgo.matrix.decomposition.SingularValue;
import org.ojalgo.matrix.decomposition.SingularValueDecomposition;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.PrimitiveDenseStore;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

public class OjalgoDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D
		implements Wrapper<PrimitiveDenseStore> {

	private static final long serialVersionUID = 6628172130438716653L;

	private transient PrimitiveDenseStore matrix = null;

	public OjalgoDenseDoubleMatrix2D(final long... size) {
		matrix = (PrimitiveDenseStore) PrimitiveDenseStore.FACTORY.makeEmpty(
				(int) size[ROW], (int) size[COLUMN]);
	}

	public OjalgoDenseDoubleMatrix2D(final Matrix m) {
		this(m.getSize());
		for (final long[] c : m.allCoordinates()) {
			this.setAsDouble(m.getAsDouble(c), c);
		}
	}

	public OjalgoDenseDoubleMatrix2D(final PrimitiveDenseStore matrix) {
		this.matrix = matrix;
	}

	public OjalgoDenseDoubleMatrix2D(MatrixStore<Double> m) {
		this.matrix = (PrimitiveDenseStore) PrimitiveDenseStore.FACTORY
				.copyStore(m);
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

	public void setDouble(final double value, final int row, final int column) {
		matrix.set(row, column, value);
	}

	public void setDouble(final double value, final long row, final long column) {
		matrix.set((int) row, (int) column, value);
	}

	public void setWrappedObject(final PrimitiveDenseStore object) {
		matrix = object;
	}

	@Override
	public double[][] toDoubleArray() throws MatrixException {
		return matrix.toRawCopy();
	}

	@Override
	public Matrix transpose() {
		return new OjalgoDenseDoubleMatrix2D(matrix.transpose());
	}

	@Override
	public Matrix inv() {
		return new OjalgoDenseDoubleMatrix2D(
				(PrimitiveDenseStore) LUDecomposition.makePrimitive().invert(
						matrix));
	}

	@Override
	public Matrix[] qr() {
		QR<Double> qr = QRDecomposition.makePrimitive();
		qr.compute(matrix);
		Matrix q = new OjalgoDenseDoubleMatrix2D(qr.getQ());
		Matrix r = new OjalgoDenseDoubleMatrix2D(qr.getR());
		return new Matrix[] { q, r };
	}

	@Override
	public Matrix[] evd() {
		Eigenvalue<Double> evd = EigenvalueDecomposition.makeJama();
		evd.compute(matrix);
		Matrix v = new OjalgoDenseDoubleMatrix2D(evd.getV());
		Matrix d = new OjalgoDenseDoubleMatrix2D(evd.getD());
		return new Matrix[] { v, d };
	}

	// SVD does not work with example test case
	// @Override
	// public Matrix[] svd() {
	// SingularValue<Double> svd = SingularValueDecomposition.makePrimitive();
	// svd.compute(matrix);
	// Matrix u = new OjalgoDenseDoubleMatrix2D(svd.getQ1());
	// Matrix s = new OjalgoDenseDoubleMatrix2D(svd.getD());
	// Matrix v = new OjalgoDenseDoubleMatrix2D(svd.getQ2());
	// return new Matrix[] { u, s, v };
	// }

	@Override
	public Matrix chol() {
		Cholesky<Double> chol = CholeskyDecomposition.makePrimitive();
		chol.compute(matrix);
		Matrix r = new OjalgoDenseDoubleMatrix2D(chol.getR());
		return r;
	}

	@Override
	public Matrix[] lu() {
		LU<Double> lu = LUDecomposition.makePrimitive();
		lu.compute(matrix);
		Matrix l = new OjalgoDenseDoubleMatrix2D(lu.getL());
		Matrix u = new OjalgoDenseDoubleMatrix2D(lu.getU());
		return new Matrix[] { l, u };
	}

	private void readObject(final ObjectInputStream s) throws IOException,
			ClassNotFoundException {
		s.defaultReadObject();
		final double[][] data = (double[][]) s.readObject();
		matrix = (PrimitiveDenseStore) PrimitiveDenseStore.FACTORY
				.copyRaw(data);
	}

	private void writeObject(final ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeObject(this.toDoubleArray());
	}

}
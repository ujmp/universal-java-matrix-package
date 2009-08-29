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
import org.ojalgo.matrix.decomposition.LUDecomposition;
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
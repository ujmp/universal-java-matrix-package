/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

package org.ujmp.owlpack;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.interfaces.Wrapper;

import edu.rice.linpack.Matrix.DMatrix.DFull;

public class OwlpackDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D
		implements Wrapper<DFull> {
	private static final long serialVersionUID = 1341952001270932703L;

	private transient DFull matrix = null;

	public OwlpackDenseDoubleMatrix2D(final long... size) {
		matrix = new DFull((int) size[ROW], (int) size[COLUMN]);
	}

	public OwlpackDenseDoubleMatrix2D(final Matrix m) {
		this(m.getSize());
		for (final long[] c : m.allCoordinates()) {
			this.setAsDouble(m.getAsDouble(c), c);
		}
	}

	public OwlpackDenseDoubleMatrix2D(final DFull matrix) {
		this.matrix = matrix;
	}

	public double getDouble(final int row, final int column) {
		return matrix.getElem(row, column);
	}

	public double getDouble(final long row, final long column) {
		return matrix.getElem((int) row, (int) column);
	}

	public long[] getSize() {
		return new long[] { matrix.numofRows(), matrix.numofCols() };
	}

	public DFull getWrappedObject() {
		return matrix;
	}

	@Override
	public Matrix mtimes(final Matrix m) {
		if (m instanceof OwlpackDenseDoubleMatrix2D) {
			final DFull mo = ((OwlpackDenseDoubleMatrix2D) m)
					.getWrappedObject();
			final DFull result = matrix.matMult(mo);
			return new OwlpackDenseDoubleMatrix2D(result);
		} else {
			return super.mtimes(m);
		}
	}

	public void setDouble(final double value, final int row, final int column) {
		matrix.setElem(row, column, value);
	}

	public void setDouble(final double value, final long row, final long column) {
		matrix.setElem((int) row, (int) column, value);
	}

	public void setWrappedObject(final DFull object) {
		matrix = object;
	}

	@Override
	public Matrix transpose() {
		DFull result = new DFull((int) getColumnCount(), (int) getRowCount());
		matrix.transpose(result);
		return new OwlpackDenseDoubleMatrix2D(result);
	}

	@Override
	public Matrix inv() {
		DFull result = new DFull(matrix);
		result.inverse();
		return new OwlpackDenseDoubleMatrix2D(result);
	}

	@Override
	public Matrix[] svd() {
		int p = (int) getRowCount();
		int n = (int) getColumnCount();
		double[] S = new double[Math.min(n + 1, p)];
		double[] E = new double[p];
		DFull U = new DFull(n, Math.min(n, p));
		DFull V = new DFull(p, p);
		matrix.svDecompose(S, E, U, V, 2);
		Matrix u = new OwlpackDenseDoubleMatrix2D(U);
		Matrix v = new OwlpackDenseDoubleMatrix2D(V);
		Matrix s = new OwlpackDenseDoubleMatrix2D(S.length, S.length);
		for (int i = 0; i < S.length; i++) {
			s.setAsDouble(S[i], i, i);
		}
		return new Matrix[] { u, s, v };
	}

	private void readObject(final ObjectInputStream s) throws IOException,
			ClassNotFoundException {
		s.defaultReadObject();
		final double[][] data = (double[][]) s.readObject();
		int rows = data.length;
		int cols = data[0].length;
		matrix = new DFull(rows, cols);
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				setDouble(data[r][c], r, c);
			}
		}
	}

	private void writeObject(final ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeObject(this.toDoubleArray());
	}

}
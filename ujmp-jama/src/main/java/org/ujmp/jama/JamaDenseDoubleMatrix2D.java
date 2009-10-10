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

package org.ujmp.jama;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

import Jama.LUDecomposition;
import Jama.QRDecomposition;
import Jama.SingularValueDecomposition;

public class JamaDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D
		implements Wrapper<Jama.Matrix> {
	private static final long serialVersionUID = -6065454603299978242L;

	private Jama.Matrix matrix = null;

	public JamaDenseDoubleMatrix2D(long... size) {
		this.matrix = new Jama.Matrix((int) size[ROW], (int) size[COLUMN]);
	}

	public JamaDenseDoubleMatrix2D(Jama.Matrix matrix) {
		this.matrix = matrix;
	}

	public JamaDenseDoubleMatrix2D(Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setAsDouble(source.getAsDouble(c), c);
		}
	}

	public static Jama.Matrix identity(int m, int n) {
		Jama.Matrix A = new Jama.Matrix(m, n);
		double[][] X = A.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X[i][j] = (i == j ? 1.0 : 0.0);
			}
		}
		return A;
	}

	public Matrix inv() throws MatrixException {
		return new JamaDenseDoubleMatrix2D(matrix.inverse());
	}

	public Matrix[] svd() throws MatrixException {
		if (getColumnCount() > getRowCount()) {
			SingularValueDecomposition svd = new SingularValueDecomposition(
					matrix.transpose());
			Matrix u = new JamaDenseDoubleMatrix2D(svd.getV());
			Matrix s = new JamaDenseDoubleMatrix2D(svd.getS().transpose());
			Matrix v = new JamaDenseDoubleMatrix2D(svd.getU());
			return new Matrix[] { u, s, v };
		} else {
			SingularValueDecomposition svd = new SingularValueDecomposition(
					matrix);
			Matrix u = new JamaDenseDoubleMatrix2D(svd.getU());
			Matrix s = new JamaDenseDoubleMatrix2D(svd.getS());
			Matrix v = new JamaDenseDoubleMatrix2D(svd.getV());
			return new Matrix[] { u, s, v };
		}
	}

	public double getDouble(long row, long column) {
		return matrix.get((int) row, (int) column);
	}

	public double getDouble(int row, int column) {
		return matrix.get(row, column);
	}

	public long[] getSize() {
		return new long[] { matrix.getRowDimension(),
				matrix.getColumnDimension() };
	}

	public void setDouble(double value, long row, long column) {
		matrix.set((int) row, (int) column, value);
	}

	public void setDouble(double value, int row, int column) {
		matrix.set(row, column, value);
	}

	public Jama.Matrix getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(Jama.Matrix object) {
		this.matrix = object;
	}

	public final Matrix copy() throws MatrixException {
		Matrix m = new JamaDenseDoubleMatrix2D(matrix.copy());
		if (getAnnotation() != null) {
			m.setAnnotation(getAnnotation().clone());
		}
		return m;
	}

	public Matrix transpose() {
		return new JamaDenseDoubleMatrix2D(matrix.transpose());
	}

	public Matrix[] qr() {
		if (isSquare()) {
			QRDecomposition qr = new QRDecomposition(matrix);
			Matrix q = new JamaDenseDoubleMatrix2D(qr.getQ());
			Matrix r = new JamaDenseDoubleMatrix2D(qr.getR());
			return new Matrix[] { q, r };
		} else {
			throw new MatrixException(
					"QR decomposition only works for square matrices");
		}
	}

	public Matrix[] lu() {
		LUDecomposition lu = new LUDecomposition(matrix);
		Matrix l = new JamaDenseDoubleMatrix2D(lu.getL());
		Matrix u = new JamaDenseDoubleMatrix2D(lu.getU());
		return new Matrix[] { l, u };
	}

	public Matrix mtimes(Matrix m) {
		if (m instanceof JamaDenseDoubleMatrix2D) {
			return new JamaDenseDoubleMatrix2D(matrix
					.times(((JamaDenseDoubleMatrix2D) m).matrix));
		} else {
			return super.mtimes(m);
		}
	}

	public Matrix times(double value) {
		return new JamaDenseDoubleMatrix2D(matrix.times(value));
	}

	public Matrix divide(double value) {
		return new JamaDenseDoubleMatrix2D(matrix.times(1.0 / value));
	}

}

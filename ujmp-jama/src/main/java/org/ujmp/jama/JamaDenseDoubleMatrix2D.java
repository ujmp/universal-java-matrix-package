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

package org.ujmp.jama;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.mapmatrix.MapMatrix;

import Jama.CholeskyDecomposition;
import Jama.EigenvalueDecomposition;
import Jama.LUDecomposition;
import Jama.QRDecomposition;
import Jama.SingularValueDecomposition;

public class JamaDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D implements Wrapper<Jama.Matrix> {
	private static final long serialVersionUID = -6065454603299978242L;

	public static final JamaDenseDoubleMatrix2DFactory Factory = new JamaDenseDoubleMatrix2DFactory();

	private final Jama.Matrix matrix;

	public JamaDenseDoubleMatrix2D(int rows, int columns) {
		super(rows, columns);
		this.matrix = new Jama.Matrix(rows, columns);
	}

	public JamaDenseDoubleMatrix2D(Jama.Matrix matrix) {
		super(matrix.getRowDimension(), matrix.getColumnDimension());
		this.matrix = matrix;
	}

	public JamaDenseDoubleMatrix2D(Matrix source) {
		super(source.getRowCount(), source.getColumnCount());
		this.matrix = new Jama.Matrix((int) source.getRowCount(), (int) source.getColumnCount());
		for (long[] c : source.availableCoordinates()) {
			setDouble(source.getAsDouble(c), c);
		}
		if (source.getMetaData() != null) {
			setMetaData(source.getMetaData().clone());
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

	public Matrix inv() {
		return new JamaDenseDoubleMatrix2D(matrix.inverse());
	}

	public Matrix invSPD() {
		CholeskyDecomposition chol = new CholeskyDecomposition(matrix);
		return new JamaDenseDoubleMatrix2D(chol.solve(Jama.Matrix.identity(matrix.getRowDimension(),
				matrix.getRowDimension())));
	}

	public Matrix[] svd() {
		if (getColumnCount() > getRowCount()) {
			SingularValueDecomposition svd = new SingularValueDecomposition(matrix.transpose());
			Matrix u = new JamaDenseDoubleMatrix2D(svd.getV());
			Matrix s = new JamaDenseDoubleMatrix2D(svd.getS().transpose());
			Matrix v = new JamaDenseDoubleMatrix2D(svd.getU());
			return new Matrix[] { u, s, v };
		} else {
			SingularValueDecomposition svd = new SingularValueDecomposition(matrix);
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

	public void setDouble(double value, long row, long column) {
		matrix.set((int) row, (int) column, value);
	}

	public void setDouble(double value, int row, int column) {
		matrix.set(row, column, value);
	}

	public Jama.Matrix getWrappedObject() {
		return matrix;
	}

	public final Matrix copy() {
		Matrix m = new JamaDenseDoubleMatrix2D(matrix.copy());
		if (getMetaData() != null) {
			m.setMetaData(getMetaData().clone());
		}
		return m;
	}

	public Matrix transpose() {
		return new JamaDenseDoubleMatrix2D(matrix.transpose());
	}

	public Matrix[] qr() {
		if (getRowCount() >= getColumnCount()) {
			QRDecomposition qr = new QRDecomposition(matrix);
			Matrix q = new JamaDenseDoubleMatrix2D(qr.getQ());
			Matrix r = new JamaDenseDoubleMatrix2D(qr.getR());
			return new Matrix[] { q, r };
		} else {
			throw new RuntimeException("QR decomposition only works for matrices m>=n");
		}
	}

	public Matrix[] lu() {
		LUDecomposition lu = new LUDecomposition(matrix);
		Matrix l = new JamaDenseDoubleMatrix2D(lu.getL());
		Matrix u = new JamaDenseDoubleMatrix2D(lu.getU());
		int m = (int) getRowCount();
		int[] piv = lu.getPivot();
		Matrix p = new JamaDenseDoubleMatrix2D(m, m);
		for (int i = 0; i < m; i++) {
			p.setAsDouble(1, i, piv[i]);
		}
		return new Matrix[] { l, u, p };
	}

	public Matrix[] eig() {
		EigenvalueDecomposition eig = new EigenvalueDecomposition(matrix);
		Matrix v = new JamaDenseDoubleMatrix2D(eig.getV());
		Matrix d = new JamaDenseDoubleMatrix2D(eig.getD());
		return new Matrix[] { v, d };
	}

	public Matrix chol() {
		CholeskyDecomposition chol = new CholeskyDecomposition(matrix);
		Matrix r = new JamaDenseDoubleMatrix2D(chol.getL());
		return r;
	}

	public Matrix mtimes(Matrix m) {
		if (m instanceof JamaDenseDoubleMatrix2D) {
			return new JamaDenseDoubleMatrix2D(matrix.times(((JamaDenseDoubleMatrix2D) m).matrix));
		} else {
			return super.mtimes(m);
		}
	}

	public Matrix times(double value) {
		Matrix result = new JamaDenseDoubleMatrix2D(matrix.times(value));
		MapMatrix<Object, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
	}

	public Matrix divide(double value) {
		Matrix result = new JamaDenseDoubleMatrix2D(matrix.times(1.0 / value));
		MapMatrix<Object, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
	}

	public double det() {
		return matrix.det();
	}

	public Matrix plus(Matrix m) {
		if (m instanceof JamaDenseDoubleMatrix2D) {
			Matrix result = new JamaDenseDoubleMatrix2D(matrix.plus(((JamaDenseDoubleMatrix2D) m).matrix));
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
		if (m instanceof JamaDenseDoubleMatrix2D) {
			Matrix result = new JamaDenseDoubleMatrix2D(matrix.minus(((JamaDenseDoubleMatrix2D) m).matrix));
			MapMatrix<Object, Object> a = getMetaData();
			if (a != null) {
				result.setMetaData(a.clone());
			}
			return result;
		} else {
			return super.minus(m);
		}
	}

	public Matrix solve(Matrix b) {
		if (b instanceof JamaDenseDoubleMatrix2D) {
			JamaDenseDoubleMatrix2D b2 = (JamaDenseDoubleMatrix2D) b;
			Jama.Matrix x = matrix.solve(b2.matrix);
			return new JamaDenseDoubleMatrix2D(x);
		} else {
			return super.solve(b);
		}
	}

	public Matrix solveSPD(Matrix b) {
		if (b instanceof JamaDenseDoubleMatrix2D) {
			JamaDenseDoubleMatrix2D b2 = (JamaDenseDoubleMatrix2D) b;
			CholeskyDecomposition chol = new CholeskyDecomposition(matrix);
			return new JamaDenseDoubleMatrix2D(chol.solve(b2.matrix));
		} else {
			return super.solve(b);
		}
	}

	public JamaDenseDoubleMatrix2DFactory getFactory() {
		return Factory;
	}

}

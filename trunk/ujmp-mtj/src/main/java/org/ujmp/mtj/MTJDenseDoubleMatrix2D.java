/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

package org.ujmp.mtj;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import no.uib.cipr.matrix.DenseCholesky;
import no.uib.cipr.matrix.DenseLU;
import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.EVD;
import no.uib.cipr.matrix.Matrices;
import no.uib.cipr.matrix.QR;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.mtj.calculation.Inv;
import org.ujmp.mtj.calculation.SVD;

public class MTJDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D
		implements Wrapper<DenseMatrix> {
	private static final long serialVersionUID = -2386081646062313108L;

	private transient DenseMatrix matrix = null;

	public MTJDenseDoubleMatrix2D(DenseMatrix m) {
		this.matrix = m;
	}

	public MTJDenseDoubleMatrix2D(no.uib.cipr.matrix.Matrix m) {
		this.matrix = new DenseMatrix(m);
	}

	public MTJDenseDoubleMatrix2D(Matrix m) throws MatrixException {
		if (m instanceof MTJDenseDoubleMatrix2D) {
			this.matrix = ((MTJDenseDoubleMatrix2D) m).matrix.copy();
		} else {
			this.matrix = new DenseMatrix(m.toDoubleArray());
		}
	}

	public MTJDenseDoubleMatrix2D(long... size) {
		this.matrix = new DenseMatrix((int) size[ROW], (int) size[COLUMN]);
	}

	public Matrix[] svd() throws MatrixException {
		return SVD.INSTANCE.calc(this);
	}

	public Matrix[] qr() throws MatrixException {
		if (getRowCount() >= getColumnCount()) {
			try {
				QR qr = QR.factorize(getWrappedObject());
				Matrix q = new MTJDenseDoubleMatrix2D(qr.getQ());
				Matrix r = new MTJDenseDoubleMatrix2D(qr.getR());
				return new Matrix[] { q, r };
			} catch (Exception e) {
				throw new MatrixException(e);
			}
		} else {
			throw new MatrixException("only allowed for matrices m>=n");
		}
	}

	public Matrix[] lu() throws MatrixException {
		try {
			DenseLU lu = DenseLU.factorize(getWrappedObject());
			Matrix l = new MTJDenseDoubleMatrix2D(lu.getL());
			Matrix u = new MTJDenseDoubleMatrix2D(lu.getU());
			int m = (int) getRowCount();
			int[] piv = lu.getPivots();
			Matrix p = new MTJDenseDoubleMatrix2D(m, m);
			// pivots seem to be broken
			// http://code.google.com/p/matrix-toolkits-java/issues/detail?id=1
			// for (int i = 0; i < m; i++) {
			// p.setAsDouble(1, i, piv[i]);
			// }
			p.eye(Ret.ORIG);
			return new Matrix[] { l, u, p };
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public Matrix chol() throws MatrixException {
		try {
			DenseCholesky chol = DenseCholesky.factorize(getWrappedObject());
			Matrix l = new MTJDenseDoubleMatrix2D(chol.getL());
			return l;
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public Matrix[] eig() throws MatrixException {
		try {
			EVD evd = EVD.factorize(getWrappedObject());
			Matrix v = new MTJDenseDoubleMatrix2D(evd.getRightEigenvectors());
			int m = (int) getRowCount();
			double[] evds = evd.getRealEigenvalues();
			Matrix d = new MTJDenseDoubleMatrix2D(m, m);
			for (int i = 0; i < m; i++) {
				d.setAsDouble(evds[i], i, i);
			}
			return new Matrix[] { v, d };
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public double getDouble(long row, long column) {
		return matrix.getData()[(int) (row + column * matrix.numRows())];
	}

	public double getDouble(int row, int column) {
		return matrix.getData()[(row + column * matrix.numRows())];
	}

	public long[] getSize() {
		return new long[] { matrix.numRows(), matrix.numColumns() };
	}

	public void setDouble(double value, long row, long column) {
		matrix.getData()[(int) (row + column * matrix.numRows())] = value;
	}

	public void setDouble(double value, int row, int column) {
		matrix.getData()[(row + column * matrix.numRows())] = value;
	}

	public Matrix transpose() {
		DenseMatrix ret = new DenseMatrix((int) getColumnCount(),
				(int) getRowCount());
		return new MTJDenseDoubleMatrix2D((DenseMatrix) matrix.transpose(ret));
	}

	public Matrix inv() {
		return new Inv(this).calcNew();
	}

	public DenseMatrix getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(DenseMatrix object) {
		this.matrix = object;
	}

	private void readObject(ObjectInputStream s) throws IOException,
			ClassNotFoundException {
		s.defaultReadObject();
		double[][] values = (double[][]) s.readObject();
		matrix = new DenseMatrix(values);
	}

	private void writeObject(ObjectOutputStream s) throws IOException,
			MatrixException {
		s.defaultWriteObject();
		s.writeObject(this.toDoubleArray());
	}

	public Matrix mtimes(Matrix m2) throws MatrixException {
		if (m2 instanceof MTJDenseDoubleMatrix2D) {
			DenseMatrix a = matrix;
			DenseMatrix b = ((MTJDenseDoubleMatrix2D) m2).getWrappedObject();
			DenseMatrix c = new DenseMatrix(a.numRows(), b.numColumns());
			try {
				a.mult(b, c);
				return new MTJDenseDoubleMatrix2D(c);
			} catch (Exception e) {
				// sometimes BLAS cannot be found. Don't know why. Use direct
				// method instead.
				double[] Bd = ((DenseMatrix) b).getData(), Cd = ((DenseMatrix) c)
						.getData();
				org.netlib.blas.Dgemm.dgemm("N", "N", c.numRows(), c
						.numColumns(), a.numColumns(), 1, a.getData(), 0, Math
						.max(1, a.numRows()), Bd, 0, Math.max(1, b.numRows()),
						1, Cd, 0, Math.max(1, c.numRows()));
				return new MTJDenseDoubleMatrix2D(c);
			}
		}
		return super.mtimes(m2);
	}

	public Matrix plus(Matrix m2) throws MatrixException {
		if (m2 instanceof MTJDenseDoubleMatrix2D) {
			DenseMatrix ret = matrix.copy();
			ret.add(((MTJDenseDoubleMatrix2D) m2).getWrappedObject());
			return new MTJDenseDoubleMatrix2D(ret);
		} else {
			return super.plus(m2);
		}
	}

	public Matrix times(double f) throws MatrixException {
		DenseMatrix ret = matrix.copy();
		ret.scale(f);
		return new MTJDenseDoubleMatrix2D(ret);
	}

	public Matrix divide(double f) throws MatrixException {
		DenseMatrix ret = matrix.copy();
		ret.scale(1.0 / f);
		return new MTJDenseDoubleMatrix2D(ret);
	}

	public Matrix copy() {
		Matrix m = new MTJDenseDoubleMatrix2D(matrix.copy());
		if (getAnnotation() != null) {
			m.setAnnotation(getAnnotation().clone());
		}
		return m;
	}

	public Matrix solve(Matrix b) {
		if (b instanceof MTJDenseDoubleMatrix2D) {
			MTJDenseDoubleMatrix2D b2 = (MTJDenseDoubleMatrix2D) b;
			DenseMatrix x = new DenseMatrix((int) getColumnCount(), (int) b2
					.getColumnCount());
			matrix.solve(b2.matrix, x);
			return new MTJDenseDoubleMatrix2D(x);
		} else {
			return super.solve(b);
		}
	}

	public Matrix invSPD() {
		DenseCholesky chol = DenseCholesky.factorize(getWrappedObject());
		return new MTJDenseDoubleMatrix2D(chol.solve(Matrices.identity(matrix
				.numRows())));
	}
}

/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrices;
import no.uib.cipr.matrix.SVD;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.doublecalculation.Calculation.Ret;
import org.ujmp.core.doublematrix.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

public class MTJDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D implements Wrapper<DenseMatrix> {
	private static final long serialVersionUID = -2386081646062313108L;

	private transient DenseMatrix matrix = null;

	public MTJDenseDoubleMatrix2D(DenseMatrix m) {
		this.matrix = m;
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

	@Override
	public Matrix inv() {
		DenseMatrix A = this.getWrappedObject();
		DenseMatrix I = Matrices.identity((int) getColumnCount());
		DenseMatrix AI = I.copy();
		return new MTJDenseDoubleMatrix2D((DenseMatrix) A.solve(I, AI));
	}

	@Override
	public Matrix[] svd() throws MatrixException {
		try {
			SVD svd = SVD.factorize(getWrappedObject());
			Matrix u = new MTJDenseDoubleMatrix2D(svd.getU());
			Matrix v = new MTJDenseDoubleMatrix2D(svd.getVt()).transpose();
			double[] svs = svd.getS();
			Matrix s = MatrixFactory.zeros(getSize());
			for (int i = (int) Math.min(s.getRowCount(), s.getColumnCount()); --i >= 0;) {
				s.setAsDouble(svs[i], i, i);
			}

			return new Matrix[] { u, s, v };
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public double getDouble(long row, long column) {
		return matrix.getData()[(int) (row + column * matrix.numRows())];
	}

	public long[] getSize() {
		return new long[] { matrix.numRows(), matrix.numColumns() };
	}

	public void setDouble(double value, long row, long column) {
		matrix.getData()[(int) (row + column * matrix.numRows())] = value;
	}

	@Override
	public Matrix transpose() {
		// TODO: maybe this can be made faster using System.arraycopy()
		DenseMatrix ret = new DenseMatrix((int) getColumnCount(), (int) getRowCount());
		return new MTJDenseDoubleMatrix2D((DenseMatrix) matrix.transpose(ret));
	}

	public DenseMatrix getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(DenseMatrix object) {
		this.matrix = object;
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		double[][] values = (double[][]) s.readObject();
		matrix = new DenseMatrix(values);
	}

	private void writeObject(ObjectOutputStream s) throws IOException, MatrixException {
		s.defaultWriteObject();
		s.writeObject(this.toDoubleArray());
	}

	@Override
	public Matrix mtimes(Matrix m2) throws MatrixException {
		if (m2 instanceof MTJDenseDoubleMatrix2D) {
			DenseMatrix a = matrix;
			DenseMatrix b = ((MTJDenseDoubleMatrix2D) m2).getWrappedObject();
			DenseMatrix c = new DenseMatrix(a.numRows(), b.numColumns());
			a.mult(b, c);
			return new MTJDenseDoubleMatrix2D(c);
		} else {
			return super.mtimes(m2);
		}
	}

	@Override
	public Matrix plus(Matrix m2) throws MatrixException {
		if (m2 instanceof MTJDenseDoubleMatrix2D) {
			DenseMatrix a = new DenseMatrix(matrix.numRows(), matrix.numColumns());
			System.arraycopy(matrix.getData(), 0, a.getData(), 0, matrix.getData().length);
			DenseMatrix b = ((MTJDenseDoubleMatrix2D) m2).getWrappedObject();
			for (int i = a.getData().length; --i >= 0;) {
				a.getData()[i] += b.getData()[i];
			}
			return new MTJDenseDoubleMatrix2D(a);
		} else {
			return super.plus(m2);
		}
	}

	@Override
	public Matrix times(Matrix m2) throws MatrixException {
		if (m2 instanceof MTJDenseDoubleMatrix2D) {
			DenseMatrix a = new DenseMatrix(matrix.numRows(), matrix.numColumns());
			System.arraycopy(matrix.getData(), 0, a.getData(), 0, matrix.getData().length);
			DenseMatrix b = ((MTJDenseDoubleMatrix2D) m2).getWrappedObject();
			for (int i = a.getData().length; --i >= 0;) {
				a.getData()[i] *= b.getData()[i];
			}
			return new MTJDenseDoubleMatrix2D(a);
		} else {
			return super.plus(m2);
		}
	}

	@Override
	public Matrix minus(Matrix m2) throws MatrixException {
		if (m2 instanceof MTJDenseDoubleMatrix2D) {
			DenseMatrix a = new DenseMatrix(matrix.numRows(), matrix.numColumns());
			System.arraycopy(matrix.getData(), 0, a.getData(), 0, matrix.getData().length);
			DenseMatrix b = ((MTJDenseDoubleMatrix2D) m2).getWrappedObject();
			for (int i = a.getData().length; --i >= 0;) {
				a.getData()[i] -= b.getData()[i];
			}
			return new MTJDenseDoubleMatrix2D(a);
		} else {
			return super.minus(m2);
		}
	}

	@Override
	public Matrix plus(Ret returnType, boolean ignoreNaN, double value) throws MatrixException {
		if (ignoreNaN) {
			switch (returnType) {
			case ORIG:
				return super.plus(returnType, ignoreNaN, value);
			case LINK:
				return super.plus(returnType, ignoreNaN, value);
			default:
				return super.plus(returnType, ignoreNaN, value);
			}
		} else {
			switch (returnType) {
			case ORIG:
				return super.plus(returnType, ignoreNaN, value);
			case LINK:
				return super.plus(returnType, ignoreNaN, value);
			default:
				return super.plus(returnType, ignoreNaN, value);
			}
		}
	}

	@Override
	public Matrix minus(Ret returnType, boolean ignoreNaN, double value) throws MatrixException {
		if (ignoreNaN) {
			switch (returnType) {
			case ORIG:
				return super.minus(returnType, ignoreNaN, value);
			case LINK:
				return super.minus(returnType, ignoreNaN, value);
			default:
				return super.minus(returnType, ignoreNaN, value);
			}
		} else {
			switch (returnType) {
			case ORIG:
				return super.minus(returnType, ignoreNaN, value);
			case LINK:
				return super.minus(returnType, ignoreNaN, value);
			default:
				return super.minus(returnType, ignoreNaN, value);
			}
		}
	}

	@Override
	public Matrix minus(Ret returnType, boolean ignoreNaN, Matrix m2) throws MatrixException {
		if (m2 instanceof MTJDenseDoubleMatrix2D) {
			DenseMatrix b = ((MTJDenseDoubleMatrix2D) m2).getWrappedObject();

			if (ignoreNaN) {
				switch (returnType) {
				case ORIG:
					return super.minus(returnType, ignoreNaN, m2);
				case LINK:
					return super.minus(returnType, ignoreNaN, m2);
				default:
					return super.minus(returnType, ignoreNaN, m2);
				}
			} else {
				switch (returnType) {
				case ORIG:
					for (int i = matrix.getData().length; --i >= 0;) {
						matrix.getData()[i] -= b.getData()[i];
					}
				case LINK:
					return super.minus(returnType, ignoreNaN, m2);
				default:
					return super.minus(returnType, ignoreNaN, m2);
				}
			}
		} else {
			return super.minus(returnType, ignoreNaN, m2);
		}
	}

	@Override
	public Matrix plus(Ret returnType, boolean ignoreNaN, Matrix m2) throws MatrixException {
		if (ignoreNaN) {
			switch (returnType) {
			case ORIG:
				return super.plus(returnType, ignoreNaN, m2);
			case LINK:
				return super.plus(returnType, ignoreNaN, m2);
			default:
				return super.plus(returnType, ignoreNaN, m2);
			}
		} else {
			switch (returnType) {
			case ORIG:
				return super.plus(returnType, ignoreNaN, m2);
			case LINK:
				return super.plus(returnType, ignoreNaN, m2);
			default:
				return super.plus(returnType, ignoreNaN, m2);
			}
		}
	}

	@Override
	public Matrix minus(double f) throws MatrixException {
		DenseMatrix a = new DenseMatrix(matrix.numRows(), matrix.numColumns());
		System.arraycopy(matrix.getData(), 0, a.getData(), 0, matrix.getData().length);
		for (int i = a.getData().length; --i >= 0;) {
			a.getData()[i] -= f;
		}
		return new MTJDenseDoubleMatrix2D(a);
	}

	@Override
	public Matrix plus(double f) throws MatrixException {
		DenseMatrix a = new DenseMatrix(matrix.numRows(), matrix.numColumns());
		System.arraycopy(matrix.getData(), 0, a.getData(), 0, matrix.getData().length);
		for (int i = a.getData().length; --i >= 0;) {
			a.getData()[i] += f;
		}
		return new MTJDenseDoubleMatrix2D(a);
	}

	@Override
	public Matrix times(double f) throws MatrixException {
		DenseMatrix a = new DenseMatrix(matrix.numRows(), matrix.numColumns());
		System.arraycopy(matrix.getData(), 0, a.getData(), 0, matrix.getData().length);
		for (int i = a.getData().length; --i >= 0;) {
			a.getData()[i] *= f;
		}
		return new MTJDenseDoubleMatrix2D(a);
	}

	@Override
	public Matrix divide(double f) throws MatrixException {
		DenseMatrix a = new DenseMatrix(matrix.numRows(), matrix.numColumns());
		System.arraycopy(matrix.getData(), 0, a.getData(), 0, matrix.getData().length);
		for (int i = a.getData().length; --i >= 0;) {
			a.getData()[i] /= f;
		}
		return new MTJDenseDoubleMatrix2D(a);
	}

	@Override
	public Matrix copy() {
		return new MTJDenseDoubleMatrix2D(matrix.copy());
	}

	public boolean containsNaN() {
		double[] data = matrix.getData();
		for (int i = matrix.getData().length; --i >= 0;) {
			if (Double.isNaN(data[i])) {
				return true;
			}
		}
		return false;
	}

}

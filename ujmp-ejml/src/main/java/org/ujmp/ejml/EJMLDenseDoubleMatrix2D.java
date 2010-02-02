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

package org.ujmp.ejml;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.ejml.alg.dense.decomposition.CholeskyDecomposition;
import org.ejml.alg.dense.decomposition.DecompositionFactory;
import org.ejml.alg.dense.decomposition.EigenDecomposition;
import org.ejml.alg.dense.decomposition.LUDecomposition;
import org.ejml.data.Complex64F;
import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.ejml.calculation.Inv;
import org.ujmp.ejml.calculation.QR;
import org.ujmp.ejml.calculation.SVD;
import org.ujmp.ejml.calculation.Solve;

public class EJMLDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D
		implements Wrapper<DenseMatrix64F> {
	private static final long serialVersionUID = -3223474248020842822L;

	private transient DenseMatrix64F matrix = null;

	public EJMLDenseDoubleMatrix2D(long... size) {
		this.matrix = new DenseMatrix64F((int) size[ROW], (int) size[COLUMN]);
	}

	public EJMLDenseDoubleMatrix2D(DenseMatrix64F m) {
		this.matrix = (DenseMatrix64F) m;
	}

	public EJMLDenseDoubleMatrix2D(Matrix source) throws MatrixException {
		this(source.getSize());
		if (source instanceof DenseDoubleMatrix2D) {
			DenseDoubleMatrix2D m2 = (DenseDoubleMatrix2D) source;
			for (int r = (int) source.getRowCount(); --r >= 0;) {
				for (int c = (int) source.getColumnCount(); --c >= 0;) {
					matrix.set(r, c, m2.getDouble(r, c));
				}
			}
		} else {
			for (long[] c : source.availableCoordinates()) {
				setDouble(source.getAsDouble(c), c);
			}
		}
	}

	public double getDouble(long row, long column) {
		return matrix.get((int) row, (int) column);
	}

	public double getDouble(int row, int column) {
		return matrix.get(row, column);
	}

	public long[] getSize() {
		return new long[] { matrix.numRows, matrix.numCols };
	}

	public void setDouble(double value, long row, long column) {
		matrix.set((int) row, (int) column, value);
	}

	public void setDouble(double value, int row, int column) {
		matrix.set(row, column, value);
	}

	public DenseMatrix64F getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(DenseMatrix64F object) {
		this.matrix = object;
	}

	public Matrix transpose() {
		DenseMatrix64F ret = new DenseMatrix64F(matrix.numCols, matrix.numRows);
		CommonOps.transpose(matrix, ret);
		return new EJMLDenseDoubleMatrix2D(ret);
	}

	public Matrix inv() {
		return Inv.INSTANCE.calc(this);
	}

	public double det() {
		return CommonOps.det(matrix);
	}

	public Matrix solve(Matrix b) {
		return Solve.INSTANCE.calc(this, b);
	}

	public Matrix plus(double value) {
		DenseMatrix64F ret = new DenseMatrix64F(matrix.numRows, matrix.numCols);
		CommonOps.add(matrix, value, ret);
		return new EJMLDenseDoubleMatrix2D(ret);
	}

	public Matrix plus(Matrix m) {
		if (m instanceof EJMLDenseDoubleMatrix2D) {
			DenseMatrix64F ret = new DenseMatrix64F(matrix.numRows,
					matrix.numCols);
			CommonOps.add(matrix, ((EJMLDenseDoubleMatrix2D) m).matrix, ret);
			return new EJMLDenseDoubleMatrix2D(ret);
		} else {
			return super.plus(m);
		}
	}

	public Matrix minus(Matrix m) {
		if (m instanceof EJMLDenseDoubleMatrix2D) {
			DenseMatrix64F ret = new DenseMatrix64F(matrix.numRows,
					matrix.numCols);
			CommonOps.sub(matrix, ((EJMLDenseDoubleMatrix2D) m).matrix, ret);
			return new EJMLDenseDoubleMatrix2D(ret);
		} else {
			return super.minus(m);
		}
	}

	public Matrix minus(double value) {
		DenseMatrix64F ret = new DenseMatrix64F(matrix.numRows, matrix.numCols);
		CommonOps.add(matrix, -value, ret);
		return new EJMLDenseDoubleMatrix2D(ret);
	}

	public Matrix times(double value) {
		DenseMatrix64F ret = new DenseMatrix64F(matrix.numRows, matrix.numCols);
		CommonOps.scale(value, matrix, ret);
		return new EJMLDenseDoubleMatrix2D(ret);
	}

	public Matrix divide(double value) {
		DenseMatrix64F ret = new DenseMatrix64F(matrix.numRows, matrix.numCols);
		CommonOps.scale(1.0 / value, matrix, ret);
		return new EJMLDenseDoubleMatrix2D(ret);
	}

	public Matrix mtimes(Matrix m) {
		if (m instanceof EJMLDenseDoubleMatrix2D) {
			DenseMatrix64F b = ((EJMLDenseDoubleMatrix2D) m).getWrappedObject();
			DenseMatrix64F ret = new DenseMatrix64F(matrix.numRows, b.numCols);
			CommonOps.mult(matrix, b, ret);
			return new EJMLDenseDoubleMatrix2D(ret);
		} else {
			return super.mtimes(m);
		}
	}

	public Matrix[] svd() {
		return SVD.INSTANCE.calc(this);
	}

	public Matrix[] qr() {
		return QR.INSTANCE.calc(this);
	}

	public Matrix chol() {
		CholeskyDecomposition chol = DecompositionFactory.chol(5);
		chol.decompose(matrix);
		Matrix l = new EJMLDenseDoubleMatrix2D(chol.getT(null));
		return l;
	}

	public Matrix[] lu() {
		if (isSquare()) {
			LUDecomposition lu = DecompositionFactory.lu();
			lu.decompose(matrix);
			DenseMatrix64F lm = new DenseMatrix64F(matrix.numRows,
					matrix.numCols);
			DenseMatrix64F um = new DenseMatrix64F(matrix.numRows,
					matrix.numCols);
			lu.getLower(lm);
			lu.getUpper(um);

			Matrix l = new EJMLDenseDoubleMatrix2D(lm);
			Matrix u = new EJMLDenseDoubleMatrix2D(um);
			Matrix p = new EJMLDenseDoubleMatrix2D(lu.getPivot(null));

			return new Matrix[] { l, u, p };
		} else {
			return super.lu();
		}
	}

	public Matrix[] eig() {
		EigenDecomposition eig = DecompositionFactory.eig();
		eig.decompose(matrix);

		int N = matrix.numRows;

		DenseMatrix64F D = new DenseMatrix64F(N, N);
		DenseMatrix64F V = new DenseMatrix64F(N, N);

		for (int i = 0; i < N; i++) {
			Complex64F c = eig.getEigenvalue(i);

			if (c.isReal()) {
				D.set(i, i, c.real);

				DenseMatrix64F v = eig.getEigenVector(i);

				if (v != null) {
					for (int j = 0; j < N; j++) {
						V.set(j, i, v.get(j, 0));
					}
				}
			}
		}

		Matrix v = new EJMLDenseDoubleMatrix2D(V);
		Matrix d = new EJMLDenseDoubleMatrix2D(D);

		return new Matrix[] { v, d };
	}

	public Matrix copy() {
		Matrix m = new EJMLDenseDoubleMatrix2D(matrix.copy());
		if (getAnnotation() != null) {
			m.setAnnotation(getAnnotation().clone());
		}
		return m;
	}

	private void readObject(ObjectInputStream s) throws IOException,
			ClassNotFoundException {
		s.defaultReadObject();
		int rows = (Integer) s.readObject();
		int columns = (Integer) s.readObject();
		double[] values = (double[]) s.readObject();
		matrix = new DenseMatrix64F(rows, columns, values, true);
	}

	private void writeObject(ObjectOutputStream s) throws IOException,
			MatrixException {
		s.defaultWriteObject();
		s.writeObject(matrix.numRows);
		s.writeObject(matrix.numCols);
		s.writeObject(matrix.data);
	}

}

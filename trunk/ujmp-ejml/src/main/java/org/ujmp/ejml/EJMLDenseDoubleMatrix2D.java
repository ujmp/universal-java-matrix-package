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

package org.ujmp.ejml;

import org.ejml.data.Complex64F;
import org.ejml.data.DenseMatrix64F;
import org.ejml.data.Matrix64F;
import org.ejml.factory.DecompositionFactory;
import org.ejml.interfaces.decomposition.CholeskyDecomposition;
import org.ejml.interfaces.decomposition.EigenDecomposition;
import org.ejml.interfaces.decomposition.LUDecomposition;
import org.ejml.ops.CommonOps;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.interfaces.HasRowMajorDoubleArray1D;
import org.ujmp.core.interfaces.HasRowMajorDoubleArray2D;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.ejml.calculation.Inv;
import org.ujmp.ejml.calculation.InvSPD;
import org.ujmp.ejml.calculation.QR;
import org.ujmp.ejml.calculation.SVD;
import org.ujmp.ejml.calculation.Solve;

public class EJMLDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D implements Wrapper<DenseMatrix64F>,
		HasRowMajorDoubleArray1D {
	private static final long serialVersionUID = -3223474248020842822L;

	public static final EJMLDenseDoubleMatrix2DFactory Factory = new EJMLDenseDoubleMatrix2DFactory();

	private final DenseMatrix64F matrix;

	public EJMLDenseDoubleMatrix2D(int rows, int columns) {
		super(rows, columns);
		this.matrix = new DenseMatrix64F(rows, columns);
	}

	public EJMLDenseDoubleMatrix2D(DenseMatrix64F m) {
		super(m.numRows, m.numCols);
		this.matrix = (DenseMatrix64F) m;
	}

	public EJMLDenseDoubleMatrix2D(Matrix source) {
		super(source.getRowCount(), source.getColumnCount());
		if (source instanceof HasRowMajorDoubleArray2D) {
			final double[][] data = ((HasRowMajorDoubleArray2D) source).getRowMajorDoubleArray2D();
			this.matrix = new DenseMatrix64F(data);
		} else if (source instanceof DenseDoubleMatrix2D) {
			this.matrix = new DenseMatrix64F((int) source.getRowCount(), (int) source.getColumnCount());
			final DenseDoubleMatrix2D m2 = (DenseDoubleMatrix2D) source;
			for (int r = (int) source.getRowCount(); --r >= 0;) {
				for (int c = (int) source.getColumnCount(); --c >= 0;) {
					matrix.set(r, c, m2.getDouble(r, c));
				}
			}
		} else {
			this.matrix = new DenseMatrix64F((int) source.getRowCount(), (int) source.getColumnCount());
			for (long[] c : source.availableCoordinates()) {
				setDouble(source.getAsDouble(c), c);
			}
		}
		if (source.getMetaData() != null) {
			setMetaData(source.getMetaData().clone());
		}
	}

	public double getDouble(long row, long column) {
		return matrix.get(MathUtil.longToInt(row), MathUtil.longToInt(column));
	}

	public double getDouble(int row, int column) {
		return matrix.get(row, column);
	}

	public long[] getSize() {
		return new long[] { matrix.numRows, matrix.numCols };
	}

	public void setDouble(double value, long row, long column) {
		matrix.set(MathUtil.longToInt(row), MathUtil.longToInt(column), value);
	}

	public void setDouble(double value, int row, int column) {
		matrix.set(row, column, value);
	}

	public DenseMatrix64F getWrappedObject() {
		return matrix;
	}

	public Matrix transpose() {
		DenseMatrix64F ret = new DenseMatrix64F(matrix.numCols, matrix.numRows);
		CommonOps.transpose(matrix, ret);
		return new EJMLDenseDoubleMatrix2D(ret);
	}

	public Matrix inv() {
		return Inv.INSTANCE.calc(this);
	}

	public Matrix invSPD() {
		return InvSPD.INSTANCE.calc(this);
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
		Matrix result = new EJMLDenseDoubleMatrix2D(ret);
		MapMatrix<Object, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
	}

	public Matrix plus(Matrix m) {
		if (m instanceof EJMLDenseDoubleMatrix2D) {
			DenseMatrix64F ret = new DenseMatrix64F(matrix.numRows, matrix.numCols);
			CommonOps.add(matrix, ((EJMLDenseDoubleMatrix2D) m).matrix, ret);
			Matrix result = new EJMLDenseDoubleMatrix2D(ret);
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
		if (m instanceof EJMLDenseDoubleMatrix2D) {
			DenseMatrix64F ret = new DenseMatrix64F(matrix.numRows, matrix.numCols);
			CommonOps.sub(matrix, ((EJMLDenseDoubleMatrix2D) m).matrix, ret);
			Matrix result = new EJMLDenseDoubleMatrix2D(ret);
			MapMatrix<Object, Object> a = getMetaData();
			if (a != null) {
				result.setMetaData(a.clone());
			}
			return result;
		} else {
			return super.minus(m);
		}
	}

	public Matrix minus(double value) {
		DenseMatrix64F ret = new DenseMatrix64F(matrix.numRows, matrix.numCols);
		CommonOps.add(matrix, -value, ret);
		Matrix result = new EJMLDenseDoubleMatrix2D(ret);
		MapMatrix<Object, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
	}

	public Matrix times(double value) {
		DenseMatrix64F ret = new DenseMatrix64F(matrix.numRows, matrix.numCols);
		CommonOps.scale(value, matrix, ret);
		Matrix result = new EJMLDenseDoubleMatrix2D(ret);
		MapMatrix<Object, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
	}

	public Matrix divide(double value) {
		DenseMatrix64F ret = new DenseMatrix64F(matrix.numRows, matrix.numCols);
		CommonOps.scale(1.0 / value, matrix, ret);
		Matrix result = new EJMLDenseDoubleMatrix2D(ret);
		MapMatrix<Object, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
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
		CholeskyDecomposition<DenseMatrix64F> chol = DecompositionFactory.chol(matrix.numRows, false);
		chol.decompose(matrix);
		Matrix l = new EJMLDenseDoubleMatrix2D(chol.getT(null));
		return l;
	}

	public Matrix[] lu() {
		if (isSquare()) {
			LUDecomposition<DenseMatrix64F> lu = DecompositionFactory.lu(matrix.numRows, matrix.numCols);
			lu.decompose(matrix);
			DenseMatrix64F lm = new DenseMatrix64F(matrix.numRows, matrix.numCols);
			DenseMatrix64F um = new DenseMatrix64F(matrix.numRows, matrix.numCols);
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
		EigenDecomposition<DenseMatrix64F> eig = DecompositionFactory.eig(matrix.numCols, true);
		eig.decompose(matrix);

		int N = matrix.numRows;

		DenseMatrix64F D = new DenseMatrix64F(N, N);
		DenseMatrix64F V = new DenseMatrix64F(N, N);

		for (int i = 0; i < N; i++) {
			Complex64F c = eig.getEigenvalue(i);

			if (c.isReal()) {
				D.set(i, i, c.real);

				Matrix64F v = eig.getEigenVector(i);

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
		if (getMetaData() != null) {
			m.setMetaData(getMetaData().clone());
		}
		return m;
	}

	public double[] getRowMajorDoubleArray1D() {
		return matrix.getData();
	}

	public EJMLDenseDoubleMatrix2DFactory getFactory() {
		return Factory;
	}

}

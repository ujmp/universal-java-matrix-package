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

package org.ujmp.jmatharray;

import org.math.array.DoubleArray;
import org.math.array.LinearAlgebra;
import org.ujmp.core.Matrix;
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

import Jama.CholeskyDecomposition;
import Jama.EigenvalueDecomposition;
import Jama.LUDecomposition;
import Jama.QRDecomposition;
import Jama.SingularValueDecomposition;

public class JMathArrayDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D
		implements Wrapper<double[][]> {
	private static final long serialVersionUID = -3223474248020842822L;

	private double[][] matrix = null;

	public JMathArrayDenseDoubleMatrix2D(long... size) {
		this.matrix = new double[(int) size[ROW]][(int) size[COLUMN]];
	}

	public JMathArrayDenseDoubleMatrix2D(double[][] m) {
		this.matrix = m;
	}

	public JMathArrayDenseDoubleMatrix2D(Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setDouble(source.getAsDouble(c), c);
		}
		if (source.getAnnotation() != null) {
			setAnnotation(source.getAnnotation().clone());
		}
	}

	public double getDouble(long row, long column) {
		return matrix[(int) row][(int) column];
	}

	public double getDouble(int row, int column) {
		return matrix[row][column];
	}

	public long[] getSize() {
		return new long[] { matrix.length,
				matrix.length == 0 ? 0 : matrix[0].length };
	}

	public void setDouble(double value, long row, long column) {
		matrix[(int) row][(int) column] = value;
	}

	public void setDouble(double value, int row, int column) {
		matrix[row][column] = value;
	}

	public Matrix[] svd() throws MatrixException {
		if (getColumnCount() > getRowCount()) {
			SingularValueDecomposition svd = new SingularValueDecomposition(
					new Jama.Matrix(DoubleArray.transpose(matrix)));
			Matrix u = new JMathArrayDenseDoubleMatrix2D(svd.getV().getArray());
			Matrix s = new JMathArrayDenseDoubleMatrix2D(svd.getS().transpose()
					.getArray());
			Matrix v = new JMathArrayDenseDoubleMatrix2D(svd.getU().getArray());
			return new Matrix[] { u, s, v };
		} else {
			SingularValueDecomposition svd = new SingularValueDecomposition(
					new Jama.Matrix(matrix));
			Matrix u = new JMathArrayDenseDoubleMatrix2D(svd.getU().getArray());
			Matrix s = new JMathArrayDenseDoubleMatrix2D(svd.getS().getArray());
			Matrix v = new JMathArrayDenseDoubleMatrix2D(svd.getV().getArray());
			return new Matrix[] { u, s, v };
		}
	}

	public Matrix[] qr() {
		if (getRowCount() >= getColumnCount()) {
			QRDecomposition qr = new QRDecomposition(new Jama.Matrix(matrix));
			Matrix q = new JMathArrayDenseDoubleMatrix2D(qr.getQ().getArray());
			Matrix r = new JMathArrayDenseDoubleMatrix2D(qr.getR().getArray());
			return new Matrix[] { q, r };
		} else {
			throw new MatrixException(
					"QR decomposition only works for matrices m>=n");
		}
	}

	public Matrix[] eig() {
		EigenvalueDecomposition eig = new EigenvalueDecomposition(
				new Jama.Matrix(matrix));
		Matrix v = new JMathArrayDenseDoubleMatrix2D(eig.getV().getArray());
		Matrix d = new JMathArrayDenseDoubleMatrix2D(eig.getD().getArray());
		return new Matrix[] { v, d };
	}

	public Matrix chol() {
		CholeskyDecomposition chol = new CholeskyDecomposition(new Jama.Matrix(
				matrix));
		Matrix r = new JMathArrayDenseDoubleMatrix2D(chol.getL().getArray());
		return r;
	}

	public Matrix[] lu() {
		if (getRowCount() >= getColumnCount()) {
			LUDecomposition lu = new LUDecomposition(new Jama.Matrix(matrix));
			Matrix l = new JMathArrayDenseDoubleMatrix2D(lu.getL().getArray());
			Matrix u = new JMathArrayDenseDoubleMatrix2D(lu.getU().getArray());
			int m = (int) getRowCount();
			int[] piv = lu.getPivot();
			Matrix p = new JMathArrayDenseDoubleMatrix2D(m, m);
			for (int i = 0; i < m; i++) {
				p.setAsDouble(1, i, piv[i]);
			}
			return new Matrix[] { l, u, p };
		} else {
			throw new MatrixException(
					"LU decomposition only works for matrices m>=n");
		}
	}

	public double[][] getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(double[][] object) {
		this.matrix = object;
	}

	public Matrix transpose() {
		return new JMathArrayDenseDoubleMatrix2D(DoubleArray.transpose(matrix));
	}

	public Matrix inv() {
		return new JMathArrayDenseDoubleMatrix2D(LinearAlgebra.inverse(matrix));
	}

	public Matrix invSPD() {
		CholeskyDecomposition chol = new CholeskyDecomposition(new Jama.Matrix(
				matrix));
		return new JMathArrayDenseDoubleMatrix2D(chol.solve(
				Jama.Matrix.identity(matrix.length, matrix.length)).getArray());
	}

	public Matrix plus(double value) {
		Matrix result = new JMathArrayDenseDoubleMatrix2D(DoubleArray.add(
				DoubleArray.copy(matrix), value));
		Annotation a = getAnnotation();
		if (a != null) {
			result.setAnnotation(a.clone());
		}
		return result;
	}

	public Matrix minus(double value) {
		Matrix result = new JMathArrayDenseDoubleMatrix2D(DoubleArray.add(
				DoubleArray.copy(matrix), -value));
		Annotation a = getAnnotation();
		if (a != null) {
			result.setAnnotation(a.clone());
		}
		return result;
	}

	public Matrix times(double value) {
		Matrix result = new JMathArrayDenseDoubleMatrix2D(LinearAlgebra.times(
				matrix, value));
		Annotation a = getAnnotation();
		if (a != null) {
			result.setAnnotation(a.clone());
		}
		return result;
	}

	public Matrix divide(double value) {
		Matrix result = new JMathArrayDenseDoubleMatrix2D(LinearAlgebra.divide(
				matrix, value));
		Annotation a = getAnnotation();
		if (a != null) {
			result.setAnnotation(a.clone());
		}
		return result;
	}

	public Matrix mtimes(Matrix m) {
		if (m instanceof JMathArrayDenseDoubleMatrix2D) {
			Matrix result = new JMathArrayDenseDoubleMatrix2D(LinearAlgebra
					.times(matrix, ((JMathArrayDenseDoubleMatrix2D) m).matrix));
			Annotation a = getAnnotation();
			if (a != null) {
				result.setAnnotation(a.clone());
			}
			return result;
		} else {
			return super.mtimes(m);
		}
	}

	public Matrix copy() {
		Matrix m = new JMathArrayDenseDoubleMatrix2D(DoubleArray.copy(matrix));
		if (getAnnotation() != null) {
			m.setAnnotation(getAnnotation().clone());
		}
		return m;
	}

	public Matrix solve(Matrix b) {
		if (b instanceof JMathArrayDenseDoubleMatrix2D) {
			JMathArrayDenseDoubleMatrix2D b2 = (JMathArrayDenseDoubleMatrix2D) b;
			double[][] x = LinearAlgebra.solve(matrix, b2.matrix);
			return new JMathArrayDenseDoubleMatrix2D(x);
		} else {
			return super.solve(b);
		}
	}

}

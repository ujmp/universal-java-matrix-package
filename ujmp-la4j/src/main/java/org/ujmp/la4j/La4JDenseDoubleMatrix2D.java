/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

package org.ujmp.la4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.la4j.LinearAlgebra.DecompositorFactory;
import org.la4j.LinearAlgebra.InverterFactory;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.MathUtil;

public class La4JDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D {
	private static final long serialVersionUID = 904914110868813155L;

	public static final La4JDenseDoubleMatrix2DFactory Factory = new La4JDenseDoubleMatrix2DFactory();

	private transient Basic2DMatrix matrix;

	public La4JDenseDoubleMatrix2D(int rows, int columns) {
		super(rows, columns);
		this.matrix = new Basic2DMatrix(rows, columns);
	}

	public La4JDenseDoubleMatrix2D(Basic2DMatrix matrix) {
		super(matrix.rows(), matrix.columns());
		this.matrix = matrix;
	}

	public La4JDenseDoubleMatrix2D(Matrix source) {
		super(source.getRowCount(), source.getColumnCount());
		this.matrix = new Basic2DMatrix((int) source.getRowCount(), (int) source.getColumnCount());
		for (long[] c : source.availableCoordinates()) {
			setDouble(source.getAsDouble(c), c);
		}
		if (source.getMetaData() != null) {
			setMetaData(source.getMetaData().clone());
		}
	}

	public double getDouble(long row, long column) {
		return matrix.get(MathUtil.longToInt(row), MathUtil.longToInt(column));
	}

	public void setDouble(double value, long row, long column) {
		matrix.set(MathUtil.longToInt(row), MathUtil.longToInt(column), value);
	}

	public double getDouble(int row, int column) {
		return matrix.get(row, column);
	}

	public void setDouble(double value, int row, int column) {
		matrix.set(row, column, value);
	}

	public Matrix plus(double value) {
		Matrix result = new La4JDenseDoubleMatrix2D((Basic2DMatrix) matrix.add(value));
		MapMatrix<String, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
	}

	public Matrix plus(Matrix m) {
		if (m instanceof La4JDenseDoubleMatrix2D) {
			Matrix result = new La4JDenseDoubleMatrix2D(
					(Basic2DMatrix) matrix.add(((La4JDenseDoubleMatrix2D) m).matrix));
			MapMatrix<String, Object> a = getMetaData();
			if (a != null) {
				result.setMetaData(a.clone());
			}
			return result;
		} else {
			return super.plus(m);
		}
	}

	public Matrix mtimes(Matrix m) {
		if (m instanceof La4JDenseDoubleMatrix2D) {
			Matrix result = new La4JDenseDoubleMatrix2D(
					(Basic2DMatrix) matrix.multiply(((La4JDenseDoubleMatrix2D) m).matrix));
			return result;
		} else {
			return super.mtimes(m);
		}
	}

	public Matrix minus(double value) {
		Matrix result = new La4JDenseDoubleMatrix2D((Basic2DMatrix) matrix.subtract(value));
		MapMatrix<String, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
	}

	public Matrix minus(Matrix m) {
		if (m instanceof La4JDenseDoubleMatrix2D) {
			Matrix result = new La4JDenseDoubleMatrix2D(
					(Basic2DMatrix) matrix.subtract(((La4JDenseDoubleMatrix2D) m).matrix));
			MapMatrix<String, Object> a = getMetaData();
			if (a != null) {
				result.setMetaData(a.clone());
			}
			return result;
		} else {
			return super.minus(m);
		}
	}

	public Matrix divide(double value) {
		Matrix result = new La4JDenseDoubleMatrix2D((Basic2DMatrix) matrix.divide(value));
		MapMatrix<String, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
	}

	public Matrix times(double value) {
		Matrix result = new La4JDenseDoubleMatrix2D((Basic2DMatrix) matrix.multiply(value));
		MapMatrix<String, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
	}

	public Matrix transpose() {
		Matrix result = new La4JDenseDoubleMatrix2D((Basic2DMatrix) matrix.transpose());
		return result;
	}

	public Matrix[] lu() {
		org.la4j.Matrix[] temp = matrix.withDecompositor(DecompositorFactory.LU).decompose();
		Matrix[] result = new Matrix[3];
		result[0] = new La4JDenseDoubleMatrix2D((Basic2DMatrix) temp[0]);
		result[1] = new La4JDenseDoubleMatrix2D((Basic2DMatrix) temp[1]);
		result[2] = new La4JDenseDoubleMatrix2D((Basic2DMatrix) temp[2].toDenseMatrix());
		return result;
	}

	public Matrix[] svd() {
		org.la4j.Matrix[] temp = matrix.withDecompositor(DecompositorFactory.SVD).decompose();
		Matrix[] result = new Matrix[3];
		result[0] = new La4JDenseDoubleMatrix2D((Basic2DMatrix) temp[0]);
		result[1] = new La4JDenseDoubleMatrix2D((Basic2DMatrix) temp[1]);
		result[2] = new La4JDenseDoubleMatrix2D((Basic2DMatrix) temp[2]);
		return result;
	}

	public Matrix chol() {
		org.la4j.Matrix[] temp = matrix.withDecompositor(DecompositorFactory.CHOLESKY).decompose();
		Matrix result = new La4JDenseDoubleMatrix2D((Basic2DMatrix) temp[0]);
		return result;
	}

	public Matrix inv() {
		org.la4j.Matrix temp = matrix.withInverter(InverterFactory.SMART).inverse();
		Matrix result = new La4JDenseDoubleMatrix2D((Basic2DMatrix) temp);
		return result;
	}

	public Matrix[] qr() {
		org.la4j.Matrix[] temp = matrix.withDecompositor(DecompositorFactory.QR).decompose();
		Matrix[] result = new Matrix[2];
		result[0] = new La4JDenseDoubleMatrix2D((Basic2DMatrix) temp[0]);
		result[1] = new La4JDenseDoubleMatrix2D((Basic2DMatrix) temp[1]);
		return result;
	}

	public Matrix[] eig() {
		org.la4j.Matrix[] temp = matrix.withDecompositor(DecompositorFactory.EIGEN).decompose();
		Matrix[] result = new Matrix[2];
		result[0] = new La4JDenseDoubleMatrix2D((Basic2DMatrix) temp[0].toDenseMatrix());
		result[1] = new La4JDenseDoubleMatrix2D((Basic2DMatrix) temp[1].toDenseMatrix());
		return result;
	}

	public La4JDenseDoubleMatrix2DFactory getFactory() {
		return Factory;
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		byte[] binary = (byte[]) s.readObject();
		matrix = Basic2DMatrix.fromBinary(binary);
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeObject(matrix.toBinary());
	}

}

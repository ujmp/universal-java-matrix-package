/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

package org.ujmp.jscience;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javolution.util.FastTable;
import javolution.util.Index;

import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.DenseMatrix;
import org.jscience.mathematics.vector.Float64Matrix;
import org.jscience.mathematics.vector.Float64Vector;
import org.jscience.mathematics.vector.LUDecomposition;
import org.jscience.mathematics.vector.SparseMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.ReflectionUtil;

public class JScienceDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D implements Wrapper<Float64Matrix> {
	private static final long serialVersionUID = -7874694468839411484L;

	public static final JScienceDenseDoubleMatrix2DFactory Factory = new JScienceDenseDoubleMatrix2DFactory();

	private transient Float64Matrix matrix;

	private Boolean transposed = false;

	private transient FastTable<Float64Vector> rows = null;

	public JScienceDenseDoubleMatrix2D(int rows, int columns) {
		super(rows, columns);
		this.matrix = Float64Matrix.valueOf(new double[rows][columns]);
	}

	public JScienceDenseDoubleMatrix2D(Float64Matrix matrix) {
		super(matrix.getNumberOfRows(), matrix.getNumberOfColumns());
		this.matrix = matrix;
	}

	public JScienceDenseDoubleMatrix2D(double[][] values) {
		super(values.length, values[0].length);
		this.matrix = Float64Matrix.valueOf(values);
	}

	public JScienceDenseDoubleMatrix2D(double[] values) {
		super(values.length, 1);
		this.matrix = Float64Matrix.valueOf(Float64Vector.valueOf(values));
	}

	public JScienceDenseDoubleMatrix2D(Matrix matrix) {
		super(matrix.getRowCount(), matrix.getColumnCount());
		this.matrix = Float64Matrix.valueOf(matrix.toDoubleArray());
		if (matrix.getMetaData() != null) {
			setMetaData(matrix.getMetaData().clone());
		}
	}

	public JScienceDenseDoubleMatrix2D(DenseMatrix<Float64> matrix) {
		super(matrix.getNumberOfRows(), matrix.getNumberOfColumns());
		this.matrix = Float64Matrix.valueOf(matrix);
	}

	public JScienceDenseDoubleMatrix2D(SparseMatrix<Float64> matrix) {
		super(matrix.getNumberOfRows(), matrix.getNumberOfColumns());
		this.matrix = Float64Matrix.valueOf(matrix);
	}

	public double getDouble(long row, long column) {
		return matrix.get((int) row, (int) column).doubleValue();
	}

	public double getDouble(int row, int column) {
		return matrix.get(row, column).doubleValue();
	}

	public void setDouble(double value, long row, long column) {
		if (getTransposed()) {
			Float64Vector f = getRowsTable().get((int) column);
			double[] data = (double[]) ReflectionUtil.extractPrivateField(f, "_values");
			data[(int) row] = value;
		} else {
			Float64Vector f = getRowsTable().get((int) row);
			double[] data = (double[]) ReflectionUtil.extractPrivateField(f, "_values");
			data[(int) column] = value;
		}
	}

	private boolean getTransposed() {
		if (transposed == null) {
			transposed = (Boolean) ReflectionUtil.extractPrivateField(Float64Matrix.class, matrix, "_transposed");
		}
		return transposed;
	}

	@SuppressWarnings("unchecked")
	private FastTable<Float64Vector> getRowsTable() {
		if (rows == null) {
			rows = (FastTable<Float64Vector>) ReflectionUtil.extractPrivateField(Float64Matrix.class, matrix, "_rows");
		}
		return rows;
	}

	public void setDouble(double value, int row, int column) {
		if (getTransposed()) {
			Float64Vector f = getRowsTable().get(column);
			double[] data = (double[]) ReflectionUtil.extractPrivateField(Float64Vector.class, f, "_values");
			data[row] = value;
		} else {
			Float64Vector f = getRowsTable().get(row);
			double[] data = (double[]) ReflectionUtil.extractPrivateField(Float64Vector.class, f, "_values");
			data[column] = value;
		}
	}

	public Matrix mtimes(Matrix that) {
		if (that instanceof JScienceDenseDoubleMatrix2D) {
			return new JScienceDenseDoubleMatrix2D(matrix.times(((JScienceDenseDoubleMatrix2D) that).matrix));
		} else {
			return super.mtimes(that);
		}
	}

	public Matrix plus(Matrix that) {
		if (that instanceof JScienceDenseDoubleMatrix2D) {
			Matrix result = new JScienceDenseDoubleMatrix2D(matrix.plus(((JScienceDenseDoubleMatrix2D) that).matrix));
			MapMatrix<String, Object> a = getMetaData();
			if (a != null) {
				result.setMetaData(a.clone());
			}
			return result;
		} else {
			return super.plus(that);
		}
	}

	public Matrix minus(Matrix that) {
		if (that instanceof JScienceDenseDoubleMatrix2D) {
			Matrix result = new JScienceDenseDoubleMatrix2D(matrix.minus(((JScienceDenseDoubleMatrix2D) that).matrix));
			MapMatrix<String, Object> a = getMetaData();
			if (a != null) {
				result.setMetaData(a.clone());
			}
			return result;
		} else {
			return super.minus(that);
		}
	}

	public Matrix times(double value) {
		Matrix result = new JScienceDenseDoubleMatrix2D(matrix.times(Float64.valueOf(value)));
		MapMatrix<String, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
	}

	public Matrix divide(double value) {
		Matrix result = new JScienceDenseDoubleMatrix2D(matrix.times(Float64.valueOf(1.0 / value)));
		MapMatrix<String, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
	}

	public Matrix transpose() {
		return new JScienceDenseDoubleMatrix2D(matrix.transpose().copy());
	}

	public Matrix inv() {
		return new JScienceDenseDoubleMatrix2D(matrix.inverse());
	}

	public Matrix[] lu() {
		if (isSquare()) {
			LUDecomposition<Float64> lu = LUDecomposition.valueOf(matrix);
			int m = (int) getRowCount();
			DenseMatrix<Float64> lt = lu.getLower(Float64.ZERO, Float64.ONE);
			DenseMatrix<Float64> ut = lu.getUpper(Float64.ZERO);
			FastTable<Index> piv = lu.getPivots();
			Matrix l = new JScienceDenseDoubleMatrix2D(lt);
			Matrix u = new JScienceDenseDoubleMatrix2D(ut);
			Matrix p = new JScienceDenseDoubleMatrix2D(m, m);
			for (int i = 0; i < m; i++) {
				p.setAsDouble(1, i, piv.get(i).intValue());
			}
			return new Matrix[] { l, u, p };
		} else {
			throw new RuntimeException("matrix must be square");
		}
	}

	public Float64Matrix getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(Float64Matrix object) {
		this.matrix = object;
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		double[][] values = (double[][]) s.readObject();
		matrix = Float64Matrix.valueOf(values);
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeObject(this.toDoubleArray());
	}

	public Matrix solve(Matrix b) {
		if (b instanceof JScienceDenseDoubleMatrix2D) {
			JScienceDenseDoubleMatrix2D b2 = (JScienceDenseDoubleMatrix2D) b;
			Float64Matrix x = Float64Matrix.valueOf(matrix.solve(b2.matrix));
			return new JScienceDenseDoubleMatrix2D(x);
		} else {
			return super.solve(b);
		}
	}

	public JScienceDenseDoubleMatrix2DFactory getFactory() {
		return Factory;
	}
}

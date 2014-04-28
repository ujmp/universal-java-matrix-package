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

package org.ujmp.jlinalg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.jlinalg.MatrixMultiplication;
import org.jlinalg.doublewrapper.DoubleWrapper;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.MathUtil;

public class JLinAlgDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D implements
		Wrapper<org.jlinalg.Matrix<DoubleWrapper>> {
	private static final long serialVersionUID = -3223474248020842822L;

	public static final JLinAlgDenseDoubleMatrix2DFactory Factory = new JLinAlgDenseDoubleMatrix2DFactory();

	private transient org.jlinalg.Matrix<DoubleWrapper> matrix;

	// matrix must be filled with zeros
	public JLinAlgDenseDoubleMatrix2D(int rows, int columns) {
		super(rows, columns);
		this.matrix = new org.jlinalg.Matrix<DoubleWrapper>(rows, columns, DoubleWrapper.FACTORY);
		for (long[] c : availableCoordinates()) {
			setDouble(0, c);
		}
	}

	public JLinAlgDenseDoubleMatrix2D(org.jlinalg.Matrix<DoubleWrapper> m) {
		super(m.getRows(), m.getCols());
		this.matrix = m;
	}

	public JLinAlgDenseDoubleMatrix2D(Matrix source) {
		this(MathUtil.longToInt(source.getRowCount()), MathUtil.longToInt(source.getColumnCount()));
		for (long[] c : source.availableCoordinates()) {
			setAsDouble(source.getAsDouble(c), c);
		}
		if (source.getMetaData() != null) {
			setMetaData(source.getMetaData().clone());
		}
	}

	public double getDouble(long row, long column) {
		DoubleWrapper d = matrix.get((int) row + 1, (int) column + 1);
		return d == null ? 0.0 : d.doubleValue();
	}

	public double getDouble(int row, int column) {
		DoubleWrapper d = matrix.get((int) row + 1, (int) column + 1);
		return d == null ? 0.0 : d.doubleValue();
	}

	public long[] getSize() {
		return new long[] { matrix.getRows(), matrix.getCols() };
	}

	public void setDouble(double value, long row, long column) {
		matrix.set((int) row + 1, (int) column + 1, new DoubleWrapper(value));
	}

	public void setDouble(double value, int row, int column) {
		matrix.set(row + 1, column + 1, new DoubleWrapper(value));
	}

	public org.jlinalg.Matrix<DoubleWrapper> getWrappedObject() {
		return matrix;
	}

	public Matrix transpose() {
		return new JLinAlgDenseDoubleMatrix2D(matrix.transpose());
	}

	public Matrix inv() {
		return new JLinAlgDenseDoubleMatrix2D(matrix.inverse());
	}

	public Matrix plus(double value) {
		Matrix result = new JLinAlgDenseDoubleMatrix2D(matrix.add(new DoubleWrapper(value)));
		MapMatrix<Object, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
	}

	public Matrix times(double value) {
		Matrix result = new JLinAlgDenseDoubleMatrix2D(matrix.multiply(new DoubleWrapper(value)));
		MapMatrix<Object, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
	}

	public Matrix mtimes(Matrix m) {
		if (m instanceof JLinAlgDenseDoubleMatrix2D) {
			org.jlinalg.Matrix<DoubleWrapper> b = ((JLinAlgDenseDoubleMatrix2D) m).getWrappedObject();
			org.jlinalg.Matrix<DoubleWrapper> c = MatrixMultiplication.strassenBodrato(matrix, b);
			return new JLinAlgDenseDoubleMatrix2D(c);
		} else {
			return super.mtimes(m);
		}
	}

	public Matrix copy() {
		Matrix m = new JLinAlgDenseDoubleMatrix2D(matrix.copy());
		if (getMetaData() != null) {
			m.setMetaData(getMetaData().clone());
		}
		return m;
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		double[][] data = (double[][]) s.readObject();
		matrix = new org.jlinalg.Matrix<DoubleWrapper>(data.length, data[0].length, DoubleWrapper.FACTORY);
		for (int r = 0; r < data.length; r++) {
			for (int c = 0; c < data[0].length; c++) {
				setDouble(data[r][c], r, c);
			}
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeObject(toDoubleArray());
	}

	public JLinAlgDenseDoubleMatrix2DFactory getFactory() {
		return Factory;
	}

}

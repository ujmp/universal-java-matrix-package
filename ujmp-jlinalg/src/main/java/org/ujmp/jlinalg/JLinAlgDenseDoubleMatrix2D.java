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

package org.ujmp.jlinalg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.jlinalg.doublewrapper.DoubleWrapper;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

public class JLinAlgDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D
		implements Wrapper<org.jlinalg.Matrix<DoubleWrapper>> {
	private static final long serialVersionUID = -3223474248020842822L;

	private transient org.jlinalg.Matrix<DoubleWrapper> matrix = null;

	// matrix must be filled with zeros
	public JLinAlgDenseDoubleMatrix2D(long... size) {
		this.matrix = new org.jlinalg.Matrix<DoubleWrapper>((int) size[ROW],
				(int) size[COLUMN], DoubleWrapper.FACTORY);
		for (long[] c : availableCoordinates()) {
			setDouble(0, c);
		}
	}

	public JLinAlgDenseDoubleMatrix2D(org.jlinalg.Matrix<DoubleWrapper> m) {
		this.matrix = m;
	}

	public JLinAlgDenseDoubleMatrix2D(Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setAsDouble(source.getAsDouble(c), c);
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

	public void setWrappedObject(org.jlinalg.Matrix<DoubleWrapper> object) {
		this.matrix = object;
	}

	public Matrix transpose() {
		return new JLinAlgDenseDoubleMatrix2D(matrix.transpose());
	}

	public Matrix inv() {
		return new JLinAlgDenseDoubleMatrix2D(matrix.inverse());
	}

	public Matrix plus(double value) {
		return new JLinAlgDenseDoubleMatrix2D(matrix.add(new DoubleWrapper(
				value)));
	}

	public Matrix times(double value) {
		return new JLinAlgDenseDoubleMatrix2D(matrix
				.multiply(new DoubleWrapper(value)));
	}

	public Matrix mtimes(Matrix m) {
		if (m instanceof JLinAlgDenseDoubleMatrix2D) {
			org.jlinalg.Matrix<DoubleWrapper> b = ((JLinAlgDenseDoubleMatrix2D) m)
					.getWrappedObject();
			return new JLinAlgDenseDoubleMatrix2D(matrix.multiply(b));
		} else {
			return super.mtimes(m);
		}
	}

	public Matrix copy() {
		Matrix m = new JLinAlgDenseDoubleMatrix2D(matrix.copy());
		if (getAnnotation() != null) {
			m.setAnnotation(getAnnotation().clone());
		}
		return m;
	}

	private void readObject(ObjectInputStream s) throws IOException,
			ClassNotFoundException {
		s.defaultReadObject();
		double[][] data = (double[][]) s.readObject();
		matrix = new org.jlinalg.Matrix<DoubleWrapper>(data.length,
				data[0].length, DoubleWrapper.FACTORY);
		for (int r = 0; r < data.length; r++) {
			for (int c = 0; c < data[0].length; c++) {
				setDouble(data[r][c], r, c);
			}
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException,
			MatrixException {
		s.defaultWriteObject();
		s.writeObject(toDoubleArray());
	}

}

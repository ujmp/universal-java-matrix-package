/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.ujmp.ojalgo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.ojalgo.matrix.BasicMatrix;
import org.ojalgo.matrix.PrimitiveMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.interfaces.Wrapper;

public class OjalgoDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D
		implements Wrapper<BasicMatrix> {
	private static final long serialVersionUID = 6628172130438716653L;

	private transient BasicMatrix matrix = null;

	public OjalgoDenseDoubleMatrix2D(long... size) {
		this.matrix = PrimitiveMatrix.FACTORY.makeZero((int) size[ROW],
				(int) size[COLUMN]);
	}

	public OjalgoDenseDoubleMatrix2D(Matrix m) {
		this(m.getSize());
		for (long[] c : m.allCoordinates()) {
			setAsDouble(m.getAsDouble(c), c);
		}
	}

	public OjalgoDenseDoubleMatrix2D(BasicMatrix matrix) {
		this.matrix = matrix;
	}

	public double getDouble(long row, long column) {
		return matrix.doubleValue((int) row, (int) column);
	}

	public double getDouble(int row, int column) {
		return matrix.doubleValue(row, column);
	}

	public long[] getSize() {
		return new long[] { matrix.getRowDim(), matrix.getColDim() };
	}

	public void setDouble(double value, long row, long column) {
		matrix = matrix.set((int) row, (int) column, value);
	}

	public void setDouble(double value, int row, int column) {
		matrix = matrix.set(row, column, value);
	}

	public BasicMatrix getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(BasicMatrix object) {
		this.matrix = object;
	}

	
	public Matrix transpose() {
		return new OjalgoDenseDoubleMatrix2D(matrix.transpose());
	}

	
	public Matrix mtimes(Matrix m) {
		if (m instanceof OjalgoDenseDoubleMatrix2D) {
			BasicMatrix mo = ((OjalgoDenseDoubleMatrix2D) m).getWrappedObject();
			BasicMatrix result = matrix.multiplyRight(mo);
			return new OjalgoDenseDoubleMatrix2D(result);
		} else {
			return super.mtimes(m);
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeObject(toDoubleArray());
	}

	private void readObject(ObjectInputStream s) throws IOException,
			ClassNotFoundException {
		s.defaultReadObject();
		double[][] data = (double[][]) s.readObject();
		this.matrix = PrimitiveMatrix.FACTORY.makeZero(data.length,
				data[0].length);
		for (int r = data.length; --r != -1;) {
			for (int c = data[0].length; --c != -1;) {
				setDouble(data[r][c], r, c);
			}
		}
	}

}

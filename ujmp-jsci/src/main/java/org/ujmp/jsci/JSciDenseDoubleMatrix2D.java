/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

package org.ujmp.jsci;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

import JSci.maths.matrices.AbstractDoubleMatrix;
import JSci.maths.matrices.AbstractDoubleSquareMatrix;
import JSci.maths.matrices.DoubleMatrix;
import JSci.maths.matrices.DoubleSquareMatrix;

public class JSciDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D
		implements Wrapper<AbstractDoubleMatrix> {
	private static final long serialVersionUID = -4314440110211101868L;

	private AbstractDoubleMatrix matrix = null;

	public JSciDenseDoubleMatrix2D(Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setAsDouble(source.getAsDouble(c), c);
		}
	}

	public JSciDenseDoubleMatrix2D(long... size) {
		if (Coordinates.product(size) != 0) {
			if (size[ROW] == size[COLUMN]) {
				this.matrix = new DoubleSquareMatrix((int) size[ROW]);
			} else {
				this.matrix = new DoubleMatrix((int) size[ROW],
						(int) size[COLUMN]);
			}
		}
	}

	public JSciDenseDoubleMatrix2D(AbstractDoubleMatrix m) {
		this.matrix = m;
	}

	public double getDouble(long row, long column) {
		return matrix.getElement((int) row, (int) column);
	}

	public double getDouble(int row, int column) {
		return matrix.getElement(row, column);
	}

	public long[] getSize() {
		return matrix == null ? Coordinates.ZERO2D : new long[] {
				matrix.rows(), matrix.columns() };
	}

	public void setDouble(double value, long row, long column) {
		matrix.setElement((int) row, (int) column, value);
	}

	public void setDouble(double value, int row, int column) {
		matrix.setElement(row, column, value);
	}

	public Matrix transpose() {
		return new JSciDenseDoubleMatrix2D((AbstractDoubleMatrix) matrix
				.transpose());
	}

	public Matrix inv() {
		if (matrix instanceof DoubleSquareMatrix) {
			return new JSciDenseDoubleMatrix2D(((DoubleSquareMatrix) matrix)
					.inverse());
		} else {
			throw new MatrixException("only allowed for square matrices");
		}
	}

	public Matrix chol() {
		if (matrix instanceof DoubleSquareMatrix) {
			AbstractDoubleSquareMatrix[] chol = ((DoubleSquareMatrix) matrix)
					.choleskyDecompose();
			return new JSciDenseDoubleMatrix2D(chol[0]);
		} else {
			throw new MatrixException("only allowed for square matrices");
		}
	}

	public Matrix[] lu() {
		if (matrix instanceof DoubleSquareMatrix) {
			AbstractDoubleSquareMatrix[] lu = ((DoubleSquareMatrix) matrix)
					.luDecompose();
			Matrix l = new JSciDenseDoubleMatrix2D(lu[0]);
			Matrix u = new JSciDenseDoubleMatrix2D(lu[1]);
			Matrix p = new JSciDenseDoubleMatrix2D(getRowCount(), getRowCount());
			p.eye(Ret.ORIG);
			return new Matrix[] { l, u, p };
		} else {
			throw new MatrixException("only allowed for square matrices");
		}
	}

	public Matrix[] qr() {
		if (matrix instanceof DoubleSquareMatrix) {
			AbstractDoubleSquareMatrix[] qr = ((DoubleSquareMatrix) matrix)
					.qrDecompose();
			Matrix q = new JSciDenseDoubleMatrix2D(qr[0]);
			Matrix r = new JSciDenseDoubleMatrix2D(qr[1]);
			return new Matrix[] { q, r };
		} else {
			throw new MatrixException("only allowed for square matrices");
		}
	}

	public Matrix[] svd() {
		if (matrix instanceof DoubleSquareMatrix) {
			AbstractDoubleSquareMatrix[] svd = ((DoubleSquareMatrix) matrix)
					.singularValueDecompose();
			Matrix u = new JSciDenseDoubleMatrix2D(svd[0]);
			Matrix s = new JSciDenseDoubleMatrix2D(svd[1]);
			Matrix v = new JSciDenseDoubleMatrix2D(svd[2]);
			return new Matrix[] { u, s, v };
		} else {
			throw new MatrixException("only allowed for square matrices");
		}
	}

	public Matrix mtimes(Matrix m) {
		if (m instanceof JSciDenseDoubleMatrix2D) {
			return new JSciDenseDoubleMatrix2D(matrix
					.multiply(((JSciDenseDoubleMatrix2D) m).matrix));
		} else {
			return super.mtimes(m);
		}
	}

	public AbstractDoubleMatrix getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(AbstractDoubleMatrix object) {
		this.matrix = object;
	}

}

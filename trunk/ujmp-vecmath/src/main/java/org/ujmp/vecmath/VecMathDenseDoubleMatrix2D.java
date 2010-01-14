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

package org.ujmp.vecmath;

import javax.vecmath.GMatrix;
import javax.vecmath.GVector;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

public class VecMathDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D
		implements Wrapper<GMatrix> {
	private static final long serialVersionUID = 3792684800581150214L;

	private GMatrix matrix = null;

	public VecMathDenseDoubleMatrix2D(GMatrix m) {
		this.matrix = m;
	}

	public VecMathDenseDoubleMatrix2D(long... size) {
		this.matrix = new GMatrix((int) size[ROW], (int) size[COLUMN]);
		// matrix is not empty by default!
		for (int r = 0; r < size[ROW]; r++) {
			for (int c = 0; c < size[COLUMN]; c++) {
				setDouble(0.0, r, c);
			}
		}
	}

	public VecMathDenseDoubleMatrix2D(Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setAsDouble(source.getAsDouble(c), c);
		}
	}

	public double getDouble(long row, long column) {
		return matrix.getElement((int) row, (int) column);
	}

	public double getDouble(int row, int column) {
		return matrix.getElement(row, column);
	}

	public long[] getSize() {
		return new long[] { matrix.getNumRow(), matrix.getNumCol() };
	}

	public void setDouble(double value, long row, long column) {
		matrix.setElement((int) row, (int) column, value);
	}

	public void setDouble(double value, int row, int column) {
		matrix.setElement(row, column, value);
	}

	public GMatrix getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(GMatrix object) {
		this.matrix = object;
	}

	public DenseDoubleMatrix2D transpose() {
		GMatrix m = (GMatrix) matrix.clone();
		m.transpose();
		return new VecMathDenseDoubleMatrix2D(m);
	}

	public DenseDoubleMatrix2D inv() {
		GMatrix m = (GMatrix) matrix.clone();
		m.invert();
		return new VecMathDenseDoubleMatrix2D(m);
	}

	public Matrix[] svd() {
		GMatrix m = (GMatrix) matrix.clone();
		int nrows = (int) getRowCount();
		int ncols = (int) getColumnCount();
		GMatrix u = new GMatrix(nrows, nrows);
		GMatrix s = new GMatrix(nrows, ncols);
		GMatrix v = new GMatrix(ncols, ncols);
		m.SVD(u, s, v);
		Matrix U = new VecMathDenseDoubleMatrix2D(u);
		Matrix S = new VecMathDenseDoubleMatrix2D(s);
		Matrix V = new VecMathDenseDoubleMatrix2D(v);
		return new Matrix[] { U, S, V };
	}

	public Matrix[] lu() {
		if (isSquare()) {
			GMatrix m = (GMatrix) matrix.clone();
			GMatrix l = (GMatrix) matrix.clone();
			GVector v = new GVector(matrix.getNumCol());
			m.LUD(l, v);
			return new Matrix[] {};
		} else {
			throw new MatrixException("only allowed for square matrices");
		}
	}

}

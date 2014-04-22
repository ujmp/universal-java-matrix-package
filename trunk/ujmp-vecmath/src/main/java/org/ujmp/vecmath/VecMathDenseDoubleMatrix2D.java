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

package org.ujmp.vecmath;

import javax.vecmath.GMatrix;
import javax.vecmath.GVector;

import org.ujmp.core.Matrix;
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.util.MathUtil;

public class VecMathDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D implements Wrapper<GMatrix> {
	private static final long serialVersionUID = 3792684800581150214L;

	public static final VecMathDenseDoubleMatrix2DFactory Factory = new VecMathDenseDoubleMatrix2DFactory();

	private final GMatrix matrix;

	public VecMathDenseDoubleMatrix2D(GMatrix m) {
		super(m.getNumRow(), m.getNumCol());
		this.matrix = m;
	}

	public VecMathDenseDoubleMatrix2D(int rows, int columns) {
		super(rows, columns);
		this.matrix = new GMatrix(rows, columns);
		// matrix is not empty by default!
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				setDouble(0.0, r, c);
			}
		}
	}

	public VecMathDenseDoubleMatrix2D(Matrix source) {
		this(MathUtil.longToInt(source.getRowCount()), MathUtil.longToInt(source.getColumnCount()));
		for (long[] c : source.availableCoordinates()) {
			setAsDouble(source.getAsDouble(c), c);
		}
		if (source.getAnnotation() != null) {
			setAnnotation(source.getAnnotation().clone());
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

	public VecMathDenseDoubleMatrix2D transpose() {
		GMatrix m = (GMatrix) matrix.clone();
		m.transpose();
		return new VecMathDenseDoubleMatrix2D(m);
	}

	public Matrix plus(Matrix m) {
		if (m instanceof VecMathDenseDoubleMatrix2D) {
			GMatrix result = (GMatrix) matrix.clone();
			result.add(((VecMathDenseDoubleMatrix2D) m).matrix);
			Matrix ret = new VecMathDenseDoubleMatrix2D(result);
			Annotation a = getAnnotation();
			if (a != null) {
				ret.setAnnotation(a.clone());
			}
			return ret;
		} else {
			return super.plus(m);
		}
	}

	public Matrix minus(Matrix m) {
		if (m instanceof VecMathDenseDoubleMatrix2D) {
			GMatrix result = (GMatrix) matrix.clone();
			result.sub(((VecMathDenseDoubleMatrix2D) m).matrix);
			Matrix ret = new VecMathDenseDoubleMatrix2D(result);
			Annotation a = getAnnotation();
			if (a != null) {
				ret.setAnnotation(a.clone());
			}
			return ret;
		} else {
			return super.minus(m);
		}
	}

	public Matrix mtimes(Matrix m) {
		if (m instanceof VecMathDenseDoubleMatrix2D) {
			GMatrix result = new GMatrix(matrix.getNumRow(), (int) m.getColumnCount());
			result.mul(matrix, ((VecMathDenseDoubleMatrix2D) m).matrix);
			Matrix ret = new VecMathDenseDoubleMatrix2D(result);
			Annotation a = getAnnotation();
			if (a != null) {
				ret.setAnnotation(a.clone());
			}
			return ret;
		} else {
			return super.mtimes(m);
		}
	}

	public DenseDoubleMatrix2D inv() {
		GMatrix m = (GMatrix) matrix.clone();
		m.invert();
		return new VecMathDenseDoubleMatrix2D(m);
	}

	// in S all entries on the diagonal are 1
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

	// non-singular matrices only
	public Matrix[] lu() {
		if (isSquare()) {
			GMatrix m = (GMatrix) matrix.clone();
			GMatrix lu = (GMatrix) matrix.clone();
			GVector piv = new GVector(matrix.getNumCol());
			m.LUD(lu, piv);
			Matrix l = new VecMathDenseDoubleMatrix2D(lu).tril(Ret.NEW, 0);
			for (int i = (int) l.getRowCount() - 1; i != -1; i--) {
				l.setAsDouble(1, i, i);
			}
			Matrix u = new VecMathDenseDoubleMatrix2D(lu).triu(Ret.NEW, 0);
			VecMathDenseDoubleMatrix2D p = new VecMathDenseDoubleMatrix2D(MathUtil.longToInt(getRowCount()),
					MathUtil.longToInt(getColumnCount()));
			for (int i = piv.getSize() - 1; i != -1; i--) {
				p.setDouble(1, i, (int) piv.getElement(i));
			}
			return new Matrix[] { l, u, p };
		} else {
			throw new RuntimeException("only allowed for square matrices");
		}
	}

	public VecMathDenseDoubleMatrix2DFactory getFactory() {
		return Factory;
	}

}

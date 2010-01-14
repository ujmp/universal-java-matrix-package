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

package org.ujmp.ejml;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.ejml.alg.dense.decomposition.chol.CholeskyDecompositionLDL;
import org.ejml.alg.dense.decomposition.lu.LUDecompositionAlt;
import org.ejml.alg.dense.decomposition.qr.QRDecompositionHouseholder;
import org.ejml.alg.dense.decomposition.svd.SvdNumericalRecipes;
import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

public class EJMLDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D
		implements Wrapper<DenseMatrix64F> {
	private static final long serialVersionUID = -3223474248020842822L;

	private transient DenseMatrix64F matrix = null;

	public EJMLDenseDoubleMatrix2D(long... size) {
		this.matrix = new DenseMatrix64F((int) size[ROW], (int) size[COLUMN]);
	}

	public EJMLDenseDoubleMatrix2D(DenseMatrix64F m) {
		this.matrix = (DenseMatrix64F) m;
	}

	public EJMLDenseDoubleMatrix2D(Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setAsDouble(source.getAsDouble(c), c);
		}
	}

	public double getDouble(long row, long column) {
		return matrix.get((int) row, (int) column);
	}

	public double getDouble(int row, int column) {
		return matrix.get(row, column);
	}

	public long[] getSize() {
		return new long[] { matrix.numRows, matrix.numCols };
	}

	public void setDouble(double value, long row, long column) {
		matrix.set((int) row, (int) column, value);
	}

	public void setDouble(double value, int row, int column) {
		matrix.set(row, column, value);
	}

	public DenseMatrix64F getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(DenseMatrix64F object) {
		this.matrix = object;
	}

	public Matrix transpose() {
		DenseMatrix64F ret = new DenseMatrix64F(matrix.numCols, matrix.numRows);
		CommonOps.transpose(matrix, ret);
		return new EJMLDenseDoubleMatrix2D(ret);
	}

	public Matrix inv() {
		DenseMatrix64F ret = new DenseMatrix64F(matrix.numRows, matrix.numCols);
		CommonOps.invert(matrix, ret);
		return new EJMLDenseDoubleMatrix2D(ret);
	}

	public Matrix solve(Matrix b) {
		if (b instanceof EJMLDenseDoubleMatrix2D) {
			EJMLDenseDoubleMatrix2D b2 = (EJMLDenseDoubleMatrix2D) b;
			DenseMatrix64F x = new DenseMatrix64F(matrix.numRows,
					matrix.numCols);
			CommonOps.solve(matrix, b2.matrix, x);
			return new EJMLDenseDoubleMatrix2D(x);
		} else {
			return super.solve(b);
		}
	}

	public Matrix plus(double value) {
		DenseMatrix64F ret = new DenseMatrix64F(matrix.numRows, matrix.numCols);
		CommonOps.add(matrix, value, ret);
		return new EJMLDenseDoubleMatrix2D(ret);
	}

	public Matrix times(double value) {
		DenseMatrix64F ret = new DenseMatrix64F(matrix.numRows, matrix.numCols);
		CommonOps.scale(value, matrix, ret);
		return new EJMLDenseDoubleMatrix2D(ret);
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
		SvdNumericalRecipes svd = new SvdNumericalRecipes();
		svd.decompose(matrix);
		Matrix u = new EJMLDenseDoubleMatrix2D(svd.getU());
		Matrix v = new EJMLDenseDoubleMatrix2D(svd.getV());
		double[] svs = svd.getW();
		Matrix s = MatrixFactory.sparse(u.getColumnCount(), v.getColumnCount());
		for (int i = (int) Math.min(s.getRowCount(), s.getColumnCount()); --i >= 0;) {
			s.setAsDouble(svs[i], i, i);
		}
		return new Matrix[] { u, s, v };
	}

	public Matrix[] qr() {
		if (matrix.numRows >= matrix.numCols) {
			QRDecompositionHouseholder qr = new QRDecompositionHouseholder();
			qr.decompose(matrix);
			DenseMatrix64F qm = new DenseMatrix64F(matrix.numRows,
					matrix.numRows);
			DenseMatrix64F rm = new DenseMatrix64F(matrix.numRows,
					matrix.numCols);
			qr.setToQ(qm);
			qr.setToR(rm, false);
			Matrix q = new EJMLDenseDoubleMatrix2D(qm);
			Matrix r = new EJMLDenseDoubleMatrix2D(rm);
			return new Matrix[] { q, r };
		} else {
			return super.qr();
		}
	}

	public Matrix chol() {
		CholeskyDecompositionLDL chol = new CholeskyDecompositionLDL();
		chol.decompose(matrix);
		Matrix l = new EJMLDenseDoubleMatrix2D(chol.getL());
		return l;
	}

	public Matrix[] lu() {
		if (isSquare()) {
			LUDecompositionAlt lu = new LUDecompositionAlt();
			lu.decompose(matrix);
			DenseMatrix64F lm = new DenseMatrix64F(matrix.numRows,
					matrix.numCols);
			DenseMatrix64F um = new DenseMatrix64F(matrix.numRows,
					matrix.numCols);
			lu.setToLower(lm);
			lu.setToUpper(um);
			Matrix l = new EJMLDenseDoubleMatrix2D(lm);
			Matrix u = new EJMLDenseDoubleMatrix2D(um);
			int[] piv = lu.getPivot();
			Matrix p = new EJMLDenseDoubleMatrix2D(matrix.numRows,
					matrix.numRows);
			for (int i = 0; i < matrix.numRows; i++) {
				p.setAsDouble(1, i, piv[i]);
			}
			return new Matrix[] { l, u, p };
		} else {
			return super.lu();
		}
	}

	public Matrix copy() {
		Matrix m = new EJMLDenseDoubleMatrix2D(matrix.copy());
		if (getAnnotation() != null) {
			m.setAnnotation(getAnnotation().clone());
		}
		return m;
	}

	private void readObject(ObjectInputStream s) throws IOException,
			ClassNotFoundException {
		s.defaultReadObject();
		int rows = (Integer) s.readObject();
		int columns = (Integer) s.readObject();
		double[] values = (double[]) s.readObject();
		matrix = new DenseMatrix64F(rows, columns, values);
	}

	private void writeObject(ObjectOutputStream s) throws IOException,
			MatrixException {
		s.defaultWriteObject();
		s.writeObject(matrix.numRows);
		s.writeObject(matrix.numCols);
		s.writeObject(matrix.data);
	}

}

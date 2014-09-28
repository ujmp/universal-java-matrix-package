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

package org.ujmp.jmatrices;

import org.jmatrices.dbl.MatrixFactory;
import org.jmatrices.dbl.decomposition.CholeskyDecomposition;
import org.jmatrices.dbl.decomposition.EigenvalueDecomposition;
import org.jmatrices.dbl.decomposition.LUDecomposition;
import org.jmatrices.dbl.decomposition.QRDecomposition;
import org.jmatrices.dbl.decomposition.SingularValueDecomposition;
import org.jmatrices.dbl.operator.MatrixOperator;
import org.jmatrices.dbl.transformer.MatrixTransformer;
import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.util.MathUtil;

public class JMatricesDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D implements
		Wrapper<org.jmatrices.dbl.Matrix> {
	private static final long serialVersionUID = 513251881654621L;

	public static final JMatricesDenseDoubleMatrix2DFactory Factory = new JMatricesDenseDoubleMatrix2DFactory();

	private final org.jmatrices.dbl.Matrix matrix;

	public JMatricesDenseDoubleMatrix2D(org.jmatrices.dbl.Matrix matrix) {
		super(matrix.rows(), matrix.cols());
		this.matrix = matrix;
	}

	public JMatricesDenseDoubleMatrix2D(int rows, int columns) {
		super(rows, columns);
		this.matrix = MatrixFactory.getMatrix(rows, columns, null);
	}

	public JMatricesDenseDoubleMatrix2D(org.ujmp.core.Matrix source) {
		this(MathUtil.longToInt(source.getRowCount()), MathUtil.longToInt(source.getColumnCount()));
		for (long[] c : source.availableCoordinates()) {
			setDouble(source.getAsDouble(c), c);
		}
		if (source.getMetaData() != null) {
			setMetaData(source.getMetaData().clone());
		}
	}

	public double getDouble(long row, long column) {
		return matrix.get((int) row + 1, (int) column + 1);
	}

	public double getDouble(int row, int column) {
		return matrix.get(row + 1, column + 1);
	}

	public void setDouble(double value, long row, long column) {
		matrix.set((int) row + 1, (int) column + 1, value);
	}

	public void setDouble(double value, int row, int column) {
		matrix.set(row + 1, column + 1, value);
	}

	public org.jmatrices.dbl.Matrix getWrappedObject() {
		return matrix;
	}

	public Matrix transpose() {
		return new JMatricesDenseDoubleMatrix2D(MatrixTransformer.transpose(matrix));
	}

	public Matrix inv() {
		return new JMatricesDenseDoubleMatrix2D(MatrixTransformer.inverse(matrix));
	}

	public Matrix[] eig() {
		EigenvalueDecomposition evd = new EigenvalueDecomposition(matrix);
		Matrix v = new JMatricesDenseDoubleMatrix2D(evd.getV());
		Matrix d = new JMatricesDenseDoubleMatrix2D(evd.getD());
		return new Matrix[] { v, d };
	}

	public Matrix[] qr() {
		if (getRowCount() >= getColumnCount()) {
			QRDecomposition qr = new QRDecomposition(matrix);
			Matrix q = new JMatricesDenseDoubleMatrix2D(qr.getQ());
			Matrix r = new JMatricesDenseDoubleMatrix2D(qr.getR());
			return new Matrix[] { q, r };
		} else {
			throw new RuntimeException("only allowed for matrices m>=n");
		}
	}

	public Matrix[] svd() {
		if (isSquare()) {
			SingularValueDecomposition qr = new SingularValueDecomposition(matrix);
			Matrix u = new JMatricesDenseDoubleMatrix2D(qr.getU());
			Matrix s = new JMatricesDenseDoubleMatrix2D(qr.getS());
			Matrix v = new JMatricesDenseDoubleMatrix2D(qr.getV());
			return new Matrix[] { u, s, v };
		} else {
			throw new RuntimeException("only allowed for square matrices");
		}
	}

	public Matrix chol() {
		CholeskyDecomposition chol = new CholeskyDecomposition(matrix);
		Matrix r = new JMatricesDenseDoubleMatrix2D(chol.getL());
		return r;
	}

	// some error
	// public Matrix invSPD() {
	// CholeskyDecomposition chol = new CholeskyDecomposition(matrix);
	// org.jmatrices.dbl.Matrix eye = MatrixFactory.getIdentityMatrix(matrix
	// .rows(), null);
	// return new JMatricesDenseDoubleMatrix2D(chol.solve(eye));
	// }

	public Matrix[] lu() {
		if (getRowCount() >= getColumnCount()) {
			LUDecomposition lu = new LUDecomposition(matrix);
			Matrix l = new JMatricesDenseDoubleMatrix2D(lu.getL());
			Matrix u = new JMatricesDenseDoubleMatrix2D(lu.getU().getSubMatrix(1, 1, (int) getColumnCount(),
					(int) getColumnCount()));
			int m = (int) getRowCount();
			int[] piv = lu.getPivot();
			Matrix p = new JMatricesDenseDoubleMatrix2D(m, m);
			for (int i = 0; i < m; i++) {
				p.setAsDouble(1, i, piv[i] - 1);
			}
			return new Matrix[] { l, u, p };
		} else {
			throw new RuntimeException("only allowed for matrices m>=n");
		}
	}

	public Matrix mtimes(Matrix m2) {
		if (m2 instanceof JMatricesDenseDoubleMatrix2D) {
			return new JMatricesDenseDoubleMatrix2D(MatrixOperator.multiply(matrix,
					((JMatricesDenseDoubleMatrix2D) m2).matrix));
		} else {
			return super.mtimes(m2);
		}
	}

	public Matrix solve(Matrix b) {
		if (b instanceof JMatricesDenseDoubleMatrix2D) {
			JMatricesDenseDoubleMatrix2D b2 = (JMatricesDenseDoubleMatrix2D) b;
			if (isSquare()) {
				org.jmatrices.dbl.Matrix x = new LUDecomposition(matrix).solve(b2.matrix);
				return new JMatricesDenseDoubleMatrix2D(x);
			} else {
				org.jmatrices.dbl.Matrix x = new QRDecomposition(matrix).solve(b2.matrix);
				return new JMatricesDenseDoubleMatrix2D(x);
			}
		} else {
			return super.solve(b);
		}
	}

	public JMatricesDenseDoubleMatrix2DFactory getFactory() {
		return Factory;
	}
}

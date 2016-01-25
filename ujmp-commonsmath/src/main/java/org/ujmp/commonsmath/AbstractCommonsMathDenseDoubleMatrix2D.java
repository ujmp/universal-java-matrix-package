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

package org.ujmp.commonsmath;

import org.apache.commons.math3.linear.CholeskyDecomposition;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.QRDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractCommonsMathDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D implements
		Wrapper<RealMatrix> {
	private static final long serialVersionUID = -1161807620507675926L;

	private final RealMatrix matrix;

	public AbstractCommonsMathDenseDoubleMatrix2D(RealMatrix matrix) {
		super(matrix.getRowDimension(), matrix.getColumnDimension());
		this.matrix = matrix;
	}

	public RealMatrix getWrappedObject() {
		return matrix;
	}

	public double getDouble(long row, long column) {
		return matrix.getEntry(MathUtil.longToInt(row), MathUtil.longToInt(column));
	}

	public double getDouble(int row, int column) {
		return matrix.getEntry(row, column);
	}

	public void setDouble(double value, long row, long column) {
		matrix.setEntry(MathUtil.longToInt(row), MathUtil.longToInt(column), value);
	}

	public void setDouble(double value, int row, int column) {
		matrix.setEntry(row, column, value);
	}

	public Matrix transpose() {
		return CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(matrix.transpose());
	}

	public Matrix inv() {
		return CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(new LUDecomposition(matrix).getSolver()
				.getInverse());
	}

	public Matrix invSPD() {
		try {
			return CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(new CholeskyDecomposition(matrix).getSolver()
					.getInverse());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Matrix[] lu() {
		LUDecomposition lu = new LUDecomposition(matrix);
		Matrix l = CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(lu.getL());
		Matrix u = CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(lu.getU());
		Matrix p = CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(lu.getP());
		return new Matrix[] { l, u, p };
	}

	public Matrix[] qr() {
		QRDecomposition qr = new QRDecomposition(matrix);
		Matrix q = CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(qr.getQ());
		Matrix r = CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(qr.getR());
		return new Matrix[] { q, r };
	}

	public Matrix[] svd() {
		SingularValueDecomposition svd = new SingularValueDecomposition(matrix);
		Matrix u = CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(svd.getU());
		Matrix s = CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(svd.getS());
		Matrix v = CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(svd.getV());
		return new Matrix[] { u, s, v };
	}

	public Matrix[] eig() {
		EigenDecomposition evd = new EigenDecomposition(matrix);
		Matrix v = CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(evd.getV());
		Matrix d = CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(evd.getD());
		return new Matrix[] { v, d };
	}

	public Matrix chol() {
		try {
			CholeskyDecomposition chol = new CholeskyDecomposition(matrix);
			Matrix l = CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(chol.getL());
			return l;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Matrix mtimes(Matrix m2) {
		if (m2 instanceof AbstractCommonsMathDenseDoubleMatrix2D) {
			return CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(matrix
					.multiply(((AbstractCommonsMathDenseDoubleMatrix2D) m2).matrix));
		} else {
			return super.mtimes(m2);
		}
	}

	public Matrix plus(Matrix m2) {
		if (m2 instanceof AbstractCommonsMathDenseDoubleMatrix2D) {
			Matrix result = CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(matrix
					.add(((AbstractCommonsMathDenseDoubleMatrix2D) m2).matrix));
			MapMatrix<String, Object> a = getMetaData();
			if (a != null) {
				result.setMetaData(a.clone());
			}
			return result;
		} else {
			return super.plus(m2);
		}
	}

	public Matrix minus(Matrix m2) {
		if (m2 instanceof AbstractCommonsMathDenseDoubleMatrix2D) {
			Matrix result = CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(matrix
					.subtract(((AbstractCommonsMathDenseDoubleMatrix2D) m2).matrix));
			MapMatrix<String, Object> a = getMetaData();
			if (a != null) {
				result.setMetaData(a.clone());
			}
			return result;
		} else {
			return super.minus(m2);
		}
	}

	public Matrix times(double value) {
		Matrix result = CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(matrix.scalarMultiply(value));
		MapMatrix<String, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
	}

	public Matrix divide(double value) {
		Matrix result = CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(matrix.scalarMultiply(1.0 / value));
		MapMatrix<String, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
	}

	public Matrix plus(double value) {
		Matrix result = CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(matrix.scalarAdd(value));
		MapMatrix<String, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
	}

	public Matrix minus(double value) {
		Matrix result = CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(matrix.scalarAdd(-value));
		MapMatrix<String, Object> a = getMetaData();
		if (a != null) {
			result.setMetaData(a.clone());
		}
		return result;
	}

	public Matrix solve(Matrix b) {
		if (b instanceof AbstractCommonsMathDenseDoubleMatrix2D) {
			AbstractCommonsMathDenseDoubleMatrix2D b2 = (AbstractCommonsMathDenseDoubleMatrix2D) b;
			if (isSquare()) {
				RealMatrix ret = new LUDecomposition(matrix).getSolver().solve(b2.matrix);
				return CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(ret);
			} else {
				RealMatrix ret = new QRDecomposition(matrix).getSolver().solve(b2.matrix);
				return CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(ret);
			}
		} else {
			return super.solve(b);
		}
	}

	public Matrix solveSPD(Matrix b) {
		try {
			if (b instanceof AbstractCommonsMathDenseDoubleMatrix2D) {
				AbstractCommonsMathDenseDoubleMatrix2D b2 = (AbstractCommonsMathDenseDoubleMatrix2D) b;
				RealMatrix ret = new CholeskyDecomposition(matrix).getSolver().solve(b2.matrix);
				return CommonsMathDenseDoubleMatrix2DFactory.INSTANCE.dense(ret);
			} else {
				return super.solve(b);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

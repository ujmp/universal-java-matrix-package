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

package org.ujmp.commonsmath;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.CholeskyDecomposition;
import org.apache.commons.math.linear.CholeskyDecompositionImpl;
import org.apache.commons.math.linear.EigenDecomposition;
import org.apache.commons.math.linear.EigenDecompositionImpl;
import org.apache.commons.math.linear.LUDecomposition;
import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.QRDecomposition;
import org.apache.commons.math.linear.QRDecompositionImpl;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.SingularValueDecomposition;
import org.apache.commons.math.linear.SingularValueDecompositionImpl;
import org.apache.commons.math.util.MathUtils;
import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

public class CommonsMathDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D
		implements Wrapper<Array2DRowRealMatrix> {
	private static final long serialVersionUID = -1161807620507675926L;

	private Array2DRowRealMatrix matrix = null;

	public CommonsMathDenseDoubleMatrix2D(long... size) {
		if (size[ROW] > 0 && size[COLUMN] > 0) {
			matrix = new Array2DRowRealMatrix((int) size[ROW],
					(int) size[COLUMN]);
		}
	}

	public CommonsMathDenseDoubleMatrix2D(org.ujmp.core.Matrix source)
			throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setAsDouble(source.getAsDouble(c), c);
		}
	}

	public CommonsMathDenseDoubleMatrix2D(RealMatrix matrix) {
		if (matrix instanceof Array2DRowRealMatrix) {
			this.matrix = (Array2DRowRealMatrix) matrix;
		} else {
			throw new MatrixException("Can oly use Array2DRowRealMatrix");
		}
	}

	public Array2DRowRealMatrix getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(Array2DRowRealMatrix object) {
		this.matrix = object;
	}

	public double getDouble(long row, long column) throws MatrixException {
		return matrix.getEntry((int) row, (int) column);
	}

	public double getDouble(int row, int column) throws MatrixException {
		return matrix.getEntry(row, column);
	}

	public void setDouble(double value, long row, long column)
			throws MatrixException {
		matrix.getDataRef()[(int) row][(int) column] = value;
	}

	public void setDouble(double value, int row, int column)
			throws MatrixException {
		matrix.getDataRef()[row][column] = value;
	}

	public long[] getSize() {
		return matrix == null ? Coordinates.ZERO2D : new long[] {
				matrix.getRowDimension(), matrix.getColumnDimension() };
	}

	public Matrix transpose() {
		return new CommonsMathDenseDoubleMatrix2D(matrix.transpose());
	}

	public Matrix inv() {
		return new CommonsMathDenseDoubleMatrix2D(new LUDecompositionImpl(
				matrix).getSolver().getInverse());
	}

	public Matrix[] lu() {
		LUDecomposition lu = new LUDecompositionImpl(matrix);
		Matrix l = new CommonsMathDenseDoubleMatrix2D(lu.getL());
		Matrix u = new CommonsMathDenseDoubleMatrix2D(lu.getU());
		return new Matrix[] { l, u };
	}

	public Matrix[] qr() {
		QRDecomposition qr = new QRDecompositionImpl(matrix);
		Matrix q = new CommonsMathDenseDoubleMatrix2D(qr.getQ());
		Matrix r = new CommonsMathDenseDoubleMatrix2D(qr.getR());
		return new Matrix[] { q, r };
	}

	public Matrix[] svd() {
		SingularValueDecomposition svd = new SingularValueDecompositionImpl(
				matrix);
		Matrix u = new CommonsMathDenseDoubleMatrix2D(svd.getU());
		Matrix s = new CommonsMathDenseDoubleMatrix2D(svd.getS());
		Matrix v = new CommonsMathDenseDoubleMatrix2D(svd.getV());
		return new Matrix[] { u, s, v };
	}

	public Matrix[] evd() {
		EigenDecomposition evd = new EigenDecompositionImpl(matrix,
				MathUtils.EPSILON);
		Matrix v = new CommonsMathDenseDoubleMatrix2D(evd.getV());
		Matrix d = new CommonsMathDenseDoubleMatrix2D(evd.getD());
		return new Matrix[] { v, d };
	}

	public Matrix chol() {
		try {
			CholeskyDecomposition chol = new CholeskyDecompositionImpl(matrix);
			Matrix l = new CommonsMathDenseDoubleMatrix2D(chol.getL());
			return l;
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public Matrix mtimes(Matrix m2) {
		if (m2 instanceof CommonsMathDenseDoubleMatrix2D) {
			return new CommonsMathDenseDoubleMatrix2D(matrix
					.multiply(((CommonsMathDenseDoubleMatrix2D) m2).matrix));
		} else {
			return super.mtimes(m2);
		}
	}
}

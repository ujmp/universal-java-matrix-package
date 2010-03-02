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

package org.ujmp.jblas;

import org.jblas.Decompose;
import org.jblas.DoubleMatrix;
import org.jblas.Eigen;
import org.jblas.Solve;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.jblas.calculation.Inv;

public class JBlasDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D
		implements Wrapper<DoubleMatrix> {
	private static final long serialVersionUID = 4929284378405884509L;

	private DoubleMatrix matrix = null;

	public JBlasDenseDoubleMatrix2D(long... size) {
		this.matrix = new DoubleMatrix((int) size[ROW], (int) size[COLUMN]);
	}

	public JBlasDenseDoubleMatrix2D(DoubleMatrix matrix) {
		this.matrix = matrix;
	}

	public JBlasDenseDoubleMatrix2D(Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setDouble(source.getAsDouble(c), c);
		}
	}

	public JBlasDenseDoubleMatrix2D(long rowCount, long columnCount,
			double[] doubleArray) {
		this.matrix = new DoubleMatrix((int) rowCount, (int) columnCount,
				doubleArray);
	}

	public Matrix inv() throws MatrixException {
		return Inv.INSTANCE.calc(this);
	}

	public double getDouble(long row, long column) {
		return matrix.get((int) row, (int) column);
	}

	public double getDouble(int row, int column) {
		return matrix.get(row, column);
	}

	public long[] getSize() {
		return new long[] { matrix.getRows(), matrix.getColumns() };
	}

	public void setDouble(double value, long row, long column) {
		matrix.put((int) row, (int) column, value);
	}

	public void setDouble(double value, int row, int column) {
		matrix.put(row, column, value);
	}

	public DoubleMatrix getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(DoubleMatrix object) {
		this.matrix = object;
	}

	public final Matrix copy() throws MatrixException {
		Matrix m = new JBlasDenseDoubleMatrix2D(matrix.dup());
		if (getAnnotation() != null) {
			m.setAnnotation(getAnnotation().clone());
		}
		return m;
	}

	public Matrix transpose() {
		return new JBlasDenseDoubleMatrix2D(matrix.transpose());
	}

	public Matrix[] lu() {
		Decompose.LUDecomposition<DoubleMatrix> lu = Decompose.lu(matrix);
		Matrix l = new JBlasDenseDoubleMatrix2D(lu.l);
		Matrix u = new JBlasDenseDoubleMatrix2D(lu.u);
		Matrix p = new JBlasDenseDoubleMatrix2D(lu.p.transpose());
		return new Matrix[] { l, u, p };
	}

	public Matrix[] eig() {
		DoubleMatrix[] eig = Eigen.symmetricEigenvectors(matrix);
		Matrix v = new JBlasDenseDoubleMatrix2D(eig[0]);
		Matrix d = new JBlasDenseDoubleMatrix2D(eig[1]);
		return new Matrix[] { v, d };
	}

	public Matrix chol() {
		DoubleMatrix r = Decompose.cholesky(matrix);
		return new JBlasDenseDoubleMatrix2D(r.transpose());
	}

	public Matrix mtimes(Matrix m) {
		if (m instanceof JBlasDenseDoubleMatrix2D) {
			DoubleMatrix r = new DoubleMatrix((int) getRowCount(), (int) m
					.getColumnCount());
			matrix.mmuli(((JBlasDenseDoubleMatrix2D) m).matrix, r);
			return new JBlasDenseDoubleMatrix2D(r);
		} else {
			return super.mtimes(m);
		}
	}

	public Matrix plus(Matrix m) {
		if (m instanceof JBlasDenseDoubleMatrix2D) {
			DoubleMatrix r = new DoubleMatrix((int) getRowCount(),
					(int) getColumnCount());
			matrix.addi(((JBlasDenseDoubleMatrix2D) m).matrix, r);
			return new JBlasDenseDoubleMatrix2D(r);
		} else {
			return super.mtimes(m);
		}
	}

	public Matrix minus(Matrix m) {
		if (m instanceof JBlasDenseDoubleMatrix2D) {
			DoubleMatrix r = new DoubleMatrix((int) getRowCount(),
					(int) getColumnCount());
			matrix.subi(((JBlasDenseDoubleMatrix2D) m).matrix, r);
			return new JBlasDenseDoubleMatrix2D(r);
		} else {
			return super.mtimes(m);
		}
	}

	public Matrix times(double value) {
		DoubleMatrix r = new DoubleMatrix((int) getRowCount(),
				(int) getColumnCount());
		return new JBlasDenseDoubleMatrix2D(matrix.muli(value, r));
	}

	public Matrix divide(double value) {
		DoubleMatrix r = new DoubleMatrix((int) getRowCount(),
				(int) getColumnCount());
		return new JBlasDenseDoubleMatrix2D(matrix.divi(value, r));
	}

	public Matrix plus(double value) {
		DoubleMatrix r = new DoubleMatrix((int) getRowCount(),
				(int) getColumnCount());
		return new JBlasDenseDoubleMatrix2D(matrix.addi(value, r));
	}

	public Matrix minus(double value) {
		DoubleMatrix r = new DoubleMatrix((int) getRowCount(),
				(int) getColumnCount());
		return new JBlasDenseDoubleMatrix2D(matrix.subi(value, r));
	}

	public Matrix solve(Matrix b) {
		if (b instanceof JBlasDenseDoubleMatrix2D) {
			JBlasDenseDoubleMatrix2D b2 = (JBlasDenseDoubleMatrix2D) b;
			DoubleMatrix x = Solve.solve(matrix, b2.getWrappedObject());
			return new JBlasDenseDoubleMatrix2D(x);
		} else {
			return super.solve(b);
		}
	}

}

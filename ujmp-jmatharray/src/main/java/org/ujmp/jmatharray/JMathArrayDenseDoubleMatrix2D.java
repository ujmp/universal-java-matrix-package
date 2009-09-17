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

package org.ujmp.jmatharray;

import org.math.array.DoubleArray;
import org.math.array.LinearAlgebra;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

public class JMathArrayDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D
		implements Wrapper<double[][]> {
	private static final long serialVersionUID = -3223474248020842822L;

	private double[][] matrix = null;

	public JMathArrayDenseDoubleMatrix2D(long... size) {
		this.matrix = new double[(int) size[ROW]][(int) size[COLUMN]];
	}

	public JMathArrayDenseDoubleMatrix2D(double[][] m) {
		this.matrix = m;
	}

	public JMathArrayDenseDoubleMatrix2D(Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setAsDouble(source.getAsDouble(c), c);
		}
	}

	public double getDouble(long row, long column) {
		return matrix[(int) row][(int) column];
	}

	public double getDouble(int row, int column) {
		return matrix[row][column];
	}

	public long[] getSize() {
		return new long[] { matrix.length,
				matrix.length == 0 ? 0 : matrix[0].length };
	}

	public void setDouble(double value, long row, long column) {
		matrix[(int) row][(int) column] = value;
	}

	public void setDouble(double value, int row, int column) {
		matrix[row][column] = value;
	}

	public double[][] getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(double[][] object) {
		this.matrix = object;
	}

	public Matrix transpose() {
		return new JMathArrayDenseDoubleMatrix2D(DoubleArray.transpose(matrix));
	}

	public Matrix inv() {
		return new JMathArrayDenseDoubleMatrix2D(LinearAlgebra.inverse(matrix));
	}

	public Matrix plus(double value) {
		return new JMathArrayDenseDoubleMatrix2D(DoubleArray.add(matrix, value));
	}

	public Matrix times(double value) {
		return new JMathArrayDenseDoubleMatrix2D(LinearAlgebra.times(matrix,
				value));
	}

	public Matrix mtimes(Matrix m) {
		if (m instanceof JMathArrayDenseDoubleMatrix2D) {
			return new JMathArrayDenseDoubleMatrix2D(LinearAlgebra.times(
					matrix, ((JMathArrayDenseDoubleMatrix2D) m).matrix));
		} else {
			return super.mtimes(m);
		}
	}

	public Matrix copy() {
		Matrix m = new JMathArrayDenseDoubleMatrix2D(DoubleArray.copy(matrix));
		if (getAnnotation() != null) {
			m.setAnnotation(getAnnotation().clone());
		}
		return m;
	}

}

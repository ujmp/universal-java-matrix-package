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

package org.ujmp.mantissa;

import org.spaceroots.mantissa.linalg.GeneralMatrix;
import org.spaceroots.mantissa.linalg.GeneralSquareMatrix;
import org.spaceroots.mantissa.linalg.SquareMatrix;
import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.UJMPSettings;

public class MantissaDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D implements
		Wrapper<org.spaceroots.mantissa.linalg.Matrix> {
	private static final long serialVersionUID = 6954233090244549806L;

	public static final MantissaDenseDoubleMatrix2DFactory Factory = new MantissaDenseDoubleMatrix2DFactory();

	private final org.spaceroots.mantissa.linalg.Matrix matrix;

	public MantissaDenseDoubleMatrix2D(org.spaceroots.mantissa.linalg.Matrix m) {
		super(m.getRows(), m.getColumns());
		this.matrix = m;
	}

	public MantissaDenseDoubleMatrix2D(int rows, int columns) {
		super(rows, columns);
		if (rows == columns) {
			this.matrix = new GeneralSquareMatrix(rows);
		} else {
			this.matrix = new GeneralMatrix(rows, columns);
		}
	}

	public MantissaDenseDoubleMatrix2D(Matrix source) {
		this(MathUtil.longToInt(source.getRowCount()), MathUtil.longToInt(source.getColumnCount()));
		for (long[] c : source.availableCoordinates()) {
			setDouble(source.getAsDouble(c), c);
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
		return matrix == null ? Coordinates.ZERO2D : new long[] { matrix.getRows(), matrix.getColumns() };
	}

	public void setDouble(double value, long row, long column) {
		matrix.setElement((int) row, (int) column, value);
	}

	public void setDouble(double value, int row, int column) {
		matrix.setElement(row, column, value);
	}

	public Matrix transpose() {
		return new MantissaDenseDoubleMatrix2D(matrix.getTranspose());
	}

	public Matrix inv() {
		if (matrix instanceof SquareMatrix) {
			try {
				return new MantissaDenseDoubleMatrix2D(((SquareMatrix) matrix).getInverse(UJMPSettings.getTolerance()));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException("only allowed for square matrices");
		}
	}

	public Matrix solve(Matrix b) {
		if (matrix instanceof SquareMatrix && b instanceof MantissaDenseDoubleMatrix2D) {
			try {
				org.spaceroots.mantissa.linalg.Matrix b2 = ((MantissaDenseDoubleMatrix2D) b).getWrappedObject();
				return new MantissaDenseDoubleMatrix2D(((SquareMatrix) matrix).solve(b2, UJMPSettings.getTolerance()));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException("only allowed for square matrices");
		}
	}

	public Matrix mtimes(Matrix m) {
		if (m instanceof MantissaDenseDoubleMatrix2D) {
			return new MantissaDenseDoubleMatrix2D(matrix.mul(((MantissaDenseDoubleMatrix2D) m).getWrappedObject()));
		} else {
			return super.mtimes(m);
		}
	}

	public org.spaceroots.mantissa.linalg.Matrix getWrappedObject() {
		return matrix;
	}

	public MantissaDenseDoubleMatrix2DFactory getFactory() {
		return Factory;
	}

}

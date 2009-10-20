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

package org.ujmp.mantissa;

import org.spaceroots.mantissa.linalg.GeneralMatrix;
import org.spaceroots.mantissa.linalg.GeneralSquareMatrix;
import org.spaceroots.mantissa.linalg.SquareMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.util.UJMPSettings;

public class MantissaDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D
		implements Wrapper<org.spaceroots.mantissa.linalg.Matrix> {
	private static final long serialVersionUID = 6954233090244549806L;

	private org.spaceroots.mantissa.linalg.Matrix matrix = null;

	public MantissaDenseDoubleMatrix2D(org.spaceroots.mantissa.linalg.Matrix m) {
		this.matrix = m;
	}

	public MantissaDenseDoubleMatrix2D(long... size) {
		if (size[ROW] > 0 && size[COLUMN] > 0) {
			if (size[ROW] == size[COLUMN]) {
				this.matrix = new GeneralSquareMatrix((int) size[ROW]);
			} else {
				this.matrix = new GeneralMatrix((int) size[ROW],
						(int) size[COLUMN]);
			}
		}
	}

	public MantissaDenseDoubleMatrix2D(Matrix source) throws MatrixException {
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
		return matrix == null ? Coordinates.ZERO2D : new long[] {
				matrix.getRows(), matrix.getColumns() };
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
				return new MantissaDenseDoubleMatrix2D(((SquareMatrix) matrix)
						.getInverse(UJMPSettings.getTolerance()));
			} catch (Exception e) {
				throw new MatrixException(e);
			}
		} else {
			throw new MatrixException("only allowed for square matrices");
		}
	}

	public Matrix mtimes(Matrix m) {
		if (m instanceof MantissaDenseDoubleMatrix2D) {
			return new MantissaDenseDoubleMatrix2D(matrix
					.mul(((MantissaDenseDoubleMatrix2D) m).getWrappedObject()));
		} else {
			return super.mtimes(m);
		}
	}

	public org.spaceroots.mantissa.linalg.Matrix getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(org.spaceroots.mantissa.linalg.Matrix object) {
		this.matrix = object;
	}

}

/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

package org.ujmp.core.matrices.misc;

import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealMatrixImpl;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.matrices.stubs.AbstractDenseDoubleMatrix2D;

public class CommonsMathRealMatrix extends AbstractDenseDoubleMatrix2D implements Wrapper<RealMatrixImpl> {
	private static final long serialVersionUID = -1161807620507675926L;

	private RealMatrixImpl matrix = null;

	public CommonsMathRealMatrix(long... size) {
		matrix = new RealMatrixImpl((int) size[ROW], (int) size[COLUMN]);
	}

	public CommonsMathRealMatrix(org.ujmp.core.Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setAsDouble(source.getAsDouble(c), c);
		}
	}

	public CommonsMathRealMatrix(RealMatrix matrix) {
		if (matrix instanceof RealMatrixImpl) {
			this.matrix = (RealMatrixImpl) matrix;
		} else {
			throw new MatrixException("Can oly use RealMatrixImpl");
		}
	}

	public RealMatrixImpl getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(RealMatrixImpl object) {
		this.matrix = object;
	}

	public double getDouble(long row, long column) throws MatrixException {
		return matrix.getEntry((int) row, (int) column);
	}

	public void setDouble(double value, long row, long column) throws MatrixException {
		matrix.getDataRef()[(int) row][(int) column] = value;
	}

	public long[] getSize() {
		return new long[] { matrix.getRowDimension(), matrix.getColumnDimension() };
	}

	@Override
	public Matrix inv() {
		return new CommonsMathRealMatrix(matrix.inverse());
	}

}

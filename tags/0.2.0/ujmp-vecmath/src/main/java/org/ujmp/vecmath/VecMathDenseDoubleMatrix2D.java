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

package org.ujmp.vecmath;

import javax.vecmath.GMatrix;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

public class VecMathDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D implements Wrapper<GMatrix> {
	private static final long serialVersionUID = 3792684800581150214L;

	private GMatrix matrix = null;

	public VecMathDenseDoubleMatrix2D(GMatrix m) {
		this.matrix = m;
	}

	public VecMathDenseDoubleMatrix2D(long... size) {
		this.matrix = new GMatrix((int) size[ROW], (int) size[COLUMN]);
		// matrix is not empty by default!
		for (int r = 0; r < size[ROW]; r++) {
			for (int c = 0; c < size[COLUMN]; c++) {
				setDouble(0.0, r, c);
			}
		}
	}

	public VecMathDenseDoubleMatrix2D(Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setAsDouble(source.getAsDouble(c), c);
		}
	}

	public double getDouble(long row, long column) {
		return matrix.getElement((int) row, (int) column);
	}

	public long[] getSize() {
		return new long[] { matrix.getNumRow(), matrix.getNumCol() };
	}

	public void setDouble(double value, long row, long column) {
		matrix.setElement((int) row, (int) column, value);
	}

	public GMatrix getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(GMatrix object) {
		this.matrix = object;
	}

}

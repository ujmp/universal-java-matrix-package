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

package org.ujmp.jsci;

import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

import JSci.maths.matrices.AbstractDoubleMatrix;
import JSci.maths.matrices.DoubleMatrix;

public class JSciDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D
		implements Wrapper<AbstractDoubleMatrix> {
	private static final long serialVersionUID = -4314440110211101868L;

	private AbstractDoubleMatrix matrix = null;

	public JSciDenseDoubleMatrix2D(Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setAsDouble(source.getAsDouble(c), c);
		}
	}

	public JSciDenseDoubleMatrix2D(long... size) {
		if (Coordinates.product(size) != 0) {
			this.matrix = new DoubleMatrix((int) size[ROW], (int) size[COLUMN]);
		}
	}

	public JSciDenseDoubleMatrix2D(AbstractDoubleMatrix m) {
		this.matrix = m;
	}

	public double getDouble(long row, long column) {
		return matrix.getElement((int) row, (int) column);
	}

	public double getDouble(int row, int column) {
		return matrix.getElement(row, column);
	}

	public long[] getSize() {
		return matrix == null ? Coordinates.ZERO2D : new long[] {
				matrix.rows(), matrix.columns() };
	}

	public void setDouble(double value, long row, long column) {
		matrix.setElement((int) row, (int) column, value);
	}

	public void setDouble(double value, int row, int column) {
		matrix.setElement(row, column, value);
	}

	
	public Matrix transpose() {
		return new JSciDenseDoubleMatrix2D((AbstractDoubleMatrix) matrix
				.transpose());
	}

	
	public Matrix mtimes(Matrix m) {
		if (m instanceof JSciDenseDoubleMatrix2D) {
			return new JSciDenseDoubleMatrix2D(matrix
					.multiply(((JSciDenseDoubleMatrix2D) m).matrix));
		} else {
			return super.mtimes(m);
		}
	}

	public AbstractDoubleMatrix getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(AbstractDoubleMatrix object) {
		this.matrix = object;
	}

}

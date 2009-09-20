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

package org.ujmp.orbital;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import orbital.math.Real;
import orbital.math.Values;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.interfaces.Wrapper;

public class OrbitalDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D
		implements Wrapper<orbital.math.Matrix> {

	private static final long serialVersionUID = 3094717557850722162L;

	private transient orbital.math.Matrix matrix = null;

	public OrbitalDenseDoubleMatrix2D(final long... size) {
		matrix = Values.getDefault().ZERO((int) size[ROW], (int) size[COLUMN]);
	}

	public OrbitalDenseDoubleMatrix2D(final Matrix m) {
		this(m.getSize());
		for (final long[] c : m.allCoordinates()) {
			this.setAsDouble(m.getAsDouble(c), c);
		}
	}

	public OrbitalDenseDoubleMatrix2D(final orbital.math.Matrix matrix) {
		this.matrix = matrix;
	}

	public double getDouble(final int row, final int column) {
		return ((Real) matrix.get(row, column)).doubleValue();
	}

	public double getDouble(final long row, final long column) {
		return ((Real) matrix.get((int) row, (int) column)).doubleValue();
	}

	public long[] getSize() {
		return new long[] { matrix.dimensions()[0], matrix.dimensions()[1] };
	}

	public orbital.math.Matrix getWrappedObject() {
		return matrix;
	}

	@Override
	public Matrix mtimes(final Matrix m) {
		if (m instanceof OrbitalDenseDoubleMatrix2D) {
			final orbital.math.Matrix mo = ((OrbitalDenseDoubleMatrix2D) m)
					.getWrappedObject();
			final orbital.math.Matrix result = matrix.multiply(mo);
			return new OrbitalDenseDoubleMatrix2D(result);
		} else {
			return super.mtimes(m);
		}
	}

	public void setDouble(final double value, final int row, final int column) {
		matrix.set(row, column, Values.getDefault().valueOf(value));
	}

	public void setDouble(final double value, final long row, final long column) {
		matrix.set((int) row, (int) column, Values.getDefault().valueOf(value));
	}

	public void setWrappedObject(final orbital.math.Matrix object) {
		matrix = object;
	}

	@Override
	public Matrix transpose() {
		return new OrbitalDenseDoubleMatrix2D(matrix.transpose());
	}

	@Override
	public Matrix inv() {
		return new OrbitalDenseDoubleMatrix2D((orbital.math.Matrix) matrix
				.inverse());
	}

	private void readObject(final ObjectInputStream s) throws IOException,
			ClassNotFoundException {
		s.defaultReadObject();
		final double[][] data = (double[][]) s.readObject();
		int rows = data.length;
		int cols = data[0].length;
		matrix = Values.getDefault().ZERO(rows, cols);
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				setDouble(data[r][c], r, c);
			}
		}
	}

	private void writeObject(final ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeObject(this.toDoubleArray());
	}

}
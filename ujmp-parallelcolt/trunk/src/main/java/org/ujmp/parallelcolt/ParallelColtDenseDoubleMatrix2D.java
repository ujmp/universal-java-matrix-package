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

package org.ujmp.parallelcolt;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

import cern.colt.matrix.tdouble.impl.DenseDoubleMatrix2D;

public class ParallelColtDenseDoubleMatrix2D extends
		AbstractDenseDoubleMatrix2D implements Wrapper<DenseDoubleMatrix2D> {
	private static final long serialVersionUID = -1941030601886654699L;

	private DenseDoubleMatrix2D matrix = null;

	public ParallelColtDenseDoubleMatrix2D(long... size) {
		this.matrix = new DenseDoubleMatrix2D((int) size[ROW],
				(int) size[COLUMN]);
	}

	public ParallelColtDenseDoubleMatrix2D(DenseDoubleMatrix2D m) {
		this.matrix = m;
	}

	public ParallelColtDenseDoubleMatrix2D(Matrix source)
			throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setAsDouble(source.getAsDouble(c), c);
		}
	}

	public double getDouble(long row, long column) {
		return matrix.getQuick((int) row, (int) column);
	}

	public double getDouble(int row, int column) {
		return matrix.getQuick(row, column);
	}

	public long[] getSize() {
		return new long[] { matrix.rows(), matrix.columns() };
	}

	public void setDouble(double value, long row, long column) {
		matrix.setQuick((int) row, (int) column, value);
	}

	public void setDouble(double value, int row, int column) {
		matrix.setQuick(row, column, value);
	}

	public DenseDoubleMatrix2D getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(DenseDoubleMatrix2D object) {
		this.matrix = object;
	}

}

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

package org.ujmp.colt;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.CoordinateSetToLongWrapper;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.AbstractSparseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import cern.jet.math.Functions;

public class ColtSparseDoubleMatrix2D extends AbstractSparseDoubleMatrix2D
		implements Wrapper<SparseDoubleMatrix2D> {
	private static final long serialVersionUID = -3223474248020842822L;

	private SparseDoubleMatrix2D matrix = null;

	public ColtSparseDoubleMatrix2D(long... size) {
		this.matrix = new SparseDoubleMatrix2D((int) size[ROW],
				(int) size[COLUMN]);
	}

	public ColtSparseDoubleMatrix2D(SparseDoubleMatrix2D m) {
		this.matrix = m;
	}

	public ColtSparseDoubleMatrix2D(Matrix source) throws MatrixException {
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

	public SparseDoubleMatrix2D getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(SparseDoubleMatrix2D object) {
		this.matrix = object;
	}

	@Override
	public Iterable<long[]> availableCoordinates() {
		return new AvailableCoordinateIterable();
	}

	class AvailableCoordinateIterable implements Iterable<long[]> {

		public Iterator<long[]> iterator() {
			Set<Coordinates> cset = new HashSet<Coordinates>();
			for (long r = getRowCount() - 1; r >= 0; r--) {
				for (long c = getColumnCount() - 1; c >= 0; c--) {
					if (getDouble(r, c) != 0.0) {
						cset.add(new Coordinates(r, c));
					}
				}
			}
			return new CoordinateSetToLongWrapper(cset).iterator();
		}
	}

	public final boolean contains(long... coordinates) {
		return getAsDouble(coordinates) != 0.0;
	}

	@Override
	public Matrix transpose() {
		return new ColtSparseDoubleMatrix2D((SparseDoubleMatrix2D) matrix
				.viewDice().copy());
	}

	@Override
	public Matrix plus(double value) {
		return new ColtSparseDoubleMatrix2D((SparseDoubleMatrix2D) matrix
				.copy().assign(Functions.plus(value)));
	}

	@Override
	public Matrix times(double value) {
		return new ColtSparseDoubleMatrix2D((SparseDoubleMatrix2D) matrix
				.copy().assign(Functions.mult(value)));
	}

	@Override
	public Matrix copy() {
		Matrix m = new ColtSparseDoubleMatrix2D((SparseDoubleMatrix2D) matrix
				.copy());
		if (getAnnotation() != null) {
			m.setAnnotation(getAnnotation().clone());
		}
		return m;
	}

	@Override
	public Matrix mtimes(Matrix m) {
		if (m instanceof ColtSparseDoubleMatrix2D) {
			SparseDoubleMatrix2D ret = new SparseDoubleMatrix2D(
					(int) getRowCount(), (int) m.getColumnCount());
			matrix.zMult(((ColtSparseDoubleMatrix2D) m).matrix, ret);
			return new ColtSparseDoubleMatrix2D(ret);
		} else {
			return super.mtimes(m);
		}
	}

}

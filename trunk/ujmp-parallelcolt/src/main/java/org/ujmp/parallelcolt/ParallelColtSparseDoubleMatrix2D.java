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

package org.ujmp.parallelcolt;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractSparseDoubleMatrix2D;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.util.CoordinateSetToLongWrapper;
import org.ujmp.core.util.MathUtil;

import cern.colt.matrix.tdouble.algo.DenseDoubleAlgebra;
import cern.colt.matrix.tdouble.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.tdouble.impl.SparseDoubleMatrix2D;

public class ParallelColtSparseDoubleMatrix2D extends AbstractSparseDoubleMatrix2D implements
		Wrapper<SparseDoubleMatrix2D> {
	private static final long serialVersionUID = -3223474248020842822L;

	public static final ParallelColtSparseDoubleMatrix2DFactory Factory = new ParallelColtSparseDoubleMatrix2DFactory();

	private final SparseDoubleMatrix2D matrix;

	public ParallelColtSparseDoubleMatrix2D(int rows, int columns) {
		super(rows, columns);
		this.matrix = new SparseDoubleMatrix2D(rows, columns);
	}

	public ParallelColtSparseDoubleMatrix2D(SparseDoubleMatrix2D m) {
		super(m.rows(), m.columns());
		this.matrix = m;
	}

	public ParallelColtSparseDoubleMatrix2D(Matrix source) {
		this(MathUtil.longToInt(source.getRowCount()), MathUtil.longToInt(source.getColumnCount()));
		for (long[] c : source.availableCoordinates()) {
			setDouble(source.getAsDouble(c), c);
		}
		if (source.getMetaData() != null) {
			setMetaData(source.getMetaData().clone());
		}
	}

	public final void clear() {
		matrix.elements().clear();
	}

	public double getDouble(long row, long column) {
		return matrix.getQuick((int) row, (int) column);
	}

	public double getDouble(int row, int column) {
		return matrix.getQuick(row, column);
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

	public Matrix inv() {
		return new ParallelColtDenseDoubleMatrix2D((DenseDoubleMatrix2D) new DenseDoubleAlgebra().inverse(matrix));
	}

	public Iterable<long[]> availableCoordinates() {
		return new AvailableCoordinateIterable();
	}

	class AvailableCoordinateIterable implements Iterable<long[]> {

		// TODO: optimize
		public Iterator<long[]> iterator() {
			Set<Coordinates> cset = new HashSet<Coordinates>();
			for (long r = getRowCount() - 1; r >= 0; r--) {
				for (long c = getColumnCount() - 1; c >= 0; c--) {
					if (getDouble(r, c) != 0.0) {
						cset.add(Coordinates.wrap(r, c).clone());
					}
				}
			}
			return new CoordinateSetToLongWrapper(cset).iterator();
		}
	}

	public final boolean containsCoordinates(long... coordinates) {
		return getAsDouble(coordinates) != 0.0;
	}

	public ParallelColtSparseDoubleMatrix2DFactory getFactory() {
		return Factory;
	}
}

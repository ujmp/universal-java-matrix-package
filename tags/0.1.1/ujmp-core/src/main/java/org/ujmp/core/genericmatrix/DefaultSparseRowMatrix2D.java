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

package org.ujmp.core.genericmatrix;

import java.util.ArrayList;
import java.util.List;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.coordinates.CoordinateIterator2D;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class DefaultSparseRowMatrix2D<A> extends AbstractSparseGenericMatrix<A> {
	private static final long serialVersionUID = -5291604525500706427L;

	private long[] size = new long[] { 1, 1 };

	private final List<Matrix> rows = new ArrayList<Matrix>();

	public DefaultSparseRowMatrix2D(long... size) {
		setSize(size);
	}

	public DefaultSparseRowMatrix2D(Matrix m) {
		setSize(m.getSize());
		for (long[] c : m.availableCoordinates()) {
			setObject(m.getObject(c), c);
		}
	}

	@Override
	public A getObject(long... coordinates) throws MatrixException {
		Matrix m = rows.get((int) coordinates[ROW]);
		return (A) m.getObject(0, coordinates[COLUMN]);
	}

	public Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	// TODO: this is certainly not the optimal way to do it!
	@Override
	public Iterable<long[]> availableCoordinates() {
		List<long[]> coordinates = new ArrayList<long[]>();
		for (int r = 0; r < size[ROW]; r++) {
			for (long[] c : rows.get(r).availableCoordinates()) {
				coordinates.add(Coordinates.plus(c, new long[] { r, 0 }));
			}
		}
		return coordinates;
	}

	public boolean contains(long... coordinates) {
		if (Coordinates.isSmallerThan(coordinates, size)) {
			return getObject(coordinates) != null;
		} else {
			return false;
		}
	}

	public double getAsDouble(long... coordinates) throws MatrixException {
		return MathUtil.getDouble(getObject(coordinates));
	}

	public void setAsDouble(double value, long... coordinates) throws MatrixException {
		setObject(value, coordinates);
	}

	public void setObject(Object o, long... coordinates) throws MatrixException {
		Matrix m = rows.get((int) coordinates[ROW]);
		m.setObject(o, 0, coordinates[COLUMN]);
	}

	public ValueType getValueType() {
		return ValueType.GENERIC;
	}

	public long[] getSize() {
		return size;
	}

	@Override
	public void setSize(long... size) {
		while (rows.size() < size[ROW]) {
			rows.add(new DefaultSparseGenericMatrix<A>(1l, size[COLUMN]));
		}

		if (this.size[COLUMN] != size[COLUMN]) {
			for (Matrix m : rows) {
				m.setSize(1, size[COLUMN]);
			}
		}
		this.size = size;
	}

	public Matrix getRow(long row) {
		return rows.get((int) row);
	}

	@Override
	public Matrix max(Ret returnType, int dimension) throws MatrixException {
		if (returnType == Ret.NEW) {

			if (dimension == ROW) {
				Matrix ret = MatrixFactory.zeros(1, getColumnCount());
				for (long[] c : availableCoordinates()) {
					double v = getAsDouble(c);
					if (v > ret.getAsDouble(0, c[COLUMN])) {
						ret.setAsDouble(v, 0, c[COLUMN]);
					}
				}
				return ret;
			} else if (dimension == COLUMN) {
				Matrix ret = MatrixFactory.zeros(getRowCount(), 1);
				for (long[] c : availableCoordinates()) {
					double v = getAsDouble(c);
					if (v > ret.getAsDouble(c[ROW], 0)) {
						ret.setAsDouble(v, c[ROW], 0);
					}
				}
				return ret;
			}

		}
		throw new MatrixException("not supported");
	}

	@Override
	public Matrix selectRows(Ret returnType, long... rows) throws MatrixException {
		if (returnType == Ret.LINK && rows.length == 1) {
			return getRow(rows[0]);
		}
		return super.selectRows(returnType, rows);
	}

}

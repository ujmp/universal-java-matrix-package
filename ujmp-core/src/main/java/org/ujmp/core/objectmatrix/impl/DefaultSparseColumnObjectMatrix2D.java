/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

package org.ujmp.core.objectmatrix.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.objectmatrix.stub.AbstractSparseObjectMatrix2D;

public class DefaultSparseColumnObjectMatrix2D extends AbstractSparseObjectMatrix2D implements
		Wrapper<Map<Long, Matrix>> {
	private static final long serialVersionUID = -1943118812754494387L;

	protected Map<Long, Matrix> columns = new HashMap<Long, Matrix>();

	public DefaultSparseColumnObjectMatrix2D(long rows, long columns) {
		super(rows, columns);
		setSize(new long[] { rows, columns });
	}

	public DefaultSparseColumnObjectMatrix2D(Matrix m) {
		super(m.getRowCount(), m.getColumnCount());
		setSize(m.getSize());
		for (long[] c : m.availableCoordinates()) {
			setObject(m.getAsObject(c), c);
		}
		if (m.getMetaData() != null) {
			setMetaData(m.getMetaData().clone());
		}
	}

	public Object getObject(long row, long column) {
		Matrix m = columns.get(column);
		return m == null ? null : m.getAsObject(row, 0);
	}

	public Object getObject(int row, int column) {
		Matrix m = columns.get(column);
		return m == null ? null : m.getAsObject(row, 0);
	}

	public Iterable<long[]> availableCoordinates() {
		return new NonZeroIterable(this);
	}

	public boolean containsCoordinates(long... coordinates) {
		if (Coordinates.isSmallerThan(coordinates, size)) {
			return getObject(coordinates) != null;
		} else {
			return false;
		}
	}

	public void setObject(Object o, long row, long column) {
		Matrix m = columns.get(column);
		if (m == null) {
			m = new DefaultSparseObjectMatrix(getRowCount(), 1);
			columns.put(column, m);
		}
		m.setAsObject(o, row, 0);
	}

	public void setObject(Object o, int row, int column) {
		setObject(o, (long) row, (long) column);
	}

	public void setSize(long... size) {
		if (this.size[ROW] != size[ROW]) {
			for (Matrix m : columns.values()) {
				m.setSize(size[ROW], 1);
			}
		}
		this.size = size;
	}

	public final void clear() {
		columns.clear();
	}

	public Matrix getColumn(long column) {
		return columns.get((int) column);
	}

	public Matrix max(Ret returnType, int dimension) {
		if (returnType == Ret.NEW) {

			if (dimension == ROW) {
				Matrix ret = Matrix.Factory.zeros(1, getColumnCount());
				for (long[] c : availableCoordinates()) {
					double v = getAsDouble(c);
					if (v > ret.getAsDouble(0, c[COLUMN])) {
						ret.setAsDouble(v, 0, c[COLUMN]);
					}
				}
				return ret;
			} else if (dimension == COLUMN) {
				Matrix ret = Matrix.Factory.zeros(getRowCount(), 1);
				for (long[] c : availableCoordinates()) {
					double v = getAsDouble(c);
					if (v > ret.getAsDouble(c[ROW], 0)) {
						ret.setAsDouble(v, c[ROW], 0);
					}
				}
				return ret;
			}

		}
		throw new RuntimeException("not supported");
	}

	public Matrix selectColumns(Ret returnType, long... columns) {
		if (returnType == Ret.LINK && columns.length == 1) {
			return getColumn(columns[0]);
		} else {
			return super.selectColumns(returnType, columns);
		}
	}

	public Map<Long, Matrix> getWrappedObject() {
		return columns;
	}

	public void setWrappedObject(Map<Long, Matrix> object) {
		this.columns = object;
	}

}

class NonZeroIterable implements Iterable<long[]> {

	private final DefaultSparseColumnObjectMatrix2D matrix;

	public NonZeroIterable(DefaultSparseColumnObjectMatrix2D matrix) {
		this.matrix = matrix;
	}

	public Iterator<long[]> iterator() {
		return new NonZeroIterator(matrix);
	}

}

class NonZeroIterator implements Iterator<long[]> {

	private final DefaultSparseColumnObjectMatrix2D matrix;
	private final long[] coordinates = new long[] { -1, -1 };
	private final Iterator<Long> columnIterator;
	private Iterator<long[]> rowIterator;
	private long currentColumn;

	public NonZeroIterator(DefaultSparseColumnObjectMatrix2D matrix) {
		this.matrix = matrix;
		columnIterator = matrix.columns.keySet().iterator();
		while (columnIterator.hasNext() && (rowIterator == null || !rowIterator.hasNext())) {
			currentColumn = columnIterator.next();
			rowIterator = matrix.columns.get(currentColumn).availableCoordinates().iterator();
		}
	}

	public boolean hasNext() {
		return rowIterator != null && rowIterator.hasNext();
	}

	public long[] next() {
		final long[] rowCoordinates = rowIterator.next();
		coordinates[Matrix.ROW] = rowCoordinates[Matrix.ROW];
		coordinates[Matrix.COLUMN] = currentColumn;
		if (!rowIterator.hasNext()) {
			while (columnIterator.hasNext() && (rowIterator == null || !rowIterator.hasNext())) {
				currentColumn = columnIterator.next();
				rowIterator = matrix.columns.get(currentColumn).availableCoordinates().iterator();
			}
		}
		return coordinates;
	}

	public void remove() {
		throw new RuntimeException("cannot modify matrix");
	}

}
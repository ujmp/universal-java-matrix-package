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

package org.ujmp.core.doublematrix.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.stub.AbstractSparseDoubleMatrix2D;
import org.ujmp.core.util.DefaultSparseDoubleVector1D;

public class DefaultSparseRowDoubleMatrix2D extends AbstractSparseDoubleMatrix2D {
	private static final long serialVersionUID = -5291604525500706427L;

	protected final Map<Long, DefaultSparseDoubleVector1D> rows = new HashMap<Long, DefaultSparseDoubleVector1D>();

	public DefaultSparseRowDoubleMatrix2D(long rows, long columns) {
		super(rows, columns);
		setSize(new long[] { rows, columns });
	}

	public DefaultSparseRowDoubleMatrix2D(Matrix m) {
		super(m.getRowCount(), m.getColumnCount());
		setSize(m.getSize());
		for (long[] c : m.availableCoordinates()) {
			setDouble(m.getAsDouble(c), c);
		}
		if (m.getMetaData() != null) {
			setMetaData(m.getMetaData().clone());
		}
	}

	public double getDouble(long row, long column) {
		Matrix m = rows.get(row);
		return m == null ? 0.0 : m.getAsDouble(0, column);
	}

	public double getDouble(int row, int column) {
		return getDouble((long) row, (long) column);
	}

	public Iterable<long[]> availableCoordinates() {
		return new NonZeroIterable(this);
	}

	public boolean containsCoordinates(long... coordinates) {
		return getDouble(coordinates) != 0.0;
	}

	public void setDouble(double o, long row, long column) {
		DefaultSparseDoubleVector1D m = rows.get(row);
		if (m == null) {
			m = new DefaultSparseDoubleVector1D(1l, getColumnCount());
			rows.put(row, m);
		}
		m.setAsDouble(o, 0, column);
	}

	public void setDouble(double o, int row, int column) {
		setDouble(o, (long) row, (long) column);
	}

	public void setSize(long... size) {
		if (this.size[COLUMN] != size[COLUMN]) {
			for (Matrix m : rows.values()) {
				m.setSize(1, size[COLUMN]);
			}
		}
		this.size = size;
	}

	public Matrix getRow(long row) {
		return rows.get(row);
	}

	public Matrix selectRows(Ret returnType, long... rows) {
		if (returnType == Ret.LINK && rows.length == 1) {
			return getRow(rows[0]);
		} else {
			return super.selectRows(returnType, rows);
		}
	}

	public final void clear() {
		rows.clear();
	}

}

class NonZeroIterable implements Iterable<long[]> {

	private final DefaultSparseRowDoubleMatrix2D matrix;

	public NonZeroIterable(DefaultSparseRowDoubleMatrix2D matrix) {
		this.matrix = matrix;
	}

	public Iterator<long[]> iterator() {
		return new NonZeroIterator(matrix);
	}

}

class NonZeroIterator implements Iterator<long[]> {

	private final DefaultSparseRowDoubleMatrix2D matrix;
	private final long[] coordinates = new long[] { -1, -1 };
	private final Iterator<Long> rowIterator;
	private Iterator<long[]> columnIterator;
	private long currentRow;

	public NonZeroIterator(DefaultSparseRowDoubleMatrix2D matrix) {
		this.matrix = matrix;
		rowIterator = matrix.rows.keySet().iterator();
		while (rowIterator.hasNext() && (columnIterator == null || !columnIterator.hasNext())) {
			currentRow = rowIterator.next();
			columnIterator = matrix.rows.get(currentRow).availableCoordinates().iterator();
		}
	}

	public boolean hasNext() {
		return columnIterator != null && columnIterator.hasNext();
	}

	public long[] next() {
		final long[] rowCoordinates = columnIterator.next();
		coordinates[Matrix.ROW] = currentRow;
		coordinates[Matrix.COLUMN] = rowCoordinates[Matrix.ROW];
		if (!columnIterator.hasNext()) {
			while (rowIterator.hasNext() && (columnIterator == null || !columnIterator.hasNext())) {
				currentRow = rowIterator.next();
				columnIterator = matrix.rows.get(currentRow).availableCoordinates().iterator();
			}
		}
		return coordinates;
	}

	public void remove() {
		throw new RuntimeException("cannot modify matrix");
	}

}
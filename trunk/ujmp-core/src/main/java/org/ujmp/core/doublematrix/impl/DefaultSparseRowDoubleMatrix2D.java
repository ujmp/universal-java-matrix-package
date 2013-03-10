/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.stub.AbstractSparseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.objectmatrix.impl.DefaultSparseObjectMatrix;

public class DefaultSparseRowDoubleMatrix2D extends AbstractSparseDoubleMatrix2D implements
		Wrapper<Map<Long, Matrix>> {
	private static final long serialVersionUID = -5291604525500706427L;

	private long[] size = new long[] { 1, 1 };

	private Map<Long, Matrix> rows = new HashMap<Long, Matrix>();

	public DefaultSparseRowDoubleMatrix2D(long... size) {
		super(size);
		setSize(size);
	}

	public DefaultSparseRowDoubleMatrix2D(Matrix m) {
		super(m);
		setSize(m.getSize());
		for (long[] c : m.availableCoordinates()) {
			setDouble(m.getAsDouble(c), c);
		}
	}

	public double getDouble(long row, long column) throws MatrixException {
		Matrix m = rows.get(row);
		return m == null ? null : m.getAsDouble(0, column);
	}

	public double getDouble(int row, int column) throws MatrixException {
		Matrix m = rows.get(row);
		return m == null ? null : m.getAsDouble(0, column);
	}

	// TODO: this is certainly not the optimal way to do it!

	public Iterable<long[]> availableCoordinates() {
		List<long[]> coordinates = new ArrayList<long[]>();
		for (Long r : rows.keySet()) {
			Matrix m = rows.get(r);
			for (long[] c : m.availableCoordinates()) {
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

	public void setDouble(double o, long row, long column) throws MatrixException {
		Matrix m = rows.get(row);
		if (m == null) {
			// TODO: there should be a faster implementation than this:
			m = new DefaultSparseObjectMatrix((long) 1, getColumnCount());
			rows.put(row, m);
		}
		m.setAsDouble(o, 0, column);
	}

	public void setDouble(double o, int row, int column) throws MatrixException {
		setDouble(o, (long) row, (long) column);
	}

	public long[] getSize() {
		return size;
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

	public Matrix selectRows(Ret returnType, long... rows) throws MatrixException {
		if (returnType == Ret.LINK && rows.length == 1) {
			return getRow(rows[0]);
		}
		return super.selectRows(returnType, rows);
	}

	public Map<Long, Matrix> getWrappedObject() {
		return rows;
	}

	public void setWrappedObject(Map<Long, Matrix> object) {
		this.rows = object;
	}

}

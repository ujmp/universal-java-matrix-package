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

package org.ujmp.core.matrices.basic;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.matrices.stubs.AbstractDenseGenericMatrix2D;

public class DefaultDenseGenericMatrix2D<A> extends AbstractDenseGenericMatrix2D<A> {
	private static final long serialVersionUID = 3132491298449205914L;

	private Object[][] values = null;

	public DefaultDenseGenericMatrix2D(Matrix m) throws MatrixException {
		values = new Object[(int) m.getRowCount()][(int) m.getColumnCount()];
		for (long[] c : m.allCoordinates()) {
			setObject(m.getObject(c), c);
		}
	}

	public DefaultDenseGenericMatrix2D(long... size) {
		values = new Object[(int) size[ROW]][(int) size[COLUMN]];
	}

	public long[] getSize() {
		return new long[] { values.length, values[0].length };
	}

	@Override
	public long getRowCount() {
		return values.length;
	}

	@Override
	public long getColumnCount() {
		return values[0].length;
	}

	public A getObject(long row, long column) {
		return (A) values[(int) row][(int) column];
	}

	public void setObject(Object value, long row, long column) {
		values[(int) row][(int) column] = value;
	}

	public double getAsDouble(long... coordinates) throws MatrixException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setAsDouble(double value, long... coordinates) throws MatrixException {
		// TODO Auto-generated method stub

	}

	public EntryType getEntryType() {
		return EntryType.GENERIC;
	}

}
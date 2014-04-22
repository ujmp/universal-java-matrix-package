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

package org.ujmp.core.stringmatrix.impl;

import org.ujmp.core.DenseMatrix2D;
import org.ujmp.core.Matrix;
import org.ujmp.core.matrix.factory.DenseMatrix2DFactory;
import org.ujmp.core.stringmatrix.stub.AbstractDenseStringMatrix2D;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;

public class SimpleDenseStringMatrix2D extends AbstractDenseStringMatrix2D {
	private static final long serialVersionUID = -4292004796378125964L;

	private String[][] values = null;

	public SimpleDenseStringMatrix2D(String string) {
		super(0, 0);
		string = string.replaceAll(StringUtil.BRACKETS, "");
		String[] rows = string.split(StringUtil.SEMICOLONORNEWLINE);
		String[] cols = rows[0].split(StringUtil.COLONORSPACES);
		values = new String[rows.length][cols.length];
		for (int r = 0; r < rows.length; r++) {
			cols = rows[r].split(StringUtil.COLONORSPACES);
			for (int c = 0; c < cols.length; c++) {
				values[r][c] = cols[c];
			}
		}
	}

	public SimpleDenseStringMatrix2D(Matrix source) {
		this(MathUtil.longToInt(source.getRowCount()), MathUtil.longToInt(source.getColumnCount()));
		for (long[] c : source.availableCoordinates()) {
			setAsString(source.getAsString(c), c);
		}
	}

	public SimpleDenseStringMatrix2D(int rows, int columns) {
		super(rows, columns);
		values = new String[rows][columns];
	}

	public SimpleDenseStringMatrix2D(String[]... values) {
		super(values.length, values[0].length);
		this.values = values;
	}

	public long[] getSize() {
		return new long[] { values.length, values.length == 0 ? 0 : values[0].length };
	}

	public long getRowCount() {
		return values.length;
	}

	public long getColumnCount() {
		return values.length == 0 ? 0 : values[0].length;
	}

	public String getString(long row, long column) {
		return values[(int) row][(int) column];
	}

	public void setString(String value, long row, long column) {
		values[(int) row][(int) column] = value;
	}

	public DenseMatrix2DFactory<? extends DenseMatrix2D> getFactory() {
		throw new RuntimeException("not implemented");
	}

}

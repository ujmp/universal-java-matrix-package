/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

package org.ujmp.core.objectmatrix.calculation;

import java.util.Collection;

import org.ujmp.core.Matrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;

public class Selection extends AbstractObjectCalculation {
	private static final long serialVersionUID = 4576183558391811345L;

	private final long[][] selection;
	private final long[] size;

	public Selection(Matrix m, String selectionString) {
		this(m, StringUtil.parseSelection(selectionString, m.getSize()));
	}

	public Selection(Matrix m, Collection<? extends Number>... selection) {
		super(m);
		this.selection = new long[selection.length][];
		if (selection[ROW] != null) {
			this.selection[ROW] = MathUtil.collectionToLongArray(selection[ROW]);
		}
		if (selection[COLUMN] != null) {
			this.selection[COLUMN] = MathUtil.collectionToLongArray(selection[COLUMN]);
		}
		if (this.selection[ROW] != null && this.selection[COLUMN] != null) {
			size = new long[] { this.selection[ROW].length, this.selection[COLUMN].length };
		} else {
			if (this.selection[ROW] == null) {
				size = new long[] { getSource().getRowCount(), this.selection[COLUMN].length };
			} else {
				size = new long[] { this.selection[ROW].length, getSource().getColumnCount() };
			}
		}
		createAnnotation();
	}

	public Selection(Matrix m, long[]... selection) {
		super(m);
		this.selection = selection;
		if (selection[ROW] != null && selection[COLUMN] != null) {
			size = new long[] { selection[ROW].length, selection[COLUMN].length };
		} else {
			if (selection[ROW] == null) {
				size = new long[] { getSource().getRowCount(), selection[COLUMN].length };
			} else {
				size = new long[] { selection[ROW].length, getSource().getColumnCount() };
			}
		}
		createAnnotation();
	}

	private void createAnnotation() {
		if (getSource().getDimensionCount() != 2) {
			throw new RuntimeException("only supported for 2d matrices");
		}
		MapMatrix<String, Object> a = getSource().getMetaData();
		if (a != null) {
			MapMatrix<String, Object> anew = new DefaultMapMatrix<String, Object>();
			anew.put(Matrix.LABEL, a.get(Matrix.LABEL));
			if (selection[ROW] == null) {
				long rowCount = getSource().getRowCount();
				for (int r = 0; r < rowCount; r++) {
					// anew.setAxisAnnotation(Matrix.COLUMN,
					// a.getAxisAnnotation(Matrix.COLUMN, r, 0),
					// r, 0);
				}
			} else {
				for (int r = 0; r < selection[ROW].length; r++) {
					// anew.setAxisAnnotation(Matrix.COLUMN,
					// a.getAxisAnnotation(Matrix.COLUMN, selection[ROW][r], 0),
					// r, 0);
				}
			}
			if (selection[COLUMN] == null) {
				long colCount = getSource().getColumnCount();
				for (int c = 0; c < colCount; c++) {
					// anew.setAxisAnnotation(Matrix.ROW,
					// a.getAxisAnnotation(Matrix.ROW, 0, c), 0, c);
				}
			} else {
				for (int c = 0; c < selection[COLUMN].length; c++) {
					// anew.setAxisAnnotation(Matrix.ROW,
					// a.getAxisAnnotation(Matrix.ROW, 0, selection[COLUMN][c]),
					// 0, c);
				}
			}
			setMetaData(anew);
		}
	}

	public Object getObject(long... coordinates) {
		if (selection[ROW] != null && selection[COLUMN] != null) {
			return getSource().getAsObject(selection[ROW][(int) coordinates[ROW]],
					selection[COLUMN][(int) coordinates[COLUMN]]);
		} else {
			if (selection[ROW] == null) {
				return getSource().getAsObject(coordinates[ROW],
						selection[COLUMN][(int) coordinates[COLUMN]]);
			} else {
				return getSource().getAsObject(selection[ROW][(int) coordinates[ROW]],
						coordinates[COLUMN]);
			}
		}
	}

	public long[] getSize() {
		return size;
	}

	public void setObject(Object value, long... coordinates) {
		if (selection[ROW] != null && selection[COLUMN] != null) {
			getSource().setAsObject(value, selection[ROW][(int) coordinates[ROW]],
					selection[COLUMN][(int) coordinates[COLUMN]]);
		} else {
			if (selection[ROW] == null) {
				getSource().setAsObject(value, coordinates[ROW],
						selection[COLUMN][(int) coordinates[COLUMN]]);
			} else {
				getSource().setAsObject(value, selection[ROW][(int) coordinates[ROW]],
						coordinates[COLUMN]);
			}
		}
	}

}

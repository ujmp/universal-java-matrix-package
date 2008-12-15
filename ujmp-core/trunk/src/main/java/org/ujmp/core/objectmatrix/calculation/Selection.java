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

package org.ujmp.core.objectmatrix.calculation;

import java.util.Collection;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;

public class Selection extends AbstractObjectCalculation {
	private static final long serialVersionUID = 4576183558391811345L;

	private long[][] selection = null;

	public Selection(Matrix m, String selectionString) {
		super(m);
		selection = StringUtil.parseSelection(selectionString, m.getSize());
	}

	public Selection(Matrix m, Collection<? extends Number>... selection) {
		super(m);
		this.selection = new long[selection.length][];
		if (selection[ROW] != null) {
			this.selection[ROW] = MathUtil.collectionToLong(selection[ROW]);
		}
		if (selection[COLUMN] != null) {
			this.selection[COLUMN] = MathUtil.collectionToLong(selection[COLUMN]);
		}
	}

	public Selection(Matrix m, long[]... selection) {
		super(m);
		this.selection = selection;
	}

	@Override
	public Object getObject(long... coordinates) throws MatrixException {
		if (selection[ROW] != null && selection[COLUMN] != null) {
			return getSource().getObject(selection[ROW][(int) coordinates[ROW]],
					selection[COLUMN][(int) coordinates[COLUMN]]);
		} else {
			if (selection[ROW] == null) {
				return getSource().getObject(coordinates[ROW],
						selection[COLUMN][(int) coordinates[COLUMN]]);
			} else {
				return getSource().getObject(selection[ROW][(int) coordinates[ROW]],
						coordinates[COLUMN]);
			}
		}
	}

	@Override
	public long[] getSize() {
		if (selection[ROW] != null && selection[COLUMN] != null) {
			return new long[] { selection[ROW].length, selection[COLUMN].length };
		} else {
			if (selection[ROW] == null) {
				return new long[] { getSource().getRowCount(), selection[COLUMN].length };
			} else {
				return new long[] { selection[ROW].length, getSource().getColumnCount() };
			}
		}
	}

	@Override
	public void setObject(Object value, long... coordinates) throws MatrixException {
		if (selection[ROW] != null && selection[COLUMN] != null) {
			getSource().setObject(value, selection[ROW][(int) coordinates[ROW]],
					selection[COLUMN][(int) coordinates[COLUMN]]);
		} else {
			if (selection[ROW] == null) {
				getSource().setObject(value, coordinates[ROW],
						selection[COLUMN][(int) coordinates[COLUMN]]);
			} else {
				getSource().setObject(value, selection[ROW][(int) coordinates[ROW]],
						coordinates[COLUMN]);
			}
		}
	}

}

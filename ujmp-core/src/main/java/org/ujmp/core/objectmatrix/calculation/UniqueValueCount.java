/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

import java.util.HashSet;
import java.util.Set;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public class UniqueValueCount extends AbstractObjectCalculation {
	private static final long serialVersionUID = -5621298156781794790L;

	public UniqueValueCount(Matrix m, int dimension) {
		super(dimension, m);
	}

	public Object getObject(long... coordinates) throws MatrixException {
		Set<Object> set = new HashSet<Object>();
		switch (getDimension()) {
		case ROW:
			for (int r = 0; r < getSource().getRowCount(); r++) {
				set.add(getSource().getAsObject(r, coordinates[COLUMN]));
			}
			return set.size();
		case COLUMN:
			for (int c = 0; c < getSource().getColumnCount(); c++) {
				set.add(getSource().getAsObject(coordinates[ROW], c));
			}
			return set.size();
		default:
			for (long[] c : getSource().availableCoordinates()) {
				set.add(getSource().getAsObject(c));
			}
			return set.size();
		}
	}

	public long[] getSize() {
		switch (getDimension()) {
		case ROW:
			return new long[] { 1, getSource().getColumnCount() };
		case COLUMN:
			return new long[] { getSource().getRowCount(), 1 };
		default:
			return new long[] { 1, 1 };
		}
	}

}

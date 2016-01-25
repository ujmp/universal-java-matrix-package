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

package org.ujmp.core.objectmatrix.calculation;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;

public class Swap extends AbstractObjectCalculation {
	private static final long serialVersionUID = -6182660661622835051L;

	private long pos1 = 0;
	private long pos2 = 0;

	public Swap(int dimension, long pos1, long pos2, Matrix m) {
		super(dimension, m);
		this.pos1 = pos1;
		this.pos2 = pos2;
	}

	public Object getObject(long... coordinates) {
		if (pos1 == pos2) {
			return getSource().getAsObject(coordinates);
		} else if (coordinates[getDimension()] == pos1) {
			coordinates = Coordinates.copyOf(coordinates);
			coordinates[getDimension()] = pos2;
			return getSource().getAsObject(coordinates);
		} else if (coordinates[getDimension()] == pos2) {
			coordinates = Coordinates.copyOf(coordinates);
			coordinates[getDimension()] = pos1;
			return getSource().getAsObject(coordinates);
		} else {
			return getSource().getAsObject(coordinates);
		}
	}

	public final Matrix calcOrig() {
		Matrix m = getSource();
		if (m.getDimensionCount() > 2) {
			throw new RuntimeException("ORIG works only for 2d matrices, use LINK or COPY instead");
		}

		if (getDimension() == Matrix.ROW) {
			long length = m.getColumnCount();
			for (long i = 0; i < length; i++) {
				Object temp = m.getAsObject(pos1, i);
				m.setAsObject(m.getAsObject(pos2, i), pos1, i);
				m.setAsObject(temp, pos2, i);
			}
		} else if (getDimension() == Matrix.COLUMN) {
			long length = m.getRowCount();
			for (long i = 0; i < length; i++) {
				Object temp = m.getAsObject(i, pos1);
				m.setAsObject(m.getAsObject(i, pos2), i, pos1);
				m.setAsObject(temp, i, pos2);
			}
		} else {
			throw new RuntimeException("this only works for rows or columns");
		}

		return getSource();
	}
}

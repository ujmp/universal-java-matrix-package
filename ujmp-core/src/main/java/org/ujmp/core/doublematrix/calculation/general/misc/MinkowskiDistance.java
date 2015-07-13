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

package org.ujmp.core.doublematrix.calculation.general.misc;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;

public class MinkowskiDistance extends AbstractDoubleCalculation {
	private static final long serialVersionUID = -182036616301344756L;

	private final long size[];
	private final double p;
	private final boolean ignoreNaN;

	public MinkowskiDistance(Matrix matrix, double p, boolean ignoreNaN) {
		super(matrix);
		this.p = p;
		this.size = new long[] { matrix.getRowCount(), matrix.getRowCount() };
		this.ignoreNaN = ignoreNaN;
	}

	public double getDouble(long... coordinates) {
		Matrix m1 = getSource().selectRows(Ret.LINK, coordinates[ROW]);
		Matrix m2 = getSource().selectRows(Ret.LINK, coordinates[COLUMN]);
		return m1.minkowskiDistanceTo(m2, p, ignoreNaN);
	}

	public long[] getSize() {
		return size;
	}

}

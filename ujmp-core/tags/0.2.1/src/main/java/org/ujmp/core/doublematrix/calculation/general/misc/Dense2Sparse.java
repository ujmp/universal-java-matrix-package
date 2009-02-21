/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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
import org.ujmp.core.MatrixFactory;

public class Dense2Sparse {

	public static Matrix calc(Matrix indices) {
		Matrix m = MatrixFactory.sparse(1, 1);

		long mrow = 1;
		long mcol = 1;

		for (int r = 0; r < indices.getRowCount(); r++) {

			if (r % 1000 == 0) {
				System.out.println("Row: " + r);
			}

			long row = (long) indices.getAsDouble(r, 0);
			long col = (long) indices.getAsDouble(r, 1);
			double val = indices.getAsDouble(r, 2);

			if (row >= mrow) {
				mrow = row + 1;
				m.setSize(mrow, mcol);
			}

			if (col >= mcol) {
				mcol = col + 1;
				m.setSize(mrow, mcol);
			}

			m.setAsDouble(val, row, col);

		}

		return m;
	}

}

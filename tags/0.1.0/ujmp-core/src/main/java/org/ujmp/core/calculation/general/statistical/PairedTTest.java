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

package org.ujmp.core.calculation.general.statistical;

import org.apache.commons.math.stat.inference.TestUtils;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.AbstractDoubleCalculation;
import org.ujmp.core.exceptions.MatrixException;

public class PairedTTest extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 9074733842439986005L;

	public PairedTTest(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		try {
			long var1 = coordinates[ROW];
			long var2 = coordinates[COLUMN];
			double[] sample1 = new double[(int) getSource().getRowCount()];
			double[] sample2 = new double[(int) getSource().getRowCount()];
			for (int r = 0; r < getSource().getRowCount(); r++) {
				sample1[r] = getSource().getAsDouble(r, var1);
				sample2[r] = getSource().getAsDouble(r, var2);
			}
			double pValue = TestUtils.pairedTTest(sample1, sample2);
			return pValue;
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@Override
	public long[] getSize() {
		return new long[] { getSource().getColumnCount(), getSource().getColumnCount() };
	}

}

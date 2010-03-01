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

package org.ujmp.core.doublematrix.calculation.general.statistical;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class Cumsum extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 5652893080805473737L;

	private Matrix cumsum = null;

	private boolean ignoreNaN = true;

	public Cumsum(Matrix m, boolean ignoreNaN) {
		super(m);
		this.ignoreNaN = ignoreNaN;
	}

	public double getDouble(long... coordinates) throws MatrixException {
		if (cumsum == null) {
			createMatrix();
		}
		return cumsum.getAsDouble(coordinates);
	}

	private void createMatrix() {
		Matrix source = getSource();
		Matrix m = Matrix.factory.dense(source.getSize());
		for (long c = 0; c < source.getColumnCount(); c++) {
			double sum = 0;
			for (long r = 0; r < source.getRowCount(); r++) {
				sum += ignoreNaN ? MathUtil.ignoreNaN(source.getAsDouble(r, c)) : source
						.getAsDouble(r, c);
				m.setAsDouble(sum, r, c);
			}
		}
		cumsum = m;
	}
}

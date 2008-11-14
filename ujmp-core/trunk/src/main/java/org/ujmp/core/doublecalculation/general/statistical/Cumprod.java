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

package org.ujmp.core.doublecalculation.general.statistical;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.doublecalculation.AbstractDoubleCalculation;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class Cumprod extends AbstractDoubleCalculation {
	private static final long serialVersionUID = -3784193604269223968L;

	private Matrix cumprod = null;

	private boolean ignoreNaN = true;

	public Cumprod(Matrix m, boolean ignoreNaN) {
		super(m);
		this.ignoreNaN = ignoreNaN;
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		if (cumprod == null) {
			createMatrix();
		}
		return cumprod.getAsDouble(coordinates);
	}

	private void createMatrix() {
		Matrix source = getSource();
		Matrix m = MatrixFactory.zeros(source.getSize());
		for (long c = 0; c < source.getColumnCount(); c++) {
			double prod = 1;
			for (long r = 0; r < source.getRowCount(); r++) {
				double v = source.getAsDouble(r, c);
				if (ignoreNaN || !MathUtil.isNaNOrInfinite(v)) {
					prod *= v;
				}
				m.setAsDouble(prod, r, c);
			}
		}
		cumprod = m;
	}

}

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

package org.ujmp.core.doublematrix.calculation.general.decomposition;

import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.UJMPSettings;

public class Pinv extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 7886298456216056038L;

	private Matrix pinv = null;

	public Pinv(Matrix matrix) {
		super(matrix);
	}

	
	public double getDouble(long... coordinates) throws MatrixException {
		if (pinv == null) {

			Matrix[] ms = getSource().svd();
			Matrix u = ms[0];
			Matrix s = ms[1];
			Matrix v = ms[2];

			for (int i = (int) Math.min(s.getRowCount(), s.getColumnCount()); --i >= 0;) {
				double d = s.getAsDouble(i, i);
				if (Math.abs(d) > UJMPSettings.getTolerance()) {
					s.setAsDouble(1.0 / d, i, i);
				}
			}

			pinv = v.mtimes(s.transpose()).mtimes(u.transpose());

		}
		return pinv.getAsDouble(coordinates);
	}

	
	public long[] getSize() {
		return Coordinates.transpose(getSource().getSize());
	}

}

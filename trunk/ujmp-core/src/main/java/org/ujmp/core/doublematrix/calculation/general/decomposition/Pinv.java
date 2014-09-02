/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.util.UJMPSettings;

public class Pinv extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 7886298456216056038L;

	private Matrix pinv = null;

	public Pinv(Matrix matrix) {
		super(matrix);
	}

	public double getDouble(long... coordinates) {
		if (pinv == null) {

			Matrix[] usv = getSource().svd();
			Matrix u = usv[0];
			Matrix s = usv[1];
			Matrix v = usv[2];

			for (int i = (int) Math.min(s.getRowCount(), s.getColumnCount()); --i >= 0;) {
				double d = s.getAsDouble(i, i);
				if (Math.abs(d) > UJMPSettings.getInstance().getTolerance()) {
					s.setAsDouble(1.0 / d, i, i);
				} else {
					s.setAsDouble(0.0, i, i);
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

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

package org.ujmp.core.objectmatrix.impl;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.objectmatrix.stub.AbstractDenseObjectMatrix;
import org.ujmp.core.util.MathUtil;

public class DefaultDenseObjectMatrixMultiD extends AbstractDenseObjectMatrix {
	private static final long serialVersionUID = 4312852021159459897L;

	private final Object[] values;
	private final int length;

	public DefaultDenseObjectMatrixMultiD(Matrix m) {
		super(m.getSize());
		this.size = Coordinates.copyOf(m.getSize());
		this.length = (int) Coordinates.product(size);
		this.values = new Object[length];
		if (m instanceof DefaultDenseObjectMatrixMultiD) {
			Object[] v = ((DefaultDenseObjectMatrixMultiD) m).values;
			System.arraycopy(v, 0, this.values, 0, v.length);
		} else {
			for (long[] c : m.allCoordinates()) {
				setObject(m.getAsObject(c), c);
			}
		}
	}

	public DefaultDenseObjectMatrixMultiD(long... size) {
		super(size);
		this.size = Coordinates.copyOf(size);
		this.length = (int) Coordinates.product(size);
		this.values = new Object[length];
	}

	public DefaultDenseObjectMatrixMultiD(Object[] v, long... size) {
		super(size);
		this.size = Coordinates.copyOf(size);
		this.length = (int) Coordinates.product(size);
		this.values = v;
	}

	public final Object getObject(long... pos) {
		return values[(int) MathUtil.pos2IndexRowMajor(size, pos)];
	}

	public final void setObject(Object value, long... pos) {
		values[(int) MathUtil.pos2IndexRowMajor(size, pos)] = value;
	}

}

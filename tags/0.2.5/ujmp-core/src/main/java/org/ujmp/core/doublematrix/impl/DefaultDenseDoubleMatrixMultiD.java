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

package org.ujmp.core.doublematrix.impl;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class DefaultDenseDoubleMatrixMultiD extends AbstractDenseDoubleMatrix {
	private static final long serialVersionUID = 2875235320924485070L;

	private double[] values = null;

	private long[] size = null;

	private int length = 0;

	public DefaultDenseDoubleMatrixMultiD(Matrix m) throws MatrixException {
		this.size = Coordinates.copyOf(m.getSize());
		this.length = (int) Coordinates.product(size);
		this.values = new double[length];
		if (m instanceof DefaultDenseDoubleMatrixMultiD) {
			double[] v = ((DefaultDenseDoubleMatrixMultiD) m).values;
			System.arraycopy(v, 0, this.values, 0, v.length);
		} else {
			for (long[] c : m.allCoordinates()) {
				setDouble(m.getAsDouble(c), c);
			}
		}
	}

	public DefaultDenseDoubleMatrixMultiD(long... size) {
		this.size = Coordinates.copyOf(size);
		this.length = (int) Coordinates.product(size);
		this.values = new double[length];
	}

	public DefaultDenseDoubleMatrixMultiD(double[] v, long... size) {
		this.size = Coordinates.copyOf(size);
		this.length = (int) Coordinates.product(size);
		this.values = v;
	}

	public final long[] getSize() {
		return size;
	}

	public final double getDouble(long... pos) {
		return values[(int) MathUtil.pos2IndexRowMajor(size, pos)];
	}

	public final void setDouble(double value, long... pos) {
		values[(int) MathUtil.pos2IndexRowMajor(size, pos)] = value;
	}

}

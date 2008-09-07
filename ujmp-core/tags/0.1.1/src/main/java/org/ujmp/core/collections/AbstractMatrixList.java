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

package org.ujmp.core.collections;

import org.ujmp.core.Matrix;

public abstract class AbstractMatrixList implements MatrixList {

	public final synchronized Matrix getFirst() {
		if (isEmpty()) {
			return null;
		}
		return get(0);
	}

	public final synchronized Matrix getLast() {
		if (isEmpty()) {
			return null;
		}
		return get(size() - 1);
	}

	public final double[][] getTrace(int i) {
		try {
			int hsize = size();
			double[][] ret = new double[hsize][2];
			for (int a = 0; a < hsize; a++) {
				Matrix m = get(a);
				ret[a][0] = 0.0;
				ret[a][1] = m.getAsDouble(i % m.getRowCount(), i / m.getRowCount());
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public final double getMaxTime() {
		Matrix m = getLast();
		return (m == null) ? 0.0 : 0.0;
	}

	public final double getMinTime() {
		Matrix m = getFirst();
		return (m == null) ? 0.0 : 0.0;
	}

	public final double getLength() {
		return getMaxTime() - getMinTime();
	}

	public final long getTraceCount() {
		Matrix m = getFirst();
		return (m == null) ? 0 : m.getColumnCount() * m.getRowCount();
	}

	public final void addAll(MatrixList matrices) {
		for (Matrix m : matrices) {
			add(m);
		}
	}

}

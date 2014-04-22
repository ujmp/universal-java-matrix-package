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

package org.ujmp.core.doublematrix.factory;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.SparseDoubleMatrix2D;

public abstract class AbstractSparseDoubleMatrix2DFactory<T extends SparseDoubleMatrix2D>
		implements SparseDoubleMatrix2DFactory<T> {
	private static final long serialVersionUID = 8897245731712863035L;

	public Matrix zeros(long... size) {
		return zeros(size[Matrix.ROW], size[Matrix.COLUMN]);
	}

	public final T rand(final long rows, final long cols) {
		final T m = zeros(rows, cols);
		m.rand(Ret.ORIG);
		return m;
	}

	public final T randn(final long rows, final long cols) {
		final T m = zeros(rows, cols);
		m.randn(Ret.ORIG);
		return m;
	}

	public final T ones(final long rows, final long cols) {
		final T m = zeros(rows, cols);
		m.ones(Ret.ORIG);
		return m;
	}

	public final T eye(final long rows, final long cols) {
		final T m = zeros(rows, cols);
		m.eye(Ret.ORIG);
		return m;
	}
}

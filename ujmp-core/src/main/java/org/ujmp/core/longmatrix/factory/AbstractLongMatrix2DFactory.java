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

package org.ujmp.core.longmatrix.factory;

import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Eye;
import org.ujmp.core.longmatrix.LongMatrix2D;

public abstract class AbstractLongMatrix2DFactory<T extends LongMatrix2D> extends
		AbstractLongMatrixFactory<T> implements LongMatrix2DFactory<T> {
	private static final long serialVersionUID = -2174717178618432412L;

	public T zeros(final long... size) {
		return zeros(size[ROW], size[COLUMN]);
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
		Eye.calcOrig(m);
		return m;
	}

}

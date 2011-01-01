/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

package org.ujmp.core.matrix.factory;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Eye;

public abstract class AbstractMatrixFactory implements MatrixFactoryRoot {
	private static final long serialVersionUID = -3618488741326819828L;

	public Matrix rand(final long... size) {
		final Matrix m = zeros(size);
		m.rand(Ret.ORIG);
		return m;
	}

	public Matrix randn(final long... size) {
		final Matrix m = zeros(size);
		m.randn(Ret.ORIG);
		return m;
	}

	public Matrix ones(final long... size) {
		final Matrix m = zeros(size);
		m.ones(Ret.ORIG);
		return m;
	}

	public Matrix eye(final long... size) {
		final Matrix m = zeros(size);
		Eye.calcOrig(m);
		return m;
	}

}

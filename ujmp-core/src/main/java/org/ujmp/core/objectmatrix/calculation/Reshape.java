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

package org.ujmp.core.objectmatrix.calculation;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.VerifyUtil;

public class Reshape extends AbstractObjectCalculation {
	private static final long serialVersionUID = -4298270756453090584L;

	private final long[] newSize;

	private final long[] oldSize;

	public Reshape(Matrix source, long... newSize) {
		super(source);
		this.newSize = newSize;
		this.oldSize = source.getSize();
		VerifyUtil.verifyEquals(Coordinates.product(oldSize), Coordinates.product(newSize),
				"new matrix must have the same number of cells");
	}

	public long[] getSize() {
		return newSize;
	}

	public Object getObject(long... coordinates) {
		long index = MathUtil.pos2IndexColumnMajor(newSize, coordinates);
		long[] oldPos = MathUtil.index2PosColumnMajor(oldSize, index);
		return getSource().getAsObject(oldPos);
	}

	@Override
	public void setObject(Object value, long... coordinates) {
		long index = MathUtil.pos2IndexColumnMajor(newSize, coordinates);
		long[] oldPos = MathUtil.index2PosColumnMajor(oldSize, index);
		getSource().setAsObject(value, oldPos);
	}

}

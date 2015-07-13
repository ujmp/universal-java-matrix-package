/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

import java.util.Arrays;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;

public class SubMatrix extends AbstractObjectCalculation {
	private static final long serialVersionUID = -195020778878294803L;

	private static final int maxTmpCoordinates = 100;

	private final long[] min;
	private final long[] max;
	private final long[] size;

	private final long[][] tmpCoordinates;
	private int runningId = 0;

	public SubMatrix(final Matrix m, final long... minAndMaxCoordinates) {
		super(m);
		if (minAndMaxCoordinates.length < 4 || minAndMaxCoordinates.length % 2 != 0) {
			throw new IllegalArgumentException("incorrect min and max values");
		}
		min = Arrays.copyOfRange(minAndMaxCoordinates, 0, minAndMaxCoordinates.length / 2);
		max = Arrays.copyOfRange(minAndMaxCoordinates, minAndMaxCoordinates.length / 2,
				minAndMaxCoordinates.length);
		size = Coordinates.minus(max, min);
		for (int i = size.length; --i != -1;) {
			size[i] = size[i] + 1;
		}
		tmpCoordinates = new long[maxTmpCoordinates][size.length];
	}

	public Object getObject(final long... coordinates) {
		runningId = ++runningId >= maxTmpCoordinates ? 0 : runningId;
		return getSource().getAsObject(
				Coordinates.plus(tmpCoordinates[runningId], coordinates, min));
	}

	public long[] getSize() {
		return size;
	}

	public void setObject(final Object value, final long... coordinates) {
		runningId = ++runningId >= maxTmpCoordinates ? 0 : runningId;
		getSource().setAsObject(value,
				Coordinates.plus(tmpCoordinates[runningId], coordinates, min));
	}
}

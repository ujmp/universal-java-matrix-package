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

package org.ujmp.core.calculation;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.CoordinateIterator;

public abstract class AbstractCalculation implements Calculation {

	private static final long serialVersionUID = -36063772015381070L;

	private final Matrix[] sources;

	private int dimension = NONE;

	private MapMatrix<Object, Object> annotation = null;

	public AbstractCalculation(Matrix... sources) {
		this.sources = sources;
		this.annotation = sources.length == 0 ? null : sources[0].getMetaData();
	}

	public AbstractCalculation(int dimension, Matrix... sources) {
		this.sources = sources;
		this.annotation = sources.length == 0 ? null : sources[0].getMetaData();
		this.dimension = dimension;
	}

	public void setMetaData(MapMatrix<Object, Object> annotation) {
		this.annotation = annotation;
	}

	public Iterable<long[]> availableCoordinates() {
		return new CoordinateIterator(getSize());
	}

	public boolean contains(long... coordinates) {
		return Coordinates.isSmallerThan(coordinates, getSize());
	}

	public MapMatrix<Object, Object> getMetaData() {
		return annotation;
	}

	public final Matrix getSource() {
		return sources[0];
	}

	public final Matrix[] getSources() {
		return sources;
	}

	public int getDimension() {
		return dimension;
	}

	public long[] getSize() {
		return getSource().getSize();
	}

	public long getRowCount() {
		return getSource().getRowCount();
	}

	public long getColumnCount() {
		return getSource().getColumnCount();
	}

	public final Matrix calc(Ret returnType) {
		switch (returnType) {
		case ORIG:
			return calcOrig();
		case LINK:
			return calcLink();
		default: // must be NEW
			return calcNew();
		}
	}

}

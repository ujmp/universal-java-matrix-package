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

package org.ujmp.core.calculation;

import org.ujmp.core.Matrix;
import org.ujmp.core.Matrix.StorageType;
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.coordinates.CoordinateIterator;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.exceptions.MatrixException;

public abstract class AbstractCalculation implements Calculation {

	private static final long serialVersionUID = -36063772015381070L;

	private Matrix[] sources = null;

	private int dimension = NONE;

	private Annotation annotation = null;

	public AbstractCalculation(Matrix... sources) {
		this.sources = sources;
		this.annotation = sources.length == 0 ? null : sources[0].getAnnotation();
	}

	public AbstractCalculation(int dimension, Matrix... sources) {
		this.sources = sources;
		this.annotation = sources.length == 0 ? null : sources[0].getAnnotation();
		this.dimension = dimension;
	}

	public boolean isSparse() {
		return false;
	}

	public void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
	}

	public long getValueCount() {
		return Coordinates.product(getSize());
	}

	public Iterable<long[]> availableCoordinates() {
		return allCoordinates();
	}

	public Iterable<long[]> allCoordinates() {
		return new CoordinateIterator(getSize());
	}

	public boolean contains(long... coordinates) {
		return Coordinates.isSmallerThan(coordinates, getSize());
	}

	public Annotation getAnnotation() {
		return annotation;
	}

	public final Matrix getSource() {
		return sources[0];
	}

	public final Matrix[] getSources() {
		return sources;
	}

	public void setSources(Matrix... sources) {
		this.sources = sources;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public long[] getSize() {
		return getSource().getSize();
	}

	public final Matrix calc(Ret returnType) throws MatrixException {
		switch (returnType) {
		case ORIG:
			return calcOrig();
		case LINK:
			return calcLink();
		default: // must be NEW
			return calcNew();
		}
	}

	public Matrix[] calcMulti() throws MatrixException {
		return new Matrix[] { calcNew() };
	}

	public final StorageType getStorageType() {
		return getSource().getStorageType();
	}

}

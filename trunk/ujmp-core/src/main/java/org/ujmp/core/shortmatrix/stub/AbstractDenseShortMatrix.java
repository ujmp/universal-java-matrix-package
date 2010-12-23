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

package org.ujmp.core.shortmatrix.stub;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.objectmatrix.DenseObjectMatrix2D;
import org.ujmp.core.objectmatrix.factory.DenseObjectMatrix2DFactory;
import org.ujmp.core.shortmatrix.DenseShortMatrix;

public abstract class AbstractDenseShortMatrix extends AbstractShortMatrix implements
		DenseShortMatrix {
	private static final long serialVersionUID = -2379265136500592409L;

	public AbstractDenseShortMatrix() {
		super();
	}

	public AbstractDenseShortMatrix(Matrix m) {
		super(m);
	}

	public AbstractDenseShortMatrix(long... size) {
		super(size);
	}

	public final boolean contains(long... coordinates) {
		return Coordinates.isSmallerThan(coordinates, getSize());
	}

	public final StorageType getStorageType() {
		return StorageType.DENSE;
	}

	public DenseObjectMatrix2DFactory getFactory() {
		return DenseObjectMatrix2D.factory;
	}

}

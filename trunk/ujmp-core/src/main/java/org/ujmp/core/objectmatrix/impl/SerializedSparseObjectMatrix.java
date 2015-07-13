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

package org.ujmp.core.objectmatrix.impl;

import java.io.IOException;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.collections.map.SerializedObjectMap;
import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.objectmatrix.stub.AbstractMapToSparseMatrixWrapper;

public class SerializedSparseObjectMatrix extends AbstractMapToSparseMatrixWrapper implements
		Erasable {
	private static final long serialVersionUID = 2239927901825378258L;

	public SerializedSparseObjectMatrix(long... size) throws IOException {
		super(new SerializedObjectMap<Coordinates, Object>(), size);
	}

	public SerializedSparseObjectMatrix(Matrix source) throws IOException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setAsObject(source.getAsObject(c), c);
		}
		MapMatrix<String, Object> a = source.getMetaData();
		if (a != null) {
			setMetaData(a.clone());
		}
	}

	public void erase() throws IOException {
		((Erasable) getMap()).erase();
	}

	public final void clear() {
		getMap().clear();
	}
}

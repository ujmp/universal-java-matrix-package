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

package org.ujmp.core.objectmatrix.calculation;

import java.util.Collection;

import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.exceptions.MatrixException;

public class Concatenation extends AbstractObjectCalculation {
	private static final long serialVersionUID = -2428322597419645314L;

	private long[] positions = null;

	private long[] size = Coordinates.ZERO2D;

	public Concatenation(int dimension, Matrix... matrices) {
		super(dimension, matrices);
		positions = new long[matrices.length];
		long pos = 0;
		for (int i = 0; i < matrices.length; i++) {
			Matrix m = matrices[i];
			positions[i] = pos;
			pos += m.getSize(dimension);
			size = Coordinates.max(size, m.getSize());
		}
		size[dimension] = pos; // set total size
	}

	public Concatenation(int dimension, Collection<Matrix> matrices) {
		this(dimension, matrices.toArray(new Matrix[matrices.size()]));
	}

	
	public Object getObject(long... coordinates) throws MatrixException {
		int i = 0;
		for (; i < positions.length; i++) {
			if (positions[i] > coordinates[getDimension()]) {
				break;
			}
		}
		i--;
		Matrix m = getSources()[i];
		long[] c = Coordinates.copyOf(coordinates);
		c[getDimension()] = c[getDimension()] - positions[i];
		return m.getAsObject(c);
	}

	
	public long[] getSize() {
		return size;
	}

	
	public void setObject(Object value, long... coordinates) throws MatrixException {
	}

}

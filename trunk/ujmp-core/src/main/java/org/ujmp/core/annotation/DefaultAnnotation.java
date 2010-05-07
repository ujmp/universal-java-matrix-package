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

package org.ujmp.core.annotation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;

public class DefaultAnnotation extends AbstractAnnotation {
	private static final long serialVersionUID = -7988756144808776868L;

	private Object matrixAnnotation = null;

	private Map<Integer, Matrix> dimensionMatrices = null;

	public DefaultAnnotation(long[] size) {
		super(size);
	}

	public Matrix getDimensionMatrix(int dimension) {
		if (dimensionMatrices == null) {
			dimensionMatrices = new HashMap<Integer, Matrix>(getDimensionCount());
		}
		Matrix m = dimensionMatrices.get(dimension);
		if (m == null) {
			long[] t = Coordinates.copyOf(getSize());
			t[dimension] = 1;
			m = MatrixFactory.sparse(ValueType.OBJECT, t);
			dimensionMatrices.put(dimension, m);
		}
		return m;
	}

	public Object getMatrixAnnotation() {
		return matrixAnnotation;
	}

	public void setMatrixAnnotation(Object matrixAnnotation) {
		this.matrixAnnotation = matrixAnnotation;
	}

	public Annotation clone() {
		Annotation a = new DefaultAnnotation(getSize());
		a.setMatrixAnnotation(getMatrixAnnotation());
		for (int i = 0; i < getDimensionCount(); i++) {
			a.setDimensionMatrix(i, getDimensionMatrix(i).clone());
		}
		return a;
	}

	public void clear() {
		matrixAnnotation = null;
		dimensionMatrices = null;
	}

	public Object getAxisAnnotation(int dimension, long... position) {
		Matrix m = getDimensionMatrix(dimension);
		long old = position[dimension];
		position[dimension] = 0;
		Object o = null;
		if (Coordinates.isSmallerThan(position, m.getSize())) {
			o = m.getAsObject(position);
		}
		position[dimension] = old;
		return o;
	}

	public long[] getPositionForLabel(int dimension, Object label) {
		if (label == null) {
			throw new MatrixException("label is null");
		}
		Matrix m = getDimensionMatrix(dimension);
		for (long[] c : m.availableCoordinates()) {
			Object o = m.getAsObject(c);
			if (label.equals(o)) {
				return c;
			}
		}
		long[] t = new long[getDimensionCount()];
		Arrays.fill(t, -1);
		return t;
	}

	public void setAxisAnnotation(int dimension, Object label, long... position) {
		Matrix m = getDimensionMatrix(dimension);
		long old = position[dimension];
		position[dimension] = 0;
		m.setAsObject(label, position);
		position[dimension] = old;
	}

	public void setDimensionMatrix(int dimension, Matrix matrix) {
		if (dimensionMatrices == null) {
			dimensionMatrices = new HashMap<Integer, Matrix>(getDimensionCount());
		}
		if (matrix == null) {
			dimensionMatrices.put(dimension, null);
		} else {
			dimensionMatrices.put(dimension, matrix);
		}
	}

}

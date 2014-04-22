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

package org.ujmp.core.annotation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.objectmatrix.impl.DefaultSparseObjectMatrix;

public class DefaultAnnotation extends AbstractAnnotation {
	private static final long serialVersionUID = -7988756144808776868L;

	// TODO: Annotation should extend MapMatrix<Object, Object>
	private final MapMatrix<Object, Object> metaData = new DefaultMapMatrix<Object, Object>();

	private Map<Integer, Matrix> dimensionMatrices = null;

	public DefaultAnnotation(int dimensionCount) {
		super(dimensionCount);
	}

	public Matrix getDimensionMatrix(int dimension) {
		Matrix m = getDimensionMatrices().get(dimension);
		if (m == null) {
			long[] t = new long[getDimensionCount()];
			Arrays.fill(t, 1);
			m = new DefaultSparseObjectMatrix(t);
			getDimensionMatrices().put(dimension, m);
		}
		return m;
	}

	public MapMatrix<Object, Object> getMetaData() {
		return metaData;
	}

	public Map<Integer, Matrix> getDimensionMatrices() {
		if (dimensionMatrices == null) {
			dimensionMatrices = new HashMap<Integer, Matrix>(2);
		}
		return dimensionMatrices;
	}

	public Object getLabelObject() {
		return metaData.get(LABEL);
	}

	public void setLabelObject(Object label) {
		metaData.put(LABEL, label);
	}

	public Object getObject(Object key) {
		return metaData.get(key);
	}

	public void setObject(Object key, Object label) {
		metaData.put(key, label);
	}

	public Annotation clone() {
		Annotation a = new DefaultAnnotation(getDimensionCount());
		a.setLabelObject(getLabelObject());
		for (int i = 0; i < getDimensionCount(); i++) {
			a.setDimensionMatrix(i, getDimensionMatrix(i).clone());
		}
		return a;
	}

	public void clear() {
		metaData.clear();
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
			throw new RuntimeException("label is null");
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
		if (!Coordinates.isSmallerThan(position, m.getSize())) {
			long[] newSize = Coordinates.max(m.getSize(), Coordinates.plus(position, 1));
			m.setSize(newSize);
		}
		m.setAsObject(label, position);
		position[dimension] = old;
	}

	public void setDimensionMatrix(int dimension, Matrix matrix) {
		getDimensionMatrices().put(dimension, matrix);
	}

}

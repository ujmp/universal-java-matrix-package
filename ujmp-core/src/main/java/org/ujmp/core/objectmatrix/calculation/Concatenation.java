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

import java.util.Collection;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.annotation.DefaultAnnotation;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.objectmatrix.impl.DefaultSparseObjectMatrix;
import org.ujmp.core.util.VerifyUtil;

// TODO: should work in more than 2 dimensions
public class Concatenation extends AbstractObjectCalculation {
	private static final long serialVersionUID = -2428322597419645314L;

	private long[] positions = null;

	private long[] size = Coordinates.ZERO2D;

	private ValueType valueType = null;

	public Concatenation(int dimension, Matrix... matrices) {
		super(dimension, matrices);

		for (int i = 0; i < matrices.length - 1; i++) {
			Matrix m0 = matrices[i];
			Matrix m1 = matrices[i + 1];
			VerifyUtil.assertNotNull(m0, "matrix is null");
			VerifyUtil.assertNotNull(m1, "matrix is null");
			VerifyUtil.assertEquals(m0.getDimensionCount(), m1.getDimensionCount(),
					"matrices have different dimensionality");
		}

		if (dimension > matrices[0].getDimensionCount() + 1) {
			throw new IllegalArgumentException("too many dimensions");
		}

		// extends dimensionality if necessary
		if (dimension > matrices[0].getDimensionCount()) {
			long[] oldSize = matrices[0].getSize();
			long[] newSize = new long[oldSize.length + 1];
			System.arraycopy(oldSize, 0, newSize, 0, oldSize.length);
			newSize[newSize.length - 1] = 1;
			for (int i = 0; i < matrices.length; i++) {
				matrices[i] = matrices[i].reshape(Ret.LINK, newSize);
			}
		}

		for (int i = 0; i < matrices.length - 1; i++) {
			Matrix m0 = matrices[i];
			Matrix m1 = matrices[i + 1];
			long[] s0 = m0.getSize();
			long[] s1 = Coordinates.copyOf(m1.getSize());
			if (!Coordinates.allEquals(s0, 0) && !Coordinates.allEquals(s1, 0)) {
				s1[dimension] = s0[dimension];
				VerifyUtil.assertEquals(s0, s1, "matrices have different sizes");
			}
		}

		valueType = matrices[0].getValueType();
		positions = new long[matrices.length];
		long pos = 0;
		for (int i = 0; i < matrices.length; i++) {
			Matrix m = matrices[i];
			if (!valueType.equals(m.getValueType())) {
				valueType = ValueType.OBJECT;
			}
			positions[i] = pos;
			pos += m.getSize(dimension);
			size = Coordinates.max(size, m.getSize());
		}
		size[dimension] = pos; // set total size

		// Annotation
		if (matrices[0].getLabelObject() != null) {
			Annotation annotation = new DefaultAnnotation(size.length);
			setAnnotation(annotation);
			annotation.setLabelObject(matrices[0].getLabelObject());
			for (int d = 0; d < matrices[0].getDimensionCount(); d++) {
				if (d == dimension) {
					annotation.setDimensionMatrix(d, matrices[0].getAnnotation()
							.getDimensionMatrix(d));
				} else {
					Matrix[] annotationMatrices = new Matrix[matrices.length];
					for (int i = 0; i < annotationMatrices.length; i++) {
						Annotation a = matrices[i].getAnnotation();
						Matrix am = null;
						if (a == null) {
							long[] size = Coordinates.copyOf(matrices[i].getSize());
							size[d] = 1;
							am = new DefaultSparseObjectMatrix(size);
						} else {
							am = a.getDimensionMatrix(d);
						}
						annotationMatrices[i] = am;
					}
					Matrix m = new Concatenation(dimension, annotationMatrices).calc(Ret.NEW);
					annotation.setDimensionMatrix(d, m);
				}
			}
		}
	}

	public Concatenation(int dimension, Collection<Matrix> matrices) {
		this(dimension, matrices.toArray(new Matrix[matrices.size()]));
	}

	public Object getObject(long... coordinates)  {
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

	public void setObject(Object value, long... coordinates)  {
	}

	public final ValueType getValueType() {
		return valueType;
	}

}

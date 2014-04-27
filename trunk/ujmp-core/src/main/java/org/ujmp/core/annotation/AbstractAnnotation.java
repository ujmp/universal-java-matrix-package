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

import org.ujmp.core.Matrix;

public abstract class AbstractAnnotation implements Annotation {
	private static final long serialVersionUID = 2939231340832922069L;

	protected final int dimensionCount;

	public AbstractAnnotation(int dimensionCount) {
		this.dimensionCount = dimensionCount;
	}

	public final Object getAxisLabelObject(int axis) {
		Matrix m = getDimensionMatrix(axis);
		return m.getLabelObject();
	}

	public final void setAxisLabelObject(int axis, Object value) {
		Matrix m = getDimensionMatrix(axis);
		m.setLabel(value);
	}

	public void setAxisLabel(int dimension, String label) {
		setAxisLabelObject(dimension, label);
	}

	public abstract Annotation clone();

}

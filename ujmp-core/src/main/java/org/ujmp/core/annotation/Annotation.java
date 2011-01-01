/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

import java.io.Serializable;
import java.util.Map;

import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.HasLabel;

public interface Annotation extends Serializable, Cloneable, HasLabel {

	public Matrix getDimensionMatrix(int dimension);

	public Map<Integer, Matrix> getDimensionMatrices();

	public void setDimensionMatrix(int dimension, Matrix matrix);

	public void setAxisAnnotation(int dimension, Object label, long... position);

	public Object getAxisAnnotation(int dimension, long... position);

	public String getAxisLabel(int dimension);

	public Object getAxisLabelObject(int dimension);

	public void setAxisLabelObject(int dimension, Object label);

	public void setAxisLabel(int dimension, String label);

	public Annotation clone();

	public boolean equals(Object annotation);

	public void clear();

	public long[] getPositionForLabel(int dimension, Object label);

}

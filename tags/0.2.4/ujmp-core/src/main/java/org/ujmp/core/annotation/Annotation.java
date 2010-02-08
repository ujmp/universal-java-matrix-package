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

import java.io.Serializable;

import org.ujmp.core.Matrix;

public interface Annotation extends Serializable {

	public long[] getSize();

	public Matrix getDimensionMatrix(int dimension);

	public void setDimensionMatrix(int dimension, Matrix matrix);

	public void setAxisAnnotation(int dimension, Object label, long... position);

	public Object getAxisAnnotation(int dimension, long... position);

	public Object getAxisAnnotation(int dimension);

	public void setAxisAnnotation(int dimension, Object label);

	public Object getMatrixAnnotation();

	public void setMatrixAnnotation(Object label);

	public Annotation clone();

	public boolean equals(Object annotation);

	public void clear();

	public long[] getPositionForLabel(int dimension, Object label);

}

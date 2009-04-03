/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

public interface Annotation extends Serializable {

	public Map<Integer, Object> getAxisLabelAnnotation();

	public Map<Integer, Map<Long, Object>> getAxisAnnotation();

	public Map<Integer, Map<Object, Long>> getAxisPositions();

	public void setAxisAnnotation(int axis, long positionOnAxis, Object value);

	public Object getAxisAnnotation(int axis, long positionOnAxis);

	public Object getAxisAnnotation(int axis);

	public void setAxisAnnotation(int axis, Object value);

	public Object getMatrixAnnotation();

	public void setMatrixAnnotation(Object matrixAnnotation);

	public Annotation clone();

	public boolean equals(Annotation a);

	public long getPositionForLabel(int dimension, Object object);

}

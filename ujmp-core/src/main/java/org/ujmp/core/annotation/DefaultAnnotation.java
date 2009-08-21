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

import java.util.HashMap;
import java.util.Map;

public class DefaultAnnotation implements Annotation {
	private static final long serialVersionUID = 4497806180298615612L;

	protected Object matrixAnnotation = null;

	protected Map<Integer, Map<Long, Object>> axisAnnotation = null;

	protected Map<Integer, Map<Object, Long>> axisPositions = null;

	protected Map<Integer, Object> axisLabelAnnotation = null;

	public DefaultAnnotation() {
		axisAnnotation = new HashMap<Integer, Map<Long, Object>>();
		axisPositions = new HashMap<Integer, Map<Object, Long>>();
		axisLabelAnnotation = new HashMap<Integer, Object>(2);
	}

	public DefaultAnnotation(Annotation annotation) {
		this.matrixAnnotation = annotation.getMatrixAnnotation();
		axisAnnotation = new HashMap<Integer, Map<Long, Object>>();
		axisPositions = new HashMap<Integer, Map<Object, Long>>();
		axisLabelAnnotation = new HashMap<Integer, Object>(2);
		axisLabelAnnotation.putAll(annotation.getAxisLabelAnnotation());
		for (int i : annotation.getAxisAnnotation().keySet()) {
			Map<Long, Object> m = annotation.getAxisAnnotation().get(i);
			Map<Long, Object> mnew = new HashMap<Long, Object>();
			mnew.putAll(m);
			axisAnnotation.put(i, mnew);
		}
		for (int i : annotation.getAxisPositions().keySet()) {
			Map<Object, Long> m = annotation.getAxisPositions().get(i);
			Map<Object, Long> mnew = new HashMap<Object, Long>();
			mnew.putAll(m);
			axisPositions.put(i, mnew);
		}
	}

	public void setAxisAnnotation(int axis, long positionOnAxis, Object value) {
		Map<Long, Object> axisMap = axisAnnotation.get(axis);
		Map<Object, Long> positionMap = axisPositions.get(axis);
		if (axisMap == null) {
			axisMap = new HashMap<Long, Object>(2);
			axisAnnotation.put(axis, axisMap);
		}
		if (positionMap == null) {
			positionMap = new HashMap<Object, Long>(2);
			axisPositions.put(axis, positionMap);
		}
		axisMap.put(positionOnAxis, value);
		positionMap.put(value, positionOnAxis);
	}

	public Object getAxisAnnotation(int axis, long positionOnAxis) {
		Map<Long, Object> axisMap = axisAnnotation.get(axis);
		if (axisMap != null) {
			return axisMap.get(positionOnAxis);
		}
		return null;
	}

	public final long getPositionForLabel(int dimension, Object object) {
		Map<Object, Long> axisMap = axisPositions.get(dimension);
		if (axisMap != null) {
			Long pos = axisMap.get(object);
			return pos == null ? -1 : pos;
		}
		return -1;
	}

	public Object getAxisAnnotation(int axis) {
		return axisLabelAnnotation.get(axis);
	}

	public void setAxisAnnotation(int axis, Object value) {
		axisLabelAnnotation.put(axis, value);
	}

	public Object getMatrixAnnotation() {
		return matrixAnnotation;
	}

	public void setMatrixAnnotation(Object matrixAnnotation) {
		this.matrixAnnotation = matrixAnnotation;
	}

	
	public Annotation clone() {
		DefaultAnnotation a = new DefaultAnnotation(this);
		return a;
	}

	public Map<Integer, Object> getAxisLabelAnnotation() {
		return axisLabelAnnotation;
	}

	public Map<Integer, Map<Long, Object>> getAxisAnnotation() {
		return axisAnnotation;
	}

	public Map<Integer, Map<Object, Long>> getAxisPositions() {
		return axisPositions;
	}

	public boolean equals(Annotation a) {
		if (a == null) {
			return false;
		}
		if (matrixAnnotation != null && !matrixAnnotation.equals(a.getMatrixAnnotation())) {
			return false;
		} else if (a.getMatrixAnnotation() != null
				&& !a.getMatrixAnnotation().equals(matrixAnnotation)) {
			return false;
		}
		if (axisLabelAnnotation != null && !axisLabelAnnotation.equals(a.getAxisLabelAnnotation())) {
			return false;
		} else {
			if (a.getAxisLabelAnnotation() != null
					&& !a.getAxisLabelAnnotation().equals(axisLabelAnnotation)) {
				return false;
			}
		}
		if (axisAnnotation != null && !axisAnnotation.equals(a.getAxisAnnotation())) {
			return false;
		} else {
			if (a.getAxisAnnotation() != null && !a.getAxisAnnotation().equals(axisAnnotation)) {
				return false;
			}
		}
		return true;
	}

	public void clear() {
		axisAnnotation.clear();
		axisLabelAnnotation.clear();
		axisPositions.clear();
	}
}

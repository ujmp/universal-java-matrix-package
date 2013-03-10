/*
 * Copyright (C) 2008-2013 by Holger Arndt
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
import org.ujmp.core.util.StringUtil;

public abstract class AbstractAnnotation implements Annotation {
	private static final long serialVersionUID = 2939231340832922069L;

	private final int dimensionCount;

	public AbstractAnnotation(int dimensionCount) {
		this.dimensionCount = dimensionCount;
	}

	public final int getDimensionCount() {
		return dimensionCount;
	}

	public final Object getAxisLabelObject(int axis) {
		Matrix m = getDimensionMatrix(axis);
		return m.getLabelObject();
	}

	public final String getAxisLabel(int axis) {
		Matrix m = getDimensionMatrix(axis);
		return m.getLabel();
	}

	public final void setAxisLabelObject(int axis, Object value) {
		Matrix m = getDimensionMatrix(axis);
		m.setLabelObject(value);
	}

	public final String toString() {
		StringBuilder s = new StringBuilder();
		String EOL = System.getProperty("line.separator");
		s.append("Label: " + getLabelObject() + EOL);
		for (int i = 0; i < getDimensionCount(); i++) {
			s.append("Dimension " + i + ":" + EOL);
			s.append(getDimensionMatrix(i));
			s.append(EOL);
		}
		return s.toString();
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getLabelObject() == null) ? 0 : getLabelObject().hashCode());
		for (int i = 0; i < getDimensionCount(); i++) {
			result = prime * result
					+ ((getDimensionMatrix(i) == null) ? 0 : getDimensionMatrix(i).hashCode());
		}
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DefaultAnnotation other = (DefaultAnnotation) obj;
		if (getDimensionCount() != other.getDimensionCount()) {
			return false;
		}
		if (getLabelObject() == null) {
			if (other.getLabelObject() != null) {
				return false;
			}
		} else if (!getLabelObject().equals(other.getLabelObject())) {
			return false;
		}
		for (int i = 0; i < getDimensionCount(); i++) {
			if (getDimensionMatrix(i) == null) {
				if (other.getDimensionMatrix(i) != null) {
					return false;
				}
			} else if (!getDimensionMatrix(i).equals(other.getDimensionMatrix(i))) {
				return false;
			}
		}
		return true;
	}

	public void setAxisLabel(int dimension, String label) {
		setAxisLabelObject(dimension, label);
	}

	public String getLabel() {
		return StringUtil.getString(getLabelObject());
	}

	public void setLabel(String label) {
		setLabelObject(label);
	}

	public abstract Annotation clone();

}

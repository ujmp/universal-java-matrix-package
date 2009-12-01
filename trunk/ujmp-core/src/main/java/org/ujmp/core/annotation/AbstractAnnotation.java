/*
 * Copyright (C) 2008-2009 by Holger Arndt
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
import org.ujmp.core.coordinates.Coordinates;

public abstract class AbstractAnnotation implements Annotation {
	private static final long serialVersionUID = 2939231340832922069L;

	private long[] size = null;

	public AbstractAnnotation(long... size) {
		this.size = Coordinates.copyOf(size);
	}

	public final long[] getSize() {
		return size;
	}

	public final int getDimensionCount() {
		return getSize().length;
	}

	public final Object getAxisAnnotation(int axis) {
		Matrix m = getDimensionMatrix(axis);
		return m.getMatrixAnnotation();
	}

	public final void setAxisAnnotation(int axis, Object value) {
		Matrix m = getDimensionMatrix(axis);
		m.setMatrixAnnotation(value);
	}

	public final String toString() {
		StringBuilder s = new StringBuilder();
		String EOL = System.getProperty("line.separator");
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
		result = prime * result
				+ ((getMatrixAnnotation() == null) ? 0 : getMatrixAnnotation().hashCode());
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
		if (getMatrixAnnotation() == null) {
			if (other.getMatrixAnnotation() != null) {
				return false;
			}
		} else if (!getMatrixAnnotation().equals(other.getMatrixAnnotation())) {
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

	public abstract Annotation clone();

}

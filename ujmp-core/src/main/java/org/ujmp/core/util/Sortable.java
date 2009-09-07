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

package org.ujmp.core.util;

public class Sortable<C extends Comparable<C>, O> implements Comparable<Sortable<C, O>> {

	private C comparable = null;

	private O object = null;

	private boolean compareObject = false;

	public Sortable(C comparable, O object) {
		this.comparable = comparable;
		this.object = object;
	}

	public Sortable(C comparable, O object, boolean compareObject) {
		this.comparable = comparable;
		this.object = object;
		this.compareObject = compareObject;
	}

	public C getComparable() {
		return comparable;
	}

	public void setComparable(C comparable) {
		this.comparable = comparable;
	}

	public O getObject() {
		return object;
	}

	public void setObject(O object) {
		this.object = object;
	}

	public String toString() {
		return "" + comparable + ": " + object;
	}

	@SuppressWarnings("unchecked")
	public int compareTo(Sortable<C, O> s) {
		if (comparable == null) {
			return Integer.MIN_VALUE;
		}
		int compObjectEqual = comparable.compareTo(s.comparable);
		if (!compareObject || !(compObjectEqual == 0)) {
			return compObjectEqual;
		}
		if (object == null) {
			return Integer.MIN_VALUE;
		}
		return ((Comparable<O>) object).compareTo(s.object);
	}

	public boolean equals(Object obj) {
		if (obj instanceof Sortable) {
			Sortable<?, ?> s = (Sortable<?, ?>) obj;
			Comparable<?> c = s.getComparable();
			Object o = s.getObject();
			if (comparable == null && c != null) {
				return false;
			}
			if (comparable.equals(c)) {
				return false;
			}
			if (object == null && o != null) {
				return false;
			}
			if (object.equals(o)) {
				return false;
			}
			return true;
		}
		return false;
	}

	public int hashCode() {
		int hash = 0;
		if (object != null) {
			hash += object.hashCode();
		}
		return hash;
	}

}

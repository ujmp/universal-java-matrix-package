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

package org.ujmp.core.collections;

import java.io.Serializable;
import java.util.Collection;

public abstract class AbstractCollection<E> implements Serializable, Collection<E> {
	private static final long serialVersionUID = -2641805901870689243L;

	public boolean addAll(Collection<? extends E> c) {
		boolean somethingAdded = false;
		for (E o : c) {
			if (add(o)) {
				somethingAdded = true;
			}
		}
		return somethingAdded;
	}

	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!contains(o)) {
				return false;
			}
		}
		return true;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public Object[] toArray() {
		throw new UnsupportedOperationException("not allowed");
	}

	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException("not allowed");
	}

	public boolean removeAll(Collection<?> c) {
		boolean somethingRemoved = false;
		for (Object o : c) {
			if (remove(o)) {
				somethingRemoved = true;
			}
		}
		return somethingRemoved;
	}
}

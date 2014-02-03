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

package org.ujmp.core.collections.set;

import java.io.Serializable;
import java.util.Collection;

public abstract class AbstractSet<E> extends java.util.AbstractSet<E> implements Serializable {
	private static final long serialVersionUID = -5947421458148665094L;

	public abstract boolean add(E value);

	public abstract boolean remove(Object o);

	public abstract void clear();

	public abstract boolean contains(Object o);

	public boolean addAll(Collection<? extends E> c) {
		boolean modified = false;
		for (E e : c) {
			if (add(e)) {
				modified = true;
			}
		}
		return modified;
	}

	public boolean removeAll(Collection<?> c) {
		boolean modified = false;
		for (Object e : c) {
			if (remove(e)) {
				modified = true;
			}
		}
		return modified;
	}

}

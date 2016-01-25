/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

package org.ujmp.core.setmatrix;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

import org.ujmp.core.genericmatrix.stub.AbstractDenseGenericMatrix2D;

public abstract class AbstractSetMatrix<E> extends AbstractDenseGenericMatrix2D<E> implements
		SetMatrix<E> {
	private static final long serialVersionUID = -3152489258987719660L;

	public AbstractSetMatrix() {
		super(0, 1);
	}

	public synchronized final long[] getSize() {
		size[ROW] = size();
		return size;
	}

	public synchronized final E getObject(long row, long column) {
		return getObject((int) row, (int) column);
	}

	public synchronized final void setObject(Object value, long row, long column) {
		setObject(value, (int) row, (int) column);
	}

	public final boolean add(E e) {
		boolean ret = addToSet(e);
		fireValueChanged();
		return ret;
	}

	public final boolean addAll(Collection<? extends E> c) {
		boolean ret = false;
		for (E value : c) {
			ret = ret | addToSet(value);
		}
		if (ret) {
			fireValueChanged();
		}
		return ret;
	}

	public final boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!contains(o)) {
				return false;
			}
		}
		return true;
	}

	public final boolean isEmpty() {
		return size() == 0;
	}

	public boolean remove(Object o) {
		boolean ret = removeFromSet(o);
		if (ret) {
			fireValueChanged();
		}
		return ret;
	}

	public boolean removeAll(Collection<?> c) {
		boolean ret = false;
		for (Object o : c) {
			ret = ret | removeFromSet(o);
		}
		if (ret) {
			fireValueChanged();
		}
		return ret;
	}

	public final boolean retainAll(Collection<?> c) {
		Objects.requireNonNull(c);
		boolean ret = false;
		Iterator<E> it = iterator();
		while (it.hasNext()) {
			if (!c.contains(it.next())) {
				it.remove();
				ret = true;
			}
		}
		if (ret) {
			fireValueChanged();
		}
		return ret;
	}

	public final E getObject(int row, int column) {
		Iterator<E> it = iterator();
		for (int i = 0; i < row && it.hasNext(); i++) {
			it.next();
		}
		if (it.hasNext()) {
			return it.next();
		} else {
			return null;
		}
	}

	public final Object[] toArray() {
		final Object[] objects = new Object[size()];
		final Iterator<E> it = iterator();
		int i = 0;
		while (it.hasNext()) {
			objects[i++] = it.next();
		}
		return objects;
	}

	@SuppressWarnings("unchecked")
	public final <T> T[] toArray(T[] a) {
		final T[] objects = (T[]) Array.newInstance(a.getClass(), size());
		final Iterator<E> it = iterator();
		int i = 0;
		while (it.hasNext()) {
			objects[i++] = (T) it.next();
		}
		return objects;
	}

	public final void clear() {
		clearSet();
		fireValueChanged();
	}

	protected abstract void clearSet();

	protected abstract boolean removeFromSet(Object value);

	protected abstract boolean addToSet(E value);

}

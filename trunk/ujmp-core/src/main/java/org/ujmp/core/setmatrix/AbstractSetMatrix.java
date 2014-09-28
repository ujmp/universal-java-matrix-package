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

package org.ujmp.core.setmatrix;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.ujmp.core.enums.ValueType;
import org.ujmp.core.genericmatrix.stub.AbstractDenseGenericMatrix2D;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractSetMatrix<E> extends AbstractDenseGenericMatrix2D<E> implements
		SetMatrix<E> {
	private static final long serialVersionUID = -3152489258987719660L;

	public AbstractSetMatrix() {
		super(0, 1);
	}

	public abstract Set<E> getSet();

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

	public boolean add(E e) {
		boolean ret = getSet().add(e);
		fireValueChanged();
		return ret;
	}

	public boolean addAll(Collection<? extends E> c) {
		boolean ret = getSet().addAll(c);
		fireValueChanged();
		return ret;
	}

	public boolean contains(Object o) {
		return getSet().contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return getSet().containsAll(c);
	}

	public boolean isEmpty() {
		return getSet().isEmpty();
	}

	public Iterator<E> iterator() {
		return getSet().iterator();
	}

	public boolean remove(Object o) {
		boolean ret = getSet().remove(o);
		fireValueChanged();
		return ret;
	}

	public boolean removeAll(Collection<?> c) {
		boolean ret = getSet().removeAll(c);
		fireValueChanged();
		return ret;
	}

	public boolean retainAll(Collection<?> c) {
		boolean ret = getSet().retainAll(c);
		fireValueChanged();
		return ret;
	}

	public int size() {
		return getSet().size();
	}

	public E getObject(int row, int column) {
		Iterator<E> it = getSet().iterator();
		for (int i = 0; i < row && it.hasNext(); i++) {
			it.next();
		}
		if (it.hasNext()) {
			return it.next();
		} else {
			return null;
		}
	}

	public Object[] toArray() {
		return getSet().toArray();
	}

	public <T> T[] toArray(T[] a) {
		return getSet().toArray(a);
	}

	public double getAsDouble(long... coordinates) {
		return MathUtil.getDouble(getObject(coordinates));
	}

	public void setAsDouble(double value, long... coordinates) {
		setAsObject(value, coordinates);
	}

	public ValueType getValueType() {
		return ValueType.OBJECT;
	}

	public final void clear() {
		getSet().clear();
	}

}

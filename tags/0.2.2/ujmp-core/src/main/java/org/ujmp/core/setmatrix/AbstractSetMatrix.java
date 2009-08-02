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

package org.ujmp.core.setmatrix;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.genericmatrix.AbstractDenseGenericMatrix2D;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractSetMatrix<A> extends AbstractDenseGenericMatrix2D<A> implements
		SetMatrix<A> {
	private static final long serialVersionUID = -3152489258987719660L;

	public abstract Set<A> getSet();

	public final long[] getSize() {
		return new long[] { size(), 1 };
	}

	public boolean add(A e) {
		boolean ret = getSet().add(e);
		notifyGUIObject();
		return ret;
	}

	public boolean addAll(Collection<? extends A> c) {
		boolean ret = getSet().addAll(c);
		notifyGUIObject();
		return ret;
	}

	public boolean contains(Object o) {
		return getSet().contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return getSet().containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return getSet().isEmpty();
	}

	public Iterator<A> iterator() {
		return getSet().iterator();
	}

	public boolean remove(Object o) {
		boolean ret = getSet().remove(o);
		notifyGUIObject();
		return ret;
	}

	public boolean removeAll(Collection<?> c) {
		boolean ret = getSet().removeAll(c);
		notifyGUIObject();
		return ret;
	}

	public boolean retainAll(Collection<?> c) {
		boolean ret = getSet().retainAll(c);
		notifyGUIObject();
		return ret;
	}

	public int size() {
		return getSet().size();
	}

	public A getObject(long row, long column) {
		return getObject((int) row, (int) column);
	}

	public A getObject(int row, int column) {
		Iterator<A> it = getSet().iterator();
		for (int i = 0; i < row && it.hasNext(); i++) {
			it.next();
		}
		if (it.hasNext()) {
			return it.next();
		} else {
			return null;
		}
	}

	public void setObject(Object value, long row, long column) {
		throw new MatrixException("modifications are only allowed over Set<?> interface");
	}

	public void setObject(Object value, int row, int column) {
		throw new MatrixException("modifications are only allowed over Set<?> interface");
	}

	public Object[] toArray() {
		return getSet().toArray();
	}

	public <T> T[] toArray(T[] a) {
		return getSet().toArray(a);
	}

	@Override
	public double getAsDouble(long... coordinates) throws MatrixException {
		return MathUtil.getDouble(getObject(coordinates));
	}

	@Override
	public void setAsDouble(double value, long... coordinates) throws MatrixException {
		setObject(value, coordinates);
	}

	@Override
	public ValueType getValueType() {
		return ValueType.OBJECT;
	}

}

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

package org.ujmp.core.listmatrix;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.genericmatrix.AbstractDenseGenericMatrix2D;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractListMatrix<A> extends AbstractDenseGenericMatrix2D<A> implements
		ListMatrix<A> {

	private static final long serialVersionUID = -6776628601679785451L;

	public abstract List<A> getList();

	public final long[] getSize() {
		return new long[] { size(), 1 };
	}

	public boolean add(A e) {
		boolean ret = getList().add(e);
		notifyGUIObject();
		return ret;
	}

	public void add(int index, A element) {
		getList().add(index, element);
		notifyGUIObject();
	}

	public boolean addAll(Collection<? extends A> c) {
		boolean ret = getList().addAll(c);
		notifyGUIObject();
		return ret;
	}

	public boolean addAll(int index, Collection<? extends A> c) {
		boolean ret = getList().addAll(index, c);
		notifyGUIObject();
		return ret;
	}

	public boolean contains(Object o) {
		return getList().contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return getList().containsAll(c);
	}

	public A get(int index) {
		return getList().get(index);
	}

	public int indexOf(Object o) {
		return getList().indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return getList().isEmpty();
	}

	public Iterator<A> iterator() {
		return getList().iterator();
	}

	public int lastIndexOf(Object o) {
		return getList().lastIndexOf(o);
	}

	public ListIterator<A> listIterator() {
		return getList().listIterator();
	}

	public ListIterator<A> listIterator(int index) {
		return getList().listIterator();
	}

	public boolean remove(Object o) {
		boolean ret = getList().remove(o);
		notifyGUIObject();
		return ret;
	}

	public A remove(int index) {
		A a = getList().remove(index);
		notifyGUIObject();
		return a;
	}

	public boolean removeAll(Collection<?> c) {
		boolean ret = getList().removeAll(c);
		notifyGUIObject();
		return ret;
	}

	public boolean retainAll(Collection<?> c) {
		boolean ret = getList().retainAll(c);
		notifyGUIObject();
		return ret;
	}

	public A set(int index, A element) {
		A a = getList().set(index, element);
		notifyGUIObject();
		return a;
	}

	public int size() {
		return getList().size();
	}

	public List<A> subList(int fromIndex, int toIndex) {
		return getList().subList(fromIndex, toIndex);
	}

	public A getObject(long row, long column) {
		A a = getList().get((int) row);
		return a;
	}

	public A getObject(int row, int column) {
		A a = getList().get(row);
		return a;
	}

	@SuppressWarnings("unchecked")
	public void setObject(Object value, long row, long column) {
		getList().set((int) row, (A) value);
		notifyGUIObject();
	}

	@SuppressWarnings("unchecked")
	public void setObject(Object value, int row, int column) {
		getList().set(row, (A) value);
		notifyGUIObject();
	}

	public Object[] toArray() {
		return getList().toArray();
	}

	public <T> T[] toArray(T[] a) {
		return getList().toArray(a);
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
		return ValueType.GENERIC;
	}

}

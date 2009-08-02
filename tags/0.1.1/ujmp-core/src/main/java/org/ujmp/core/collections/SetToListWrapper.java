/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

public class SetToListWrapper<A> implements Wrapper<Set<A>>, List<A> {

	private Set<A> set = null;

	public SetToListWrapper(Set<A> set) {
		this.set = set;
	}

	@Override
	public Set<A> getWrappedObject() {
		return set;
	}

	@Override
	public void setWrappedObject(Set<A> object) {
		this.set = object;
	}

	@Override
	public boolean add(A e) {
		return set.add(e);
	}

	@Override
	public void add(int index, A element) {
		throw new MatrixException("not implemented");
	}

	@Override
	public boolean addAll(Collection<? extends A> c) {
		return set.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends A> c) {
		throw new MatrixException("not implemented");
	}

	@Override
	public void clear() {
		set.clear();
	}

	@Override
	public boolean contains(Object o) {
		return set.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	@Override
	public A get(int index) {
		Iterator<A> it = set.iterator();
		for (int i = 0; i < index && it.hasNext(); i++) {
			it.next();
		}
		if (it.hasNext()) {
			return it.next();
		} else {
			return null;
		}
	}

	@Override
	public int indexOf(Object o) {
		Iterator<A> it = set.iterator();
		for (int i = 0; it.hasNext(); i++) {
			A a = it.next();
			if (o.equals(a)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public Iterator<A> iterator() {
		return set.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		return indexOf(o);
	}

	@Override
	public ListIterator<A> listIterator() {
		throw new MatrixException("not implemented");
	}

	@Override
	public ListIterator<A> listIterator(int index) {
		throw new MatrixException("not implemented");
	}

	@Override
	public boolean remove(Object o) {
		return set.remove(o);
	}

	@Override
	public A remove(int index) {
		throw new MatrixException("not implemented");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return set.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return set.retainAll(c);
	}

	@Override
	public A set(int index, A element) {
		throw new MatrixException("not implemented");
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public List<A> subList(int fromIndex, int toIndex) {
		throw new MatrixException("not implemented");
	}

	@Override
	public Object[] toArray() {
		return set.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

	public String toString() {
		return set.toString();
	}

}

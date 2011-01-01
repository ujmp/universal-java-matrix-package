/*
 * Copyright (C) 2008-2011 by Holger Arndt
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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

public class SetToListWrapper<A> implements Wrapper<Set<A>>, List<A>, Serializable {
	private static final long serialVersionUID = 4170885424845593802L;

	private Set<A> set = null;

	public SetToListWrapper(Set<A> set) {
		this.set = set;
	}

	
	public Set<A> getWrappedObject() {
		return set;
	}

	
	public void setWrappedObject(Set<A> object) {
		this.set = object;
	}

	
	public boolean add(A e) {
		return set.add(e);
	}

	
	public void add(int index, A element) {
		throw new MatrixException("not implemented");
	}

	
	public boolean addAll(Collection<? extends A> c) {
		return set.addAll(c);
	}

	
	public boolean addAll(int index, Collection<? extends A> c) {
		throw new MatrixException("not implemented");
	}

	
	public void clear() {
		set.clear();
	}

	
	public boolean contains(Object o) {
		return set.contains(o);
	}

	
	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	
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

	
	public boolean isEmpty() {
		return set.isEmpty();
	}

	
	public Iterator<A> iterator() {
		return set.iterator();
	}

	
	public int lastIndexOf(Object o) {
		return indexOf(o);
	}

	
	public ListIterator<A> listIterator() {
		throw new MatrixException("not implemented");
	}

	
	public ListIterator<A> listIterator(int index) {
		throw new MatrixException("not implemented");
	}

	
	public boolean remove(Object o) {
		return set.remove(o);
	}

	
	public A remove(int index) {
		throw new MatrixException("not implemented");
	}

	
	public boolean removeAll(Collection<?> c) {
		return set.removeAll(c);
	}

	
	public boolean retainAll(Collection<?> c) {
		return set.retainAll(c);
	}

	
	public A set(int index, A element) {
		throw new MatrixException("not implemented");
	}

	
	public int size() {
		return set.size();
	}

	
	public List<A> subList(int fromIndex, int toIndex) {
		throw new MatrixException("not implemented");
	}

	
	public Object[] toArray() {
		return set.toArray();
	}

	
	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

	public String toString() {
		return set.toString();
	}

}

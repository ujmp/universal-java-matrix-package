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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.ujmp.core.exceptions.MatrixException;

public class SortedListSet<A> implements SortedSet<A>, List<A>, Serializable {
	private static final long serialVersionUID = -9069699328418816771L;

	private SortedSet<A> set = new TreeSet<A>();

	private List<A> list = new ArrayList<A>();

	public SortedListSet() {
	}

	@Override
	public Comparator<? super A> comparator() {
		return set.comparator();
	}

	@Override
	public A first() {
		return set.first();
	}

	@Override
	public SortedSet<A> headSet(A toElement) {
		return set.headSet(toElement);
	}

	@Override
	public A last() {
		return set.last();
	}

	@Override
	public SortedSet<A> subSet(A fromElement, A toElement) {
		return set.subSet(fromElement, toElement);
	}

	@Override
	public SortedSet<A> tailSet(A fromElement) {
		return set.tailSet(fromElement);
	}

	@Override
	public boolean add(A e) {
		list.clear();
		return set.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends A> c) {
		list.clear();
		return set.addAll(c);
	}

	@Override
	public void clear() {
		list.clear();
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
	public boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public Iterator<A> iterator() {
		return set.iterator();
	}

	@Override
	public boolean remove(Object o) {
		list.clear();
		return set.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		list.clear();
		return set.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		list.clear();
		return set.retainAll(c);
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public Object[] toArray() {
		return set.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

	@Override
	public void add(int index, A element) {
		throw new MatrixException("not implemented");
	}

	@Override
	public boolean addAll(int index, Collection<? extends A> c) {
		throw new MatrixException("not implemented");
	}

	@Override
	public A get(int index) {
		createList();
		return list.get(index);
	}

	@Override
	public int indexOf(Object o) {
		createList();
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		createList();
		return list.lastIndexOf(o);
	}

	@Override
	public ListIterator<A> listIterator() {
		createList();
		return list.listIterator();
	}

	@Override
	public ListIterator<A> listIterator(int index) {
		createList();
		return list.listIterator(index);
	}

	@Override
	public A remove(int index) {
		createList();
		A o = list.remove(index);
		list.clear();
		set.remove(o);
		return o;
	}

	@Override
	public A set(int index, A element) {
		throw new MatrixException("not implemented");
	}

	@Override
	public List<A> subList(int fromIndex, int toIndex) {
		createList();
		return list.subList(fromIndex, toIndex);
	}

	private void createList() {
		if (list.size() != set.size()) {
			list.clear();
			for (A a : set) {
				list.add(a);
			}
		}
	}

}

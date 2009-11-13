/*
 * Copyright (C) 2008-2009 by Holger Arndt
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
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.ujmp.core.exceptions.MatrixException;

public class SortedListSet<A> extends AbstractList<A> implements SortedSet<A>, List<A>,
		Serializable {
	private static final long serialVersionUID = -9069699328418816771L;

	private SortedSet<A> set = new TreeSet<A>();

	private List<A> list = new ArrayList<A>();

	public SortedListSet() {
	}

	public Comparator<? super A> comparator() {
		return set.comparator();
	}

	public A first() {
		return set.first();
	}

	public SortedSet<A> headSet(A toElement) {
		return set.headSet(toElement);
	}

	public A last() {
		return set.last();
	}

	public SortedSet<A> subSet(A fromElement, A toElement) {
		return set.subSet(fromElement, toElement);
	}

	public SortedSet<A> tailSet(A fromElement) {
		return set.tailSet(fromElement);
	}

	public synchronized boolean add(A e) {
		list.clear();
		return set.add(e);
	}

	public synchronized boolean addAll(Collection<? extends A> c) {
		list.clear();
		return set.addAll(c);
	}

	public synchronized void clear() {
		list.clear();
		set.clear();
	}

	public boolean contains(Object o) {
		return set.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	public Iterator<A> iterator() {
		return set.iterator();
	}

	public synchronized boolean remove(Object o) {
		list.clear();
		return set.remove(o);
	}

	public synchronized boolean removeAll(Collection<?> c) {
		list.clear();
		return set.removeAll(c);
	}

	public synchronized boolean retainAll(Collection<?> c) {
		list.clear();
		return set.retainAll(c);
	}

	public int size() {
		return set.size();
	}

	public Object[] toArray() {
		return set.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

	public void add(int index, A element) {
		throw new MatrixException("not implemented");
	}

	public boolean addAll(int index, Collection<? extends A> c) {
		throw new MatrixException("not implemented");
	}

	public synchronized A get(int index) {
		createList();
		return list.get(index);
	}

	public synchronized int indexOf(Object o) {
		createList();
		return list.indexOf(o);
	}

	public synchronized int lastIndexOf(Object o) {
		createList();
		return list.lastIndexOf(o);
	}

	public synchronized ListIterator<A> listIterator() {
		createList();
		return list.listIterator();
	}

	public synchronized ListIterator<A> listIterator(int index) {
		createList();
		return list.listIterator(index);
	}

	public synchronized A remove(int index) {
		createList();
		A o = list.remove(index);
		list.clear();
		set.remove(o);
		return o;
	}

	public A set(int index, A element) {
		throw new MatrixException("not implemented");
	}

	public synchronized List<A> subList(int fromIndex, int toIndex) {
		createList();
		return list.subList(fromIndex, toIndex);
	}

	private void createList() {
		if (list.size() != set.size()) {
			list.clear();
			list.addAll(set);
		}
	}

}

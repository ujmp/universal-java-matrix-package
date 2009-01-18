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

package org.ujmp.core.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ujmp.core.interfaces.Wrapper;

public class MapToListWrapper<A> implements Wrapper<Map<Integer, A>>, List<A> {

	private static final Logger logger = Logger.getLogger(MapToListWrapper.class.getName());

	private Map<Integer, A> map = null;

	private int pos = 0;

	private int start = 0;

	public MapToListWrapper(Map<Integer, A> map) {
		this.map = map;
		start = 0;
		pos = map.size();
	}

	public boolean add(A e) {
		map.put(pos++, e);
		return true;
	}

	public void add(int index, A element) {
		logger.log(Level.WARNING, "not implemented");
	}

	public boolean addAll(Collection<? extends A> c) {
		for (A a : c) {
			add(a);
		}
		return true;
	}

	public boolean addAll(int index, Collection<? extends A> c) {
		logger.log(Level.WARNING, "not implemented");
		return false;
	}

	public void clear() {
		start = 0;
		pos = 0;
		map.clear();
	}

	public boolean contains(Object o) {
		return map.containsValue(o);
	}

	public boolean containsAll(Collection<?> c) {
		logger.log(Level.WARNING, "not implemented");
		return false;
	}

	public A get(int index) {
		return map.get(start + index);
	}

	public int indexOf(Object o) {
		logger.log(Level.WARNING, "not implemented");
		return 0;
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Iterator<A> iterator() {
		return map.values().iterator();
	}

	public int lastIndexOf(Object o) {
		logger.log(Level.WARNING, "not implemented");
		return 0;
	}

	public ListIterator<A> listIterator() {
		logger.log(Level.WARNING, "not implemented");
		return null;
	}

	public ListIterator<A> listIterator(int index) {
		logger.log(Level.WARNING, "not implemented");
		return null;
	}

	public boolean remove(Object o) {
		remove(indexOf(o));
		return true;
	}

	public A remove(int index) {
		if (index == 0) {
			map.remove(start);
			start++;
		} else {
			logger.log(Level.WARNING, "not implemented");
		}
		return null;
	}

	public boolean removeAll(Collection<?> c) {
		logger.log(Level.WARNING, "not implemented");
		return false;
	}

	public boolean retainAll(Collection<?> c) {
		logger.log(Level.WARNING, "not implemented");
		return false;
	}

	public A set(int index, A element) {
		map.put(start + index, element);
		return null;
	}

	public int size() {
		return map.size();
	}

	public List<A> subList(int fromIndex, int toIndex) {
		logger.log(Level.WARNING, "not implemented");
		return null;
	}

	public Object[] toArray() {
		logger.log(Level.WARNING, "not implemented");
		return null;
	}

	public <T> T[] toArray(T[] a) {
		logger.log(Level.WARNING, "not implemented");
		return null;
	}

	public Map<Integer, A> getWrappedObject() {
		return map;
	}

	public void setWrappedObject(Map<Integer, A> object) {
		this.map = object;
	}

}

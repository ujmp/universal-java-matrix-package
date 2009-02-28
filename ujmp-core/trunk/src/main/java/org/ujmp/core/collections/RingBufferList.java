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

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class RingBufferList<A> implements List<A>, Serializable {
	private static final long serialVersionUID = 7887830184838493458L;

	private int start = -1;

	private int end = -1;

	private Object values[];

	public RingBufferList() {
		this(10);
	}

	public RingBufferList(int maximumSize) {
		values = new Object[maximumSize];
	}

	public int maxSize() {
		return values.length;
	}

	public boolean add(A a) {
		if (end >= 0) {
			end++;
			if (end >= values.length) {
				end = 0;
			}
			if (end == start) {
				start++;
			}
			if (start >= values.length) {
				start = 0;
			}
		} else {
			start = 0;
			end = 0;
		}
		values[end] = a;
		return true;
	}

	public int size() {
		if (end < 0) {
			return 0;
		}
		return end < start ? values.length : end - start + 1;
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < size(); i++) {
			s.append(get(i));
			if (i < size() - 1) {
				s.append(", ");
			}
		}
		return s.toString();
	}

	public void addFirst(A a) {
		if (end >= 0) {
			start--;
			if (start < 0) {
				start = values.length - 1;
			}
			if (start == end) {
				end--;
			}
			if (end < 0) {
				end = values.length - 1;
			}
		} else {
			start = 0;
			end = 0;
		}
		values[start] = a;
	}

	@SuppressWarnings("unchecked")
	public A get(int index) {
		return (A) values[(start + index) % values.length];
	}

	@SuppressWarnings("unchecked")
	public A set(int index, A a) {
		A old = (A) values[(start + index) % values.length];
		values[(start + index) % values.length] = a;
		return old;
	}

	public void clear() {
		start = -1;
		end = -1;
	}

	public void add(int index, A element) {
		new Exception("not implemented").printStackTrace();
	}

	public boolean addAll(Collection<? extends A> c) {
		for (A a : c) {
			add(a);
		}
		return false;
	}

	public boolean addAll(int index, Collection<? extends A> c) {
		new Exception("not implemented").printStackTrace();
		return false;
	}

	public boolean contains(Object o) {
		for (int i = size(); --i >= 0;) {
			if (o.equals(get(i))) {
				return true;
			}
		}
		return false;
	}

	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!contains(o)) {
				return false;
			}
		}
		return true;
	}

	public int indexOf(Object o) {
		for (int i = 0; i < size(); i++) {
			if (o.equals(get(i))) {
				return i;
			}
		}
		return -1;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public Iterator<A> iterator() {
		return new RingBufferIterator();
	}

	private class RingBufferIterator implements Iterator<A> {

		int pos = 0;

		public RingBufferIterator() {
		}

		public boolean hasNext() {
			return pos < size();
		}

		public A next() {
			A a = get(pos);
			pos++;
			return a;
		}

		public void remove() {
			new Exception("not implemented").printStackTrace();
		}

	}

	public int lastIndexOf(Object o) {
		for (int i = size(); --i >= 0;) {
			if (o.equals(get(i))) {
				return i;
			}
		}
		return -1;
	}

	public ListIterator<A> listIterator() {
		new Exception("not implemented").printStackTrace();
		return null;
	}

	public ListIterator<A> listIterator(int index) {
		new Exception("not implemented").printStackTrace();
		return null;
	}

	public boolean remove(Object o) {
		new Exception("not implemented").printStackTrace();
		return false;
	}

	public A remove(int index) {
		new Exception("not implemented").printStackTrace();
		return null;
	}

	public boolean removeAll(Collection<?> c) {
		new Exception("not implemented").printStackTrace();
		return false;
	}

	public boolean retainAll(Collection<?> c) {
		new Exception("not implemented").printStackTrace();
		return false;
	}

	public List<A> subList(int fromIndex, int toIndex) {
		new Exception("not implemented").printStackTrace();
		return null;
	}

	public Object[] toArray() {
		new Exception("not implemented").printStackTrace();
		return null;
	}

	public <T> T[] toArray(T[] a) {
		new Exception("not implemented").printStackTrace();
		return null;
	}

}
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

package org.ujmp.core.listmatrix;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import org.ujmp.core.genericmatrix.stub.AbstractDenseGenericMatrix2D;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractListMatrix<E> extends AbstractDenseGenericMatrix2D<E> implements
		ListMatrix<E> {
	private static final long serialVersionUID = -6776628601679785451L;

	public AbstractListMatrix() {
		super(0, 1);
	}

	public abstract E get(int index);

	public final boolean add(E t) {
		boolean ret = addToList(t);
		fireValueChanged();
		return ret;
	}

	protected abstract boolean addToList(E t);

	protected abstract void addToList(int index, E element);

	public final E getObject(long row, long column) {
		return get(MathUtil.longToInt(row));
	}

	public final void setObject(E value, long row, long column) {
		set(MathUtil.longToInt(row), value);
	}

	public void add(int index, E element) {
		addToList(index, element);
		fireValueChanged();
	}

	public final E remove(int index) {
		E e = removeFromList(index);
		fireValueChanged();
		return e;
	}

	protected abstract E removeFromList(int index);

	public final boolean remove(Object o) {
		boolean ret = removeFromList(o);
		fireValueChanged();
		return ret;
	}

	protected abstract boolean removeFromList(Object o);

	public final E set(int index, E element) {
		E ret = setToList(index, element);
		fireValueChanged();
		return ret;
	}

	protected abstract E setToList(int index, E element);

	public final void clear() {
		clearList();
		fireValueChanged();
	}

	protected abstract void clearList();

	public abstract int size();

	public final long[] getSize() {
		size[ROW] = size();
		return size;
	}

	public final boolean contains(Object o) {
		return indexOf(o) >= 0;
	}

	public final boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (contains(o)) {
				return true;
			}
		}
		return false;
	}

	public final int indexOf(Object o) {
		ListIterator<E> it = listIterator();
		if (o == null) {
			while (it.hasNext())
				if (it.next() == null)
					return it.previousIndex();
		} else {
			while (it.hasNext())
				if (o.equals(it.next()))
					return it.previousIndex();
		}
		return -1;
	}

	public final boolean isEmpty() {
		return size() == 0;
	}

	public final int lastIndexOf(Object o) {
		ListIterator<E> it = listIterator(size());
		if (o == null) {
			while (it.hasPrevious())
				if (it.previous() == null)
					return it.nextIndex();
		} else {
			while (it.hasPrevious())
				if (o.equals(it.previous()))
					return it.nextIndex();
		}
		return -1;
	}

	public final boolean removeAll(Collection<?> c) {
		boolean ret = false;
		for (Object o : c) {
			if (remove(o)) {
				ret = true;
			}
		}
		return ret;
	}

	public Iterator<E> iterator() {
		return new Iterator<E>() {
			int current = -1;

			public boolean hasNext() {
				return current < size() - 1;
			}

			public E next() {
				current++;
				return get(current);
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public final Object[] toArray() {
		final Object[] objects = new Object[size()];
		for (int i = size() - 1; i != -1; i--) {
			objects[i] = get(i);
		}
		return objects;
	}

	@SuppressWarnings("unchecked")
	public final <T> T[] toArray(T[] a) {
		final T[] objects = (T[]) Array.newInstance(a.getClass(), size());
		for (int i = size() - 1; i != -1; i--) {
			objects[i] = (T) get(i);
		}
		return objects;
	}

	public boolean addAll(Collection<? extends E> c) {
		boolean ret = false;
		for (E o : c) {
			if (addToList(o)) {
				ret = true;
			}
		}
		if (ret) {
			fireValueChanged();
		}
		return ret;
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException();
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

	public ListIterator<E> listIterator() {
		return new ListIterator<E>() {
			int current = -1;

			public boolean hasNext() {
				return current < size() - 1;
			}

			public E next() {
				current++;
				return get(current);
			}

			public boolean hasPrevious() {
				return current > 0;
			}

			public E previous() {
				E e = get(current);
				current--;
				return e;
			}

			public int nextIndex() {
				return current + 1;
			}

			public int previousIndex() {
				return current;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}

			public void set(E e) {
				throw new UnsupportedOperationException();
			}

			public void add(E e) {
				throw new UnsupportedOperationException();
			}
		};
	}

	public ListIterator<E> listIterator(int index) {
		throw new UnsupportedOperationException();
	}

	public List<E> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	public final E getLast() {
		return get(size() - 1);
	}

}

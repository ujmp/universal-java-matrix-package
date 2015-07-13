/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

package org.ujmp.core.util;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LongArrayList {

	private static final int DEFAULT_CAPACITY = 10;

	private static final long[] EMPTY_ELEMENTDATA = {};

	private transient long[] elementData;

	private int modCount = 0;

	private int size;

	public LongArrayList(int initialCapacity) {
		super();
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
		this.elementData = new long[initialCapacity];
	}

	public LongArrayList() {
		super();
		this.elementData = EMPTY_ELEMENTDATA;
	}

	public void trimToSize() {
		modCount++;
		if (size < elementData.length) {
			elementData = Arrays.copyOf(elementData, size);
		}
	}

	public void ensureCapacity(int minCapacity) {
		int minExpand = (elementData != EMPTY_ELEMENTDATA) ? 0 : DEFAULT_CAPACITY;

		if (minCapacity > minExpand) {
			ensureExplicitCapacity(minCapacity);
		}
	}

	private void ensureCapacityInternal(int minCapacity) {
		if (elementData == EMPTY_ELEMENTDATA) {
			minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
		}

		ensureExplicitCapacity(minCapacity);
	}

	private void ensureExplicitCapacity(int minCapacity) {
		modCount++;

		if (minCapacity - elementData.length > 0)
			grow(minCapacity);
	}

	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	private void grow(int minCapacity) {
		int oldCapacity = elementData.length;
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		if (newCapacity - minCapacity < 0)
			newCapacity = minCapacity;
		if (newCapacity - MAX_ARRAY_SIZE > 0)
			newCapacity = hugeCapacity(minCapacity);
		elementData = Arrays.copyOf(elementData, newCapacity);
	}

	private static int hugeCapacity(int minCapacity) {
		if (minCapacity < 0) // overflow
			throw new OutOfMemoryError();
		return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
	}

	public long size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean contains(long o) {
		return indexOf(o) >= 0;
	}

	public int indexOf(long o) {
		for (int i = 0; i < size; i++)
			if (o == elementData[i]) {
				return i;
			}
		return -1;
	}

	public int lastIndexOf(long o) {
		for (int i = MathUtil.longToInt(size) - 1; i >= 0; i--)
			if (o == elementData[i]) {
				return i;
			}
		return -1;
	}

	public long[] toArray() {
		return Arrays.copyOf(elementData, size);
	}

	public long get(int index) {
		return elementData[index];
	}

	public long set(int index, long element) {
		long oldValue = elementData[index];
		elementData[index] = element;
		return oldValue;
	}

	public boolean add(long e) {
		ensureCapacityInternal(size + 1); // Increments modCount!!
		elementData[size++] = e;
		return true;
	}

	public void add(int index, long element) {
		ensureCapacityInternal(size + 1); // Increments modCount!!
		System.arraycopy(elementData, index, elementData, index + 1, size - index);
		elementData[index] = element;
		size++;
	}

	public long remove(int index) {
		rangeCheck(index);

		modCount++;
		long oldValue = elementData[index];

		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);

		return oldValue;
	}

	public boolean remove(long o) {
		for (int index = 0; index < size; index++)
			if (o == elementData[index]) {
				fastRemove(index);
				return true;
			}
		return false;
	}

	private void fastRemove(int index) {
		modCount++;
		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
	}

	public void clear() {
		modCount++;
		size = 0;
	}

	protected void removeRange(int fromIndex, int toIndex) {
		modCount++;
		int numMoved = size - toIndex;
		System.arraycopy(elementData, toIndex, elementData, fromIndex, numMoved);
		int newSize = size - (toIndex - fromIndex);
		size = newSize;
	}

	private void rangeCheck(int index) {
		if (index >= size)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	private String outOfBoundsMsg(int index) {
		return "Index: " + index + ", Size: " + size;
	}

	public ListIterator<Long> listIterator(int index) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException("Index: " + index);
		return new ListItr(index);
	}

	public ListIterator<Long> listIterator() {
		return new ListItr(0);
	}

	public Iterator<Long> iterator() {
		return new Itr();
	}

	private class Itr implements Iterator<Long> {
		int cursor; // index of next element to return
		int lastRet = -1; // index of last element returned; -1 if no such
		int expectedModCount = modCount;

		public boolean hasNext() {
			return cursor != size;
		}

		public Long next() {
			checkForComodification();
			int i = cursor;
			if (i >= size)
				throw new NoSuchElementException();
			long[] elementData = LongArrayList.this.elementData;
			if (i >= elementData.length)
				throw new ConcurrentModificationException();
			cursor = i + 1;
			return (Long) elementData[lastRet = i];
		}

		public void remove() {
			if (lastRet < 0)
				throw new IllegalStateException();
			checkForComodification();

			try {
				LongArrayList.this.remove(lastRet);
				cursor = lastRet;
				lastRet = -1;
				expectedModCount = modCount;
			} catch (IndexOutOfBoundsException ex) {
				throw new ConcurrentModificationException();
			}
		}

		final void checkForComodification() {
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
		}
	}

	private class ListItr extends Itr implements ListIterator<Long> {
		ListItr(int index) {
			super();
			cursor = index;
		}

		public boolean hasPrevious() {
			return cursor != 0;
		}

		public int nextIndex() {
			return cursor;
		}

		public int previousIndex() {
			return cursor - 1;
		}

		public Long previous() {
			checkForComodification();
			int i = cursor - 1;
			if (i < 0)
				throw new NoSuchElementException();
			long[] elementData = LongArrayList.this.elementData;
			if (i >= elementData.length)
				throw new ConcurrentModificationException();
			cursor = i;
			return (Long) elementData[lastRet = i];
		}

		public void set(Long e) {
			if (lastRet < 0)
				throw new IllegalStateException();
			checkForComodification();

			try {
				LongArrayList.this.set(lastRet, e);
			} catch (IndexOutOfBoundsException ex) {
				throw new ConcurrentModificationException();
			}
		}

		public void add(Long e) {
			checkForComodification();

			try {
				int i = cursor;
				LongArrayList.this.add(i, e);
				cursor = i + 1;
				lastRet = -1;
				expectedModCount = modCount;
			} catch (IndexOutOfBoundsException ex) {
				throw new ConcurrentModificationException();
			}
		}
	}

}

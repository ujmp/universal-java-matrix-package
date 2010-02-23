/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

import java.util.BitSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

public class BitSetSet implements Set<Integer>, SortedSet<Integer> {

	BitSet bitset = new BitSet();

	public BitSetSet() {
	}

	public BitSetSet(BitSetSet source) {
		bitset.or(source.bitset);
	}

	public boolean add(Integer e) {
		bitset.set(e);
		return true;
	}

	public void set(int start, int end) {
		bitset.set(start, end);
	}

	public void clear(int index) {
		bitset.clear(index);
	}

	public boolean addAll(Collection<? extends Integer> c) {
		if (c instanceof BitSetSet) {
			addAll((BitSetSet) c);
		} else {
			for (Integer i : c) {
				bitset.set(i);
			}
		}
		return true;
	}

	public void addAll(BitSetSet bss) {
		bitset.or(bss.bitset);
	}

	public BitSetSet clone() {
		return new BitSetSet(this);
	}

	public void clear() {
		bitset.clear();
	}

	public boolean contains(Object o) {
		return bitset.get((Integer) o);
	}

	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!bitset.get((Integer) o))
				return false;
		}
		return true;
	}

	public boolean isEmpty() {
		return bitset.isEmpty();
	}

	public Iterator<Integer> iterator() {
		return new BitIterator();
	}

	class BitIterator implements Iterator<Integer> {

		int index = 0;

		public BitIterator() {

		}

		public boolean hasNext() {
			return index != -1 && bitset.nextSetBit(index) != -1;
		}

		public Integer next() {
			int ret = bitset.nextSetBit(index);
			index = bitset.nextSetBit(ret + 1);
			return ret;
		}

		public void remove() {
			(new Exception("not implemented")).printStackTrace();
		}

	}

	public boolean remove(Object o) {
		bitset.clear((Integer) o);
		return true;
	}

	public boolean removeAll(Collection<?> c) {
		for (Object o : c) {
			int i = (Integer) o;
			if (i >= 0) {
				bitset.clear(i);
			}
		}
		return true;
	}

	public String toString() {
		return bitset.toString();
	}

	public boolean retainAll(Collection<?> c) {
		(new Exception("not implemented")).printStackTrace();
		return false;
	}

	public int size() {
		return bitset.cardinality();
	}

	public Object[] toArray() {
		(new Exception("not implemented")).printStackTrace();
		return null;
	}

	public <T> T[] toArray(T[] a) {
		(new Exception("not implemented")).printStackTrace();
		return null;
	}

	public Comparator<? super Integer> comparator() {
		Comparator<Integer> comparator = new Comparator<Integer>() {

			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}

		};
		return comparator;
	}

	public Integer first() {
		return bitset.nextSetBit(0);
	}

	public SortedSet<Integer> headSet(Integer toElement) {
		(new Exception("not implemented")).printStackTrace();
		return null;
	}

	public Integer last() {
		return bitset.length() - 1;
	}

	public SortedSet<Integer> subSet(Integer fromElement, Integer toElement) {
		(new Exception("not implemented")).printStackTrace();
		return null;
	}

	public SortedSet<Integer> tailSet(Integer fromElement) {
		(new Exception("not implemented")).printStackTrace();
		return null;
	}

}

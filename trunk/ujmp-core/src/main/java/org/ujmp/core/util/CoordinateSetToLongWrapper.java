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

package org.ujmp.core.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.ujmp.core.Coordinates;
import org.ujmp.core.interfaces.Wrapper;

public class CoordinateSetToLongWrapper implements Set<long[]>, Wrapper<Set<Coordinates>> {

	private Set<Coordinates> set = null;

	public CoordinateSetToLongWrapper(Set<Coordinates> set) {
		this.set = set;
	}

	public Set<Coordinates> getWrappedObject() {
		return set;
	}

	public void setWrappedObject(Set<Coordinates> object) {
		this.set = object;
	}

	public boolean add(long[] e) {
		return set.add(Coordinates.wrap(e).clone());
	}

	public boolean addAll(Collection<? extends long[]> c) {
		return false;
	}

	public void clear() {
		set.clear();
	}

	public boolean contains(Object o) {
		return set.contains(Coordinates.wrap((long[]) o));
	}

	public boolean containsAll(Collection<?> c) {
		throw new RuntimeException("not implemented");
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	public Iterator<long[]> iterator() {
		return new LongIterator(this);
	}

	class LongIterator implements Iterator<long[]> {

		Iterator<Coordinates> it = null;

		public LongIterator(CoordinateSetToLongWrapper wrapper) {
			it = wrapper.set.iterator();
		}

		public boolean hasNext() {
			return it.hasNext();
		}

		public long[] next() {
			return it.next().getLongCoordinates();
		}

		public void remove() {
			throw new RuntimeException("not implemented");
		}

	}

	public boolean remove(Object o) {
		return set.remove(Coordinates.wrap((long[]) o));
	}

	public boolean removeAll(Collection<?> c) {
		throw new RuntimeException("not implemented");
	}

	public boolean retainAll(Collection<?> c) {
		throw new RuntimeException("not implemented");
	}

	public int size() {
		return set.size();
	}

	public Object[] toArray() {
		throw new RuntimeException("not implemented");
	}

	public <T> T[] toArray(T[] a) {
		throw new RuntimeException("not implemented");
	}

}

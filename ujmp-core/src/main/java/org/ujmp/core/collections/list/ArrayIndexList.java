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

package org.ujmp.core.collections.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * ArrayIndexList is like an ArrayList, but keeps track of the indices where
 * objects have been added. This improves the speed of indexOf() and contains()
 * 
 * @author Holger Arndt
 * 
 * @param <M>
 *            Type of the elements in the list
 */
public class ArrayIndexList<M> extends ArrayList<M> {
	private static final long serialVersionUID = 3657191905843442834L;

	private final Map<M, Integer> indexMap = new HashMap<M, Integer>();

	public ArrayIndexList() {
		super();
	}

	public ArrayIndexList(Collection<? extends M> c) {
		super();
		addAll(c);
	}

	public void add(int index, M element) {
		throw new RuntimeException("not implemented");
	}

	public boolean add(M e) {
		indexMap.put(e, size());
		return super.add(e);
	}

	public boolean addAll(Collection<? extends M> c) {
		for (M m : c) {
			add(m);
		}
		return true;
	}

	public boolean addAll(int index, Collection<? extends M> c) {
		throw new RuntimeException("not implemented");
	}

	public void clear() {
		indexMap.clear();
		super.clear();
	}

	public boolean contains(Object o) {
		return indexMap.containsKey(o);
	}

	public int indexOf(Object o) {
		Integer index = indexMap.get(o);
		if (index == null) {
			return -1;
		} else {
			return index;
		}
	}

	public int lastIndexOf(Object o) {

		return super.lastIndexOf(o);
	}

	public M remove(int index) {
		M m = super.remove(index);
		indexMap.remove(m);
		return m;
	}

	public boolean remove(Object o) {
		indexMap.remove(o);
		return super.remove(o);
	}

	public M set(int index, M element) {
		throw new RuntimeException("not implemented");
	}

}

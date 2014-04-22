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

package org.ujmp.core.treematrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ujmp.core.collections.list.ArrayIndexList;
import org.ujmp.core.doublematrix.SparseDoubleMatrix2D;
import org.ujmp.core.doublematrix.factory.SparseDoubleMatrix2DFactory;

public class DefaultTreeMatrix<T> extends AbstractTreeMatrix<T> {
	private static final long serialVersionUID = -6752285310555819432L;

	private final List<T> objects = new ArrayIndexList<T>();

	private T root = null;

	private final Map<T, List<T>> childrenMap = new HashMap<T, List<T>>();

	private final Map<T, T> parentMap = new HashMap<T, T>();

	public List<T> getChildren(Object o) {
		List<T> children = childrenMap.get(o);
		if (children == null) {
			children = new ArrayList<T>();
			childrenMap.put((T) o, children);
		}
		return children;
	}

	public Map<T, T> getParentMap() {
		return parentMap;
	}

	public List<T> getObjectList() {
		return objects;
	}

	public Object getRoot() {
		return root;
	}

	public void setRoot(T o) {
		this.root = o;
		if (!objects.contains(o)) {
			objects.add(o);
		}
		notifyGUIObject();
	}

	public SparseDoubleMatrix2DFactory<? extends SparseDoubleMatrix2D> getFactory() {
		throw new RuntimeException("not implemented");
	}

}

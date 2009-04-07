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

package org.ujmp.core.treematrix;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.ujmp.core.collections.ArrayIndexList;

public class DefaultTreeMatrix extends AbstractTreeMatrix {
	private static final long serialVersionUID = -6752285310555819432L;

	private final List<Object> objects = new ArrayIndexList<Object>();

	private Object root = null;

	private final Map<Object, List<Object>> childrenMap = new HashMap<Object, List<Object>>();

	@Override
	public List<Object> getChildren(Object o) {
		List<Object> children = childrenMap.get(o);
		if (children == null) {
			children = new LinkedList<Object>();
			childrenMap.put(o, children);
		}
		return children;
	}

	@Override
	public List<Object> getObjectList() {
		return objects;
	}

	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public void setRoot(Object o) {
		this.root = o;
		if (!objects.contains(o)) {
			objects.add(o);
		}
		notifyGUIObject();
	}

}

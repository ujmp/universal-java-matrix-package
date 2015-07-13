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

package org.ujmp.core.treematrix;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface Tree<T> {

	public Map<T, T> getParentMap();

	public void setRoot(T root);

	public List<T> getChildren(Object parent);

	public long getChildCountRecursive(Object parent);

	public T getParent(T o);

	public Collection<T> getObjectList();

	public boolean isChild(Object parent, Object child);

	public boolean isChild(int parentId, int childId);

	public T getObject(int index);

	public void addChild(T parent, T child);

	public void addChildren(T parent, Collection<? extends T> children);

	public void removeChild(T parent, T child);

	public void addObject(T o);

	public int getNumberOfObjects();

}

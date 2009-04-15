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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

import org.ujmp.core.doublematrix.AbstractSparseDoubleMatrix2D;

public abstract class AbstractTreeMatrix extends AbstractSparseDoubleMatrix2D implements TreeMatrix {
	private static final long serialVersionUID = 7731771819651651188L;

	public boolean contains(long... coordinates) {
		return false;
	}

	public final boolean isChild(Object parent, Object child) {
		return getChildren(parent).contains(child);
	}

	public final boolean isChild(int parentId, int childId) {
		Object parent = getObject(parentId);
		Object child = getObject(childId);
		return isChild(parent, child);
	}

	@SuppressWarnings("unchecked")
	public final Object getObject(int index) {
		if (getObjectList() instanceof List) {
			return ((List) getObjectList()).get(index);
		} else {
			// TODO: improve
			return new ArrayList(getObjectList()).get(index);
		}
	}

	@Override
	public Object getParent(Object o) {
		return getParentMap().get(o);
	}

	@Override
	public void addChildren(Object parent, Collection<? extends Object> children) {
		for (Object child : children) {
			addChild(parent, child);
		}
	}

	public final long[] getSize() {
		return new long[] { getObjectList().size(), getObjectList().size() };
	}

	public final double getDouble(int row, int column) {
		return isChild(row, column) ? 1.0 : 0.0;
	}

	public final double getDouble(long row, long column) {
		return getDouble((int) row, (int) column);
	}

	public final void addChild(Object parent, Object child) {
		if (!getObjectList().contains(child)) {
			getObjectList().add(child);
		}
		getChildren(parent).add(child);
		getParentMap().put(child, parent);
		notifyGUIObject();
	}

	public final void removeChild(Object parent, Object child) {
		getChildren(parent).remove(child);
		Object oldParent = getParentMap().get(child);
		if (parent.equals(oldParent)) {
			getParentMap().remove(child);
		}
		notifyGUIObject();
	}

	public final void setDouble(double value, long row, long column) {
		setDouble(value, (int) row, (int) column);
	}

	public void setDouble(double value, int row, int column) {
		Object parent = getObject(row);
		Object child = getObject(column);
		if (value == 0.0) {
			removeChild(parent, child);
		} else {
			addChild(parent, child);
		}
	}

	public void addTreeModelListener(TreeModelListener l) {
	}

	public final Object getChild(Object parent, int index) {
		return getChildren(parent).get(index);
	}

	public final int getChildCount(Object parent) {
		return getChildren(parent).size();
	}

	public final int getIndexOfChild(Object parent, Object child) {
		return getChildren(parent).indexOf(child);
	}

	public final boolean isLeaf(Object node) {
		return getChildren(node).size() == 0;
	}

	public final void removeTreeModelListener(TreeModelListener l) {
	}

	public final void valueForPathChanged(TreePath path, Object newValue) {
	}

	public final int getNumberOfObjects() {
		return getObjectList().size();
	}

	public void addObject(Object o) {
		getObjectList().add(o);
	}

}

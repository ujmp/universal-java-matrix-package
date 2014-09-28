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
import java.util.Collection;
import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

import org.ujmp.core.doublematrix.stub.AbstractSparseDoubleMatrix2D;

public abstract class AbstractTreeMatrix<T> extends AbstractSparseDoubleMatrix2D implements
		TreeMatrix<T> {
	private static final long serialVersionUID = 7731771819651651188L;

	public AbstractTreeMatrix() {
		super(1, 1);
	}

	public boolean contains(long... coordinates) {
		return false;
	}

	public final boolean isChild(Object parent, Object child) {
		return getChildren(parent).contains(child);
	}

	public final boolean isChild(int parentId, int childId) {
		T parent = getObject(parentId);
		T child = getObject(childId);
		return isChild(parent, child);
	}

	public final T getObject(int index) {
		if (getObjectList() instanceof List) {
			return ((List<T>) getObjectList()).get(index);
		} else {
			// TODO: improve
			return new ArrayList<T>(getObjectList()).get(index);
		}
	}

	public final long getChildCountRecursive(Object parent) {
		long count = 0;
		count += getChildCount(parent);
		for (T child : getChildren(parent)) {
			count += getChildCountRecursive(child);
		}
		return count;
	}

	public T getParent(Object o) {
		return getParentMap().get(o);
	}

	public void addChildren(T parent, Collection<? extends T> children) {
		for (T child : children) {
			addChild(parent, child);
		}
	}

	public final long[] getSize() {
		size[ROW] = getObjectList().size();
		size[COLUMN] = getObjectList().size();
		return size;
	}

	public final double getDouble(int row, int column) {
		return isChild(row, column) ? 1.0 : 0.0;
	}

	public final double getDouble(long row, long column) {
		return getDouble((int) row, (int) column);
	}

	public final void addChild(T parent, T child) {
		if (!getObjectList().contains(child)) {
			getObjectList().add(child);
		}
		getChildren(parent).add(child);
		getParentMap().put(child, parent);
		fireValueChanged();
	}

	public final void removeChild(T parent, T child) {
		getChildren(parent).remove(child);
		T oldParent = getParentMap().get(child);
		if (parent.equals(oldParent)) {
			getParentMap().remove(child);
		}
		fireValueChanged();
	}

	public final void setDouble(double value, long row, long column) {
		setDouble(value, (int) row, (int) column);
	}

	public void setDouble(double value, int row, int column) {
		T parent = getObject(row);
		T child = getObject(column);
		if (value == 0.0) {
			removeChild(parent, child);
		} else {
			addChild(parent, child);
		}
	}

	public void addTreeModelListener(TreeModelListener l) {
	}

	public final T getChild(Object parent, int index) {
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

	public void addObject(T o) {
		getObjectList().add(o);
	}

}

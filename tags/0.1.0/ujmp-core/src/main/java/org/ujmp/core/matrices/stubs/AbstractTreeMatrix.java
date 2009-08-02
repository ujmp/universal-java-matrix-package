/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

package org.ujmp.core.matrices.stubs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public abstract class AbstractTreeMatrix<A> extends AbstractSparseDoubleMatrix implements TreeModel {

	public boolean contains(long... coordinates) {
		return false;
	}

	public abstract Collection<Object> getObjectList();

	public final boolean isChild(Object parent, Object child) {
		return getChildren(parent).contains(child);
	}

	@SuppressWarnings("unchecked")
	public final int getIndexOf(Object object) {
		if (getObjectList() instanceof List) {
			return ((List) getObjectList()).indexOf(object);
		} else {
			// TODO: improve
			return new ArrayList(getObjectList()).indexOf(object);
		}
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

	public abstract List<Object> getChildren(Object o);

	public Iterable<long[]> allCoordinates() {
		return Collections.emptyList();
	}

	public final long[] getSize() {
		return new long[] { getObjectList().size(), getObjectList().size() };
	}

	public final double getDouble(long... coordinates) {
		return isChild(coordinates[ROW], coordinates[COLUMN]) ? 1.0 : 0.0;
	}

	@Override
	public long getValueCount() {
		return 0;
	}

	public final void addChild(Object parent, Object child) {
		getChildren(parent).add(child);
		if (!getObjectList().contains(child)) {
			getObjectList().add(child);
		}
		notifyGUIObject();
	}

	public final void removeChild(Object parent, Object child) {
		getChildren(parent).remove(child);
		notifyGUIObject();
	}

	public void setDouble(double value, long... coordinates) {
		Object parent = getObject((int) coordinates[ROW]);
		Object child = getObject((int) coordinates[COLUMN]);
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

	public abstract Object getRoot();

	public abstract void setRoot(Object o);

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

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

package org.ujmp.gui.table;

import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FastListSelectionModel64 implements ListSelectionModel64 {

	private final int selectionMode = SINGLE_INTERVAL_SELECTION;

	private final EventListenerList listenerList = new EventListenerList();

	private long minIndex = -1;
	private long maxIndex = -1;

	private boolean valueIsAdjusting = false;

	public FastListSelectionModel64() {
	}

	public final void addListSelectionListener(final ListSelectionListener l) {
		listenerList.add(ListSelectionListener.class, l);
	}

	public final void removeListSelectionListener(final ListSelectionListener l) {
		listenerList.remove(ListSelectionListener.class, l);
	}

	public final void addListSelectionListener(final ListSelectionListener64 l) {
		listenerList.add(ListSelectionListener64.class, l);
	}

	public final void removeListSelectionListener(final ListSelectionListener64 l) {
		listenerList.remove(ListSelectionListener64.class, l);
	}

	public final void addSelectionInterval(final int index0, final int index1) {
		if (index1 > index0) {
			minIndex = Math.min(minIndex, index0);
			maxIndex = Math.max(maxIndex, index1);
			fireValueChanged(index0, index1, getValueIsAdjusting());
		} else {
			minIndex = Math.min(minIndex, index1);
			maxIndex = Math.max(maxIndex, index0);
			fireValueChanged(index1, index0, getValueIsAdjusting());
		}
	}

	public final void addSelectionInterval(final long index0, final long index1) {
		if (index1 > index0) {
			minIndex = Math.min(minIndex, index0);
			maxIndex = Math.max(maxIndex, index1);
			fireValueChanged(index0, index1, getValueIsAdjusting());
		} else {
			minIndex = Math.min(minIndex, index1);
			maxIndex = Math.max(maxIndex, index0);
			fireValueChanged(index1, index0, getValueIsAdjusting());
		}
	}

	protected final void fireValueChanged(final long firstIndex, final long lastIndex) {
		fireValueChanged(firstIndex, lastIndex, getValueIsAdjusting());
	}

	protected final void fireValueChanged(final long firstIndex, final long lastIndex, final boolean isAdjusting) {
		final Object[] listeners = listenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ListSelectionListener64.class) {
				((ListSelectionListener64) listeners[i + 1]).valueChanged(new ListSelectionEvent64(this, firstIndex,
						lastIndex, isAdjusting));
			} else if (listeners[i] == ListSelectionListener.class) {
				((ListSelectionListener) listeners[i + 1]).valueChanged(new ListSelectionEvent(this, (int) firstIndex,
						(int) lastIndex, isAdjusting));
			}
		}
	}

	public final void clearSelection() {
		minIndex = -1;
		maxIndex = -1;
		fireValueChanged();
	}

	private final void fireValueChanged() {
		fireValueChanged(0, Long.MAX_VALUE);
	}

	public final int getMinSelectionIndex() {
		return (int) minIndex;
	}

	public final long getMinSelectionIndex64() {
		return minIndex;
	}

	public final int getAnchorSelectionIndex() {
		return (int) minIndex;
	}

	public final long getAnchorSelectionIndex64() {
		return minIndex;
	}

	public final int getLeadSelectionIndex() {
		return (int) maxIndex;
	}

	public final long getLeadSelectionIndex64() {
		return maxIndex;
	}

	public final int getMaxSelectionIndex() {
		return (int) maxIndex;
	}

	public final long getMaxSelectionIndex64() {
		return maxIndex;
	}

	public final int getSelectionMode() {
		return selectionMode;
	}

	public final boolean getValueIsAdjusting() {
		return valueIsAdjusting;
	}

	public final void insertIndexInterval(final int index, final int length, final boolean before) {
		minIndex = Math.min(minIndex, index);
		maxIndex = Math.max(maxIndex, index + length);
		fireValueChanged();
	}

	public final void insertIndexInterval(final long index, final long length, final boolean before) {
		minIndex = Math.min(minIndex, index);
		maxIndex = Math.max(maxIndex, index + length);
		fireValueChanged();
	}

	public final boolean isSelectedIndex(final int index) {
		return index >= minIndex && index <= maxIndex;
	}

	public final boolean isSelectedIndex(final long index) {
		return index >= minIndex && index <= maxIndex;
	}

	public final boolean isSelectionEmpty() {
		return minIndex == -1 || maxIndex == -1;
	}

	public final void removeIndexInterval(final int index0, final int index1) {
		if (index1 >= index0) {
			minIndex = Math.max(minIndex, index0);
			maxIndex = Math.min(maxIndex, index1);
		} else {
			minIndex = Math.max(minIndex, index1);
			maxIndex = Math.min(maxIndex, index0);
		}
		fireValueChanged();
	}

	public final void removeIndexInterval(final long index0, final long index1) {
		if (index1 >= index0) {
			minIndex = Math.max(minIndex, index0);
			maxIndex = Math.min(maxIndex, index1);
		} else {
			minIndex = Math.max(minIndex, index1);
			maxIndex = Math.min(maxIndex, index0);
		}
		fireValueChanged();
	}

	public final void removeSelectionInterval(final int index0, final int index1) {
		if (index1 >= index0) {
			minIndex = Math.max(minIndex, index0);
			maxIndex = Math.min(maxIndex, index1);
		} else {
			minIndex = Math.max(minIndex, index1);
			maxIndex = Math.min(maxIndex, index0);
		}
		fireValueChanged(index0, index1, getValueIsAdjusting());
	}

	public final void removeSelectionInterval(final long index0, final long index1) {
		if (index1 >= index0) {
			minIndex = Math.max(minIndex, index0);
			maxIndex = Math.min(maxIndex, index1);
		} else {
			minIndex = Math.max(minIndex, index1);
			maxIndex = Math.min(maxIndex, index0);
		}
		fireValueChanged(index0, index1, getValueIsAdjusting());
	}

	public final void setAnchorSelectionIndex(final int index) {
		minIndex = Math.min(minIndex, index);
		fireValueChanged();
	}

	public final void setAnchorSelectionIndex(final long index) {
		minIndex = Math.min(minIndex, index);
		fireValueChanged();
	}

	public final void setLeadSelectionIndex(final int index) {
		maxIndex = Math.max(maxIndex, index);
		fireValueChanged();
	}

	public final void setLeadSelectionIndex(final long index) {
		maxIndex = Math.max(maxIndex, index);
		fireValueChanged();
	}

	public final void setSelectionInterval(final int index0, final int index1) {
		long oldMinIndex = minIndex;
		long oldMaxIndex = maxIndex;
		if (index1 > index0) {
			minIndex = index0;
			maxIndex = index1;
		} else {
			minIndex = index1;
			maxIndex = index0;
		}
		fireValueChanged(Math.min(oldMinIndex, minIndex), Math.max(oldMaxIndex, maxIndex), getValueIsAdjusting());
	}

	public final void setSelectionInterval(final long index0, final long index1) {
		long oldMinIndex = minIndex;
		long oldMaxIndex = maxIndex;
		if (index1 > index0) {
			minIndex = index0;
			maxIndex = index1;
		} else {
			minIndex = index1;
			maxIndex = index0;
		}
		fireValueChanged(Math.min(oldMinIndex, minIndex), Math.max(oldMaxIndex, maxIndex), getValueIsAdjusting());
	}

	public final void setSelectionMode(final int selectionMode) {
		throw new UnsupportedOperationException("selection mode may not be changed");
	}

	public final void setValueIsAdjusting(final boolean valueIsAdjusting) {
		this.valueIsAdjusting = valueIsAdjusting;
	}

}

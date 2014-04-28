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

package org.ujmp.gui.table;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.ujmp.gui.MatrixGUIObject;

public class RowHeaderTableModel64 implements TableModel64, TableModelListener64 {

	private transient EventListenerList listenerList = null;

	private final MatrixGUIObject model;

	public RowHeaderTableModel64(MatrixGUIObject model) {
		this.model = model;
		model.addTableModelListener(this);
	}

	public int getRowCount() {
		return model.getRowCount();
	}

	public int getColumnCount() {
		return 1;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return model.getMatrix().getRowLabel(rowIndex);
	}

	public String getColumnName(int columnIndex) {
		return null;
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return !model.getMatrix().isReadOnly();
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		model.getMatrix().setRowLabel(rowIndex, String.valueOf(aValue));
	}

	public void addTableModelListener(TableModelListener l) {
		getListenerList().add(TableModelListener.class, l);
	}

	public void removeTableModelListener(TableModelListener l) {
		getListenerList().remove(TableModelListener.class, l);
	}

	public EventListenerList getListenerList() {
		if (listenerList == null) {
			synchronized (this) {
				if (listenerList == null) {
					listenerList = new EventListenerList();
				}
			}
		}
		return listenerList;
	}

	public long getRowCount64() {
		return model.getRowCount64();
	}

	public long getColumnCount64() {
		return 1;
	}

	public String getColumnName(long columnIndex) {
		return null;
	}

	public Class<?> getColumnClass(long columnIndex) {
		return String.class;
	}

	public boolean isCellEditable(long rowIndex, long columnIndex) {
		return !model.getMatrix().isReadOnly();
	}

	public Object getValueAt(long rowIndex, long columnIndex) {
		return model.getMatrix().getRowLabel(rowIndex);
	}

	public void setValueAt(Object aValue, long rowIndex, long columnIndex) {
		model.getMatrix().setRowLabel(rowIndex, String.valueOf(aValue));
	}

	public void addTableModelListener(TableModelListener64 l) {
		getListenerList().add(TableModelListener64.class, l);
	}

	public void removeTableModelListener(TableModelListener64 l) {
		getListenerList().remove(TableModelListener64.class, l);
	}

	public void tableChanged(TableModelEvent e) {
		fireValueChanged();
	}

	public void tableChanged(TableModelEvent64 e) {
		fireValueChanged();
	}

	public final void fireValueChanged() {
		for (final Object o : getListenerList().getListenerList()) {
			if (o instanceof TableModelListener64) {
				((TableModelListener64) o).tableChanged(new TableModelEvent64(this));
			} else if (o instanceof TableModelListener) {
				((TableModelListener) o).tableChanged(new TableModelEvent(this));
			}
		}
	}

	public final void fireValueChanged(final long row, final long column, final Object value) {
		for (final Object o : getListenerList().getListenerList()) {
			if (o instanceof TableModelListener64) {
				((TableModelListener64) o).tableChanged(new TableModelEvent64(this, row, row, column,
						TableModelEvent64.UPDATE));
			} else if (o instanceof TableModelListener) {
				((TableModelListener) o).tableChanged(new TableModelEvent(this, (int) row, (int) row, (int) column,
						TableModelEvent.UPDATE));
			}
		}
	}

}
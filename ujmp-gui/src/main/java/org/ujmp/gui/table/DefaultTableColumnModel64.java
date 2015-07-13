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

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import org.ujmp.core.util.MathUtil;

public class DefaultTableColumnModel64 extends DefaultTableColumnModel implements TableColumnModel64 {
	private static final long serialVersionUID = 6012896374236252508L;

	private final TableModel64 tableModel64;

	private int defaultColumnWidth = -1;

	private final Map<Long, Integer> columnWidths = new TreeMap<Long, Integer>();

	public DefaultTableColumnModel64(TableModel64 m) {
		this.tableModel64 = m;
	}

	public int getColumnWidth(long column) {
		Integer width = columnWidths.get(column);
		if (width == null) {
			return getDefaultColumnWidth();
		} else {
			return width;
		}
	}

	public int getDefaultColumnWidth() {
		if (defaultColumnWidth == -1) {
			if (tableModel64.getColumnCount() < 10) {
				return MatrixTable64.LARGECOLUMNWIDTH;
			} else {
				return MatrixTable64.SMALLCOLUMNWIDTH;
			}
		} else {
			return defaultColumnWidth;
		}
	}

	public void setDefaultColumnWidth(int width) {
		this.defaultColumnWidth = width;
	}

	public TableColumn64 getColumn(int columnIndex) {
		TableColumn64 tableColumn = new TableColumn64(this, columnIndex);
		return tableColumn;
	}

	public ListSelectionModel64 getSelectionModel() {
		if (selectionModel instanceof ListSelectionModel64) {
			return (ListSelectionModel64) selectionModel;
		} else {
			return null;
		}
	}

	public void addColumnModelListener(TableColumnModelListener x) {
		if (x instanceof TableColumnModelListener64) {
			super.addColumnModelListener(x);
		} else {
			throw new IllegalArgumentException("use TableColumnModelListener64");
		}
	}

	public void removeColumnModelListener(TableColumnModelListener x) {
		if (x instanceof TableColumnModelListener64) {
			super.removeColumnModelListener(x);
		} else {
			throw new IllegalArgumentException("use TableColumnModelListener64");
		}
	}

	public long getColumnCount64() {
		return tableModel64.getColumnCount64();
	}

	public int getColumnCount() {
		return MathUtil.longToInt(getColumnCount64());
	}

	public void addColumn(TableColumn64 aColumn) {
		throw new UnsupportedOperationException("not implemented");
	}

	public void addColumn(TableColumn aColumn) {
		throw new UnsupportedOperationException("not implemented");
	}

	public void removeColumn(TableColumn64 column) {
		throw new UnsupportedOperationException("not implemented");
	}

	public void moveColumn(long columnIndex, long newIndex) {
		throw new UnsupportedOperationException("not implemented");
	}

	public Enumeration<TableColumn64> getColumns64() {
		throw new UnsupportedOperationException("not implemented");
	}

	public Enumeration<TableColumn> getColumns() {
		return new ConstantTableColumnEnumeration(this, tableModel64);
	}

	public long getColumnIndex64(Object columnIdentifier) {
		throw new UnsupportedOperationException("not implemented");
	}

	public TableColumn64 getColumn(long columnIndex) {
		throw new UnsupportedOperationException("not implemented");
	}

	public long[] getSelectedColumns64() {
		throw new UnsupportedOperationException("not implemented");
	}

	public long getSelectedColumnCount64() {
		throw new UnsupportedOperationException("not implemented");
	}

	public void setSelectionModel(ListSelectionModel64 newModel) {
		super.setSelectionModel(newModel);
	}

	public void setSelectionModel(ListSelectionModel newModel) {
		if (newModel instanceof ListSelectionModel64) {
			setSelectionModel((ListSelectionModel64) newModel);
		} else {
			throw new IllegalArgumentException("use ListSelectionModel64");
		}
	}

	public void addColumnModelListener(TableColumnModelListener64 x) {
		throw new UnsupportedOperationException("not implemented");
	}

	public void removeColumnModelListener(TableColumnModelListener64 x) {
		throw new UnsupportedOperationException("not implemented");
	}

	protected ListSelectionModel64 createSelectionModel() {
		return new FastListSelectionModel64();
	}

	public void removeColumn(TableColumn column) {
		if (column instanceof TableColumn64) {
			removeColumn((TableColumn64) column);
		} else {
			throw new IllegalArgumentException("use TableColumn64");
		}
	}

	public void moveColumn(int columnIndex, int newIndex) {
		moveColumn((long) columnIndex, (long) newIndex);
	}

	public void setColumnMargin(int newMargin) {
		if (newMargin != columnMargin) {
			columnMargin = newMargin;
			fireColumnMarginChanged();
		}
	}

	protected void fireColumnMarginChanged() {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TableColumnModelListener64.class) {

				if (changeEvent == null) {
					changeEvent = new ChangeEvent(this);
				}
				((TableColumnModelListener64) listeners[i + 1]).columnMarginChanged(changeEvent);
			} else if (listeners[i] == TableColumnModelListener.class) {

				if (changeEvent == null) {
					changeEvent = new ChangeEvent(this);
				}
				((TableColumnModelListener) listeners[i + 1]).columnMarginChanged(changeEvent);
			}
		}
	}

	public int getColumnIndex(Object columnIdentifier) {
		return MathUtil.longToInt(getColumnIndex64(columnIdentifier));
	}

	public int getColumnIndexAtX(int xPosition) {
		return MathUtil.longToInt(getColumnIndexAtX((long) xPosition));
	}

	public long getColumnIndexAtX(long xPosition) {
		if (xPosition < 0 || xPosition > getTotalColumnWidth()) {
			return -1;
		} else {
			long lastColumn = 0;
			long lastPosition = 0;
			for (Long column : columnWidths.keySet()) {
				long distance = (column - lastColumn) * getDefaultColumnWidth();
				long newPosition = lastPosition + distance;
				if (xPosition <= newPosition) {
					return lastColumn + (xPosition - lastPosition) / getDefaultColumnWidth();
				}
				lastPosition = newPosition;

				distance = columnWidths.get(column);
				newPosition = lastPosition + distance;
				if (xPosition <= newPosition) {
					return column;
				}
				lastPosition = newPosition;
				lastColumn = column + 1;
			}
			return lastColumn + (xPosition - lastPosition) / getDefaultColumnWidth();
		}
	}

	public int getTotalColumnWidth() {
		if (totalColumnWidth == -1) {
			if (getColumnCount64() == -1) {
				return 0;
			} else {
				recalcWidthCache();
			}
		}
		return totalColumnWidth;
	}

	protected void recalcWidthCache() {
		long width = (getColumnCount64() - columnWidths.size()) * getDefaultColumnWidth();
		for (Long column : columnWidths.keySet()) {
			width += columnWidths.get(column);
		}
		totalColumnWidth = MathUtil.longToInt(width);
	}

	public void setColumnSelectionAllowed(boolean flag) {
		columnSelectionAllowed = flag;
	}

	public boolean getColumnSelectionAllowed() {
		return columnSelectionAllowed;
	}

	private void invalidateWidthCache() {
		totalColumnWidth = -1;
	}

	public void setColumnWidth(long index, int width) {
		invalidateWidthCache();
		columnWidths.put(index, width);
		fireColumnMarginChanged();
	}

}

class ConstantTableColumnEnumeration implements Enumeration<TableColumn> {

	private final TableColumn64 tableColumn64;
	private final TableModel64 tableModel64;
	private long index = 0;

	public ConstantTableColumnEnumeration(TableColumnModel64 tableColumnModel, TableModel64 tableModel64) {
		this.tableModel64 = tableModel64;
		this.tableColumn64 = new TableColumn64(tableColumnModel, 0);
	}

	public boolean hasMoreElements() {
		return index < tableModel64.getColumnCount();
	}

	public TableColumn nextElement() {
		tableColumn64.setModelIndex(index++);
		return tableColumn64;
	}
}

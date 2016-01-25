/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

package org.ujmp.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.collections.list.ArrayIndexList;
import org.ujmp.core.collections.map.SoftHashMap;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.util.ColorUtil;
import org.ujmp.core.util.MathUtil;
import org.ujmp.gui.table.TableModelEvent64;
import org.ujmp.gui.table.TableModelListener64;
import org.ujmp.gui.util.DataItem;
import org.ujmp.gui.util.LoadDataTask;
import org.ujmp.gui.util.UpdateIconTimerTask;

public class DefaultMatrixGUIObject extends AbstractMatrixGUIObject {
	private static final long serialVersionUID = -7974044446109857973L;

	protected final Map<Coordinates, DataItem> dataCache = Collections
			.synchronizedMap(new SoftHashMap<Coordinates, DataItem>());
	protected final List<Coordinates> todo = Collections.synchronizedList(new ArrayIndexList<Coordinates>());

	protected volatile long rowCount = -1;
	protected volatile long columnCount = -1;

	protected volatile boolean iconUpToDate = false;
	protected volatile boolean rowCountUpToDate = false;
	protected volatile boolean columnCountUpToDate = false;

	protected Image icon = null;

	public DefaultMatrixGUIObject(Matrix matrix) {
		super(matrix);
		UpdateIconTimerTask.getInstance().add(this);
		LoadDataTask.getInstance().add(this);
	}

	public long getRowCount64() {
		return rowCount;
	}

	public long getColumnCount64() {
		return columnCount;
	}

	public void fireValueChanged(Coordinates coordinates, Object value) {
		iconUpToDate = false;
		dataCache.put(coordinates, new DataItem(value, ColorUtil.fromObject(value)));
		for (final Object o : getListenerList().getListenerList()) {
			if (o instanceof TableModelListener64) {
				((TableModelListener64) o).tableChanged(new TableModelEvent64(this, coordinates.getRow(), coordinates
						.getRow(), coordinates.getColumn(), TableModelEvent64.UPDATE));
			} else if (o instanceof TableModelListener) {
				((TableModelListener) o).tableChanged(new TableModelEvent(this,
						MathUtil.longToInt(coordinates.getRow()), MathUtil.longToInt(coordinates.getRow()), MathUtil
								.longToInt(coordinates.getColumn()), TableModelEvent.UPDATE));
			}
		}
		super.fireValueChanged();
	}

	public void fireValueChanged(Coordinates start, Coordinates end) {
		System.out.println("fireValueChanged start, end");
	}

	public final void fireValueChanged() {
		iconUpToDate = false;
		rowCountUpToDate = false;
		columnCountUpToDate = false;
		dataCache.clear();
		for (final Object o : getListenerList().getListenerList()) {
			if (o instanceof TableModelListener64) {
				((TableModelListener64) o).tableChanged(new TableModelEvent64(this));
			} else if (o instanceof TableModelListener) {
				((TableModelListener) o).tableChanged(new TableModelEvent(this));
			}
		}
		super.fireValueChanged();
	}

	public final void updateUI() {
		super.fireValueChanged();
	}

	public final void fireValueChanged(final long row, final long column, final Object value) {
		fireValueChanged(Coordinates.wrap(row, column), value);
	}

	public synchronized Object getValueAt(final int rowIndex, final int columnIndex) {
		return getValueAt((long) rowIndex, (long) columnIndex);
	}

	public synchronized Object getValueAt(long rowIndex, long columnIndex) {
		Coordinates coordinates = Coordinates.wrap(rowIndex, columnIndex);
		DataItem dataItem = dataCache.get(coordinates);
		if (dataItem != null) {
			return dataItem == null ? null : dataItem.getObject();
		} else {
			if (!todo.contains(coordinates)) {
				todo.add(coordinates);
			}
		}
		return GUIObject.PRELOADER;
	}

	public synchronized Color getColorAt(long rowIndex, long columnIndex) {
		Coordinates coordinates = Coordinates.wrap(rowIndex, columnIndex);
		DataItem dataItem = dataCache.get(coordinates);
		if (dataItem != null) {
			return dataItem == null ? null : dataItem.getColor();
		} else {
			if (!todo.contains(coordinates)) {
				todo.add(coordinates);
			}
		}
		return Color.LIGHT_GRAY;
	}

	public final synchronized void clear() {
		matrix.clear();
		fireValueChanged();
	}

	public final String getLabel() {
		return matrix.getLabel();
	}

	public final void setLabel(final Object label) {
		matrix.setLabel(label);
	}

	public final Object getLabelObject() {
		return matrix.getLabelObject();
	}

	public final String getColumnName(final long columnIndex) {
		return matrix.getColumnLabel(columnIndex);
	}

	public final String getColumnName(final int columnIndex) {
		return getColumnName((long) columnIndex);
	}

	public final synchronized void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
		setValueAt(aValue, (long) rowIndex, (long) columnIndex);
	}

	public final synchronized void setValueAt(final Object aValue, final long rowIndex, final long columnIndex) {
		matrix.setAsObject(aValue, rowIndex, columnIndex);
		fireValueChanged(rowIndex, columnIndex, aValue);
	}

	public Image getIcon() {
		return icon;
	}

	public final String getDescription() {
		return matrix.getLabel();
	}

	public final void setDescription(final String description) {
		matrix.setLabel(description);
	}

	public final String toString() {
		if (matrix.getLabel() != null) {
			return Coordinates.toString("[", "x", "]", matrix.getSize()) + matrix.getClass().getSimpleName() + " ["
					+ matrix.getLabel() + "]";
		} else {
			return Coordinates.toString("[", "x", "]", matrix.getSize()) + matrix.getClass().getSimpleName();
		}
	}

	public boolean isIconUpToDate() {
		return iconUpToDate;
	}

	public void setIconUpToDate(boolean b) {
		this.iconUpToDate = b;
	}

	public void setIcon(BufferedImage image) {
		this.icon = image;
	}

	public boolean isColumnCountUpToDate() {
		return columnCountUpToDate;
	}

	public void setColumnCount(long columnCount) {
		this.columnCount = columnCount;
	}

	public void setColumnCountUpToDate(boolean b) {
		this.columnCountUpToDate = b;
	}

	public boolean isRowCountUpToDate() {
		return rowCountUpToDate;
	}

	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}

	public void setRowCountUpToDate(boolean b) {
		this.rowCountUpToDate = b;
	}

	public List<Coordinates> getTodo() {
		return todo;
	}

	public Map<Coordinates, DataItem> getDataCache() {
		return dataCache;
	}

}

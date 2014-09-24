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

package org.ujmp.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.collections.list.ArrayIndexList;
import org.ujmp.core.collections.map.SoftHashMap;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.UJMPTimer;
import org.ujmp.gui.renderer.MatrixHeatmapRenderer;
import org.ujmp.gui.table.TableModelEvent64;
import org.ujmp.gui.table.TableModelListener64;
import org.ujmp.gui.util.ColorUtil;

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

	private final UpdateIconTimerTask updateIconTimerTask;
	private final LoadDataTask loadDataTask;

	protected Image icon = null;

	public DefaultMatrixGUIObject(Matrix matrix) {
		super(matrix);
		updateIconTimerTask = new UpdateIconTimerTask(this);
		loadDataTask = new LoadDataTask(this);
		UJMPTimer.newInstance().schedule(loadDataTask, 50, 50);
		UJMPTimer.newInstance().schedule(updateIconTimerTask, 300, 300);
	}

	public long getRowCount64() {
		return rowCount;
	}

	public long getColumnCount64() {
		return columnCount;
	}

	public final void fireValueChanged() {
		iconUpToDate = false;
		for (final Object o : getListenerList().getListenerList()) {
			if (o instanceof TableModelListener64) {
				((TableModelListener64) o).tableChanged(new TableModelEvent64(this));
			} else if (o instanceof TableModelListener) {
				((TableModelListener) o).tableChanged(new TableModelEvent(this));
			}
		}
		super.fireValueChanged();
	}

	public final void fireValueChanged(final long row, final long column, final Object value) {
		iconUpToDate = false;
		dataCache.put(Coordinates.wrap(row, column), new DataItem(value, ColorUtil.fromObject(value)));
		for (final Object o : getListenerList().getListenerList()) {
			if (o instanceof TableModelListener64) {
				((TableModelListener64) o).tableChanged(new TableModelEvent64(this, row, row, column,
						TableModelEvent64.UPDATE));
			} else if (o instanceof TableModelListener) {
				((TableModelListener) o).tableChanged(new TableModelEvent(this, MathUtil.longToInt(row), MathUtil
						.longToInt(row), MathUtil.longToInt(column), TableModelEvent.UPDATE));
			}
		}
		super.fireValueChanged();
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

}

class DataItem {
	private final Object object;
	private final Color color;

	public DataItem(Object object, Color color) {
		this.object = object;
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public Object getObject() {
		return object;
	}
}

class UpdateIconTimerTask extends TimerTask {
	private final DefaultMatrixGUIObject matrixGuiObject;

	public UpdateIconTimerTask(DefaultMatrixGUIObject matrixGuiObject) {
		this.matrixGuiObject = matrixGuiObject;
	}

	@Override
	public void run() {
		if (!matrixGuiObject.iconUpToDate) {
			matrixGuiObject.iconUpToDate = true;
			BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
			MatrixHeatmapRenderer.paintMatrix(image.getGraphics(), matrixGuiObject, 16, 16, 0, 0);
			matrixGuiObject.icon = image;
		}
	}
}

class LoadDataTask extends TimerTask {
	private final DefaultMatrixGUIObject matrixGUIObject;

	public LoadDataTask(DefaultMatrixGUIObject matrixGUIObject) {
		this.matrixGUIObject = matrixGUIObject;
	}

	@Override
	public void run() {
		if (!matrixGUIObject.columnCountUpToDate) {
			matrixGUIObject.columnCount = matrixGUIObject.getMatrix().getColumnCount();
			matrixGUIObject.columnCountUpToDate = true;
			matrixGUIObject.fireValueChanged(TableModelEvent.HEADER_ROW, TableModelEvent.ALL_COLUMNS, null);
		}

		if (!matrixGUIObject.rowCountUpToDate) {
			matrixGUIObject.rowCount = matrixGUIObject.getMatrix().getRowCount();
			matrixGUIObject.rowCountUpToDate = true;
			matrixGUIObject.fireValueChanged(TableModelEvent.HEADER_ROW, TableModelEvent.ALL_COLUMNS, null);
		}

		if (matrixGUIObject.rowCountUpToDate && matrixGUIObject.columnCountUpToDate && !matrixGUIObject.todo.isEmpty()) {
			long t0 = System.currentTimeMillis();
			while (matrixGUIObject.rowCountUpToDate && matrixGUIObject.columnCountUpToDate
					&& !matrixGUIObject.todo.isEmpty() && System.currentTimeMillis() - t0 < 300) {
				Coordinates coordinates = matrixGUIObject.todo.remove(matrixGUIObject.todo.size() - 1);
				Object object = matrixGUIObject.getMatrix().getAsObject(coordinates.getLongCoordinates());
				matrixGUIObject.dataCache.put(coordinates, new DataItem(object, ColorUtil.fromObject(object)));
			}
			matrixGUIObject.fireValueChanged();
		}
	}
}
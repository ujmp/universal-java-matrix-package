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
import java.util.Deque;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedDeque;

import javax.swing.event.TableModelEvent;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.collections.map.SoftHashMap;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.util.UJMPTimer;
import org.ujmp.gui.renderer.MatrixHeatmapRenderer;
import org.ujmp.gui.util.ColorUtil;

public class DefaultMatrixGUIObject extends AbstractMatrixGUIObject {
	private static final long serialVersionUID = -7974044446109857973L;

	protected final Map<Coordinates, DataItem> dataCache = new SoftHashMap<Coordinates, DataItem>();
	protected final Deque<Coordinates> todo;

	protected volatile long rowCount = -1;
	protected volatile long columnCount = -1;

	private final LoadDataThread loadDataThread;
	private final UpdateIconTimerTask updateIconTimerTask;

	protected Image matrixIcon = null;

	public DefaultMatrixGUIObject(Matrix matrix) {
		super(matrix);
		todo = new ConcurrentLinkedDeque<Coordinates>();
		loadDataThread = new LoadDataThread(this);
		loadDataThread.start();
		updateIconTimerTask = new UpdateIconTimerTask(this);
		UJMPTimer.newInstance().schedule(updateIconTimerTask, 5000, 5000);
	}

	public long getRowCount64() {
		return rowCount;
	}

	public long getColumnCount64() {
		return columnCount;
	}

	public Object getValueAt(final int rowIndex, final int columnIndex) {
		return getValueAt((long) rowIndex, (long) columnIndex);
	}

	public synchronized Object getValueAt(long rowIndex, long columnIndex) {
		Coordinates coordinates = Coordinates.wrap(rowIndex, columnIndex);
		if (dataCache.containsKey(coordinates)) {
			DataItem dataItem = dataCache.get(coordinates);
			return dataItem == null ? null : dataItem.getObject();
		} else {
			if (!todo.contains(coordinates)) {
				todo.addFirst(coordinates);
			}
		}
		return GUIObject.PRELOADER;
	}

	public synchronized Color getColorAt(long rowIndex, long columnIndex) {
		Coordinates coordinates = Coordinates.wrap(rowIndex, columnIndex);
		if (dataCache.containsKey(coordinates)) {
			DataItem dataItem = dataCache.get(coordinates);
			return dataItem == null ? null : dataItem.getColor();
		} else {
			if (!todo.contains(coordinates)) {
				todo.addFirst(coordinates);
			}
		}
		return Color.LIGHT_GRAY;
	}

	public final void clear() {
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

	public final void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
		setValueAt(aValue, (long) rowIndex, (long) columnIndex);
	}

	public final void setValueAt(final Object aValue, final long rowIndex, final long columnIndex) {
		matrix.setAsObject(aValue, rowIndex, columnIndex);
		fireValueChanged(rowIndex, columnIndex, aValue);
	}

	public Image getIcon() {
		return matrixIcon;
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

class LoadDataThread extends Thread {
	public LoadDataThread(DefaultMatrixGUIObject realTimeMatrixGUIObject) {
		super(new LoadDataRunnable(realTimeMatrixGUIObject));
		this.setDaemon(true);
	}
}

class LoadDataRunnable implements Runnable {

	private final Deque<Coordinates> todo;
	private final DefaultMatrixGUIObject matrixGUIObject;
	private Map<Coordinates, DataItem> dataCache;
	private Matrix matrix;

	public LoadDataRunnable(DefaultMatrixGUIObject matrixGUIObject) {
		this.matrixGUIObject = matrixGUIObject;
		this.todo = matrixGUIObject.todo;
		this.dataCache = matrixGUIObject.dataCache;
		this.matrix = matrixGUIObject.matrix;
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (matrixGUIObject.columnCount == -1) {
				matrixGUIObject.columnCount = matrix.getColumnCount();
				matrixGUIObject.fireValueChanged(TableModelEvent.HEADER_ROW, TableModelEvent.ALL_COLUMNS, null);
			}

			if (matrixGUIObject.rowCount == -1) {
				matrixGUIObject.rowCount = matrix.getRowCount();
				matrixGUIObject.fireValueChanged(TableModelEvent.HEADER_ROW, TableModelEvent.ALL_COLUMNS, null);
			}

			while (matrixGUIObject.rowCount != -1 && matrixGUIObject.columnCount != -1 && !todo.isEmpty()) {
				Coordinates coordinates = todo.pollFirst();
				Object object = matrix.getAsObject(coordinates.getLongCoordinates());
				dataCache.put(coordinates, new DataItem(object, ColorUtil.fromObject(object)));
				matrixGUIObject.fireValueChanged(coordinates.getRow(), coordinates.getColumn(), object);
			}
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
		BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
		MatrixHeatmapRenderer.paintMatrix(image.getGraphics(), matrixGuiObject, 16, 16, 0, 0);
		matrixGuiObject.matrixIcon = image;
	}
}

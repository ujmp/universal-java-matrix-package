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

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.event.TableModelEvent;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.collections.map.SoftHashMap;
import org.ujmp.core.interfaces.GUIObject;

public class RealTimeMatrixGUIObject extends AbstractMatrixGUIObject {
	private static final long serialVersionUID = -7974044446109857973L;

	protected final Map<Coordinates, Object> dataCache = new SoftHashMap<Coordinates, Object>();

	protected final Queue<Coordinates> todo;

	protected volatile long rowCount = -1;
	protected volatile long columnCount = -1;

	private final LoadDataThread loadDataThread;

	public RealTimeMatrixGUIObject(Matrix matrix) {
		super(matrix);
		todo = new ConcurrentLinkedQueue<Coordinates>();
		loadDataThread = new LoadDataThread(this);
		loadDataThread.start();
	}

	public long getRowCount64() {
		return rowCount;
	}

	public long getColumnCount64() {
		return columnCount;
	}

	public String getColumnName(int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public synchronized Object getValueAt(final int rowIndex, final int columnIndex) {
		Coordinates coordinates = Coordinates.wrap(rowIndex, columnIndex);
		Object object = dataCache.get(coordinates);
		if (object != null) {
			return object;
		} else {
			// reinsert to change priority
			todo.remove(coordinates);
			todo.add(coordinates);
		}
		return GUIObject.PRELOADER;
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub

	}

	public void clear() {
		// TODO Auto-generated method stub

	}

	public String getLabel() {
		return null;
	}

	public void setLabel(Object label) {
		// TODO Auto-generated method stub

	}

	public Object getLabelObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDescription(String description) {
		// TODO Auto-generated method stub

	}

	public String getColumnName(long columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getValueAt(long rowIndex, long columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setValueAt(Object aValue, long rowIndex, long columnIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}

class LoadDataThread extends Thread {
	public LoadDataThread(RealTimeMatrixGUIObject realTimeMatrixGUIObject) {
		super(new LoadDataRunnable(realTimeMatrixGUIObject));
	}
}

class LoadDataRunnable implements Runnable {

	private final Queue<Coordinates> todo;
	private final RealTimeMatrixGUIObject matrixGUIObject;
	private Map<Coordinates, Object> dataCache;
	private Matrix matrix;

	public LoadDataRunnable(RealTimeMatrixGUIObject realTimeMatrixGUIObject) {
		this.matrixGUIObject = realTimeMatrixGUIObject;
		this.todo = realTimeMatrixGUIObject.todo;
		this.dataCache = realTimeMatrixGUIObject.dataCache;
		this.matrix = realTimeMatrixGUIObject.matrix;
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
				Coordinates coordinates = todo.poll();
				Object object = matrix.getAsObject(coordinates.getLongCoordinates());
				dataCache.put(coordinates, object);
				matrixGUIObject.fireValueChanged(coordinates.getRow(), coordinates.getColumn(), object);
			}

		}
	}

}

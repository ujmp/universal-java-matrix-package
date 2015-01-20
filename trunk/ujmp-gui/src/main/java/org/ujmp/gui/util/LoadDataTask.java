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

package org.ujmp.gui.util;

import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

import javax.swing.event.TableModelEvent;

import org.ujmp.core.Coordinates;
import org.ujmp.core.util.ColorUtil;
import org.ujmp.core.util.UJMPTimer;
import org.ujmp.gui.DefaultMatrixGUIObject;

public class LoadDataTask extends TimerTask {
	private static LoadDataTask instance = null;
	private static final Object lock = new Object();

	private List<DefaultMatrixGUIObject> list = new LinkedList<DefaultMatrixGUIObject>();

	public static final LoadDataTask getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new LoadDataTask();
				}
			}
		}
		return instance;
	}

	private LoadDataTask() {
		UJMPTimer.newInstance("LoadData").schedule(this, 50, 50);
	}

	public void add(DefaultMatrixGUIObject matrixGUIObject) {
		list.add(matrixGUIObject);
	}

	@Override
	public void run() {
		try {
			for (DefaultMatrixGUIObject matrixGUIObject : list) {

				try {
					if (!matrixGUIObject.isColumnCountUpToDate()) {
						matrixGUIObject.setColumnCount(matrixGUIObject.getMatrix().getColumnCount());
						matrixGUIObject.setColumnCountUpToDate(true);
						matrixGUIObject.fireValueChanged(TableModelEvent.HEADER_ROW, TableModelEvent.ALL_COLUMNS, null);
					}
				} catch (Exception e) {
					e.printStackTrace();
					matrixGUIObject.setColumnCount(0);
					matrixGUIObject.setColumnCountUpToDate(true);
					matrixGUIObject.fireValueChanged(TableModelEvent.HEADER_ROW, TableModelEvent.ALL_COLUMNS, null);
					// continue for other objects
				}

				try {
					if (!matrixGUIObject.isRowCountUpToDate()) {
						matrixGUIObject.setRowCount(matrixGUIObject.getMatrix().getRowCount());
						matrixGUIObject.setRowCountUpToDate(true);
						matrixGUIObject.fireValueChanged(TableModelEvent.HEADER_ROW, TableModelEvent.ALL_COLUMNS, null);
					}
				} catch (Exception e) {
					e.printStackTrace();
					matrixGUIObject.setRowCount(0);
					matrixGUIObject.setRowCountUpToDate(true);
					matrixGUIObject.fireValueChanged(TableModelEvent.HEADER_ROW, TableModelEvent.ALL_COLUMNS, null);
					// continue for other objects
				}

				try {
					if (matrixGUIObject.isRowCountUpToDate() && matrixGUIObject.isColumnCountUpToDate()
							&& !matrixGUIObject.getTodo().isEmpty()) {
						long t0 = System.currentTimeMillis();
						while (matrixGUIObject.isRowCountUpToDate() && matrixGUIObject.isColumnCountUpToDate()
								&& !matrixGUIObject.getTodo().isEmpty() && System.currentTimeMillis() - t0 < 300) {
							Coordinates coordinates = matrixGUIObject.getTodo().remove(
									matrixGUIObject.getTodo().size() - 1);
							Object object = matrixGUIObject.getMatrix().getAsObject(coordinates.getLongCoordinates());
							matrixGUIObject.getDataCache().put(coordinates,
									new DataItem(object, ColorUtil.fromObject(object)));
						}
						matrixGUIObject.fireValueChanged();
					}
				} catch (Exception e) {
					e.printStackTrace();
					// continue for other objects
				}

			}
		} catch (ConcurrentModificationException e) {
			// no problem, retry later
		}
	}
}

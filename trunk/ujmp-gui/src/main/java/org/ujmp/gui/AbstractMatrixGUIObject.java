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

import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.CoreObject;
import org.ujmp.core.util.MathUtil;
import org.ujmp.gui.frame.MatrixFrame;
import org.ujmp.gui.panels.MatrixPanel;
import org.ujmp.gui.table.FastListSelectionModel64;
import org.ujmp.gui.table.TableModelEvent64;
import org.ujmp.gui.table.TableModelListener64;

public abstract class AbstractMatrixGUIObject extends AbstractGUIObject implements MatrixGUIObject {
	private static final long serialVersionUID = 8579443899197329157L;

	private transient EventListenerList listenerList = null;

	private transient FastListSelectionModel64 rowSelectionModel = null;
	private transient FastListSelectionModel64 columnSelectionModel = null;

	private transient JFrame frame = null;
	private transient JPanel panel = null;

	protected final Matrix matrix;

	private long[] mouseOverCoordinates = Coordinates.ZERO2D;

	public AbstractMatrixGUIObject(Matrix matrix) {
		this.matrix = matrix;
	}

	public final EventListenerList getListenerList() {
		if (listenerList == null) {
			synchronized (this) {
				if (listenerList == null) {
					listenerList = new EventListenerList();
				}
			}
		}
		return listenerList;
	}

	public final void addTableModelListener(final TableModelListener64 l) {
		getListenerList().add(TableModelListener64.class, l);
	}

	public final void removeTableModelListener(final TableModelListener64 l) {
		getListenerList().remove(TableModelListener64.class, l);
	}

	public final void addTableModelListener(final TableModelListener l) {
		getListenerList().add(TableModelListener.class, l);
	}

	public final void removeTableModelListener(final TableModelListener l) {
		getListenerList().remove(TableModelListener.class, l);
	}

	

	public final FastListSelectionModel64 getColumnSelectionModel() {
		if (columnSelectionModel == null) {
			synchronized (this) {
				if (columnSelectionModel == null) {
					columnSelectionModel = new FastListSelectionModel64();
				}
			}
		}
		return columnSelectionModel;
	}

	public final FastListSelectionModel64 getRowSelectionModel() {
		if (rowSelectionModel == null) {
			synchronized (this) {
				if (rowSelectionModel == null) {
					rowSelectionModel = new FastListSelectionModel64();
				}
			}
		}
		return rowSelectionModel;
	}

	public final int getColumnCount() {
		return MathUtil.getInt(getColumnCount64());
	}

	public final int getRowCount() {
		return MathUtil.getInt(getRowCount64());
	}

	public final JFrame getFrame() {
		if (frame == null) {
			synchronized (this) {
				if (frame == null) {
					frame = new MatrixFrame(this);
				}
			}
		}
		return frame;
	}

	public final JPanel getPanel() {
		if (panel == null) {
			synchronized (this) {
				if (panel == null) {
					panel = new MatrixPanel(this);
				}
			}
		}
		return panel;
	}

	public Image getIcon() {
		return null;
	}

	public final boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return !matrix.isReadOnly();
	}

	public final boolean isCellEditable(final long rowIndex, final long columnIndex) {
		return !matrix.isReadOnly();
	}

	public final Matrix getMatrix() {
		return matrix;
	}

	public final Class<?> getColumnClass(final long columnIndex) {
		return Object.class;
	}

	public final Class<?> getColumnClass(final int columnIndex) {
		return Object.class;
	}

	public final CoreObject getCoreObject() {
		return matrix;
	}

	public void setMouseOverCoordinates(long... coordinates) {
		this.mouseOverCoordinates = coordinates;
	}

	public long[] getMouseOverCoordinates() {
		return mouseOverCoordinates;
	}

}

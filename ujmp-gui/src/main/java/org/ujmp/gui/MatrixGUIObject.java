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

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.gui.frame.MatrixFrame;
import org.ujmp.gui.panels.MatrixPanel;
import org.ujmp.gui.table.FastListSelectionModel64;
import org.ujmp.gui.table.TableModel64;
import org.ujmp.gui.table.TableModelEvent64;
import org.ujmp.gui.table.TableModelListener64;

public class MatrixGUIObject extends AbstractGUIObject implements TableModel64 {
	private static final long serialVersionUID = -5777110889052748093L;

	private final Matrix matrix;

	private transient FastListSelectionModel64 rowSelectionModel = null;
	private transient FastListSelectionModel64 columnSelectionModel = null;

	private transient EventListenerList listenerList = null;

	private transient JFrame frame = null;
	private transient JPanel panel = null;

	public MatrixGUIObject(Matrix m) {
		this.matrix = m;
	}

	public final Matrix getMatrix() {
		return matrix;
	}

	public final void clear() {
		matrix.clear();
		fireValueChanged();
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

	public final String getLabel() {
		return matrix.getLabel();
	}

	public final void setLabel(final String label) {
		matrix.setLabel(label);
	}

	public final Object getLabelObject() {
		return matrix.getLabelObject();
	}

	public final void setLabelObject(final Object label) {
		matrix.setLabelObject(label);
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
				((TableModelListener64) o).tableChanged(new TableModelEvent64(this, row, row, column, TableModelEvent64.UPDATE));
			} else if (o instanceof TableModelListener) {
				((TableModelListener) o).tableChanged(new TableModelEvent(this, (int) row, (int) row, (int) column, TableModelEvent.UPDATE));
			}
		}
	}

	public final Class<?> getColumnClass(final long columnIndex) {
		return Object.class;
	}

	public final Class<?> getColumnClass(final int columnIndex) {
		return getColumnClass((long) columnIndex);
	}

	public final int getColumnCount() {
		return (int) getColumnCount64();
	}

	public final long getColumnCount64() {
		return matrix.getColumnCount();
	}

	public final String getColumnName(final long columnIndex) {
		return matrix.getColumnLabel(columnIndex);
	}

	public final String getColumnName(final int columnIndex) {
		return getColumnName((long) columnIndex);
	}

	public final int getRowCount() {
		return (int) getRowCount64();
	}

	public final long getRowCount64() {
		return matrix.getRowCount();
	}

	public final Object getValueAt(final int rowIndex, final int columnIndex) {
		return getValueAt((long) rowIndex, (long) columnIndex);
	}

	public final Object getValueAt(final long rowIndex, final long columnIndex) {
		return matrix.getAsObject(rowIndex, columnIndex);
	}

	public final boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return isCellEditable((long) rowIndex, (long) columnIndex);
	}

	public final boolean isCellEditable(final long rowIndex, final long columnIndex) {
		return !matrix.isReadOnly();
	}

	public final void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
		setValueAt(aValue, (long) rowIndex, (long) columnIndex);
	}

	public final void setValueAt(final Object aValue, final long rowIndex, final long columnIndex) {
		matrix.setAsObject(aValue, rowIndex, columnIndex);
		fireValueChanged(rowIndex, columnIndex, aValue);
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

	public final Icon getIcon() {
		return null;
	}

	public final String getDescription() {
		return matrix.getLabel();
	}

	public final void setDescription(final String description) {
		matrix.setLabel(description);
	}

	public final String toString() {
		if (matrix.getLabel() != null) {
			return Coordinates.toString("[", "x", "]", matrix.getSize()) + matrix.getClass().getSimpleName() + " [" + matrix.getLabel() + "]";
		} else {
			return Coordinates.toString("[", "x", "]", matrix.getSize()) + matrix.getClass().getSimpleName();
		}
	}

	public final Matrix getCoreObject() {
		return matrix;
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

}

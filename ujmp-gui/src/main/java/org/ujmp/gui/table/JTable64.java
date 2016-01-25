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

package org.ujmp.gui.table;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.accessibility.Accessible;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.Scrollable;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.event.TableModelEvent;
import javax.swing.plaf.TableUI;

public class JTable64 extends JTable implements TableModelListener64, Scrollable, TableColumnModelListener64,
		ListSelectionListener64, CellEditorListener, Accessible, RowSorterListener {
	private static final long serialVersionUID = -4778128053560013569L;

	public JTable64(TableModel64 dataModel) {
		this(dataModel, null, null);
	}

	public JTable64(TableModel64 dataModel, TableColumnModel64 tableColumnModel, ListSelectionModel64 rowSelectionModel) {
		super(dataModel, tableColumnModel == null ? new DefaultTableColumnModel64(dataModel) : tableColumnModel,
				rowSelectionModel == null ? new FastListSelectionModel64() : rowSelectionModel);
	}

	// do not set widths from preferred sizes
	@SuppressWarnings("deprecation")
	public void doLayout() {
		layout();
	}

	public Dimension getPreferredScrollableViewportSize() {
		return new Dimension(getColumnModel64().getTotalColumnWidth(), Integer.MAX_VALUE);
	}

	public TableModel64 getModel() {
		return (TableModel64) super.getModel();
	}

	protected void initializeLocalVars() {
		setOpaque(true);
		createDefaultRenderers();
		createDefaultEditors();

		setTableHeader(createDefaultTableHeader64());

		setShowGrid(true);
		setAutoResizeMode(AUTO_RESIZE_OFF);
		setRowHeight(16);
		setRowMargin(1);
		setRowSelectionAllowed(true);
		setCellEditor(null);
		setEditingColumn(-1);
		setEditingRow(-1);
		setSurrendersFocusOnKeystroke(false);
		// setPreferredScrollableViewportSize(new Dimension(450, 400));

		// I'm registered to do tool tips so we can draw tips for the renderers
		ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
		toolTipManager.registerComponent(this);

		setAutoscrolls(true);
	}

	protected JTableHeader64 createDefaultTableHeader64() {
		return new JTableHeader64((TableColumnModel64) columnModel);
	}

	public ListSelectionModel64 getSelectionModel() {
		if (selectionModel instanceof ListSelectionModel64) {
			return (ListSelectionModel64) selectionModel;
		} else {
			throw new RuntimeException("not a ListSelectionModel64");
		}
	}

	public void setSelectionModel(ListSelectionModel newModel) {
		if (newModel instanceof ListSelectionModel64) {
			super.setSelectionModel(newModel);
		} else {
			throw new IllegalArgumentException("needs ListSelectionModel64");
		}
	}

	public void setSelectionModel(ListSelectionModel64 newModel) {
		if (newModel == null) {
			throw new IllegalArgumentException("Cannot set a null SelectionModel");
		}
		if (!(newModel instanceof ListSelectionModel64)) {
			throw new IllegalArgumentException("needs ListSelectionModel64");
		}

		ListSelectionModel64 oldModel = (ListSelectionModel64) selectionModel;

		if (newModel != oldModel) {
			if (oldModel != null) {
				oldModel.removeListSelectionListener(this);
			}

			selectionModel = newModel;
			newModel.addListSelectionListener(this);

			firePropertyChange("selectionModel", oldModel, newModel);
			repaint();
		}
	}

	public void valueChanged(ListSelectionEvent64 e) {
		super.valueChanged((ListSelectionEvent) e);
	}

	public void columnAdded(TableColumnModelEvent64 e) {
		throw new UnsupportedOperationException("not implemented");
	}

	public void columnRemoved(TableColumnModelEvent64 e) {
		throw new UnsupportedOperationException("not implemented");
	}

	public void columnMoved(TableColumnModelEvent64 e) {
		throw new UnsupportedOperationException("not implemented");
	}

	public void columnSelectionChanged(ListSelectionEvent64 e) {
		super.columnSelectionChanged(e);
	}

	public void tableChanged(TableModelEvent64 e) {
		super.tableChanged((TableModelEvent) e);
	}

	// must be override, otherwise it will iterate over all columns
	public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
		Rectangle r = new Rectangle();
		boolean valid = true;
		if (row < 0) {
			// y = height = 0;
			valid = false;
		} else if (row >= getRowCount()) {
			r.y = getHeight();
			valid = false;
		} else {
			r.height = getRowHeight(row);
			// r.y = (getRowModel() == null) ? row * r.height :
			// getRowModel().getPosition(row);
			r.y = row * r.height;
		}

		if (column < 0) {
			if (!getComponentOrientation().isLeftToRight()) {
				r.x = getWidth();
			}
			// otherwise, x = width = 0;
			valid = false;
		} else if (column >= getColumnCount()) {
			if (getComponentOrientation().isLeftToRight()) {
				r.x = getWidth();
			}
			// otherwise, x = width = 0;
			valid = false;
		} else {
			TableColumnModel64 cm = getColumnModel64();
			if (getComponentOrientation().isLeftToRight()) {
				for (int i = 0; i < column; i++) {
					r.x += cm.getColumnWidth(i);
				}
			} else {
				for (int i = cm.getColumnCount() - 1; i > column; i--) {
					r.x += cm.getColumnWidth(i);
				}
			}
			r.width = cm.getColumnWidth(column);
		}

		if (valid && !includeSpacing) {
			// Bound the margins by their associated dimensions to prevent
			// returning bounds with negative dimensions.
			int rm = Math.min(getRowMargin(), r.height);
			int cm = Math.min(getColumnModel().getColumnMargin(), r.width);
			// This is not the same as grow(), it rounds differently.
			r.setBounds(r.x + cm / 2, r.y + rm / 2, r.width - cm, r.height - rm);
		}
		return r;
	}

	public TableColumnModel64 getColumnModel64() {
		return (TableColumnModel64) getColumnModel();
	}

	public void updateUI() {
		// Update the UI of the table header
		if (tableHeader != null && tableHeader.getParent() == null) {
			tableHeader.updateUI();
		}

		setUI((TableUI) UIManager.getUI(this));
	}

}

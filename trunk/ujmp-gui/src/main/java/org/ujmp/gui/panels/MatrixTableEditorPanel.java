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

package org.ujmp.gui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;

import org.ujmp.core.Matrix;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.menu.MatrixPopupMenu;
import org.ujmp.gui.table.ColumnTableHeaderRenderer64;
import org.ujmp.gui.table.JTable64;
import org.ujmp.gui.table.JTableHeader64;
import org.ujmp.gui.table.ListSelectionEvent64;
import org.ujmp.gui.table.ListSelectionListener64;
import org.ujmp.gui.table.MatrixTable64;
import org.ujmp.gui.table.RowHeaderTableModel64;
import org.ujmp.gui.table.RowTableHeaderRenderer64;
import org.ujmp.gui.table.TableColumnModel64;
import org.ujmp.gui.table.TableModelEvent64;
import org.ujmp.gui.table.TableModelListener64;
import org.ujmp.gui.util.Preloader;

public class MatrixTableEditorPanel extends JPanel implements TableModelListener64, MouseListener, MouseMotionListener,
		KeyListener, ListSelectionListener64 {
	private static final long serialVersionUID = -1794955656888362574L;

	private final MatrixGUIObject dataModel;

	private final JTable64 jTable;
	private final JTable64 rowHeader;
	private final JScrollPane scrollPane;
	private final Preloader preloader = new Preloader();

	private boolean scroll = true;
	private boolean isShowPreloader = true;

	public MatrixTableEditorPanel(String title, MatrixGUIObject m) {
		dataModel = m;

		setBorder(BorderFactory.createTitledBorder(title == null ? "Matrix Editor" : title));
		setLayout(new BorderLayout());

		jTable = new MatrixTable64(m);

		rowHeader = new JTable64(new RowHeaderTableModel64(m), new SingleTableColumnModel(dataModel),
				dataModel.getRowSelectionModel());
		rowHeader.getTableHeader().setReorderingAllowed(false);
		rowHeader.setColumnSelectionAllowed(false);
		rowHeader.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		rowHeader.setPreferredScrollableViewportSize(new Dimension(
				rowHeader.getColumnModel64().getDefaultColumnWidth(), Integer.MAX_VALUE));
		rowHeader.setDefaultRenderer(Object.class, new RowTableHeaderRenderer64(jTable));

		JTableHeader64 tableHeader = new JTableHeader64((TableColumnModel64) jTable.getColumnModel());
		tableHeader.setReorderingAllowed(false);
		tableHeader.setDefaultRenderer(new ColumnTableHeaderRenderer64(jTable));
		// tableHeader.setResizingAllowed(false);
		jTable.setTableHeader(tableHeader);

		scrollPane = new JScrollPane(jTable);
		scrollPane.setRowHeaderView(rowHeader);

		this.addMouseMotionListener(this);
		jTable.addMouseMotionListener(this);
		this.addMouseListener(this);
		jTable.addMouseListener(this);
		jTable.addKeyListener(this);
		dataModel.addTableModelListener(this);
		dataModel.getRowSelectionModel().addListSelectionListener(this);
		dataModel.getColumnSelectionModel().addListSelectionListener(this);

		if (dataModel.getRowCount() < 0 || dataModel.getColumnCount() < 0) {
			isShowPreloader = true;

			this.add(preloader, BorderLayout.CENTER);
		} else {
			isShowPreloader = false;
			this.add(scrollPane, BorderLayout.CENTER);
		}
	}

	public void tableChanged(TableModelEvent64 e) {
		removePreloader();
	}

	private void removePreloader() {
		if (isShowPreloader && dataModel.getRowCount() >= 0 && dataModel.getColumnCount() >= 0) {
			isShowPreloader = false;
			this.remove(preloader);
			this.add(scrollPane, BorderLayout.CENTER);
			this.revalidate();
		}
	}

	public void tableChanged(TableModelEvent e) {
		removePreloader();
	}

	public void mouseClicked(MouseEvent e) {
		int row = jTable.rowAtPoint(e.getPoint());
		int col = jTable.columnAtPoint(e.getPoint());

		// seems to happen sometimes
		if (row < 0 || col < 0) {
			return;
		}

		if (e.getButton() == MouseEvent.BUTTON3) {
			// right click: show menu
			JPopupMenu popup = new MatrixPopupMenu(null, dataModel, row, col);
			popup.show(jTable, e.getX(), e.getY());
		} else if (e.getButton() == MouseEvent.BUTTON1) {
			// left click: show new window if value is a matrix
			Object o = dataModel.getMatrix().getAsObject(row, col);
			if (o instanceof Matrix) {
				((Matrix) o).showGUI();
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		scroll = false;
	}

	public void mouseReleased(MouseEvent e) {
		scroll = true;
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void valueChanged(ListSelectionEvent e) {
		valueChanged(new ListSelectionEvent64(e.getSource(), e.getFirstIndex(), e.getLastIndex(),
				e.getValueIsAdjusting()));
	}

	public void valueChanged(ListSelectionEvent64 e) {
		if (scroll && e.getValueIsAdjusting() == false) {
			long minRow = jTable.getSelectionModel().getMinSelectionIndex();
			long minCol = jTable.getColumnModel().getSelectionModel().getMinSelectionIndex();
			long maxRow = jTable.getSelectionModel().getMaxSelectionIndex();
			long maxCol = jTable.getColumnModel().getSelectionModel().getMaxSelectionIndex();
			if (minRow == maxRow && minCol == maxCol) {
				JViewport viewport = (JViewport) jTable.getParent();
				Rectangle rect = jTable.getCellRect((int) minRow, (int) minCol, true);
				Rectangle viewRect = viewport.getViewRect();
				rect.setLocation(rect.x - viewRect.x, rect.y - viewRect.y);
				int centerX = (viewRect.width - rect.width) / 2;
				int centerY = (viewRect.height - rect.height) / 2;
				if (rect.x < centerX) {
					centerX = -centerX;
				}
				if (rect.y < centerY) {
					centerY = -centerY;
				}
				rect.translate(centerX, centerY);
				viewport.scrollRectToVisible(rect);
			}
		}
	}

	protected void finalize() throws Throwable {
		super.finalize();
		if (dataModel != null)
			dataModel.removeTableModelListener(this);
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		final int row = jTable.rowAtPoint(e.getPoint());
		final int col = jTable.columnAtPoint(e.getPoint());
		dataModel.setMouseOverCoordinates(row, col);
	}

}

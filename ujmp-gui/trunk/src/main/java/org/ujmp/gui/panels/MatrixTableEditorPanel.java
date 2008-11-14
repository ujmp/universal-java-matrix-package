/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.editor.MatrixTableCellEditor;
import org.ujmp.gui.menu.MatrixPopupMenu;
import org.ujmp.gui.renderer.MatrixValueTableCellRenderer;

public class MatrixTableEditorPanel extends JPanel implements TableModelListener, MouseListener, KeyListener,
		ListSelectionListener {
	private static final long serialVersionUID = -1794955656888362574L;

	private MatrixGUIObject dataModel = null;

	private final TableModel emptyModel = new DefaultTableModel();

	private JTable jTable = null;

	private JList rowHeader = null;

	private JScrollPane scrollPane = null;

	private boolean scroll = true;

	public MatrixTableEditorPanel(MatrixGUIObject m) {
		this();
		setMatrix(m);
	}

	public MatrixTableEditorPanel() {
		setBorder(BorderFactory.createTitledBorder("Matrix Editor"));
		setLayout(new GridBagLayout());

		jTable = new JTable();
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jTable.getTableHeader().setReorderingAllowed(false);
		jTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		jTable.setColumnSelectionAllowed(true);
		jTable.setDefaultRenderer(Object.class, new MatrixValueTableCellRenderer());
		jTable.setDefaultEditor(Object.class, new MatrixTableCellEditor());
		jTable.addMouseListener(this);
		jTable.addKeyListener(this);
		jTable.getSelectionModel().addListSelectionListener(this);

		rowHeader = new JList();
		rowHeader.setOpaque(true);
		rowHeader.setFixedCellHeight(jTable.getRowHeight());
		rowHeader.setCellRenderer(new RowHeaderRenderer(jTable));

		scrollPane = new JScrollPane(jTable);

		this.addMouseListener(this);

		this.add(jTable.getTableHeader(), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		this.add(scrollPane, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
	}

	public void tableChanged(TableModelEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			int row = jTable.rowAtPoint(e.getPoint());
			int col = jTable.columnAtPoint(e.getPoint());
			JPopupMenu popup = new MatrixPopupMenu(null, dataModel, row, col);
			popup.show(jTable, e.getX(), e.getY());
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
		if (scroll && e.getValueIsAdjusting() == false) {
			int minRow = jTable.getSelectionModel().getMinSelectionIndex();
			int minCol = jTable.getColumnModel().getSelectionModel().getMinSelectionIndex();
			int maxRow = jTable.getSelectionModel().getMaxSelectionIndex();
			int maxCol = jTable.getColumnModel().getSelectionModel().getMaxSelectionIndex();
			if (minRow == maxRow && minCol == maxCol) {
				JViewport viewport = (JViewport) jTable.getParent();
				Rectangle rect = jTable.getCellRect(minRow, minCol, true);
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

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (dataModel != null)
			dataModel.removeTableModelListener(this);
	}

	public void setMatrix(MatrixGUIObject m) {
		if (dataModel != null) {
			dataModel.removeTableModelListener(this);
			dataModel.getRowSelectionModel().removeListSelectionListener(this);
			dataModel.getColumnSelectionModel().removeListSelectionListener(this);
		}

		dataModel = m;

		if (dataModel != null) {
			jTable.setModel(dataModel);
			jTable.setSelectionModel(dataModel.getRowSelectionModel());
			jTable.getColumnModel().setSelectionModel(dataModel.getColumnSelectionModel());
			dataModel.getRowSelectionModel().addListSelectionListener(this);
			dataModel.getColumnSelectionModel().addListSelectionListener(this);
			if (dataModel.getRowCount() <= 100000) {
				AbstractListModel rowListModel = new RowListModel(dataModel);
				rowHeader.setModel(rowListModel);
				scrollPane.setRowHeaderView(rowHeader);
			} else {
				scrollPane.setRowHeaderView(null);
			}
		} else {
			jTable.setModel(emptyModel);
			scrollPane.setRowHeaderView(null);
		}
	}

}

class RowListModel extends AbstractListModel {
	private static final long serialVersionUID = 508583105448562780L;

	private MatrixGUIObject model = null;

	public RowListModel(MatrixGUIObject m) {
		this.model = m;
	}

	public Object getElementAt(int index) {
		return model.getRowName(index);
	}

	public int getSize() {
		return model.getRowCount();
	}

}

class RowHeaderRenderer extends JLabel implements ListCellRenderer {
	private static final long serialVersionUID = -9181159352100487913L;

	private final JTable table;

	private final Border selectedBorder;

	private final Border normalBorder;

	private final Font selectedFont;

	private final Font normalFont;

	final JTableHeader header;

	RowHeaderRenderer(JTable table) {
		this.table = table;
		normalBorder = UIManager.getBorder("TableHeader.cellBorder");
		selectedBorder = BorderFactory.createRaisedBevelBorder();
		header = table.getTableHeader();
		normalFont = header.getFont();
		// selectedFont = normalFont.deriveFont(normalFont.getStyle() |
		// Font.BOLD);
		selectedFont = normalFont;
		setForeground(header.getForeground());
		setBackground(header.getBackground());
		setOpaque(true);
		setHorizontalAlignment(RIGHT);
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		if (table.getSelectionModel().isSelectedIndex(index)) {
			setFont(selectedFont);
			// setBackground(Color.blue);
			// setBorder(selectedBorder);
		} else {
			setFont(normalFont);
			setBackground(header.getBackground());
			// setBorder(normalBorder);
		}
		String label = String.valueOf(value);
		setText(label + " ");
		return this;
	}
}

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

package org.ujmp.gui.table;

import java.awt.Dimension;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.JTableHeader;

public class JTableHeader64 extends JTableHeader implements TableColumnModelListener64, ListSelectionListener64 {
	private static final long serialVersionUID = 5226342858261016321L;

	public JTableHeader64(TableColumnModel64 cm) {
		super(cm);
		cm.getSelectionModel().addListSelectionListener(this);
	}

	public TableColumnModel64 getColumnModel() {
		return (TableColumnModel64) columnModel;
	}

	public void columnMarginChanged(ChangeEvent e) {
		resizeAndRepaint();
	}

	public void columnAdded(TableColumnModelEvent64 e) {
		resizeAndRepaint();
	}

	public void columnRemoved(TableColumnModelEvent64 e) {
		resizeAndRepaint();
	}

	public void columnMoved(TableColumnModelEvent64 e) {
		repaint();
	}

	public void columnSelectionChanged(ListSelectionEvent64 e) {
		repaint();
	}

	// must be overridden, otherwise it will iterate over all columns
	public Dimension getPreferredSize() {
		return new Dimension(getColumnModel().getTotalColumnWidth(), MatrixTable64.ROWHEIGHT);
	}

	public void valueChanged(ListSelectionEvent e) {
		repaint();
	}

	public void valueChanged(ListSelectionEvent64 e) {
		repaint();
	}

}

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

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;

public class RowTableHeaderRenderer64 extends JLabel implements TableCellRenderer64 {
	private static final long serialVersionUID = 6389596849360878514L;

	public RowTableHeaderRenderer64(JTable table) {
		setForeground(table.getTableHeader().getForeground());
		setBackground(table.getTableHeader().getBackground());
		setOpaque(true);
		setHorizontalAlignment(RIGHT);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		return getTableCellRendererComponent((JTable64) table, value, isSelected, hasFocus, (long) row, (long) column);
	}

	public Component getTableCellRendererComponent(JTable64 table, Object value, boolean isSelected, boolean hasFocus,
			long row, long column) {
		if (isSelected) {
			setBackground(table.getSelectionBackground());
		} else {
			setBackground(table.getBackground());
		}
		if (value != null) {
			setText("[" + String.valueOf(value) + "] " + String.valueOf(row) + " ");
			setToolTipText("[" + String.valueOf(value) + "] " + String.valueOf(row));
		} else {
			setText(String.valueOf(row) + " ");
			setToolTipText(String.valueOf(row));
		}
		return this;
	}

}
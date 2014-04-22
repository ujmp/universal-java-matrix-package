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

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;

import org.ujmp.core.util.MathUtil;

public class ColumnTableHeaderRenderer64 extends JLabel implements TableCellRenderer64 {
	private static final long serialVersionUID = -800986952766999425L;

	public ColumnTableHeaderRenderer64(JTable64 table) {
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.GRAY));
		setForeground(table.getTableHeader().getForeground());
		setBackground(table.getTableHeader().getBackground());
		setOpaque(true);
		setHorizontalAlignment(CENTER);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		return getTableCellRendererComponent(table, value, isSelected, hasFocus, (long) row, (long) column);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			long row, long column) {
		if (table.getColumnModel().getSelectionModel().isSelectedIndex(MathUtil.longToInt(column))) {
			setBackground(table.getSelectionBackground());
		} else {
			setBackground(table.getBackground());
		}
		setText(String.valueOf(column));
		return this;
	}

}

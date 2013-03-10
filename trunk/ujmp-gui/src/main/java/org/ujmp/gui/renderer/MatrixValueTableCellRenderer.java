/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

package org.ujmp.gui.renderer;

import java.awt.Color;
import java.awt.Component;
import java.util.ConcurrentModificationException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

import org.ujmp.core.util.StringUtil;
import org.ujmp.core.util.UJMPFormat;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.util.ColorUtil;
import org.ujmp.gui.util.TooltipUtil;

public class MatrixValueTableCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = -1473046176750819621L;

	private static final Color SELECTCOLOR = new Color(200, 200, 255);

	private final Border border = BorderFactory.createLineBorder(Color.blue, 2);

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table,
				value, isSelected, hasFocus, row, column);
		label.setHorizontalAlignment(JLabel.CENTER);

		MatrixGUIObject m = (MatrixGUIObject) table.getModel();

		Color c = ColorUtil.fromObject(value);

		try {
			setToolTipText(TooltipUtil.getTooltip(m, row, column));
		} catch (ConcurrentModificationException e) {
			// not too bad
		}

		int width = table.getColumnModel().getColumn(column).getWidth();
		if (width < 25) {
			label.setText("");
		} else {
			String s = UJMPFormat.getSingleLineInstance().format(value);
			if (s != null && s.length() > 100) {
				s = s.substring(0, 100) + "...";
			}
			label.setText(s);
		}
		label.setForeground(ColorUtil.contrastBW(c));
		label.setBackground(c);
		if (isSelected) {
			label.setBorder(border);
			// label.setBackground(SELECTCOLOR);
		} else {
			label.setBorder(null);
		}

		return label;
	}

	protected void setValue(Object value) {
		setText((value == null) ? "" : StringUtil.format(value));
	}

}

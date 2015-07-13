/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

import org.ujmp.core.Matrix;
import org.ujmp.core.util.ColorUtil;
import org.ujmp.core.util.StringUtil;
import org.ujmp.core.util.UJMPFormat;

public class MatrixValueTableCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = -1473046176750819621L;

	private static final Color SELECTCOLOR = new Color(100, 100, 255, 76);

	private static final Border borderSelected = BorderFactory.createLineBorder(Color.blue, 1);
	private static final Border borderNotSelected = BorderFactory.createLineBorder(Color.white, 1);

	private final MatrixHeatmapRenderer matrixRenderer = new MatrixHeatmapRenderer();

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		label.setHorizontalAlignment(JLabel.CENTER);

		Color c = ColorUtil.fromObject(value);

		int width = table.getColumnModel().getColumn(column).getWidth();
		if (width < 25) {
			label.setText("");
		} else {
			String s;
			if (value == null) {
				s = "";
			} else if (value instanceof Matrix) {
				s = "";
				Matrix ma = (Matrix) value;
				if (ma.getLabel() != null) {
					s += "[" + ma.getLabel() + "]";
				} else {
					s += ma.getClass().getSimpleName();
				}
				return matrixRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			} else if (value instanceof Integer) {
				s = String.valueOf(value);
			} else if (value instanceof Byte) {
				s = String.valueOf(value);
			} else if (value instanceof Long) {
				s = String.valueOf(value);
			} else {
				s = UJMPFormat.getSingleLineInstance().format(value);
			}
			if (s != null && s.length() > 100) {
				s = s.substring(0, 100) + "...";
			}
			label.setText(s);
		}
		label.setForeground(ColorUtil.contrastBW(c));
		if (isSelected) {
			label.setBorder(borderSelected);
			label.setBackground(ColorUtil.add(c, SELECTCOLOR));
		} else {
			label.setBorder(borderNotSelected);
			label.setBackground(c);
		}

		return label;
	}

	protected void setValue(Object value) {
		setText((value == null) ? "" : StringUtil.format(value));
	}

}

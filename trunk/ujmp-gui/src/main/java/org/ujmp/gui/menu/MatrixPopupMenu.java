/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

package org.ujmp.gui.menu;

import java.awt.Color;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.actions.MatrixActions;

public class MatrixPopupMenu extends JPopupMenu {
	private static final long serialVersionUID = -5501347047922058729L;

	public MatrixPopupMenu(JComponent c, MatrixGUIObject matrix, int row,
			int column) {
		JLabel label = new JLabel();
		label.setForeground(new Color(0, 0, 255));
		List<JComponent> actions = null;

		if (matrix.getRowSelectionModel().isSelectedIndex(row)
				&& matrix.getColumnSelectionModel().isSelectedIndex(column)) {
			int startX = matrix.getColumnSelectionModel()
					.getMinSelectionIndex();
			int endX = matrix.getColumnSelectionModel().getMaxSelectionIndex();
			int startY = matrix.getRowSelectionModel().getMinSelectionIndex();
			int endY = matrix.getRowSelectionModel().getMaxSelectionIndex();
			Matrix subMatrix = matrix.getMatrix().subMatrix(Ret.LINK, startY,
					startX, endY, endX);
			actions = new MatrixActions(c, (MatrixGUIObject) subMatrix
					.getGUIObject(), null);
			label.setText(" Selection "
					+ Coordinates.toString(subMatrix.getSize()).replaceAll(",",
							"x"));
		} else {
			actions = new MatrixActions(c, matrix, null);
			label.setText(" Matrix ["
					+ Coordinates.toString(matrix.getSize()).replaceAll(",",
							"x") + "]");
		}

		add(label);
		add(new JSeparator());

		for (JComponent jc : actions) {
			add(jc);
		}
	}
}

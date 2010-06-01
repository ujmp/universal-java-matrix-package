/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

package org.ujmp.gui.actions;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.MatrixGUIObject;

public class SelectAction extends AbstractMatrixAction {
	private static final long serialVersionUID = 252129881194877739L;

	public SelectAction(JComponent c, MatrixGUIObject m, GUIObject v) {
		super(c, m, v);
		putValue(Action.NAME, "Select...");
		putValue(Action.SHORT_DESCRIPTION,
				"Select rows or columns in this matrix");
	}

	public Object call() {
		try {
			String s = JOptionPane.showInputDialog(getComponent(),
					"Enter the rows and columns to select, e.g. 1,3-5;4-5,7",
					"Select", JOptionPane.QUESTION_MESSAGE);

			Matrix m = getMatrixObject().getMatrix().select(getNewOrLink(), s);
			m.showGUI();
			return m;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

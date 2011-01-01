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

package org.ujmp.gui.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.interfaces.GUIObject;

public class DenseMatrixAction extends ObjectAction {
	private static final long serialVersionUID = 8315118041433423085L;

	public DenseMatrixAction(JComponent c, GUIObject o) {
		super(c, o);
		putValue(Action.NAME, "Dense Matrix...");
		putValue(Action.SHORT_DESCRIPTION, "Creates an empty dense matrix");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
	}

	
	public Object call() {
		try {
			ValueType valueType = ValueType.values()[JOptionPane
					.showOptionDialog(getComponent(),
							"Select the value type for the new matrix",
							"Sparse Matrix", JOptionPane.OK_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, ValueType
									.values(), ValueType.DOUBLE)];
			long[] size = null;
			while (size == null || size.length < 2) {
				String s = JOptionPane.showInputDialog(getComponent(),
						"Enter the size of the new matrix, e.g. 3x5x6",
						"Dense Matrix", JOptionPane.QUESTION_MESSAGE);
				try {
					size = Coordinates.parseString(s);
				} catch (Exception e) {
				}
			}
			Matrix m = MatrixFactory.dense(valueType, size);
			m.showGUI();
			return m;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

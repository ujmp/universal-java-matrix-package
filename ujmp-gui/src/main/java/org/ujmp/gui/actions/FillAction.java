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

package org.ujmp.gui.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.MatrixGUIObject;

public class FillAction extends AbstractMatrixAction {
	private static final long serialVersionUID = 6318874871015478768L;

	private String initialValue = "";

	public FillAction(JComponent c, MatrixGUIObject m, GUIObject v) {
		this(c, m, v, "");
	}

	public FillAction(JComponent c, MatrixGUIObject m, GUIObject v,
			String initialValue) {
		super(c, m, v);
		this.initialValue = initialValue;
		putValue(Action.NAME, "Fill");
		putValue(Action.SHORT_DESCRIPTION, "sets all entries to the same value");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F,
				KeyEvent.CTRL_DOWN_MASK));
	}

	public Object call() {
		String s = JOptionPane.showInputDialog("Enter value:", initialValue);
		return getMatrixObject().getMatrix().fill(getRet(), s);
	}

}

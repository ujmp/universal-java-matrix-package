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
import javax.swing.KeyStroke;

import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.util.GUIUtil;

public class PlusAction extends AbstractMatrixAction {
	private static final long serialVersionUID = 7906893375861430374L;

	public PlusAction(JComponent c, MatrixGUIObject m, GUIObject v) {
		super(c, m, v);
		putValue(Action.NAME, "Plus");
		putValue(Action.SHORT_DESCRIPTION, "add a value to all cells");
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_PLUS, KeyEvent.CTRL_DOWN_MASK));
	}

	public Object call()  {
		Matrix m = getMatrixObject().getMatrix().plus(
				getRet(),
				getIgnoreMissing(),
				GUIUtil.getDouble("Value to add", -Double.MAX_VALUE,
						Double.MAX_VALUE));
		m.showGUI();
		return m;
	}

}

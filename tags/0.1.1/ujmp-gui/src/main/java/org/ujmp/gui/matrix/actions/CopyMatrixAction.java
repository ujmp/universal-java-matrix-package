/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

package org.ujmp.gui.matrix.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.HasMatrixList;
import org.ujmp.gui.matrix.MatrixGUIObject;

public class CopyMatrixAction extends MatrixAction {
	private static final long serialVersionUID = 6122103774731476379L;

	public CopyMatrixAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Copy Matrix");
		putValue(Action.SHORT_DESCRIPTION, "copy the content of this matrix to a new matrix");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, 0));
	}

	@Override
	public Object call() {
		Matrix m = getMatrixObject().getMatrix().clone();
		m.showGUI();
		return m;
	}

}

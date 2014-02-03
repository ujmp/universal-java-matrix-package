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

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.interfaces.GUIObject;

public class ImportMatrixFromClipboardAction extends AbstractObjectAction {
	private static final long serialVersionUID = -7154091864377936296L;

	public ImportMatrixFromClipboardAction(JComponent c, GUIObject m) {
		super(c, m);
		putValue(Action.NAME, "from Clipboard...");
		putValue(Action.SHORT_DESCRIPTION, "import a matrix from clipboard");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
	}

	public Object call() {
		try {
			FileFormat fileFormat = FileFormat.values()[JOptionPane
					.showOptionDialog(getComponent(), "Select format",
							"Import Matrix", JOptionPane.OK_OPTION,
							JOptionPane.QUESTION_MESSAGE, null,
							FileFormat.values(), FileFormat.CSV)];

			Matrix m = Matrix.Factory.importFromClipboard(fileFormat);
			m.showGUI();
			return m;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

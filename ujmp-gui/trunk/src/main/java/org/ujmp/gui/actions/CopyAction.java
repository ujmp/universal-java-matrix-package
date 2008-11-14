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

package org.ujmp.gui.actions;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.util.logging.Level;

import javax.swing.Action;
import javax.swing.JComponent;

import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.clipboard.MatrixSelection;

public class CopyAction extends ObjectAction {
	private static final long serialVersionUID = -2679630812122400202L;

	public CopyAction(JComponent c, GUIObject o) {
		super(c, o);
		putValue(Action.NAME, "Copy");
		if (o == null) {
			putValue(Action.SHORT_DESCRIPTION, "Copy Object to clipboard");
		} else {
			putValue(Action.SHORT_DESCRIPTION, "Copy " + o.getClass().getSimpleName() + " to clipboard");
		}
	}

	@Override
	public Object call() {
		try {
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

			if (getObject() instanceof MatrixGUIObject) {
				MatrixSelection ms = new MatrixSelection(((MatrixGUIObject) getObject()).getMatrix());
				clipboard.setContents(ms, ms);
			} else {
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not copy to clipboard", e);
		}
		return null;
	}

}

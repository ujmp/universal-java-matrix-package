/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.Action;
import javax.swing.JComponent;

import org.ujmp.core.interfaces.GUIObject;

public class PasteAction extends ObjectAction {
	private static final long serialVersionUID = -4029079211237756552L;

	public PasteAction(JComponent c, GUIObject o) {
		super(c, o);
		putValue(Action.NAME, "Paste");
		putValue(Action.SHORT_DESCRIPTION, "Paste from clipboard");
	}

	@Override
	public Object call() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable clipData = clipboard.getContents(getObject());
		String s;
		try {
			s = (String) (clipData.getTransferData(DataFlavor.stringFlavor));
		} catch (Exception ex) {
			s = ex.toString();
		}
		System.out.println(s);
		return null;
	}

}

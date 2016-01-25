/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.actions.ImportMatrixFromClipboardAction;
import org.ujmp.gui.actions.ImportMatrixFromDatabaseAction;
import org.ujmp.gui.actions.ImportMatrixFromFileAction;
import org.ujmp.gui.actions.ImportMatrixFromURLAction;

public class UJMPImportMenu extends JMenu {
	private static final long serialVersionUID = -7979875562887178063L;

	public UJMPImportMenu(JComponent c, GUIObject o) {
		super("Import");
		setMnemonic(KeyEvent.VK_I);
		add(new JMenuItem(new ImportMatrixFromFileAction(c, o)));
		add(new JMenuItem(new ImportMatrixFromClipboardAction(c, o)));
		add(new JMenuItem(new ImportMatrixFromURLAction(c, o)));
		add(new JMenuItem(new ImportMatrixFromDatabaseAction(c, o)));
	}

}

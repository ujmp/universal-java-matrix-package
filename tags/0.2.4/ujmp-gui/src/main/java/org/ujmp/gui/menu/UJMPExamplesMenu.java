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

package org.ujmp.gui.menu;

import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.ujmp.gui.actions.MandelbrotMatrixAction;
import org.ujmp.gui.actions.PascalMatrixAction;
import org.ujmp.gui.actions.SunSpotDataMatrixAction;
import org.ujmp.gui.actions.WelcomeMatrixAction;

public class UJMPExamplesMenu extends JMenu {
	private static final long serialVersionUID = -7279072623034811310L;

	public UJMPExamplesMenu(JComponent component) {
		super("Examples");
		setMnemonic(KeyEvent.VK_E);
		add(new MatrixExamplesMenu());
	}

	class MatrixExamplesMenu extends JMenu {
		private static final long serialVersionUID = -5582717033551246385L;

		public MatrixExamplesMenu() {
			super("Matrix");
			add(new JMenuItem(new WelcomeMatrixAction(this, null, null)));
			add(new JMenuItem(new MandelbrotMatrixAction(this, null, null)));
			add(new JMenuItem(new SunSpotDataMatrixAction(this, null, null)));
			add(new JMenuItem(new PascalMatrixAction(this, null, null)));
		}
	}

}

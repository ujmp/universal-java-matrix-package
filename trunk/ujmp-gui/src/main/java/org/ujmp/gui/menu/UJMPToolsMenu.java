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

package org.ujmp.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.util.JMathLib;
import org.ujmp.core.util.matrices.MatrixAvailableProcessors;
import org.ujmp.core.util.matrices.MatrixMemoryUsage;
import org.ujmp.core.util.matrices.MatrixRandomSeed;
import org.ujmp.core.util.matrices.MatrixRunningThreads;
import org.ujmp.core.util.matrices.MatrixSystemEnvironment;
import org.ujmp.core.util.matrices.MatrixSystemProperties;
import org.ujmp.core.util.matrices.MatrixSystemTime;
import org.ujmp.core.util.matrices.UJMPPluginsMatrix;
import org.ujmp.gui.actions.ShowInFrameAction;
import org.ujmp.gui.util.MatrixUIDefaults;

public class UJMPToolsMenu extends JMenu {
	private static final long serialVersionUID = 853886481708901509L;

	public UJMPToolsMenu(JComponent component) {
		super("Tools");
		setMnemonic(KeyEvent.VK_T);
		add(new JMenuItem(new ShowInFrameAction(component,
				new UJMPPluginsMatrix())));
		add(new JSeparator());
		add(new JMenuItem(new ShowInFrameAction(component, FileFormat
				.getMatrix())));
		add(new JMenuItem(new ShowInFrameAction(component,
				new MatrixSystemProperties())));
		add(new JMenuItem(new ShowInFrameAction(component,
				new MatrixSystemEnvironment())));
		add(new JMenuItem(new ShowInFrameAction(component, MatrixUIDefaults
				.getInstance())));
		add(new JSeparator());
		add(new JMenuItem(new ShowInFrameAction(component,
				new MatrixMemoryUsage())));
		add(new JMenuItem(new ShowInFrameAction(component,
				new MatrixRunningThreads())));
		add(new JMenuItem(new ShowInFrameAction(component,
				new MatrixSystemTime())));
		add(new JMenuItem(new ShowInFrameAction(component,
				new MatrixRandomSeed())));
		add(new JMenuItem(new ShowInFrameAction(component,
				new MatrixAvailableProcessors())));
		
		if (JMathLib.isAvailable()) {
			add(new JSeparator());
			add(new JMathLibAction());
		}
	}

	class JMathLibAction extends AbstractAction {
		private static final long serialVersionUID = 1895232937545702538L;

		public JMathLibAction() {
			putValue(Action.NAME, "JMathLib");
			putValue(Action.SHORT_DESCRIPTION, "Show JMathlib in a new Window");
		}

		
		public void actionPerformed(ActionEvent e) {
			JMathLib.showGUI();
		}

	}
}

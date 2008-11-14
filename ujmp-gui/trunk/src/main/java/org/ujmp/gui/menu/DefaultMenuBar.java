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

package org.ujmp.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.util.JMathLib;
import org.ujmp.gui.actions.AboutAction;
import org.ujmp.gui.matrix.MatrixAvailableProcessors;
import org.ujmp.gui.matrix.MatrixMemoryUsage;
import org.ujmp.gui.matrix.MatrixRandomSeed;
import org.ujmp.gui.matrix.MatrixRunningThreads;
import org.ujmp.gui.matrix.MatrixSystemEnvironment;
import org.ujmp.gui.matrix.MatrixSystemProperties;
import org.ujmp.gui.matrix.MatrixSystemTime;
import org.ujmp.gui.matrix.MatrixUIDefaults;
import org.ujmp.gui.matrix.actions.ShowInFrameAction;
import org.ujmp.gui.util.FrameManager;

public class DefaultMenuBar extends JMenuBar {
	private static final long serialVersionUID = -6115122804967308915L;

	public DefaultMenuBar(JComponent component, GUIObject o) {
	}

	class JMathLibAction extends AbstractAction {
		private static final long serialVersionUID = 1895232937545702538L;

		public JMathLibAction() {
			putValue(Action.NAME, "JMathLib");
			putValue(Action.SHORT_DESCRIPTION, "Show JMathlib in a new Window");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JMathLib.showGUI();
		}

	}

	public void init(JComponent component, GUIObject o) {
		JMenu optionsMenu = new JMenu("Tools");
		optionsMenu.setMnemonic(KeyEvent.VK_T);
		optionsMenu.add(new JSeparator());

		optionsMenu.add(new JMenuItem(new ShowInFrameAction(component, MatrixSystemProperties.getInstance())));
		optionsMenu.add(new JMenuItem(new ShowInFrameAction(component, MatrixSystemEnvironment.getInstance())));
		optionsMenu.add(new JMenuItem(new ShowInFrameAction(component, MatrixUIDefaults.getInstance())));
		optionsMenu.add(new JSeparator());
		optionsMenu.add(new JMenuItem(new ShowInFrameAction(component, MatrixMemoryUsage.getInstance())));
		optionsMenu.add(new JMenuItem(new ShowInFrameAction(component, MatrixRunningThreads.getInstance())));
		optionsMenu.add(new JMenuItem(new ShowInFrameAction(component, MatrixSystemTime.getInstance())));
		optionsMenu.add(new JMenuItem(new ShowInFrameAction(component, MatrixRandomSeed.getInstance())));
		optionsMenu.add(new JMenuItem(new ShowInFrameAction(component, MatrixAvailableProcessors.getInstance())));
		optionsMenu.add(new JSeparator());

		if (JMathLib.isAvailable()) {
			optionsMenu.add(new JSeparator());
			optionsMenu.add(new JMathLibAction());
		}
		add(optionsMenu);

		JMenu windowMenu = new JMenu("Window");
		windowMenu.setMnemonic(KeyEvent.VK_W);
		for (JComponent a : FrameManager.getActions()) {
			windowMenu.add(a);
		}
		add(windowMenu);

		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);

		helpMenu.add(new AboutAction(component, o));

		add(helpMenu);
	}

}

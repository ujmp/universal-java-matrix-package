/*
 * Copyright (C) 2008-2015 by Holger Arndt
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
import java.net.SocketException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.ujmp.core.util.JMathLib;
import org.ujmp.core.util.matrices.AvailableProcessorsMatrix;
import org.ujmp.core.util.matrices.FileFormatMatrix;
import org.ujmp.core.util.matrices.LocalhostMatrix;
import org.ujmp.core.util.matrices.MatrixLibraries;
import org.ujmp.core.util.matrices.MemoryUsageMatrix;
import org.ujmp.core.util.matrices.OperatingSystemMatrix;
import org.ujmp.core.util.matrices.RandomSeedMatrix;
import org.ujmp.core.util.matrices.RunningThreadsMatrix;
import org.ujmp.core.util.matrices.SystemEnvironmentMatrix;
import org.ujmp.core.util.matrices.SystemPropertiesMatrix;
import org.ujmp.core.util.matrices.SystemTimeMatrix;
import org.ujmp.core.util.matrices.UJMPPluginsMatrix;
import org.ujmp.gui.actions.ShowInFrameAction;
import org.ujmp.gui.util.MatrixUIDefaults;

public class UJMPToolsMenu extends JMenu {
	private static final long serialVersionUID = 853886481708901509L;

	public UJMPToolsMenu(JComponent component) {
		super("Tools");
		setMnemonic(KeyEvent.VK_T);
		add(new JMenuItem(new ShowInFrameAction(component, "UJMP Plugins", UJMPPluginsMatrix.class)));
		add(new LocalhostAction());
		add(new MatrixLibrariesAction());
		add(new JSeparator());
		add(new JMenuItem(new ShowInFrameAction(component, "Supported File Formats", FileFormatMatrix.class)));
		add(new JMenuItem(new ShowInFrameAction(component, "System Properties", SystemPropertiesMatrix.class)));
		add(new JMenuItem(new ShowInFrameAction(component, "System Environment", SystemEnvironmentMatrix.class)));
		add(new JMenuItem(new ShowInFrameAction(component, "UI Defaults", MatrixUIDefaults.class)));
		add(new JSeparator());
		add(new JMenuItem(new ShowInFrameAction(component, "Memory Usage", MemoryUsageMatrix.class)));
		add(new JMenuItem(new ShowInFrameAction(component, "Running Threads", RunningThreadsMatrix.class)));
		add(new JMenuItem(new ShowInFrameAction(component, "System Time", SystemTimeMatrix.class)));
		add(new JMenuItem(new ShowInFrameAction(component, "Random Seed", RandomSeedMatrix.class)));
		add(new JMenuItem(new ShowInFrameAction(component, "Available Processors", AvailableProcessorsMatrix.class)));
		add(new JMenuItem(new ShowInFrameAction(component, "Operating System", OperatingSystemMatrix.class)));

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

	class MatrixLibrariesAction extends AbstractAction {
		private static final long serialVersionUID = 5469558464204854759L;

		public MatrixLibrariesAction() {
			putValue(Action.NAME, "Matrix Libraries");
			putValue(Action.SHORT_DESCRIPTION, "Show overview of matrix libraries in a new window");
		}

		public void actionPerformed(ActionEvent e) {
			new MatrixLibraries().showGUI();
		}
	}

	class LocalhostAction extends AbstractAction {
		private static final long serialVersionUID = 1730034739534995562L;

		public LocalhostAction() {
			putValue(Action.NAME, "Localhost");
			putValue(Action.SHORT_DESCRIPTION, "Show data on localhost");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				LocalhostMatrix.getInstance().showGUI();
			} catch (SocketException e1) {
				e1.printStackTrace();
			}
		}
	}
}

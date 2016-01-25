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

package org.ujmp.gui.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.UIManager;

import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.io.ExportJPEG;
import org.ujmp.gui.io.ExportPDF;
import org.ujmp.gui.io.ExportPNG;
import org.ujmp.gui.util.GUIUtil;

public class PanelActions extends ArrayList<JComponent> {
	private static final long serialVersionUID = 4269896034756524809L;

	private final JComponent component;

	public PanelActions(JComponent c, GUIObject o) {
		this.component = c;
		this.add(new JMenuItem(exportToPngAction));
		this.add(new JMenuItem(exportToJpgAction));
		JMenuItem mi = new JMenuItem(exportToPdfAction);
		this.add(mi);
		if (!ExportPDF.isSupported()) {
			mi.setEnabled(false);
			mi.setToolTipText("iText library not found in classpath");
		}
		this.add(new JSeparator());
		this.add(new JMenuItem(new PrintAction(c, o)));
	}

	public final Action exportToPdfAction = new AbstractAction("Export to PDF...", UIManager.getIcon("JDMP.icon.pdf")) {
		private static final long serialVersionUID = -7413294854080175036L;

		public void actionPerformed(ActionEvent e) {
			ExportPDF.save(ExportPDF.selectFile(), component);
		}
	};

	public final Action exportToJpgAction = new AbstractAction("Export to JPG...", UIManager.getIcon("JDMP.icon.image")) {
		private static final long serialVersionUID = 2903870037000412488L;

		public void actionPerformed(ActionEvent e) {
			ExportJPEG.save(ExportJPEG.selectFile(), component, GUIUtil.getInt("Export width:", 1, 10000));
		}
	};

	public final Action exportToPngAction = new AbstractAction("Export to PNG...", UIManager.getIcon("JDMP.icon.image")) {
		private static final long serialVersionUID = 667245834772891667L;

		public void actionPerformed(ActionEvent e) {
			ExportPNG.save(ExportPNG.selectFile(), component, GUIUtil.getInt("Export width:", 1, 10000));
		}
	};

}

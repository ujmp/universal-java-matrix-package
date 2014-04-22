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

package org.ujmp.jung;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import org.ujmp.gui.actions.PanelActions;
import org.ujmp.jung.JungVisualizationViewer.GraphLayout;

public class JungGraphActions extends JPopupMenu {

	private static final long serialVersionUID = -2307893165969916295L;

	JungVisualizationViewer<?, ?> jungGraphPanel = null;

	public JungGraphActions(JungVisualizationViewer<?, ?> m) {
		this.jungGraphPanel = m;

		JMenu panelMenu = new JMenu("This Panel");
		for (JComponent c : new PanelActions(m, null)) {
			panelMenu.add(c);
		}
		this.add(panelMenu);

		JMenu layoutMenu = new JMenu("Select Layout");
		layoutMenu.add(circleLayoutAction);
		layoutMenu.add(frLayoutAction);
		layoutMenu.add(frLayout2Action);
		layoutMenu.add(kkLayoutAction);
		layoutMenu.add(iSomLayoutAction);
		layoutMenu.add(springLayoutAction);
		layoutMenu.add(springLayout2Action);

		this.add(layoutMenu);
		this.add(toggleEdgeLabelsAction);
		this.add(toggleVertexLabelsAction);
		this.add(new JSeparator());
		this.add(new JSeparator());
		this.add(refreshAction);
	}

	public final Action frLayoutAction = new AbstractAction("FRLayout") {
		private static final long serialVersionUID = 3149916178777567323L;

		public void actionPerformed(ActionEvent e) {
			jungGraphPanel.switchLayout(GraphLayout.FRLayout);
		}
	};

	public final Action frLayout2Action = new AbstractAction("FRLayout2") {
		private static final long serialVersionUID = 1862486279803190687L;

		public void actionPerformed(ActionEvent e) {
			jungGraphPanel.switchLayout(GraphLayout.FRLayout2);
		}
	};

	public final Action iSomLayoutAction = new AbstractAction("ISOMLayout") {
		private static final long serialVersionUID = 1862486279803190687L;

		public void actionPerformed(ActionEvent e) {
			jungGraphPanel.switchLayout(GraphLayout.ISOMLayout);
		}
	};

	public final Action kkLayoutAction = new AbstractAction("KKLayout") {
		private static final long serialVersionUID = 8756219332341323478L;

		public void actionPerformed(ActionEvent e) {
			jungGraphPanel.switchLayout(GraphLayout.KKLayout);
		}
	};

	public final Action springLayoutAction = new AbstractAction("SpringLayout") {
		private static final long serialVersionUID = -9129746911116351142L;

		public void actionPerformed(ActionEvent e) {
			jungGraphPanel.switchLayout(GraphLayout.SpringLayout);
		}
	};

	public final Action springLayout2Action = new AbstractAction("SpringLayout2") {
		private static final long serialVersionUID = 2852217645436316157L;

		public void actionPerformed(ActionEvent e) {
			jungGraphPanel.switchLayout(GraphLayout.SpringLayout2);
		}
	};

	public final Action circleLayoutAction = new AbstractAction("CircleLayout") {
		private static final long serialVersionUID = -3030980988050670381L;

		public void actionPerformed(ActionEvent e) {
			jungGraphPanel.switchLayout(GraphLayout.CircleLayout);
		}
	};

	public final Action refreshAction = new AbstractAction("Refresh") {
		private static final long serialVersionUID = -8057389215808050942L;

		public void actionPerformed(ActionEvent e) {
			jungGraphPanel.refreshUI();
		}
	};

	public final Action toggleEdgeLabelsAction = new AbstractAction("Toggle Edge Labels") {
		private static final long serialVersionUID = 5043606502712307760L;

		public void actionPerformed(ActionEvent e) {
			jungGraphPanel.setShowEdgeLabels(!jungGraphPanel.isShowEdgeLabels());
		}
	};

	public final Action toggleVertexLabelsAction = new AbstractAction("Toggle Vertex Labels") {
		private static final long serialVersionUID = -8736147166116311565L;

		public void actionPerformed(ActionEvent e) {
			jungGraphPanel.setShowNodeLabels(!jungGraphPanel.isShowNodeLabels());
		}
	};

}

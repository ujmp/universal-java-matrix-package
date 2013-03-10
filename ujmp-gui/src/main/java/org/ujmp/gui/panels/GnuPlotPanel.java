/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

package org.ujmp.gui.panels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.ujmp.core.util.GnuPlot;
import org.ujmp.gui.MatrixGUIObject;

public class GnuPlotPanel extends JPanel {
	private static final long serialVersionUID = -241038814283185885L;

	private MatrixGUIObject matrix = null;

	public GnuPlotPanel(MatrixGUIObject m) {
		this.matrix = m;
		setLayout(new FlowLayout());

		add(new JButton(new XYPlotAction()));
		add(new JButton(new ScatterPlotAction()));
	}

	class ScatterPlotAction extends AbstractAction {
		private static final long serialVersionUID = 4837137928213709856L;

		public ScatterPlotAction() {
			super("Scatter Plot");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				GnuPlot.getInstance().scatterPlot(matrix.getMatrix());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class XYPlotAction extends AbstractAction {
		private static final long serialVersionUID = -4928348084073744818L;

		public XYPlotAction() {
			super("XY Plot");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				GnuPlot.getInstance().plot(matrix.getMatrix());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}

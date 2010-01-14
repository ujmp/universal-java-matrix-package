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

package org.ujmp.gui.panels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.util.Octave;
import org.ujmp.gui.MatrixGUIObject;

public class OctavePanel extends JPanel {
	private static final long serialVersionUID = -894693635404746973L;

	private MatrixGUIObject matrix = null;

	public OctavePanel(MatrixGUIObject m) {
		this.matrix = m;

		setLayout(new FlowLayout());

		add(new JButton(new MatrixPlotAction()));
		add(new JButton(new XYPlotAction()));
		add(new JButton(new ScatterPlotAction()));
		add(new JButton(new HistogramPlotAction()));
	}

	class MatrixPlotAction extends AbstractAction {
		private static final long serialVersionUID = -4928348084073744818L;

		public MatrixPlotAction() {
			super("Matrix Plot");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				Octave.getInstance().plot(matrix.getMatrix());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class XYPlotAction extends AbstractAction {
		private static final long serialVersionUID = 4954900597400686518L;

		public XYPlotAction() {
			super("XY Plot");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				Octave.getInstance().plot(
						matrix.getMatrix().selectColumns(Ret.NEW, 0),
						matrix.getMatrix().selectColumns(Ret.NEW, 1));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class ScatterPlotAction extends AbstractAction {
		private static final long serialVersionUID = 4837137928213709856L;

		public ScatterPlotAction() {
			super("Scatter Plot");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				Octave.getInstance().plot(
						matrix.getMatrix().selectColumns(Ret.NEW, 0),
						matrix.getMatrix().selectColumns(Ret.NEW, 1), "x");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class HistogramPlotAction extends AbstractAction {
		private static final long serialVersionUID = -1396238157254507002L;

		public HistogramPlotAction() {
			super("Histogram");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				Octave.getInstance().hist(matrix.getMatrix());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}

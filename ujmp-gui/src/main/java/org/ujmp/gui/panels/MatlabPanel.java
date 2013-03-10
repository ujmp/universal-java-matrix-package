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

import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.util.Matlab;
import org.ujmp.gui.MatrixGUIObject;

public class MatlabPanel extends JPanel {
	private static final long serialVersionUID = -2014717060178963100L;

	private MatrixGUIObject matrix = null;

	public MatlabPanel(MatrixGUIObject m) {
		this.matrix = m;
		setLayout(new FlowLayout());

		add(new JButton(new MatrixPlotAction()));
		add(new JButton(new XYPlotAction()));
		add(new JButton(new ScatterPlotAction()));
		add(new JButton(new HistogramAction()));
		add(new JButton(new SurfAction()));
		add(new JButton(new ImagescAction()));
		add(new JButton(new BarAction()));
		add(new JButton(new BarhAction()));
		add(new JButton(new StemAction()));
		add(new JButton(new PieAction()));
		add(new JButton(new Pie3Action()));
		add(new JButton(new PairsAction()));
	}

	class ScatterPlotAction extends AbstractAction {
		private static final long serialVersionUID = 4837137928213709856L;

		public ScatterPlotAction() {
			super("Scatter Plot");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				Matlab.getInstance().plot(
						matrix.getMatrix().selectColumns(Ret.NEW, 0),
						matrix.getMatrix().selectColumns(Ret.NEW, 1), "x");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class MatrixPlotAction extends AbstractAction {
		private static final long serialVersionUID = -4928348084073744818L;

		public MatrixPlotAction() {
			super("Matrix Plot");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				Matlab.getInstance().plot(matrix.getMatrix());
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
				Matlab.getInstance().plot(
						matrix.getMatrix().selectColumns(Ret.NEW, 0),
						matrix.getMatrix().selectColumns(Ret.NEW, 1));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class HistogramAction extends AbstractAction {
		private static final long serialVersionUID = -320738954210581946L;

		public HistogramAction() {
			super("Histogram");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				Matlab.getInstance().hist(matrix.getMatrix());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class SurfAction extends AbstractAction {
		private static final long serialVersionUID = 1936056248662686441L;

		public SurfAction() {
			super("Surf");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				Matlab.getInstance().surf(matrix.getMatrix());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class ImagescAction extends AbstractAction {
		private static final long serialVersionUID = -69661897898443618L;

		public ImagescAction() {
			super("ImageSC");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				Matlab.getInstance().imagesc(matrix.getMatrix());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class BarAction extends AbstractAction {
		private static final long serialVersionUID = 2326046110179603901L;

		public BarAction() {
			super("Bar");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				Matlab.getInstance().bar(matrix.getMatrix());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class BarhAction extends AbstractAction {
		private static final long serialVersionUID = 1773781454473042057L;

		public BarhAction() {
			super("BarH");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				Matlab.getInstance().barh(matrix.getMatrix());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class StemAction extends AbstractAction {
		private static final long serialVersionUID = 8489278103292046885L;

		public StemAction() {
			super("Stem");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				Matlab.getInstance().stem(matrix.getMatrix());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class PairsAction extends AbstractAction {
		private static final long serialVersionUID = 5809558873714857141L;

		public PairsAction() {
			super("Pairs");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				Matlab.getInstance().plotmatrix(matrix.getMatrix());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class PieAction extends AbstractAction {
		private static final long serialVersionUID = -7468454385004258030L;

		public PieAction() {
			super("Pie Chart");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				Matlab.getInstance().pie(matrix.getMatrix());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class Pie3Action extends AbstractAction {
		private static final long serialVersionUID = -6838327620135999067L;

		public Pie3Action() {
			super("3D Pie Chart");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				Matlab.getInstance().pie3(matrix.getMatrix());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}

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

package org.ujmp.gui.panels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.ujmp.core.util.R;
import org.ujmp.gui.MatrixGUIObject;

public class RPanel extends JPanel {
	private static final long serialVersionUID = -3779245352485347462L;

	private MatrixGUIObject matrix = null;

	public RPanel(MatrixGUIObject m) {
		this.matrix = m;

		setLayout(new FlowLayout());

		add(new JButton(new ScatterPlotAction()));
		add(new JButton(new ImageAction()));
		add(new JButton(new HistAction()));
		add(new JButton(new PairsPlotAction()));
		add(new JButton(new BoxPlotAction()));
		add(new JButton(new QQNormAction()));
		add(new JButton(new CloseLastFigureAction()));

	}

	class ScatterPlotAction extends AbstractAction {

		public ScatterPlotAction() {
			super("Scatter Plot");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				R.getInstance().plot(matrix.getMatrix(), "col=\"blue\",pch=16");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class BoxPlotAction extends AbstractAction {

		public BoxPlotAction() {
			super("Box Plot");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				R.getInstance().boxplot(matrix.getMatrix(), "col=\"blue\"");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class PairsPlotAction extends AbstractAction {

		public PairsPlotAction() {
			super("Pairs Plot");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				R.getInstance().pairs(matrix.getMatrix(), "col=\"blue\",pch=16");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class QQNormAction extends AbstractAction {

		public QQNormAction() {
			super("QQNorm");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				R.getInstance().qqnorm(matrix.getMatrix(), "col=\"blue\",pch=16");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class ImageAction extends AbstractAction {

		public ImageAction() {
			super("Image");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				R.getInstance().image(matrix.getMatrix());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class HistAction extends AbstractAction {

		public HistAction() {
			super("Histogram");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				R.getInstance().hist(matrix.getMatrix(), "col=\"blue\"");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class CloseLastFigureAction extends AbstractAction {

		public CloseLastFigureAction() {
			super("Close last Figure");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				R.getInstance().closeLastFigure();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}

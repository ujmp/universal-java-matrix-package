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

package org.ujmp.gui.matrix;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.lang.reflect.Constructor;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.tree.TreeModel;

import org.ujmp.core.Matrix;
import org.ujmp.core.util.GnuPlot;
import org.ujmp.core.util.Matlab;
import org.ujmp.core.util.Octave;
import org.ujmp.core.util.R;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.matrix.panels.AbstractPanel;
import org.ujmp.gui.matrix.panels.BufferedPanel;
import org.ujmp.gui.matrix.panels.GnuPlotPanel;
import org.ujmp.gui.matrix.panels.MatlabPanel;
import org.ujmp.gui.matrix.panels.OctavePanel;
import org.ujmp.gui.matrix.panels.RPanel;
import org.ujmp.gui.plot.MatrixPlot;

public class MatrixPanel extends AbstractPanel {
	private static final long serialVersionUID = 3912987239953510584L;

	private final JSplitPane splitPane1 = new JSplitPane();

	private final JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

	private MatrixPaintPanel matrixPaintPanel = null;

	private MatrixPlot matrixPlot = null;

	public MatrixPanel(MatrixGUIObject m) {
		super(m);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setMinimumSize(new Dimension(10, 10));

		matrixPlot = new MatrixPlot(m, true);

		matrixPaintPanel = new MatrixPaintPanel(m, false);
		tabbedPane.add("2D Visualization", matrixPaintPanel);

		if (false) {
			try {
				// tabbedPane.add("3D Visualization", new Matrix3DPanel(m,
				// false));
			} catch (Throwable t) {
			}
		}

		if (m.getMatrix() instanceof TreeModel) {
			tabbedPane.add("Tree View", new MatrixTreePanel(m));
		}

		try {
			Class<?> c = Class.forName("org.ujmp.jmathplot.JMathPlotLinePanel");
			Constructor<?> con = c.getConstructor(Matrix.class);
			JPanel panel = (JPanel) con.newInstance(m.getMatrix());
			tabbedPane.add("Line Plot", panel);
		} catch (Exception e) {
		}

		try {
			Class<?> c = Class.forName("org.ujmp.jmathplot.JMathPlotXYPanel");
			Constructor<?> con = c.getConstructor(Matrix.class);
			JPanel panel = (JPanel) con.newInstance(m.getMatrix());
			tabbedPane.add("XY Plot", panel);
		} catch (Exception e) {
		}

		try {
			Class<?> c = Class.forName("org.ujmp.jmathplot.JMathPlotScatterPanel");
			Constructor<?> con = c.getConstructor(Matrix.class);
			JPanel panel = (JPanel) con.newInstance(m.getMatrix());
			tabbedPane.add("Scatter Plot", panel);
		} catch (Exception e) {
		}

		try {
			Class<?> c = Class.forName("org.ujmp.jmathplot.JMathPlotBarPanel");
			Constructor<?> con = c.getConstructor(Matrix.class);
			JPanel panel = (JPanel) con.newInstance(m.getMatrix());
			tabbedPane.add("Bar Plot", panel);
		} catch (Exception e) {
		}

		try {
			Class<?> c = Class.forName("org.ujmp.jmathplot.JMathPlotHistogramPanel");
			Constructor<?> con = c.getConstructor(Matrix.class);
			JPanel panel = (JPanel) con.newInstance(m.getMatrix());
			tabbedPane.add("Histogram", panel);
		} catch (Exception e) {
		}

		try {
			Class<?> c = Class.forName("org.ujmp.jmathplot.JMathPlotStaircasePanel");
			Constructor<?> con = c.getConstructor(Matrix.class);
			JPanel panel = (JPanel) con.newInstance(m.getMatrix());
			tabbedPane.add("Staircase Plot", panel);
		} catch (Exception e) {
		}

		try {
			Class<?> c = Class.forName("org.ujmp.jmathplot.JMathPlotGridPanel");
			Constructor<?> con = c.getConstructor(Matrix.class);
			JPanel panel = (JPanel) con.newInstance(m.getMatrix());
			tabbedPane.add("Grid Plot", panel);
		} catch (Exception e) {
		}

		try {
			if (m.getColumnCount() >= 3) {
				Class<?> c = Class.forName("org.ujmp.jmathplot.JMathPlotBar3DPanel");
				Constructor<?> con = c.getConstructor(Matrix.class);
				JPanel panel = (JPanel) con.newInstance(m.getMatrix());
				tabbedPane.add("Bar Plot 3D", panel);
			}
		} catch (Exception e) {
		}

		try {
			if (m.getColumnCount() >= 3) {
				Class<?> c = Class.forName("org.ujmp.jmathplot.JMathPlotScatter3DPanel");
				Constructor<?> con = c.getConstructor(Matrix.class);
				JPanel panel = (JPanel) con.newInstance(m.getMatrix());
				tabbedPane.add("Scatter Plot 3D", panel);
			}
		} catch (Exception e) {
		}

		try {
			if (m.getColumnCount() >= 3) {
				Class<?> c = Class.forName("org.ujmp.jmathplot.JMathPlotLine3DPanel");
				Constructor<?> con = c.getConstructor(Matrix.class);
				JPanel panel = (JPanel) con.newInstance(m.getMatrix());
				tabbedPane.add("Line Plot 3D", panel);
			}
		} catch (Exception e) {
		}

		if (GnuPlot.isAvailable()) {
			GnuPlotPanel gnuPlotPanel = new GnuPlotPanel(m);
			tabbedPane.add("GnuPlot", gnuPlotPanel);
		}

		if (Matlab.isAvailable()) {
			MatlabPanel matlabPanel = new MatlabPanel(m);
			tabbedPane.add("Matlab", matlabPanel);
		}

		if (Octave.isAvailable()) {
			OctavePanel octavePanel = new OctavePanel(m);
			tabbedPane.add("Octave", octavePanel);
		}

		if (R.isAvailable()) {
			RPanel rPanel = new RPanel(m);
			tabbedPane.add("R", rPanel);
		}

		splitPane2.setTopComponent(new BufferedPanel(matrixPlot));
		splitPane2.setBottomComponent(tabbedPane);
		splitPane1.setLeftComponent(splitPane2);

		splitPane1.setRightComponent(new MatrixEditorPanel(m));

		add(splitPane1, BorderLayout.CENTER);

		splitPane1.setDividerLocation(600);
		splitPane2.setDividerLocation(200);
	}

}

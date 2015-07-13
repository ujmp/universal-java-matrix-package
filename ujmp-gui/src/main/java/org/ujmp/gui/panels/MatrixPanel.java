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

package org.ujmp.gui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.lang.reflect.Constructor;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.tree.TreeModel;

import org.ujmp.core.Matrix;
import org.ujmp.core.graphmatrix.GraphMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.treematrix.TreeMatrix;
import org.ujmp.core.util.GnuPlot;
import org.ujmp.core.util.Matlab;
import org.ujmp.core.util.Octave;
import org.ujmp.core.util.R;
import org.ujmp.gui.DefaultMatrixGUIObject;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.graph.GraphPanel;
import org.ujmp.gui.plot.MatrixPlot;

public class MatrixPanel extends AbstractPanel {
	private static final long serialVersionUID = 3912987239953510584L;

	public MatrixPanel(MatrixGUIObject m) {
		super(m);
		if (!(m instanceof DefaultMatrixGUIObject)) {
			m = new DefaultMatrixGUIObject(m.getMatrix());
		}

		JSplitPane splitPane1 = new JSplitPane();
		JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setMinimumSize(new Dimension(10, 10));

		MapMatrix<String, Object> metaData = m.getMatrix().getMetaData();
		if (metaData != null) {
			splitPane2.setTopComponent(new MatrixEditorPanel("Meta Data", (MatrixGUIObject) m.getMatrix().getMetaData()
					.getGUIObject()));
		}

		tabbedPane.add("Heatmap", new MatrixHeatmapPanel(m, false));

		tabbedPane.add("Plot", new BufferedPanel(new MatrixPlot(m, true)));

		if (m.getMatrix() instanceof GraphMatrix && m.getColumnCount() < 1000) {
			tabbedPane.add("Graph", new GraphPanel(m));
			tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);
		}

		if (m.getMatrix() instanceof GraphMatrix && ((GraphMatrix<?, ?>) m.getMatrix()).getNodeCount() < 1000) {
			try {
				Class<?> graphPanelClass = Class.forName("org.ujmp.jung.JungVisualizationViewer");
				Constructor<?> graphPanelConstructor = graphPanelClass.getConstructor(GraphMatrix.class);
				JPanel graphPanel = (JPanel) graphPanelConstructor.newInstance(m.getMatrix());
				tabbedPane.add("JUNG Graph", graphPanel);
				tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);
			} catch (Throwable e) {
			}
		}

		if (m.getMatrix() instanceof TreeModel) {
			tabbedPane.add("Tree", new MatrixTreePanel(m));
		}

		if (m.getMatrix() instanceof TreeMatrix) {
			tabbedPane.add("Treemap", new MatrixTreemapPanel((TreeMatrix<?>) m.getMatrix()));
		}

		if (m.getRowCount() > 1) {
			try {
				Class.forName("org.math.plot.PlotPanel");
				Class<?> c = Class.forName("org.ujmp.jmathplot.JMathPlotLinePanel");
				Constructor<?> con = c.getConstructor(Matrix.class);
				JPanel panel = (JPanel) con.newInstance(m.getMatrix());
				tabbedPane.add("Line Plot", panel);
			} catch (Throwable e) {
			}
		}

		if (m.getRowCount() > 1 && m.getColumnCount() > 1) {
			try {
				Class.forName("org.math.plot.PlotPanel");
				Class<?> c = Class.forName("org.ujmp.jmathplot.JMathPlotXYPanel");
				Constructor<?> con = c.getConstructor(Matrix.class);
				JPanel panel = (JPanel) con.newInstance(m.getMatrix());
				tabbedPane.add("XY Plot", panel);
			} catch (Throwable e) {
			}
		}

		if (m.getRowCount() > 1 && m.getColumnCount() > 1) {
			try {
				Class.forName("org.math.plot.PlotPanel");
				Class<?> c = Class.forName("org.ujmp.jmathplot.JMathPlotScatterPanel");
				Constructor<?> con = c.getConstructor(Matrix.class);
				JPanel panel = (JPanel) con.newInstance(m.getMatrix());
				tabbedPane.add("Scatter Plot", panel);
			} catch (Throwable e) {
			}
		}

		if (m.getRowCount() > 1 && m.getColumnCount() > 1) {
			try {
				Class.forName("org.math.plot.PlotPanel");
				Class<?> c = Class.forName("org.ujmp.jmathplot.JMathPlotBarPanel");
				Constructor<?> con = c.getConstructor(Matrix.class);
				JPanel panel = (JPanel) con.newInstance(m.getMatrix());
				tabbedPane.add("Bar Plot", panel);
			} catch (Throwable e) {
			}
		}

		if (m.getRowCount() > 1 && m.getColumnCount() > 1) {
			try {
				Class.forName("org.math.plot.PlotPanel");
				Class<?> c = Class.forName("org.ujmp.jmathplot.JMathPlotHistogramPanel");
				Constructor<?> con = c.getConstructor(Matrix.class);
				JPanel panel = (JPanel) con.newInstance(m.getMatrix());
				tabbedPane.add("Histogram", panel);
			} catch (Throwable e) {
			}
		}

		if (m.getRowCount() > 1 && m.getColumnCount() > 1) {
			try {
				Class.forName("org.math.plot.PlotPanel");
				Class<?> c = Class.forName("org.ujmp.jmathplot.JMathPlotStaircasePanel");
				Constructor<?> con = c.getConstructor(Matrix.class);
				JPanel panel = (JPanel) con.newInstance(m.getMatrix());
				tabbedPane.add("Staircase Plot", panel);
			} catch (Throwable e) {
			}
		}

		if (m.getRowCount() > 1 && m.getColumnCount() > 1) {
			try {
				Class.forName("org.math.plot.PlotPanel");
				Class<?> c = Class.forName("org.ujmp.jmathplot.JMathPlotGridPanel");
				Constructor<?> con = c.getConstructor(Matrix.class);
				JPanel panel = (JPanel) con.newInstance(m.getMatrix());
				tabbedPane.add("Grid Plot", panel);
			} catch (Throwable e) {
			}
		}

		if (m.getColumnCount() >= 3) {
			try {

				Class.forName("org.math.plot.PlotPanel");
				Class<?> c = Class.forName("org.ujmp.jmathplot.JMathPlotBar3DPanel");
				Constructor<?> con = c.getConstructor(Matrix.class);
				JPanel panel = (JPanel) con.newInstance(m.getMatrix());
				tabbedPane.add("Bar Plot 3D", panel);
			} catch (Throwable e) {
			}
		}

		try {
			if (m.getColumnCount() >= 3) {
				Class<?> c = Class.forName("org.ujmp.jmathplot.JMathPlotScatter3DPanel");
				Constructor<?> con = c.getConstructor(Matrix.class);
				JPanel panel = (JPanel) con.newInstance(m.getMatrix());
				tabbedPane.add("Scatter Plot 3D", panel);
			}
		} catch (Throwable e) {
		}

		try {
			if (m.getColumnCount() >= 3) {
				Class.forName("org.math.plot.PlotPanel");
				Class<?> c = Class.forName("org.ujmp.jmathplot.JMathPlotLine3DPanel");
				Constructor<?> con = c.getConstructor(Matrix.class);
				JPanel panel = (JPanel) con.newInstance(m.getMatrix());
				tabbedPane.add("Line Plot 3D", panel);
			}
		} catch (Throwable e) {
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

		// try {
		// Class<?> jfreechart = Class
		// .forName("org.ujmp.jfreechart.MatrixChartPanel");
		// JPanel panel = (JPanel) jfreechart.getConstructor(
		// MatrixGUIObject.class).newInstance(m);
		// splitPane2.setTopComponent(new BufferedPanel(panel));
		// } catch (Throwable t) {
		// }

		splitPane2.setBottomComponent(tabbedPane);
		splitPane1.setLeftComponent(splitPane2);

		splitPane1.setRightComponent(new MatrixEditorPanel(m));

		add(splitPane1, BorderLayout.CENTER);

		splitPane1.setDividerLocation(600);
		splitPane2.setDividerLocation(200);
	}
}

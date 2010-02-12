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

package org.ujmp.jfreechart;

import java.awt.Dimension;

import javax.swing.UIManager;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.StringUtil;
import org.ujmp.gui.MatrixGUIObject;

public class MatrixChartPanel extends ChartPanel {
	private static final long serialVersionUID = 3661988250162505586L;

	// private Matrix matrix = null;

	// private XYPlot plot = null;

	public MatrixChartPanel(Matrix m) {
		this((MatrixGUIObject) m.getGUIObject());
	}

	public MatrixChartPanel(MatrixGUIObject m) {
		super(null, true);

		// this.matrix = m;

		// ((MatrixGUIObject) m.getGUIObject()).getRowSelectionModel()
		// .addListSelectionListener(this);

		// addComponentListener(this);

		// updatePopupMenu();

		XYSeriesCollectionWrapper dataset = new XYSeriesCollectionWrapper(m);

		// dataset.addChangeListener(this);

		// if (showBorder) {
		// setBorder(BorderFactory.createTitledBorder("Chart"));
		// }

		setPreferredSize(new Dimension(800, 600));

		setMaximumDrawWidth(2000);
		setMaximumDrawHeight(2000);

		String title = m.getLabel();
		String xLabel = StringUtil.format(m.getMatrix().getAxisAnnotation(
				Matrix.ROW));
		String yLabel = null;

		final JFreeChart chart = ChartFactory.createXYLineChart(title, xLabel,
				yLabel, dataset, PlotOrientation.VERTICAL, true, true, false);

		chart.setTitle((String) null);

		chart.setBackgroundPaint(UIManager.getColor("Panel.background"));
		XYPlot plot = chart.getXYPlot();
		// plot.setBackgroundPaint(new Color(206, 203, 186));
		// plot.setDomainGridlinePaint(Color.white);
		// plot.setRangeGridlinePaint(Color.white);
		// plot.setDomainCrosshairVisible(false);
		// plot.setRangeCrosshairVisible(false);
		// plot.addChangeListener(this);
		//
		// zeroMarker.setPaint(new Color(0, 0, 0, 128));
		// plot.addRangeMarker(zeroMarker, Layer.FOREGROUND);
		//
		// try {
		// plot.addRangeMarker(dataset.getMeanMarker(0));
		// plot.addRangeMarker(dataset.getStandardDeviationMarker(0));
		// plot.addRangeMarker(dataset.getMinMaxMarker(0));
		// } catch (Exception e) {
		// System.out.println("error in VariableChartPanel");
		// }

		// rangeSelection.setPaint(new Color(200, 200, 235, 128));
		// rangeSelection.setLabelPaint(new Color(0, 0, 0));
		// rangeSelection.setLabelAnchor(RectangleAnchor.TOP);
		// rangeSelection.setLabelTextAnchor(TextAnchor.TOP_CENTER);
		// rangeSelection.setOutlinePaint(new Color(50, 50, 235));
		// plot.addDomainMarker(rangeSelection, Layer.FOREGROUND);

		// legend = chart.getLegend();
		// chart.clearSubtitles();

		setChart(chart);
		setMouseZoomable(false);
	}

}

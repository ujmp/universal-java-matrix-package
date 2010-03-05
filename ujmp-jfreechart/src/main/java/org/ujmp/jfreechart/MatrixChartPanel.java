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

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.XYDataset;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.StringUtil;
import org.ujmp.gui.MatrixGUIObject;

public class MatrixChartPanel extends AbstractChartPanel {
	private static final long serialVersionUID = 3661988250162505586L;

	public MatrixChartPanel(Matrix m) {
		this((MatrixGUIObject) m.getGUIObject());
	}

	public MatrixChartPanel(MatrixGUIObject guiObject) {
		this(guiObject, new ChartConfiguration());
	}

	public MatrixChartPanel(Matrix m, ChartConfiguration config) {
		this((MatrixGUIObject) m.getGUIObject(), config);
	}

	public MatrixChartPanel(MatrixGUIObject matrix, ChartConfiguration config) {
		super(matrix, config);
	}

	public synchronized void redraw() {
		Dataset dataset = null;
		dataset = new XYSeriesCollectionWrapper(getMatrix());
		// dataset = new CategoryDatasetWrapper(getMatrix());

		String title = getMatrix().getLabel();
		String xLabel = StringUtil.format(getMatrix().getMatrix()
				.getAxisAnnotation(Matrix.ROW));
		String yLabel = null;

		// setChart(ChartFactory.createLineChart(title, xLabel, yLabel,
		// (CategoryDataset) dataset, PlotOrientation.VERTICAL, true,
		// true, false));
		setChart(ChartFactory.createXYLineChart(title, xLabel, yLabel,
				(XYDataset) dataset, PlotOrientation.VERTICAL, true, true,
				false));

		XYPlot plot = getChart().getXYPlot();

		if (getConfig().isLogScaleDomain()) {
			try {
				NumberAxis axis = new LogarithmicAxis(null);
				plot.setDomainAxis(axis);
			} catch (Exception e) {
				NumberAxis axis = new NumberAxis();
				plot.setDomainAxis(axis);
			}
		} else {
			NumberAxis axis = new NumberAxis();
			plot.setDomainAxis(axis);
		}

		if (getConfig().isLogScaleRange()) {
			try {
				NumberAxis axis = new LogarithmicAxis(null);
				plot.setRangeAxis(axis);
			} catch (Exception e) {
				NumberAxis axis = new NumberAxis();
				plot.setRangeAxis(axis);
			}
		} else {
			NumberAxis axis = new NumberAxis();
			plot.setRangeAxis(axis);
		}

		getChart().setTitle((String) null);

		getChart().setBackgroundPaint(Color.WHITE);

		plot.setDomainGridlinesVisible(false);
		plot.setRangeGridlinesVisible(false);

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

		renderer.setBaseShapesVisible(false);
		renderer.setDrawSeriesLineAsPath(true);
		for (int i = 0; i < getMatrix().getColumnCount(); i++) {
			renderer.setSeriesStroke(i, new BasicStroke(3));
			plot.setRenderer(i, renderer);
		}

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setDomainCrosshairVisible(false);
		plot.setRangeCrosshairVisible(false);

		plot.getRangeAxis().setAutoRange(true);
		plot.getDomainAxis().setAutoRange(true);
		plot.getDomainAxis().setUpperMargin(0);

		setMouseZoomable(false);
	}

}

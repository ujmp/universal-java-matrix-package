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
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.interfaces.CanRenderGraph;
import org.ujmp.gui.io.ExportJPEG;
import org.ujmp.gui.io.ExportPDF;

public abstract class AbstractChartPanel extends ChartPanel implements
		CanRenderGraph {
	private static final long serialVersionUID = -7609107739440534835L;

	private ChartConfiguration config = null;

	private MatrixGUIObject matrix = null;

	public AbstractChartPanel(MatrixGUIObject matrix, ChartConfiguration config) {
		super(null, true);
		this.matrix = matrix;
		this.config = config;
		setPreferredSize(new Dimension(800, 600));
		setMaximumDrawWidth(2000);
		setMaximumDrawHeight(2000);
		redraw();
	}

	public synchronized void renderGraph(Graphics2D g2d) {
		Rectangle2D r = new Rectangle2D.Double(0, 0, getWidth(), getHeight());
		try {
			redraw();
		} catch (Exception e) {
		}
		getChart().draw(g2d, r);
	}

	public synchronized void export(FileFormat fileFormat, File file) {
		JFrame frame = null;
		if (isVisible()) {
			frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setContentPane(this);
			frame.setSize(800, 600);
			frame.setVisible(true);
			try {
				redraw();
				repaint();
			} catch (Exception e) {
			}
		}
		if (FileFormat.JPG.equals(fileFormat)) {
			try {
				ExportJPEG.save(file, this);
			} catch (Exception e) {
			}
		} else if (FileFormat.PDF.equals(fileFormat)) {
			try {
				ExportPDF.save(file, this);
			} catch (Exception e) {
			}
		} else {
			throw new MatrixException("FileFormat not yet supported: "
					+ fileFormat);
		}
		if (frame != null) {
			frame.setVisible(false);
			frame = null;
		}
	}

	// public synchronized void redraw() {

	// ((MatrixGUIObject) m.getGUIObject()).getRowSelectionModel()
	// .addListSelectionListener(this);

	// addComponentListener(this);

	// updatePopupMenu();

	// dataset.addChangeListener(this);

	// if (showBorder) {
	// setBorder(BorderFactory.createTitledBorder("Chart"));
	// }

	// chart.setBackgroundPaint(UIManager.getColor("Panel.background"));

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
	// }

	public abstract void redraw();

	public synchronized ChartConfiguration getConfig() {
		return config;
	}

	public synchronized void setConfig(ChartConfiguration config) {
		this.config = config;
		redraw();
	}

	public MatrixGUIObject getMatrix() {
		return matrix;
	}

	public synchronized void setMatrix(MatrixGUIObject matrix) {
		this.matrix = matrix;
		redraw();
	}

}

/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.ujmp.jmathplot;

import java.awt.BorderLayout;

import org.math.plot.Plot2DPanel;
import org.ujmp.core.Matrix;

public class JMathPlotHistogramPanel extends AbstractJMathPlotPanel {
	private static final long serialVersionUID = 6167298417945009767L;

	private final int bins = 10;

	public JMathPlotHistogramPanel(Matrix matrix) {
		super(matrix);
	}

	
	public void repaintUI() {
		Matrix matrix = getMatrix();
		Plot2DPanel panel = new Plot2DPanel();
		for (int c = 0; c < matrix.getColumnCount(); c++) {
			double[] y = new double[(int) matrix.getRowCount()];
			for (int r = 0; r < matrix.getRowCount(); r++) {
				y[r] = matrix.getAsDouble(r, c);
			}
			String colName = matrix.getColumnLabel(c);
			colName = colName == null ? "Column " + c : colName;
			panel.addHistogramPlot(colName, y, bins);
		}
		panel.setAxisLabels(new String[] { "Value", "Frequency" });
		setLayout(new BorderLayout());
		panel.addLegend("SOUTH");
		add(panel, BorderLayout.CENTER);
		setPanel(panel);
		getParent().repaint();
	}

}

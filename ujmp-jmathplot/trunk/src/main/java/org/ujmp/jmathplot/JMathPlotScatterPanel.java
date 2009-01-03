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

public class JMathPlotScatterPanel extends AbstractJMathPlotPanel {
	private static final long serialVersionUID = -4535556358042506549L;

	public JMathPlotScatterPanel(Matrix matrix) {
		super(matrix);
	}

	@Override
	public void repaintUI() {
		Matrix matrix = getMatrix();

		String xLabel = matrix.getColumnLabel(0);
		xLabel = xLabel == null ? "Column 0" : xLabel;
		String yLabel = null;
		if (matrix.getColumnCount() == 2) {
			yLabel = matrix.getColumnLabel(1);
		}
		yLabel = yLabel == null ? "Value" : yLabel;

		Plot2DPanel panel = new Plot2DPanel();
		double[] x = new double[(int) getMatrix().getRowCount()];
		for (int r = 0; r < matrix.getRowCount(); r++) {
			x[r] = matrix.getAsDouble(r, 0);
		}
		for (int c = 1; c < matrix.getColumnCount(); c++) {
			double[] y = new double[(int) matrix.getRowCount()];
			for (int r = 0; r < matrix.getRowCount(); r++) {
				y[r] = matrix.getAsDouble(r, c);
			}
			String colLabel = matrix.getColumnLabel(c);
			colLabel = colLabel == null ? "Column " + c : colLabel;
			panel.addScatterPlot(colLabel, x, y);
		}

		panel.setAxisLabels(new String[] { xLabel, yLabel });
		panel.addLegend("SOUTH");
		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
		setPanel(panel);
		getParent().repaint();
	}

}

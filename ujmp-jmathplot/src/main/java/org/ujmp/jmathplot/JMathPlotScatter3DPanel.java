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

package org.ujmp.jmathplot;

import java.awt.BorderLayout;

import org.math.plot.Plot3DPanel;
import org.ujmp.core.Matrix;

public class JMathPlotScatter3DPanel extends AbstractJMathPlotPanel {
	private static final long serialVersionUID = -63631417483257727L;

	public JMathPlotScatter3DPanel(Matrix matrix) {
		super(matrix);
	}

	
	public void repaintUI() {
		Matrix matrix = getMatrix();
		Plot3DPanel panel = new Plot3DPanel();
		double[] x = new double[(int) getMatrix().getRowCount()];
		for (int r = 0; r < matrix.getRowCount(); r++) {
			x[r] = matrix.getAsDouble(r, 0);
		}
		double[] y = new double[(int) getMatrix().getRowCount()];
		for (int r = 0; r < matrix.getRowCount(); r++) {
			y[r] = matrix.getAsDouble(r, 1);
		}
		for (int c = 2; c < matrix.getColumnCount(); c++) {
			double[] z = new double[(int) matrix.getRowCount()];
			for (int r = 0; r < matrix.getRowCount(); r++) {
				z[r] = matrix.getAsDouble(r, c);
			}
			panel.addScatterPlot("Column " + c, x, y, z);
		}
		panel.setAxisLabels(new String[] { "Column 0", "Column 1", "Value" });
		panel.addLegend("SOUTH");
		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
		setPanel(panel);
		getParent().repaint();
	}

}

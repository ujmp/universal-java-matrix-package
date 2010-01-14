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

package org.ujmp.jmathplot;

import java.awt.BorderLayout;

import org.math.plot.Plot3DPanel;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.MathUtil;

public class JMathPlotGridPanel extends AbstractJMathPlotPanel {
	private static final long serialVersionUID = 8213318827244466938L;

	public JMathPlotGridPanel(Matrix matrix) {
		super(matrix);
	}

	
	public void repaintUI() {
		Matrix matrix = getMatrix();
		Plot3DPanel panel = new Plot3DPanel();
		double[] y = MathUtil.sequenceDouble(0, matrix.getRowCount() - 1);
		double[] x = MathUtil.sequenceDouble(0, matrix.getColumnCount() - 1);
		panel.addGridPlot("Grid", x, y, matrix.toDoubleArray());
		panel.setAxisLabels(new String[] { "Column", "Row", "Value" });
		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
		setPanel(panel);
		getParent().repaint();
	}

}

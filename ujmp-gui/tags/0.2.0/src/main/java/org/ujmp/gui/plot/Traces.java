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

package org.ujmp.gui.plot;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Traces {

	private PlotSettings plotSettings = null;

	public Traces(PlotSettings plotSettings) {
		this.plotSettings = plotSettings;
	}

	public void paintComponent(Graphics g) {
		try {
			long t0 = System.currentTimeMillis();

			Graphics2D g2d = (Graphics2D) g;

			double xf = plotSettings.getXFactor();
			double yf = plotSettings.getYFactor();

			for (int t = 0; t < Math.min(10, plotSettings.getMatrixGUIObject().getColumnCount()); t++) {

				if (plotSettings.isShowTrace(t)) {

					long column = t;

					g2d.setStroke(plotSettings.getTraceStroke(t));
					g2d.setColor(plotSettings.getTraceColor(t));

					double xs = plotSettings.getXStepSize();

					long dots = 0;
					for (double xr = plotSettings.getMinXValue() + xs; xr <= plotSettings.getMaxXValue(); xr += xs) {
						dots++;
						long row1 = (long) (xr - xs);
						long row2 = (long) xr;

						double yv1 = plotSettings.getMatrixGUIObject().getDoubleValueAt(row1, column);
						double yv2 = plotSettings.getMatrixGUIObject().getDoubleValueAt(row2, column);

						int x1 = (int) ((xr - xs) * xf);
						int x2 = (int) (xr * xf);
						x2 = (x2 == x1) ? x2++ : x2;

						int y1 = (int) (plotSettings.getHeight() - 1 - yv1 * yf + plotSettings.getMinYValue() * yf);
						int y2 = (int) (plotSettings.getHeight() - 1 - yv2 * yf + plotSettings.getMinYValue() * yf);

						g2d.drawLine(x1, y1, x2, y2);
					}

				}

				long t1 = System.currentTimeMillis();
				if (t1 - t0 > plotSettings.getTimeLimit()) {
					return;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

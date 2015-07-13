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

package org.ujmp.gui.plot;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class RunningAveragePlot {

	private PlotSettings plotSettings = null;

	public RunningAveragePlot(PlotSettings plotSettings) {
		this.plotSettings = plotSettings;
	}

	public void paintComponent(Graphics g) {
		try {
			long t0 = System.currentTimeMillis();

			Graphics2D g2d = (Graphics2D) g;

			double xf = plotSettings.getXFactor();
			double yf = plotSettings.getYFactor();

			if (plotSettings.getMatrixGUIObject().getRowCount() < 2) {
				return;
			}

			for (int t = 0; t < Math.min(10, plotSettings.getMatrixGUIObject().getColumnCount()); t++) {

				if (plotSettings.isShowTrace(t)) {

					long column = t;

					g2d.setStroke(plotSettings.getRunningAverageStroke());
					g2d.setColor(plotSettings.getRunningAverageLineColor());

					double xs = plotSettings.getXStepSize();

					double sum = plotSettings.getMatrixGUIObject().getMatrix()
							.getAsDouble((long) plotSettings.getMinXValue(), column);
					double average = sum;
					double oldAverage = average;
					double firstPoint = plotSettings.getMinXValue();
					double nmbOfPoints = 1;
					long dots = 0;

					for (double xr = plotSettings.getMinXValue() + xs; xr <= plotSettings.getMaxXValue(); xr += xs) {
						dots++;
						long row1 = (long) (xr - xs);
						long row2 = (long) xr;
						long rowRA = (long) (firstPoint);

						double yv1 = plotSettings.getMatrixGUIObject().getMatrix().getAsDouble(row1, column);
						double yv2 = plotSettings.getMatrixGUIObject().getMatrix().getAsDouble(row2, column);
						double yvRA = plotSettings.getMatrixGUIObject().getMatrix().getAsDouble(rowRA, column);

						sum += yv2;
						nmbOfPoints++;
						if (nmbOfPoints > (plotSettings.getRunningAverageLength()) / xs) {
							sum = sum - yvRA;
							firstPoint += xs;
							nmbOfPoints--;
						}
						average = sum / nmbOfPoints;

						int x1 = (int) ((xr - xs) * xf);
						int x2 = (int) (xr * xf);
						x2 = (x2 == x1) ? x2++ : x2;

						int y1 = (int) (plotSettings.getHeight() - 1 - oldAverage * yf + plotSettings.getMinYValue()
								* yf);
						int y2 = (int) (plotSettings.getHeight() - 1 - average * yf + plotSettings.getMinYValue() * yf);

						g2d.drawLine(x1, y1, x2, y2);
						oldAverage = average;
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

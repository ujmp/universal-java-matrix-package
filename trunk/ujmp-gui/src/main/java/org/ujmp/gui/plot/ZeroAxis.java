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

package org.ujmp.gui.plot;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class ZeroAxis {

	private PlotSettings plotSettings = null;

	public ZeroAxis(PlotSettings plotSettings) {
		this.plotSettings = plotSettings;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		double yf = plotSettings.getYFactor();
		int x1 = 0;
		int x2 = plotSettings.getWidth() - 1;
		int y1 = (int) (plotSettings.getHeight() - 1 + plotSettings.getMinYValue() * yf);
		int y2 = y1;

		g2d.setStroke(plotSettings.getZeroAxisStroke());
		g2d.setColor(plotSettings.getZeroAxisColor());
		g2d.drawLine(x1, y1, x2, y2);
	}

}

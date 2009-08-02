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

public class YAxis {

	private PlotSettings plotSettings = null;

	public YAxis(PlotSettings plotSettings) {
		this.plotSettings = plotSettings;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int x1 = 0;
		int y1 = plotSettings.getHeight() - 1;
		int x2 = 0;
		int y2 = 0;
		g2d.setStroke(plotSettings.getAxisStroke());
		g2d.setColor(plotSettings.getAxisColor());
		g2d.drawLine(x1, y1, x2, y2);
	}

}

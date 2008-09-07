/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

package org.ujmp.gui.matrix.plot;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ListSelectionModel;

public class Selection {

	private PlotSettings plotSettings = null;

	public Selection(PlotSettings plotSettings) {
		this.plotSettings = plotSettings;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		ListSelectionModel lsm = plotSettings.getMatrixGUIObject().getRowSelectionModel();
		int min = lsm.getMinSelectionIndex();
		int max = lsm.getMaxSelectionIndex();

		if (min != -1 && max != -1) {
			double xf = plotSettings.getXFactor();

			int y1 = 0;
			int y2 = plotSettings.getHeight() - 1;
			int h = y2 - y1;

			int x1 = (int) (min * xf);
			int x2 = (int) (max * xf);
			int w = x2 - x1;

			g2d.setColor(plotSettings.getSelectionLineColor());
			g2d.drawLine(x1, y1, x1, y2);
			g2d.drawLine(x2, y1, x2, y2);
			g2d.setColor(plotSettings.getSelectionColor());
			if (w != 0) {
				g2d.fillRect(x1, y1, w, h);
			}
		}

	}

}

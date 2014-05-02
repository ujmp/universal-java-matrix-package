/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.ujmp.gui.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.ujmp.gui.AbstractMatrixGUIObject;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.panels.AbstractPanel;
import org.ujmp.gui.util.UIDefaults;

public class GraphPanel extends AbstractPanel {
	private static final long serialVersionUID = 4424717069537457440L;

	public GraphPanel(MatrixGUIObject o) {
		super(o);
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.addRenderingHints(UIDefaults.AALIAS);
		AbstractMatrixGUIObject m = (AbstractMatrixGUIObject) getGUIObject();
		final int totalCount = m.getColumnCount();

		Color color = new Color(0, 0, 200, 128);

		final int size = getCircleSize(totalCount);
		final int xCount = getSize().width / size;

		for (int index = 0; index < totalCount; index++) {
			g2d.setColor(color);
			g2d.fillOval(size * (index % xCount), size * (index / xCount), size / 2, size / 2);
		}

		for (long[] c : m.getMatrix().availableCoordinates()) {
			g2d.setColor(color);
			g2d.drawLine((int) (size * (c[0] % xCount)), (int) (size * (c[0] / xCount)),
					(int) (size * (c[1] % xCount)), (int) (size * (c[1] / xCount)));
		}
	}

	public final int getXPos(int totalCount, int index) {
		return (getSize().height / totalCount) * index;
	}

	public final int getCircleSize(int totalCount) {
		int area = getSize().height * getSize().width;
		int size = (int) (Math.sqrt(area) / Math.sqrt(totalCount) / 1.1);
		size = Math.max(size, 1);
		return size;
	}
}

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

package org.ujmp.gui.statusbar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JProgressBar;

import org.ujmp.core.util.UJMPTimer;

public class MemoryUsage extends JProgressBar {
	private static final long serialVersionUID = 5692292627429288637L;

	private int used = 0;

	private final UJMPTimer timer;

	public MemoryUsage() {
		setBorder(BorderFactory.createEtchedBorder());
		setMinimumSize(new Dimension(50, 30));
		timer = UJMPTimer.newInstance();
		timer.schedule(new UpdateTask(this), 0, 1000);
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		String s = used + "MB";
		int stringWidth = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
		int stringHeight = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getHeight();
		int x = (getWidth() - stringWidth) / 2;
		int y = (getHeight() - stringHeight) / 2;
		y = ((getHeight() - g2d.getFontMetrics().getHeight()) / 2) + g2d.getFontMetrics().getAscent();
		g2d.setColor(Color.darkGray);
		g2d.drawString(s, x, y);
	}

	public void update() {
		int max = (int) Runtime.getRuntime().maxMemory() / 1048576;
		int total = (int) Runtime.getRuntime().totalMemory() / 1048576;
		int free = (int) Runtime.getRuntime().freeMemory() / 1048576;
		used = total - free;
		setMinimum(0);
		setMaximum(max);
		setValue(used);
		setToolTipText("" + used + "MB of " + max + "MB used");
	}

	class UpdateTask extends TimerTask {

		private MemoryUsage memoryUsage = null;

		public UpdateTask(MemoryUsage memoryUsage) {
			this.memoryUsage = memoryUsage;
		}

		public void run() {
			memoryUsage.update();
		}

	}

}

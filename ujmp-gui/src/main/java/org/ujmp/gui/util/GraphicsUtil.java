/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

package org.ujmp.gui.util;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.Window;

public abstract class GraphicsUtil {
	public static final int ALIGNCENTER = 0;

	public static final int ALIGNLEFT = 1;

	public static final int ALIGNRIGHT = 2;

	public static final int ALIGNBOTTOM = 3;

	public static final int ALIGNTOP = 4;

	public static final float[] DASHPATTERN10 = { 1f, 1f };

	public static final float[] DASHPATTERN01 = { 0.1f, 0.1f };

	public static final Stroke DASHEDSTROKE = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
			0.0f, DASHPATTERN10, 0);

	public static final void drawString(Graphics2D g2d, double xPos, double yPos, int xAlign, int yAlign, String s) {
		FontMetrics fm = g2d.getFontMetrics(g2d.getFont());
		if ((xAlign == ALIGNCENTER) && (yAlign == ALIGNCENTER)) {
			g2d.drawString(s, (float) (xPos - (fm.getStringBounds(s, g2d).getWidth() / 2.0)), (float) (yPos + (g2d
					.getFont().getSize2D() / 2.0)));
		} else if ((xAlign == ALIGNCENTER) && (yAlign == ALIGNTOP)) {
			g2d.drawString(s, (float) (xPos - (fm.getStringBounds(s, g2d).getWidth() / 2.0)),
					(float) (yPos + (g2d.getFont().getSize2D())));
		} else if ((xAlign == ALIGNCENTER) && (yAlign == ALIGNBOTTOM)) {
			g2d.drawString(s, (float) (xPos), (float) (yPos));
		} else if ((xAlign == ALIGNLEFT) && (yAlign == ALIGNCENTER)) {
			g2d.drawString(s, (float) (xPos), (float) (yPos + (g2d.getFont().getSize2D() / 2.0)));
		} else if ((xAlign == ALIGNLEFT) && (yAlign == ALIGNTOP)) {
			g2d.drawString(s, (float) (xPos), (float) (yPos + (g2d.getFont().getSize2D())));
		} else if ((xAlign == ALIGNLEFT) && (yAlign == ALIGNBOTTOM)) {
			g2d.drawString(s, (float) (xPos), (float) (yPos));
		} else if ((xAlign == ALIGNRIGHT) && (yAlign == ALIGNCENTER)) {
			g2d.drawString(s, (float) (xPos - (fm.getStringBounds(s, g2d).getWidth())), (float) (yPos + (g2d.getFont()
					.getSize2D() / 2.0)));
		} else if ((xAlign == ALIGNRIGHT) && (yAlign == ALIGNTOP)) {
			g2d.drawString(s, (float) (xPos - (fm.getStringBounds(s, g2d).getWidth())),
					(float) (yPos + (g2d.getFont().getSize2D())));
		} else if ((xAlign == ALIGNRIGHT) && (yAlign == ALIGNBOTTOM)) {
			g2d.drawString(s, (float) (xPos - (fm.getStringBounds(s, g2d).getWidth())), (float) (yPos));
		} else {
			// error
		}
	}

	public static final void centerOnScreen(Window w) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = w.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		w.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}

	public static final void drawString(Graphics2D g2d, double xPos, double yPos, int xAlign, String s) {
		GraphicsUtil.drawString(g2d, xPos, yPos, xAlign, GraphicsUtil.ALIGNCENTER, s);
	}

	public static final void drawString(Graphics2D g2d, double xPos, double yPos, String s) {
		GraphicsUtil.drawString(g2d, xPos, yPos, GraphicsUtil.ALIGNCENTER, GraphicsUtil.ALIGNCENTER, s);
	}

	public static final void drawString(Graphics2D g2d, int xPos, int yPos, String s) {
		GraphicsUtil.drawString(g2d, xPos, yPos, GraphicsUtil.ALIGNCENTER, GraphicsUtil.ALIGNCENTER, s);
	}
}

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

package org.ujmp.gui.matrix;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import org.ujmp.core.collections.DefaultMatrixList;
import org.ujmp.gui.util.ColorUtil;

public class Matrix3DTableCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 4107015466785369684L;

	private static final Logger logger = Logger.getLogger(Matrix3DTableCellRenderer.class.getName());

	protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

	private Color unselectedForeground;

	private Color unselectedBackground;

	private DefaultMatrixList timeSeries = null;

	private BufferedImage bufferedImage = null;

	private final TimeSeriesImageObserver timeSeriesImageObserver = new TimeSeriesImageObserver();

	private int WIDTH = 0;

	private int HEIGHT = 0;

	private int PADDINGX = 0;

	private int PADDINGY = 0;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		if (value instanceof DefaultMatrixList)
			timeSeries = (DefaultMatrixList) value;
		else
			return null;

		PADDINGX = UIManager.getInt("Table.paddingX");
		PADDINGY = UIManager.getInt("Table.paddingY");
		WIDTH = table.getColumnModel().getColumn(column).getWidth() - (2 * PADDINGX) - 1;
		HEIGHT = table.getRowHeight(row) - (2 * PADDINGY) - 1;

		if (isSelected) {
			super.setForeground(table.getSelectionForeground());
			super.setBackground(table.getSelectionBackground());
		} else {
			super.setForeground((unselectedForeground != null) ? unselectedForeground : table.getForeground());
			super.setBackground((unselectedBackground != null) ? unselectedBackground : table.getBackground());
		}

		setFont(table.getFont());

		if (hasFocus) {
			Border border = null;
			if (isSelected) {
				border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
			}
			if (border == null) {
				border = UIManager.getBorder("Table.focusCellHighlightBorder");
			}
			setBorder(border);

			if (!isSelected && table.isCellEditable(row, column)) {
				Color col;
				col = UIManager.getColor("Table.focusCellForeground");
				if (col != null) {
					super.setForeground(col);
				}
				col = UIManager.getColor("Table.focusCellBackground");
				if (col != null) {
					super.setBackground(col);
				}
			}
		} else {
			setBorder(noFocusBorder);
		}

		return this;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (timeSeries == null) {
			Graphics2D g2d = (Graphics2D) g;
			if (g2d != null) {
				g2d.setColor(Color.RED);
				g2d.drawLine(0, 0, WIDTH - 1, HEIGHT - 1);
				g2d.drawLine(WIDTH - 1, 0, 0, HEIGHT - 1);
				g2d.dispose();
			}
		} else {
			paintTimeSeries(g, timeSeries);
		}
	}

	public void paintTimeSeries(Graphics g, DefaultMatrixList timeSeries) {
		bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		boolean useOwnTime = false;
		try {
			Graphics bg = bufferedImage.getGraphics();

			bg.setColor(Color.LIGHT_GRAY);
			bg.fillRect(0, 0, WIDTH, HEIGHT);

			double timeStart = timeSeries.getMinTime();
			double timeEnd = timeSeries.getMaxTime();
			double totalTime = Math.abs(timeEnd - timeStart);
			if (timeStart == 0 && totalTime == 0.0 && timeSeries.getMatrixCount() != 0) {
				totalTime = timeSeries.getMatrixCount();
				timeEnd = totalTime;
				useOwnTime = true;
			}
			double pixelPerTimeStep = WIDTH / totalTime;
			int timeStepSize = (int) Math.ceil(Double.MIN_VALUE + 1.0 / pixelPerTimeStep);

			double minValue = -3;
			double maxValue = 3;
			double totalValues = maxValue - minValue;
			double pixelPerValue = HEIGHT / totalValues;

			int x1 = 0;
			int x2 = WIDTH;
			int y1 = (int) ((0 - minValue) * pixelPerValue);
			int y2 = y1;
			bg.setColor(Color.GRAY);
			bg.drawLine(x1, HEIGHT - y1 - 1, x2, HEIGHT - y2 - 1);

			for (int i = 0; i < ColorUtil.TRACECOLORS.length && i < timeSeries.getTraceCount(); i++) {
				double[][] trace = timeSeries.getTrace(i);
				if (trace != null) {
					bg.setColor(ColorUtil.TRACECOLORS[i]);
					for (int x = 1; x < trace.length; x += timeStepSize) {
						if (useOwnTime) {
							x1 = (int) ((x - timeStart) * pixelPerTimeStep);
							x2 = (int) ((x - 1 - timeStart) * pixelPerTimeStep);
						} else {
							x1 = (int) ((trace[x][0] - timeStart) * pixelPerTimeStep);
							x2 = (int) ((trace[x - 1][0] - timeStart) * pixelPerTimeStep);
						}
						y1 = (int) ((trace[x][1] - minValue) * pixelPerValue);
						y2 = (int) ((trace[x - 1][1] - minValue) * pixelPerValue);
						if (x1 > 0 && x1 < 10000 && x2 > 0 && x2 < 10000 && y1 > 0 && y1 < 10000 && y2 > 0
								&& y2 < 10000) {
							bg.drawLine(x1, HEIGHT - y1 - 1, x2, HEIGHT - y2 - 1);
						}
					}
					// for (int x = 1; x < trace.length; x++) {
					// x1 = (int) ((trace[x][0] - timeStart) *
					// pixelPerTimeStep);
					// x2 = (int) ((trace[x - 1][0] - timeStart) *
					// pixelPerTimeStep);
					// y1 = (int) ((trace[x][1] - minValue) * pixelPerValue);
					// y2 = (int) ((trace[x - 1][1] - minValue) *
					// pixelPerValue);
					// bg.drawLine(x1, HEIGHT - y1 - 1, x2, HEIGHT - y2 - 1);
					// }
				}
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not paint time series", e);
		}
		g.drawImage(bufferedImage, PADDINGX, PADDINGY, WIDTH, HEIGHT, timeSeriesImageObserver);
	}
}

class TimeSeriesImageObserver implements ImageObserver {

	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		return false;
	}

}

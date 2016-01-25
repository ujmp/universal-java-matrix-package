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

package org.ujmp.gui.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ConcurrentModificationException;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

import org.ujmp.core.Matrix;
import org.ujmp.core.util.ColorUtil;
import org.ujmp.core.util.UJMPFormat;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.util.GraphicsUtil;
import org.ujmp.gui.util.UIDefaults;

public class MatrixHeatmapRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 942689931503793487L;

	private MatrixGUIObject matrixGUIObject = null;

	// private static final int PADDINGX = UIManager.getInt("Table.paddingX");
	// private static final int PADDINGY = UIManager.getInt("Table.paddingY");

	private static final int PADDINGX = 1;
	private static final int PADDINGY = 1;

	private final Border borderSelected = BorderFactory.createLineBorder(Color.blue, 1);

	public MatrixHeatmapRenderer() {
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (value instanceof MatrixGUIObject) {
			matrixGUIObject = (MatrixGUIObject) value;
		} else if (value instanceof Matrix) {
			matrixGUIObject = (MatrixGUIObject) ((Matrix) value).getGUIObject();
		} else {
			matrixGUIObject = null;
		}

		setSize(table.getColumnModel().getColumn(column).getWidth() - 1, table.getRowHeight(row) - 1);

		if (isSelected) {
			super.setForeground(table.getSelectionForeground());
			super.setBackground(table.getSelectionBackground());
			super.setBorder(borderSelected);
		} else {
			super.setForeground(table.getForeground());
			super.setBackground(table.getBackground());
			super.setBorder(null);
		}

		setFont(table.getFont());

		return this;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(getBackground());
		g2d.fillRect(0, 0, getWidth(), getHeight());

		int width = getWidth();
		int height = getHeight();
		width = width == 0 ? 1 : width;
		height = height == 0 ? 1 : height;

		try {
			if (matrixGUIObject != null) {

				long totalColumn = matrixGUIObject.getColumnCount();
				long totalRows = matrixGUIObject.getRowCount();

				if (totalColumn < 1 || totalRows < 1) {
					g2d.setColor(Color.GRAY);
					g2d.fillRect(PADDINGX, PADDINGY, width - PADDINGX - PADDINGX, height - PADDINGY - PADDINGY);
					g2d.setColor(Color.DARK_GRAY);
					g2d.addRenderingHints(UIDefaults.AALIAS);
					String s = "";
					if (matrixGUIObject.getLabel() != null) {
						s += "[" + matrixGUIObject.getLabel() + "]";
					} else {
						s += matrixGUIObject.getMatrix().getClass().getSimpleName();
					}
					GraphicsUtil.drawString(g2d, width / 2.0, height / 2.0 - 1.0, GraphicsUtil.ALIGNCENTER,
							GraphicsUtil.ALIGNCENTER, s);
					return;
				}

				int xsize = (int) Math.min(totalColumn, width);
				int ysize = (int) Math.min(totalRows, height);
				xsize = xsize == 0 ? 1 : xsize;
				ysize = ysize == 0 ? 1 : ysize;

				double stepsizeX = (double) totalColumn / (double) width;
				double stepsizeY = (double) totalRows / (double) height;
				if (stepsizeX < 1.0) {
					stepsizeX = 1.0;
				}
				if (stepsizeY < 1.0) {
					stepsizeY = 1.0;
				}

				final BufferedImage bufferedImage = new BufferedImage(xsize, ysize, BufferedImage.TYPE_INT_RGB);
				final int[] pixels = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();

				if (stepsizeX != 1.0 || stepsizeY != 1.0) {
					int pos = 0;
					for (int y = 0; y < ysize; y++) {
						for (int x = 0; x < xsize; x++) {
							final long mx = (long) Math.floor(x * stepsizeX);
							final long my = (long) Math.floor(y * stepsizeY);
							final Color col = matrixGUIObject.getColorAt(my, mx);
							if (col != null) {
								pixels[pos++] = (col.getRed() << 16) + (col.getGreen() << 8) + col.getBlue();
							}
						}
					}
				} else {
					final long rowCount = matrixGUIObject.getRowCount();
					final long colCount = matrixGUIObject.getColumnCount();
					for (int row = 0; row < rowCount; row++) {
						for (int col = 0; col < colCount; col++) {
							final Color color = matrixGUIObject.getColorAt(row, col);
							final int pos = getPosition(totalColumn, row, col);
							if (color != null) {
								pixels[pos] = (color.getRed() << 16) + (color.getGreen() << 8) + color.getBlue();
							}
						}
					}
				}

				g2d.drawImage(bufferedImage, PADDINGX, PADDINGY, width - PADDINGX - PADDINGX, height - PADDINGY
						- PADDINGY, null);

				if (width > 20 && matrixGUIObject.getRowCount() == 1 && matrixGUIObject.getColumnCount() == 1) {
					final Color col = matrixGUIObject.getColorAt(0, 0);
					g2d.setColor(ColorUtil.contrastBW(col));
					String s = UJMPFormat.getSingleLineInstance().format(matrixGUIObject.getValueAt(0, 0));
					if (s != null && s.length() > 25) {
						s = s.substring(0, 25) + "...";
					}
					g2d.addRenderingHints(UIDefaults.AALIAS);
					GraphicsUtil.drawString(g2d, width / 2.0, height / 2.0 - 1.0, GraphicsUtil.ALIGNCENTER,
							GraphicsUtil.ALIGNCENTER, s);
				} else if (width > 20) {
					g2d.setColor(Color.DARK_GRAY);
					g2d.addRenderingHints(UIDefaults.AALIAS);
					String s = "";
					if (matrixGUIObject.getLabel() != null) {
						s += "[" + matrixGUIObject.getLabel() + "]";
					} else {
						s += matrixGUIObject.getMatrix().getClass().getSimpleName();
					}
					GraphicsUtil.drawString(g2d, width / 2.0, height / 2.0 - 1.0, GraphicsUtil.ALIGNCENTER,
							GraphicsUtil.ALIGNCENTER, s);
				}

			} else {
				g2d.setColor(Color.GRAY);
				g2d.fillRect(PADDINGX, PADDINGY, width - PADDINGX - PADDINGX, height - PADDINGY - PADDINGY);
			}
		} catch (ConcurrentModificationException e) {
			// not too bad
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int getPosition(long totalColumn, long currentRow, long currentColumn) {
		return (int) (totalColumn * currentRow + currentColumn);
	}

	public static void paintMatrix(Graphics g, MatrixGUIObject matrix, int width, int height, int paddingX, int paddingY) {
		if (g == null) {
			return;
		}

		Graphics2D g2d = (Graphics2D) g;
		g2d.addRenderingHints(UIDefaults.AALIAS);

		if (matrix == null) {
			g2d.setColor(Color.GRAY);
			g2d.drawLine(paddingX, paddingY, width - paddingX, height - paddingY);
			g2d.drawLine(width - paddingX, paddingY, paddingX, height - paddingY);
		} else {
			g2d.translate(paddingX, paddingY);
			paintMatrixOriginal(g2d, matrix, width - paddingX - paddingX, height - paddingY - paddingY);
			g2d.translate(-paddingX, -paddingX);
		}
	}

	public static void paintMatrix(Graphics g, MatrixGUIObject matrix, int width, int height) {
		paintMatrix(g, matrix, width, height, PADDINGX, PADDINGY);
	}

	private static boolean paintMatrixOriginal(Graphics g, MatrixGUIObject matrix, int width, int height) {
		boolean wasAllDataLoaded = true;
		try {
			long cols = matrix.getColumnCount();
			long rows = matrix.getRowCount();

			width = width < 1 ? 1 : width;
			height = height < 1 ? 1 : height;
			long xSize = cols > 0 ? cols : 1;
			long ySize = rows > 0 ? rows : 1;

			xSize = xSize > 0 ? xSize : 1;
			ySize = ySize > 0 ? ySize : 1;

			long xStepSize = (long) Math.ceil((double) xSize / (double) width);
			long yStepSize = (long) Math.ceil((double) ySize / (double) height);

			int imgWidth = (int) Math.ceil(((double) xSize / (double) xStepSize));
			int imgHeight = (int) Math.ceil(((double) ySize / (double) yStepSize));
			imgWidth = imgWidth > 0 ? imgWidth : 1;
			imgHeight = imgHeight > 0 ? imgHeight : 1;

			BufferedImage bufferedImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D) g;
			Graphics2D bg = (Graphics2D) bufferedImage.getGraphics();
			bg.addRenderingHints(UIDefaults.AALIAS);

			for (long col = 0; col < cols; col += xStepSize) {
				for (long row = 0; row < rows; row += yStepSize) {
					bg.setColor(matrix.getColorAt(row, col));
					bg.fillRect((int) (col / xStepSize), (int) (row / yStepSize), 1, 1);
					if (wasAllDataLoaded && matrix.getValueAt(row, col) == MatrixGUIObject.PRELOADER) {
						wasAllDataLoaded = false;
					}
				}
			}
			g2d.drawImage(bufferedImage, 0, 0, width, height, 0, 0, bufferedImage.getWidth(),
					bufferedImage.getHeight(), null);
			if (width > 20 && matrix.getRowCount() == 1 && matrix.getColumnCount() == 1) {
				String s = UJMPFormat.getSingleLineInstance().format(matrix.getValueAt(0, 0));
				if (s != null && s.length() > 25) {
					s = s.substring(0, 25) + "...";
				}
				g2d.setColor(ColorUtil.contrastBW(bg.getColor()));
				GraphicsUtil.drawString(g2d, width / 2.0, height / 2.0 - 1.0, GraphicsUtil.ALIGNCENTER,
						GraphicsUtil.ALIGNCENTER, s);
			}
		} catch (Exception e) {
			wasAllDataLoaded = false;
			e.printStackTrace();
		}
		return wasAllDataLoaded;
	}

	public void setMatrix(MatrixGUIObject matrix) {
		this.matrixGUIObject = matrix;
	}

}

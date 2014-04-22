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

package org.ujmp.gui.panels;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.HasToolTip;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.actions.MatrixActions;
import org.ujmp.gui.interfaces.CanBeRepainted;
import org.ujmp.gui.io.ExportJPEG;
import org.ujmp.gui.io.ExportPDF;
import org.ujmp.gui.io.ExportPNG;
import org.ujmp.gui.menu.MatrixPopupMenu;
import org.ujmp.gui.renderer.MatrixHeatmapRenderer;
import org.ujmp.gui.util.GraphicsExecutor;
import org.ujmp.gui.util.TooltipUtil;

public class MatrixHeatmapPanel extends JPanel implements ComponentListener, TableModelListener, MouseListener,
		MouseMotionListener, CanBeRepainted, HasToolTip, ListSelectionListener {
	private static final long serialVersionUID = 843653796010276950L;

	private final MatrixGUIObject matrixGUIObject;
	private final Matrix matrix;

	private final MatrixHeatmapRenderer renderer;

	private BufferedImage bufferedImage = null;

	private static int PADDINGX = UIManager.getInt("Table.paddingX");

	private static int PADDINGY = UIManager.getInt("Table.paddingY");

	private long startRow = 0;

	private long startCol = 0;

	public MatrixHeatmapPanel(MatrixGUIObject matrixGUIObject, boolean showBorder) {
		if (matrixGUIObject == null) {
			throw new IllegalArgumentException("matrixGUIObject is null");
		}

		if (showBorder) {
			setBorder(BorderFactory.createTitledBorder("Matrix Heatmap"));
		}

		addComponentListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		setPreferredSize(new Dimension(600, 400));

		this.matrixGUIObject = matrixGUIObject;
		this.matrix = matrixGUIObject.getMatrix();
		renderer = new MatrixHeatmapRenderer();

		matrixGUIObject.addTableModelListener(this);
		matrixGUIObject.getRowSelectionModel().addListSelectionListener(this);
		matrixGUIObject.getColumnSelectionModel().addListSelectionListener(this);

		ToolTipManager.sharedInstance().registerComponent(this);

		registerKeyboardActions();

		GraphicsExecutor.scheduleUpdate(this);
	}

	private void registerKeyboardActions() {
		for (JComponent c : new MatrixActions(this, matrixGUIObject, null)) {
			if (c instanceof JMenuItem) {
				registerKeyboardAction(((JMenuItem) c).getAction());
			}
		}
	}

	private void registerKeyboardAction(Action a) {
		// KeyStroke keyStroke = (KeyStroke) a.getValue(Action.ACCELERATOR_KEY);
		// getActionMap().put(a.getValue(Action.NAME), a);
		// getInputMap(WHEN_IN_FOCUSED_WINDOW).put(keyStroke,
		// a.getValue(Action.NAME));
	}

	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		renderer.setSize(getSize());
		GraphicsExecutor.scheduleUpdate(this);
	}

	public void componentShown(ComponentEvent e) {
	}

	public void tableChanged(TableModelEvent e) {
		GraphicsExecutor.scheduleUpdate(this);
	}

	public void mouseClicked(MouseEvent e) {
		long newRow = getRowPos(e.getY());
		long newCol = getColPos(e.getX());

		if (e.getButton() == MouseEvent.BUTTON3) {
			newRow = newRow < 0 ? 0 : newRow;
			newCol = newCol < 0 ? 0 : newCol;
			newRow = newRow >= matrix.getRowCount() ? matrix.getRowCount() - 1 : newRow;
			newCol = newCol >= matrix.getColumnCount() ? matrix.getColumnCount() - 1 : newCol;

			JPopupMenu popup = new MatrixPopupMenu(this, matrixGUIObject, newRow, newCol);
			popup.show(this, e.getX(), e.getY());
		} else if (e.getButton() == MouseEvent.BUTTON1) {
			// left click: show new window if value is a matrix
			Object o = matrix.getAsObject(newRow, newCol);
			if (o instanceof Matrix) {
				((Matrix) o).showGUI();
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			startRow = getRowPos(e.getY());
			startCol = getColPos(e.getX());
			startRow = startRow < 0 ? 0 : startRow;
			startCol = startCol < 0 ? 0 : startCol;
			startRow = startRow >= matrix.getRowCount() ? matrix.getRowCount() - 1 : startRow;
			startCol = startCol >= matrix.getColumnCount() ? matrix.getColumnCount() - 1 : startCol;
			matrixGUIObject.getRowSelectionModel().setValueIsAdjusting(true);
			matrixGUIObject.getColumnSelectionModel().setValueIsAdjusting(true);
			matrixGUIObject.getRowSelectionModel().setSelectionInterval(startRow, startRow);
			matrixGUIObject.getColumnSelectionModel().setSelectionInterval(startCol, startCol);
		}
		repaint(100);
	}

	private long getRowPos(int y) {
		return matrix.getRowCount() * y / getHeight();
	}

	private long getColPos(int x) {
		return matrix.getColumnCount() * x / getWidth();
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			long newRow = getRowPos(e.getY());
			long newCol = getColPos(e.getX());
			newRow = newRow < 0 ? 0 : newRow;
			newCol = newCol < 0 ? 0 : newCol;
			newRow = newRow >= matrix.getRowCount() ? matrix.getRowCount() - 1 : newRow;
			newCol = newCol >= matrix.getColumnCount() ? matrix.getColumnCount() - 1 : newCol;
			matrixGUIObject.getRowSelectionModel().setValueIsAdjusting(false);
			matrixGUIObject.getColumnSelectionModel().setValueIsAdjusting(false);
			matrixGUIObject.getRowSelectionModel().setSelectionInterval(startRow, newRow);
			matrixGUIObject.getColumnSelectionModel().setSelectionInterval(startCol, newCol);
			repaint(100);
		}
	}

	public MatrixGUIObject getMatrix() {
		return matrixGUIObject;
	}

	public String getToolTipText(MouseEvent e) {
		// only generate tool text when a matrix with size >0 is available
		if (!matrix.isEmpty()) {
			long r = getRowPos(e.getY());
			long c = getColPos(e.getX());
			r = r < 0 ? 0 : r;
			c = c < 0 ? 0 : c;
			r = r >= matrix.getRowCount() ? matrix.getRowCount() - 1 : r;
			c = c >= matrix.getColumnCount() ? matrix.getColumnCount() - 1 : c;

			String toolTip = TooltipUtil.getTooltip(matrix, r, c);
			return toolTip;
		} else {
			return null;
		}
	}

	protected void finalize() throws Throwable {
		super.finalize();
		if (matrixGUIObject != null) {
			matrixGUIObject.removeTableModelListener(this);
		}
		ToolTipManager.sharedInstance().unregisterComponent(this);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		if (bufferedImage != null) {
			g2d.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), null);

			if (!matrixGUIObject.getRowSelectionModel().isSelectionEmpty()) {
				g2d.setColor(Color.BLUE);

				long x1 = matrixGUIObject.getColumnSelectionModel().getMinSelectionIndex64();
				long x2 = matrixGUIObject.getColumnSelectionModel().getMaxSelectionIndex64();
				long y1 = matrixGUIObject.getRowSelectionModel().getMinSelectionIndex64();
				long y2 = matrixGUIObject.getRowSelectionModel().getMaxSelectionIndex64();
				double scaleX = (double) (getWidth() - PADDINGX - PADDINGX) / (double) matrix.getColumnCount();
				double scaleY = (double) (getHeight() - PADDINGY - PADDINGY) / (double) matrix.getRowCount();
				g2d.setStroke(new BasicStroke(2.0f));
				g2d.drawRect((int) Math.floor(PADDINGX + x1 * scaleX), (int) Math.floor(PADDINGY + y1 * scaleY),
						(int) Math.ceil(scaleX + (x2 - x1) * scaleX), (int) Math.ceil(scaleY + (y2 - y1) * scaleY));
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.3f));
				g2d.fillRect((int) Math.floor(PADDINGX + x1 * scaleX), (int) Math.floor(PADDINGY + y1 * scaleY),
						(int) Math.ceil(scaleX + (x2 - x1) * scaleX), (int) Math.ceil(scaleY + (y2 - y1) * scaleY));
			}
		} else {
			g2d.setColor(Color.GRAY);
			g2d.drawLine(0, 0, getWidth(), getHeight());
			g2d.drawLine(0, getHeight(), getWidth(), 0);
			GraphicsExecutor.scheduleUpdate(this);
		}
	}

	public void repaintUI() {
		if (getWidth() > 0 && getHeight() > 0) {
			BufferedImage tempBufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
			renderer.setSize(getWidth(), getHeight());
			renderer.setMatrix(matrix);
			Graphics2D g2d = (Graphics2D) tempBufferedImage.getGraphics();
			renderer.paintComponent(g2d);
			g2d.dispose();
			bufferedImage = tempBufferedImage;
		}
	}

	public void exportToPDF(File file) {
		ExportPDF.save(file, this);
	}

	public void exportToJPEG(File file) {
		ExportJPEG.save(file, this);
	}

	public void exportToPNG(File file) {
		ExportPNG.save(file, this);
	}

	public void mouseDragged(MouseEvent e) {
		if (matrixGUIObject.getRowSelectionModel().getValueIsAdjusting()) {
			long newRow = getRowPos(e.getY());
			long newCol = getColPos(e.getX());
			newRow = newRow < 0 ? 0 : newRow;
			newCol = newCol < 0 ? 0 : newCol;
			newRow = newRow >= matrix.getRowCount() ? matrix.getRowCount() - 1 : newRow;
			newCol = newCol >= matrix.getColumnCount() ? matrix.getColumnCount() - 1 : newCol;
			matrixGUIObject.getRowSelectionModel().setSelectionInterval(startRow, newRow);
			matrixGUIObject.getColumnSelectionModel().setSelectionInterval(startCol, newCol);
			repaint(100);
		}
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void valueChanged(ListSelectionEvent e) {
		repaint(100);
	}
}

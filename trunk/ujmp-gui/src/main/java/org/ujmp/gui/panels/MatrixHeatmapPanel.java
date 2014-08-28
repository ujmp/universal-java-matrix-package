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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

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

import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.HasToolTip;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.actions.MatrixActions;
import org.ujmp.gui.interfaces.CanBeRepainted;
import org.ujmp.gui.menu.MatrixPopupMenu;
import org.ujmp.gui.renderer.MatrixHeatmapRenderer;
import org.ujmp.gui.table.TableModelEvent64;
import org.ujmp.gui.table.TableModelListener64;
import org.ujmp.gui.util.GraphicsExecutor;
import org.ujmp.gui.util.Preloader;
import org.ujmp.gui.util.TooltipUtil;

public class MatrixHeatmapPanel extends JPanel implements ComponentListener, TableModelListener64, MouseListener,
		KeyListener, MouseMotionListener, CanBeRepainted, HasToolTip, ListSelectionListener {
	private static final long serialVersionUID = 843653796010276950L;

	private final MatrixGUIObject matrixGUIObject;
	private final MatrixHeatmapRenderer renderer = new MatrixHeatmapRenderer();
	private final Preloader preloader = new Preloader();

	private boolean isPreloaderVisible = true;

	private BufferedImage bufferedImage = null;

	private static int PADDINGX = UIManager.getInt("Table.paddingX");
	private static int PADDINGY = UIManager.getInt("Table.paddingY");

	private long startRow = 0;
	private long startCol = 0;

	public MatrixHeatmapPanel(MatrixGUIObject matrixGUIObject, boolean showBorder) {
		this.matrixGUIObject = matrixGUIObject;
		if (matrixGUIObject == null) {
			throw new IllegalArgumentException("matrixGUIObject is null");
		}

		if (showBorder) {
			setBorder(BorderFactory.createTitledBorder("Matrix Heatmap"));
		}

		setPreferredSize(new Dimension(600, 400));
		setLayout(new BorderLayout());
		add(preloader, BorderLayout.CENTER);

		addComponentListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);

		matrixGUIObject.addTableModelListener(this);
		matrixGUIObject.getRowSelectionModel().addListSelectionListener(this);
		matrixGUIObject.getColumnSelectionModel().addListSelectionListener(this);

		ToolTipManager.sharedInstance().registerComponent(this);

		registerKeyboardActions();
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
		GraphicsExecutor.scheduleUpdate(this);
	}

	public void tableChanged(TableModelEvent64 e) {
		GraphicsExecutor.scheduleUpdate(this);
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
			newRow = newRow >= matrixGUIObject.getRowCount() ? matrixGUIObject.getRowCount() - 1 : newRow;
			newCol = newCol >= matrixGUIObject.getColumnCount() ? matrixGUIObject.getColumnCount() - 1 : newCol;

			JPopupMenu popup = new MatrixPopupMenu(this, matrixGUIObject, newRow, newCol);
			popup.show(this, e.getX(), e.getY());
		} else if (e.getButton() == MouseEvent.BUTTON1) {
			// left click: show new window if value is a matrix
			Object o = matrixGUIObject.getValueAt(newRow, newCol);
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
			requestFocus();
			startRow = getRowPos(e.getY());
			startCol = getColPos(e.getX());
			startRow = startRow < 0 ? 0 : startRow;
			startCol = startCol < 0 ? 0 : startCol;
			startRow = startRow >= matrixGUIObject.getRowCount() ? matrixGUIObject.getRowCount() - 1 : startRow;
			startCol = startCol >= matrixGUIObject.getColumnCount() ? matrixGUIObject.getColumnCount() - 1 : startCol;
			matrixGUIObject.getRowSelectionModel().setValueIsAdjusting(true);
			matrixGUIObject.getColumnSelectionModel().setValueIsAdjusting(true);
			matrixGUIObject.getRowSelectionModel().setSelectionInterval(startRow, startRow);
			matrixGUIObject.getColumnSelectionModel().setSelectionInterval(startCol, startCol);
		}
		repaint(100);
	}

	private long getRowPos(int y) {
		return matrixGUIObject.getRowCount() * y / getHeight();
	}

	private long getColPos(int x) {
		return matrixGUIObject.getColumnCount() * x / getWidth();
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			long newRow = getRowPos(e.getY());
			long newCol = getColPos(e.getX());
			newRow = newRow < 0 ? 0 : newRow;
			newCol = newCol < 0 ? 0 : newCol;
			newRow = newRow >= matrixGUIObject.getRowCount() ? matrixGUIObject.getRowCount() - 1 : newRow;
			newCol = newCol >= matrixGUIObject.getColumnCount() ? matrixGUIObject.getColumnCount() - 1 : newCol;
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
		if (matrixGUIObject.getRowCount() > 0 && matrixGUIObject.getColumnCount() > 0) {
			long r = getRowPos(e.getY());
			long c = getColPos(e.getX());
			r = r < 0 ? 0 : r;
			c = c < 0 ? 0 : c;
			r = r >= matrixGUIObject.getRowCount() ? matrixGUIObject.getRowCount() - 1 : r;
			c = c >= matrixGUIObject.getColumnCount() ? matrixGUIObject.getColumnCount() - 1 : c;

			String toolTip = TooltipUtil.getTooltip(matrixGUIObject, r, c);
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
		if (bufferedImage == null) {
			super.paintComponent(g);
		} else {
			if (isPreloaderVisible) {
				remove(preloader);
				isPreloaderVisible = false;
			}
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), null);

			if (!matrixGUIObject.getRowSelectionModel().isSelectionEmpty()) {
				g2d.setColor(Color.BLUE);
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.3f));
				long x1 = matrixGUIObject.getColumnSelectionModel().getMinSelectionIndex64();
				long x2 = matrixGUIObject.getColumnSelectionModel().getMaxSelectionIndex64();
				long y1 = matrixGUIObject.getRowSelectionModel().getMinSelectionIndex64();
				long y2 = matrixGUIObject.getRowSelectionModel().getMaxSelectionIndex64();
				double scaleX = (double) (getWidth() - PADDINGX - PADDINGX) / (double) matrixGUIObject.getColumnCount();
				double scaleY = (double) (getHeight() - PADDINGY - PADDINGY) / (double) matrixGUIObject.getRowCount();
				g2d.setStroke(new BasicStroke(2.0f));
				g2d.drawRect((int) Math.floor(PADDINGX + x1 * scaleX), (int) Math.floor(PADDINGY + y1 * scaleY),
						(int) Math.ceil(scaleX + (x2 - x1) * scaleX), (int) Math.ceil(scaleY + (y2 - y1) * scaleY));
				g2d.fillRect((int) Math.floor(PADDINGX + x1 * scaleX), (int) Math.floor(PADDINGY + y1 * scaleY),
						(int) Math.ceil(scaleX + (x2 - x1) * scaleX), (int) Math.ceil(scaleY + (y2 - y1) * scaleY));
			}
		}
	}

	public void repaintUI() {
		if (getWidth() > 0 && getHeight() > 0 && matrixGUIObject.getRowCount() >= 0
				&& matrixGUIObject.getColumnCount() >= 0) {
			BufferedImage tempBufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
			renderer.setSize(getWidth(), getHeight());
			renderer.setMatrix(matrixGUIObject);
			Graphics2D g2d = (Graphics2D) tempBufferedImage.getGraphics();
			renderer.paintComponent(g2d);
			g2d.dispose();
			bufferedImage = tempBufferedImage;
		}
	}

	public void mouseDragged(MouseEvent e) {
		if (matrixGUIObject.getRowSelectionModel().getValueIsAdjusting()) {
			long newRow = getRowPos(e.getY());
			long newCol = getColPos(e.getX());
			newRow = newRow < 0 ? 0 : newRow;
			newCol = newCol < 0 ? 0 : newCol;
			newRow = newRow >= matrixGUIObject.getRowCount() ? matrixGUIObject.getRowCount() - 1 : newRow;
			newCol = newCol >= matrixGUIObject.getColumnCount() ? matrixGUIObject.getColumnCount() - 1 : newCol;
			matrixGUIObject.getRowSelectionModel().setSelectionInterval(startRow, newRow);
			matrixGUIObject.getColumnSelectionModel().setSelectionInterval(startCol, newCol);
			repaint(100);
		}
	}

	public void mouseMoved(MouseEvent e) {
		final long row = getRowPos(e.getY());
		final long col = getColPos(e.getX());
		matrixGUIObject.setMouseOverCoordinates(row, col);
	}

	public void valueChanged(ListSelectionEvent e) {
		repaint(100);
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		long rows = matrixGUIObject.getRowCount64();
		long cols = matrixGUIObject.getColumnCount64();
		long minRow = matrixGUIObject.getRowSelectionModel().getMinSelectionIndex64();
		long maxRow = matrixGUIObject.getRowSelectionModel().getMaxSelectionIndex64();
		long minCol = matrixGUIObject.getColumnSelectionModel().getMinSelectionIndex64();
		long maxCol = matrixGUIObject.getColumnSelectionModel().getMaxSelectionIndex64();
		if ((e.getKeyCode() == KeyEvent.VK_A) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
			matrixGUIObject.getColumnSelectionModel().setSelectionInterval(0, cols - 1);
			matrixGUIObject.getRowSelectionModel().setSelectionInterval(0, rows - 1);
		} else if (e.getKeyCode() == KeyEvent.VK_UP && minRow > 0) {
			matrixGUIObject.getRowSelectionModel().setSelectionInterval(minRow - 1, maxRow - 1);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN && maxRow < rows - 1) {
			matrixGUIObject.getRowSelectionModel().setSelectionInterval(minRow + 1, maxRow + 1);
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT && minCol > 0) {
			matrixGUIObject.getColumnSelectionModel().setSelectionInterval(minCol - 1, maxCol - 1);
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && maxCol < cols - 1) {
			matrixGUIObject.getColumnSelectionModel().setSelectionInterval(minCol + 1, maxCol + 1);
		}
	}

	public void keyReleased(KeyEvent e) {
	}

}

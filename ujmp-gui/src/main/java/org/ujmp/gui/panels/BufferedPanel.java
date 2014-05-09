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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;

import javax.swing.JPanel;

import org.ujmp.gui.interfaces.CanBeRepainted;
import org.ujmp.gui.interfaces.CanBeUpdated;
import org.ujmp.gui.util.GraphicsExecutor;
import org.ujmp.gui.util.Preloader;
import org.ujmp.gui.util.UIDefaults;
import org.ujmp.gui.util.UpdateListener;

public class BufferedPanel extends JPanel implements CanBeRepainted, ComponentListener, UpdateListener, MouseListener {
	private static final long serialVersionUID = 7495571585267828933L;

	private final JPanel panel;

	private BufferedImage bufferedImage = null;

	public BufferedPanel(JPanel panel) {
		this.panel = panel;
		this.addComponentListener(this);
		this.addMouseListener(this);

		panel.setLayout(new BorderLayout());
		panel.add(new Preloader(), BorderLayout.CENTER);

		if (panel instanceof CanBeUpdated) {
			((CanBeUpdated) panel).addUpdateListener(this);
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (isShowing()) {
			Graphics2D g2d = (Graphics2D) g;
			if (bufferedImage != null) {
				g2d.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), null);
			} else {
				g2d.addRenderingHints(UIDefaults.AALIAS);
				g2d.setColor(Color.gray);
				g2d.drawLine(0, 0, getWidth(), getHeight());
				g2d.drawLine(getWidth(), 0, 0, getHeight());
				GraphicsExecutor.scheduleUpdate(this);
			}
		}
	}

	private void paintUsingReflection(Graphics2D g2d) throws Exception {
		try {
			if (panel != null && g2d != null) {
				panel.paint(g2d);
			}
		} catch (Exception e) {
			Method m = panel.getClass().getMethod("paintComponent", Graphics.class);
			boolean accessible = m.isAccessible();
			m.setAccessible(true);
			m.invoke(panel, new Object[] { g2d });
			m.setAccessible(accessible);
		}
	}

	public void repaintUI() {
		try {
			if (isShowing()) {
				int width = getWidth();
				width = width < 1 ? 1 : width;
				int height = getHeight();
				height = height < 1 ? 1 : height;
				BufferedImage tempBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				panel.setSize(width, height);
				Graphics2D g2d = (Graphics2D) tempBufferedImage.getGraphics();
				paintUsingReflection(g2d);
				g2d.dispose();
				bufferedImage = tempBufferedImage;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void componentHidden(ComponentEvent e) {
		GraphicsExecutor.scheduleUpdate(this);
	}

	public void componentMoved(ComponentEvent e) {
		GraphicsExecutor.scheduleUpdate(this);
	}

	public void componentResized(ComponentEvent e) {
		GraphicsExecutor.scheduleUpdate(this);
	}

	public void componentShown(ComponentEvent e) {
		GraphicsExecutor.scheduleUpdate(this);
	}

	public void updated() {
		GraphicsExecutor.scheduleUpdate(this);
	}

	public void mouseClicked(MouseEvent e) {
		if (panel.getMouseListeners() != null && panel.getMouseListeners().length != 0) {
			MouseEvent e2 = new MouseEvent(panel, e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(),
					e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), e.getButton());
			for (MouseListener m : panel.getMouseListeners()) {
				m.mouseClicked(e2);
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		if (panel.getMouseListeners() != null && panel.getMouseListeners().length != 0) {
			MouseEvent e2 = new MouseEvent(panel, e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(),
					e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), e.getButton());
			for (MouseListener m : panel.getMouseListeners()) {
				m.mousePressed(e2);
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (panel.getMouseListeners() != null && panel.getMouseListeners().length != 0) {
			MouseEvent e2 = new MouseEvent(panel, e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(),
					e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), e.getButton());
			for (MouseListener m : panel.getMouseListeners()) {
				m.mouseReleased(e2);
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
		if (panel.getMouseListeners() != null && panel.getMouseListeners().length != 0) {
			MouseEvent e2 = new MouseEvent(panel, e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(),
					e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), e.getButton());
			for (MouseListener m : panel.getMouseListeners()) {
				m.mouseEntered(e2);
			}
		}
	}

	public void mouseExited(MouseEvent e) {
		if (panel.getMouseListeners() != null && panel.getMouseListeners().length != 0) {
			MouseEvent e2 = new MouseEvent(panel, e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(),
					e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), e.getButton());
			for (MouseListener m : panel.getMouseListeners()) {
				m.mouseExited(e2);
			}
		}
	}
}

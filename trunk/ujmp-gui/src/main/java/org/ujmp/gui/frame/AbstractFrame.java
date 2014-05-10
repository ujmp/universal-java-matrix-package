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

package org.ujmp.gui.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.util.UJMPTimer;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.io.ExportJPEG;
import org.ujmp.gui.io.ExportPDF;
import org.ujmp.gui.io.ExportPNG;
import org.ujmp.gui.statusbar.StatusBar;
import org.ujmp.gui.util.FrameManager;
import org.ujmp.gui.util.UIDefaults;

public abstract class AbstractFrame extends JFrame {
	private static final long serialVersionUID = -4656308453503586700L;

	private int modCount = -1;

	private final GUIObject guiObject;

	private final StatusBar statusBar;
	private final TimerTask updateTask;
	private final UJMPTimer timer;

	public AbstractFrame(Matrix matrix, JComponent component) {
		this(matrix.getGUIObject(), component);
	}

	public AbstractFrame(GUIObject o, JComponent component) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		UIDefaults.setDefaults();
		FrameManager.registerFrame(o, this);
		this.guiObject = o;

		URL url = ClassLoader.getSystemResource("org/ujmp/gui/UJMP.png");
		Image img = Toolkit.getDefaultToolkit().createImage(url);
		setIconImage(img);

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		if (d.getHeight() < 800) {
			setPreferredSize(new Dimension(700, 500));
			setSize(new Dimension(700, 500));
			setExtendedState(MAXIMIZED_BOTH);
		} else if (d.getHeight() < 1024) {
			setPreferredSize(new Dimension(1000, 600));
			setSize(new Dimension(1000, 600));
			setExtendedState(MAXIMIZED_BOTH);
		} else {
			setPreferredSize(new Dimension(1280, 800));
			setSize(new Dimension(1280, 800));
		}

		statusBar = new StatusBar(guiObject);
		getContentPane().add(statusBar, BorderLayout.SOUTH);
		getContentPane().add(component, BorderLayout.CENTER);

		updateTask = new TimerTask() {
			public void run() {
				updateTitle();
				if (modCount != guiObject.getModCount()) {
					modCount = guiObject.getModCount();
					repaint(1000);
				}
			}
		};

		updateTitle();
		timer = UJMPTimer.newInstance();
		timer.scheduleAtFixedRate(updateTask, 5000, 5000);
	}

	public final void setVisible(boolean state) {
		if (state == true && isVisible()) {
			return;
		}
		if (state == false && !isVisible()) {
			return;
		}

		super.setVisible(state);
	}

	private void updateTitle() {
		String label = guiObject.getLabel() == null ? "no label" : guiObject.getLabel();
		if (guiObject instanceof MatrixGUIObject) {
			MatrixGUIObject mgui = (MatrixGUIObject) guiObject;
			String size = Coordinates.toString("[", "x", "]", mgui.getRowCount64(), mgui.getColumnCount64());
			setTitle(size + " " + mgui.getMatrix().getClass().getSimpleName() + " [" + label + "]");
		} else {
			setTitle(guiObject.toString());
		}
		if (guiObject.getIcon() != null) {
			setIconImage(guiObject.getIcon());
		}
	}

	public final GUIObject getObject() {
		return guiObject;
	}

	public final void exportToPDF(File file) {
		ExportPDF.save(file, this);
	}

	public final void exportToPNG(File file) {
		ExportPNG.save(file, this);
	}

	public final void exportToJPEG(File file) {
		ExportJPEG.save(file, this);
	}

}

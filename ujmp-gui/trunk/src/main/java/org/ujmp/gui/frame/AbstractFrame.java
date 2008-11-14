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

package org.ujmp.gui.frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.io.ExportJPEG;
import org.ujmp.gui.io.ExportPDF;
import org.ujmp.gui.io.ExportPNG;
import org.ujmp.gui.statusbar.StatusBar;
import org.ujmp.gui.toolbar.DefaultToolbar;
import org.ujmp.gui.util.FrameManager;
import org.ujmp.gui.util.UIDefaults;

public abstract class AbstractFrame extends JFrame {
	private static final long serialVersionUID = -4656308453503586700L;

	private static Image image = Toolkit.getDefaultToolkit().getImage("ujmp16.png");

	private GUIObject object = null;

	private StatusBar statusBar = null;

	private static int frameCount = 0;

	public AbstractFrame(GUIObject o, JComponent component) throws MatrixException {
		UIDefaults.setDefaults();
		FrameManager.registerFrame(this);
		this.object = o;
		String label = o.getLabel() == null ? "no label" : o.getLabel();
		if (o instanceof MatrixGUIObject) {
			MatrixGUIObject mgui = (MatrixGUIObject) o;
			Matrix m = mgui.getMatrix();
			String size = Coordinates.toString(m.getSize()).replaceAll(",", "x");
			setTitle("[" + size + "] " + m.getClass().getSimpleName() + " [" + label + "]");
		} else {
			setTitle(o.toString());
		}

		setIconImage(image);

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		if (d.getHeight() < 1024) {
			setExtendedState(MAXIMIZED_BOTH);
		} else {
			setPreferredSize(new Dimension(1280, 800));
			setSize(new Dimension(1280, 800));
		}

		statusBar = new StatusBar(object);
		getContentPane().add(statusBar, BorderLayout.SOUTH);

		getContentPane().add(component, BorderLayout.CENTER);

		DefaultToolbar toolbar = new DefaultToolbar(component, o);
		getContentPane().add(toolbar, BorderLayout.NORTH);
	}

	@Override
	public final void setVisible(boolean state) {
		if (state == true && isVisible()) {
			return;
		}
		if (state == false && !isVisible()) {
			return;
		}

		super.setVisible(state);
		if (state) {
			frameCount++;
			statusBar.start();
		} else {
			frameCount--;
			statusBar.stop();
		}

		if (frameCount == 0) {
			Component parentComponent = null;
			String message = "Do you want to exit the program, close the window or restore it?";
			String title = "Last Window Closed";
			int messageType = JOptionPane.QUESTION_MESSAGE;
			Icon icon = null;
			int initialValue = 0;
			String[] options = new String[] { "Exit", "Close", "Restore" };
			int ret = JOptionPane.showOptionDialog(parentComponent, message, title, 0, messageType, icon, options,
					initialValue);

			if (ret == 0) {
				System.exit(0);
			} else if (ret == 2) {
				setVisible(true);
			}
		}
	}

	public final GUIObject getObject() {
		return object;
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

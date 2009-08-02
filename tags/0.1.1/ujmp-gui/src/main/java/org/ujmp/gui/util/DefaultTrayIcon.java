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

package org.ujmp.gui.util;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DefaultTrayIcon extends TrayIcon implements ActionListener {

	private static Image image = Toolkit.getDefaultToolkit().getImage("jdmp16.png");

	public DefaultTrayIcon() {
		super(image);
		setToolTip("JDMP");

		TaskQueue.addActionListener(this);

		MouseListener mouseListener = new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
					// Workspace.getInstance().showFrame();
				}
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		};

		PopupMenu popup = new PopupMenu();

		MenuItem exitItem = new MenuItem("Exit");
		ActionListener exitListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// new ExitAction().call();
			}
		};
		exitItem.addActionListener(exitListener);
		popup.add(exitItem);

		setPopupMenu(popup);

		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayMessage("Action Event", "An Action Event Has Been Peformed!", TrayIcon.MessageType.INFO);
			}
		};

		setImageAutoSize(true);
		addActionListener(actionListener);
		addMouseListener(mouseListener);

	}

	public void actionPerformed(ActionEvent e) {
		displayMessage("New task added", e.getActionCommand(), TrayIcon.MessageType.INFO);
	}

}

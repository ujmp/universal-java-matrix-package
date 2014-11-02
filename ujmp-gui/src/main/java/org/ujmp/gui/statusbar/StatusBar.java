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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.ujmp.core.Coordinates;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.util.UJMPTimer;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.util.TaskQueue;

public class StatusBar extends JPanel {
	private static final long serialVersionUID = -92341245296146976L;

	private final JLabel statusLabel = new JLabel();

	private final GUIObject guiObject;

	private final JProgressBar jProgressBar = new JProgressBar();

	private final UJMPTimer timer;

	public StatusBar(GUIObject o) {
		this.guiObject = o;
		this.setPreferredSize(new Dimension(1000, 30));
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setLayout(new GridBagLayout());
		statusLabel.setPreferredSize(new Dimension(2000, 30));
		statusLabel.setMinimumSize(new Dimension(200, 30));

		add(statusLabel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.BOTH,
				new Insets(2, 2, 2, 2), 0, 0));

		add(new MemoryUsage(), new GridBagConstraints(3, 0, 1, 1, 0.0, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

		jProgressBar.setStringPainted(false);
		jProgressBar.setMinimum(0);
		jProgressBar.setMaximum(1000);
		jProgressBar.setValue(1000);
		jProgressBar.setVisible(false);

		statusLabel.setBorder(BorderFactory.createEtchedBorder());
		jProgressBar.setBorder(BorderFactory.createEtchedBorder());

		add(jProgressBar, new GridBagConstraints(1, 0, 1, 1, 0.8, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

		timer = UJMPTimer.newInstance("StatusBar "+guiObject.getCoreObject().getClass().getSimpleName());
		timer.schedule(new UpdateTask(this), 200, 200);
	}

	public void setStatusString(String s) {
		statusLabel.setText(s);
	}

	public void setProgress(Double progress) {
		if (progress == null) {
			jProgressBar.setValue(0);
			jProgressBar.setIndeterminate(true);
			jProgressBar.setVisible(true);
		} else if (progress == 1.0) {
			jProgressBar.setValue(1000);
			jProgressBar.setVisible(false);
		} else {
			int value = (int) (progress * jProgressBar.getMaximum());
			jProgressBar.setIndeterminate(false);
			jProgressBar.setValue(value);
			jProgressBar.setVisible(true);
		}
	}

	public GUIObject getObject() {
		return guiObject;
	}

	class UpdateTask extends TimerTask {

		private StatusBar statusBar = null;

		public UpdateTask(StatusBar statusBar) {
			this.statusBar = statusBar;
		}

		public void run() {
			StringBuilder s = new StringBuilder();
			if (guiObject instanceof MatrixGUIObject) {
				MatrixGUIObject matrix = ((MatrixGUIObject) guiObject);
				long[] c = matrix.getMouseOverCoordinates();
				s.append("Position: ");
				s.append(Coordinates.toString(c));
				if (!matrix.getRowSelectionModel().isSelectionEmpty()
						|| !matrix.getColumnSelectionModel().isSelectionEmpty()) {
					long y0 = matrix.getRowSelectionModel().getMinSelectionIndex64();
					long y1 = matrix.getRowSelectionModel().getMaxSelectionIndex64();
					long x0 = matrix.getColumnSelectionModel().getMinSelectionIndex64();
					long x1 = matrix.getColumnSelectionModel().getMaxSelectionIndex64();
					long xSize = 1 + Math.abs(x1 - x0);
					long ySize = 1 + Math.abs(y1 - y0);
					s.append(" Selection: [" + y0 + "," + x0 + "] - [" + y1 + "," + x1 + "] = ");
					s.append("[" + ySize + "x" + xSize + "]");
				}
				setStatusString(s.toString());
			}
			statusBar.setProgress(TaskQueue.getProgress());
		}
	}

}

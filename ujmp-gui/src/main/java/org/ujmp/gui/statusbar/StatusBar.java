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

import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.AbstractMatrixGUIObject;
import org.ujmp.gui.DefaultMatrixGUIObject;
import org.ujmp.gui.util.TaskQueue;

public class StatusBar extends JPanel {
	private static final long serialVersionUID = -92341245296146976L;

	private final JLabel taskStatus = new JLabel();

	private JLabel objectStatus = null;

	private GUIObject object = null;

	private final JProgressBar jProgressBar = new JProgressBar();

	public StatusBar(GUIObject o) {
		this.object = o;
		if (o instanceof DefaultMatrixGUIObject) {
			this.objectStatus = new MatrixStatisticsBar((AbstractMatrixGUIObject) o);
		} else {
			this.objectStatus = new JLabel();
		}
		this.setPreferredSize(new Dimension(1000, 30));
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setLayout(new GridBagLayout());

		taskStatus.setPreferredSize(new Dimension(200, 30));
		taskStatus.setMinimumSize(new Dimension(200, 30));

		add(objectStatus, new GridBagConstraints(0, 0, 1, 1, 0.2, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

		add(taskStatus, new GridBagConstraints(2, 0, 1, 1, 0.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.BOTH,
				new Insets(2, 2, 2, 2), 0, 0));

		add(new MemoryUsage(), new GridBagConstraints(3, 0, 1, 1, 0.0, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

		jProgressBar.setStringPainted(false);
		jProgressBar.setMinimum(0);
		jProgressBar.setMaximum(1000);
		jProgressBar.setValue(1000);
		jProgressBar.setVisible(false);

		objectStatus.setBorder(BorderFactory.createEtchedBorder());
		taskStatus.setBorder(BorderFactory.createEtchedBorder());
		jProgressBar.setBorder(BorderFactory.createEtchedBorder());

		add(jProgressBar, new GridBagConstraints(1, 0, 1, 1, 0.8, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

	}

	public void start() {
		stop();
		// timer = new Timer("Toolbar Timer for " + object.getLabel());
		// timer.schedule(new UpdateTask(this), 1000, // initial delay
		// 1000); // subsequent rate
	}

	public void stop() {
		// if (timer != null) {
		// timer.cancel();
		// timer = null;
		// }
	}

	public void setTaskString(String s) {
		taskStatus.setText(s);
	}

	public void setObjectString(String s) {
		objectStatus.setText(s);
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
		return object;
	}

	class UpdateTask extends TimerTask {

		private StatusBar statusBar = null;

		public UpdateTask(StatusBar statusBar) {
			this.statusBar = statusBar;
		}

		public void run() {
			statusBar.setTaskString(TaskQueue.getStatus());
			// statusBar.setToolTipText(getObject().getToolTipText());
			statusBar.setObjectString("" + getObject());
			statusBar.setProgress(TaskQueue.getProgress());
		}

	}

}

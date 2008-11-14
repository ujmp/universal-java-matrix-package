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

package org.ujmp.gui.statusbar;

import java.awt.Dimension;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JProgressBar;

import org.ujmp.gui.util.GlobalTimer;

public class MemoryUsage extends JProgressBar {
	private static final long serialVersionUID = 5692292627429288637L;

	public MemoryUsage() {
		setBorder(BorderFactory.createEtchedBorder());
		setMinimumSize(new Dimension(50, 30));
		GlobalTimer.getInstance().schedule(new UpdateTask(this), 0, 1000);
	}

	public void update() {
		int max = (int) Runtime.getRuntime().maxMemory() / 1048576;
		int total = (int) Runtime.getRuntime().totalMemory() / 1048576;
		int free = (int) Runtime.getRuntime().freeMemory() / 1048576;
		int used = total - free;
		setMinimum(0);
		setMaximum(max);
		setValue(used);
		setToolTipText("" + used + "MB of " + max + "MB used");
	}

	class UpdateTask extends TimerTask {

		private MemoryUsage memoryUsage = null;

		public UpdateTask(MemoryUsage memoryUsage) {
			this.memoryUsage = memoryUsage;
		}

		@Override
		public void run() {
			memoryUsage.update();
		}

	}

}

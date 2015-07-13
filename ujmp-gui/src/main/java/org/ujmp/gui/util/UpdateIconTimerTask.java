/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

import org.ujmp.core.util.UJMPTimer;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.renderer.MatrixHeatmapRenderer;

public class UpdateIconTimerTask extends TimerTask {
	private static UpdateIconTimerTask instance = null;
	private static final Object lock = new Object();

	private List<MatrixGUIObject> list = new LinkedList<MatrixGUIObject>();

	public static final UpdateIconTimerTask getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new UpdateIconTimerTask();
				}
			}
		}
		return instance;
	}

	private UpdateIconTimerTask() {
		UJMPTimer.newInstance("UpdateIcon").schedule(this, 300, 300);
	}

	public void add(MatrixGUIObject matrixGUIObject) {
		list.add(matrixGUIObject);
	}

	@Override
	public void run() {
		try {
			for (MatrixGUIObject matrixGuiObject : list) {
				if (!matrixGuiObject.isIconUpToDate()) {
					matrixGuiObject.setIconUpToDate(true);
					BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
					MatrixHeatmapRenderer.paintMatrix(image.getGraphics(), matrixGuiObject, 16, 16, 0, 0);
					matrixGuiObject.setIcon(image);
				}
			}
		} catch (ConcurrentModificationException e) {
			// no problem, retry later
		}
	}
}

/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.EventListenerList;

import org.ujmp.gui.actions.ObjectAction;

public abstract class TaskQueue {

	private static final Logger logger = Logger.getLogger(TaskQueue.class.getName());

	private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>());

	private static double progress = 1.0;

	private static EventListenerList listenerList = new EventListenerList();

	private static String status = "";

	public static final String getStatus() {
		if (getWaitingCount() == 0 && getProgress() == 1.0) {
			return "Ready";
		} else if (getWaitingCount() == 0 && getProgress() != 1.0) {
			return status;
		} else {
			return status + " (" + getWaitingCount() + " tasks waiting)";
		}
	}

	public static final Future<?> submit(ObjectAction c) {
		logger.log(Level.INFO, "New task added: " + c);
		fireActionPerformed(new ActionEvent(c, 0, c.toString()));
		return executor.submit(c);
	}

	public static void setStatus(String s) {
		status = s;
		logger.log(Level.FINER, "Status: " + s);
	}

	public static final void setProgress(double p) {
		progress = p;
		logger.log(Level.FINER, "Progress: " + p);
	}

	public static final double getProgress() {
		return progress;
	}

	public static final int getWaitingCount() {
		return executor.getQueue().size();
	}

	public static final void addActionListener(ActionListener l) {
		listenerList.add(ActionListener.class, l);
	}

	public static final void removeActionListener(ActionListener l) {
		listenerList.remove(ActionListener.class, l);
	}

	public static void fireActionPerformed(ActionEvent e) {
		for (Object o : listenerList.getListenerList()) {
			if (o instanceof ActionListener) {
				((ActionListener) o).actionPerformed(e);
			}
		}
	}

	public static void submit(Runnable runnable) {
		executor.submit(runnable);
	}

}

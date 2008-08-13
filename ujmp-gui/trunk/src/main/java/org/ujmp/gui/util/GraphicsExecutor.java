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

import java.awt.Component;
import java.awt.Cursor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class GraphicsExecutor {

	private static final int count = Runtime.getRuntime().availableProcessors();

	private static List<Executor> executors = new ArrayList<Executor>(count);

	static {
		for (int i = 0; i < count; i++) {
			Executor executor = new Executor();
			executors.add(executor);
		}
	}

	public static final void scheduleUpdate(CanBeRepainted component) {
		Component c = (Component) component;
		while (c != null && !(c instanceof JFrame)) {
			c = c.getParent();
		}
		if (c != null && c.isVisible()) {
			Executor executor = getExecutor(component);
			executor.sheduleUpdate(component);
		}
	}

	private static Executor getExecutor(CanBeRepainted component) {
		return executors.get(Math.abs(component.hashCode()) % count);
	}

	public static final void setFinished(CanBeRepainted component) {
		executors.get(component.hashCode() % count).setFinished(component);
	}

}

class UpdateTask implements Runnable {

	private static final Logger logger = Logger.getLogger(UpdateTask.class.getName());

	private CanBeRepainted component = null;

	public UpdateTask(CanBeRepainted component) {
		this.component = component;
		if (component == null) {
			System.out.println("null component");
		}
	}

	public void run() {
		try {
			GraphicsExecutor.setFinished(component);
			((JComponent) component).setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			component.repaintUI();
			((JComponent) component).setCursor(Cursor.getDefaultCursor());
			((JComponent) component).repaint(1);

		} catch (Exception e) {
			logger.log(Level.WARNING, "could not repaint ui", e);
		}
	}

}

class Executor extends ThreadPoolExecutor {

	private final Set<CanBeRepainted> waitingTasks = Collections.synchronizedSet(new HashSet<CanBeRepainted>());

	public Executor() {
		super(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new LowPriorityThreadFactory());
	}

	public void setFinished(CanBeRepainted component) {
		waitingTasks.remove(component);
	}

	public void sheduleUpdate(CanBeRepainted component) {
		if (!waitingTasks.contains(component)) {
			// System.out.println("schedule update: " + component);
			waitingTasks.add(component);
			UpdateTask task = new UpdateTask(component);
			submit(task);
		}
	}
}

class LowPriorityThreadFactory implements ThreadFactory {
	static final AtomicInteger poolNumber = new AtomicInteger(1);

	final ThreadGroup group;

	final AtomicInteger threadNumber = new AtomicInteger(1);

	final String namePrefix;

	public LowPriorityThreadFactory() {
		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		namePrefix = "GraphicsExecutorPool-";
	}

	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
		if (t.isDaemon())
			t.setDaemon(false);
		if (t.getPriority() != Thread.MIN_PRIORITY)
			t.setPriority(Thread.MIN_PRIORITY);
		return t;
	}
}
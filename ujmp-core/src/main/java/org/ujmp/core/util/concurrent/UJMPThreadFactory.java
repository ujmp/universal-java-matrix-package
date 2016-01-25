/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

package org.ujmp.core.util.concurrent;

import java.util.concurrent.ThreadFactory;

public class UJMPThreadFactory implements ThreadFactory {

	private int count = 0;

	private String name = "unknown";

	private int priority = Thread.NORM_PRIORITY;

	private boolean deamon = true;

	public UJMPThreadFactory(String name, int priority, boolean deamon) {
		this.name = name == null ? this.name : name;
		this.priority = priority;
		this.deamon = deamon;
	}

	public Thread newThread(Runnable r) {
		Thread t = new Thread(r, "UJMP-" + name + "-" + count++);
		t.setPriority(priority);
		t.setDaemon(deamon);
		return t;
	}

}

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

package org.ujmp.core.util.matrices;

import java.net.SocketException;

import org.ujmp.core.Matrix;
import org.ujmp.core.filematrix.DirectoryMatrix;
import org.ujmp.core.util.UJMPSettings;

public class LocalhostMatrix extends RemoteHostMatrix {
	private static final long serialVersionUID = 5276053410827939716L;

	private static final Object lock = new Object();
	private static LocalhostMatrix instance = null;

	public static final LocalhostMatrix getInstance() throws SocketException {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new LocalhostMatrix();
				}
			}
		}
		return instance;
	}

	private LocalhostMatrix() throws SocketException {
		super("localhost");
		put("Available Processors", Matrix.Factory.availableProcessors());
		put("Memory Usage", Matrix.Factory.memoryUsage());
		put("Operating System", Matrix.Factory.operatingSystem());
		put("Running Threads", Matrix.Factory.runningThreads());
		put("System Environment", Matrix.Factory.systemEnvironment());
		put("System Properties", Matrix.Factory.systemProperties());
		put("System Time", Matrix.Factory.systemTime());
		put("UJMP Settings", UJMPSettings.getInstance());
		put("File System", new DirectoryMatrix());
		put("Network", NetworkMatrix.getInstance());
	}

}

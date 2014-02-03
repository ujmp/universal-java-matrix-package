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

package org.ujmp.core.util.matrices;

import org.ujmp.core.Matrix;

public class LocalhostMatrix extends RemoteHostMatrix {
	private static final long serialVersionUID = 5276053410827939716L;

	private final Matrix processors = Matrix.Factory.availableProcessors();
	private final Matrix memory = Matrix.Factory.memoryUsage();
	private final Matrix threads = Matrix.Factory.runningThreads();
	private final Matrix systemEnvironment = Matrix.Factory.systemEnvironment();
	private final Matrix systemProperties = Matrix.Factory.systemProperties();
	private final Matrix systemTime = Matrix.Factory.systemTime();
	private final Matrix fileSystem = new FileSystemMatrix();

	public LocalhostMatrix() {
		super("localhost");
		setLabel("Localhost");
		setColumnLabel(0, "Key");
		setColumnLabel(1, "Value");
		getMap().put("Available Processors", processors);
		getMap().put("Memory Usage", memory);
		getMap().put("Running Threads", threads);
		getMap().put("System Environment", systemEnvironment);
		getMap().put("System Properties", systemProperties);
		getMap().put("System Time", systemTime);
		getMap().put("File System", fileSystem);
	}

}

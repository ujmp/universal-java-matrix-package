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

package org.ujmp.core.util.matrices;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.TreeMap;

import org.ujmp.core.Matrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;

public class NetworkMatrix extends DefaultMapMatrix<String, Matrix> {
	private static final long serialVersionUID = -1878074546014731559L;

	private static final Object lock = new Object();
	private static NetworkMatrix instance = null;

	public static final NetworkMatrix getInstance() throws SocketException {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new NetworkMatrix();
				}
			}
		}
		return instance;
	}

	private NetworkMatrix() throws SocketException {
		super(new TreeMap<String, Matrix>());
		setLabel("Network");
		Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
		while (networkInterfaces.hasMoreElements()) {
			NetworkInterface networkInterface = networkInterfaces.nextElement();
			put(networkInterface.getName(), new NetworkInterfaceMatrix(networkInterface));
		}
	}

	public final boolean isReadOnly() {
		return true;
	}

}

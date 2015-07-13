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

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

import org.ujmp.core.Matrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.util.concurrent.BackgroundTask;

public class NetworkInterfaceMatrix extends DefaultMapMatrix<String, Matrix> {

	private static final long serialVersionUID = 2604261022691386860L;

	public NetworkInterfaceMatrix(final NetworkInterface networkInterface) throws SocketException {
		setLabel(networkInterface.getDisplayName());
		setMetaData("MTU", networkInterface.getMTU());
		setMetaData("IsLoopback", networkInterface.isLoopback());
		setMetaData("IsPointToPoint", networkInterface.isPointToPoint());
		setMetaData("IsUp", networkInterface.isUp());
		setMetaData("IsVirtual", networkInterface.isVirtual());

		new BackgroundTask() {

			@Override
			public Object run() {
				for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
					try {
						put(address.getAddress() + "/" + address.getNetworkPrefixLength(),
								new SubnetMatrix(address));
					} catch (SocketException e) {
						e.printStackTrace();
					}
				}
				return null;
			}
		};

	}

	public final boolean isReadOnly() {
		return true;
	}

}

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

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.TreeMap;

import org.ujmp.core.Matrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.util.concurrent.BackgroundTask;
import org.ujmp.core.util.concurrent.PFor;

public class SubnetMatrix extends DefaultMapMatrix<String, Matrix> {
	private static final long serialVersionUID = -6949479403348183259L;

	private final InterfaceAddress address;

	public SubnetMatrix(InterfaceAddress address) throws SocketException {
		super(new TreeMap<String, Matrix>());
		this.address = address;
		setLabel(address.getAddress() + "/" + address.getNetworkPrefixLength());
		put(address.getAddress().getHostAddress(), new RemoteHostMatrix(address.getAddress()
				.getHostAddress()));
		new BackgroundTask() {
			@Override
			public Object run() {
				search();
				return null;
			}
		};
	}

	private void search() {
		try {
			final byte[] ip = address.getAddress().getAddress();

			if (ip.length != 4 || ip[0] == (byte) 127) {
				return;
			}

			new PFor(32, 1, 254) {
				@Override
				public void step(int i) {
					byte[] tmpip = Arrays.copyOf(ip, ip.length);
					tmpip[3] = (byte) i;
					try {
						InetAddress tmpaddress = InetAddress.getByAddress(tmpip);
						if (tmpaddress.isReachable(3000)
								&& !containsKey(tmpaddress.getHostAddress())) {
							put(tmpaddress.getHostAddress(),
									new RemoteHostMatrix(tmpaddress.getHostAddress()));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Matrix get(Object key) {
		Matrix matrix = super.get(key);
		if (matrix == null && key != null) {
			String address = key.toString();
			if (!address.isEmpty()) {
				matrix = new RemoteHostMatrix(address);
				super.put(String.valueOf(key), matrix);
			}
		}
		return matrix;
	}

}

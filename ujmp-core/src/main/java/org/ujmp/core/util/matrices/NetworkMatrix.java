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

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.TreeMap;

import org.ujmp.core.Matrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.util.NetworkUtil;
import org.ujmp.core.util.concurrent.BackgroundTask;
import org.ujmp.core.util.concurrent.PFor;

public class NetworkMatrix extends DefaultMapMatrix<String, Matrix> {
	private static final long serialVersionUID = -1878074546014731559L;

	private static final Object lock = new Object();
	private static NetworkMatrix instance = null;

	public static final NetworkMatrix getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new NetworkMatrix();
				}
			}
		}
		return instance;
	}

	private NetworkMatrix() {
		super(new TreeMap<String, Matrix>());
		setLabel("Network");
		new BackgroundTask() {
			@Override
			public Void run() {
				search();
				return null;
			}
		};
	}

	public final boolean isReadOnly() {
		return true;
	}

	private void search() {
		try {
			NetworkUtil.getIPAddresses();

			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
					.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = networkInterfaces.nextElement();
				Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress myAddress = addresses.nextElement();

					byte[] ip = myAddress.getAddress();
					if (ip.length != 4 || ip[0] == (byte) 127) {
						continue;
					}

					new PFor(32, 1, 254, new Object[] { ip, myAddress }) {
						@Override
						public void step(int i) {
							byte[] ip = Arrays.copyOf((byte[]) getObject(0),
									((byte[]) getObject(0)).length);
							ip[3] = (byte) i;
							try {
								InetAddress address = InetAddress.getByAddress(ip);
								if (!address.equals(getObject(1))) {
									if (address.isReachable(3000)) {
										put(address.getHostAddress(),
												new RemoteHostMatrix(address.getHostAddress()));
										fireValueChanged();
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					};
				}

			}
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

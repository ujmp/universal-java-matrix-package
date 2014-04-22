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

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.ujmp.core.Matrix;
import org.ujmp.core.collections.map.AbstractMap;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.objectmatrix.DenseObjectMatrix2D;
import org.ujmp.core.objectmatrix.factory.DenseObjectMatrix2DFactory;
import org.ujmp.core.util.concurrent.BackgroundTask;

public class RemoteHostMatrix extends AbstractMapMatrix<String, Matrix> {
	private static final long serialVersionUID = 7209155858904976010L;

	private final String address;
	private final Map<String, Matrix> map;

	public RemoteHostMatrix(String address) {
		this.address = address;
		setLabel(address);
		map = new RemoteMachineMap(this, address);
	}

	@Override
	public Map<String, Matrix> getMap() {
		return map;
	}

	@Override
	public MapMatrix<String, Matrix> clone() {
		return null;
	}

	public String getAddress() {
		return address;
	}

	public DenseObjectMatrix2DFactory<? extends DenseObjectMatrix2D> getFactory() {
		throw new RuntimeException("not implemented");
	}

}

class RemoteMachineMap extends AbstractMap<String, Matrix> {
	private static final long serialVersionUID = -2010821981402892951L;
	private final Map<String, Matrix> map;
	private final String address;
	private final RemoteHostMatrix remoteMachineMatrix;

	public RemoteMachineMap(RemoteHostMatrix matrix, String address) {
		this.remoteMachineMatrix = matrix;
		this.address = address;
		map = new TreeMap<String, Matrix>();

		new BackgroundTask(address) {

			@Override
			public Object run() {
				return searchServices();
			}

			private Object searchServices() {
				boolean portAvailable = false;
				final int delay = 3000;
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(getAddress(), 10413), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						// map.put("JDMP", new RemoteMatrix("http://" +
						// getObject(0) + ":10413/"));
						remoteMachineMatrix.notifyGUIObject();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(getAddress(), 80), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						map.put("HTTP", new HttpMatrix("http://" + getObject(0)));
						remoteMachineMatrix.notifyGUIObject();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(getAddress(), 443), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						map.put("HTTPS", new HttpMatrix("https://" + getObject(0)));
						remoteMachineMatrix.notifyGUIObject();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(getAddress(), 3306), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						// map.put("MySQL", new
						// MySQLDatabaseMapMatrix(getAddress(), 3306, "root",
						// ""));
						remoteMachineMatrix.notifyGUIObject();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(getAddress(), 22), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						map.put("SSH", null);
						remoteMachineMatrix.notifyGUIObject();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(getAddress(), 20), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						map.put("FTP", null);
						remoteMachineMatrix.notifyGUIObject();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(getAddress(), 23), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						map.put("Telnet", null);
						remoteMachineMatrix.notifyGUIObject();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(getAddress(), 445), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						map.put("SMB", null);
						remoteMachineMatrix.notifyGUIObject();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(getAddress(), 1433), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						map.put("MSSQL", null);
						remoteMachineMatrix.notifyGUIObject();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(getAddress(), 5432), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						map.put("PostgreSQL", null);
						remoteMachineMatrix.notifyGUIObject();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(getAddress(), 11211), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						map.put("memcached", null);
						remoteMachineMatrix.notifyGUIObject();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(getAddress(), 27017), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						map.put("mongoDB", null);
						remoteMachineMatrix.notifyGUIObject();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(getAddress(), 25), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						map.put("SMTP", null);
						remoteMachineMatrix.notifyGUIObject();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(getAddress(), 143), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						map.put("IMAP", null);
						remoteMachineMatrix.notifyGUIObject();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(getAddress(), 993), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						map.put("IMAPS", null);
						remoteMachineMatrix.notifyGUIObject();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(getAddress(), 2049), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						map.put("NFS", null);
						remoteMachineMatrix.notifyGUIObject();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(getAddress(), 8080), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						map.put("Tomcat", null);
						remoteMachineMatrix.notifyGUIObject();
					}
				} catch (Exception e) {
				}

				return null;
			}
		};
	}

	private String getAddress() {
		return address;
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Matrix get(Object key) {
		return map.get(key);
	}

	@Override
	public Set<String> keySet() {
		return map.keySet();
	}

	@Override
	public Matrix put(String key, Matrix value) {
		return map.put(key, value);
	}

	@Override
	public Matrix remove(Object key) {
		return map.remove(key);
	}

	@Override
	public int size() {
		return map.size();
	}

}
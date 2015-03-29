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
import java.util.TreeMap;

import org.ujmp.core.Matrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.util.NetworkUtil;
import org.ujmp.core.util.concurrent.BackgroundTask;

public class RemoteHostMatrix extends DefaultMapMatrix<String, Matrix> {
	private static final long serialVersionUID = 7209155858904976010L;

	public RemoteHostMatrix(final String address) {
		super(new TreeMap<String, Matrix>());
		setLabel(NetworkUtil.getHostName(address));

		new BackgroundTask() {

			@Override
			public Object run() {
				return searchServices();
			}

			private Object searchServices() {
				boolean portAvailable = false;
				final int delay = 3000;
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 10413), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						// map.put("JDMP", new RemoteMatrix("http://" +
						// getObject(0) + ":10413/"));
						put("JDMP", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 80), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("HTTP", new HttpMatrix("http://" + getObject(0)));
						fireValueChanged();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 443), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("HTTPS", new HttpMatrix("https://" + getObject(0)));
						fireValueChanged();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 3306), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						// map.put("MySQL", new
						// MySQLDatabaseMapMatrix(getAddress(), 3306, "root",
						// ""));
						put("MySQL", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 22), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("SSH", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 20), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("FTP", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 23), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("Telnet", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 445), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("SMB", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 1433), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("MSSQL", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 5432), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("PostgreSQL", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 11211), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("memcached", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 27017), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("mongoDB", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 25), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("SMTP", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 143), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("IMAP", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 993), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("IMAPS", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 2049), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("NFS", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 8080), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("Tomcat", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}

				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 50010), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("HDFS DataNode", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}

				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 50070), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("HDFS NameNode", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}

				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 50090), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("HDFS Secondary NameNode", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}

				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 8021), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("Hadoop JobTracker", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}

				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 50030), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("Hadoop JobTracker", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}

				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 50060), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("Hadoop TaskTracker", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}

				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 9200), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("Elasticsearch HTTP", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}

				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(address, 9300), delay);
					portAvailable = socket.isConnected();
					socket.close();
					if (portAvailable) {
						put("Elasticsearch Transport", null);
						fireValueChanged();
					}
				} catch (Exception e) {
				}

				return null;
			}
		};
	}

}

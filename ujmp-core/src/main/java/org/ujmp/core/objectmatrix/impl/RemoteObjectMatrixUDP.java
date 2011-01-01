/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

package org.ujmp.core.objectmatrix.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.objectmatrix.stub.AbstractDenseObjectMatrix;

public class RemoteObjectMatrixUDP extends AbstractDenseObjectMatrix {
	private static final long serialVersionUID = 3889079475875267966L;

	private static final int BUFFERSIZE = 512;

	private static final int TIMEOUT = 1000;

	private DatagramPacket receivedPacket = null;

	private DatagramSocket socket = null;

	private SocketAddress destination = null;

	public RemoteObjectMatrixUDP(String server, int port) {
		try {
			socket = new DatagramSocket();
			socket.setSoTimeout(TIMEOUT);
			destination = new InetSocketAddress(server, port);
			receivedPacket = new DatagramPacket(new byte[BUFFERSIZE], BUFFERSIZE);
		} catch (Exception e) {
			throw new MatrixException("could not connnect to matrix", e);
		}
	}

	public synchronized long[] getSize() {
		// TODO: not working
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(BUFFERSIZE);
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeInt(ServerObjectMatrixUDP.GETDIMENSIONCOUNT);
			// oos.writeInt(dimension);
			oos.flush();
			oos.close();
			DatagramPacket sentPacket = new DatagramPacket(bos.toByteArray(), bos.size(),
					destination);
			socket.send(sentPacket);
			socket.receive(receivedPacket);
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(receivedPacket
					.getData()));
			int command = ois.readInt();
			if (command != ServerObjectMatrixUDP.GETDIMENSIONCOUNT) {
				throw new MatrixException("could not set value");
			}
			ois.close();
			return null;
		} catch (Exception e) {
			throw new MatrixException("could not send packet", e);
		}
	}

	public synchronized double getAsDouble(long... coordinates) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(BUFFERSIZE);
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeInt(ServerObjectMatrixUDP.GETDOUBLEVALUE);
			oos.writeObject(coordinates);
			oos.flush();
			oos.close();
			DatagramPacket sentPacket = new DatagramPacket(bos.toByteArray(), bos.size(),
					destination);
			socket.send(sentPacket);
			socket.receive(receivedPacket);
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(receivedPacket
					.getData()));
			int command = ois.readInt();
			if (command != ServerObjectMatrixUDP.GETDOUBLEVALUE) {
				throw new MatrixException("could not get value");
			}
			double result = ois.readDouble();
			ois.close();
			return result;
		} catch (Exception e) {
			throw new MatrixException("could not send packet", e);
		}
	}

	public synchronized void setAsDouble(double value, long... coordinates) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(BUFFERSIZE);
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeInt(ServerObjectMatrixUDP.SETDOUBLEVALUE);
			oos.writeObject(coordinates);
			oos.writeDouble(value);
			oos.flush();
			oos.close();
			DatagramPacket sentPacket = new DatagramPacket(bos.toByteArray(), bos.size(),
					destination);
			socket.send(sentPacket);
			socket.receive(receivedPacket);
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(receivedPacket
					.getData()));
			int command = ois.readInt();
			ois.close();
			if (command != ServerObjectMatrixUDP.SETDOUBLEVALUE) {
				throw new MatrixException("could not set value");
			}
		} catch (Exception e) {
			throw new MatrixException("could not send packet", e);
		}
	}

	public Object getObject(long... coordinates) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setObject(Object o, long... coordinates) {
		// TODO Auto-generated method stub

	}

}

/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

package org.ujmp.core.genericmatrix;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;

import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.exceptions.MatrixException;

public class ServerMatrixUDP<A> extends AbstractGenericMatrix<A> {
	private static final long serialVersionUID = 3907994158174208114L;

	private static final int BUFFERSIZE = 512;

	public static final int GETDOUBLEVALUE = 0;

	public static final int SETDOUBLEVALUE = 1;

	public static final int GETDIMENSIONCOUNT = 2;

	private Matrix matrix = null;

	private DatagramSocket socket = null;

	private DatagramPacket receivedPacket = null;

	private ServerThread thread = null;

	public ServerMatrixUDP(Matrix matrix, int port) {
		this.matrix = matrix;

		try {
			receivedPacket = new DatagramPacket(new byte[BUFFERSIZE], BUFFERSIZE);
			thread = new ServerThread();
			socket = new DatagramSocket(port);
			thread.start();
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not open socket", e);
		}
	}

	public Iterable<long[]> allCoordinates() {
		return matrix.allCoordinates();
	}

	public long[] getSize() {
		return matrix.getSize();
	}

	public double getAsDouble(long... coordinates) throws MatrixException {
		return matrix.getAsDouble(coordinates);
	}

	@Override
	public A getObject(long... coordinates) throws MatrixException {
		return (A) matrix.getObject(coordinates);
	}

	@Override
	public long getValueCount() {
		return matrix.getValueCount();
	}

	public boolean isSparse() {
		return matrix.isSparse();
	}

	public void setAsDouble(double value, long... coordinates) throws MatrixException {
		matrix.setAsDouble(value, coordinates);
	}

	public void setObject(Object o, long... coordinates) throws MatrixException {
		matrix.setObject(o, coordinates);
	}

	class ServerThread extends Thread {

		public ServerThread() {

		}

		@Override
		public void run() {

			try {

				while (true) {

					socket.receive(receivedPacket);

					ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(receivedPacket.getData()));
					ByteArrayOutputStream bos = new ByteArrayOutputStream(BUFFERSIZE);
					ObjectOutputStream oos = new ObjectOutputStream(bos);

					int command = ois.readInt();
					Coordinates coordinates = null;
					double value = 0.0;
					switch (command) {
					case SETDOUBLEVALUE:
						coordinates = (Coordinates) ois.readObject();
						value = ois.readDouble();
						setAsDouble(value, coordinates.dimensions);
						oos.writeInt(SETDOUBLEVALUE);
						break;
					case GETDOUBLEVALUE:
						coordinates = (Coordinates) ois.readObject();
						value = getAsDouble(coordinates.dimensions);
						oos.writeInt(GETDOUBLEVALUE);
						oos.writeDouble(value);
						break;
					case GETDIMENSIONCOUNT:
						int dimension = ois.readInt();
						int result = (int) getSize(dimension);
						oos.writeInt(GETDIMENSIONCOUNT);
						oos.writeInt(result);
						break;
					}

					oos.flush();

					DatagramPacket sentPacket = new DatagramPacket(bos.toByteArray(), bos.size(), receivedPacket
							.getAddress(), receivedPacket.getPort());
					socket.send(sentPacket);

				}

			} catch (Exception e) {
				logger.log(Level.WARNING, "error in data transmission", e);
			}
		}
	}

	public boolean contains(long... coordinates) {
		return matrix.contains(coordinates);
	}

	@Override
	public boolean isReadOnly() {
		return matrix.isReadOnly();
	}

	public EntryType getEntryType() {
		return matrix.getEntryType();
	}

}

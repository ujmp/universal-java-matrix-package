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

package org.ujmp.core.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import org.ujmp.core.Matrix;

public class MatrixCommunicationThread extends Thread {

	private final Matrix matrix;
	private final InputStream inputStream;
	private final OutputStream outputStream;
	private final Socket socket;

	public MatrixCommunicationThread(Matrix matrix, Socket socket) throws IOException {
		this.matrix = matrix;
		this.socket = socket;
		this.inputStream = socket.getInputStream();
		this.outputStream = socket.getOutputStream();
		this.setName("Matrix Communication on Port " + socket.getPort());
		this.setDaemon(true);
		this.start();
	}

	public void run() {
		byte[] inputBuffer = new byte[1000000];
		byte[] outputBuffer = new byte[1000000];
		ByteBuffer inputBB = ByteBuffer.wrap(inputBuffer);
		ByteBuffer outputBB = ByteBuffer.wrap(outputBuffer);
		try {
			while (socket.isConnected() && !socket.isInputShutdown() && !socket.isOutputShutdown()) {
				inputStream.read(inputBuffer);
				inputBB.rewind();
				outputBB.rewind();
				int type = inputBB.getInt();
				switch (type) {
				case ClientMatrix.GETSIZE:
					outputBB.putLong(matrix.getRowCount());
					outputBB.putLong(matrix.getColumnCount());
					break;
				case ClientMatrix.GETDOUBLE:
					outputBB.putDouble(matrix.getAsDouble(inputBB.getLong(), inputBB.getLong()));
					break;
				case ClientMatrix.SETDOUBLE:
					matrix.setAsDouble(inputBB.getDouble(), inputBB.getLong(), inputBB.getLong());
					break;
				default:
					throw new RuntimeException("unknown command: " + type);
				}

				outputStream.write(outputBuffer, 0, outputBB.position());
				outputStream.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

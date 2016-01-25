/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

import java.net.ServerSocket;
import java.net.Socket;

import org.ujmp.core.Matrix;

public class MatrixSocketThread extends Thread {

	private final Matrix matrix;
	private final ServerSocket serverSocket;

	public MatrixSocketThread(Matrix matrix, ServerSocket serverSocket) {
		this.matrix = matrix;
		this.serverSocket = serverSocket;
		this.setName("Matrix Socket on Port " + serverSocket.getLocalPort());
		this.setDaemon(true);
		this.start();
	}

	public void run() {
		while (!serverSocket.isClosed()) {
			try {
				System.out.println("UJMP ServerMatrix is waiting for connections on port "
						+ serverSocket.getLocalPort());
				Socket socket = serverSocket.accept();
				new MatrixCommunicationThread(matrix, socket);
				System.out.println("UJMP ClientMatrix connected on port "
						+ serverSocket.getLocalPort());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

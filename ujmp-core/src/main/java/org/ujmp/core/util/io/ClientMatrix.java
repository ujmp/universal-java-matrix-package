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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;

public class ClientMatrix extends AbstractDenseDoubleMatrix2D {
	private static final long serialVersionUID = 7947043738616156003L;

	public static final int GETSIZE = 1;
	public static final int GETDOUBLE = 2;
	public static final int SETDOUBLE = 3;
	public static final int ISREADONLY = 4;
	public static final int PLUSMATRIX = 5;

	private final byte[] inputBuffer = new byte[1000000];
	private final byte[] outputBuffer = new byte[1000000];
	private final ByteBuffer inputBB = ByteBuffer.wrap(inputBuffer);
	private final ByteBuffer outputBB = ByteBuffer.wrap(outputBuffer);
	private final InputStream inputStream;
	private final OutputStream outputStream;
	private final boolean isReadOnly;

	public ClientMatrix(int port) throws UnknownHostException, IOException {
		this(open("localhost", port));
	}

	public ClientMatrix(String host, int port) throws UnknownHostException, IOException {
		this(open(host, port));
	}

	private static Socket open(String host, int port) throws UnknownHostException, IOException {
		return new Socket(host, port);
	}

	public ClientMatrix(Socket socket) throws IOException {
		this(getInputStream(socket), getOutputStream(socket));
	}

	public ClientMatrix(InputStream inputStream, OutputStream outputStream) throws IOException {
		this(inputStream, outputStream, getSize(inputStream, outputStream), isReadOnly(inputStream,
				outputStream));
	}

	private ClientMatrix(InputStream inputStream, OutputStream outputStream, long[] size,
			boolean isReadOnly) {
		super(size[ROW], size[COLUMN]);
		this.inputStream = inputStream;
		this.outputStream = outputStream;
		this.isReadOnly = isReadOnly;
	}

	private static InputStream getInputStream(Socket socket) throws IOException {
		return socket.getInputStream();
	}

	private static OutputStream getOutputStream(Socket socket) throws IOException {
		return socket.getOutputStream();
	}

	private static long[] getSize(InputStream inputStream, OutputStream outputStream)
			throws IOException {
		byte[] buffer = new byte[128];
		ByteBuffer bb = ByteBuffer.wrap(buffer);
		bb.putInt(GETSIZE);
		outputStream.write(buffer, 0, bb.position());
		outputStream.flush();
		inputStream.read(buffer);
		bb.rewind();
		return new long[] { bb.getLong(), bb.getLong() };
	}

	private static boolean isReadOnly(InputStream inputStream, OutputStream outputStream)
			throws IOException {
		byte[] buffer = new byte[128];
		ByteBuffer bb = ByteBuffer.wrap(buffer);
		bb.putInt(ISREADONLY);
		outputStream.write(buffer, 0, bb.position());
		outputStream.flush();
		inputStream.read(buffer);
		bb.rewind();
		return bb.get() == 1;
	}

	public synchronized double getDouble(long row, long column) {
		try {
			outputBB.rewind();
			outputBB.putInt(GETDOUBLE);
			outputBB.putLong(row);
			outputBB.putLong(column);
			outputStream.write(outputBuffer, 0, outputBB.position());
			outputStream.flush();
			inputStream.read(inputBuffer);
			inputBB.rewind();
			return inputBB.getDouble();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public final boolean isReadOnly() {
		return isReadOnly;
	}

	public synchronized void setDouble(double value, long row, long column) {
		if (isReadOnly) {
			throw new RuntimeException("matrix is read only");
		}
		try {
			outputBB.rewind();
			outputBB.putInt(SETDOUBLE);
			outputBB.putDouble(value);
			outputBB.putLong(row);
			outputBB.putLong(column);
			outputStream.write(outputBuffer, 0, outputBB.position());
			outputStream.flush();
			fireValueChanged();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized Matrix plus(Ret returnType, boolean ignoreNaN, Matrix m) {
		if (returnType == Ret.ORIG) {
			try {
				outputBB.rewind();
				outputBB.putInt(PLUSMATRIX);
				outputStream.write(outputBuffer, 0, outputBB.position());
				outputStream.flush();
				fireValueChanged();
				return this;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			return super.plus(returnType, ignoreNaN, m);
		}
	}

	public double getDouble(int row, int column) {
		return getDouble((long) row, (long) column);
	}

	public void setDouble(double value, int row, int column) {
		setDouble(value, (long) row, (long) column);
	}

}

/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;

import org.ujmp.core.collections.SoftHashMap;

public class BufferedRandomAccessFile extends RandomAccessFile {

	private int bufferSize = 65536;

	private final Map<Long, byte[]> buffer = new SoftHashMap<Long, byte[]>();

	public BufferedRandomAccessFile(File file, String mode) throws FileNotFoundException {
		super(file, mode);
	}

	public BufferedRandomAccessFile(File file, String mode, int bufferSize)
			throws FileNotFoundException {
		super(file, mode);
		this.bufferSize = bufferSize;
	}

	
	public synchronized int read() throws IOException {
		byte[] b = new byte[1];
		read(b);
		seek(getFilePointer() + 1);
		new IOException("don't use this method").printStackTrace();
		return b[0];
	}

	
	public synchronized void seek(long pos) throws IOException {
		new IOException("don't use this method").printStackTrace();
		super.seek(pos);

	}

	
	public synchronized int read(byte[] b) throws IOException {
		new IOException("don't use this method").printStackTrace();
		return super.read(b);
	}

	public synchronized int read(long seek, byte b[]) throws IOException {
		if (b.length > bufferSize) {
			throw (new IOException("cannot read more than buffersize"));
		}

		long pos = (seek / bufferSize) * bufferSize;
		int offset = (int) (seek - pos);

		byte[] bytes = buffer.get(pos);
		if (bytes == null) {
			super.seek(pos);
			bytes = new byte[bufferSize];
			super.read(bytes);
			buffer.put(pos, bytes);
		}

		if (offset + b.length > bufferSize) {
			System.arraycopy(bytes, offset, b, 0, bufferSize - offset);
			pos += bufferSize;
			bytes = buffer.get(pos);
			if (bytes == null) {
				super.seek(pos);
				bytes = new byte[bufferSize];
				super.read(bytes);
				buffer.put(pos, bytes);
			}
			System.arraycopy(bytes, 0, b, bufferSize - offset, b.length - bufferSize + offset);
		} else {
			System.arraycopy(bytes, offset, b, 0, b.length);
		}
		return b.length;

	}

	public synchronized void writeByte(long seek, byte value) throws IOException {
		super.seek(seek);
		super.writeByte(value);
		buffer.clear();
	}

	public synchronized void writeChar(long seek, char value) throws IOException {
		super.seek(seek);
		super.writeChar(value);
		buffer.clear();
	}

	public synchronized void writeDouble(long seek, double value) throws IOException {
		super.seek(seek);
		super.writeDouble(value);
		buffer.clear();
	}

	public synchronized void writeFloat(long seek, float value) throws IOException {
		super.seek(seek);
		super.writeFloat(value);
		buffer.clear();
	}

	public synchronized void writeShort(long seek, short value) throws IOException {
		super.seek(seek);
		super.writeShort(value);
		buffer.clear();
	}

	public synchronized void write(long seek, byte[] array) throws IOException {
		super.seek(seek);
		super.write(array);
		buffer.clear();
	}

	public synchronized void writeInt(long seek, int value) throws IOException {
		super.seek(seek);
		super.writeInt(value);
		buffer.clear();
	}

	public synchronized void writeLong(long seek, long value) throws IOException {
		super.seek(seek);
		super.writeLong(value);
		buffer.clear();
	}

}

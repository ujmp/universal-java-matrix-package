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

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.ujmp.core.bytearraymatrix.stub.AbstractDenseByteArrayMatrix2D;

public class ByteArrayMappedFileMatrix extends AbstractDenseByteArrayMatrix2D implements Closeable {
	private static final long serialVersionUID = -6947027888476772778L;

	private long sizePerByteBuffer = 128 * 1024 * 1024;

	private long size;

	private int bytesPerCell = 4;

	RandomAccessFile ra;
	FileChannel fc;

	// maximal size for one ByteBuffer is Integer.MAXVALUE so we have to use
	// more than one
	private final List<MappedByteBuffer> byteBufferList = new ArrayList<MappedByteBuffer>();

	public ByteArrayMappedFileMatrix(String filename) throws IOException {
		this(new File(filename));
	}

	public ByteArrayMappedFileMatrix(String filename, OpenOption... openOptions) throws IOException {
		this(new File(filename), openOptions);
	}

	public ByteArrayMappedFileMatrix(Path path, OpenOption... openOptions) throws IOException {
		ra = new RandomAccessFile(path.toFile(), "rw");
		fc = ra.getChannel();
		size = fc.size();
		for (long pos = 0; pos < size; pos += sizePerByteBuffer) {
			byteBufferList.add(fc.map(MapMode.READ_WRITE, pos,
					Math.min(sizePerByteBuffer, size - pos)));
		}
	}

	public ByteArrayMappedFileMatrix(File file, OpenOption... openOptions) throws IOException {
		this(file.toPath(), openOptions);
	}

	public ByteArrayMappedFileMatrix(File file) throws IOException {
		this(file.toPath());
	}

	public byte[] getByteArray(long row, long column) {
		int byteBufferId = (int) (row / sizePerByteBuffer);
		int pos = (int) (row - (byteBufferId * sizePerByteBuffer));
		MappedByteBuffer mb = byteBufferList.get(byteBufferId);
		byte[] result = new byte[bytesPerCell];
		mb.get(result, pos, bytesPerCell);
		return result;
	}

	public void setByteArray(byte[] value, long row, long column) {
		int byteBufferId = (int) (row / sizePerByteBuffer);
		int pos = (int) (row - (byteBufferId * sizePerByteBuffer));
		MappedByteBuffer mb = byteBufferList.get(byteBufferId);
		// todo: two different byte buffers
		mb.put(value, pos, bytesPerCell);
	}

	public byte[] getByteArray(int row, int column) {
		return getByteArray((long) row, (long) column);
	}

	public void setByteArray(byte[] value, int row, int column) {
		setByteArray(value, (long) row, (long) column);
	}

	public long[] getSize() {
		return new long[] { size, 1 };
	}

	public void close() throws IOException {
		fc.close();
		ra.close();
	}
}

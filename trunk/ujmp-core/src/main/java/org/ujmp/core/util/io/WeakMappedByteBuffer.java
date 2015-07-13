/*
 * Copyright (C) 2008-2015 by Holger Arndt
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
import java.io.RandomAccessFile;
import java.lang.ref.WeakReference;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import org.ujmp.core.util.MathUtil;

public class WeakMappedByteBuffer {

	public static final int DEFAULTBUFFERSIZE = AbstractByteBufferConcatenation.DEFAULTBUFFERSIZE;

	private WeakReference<ByteBuffer> byteBufferReference = null;
	private final FileChannel fileChannel;
	private final MapMode mapMode;
	private final long pos;
	private final long size;

	public WeakMappedByteBuffer(FileChannel fileChannel, MapMode mapMode, long pos, int size) {
		this.fileChannel = fileChannel;
		this.mapMode = mapMode;
		this.pos = pos;
		this.size = size;
	}

	public static final WeakMappedByteBuffer[] create(RandomAccessFile randomAccessFile)
			throws IOException {
		long fileLength = randomAccessFile.length();
		FileChannel fc = randomAccessFile.getChannel();

		MapMode mapMode = MapMode.READ_ONLY;

		final int bufferCount = (int) Math.ceil((double) fileLength / (double) DEFAULTBUFFERSIZE);
		final WeakMappedByteBuffer[] buffers = new WeakMappedByteBuffer[bufferCount];
		int i = 0;
		for (long filePos = 0; filePos < fileLength; filePos += DEFAULTBUFFERSIZE) {
			WeakMappedByteBuffer buf = new WeakMappedByteBuffer(fc, mapMode, filePos,
					MathUtil.longToInt(Math.min(DEFAULTBUFFERSIZE, fileLength - filePos)));
			buffers[i++] = buf;
		}
		return buffers;
	}

	public ByteBuffer getOrCreateByteBuffer() {
		try {
			ByteBuffer byteBuffer = byteBufferReference == null ? null : byteBufferReference.get();
			if (byteBuffer == null || byteBufferReference == null
					|| byteBufferReference.get() == null) {
				synchronized (this) {
					if (byteBuffer == null || byteBufferReference == null
							|| byteBufferReference.get() == null) {
						byteBuffer = fileChannel.map(mapMode, pos, size);
						byteBufferReference = new WeakReference<ByteBuffer>(byteBuffer);
					}
				}
			}
			return byteBuffer;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public ByteBuffer slice() {
		return getOrCreateByteBuffer().slice();
	}

	public ByteBuffer duplicate() {
		return getOrCreateByteBuffer().duplicate();
	}

	public ByteBuffer asReadOnlyBuffer() {
		return getOrCreateByteBuffer().asReadOnlyBuffer();
	}

	public byte get() {
		return getOrCreateByteBuffer().get();
	}

	public ByteBuffer put(byte b) {
		return getOrCreateByteBuffer().put(b);
	}

	public byte get(int index) {
		return getOrCreateByteBuffer().get(index);
	}

	public ByteBuffer put(int index, byte b) {
		return getOrCreateByteBuffer().put(index, b);
	}

	public ByteBuffer compact() {
		return getOrCreateByteBuffer().compact();
	}

	public boolean isDirect() {
		return getOrCreateByteBuffer().isDirect();
	}

	public char getChar() {
		return getOrCreateByteBuffer().getChar();
	}

	public ByteBuffer putChar(char value) {
		return getOrCreateByteBuffer().putChar(value);
	}

	public char getChar(int index) {
		return getOrCreateByteBuffer().getChar(index);
	}

	public ByteBuffer putChar(int index, char value) {
		return getOrCreateByteBuffer().putChar(index, value);
	}

	public CharBuffer asCharBuffer() {
		return getOrCreateByteBuffer().asCharBuffer();
	}

	public short getShort() {
		return getOrCreateByteBuffer().getShort();
	}

	public ByteBuffer putShort(short value) {
		return getOrCreateByteBuffer().putShort(value);
	}

	public short getShort(int index) {
		return getOrCreateByteBuffer().getShort(index);
	}

	public ByteBuffer putShort(int index, short value) {
		return getOrCreateByteBuffer().putShort(index, value);
	}

	public ShortBuffer asShortBuffer() {
		return getOrCreateByteBuffer().asShortBuffer();
	}

	public int getInt() {
		return getOrCreateByteBuffer().getInt();
	}

	public ByteBuffer putInt(int value) {
		return getOrCreateByteBuffer().putInt(value);
	}

	public int getInt(int index) {
		return getOrCreateByteBuffer().getInt(index);
	}

	public ByteBuffer putInt(int index, int value) {
		return getOrCreateByteBuffer().putInt(index, value);
	}

	public IntBuffer asIntBuffer() {
		return getOrCreateByteBuffer().asIntBuffer();
	}

	public long getLong() {
		return getOrCreateByteBuffer().getLong();
	}

	public ByteBuffer putLong(long value) {
		return getOrCreateByteBuffer().putLong(value);
	}

	public long getLong(int index) {
		return getOrCreateByteBuffer().getLong(index);
	}

	public ByteBuffer putLong(int index, long value) {
		return getOrCreateByteBuffer().putLong(index, value);
	}

	public LongBuffer asLongBuffer() {
		return getOrCreateByteBuffer().asLongBuffer();
	}

	public float getFloat() {
		return getOrCreateByteBuffer().getFloat();
	}

	public ByteBuffer putFloat(float value) {
		return getOrCreateByteBuffer().putFloat(value);
	}

	public float getFloat(int index) {
		return getOrCreateByteBuffer().getFloat(index);
	}

	public ByteBuffer putFloat(int index, float value) {
		return getOrCreateByteBuffer().putFloat(index, value);
	}

	public FloatBuffer asFloatBuffer() {
		return getOrCreateByteBuffer().asFloatBuffer();
	}

	public double getDouble() {
		return getOrCreateByteBuffer().getDouble();
	}

	public ByteBuffer putDouble(double value) {
		return getOrCreateByteBuffer().putDouble(value);
	}

	public double getDouble(int index) {
		return getOrCreateByteBuffer().getDouble(index);
	}

	public ByteBuffer putDouble(int index, double value) {
		return getOrCreateByteBuffer().putDouble(index, value);
	}

	public DoubleBuffer asDoubleBuffer() {
		return getOrCreateByteBuffer().asDoubleBuffer();
	}

	public boolean isReadOnly() {
		return getOrCreateByteBuffer().isReadOnly();
	}

	public long capacity() {
		return getOrCreateByteBuffer().capacity();
	}

	public void position(int offset) {
		getOrCreateByteBuffer().position(offset);
	}

	public void put(byte[] bytes, int offset, int length) {
		getOrCreateByteBuffer().put(bytes, offset, length);
	}

	public void get(byte[] dst, int offset, int length) {
		getOrCreateByteBuffer().get(dst, offset, length);
	}

	public Buffer rewind() {
		return getOrCreateByteBuffer().rewind();
	}

	public Buffer reset() {
		return getOrCreateByteBuffer().rewind();
	}

}

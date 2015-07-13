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

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import org.ujmp.core.collections.list.FastArrayList;
import org.ujmp.core.util.MathUtil;

public class MemoryByteBufferConcatenation extends AbstractMemoryByteBufferConcatenation {

	private final boolean useDirect;
	private final int maxBufferSize;

	public MemoryByteBufferConcatenation(final long length) {
		this(true, length);
	}

	public MemoryByteBufferConcatenation(final boolean useDirect, final long length) {
		this(useDirect, DEFAULTBUFFERSIZE, createByteBuffers(useDirect, DEFAULTBUFFERSIZE, length));
	}

	public MemoryByteBufferConcatenation(final ByteBuffer... byteBuffers) {
		this(true, DEFAULTBUFFERSIZE, byteBuffers);
	}

	public MemoryByteBufferConcatenation(final boolean useDirect, final int maxBufferSize,
			final ByteBuffer... byteBuffers) {
		super(byteBuffers);
		this.useDirect = useDirect;
		this.maxBufferSize = maxBufferSize;
	}

	private static final ByteBuffer[] createByteBuffers(final boolean useDirect,
			final long singleBufferSize, final long length) {
		final int count = MathUtil.longToInt((long) Math.ceil((double) length / singleBufferSize));
		final ByteBuffer[] byteBuffers = new ByteBuffer[count];
		long remaining = length;
		for (int i = 0; i < count; i++) {
			final int size = (int) Math.min(singleBufferSize, remaining);
			if (useDirect) {
				byteBuffers[i] = ByteBuffer.allocateDirect(size);
			} else {
				byteBuffers[i] = ByteBuffer.allocate(size);
			}
			remaining -= size;
		}
		return byteBuffers;
	}

	public void insertBytes(final byte[] bytes, final long position) {
		throw new RuntimeException("not supported");
	}

	public void expand(long bytesToAdd) {
		synchronized (this) {
			List<ByteBuffer> byteBufferList = Arrays.asList(byteBuffers);
			final ByteBuffer lastBuffer = byteBufferList.remove(byteBufferList.size() - 1);
			if (lastBuffer.capacity() < maxBufferSize) {
				// expand last buffer to maximum size
				int newSize = (int) Math.min(maxBufferSize, lastBuffer.capacity() + bytesToAdd);
				bytesToAdd -= newSize - lastBuffer.capacity();
				// copy data
				ByteBuffer newBuffer = allocate(useDirect, newSize);
				newBuffer.put(lastBuffer);
				byteBufferList.add(newBuffer);
			}
			// create new buffers
			while (bytesToAdd > 0) {
				int newSize = (int) Math.min(maxBufferSize, bytesToAdd);
				ByteBuffer newBuffer = allocate(useDirect, newSize);
				byteBufferList.add(newBuffer);
				bytesToAdd -= newSize;
			}
			byteBuffers = byteBufferList.toArray(byteBuffers);
		}
	}

	public void shrink(long bytesToRemove) {
		synchronized (this) {
			List<ByteBuffer> byteBufferList = new FastArrayList<ByteBuffer>(byteBuffers);
			while (bytesToRemove > 0) {
				ByteBuffer lastBuffer = byteBufferList.remove(byteBufferList.size() - 1);
				int capacity = lastBuffer.capacity();
				if (capacity <= bytesToRemove) {
					totalLength -= capacity;
					bytesToRemove -= capacity;
				} else {
					int newSize = (int) (capacity - bytesToRemove);
					totalLength -= bytesToRemove;
					bytesToRemove = 0;
					ByteBuffer newBuffer = allocate(useDirect, newSize);
					lastBuffer.rewind();
					for (int i = 0; i < newSize; i++) {
						newBuffer.put(lastBuffer.get());
					}
					byteBufferList.add(newBuffer);
				}
			}
			byteBuffers = byteBufferList.toArray(byteBuffers);
		}
	}

	private static final ByteBuffer allocate(final boolean useDirect, final int capacity) {
		if (useDirect) {
			return ByteBuffer.allocateDirect(capacity);
		} else {
			return ByteBuffer.allocate(capacity);
		}
	}

}

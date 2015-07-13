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

public abstract class AbstractMemoryByteBufferConcatenation extends AbstractByteBufferConcatenation {

	protected ByteBuffer[] byteBuffers;
	protected long totalLength;

	public AbstractMemoryByteBufferConcatenation(final ByteBuffer... byteBuffers) {
		if (byteBuffers.length == 0) {
			throw new IllegalArgumentException("byteBuffers is empty");
		}
		for (ByteBuffer bb : byteBuffers) {
			if (bb == null) {
				throw new IllegalArgumentException("cannot use ByteBuffer which is null");
			} else if (bb.capacity() == 0) {
				throw new IllegalArgumentException("cannot use ByteBuffer which has no capacity");
			}
		}
		this.byteBuffers = byteBuffers;
		long length = 0;
		for (int i = 0; i < byteBuffers.length; i++) {
			length += byteBuffers[i].capacity();
		}
		this.totalLength = length;
	}

	public final long getLength() {
		return totalLength;
	}

	public final void deleteBytes(final long position, final int count) {
		synchronized (this) {
			final byte[] copyBuffer = new byte[DEFAULTBUFFERSIZE];
			for (long pos = position; pos < totalLength; pos += DEFAULTBUFFERSIZE) {
				final int length = (int) Math.min(DEFAULTBUFFERSIZE, totalLength - pos - count);
				getBytes(copyBuffer, pos + count, length);
				setBytes(copyBuffer, pos, length);
			}
			shrink(count);
		}
	}

	public final String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < byteBuffers.length; i++) {
			sb.append(byteBuffers[i].capacity());
			sb.append(",");
		}
		sb.append(" total: ");
		sb.append(totalLength);
		return sb.toString();
	}

	public final byte getByte(final long position) {
		int byteBufferId = 0;
		long offset = position;
		while (offset >= byteBuffers[byteBufferId].capacity()) {
			offset -= byteBuffers[byteBufferId++].capacity();
		}
		return byteBuffers[byteBufferId].get((int) offset);
	}

	public final void setByte(final byte b, final long position) {
		int byteBufferId = 0;
		long offset = position;
		while (offset >= byteBuffers[byteBufferId].capacity()) {
			offset -= byteBuffers[byteBufferId++].capacity();
		}
		byteBuffers[byteBufferId].put((int) offset, b);
	}

	public final void getBytes(final byte[] bytes, final long position, final int length) {
		if (position + length > totalLength) {
			throw new IllegalArgumentException("cannot read beyond buffer size");
		}
		int byteBufferId = 0;
		long offset = position;
		while (offset >= byteBuffers[byteBufferId].capacity()) {
			offset -= byteBuffers[byteBufferId++].capacity();
		}
		int remaining = length;
		int pos = 0;
		while (remaining > 0) {
			final int byteCount = Math.min((int) (byteBuffers[byteBufferId].capacity() - offset),
					remaining);
			synchronized (byteBuffers[byteBufferId]) {
				byteBuffers[byteBufferId].position((int) offset);
				byteBuffers[byteBufferId].get(bytes, pos, byteCount);
			}
			remaining -= byteCount;
			pos += byteCount;
			byteBufferId++;
			offset = 0;
		}
	}

	public final void setBytes(final byte[] bytes, final long position, final int length) {
		synchronized (this) {
			int byteBufferId = 0;
			long offset = position;
			while (offset >= byteBuffers[byteBufferId].capacity()) {
				offset -= byteBuffers[byteBufferId++].capacity();
			}
			int remaining = length;
			int pos = 0;
			while (remaining > 0) {
				final int byteCount = Math.min(
						(int) (byteBuffers[byteBufferId].capacity() - offset), remaining);
				synchronized (byteBuffers[byteBufferId]) {
					byteBuffers[byteBufferId].position((int) offset);
					byteBuffers[byteBufferId].put(bytes, pos, byteCount);
				}
				remaining -= byteCount;
				pos += byteCount;
				byteBufferId++;
				offset = 0;
			}
		}
	}

}

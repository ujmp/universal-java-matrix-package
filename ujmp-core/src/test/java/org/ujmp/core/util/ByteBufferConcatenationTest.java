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

package org.ujmp.core.util;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

import org.junit.Test;
import org.ujmp.core.util.io.AbstractByteBufferConcatenation;
import org.ujmp.core.util.io.MemoryByteBufferConcatenation;

public class ByteBufferConcatenationTest {

	@Test
	public void testSingleRead() {
		ByteBuffer b1 = ByteBuffer.wrap(new byte[] { 0, 1, 2, 3, 4, 5 });
		ByteBuffer b2 = ByteBuffer.wrap(new byte[] { 6 });
		ByteBuffer b3 = ByteBuffer.wrap(new byte[] { 7, 8 });
		ByteBuffer b4 = ByteBuffer.wrap(new byte[] { 9, 10, 11, 12, 13, 14, 15 });
		AbstractByteBufferConcatenation bb = new MemoryByteBufferConcatenation(b1, b2, b3, b4);

		assertEquals(bb.getLength(), 16);

		for (int i = 0; i < 16; i++) {
			assertEquals(bb.getByte(i), i);
		}
	}

	@Test
	public void testSingleWrite() {
		ByteBuffer b1 = ByteBuffer.wrap(new byte[] { 0, 0, 0, 0, 0, 0 });
		ByteBuffer b2 = ByteBuffer.wrap(new byte[] { 0 });
		ByteBuffer b3 = ByteBuffer.wrap(new byte[] { 0, 0 });
		ByteBuffer b4 = ByteBuffer.wrap(new byte[] { 0, 0, 0, 0, 0, 0, 0 });
		AbstractByteBufferConcatenation bb = new MemoryByteBufferConcatenation(b1, b2, b3, b4);

		assertEquals(bb.getLength(), 16);

		for (int i = 0; i < 16; i++) {
			bb.setByte((byte) i, i);
		}

		for (int i = 0; i < 16; i++) {
			assertEquals(bb.getByte(i), i);
		}
	}

	@Test
	public void testReadAll() {
		ByteBuffer b1 = ByteBuffer.wrap(new byte[] { 0, 1, 2, 3, 4, 5 });
		ByteBuffer b2 = ByteBuffer.wrap(new byte[] { 6 });
		ByteBuffer b3 = ByteBuffer.wrap(new byte[] { 7, 8 });
		ByteBuffer b4 = ByteBuffer.wrap(new byte[] { 9, 10, 11, 12, 13, 14, 15 });
		AbstractByteBufferConcatenation bb = new MemoryByteBufferConcatenation(b1, b2, b3, b4);

		byte[] bytes = new byte[16];
		bb.getBytes(bytes, 0);

		for (int i = 0; i < 16; i++) {
			assertEquals(bytes[i], i);
		}
	}

	@Test
	public void testWriteAll() {
		ByteBuffer b1 = ByteBuffer.wrap(new byte[] { 0, 0, 0, 0, 0, 0 });
		ByteBuffer b2 = ByteBuffer.wrap(new byte[] { 0 });
		ByteBuffer b3 = ByteBuffer.wrap(new byte[] { 0, 0 });
		ByteBuffer b4 = ByteBuffer.wrap(new byte[] { 0, 0, 0, 0, 0, 0, 0 });
		AbstractByteBufferConcatenation bb = new MemoryByteBufferConcatenation(b1, b2, b3, b4);

		byte[] bytes = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
		bb.setBytes(bytes, 0);

		for (int i = 0; i < 16; i++) {
			assertEquals(bytes[i], i);
		}
	}

	@Test
	public void testLongReadWrite() {
		AbstractByteBufferConcatenation bb = new MemoryByteBufferConcatenation(30000);

		for (long i = bb.getLength() - 1; --i != -1;) {
			bb.setByte((byte) (i % 100), i);
		}

		for (long i = bb.getLength() - 1; --i != -1;) {
			assertEquals(bb.getByte(i), i % 100);
		}
	}

	@Test
	public void testReadSecondHalf() {
		ByteBuffer b1 = ByteBuffer.wrap(new byte[] { 0, 1, 2, 3, 4, 5 });
		ByteBuffer b2 = ByteBuffer.wrap(new byte[] { 6 });
		ByteBuffer b3 = ByteBuffer.wrap(new byte[] { 7, 8 });
		ByteBuffer b4 = ByteBuffer.wrap(new byte[] { 9, 10, 11, 12, 13, 14, 15 });
		AbstractByteBufferConcatenation bb = new MemoryByteBufferConcatenation(b1, b2, b3, b4);

		byte[] bytes = new byte[8];
		bb.getBytes(bytes, 8);

		for (int i = 8; i < 16; i++) {
			assertEquals(bytes[i - 8], i);
		}
	}

	@Test
	public void testWriteSecondHalf() {
		ByteBuffer b1 = ByteBuffer.wrap(new byte[] { 0, 0, 0, 0, 0, 0 });
		ByteBuffer b2 = ByteBuffer.wrap(new byte[] { 0 });
		ByteBuffer b3 = ByteBuffer.wrap(new byte[] { 0, 0 });
		ByteBuffer b4 = ByteBuffer.wrap(new byte[] { 0, 0, 0, 0, 0, 0, 0 });
		AbstractByteBufferConcatenation bb = new MemoryByteBufferConcatenation(b1, b2, b3, b4);

		byte[] bytes = new byte[] { 8, 9, 10, 11, 12, 13, 14, 15 };
		bb.setBytes(bytes, 0);

		for (int i = 8; i < 16; i++) {
			assertEquals(bytes[i - 8], i);
		}
	}

	@Test
	public void testReadFirstHalf() {
		ByteBuffer b1 = ByteBuffer.wrap(new byte[] { 0, 1, 2, 3, 4, 5 });
		ByteBuffer b2 = ByteBuffer.wrap(new byte[] { 6 });
		ByteBuffer b3 = ByteBuffer.wrap(new byte[] { 7, 8 });
		ByteBuffer b4 = ByteBuffer.wrap(new byte[] { 9, 10, 11, 12, 13, 14, 15 });
		AbstractByteBufferConcatenation bb = new MemoryByteBufferConcatenation(b1, b2, b3, b4);

		byte[] bytes = new byte[8];
		bb.getBytes(bytes, 0);

		for (int i = 0; i < 8; i++) {
			assertEquals(bytes[i], i);
		}
	}

	@Test
	public void testWriteFirstHalf() {
		ByteBuffer b1 = ByteBuffer.wrap(new byte[] { 0, 0, 0, 0, 0, 0 });
		ByteBuffer b2 = ByteBuffer.wrap(new byte[] { 0 });
		ByteBuffer b3 = ByteBuffer.wrap(new byte[] { 0, 0 });
		ByteBuffer b4 = ByteBuffer.wrap(new byte[] { 0, 0, 0, 0, 0, 0, 0 });
		AbstractByteBufferConcatenation bb = new MemoryByteBufferConcatenation(b1, b2, b3, b4);

		byte[] bytes = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
		bb.setBytes(bytes, 0);

		for (int i = 0; i < 8; i++) {
			assertEquals(bytes[i], i);
		}
	}

	@Test
	public void testReadMiddle() {
		ByteBuffer b1 = ByteBuffer.wrap(new byte[] { 0, 1, 2, 3, 4, 5 });
		ByteBuffer b2 = ByteBuffer.wrap(new byte[] { 6 });
		ByteBuffer b3 = ByteBuffer.wrap(new byte[] { 7, 8 });
		ByteBuffer b4 = ByteBuffer.wrap(new byte[] { 9, 10, 11, 12, 13, 14, 15 });
		AbstractByteBufferConcatenation bb = new MemoryByteBufferConcatenation(b1, b2, b3, b4);

		byte[] bytes = new byte[8];
		bb.getBytes(bytes, 4);

		for (int i = 4; i < 12; i++) {
			assertEquals(bytes[i - 4], i);
		}
	}

	@Test
	public void testDeleteFirst() {
		ByteBuffer b1 = ByteBuffer.wrap(new byte[] { 0, 1, 2, 3, 4, 5 });
		ByteBuffer b2 = ByteBuffer.wrap(new byte[] { 6 });
		ByteBuffer b3 = ByteBuffer.wrap(new byte[] { 7, 8 });
		ByteBuffer b4 = ByteBuffer.wrap(new byte[] { 9, 10, 11, 12, 13, 14, 15 });
		AbstractByteBufferConcatenation bb = new MemoryByteBufferConcatenation(b1, b2, b3, b4);

		bb.deleteByte(0);

		for (int i = 0; i < 15; i++) {
			assertEquals(bb.getByte(i), i + 1);
		}
	}

	@Test
	public void testDeleteSecond() {
		ByteBuffer b1 = ByteBuffer.wrap(new byte[] { 0, 1, 2, 3, 4, 5 });
		ByteBuffer b2 = ByteBuffer.wrap(new byte[] { 6 });
		ByteBuffer b3 = ByteBuffer.wrap(new byte[] { 7, 8 });
		ByteBuffer b4 = ByteBuffer.wrap(new byte[] { 9, 10, 11, 12, 13, 14, 15 });
		AbstractByteBufferConcatenation bb = new MemoryByteBufferConcatenation(b1, b2, b3, b4);

		bb.deleteByte(1);

		assertEquals(bb.getByte(0), 0);
		assertEquals(bb.getByte(1), 2);
		assertEquals(bb.getByte(2), 3);
		assertEquals(bb.getByte(3), 4);
	}

	@Test
	public void testDelete8() {
		AbstractByteBufferConcatenation bb = new MemoryByteBufferConcatenation(19000000);
		bb.deleteByte(0);
	}

	@Test
	public void testDelete6() {
		ByteBuffer b1 = ByteBuffer.wrap(new byte[] { 0, 1, 2, 3, 4, 5 });
		ByteBuffer b2 = ByteBuffer.wrap(new byte[] { 6 });
		ByteBuffer b3 = ByteBuffer.wrap(new byte[] { 7, 8 });
		ByteBuffer b4 = ByteBuffer.wrap(new byte[] { 9, 10, 11, 12, 13, 14, 15 });
		AbstractByteBufferConcatenation bb = new MemoryByteBufferConcatenation(b1, b2, b3, b4);

		bb.deleteByte(6);

		assertEquals(bb.getByte(4), 4);
		assertEquals(bb.getByte(5), 5);
		assertEquals(bb.getByte(6), 7);
		assertEquals(bb.getByte(7), 8);
	}

	@Test
	public void testWriteMiddle() {
		ByteBuffer b1 = ByteBuffer.wrap(new byte[] { 0, 0, 0, 0, 0, 0 });
		ByteBuffer b2 = ByteBuffer.wrap(new byte[] { 0 });
		ByteBuffer b3 = ByteBuffer.wrap(new byte[] { 0, 0 });
		ByteBuffer b4 = ByteBuffer.wrap(new byte[] { 0, 0, 0, 0, 0, 0, 0 });
		AbstractByteBufferConcatenation bb = new MemoryByteBufferConcatenation(b1, b2, b3, b4);

		byte[] bytes = new byte[] { 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		bb.setBytes(bytes, 4);

		for (int i = 4; i < 12; i++) {
			assertEquals(bytes[i - 4], i);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyBuffer() {
		ByteBuffer b1 = ByteBuffer.wrap(new byte[] {});
		ByteBuffer b2 = ByteBuffer.wrap(new byte[] {});
		ByteBuffer b3 = ByteBuffer.wrap(new byte[] {});
		ByteBuffer b4 = ByteBuffer.wrap(new byte[] {});
		ByteBuffer b5 = ByteBuffer.wrap(new byte[] {});
		AbstractByteBufferConcatenation bb = new MemoryByteBufferConcatenation(b1, b2, b3, b4, b5);

		assertEquals(bb.getLength(), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullBuffer() {
		ByteBuffer b1 = ByteBuffer.wrap(new byte[] {});
		ByteBuffer b2 = null;
		ByteBuffer b3 = ByteBuffer.wrap(new byte[] {});
		ByteBuffer b4 = ByteBuffer.wrap(new byte[] {});
		ByteBuffer b5 = ByteBuffer.wrap(new byte[] {});
		new MemoryByteBufferConcatenation(b1, b2, b3, b4, b5);
	}

}

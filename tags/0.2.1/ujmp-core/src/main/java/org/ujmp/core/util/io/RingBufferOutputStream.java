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

import java.io.IOException;
import java.io.OutputStream;

public class RingBufferOutputStream extends OutputStream {

	private int start = -1;

	private int end = -1;

	private final byte values[];

	public RingBufferOutputStream() {
		this(10);
	}

	public RingBufferOutputStream(int maximumSize) {
		values = new byte[maximumSize];
	}

	public int maxSize() {
		return values.length;
	}

	public boolean add(byte a) {
		if (end >= 0) {
			end++;
			if (end >= values.length) {
				end = 0;
			}
			if (end == start) {
				start++;
			}
			if (start >= values.length) {
				start = 0;
			}
		} else {
			start = 0;
			end = 0;
		}
		values[end] = a;
		return true;
	}

	public int size() {
		if (end < 0) {
			return 0;
		}
		return end < start ? values.length : end - start + 1;
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < size(); i++) {
			s.append((char) get(i));
		}
		return s.toString();
	}

	public byte get(int index) {
		return values[(start + index) % values.length];
	}

	public byte set(int index, byte a) {
		byte old = values[(start + index) % values.length];
		values[(start + index) % values.length] = a;
		return old;
	}

	public void clear() {
		start = -1;
		end = -1;
	}

	public void add(int index, char element) {
		new Exception("not implemented").printStackTrace();
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public void write(int b) throws IOException {
		add((byte) b);
	}

}
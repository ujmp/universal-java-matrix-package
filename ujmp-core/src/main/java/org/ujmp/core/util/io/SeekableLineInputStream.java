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
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeekableLineInputStream extends InputStream {

	private final int bufferSize = 65536;

	private BufferedRandomAccessFile in = null;

	private final List<Long> lineEnds = new ArrayList<Long>();

	public SeekableLineInputStream(String file) throws IOException {
		this(new File(file));
	}

	public SeekableLineInputStream(File file) throws IOException {
		in = new BufferedRandomAccessFile(file, "r", bufferSize);
		byte[] bytes = new byte[bufferSize];
		for (long pos = 0; pos < in.length(); pos += bufferSize) {
			Arrays.fill(bytes, (byte) 0);
			in.read(pos, bytes);

			for (int i = 0; i < bufferSize; i++) {
				byte b = bytes[i];
				if (b == 10) {
					lineEnds.add(pos + i);
				}
			}
		}
		System.out.println(getLineCount() + " lines");
	}

	@Override
	public void close() throws IOException {
		in.close();
	}

	public int getLineCount() {
		return lineEnds.size() + 1;
	}

	@Override
	public int read() throws IOException {
		return in.read();
	}

	public String readLine(int lineNumber) throws IOException {
		String line = null;
		if (line == null) {
			long start = 0;
			if (lineNumber > 0) {
				start = lineEnds.get(lineNumber - 1) + 1;
			}
			long end = 0;
			if (lineNumber < getLineCount() - 1) {
				end = lineEnds.get(lineNumber);
			} else {
				end = in.length();
			}
			int length = (int) (end - start);
			byte[] bytes = new byte[length];
			in.read(start, bytes);
			line = new String(bytes);
		}
		return line;
	}
}

/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

/**
 * This stream searches all line end characters (hex 0A) in a file. For Linux
 * line splitting is OK, for Windows the character hex 0D has to be eliminated
 * with String.trim()
 */
public class SeekableLineInputStream extends InputStream {

	private static final long MAXLINECOUNT = 1000;

	private final long[] countsTotal = new long[256];

	private final long[] diffSum = new long[256];

	private int bufferSize = 65536;

	private BufferedRandomAccessFile in;

	private final List<Long> lineEnds = new ArrayList<Long>();

	private long maxLineLength = 0;

	public SeekableLineInputStream(String file) throws IOException {
		this(new File(file));
	}

	public SeekableLineInputStream(File file) throws IOException {
		in = new BufferedRandomAccessFile(file, "r", bufferSize);
		long totalLength = in.length();
		long last = -1;
		long lineCount = 0;

		final long[][] countsPerLine = new long[2][256];
		long[] c = countsPerLine[0];
		final long[] c0 = countsPerLine[0];
		final long[] c1 = countsPerLine[1];

		final byte[] bytes = new byte[bufferSize];
		byte b;
		for (long pos = 0; pos < totalLength; pos += bufferSize) {
			Arrays.fill(bytes, (byte) 0);
			in.read(pos, bytes);
			for (int i = 0; i < bufferSize; i++) {
				b = bytes[i];

				// count characters
				if (lineCount < MAXLINECOUNT) {
					c[b + 128]++;
				}

				// when a new line comes
				if (b == 10) {

					if (lineCount < MAXLINECOUNT) {
						// sum up character frequencies
						for (int j = 256; --j != -1;) {
							countsTotal[j] += c[j];
						}
						for (int j = 256; --j != -1;) {
							diffSum[j] += Math.abs(c0[j] - c1[j]);
						}
						// ignore difference for first line
						if (lineCount == 0) {
							Arrays.fill(diffSum, 0);
						}
						c = countsPerLine[(int) (++lineCount % 2)];
						Arrays.fill(c, 0);
					}

					long length = pos + i - last;
					if (length > maxLineLength) {
						maxLineLength = length;
					}
					lineEnds.add(pos + i);
					last = pos + i;
				}
			}
		}

		// remove last newline, if it is the last byte in the file
		lineEnds.remove(totalLength - 1);

		for (int i = 0; i < 256; i++) {
			if (countsTotal[i] > 0)
				System.out.println((i - 128) + " " + countsTotal[i] + " " + diffSum[i]);
		}

		System.out.println("This file has " + getLineCount() + " lines");

		// if initial buffer size was too small, we have to increase it now
		if (maxLineLength + 1 > bufferSize) {
			bufferSize = (int) maxLineLength + 1;
			in.close();
			in = new BufferedRandomAccessFile(file, "r", bufferSize);
		}
	}

	public long getMaxLineLength() {
		return maxLineLength;
	}

	public void close() throws IOException {
		in.close();
	}

	public int getLineCount() {
		return lineEnds.size() + 1;
	}

	public int read() throws IOException {
		return in.read();
	}

	public String getMostProbableDelimiter() {
		long lines = Math.min(MAXLINECOUNT, lineEnds.size());
		return null;
	}

	public String readLine(int lineNumber) throws IOException {
		String line;
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
		if (length == 0) {
			return "";
		}
		byte[] bytes = new byte[length];
		in.read(start, bytes);

		// eliminate Windows line end
		if (bytes[bytes.length - 1] == 13) {
			line = new String(bytes, 0, bytes.length - 1);
		} else {
			line = new String(bytes);
		}
		return line;
	}
}

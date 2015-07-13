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

package org.ujmp.core.stringmatrix.impl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.List;

import org.ujmp.core.stringmatrix.stub.AbstractDenseStringMatrix2D;
import org.ujmp.core.util.LongArrayList;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;
import org.ujmp.core.util.io.AbstractByteBufferConcatenation;
import org.ujmp.core.util.io.ByteBufferConcatenation;
import org.ujmp.core.util.io.MemoryByteBufferConcatenation;
import org.ujmp.core.util.io.WeakMappedByteBuffer;
import org.ujmp.core.util.io.WeakMappedByteBufferConcatenation;

public class DenseCSVStringMatrix2D extends AbstractDenseStringMatrix2D {
	private static final long serialVersionUID = -1966465041178705488L;

	public static final int DEFAULTBUFFERSIZE = AbstractByteBufferConcatenation.DEFAULTBUFFERSIZE;

	private final ByteBufferConcatenation byteBufferConcatenation;
	private char columnSeparator;
	private char enclosingCharacter;

	private final LongArrayList rowIndexList = new LongArrayList(4096);

	private long rowCacheId = -1;
	private List<String> rowCache = null;

	public DenseCSVStringMatrix2D(final byte[] bytes) {
		this(ByteBuffer.wrap(bytes));
	}

	public DenseCSVStringMatrix2D(char columnSeparator, char enclosingCharacter, final byte[] bytes) {
		this(columnSeparator, enclosingCharacter, ByteBuffer.wrap(bytes));
	}

	public DenseCSVStringMatrix2D(final char columnSeparator, final byte[] bytes) {
		this(columnSeparator, ByteBuffer.wrap(bytes));
	}

	public DenseCSVStringMatrix2D(final char columnSeparator,
			final WeakMappedByteBuffer... byteBuffers) {
		this(columnSeparator, '\0', byteBuffers);
	}

	public DenseCSVStringMatrix2D(final char columnSeparator, final ByteBuffer... byteBuffers) {
		this(columnSeparator, '\0', byteBuffers);
	}

	public DenseCSVStringMatrix2D(final char columnSeparator, final char enclosingCharacter,
			final WeakMappedByteBuffer... byteBuffers) {
		super(0, 0);
		this.byteBufferConcatenation = new WeakMappedByteBufferConcatenation(byteBuffers);
		this.columnSeparator = columnSeparator;
		this.enclosingCharacter = enclosingCharacter;
	}

	public DenseCSVStringMatrix2D(final char columnSeparator, final char enclosingCharacter,
			final ByteBuffer... byteBuffers) {
		super(0, 0);
		this.byteBufferConcatenation = new MemoryByteBufferConcatenation(byteBuffers);
		this.columnSeparator = columnSeparator;
		this.enclosingCharacter = enclosingCharacter;
	}

	public DenseCSVStringMatrix2D(ByteBuffer... byteBuffers) {
		this('\0', byteBuffers);
	}

	public DenseCSVStringMatrix2D(WeakMappedByteBuffer... byteBuffers) {
		this('\0', byteBuffers);
	}

	public DenseCSVStringMatrix2D(String filename) throws IOException {
		this(new File(filename));
	}

	public DenseCSVStringMatrix2D(File file) throws IOException {
		this(new RandomAccessFile(file, "r"));
	}

	public DenseCSVStringMatrix2D(RandomAccessFile randomAccessFile) throws IOException {
		this(WeakMappedByteBuffer.create(randomAccessFile));
	}

	public DenseCSVStringMatrix2D(char columnSeparator, String filename) throws IOException {
		this(columnSeparator, new File(filename));
	}

	public DenseCSVStringMatrix2D(char columnSeparator, File file) throws IOException {
		this(columnSeparator, new RandomAccessFile(file, "r"));
	}

	public DenseCSVStringMatrix2D(char columnSeparator, char enclosingCharacter, File file)
			throws IOException {
		this(columnSeparator, enclosingCharacter, new RandomAccessFile(file, "r"));
	}

	public DenseCSVStringMatrix2D(char columnSeparator, RandomAccessFile randomAccessFile)
			throws IOException {
		this(columnSeparator, WeakMappedByteBuffer.create(randomAccessFile));
	}

	public DenseCSVStringMatrix2D(char columnSeparator, char enclosingCharacter,
			RandomAccessFile randomAccessFile) throws IOException {
		this(columnSeparator, enclosingCharacter, WeakMappedByteBuffer.create(randomAccessFile));
	}

	public char getColumnSeparator() {
		return columnSeparator;
	}

	public synchronized String getString(long row, long column) {
		countRowsAndColumns();

		if (rowCacheId == row) {
			if (column < rowCache.size()) {
				return rowCache.get(MathUtil.longToInt(column));
			} else {
				return null;
			}
		}

		final long rowPos1 = rowIndexList.get(MathUtil.longToInt(row));
		final long rowPos2;
		if (row == size[ROW] - 1) {
			rowPos2 = byteBufferConcatenation.getLength();
		} else {
			rowPos2 = rowIndexList.get(MathUtil.longToInt(row + 1));
		}
		final int length = MathUtil.longToInt(rowPos2 - rowPos1);
		final byte[] buffer = new byte[length];

		byteBufferConcatenation.getBytes(buffer, rowPos1, length);

		rowCacheId = row;
		rowCache = StringUtil.split(new String(buffer, 0, length), columnSeparator,
				enclosingCharacter);
		if (column < rowCache.size()) {
			return rowCache.get(MathUtil.longToInt(column));
		} else {
			return null;
		}
	}

	public void setString(String value, long row, long column) {
		throw new RuntimeException("matrix is read only");
	}

	public boolean isReadOnly() {
		return true;
	}

	private void countRowsAndColumns() {
		if (size[ROW] == 0) {
			synchronized (this) {
				if (size[ROW] == 0) {
					long rows = 0;

					long minTabCount = Long.MAX_VALUE;
					long minCommaCount = Long.MAX_VALUE;
					long minSemicolonCount = Long.MAX_VALUE;
					long minSpaceCount = Long.MAX_VALUE;
					long minSepCount = Long.MAX_VALUE;
					long maxTabCount = 0;
					long maxCommaCount = 0;
					long maxSemicolonCount = 0;
					long maxSpaceCount = 0;
					long maxSepCount = 0;
					long tabCount = 0;
					long commaCount = 0;
					long semicolonCount = 0;
					long spaceCount = 0;
					long sepCount = 0;

					boolean active = true;

					final byte[] buffer = new byte[DEFAULTBUFFERSIZE];

					rowIndexList.add(0l);

					for (long pos = 0; pos < byteBufferConcatenation.getLength(); pos += buffer.length) {
						final long remaining = byteBufferConcatenation.getLength() - pos;
						final int lengthToRead = MathUtil.longToInt(Math.min(remaining,
								buffer.length));
						byteBufferConcatenation.getBytes(buffer, pos, lengthToRead);
						for (int i = 0; i < lengthToRead; i++) {
							if (buffer[i] == enclosingCharacter) {
								active = !active;
							} else if (active) {
								if (buffer[i] == columnSeparator) {
									sepCount++;
								} else {
									switch (buffer[i]) {
									case '\\':
										i++; // skip masked character
										break;
									case '\n':
										maxTabCount = Math.max(maxTabCount, tabCount);
										minTabCount = Math.min(minTabCount, tabCount);
										maxCommaCount = Math.max(maxCommaCount, commaCount);
										minCommaCount = Math.min(minCommaCount, commaCount);
										maxSemicolonCount = Math.max(maxSemicolonCount,
												semicolonCount);
										minSemicolonCount = Math.min(minSemicolonCount,
												semicolonCount);
										maxSpaceCount = Math.max(maxSpaceCount, spaceCount);
										minSpaceCount = Math.min(minSpaceCount, spaceCount);
										maxSepCount = Math.max(maxSepCount, sepCount);
										minSepCount = Math.min(minSepCount, sepCount);
										tabCount = 0;
										commaCount = 0;
										semicolonCount = 0;
										spaceCount = 0;
										sepCount = 0;
										rowIndexList.add(pos + i + 1);
										rows++;
										break;
									case '\t':
										tabCount++;
										break;
									case ';':
										semicolonCount++;
										break;
									case ',':
										commaCount++;
										break;
									case ' ':
										spaceCount++;
										break;
									default:
										break;
									}
								}
							}
						}
					}

					if (byteBufferConcatenation.getByte(byteBufferConcatenation.getLength() - 1) != '\n') {
						rows++;
					}

					if (sepCount > 0 || tabCount > 0 || commaCount > 0 || semicolonCount > 0
							|| spaceCount > 0) {
						maxTabCount = Math.max(maxTabCount, tabCount);
						minTabCount = Math.min(minTabCount, tabCount);
						maxCommaCount = Math.max(maxCommaCount, commaCount);
						minCommaCount = Math.min(minCommaCount, commaCount);
						maxSemicolonCount = Math.max(maxSemicolonCount, semicolonCount);
						minSemicolonCount = Math.min(minSemicolonCount, semicolonCount);
						maxSpaceCount = Math.max(maxSpaceCount, spaceCount);
						minSpaceCount = Math.min(minSpaceCount, spaceCount);
						maxSepCount = Math.max(maxSepCount, sepCount);
						minSepCount = Math.min(minSepCount, sepCount);
					}

					if (columnSeparator == '\0') {
						if (tabCount > 0) {
							columnSeparator = '\t';
						} else if (minSemicolonCount == maxSemicolonCount && minSemicolonCount > 0) {
							columnSeparator = ';';
						} else if (minCommaCount == maxCommaCount && minCommaCount > 0) {
							columnSeparator = ',';
						} else if (minSpaceCount == maxSpaceCount && minSpaceCount > 0) {
							columnSeparator = ' ';
						} else {
							columnSeparator = '\t';
						}
						if (columnSeparator == '\t') {
							size[ROW] = rows;
							size[COLUMN] = maxTabCount + 1;
						} else if (columnSeparator == ',') {
							size[ROW] = rows;
							size[COLUMN] = maxCommaCount + 1;
						} else if (columnSeparator == ';') {
							size[ROW] = rows;
							size[COLUMN] = maxSemicolonCount + 1;
						} else if (columnSeparator == ' ') {
							size[ROW] = rows;
							size[COLUMN] = maxSpaceCount + 1;
						} else {
							size[ROW] = rows;
							size[COLUMN] = maxSepCount + 1;
						}
					} else {
						size[ROW] = rows;
						size[COLUMN] = maxSepCount + 1;
					}

				}
			}
			System.out.println(size[ROW] + " rows, " + size[COLUMN] + " columns");
		}
	}

	public long[] getSize() {
		countRowsAndColumns();
		return size;
	}

}

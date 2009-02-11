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

package org.ujmp.core.doublematrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;

import org.ujmp.core.Matrix;
import org.ujmp.core.util.io.BufferedRandomAccessFile;

public class DenseFileMatrix2D extends AbstractDenseDoubleMatrix2D {
	private static final long serialVersionUID = 1754729146021609978L;

	private BufferedRandomAccessFile randomAccessFile = null;

	public static final int BYTE = 0;

	public static final int CHAR = 1;

	public static final int DOUBLE = 2;

	public static final int FLOAT = 3;

	public static final int INT = 4;

	public static final int LONG = 5;

	public static final int SHORT = 6;

	public static final int UNSIGNEDBYTE = 7;

	public static final int UNSIGNEDSHORT = 8;

	public static final int SHORTLITTLEENDIAN = 9;

	public static final int INTLITTLEENDIAN = 10;

	public static final int LONGLITTLEENDIAN = 11;

	public static final int BOOLEAN = 12;

	private int dataType = DOUBLE;

	private File file = null;

	private long rowCount = 0;

	private long columnCount = 0;

	private long offset = 0;

	private int bitsPerValue = 1;

	private boolean readOnly = false;

	private static ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;

	public DenseFileMatrix2D(File file, long rowCount, long columnCount) {
		this(file, rowCount, columnCount, 0, DOUBLE, false);
	}

	public DenseFileMatrix2D(File file, long rowCount, long columnCount, int dataType) {
		this(file, rowCount, columnCount, 0, dataType, false);
	}

	public DenseFileMatrix2D(File file, long rowCount, long columnCount, long offset, int dataType,
			boolean readOnly) {
		this.file = file;
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.offset = offset;
		this.dataType = dataType;
		this.bitsPerValue = getBitsPerValue(dataType);
		this.readOnly = readOnly;

		try {
			if (readOnly) {
				randomAccessFile = new BufferedRandomAccessFile(file, "r");
			} else {
				try {
					randomAccessFile = new BufferedRandomAccessFile(file, "rw");
				} catch (FileNotFoundException e) {
					randomAccessFile = new BufferedRandomAccessFile(file, "r");
				}
				long difference = getFileLength() + offset - randomAccessFile.length();
				if (difference > 0) {
					long seek = (long) (Math.ceil(getPos(getRowCount(), getColumnCount())));
					switch (dataType) {
					case BYTE:
						randomAccessFile.writeByte(seek, (byte) 0.0);
						break;
					case CHAR:
						randomAccessFile.writeChar(seek, (char) 0.0);
						break;
					case DOUBLE:
						randomAccessFile.writeDouble(seek, 0.0);
						break;
					case FLOAT:
						randomAccessFile.writeFloat(seek, (float) 0.0);
						break;
					case SHORT:
						randomAccessFile.writeShort(seek, (short) 0.0);
						break;
					case INT:
						randomAccessFile.writeInt(seek, (int) 0.0);
						break;
					case LONG:
						randomAccessFile.writeLong(seek, (long) 0.0);
						break;
					case UNSIGNEDBYTE:
						randomAccessFile.writeByte(seek, (byte) 0.0);
						break;
					case UNSIGNEDSHORT:
						randomAccessFile.writeShort(seek, (short) 0.0);
						break;
					case INTLITTLEENDIAN:
						randomAccessFile.writeInt(seek, (int) 0.0);
						break;
					case SHORTLITTLEENDIAN:
						randomAccessFile.writeInt(seek, (short) 0.0);
						break;
					case LONGLITTLEENDIAN:
						randomAccessFile.writeLong(seek, (long) 0.0);
						break;
					case BOOLEAN:
						randomAccessFile.writeByte(seek, (byte) 0.0);
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not open file", e);
		}
	}

	public DenseFileMatrix2D(long rowCount, long columnCount) throws IOException {
		this(rowCount, columnCount, DOUBLE);
	}

	public DenseFileMatrix2D(long... size) throws IOException {
		this(size[ROW], size[COLUMN], DOUBLE);
	}

	public DenseFileMatrix2D(Matrix m) throws IOException {
		this(m.getSize());
		for (long[] c : m.allCoordinates()) {
			setAsDouble(m.getAsDouble(c), c);
		}
	}

	public DenseFileMatrix2D(long rowCount, long columnCount, int dataType) throws IOException {
		this(File.createTempFile("matrix", null), rowCount, columnCount, dataType);
	}

	public BufferedRandomAccessFile getRandomAccessFile() {
		return randomAccessFile;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public void setColumnCount(int colCount) {
		this.columnCount = colCount;
	}

	public File getFile() {
		return file;
	}

	private static final int getBitsPerValue(int dataType) {
		switch (dataType) {
		case BYTE:
			return 8;
		case CHAR:
			return 8;
		case DOUBLE:
			return 64;
		case FLOAT:
			return 32;
		case INT:
			return 32;
		case INTLITTLEENDIAN:
			return 32;
		case LONG:
			return 64;
		case LONGLITTLEENDIAN:
			return 64;
		case SHORT:
			return 16;
		case UNSIGNEDBYTE:
			return 8;
		case UNSIGNEDSHORT:
			return 16;
		case SHORTLITTLEENDIAN:
			return 16;
		case BOOLEAN:
			return 1;
		default:
			return 32;
		}
	}

	public double getBytesPerValue() {
		return getBitsPerValue() / 8.0;
	}

	public int getBitsPerValue() {
		return bitsPerValue;
	}

	private double getPos(long row, long column) {
		if (getBytesPerValue() < 1) {
			return ((double) row * (double) columnCount * getBytesPerValue())
					+ (column * getBytesPerValue()) + offset;
		}
		return (row * columnCount * getBytesPerValue()) + (column * getBytesPerValue()) + offset;
	}

	public long getFileLength() {
		double prod = (double) getRowCount() * (double) getColumnCount() * getBytesPerValue();
		return (long) Math.ceil(prod);
	}

	public int getDataType() {
		return dataType;
	}

	public synchronized double getDouble(int row, int column) {
		return getDouble((long) row, (long) column);
	}

	public synchronized double getDouble(long row, long column) {
		if (randomAccessFile != null) {
			try {
				double pos = getPos(row, column);

				if (pos < getFileLength() + offset) {

					long seek = (long) Math.floor(pos);

					byte[] bytes = null;

					switch (getDataType()) {
					case BYTE:
						bytes = new byte[1];
						randomAccessFile.read(seek, bytes);
						return bytes[0];
					case CHAR:
						bytes = new byte[1];
						randomAccessFile.read(seek, bytes);
						return bytes[0];
					case DOUBLE:
						bytes = new byte[8];
						randomAccessFile.read(seek, bytes);
						return ByteBuffer.wrap(bytes).order(byteOrder).getDouble();
					case FLOAT:
						bytes = new byte[4];
						randomAccessFile.read(seek, bytes);
						return ByteBuffer.wrap(bytes).order(byteOrder).getFloat();
					case SHORT:
						bytes = new byte[2];
						randomAccessFile.read(seek, bytes);
						return ByteBuffer.wrap(bytes).order(byteOrder).getShort();
					case INT:
						bytes = new byte[4];
						randomAccessFile.read(seek, bytes);
						return ByteBuffer.wrap(bytes).order(byteOrder).getInt();
					case LONG:
						bytes = new byte[8];
						randomAccessFile.read(seek, bytes);
						return ByteBuffer.wrap(bytes).order(byteOrder).getLong();
					case LONGLITTLEENDIAN:
						bytes = new byte[8];
						randomAccessFile.read(seek, bytes);
						return ByteBuffer.wrap(bytes).order(byteOrder).getLong();
					case SHORTLITTLEENDIAN:
						bytes = new byte[2];
						randomAccessFile.read(seek, bytes);
						return ByteBuffer.wrap(bytes).order(byteOrder).getShort();
					case INTLITTLEENDIAN:
						bytes = new byte[4];
						randomAccessFile.read(seek, bytes);
						return ByteBuffer.wrap(bytes).order(byteOrder).getInt();
					case UNSIGNEDBYTE:
						return randomAccessFile.readUnsignedByte();
					case UNSIGNEDSHORT:
						return randomAccessFile.readUnsignedShort();
					case BOOLEAN:
						return getBit(randomAccessFile.readByte(), pos - Math.floor(pos));
					}

				} else {
					logger.log(Level.WARNING, "no such coordinates: " + row + "," + column);
				}
			} catch (Exception e) {
				logger.log(Level.WARNING, "could not read value", e);
			}
		}
		return 0.0;
	}

	private static final double getBit(byte b, double offset) {
		if (offset == 0.0) {
			return ((b & 0x01) > 0) ? 1 : 0;
		} else if (offset == 0.125) {
			return ((b & 0x02) > 0) ? 1 : 0;
		} else if (offset == 0.25) {
			return ((b & 0x04) > 0) ? 1 : 0;
		} else if (offset == 0.375) {
			return ((b & 0x08) > 0) ? 1 : 0;
		} else if (offset == 0.5) {
			return ((b & 0x10) > 0) ? 1 : 0;
		} else if (offset == 0.625) {
			return ((b & 0x20) > 0) ? 1 : 0;
		} else if (offset == 0.75) {
			return ((b & 0x40) > 0) ? 1 : 0;
		} else if (offset == 0.875) {
			return ((b & 0x80) > 0) ? 1 : 0;
		}
		return 0.0;
	}

	private static final byte setBit(byte b, double offset) {
		if (offset == 0.0) {
			b = (byte) (b | 0x01);
		} else if (offset == 0.125) {
			b = (byte) (b | 0x02);
		} else if (offset == 0.25) {
			b = (byte) (b | 0x04);
		} else if (offset == 0.375) {
			b = (byte) (b | 0x08);
		} else if (offset == 0.5) {
			b = (byte) (b | 0x10);
		} else if (offset == 0.625) {
			b = (byte) (b | 0x20);
		} else if (offset == 0.75) {
			b = (byte) (b | 0x40);
		} else if (offset == 0.875) {
			b = (byte) (b | 0x80);
		}
		return b;
	}

	public synchronized void setDouble(double value, int row, int column) {
		setDouble(value, (long) row, (long) column);
	}

	public synchronized void setDouble(double value, long row, long column) {
		if (isReadOnly())
			return;

		if (randomAccessFile != null) {
			try {
				double pos = getPos(row, column);

				long seek = (long) Math.floor(pos);

				ByteBuffer bb = null;

				switch (dataType) {
				case BYTE:
					randomAccessFile.writeByte(seek, (byte) value);
					break;
				case CHAR:
					randomAccessFile.writeChar(seek, (char) value);
					break;
				case DOUBLE:
					randomAccessFile.writeDouble(seek, value);
					break;
				case FLOAT:
					randomAccessFile.writeFloat(seek, (float) value);
					break;
				case SHORT:
					randomAccessFile.writeShort(seek, (short) value);
					break;
				case SHORTLITTLEENDIAN:
					bb = ByteBuffer.allocate(2).order(byteOrder);
					bb.putShort((short) value);
					randomAccessFile.write(seek, bb.array());
					break;
				case INT:
					randomAccessFile.writeInt(seek, (int) value);
					break;
				case INTLITTLEENDIAN:
					bb = ByteBuffer.allocate(4).order(byteOrder);
					bb.putInt((int) value);
					randomAccessFile.write(seek, bb.array());
					break;
				case LONG:
					randomAccessFile.writeLong(seek, (long) value);
					break;
				case LONGLITTLEENDIAN:
					bb = ByteBuffer.allocate(8).order(byteOrder);
					bb.putLong((long) value);
					randomAccessFile.write(seek, bb.array());
					break;
				case UNSIGNEDBYTE:
					randomAccessFile.writeByte(seek, (byte) value);
					break;
				case UNSIGNEDSHORT:
					randomAccessFile.writeShort(seek, (short) value);
					break;
				case BOOLEAN:
					throw new IOException("not supported");
				}
			} catch (Exception e) {
				logger.log(Level.WARNING, "could not write value at coordinates " + row + ","
						+ column, e);
			}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (randomAccessFile != null) {
			try {
				randomAccessFile.close();
				randomAccessFile = null;
			} catch (Exception e) {
			}
		}
	}

	public long[] getSize() {
		return new long[] { rowCount, columnCount };
	}

	@Override
	public boolean isReadOnly() {
		return readOnly;
	}

	public static final int getShortLittleEndian(byte[] bytes) {
		return ByteBuffer.wrap(bytes).order(byteOrder).getShort();
	}

	public static final int getIntLittleEndian(byte[] bytes) {
		return ByteBuffer.wrap(bytes).order(byteOrder).getInt();
	}

}

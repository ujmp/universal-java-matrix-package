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

package org.ujmp.core.doublematrix.impl;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.io.BufferedRandomAccessFile;

public class DenseFileMatrix extends AbstractDenseDoubleMatrix implements Erasable, Closeable {
	private static final long serialVersionUID = 1754729146021609978L;

	private transient BufferedRandomAccessFile randomAccessFile = null;

	private int bufferSize = 65536;

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

	private long[] size;

	private long offset = 0;

	private int bitsPerValue = 1;

	private boolean readOnly = false;

	private static ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;

	public DenseFileMatrix(File file, long... size) throws IOException {
		this(file, 0, DOUBLE, false, size);
	}

	public DenseFileMatrix(File file) throws IOException {
		this(file, 0, BYTE, true, file.length(), 1);
	}

	public DenseFileMatrix(File file, int dataType, long... size) throws IOException {
		this(file, 0, dataType, false, size);
	}

	public DenseFileMatrix(File file, long offset, int dataType, boolean readOnly, long... size)
			throws IOException {
		this(65536, file, offset, dataType, readOnly, size);
	}

	public DenseFileMatrix(int bufferSize, File file, long offset, int dataType, boolean readOnly,
			long... size) throws IOException {
		super(size);
		if (file == null) {
			file = File.createTempFile("denseFileMatrix", ".dat");
			file.deleteOnExit();
		}
		this.bufferSize = bufferSize;
		this.file = file;
		this.size = size;
		this.offset = offset;
		this.dataType = dataType;
		this.bitsPerValue = getBitsPerValue(dataType);
		this.readOnly = readOnly;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	private void createFile() {
		try {
			if (readOnly) {
				randomAccessFile = new BufferedRandomAccessFile(file, "r", bufferSize);
			} else {
				try {
					randomAccessFile = new BufferedRandomAccessFile(file, "rw", bufferSize);
				} catch (FileNotFoundException e) {
					randomAccessFile = new BufferedRandomAccessFile(file, "r", bufferSize);
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
			throw new MatrixException("could not open file", e);
		}
	}

	public DenseFileMatrix(long... size) throws IOException {
		this(null, size);
	}

	public DenseFileMatrix(Matrix m) throws IOException {
		this(m.getSize());
		for (long[] c : m.allCoordinates()) {
			setAsDouble(m.getAsDouble(c), c);
		}
		Annotation a = m.getAnnotation();
		if (a != null) {
			setAnnotation(a.clone());
		}
	}

	public BufferedRandomAccessFile getRandomAccessFile() {
		return randomAccessFile;
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

	public long getBytesPerValue() {
		return getBitsPerValue() / 8;
	}

	public int getBitsPerValue() {
		return bitsPerValue;
	}

	private long getPos(long... pos) {
		if (getBytesPerValue() < 1) {
			throw new RuntimeException("not supported");
		}
		return MathUtil.pos2IndexRowMajor(size, pos) * getBytesPerValue() + offset;
	}

	public long getFileLength() {
		double prod = (double) getRowCount() * (double) getColumnCount() * getBytesPerValue();
		return (long) Math.ceil(prod);
	}

	public int getDataType() {
		return dataType;
	}

	public synchronized double getDouble(long... c) {
		if (randomAccessFile == null) {
			createFile();
		}
		if (randomAccessFile != null) {
			try {
				long seek = getPos(c);

				if (seek < getFileLength() + offset) {

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
						// return getBit(randomAccessFile.readByte(), pos -
						// Math.floor(pos));
					}

				} else {
					throw new MatrixException("no such coordinates: " + Coordinates.toString(c));
				}
			} catch (Exception e) {
				throw new MatrixException("could not read value", e);
			}
		}
		return 0.0;
	}

	public void setSize(long... size) {
		this.size = size;
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

	public synchronized void setDouble(double value, long... c) {
		if (isReadOnly())
			return;

		try {

			if (file == null) {
				file = File.createTempFile("matrix", null);
			}

			if (randomAccessFile == null) {
				createFile();
			}

			long seek = getPos(c);

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
			throw new MatrixException("could not write value at coordinates "
					+ Coordinates.toString(c), e);
		}
	}

	protected void finalize() throws Throwable {
		super.finalize();
		if (randomAccessFile != null) {
			try {
				randomAccessFile.close();
				randomAccessFile = null;
			} catch (Throwable e) {
			}
		}
	}

	public long[] getSize() {
		return size;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public static final int getShortLittleEndian(byte[] bytes) {
		return ByteBuffer.wrap(bytes).order(byteOrder).getShort();
	}

	public static final int getIntLittleEndian(byte[] bytes) {
		return ByteBuffer.wrap(bytes).order(byteOrder).getInt();
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		for (long[] c : availableCoordinates()) {
			s.writeObject(new Coordinates(c));
			s.writeObject(getDouble(c));
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		while (true) {
			try {
				Coordinates c = (Coordinates) s.readObject();
				Double o = (Double) s.readObject();
				setDouble(o, c.co);
			} catch (OptionalDataException e) {
				return;
			}
		}
	}

	public void erase() throws IOException {
		close();
		file.delete();
	}

	public void close() throws IOException {
		if (randomAccessFile != null) {
			randomAccessFile.close();
		}

	}

}

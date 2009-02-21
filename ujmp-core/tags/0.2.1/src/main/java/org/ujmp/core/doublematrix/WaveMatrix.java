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

import org.ujmp.core.util.StringUtil;

public class WaveMatrix extends DenseFileMatrix2D {
	private static final long serialVersionUID = -4952985947339369630L;

	public static final int HEADERLENGTH = 44;

	private final boolean ignoreHeader = true;

	public WaveMatrix(String filename) {
		this(new File(filename));
	}

	public WaveMatrix(File file) {
		this(file, false);
	}

	public WaveMatrix(File file, boolean readOnly) {
		super(file, 1, 1, 44, SHORTLITTLEENDIAN, readOnly);
		setRowCount((int) (getDataLength() / (getBitsPerSample() / 8) / getChannels()));
		setColumnCount(getChannels());
		// setRowCount(100000000);
		System.out.println(toString());
	}

	public double getEstimatedMinValue(long timeOut) {
		return -32768;
	}

	public double getEstimatedMaxValue(long timeOut) {
		return 32768;
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("RIFF Tag:         " + getRIFFTag() + "\n");
		s.append("WAVE Tag:         " + getWAVETag() + "\n");
		s.append("fmt  Tag:         " + getFmtTag() + "\n");
		s.append("data Tag:         " + getDataTag() + "\n");
		s.append("Format:           " + getFormat() + "\n");
		s.append("Channels:         " + getChannels() + "\n");
		s.append("SampleRate:       " + getSampleRate() + "\n");
		s.append("BitsPerSample:    " + getBitsPerSample() + "\n");
		s.append("BytesPerSecond:   " + getBytesPerSecond() + "\n");
		s.append("BlockAlign:       " + getBlockAlign() + "\n");
		s.append("DataLengthHeader: " + getDataLengthFromHeader() + "\n");
		s.append("DataLengthFile:   " + getDataLengthFromFile() + "\n");
		s.append("Duration:         " + StringUtil.format(getDuration()) + "s\n");
		s.append("RowCount:         " + getRowCount() + "\n");
		s.append("ColumnCount:      " + getColumnCount() + "\n");
		s.append("Header-Check:     " + (isWaveFile() ? "passed" : "error") + "\n");
		return s.toString();
	}

	public int getChannels() {
		byte[] bytes = new byte[2];
		try {
			getRandomAccessFile().read(22, bytes);
			return getShortLittleEndian(bytes);
		} catch (Exception e) {
			return 0;
		}
	}

	public double getDuration() {
		return (double) getDataLength() / (double) getBytesPerSecond();
	}

	public String getFormat() {
		byte[] bytes = new byte[2];
		try {
			getRandomAccessFile().read(20, bytes);
			return getShortLittleEndian(bytes) == 1 ? "PCM" : "unknown";
		} catch (Exception e) {
			return "unknown";
		}
	}

	public String getFmtTag() {
		byte[] bytes = new byte[4];
		try {
			getRandomAccessFile().read(12, bytes);
			return new String(bytes);
		} catch (Exception e) {
			return "";
		}
	}

	public String getDataTag() {
		byte[] bytes = new byte[4];
		try {
			getRandomAccessFile().read(36, bytes);
			return new String(bytes);
		} catch (Exception e) {
			return "";
		}
	}

	public String getRIFFTag() {
		byte[] bytes = new byte[4];
		try {
			getRandomAccessFile().read(0, bytes);
			return new String(bytes);
		} catch (Exception e) {
			return "";
		}
	}

	public String getWAVETag() {
		byte[] bytes = new byte[4];
		try {
			getRandomAccessFile().read(8, bytes);
			return new String(bytes);
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isWaveFile() {
		if (!"RIFF".equals(getRIFFTag())) {
			return false;
		}
		if (!"WAVE".equals(getWAVETag())) {
			return false;
		}
		if (!"fmt ".equals(getFmtTag())) {
			return false;
		}
		if (!"PCM".equals(getFormat())) {
			return false;
		}
		if (!"data".equals(getDataTag())) {
			return false;
		}
		if (getBitsPerSample() != 8 && getBitsPerSample() != 16 && getBitsPerSample() != 32) {
			return false;
		}
		return true;
	}

	public int getBitsPerSample() {
		byte[] bytes = new byte[2];
		try {
			getRandomAccessFile().read(34, bytes);
			return getShortLittleEndian(bytes);
		} catch (Exception e) {
			return 0;
		}
	}

	public int getBlockAlign() {
		byte[] bytes = new byte[2];
		try {
			getRandomAccessFile().read(32, bytes);
			return getShortLittleEndian(bytes);
		} catch (Exception e) {
			return 0;
		}
	}

	public int getSampleRate() {
		byte[] bytes = new byte[4];
		try {
			getRandomAccessFile().read(24, bytes);
			return getIntLittleEndian(bytes);
		} catch (Exception e) {
			return 0;
		}
	}

	public long getDataLengthFromFile() {
		return getFile().length() - HEADERLENGTH;
	}

	public int getDataLengthFromHeader() {
		byte[] bytes = new byte[4];
		try {
			getRandomAccessFile().read(40, bytes);
			return getIntLittleEndian(bytes);
		} catch (Exception e) {
			return 0;
		}
	}

	public long getDataLength() {
		return (ignoreHeader) ? getDataLengthFromFile() : getDataLengthFromHeader();
	}

	public int getBytesPerSecond() {
		byte[] bytes = new byte[4];
		try {
			getRandomAccessFile().read(28, bytes);
			return getIntLittleEndian(bytes);
		} catch (Exception e) {
			return 0;
		}
	}

}

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

package org.ujmp.core.doublematrix.impl;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;

public class ImageMatrix extends AbstractDenseDoubleMatrix2D {
	private static final long serialVersionUID = -1354524587823816194L;

	private BufferedImage bufferedImage = null;

	private int[] pixelsInt = null;

	private byte[] pixelsByte = null;

	public ImageMatrix(String filename, Object... parameters) throws IOException {
		this(new File(filename));
	}

	public ImageMatrix(File file, Object... parameters) throws IOException {
		bufferedImage = ImageIO.read(file);
		if (bufferedImage.getRaster().getDataBuffer() instanceof DataBufferInt) {
			pixelsInt = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
		} else {
			pixelsByte = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
		}
	}

	public ImageMatrix(InputStream stream, Object... parameters) throws IOException {
		bufferedImage = ImageIO.read(stream);
		if (bufferedImage.getRaster().getDataBuffer() instanceof DataBufferInt) {
			pixelsInt = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
		} else {
			pixelsByte = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
		}
	}

	public double getDouble(int row, int column) {
		return getDouble((long) row, (long) column);
	}

	public void setDouble(double value, int row, int column) {
		setDouble(value, (long) row, (long) column);
	}

	public double getDouble(long row, long column) throws MatrixException {
		if (pixelsInt != null) {
			int pos = (int) (row * bufferedImage.getWidth() + column);
			return pixelsInt[pos];
		} else {
			int pos = (int) (row * 3 * bufferedImage.getWidth() + column);
			return 256 * 256 * pixelsByte[pos] + 256 * pixelsByte[pos + 1] + pixelsByte[pos + 2];
		}
	}

	public void setDouble(double value, long row, long column) throws MatrixException {
		if (pixelsInt != null) {
			int pos = (int) (row * bufferedImage.getWidth() + column);
			pixelsInt[pos] = (int) value;
		} else {
			int pos = (int) (row * 3 * bufferedImage.getWidth() + column);
			pixelsByte[pos] = (byte) value;
		}
	}

	public long[] getSize() {
		return new long[] { bufferedImage.getHeight(), bufferedImage.getWidth() };
	}

}

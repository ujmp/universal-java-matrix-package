/*
 * Copyright (C) 2008-2009 by Holger Arndt
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.intmatrix.stub.AbstractDenseIntMatrix2D;

public class ImageMatrix extends AbstractDenseIntMatrix2D {
	private static final long serialVersionUID = -1354524587823816194L;

	private BufferedImage bufferedImage = null;

	public ImageMatrix(String filename, Object... parameters) throws IOException {
		this(new File(filename));
	}

	public ImageMatrix(BufferedImage image) throws IOException {
		bufferedImage = image;
	}

	public ImageMatrix(File file, Object... parameters) throws IOException {
		this(ImageIO.read(file));
	}

	public ImageMatrix(InputStream stream, Object... parameters) throws IOException {
		this(ImageIO.read(stream));
	}

	public int getInt(long row, long column) {
		return getInt((int) row, (int) column);
	}

	public void setInt(int value, long row, long column) {
		setInt(value, (int) row, (int) column);
	}

	public int getInt(int row, int column) throws MatrixException {
		return bufferedImage.getRGB(column, row);
	}

	public void setInt(int value, int row, int column) throws MatrixException {
		bufferedImage.setRGB(column, row, value);
	}

	public long[] getSize() {
		return new long[] { bufferedImage.getHeight(), bufferedImage.getWidth() };
	}

}

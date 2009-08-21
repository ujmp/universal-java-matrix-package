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

package org.ujmp.gui.io;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public abstract class ExportPNG {

	private static final Logger logger = Logger.getLogger(ExportPNG.class.getName());

	public static final void save(String file, Component c) {
		save(file, c, c.getWidth());
	}

	public static final void save(File file, Component c) {
		save(file, c, c.getWidth());
	}

	public static final void save(String file, Component c, int width) {
		save(new File(file), c, width);
	}

	public static final void save(File file, Component c, int width) {
		if (file == null) {
			logger.log(Level.WARNING, "no file selected");
			return;
		}
		if (c == null) {
			logger.log(Level.WARNING, "no component provided");
			return;
		}
		double factor = width / c.getWidth();
		BufferedImage image = new BufferedImage((int) (c.getWidth() * factor), (int) (c.getHeight() * factor),
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		g2.scale(factor, factor);
		c.paint(g2);
		try {
			OutputStream out = new FileOutputStream(file);
			Iterator<?> writers = ImageIO.getImageWritersByFormatName("png");
			ImageWriter writer = (ImageWriter) writers.next();
			ImageOutputStream ios = ImageIO.createImageOutputStream(file);
			writer.setOutput(ios);
			writer.write(image);
			out.close();
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not save image", e);
		}
	}

}

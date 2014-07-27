/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.ujmp.core.util;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public abstract class ResourceUtil {

	public static void copyToFile(String resource, File file) throws Exception {
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(resource);
		FileOutputStream os = new FileOutputStream(file);

		BufferedInputStream bis = new BufferedInputStream(is);
		BufferedOutputStream bos = new BufferedOutputStream(os);

		byte buf[] = new byte[8192];
		int len;
		while ((len = bis.read(buf)) > 0) {
			bos.write(buf, 0, len);
		}

		bis.close();
		bos.close();

		is.close();
		os.close();
	}

	public static byte[] getResourceAsBytes(String url) throws IOException {
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(url);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		BufferedInputStream bis = new BufferedInputStream(is);

		byte buf[] = new byte[8192];
		int len;
		while ((len = bis.read(buf)) > 0) {
			os.write(buf, 0, len);
		}

		bis.close();
		is.close();
		os.close();

		return os.toByteArray();
	}

	public static String getResourceAsString(String url) throws IOException {
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(url);
		InputStreamReader ir = new InputStreamReader(is);
		StringBuilder sb = new StringBuilder();

		char buf[] = new char[8192];
		int len;
		while ((len = ir.read(buf)) > 0) {
			sb.append(buf, 0, len);
		}

		ir.close();
		is.close();

		return sb.toString();
	}

	public static Image loadImage(String url) throws IOException {
		InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(url);
		Image image = ImageIO.read(stream);
		return image;
	}

	public static ImageIcon loadImageIcon(String url) throws IOException {
		byte[] data = getResourceAsBytes(url);
		ImageIcon icon = new ImageIcon(data);
		return icon;
	}

}

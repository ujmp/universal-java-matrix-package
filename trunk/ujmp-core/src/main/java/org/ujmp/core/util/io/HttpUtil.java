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

package org.ujmp.core.util.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.ujmp.core.util.UJMPSettings;

public abstract class HttpUtil {

	public static final byte[] getBytesFromUrl(String urlString) throws IOException {
		return getBytesFromUrl(new URL(urlString));
	}

	public static final byte[] getBytesFromUrl(URL url) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		download(url, output);
		output.close();
		return output.toByteArray();
	}

	public static final String getStringFromUrl(String urlString) throws IOException {
		return new String(getBytesFromUrl(urlString));
	}

	public static void download(String url, File file) throws IOException {
		download(new URL(url), file);
	}

	public static void download(URL url, File file) throws IOException {
		FileOutputStream os = new FileOutputStream(file);
		download(url, os);
		os.close();
	}

	public static void download(String url, OutputStream output) throws IOException {
		download(new URL(url), output);
	}

	public static void download(URL url, OutputStream output) throws IOException {
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("User-Agent", UJMPSettings.getInstance().getUserAgent());
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setConnectTimeout(3000);

		InputStream input = connection.getInputStream();
		byte[] buffer = new byte[8192];
		int n = -1;
		while ((n = input.read(buffer)) != -1) {
			if (n > 0) {
				output.write(buffer, 0, n);
			}
		}
		output.flush();
		input.close();
	}

}

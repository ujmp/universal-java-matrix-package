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

package org.ujmp.core.util.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;

import org.ujmp.core.util.VerifyUtil;

public class IntelligentFileReader extends Reader {

	private static final Logger logger = Logger.getLogger(IntelligentFileReader.class.getName());

	private FileReader fr = null;

	private InputStream is = null;

	private LineNumberReader lr = null;

	private String encoding = "UTF-8";

	public IntelligentFileReader(String file) {
		this(new File(file));
	}

	public IntelligentFileReader(InputStream inputStream) {
		try {
			lr = new LineNumberReader(new InputStreamReader(inputStream, encoding));
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not open stream", e);
		}
	}

	public IntelligentFileReader(Reader reader) {
		try {
			lr = new LineNumberReader(reader);
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not open stream", e);
		}
	}

	public IntelligentFileReader(File file) {
		this(file, "UTF-8");
	}

	public IntelligentFileReader(File file, String encoding) {
		this.encoding = encoding;
		if (file != null && file.exists()) {
			if (file.getAbsolutePath().toLowerCase().endsWith(".gz")) {
				try {
					is = new GZIPInputStream(new FileInputStream(file));
					lr = new LineNumberReader(new InputStreamReader(is, encoding));
				} catch (Exception e) {
					logger.log(Level.WARNING, "could not open file " + file, e);
				}
			} else if (file.getAbsolutePath().toLowerCase().endsWith(".z")) {
				try {
					is = new ZipInputStream(new FileInputStream(file));
					lr = new LineNumberReader(new InputStreamReader(is, encoding));
				} catch (Exception e) {
					logger.log(Level.WARNING, "could not open file " + file, e);
				}
			} else {
				try {
					is = new FileInputStream(file);
					lr = new LineNumberReader(new InputStreamReader(is, encoding));
				} catch (Exception e) {
					logger.log(Level.WARNING, "could not open file " + file, e);
				}
			}
		} else {
			logger.log(Level.WARNING, "cannot open file: " + file);
		}
	}

	public IntelligentFileReader(URLConnection connection) throws IOException {
		this(connection.getInputStream());
	}

	public String readLine() {
		if (lr == null)
			return null;
		try {
			return lr.readLine();
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not read line", e);
		}
		return null;
	}

	public void close() {
		try {
			if (lr != null)
				lr.close();
		} catch (Exception e) {
		}
		try {
			if (fr != null)
				fr.close();
		} catch (Exception e) {
		}
		try {
			if (is != null)
				is.close();
		} catch (Exception e) {
		}
	}

	public int getLineNumber() {
		if (lr != null)
			return lr.getLineNumber();
		else
			return -1;
	}

	public int read(char[] cbuf, int off, int len) throws IOException {
		return lr.read(cbuf, off, len);
	}

	public static String load(String filename) {
		StringBuilder s = new StringBuilder();
		IntelligentFileReader fr = new IntelligentFileReader(filename);
		String line = null;
		while ((line = fr.readLine()) != null) {
			s.append(line + "\n");
		}
		fr.close();
		return s.toString();
	}

	public static String load(File filename) {
		StringBuilder s = new StringBuilder();
		IntelligentFileReader fr = new IntelligentFileReader(filename);
		String line = null;
		while ((line = fr.readLine()) != null) {
			s.append(line + "\n");
		}
		fr.close();
		return s.toString();
	}

	public static String load(InputStream stream) {
		StringBuilder s = new StringBuilder();
		IntelligentFileReader fr = new IntelligentFileReader(stream);
		String line = null;
		while ((line = fr.readLine()) != null) {
			s.append(line + "\n");
		}
		fr.close();
		return s.toString();
	}

	public static String load(Reader reader) {
		StringBuilder s = new StringBuilder();
		IntelligentFileReader fr = new IntelligentFileReader(reader);
		String line = null;
		while ((line = fr.readLine()) != null) {
			s.append(line + "\n");
		}
		fr.close();
		return s.toString();
	}

	public static byte[] readBytes(File file) {
		try {
			VerifyUtil.verifyNotNull(file, "file is null");
			VerifyUtil.verifyFalse(file.isDirectory(), "file is a directory");
			VerifyUtil.verifyTrue(file.canRead(), "cannot read from file");
			byte[] data = new byte[(int) file.length()];
			FileInputStream fi = new FileInputStream(file);
			BufferedInputStream bi = new BufferedInputStream(fi);
			bi.read(data);
			bi.close();
			fi.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String load(URL url) throws IOException {
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setConnectTimeout(3000);
		return load(connection.getInputStream());
	}
}

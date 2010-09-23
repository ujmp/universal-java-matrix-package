/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

package org.ujmp.core.io;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.io.FileUtil;

public abstract class ImportMatrix {

	public static Matrix fromFile(File file, Object... parameters) throws MatrixException,
			IOException {
		return fromFile(FileUtil.guessFormat(file), file, parameters);
	}

	public static Matrix fromFile(FileFormat format, File file, Object... parameters)
			throws MatrixException, IOException {
		try {
			Class<?> c = Class.forName("org.ujmp.core.io.ImportMatrix" + format.name());
			Method m = c.getMethod("fromFile", new Class<?>[] { File.class, Object[].class });
			Matrix matrix = (Matrix) m.invoke(null, file, parameters);
			return matrix;
		} catch (ClassNotFoundException e) {
			throw new MatrixException("format not supported: " + format, e);
		} catch (NoSuchMethodException e) {
			throw new MatrixException("format not supported: " + format, e);
		} catch (IllegalAccessException e) {
			throw new MatrixException("format not supported: " + format, e);
		} catch (InvocationTargetException e) {
			throw new MatrixException("could not import", e);
		}
	}

	public static Matrix fromString(String string, Object... parameters) {
		return ImportMatrixCSV.fromString(string, parameters);
	}

	public static Matrix fromString(FileFormat format, String string, Object... parameters)
			throws MatrixException {
		try {
			Class<?> c = Class.forName("org.ujmp.core.io.ImportMatrix" + format.name());
			Method m = c.getMethod("fromString", new Class<?>[] { String.class, Object[].class });
			Matrix matrix = (Matrix) m.invoke(null, string, parameters);
			return matrix;
		} catch (ClassNotFoundException e) {
			throw new MatrixException("format not supported: " + format, e);
		} catch (NoSuchMethodException e) {
			throw new MatrixException("format not supported: " + format, e);
		} catch (IllegalAccessException e) {
			throw new MatrixException("format not supported: " + format, e);
		} catch (InvocationTargetException e) {
			throw new MatrixException("could not import", e);
		}
	}

	public static Matrix fromURL(FileFormat format, URL url, Object... parameters)
			throws MatrixException, IOException {
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setConnectTimeout(3000);
		Matrix m = fromStream(format, connection.getInputStream(), parameters);
		return m;
	}

	public static Matrix fromURL(FileFormat format, String urlString, Object... parameters)
			throws MatrixException, IOException {
		if (FileFormat.ImapMessages.equals(format)) {
			return ImportMatrixImapMessages.fromURL(urlString, parameters);
		} else if (FileFormat.ImapFolders.equals(format)) {
			return ImportMatrixImapFolders.fromURL(urlString, parameters);
		} else {
			return fromURL(format, new URL(urlString), parameters);
		}
	}

	public static Matrix fromStream(FileFormat format, InputStream stream, Object... parameters)
			throws MatrixException, IOException {
		try {
			Class<?> c = Class.forName("org.ujmp.core.io.ImportMatrix" + format.name());
			Method m = c.getMethod("fromStream",
					new Class<?>[] { InputStream.class, Object[].class });
			Matrix matrix = (Matrix) m.invoke(null, stream, parameters);
			return matrix;
		} catch (ClassNotFoundException e) {
			throw new MatrixException("format not supported: " + format, e);
		} catch (NoSuchMethodException e) {
			throw new MatrixException("format not supported: " + format, e);
		} catch (IllegalAccessException e) {
			throw new MatrixException("format not supported: " + format, e);
		} catch (InvocationTargetException e) {
			throw new MatrixException("could not import", e);
		}
	}

	public static Matrix fromResource(FileFormat format, String name, Object... parameters)
			throws MatrixException, IOException {
		try {
			InputStream stream = ImportMatrix.class.getClassLoader().getResourceAsStream(name);
			if (stream == null) {
				return null;
			}
			Matrix m = fromStream(format, stream, parameters);
			stream.close();
			return m;
		} catch (Exception e) {
			throw new MatrixException("could not import", e);
		}
	}

	public static Matrix fromReader(FileFormat format, Reader reader, Object... parameters)
			throws MatrixException, IOException {
		try {
			Class<?> c = Class.forName("org.ujmp.core.io.ImportMatrix" + format.name());
			Method m = c.getMethod("fromReader", new Class<?>[] { Reader.class, Object[].class });
			Matrix matrix = (Matrix) m.invoke(null, reader, parameters);
			return matrix;
		} catch (ClassNotFoundException e) {
			throw new MatrixException("format not supported: " + format, e);
		} catch (NoSuchMethodException e) {
			throw new MatrixException("format not supported: " + format, e);
		} catch (IllegalAccessException e) {
			throw new MatrixException("format not supported: " + format, e);
		} catch (InvocationTargetException e) {
			throw new MatrixException("could not import", e);
		}
	}

	public static Matrix fromClipboard(FileFormat format, Object... parameters)
			throws MatrixException {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable clipData = clipboard.getContents(null);
		String s;
		try {
			s = (String) (clipData.getTransferData(DataFlavor.stringFlavor));
		} catch (Exception ex) {
			s = ex.toString();
		}
		return fromString(format, s, parameters);
	}
}

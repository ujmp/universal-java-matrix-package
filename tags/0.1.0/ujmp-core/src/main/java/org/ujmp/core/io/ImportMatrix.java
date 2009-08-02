/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

import org.ujmp.core.Matrix;
import org.ujmp.core.Matrix.Format;
import org.ujmp.core.exceptions.MatrixException;

public abstract class ImportMatrix {

	public static Matrix fromFile(File file, Object... parameters) throws MatrixException, IOException {
		return fromFile(ExportMatrix.guessFormat(file), file, parameters);
	}

	public static Matrix fromFile(Format format, File file, Object... parameters) throws MatrixException, IOException {
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

	public static Matrix fromString(Format format, String string, Object parameters) throws MatrixException {
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

	public static Matrix fromStream(Format format, InputStream stream, Object parameters) throws MatrixException,
			IOException {
		try {
			Class<?> c = Class.forName("org.ujmp.core.io.ImportMatrix" + format.name());
			Method m = c.getMethod("fromStream", new Class<?>[] { InputStream.class, Object[].class });
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

	public static Matrix fromReader(Format format, Reader reader, Object parameters) throws MatrixException,
			IOException {
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

	public static Matrix fromClipboard(Format format, Object... parameters) throws MatrixException {
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

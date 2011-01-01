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

package org.ujmp.core.io;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.io.FileUtil;

public abstract class ExportMatrix {

	public static final void toFile(File file, Matrix m, Object... parameters)
			throws MatrixException, IOException {
		toFile(FileUtil.guessFormat(file), file, m, parameters);
	}

	public static final void toFile(String file, Matrix m, Object... parameters)
			throws MatrixException, IOException {
		toFile(FileUtil.guessFormat(new File(file)), file, m, parameters);
	}

	public static final void toFile(FileFormat format, String filename, Matrix matrix,
			Object... parameters) throws MatrixException, IOException {
		toFile(format, new File(filename), matrix, parameters);
	}

	public static final void toFile(FileFormat format, File file, Matrix matrix,
			Object... parameters) throws MatrixException, IOException {
		try {
			File dir = file.getParentFile();
			if (dir != null && !dir.exists()) {
				dir.mkdirs();
			}
			Class<?> c = Class.forName("org.ujmp.core.io.ExportMatrix" + format.name());
			Method m = c.getMethod("toFile", new Class<?>[] { File.class, Matrix.class,
					Object[].class });
			m.invoke(null, file, matrix, parameters);
		} catch (ClassNotFoundException e) {
			throw new MatrixException("file format not supported: " + format, e);
		} catch (NoSuchMethodException e) {
			throw new MatrixException("file format not supported: " + format, e);
		} catch (IllegalAccessException e) {
			throw new MatrixException("file format not supported: " + format, e);
		} catch (InvocationTargetException e) {
			throw new MatrixException("error exporting matrix in format: " + format, e);
		}
	}

	public static final String toString(FileFormat format, Matrix matrix, Object... parameters)
			throws MatrixException, IOException {
		StringWriter writer = new StringWriter();
		toWriter(format, writer, matrix, parameters);
		writer.close();
		return writer.toString();
	}

	public static final void toClipboard(FileFormat format, Matrix matrix, Object... parameters)
			throws MatrixException, IOException {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection ms = new StringSelection(toString(format, matrix, parameters));
		clipboard.setContents(ms, ms);
	}

	public static final void toStream(FileFormat format, OutputStream outputStream, Matrix matrix,
			Object... parameters) throws IOException {
		try {
			Class<?> c = Class.forName("org.ujmp.core.io.ExportMatrix" + format.name());
			Method m = c.getMethod("toStream", new Class<?>[] { OutputStream.class, Matrix.class,
					Object[].class });
			m.invoke(null, outputStream, matrix, parameters);
		} catch (Exception e) {
			throw new MatrixException("stream format not supported: " + format, e);
		}
	}

	public static final void toWriter(FileFormat format, Writer writer, Matrix matrix,
			Object... parameters) {
		try {
			Class<?> c = Class.forName("org.ujmp.core.io.ExportMatrix" + format.name());
			Method m = c.getMethod("toWriter", new Class<?>[] { Writer.class, Matrix.class,
					Object[].class });
			m.invoke(null, writer, matrix, parameters);
		} catch (Exception e) {
			throw new MatrixException("writer format not supported: " + format, e);
		}
	}
}

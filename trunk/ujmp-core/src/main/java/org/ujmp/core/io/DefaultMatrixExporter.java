/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.DB;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.exceptions.MatrixException;

public class DefaultMatrixExporter implements MatrixExporter {

	private final Matrix matrix;

	public DefaultMatrixExporter(Matrix matrix) {
		this.matrix = matrix;
	}

	public final void toFile(File file, Object... parameters) throws MatrixException, IOException {
		ExportMatrix.toFile(file, matrix, parameters);
	}

	public final void toClipboard(FileFormat format, Object... parameters) throws MatrixException,
			IOException {
		ExportMatrix.toClipboard(format, matrix, parameters);
	}

	public final void toFile(String file, Object... parameters) throws MatrixException, IOException {
		ExportMatrix.toFile(file, matrix, parameters);
	}

	public final void toJDBC(String url, String tablename, String username, String password)
			throws MatrixException {
		ExportMatrix.toJDBC(matrix, url, tablename, username, password);
	}

	public final void toJDBC(DB type, String host, int port, String database, String tablename,
			String username, String password) throws MatrixException {
		ExportMatrix.toJDBC(matrix, type, host, port, database, tablename, username, password);
	}

	public final void toFile(FileFormat format, String filename, Object... parameters)
			throws MatrixException, IOException {
		ExportMatrix.toFile(format, filename, matrix, parameters);
	}

	public final void toFile(FileFormat format, File file, Object... parameters)
			throws MatrixException, IOException {
		ExportMatrix.toFile(format, file, matrix, parameters);
	}

	public final void toStream(FileFormat format, OutputStream outputStream, Object... parameters)
			throws MatrixException, IOException {
		ExportMatrix.toStream(format, outputStream, matrix, parameters);
	}

	public final void toWriter(FileFormat format, Writer writer, Object... parameters)
			throws MatrixException, IOException {
		ExportMatrix.toWriter(format, writer, matrix, parameters);
	}

	public final String toString(FileFormat format, Object... parameters) throws MatrixException,
			IOException {
		return ExportMatrix.toString(format, matrix, parameters);
	}

}

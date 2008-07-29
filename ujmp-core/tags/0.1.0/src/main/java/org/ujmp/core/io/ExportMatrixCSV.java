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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.io.IntelligentFileWriter;

public abstract class ExportMatrixCSV {

	private static String separator = "\t";

	public static void toFile(File file, Matrix matrix, Object... parameters) throws IOException, MatrixException {
		IntelligentFileWriter writer = new IntelligentFileWriter(file);
		toWriter(writer, matrix, parameters);
		writer.close();
	}

	public static void toStream(OutputStream outputStream, Matrix matrix, Object... parameters) throws IOException,
			MatrixException {
		OutputStreamWriter writer = new OutputStreamWriter(outputStream);
		toWriter(writer, matrix, parameters);
		writer.close();
	}

	public static void toWriter(Writer writer, Matrix matrix, Object... parameters) throws IOException, MatrixException {
		String lineend = System.getProperty("line.separator");
		long rowCount = matrix.getRowCount();
		long colCount = matrix.getColumnCount();
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
				writer.append("" + matrix.getObject(row, col));
				if (col < colCount - 1) {
					writer.append(separator);
				}
			}
			if (row < rowCount - 1) {
				writer.append(lineend);
			}
		}
	}
}

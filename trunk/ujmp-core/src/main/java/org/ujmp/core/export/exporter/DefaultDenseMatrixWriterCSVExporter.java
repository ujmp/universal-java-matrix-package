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

package org.ujmp.core.export.exporter;

import java.io.IOException;
import java.io.Writer;

import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.export.format.MatrixCSVExportFormat;
import org.ujmp.core.util.StringUtil;

public class DefaultDenseMatrixWriterCSVExporter extends AbstractDenseMatrixWriterExporter
		implements MatrixCSVExportFormat {

	public DefaultDenseMatrixWriterCSVExporter(DenseMatrix matrix, Writer writer) {
		super(matrix, writer);
	}

	public void asCSV(String columnSeparator, String lineSeparator) throws IOException {
		final Writer writer = getWriter();
		final Matrix matrix = getMatrix();

		final long rowCount = matrix.getRowCount();
		final long colCount = matrix.getColumnCount();

		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
				String s = StringUtil.convert(matrix.getAsObject(row, col));
				if (s == null) {
					s = "";
				}
				if (s.contains("\n") || s.contains("\r") || s.contains("\\u000a")
						|| s.contains("\\u000d")) {
					throw new RuntimeException(
							"at least one cell contains a line break, CSV output will be garbage");
				}
				if (s.contains("\t") || s.contains("\\u0009") || s.contains("\\u00ad")) {
					throw new RuntimeException(
							"at least one cell contains a tabulator, CSV output will be garbage");
				}
				writer.append(s);
				if (col < colCount - 1) {
					writer.append(columnSeparator);
				}
			}
			if (row < rowCount - 1) {
				writer.append(lineSeparator);
			}
		}
	}

	public void asCSV(String columnSeparator) throws IOException {
		asCSV(columnSeparator, System.getProperty("line.separator"));
	}

	public void asCSV() throws IOException {
		asCSV("\t");
	}

}

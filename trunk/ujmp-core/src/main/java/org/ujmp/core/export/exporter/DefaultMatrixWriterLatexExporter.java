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

import org.ujmp.core.Matrix;
import org.ujmp.core.export.format.MatrixLatexExportFormat;
import org.ujmp.core.util.UJMPFormat;

public class DefaultMatrixWriterLatexExporter extends AbstractMatrixWriterExporter implements
		MatrixLatexExportFormat {

	public DefaultMatrixWriterLatexExporter(Matrix matrix, Writer writer) {
		super(matrix, writer);
	}

	public void asLatex() throws IOException {
		final String EOL = System.getProperty("line.separator");
		final Writer writer = getWriter();
		final Matrix matrix = getMatrix();

		final long rowCount = matrix.getRowCount();
		final long colCount = matrix.getColumnCount();

		writer.write("\\begin{table}[!ht]" + EOL);
		writer.write("\\centering" + EOL);

		if (matrix.getLabelObject() != null) {
			writer.write("\\caption{"
					+ UJMPFormat.getSingleLineInstance().format(matrix.getLabelObject()) + "}"
					+ EOL);
		}

		StringBuilder buf = new StringBuilder();
		for (long i = matrix.getColumnCount() - 1; i != -1; i--) {
			buf.append('c');
		}
		String alignment = buf.toString();

		writer.write("\\begin{tabular}{" + alignment + "}" + EOL);
		writer.write("\\toprule" + EOL);

		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
				writer.write(UJMPFormat.getSingleLineInstance()
						.format(matrix.getAsObject(row, col)));

				if (col < colCount - 1) {
					writer.write(" & ");
				}
			}
			writer.write(" \\\\" + EOL);
		}

		writer.write("\\bottomrule" + EOL);
		writer.write("\\end{tabular}" + EOL);
		writer.write("\\end{table}" + EOL);
	}

}

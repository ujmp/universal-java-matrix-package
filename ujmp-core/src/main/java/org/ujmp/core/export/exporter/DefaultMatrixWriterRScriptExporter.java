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

package org.ujmp.core.export.exporter;

import java.io.IOException;
import java.io.Writer;

import org.ujmp.core.Matrix;
import org.ujmp.core.export.format.MatrixRScriptExportFormat;

public class DefaultMatrixWriterRScriptExporter extends AbstractMatrixWriterExporter implements
		MatrixRScriptExportFormat {

	public DefaultMatrixWriterRScriptExporter(Matrix matrix, Writer writer) {
		super(matrix, writer);
	}

	public void asRScript(String variableName) throws IOException {
		final String EOL = System.getProperty("line.separator");
		final Writer writer = getWriter();
		final Matrix matrix = getMatrix();

		final long nrow = matrix.getRowCount();
		final long ncol = matrix.getColumnCount();

		writer.append(variableName);
		writer.append(" <- ");
		writer.append("matrix(c(");

		for (int c = 0; c < ncol; c++) {
			writer.append("c(");
			for (int r = 0; r < nrow; r++) {
				writer.append(String.valueOf(matrix.getAsDouble(r, c)));
				if ((r + 1) < nrow) {
					writer.append(",");
				}
			}
			writer.append(")");
			if ((c + 1) < ncol) {
				writer.append(",");
			}
		}
		writer.append("),ncol=" + ncol + ",nrow=" + nrow + ")" + EOL);
	}

}

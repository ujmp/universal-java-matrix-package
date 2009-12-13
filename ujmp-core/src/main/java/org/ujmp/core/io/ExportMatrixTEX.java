/*
 * Copyright (C) 2008-2009 by Holger Arndt
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
import org.ujmp.core.util.UJMPFormat;
import org.ujmp.core.util.io.IntelligentFileWriter;

public class ExportMatrixTEX {

	public static final void toFile(File file, Matrix m, Object... parameters) throws IOException {
		IntelligentFileWriter w = new IntelligentFileWriter(file);
		toWriter(w, m, parameters);
		w.close();
	}

	public static final void toStream(OutputStream out, Matrix m, Object... parameters)
			throws IOException {
		OutputStreamWriter w = new OutputStreamWriter(out);
		toWriter(w, m, parameters);
		w.close();
	}

	public static void toWriter(Writer w, Matrix m, Object... parameters) throws IOException,
			MatrixException {
		String EOL = System.getProperty("line.separator");

		long rowCount = m.getRowCount();
		long colCount = m.getColumnCount();

		w.write("\\begin{table}[!ht]" + EOL);
		w.write("\\centering" + EOL);

		if (m.getMatrixAnnotation() != null) {
			w.write("\\caption{"
					+ UJMPFormat.getSingleLineInstance().format(m.getMatrixAnnotation()) + "}"
					+ EOL);
		}

		StringBuilder buf = new StringBuilder();
		for (long i = m.getColumnCount() - 1; i != -1; i--) {
			buf.append('c');
		}
		String alignment = buf.toString();

		w.write("\\begin{tabular}{" + alignment + "}" + EOL);
		w.write("\\toprule" + EOL);

		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
				w.write(UJMPFormat.getSingleLineInstance().format(m.getAsObject(row, col)));

				if (col < colCount - 1) {
					w.write(" & ");
				}
			}
			w.write(" \\\\" + EOL);
		}

		w.write("\\bottomrule" + EOL);
		w.write("\\end{tabular}" + EOL);
		w.write("\\end{table}" + EOL);
	}
}

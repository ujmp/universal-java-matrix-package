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
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.StringUtil;
import org.ujmp.core.util.io.IntelligentFileWriter;

public class ExportMatrixHTML {

	public static void toFile(File file, Matrix matrix, Object... parameters) throws IOException,
			MatrixException {
		IntelligentFileWriter writer = new IntelligentFileWriter(file);
		toWriter(writer, matrix, parameters);
		writer.close();
	}

	public static void toStream(OutputStream outputStream, Matrix matrix, Object... parameters)
			throws IOException, MatrixException {
		OutputStreamWriter writer = new OutputStreamWriter(outputStream);
		toWriter(writer, matrix, parameters);
		writer.close();
	}

	public static void toWriter(Writer out, Matrix m, Object... parameters) throws IOException,
			MatrixException {
		String EOL = System.getProperty("line.separator");

		long rowCount = m.getRowCount();
		long colCount = m.getColumnCount();

		out.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" ");
		out.append("\"http://www.w3.org/TR/html4/strict.dtd\">" + EOL);
		out.append("<body>" + EOL);
		out.append("<html>" + EOL + EOL);
		out.append("<table>" + EOL);
		for (int row = 0; row < rowCount; row++) {
			out.append("<tr>" + EOL);
			for (int col = 0; col < colCount; col++) {
				out.append("<td>");
				out.append(StringUtil.convert(m.getAsObject(row, col)));
				out.append("</td>" + EOL);
			}
			out.append("</tr>" + EOL);
		}
		out.append("</table>" + EOL);
		out.append("</body>" + EOL);
		out.append("</html>" + EOL);
	}

}

/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

import java.awt.Color;
import java.io.IOException;
import java.io.Writer;

import org.ujmp.core.Matrix;
import org.ujmp.core.export.format.MatrixHtmlExportFormat;
import org.ujmp.core.util.ColorUtil;

public class DefaultMatrixWriterHtmlExporter extends AbstractMatrixWriterExporter implements
		MatrixHtmlExportFormat {

	public DefaultMatrixWriterHtmlExporter(Matrix matrix, Writer writer) {
		super(matrix, writer);
	}

	public void asHtml() throws IOException {
		final String EOL = System.getProperty("line.separator");
		final Writer writer = getWriter();
		final Matrix matrix = getMatrix();

		final long rowCount = matrix.getRowCount();
		final long colCount = matrix.getColumnCount();

		writer.write("<div class=\"table-reponsive\" style=\"overflow-x: scroll;\">" + EOL);
		writer.write("<table class=\"table table-bordered\">" + EOL);

		if (matrix.getMetaData() != null) {
			writer.write("<thead>" + EOL);
			writer.write("<tr>" + EOL);

			writer.write("<th>" + EOL);
			if (matrix.getLabelObject() != null) {
				writer.write(String.valueOf(matrix.getLabelObject()));
			}
			writer.write("</th>" + EOL);

			for (long c = 0; c < matrix.getColumnCount(); c++) {
				writer.write("<th>");
				writer.write(matrix.getColumnLabel(c));
				writer.write("</th>" + EOL);
			}

			writer.write("</tr>" + EOL);
			writer.write("</thead>" + EOL);
		}

		writer.write("<tbody>" + EOL);

		for (int row = 0; row < rowCount; row++) {
			writer.write("<tr>" + EOL);

			if (matrix.getMetaData() != null) {
				writer.write("<th>");
				writer.write(matrix.getRowLabel(row));
				writer.write("</th>" + EOL);
			}

			for (int col = 0; col < colCount; col++) {
				Object o = matrix.getAsObject(row, col);
				Color bg = ColorUtil.fromObject(o);
				Color fg = ColorUtil.contrastBW(bg);
				writer.write("<td class=\"text-right\" style=\"color:" + ColorUtil.toHtmlColor(fg)
						+ ";background:" + ColorUtil.toHtmlColor(bg) + "\">");
				writer.write(matrix.getAsString(row, col));
				writer.write("</td>" + EOL);
			}
			writer.write("</tr>" + EOL);
		}

		writer.write("</tbody>" + EOL);
		writer.write("</table>" + EOL);
		writer.write("</div>" + EOL);
	}

}

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
import java.util.Collection;

import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;
import org.ujmp.core.exception.NotImplementedException;
import org.ujmp.core.export.format.MatrixJsonExportFormat;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.setmatrix.SetMatrix;
import org.ujmp.core.util.ColorUtil;

public class DefaultMatrixWriterJsonExporter extends AbstractMatrixWriterExporter implements MatrixJsonExportFormat {

	final String EOL = System.getProperty("line.separator");

	public DefaultMatrixWriterJsonExporter(Matrix matrix, Writer writer) {
		super(matrix, writer);
	}

	public void asJson() throws IOException {
		final Writer writer = getWriter();
		final Matrix matrix = getMatrix();

		if (matrix instanceof MapMatrix) {
			exportMapMatrix((MapMatrix<?, ?>) matrix, writer);
		} else if (matrix instanceof ListMatrix) {
			exportListMatrix((ListMatrix<?>) matrix, writer);
		} else if (matrix instanceof SetMatrix) {
			exportSetMatrix((SetMatrix<?>) matrix, writer);
		} else if (matrix instanceof SparseMatrix) {
			exportSparseMatrix((SparseMatrix) matrix, writer);
		} else {
			exportDenseMatrix((DenseMatrix) matrix, writer);
		}
	}

	private void exportDenseMatrix(DenseMatrix matrix, Writer writer) throws IOException {

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
				writer.write("<td class=\"text-right\" style=\"color:" + ColorUtil.toHtmlColor(fg) + ";background:"
						+ ColorUtil.toHtmlColor(bg) + "\">");
				writer.write(matrix.getAsString(row, col));
				writer.write("</td>" + EOL);
			}
			writer.write("</tr>" + EOL);
		}

		writer.write("</tbody>" + EOL);
		writer.write("</table>" + EOL);
		writer.write("</div>" + EOL);
	}

	private void exportSparseMatrix(SparseMatrix matrix, Writer writer) throws IOException {
		throw new NotImplementedException();
	}

	private void exportListMatrix(ListMatrix<?> matrix, Writer writer) throws IOException {
		writer.write("{");
		writer.write(EOL);
		writer.write("  \"type\": \"ListMatrix\",");
		writer.write(EOL);
		writer.write("  \"class\": \"" + matrix.getClass().getName() + "\",");
		writer.write(EOL);
		if (matrix.getMetaData() != null) {
			writer.write("  \"metaData\":");
			writer.write(EOL);
			writer.write(matrix.getMetaData().toJson());
			writer.write(EOL);
		}
		writer.write("  \"data\":");
		writer.write(EOL);
		writer.write("  [");
		writer.write(EOL);
		for (Object o : matrix) {
			writer.write(objectToJson(o));
			writer.write(EOL);
		}
		writer.write("  ]");
		writer.write(EOL);
		writer.write("}");
		writer.write(EOL);
	}

	private String objectToJson(Object o) {
		if (o == null) {
			return "null";
		} else if (o instanceof Boolean) {
			return ((Boolean) o) ? "true" : "false";
		} else if (o instanceof String) {
			return "\"" + replaceControlChars((String) o) + "\"";
		} else if (o instanceof Integer) {
			return ((Integer) o).toString();
		} else if (o instanceof Short) {
			return ((Short) o).toString();
		} else if (o instanceof Long) {
			return ((Long) o).toString();
		} else if (o instanceof Float) {
			return ((Float) o).toString();
		} else if (o instanceof Double) {
			return ((Double) o).toString();
		} else if (o instanceof Matrix) {
			return ((Matrix) o).toJson();
		} else if (o instanceof Collection) {
			return collectionToJson((Collection<?>) o);
		} else {
			return null;
		}
	}

	private String collectionToJson(Collection<?> collection) {
		StringBuilder s = new StringBuilder();
		s.append("[" + EOL);
		for (Object o : collection) {
			s.append(objectToJson(o));
		}
		s.append("]" + EOL);
		return s.toString();
	}

	private void exportMapMatrix(MapMatrix<?, ?> matrix, Writer writer) throws IOException {
		throw new NotImplementedException();
	}

	private void exportSetMatrix(SetMatrix<?> matrix, Writer writer) throws IOException {
		throw new NotImplementedException();
	}

	private static final String replaceControlChars(String s) {
		s = s.replaceAll("\\", "\\\\");
		s = s.replaceAll("\"", "\\\"");
		s = s.replaceAll("/", "\\/");
		s = s.replaceAll("\b", "\\b");
		s = s.replaceAll("\f", "\\f");
		s = s.replaceAll("\n", "\\n");
		s = s.replaceAll("\r", "\\r");
		s = s.replaceAll("\t", "\\t");
		return s;
	}
}

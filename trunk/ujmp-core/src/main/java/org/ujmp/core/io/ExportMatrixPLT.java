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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.ujmp.core.Matrix;
import org.ujmp.core.util.StringUtil;
import org.ujmp.core.util.io.IntelligentFileWriter;

public abstract class ExportMatrixPLT {

	public static void toFile(File file, Matrix matrix, Object... parameters) throws Exception {
		IntelligentFileWriter writer = new IntelligentFileWriter(file);
		toWriter(writer, matrix, parameters);
		writer.close();
	}

	public static void toStream(OutputStream outputStream, Matrix matrix, Object... parameters)
			throws Exception {
		OutputStreamWriter writer = new OutputStreamWriter(outputStream);
		toWriter(writer, matrix, parameters);
		writer.close();
	}

	public static void toWriter(Writer writer, Matrix matrix, Object... parameters)
			throws Exception {
		String EOL = System.getProperty("line.separator");
		boolean xy = false;
		boolean logx = false;
		boolean logy = false;
		for (Object o : parameters) {
			String s = StringUtil.getString(o);
			if ("logx".equalsIgnoreCase(s)) {
				logx = true;
			} else if ("logy".equalsIgnoreCase(s)) {
				logy = true;
			} else if ("xy".equalsIgnoreCase(s)) {
				xy = true;
			}
		}

		writeData(writer, matrix, xy, logx, logy);
		writer.write("pause -1 'Export plot to eps?'" + EOL);
		writer.write("set output 'plot" + System.currentTimeMillis() + ".eps'" + EOL);
		writer.write("set terminal postscript eps" + EOL);
		writer.write("replot" + EOL);
		writeData(writer, matrix, xy, logx, logy);
	}

	private static void writeData(Writer writer, Matrix matrix, boolean xy, boolean logx,
			boolean logy) throws Exception {
		String EOL = System.getProperty("line.separator");
		writer.write("set key outside below" + EOL);
		writer.write("set autoscale fix" + EOL);
		if (logx && logy) {
			writer.write("set log xy" + EOL);
		} else if (logx) {
			writer.write("set log x" + EOL);
		} else if (logx) {
			writer.write("set log x" + EOL);
		}
		if (xy) {
			String x = matrix.getColumnLabel(0);
			x = x == null ? "column 0" : x;
			writer.write("set xlabel '" + x + "'" + EOL);
		} else {
			writer.write("set xlabel 'column'" + EOL);
		}
		// writer.write("set ylabel 'value'" + EOL);
		writer.write("set title '" + StringUtil.format(matrix.getLabel()) + "'" + EOL);
		writer.write("plot ");

		int startColumn = xy ? 1 : 0;
		for (int c = startColumn; c < matrix.getColumnCount(); c++) {
			String x = matrix.getColumnLabel(c);
			x = x == null ? "column " + c : x;
			writer.write("'-' using 1:2 title '" + x + "' with linespoints");
			if (c < matrix.getColumnCount() - 1) {
				writer.write(", ");
			} else {
				writer.write(EOL);
			}
		}

		if (xy) {
			for (int c = 1; c < matrix.getColumnCount(); c++) {
				for (int r = 0; r < matrix.getRowCount(); r++) {
					writer.write(matrix.getAsDouble(r, 0) + " " + matrix.getAsDouble(r, c) + EOL);
				}
				writer.write("e" + EOL); // data end
			}
		} else {
			for (int c = 0; c < matrix.getColumnCount(); c++) {
				for (int r = 0; r < matrix.getRowCount(); r++) {
					writer.write(r + " " + matrix.getAsDouble(r, c) + EOL);
				}
				writer.write("e" + EOL); // data end
			}
		}
	}
}

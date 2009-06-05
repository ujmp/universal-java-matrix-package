/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.io.IntelligentFileReader;

public abstract class ImportMatrixCSV {

	private static String fieldDelimiter = "[,;\t]";

	private static final boolean trimFields = true;

	private static final boolean ignoreQuotationMarks = true;

	private static final String quotation = "\"";

	public static final Matrix fromString(String string, Object... parameters)
			throws MatrixException {
		StringReader sr = new StringReader(string);
		IntelligentFileReader r = new IntelligentFileReader(sr);
		Matrix m = fromReader(r, parameters);
		r.close();
		return m;
	}

	public static final Matrix fromStream(InputStream stream, Object... parameters)
			throws MatrixException, IOException {
		InputStreamReader r = new InputStreamReader(stream);
		Matrix m = fromReader(r, parameters);
		r.close();
		return m;
	}

	public static final Matrix fromFile(File file, Object... parameters) throws MatrixException,
			IOException {
		FileReader lr = new FileReader(file);
		Matrix m = fromReader(lr, parameters);
		m.setLabel(file.getAbsolutePath());
		lr.close();
		return m;
	}

	public static final Matrix fromReader(Reader reader, Object... parameters)
			throws MatrixException {
		List<String[]> rowData = new ArrayList<String[]>();

		if (parameters.length == 1 && parameters[0] instanceof String) {
			fieldDelimiter = (String) parameters[0];
		} else {
			System.out
					.println("You should specify the column separator to make sure that the file is parsed correctly.");
			System.out
					.println("Example: MatrixFactory.importFromFile(FileFormat.CSV, file, \";\")");
		}
		try {
			Pattern p = Pattern.compile(fieldDelimiter);
			IntelligentFileReader lr = new IntelligentFileReader(reader);
			int rows = 0;
			int cols = 0;
			String line = null;
			while ((line = lr.readLine()) != null) {
				if (line.length() > 0) {
					String[] fields = p.split(line);
					if (trimFields) {
						for (int i = 0; i < fields.length; i++) {
							fields[i] = fields[i].trim();
						}
					}
					if (ignoreQuotationMarks) {
						for (int i = 0; i < fields.length; i++) {
							String s = fields[i];
							if (s.length() > 1 && s.startsWith(quotation) && s.endsWith(quotation)) {
								fields[i] = s.substring(1, s.length() - 2);
							}
						}
					}
					int lcols = fields.length;
					rowData.add(fields);
					if (lcols > cols) {
						cols = lcols;
					}
					rows++;
				} else {
					rowData.add(new String[] { "" });
					rows++;
				}
			}
			lr.close();
			Matrix m = MatrixFactory.zeros(ValueType.STRING, rows, cols);

			int r = 0;
			for (String[] fields : rowData) {
				for (int c = fields.length - 1; c != -1; c--) {
					m.setAsString(fields[c], r, c);
				}
				r++;
			}

			return m;
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

}

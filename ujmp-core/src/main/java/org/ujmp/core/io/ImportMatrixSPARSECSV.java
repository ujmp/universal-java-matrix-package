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
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.util.io.IntelligentFileReader;

public abstract class ImportMatrixSPARSECSV {

	public static final Matrix fromString(String string, Object... parameters) {
		StringReader sr = new StringReader(string);
		IntelligentFileReader r = new IntelligentFileReader(sr);
		Matrix m = fromReader(r);
		r.close();
		return m;
	}

	public static final Matrix fromStream(InputStream stream, Object... parameters)
			throws IOException {
		InputStreamReader r = new InputStreamReader(stream);
		Matrix m = fromReader(r, parameters);
		r.close();
		return m;
	}

	public static final Matrix fromFile(File file, Object... parameters) throws IOException {
		FileReader lr = new FileReader(file);
		Matrix m = fromReader(lr, parameters);
		m.setLabel(file.getAbsolutePath());
		lr.close();
		return m;
	}

	public static final Matrix fromReader(Reader reader, Object... parameters) {
		List<Long> rowData = new ArrayList<Long>();
		List<Long> colData = new ArrayList<Long>();
		List<String> data = new ArrayList<String>();

		String separator = "[,;\t]";
		if (parameters.length == 1 && parameters[0] instanceof String) {
			separator = (String) parameters[0];
		}
		try {
			Pattern p = Pattern.compile(separator);
			IntelligentFileReader lr = new IntelligentFileReader(reader);
			long rows = 0;
			long cols = 0;
			String line = null;
			while ((line = lr.readLine()) != null) {
				if (line.length() > 0) {
					String[] fields = p.split(line);
					if (fields.length == 3) {
						long row = Long.parseLong(fields[0]);
						long col = Long.parseLong(fields[1]);
						String s = fields[2];
						if (row > rows) {
							rows = row;
						}
						if (col > cols) {
							cols = col;
						}
						rowData.add(row);
						colData.add(col);
						data.add(s);
					}
				}
			}
			lr.close();
			Matrix m = Matrix.Factory.sparse(ValueType.STRING, rows + 1, cols + 1);

			for (int i = 0; i < data.size(); i++) {
				long row = rowData.get(i);
				long col = colData.get(i);
				String s = data.get(i);
				m.setAsString(s, row, col);
			}

			return m;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.util.StringUtil;

public abstract class ImportMatrixM {

	/**
	 * Creates a DefaultDenseStringMatrix2D from a given String. The string
	 * contains the rows of the matrix separated by semicolons or new lines. The
	 * columns of the matrix are separated by spaces or commas. All types of
	 * brackets are ignored.
	 * <p>
	 * Examples: "[1 2 3; 4 5 6]" or "(test1,test2);(test3,test4)"
	 * 
	 * @param string
	 *            the string to parse
	 * @return a StringMatrix with the desired values
	 */
	public static Matrix fromString(String string, Object... parameters) {
		string = string.replaceAll(StringUtil.BRACKETS, "");
		String[] rows = string.split(StringUtil.SEMICOLONORNEWLINE);
		String[] cols = rows[0].split(StringUtil.COLONORSPACES);
		Object[][] values = new String[rows.length][cols.length];
		for (int r = 0; r < rows.length; r++) {
			cols = rows[r].split(StringUtil.COLONORSPACES);
			for (int c = 0; c < cols.length; c++) {
				values[r][c] = cols[c];
			}
		}
		return MatrixFactory.linkToArray(values);
	}

	public static Matrix fromFile(File file, Object... parameters) throws IOException {
		Reader reader = new BufferedReader(new FileReader(file));
		Matrix matrix = fromReader(reader, parameters);
		reader.close();
		return matrix;
	}

	public static Matrix fromStream(InputStream stream, Object... parameters) throws IOException {
		InputStreamReader r = new InputStreamReader(stream);
		Matrix matrix = fromReader(r, parameters);
		r.close();
		return matrix;
	}

	public static Matrix fromReader(Reader reader, Object... parameters) throws IOException {
		String EOL = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		LineNumberReader lr = new LineNumberReader(reader);
		String line = null;
		while ((line = lr.readLine()) != null) {
			sb.append(line + EOL);
		}
		return fromString(sb.toString(), parameters);
	}

}

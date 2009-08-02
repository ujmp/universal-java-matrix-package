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

package org.ujmp.core.stringmatrix;

import java.io.File;
import java.io.IOException;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.io.SeekableLineInputStream;

public class CSVMatrix extends AbstractDenseStringMatrix2D {
	private static final long serialVersionUID = 6025235663309962730L;

	private final String separator = "\t";

	private int columnCount = 0;

	private SeekableLineInputStream sli = null;

	public CSVMatrix(String file) throws IOException {
		this(new File(file));
	}

	public CSVMatrix(File file) throws IOException {
		sli = new SeekableLineInputStream(file);
		for (int i = 0; i < 100; i++) {
			String line = sli.readLine(MathUtil.nextInteger(0, sli.getLineCount()));
			int c = line.split(separator).length;
			if (c > columnCount) {
				columnCount = c;
			}
		}
	}

	public long[] getSize() {
		return new long[] { sli.getLineCount(), columnCount };
	}

	public String getString(long row, long column) throws MatrixException {
		try {
			String line = sli.readLine((int) row);
			String fields[] = line.split(separator);
			if (fields.length > columnCount) {
				columnCount = fields.length;
			}
			if (column < fields.length) {
				return fields[(int) column];
			}
		} catch (Exception e) {
			throw new MatrixException(e);
		}
		return null;
	}

	public void setString(String value, long row, long column) {
	}
}

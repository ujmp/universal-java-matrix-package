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

package org.ujmp.core.stringmatrix.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.ujmp.core.collections.map.SoftHashMap;
import org.ujmp.core.stringmatrix.stub.AbstractSparseStringMatrix2D;
import org.ujmp.core.util.io.IntelligentFileReader;
import org.ujmp.core.util.io.SeekableLineInputStream;

public class SparseCSVMatrix extends AbstractSparseStringMatrix2D {
	private static final long serialVersionUID = 3021406834325366430L;

	private String fieldDelimiter = "\t";

	private SeekableLineInputStream sli = null;

	private final Map<Long, Object[]> data = new SoftHashMap<Long, Object[]>();

	private final Map<Long, List<Long>> rowToLine = new HashMap<Long, List<Long>>();

	public SparseCSVMatrix(String file, Object... parameters) throws IOException {
		this(new File(file), parameters);
	}

	public SparseCSVMatrix(File file, Object... parameters) throws IOException {
		super(1, 1);
		if (parameters.length != 0 && parameters[0] instanceof String) {
			this.fieldDelimiter = (String) parameters[0];
		}

		long rows = 0;
		long cols = 0;
		long lastRow = -1;

		IntelligentFileReader lr = new IntelligentFileReader(file);

		System.out.print("determining matrix size.");
		long i = 0;
		String line = null;
		while ((line = lr.readLine()) != null) {
			if (i % 100000 == 0) {
				System.out.print(".");
			}
			String[] fields = line.split(fieldDelimiter);
			long row = Long.parseLong(fields[0]);
			long col = Long.parseLong(fields[1]);
			if (row > rows) {
				rows = row;
			}
			if (col > cols) {
				cols = col;
			}

			if (lastRow != row) {
				lastRow = row;
				List<Long> list = rowToLine.get(row);
				if (list == null) {
					list = new LinkedList<Long>();
					rowToLine.put(row, list);
				}
				list.add(i);
			}
			i++;
		}
		lr.close();
		size = new long[] { rows, cols };
		System.out.println("ok");
		sli = new SeekableLineInputStream(file);

	}

	public final void clear() {
		throw new RuntimeException("matrix cannot be modified");
	}

	public String getString(long row, long column) {
		try {
			List<Long> linesToCheck = rowToLine.get(row);
			if (linesToCheck == null) {
				return null;
			}
			for (long startLine : linesToCheck) {
				for (long l = startLine; l < sli.getLineCount(); l++) {
					Object objects[] = data.get(l);
					if (objects == null) {
						String line = sli.readLine((int) l);
						String[] strings = line.split(fieldDelimiter);
						long foundRow = Long.parseLong(strings[0]);
						long foundColumn = Long.parseLong(strings[1]);
						objects = new Object[] { foundRow, foundColumn, strings[2] };
						data.put(l, objects);
					}

					if ((Long) objects[0] != row) {
						break;
					}

					if ((Long) objects[1] == column) {
						return (String) objects[2];
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	public Iterable<long[]> availableCoordinates() {
		return new Iterable<long[]>() {

			public Iterator<long[]> iterator() {
				return new SparseCSVMatrixIterator();
			}
		};
	}

	public void setString(String value, long row, long column) {
	}

	public boolean containsCoordinates(long... coordinates) {
		return getString(coordinates) != null;
	}

	class SparseCSVMatrixIterator implements Iterator<long[]> {

		long l = 0;

		public boolean hasNext() {
			return l < sli.getLineCount();
		}

		public long[] next() {
			try {
				Object[] objects = data.get(l);
				if (objects == null) {
					String line = sli.readLine((int) l);
					String[] strings = line.split(fieldDelimiter);
					long row = Long.parseLong(strings[0]);
					long col = Long.parseLong(strings[1]);
					objects = new Object[] { row, col, strings[2] };
					data.put(l, objects);
				}

				long[] c = new long[] { (Long) objects[0], (Long) objects[1] };
				l++;
				return c;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		public void remove() {
		}

	}

}

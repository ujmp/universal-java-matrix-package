/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

package org.ujmp.core.objectmatrix.calculation;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.exceptions.MatrixException;

public class Join extends AbstractObjectCalculation {
	private static final long serialVersionUID = -4037364843847848445L;

	private Matrix result = null;

	private long column1 = 0;

	private long column2 = 0;

	public Join(Matrix m1, Matrix m2, long column1, long column2) {
		super(m1, m2);
		this.column1 = column1;
		this.column2 = column2;
	}

	
	public Object getObject(long... coordinates) throws MatrixException {
		if (result == null) {
			createMatrix();
		}
		return result.getAsObject(coordinates);
	}

	
	public long[] getSize() {
		if (result == null) {
			createMatrix();
		}
		return result.getSize();
	}

	private void createMatrix() {
		Matrix m1 = getSource();
		Matrix m2 = getSources()[1];

		Map<Object, List<Long>> right = new HashMap<Object, List<Long>>();

		for (long r = 0; r < m2.getRowCount(); r++) {
			Object o = m2.getAsObject(r, column2);
			List<Long> list = right.get(o);
			if (list == null) {
				list = new LinkedList<Long>();
				right.put(o, list);
			}
			list.add(r);
		}

		result = MatrixFactory.dense(getValueType(), m1.getRowCount(), m1.getColumnCount()
				+ m2.getColumnCount());

		for (long[] c : m1.allCoordinates()) {
			result.setAsObject(m1.getAsObject(c), c);
		}

		for (long r = 0; r < m1.getRowCount(); r++) {
			Object o = m1.getAsObject(r, column1);
			List<Long> list = right.get(o);
			if (list != null) {
				long row2 = list.iterator().next();
				for (long c = 0; c < m2.getColumnCount(); c++) {
					result.setAsObject(m2.getAsObject(row2, c), r, c + m1.getColumnCount());
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Matrix m1 = MatrixFactory.importFromFile(FileFormat.CSV, new File(
				"c:/Documents and Settings/holger/Desktop/original.log"), "\t");
		Matrix m2 = MatrixFactory.importFromFile(FileFormat.CSV, new File(
				"c:/Documents and Settings/holger/Desktop/js.log"), "\t");

		new Join(m1, m2, 3, 3).calc(Ret.NEW).exportToFile("c:/test.txt");
	}
}

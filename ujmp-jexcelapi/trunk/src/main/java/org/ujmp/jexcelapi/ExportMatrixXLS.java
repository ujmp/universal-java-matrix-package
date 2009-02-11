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

package org.ujmp.jexcelapi;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import jxl.Workbook;
import jxl.biff.EmptyCell;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.StringUtil;

public abstract class ExportMatrixXLS {

	public static void toFile(File file, Matrix matrix, Object... parameters)
			throws IOException, MatrixException {
		try {
			WritableWorkbook writableWorkbook = Workbook.createWorkbook(file);
			WritableSheet sheet = writableWorkbook
					.createSheet("First Sheet", 0);
			for (long[] c : matrix.allCoordinates()) {
				int row = (int) c[Matrix.ROW];
				int column = (int) c[Matrix.COLUMN];
				Object o = matrix.getAsObject(c);
				WritableCell cell = null;
				if (o == null) {
					cell = new EmptyCell(column, row);
				} else if (o instanceof Number) {
					cell = new jxl.write.Number(column, row, ((Number) o)
							.doubleValue());
				} else {
					cell = new Label(column, row, StringUtil.convert(o));
				}
				sheet.addCell(cell);
			}
			writableWorkbook.write();
			writableWorkbook.close();
		} catch (WriteException e) {
			throw new MatrixException("could not save to file " + file, e);
		}
	}

	public static void toStream(OutputStream outputStream, Matrix matrix,
			Object... parameters) throws IOException, MatrixException {
		try {
			WritableWorkbook writableWorkbook = Workbook
					.createWorkbook(outputStream);
			WritableSheet sheet = writableWorkbook
					.createSheet("First Sheet", 0);
			for (long[] c : matrix.allCoordinates()) {
				int row = (int) c[Matrix.ROW];
				int column = (int) c[Matrix.COLUMN];
				Object o = matrix.getAsObject(c);
				WritableCell cell = null;
				if (o == null) {
					cell = new EmptyCell(column, row);
				} else if (o instanceof Number) {
					cell = new jxl.write.Number(column, row, ((Number) o)
							.doubleValue());
				} else {
					cell = new Label(column, row, StringUtil.convert(o));
				}
				sheet.addCell(cell);
			}
			writableWorkbook.write();
			writableWorkbook.close();
		} catch (WriteException e) {
			throw new MatrixException("could not save to stream", e);
		}
	}

}

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

package org.ujmp.jexcelapi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import jxl.Cell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public abstract class ImportMatrixXLS {

	public static final Matrix fromFile(File file, Object... parameters)
			throws MatrixException, IOException {
		try {
			int sheetNr = 0;
			if (parameters != null && parameters.length > 0) {
				sheetNr = MathUtil.getInt(parameters[0]);
			}
			Workbook workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(sheetNr);

			int rows = sheet.getRows();
			int columns = sheet.getColumns();
			Matrix matrix = MatrixFactory
					.zeros(ValueType.OBJECT, rows, columns);
			for (int row = 0; row < rows; row++) {
				for (int column = 0; column < columns; column++) {
					Cell c = sheet.getCell(column, row);
					Object o = null;
					if (c instanceof NumberCell) {
						o = ((NumberCell) c).getValue();
					} else {
						o = c.getContents();
					}
					matrix.setAsObject(o, row, column);
				}
			}
			workbook.close();
			return matrix;
		} catch (BiffException e) {
			throw new MatrixException("could not import from file " + file, e);
		}
	}

	public static final Matrix fromStream(InputStream inputStream,
			Object... parameters) throws IOException, MatrixException {
		try {
			Workbook workbook = Workbook.getWorkbook(inputStream);
			Sheet sheet = workbook.getSheet(0);
			int rows = sheet.getRows();
			int columns = sheet.getColumns();
			Matrix matrix = MatrixFactory
					.zeros(ValueType.OBJECT, rows, columns);
			for (int row = 0; row < rows; row++) {
				for (int column = 0; column < columns; column++) {
					Cell c = sheet.getCell(column, row);
					Object o = null;
					if (c instanceof NumberCell) {
						o = ((NumberCell) c).getValue();
					} else {
						o = c.getContents();
					}
					matrix.setAsObject(o, row, column);
				}
			}
			workbook.close();
			return matrix;
		} catch (BiffException e) {
			throw new MatrixException("could not import from stream", e);
		}
	}

}

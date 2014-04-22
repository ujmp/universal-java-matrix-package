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

package org.ujmp.poi;

import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.ujmp.core.objectmatrix.DenseObjectMatrix2D;
import org.ujmp.core.objectmatrix.impl.DefaultDenseObjectMatrix2D;

public abstract class AbstractMatrixExcelImporter {

	public DenseObjectMatrix2D importFromSheet(final Sheet sheet) throws InvalidFormatException, IOException {
		final int rowCount = sheet.getLastRowNum();
		int columnCount = 0;

		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.getLastCellNum() > columnCount) {
				columnCount = row.getLastCellNum();
			}
		}

		final DefaultDenseObjectMatrix2D matrix = new DefaultDenseObjectMatrix2D(rowCount, columnCount);
		matrix.setLabel(sheet.getSheetName());

		for (int r = 0; r < rowCount; r++) {
			Row row = sheet.getRow(r);
			if (row != null) {
				for (int c = 0; c < columnCount; c++) {
					Cell cell = row.getCell(c);
					if (cell != null) {
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_BLANK:
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							matrix.setAsBoolean(cell.getBooleanCellValue(), r, c);
							break;
						case Cell.CELL_TYPE_ERROR:
							break;
						case Cell.CELL_TYPE_FORMULA:
							matrix.setAsString(cell.getCellFormula(), r, c);
							break;
						case Cell.CELL_TYPE_NUMERIC:
							matrix.setAsDouble(cell.getNumericCellValue(), r, c);
							break;
						case Cell.CELL_TYPE_STRING:
							matrix.setAsString(cell.getStringCellValue(), r, c);
							break;
						default:
							break;
						}

					}
				}
			}
		}

		return matrix;
	}

	public DenseObjectMatrix2D importFromWorkbook(final Workbook workbook, final int sheetNumber)
			throws InvalidFormatException, IOException {
		final Sheet sheet = workbook.getSheetAt(sheetNumber);
		return importFromSheet(sheet);
	}

}

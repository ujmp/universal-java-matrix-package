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

package org.ujmp.poi;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ujmp.core.Matrix;

public class MatrixXLSXExporter extends AbstractMatrixExcelExporter {

	public void exportToXLSXFile(final Matrix matrix, final File file, String sheetName) throws IOException {
		final Workbook workbook = new XSSFWorkbook();

		exportToExcelFile(workbook, matrix, file, sheetName);

		final FileOutputStream fileOutputStream = new FileOutputStream(file);
		final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
		workbook.write(fileOutputStream);
		bufferedOutputStream.close();
		fileOutputStream.close();
	}
}

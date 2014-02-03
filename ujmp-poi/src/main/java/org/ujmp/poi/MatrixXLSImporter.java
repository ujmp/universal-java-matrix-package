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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.ujmp.core.objectmatrix.DenseObjectMatrix2D;

public class MatrixXLSImporter extends AbstractMatrixExcelImporter {

	public DenseObjectMatrix2D importFromXLS(final File file, final int sheetNumber) throws InvalidFormatException, IOException {
		final FileInputStream fileInputStream = new FileInputStream(file);
		final BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
		final DenseObjectMatrix2D matrix = importFromXLS(bufferedInputStream, sheetNumber);
		bufferedInputStream.close();
		fileInputStream.close();
		return matrix;
	}

	public DenseObjectMatrix2D importFromXLS(final String filename, final int sheetNumber) throws InvalidFormatException, IOException {
		return importFromXLS(new File(filename), sheetNumber);
	}

	public DenseObjectMatrix2D importFromXLS(final InputStream inputStream, final int sheetNumber) throws InvalidFormatException, IOException {
		final HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
		return importFromWorkbook(workbook, sheetNumber);
	}

}

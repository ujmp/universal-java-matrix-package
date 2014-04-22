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

package org.ujmp.core.importer;

import java.io.File;
import java.io.IOException;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.importer.format.MatrixBMPImportFormat;
import org.ujmp.core.importer.format.MatrixCSVImportFormat;
import org.ujmp.core.importer.format.MatrixGIFImportFormat;
import org.ujmp.core.importer.format.MatrixJPGImportFormat;
import org.ujmp.core.importer.format.MatrixPNGImportFormat;
import org.ujmp.core.intmatrix.impl.ImageMatrix;
import org.ujmp.core.stringmatrix.impl.CSVMatrix;

public class DefaultMatrixFileImageImporter extends AbstractMatrixFileImporter implements
		MatrixJPGImportFormat, MatrixPNGImportFormat, MatrixBMPImportFormat, MatrixGIFImportFormat,
		MatrixCSVImportFormat {

	public DefaultMatrixFileImageImporter(Matrix matrix, File file) {
		super(matrix, file);
	}

	public Matrix asJPG() throws IOException {
		Matrix tmp = new ImageMatrix(getFile());
		if (getTargetMatrix() == null) {
			return tmp;
		} else {
			getTargetMatrix().setContent(Ret.ORIG, tmp, 0, 0);
			return getTargetMatrix();
		}
	}

	public Matrix asGIF() throws IOException {
		Matrix tmp = new ImageMatrix(getFile());
		if (getTargetMatrix() == null) {
			return tmp;
		} else {
			getTargetMatrix().setContent(Ret.ORIG, tmp, 0, 0);
			return getTargetMatrix();
		}
	}

	public Matrix asBMP() throws IOException {
		Matrix tmp = new ImageMatrix(getFile());
		if (getTargetMatrix() == null) {
			return tmp;
		} else {
			getTargetMatrix().setContent(Ret.ORIG, tmp, 0, 0);
			return getTargetMatrix();
		}
	}

	public Matrix asPNG() throws IOException {
		Matrix tmp = new ImageMatrix(getFile());
		if (getTargetMatrix() == null) {
			return tmp;
		} else {
			getTargetMatrix().setContent(Ret.ORIG, tmp, 0, 0);
			return getTargetMatrix();
		}
	}

	public Matrix asCSV() throws IOException {
		return asCSV("[,;\t]", System.getProperty("line.separator"));
	}

	public Matrix asCSV(String columnSeparator) throws IOException {
		return asCSV(columnSeparator, System.getProperty("line.separator"));
	}

	public Matrix asCSV(String columnSeparator, String lineSeparator) throws IOException {
		Matrix tmp = new CSVMatrix(getFile());
		if (getTargetMatrix() == null) {
			return tmp;
		} else {
			getTargetMatrix().setContent(Ret.ORIG, tmp, 0, 0);
			return getTargetMatrix();
		}
	}

}

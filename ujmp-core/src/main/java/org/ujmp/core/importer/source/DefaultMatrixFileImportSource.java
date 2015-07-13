/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

package org.ujmp.core.importer.source;

import java.io.File;
import java.io.IOException;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.importer.DefaultMatrixFileImporter;
import org.ujmp.core.stringmatrix.impl.DenseCSVStringMatrix2D;

public class DefaultMatrixFileImportSource extends AbstractMatrixFileImportSource {

	public DefaultMatrixFileImportSource(Matrix matrix, File file) {
		super(matrix, file);
	}

	public DefaultMatrixFileImportSource(Matrix matrix, String file) {
		super(matrix, file);
	}

	public Matrix asPDF() throws IOException {
		return new DefaultMatrixFileImporter(getTargetMatrix(), getFile()).asPDF();
	}

	public Matrix asJPG() throws IOException {
		return new DefaultMatrixFileImporter(getTargetMatrix(), getFile()).asJPG();
	}

	public Matrix asTIFF() throws IOException {
		return new DefaultMatrixFileImporter(getTargetMatrix(), getFile()).asTIFF();
	}

	public Matrix asBMP() throws IOException {
		return new DefaultMatrixFileImporter(getTargetMatrix(), getFile()).asBMP();
	}

	public Matrix asGIF() throws IOException {
		return new DefaultMatrixFileImporter(getTargetMatrix(), getFile()).asGIF();
	}

	public Matrix asPNG() throws IOException {
		return new DefaultMatrixFileImporter(getTargetMatrix(), getFile()).asPNG();
	}

	public Matrix asDenseCSV() throws IOException {
		return asDenseCSV('\0');
	}

	public Matrix asDenseCSV(char columnSeparator) throws IOException {
		return asDenseCSV(columnSeparator, '\0');
	}

	public Matrix asDenseCSV(char columnSeparator, char enclosingCharacter) throws IOException {
		Matrix m = new DenseCSVStringMatrix2D(columnSeparator, enclosingCharacter, getFile());
		if (getTargetMatrix() != null) {
			getTargetMatrix().setContent(Ret.ORIG, m, 0, 0);
			return getTargetMatrix();
		} else {
			return m;
		}
	}

}

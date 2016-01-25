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

package org.ujmp.core.importer.sourceselector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

import org.ujmp.core.Matrix;
import org.ujmp.core.importer.source.DefaultMatrixByteArrayImportSource;
import org.ujmp.core.importer.source.DefaultMatrixClipboardImportSource;
import org.ujmp.core.importer.source.DefaultMatrixFileImportSource;
import org.ujmp.core.importer.source.DefaultMatrixReaderImportSource;
import org.ujmp.core.importer.source.DefaultMatrixStreamImportSource;
import org.ujmp.core.importer.source.DefaultMatrixStringImportSource;
import org.ujmp.core.importer.source.DefaultMatrixUrlImportSource;
import org.ujmp.core.importer.source.MatrixByteArrayImportSource;
import org.ujmp.core.importer.source.MatrixClipboardImportSource;
import org.ujmp.core.importer.source.MatrixFileImportSource;
import org.ujmp.core.importer.source.MatrixReaderImportSource;
import org.ujmp.core.importer.source.MatrixStreamImportSource;
import org.ujmp.core.importer.source.MatrixStringImportSource;
import org.ujmp.core.importer.source.MatrixURLImportSource;

public class DefaultMatrixImportSourceSelector extends AbstractMatrixImportSourceSelector {

	public DefaultMatrixImportSourceSelector(Matrix targetMatrix) {
		super(targetMatrix);
	}

	public DefaultMatrixImportSourceSelector() {
	}

	public MatrixClipboardImportSource clipboard() {
		return new DefaultMatrixClipboardImportSource(getTargetMatrix());
	}

	public MatrixFileImportSource file(File file) throws IOException {
		return new DefaultMatrixFileImportSource(getTargetMatrix(), file);
	}

	public MatrixFileImportSource file(String file) throws IOException {
		return new DefaultMatrixFileImportSource(getTargetMatrix(), file);
	}

	public MatrixReaderImportSource reader(Reader reader) throws IOException {
		return new DefaultMatrixReaderImportSource(getTargetMatrix(), reader);
	}

	public MatrixStreamImportSource stream(InputStream stream) throws IOException {
		return new DefaultMatrixStreamImportSource(getTargetMatrix(), stream);
	}

	public MatrixStringImportSource string(String string) {
		return new DefaultMatrixStringImportSource(getTargetMatrix(), string);
	}

	public MatrixURLImportSource url(URL url) throws IOException {
		return new DefaultMatrixUrlImportSource(getTargetMatrix(), url);
	}

	public MatrixByteArrayImportSource byteArray(byte[] bytes) {
		return new DefaultMatrixByteArrayImportSource(getTargetMatrix(), bytes);
	}

}

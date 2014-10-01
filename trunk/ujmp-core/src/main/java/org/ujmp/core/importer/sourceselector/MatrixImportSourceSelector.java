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

package org.ujmp.core.importer.sourceselector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

import org.ujmp.core.importer.source.MatrixByteArrayImportSource;
import org.ujmp.core.importer.source.MatrixClipboardImportSource;
import org.ujmp.core.importer.source.MatrixFileImportSource;
import org.ujmp.core.importer.source.MatrixReaderImportSource;
import org.ujmp.core.importer.source.MatrixStreamImportSource;
import org.ujmp.core.importer.source.MatrixStringImportSource;
import org.ujmp.core.importer.source.MatrixURLImportSource;

public interface MatrixImportSourceSelector {

	public MatrixClipboardImportSource clipboard();

	public MatrixURLImportSource url(URL url) throws IOException;

	public MatrixFileImportSource file(File file) throws IOException;

	public MatrixFileImportSource file(String file) throws IOException;

	public MatrixReaderImportSource reader(Reader reader) throws IOException;

	public MatrixStreamImportSource stream(InputStream stream) throws IOException;

	public MatrixStringImportSource string(String s);

	public MatrixByteArrayImportSource byteArray(byte[] bytes);

}

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

package org.ujmp.core.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.exceptions.MatrixException;

public interface ExportMatrixInterface {

	public void exportToFile(FileFormat format, File file, Object... parameters)
			throws MatrixException, IOException;

	public void exportToFile(FileFormat format, String file, Object... parameters)
			throws MatrixException, IOException;

	public void exportToFile(File file, Object... parameters) throws MatrixException, IOException;

	public void exportToFile(String file, Object... parameters) throws MatrixException, IOException;

	public void exportToStream(FileFormat format, OutputStream stream, Object... parameters)
			throws MatrixException, IOException;

	public void exportToWriter(FileFormat format, Writer writer, Object... parameters)
			throws MatrixException, IOException;

	public void exportToClipboard(FileFormat format, Object... parameters) throws MatrixException,
			IOException;

	public String exportToString(FileFormat format, Object... parameters) throws MatrixException,
			IOException;

}

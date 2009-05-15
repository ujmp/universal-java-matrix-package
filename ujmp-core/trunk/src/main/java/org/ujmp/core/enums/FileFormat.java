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

package org.ujmp.core.enums;

import java.io.File;

/**
 * Import and export formats that are supported.
 */
public enum FileFormat {
	CSV, TXT, M, MAT, GIF, FILE, MDB, R, JPG, HTML, MTX, XLS, SER, GraphML, TEX, WAV, BMP, TIFF, PLT, JPEG, PDF, PNG, XML, AML, ARFF, ATT, LOG, NET, XRFF, STRING, SPARSECSV, RAW, ImapMessages, ImapFolders;

	public static FileFormat guess(File file) {
		String name = file.getName().toLowerCase();
		if (name.endsWith(".pdf")) {
			return FileFormat.PDF;
		}
		if (name.endsWith(".png")) {
			return FileFormat.PNG;
		}
		if (name.endsWith(".gif")) {
			return FileFormat.GIF;
		}
		if (name.endsWith(".jpg")) {
			return FileFormat.JPG;
		}
		return FileFormat.TXT;
	}
}

/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

import org.ujmp.core.util.io.UJMPFileFilter;

/**
 * Import and export formats that are supported.
 */
public enum FileFormat {
	CSV("Comma Separated Files", "csv"), //
	TXT("Text Files", "txt"), //
	M("Matlab Script Files", "m"), //
	MAT("Matlab Data Files", "mat"), //
	GIF("GIF Image Files", "gif"), //
	FILE("Text Files", "*"), //
	MDB("Microsoft Access Files", "mdb"), //
	R("R Files", "r"), //
	RSS("RSS Feed", "rss", "rdf", "xml"), //
	ATOM("Atom Feed", "atom", "xml"), //
	JPG("JPG Image Files", "jpg", "jpeg"), //
	HTML("HTML Files", "html", "htm"), //
	MTX("Matrix Data Format", "mtx"), //
	XLS("Microsoft Excel Files", "xls"), //
	SER("Serialized Data Files", "ser", "obj", "dat"), //
	GRAPHML("GraphML Files", "graphml", "gml"), //
	TEX("Latex Files", "tex"), //
	WAV("Wave Audio Files", "wav"), //
	BMP("BMP Image Files", "bmp"), //
	TIFF("TIFF Image Files", "tif"), //
	PLT("GnuPlot Files", "plt"), //
	PDF("PDF Files", "pdf"), //
	PNG("PNG Images Files", "png"), //
	XML("XML Files", "xml"), //
	AML("AML Files", "aml"), //
	ARFF("ARFF Files", "arf"), //
	ATT("ATT Files", "att"), //
	LOG("Log Files", "log"), //
	NET("Net Files", "net"), //
	STRING("String files", "txt"), //
	SPARSECSV("Sparse CSV Files", "csv"), //
	RAW("Binary Files", "raw", "bin"), //
	ImapMessages("Imap Messages", "imap"), //
	ImapFolders("Imap Folders", "imap");

	private String[] extensions = null;

	private String description = null;

	private javax.swing.filechooser.FileFilter fileFilter = null;

	FileFormat(String description, String... extensions) {
		this.extensions = extensions;
		this.description = description;
		this.fileFilter = new UJMPFileFilter(getDescription(), getExtensions());
	}

	public String[] getExtensions() {
		return extensions;
	}

	public javax.swing.filechooser.FileFilter getFileFilter() {
		return fileFilter;
	}

	public String getDescription() {
		return description;
	}

	public static FileFormat guess(File file) {
		if (file == null) {
			System.out.println();
		}
		String name = file.getName().toLowerCase();
		for (FileFormat f : FileFormat.values()) {
			for (String e : f.getExtensions()) {
				if (name.endsWith(e)) {
					return f;
				}
			}
		}
		return FileFormat.TXT;
	}

}

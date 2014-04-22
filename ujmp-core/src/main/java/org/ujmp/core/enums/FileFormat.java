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

package org.ujmp.core.enums;

import java.io.File;

import org.ujmp.core.util.io.UJMPFileFilter;

/**
 * Import and export formats that are supported.
 */
public enum FileFormat {
	UNKNOWN("unknown", ""), //
	AML("AML Files", "aml"), //
	ARFF("ARFF Files", "arf"), //
	ATOM("Atom Feed", "atom", "xml"), //
	ATT("ATT Files", "att"), //
	AVI("AVI Files", "avi"), //
	BMP("BMP Image Files", "bmp"), //
	C("C Files", "c"), //
	CS("C# Files", "cs"), //
	CSV("Comma Separated Files", "csv"), //
	DIRECTORY("Directory", ""), //
	DLL("Microsoft Windows DLL Files", "dll"), //
	DOC("Microsoft Word Files", "doc"), //
	EXE("Executable Files", "exe"), //
	FILE("Text Files", "*"), //
	GIF("GIF Image Files", "gif"), //
	GRAPHML("GraphML Files", "graphml", "gml"), //
	H("C Header Files", "h"), //
	HEX("Binary Files", "raw", "hex", "bin"), //
	HTML("HTML Files", "html", "htm"), //
	ImapMessages("Imap Messages", "imap"), //
	ImapFolders("Imap Folders", "imap"), //
	JAVA("Java Files", "java"), //
	JAR("Java JAR Files", "jar"), //
	JPG("JPG Image Files", "jpg", "jpeg"), //
	LOG("Log Files", "log"), //
	M("Matlab Script Files", "m"), //
	MAT("Matlab Data Files", "mat"), //
	MDB("Microsoft Access Files", "mdb"), //
	MP3("MP3 Audio Files", "mp3"), //
	MPG("MPG Files", "mpg", "mpeg"), //
	MTX("Matrix Data Format", "mtx"), //
	NET("Net Files", "net"), //
	PDF("PDF Files", "pdf"), //
	PHP("PHP Files", "php"), //
	PLT("GnuPlot Files", "plt"), //
	PNG("PNG Images Files", "png"), //
	PS("PostScript Files", "ps"), //
	R("Matlab R Files", "r"), //
	RAR("RAR Files", "rar"), //
	RSS("RSS Feed", "rss", "rdf", "xml"), //
	SER("Serialized Data Files", "ser", "obj", "dat"), //
	SPARSECSV("Sparse CSV Files", "csv"), //
	SQL("SQL Files", "sql"), //
	TEX("Latex Files", "tex"), //
	TIFF("TIFF Image Files", "tif"), //
	TMP("TMP Files", "tmp", "temp"), //
	TXT("Text Files", "txt"), //
	WAV("Wave Audio Files", "wav"), //
	XLS("Microsoft Excel Files", "xls"), //
	XML("XML Files", "xml"), //
	ZIP("ZIP Files", "zip"); //

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
			return FileFormat.UNKNOWN;
		}
		if (file.isDirectory()) {
			return FileFormat.DIRECTORY;
		}
		String name = file.getName().toLowerCase();
		for (FileFormat f : FileFormat.values()) {
			for (String e : f.getExtensions()) {
				if (name.endsWith("." + e)) {
					return f;
				}
			}
		}
		return FileFormat.UNKNOWN;
	}

	public static boolean isImage(FileFormat fileformat) {
		switch (fileformat) {
		case BMP:
			return true;
		case GIF:
			return true;
		case JPG:
			return true;
		case PNG:
			return true;
		case TIFF:
			return true;
		default:
			return false;
		}
	}

	public static boolean isText(FileFormat fileformat) {
		switch (fileformat) {
		case C:
			return true;
		case CS:
			return true;
		case CSV:
			return true;
		case GRAPHML:
			return true;
		case HTML:
			return true;
		case JAVA:
			return true;
		case LOG:
			return true;
		case M:
			return true;
		case NET:
			return true;
		case PHP:
			return true;
		case PLT:
			return true;
		case R:
			return true;
		case SQL:
			return true;
		case TEX:
			return true;
		case TXT:
			return true;
		case XML:
			return true;
		default:
			return false;
		}
	}

}

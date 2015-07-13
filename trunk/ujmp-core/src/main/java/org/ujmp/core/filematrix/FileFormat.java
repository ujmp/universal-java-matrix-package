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

package org.ujmp.core.filematrix;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.filechooser.FileFilter;

import org.ujmp.core.util.io.UJMPFileFilter;

/**
 * Import and export formats that are supported.
 */
public enum FileFormat {
	UNKNOWN("unknown", ""), //
	AES("AES Crypt File", new byte[] { 0x41, 0x45, 0x53 }, "aes"), //
	AI("Adobe Illustrator File", "ai"), //
	AML("AML Files", "aml"), //
	ARJ("ARJ Compressed File", new byte[] { 0x60, (byte) 0xEA }, "arj"), //
	ARC("FreeArc Compressed File", new byte[] { 0x41, 0x72, 0x43, 0x01 }, "arc"), //
	ARFF("Attribute-Relation File", "arf", "arff"), //
	ATOM("Atom Feed", "atom", "xml"), //
	ATT("ATT File", "att"), //
	AVI("AVI Video File", 8, new byte[] { 0x41, 0x56, 0x49, 0x20, 0x4C, 0x49, 0x53, 0x54 }, "avi"), //
	BMP("BMP Image File", new byte[] { 0x42, 0x4D }, "bmp"), //
	BZ("BZip File", new byte[] { 0x42, 0x5A }, "bz", "bzip", "bz2", "tbz2", "tb2"), //
	C("C File", "c"), //
	CAB("CAB Installer File", new byte[] { 0x4D, 0x53, 0x43, 0x46 }, "cab"), //
	CLASS("Class File", new byte[] { (byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE }, "class"), //
	CS("C# File", "cs"), //
	CSV("Comma Separated File", "csv"), //
	DB("SQLite Database File", new byte[] { 0x53, 0x51, 0x4C, 0x69, 0x74, 0x65, 0x20, 0x66, 0x6F,
			0x72, 0x6D, 0x61, 0x74, 0x20 }, "db"), //
	DIRECTORY("Directory", ""), //
	DLL("Windows Dynamic Library", new byte[] { 0x4D, 0x5A }, "dll"), //
	DOC("Microsoft Word File", new byte[] { (byte) 0xD0, (byte) 0xCF, 0x11, (byte) 0xE0,
			(byte) 0xA1, (byte) 0xB1, 0x1A, (byte) 0xE1 }, "doc"), //
	DOCX("Microsoft Word 2010 File", new byte[] { 0x50, 0x4B, 0x03, 0x04 }, "docx"), //
	DWG("AutoCAD File", new byte[] { 0x41, 0x43, 0x31, 0x30 }, "dwg"), //
	ELF("Unix Executable File", new byte[] { 0x7F, 0x45, 0x4C, 0x46 }, "elf"), //
	EML("Email File", new byte[] { 0x52, 0x65, 0x74, 0x75, 0x72, 0x6E, 0x2D, 0x50, 0x61, 0x74,
			0x68, 0x3A, 0x20 }, "eml"), //
	EPS("EPS File", new byte[] { 0x25, 0x21, 0x50, 0x53, 0x2D, 0x41, 0x64, 0x6F, 0x62, 0x65, 0x2D,
			0x33, 0x2E, 0x30, 0x20, 0x45, 0x50, 0x53, 0x46, 0x2D, 0x33, 0x20, 0x30 }, "eps"), //
	EPUB("Open Publication Structure eBook File", new byte[] { 0x50, 0x4B, 0x03, 0x04, 0x0A, 0x00,
			0x02, 0x00 }, "epub"), //
	EXE("Windows Executable File", new byte[] { 0x4D, 0x5A }, "exe"), //
	FDB("Firebird/Interbase Database File", new byte[] { 0x01, 0x00, 0x39, 0x30 }, "fdb", "gdb"), //
	FIG("Xfig File", new byte[] { 0x23, 0x46, 0x49, 0x47 }, "fig"), //
	FILE("Text File", "*"), //
	FLAC("Free Lossless Audio Codec File", new byte[] { 0x66, 0x4C, 0x61, 0x43, 0x00, 0x00, 0x00,
			0x22 }, "flac"), //
	FLV("Flash Video File", new byte[] { 0x46, 0x4C, 0x56 }, "flv"), //
	GIF("GIF Image File", new byte[] { 0x47, 0x49, 0x46, 0x38 }, "gif"), //
	GRAPHML("GraphML File", "graphml", "gml"), //
	GZ("GZip File", new byte[] { 0x1F, (byte) 0x8B, 0x08 }, "gz", "tgz"), //
	H("C Header File", "h"), //
	HEX("Binary File", "raw", "hex", "bin"), //
	HLP("Help File", new byte[] { 0x3F, 0x5F, 0x03, 0x00 }, "hlp"), //
	HTML("HTML File", "html", "htm"), //
	ICO("Icon File", new byte[] { 0x00, 0x00, 0x01, 0x00 }, "ico"), //
	ImapMessages("Imap Message", "imap"), //
	ImapFolders("Imap Folder", "imap"), //
	ISO("ISO-9660 Disc Image", new byte[] { 0x43, 0x44, 0x30, 0x30, 0x31 }, "iso"), //
	JAVA("Java File", "java"), //
	JAR("Java JAR File", new byte[] { 0x50, 0x4B, 0x03, 0x04, 0x14, 0x00, 0x08, 0x00, 0x08, 0x00 },
			"jar"), //
	JPG("JPEG Image File", new byte[] { (byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0 },
			"jpg", "jpeg", "jfif", "jpe"), //
	JPEG2000("JPEG2000 Image File", new byte[] { 0x00, 0x00, 0x00, 0x0C, 0x6A, 0x50, 0x20, 0x20,
			0x0D, 0x0A }, "jp2"), //
	LOG("Log File", "log"), //
	M("Matlab Script File", "m"), //
	MAT("Matlab Data File", "mat"), //
	MDB("Microsoft Access File", new byte[] { 0x53, 0x74, 0x61, 0x6E, 0x64, 0x61, 0x72, 0x64, 0x20,
			0x4A, 0x65, 0x74 }, "mdb"), //
	MDF("Microsoft SQL Server 2000 Database File", new byte[] { 0x01, 0x0F, 0x00, 0x00 }, "mdf"), //
	MID("MIDI File", new byte[] { 0x4D, 0x54, 0x68, 0x64 }, "mid"), //
	MKV("Matroska Stream File", new byte[] { 0x1A, 0x45, (byte) 0xDF, (byte) 0xA3, (byte) 0x93,
			0x42, (byte) 0x82, (byte) 0x88, 0x6D, 0x61, 0x74, 0x72, 0x6F, 0x73, 0x6B, 0x61 }, "mkv"), //
	MOV("MOV Video File", new byte[] { 0x6D, 0x6F, 0x6F, 0x76 }, "mov"), //
	MP3("MP3 Audio File", new byte[] { 0x49, 0x44, 0x33 }, "mp3"), //
	MP4("MPEG4 Video File", 4, new byte[] { 0x66, 0x74, 0x79, 0x70 }, "mp4", "m4a", "m4v"), //
	MPG("MPEG File", new byte[] { 0x00, 0x00, 0x01, (byte) 0xBA }, "mpg", "mpeg", "vob"), //
	MSG("Outlook Message File", new byte[] { (byte) 0xD0, (byte) 0xCF, 0x11, (byte) 0xE0,
			(byte) 0xA1, (byte) 0xB1, 0x1A, (byte) 0xE1 }, "msg"), //
	MSI("Microsoft Installer File", new byte[] { (byte) 0xD0, (byte) 0xCF, 0x11, (byte) 0xE0,
			(byte) 0xA1, (byte) 0xB1, 0x1A, (byte) 0xE1 }, "msi"), //
	MTX("Matrix Data Format", "mtx"), //
	NET("Net Files", "net"), //
	OGG("Ogg Vorbis Codec Compressed Multimedia File", new byte[] { 0x4C, 0x01 }, "ogg", "oga",
			"ogv", "ogx"), //
	OBJ("Object Code File", new byte[] { 0x4C, 0x01 }, "obj"), //
	PDF("PDF File", new byte[] { 0x25, 0x50, 0x44, 0x46 }, "pdf"), //
	PHP("PHP File", "php"), //
	PLT("GnuPlot File", "plt"), //
	PNG("PNG Image File", new byte[] { (byte) 0x89, 0x50, 0x4E, 0x47 }, "png"), //
	PPM("Portable Pixmap Image File", new byte[] { 0x50, 0x36 }, "ppm"), //
	PPT("Microsoft PowerPoint Document", new byte[] { (byte) 0xD0, (byte) 0xCF, 0x11, (byte) 0xE0,
			(byte) 0xA1, (byte) 0xB1, 0x1A, (byte) 0xE1 }, "ppt"), //
	PPTX("Microsoft PowerPoint 2010 Document", new byte[] { 0x50, 0x4B, 0x03, 0x04 }, "pptx"), //
	PS("PostScript File", new byte[] { 0x25, 0x21 }, "ps"), //
	PSD("Photoshop Image File", new byte[] { 0x38, 0x42, 0x50, 0x53 }, "psd"), //
	PST("Outlook Post Office File", new byte[] { 0x21, 0x42, 0x44, 0x4E, 0x42 }, "pst"), //
	R("Matlab R File", "r"), //
	RAR("RAR Compressed File", new byte[] { 0x52, 0x61, 0x72, 0x21, 0x1A, 0x07 }, "rar"), //
	RPM("RedHat Package Manager File", new byte[] { (byte) 0xED, (byte) 0xAB, (byte) 0xEE,
			(byte) 0xDB }, "rpm"), //
	RSS("RSS Feed", "rss", "rdf", "xml"), //
	RTF("RTF Document File", new byte[] { 0x7B, 0x5C, 0x72, 0x74, 0x66, 0x31 }, "rtf"), //
	SAV("SPSS Data File", new byte[] { 0x24, 0x46, 0x4C, 0x32, 0x40, 0x28, 0x23, 0x29, 0x20, 0x53,
			0x50, 0x53, 0x53, 0x20, 0x44, 0x41, 0x54, 0x41, 0x20, 0x46, 0x49, 0x4C, 0x45 }, "sav"), //
	SER("Serialized Data Files", "ser", "obj", "dat"), //
	SLN("Visual Studio File", new byte[] { 0x4D, 0x69, 0x63, 0x72, 0x6F, 0x73, 0x6F, 0x66, 0x74,
			0x20, 0x56, 0x69, 0x73, 0x75, 0x61, 0x6C, 0x20, 0x53, 0x74, 0x75, 0x64, 0x69, 0x6F,
			0x20, 0x53, 0x6F, 0x6C, 0x75, 0x74, 0x69, 0x6F, 0x6E, 0x20, 0x46, 0x69, 0x6C, 0x65 },
			"sln"), //
	SPARSECSV("Sparse CSV File", "csv"), //
	SQL("SQL File", "sql"), //
	SWF("Flash Shockwave File", new byte[] { 0x46, 0x57, 0x53 }, "swf"), //
	SYS("SYS File", new byte[] { 0x4D, 0x5A }, "sys"), //
	TAR("Tar File", new byte[] { 0x75, 0x73, 0x74, 0x61, 0x72 }, "tar"), //
	TEX("Latex Files", "tex"), //
	TIF("TIF Image File", new byte[] { 0x49, 0x49 }, "tif", "tiff"), //
	TTF("True Type Font File", new byte[] { 0x00, 0x01, 0x00, 0x00, 0x00 }, "ttf"), //
	TXT("Text File", "txt"), //
	VCF("vCard File", new byte[] { 0x42, 0x45, 0x47, 0x49, 0x4E, 0x3A, 0x56, 0x43, 0x41, 0x52,
			0x44, 0x0D, 0x0A }, "vcf"), //
	VMDK("VMWare Disk File", new byte[] { 0x4B, 0x44, 0x4D, 0x56 }, "vmdk"), //
	VSD("Visio Document", new byte[] { (byte) 0xD0, (byte) 0xCF, 0x11, (byte) 0xE0, (byte) 0xA1,
			(byte) 0xB1, 0x1A, (byte) 0xE1 }, "vsd"), //
	WAV("Wave Audio File", 8, new byte[] { 0x57, 0x41, 0x56, 0x45, 0x66, 0x6D, 0x74, 0x20 }, "wav"), //
	WMA("Windows Audio File", new byte[] { 0x30, 0x26, (byte) 0xB2, 0x75, (byte) 0x8E, 0x66,
			(byte) 0xCF }, "wma"), //
	WMF("Windows Meta File", new byte[] { (byte) 0xD7, (byte) 0xCD, (byte) 0xC6, (byte) 0x9A },
			"wmf"), //
	WMV("Windows Video File", new byte[] { 0x30, 0x26, (byte) 0xB2, 0x75, (byte) 0x8E, 0x66,
			(byte) 0xCF }, "wmv"), //
	XCF("Gimp Image File",
			new byte[] { 0x67, 0x69, 0x6d, 0x70, 0x20, 0x78, 0x63, 0x66, 0x20, 0x76 }, "xcf"), //
	XLS("Microsoft Excel File", new byte[] { (byte) 0xD0, (byte) 0xCF, 0x11, (byte) 0xE0,
			(byte) 0xA1, (byte) 0xB1, 0x1A, (byte) 0xE1 }, "xls"), //
	XLSX("Microsoft Excel 2010 File", new byte[] { 0x50, 0x4B, 0x03, 0x04 }, "xlsx"), //
	XML("XML File", new byte[] { '<', 'x', 'm', 'l' }, "xml"), //
	Z("Compress File", new byte[] { 0x1F, (byte) 0x9D }, "z"), //
	Z7("7-Zip File", new byte[] { 0x37, 0x7A, (byte) 0xBC, (byte) 0xAF, 0x27, 0x1C }, "7z"), //
	ZIP("PKZip File", new byte[] { 0x50, 0x4B, 0x03, 0x04 }, "zip"), //
	ZLIB("Zlib File", new byte[] { 0x78, (byte) 0x9C }, "zlib"), //
	ZOO("ZOO Compressed File", new byte[] { 0x5A, 0x4F, 0x4F, 0x20 }, "zoo"); //

	private final String[] extensions;
	private final String description;
	private final byte[] magicBytes;
	private final int offset;

	private static int maxMagicByteLength = 0;

	private static final Object lock = new Object();

	private FileFilter fileFilter = null;

	private FileFormat(String description, byte[] magicBytes, String... extensions) {
		this(description, 0, magicBytes, extensions);
	}

	private FileFormat(String description, String... extensions) {
		this(description, 0, new byte[] {}, extensions);
	}

	private FileFormat(String description, int offset, byte[] magicBytes, String... extensions) {
		this.extensions = extensions;
		this.offset = offset;
		this.magicBytes = magicBytes;
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

	public static FileFormat guess(String filename) {
		filename = filename.toLowerCase();
		for (FileFormat f : FileFormat.values()) {
			for (String e : f.getExtensions()) {
				if (filename.endsWith("." + e)) {
					return f;
				}
			}
		}
		return FileFormat.UNKNOWN;
	}

	public static FileFormat guess(File file) throws IOException {
		if (file == null || !file.isFile() || !file.canRead()) {
			return FileFormat.UNKNOWN;
		}

		FileFormat format = guess(file.getName());
		if (format != FileFormat.UNKNOWN) {
			return format;
		} else {
			final InputStream is = new FileInputStream(file);
			format = guess(is);
			is.close();
			return format;
		}
	}

	public static int getMaxMagicByteLength() {
		if (maxMagicByteLength == 0) {
			synchronized (lock) {
				if (maxMagicByteLength == 0) {
					for (FileFormat f : FileFormat.values()) {
						if (maxMagicByteLength < f.getMagicBytes().length) {
							maxMagicByteLength = f.getMagicBytes().length;
						}
					}
				}
			}
		}
		return maxMagicByteLength;
	}

	private int getOffset() {
		return offset;
	}

	private byte[] getMagicBytes() {
		return magicBytes;
	}

	public static boolean isImage(FileFormat fileformat) {
		switch (fileformat) {
		case BMP:
			return true;
		case GIF:
			return true;
		case JPG:
			return true;
		case JPEG2000:
			return true;
		case ICO:
			return true;
		case PNG:
			return true;
		case TIF:
			return true;
		case XCF:
			return true;
		case PSD:
			return true;
		default:
			return false;
		}
	}

	public static boolean isCompressed(FileFormat fileformat) {
		switch (fileformat) {
		case GZ:
			return true;
		case Z:
			return true;
		case Z7:
			return true;
		case ZIP:
			return true;
		case ZLIB:
			return true;
		case ZOO:
			return true;
		case ARC:
			return true;
		case ARJ:
			return true;
		case RAR:
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

	public static FileFormat guess(final byte[] data) {
		for (FileFormat f : FileFormat.values()) {
			final byte[] magic = f.getMagicBytes();
			if (magic.length == 0) {
				continue;
			}
			final int offset = f.getOffset();
			if (offset + magic.length > data.length) {
				continue;
			} else {
				int len = magic.length;
				boolean match = true;
				for (int i = 0; i < len; i++) {
					if (data[offset + i] != magic[i]) {
						match = false;
					}
				}
				if (match) {
					return f;
				}
			}
		}
		return FileFormat.UNKNOWN;
	}

	public static FileFormat guess(InputStream is) throws IOException {
		final byte[] data = new byte[getMaxMagicByteLength()];
		is.read(data);
		return guess(data);
	}

}

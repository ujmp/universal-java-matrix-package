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

package org.ujmp.core.objectmatrix.impl;

import java.io.File;
import java.io.IOException;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.stringmatrix.impl.FileListMatrix;

public class FileMatrix extends DefaultMapMatrix<String, Object> {
	private static final long serialVersionUID = 7869997158743678080L;

	public static final String FILES = "Files";
	public static final String TEXT = "Text";
	public static final String BYTES = "Bytes";
	public static final String SIZE = "Size";
	public static final String CANREAD = "CanRead";
	public static final String CANWRITE = "CanWrite";
	public static final String ISHIDDEN = "IsHidden";
	public static final String ISFILE = "IsFile";
	public static final String ISDIRECTORY = "IsDirectory";
	public static final String LASTMODIFIED = "LastModified";
	public static final String FILENAME = "FileName";
	public static final String CANEXECUTE = "CanExecute";
	public static final String PATH = "Path";
	public static final String EXTENSION = "Extension";
	public static final String FILEFORMAT = "FileFormat";
	public static final String MD5 = "MD5";
	public static final String AVARAGECOLOR = "AvarageColor";
	public static final String THUMBNAIL = "Thumbnail";
	public static final String IMAGE = "Image";

	private final FileFormat fileformat;

	public FileMatrix(File file, Object... parameters) throws IOException {
		this(null, file, parameters);
	}

	public FileMatrix(FileFormat fileFormat, File file, Object... parameters) throws IOException {
		setLabel(file);
		if (fileFormat == null) {
			this.fileformat = FileFormat.guess(file);
		} else {
			this.fileformat = fileFormat;
		}
		put(PATH, file.getPath());
		put(FILENAME, file.getName());
		put(CANREAD, file.canRead());
		put(CANWRITE, file.canWrite());
		put(ISHIDDEN, file.isHidden());
		put(ISDIRECTORY, file.isDirectory());
		put(ISFILE, file.isFile());
		put(LASTMODIFIED, file.lastModified());
		put(SIZE, file.length());
		put(FILEFORMAT, this.fileformat);
		if (file.isDirectory()) {
			put(FILES, new FileListMatrix(file, parameters));
		} else {
			put(BYTES, Matrix.Factory.linkToFile(FileFormat.HEX, file));
		}
		if (FileFormat.isImage(this.fileformat)) {
			put(AVARAGECOLOR, Matrix.Factory.linkToFile(this.fileformat, file, 1, 1));
			put(THUMBNAIL, Matrix.Factory.linkToFile(this.fileformat, file, 30, 30));
			put(IMAGE, Matrix.Factory.linkToFile(this.fileformat, file));
		}
		if (this.fileformat == FileFormat.UNKNOWN || FileFormat.isText(this.fileformat)) {
			put(TEXT, Matrix.Factory.linkToFile(FileFormat.CSV, file));
		}
	}

}

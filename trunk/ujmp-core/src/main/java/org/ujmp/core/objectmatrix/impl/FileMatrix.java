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

package org.ujmp.core.objectmatrix.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.collections.LazyMap;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.stringmatrix.stub.AbstractDenseStringMatrix2D;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.io.FileUtil;
import org.ujmp.core.util.io.IntelligentFileReader;
import org.ujmp.core.util.io.IntelligentFileWriter;

public class FileMatrix extends AbstractMapMatrix<String, Object> {
	private static final long serialVersionUID = 7869997158743678080L;

	public static final String CONTENT = "Content";

	public static final String TEXT = "Text";

	public static final String ID = "Id";

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

	public static final String ANNOTATION = "Annotation";

	private FileMap map = null;

	private final FileFormat fileFormat;

	private final File file;

	private final Object[] parameters;

	public FileMatrix(File file, Object... parameters) throws IOException {
		this(null, file, parameters);
	}

	public FileMatrix(FileFormat fileFormat, File file, Object... parameters) throws IOException {
		this.fileFormat = fileFormat;
		this.file = file;
		this.parameters = parameters;
		setLabelObject(file);
	}

	public Map<String, Object> getMap() {
		if (map == null) {
			try {
				map = new FileMap(fileFormat, file, parameters);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return map;
	}

	class FileMap extends LazyMap<String, Object> {
		private static final long serialVersionUID = -4946966403241068247L;

		private final File finalFile;

		private final FileFormat fileformat;

		private final Object[] parameters;

		public FileMap(File file, Object... paramegters) throws IOException {
			this(null, file, paramegters);
		}

		public FileMap(FileFormat fileFormat, File file, Object... paramegters) throws IOException {
			if (fileFormat == null) {
				this.fileformat = FileFormat.guess(file);
			} else {
				this.fileformat = fileFormat;
			}
			this.parameters = paramegters;
			this.finalFile = file;
			put(ID, file.getAbsolutePath());
			put(PATH, file.getPath());
			put(FILENAME, file.getName());
			// String[] components = file.getName().split("\\.");
			// if (components != null && components.length > 1) {
			// map.put(EXTENSION, components[components.length - 1]);
			// } else {
			// map.put(EXTENSION, null);
			// }
			put(CANREAD, file.canRead());
			put(CANWRITE, file.canWrite());
			put(ISHIDDEN, file.isHidden());
			put(ISDIRECTORY, file.isDirectory());
			put(ISFILE, file.isFile());
			put(LASTMODIFIED, file.lastModified());
			put(SIZE, file.length());
			put(FILEFORMAT, this.fileformat);

			if (fileformat == null) {
				put(CONTENT, null);
			} else {
				Callable<Matrix> c = new Callable<Matrix>() {
					public Matrix call() throws Exception {
						return MatrixFactory.linkToFile(fileformat, finalFile, parameters);
					}
				};
				put(CONTENT, c);
			}
			Callable<Object> md5 = new Callable<Object>() {
				public Object call() throws Exception {
					try {
						if (finalFile != null && !finalFile.isDirectory() && finalFile.canRead()) {
							return MathUtil.md5(finalFile);
						} else {
							return 0;
						}
					} catch (Throwable t) {
						return Double.NaN;
					}
				}
			};
			put(MD5, md5);
			Callable<Matrix> bytes = new Callable<Matrix>() {
				public Matrix call() throws Exception {
					return MatrixFactory.linkToFile(FileFormat.RAW, finalFile, parameters);
				}
			};
			put(BYTES, bytes);
			Callable<Matrix> annotation = new Callable<Matrix>() {
				public Matrix call() throws Exception {
					return new AnnotationMatrix(FileUtil.appendExtension(finalFile, ".xml"));
				}
			};
			put(ANNOTATION, annotation);

		}

		public File getFinalFile() {
			return finalFile;
		}

	}

	public MapMatrix<String, Object> copy() {
		try {
			MapMatrix<String, Object> ret;
			ret = new FileMatrix(map.getFinalFile());
			return ret;
		} catch (IOException e) {
			throw new MatrixException(e);
		}
	}

	class AnnotationMatrix extends AbstractDenseStringMatrix2D {
		private static final long serialVersionUID = 1978026473405074699L;

		private final File finalFile;

		public AnnotationMatrix(File file) {
			this.finalFile = file;
		}

		public String getString(long row, long column) {
			return IntelligentFileReader.load(finalFile);
		}

		public void setString(String value, long row, long column) {
			try {
				IntelligentFileWriter.save(finalFile, value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public long[] getSize() {
			return new long[] { 1, 1 };
		}

	}
}

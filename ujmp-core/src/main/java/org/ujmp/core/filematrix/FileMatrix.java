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

package org.ujmp.core.filematrix;

import java.io.File;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Zeros;
import org.ujmp.core.objectmatrix.stub.AbstractObjectMatrix;

public class FileMatrix extends AbstractObjectMatrix implements FileOrDirectoryMatrix {
	private static final long serialVersionUID = -4912495890644097086L;

	private final File file;

	private Matrix matrix = null;

	public FileMatrix(String filename) {
		this(new File(filename));
	}

	public FileMatrix(File file) {
		super(0, 0);
		this.file = file;
		if (file == null) {
			setLabel("/");
		} else {
			setLabel(file.getName());
			setMetaData(PATH, file.getPath());
			setMetaData(FILENAME, file.getName());
			setMetaData(CANEXECUTE, file.canExecute());
			setMetaData(CANREAD, file.canRead());
			setMetaData(CANWRITE, file.canWrite());
			setMetaData(ISHIDDEN, file.isHidden());
			setMetaData(ISDIRECTORY, file.isDirectory());
			setMetaData(ISFILE, file.isFile());
			setMetaData(LASTMODIFIED, file.lastModified());
			setMetaData(SIZE, file.length());

		}
	}

	private void loadContent() {
		if (matrix == null) {
			synchronized (this) {
				if (matrix == null) {
					try {
						FileFormat format = FileFormat.guess(file);
						setMetaData(FILEFORMAT, format);
						switch (format) {
						case BMP:
						case GIF:
						case JPG:
						case JPEG2000:
						case PNG:
						case TIF:
							matrix = Matrix.Factory.linkToImage(file);
							break;
						case DB:
							matrix = Matrix.Factory.linkToJDBC(file);
							break;
						case ZIP:
							matrix = Matrix.Factory.linkToZipFile(file);
							break;
						case TXT:
						case CSV:
							matrix = Matrix.Factory.linkTo().file(file).asDenseCSV();
							break;
						default:
							matrix = Matrix.Factory.emptyMatrix();
							break;
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
	}

	public boolean containsCoordinates(long... coordinates) {
		loadContent();
		return matrix.containsCoordinates(coordinates);
	}

	public boolean isSparse() {
		loadContent();
		return matrix.isSparse();
	}

	public long[] getSize() {
		loadContent();
		size = matrix.getSize();
		return size;
	}

	public Object getObject(long... coordinates) {
		loadContent();
		return matrix.getAsObject(coordinates);
	}

	public Iterable<long[]> availableCoordinates() {
		loadContent();
		return matrix.availableCoordinates();
	}

	public final void clear() {
		matrix.clear();
	}

}

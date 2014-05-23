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

import org.ujmp.core.listmatrix.AbstractListMatrix;

public class DirectoryMatrix extends AbstractListMatrix<FileOrDirectoryMatrix> implements
		FileOrDirectoryMatrix {
	private static final long serialVersionUID = -4912495890644097086L;

	private final File path;

	public DirectoryMatrix() {
		this((File) null);
	}

	public DirectoryMatrix(String path) {
		this(new File(path));
	}

	public DirectoryMatrix(File path) {
		this.path = path;
		if (path == null) {
			setLabel("/");
		} else {
			if (path.getParent() == null) {
				setLabel(path.getAbsolutePath());
			} else {
				setLabel(path.getName());
			}
			setMetaData(PATH, path.getPath());
			setMetaData(FILENAME, path.getName());
			setMetaData(CANEXECUTE, path.canExecute());
			setMetaData(CANREAD, path.canRead());
			setMetaData(CANWRITE, path.canWrite());
			setMetaData(ISHIDDEN, path.isHidden());
			setMetaData(ISDIRECTORY, path.isDirectory());
			setMetaData(ISFILE, path.isFile());
			setMetaData(LASTMODIFIED, path.lastModified());
			setMetaData(SIZE, path.length());
		}
	}

	@Override
	public FileOrDirectoryMatrix get(int index) {
		File[] files = null;
		if (path == null) {
			files = File.listRoots();
		} else {
			files = path.listFiles();
		}
		if (files[index].isFile()) {
			return new FileMatrix(files[index]);
		} else {
			return new DirectoryMatrix(files[index]);
		}
	}

	@Override
	public boolean addToList(FileOrDirectoryMatrix t) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addToList(int index, FileOrDirectoryMatrix element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DirectoryMatrix removeFromList(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeFromList(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DirectoryMatrix setToList(int index, FileOrDirectoryMatrix element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearList() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		if (path == null) {
			return File.listRoots().length;
		} else {
			return path.listFiles().length;
		}
	}
}

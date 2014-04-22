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

package org.ujmp.core.util.matrices;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.ujmp.core.DenseMatrix2D;
import org.ujmp.core.matrix.factory.DenseMatrix2DFactory;
import org.ujmp.core.setmatrix.AbstractSetMatrix;

public class DirectoryMatrix extends AbstractSetMatrix<FileOrDirectoryMatrix> implements
		FileOrDirectoryMatrix {
	private static final long serialVersionUID = 3355489501094215446L;

	private final File directory;
	private Set<FileOrDirectoryMatrix> fileSet;

	public DirectoryMatrix(File directory) {
		if (!directory.isDirectory()) {
			throw new RuntimeException(directory + " is not a directory");
		}
		this.directory = directory;
		if (directory.getName().isEmpty()) {
			setLabel(directory.getPath());
		} else {
			setLabel(directory.getName());
		}
	}

	@Override
	public Set<FileOrDirectoryMatrix> getSet() {
		if (fileSet == null) {
			synchronized (this) {
				if (fileSet == null) {
					fileSet = new HashSet<FileOrDirectoryMatrix>();
					File[] files = null;
					if (directory.canRead()) {
						files = directory.listFiles();
						if (files != null) {
							for (File file : files) {
								if (file.isDirectory()) {
									fileSet.add(new DirectoryMatrix(file));
								} else if (file.isFile()) {
									fileSet.add(new FileMatrix(file));
								}
							}
						}
					}
				}
			}
		}
		return fileSet;
	}

	public DenseMatrix2DFactory<? extends DenseMatrix2D> getFactory() {
		throw new RuntimeException("not implemented");
	}
}

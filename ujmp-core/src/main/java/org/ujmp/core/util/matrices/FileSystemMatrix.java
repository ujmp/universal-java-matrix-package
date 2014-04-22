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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ujmp.core.DenseMatrix2D;
import org.ujmp.core.matrix.factory.DenseMatrix2DFactory;
import org.ujmp.core.setmatrix.AbstractSetMatrix;

public class FileSystemMatrix extends AbstractSetMatrix<DirectoryMatrix> {
	private static final long serialVersionUID = 7296244867263243814L;

	private Set<DirectoryMatrix> directoryMatrixSet = null;

	public FileSystemMatrix() {
		setLabel("File System");
	}

	@Override
	public Set<DirectoryMatrix> getSet() {
		if (directoryMatrixSet == null) {
			synchronized (this) {
				if (directoryMatrixSet == null) {
					directoryMatrixSet = new HashSet<DirectoryMatrix>();
					List<File> directoryList = Arrays.asList(File.listRoots());
					for (File directory : directoryList) {
						try {
							directoryMatrixSet.add(new DirectoryMatrix(directory));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return directoryMatrixSet;
	}

	public DenseMatrix2DFactory<? extends DenseMatrix2D> getFactory() {
		throw new RuntimeException("not implemented");
	}

}

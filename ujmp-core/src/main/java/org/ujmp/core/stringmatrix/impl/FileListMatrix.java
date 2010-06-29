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

package org.ujmp.core.stringmatrix.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ujmp.core.Matrix;
import org.ujmp.core.listmatrix.AbstractListMatrix;
import org.ujmp.core.objectmatrix.impl.FileMatrix;

public class FileListMatrix extends AbstractListMatrix<Matrix> {
	private static final long serialVersionUID = -2627484975560893624L;

	private final List<Matrix> list = new ArrayList<Matrix>();

	public FileListMatrix(String path) throws IOException {
		this(new File(path));
	}

	public FileListMatrix(File path) throws IOException {
		File[] files = path.listFiles();
		for (File f : files) {
			list.add(new FileMatrix(f));
		}
	}

	@Override
	public List<Matrix> getList() {
		return list;
	}

	// public void setString(String value, long row, long column) {
	// if (column == 0 && row < files.size()) {
	// File source = files.get((int) row);
	// File target = new File(source.getParent() + File.separator + value);
	// source.renameTo(target);
	// files.set((int) row, target);
	// }
	// }

}

/*
 * Copyright (C) 2008-2009 by Holger Arndt
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.ujmp.core.stringmatrix.stub.AbstractDenseStringMatrix2D;

public class FileListMatrix extends AbstractDenseStringMatrix2D {
	private static final long serialVersionUID = -2627484975560893624L;

	private List<File> files = null;

	public FileListMatrix(String path) {
		this(new File(path));
	}

	public FileListMatrix(File path) {
		this.files = Arrays.asList(path.listFiles());
		Collections.sort(files);
		setMatrixAnnotation(path);
	}

	public long[] getSize() {
		return new long[] { files.size(), 2 };
	}

	public String getString(long row, long column) {
		switch ((int) column) {
		case 0:
			File f = files.get((int) row);
			return f.getName();
		case 1:
			return "" + files.get((int) row).length();
		}
		return null;
	}

	public void setString(String value, long row, long column) {
		if (column == 0 && row < files.size()) {
			File source = files.get((int) row);
			File target = new File(source.getParent() + File.separator + value);
			source.renameTo(target);
			files.set((int) row, target);
		}
	}

}

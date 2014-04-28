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

import org.ujmp.core.mapmatrix.DefaultMapMatrix;

public class FileMatrix extends DefaultMapMatrix<String, Object> implements FileOrDirectoryMatrix {
	private static final long serialVersionUID = 6095202793321378958L;

	private final File file;

	public FileMatrix(File file) {
		this.file = file;
		setLabel(file.getName());
		put("CanExecute", file.canExecute());
		put("CanRead", file.canRead());
		put("CanWrite", file.canWrite());
		put("AbsolutePath", file.getAbsolutePath());
		put("Name", file.getName());
		put("Parent", file.getParent());
		put("IsHidden", file.isHidden());
		put("LastModified", file.lastModified());
		put("Length", file.length());
		put("TextContent", new TextContentMatrix(file));
	}

	public boolean isReadOnly() {
		return !file.canWrite();
	}

}

/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

import org.ujmp.core.collections.AbstractMapMatrixList;
import org.ujmp.core.listmatrix.AbstractListMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.objectmatrix.impl.FileMatrix;

public class FileListMatrix extends AbstractMapMatrixList<String, Object> {
	private static final long serialVersionUID = -4912495890644097086L;

	private PrivateFileListMatrix list = null;

	private final File path;

	public FileListMatrix(Object... parameters) throws IOException {
		this((File) null, parameters);
	}

	public FileListMatrix(String path, Object... parameters) {
		this(new File(path), parameters);
	}

	public FileListMatrix(File path, Object... parameters) {
		this.path = path;
		if (path == null) {
			setLabelObject("/");
		} else {
			setLabelObject(path);
		}
	}

	@Override
	public PrivateFileListMatrix getList() {
		if (list == null) {
			list = new PrivateFileListMatrix(path);
		}
		return list;
	}

	class PrivateFileListMatrix extends AbstractListMatrix<MapMatrix<String, Object>> {
		private static final long serialVersionUID = -2627484975560893624L;

		private final File path;

		private List<MapMatrix<String, Object>> list = null;

		public PrivateFileListMatrix(File path) {
			this.path = path;
			if (path == null) {
				setLabelObject("/");
			} else {
				setLabelObject(path);
			}
		}

		@Override
		public List<MapMatrix<String, Object>> getList() {
			if (list == null) {
				try {
					list = new ArrayList<MapMatrix<String, Object>>();
					File[] files = null;
					if (path == null) {
						files = File.listRoots();
					} else {
						files = path.listFiles();
					}
					if (files != null) {
						for (File f : files) {
							list.add(new FileMatrix(f));
						}
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			return list;
		}

	}

}

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
import java.util.HashMap;
import java.util.Map;

import org.ujmp.core.mapmatrix.AbstractMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.objectmatrix.DenseObjectMatrix2D;
import org.ujmp.core.objectmatrix.factory.DenseObjectMatrix2DFactory;

public class FileMatrix extends AbstractMapMatrix<String, Object> implements FileOrDirectoryMatrix {
	private static final long serialVersionUID = 6095202793321378958L;

	private final File file;
	private Map<String, Object> map = null;

	public FileMatrix(File file) {
		this.file = file;
		setLabel(file.getName());
	}

	@Override
	public Map<String, Object> getMap() {
		if (map == null) {
			synchronized (this) {
				if (map == null) {
					map = new HashMap<String, Object>();
					map.put("CanExecute", file.canExecute());
					map.put("CanRead", file.canRead());
					map.put("CanWrite", file.canWrite());
					map.put("AbsolutePath", file.getAbsolutePath());
					map.put("Name", file.getName());
					map.put("Parent", file.getParent());
					map.put("IsHidden", file.isHidden());
					map.put("LastModified", file.lastModified());
					map.put("Length", file.length());
					map.put("TextContent", new TextContentMatrix(file));
				}
			}
		}
		return map;
	}

	public boolean isReadOnly() {
		return !file.canWrite();
	}

	@Override
	public MapMatrix<String, Object> clone() {
		return null;
	}

	public DenseObjectMatrix2DFactory<? extends DenseObjectMatrix2D> getFactory() {
		throw new RuntimeException("not implemented");
	}

}

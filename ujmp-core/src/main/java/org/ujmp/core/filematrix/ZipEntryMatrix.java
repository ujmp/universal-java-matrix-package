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

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.ujmp.core.Matrix;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;

public class ZipEntryMatrix extends AbstractMapMatrix<String, Matrix> implements
		FileOrDirectoryMatrix {
	private static final long serialVersionUID = 7888827379525961742L;

	private final ZipFile zipFile;
	private final ZipEntry zipEntry;

	private final Map<String, Matrix> map = new TreeMap<String, Matrix>();

	public ZipEntryMatrix(ZipFile zipFile, ZipEntry zipEntry) {
		this.zipFile = zipFile;
		this.zipEntry = zipEntry;
		setLabel(zipEntry.getName());
		setMetaData("CompressedSize", zipEntry.getCompressedSize());
		setMetaData("CRC", zipEntry.getCrc());
		setMetaData("Method", zipEntry.getMethod());
		setMetaData("Size", zipEntry.getSize());
		setMetaData("Time", zipEntry.getTime());
		setMetaData("IsDirectory", zipEntry.isDirectory());
		setMetaData("Comment", zipEntry.getComment());
	}

	public Matrix get(Object key) {
		ensureData();
		return map.get(key);
	}

	private void ensureData() {
		if (map.isEmpty()) {
			synchronized (map) {
				if (map.isEmpty()) {
					try {
						InputStream is = zipFile.getInputStream(zipEntry);
						if (!zipEntry.isDirectory()) {
							FileFormat format = FileFormat.guess(is);
							map.put(FILEFORMAT, Matrix.Factory.linkToValue(format));
						}
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
	}

	public Set<String> keySet() {
		ensureData();
		return map.keySet();
	}

	@Override
	protected void clearMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Matrix removeFromMap(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Matrix putIntoMap(String key, Matrix value) {
		throw new UnsupportedOperationException();
	}

	public int size() {
		ensureData();
		return map.size();
	}
}

/*
 * Copyright (C) 2008-2015 by Holger Arndt
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
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.ujmp.core.listmatrix.AbstractListMatrix;

public class ZipFileMatrix extends AbstractListMatrix<ZipEntryMatrix> implements
		FileOrDirectoryMatrix {
	private static final long serialVersionUID = -6214617583866869896L;

	private final ZipFile zipFile;
	private final List<ZipEntryMatrix> list = new ArrayList<ZipEntryMatrix>();

	public ZipFileMatrix(File file) throws ZipException, IOException {
		// must be this charset, otherwise a strange exception will occur
		this.zipFile = new ZipFile(file, Charset.forName("Cp437"));
		setLabel(zipFile.getName());
		setMetaData("Comment", zipFile.getComment());
	}

	@Override
	public ZipEntryMatrix get(int index) {
		ensureData();
		return list.get(index);
	}

	private void ensureData() {
		if (list.isEmpty()) {
			synchronized (list) {
				if (list.isEmpty()) {
					Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
					while (enumeration.hasMoreElements()) {
						ZipEntry zipEntry = enumeration.nextElement();
						list.add(new ZipEntryMatrix(zipFile, zipEntry));
					}
				}
			}
		}
	}

	@Override
	public boolean addToList(ZipEntryMatrix t) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addToList(int index, ZipEntryMatrix element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ZipEntryMatrix removeFromList(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeFromList(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ZipEntryMatrix setToList(int index, ZipEntryMatrix element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearList() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		ensureData();
		return list.size();
	}
}

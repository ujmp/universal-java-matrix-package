/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.ujmp.lucene;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.util.Map;

import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.objectmatrix.AbstractMapToSparseMatrixWrapper;

public class LuceneSparseObjectMatrix extends AbstractMapToSparseMatrixWrapper
		implements Flushable, Closeable, Erasable {
	private static final long serialVersionUID = -5164282058414257917L;

	private LuceneMap<Coordinates, Object> map = null;

	public LuceneSparseObjectMatrix(long... size) {
		super(size);
	}

	public LuceneSparseObjectMatrix(Matrix matrix) {
		super(matrix);
	}

	@Override
	public Map<Coordinates, Object> getMap() {
		if (map == null) {
			try {
				map = new LuceneMap<Coordinates, Object>();
			} catch (Exception e) {
				throw new MatrixException("could not create map", e);
			}
		}
		return map;
	}

	@Override
	public void setMap(Map<Coordinates, Object> map) {
		throw new MatrixException("cannot exchange map");
	}

	@Override
	public void flush() throws IOException {
		if (map != null) {
			map.flush();
		}
	}

	@Override
	public void close() throws IOException {
		if (map != null) {
			map.close();
		}
	}

	@Override
	public void erase() throws IOException {
		if (map != null) {
			map.close();
			map.erase();
		}
	}

}

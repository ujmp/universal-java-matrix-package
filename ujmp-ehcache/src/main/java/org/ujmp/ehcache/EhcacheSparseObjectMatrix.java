/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

package org.ujmp.ehcache;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.objectmatrix.stub.AbstractMapToSparseMatrixWrapper;

public class EhcacheSparseObjectMatrix extends AbstractMapToSparseMatrixWrapper
		implements Flushable, Closeable, Erasable {
	private static final long serialVersionUID = -7743149828558906127L;

	public EhcacheSparseObjectMatrix(Matrix m) throws MatrixException,
			IOException {
		super(new EhcacheMap<Coordinates, Object>(), m);
	}

	public EhcacheSparseObjectMatrix(long... size) throws MatrixException,
			IOException {
		super(new EhcacheMap<Coordinates, Object>(), size);
	}

	public void erase() throws IOException {
		((Erasable) getMap()).erase();
	}

	public void flush() throws IOException {
		((Flushable) getMap()).flush();
	}

	public void close() throws IOException {
		((Closeable) getMap()).close();
	}

}

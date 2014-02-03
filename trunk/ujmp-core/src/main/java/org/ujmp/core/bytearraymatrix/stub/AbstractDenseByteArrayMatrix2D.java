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

package org.ujmp.core.bytearraymatrix.stub;

import org.ujmp.core.Matrix;
import org.ujmp.core.bytearraymatrix.DenseByteArrayMatrix2D;
import org.ujmp.core.objectmatrix.DenseObjectMatrix2D;
import org.ujmp.core.objectmatrix.factory.DenseObjectMatrix2DFactory;
import org.ujmp.core.util.CoordinateIterator2D;
import org.ujmp.core.util.VerifyUtil;

public abstract class AbstractDenseByteArrayMatrix2D extends AbstractDenseByteArrayMatrix implements
		DenseByteArrayMatrix2D {
	private static final long serialVersionUID = 1145951193934208497L;

	public AbstractDenseByteArrayMatrix2D() {
		super();
	}

	public AbstractDenseByteArrayMatrix2D(Matrix m) {
		super(m);
	}

	public AbstractDenseByteArrayMatrix2D(long... size) {
		super(size);
		VerifyUtil.assert2D(size);
	}

	public final Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	public final byte[] getByteArray(long... coordinates) {
		return getByteArray(coordinates[ROW], coordinates[COLUMN]);
	}

	public final void setByteArray(byte[] value, long... coordinates) {
		setByteArray(value, coordinates[ROW], coordinates[COLUMN]);
	}

	public final byte[] getObject(long row, long column) {
		return getByteArray(row, column);
	}

	public final byte[] getObject(int row, int column) {
		return getByteArray(row, column);
	}

	public final void setObject(byte[] value, long row, long column) {
		setByteArray(value, row, column);
	}

	public final void setObject(byte[] value, int row, int column) {
		setByteArray(value, row, column);
	}

	public DenseObjectMatrix2DFactory<? extends DenseObjectMatrix2D> getFactory() {
		return DenseObjectMatrix2D.Factory;
	}

	public final int getDimensionCount() {
		return 2;
	}

}

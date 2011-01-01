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

package org.ujmp.core.datematrix.stub;

import java.util.Date;

import org.ujmp.core.Matrix;
import org.ujmp.core.datematrix.DenseDateMatrix2D;
import org.ujmp.core.objectmatrix.DenseObjectMatrix2D;
import org.ujmp.core.objectmatrix.factory.DenseObjectMatrix2DFactory;
import org.ujmp.core.util.CoordinateIterator2D;
import org.ujmp.core.util.VerifyUtil;

public abstract class AbstractDenseDateMatrix2D extends AbstractDenseDateMatrix implements
		DenseDateMatrix2D {
	private static final long serialVersionUID = -2706051207687879249L;

	public AbstractDenseDateMatrix2D() {
		super();
	}

	public AbstractDenseDateMatrix2D(Matrix m) {
		super(m);
	}

	public AbstractDenseDateMatrix2D(long... size) {
		super(size);
		VerifyUtil.assert2D(size);
	}

	public final Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	public final Date getDate(long... coordinates) {
		return getDate(coordinates[ROW], coordinates[COLUMN]);
	}

	public final void setDate(Date value, long... coordinates) {
		setDate(value, coordinates[ROW], coordinates[COLUMN]);
	}

	public Date getObject(long row, long column) {
		return getDate(row, column);
	}

	public Date getObject(int row, int column) {
		return getDate(row, column);
	}

	public void setObject(Date value, long row, long column) {
		setDate(value, row, column);
	}

	public void setObject(Date value, int row, int column) {
		setDate(value, row, column);
	}

	public DenseObjectMatrix2DFactory getFactory() {
		return DenseObjectMatrix2D.factory;
	}

	public final int getDimensionCount() {
		return 2;
	}

}

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

package org.ujmp.core.doublematrix.stub;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.DivideScalar;
import org.ujmp.core.calculation.MinusMatrix;
import org.ujmp.core.calculation.MinusScalar;
import org.ujmp.core.calculation.Mtimes;
import org.ujmp.core.calculation.PlusMatrix;
import org.ujmp.core.calculation.PlusScalar;
import org.ujmp.core.calculation.TimesMatrix;
import org.ujmp.core.calculation.TimesScalar;
import org.ujmp.core.calculation.Transpose;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.factory.DenseDoubleMatrix2DFactory;
import org.ujmp.core.util.CoordinateIterator2D;
import org.ujmp.core.util.VerifyUtil;

public abstract class AbstractDenseDoubleMatrix2D extends AbstractDoubleMatrix2D implements
		DenseDoubleMatrix2D {
	private static final long serialVersionUID = 4518790844453035022L;

	public AbstractDenseDoubleMatrix2D() {
		super();
	}

	public AbstractDenseDoubleMatrix2D(Matrix m) {
		super(m);
	}

	public AbstractDenseDoubleMatrix2D(long... size) {
		super(size);
		VerifyUtil.assert2D(size);
	}

	public final Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	public final Double getObject(long row, long column) {
		return getDouble(row, column);
	}

	public final void setObject(Double o, long row, long column) {
		setDouble(o, row, column);
	}

	public final Double getObject(int row, int column) {
		return getDouble(row, column);
	}

	public final void setObject(Double o, int row, int column) {
		setDouble(o, row, column);
	}

	public double getAsDouble(long row, long column) {
		return getDouble(row, column);
	}

	public double getAsDouble(int row, int column) {
		return getDouble(row, column);
	}

	public void setAsDouble(double value, int row, int column) {
		setDouble(value, row, column);
	}

	public void setAsDouble(double value, long row, long column) {
		setDouble(value, row, column);
	}

	public Matrix mtimes(Matrix m2) {
		if (m2 instanceof DenseDoubleMatrix2D) {
			final DenseDoubleMatrix2D result = DenseDoubleMatrix2D.Factory.zeros(getRowCount(),
					m2.getColumnCount());
			Mtimes.DENSEDOUBLEMATRIX2D.calc(this, (DenseDoubleMatrix2D) m2, result);
			return result;
		} else {
			return super.mtimes(m2);
		}
	}

	public Matrix times(Matrix m2) {
		if (m2 instanceof DenseDoubleMatrix2D) {
			final DenseDoubleMatrix2D result = DenseDoubleMatrix2D.Factory.zeros(getRowCount(),
					getColumnCount());
			TimesMatrix.DENSEDOUBLEMATRIX2D.calc(this, (DenseDoubleMatrix2D) m2, result);
			return result;
		} else {
			return super.times(m2);
		}
	}

	public Matrix divide(Matrix m2) {
		if (m2 instanceof DenseDoubleMatrix2D) {
			final DenseDoubleMatrix2D result = DenseDoubleMatrix2D.Factory.zeros(getRowCount(),
					getColumnCount());
			DenseDoubleMatrix2D.divideMatrix.calc(this, (DenseDoubleMatrix2D) m2, result);
			return result;
		} else {
			return super.divide(m2);
		}
	}

	public Matrix plus(Matrix m2) {
		if (m2 instanceof DenseDoubleMatrix2D) {
			final DenseDoubleMatrix2D result = DenseDoubleMatrix2D.Factory.zeros(getRowCount(),
					getColumnCount());
			PlusMatrix.DENSEDOUBLEMATRIX2D.calc(this, (DenseDoubleMatrix2D) m2, result);
			return result;
		} else {
			return super.plus(m2);
		}
	}

	public Matrix minus(Matrix m2) {
		if (m2 instanceof DenseDoubleMatrix2D) {
			final DenseDoubleMatrix2D result = DenseDoubleMatrix2D.Factory.zeros(getRowCount(),
					getColumnCount());
			MinusMatrix.DENSEDOUBLEMATRIX2D.calc(this, (DenseDoubleMatrix2D) m2, result);
			return result;
		} else {
			return super.minus(m2);
		}
	}

	public Matrix minus(double v) {
		final DenseDoubleMatrix2D result = DenseDoubleMatrix2D.Factory.zeros(getRowCount(),
				getColumnCount());
		MinusScalar.DENSEDOUBLEMATRIX2D.calc(this, v, result);
		return result;
	}

	public Matrix plus(double v) {
		final DenseDoubleMatrix2D result = DenseDoubleMatrix2D.Factory.zeros(getRowCount(),
				getColumnCount());
		PlusScalar.DENSEDOUBLEMATRIX2D.calc(this, v, result);
		return result;
	}

	public Matrix times(double v) {
		final DenseDoubleMatrix2D result = DenseDoubleMatrix2D.Factory.zeros(getRowCount(),
				getColumnCount());
		TimesScalar.DENSEDOUBLEMATRIX2D.calc(this, v, result);
		return result;
	}

	public Matrix divide(double v) {
		final DenseDoubleMatrix2D result = DenseDoubleMatrix2D.Factory.zeros(getRowCount(),
				getColumnCount());
		DivideScalar.DENSEDOUBLEMATRIX2D.calc(this, v, result);
		return result;
	}

	public Matrix transpose() {
		final DenseDoubleMatrix2D result = DenseDoubleMatrix2D.Factory.zeros(getColumnCount(),
				getRowCount());
		Transpose.DENSEDOUBLEMATRIX2D.calc(this, result);
		return result;
	}

	public final int getDimensionCount() {
		return 2;
	}

	public final boolean contains(long... coordinates) {
		return Coordinates.isSmallerThan(coordinates, getSize());
	}

	public DenseDoubleMatrix2DFactory<? extends DenseDoubleMatrix2D> getFactory() {
		return DenseDoubleMatrix2D.Factory;
	}
}

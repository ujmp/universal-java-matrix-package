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

package org.ujmp.commonsmath;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.util.MathUtil;

public class CommonsMathArrayDenseDoubleMatrix2D extends AbstractCommonsMathDenseDoubleMatrix2D {
	private static final long serialVersionUID = -7738250596107670752L;

	public static final CommonsMathArrayDenseDoubleMatrix2DFactory Factory = new CommonsMathArrayDenseDoubleMatrix2DFactory();

	public CommonsMathArrayDenseDoubleMatrix2D(long... size) {
		super(new Array2DRowRealMatrix(MathUtil.longToInt(size[ROW]), MathUtil.longToInt(size[COLUMN])));
	}

	public CommonsMathArrayDenseDoubleMatrix2D(org.ujmp.core.Matrix source) {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setDouble(source.getAsDouble(c), c);
		}
		Annotation a = source.getAnnotation();
		if (a != null) {
			setAnnotation(a.clone());
		}
	}

	public CommonsMathArrayDenseDoubleMatrix2D(Array2DRowRealMatrix matrix) {
		super(matrix);
	}

	public Matrix chol() {
		return super.chol();
	}

	public Matrix inv() {
		return super.inv();
	}

	public Matrix transpose() {
		return super.transpose();
	}

	public Matrix mtimes(Matrix m) {
		return super.mtimes(m);
	}

	public Matrix plus(Matrix m) {
		return super.plus(m);
	}

	public Matrix minus(Matrix m) {
		return super.minus(m);
	}

	public Matrix[] eig() {
		return super.eig();
	}

	public Matrix[] lu() {
		return super.lu();
	}

	public Matrix[] svd() {
		return super.svd();
	}

	public Matrix[] qr() {
		return super.qr();
	}

	public Matrix plus(double value) {
		return super.plus(value);
	}

	public Matrix minus(double value) {
		return super.minus(value);
	}

	public Matrix times(double value) {
		return super.times(value);
	}

	public Matrix divide(double value) {
		return super.divide(value);
	}

	public Matrix solve(Matrix b) {
		return super.solve(b);
	}

	public CommonsMathArrayDenseDoubleMatrix2DFactory getFactory() {
		return Factory;
	}

}

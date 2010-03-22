/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

package org.ujmp.core.doublematrix;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.DivideMatrix;
import org.ujmp.core.calculation.DivideScalar;
import org.ujmp.core.calculation.MinusMatrix;
import org.ujmp.core.calculation.MinusScalar;
import org.ujmp.core.calculation.Mtimes;
import org.ujmp.core.calculation.PlusMatrix;
import org.ujmp.core.calculation.PlusScalar;
import org.ujmp.core.calculation.TimesMatrix;
import org.ujmp.core.calculation.TimesScalar;
import org.ujmp.core.calculation.Transpose;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Chol;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Eig;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Inv;
import org.ujmp.core.doublematrix.calculation.general.decomposition.LU;
import org.ujmp.core.doublematrix.calculation.general.decomposition.QR;
import org.ujmp.core.doublematrix.calculation.general.decomposition.SVD;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Solve;
import org.ujmp.core.doublematrix.factory.DefaultDenseDoubleMatrix2DFactory;
import org.ujmp.core.doublematrix.factory.DenseDoubleMatrix2DFactory;
import org.ujmp.core.matrix.DenseMatrix2D;

public interface DenseDoubleMatrix2D extends DoubleMatrix2D, DenseDoubleMatrix, DenseMatrix2D {

	public static DenseDoubleMatrix2DFactory factory = new DefaultDenseDoubleMatrix2DFactory();

	public static Transpose<DenseDoubleMatrix2D> transpose = Transpose.DENSEDOUBLEMATRIX2D;

	public static PlusMatrix<DenseDoubleMatrix2D> plusMatrix = PlusMatrix.DENSEDOUBLEMATRIX2D;

	public static MinusMatrix<DenseDoubleMatrix2D> minusMatrix = MinusMatrix.DENSEDOUBLEMATRIX2D;

	public static TimesMatrix<DenseDoubleMatrix2D> timesMatrix = TimesMatrix.DENSEDOUBLEMATRIX2D;

	public static DivideMatrix<DenseDoubleMatrix2D> divideMatrix = DivideMatrix.DENSEDOUBLEMATRIX2D;

	public static PlusScalar<DenseDoubleMatrix2D> plusScalar = PlusScalar.DENSEDOUBLEMATRIX2D;

	public static MinusScalar<DenseDoubleMatrix2D> minusScalar = MinusScalar.DENSEDOUBLEMATRIX2D;

	public static TimesScalar<DenseDoubleMatrix2D> timesScalar = TimesScalar.DENSEDOUBLEMATRIX2D;

	public static DivideScalar<DenseDoubleMatrix2D> divideScalar = DivideScalar.DENSEDOUBLEMATRIX2D;

	public static Mtimes<DenseDoubleMatrix2D, DenseDoubleMatrix2D, DenseDoubleMatrix2D> mtimes = Mtimes.DENSEDOUBLEMATRIX2D;

	public static SVD<Matrix> svd = org.ujmp.core.doublematrix.calculation.general.decomposition.SVD.INSTANCE;

	public static LU<Matrix> lu = org.ujmp.core.doublematrix.calculation.general.decomposition.LU.INSTANCE;

	public static QR<Matrix> qr = org.ujmp.core.doublematrix.calculation.general.decomposition.QR.INSTANCE;

	public static Inv<Matrix> inv = Inv.INSTANCE;

	public static Solve<Matrix> solve = Solve.INSTANCE;

	public static Chol<Matrix> chol = Chol.INSTANCE;

	public static Eig<Matrix> eig = Eig.INSTANCE;

	public DenseDoubleMatrix2DFactory getFactory();
}

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

package org.ujmp.core.doublematrix;

import org.ujmp.core.DenseMatrix2D;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.DivideMatrix;
import org.ujmp.core.calculation.DivideMatrixCalculation;
import org.ujmp.core.calculation.DivideScalar;
import org.ujmp.core.calculation.DivideScalarCalculation;
import org.ujmp.core.calculation.MinusMatrix;
import org.ujmp.core.calculation.MinusMatrixCalculation;
import org.ujmp.core.calculation.MinusScalar;
import org.ujmp.core.calculation.MinusScalarCalculation;
import org.ujmp.core.calculation.Mtimes;
import org.ujmp.core.calculation.MtimesCalculation;
import org.ujmp.core.calculation.PlusMatrix;
import org.ujmp.core.calculation.PlusMatrixCalculation;
import org.ujmp.core.calculation.PlusScalar;
import org.ujmp.core.calculation.PlusScalarCalculation;
import org.ujmp.core.calculation.TimesMatrix;
import org.ujmp.core.calculation.TimesMatrixCalculation;
import org.ujmp.core.calculation.TimesScalar;
import org.ujmp.core.calculation.TimesScalarCalculation;
import org.ujmp.core.calculation.Transpose;
import org.ujmp.core.calculation.TransposeCalculation;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Chol;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Eig;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Inv;
import org.ujmp.core.doublematrix.calculation.general.decomposition.InvSPD;
import org.ujmp.core.doublematrix.calculation.general.decomposition.InvSymm;
import org.ujmp.core.doublematrix.calculation.general.decomposition.LU;
import org.ujmp.core.doublematrix.calculation.general.decomposition.QR;
import org.ujmp.core.doublematrix.calculation.general.decomposition.SVD;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Solve;
import org.ujmp.core.doublematrix.calculation.general.decomposition.SolveSPD;
import org.ujmp.core.doublematrix.calculation.general.decomposition.SolveSymm;
import org.ujmp.core.doublematrix.factory.DefaultDenseDoubleMatrix2DFactory;
import org.ujmp.core.doublematrix.factory.DenseDoubleMatrix2DFactory;

public interface DenseDoubleMatrix2D extends DoubleMatrix2D, DenseDoubleMatrix, DenseMatrix2D {

	public static DenseDoubleMatrix2DFactory<? extends DenseDoubleMatrix2D> Factory = new DefaultDenseDoubleMatrix2DFactory();

	public DenseDoubleMatrix2DFactory<? extends DenseDoubleMatrix2D> getFactory();

	public static TransposeCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D> transpose = Transpose.DENSEDOUBLEMATRIX2D;

	public static PlusMatrixCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D, DenseDoubleMatrix2D> plusMatrix = PlusMatrix.DENSEDOUBLEMATRIX2D;

	public static MinusMatrixCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D, DenseDoubleMatrix2D> minusMatrix = MinusMatrix.DENSEDOUBLEMATRIX2D;

	public static TimesMatrixCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D, DenseDoubleMatrix2D> timesMatrix = TimesMatrix.DENSEDOUBLEMATRIX2D;

	public static DivideMatrixCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D, DenseDoubleMatrix2D> divideMatrix = DivideMatrix.DENSEDOUBLEMATRIX2D;

	public static PlusScalarCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D> plusScalar = PlusScalar.DENSEDOUBLEMATRIX2D;

	public static MinusScalarCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D> minusScalar = MinusScalar.DENSEDOUBLEMATRIX2D;

	public static TimesScalarCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D> timesScalar = TimesScalar.DENSEDOUBLEMATRIX2D;

	public static DivideScalarCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D> divideScalar = DivideScalar.DENSEDOUBLEMATRIX2D;

	public static MtimesCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D, DenseDoubleMatrix2D> mtimes = Mtimes.DENSEDOUBLEMATRIX2D;

	public static SVD<Matrix> svd = org.ujmp.core.doublematrix.calculation.general.decomposition.SVD.INSTANCE;

	public static LU<Matrix> lu = org.ujmp.core.doublematrix.calculation.general.decomposition.LU.INSTANCE;

	public static QR<Matrix> qr = org.ujmp.core.doublematrix.calculation.general.decomposition.QR.INSTANCE;

	public static Inv<Matrix> inv = Inv.INSTANCE;

	public static InvSymm<Matrix> invSymm = InvSymm.INSTANCE;

	public static InvSPD<Matrix> invSPD = InvSPD.INSTANCE;

	public static Solve<Matrix> solve = Solve.INSTANCE;

	public static SolveSymm<Matrix> solveSymm = SolveSymm.INSTANCE;

	public static SolveSPD<Matrix> solveSPD = SolveSPD.INSTANCE;

	public static Chol<Matrix> chol = Chol.INSTANCE;

	public static Eig<Matrix> eig = Eig.INSTANCE;

}

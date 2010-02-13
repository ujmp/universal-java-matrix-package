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

package org.ujmp.core;

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

public abstract class Ops {

	public static Transpose<Matrix> TRANSPOSE = Transpose.INSTANCE;

	public static PlusMatrix<Matrix> PLUSMATRIX = PlusMatrix.INSTANCE;

	public static MinusMatrix<Matrix> MINUSMATRIX = MinusMatrix.INSTANCE;

	public static TimesMatrix<Matrix> TIMESMATRIX = TimesMatrix.INSTANCE;

	public static DivideMatrix<Matrix> DIVIDEMATRIX = DivideMatrix.INSTANCE;

	public static PlusScalar<Matrix> PLUSSCALAR = PlusScalar.INSTANCE;

	public static MinusScalar<Matrix> MINUSSCALAR = MinusScalar.INSTANCE;

	public static TimesScalar<Matrix> TIMESSCALAR = TimesScalar.INSTANCE;

	public static DivideScalar<Matrix> DIVIDESCALAR = DivideScalar.INSTANCE;

	public static Mtimes<Matrix, Matrix, Matrix> MTIMES = Mtimes.INSTANCE;

	public static SVD<Matrix> SVD = org.ujmp.core.doublematrix.calculation.general.decomposition.SVD.INSTANCE;

	public static LU<Matrix> LU = org.ujmp.core.doublematrix.calculation.general.decomposition.LU.INSTANCE;

	public static QR<Matrix> QR = org.ujmp.core.doublematrix.calculation.general.decomposition.QR.INSTANCE;

	public static Inv<Matrix> INV = Inv.INSTANCE;

	public static Solve<Matrix> SOLVE = Solve.INSTANCE;

	public static Chol<Matrix> CHOL = Chol.INSTANCE;

	public static Eig<Matrix> EIG = Eig.INSTANCE;
}

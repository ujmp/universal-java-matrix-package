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

package org.ujmp.core.util;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Chol;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Eig;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Inv;
import org.ujmp.core.doublematrix.calculation.general.decomposition.LU;
import org.ujmp.core.doublematrix.calculation.general.decomposition.QR;
import org.ujmp.core.doublematrix.calculation.general.decomposition.SVD;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Solve;

@SuppressWarnings("unchecked")
public abstract class DecompositionOps {

	public static SVD<Matrix> SVD_UJMP = org.ujmp.core.doublematrix.calculation.general.decomposition.SVD.INSTANCE;

	public static SVD<Matrix> SVD_EJML = null;

	public static SVD<Matrix> SVD_OJALGO = null;

	public static SVD<Matrix> SVD_MTJ = null;

	public static Inv<Matrix> INV_UJMP = org.ujmp.core.doublematrix.calculation.general.decomposition.Inv.INSTANCE;

	public static Inv<Matrix> INV_EJML = null;

	public static Inv<Matrix> INV_OJALGO = null;

	public static Inv<Matrix> INV_MTJ = null;

	public static Solve<Matrix> SOLVE_UJMP = org.ujmp.core.doublematrix.calculation.general.decomposition.Solve.INSTANCE;

	public static Solve<Matrix> SOLVE_EJML = null;

	public static Solve<Matrix> SOLVE_OJALGO = null;

	public static Solve<Matrix> SOLVE_MTJ = null;

	public static LU<Matrix> LU_UJMP = org.ujmp.core.doublematrix.calculation.general.decomposition.LU.INSTANCE;

	public static LU<Matrix> LU_EJML = null;

	public static LU<Matrix> LU_OJALGO = null;

	public static LU<Matrix> LU_MTJ = null;

	public static QR<Matrix> QR_UJMP = org.ujmp.core.doublematrix.calculation.general.decomposition.QR.INSTANCE;

	public static QR<Matrix> QR_EJML = null;

	public static QR<Matrix> QR_OJALGO = null;

	public static QR<Matrix> QR_MTJ = null;

	public static Chol<Matrix> CHOL_UJMP = org.ujmp.core.doublematrix.calculation.general.decomposition.Chol.INSTANCE;

	public static Chol<Matrix> CHOL_EJML = null;

	public static Chol<Matrix> CHOL_OJALGO = null;

	public static Chol<Matrix> CHOL_MTJ = null;

	public static Eig<Matrix> EIG_UJMP = org.ujmp.core.doublematrix.calculation.general.decomposition.Eig.INSTANCE;

	public static Eig<Matrix> EIG_EJML = null;

	public static Eig<Matrix> EIG_OJALGO = null;

	public static Eig<Matrix> EIG_MTJ = null;

	static {
		init();
	}

	public static void init() {
		initSVD();
		initInv();
		initSolve();
		initLU();
		initQR();
		initChol();
		initEig();
	}

	public static void initSVD() {
		try {
			SVD_EJML = (SVD<Matrix>) Class.forName("org.ujmp.ejml.calculation.SVD").newInstance();
		} catch (Throwable t) {
		}
		try {
			SVD_OJALGO = (SVD<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.SVD")
					.newInstance();
		} catch (Throwable t) {
		}
		try {
			SVD_MTJ = (SVD<Matrix>) Class.forName("org.ujmp.mtj.calculation.SVD").newInstance();
		} catch (Throwable t) {
		}
	}

	public static void initInv() {
		try {
			INV_EJML = (Inv<Matrix>) Class.forName("org.ujmp.ejml.calculation.Inv").newInstance();
		} catch (Throwable t) {
		}
		try {
			INV_OJALGO = (Inv<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.Inv")
					.newInstance();
		} catch (Throwable t) {
		}
		try {
			INV_OJALGO = (Inv<Matrix>) Class.forName("org.ujmp.mtj.calculation.Inv").newInstance();
		} catch (Throwable t) {
		}
	}

	public static void initSolve() {
		try {
			SOLVE_EJML = (Solve<Matrix>) Class.forName("org.ujmp.ejml.calculation.Solve")
					.newInstance();
		} catch (Throwable t) {
		}
		try {
			// TODO: wait for fix in ojalgo
			// SOLVE_OJALGO = (Solve<Matrix>)
			// Class.forName("org.ujmp.ojalgo.calculation.Solve")
			// .newInstance();
		} catch (Throwable t) {
		}
		try {
			SOLVE_MTJ = (Solve<Matrix>) Class.forName("org.ujmp.mtj.calculation.Solve")
					.newInstance();
		} catch (Throwable t) {
		}
	}

	public static void initLU() {
		try {
			LU_EJML = (LU<Matrix>) Class.forName("org.ujmp.ejml.calculation.LU").newInstance();
		} catch (Throwable t) {
		}
		try {
			LU_OJALGO = (LU<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.LU").newInstance();
		} catch (Throwable t) {
		}
		try {
			LU_MTJ = (LU<Matrix>) Class.forName("org.ujmp.mtj.calculation.LU").newInstance();
		} catch (Throwable t) {
		}
	}

	public static void initQR() {
		try {
			QR_EJML = (QR<Matrix>) Class.forName("org.ujmp.ejml.calculation.QR").newInstance();
		} catch (Throwable t) {
		}
		try {
			QR_OJALGO = (QR<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.QR").newInstance();
		} catch (Throwable t) {
		}
		try {
			QR_MTJ = (QR<Matrix>) Class.forName("org.ujmp.mtj.calculation.QR").newInstance();
		} catch (Throwable t) {
		}
	}

	public static void initChol() {
		try {
			CHOL_EJML = (Chol<Matrix>) Class.forName("org.ujmp.ejml.calculation.Chol")
					.newInstance();
		} catch (Throwable t) {
		}
		try {
			CHOL_OJALGO = (Chol<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.Chol")
					.newInstance();
		} catch (Throwable t) {
		}
		try {
			CHOL_MTJ = (Chol<Matrix>) Class.forName("org.ujmp.mtj.calculation.Chol").newInstance();
		} catch (Throwable t) {
		}
	}

	public static void initEig() {
		try {
			EIG_EJML = (Eig<Matrix>) Class.forName("org.ujmp.ejml.calculation.Eig").newInstance();
		} catch (Throwable t) {
		}
		try {
			EIG_OJALGO = (Eig<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.Eig")
					.newInstance();
		} catch (Throwable t) {
		}
		try {
			EIG_MTJ = (Eig<Matrix>) Class.forName("org.ujmp.mtj.calculation.Eig").newInstance();
		} catch (Throwable t) {
		}
	}

}

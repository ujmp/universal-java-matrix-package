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
import org.ujmp.core.doublematrix.calculation.general.decomposition.InvSPD;
import org.ujmp.core.doublematrix.calculation.general.decomposition.LU;
import org.ujmp.core.doublematrix.calculation.general.decomposition.QR;
import org.ujmp.core.doublematrix.calculation.general.decomposition.SVD;
import org.ujmp.core.doublematrix.calculation.general.decomposition.Solve;
import org.ujmp.core.doublematrix.calculation.general.decomposition.SolveSPD;

@SuppressWarnings("unchecked")
public abstract class DecompositionOps {

	public static SVD<Matrix> SVD_UJMP = org.ujmp.core.doublematrix.calculation.general.decomposition.SVD.INSTANCE;

	public static SVD<Matrix> SVD_EJML = null;

	public static SVD<Matrix> SVD_OJALGO = null;

	public static SVD<Matrix> SVD_MTJ = null;

	public static Inv<Matrix> INV_UJMP = org.ujmp.core.doublematrix.calculation.general.decomposition.Inv.INSTANCE;

	public static Inv<Matrix> INV_EJML = null;

	public static Inv<Matrix> INV_OJALGO = null;

	public static Inv<Matrix> INV_JBLAS = null;

	public static Inv<Matrix> INV_MTJ = null;

	public static InvSPD<Matrix> INVSPD_UJMP = org.ujmp.core.doublematrix.calculation.general.decomposition.InvSPD.INSTANCE;

	public static InvSPD<Matrix> INVSPD_EJML = null;

	public static InvSPD<Matrix> INVSPD_OJALGO = null;

	public static InvSPD<Matrix> INVSPD_JBLAS = null;

	public static InvSPD<Matrix> INVSPD_MTJ = null;

	public static Solve<Matrix> SOLVE_UJMP = org.ujmp.core.doublematrix.calculation.general.decomposition.Solve.INSTANCE;

	public static Solve<Matrix> SOLVE_EJML = null;

	public static Solve<Matrix> SOLVE_OJALGO = null;

	public static Solve<Matrix> SOLVE_MTJ = null;

	public static Solve<Matrix> SOLVE_JBLAS = null;

	public static SolveSPD<Matrix> SOLVESPD_UJMP = org.ujmp.core.doublematrix.calculation.general.decomposition.SolveSPD.INSTANCE;

	public static SolveSPD<Matrix> SOLVESPD_EJML = null;

	public static SolveSPD<Matrix> SOLVESPD_OJALGO = null;

	public static SolveSPD<Matrix> SOLVESPD_MTJ = null;

	public static SolveSPD<Matrix> SOLVESPD_JBLAS = null;

	public static LU<Matrix> LU_UJMP = org.ujmp.core.doublematrix.calculation.general.decomposition.LU.INSTANCE;

	public static LU<Matrix> LU_EJML = null;

	public static LU<Matrix> LU_OJALGO = null;

	public static LU<Matrix> LU_MTJ = null;

	public static LU<Matrix> LU_JBLAS = null;

	public static QR<Matrix> QR_UJMP = org.ujmp.core.doublematrix.calculation.general.decomposition.QR.INSTANCE;

	public static QR<Matrix> QR_EJML = null;

	public static QR<Matrix> QR_OJALGO = null;

	public static QR<Matrix> QR_MTJ = null;

	public static Chol<Matrix> CHOL_UJMP = org.ujmp.core.doublematrix.calculation.general.decomposition.Chol.INSTANCE;

	public static Chol<Matrix> CHOL_EJML = null;

	public static Chol<Matrix> CHOL_OJALGO = null;

	public static Chol<Matrix> CHOL_MTJ = null;

	public static Chol<Matrix> CHOL_JBLAS = null;

	public static Eig<Matrix> EIG_UJMP = org.ujmp.core.doublematrix.calculation.general.decomposition.Eig.INSTANCE;

	public static Eig<Matrix> EIG_EJML = null;

	public static Eig<Matrix> EIG_OJALGO = null;

	public static Eig<Matrix> EIG_MTJ = null;

	public static Eig<Matrix> EIG_JBLAS = null;

	static {
		init();
	}

	public static void init() {
		initSVD();
		initInv();
		initInvSPD();
		initSolve();
		initSolveSPD();
		initLU();
		initQR();
		initChol();
		initEig();
	}

	public static void initSVD() {
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.ejml.Plugin").newInstance();
			if (p.isAvailable()) {
				SVD_EJML = (SVD<Matrix>) Class.forName("org.ujmp.ejml.calculation.SVD")
						.newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.ojalgo.Plugin")
					.newInstance();
			if (p.isAvailable()) {
				SVD_OJALGO = (SVD<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.SVD")
						.newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.mtj.Plugin").newInstance();
			if (p.isAvailable()) {
				SVD_MTJ = (SVD<Matrix>) Class.forName("org.ujmp.mtj.calculation.SVD").newInstance();
			}
		} catch (Throwable t) {
		}
	}

	public static void initInv() {
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.ejml.Plugin").newInstance();
			if (p.isAvailable()) {
				INV_EJML = (Inv<Matrix>) Class.forName("org.ujmp.ejml.calculation.Inv")
						.newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.ojalgo.Plugin")
					.newInstance();
			if (p.isAvailable()) {
				INV_OJALGO = (Inv<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.Inv")
						.newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.jblas.Plugin")
					.newInstance();
			if (p.isAvailable()) {
				INV_JBLAS = (Inv<Matrix>) Class.forName("org.ujmp.jblas.calculation.Inv")
						.newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.mtj.Plugin").newInstance();
			if (p.isAvailable()) {
				INV_MTJ = (Inv<Matrix>) Class.forName("org.ujmp.mtj.calculation.Inv").newInstance();
			}
		} catch (Throwable t) {
		}
	}

	public static void initInvSPD() {
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.ejml.Plugin").newInstance();
			if (p.isAvailable()) {
				INVSPD_EJML = (InvSPD<Matrix>) Class.forName("org.ujmp.ejml.calculation.InvSPD")
						.newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.ojalgo.Plugin")
					.newInstance();
			if (p.isAvailable()) {
				INVSPD_OJALGO = (InvSPD<Matrix>) Class
						.forName("org.ujmp.ojalgo.calculation.InvSPD").newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.jblas.Plugin")
					.newInstance();
			if (p.isAvailable()) {
				INVSPD_JBLAS = (InvSPD<Matrix>) Class.forName("org.ujmp.jblas.calculation.InvSPD")
						.newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.mtj.Plugin").newInstance();
			if (p.isAvailable()) {
				INVSPD_MTJ = (InvSPD<Matrix>) Class.forName("org.ujmp.mtj.calculation.InvSPD")
						.newInstance();
			}
		} catch (Throwable t) {
		}
	}

	public static void initSolve() {
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.ejml.Plugin").newInstance();
			if (p.isAvailable()) {
				SOLVE_EJML = (Solve<Matrix>) Class.forName("org.ujmp.ejml.calculation.Solve")
						.newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.ojalgo.Plugin")
					.newInstance();
			if (p.isAvailable()) {
				SOLVE_OJALGO = (Solve<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.Solve")
						.newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.jblas.Plugin")
					.newInstance();
			if (p.isAvailable()) {
				SOLVE_JBLAS = (Solve<Matrix>) Class.forName("org.ujmp.jblas.calculation.Solve")
						.newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.mtj.Plugin").newInstance();
			if (p.isAvailable()) {
				SOLVE_MTJ = (Solve<Matrix>) Class.forName("org.ujmp.mtj.calculation.Solve")
						.newInstance();
			}
		} catch (Throwable t) {
		}
	}

	public static void initSolveSPD() {
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.ejml.Plugin").newInstance();
			if (p.isAvailable()) {
				SOLVESPD_EJML = (SolveSPD<Matrix>) Class.forName(
						"org.ujmp.ejml.calculation.SolveSPD").newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.ojalgo.Plugin")
					.newInstance();
			if (p.isAvailable()) {
				SOLVESPD_OJALGO = (SolveSPD<Matrix>) Class.forName(
						"org.ujmp.ojalgo.calculation.SolveSPD").newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.jblas.Plugin")
					.newInstance();
			if (p.isAvailable()) {
				SOLVESPD_JBLAS = (SolveSPD<Matrix>) Class.forName(
						"org.ujmp.jblas.calculation.SolveSPD").newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.mtj.Plugin").newInstance();
			if (p.isAvailable()) {
				SOLVESPD_MTJ = (SolveSPD<Matrix>) Class
						.forName("org.ujmp.mtj.calculation.SolveSPD").newInstance();
			}
		} catch (Throwable t) {
		}
	}

	public static void initLU() {
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.ejml.Plugin").newInstance();
			if (p.isAvailable()) {
				LU_EJML = (LU<Matrix>) Class.forName("org.ujmp.ejml.calculation.LU").newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.ojalgo.Plugin")
					.newInstance();
			if (p.isAvailable()) {
				LU_OJALGO = (LU<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.LU")
						.newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.mtj.Plugin").newInstance();
			if (p.isAvailable()) {
				LU_MTJ = (LU<Matrix>) Class.forName("org.ujmp.mtj.calculation.LU").newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.jblas.Plugin")
					.newInstance();
			if (p.isAvailable()) {
				LU_JBLAS = (LU<Matrix>) Class.forName("org.ujmp.jblas.calculation.LU")
						.newInstance();
			}
		} catch (Throwable t) {
		}
	}

	public static void initQR() {
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.ejml.Plugin").newInstance();
			if (p.isAvailable()) {
				QR_EJML = (QR<Matrix>) Class.forName("org.ujmp.ejml.calculation.QR").newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.ojalgo.Plugin")
					.newInstance();
			if (p.isAvailable()) {
				QR_OJALGO = (QR<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.QR")
						.newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.mtj.Plugin").newInstance();
			if (p.isAvailable()) {
				QR_MTJ = (QR<Matrix>) Class.forName("org.ujmp.mtj.calculation.QR").newInstance();
			}
		} catch (Throwable t) {
		}
	}

	public static void initChol() {
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.ejml.Plugin").newInstance();
			if (p.isAvailable()) {
				CHOL_EJML = (Chol<Matrix>) Class.forName("org.ujmp.ejml.calculation.Chol")
						.newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.ojalgo.Plugin")
					.newInstance();
			if (p.isAvailable()) {
				CHOL_OJALGO = (Chol<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.Chol")
						.newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.mtj.Plugin").newInstance();
			if (p.isAvailable()) {
				CHOL_MTJ = (Chol<Matrix>) Class.forName("org.ujmp.mtj.calculation.Chol")
						.newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.jblas.Plugin")
					.newInstance();
			if (p.isAvailable()) {
				CHOL_JBLAS = (Chol<Matrix>) Class.forName("org.ujmp.jblas.calculation.Chol")
						.newInstance();
			}
		} catch (Throwable t) {
		}
	}

	public static void initEig() {
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.ejml.Plugin").newInstance();
			if (p.isAvailable()) {
				EIG_EJML = (Eig<Matrix>) Class.forName("org.ujmp.ejml.calculation.Eig")
						.newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.ojalgo.Plugin")
					.newInstance();
			if (p.isAvailable()) {
				EIG_OJALGO = (Eig<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.Eig")
						.newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.mtj.Plugin").newInstance();
			if (p.isAvailable()) {
				EIG_MTJ = (Eig<Matrix>) Class.forName("org.ujmp.mtj.calculation.Eig").newInstance();
			}
		} catch (Throwable t) {
		}
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.jblas.Plugin")
					.newInstance();
			if (p.isAvailable()) {
				EIG_JBLAS = (Eig<Matrix>) Class.forName("org.ujmp.jblas.calculation.Eig")
						.newInstance();
			}
		} catch (Throwable t) {
		}
	}

}

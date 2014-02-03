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

package org.ujmp.core.doublematrix.calculation.general.decomposition;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.util.DecompositionOps;
import org.ujmp.core.util.UJMPSettings;

public interface InvSPD<T> extends Inv<T> {

	public static int THRESHOLD = 100;

	public T calc(T source);

	public static final InvSPD<Matrix> MATRIX = new InvSPD<Matrix>() {

		public final Matrix calc(Matrix source) {
			if (source.getDimensionCount() != 2 || source.getRowCount() != source.getColumnCount()) {
				throw new RuntimeException(
						"inverse only possible for square matrices. use pinv or ginv instead");
			}
			if (UJMPSettings.getNumberOfThreads() == 1) {
				if (source.getRowCount() >= THRESHOLD && source.getColumnCount() >= THRESHOLD) {
					return MATRIXLARGESINGLETHREADED.calc(source);
				} else {
					return MATRIXSMALLSINGLETHREADED.calc(source);
				}
			} else {
				if (source.getRowCount() >= THRESHOLD && source.getColumnCount() >= THRESHOLD) {
					return MATRIXLARGEMULTITHREADED.calc(source);
				} else {
					return MATRIXSMALLMULTITHREADED.calc(source);
				}
			}
		}
	};

	public final InvSPD<Matrix> UJMP = new InvSPD<Matrix>() {
		public final Matrix calc(Matrix source) {
			DenseDoubleMatrix2D b = DenseDoubleMatrix2D.Factory.zeros(source.getRowCount(), source
					.getRowCount());
			for (int i = (int) source.getRowCount(); --i >= 0;) {
				b.setDouble(1.0, i, i);
			}
			return Chol.INSTANCE.solve(source, b);
		}
	};

	public final InvSPD<Matrix> INSTANCE = MATRIX;

	public static final InvSPD<Matrix> MATRIXSMALLSINGLETHREADED = UJMP;

	public static final InvSPD<Matrix> MATRIXLARGESINGLETHREADED = new InvSPD<Matrix>() {
		public final Matrix calc(Matrix source) {
			InvSPD<Matrix> inv = null;
			if (UJMPSettings.isUseJBlas()) {
				inv = DecompositionOps.INVSPD_JBLAS;
			}
			if (inv == null && UJMPSettings.isUseEJML()) {
				inv = DecompositionOps.INVSPD_EJML;
			}
			if (inv == null && UJMPSettings.isUseOjalgo()) {
				inv = DecompositionOps.INVSPD_OJALGO;
			}
			if (inv == null && UJMPSettings.isUseMTJ()) {
				inv = DecompositionOps.INVSPD_MTJ;
			}
			if (inv == null) {
				inv = UJMP;
			}
			return inv.calc(source);
		}
	};

	public static final InvSPD<Matrix> MATRIXLARGEMULTITHREADED = new InvSPD<Matrix>() {
		public Matrix calc(Matrix source) {
			Inv<Matrix> inv = null;
			if (UJMPSettings.isUseJBlas()) {
				inv = DecompositionOps.INVSPD_JBLAS;
			}
			if (inv == null && UJMPSettings.isUseOjalgo()) {
				inv = DecompositionOps.INVSPD_OJALGO;
			}
			if (inv == null && UJMPSettings.isUseEJML()) {
				inv = DecompositionOps.INVSPD_EJML;
			}
			if (inv == null && UJMPSettings.isUseParallelColt()) {
				inv = DecompositionOps.INVSPD_PARALLELCOLT;
			}
			if (inv == null && UJMPSettings.isUseMTJ()) {
				inv = DecompositionOps.INVSPD_MTJ;
			}
			if (inv == null) {
				inv = UJMP;
			}
			return inv.calc(source);
		}
	};

	public static final InvSPD<Matrix> MATRIXSMALLMULTITHREADED = UJMP;

}

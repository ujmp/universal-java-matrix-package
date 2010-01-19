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

package org.ujmp.core.doublematrix.calculation.general.decomposition;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.UJMPSettings;

public interface Inv<T> {

	public T calc(T source);

	public static Inv<Matrix> MATRIX = new Inv<Matrix>() {

		public Matrix calc(Matrix source) {
			if (source.getDimensionCount() != 2 || source.getRowCount() != source.getColumnCount()) {
				throw new MatrixException(
						"inverse only possible for square matrices. use pinv or ginv instead");
			}
			if (UJMPSettings.getNumberOfThreads() == 1) {
				if (source.getRowCount() >= 100 && source.getColumnCount() >= 100) {
					return MATRIXLARGESINGLETHREADED.calc(source);
				} else {
					return MATRIXSMALLSINGLETHREADED.calc(source);
				}
			} else {
				if (source.getRowCount() >= 100 && source.getColumnCount() >= 100) {
					return MATRIXLARGEMULTITHREADED.calc(source);
				} else {
					return MATRIXSMALLMULTITHREADED.calc(source);
				}
			}
		}
	};

	public Inv<Matrix> UJMP = new Inv<Matrix>() {
		public Matrix calc(Matrix source) {
			return LU.INSTANCE.solve(source, MatrixFactory.eye(source.getRowCount(), source
					.getRowCount()));
		}
	};

	public Inv<Matrix> INSTANCE = MATRIX;

	public static Inv<Matrix> MATRIXSMALLSINGLETHREADED = new Inv<Matrix>() {

		@SuppressWarnings("unchecked")
		public Matrix calc(Matrix source) {
			Inv<Matrix> inv = null;

			try {
				inv = (Inv<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.Inv").newInstance();
			} catch (Throwable e) {
			}
			try {
				inv = (Inv<Matrix>) Class.forName("org.ujmp.ejml.calculation.Inv").newInstance();
			} catch (Throwable e) {
			}

			if (inv == null) {
				inv = UJMP;
			}
			return inv.calc(source);
		}
	};

	public static Inv<Matrix> MATRIXLARGESINGLETHREADED = new Inv<Matrix>() {

		@SuppressWarnings("unchecked")
		public Matrix calc(Matrix source) {
			Inv<Matrix> inv = null;

			try {
				inv = (Inv<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.Inv").newInstance();
			} catch (Throwable e) {
			}
			try {
				inv = (Inv<Matrix>) Class.forName("org.ujmp.ejml.calculation.Inv").newInstance();
			} catch (Throwable e) {
			}

			if (inv == null) {
				inv = UJMP;
			}
			return inv.calc(source);
		}
	};

	public static Inv<Matrix> MATRIXSMALLMULTITHREADED = new Inv<Matrix>() {

		@SuppressWarnings("unchecked")
		public Matrix calc(Matrix source) {
			Inv<Matrix> inv = null;

			try {
				inv = (Inv<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.Inv").newInstance();
			} catch (Throwable e) {
			}
			try {
				inv = (Inv<Matrix>) Class.forName("org.ujmp.ejml.calculation.Inv").newInstance();
			} catch (Throwable e) {
			}

			if (inv == null) {
				inv = UJMP;
			}
			return inv.calc(source);
		}
	};

	public static Inv<Matrix> MATRIXLARGEMULTITHREADED = new Inv<Matrix>() {

		@SuppressWarnings("unchecked")
		public Matrix calc(Matrix source) {
			Inv<Matrix> inv = null;

			try {
				inv = (Inv<Matrix>) Class.forName("org.ujmp.ojalgo.calculation.Inv").newInstance();
			} catch (Throwable e) {
			}
			try {
				inv = (Inv<Matrix>) Class.forName("org.ujmp.ejml.calculation.Inv").newInstance();
			} catch (Throwable e) {
			}

			if (inv == null) {
				inv = UJMP;
			}
			return inv.calc(source);
		}
	};
}

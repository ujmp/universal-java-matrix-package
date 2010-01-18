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

package org.ujmp.core.calculation;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.concurrent.PFor;

public interface Gemm<T> {

	public void calc(final double alpha, final T A, final double beta, final T B, T C);

	public Gemm<Matrix> INSTANCE = new Gemm<Matrix>() {

		public void calc(final double alpha, final Matrix A, final double beta, final Matrix B,
				final Matrix C) {
			final int m1RowCount = (int) A.getRowCount();
			final int m1ColumnCount = (int) A.getColumnCount();
			final int m2RowCount = (int) B.getRowCount();
			final int m2ColumnCount = (int) B.getColumnCount();

			if (m1ColumnCount != m2RowCount) {
				throw new MatrixException("matrices have wrong size");
			}

			if (alpha == 0 || beta == 0) {
				return;
			}

			if (C.getRowCount() >= 100 && C.getColumnCount() >= 100) {
				new PFor(0, m2ColumnCount - 1) {

					@Override
					public void step(int i) {
						if (beta != 1.0) {
							for (int irow = 0; irow < m1RowCount; ++irow) {
								C.setAsDouble(C.getAsDouble(irow, i) * beta, irow, i);
							}
						}
						for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
							double temp = alpha * B.getAsDouble(lcol, i);
							if (temp != 0.0) {
								for (int irow = 0; irow < m1RowCount; ++irow) {
									C.setAsDouble(C.getAsDouble(irow, i)
											+ A.getAsDouble(irow, lcol) * temp, irow, i);
								}
							}
						}
					}
				};
			} else {
				for (int i = 0; i < m2ColumnCount; i++) {
					if (beta != 1.0) {
						for (int irow = 0; irow < m1RowCount; ++irow) {
							C.setAsDouble(C.getAsDouble(irow, i) * beta, irow, i);
						}
					}
					for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
						double temp = alpha * B.getAsDouble(lcol, i);
						if (temp != 0.0) {
							for (int irow = 0; irow < m1RowCount; ++irow) {
								C.setAsDouble(C.getAsDouble(irow, i) + A.getAsDouble(irow, lcol)
										* temp, irow, i);
							}
						}
					}
				}
			}
		}
	};

}

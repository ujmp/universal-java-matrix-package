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

package org.ujmp.core.intmatrix.calculation;

import org.ujmp.core.Matrix;

/**
 * Creates a magic square matrix. The sums of all rows and columns are equal.
 * This code is taken from JAMA.
 */
public class Magic extends AbstractIntCalculation {
	private static final long serialVersionUID = -2372321035531662110L;

	private final Matrix magic;

	public Magic(Matrix matrix, int size) {
		super(matrix);
		this.magic = magic(size);
	}

	public int getInt(long... coordinates) {
		return magic.getAsInt(coordinates);
	}

	public static Matrix magic(int n) {
		final int[][] M = new int[n][n];

		// Odd order
		if ((n % 2) == 1) {
			int a = (n + 1) / 2;
			int b = (n + 1);

			for (int j = 0; j < n; j++) {
				for (int i = 0; i < n; i++) {
					M[i][j] = n * ((i + j + a) % n) + ((i + 2 * j + b) % n) + 1;
				}
			}

			// Doubly Even Order
		} else if ((n % 4) == 0) {
			for (int j = 0; j < n; j++) {
				for (int i = 0; i < n; i++) {
					if (((i + 1) / 2) % 2 == ((j + 1) / 2) % 2) {
						M[i][j] = n * n - n * i - j;
					} else {
						M[i][j] = n * i + j + 1;
					}
				}
			}

			// Singly Even Order
		} else {
			int p = n / 2;
			int k = (n - 2) / 4;

			Matrix A = magic(p);

			for (int j = 0; j < p; j++) {
				for (int i = 0; i < p; i++) {
					int aij = A.getAsInt(i, j);
					M[i][j] = aij;
					M[i][j + p] = aij + 2 * p * p;
					M[i + p][j] = aij + 3 * p * p;
					M[i + p][j + p] = aij + p * p;
				}
			}

			for (int i = 0; i < p; i++) {
				for (int j = 0; j < k; j++) {
					int t = M[i][j];
					M[i][j] = M[i + p][j];
					M[i + p][j] = t;
				}

				for (int j = n - k + 1; j < n; j++) {
					int t = M[i][j];
					M[i][j] = M[i + p][j];
					M[i + p][j] = t;
				}
			}
			int t = M[k][0];
			M[k][0] = M[k + p][0];
			M[k + p][0] = t;
			t = M[k][k];
			M[k][k] = M[k + p][k];
			M[k + p][k] = t;
		}
		return Matrix.Factory.linkToArray(M);
	}

}

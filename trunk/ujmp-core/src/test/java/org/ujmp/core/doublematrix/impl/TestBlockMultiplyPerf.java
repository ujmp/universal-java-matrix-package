/*
 * Copyright (C) 2010 by Frode Carlsen
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
package org.ujmp.core.doublematrix.impl;

import static java.lang.System.out;
import static junit.framework.Assert.assertTrue;

import org.ujmp.core.Matrix;

public class TestBlockMultiplyPerf {

	static BlockDenseDoubleMatrix2D multiplyBlock(final int dim, final int blocksz)
			throws Exception {
		BlockDenseDoubleMatrix2D res = TestBlockMultiply.multiplyMatrix(dim, dim, dim, blocksz,
				true);
		printoutMatrixSelectedContents(res);
		return res;
	}

	static void printoutMatrixSelectedContents(Matrix res) {
		int r = Math.min(100, (int) res.getRowCount() - 1);
		int c = Math.min(100, (int) res.getColumnCount() - 1);
		out.format("\n\tMatrix C = (%s, %s)\n", res.getRowCount(), res.getColumnCount());
	}

	private static Matrix translateDenseDoubleMatrix2D(final BlockDenseDoubleMatrix2D mat) {
		if (mat == null) {
			return null;
		}

		DefaultDenseDoubleMatrix2D res = new DefaultDenseDoubleMatrix2D(mat.getRowCount(), mat
				.getColumnCount());
		for (int i = 0; i < mat.getRowCount(); i++) {
			for (int j = 0; j < mat.getColumnCount(); j++) {
				res.setAsDouble(mat.getDouble(i, j), i, j);
			}
		}
		return res;
	}

	// @Test
	public void testMultiplyBlockLayoutSize100_x10() throws Exception {
		BlockDenseDoubleMatrix2D last = null;
		for (int i = 0; i < 10; i++) {
			BlockDenseDoubleMatrix2D next = multiplyBlock(100, 20);
			if (next != null) {
				last = next;
			}
		}
		assertTrue("last is null", last != null);
	}

	// @Test
	public void testMultiplyBlockLayoutSize1000() throws Exception {
		multiplyBlock(Fixture.M, 200);
	}

	// @Test
	public void testMultiplyBlockLayoutSize1000_x10() throws Exception {
		BlockDenseDoubleMatrix2D last = null;
		for (int i = 0; i < 10; i++) {
			BlockDenseDoubleMatrix2D next = multiplyBlock(1000, 200);
			if (next != null) {
				last = next;
			}
		}
		assertTrue("last is null", last != null);
	}

	// @Test
	public void testMultiplyBlockLayoutSize1001() throws Exception {
		multiplyBlock(1001, 201);
	}

	// @Test
	public void testMultiplyBlockLayoutSize1200() throws Exception {
		BlockDenseDoubleMatrix2D last = null;
		BlockDenseDoubleMatrix2D next = multiplyBlock(1200, 200);
		if (next != null) {
			last = next;
		}

		assertTrue("last is null", last != null);
	}

	// @Test
	public void testMultiplyBlockLayoutSize1200x10() throws Exception {
		BlockDenseDoubleMatrix2D last = null;
		for (int i = 0; i < 10; i++) {
			BlockDenseDoubleMatrix2D next = multiplyBlock(1200, 100);
			if (next != null) {
				last = next;
			}
		}
		assertTrue("last is null", last != null);
	}

	// @Test
	public void testMultiplyBlockLayoutSize200_x10() throws Exception {
		BlockDenseDoubleMatrix2D last = null;
		for (int i = 0; i < 10; i++) {
			BlockDenseDoubleMatrix2D next = multiplyBlock(200, 100);
			if (next != null) {
				last = next;
			}
		}
		assertTrue("last is null", last != null);
	}

	// @Test
	public void testMultiplyBlockLayoutSize2000() throws Exception {
		BlockDenseDoubleMatrix2D last = null;
		BlockDenseDoubleMatrix2D next = multiplyBlock(2000, 200);
		if (next != null) {
			last = next;
		}

		assertTrue("last is null", last != null);
	}

	// @Test
	public void testMultiplyBlockLayoutSize300_x10() throws Exception {
		BlockDenseDoubleMatrix2D last = null;
		for (int i = 0; i < 10; i++) {
			BlockDenseDoubleMatrix2D next = multiplyBlock(300, 150);
			if (next != null) {
				last = next;
			}
		}
		assertTrue("last is null", last != null);
	}

	// @Test
	public void testMultiplyBlockLayoutSize50_x10() throws Exception {
		BlockDenseDoubleMatrix2D last = null;
		for (int i = 0; i < 10; i++) {
			BlockDenseDoubleMatrix2D next = multiplyBlock(50, 10);
			if (next != null) {
				last = next;
			}
		}
		assertTrue("last is null", last != null);
	}

}

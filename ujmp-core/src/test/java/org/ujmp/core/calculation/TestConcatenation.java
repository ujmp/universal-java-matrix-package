/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;

public class TestConcatenation {

	@Test
	public void testHorizontal() throws Exception {
		Matrix m1 = MatrixFactory.linkToArray(new double[][] { { 1, 2, 3 }, { 4, 5, 6 } });
		Matrix m2 = MatrixFactory.linkToArray(new double[][] { { 7, 8 }, { 9, 10 } });
		Matrix m3 = MatrixFactory.linkToArray(new double[][] { { 11 }, { 12 } });
		Matrix m = MatrixFactory.horCat(m1, m2, m3);
		Matrix expected = MatrixFactory.linkToArray(new double[][] { { 1, 2, 3, 7, 8, 11 },
				{ 4, 5, 6, 9, 10, 12 } });
		assertTrue(expected.equals(m));
	}

	@Test
	public void testVertical() throws Exception {
		Matrix m1 = MatrixFactory.linkToArray(new double[][] { { 1, 2, 3 }, { 4, 5, 6 } });
		Matrix m2 = MatrixFactory.linkToArray(new double[][] { { 7, 8, 9 } });
		Matrix m3 = MatrixFactory.linkToArray(new double[][] { { 10, 11, 12 } });
		Matrix m = MatrixFactory.vertCat(m1, m2, m3);
		Matrix expected = MatrixFactory.linkToArray(new double[][] { { 1, 2, 3 }, { 4, 5, 6 },
				{ 7, 8, 9 }, { 10, 11, 12 } });
		assertTrue(expected.equals(m));
	}

	@Test
	public void testEmpty() throws Exception {
		Matrix m1 = MatrixFactory.linkToArray(new double[][] { { 1, 2, 3 }, { 4, 5, 6 } });
		Matrix m2 = MatrixFactory.emptyMatrix();
		Matrix m3 = MatrixFactory.linkToArray(new double[][] { { 10, 11, 12 } });
		Matrix m = MatrixFactory.vertCat(m1, m2, m3);
		Matrix expected = MatrixFactory.linkToArray(new double[][] { { 1, 2, 3 }, { 4, 5, 6 },
				{ 10, 11, 12 } });
		assertTrue(expected.equals(m));
	}
}

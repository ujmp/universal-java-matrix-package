/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

package org.ujmp.core.stringmatrix.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.ujmp.core.Matrix;
import org.ujmp.core.implementations.AbstractMatrixTest;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.matrices.MatrixLibraries;

public class TestDefaultDenseStringMatrix2D extends AbstractMatrixTest {

	public Matrix createMatrix(long... size) {
		return new DefaultDenseStringMatrix2D(MathUtil.longToInt(size[Matrix.ROW]),
				MathUtil.longToInt(size[Matrix.COLUMN]));
	}

	public Matrix createMatrix(Matrix source) {
		return new DefaultDenseStringMatrix2D(source);
	}

	public boolean isTestLarge() {
		return false;
	}

	@Override
	public int getMatrixLibraryId() {
		return MatrixLibraries.UJMP;
	}

	@Override
	public boolean isTestSparse() {
		return false;
	}

	@Test
	public void testStringTranspose() {
		Matrix m1 = new DefaultDenseStringMatrix2D(2, 1);
		m1.setAsString("string1", 0, 0);
		m1.setAsString("string2", 1, 0);
		Matrix m2 = m1.transpose();
		assertEquals("string1", m2.getAsString(0, 0));
		assertEquals("string2", m2.getAsString(0, 1));
	}
}

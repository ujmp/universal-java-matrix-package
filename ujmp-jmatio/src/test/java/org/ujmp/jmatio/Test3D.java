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

package org.ujmp.jmatio;

import junit.framework.TestCase;

import org.junit.Test;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.ValueType;

public class Test3D extends TestCase {

	public void testIndexCalc() {
		MLDoubleMatrix mlDouble = new MLDoubleMatrix(3, 4, 5);
		assertEquals(0, mlDouble.getIndex(0, 0, 0));
		assertEquals(1, mlDouble.getIndex(1, 0, 0));
		assertEquals(3, mlDouble.getIndex(0, 1, 0));
		assertEquals(12, mlDouble.getIndex(0, 0, 1));
		assertEquals(2, mlDouble.getIndex(2, 0, 0));
		assertEquals(5, mlDouble.getIndex(2, 1, 0));
		assertEquals(13, mlDouble.getIndex(1, 0, 1));
		assertEquals(15, mlDouble.getIndex(0, 1, 1));
		assertEquals(16, mlDouble.getIndex(1, 1, 1));
		assertEquals(24, mlDouble.getIndex(0, 0, 2));
		assertEquals(36, mlDouble.getIndex(0, 0, 3));
		assertEquals(43, mlDouble.getIndex(1, 2, 3));
		assertEquals(59, mlDouble.getIndex(2, 3, 4));
	}

	@Test
	public void testUJMP() {
		Matrix original = MatrixFactory.rand(ValueType.DOUBLE, 3, 4, 5);
		MLDoubleMatrix mlDouble = new MLDoubleMatrix(original);
		assertEquals("dimension count", original.getDimensionCount(), mlDouble.getDimensionCount());
		assertEquals("row count", original.getRowCount(), mlDouble.getRowCount());
		assertEquals("column count", original.getColumnCount(), mlDouble.getColumnCount());
		assertTrue("content equals", original.equalsContent(mlDouble));
	}

}

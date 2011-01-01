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

package org.ujmp.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.matrix.SparseMatrix;

public class MatrixFactoryTest {

	@Test
	public void testZerosConstructors0D() throws Exception {
		try {
			Matrix.factory.zeros();
			throw new Exception("should give an exception");
		} catch (MatrixException e) {
			return;
			// error is expected
		}
	}

	@Test
	public void testZerosConstructors1D() throws Exception {
		try {
			Matrix.factory.zeros(4);
			throw new Exception("should give an exception");
		} catch (MatrixException e) {
			return;
			// error is expected
		}
	}

	@Test
	public void testZerosConstructors2D() throws Exception {
		long[] size = new long[] { 4, 5 };
		for (ValueType e : ValueType.values()) {
			Matrix m = MatrixFactory.zeros(e, size);
			assertEquals(e.name(), e, m.getValueType());
			assertTrue(e.name(), Coordinates.equals(size, m.getSize()));
		}
	}

	@Test
	public void testZerosConstructors3D() throws Exception {
		long[] size = new long[] { 4, 5, 6 };
		for (ValueType e : ValueType.values()) {
			Matrix m = MatrixFactory.zeros(e, size);
			assertEquals(e.name(), e, m.getValueType());
			assertTrue(e.name(), Coordinates.equals(size, m.getSize()));
		}
	}

	@Test
	public void testZerosConstructorsMultiD() throws Exception {
		long[] size = new long[] { 4, 5, 6, 7, 8 };
		for (ValueType e : ValueType.values()) {
			Matrix m = MatrixFactory.zeros(e, size);
			assertEquals(e.name(), e, m.getValueType());
			assertTrue(e.name(), Coordinates.equals(size, m.getSize()));
		}
	}

	@Test
	public void testSparseConstructors0D() throws Exception {
		try {
			SparseMatrix.factory.zeros();
			throw new Exception("should give an exception");
		} catch (Exception e) {
			return;
			// error is expected
		}
	}

	@Test
	public void testSparseConstructors1D() throws Exception {
		try {
			SparseMatrix.factory.zeros(4);
			throw new Exception("should give an exception");
		} catch (Exception e) {
			return;
			// error is expected
		}
	}

	@Test
	public void testSparseConstructors2D() throws Exception {
		long[] size = new long[] { 4, 5 };
		for (ValueType e : ValueType.values()) {
			Matrix m = MatrixFactory.sparse(e, size);
			assertEquals(e.name(), e, m.getValueType());
			assertTrue(e.name(), Coordinates.equals(size, m.getSize()));
		}
	}

	@Test
	public void testSparseConstructors3D() throws Exception {
		long[] size = new long[] { 4, 5, 6 };
		for (ValueType e : ValueType.values()) {
			Matrix m = MatrixFactory.sparse(e, size);
			assertEquals(e.name(), e, m.getValueType());
			assertTrue(e.name(), Coordinates.equals(size, m.getSize()));
		}
	}

	@Test
	public void testSparseConstructorsMultiD() throws Exception {
		long[] size = new long[] { 4, 5, 6, 7, 8 };
		for (ValueType e : ValueType.values()) {
			Matrix m = MatrixFactory.sparse(e, size);
			assertEquals(e.name(), e, m.getValueType());
			assertTrue(e.name(), Coordinates.equals(size, m.getSize()));
		}
	}
}

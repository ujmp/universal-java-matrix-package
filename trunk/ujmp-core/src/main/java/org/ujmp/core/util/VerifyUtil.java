/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

import java.util.Arrays;

import org.ujmp.core.Matrix;

public abstract class VerifyUtil {

	public static final void assertTrue(boolean test, String message, Object... messageArgs) {
		if (!test) {
			String text = (messageArgs == null || messageArgs.length == 0) ? message : String
					.format(message, messageArgs);
			throw new IllegalArgumentException(text);
		}
	}

	public static final void assertFalse(boolean test, String message, Object... messageArgs) {
		if (!test) {
			String text = (messageArgs == null || messageArgs.length == 0) ? message : String
					.format(message, messageArgs);
			throw new IllegalArgumentException(text);
		}
	}

	public static final void assertTrue(boolean test, String message) {
		if (!test) {
			throw new IllegalArgumentException(message);
		}
	}

	public static final void assertFalse(boolean test, String message) {
		if (test) {
			throw new IllegalArgumentException(message);
		}
	}

	public static final void assertSameSize(final Matrix m1, final Matrix m2) {
		assertTrue(Arrays.equals(m1.getSize(), m2.getSize()), "matrices have different sizes");
	}

	public static void assertSameSize(Matrix... matrices) {
		assertTrue(matrices.length > 1, "more than one matrix must be provided");
		for (int i = matrices.length; --i != 0;) {
			assertTrue(Arrays.equals(matrices[i].getSize(), matrices[i - 1].getSize()),
					"matrices have different sizes");
		}
	}

	public static void assertSameSize(double[][] source1, double[][] source2, double[][] target) {
		assertNotNull(source1, "matrix1 cannot be null");
		assertNotNull(source2, "matrix2 cannot be null");
		assertNotNull(target, "matrix3 cannot be null");
		assertNotNull(source1[0], "matrix1 must be 2d");
		assertNotNull(source2[0], "matrix2 must be 2d");
		assertNotNull(target[0], "matrix3 must be 2d");
		assertEquals(source1.length, source2.length, "matrix1 and matrix2 have different sizes");
		assertEquals(source2.length, target.length, "matrix1 and matrix3 have different sizes");
		assertEquals(source1[0].length, source2[0].length,
				"matrix1 and matrix2 have different sizes");
		assertEquals(source2[0].length, target[0].length,
				"matrix1 and matrix3 have different sizes");
	}

	public static void assertEquals(int i1, int i2, String message) {
		assertTrue(i1 == i2, message);
	}

	public static void assertEquals(double d1, double d2, double tol, String message) {
		assertTrue(Math.abs(d1 - d2) < tol, message);
	}

	public static void assertEquals(float d1, float d2, float tol, String message) {
		assertTrue(Math.abs(d1 - d2) < tol, message);
	}

	public static void assertEquals(long[] s1, long[] s2, String message) {
		assertTrue(Arrays.equals(s1, s2), message);
	}

	public static void assertEquals(int[] s1, int[] s2, String message) {
		assertTrue(Arrays.equals(s1, s2), message);
	}

	public static void assertEquals(double[] s1, double[] s2, String message) {
		assertTrue(Arrays.equals(s1, s2), message);
	}

	public static void assertEquals(float[] s1, float[] s2, String message) {
		assertTrue(Arrays.equals(s1, s2), message);
	}

	public static void assertNotNull(Object o, String message) {
		assertFalse(o == null, message);
	}

	public static void assertNull(Object o, String message) {
		assertTrue(o == null, message);
	}

	public static void assertSameSize(double[] source1, double[] source2, double[] target) {
		assertNotNull(source1, "matrix1 cannot be null");
		assertNotNull(source2, "matrix2 cannot be null");
		assertNotNull(target, "matrix3 cannot be null");
		assertEquals(source1.length, source2.length, "matrix1 and matrix2 have different sizes");
		assertEquals(source2.length, target.length, "matrix1 and matrix3 have different sizes");
	}

	public static void assertSameSize(double[][] source, double[][] target) {
		assertNotNull(source, "matrix1 cannot be null");
		assertNotNull(target, "matrix2 cannot be null");
		assertNotNull(source[0], "matrix1 must be 2d");
		assertNotNull(target[0], "matrix2 must be 2d");
		assertEquals(source.length, target.length, "matrix1 and matrix2 have different sizes");
		assertEquals(source[0].length, target[0].length, "matrix1 and matrix2 have different sizes");
	}

	public static void assertSameSize(double[] source, double[] target) {
		assertNotNull(source, "matrix1 cannot be null");
		assertNotNull(target, "matrix2 cannot be null");
		assertEquals(source.length, target.length, "matrix1 and matrix2 have different sizes");
	}

	public static void assert2D(Matrix m) {
		assertNotNull(m, "matrix cannot be null");
		assertEquals(m.getDimensionCount(), 2, "matrix is not 2d");
	}

	public static void assertEquals(long l1, long l2, String message) {
		assertTrue(l1 == l2, message);
	}

	public static void assert2D(long... size) {
		assertNotNull(size, "size cannot be null");
		assertTrue(size.length == 2, "size must be 2d");

	}

	public static void assertSquare(Matrix matrix) {
		assert2D(matrix);
		assertTrue(matrix.getRowCount() == matrix.getColumnCount(), "matrix must be square");
	}

	public static void assertSingleValue(Matrix matrix) {
		assert2D(matrix);
		assertTrue(matrix.getRowCount() == 1, "matrix must be 1x1");
		assertTrue(matrix.getColumnCount() == 1, "matrix must be 1x1");
	}
}

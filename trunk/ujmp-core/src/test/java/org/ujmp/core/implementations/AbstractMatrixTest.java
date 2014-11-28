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

package org.ujmp.core.implementations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.benchmark.BenchmarkUtil;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.ArrayDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrixMultiD;
import org.ujmp.core.doublematrix.stub.AbstractDoubleMatrix;
import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.SerializationUtil;
import org.ujmp.core.util.matrices.MatrixLibraries;

public abstract class AbstractMatrixTest {

	public static final int ROW = Matrix.ROW;
	public static final int COLUMN = Matrix.COLUMN;

	private static final MatrixLibraries LIBRARIES = new MatrixLibraries();

	private static final String SQUARE = LIBRARIES.square();
	private static final String TALL = LIBRARIES.tall();
	private static final String FAT = LIBRARIES.fat();
	private static final String ALL = LIBRARIES.all();

	public enum MatrixLayout {
		SQUARE, FAT, TALL
	};

	public enum EntryType {
		RANDN, RAND, RANDSYMM, RANDNSYMM, SINGULAR, SPD
	};

	public enum Size {
		SINGLEENTRY, LARGE, SMALL
	};

	public static final double TOLERANCE = 1e-3;

	public abstract Matrix createMatrix(long... size) throws Exception;

	public abstract Matrix createMatrix(Matrix source) throws Exception;

	public abstract boolean isTestLarge();

	public abstract boolean isTestSparse();

	public abstract int getMatrixLibraryId();

	public String getLabel() {
		return this.getClass().getSimpleName();
	}

	public final Matrix getTestMatrix() throws Exception {
		Matrix m = createMatrixWithAnnotation(3, 3);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		m.setAsDouble(2.0, 2, 1);
		m.setAsDouble(-2.0, 1, 2);
		return m;
	}

	private final Matrix createMatrixWithAnnotation(long... size) throws Exception {
		Matrix m = createMatrix(size);
		setAnnotation(m);
		return m;
	}

	private final Matrix createMatrixWithAnnotation(Matrix matrix) throws Exception {
		Matrix m = createMatrix(matrix);
		setAnnotation(m);
		return m;
	}

	@Test
	public void testSparseSetToZero() throws Exception {
		Matrix m = createMatrix(2, 2);
		if (isTestSparse() && m.isSparse()) {
			m = createMatrix(800000, 900000);
			m.setAsDouble(1.0, 3, 4);
			m.setAsDouble(2.0, 334, 2214);
			m.setAsDouble(3.0, 335, 2215);
			m.setAsDouble(4.0, 334232, 3434);
			assertEquals(1.0, m.getAsDouble(3, 4), TOLERANCE);
			assertEquals(2.0, m.getAsDouble(334, 2214), TOLERANCE);
			assertEquals(3.0, m.getAsDouble(335, 2215), TOLERANCE);
			assertEquals(4.0, m.getAsDouble(334232, 3434), TOLERANCE);
			m.setAsDouble(0.0, 335, 2215);
			assertEquals(1.0, m.getAsDouble(3, 4), TOLERANCE);
			assertEquals(2.0, m.getAsDouble(334, 2214), TOLERANCE);
			assertEquals(0.0, m.getAsDouble(335, 2215), TOLERANCE);
			assertEquals(4.0, m.getAsDouble(334232, 3434), TOLERANCE);
		}
	}

	@Test
	public void testClear() throws Exception {
		Matrix m = createMatrix(3, 2);
		m.randn(Ret.ORIG);
		m.clear();
		assertEquals(0.0, m.getAsDouble(0, 0), TOLERANCE);
		assertEquals(0.0, m.getAsDouble(1, 0), TOLERANCE);
		assertEquals(0.0, m.getAsDouble(2, 0), TOLERANCE);
		assertEquals(0.0, m.getAsDouble(0, 1), TOLERANCE);
		assertEquals(0.0, m.getAsDouble(1, 1), TOLERANCE);
		assertEquals(0.0, m.getAsDouble(2, 1), TOLERANCE);
	}

	@Test
	public void testSparseMultiplyLarge() throws Exception {
		Matrix m1 = createMatrix(2, 2);
		Matrix m2 = null;
		if (isTestSparse() && m1.isSparse()) {
			m1 = createMatrix(800000, 900000);
			m2 = createMatrix(900000, 400000);
			m1.setAsDouble(5.0, 0, 0);
			m1.setAsDouble(4.0, 1, 1);
			m1.setAsDouble(1.0, 3, 4);
			m1.setAsDouble(2.0, 4, 2);
			m1.setAsDouble(3.0, 3, 5);
			m1.setAsDouble(4.0, 4, 4);
			m1.setAsDouble(2.0, 334, 2214);
			m1.setAsDouble(3.0, 335, 2215);
			m1.setAsDouble(4.0, 334232, 3434);
			m2.setAsDouble(7.0, 0, 0);
			m2.setAsDouble(6.0, 1, 1);
			m2.setAsDouble(1.0, 3, 4);
			m2.setAsDouble(2.0, 4, 1);
			m2.setAsDouble(3.0, 3, 2);
			m2.setAsDouble(4.0, 2, 3);
			m2.setAsDouble(2.0, 2214, 334);
			m2.setAsDouble(3.0, 2215, 335);
			m2.setAsDouble(4.0, 334232, 3434);
			Matrix m3 = m1.mtimes(m2);
			assertEquals(35.0, m3.getAsDouble(0, 0), TOLERANCE);
			assertEquals(24.0, m3.getAsDouble(1, 1), TOLERANCE);
			assertEquals(2.0, m3.getAsDouble(3, 1), TOLERANCE);
			assertEquals(8.0, m3.getAsDouble(4, 1), TOLERANCE);
			assertEquals(8.0, m3.getAsDouble(4, 3), TOLERANCE);
		}
	}

	@Test
	public void testSparseMultiplySmall() throws Exception {
		Matrix m1 = createMatrix(2, 2);
		Matrix m2 = null;
		if (isTestSparse() && m1.isSparse()) {
			m1 = createMatrix(8, 9);
			m2 = createMatrix(9, 4);
			m1.setAsDouble(5.0, 0, 0);
			m1.setAsDouble(4.0, 1, 1);
			m1.setAsDouble(1.0, 3, 4);
			m1.setAsDouble(2.0, 4, 2);
			m1.setAsDouble(3.0, 3, 5);
			m1.setAsDouble(4.0, 4, 4);
			m2.setAsDouble(7.0, 0, 0);
			m2.setAsDouble(6.0, 1, 1);
			m2.setAsDouble(1.0, 3, 4);
			m2.setAsDouble(2.0, 4, 1);
			m2.setAsDouble(3.0, 3, 2);
			m2.setAsDouble(4.0, 2, 3);
			Matrix m3 = m1.mtimes(m2);
			Matrix m4 = m1.mtimes(Ret.LINK, true, m2);
			assertEquals(m3, m4);
		}
	}

	@Test
	public void testSparseIterator() throws Exception {
		Matrix m = createMatrix(2, 2);
		if (isTestSparse() && m.isSparse()) {
			m = createMatrix(800000, 900000);
			m.setAsDouble(1.0, 3, 4);
			m.setAsDouble(2.0, 334, 2214);
			m.setAsDouble(3.0, 335, 2215);
			m.setAsDouble(4.0, 334232, 3434);

			List<Coordinates> list = new ArrayList<Coordinates>();

			for (long[] c : m.nonZeroCoordinates()) {
				list.add(Coordinates.wrap(Coordinates.copyOf(c)));
			}

			assertEquals(4, list.size());
			assertTrue(list.contains(Coordinates.wrap(3, 4)));
			assertTrue(list.contains(Coordinates.wrap(334, 2214)));
			assertTrue(list.contains(Coordinates.wrap(335, 2215)));
			assertTrue(list.contains(Coordinates.wrap(334232, 3434)));

			m.setAsDouble(0.0, 335, 2215);

			list.clear();

			for (long[] c : m.nonZeroCoordinates()) {
				list.add(Coordinates.wrap(Coordinates.copyOf(c)));
			}

			assertEquals(3, list.size());
			assertTrue(list.contains(Coordinates.wrap(3, 4)));
			assertTrue(list.contains(Coordinates.wrap(334, 2214)));
			assertTrue(list.contains(Coordinates.wrap(334232, 3434)));
		}
	}

	@Test
	public void testSparseTranspose() throws Exception {
		Matrix m = createMatrix(2, 2);
		if (isTestSparse() && m.isSparse()) {
			m = createMatrix(800000, 900000);
			m.setAsDouble(1, 3, 4);
			m.setAsDouble(1, 334534, 4454);
			assertEquals(1.0, m.getAsDouble(3, 4), TOLERANCE);
			assertEquals(1.0, m.getAsDouble(334534, 4454), TOLERANCE);
			m = m.transpose();
			assertEquals(1.0, m.getAsDouble(4, 3), TOLERANCE);
			assertEquals(1.0, m.getAsDouble(4454, 334534), TOLERANCE);
		}
	}

	@Test
	public final void testExtractAnnotation() throws Exception {
		Matrix m1 = DenseDoubleMatrix2D.Factory.randn(5, 5);
		Matrix m2 = m1.extractAnnotation(Ret.NEW, Matrix.ROW);
		assertEquals(getLabel(), 4, m2.getRowCount());
		Matrix m3 = m2.includeAnnotation(Ret.NEW, Matrix.ROW);
		m3.setMetaData(null);
		assertEquals(getLabel(), m1, m3);

		m1 = DenseDoubleMatrix2D.Factory.randn(5, 5);
		m2 = m1.extractAnnotation(Ret.LINK, Matrix.ROW);
		assertEquals(getLabel(), 4, m2.getRowCount());
		m3 = m2.includeAnnotation(Ret.LINK, Matrix.ROW);
		m3.setMetaData(null);
		assertEquals(getLabel(), m1, m3);

		m1 = DenseDoubleMatrix2D.Factory.randn(5, 5);
		m2 = m1.extractAnnotation(Ret.NEW, Matrix.COLUMN);
		assertEquals(getLabel(), 4, m2.getColumnCount());
		m3 = m2.includeAnnotation(Ret.NEW, Matrix.COLUMN);
		m3.setMetaData(null);
		assertEquals(getLabel(), m1, m3);

		m1 = DenseDoubleMatrix2D.Factory.randn(5, 5);
		m2 = m1.extractAnnotation(Ret.LINK, Matrix.COLUMN);
		assertEquals(getLabel(), 4, m2.getColumnCount());
		m3 = m2.includeAnnotation(Ret.LINK, Matrix.COLUMN);
		m3.setMetaData(null);
		assertEquals(getLabel(), m1, m3);
	}

	// Test interface CoordinateFunctions
	@Test
	public final void testCoordinateIterator2D() throws Exception {
		Matrix m = createMatrixWithAnnotation(3, 3);
		m.setAsDouble(1.0, 2, 2);
		Iterator<long[]> ci = m.allCoordinates().iterator();
		long[] c1 = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c1, new long[] { 0, 0 }));
		long[] c2 = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c2, new long[] { 0, 1 }));
		long[] c3 = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c3, new long[] { 0, 2 }));
		long[] c4 = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c4, new long[] { 1, 0 }));
		long[] c5 = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c5, new long[] { 1, 1 }));
		long[] c6 = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c6, new long[] { 1, 2 }));
		long[] c7 = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c7, new long[] { 2, 0 }));
		long[] c8 = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c8, new long[] { 2, 1 }));
		long[] c9 = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c9, new long[] { 2, 2 }));
		assertFalse(getLabel(), ci.hasNext());

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public void testAvailableCoordinateIterator2D() throws Exception {
		Matrix m = getTestMatrix();

		List<Coordinates> clist = new ArrayList<Coordinates>();

		for (long[] c : m.availableCoordinates()) {
			clist.add(Coordinates.wrap(c).clone());
		}

		if (m.isSparse()) {
			assertEquals(getLabel(), 5, clist.size());
			assertTrue(getLabel(), clist.contains(Coordinates.wrap(0, 0)));
			assertTrue(getLabel(), clist.contains(Coordinates.wrap(1, 0)));
			assertTrue(getLabel(), clist.contains(Coordinates.wrap(1, 1)));
			assertTrue(getLabel(), clist.contains(Coordinates.wrap(2, 1)));
			assertTrue(getLabel(), clist.contains(Coordinates.wrap(1, 2)));
		} else {
			assertEquals(getLabel(), 9, clist.size());
			assertTrue(getLabel(), clist.contains(Coordinates.wrap(0, 0)));
			assertTrue(getLabel(), clist.contains(Coordinates.wrap(0, 1)));
			assertTrue(getLabel(), clist.contains(Coordinates.wrap(0, 2)));
			assertTrue(getLabel(), clist.contains(Coordinates.wrap(1, 0)));
			assertTrue(getLabel(), clist.contains(Coordinates.wrap(1, 1)));
			assertTrue(getLabel(), clist.contains(Coordinates.wrap(1, 2)));
			assertTrue(getLabel(), clist.contains(Coordinates.wrap(2, 0)));
			assertTrue(getLabel(), clist.contains(Coordinates.wrap(2, 1)));
			assertTrue(getLabel(), clist.contains(Coordinates.wrap(2, 2)));
		}

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}

	}

	@Test
	public final void testSelectedCoordinatesString() throws Exception {
		Matrix m = getTestMatrix();

		Matrix mTest = createMatrixWithAnnotation(2, 3);
		mTest.setAsObject(mTest.getAsObject(0, 0), 0, 0);
		mTest.setAsObject(mTest.getAsObject(0, 1), 0, 1);
		mTest.setAsObject(mTest.getAsObject(0, 2), 0, 2);
		mTest.setAsObject(mTest.getAsObject(1, 0), 1, 0);
		mTest.setAsObject(mTest.getAsObject(1, 1), 1, 1);
		mTest.setAsObject(mTest.getAsObject(1, 2), 1, 2);

		List<Coordinates> clist = new ArrayList<Coordinates>();

		for (long[] c : m.selectedCoordinates("1:2;:")) {
			clist.add(Coordinates.wrap(c).clone());
		}

		assertEquals(getLabel(), 6, clist.size());
		assertTrue(getLabel(), clist.contains(Coordinates.wrap(0, 0)));
		assertTrue(getLabel(), clist.contains(Coordinates.wrap(0, 1)));
		assertTrue(getLabel(), clist.contains(Coordinates.wrap(0, 2)));
		assertTrue(getLabel(), clist.contains(Coordinates.wrap(1, 0)));
		assertTrue(getLabel(), clist.contains(Coordinates.wrap(1, 1)));
		assertTrue(getLabel(), clist.contains(Coordinates.wrap(1, 2)));

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public final void testRowMajorDoubleArray2DConstructor() throws Exception {
		Matrix m = new ArrayDenseDoubleMatrix2D(23, 17);
		setAnnotation(m);
		m.randn(Ret.ORIG);
		Matrix m2 = createMatrixWithAnnotation(m);
		assertEquals(getLabel(), m, m2);

		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
	}

	@Test
	public final void testColumnMajorDoubleArray1DConstructor() throws Exception {
		Matrix m = new DefaultDenseDoubleMatrix2D(23, 17);
		setAnnotation(m);
		m.randn(Ret.ORIG);
		Matrix m2 = createMatrixWithAnnotation(m);
		assertEquals(getLabel(), m, m2);

		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
	}

	@Test
	public final void testConstructorWithAnnotation() throws Exception {
		Matrix m = createMatrixWithAnnotation(3, 3);
		Matrix m2 = createMatrix(m);
		assertTrue(getLabel(), m.equalsContent(m2));
		assertTrue(getLabel(), m.equalsAnnotation(m2));
		assertEquals(getLabel(), m, m2);
	}

	@Test
	public final void testOtherConstructor() throws Exception {
		Matrix m = new DefaultDenseDoubleMatrixMultiD(23, 17);
		setAnnotation(m);
		m.randn(Ret.ORIG);
		Matrix m2 = createMatrixWithAnnotation(m);
		assertEquals(getLabel(), m, m2);

		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
	}

	@Test
	public void testSelectedCoordinates() throws Exception {
		Matrix m = getTestMatrix();

		Matrix mTest = createMatrixWithAnnotation(2, 3);
		mTest.setAsObject(mTest.getAsObject(0, 0), 0, 0);
		mTest.setAsObject(mTest.getAsObject(0, 1), 0, 1);
		mTest.setAsObject(mTest.getAsObject(0, 2), 0, 2);
		mTest.setAsObject(mTest.getAsObject(1, 0), 1, 0);
		mTest.setAsObject(mTest.getAsObject(1, 1), 1, 1);
		mTest.setAsObject(mTest.getAsObject(1, 2), 1, 2);

		Iterator<long[]> ci = m.selectedCoordinates(new long[] { 0, 1 }, new long[] { 0, 1, 2 })
				.iterator();
		long[] c = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c, new long[] { 0, 0 }));
		c = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c, new long[] { 0, 1 }));
		c = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c, new long[] { 0, 2 }));
		c = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c, new long[] { 1, 0 }));
		c = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c, new long[] { 1, 1 }));
		c = ci.next();
		assertTrue(getLabel(), Coordinates.equals(c, new long[] { 1, 2 }));
		assertFalse(getLabel(), ci.hasNext());

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public final void testGetCoordinatesOfMaximum() throws Exception {
		Matrix m = getTestMatrix();
		long[] c = m.getCoordinatesOfMaximum();
		assertTrue(getLabel(), Coordinates.equals(c, new long[] { 1, 1 }));

		m = createMatrixWithAnnotation(2, 2);
		m.setAsDouble(Double.NaN, 0, 0);
		m.setAsDouble(Double.NaN, 0, 1);
		m.setAsDouble(Double.NaN, 1, 0);
		m.setAsDouble(Double.NaN, 1, 1);
		c = m.getCoordinatesOfMaximum();
		assertTrue(getLabel(), Coordinates.equals(c, new long[] { -1, -1 }));

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public final void testGetCoordinatesOfMininim() throws Exception {
		Matrix m = getTestMatrix();
		long[] c = m.getCoordinatesOfMinimum();
		assertTrue(getLabel(), Coordinates.equals(c, new long[] { 1, 2 }));

		m = createMatrixWithAnnotation(2, 2);
		m.setAsDouble(Double.NaN, 0, 0);
		m.setAsDouble(Double.NaN, 0, 1);
		m.setAsDouble(Double.NaN, 1, 0);
		m.setAsDouble(Double.NaN, 1, 1);
		c = m.getCoordinatesOfMaximum();
		assertTrue(getLabel(), Coordinates.equals(c, new long[] { -1, -1 }));

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public final void testContains() throws Exception {
		Matrix m = getTestMatrix();

		assertTrue(m.containsCoordinates(0, 0));

		if (m.isSparse()) {
			assertFalse(m.containsCoordinates(0, 1));
		} else {
			assertTrue(m.containsCoordinates(0, 1));
		}

		if (m.isSparse()) {
			assertFalse(m.containsCoordinates(0, 2));
		} else {
			assertTrue(m.containsCoordinates(0, 2));
		}

		assertTrue(m.containsCoordinates(1, 0));
		assertTrue(m.containsCoordinates(1, 1));

		if (m.isSparse()) {
			assertFalse(m.containsCoordinates(0, 1));
		} else {
			assertTrue(m.containsCoordinates(1, 2));
		}

		if (m.isSparse()) {
			assertFalse(m.containsCoordinates(0, 1));
		} else {
			assertTrue(m.containsCoordinates(2, 0));
		}

		assertTrue(m.containsCoordinates(2, 1));

		if (m.isSparse()) {
			assertFalse(m.containsCoordinates(0, 1));
		} else {
			assertTrue(m.containsCoordinates(2, 2));
		}

		assertFalse(m.containsCoordinates(7, 7));

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public final void testSize() throws Exception {
		Matrix m = createMatrixWithAnnotation(21, 12);
		m.setAsDouble(1.0, 20, 11);
		assertEquals(getLabel(), 21, m.getRowCount());
		assertEquals(getLabel(), 12, m.getColumnCount());

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public final void testZeros() throws Exception {
		Matrix m = createMatrixWithAnnotation(21, 12);

		for (long[] c : m.allCoordinates()) {
			assertEquals(getLabel(), 0.0, m.getAsDouble(c), 0.0);
		}

		for (long[] c : m.availableCoordinates()) {
			assertEquals(getLabel(), 0.0, m.getAsDouble(c), 0.0);
		}

		m.fill(Ret.ORIG, 0.0);

		for (long[] c : m.allCoordinates()) {
			assertEquals(getLabel(), 0.0, m.getAsDouble(c), 0.0);
		}

		for (long[] c : m.availableCoordinates()) {
			assertEquals(getLabel(), 0.0, m.getAsDouble(c), 0.0);
		}

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public final void testNaN() throws Exception {
		Matrix m = createMatrixWithAnnotation(21, 12);

		for (long[] c : m.allCoordinates()) {
			assertEquals(getLabel(), 0.0, m.getAsDouble(c), 0.0);
		}

		for (long[] c : m.availableCoordinates()) {
			assertEquals(getLabel(), 0.0, m.getAsDouble(c), 0.0);
		}

		m.fill(Ret.ORIG, Double.NaN);

		for (long[] c : m.allCoordinates()) {
			assertTrue(getLabel(), Double.isNaN(m.getAsDouble(c)));
		}

		for (long[] c : m.availableCoordinates()) {
			assertTrue(getLabel(), Double.isNaN(m.getAsDouble(c)));
		}

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public final void testClone() throws Exception {
		Matrix m = createMatrixWithAnnotation(2, 2);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		Matrix m2 = m.clone();
		assertTrue(getLabel(), m.equalsContent(m2));
		assertTrue(getLabel(), m.equalsAnnotation(m2));

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public final void testAnnotation() throws Exception {
		Matrix m = createMatrixWithAnnotation(2, 2);
		compareAnnotation(m);

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public final void testCountMissingValues() throws Exception {
		Matrix m = createMatrixWithAnnotation(4, 4);
		m = m.zeros(Ret.ORIG);
		m.setAsDouble(Double.NaN, 1, 0);
		m.setAsDouble(Double.NaN, 3, 0);
		m.setAsDouble(Double.NaN, 2, 1);
		m.setAsDouble(Double.NaN, 1, 1);
		m.setAsDouble(Double.NaN, 3, 1);
		m.setAsDouble(Double.NaN, 1, 2);

		Matrix m1 = m.countMissing(Ret.NEW, Matrix.ROW);
		Matrix m2 = m.countMissing(Ret.NEW, Matrix.COLUMN);
		Matrix m3 = m.countMissing(Ret.NEW, Matrix.ALL);

		assertEquals(getLabel(), 2.0, m1.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 3.0, m1.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 1.0, m1.getAsDouble(0, 2), TOLERANCE);
		assertEquals(getLabel(), 0.0, m1.getAsDouble(0, 3), TOLERANCE);

		assertEquals(getLabel(), 0.0, m2.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 3.0, m2.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 1.0, m2.getAsDouble(2, 0), TOLERANCE);
		assertEquals(getLabel(), 2.0, m2.getAsDouble(3, 0), TOLERANCE);

		assertEquals(getLabel(), 6.0, m3.getAsDouble(0, 0), TOLERANCE);

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public final void testSerialize() throws Exception {
		Matrix m = createMatrixWithAnnotation(2, 2);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		byte[] data = SerializationUtil.serialize(m);
		Matrix m2 = (Matrix) SerializationUtil.deserialize(data);
		if (m2.isTransient()) {
			Matrix m0 = Matrix.Factory.zeros(2, 2);
			assertEquals(getLabel(), m0, m2);
		} else {
			assertEquals(getLabel(), m, m2);
		}

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public final void testToDoubleArray() throws Exception {
		Matrix m = createMatrixWithAnnotation(2, 2);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		double[][] values = m.toDoubleArray();
		assertEquals(getLabel(), 1.0, values[0][0], TOLERANCE);
		assertEquals(getLabel(), 2.0, values[0][1], TOLERANCE);
		assertEquals(getLabel(), 3.0, values[1][0], TOLERANCE);
		assertEquals(getLabel(), 4.0, values[1][1], TOLERANCE);

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public final void testSetAndGet() throws Exception {
		Matrix m = createMatrixWithAnnotation(2, 2);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		assertEquals(getLabel(), 1.0, m.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 2.0, m.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 3.0, m.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, m.getAsDouble(1, 1), TOLERANCE);

		m.setAsDouble(0.0, 0, 1);
		m.setAsDouble(4.0, 1, 0);

		assertEquals(getLabel(), 1.0, m.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 0.0, m.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 4.0, m.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, m.getAsDouble(1, 1), TOLERANCE);

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public final void testPlusScalarSmall() throws Exception {
		Matrix m = createMatrixWithAnnotation(2, 2);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		Matrix r = m.plus(1.0);

		assertEquals(getLabel(), 2.0, r.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 3.0, r.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 4.0, r.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 5.0, r.getAsDouble(1, 1), TOLERANCE);

		assertEquals(getLabel(), 1.0, m.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 2.0, m.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 3.0, m.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, m.getAsDouble(1, 1), TOLERANCE);

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
		if (r instanceof Erasable) {
			((Erasable) r).erase();
		}
	}

	@Test
	public final void testPlusScalarLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix m = createMatrixWithAnnotation(119, 119);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		Matrix r = m.plus(1.0);

		assertEquals(getLabel(), 2.0, r.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 3.0, r.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 4.0, r.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 5.0, r.getAsDouble(1, 1), TOLERANCE);

		assertEquals(getLabel(), 1.0, m.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 2.0, m.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 3.0, m.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, m.getAsDouble(1, 1), TOLERANCE);

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
		if (r instanceof Erasable) {
			((Erasable) r).erase();
		}
	}

	@Test
	public final void testPlusMatrixSmall() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(2, 2);
		Matrix m2 = createMatrixWithAnnotation(2, 2);
		m1.setAsDouble(1.0, 0, 0);
		m1.setAsDouble(2.0, 0, 1);
		m1.setAsDouble(3.0, 1, 0);
		m1.setAsDouble(4.0, 1, 1);
		m2.setAsDouble(1.0, 0, 0);
		m2.setAsDouble(1.0, 0, 1);
		m2.setAsDouble(1.0, 1, 0);
		m2.setAsDouble(1.0, 1, 1);
		Matrix r = m1.plus(m2);

		assertEquals(getLabel(), 2.0, r.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 3.0, r.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 4.0, r.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 5.0, r.getAsDouble(1, 1), TOLERANCE);

		assertEquals(getLabel(), 1.0, m1.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 2.0, m1.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 3.0, m1.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, m1.getAsDouble(1, 1), TOLERANCE);

		assertEquals(getLabel(), 1.0, m2.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 1.0, m2.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 1.0, m2.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 1.0, m2.getAsDouble(1, 1), TOLERANCE);

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}
		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
		if (r instanceof Erasable) {
			((Erasable) r).erase();
		}
	}

	@Test
	public final void testPlusMatrixLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix m1 = createMatrixWithAnnotation(121, 111);
		Matrix m2 = createMatrixWithAnnotation(121, 111);
		m1.setAsDouble(1.0, 0, 0);
		m1.setAsDouble(2.0, 0, 1);
		m1.setAsDouble(3.0, 1, 0);
		m1.setAsDouble(4.0, 1, 1);
		m2.setAsDouble(1.0, 0, 0);
		m2.setAsDouble(1.0, 0, 1);
		m2.setAsDouble(1.0, 1, 0);
		m2.setAsDouble(1.0, 1, 1);
		Matrix r = m1.plus(m2);

		assertEquals(getLabel(), 2.0, r.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 3.0, r.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 4.0, r.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 5.0, r.getAsDouble(1, 1), TOLERANCE);

		assertEquals(getLabel(), 1.0, m1.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 2.0, m1.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 3.0, m1.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, m1.getAsDouble(1, 1), TOLERANCE);

		assertEquals(getLabel(), 1.0, m2.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 1.0, m2.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 1.0, m2.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 1.0, m2.getAsDouble(1, 1), TOLERANCE);

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}
		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
		if (r instanceof Erasable) {
			((Erasable) r).erase();
		}
	}

	@Test
	public final void testTransposeSmall() throws Exception {
		// TODO: check labels
		Matrix m = createMatrixWithAnnotation(2, 3);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 0, 2);
		m.setAsDouble(4.0, 1, 0);
		m.setAsDouble(5.0, 1, 1);
		m.setAsDouble(6.0, 1, 2);
		Matrix r = m.transpose();
		assertEquals(getLabel(), m.getRowCount(), r.getColumnCount());
		assertEquals(getLabel(), m.getColumnCount(), r.getRowCount());
		assertEquals(getLabel(), 1.0, r.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, r.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 2.0, r.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 5.0, r.getAsDouble(1, 1), TOLERANCE);
		assertEquals(getLabel(), 3.0, r.getAsDouble(2, 0), TOLERANCE);
		assertEquals(getLabel(), 6.0, r.getAsDouble(2, 1), TOLERANCE);

		assertEquals(getLabel(), 1.0, m.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 2.0, m.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 3.0, m.getAsDouble(0, 2), TOLERANCE);
		assertEquals(getLabel(), 4.0, m.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 5.0, m.getAsDouble(1, 1), TOLERANCE);
		assertEquals(getLabel(), 6.0, m.getAsDouble(1, 2), TOLERANCE);

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public final void testTransposeLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		// TODO: check labels
		Matrix m = createMatrixWithAnnotation(111, 101);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 0, 2);
		m.setAsDouble(4.0, 1, 0);
		m.setAsDouble(5.0, 1, 1);
		m.setAsDouble(6.0, 1, 2);
		Matrix r = m.transpose();
		assertEquals(getLabel(), m.getRowCount(), r.getColumnCount());
		assertEquals(getLabel(), m.getColumnCount(), r.getRowCount());
		assertEquals(getLabel(), 1.0, r.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, r.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 2.0, r.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 5.0, r.getAsDouble(1, 1), TOLERANCE);
		assertEquals(getLabel(), 3.0, r.getAsDouble(2, 0), TOLERANCE);
		assertEquals(getLabel(), 6.0, r.getAsDouble(2, 1), TOLERANCE);

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public final void testTransposeNewSmall() throws Exception {
		Matrix m = createMatrixWithAnnotation(2, 3);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 0, 2);
		m.setAsDouble(4.0, 1, 0);
		m.setAsDouble(5.0, 1, 1);
		m.setAsDouble(6.0, 1, 2);
		m.setLabel("label");
		m.setRowLabel(1, "row1");
		m.setColumnLabel(2, "col2");
		Matrix r = m.transpose(Ret.NEW);
		assertEquals(getLabel(), m.getRowCount(), r.getColumnCount());
		assertEquals(getLabel(), m.getColumnCount(), r.getRowCount());
		assertEquals(getLabel(), 1.0, r.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, r.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 2.0, r.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 5.0, r.getAsDouble(1, 1), TOLERANCE);
		assertEquals(getLabel(), 3.0, r.getAsDouble(2, 0), TOLERANCE);
		assertEquals(getLabel(), 6.0, r.getAsDouble(2, 1), TOLERANCE);
		assertEquals(getLabel(), "label", r.getLabel());
		assertEquals(getLabel(), "row1", r.getColumnLabel(1));
		assertEquals(getLabel(), "col2", r.getRowLabel(2));

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public final void testTransposeLinkSmall() throws Exception {
		Matrix m = createMatrixWithAnnotation(2, 3);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 0, 2);
		m.setAsDouble(4.0, 1, 0);
		m.setAsDouble(5.0, 1, 1);
		m.setAsDouble(6.0, 1, 2);
		m.setLabel("label");
		m.setRowLabel(1, "row1");
		m.setColumnLabel(2, "col2");
		Matrix r = m.transpose(Ret.LINK);
		assertEquals(getLabel(), m.getRowCount(), r.getColumnCount());
		assertEquals(getLabel(), m.getColumnCount(), r.getRowCount());
		assertEquals(getLabel(), 1.0, r.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, r.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 2.0, r.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 5.0, r.getAsDouble(1, 1), TOLERANCE);
		assertEquals(getLabel(), 3.0, r.getAsDouble(2, 0), TOLERANCE);
		assertEquals(getLabel(), 6.0, r.getAsDouble(2, 1), TOLERANCE);
		assertEquals(getLabel(), "label", r.getLabel());
		assertEquals(getLabel(), "row1", r.getColumnLabel(1));
		assertEquals(getLabel(), "col2", r.getRowLabel(2));

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public final void testEmpty() throws Exception {
		Matrix m = createMatrixWithAnnotation(2, 2);
		if (m instanceof AbstractDoubleMatrix) {
			assertEquals(getLabel(), 0.0, m.getAsDouble(0, 0), TOLERANCE);
			assertEquals(getLabel(), 0.0, m.getAsDouble(0, 1), TOLERANCE);
			assertEquals(getLabel(), 0.0, m.getAsDouble(1, 0), TOLERANCE);
			assertEquals(getLabel(), 0.0, m.getAsDouble(1, 1), TOLERANCE);
		} else {
			assertEquals(getLabel(), null, m.getAsObject(0, 0));
			assertEquals(getLabel(), null, m.getAsObject(0, 1));
			assertEquals(getLabel(), null, m.getAsObject(1, 0));
			assertEquals(getLabel(), null, m.getAsObject(1, 1));
		}

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public final void testMinusScalarSmall() throws Exception {
		Matrix m = createMatrixWithAnnotation(2, 2);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		Matrix r = m.minus(1.0);
		assertEquals(getLabel(), 0.0, r.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 1.0, r.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 2.0, r.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 3.0, r.getAsDouble(1, 1), TOLERANCE);

		assertEquals(getLabel(), 1.0, m.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 2.0, m.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 3.0, m.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, m.getAsDouble(1, 1), TOLERANCE);

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
		if (r instanceof Erasable) {
			((Erasable) r).erase();
		}
	}

	@Test
	public void testMinusScalarLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix m = createMatrixWithAnnotation(124, 114);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		Matrix r = m.minus(1.0);
		assertEquals(getLabel(), 0.0, r.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 1.0, r.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 2.0, r.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 3.0, r.getAsDouble(1, 1), TOLERANCE);

		assertEquals(getLabel(), 1.0, m.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 2.0, m.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 3.0, m.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, m.getAsDouble(1, 1), TOLERANCE);

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
		if (r instanceof Erasable) {
			((Erasable) r).erase();
		}
	}

	@Test
	public void testTimesScalarSmall() throws Exception {
		Matrix m = createMatrixWithAnnotation(2, 2);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		Matrix r = m.times(2.0);
		assertEquals(getLabel(), 2.0, r.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, r.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 6.0, r.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 8.0, r.getAsDouble(1, 1), TOLERANCE);

		assertEquals(getLabel(), 1.0, m.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 2.0, m.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 3.0, m.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, m.getAsDouble(1, 1), TOLERANCE);

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
		if (r instanceof Erasable) {
			((Erasable) r).erase();
		}
	}

	@Test
	public final void testTimesScalarLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix m = createMatrixWithAnnotation(128, 113);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		Matrix r = m.times(2.0);
		assertEquals(getLabel(), 2.0, r.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, r.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 6.0, r.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 8.0, r.getAsDouble(1, 1), TOLERANCE);

		assertEquals(getLabel(), 1.0, m.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 2.0, m.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 3.0, m.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, m.getAsDouble(1, 1), TOLERANCE);

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
		if (r instanceof Erasable) {
			((Erasable) r).erase();
		}
	}

	@Test
	public final void testDivideScalarSmall() throws Exception {
		Matrix m = createMatrixWithAnnotation(2, 2);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		Matrix r = m.divide(2.0);
		assertEquals(getLabel(), 0.5, r.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 1.0, r.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 1.5, r.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 2.0, r.getAsDouble(1, 1), TOLERANCE);

		assertEquals(getLabel(), 1.0, m.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 2.0, m.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 3.0, m.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, m.getAsDouble(1, 1), TOLERANCE);

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
		if (r instanceof Erasable) {
			((Erasable) r).erase();
		}
	}

	@Test
	public final void testDivideScalarLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix m = createMatrixWithAnnotation(2, 2);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		Matrix r = m.divide(2.0);
		assertEquals(getLabel(), 0.5, r.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 1.0, r.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 1.5, r.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 2.0, r.getAsDouble(1, 1), TOLERANCE);

		assertEquals(getLabel(), 1.0, m.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 2.0, m.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 3.0, m.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, m.getAsDouble(1, 1), TOLERANCE);

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
		if (r instanceof Erasable) {
			((Erasable) r).erase();
		}
	}

	@Test
	public final void testMinusMatrixSmall() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(2, 2);
		Matrix m2 = createMatrixWithAnnotation(2, 2);
		m1.setAsDouble(1.0, 0, 0);
		m1.setAsDouble(2.0, 0, 1);
		m1.setAsDouble(3.0, 1, 0);
		m1.setAsDouble(4.0, 1, 1);
		m2.setAsDouble(0.0, 0, 0);
		m2.setAsDouble(1.0, 0, 1);
		m2.setAsDouble(2.0, 1, 0);
		m2.setAsDouble(3.0, 1, 1);
		Matrix r = m1.minus(m2);
		assertEquals(getLabel(), 1.0, r.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 1.0, r.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 1.0, r.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 1.0, r.getAsDouble(1, 1), TOLERANCE);

		assertEquals(getLabel(), 1.0, m1.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 2.0, m1.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 3.0, m1.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, m1.getAsDouble(1, 1), TOLERANCE);

		assertEquals(getLabel(), 0.0, m2.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 1.0, m2.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 2.0, m2.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 3.0, m2.getAsDouble(1, 1), TOLERANCE);

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}
		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
		if (r instanceof Erasable) {
			((Erasable) r).erase();
		}
	}

	@Test
	public final void testMinusMatrixLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix m1 = createMatrixWithAnnotation(123, 109);
		Matrix m2 = createMatrixWithAnnotation(123, 109);
		m1.setAsDouble(1.0, 0, 0);
		m1.setAsDouble(2.0, 0, 1);
		m1.setAsDouble(3.0, 1, 0);
		m1.setAsDouble(4.0, 1, 1);
		m2.setAsDouble(0.0, 0, 0);
		m2.setAsDouble(1.0, 0, 1);
		m2.setAsDouble(2.0, 1, 0);
		m2.setAsDouble(3.0, 1, 1);
		Matrix r = m1.minus(m2);
		assertEquals(getLabel(), 1.0, r.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 1.0, r.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 1.0, r.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 1.0, r.getAsDouble(1, 1), TOLERANCE);

		assertEquals(getLabel(), 1.0, m1.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 2.0, m1.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 3.0, m1.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, m1.getAsDouble(1, 1), TOLERANCE);

		assertEquals(getLabel(), 0.0, m2.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 1.0, m2.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 2.0, m2.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 3.0, m2.getAsDouble(1, 1), TOLERANCE);

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}
		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
		if (r instanceof Erasable) {
			((Erasable) r).erase();
		}
	}

	@Test
	public final void test0PlusXMatrix() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(5, 7);
		Matrix m2 = createMatrixWithAnnotation(5, 7);
		m2.randn(Ret.ORIG);
		Matrix m3 = m1.plus(m2);
		assertEquals(getLabel(), m2, m3);
		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}
		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
		if (m3 instanceof Erasable) {
			((Erasable) m3).erase();
		}
	}

	@Test
	public final void testXPlus0MatrixMatrix() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(5, 7);
		Matrix m2 = createMatrixWithAnnotation(5, 7);
		m1.randn(Ret.ORIG);
		Matrix m3 = m1.plus(m2);
		assertEquals(getLabel(), m1, m3);
		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}
		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
		if (m3 instanceof Erasable) {
			((Erasable) m3).erase();
		}
	}

	@Test
	public final void test0Plus0Matrix() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(5, 7);
		Matrix m2 = createMatrixWithAnnotation(5, 7);
		Matrix m3 = m1.plus(m2);
		assertTrue(getLabel(), m3.isEmpty());
		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}
		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
		if (m3 instanceof Erasable) {
			((Erasable) m3).erase();
		}
	}

	@Test
	public final void test0Minus0Matrix() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(5, 7);
		Matrix m2 = createMatrixWithAnnotation(5, 7);
		Matrix m3 = m1.minus(m2);
		assertTrue(getLabel(), m3.isEmpty());
		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}
		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
		if (m3 instanceof Erasable) {
			((Erasable) m3).erase();
		}
	}

	@Test
	public final void testToDoubleArraySmall() throws Exception {
		Matrix m = createMatrixWithAnnotation(6, 7);
		m.randn(Ret.ORIG);

		double[][] array = m.toDoubleArray();

		for (int r = 0; r < m.getRowCount(); r++) {
			for (int c = 0; c < m.getColumnCount(); c++) {
				assertEquals(getLabel(), m.getAsDouble(r, c), array[r][c], 0.0);
			}
		}

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public final void testToDoubleArrayLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix m = createMatrixWithAnnotation(106, 117);
		m.randn(Ret.ORIG);

		double[][] array = m.toDoubleArray();

		for (int r = 0; r < m.getRowCount(); r++) {
			for (int c = 0; c < m.getColumnCount(); c++) {
				assertEquals(getLabel(), m.getAsDouble(r, c), array[r][c], 0.0);
			}
		}

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public final void testXMinus0Matrix() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(5, 7);
		Matrix m2 = createMatrixWithAnnotation(5, 7);
		m1.randn(Ret.ORIG);
		Matrix m3 = m1.minus(m2);
		assertEquals(getLabel(), m1, m3);

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}
		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
		if (m3 instanceof Erasable) {
			((Erasable) m3).erase();
		}
	}

	@Test
	public final void test0MinusXMatrix() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(5, 7);
		Matrix m2 = createMatrixWithAnnotation(5, 7);
		m2.randn(Ret.ORIG);
		Matrix m3 = m1.minus(m2);
		Matrix m4 = m2.times(-1);
		assertEquals(getLabel(), m4, m3);

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}
		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
		if (m3 instanceof Erasable) {
			((Erasable) m3).erase();
		}
		if (m4 instanceof Erasable) {
			((Erasable) m4).erase();
		}
	}

	@Test
	public final void test0TimesXMatrix() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(5, 7);
		Matrix m2 = createMatrixWithAnnotation(5, 7);
		m2.randn(Ret.ORIG);
		Matrix m3 = m1.times(m2);
		assertTrue(getLabel(), m3.isEmpty());

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}
		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
		if (m3 instanceof Erasable) {
			((Erasable) m3).erase();
		}
	}

	@Test
	public final void testXTimes0Matrix() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(5, 7);
		Matrix m2 = createMatrixWithAnnotation(5, 7);
		m1.randn(Ret.ORIG);
		Matrix m3 = m1.times(m2);
		assertTrue(getLabel(), m3.isEmpty());

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}
		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
		if (m3 instanceof Erasable) {
			((Erasable) m3).erase();
		}
	}

	@Test
	public final void test0Times0Matrix() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(5, 7);
		Matrix m2 = createMatrixWithAnnotation(5, 7);
		Matrix m3 = m1.times(m2);
		assertTrue(getLabel(), m3.isEmpty());

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}
		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
		if (m3 instanceof Erasable) {
			((Erasable) m3).erase();
		}
	}

	@Test
	public final void test0Divide0Matrix() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(5, 7);
		Matrix m2 = createMatrixWithAnnotation(5, 7);
		Matrix m3 = m1.divide(m2);
		assertEquals(getLabel(), 35, m3.countMissing(Ret.NEW, Matrix.ALL).intValue());

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}
		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
		if (m3 instanceof Erasable) {
			((Erasable) m3).erase();
		}
	}

	@Test
	public final void testXDivide0Matrix() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(5, 7);
		Matrix m2 = createMatrixWithAnnotation(5, 7);
		m1.randn(Ret.ORIG);
		Matrix m3 = m1.divide(m2);
		assertEquals(getLabel(), 35, m3.countMissing(Ret.NEW, Matrix.ALL).intValue());

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}
		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
		if (m3 instanceof Erasable) {
			((Erasable) m3).erase();
		}
	}

	@Test
	public final void test0DivideXMatrix() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(5, 7);
		Matrix m2 = createMatrixWithAnnotation(5, 7);
		m2.randn(Ret.ORIG);
		Matrix m3 = m1.divide(m2);
		assertTrue(getLabel(), m3.isEmpty());

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}
		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
		if (m3 instanceof Erasable) {
			((Erasable) m3).erase();
		}
	}

	@Test
	public final void testMTimesFixedSmall() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(2, 2);

		m1.setAsDouble(-1.0, 0, 0);
		m1.setAsDouble(2.0, 0, 1);
		m1.setAsDouble(-3.0, 1, 0);
		m1.setAsDouble(4.0, 1, 1);
		Matrix m2 = createMatrixWithAnnotation(2, 3);
		m2.setAsDouble(1.0, 0, 0);
		m2.setAsDouble(-2.0, 0, 1);
		m2.setAsDouble(3.0, 0, 2);
		m2.setAsDouble(-4.0, 1, 0);
		m2.setAsDouble(5.0, 1, 1);
		m2.setAsDouble(-6.0, 1, 2);

		Matrix m3 = m1.mtimes(m2);

		assertEquals(getLabel(), 2, m3.getRowCount());
		assertEquals(getLabel(), 3, m3.getColumnCount());
		assertEquals(getLabel(), -9.0, m3.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 12.0, m3.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), -15.0, m3.getAsDouble(0, 2), TOLERANCE);
		assertEquals(getLabel(), -19.0, m3.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 26.0, m3.getAsDouble(1, 1), TOLERANCE);
		assertEquals(getLabel(), -33.0, m3.getAsDouble(1, 2), TOLERANCE);

		assertEquals(getLabel(), -1.0, m1.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 2.0, m1.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), -3.0, m1.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, m1.getAsDouble(1, 1), TOLERANCE);

		assertEquals(getLabel(), 1.0, m2.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), -2.0, m2.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 3.0, m2.getAsDouble(0, 2), TOLERANCE);
		assertEquals(getLabel(), -4.0, m2.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 5.0, m2.getAsDouble(1, 1), TOLERANCE);
		assertEquals(getLabel(), -6.0, m2.getAsDouble(1, 2), TOLERANCE);

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}

		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
	}

	@Test
	public final void testMTimesFixedLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix m1 = createMatrixWithAnnotation(109, 111);

		m1.setAsDouble(-1.0, 0, 0);
		m1.setAsDouble(2.0, 0, 1);
		m1.setAsDouble(-3.0, 1, 0);
		m1.setAsDouble(4.0, 1, 1);
		Matrix m2 = createMatrixWithAnnotation(111, 127);
		m2.setAsDouble(1.0, 0, 0);
		m2.setAsDouble(-2.0, 0, 1);
		m2.setAsDouble(3.0, 0, 2);
		m2.setAsDouble(-4.0, 1, 0);
		m2.setAsDouble(5.0, 1, 1);
		m2.setAsDouble(-6.0, 1, 2);

		Matrix m3 = m1.mtimes(m2);

		assertEquals(getLabel(), 109, m3.getRowCount());
		assertEquals(getLabel(), 127, m3.getColumnCount());
		assertEquals(getLabel(), -9.0, m3.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 12.0, m3.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), -15.0, m3.getAsDouble(0, 2), TOLERANCE);
		assertEquals(getLabel(), -19.0, m3.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 26.0, m3.getAsDouble(1, 1), TOLERANCE);
		assertEquals(getLabel(), -33.0, m3.getAsDouble(1, 2), TOLERANCE);

		assertEquals(getLabel(), -1.0, m1.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), 2.0, m1.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), -3.0, m1.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 4.0, m1.getAsDouble(1, 1), TOLERANCE);

		assertEquals(getLabel(), 1.0, m2.getAsDouble(0, 0), TOLERANCE);
		assertEquals(getLabel(), -2.0, m2.getAsDouble(0, 1), TOLERANCE);
		assertEquals(getLabel(), 3.0, m2.getAsDouble(0, 2), TOLERANCE);
		assertEquals(getLabel(), -4.0, m2.getAsDouble(1, 0), TOLERANCE);
		assertEquals(getLabel(), 5.0, m2.getAsDouble(1, 1), TOLERANCE);
		assertEquals(getLabel(), -6.0, m2.getAsDouble(1, 2), TOLERANCE);

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}

		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
	}

	@Test
	public final void testInvRandSmall() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(10, 10);

		do {
			m1.randn(Ret.ORIG);
		} while (m1.isSingular());

		Matrix m2 = m1.inv();
		Matrix m3 = m1.mtimes(m2);
		Matrix eye = DenseDoubleMatrix2D.Factory.eye(m1.getRowCount(), m1.getColumnCount());
		assertEquals(getLabel(), 0.0, eye.minus(m3).getEuklideanValue(), TOLERANCE);

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}
		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
		if (m3 instanceof Erasable) {
			((Erasable) m3).erase();
		}
		if (eye instanceof Erasable) {
			((Erasable) eye).erase();
		}
	}

	@Test
	public final void testInvSPDRandSmall() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(10, 10);

		BenchmarkUtil.randPositiveDefinite(System.currentTimeMillis(), 0, 0, m1);

		Matrix m2 = m1.invSPD();
		Matrix m3 = m1.mtimes(m2);
		Matrix eye = DenseDoubleMatrix2D.Factory.eye(m1.getRowCount(), m1.getColumnCount());
		assertEquals(getLabel(), 0.0, eye.minus(m3).getEuklideanValue(), TOLERANCE);

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}
		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
		if (m3 instanceof Erasable) {
			((Erasable) m3).erase();
		}
		if (eye instanceof Erasable) {
			((Erasable) eye).erase();
		}
	}

	@Test
	public final void testInvRandLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix m1 = createMatrixWithAnnotation(128, 128);

		do {
			m1.randn(Ret.ORIG);
		} while (m1.isSingular());

		Matrix m2 = m1.inv();
		Matrix m3 = m1.mtimes(m2);
		Matrix eye = DenseDoubleMatrix2D.Factory.eye(m1.getRowCount(), m1.getColumnCount());
		assertEquals(getLabel(), 0.0, eye.minus(m3).getEuklideanValue(), TOLERANCE);

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}
		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
		if (m3 instanceof Erasable) {
			((Erasable) m3).erase();
		}
		if (eye instanceof Erasable) {
			((Erasable) eye).erase();
		}
	}

	@Test
	public final void testInvSPDRandLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix m1 = createMatrixWithAnnotation(128, 128);

		BenchmarkUtil.randPositiveDefinite(System.currentTimeMillis(), 0, 0, m1);

		Matrix m2 = m1.invSPD();
		Matrix m3 = m1.mtimes(m2);
		Matrix eye = DenseDoubleMatrix2D.Factory.eye(m1.getRowCount(), m1.getColumnCount());
		assertEquals(getLabel(), 0.0, eye.minus(m3).getEuklideanValue(), TOLERANCE);

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}
		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
		if (m3 instanceof Erasable) {
			((Erasable) m3).erase();
		}
		if (eye instanceof Erasable) {
			((Erasable) eye).erase();
		}
	}

	public final void testInvFixedSmall() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(3, 3);

		if (!isSupported(m1, MatrixLibraries.INV, MatrixLayout.SQUARE, null)) {
			return;
		}

		m1.setAsDouble(1.0, 0, 0);
		m1.setAsDouble(2.0, 1, 0);
		m1.setAsDouble(3.0, 2, 0);
		m1.setAsDouble(4.0, 0, 1);
		m1.setAsDouble(1.0, 1, 1);
		m1.setAsDouble(2.0, 2, 1);
		m1.setAsDouble(3.0, 0, 2);
		m1.setAsDouble(7.0, 1, 2);
		m1.setAsDouble(1.0, 2, 2);

		Matrix m2 = m1.inv();

		assertEquals(getLabel(), -0.1970, m2.getAsDouble(0, 0), 0.001);
		assertEquals(getLabel(), 0.2879, m2.getAsDouble(1, 0), 0.001);
		assertEquals(getLabel(), 0.0152, m2.getAsDouble(2, 0), 0.001);
		assertEquals(getLabel(), 0.0303, m2.getAsDouble(0, 1), 0.001);
		assertEquals(getLabel(), -0.1212, m2.getAsDouble(1, 1), 0.001);
		assertEquals(getLabel(), 0.1515, m2.getAsDouble(2, 1), 0.001);
		assertEquals(getLabel(), 0.3788, m2.getAsDouble(0, 2), 0.001);
		assertEquals(getLabel(), -0.0152, m2.getAsDouble(1, 2), 0.001);
		assertEquals(getLabel(), -0.1061, m2.getAsDouble(2, 2), 0.001);

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}

		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
	}

	@Test
	public final void testPinvFixedSmall() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(3, 3);

		if (!isSupported(m1, MatrixLibraries.SVD, MatrixLayout.SQUARE, EntryType.RANDN)) {
			return;
		}

		m1.setAsDouble(1.0, 0, 0);
		m1.setAsDouble(2.0, 1, 0);
		m1.setAsDouble(3.0, 2, 0);
		m1.setAsDouble(4.0, 0, 1);
		m1.setAsDouble(1.0, 1, 1);
		m1.setAsDouble(2.0, 2, 1);
		m1.setAsDouble(3.0, 0, 2);
		m1.setAsDouble(7.0, 1, 2);
		m1.setAsDouble(1.0, 2, 2);

		Matrix m2 = m1.pinv();

		assertEquals(getLabel(), -0.1970, m2.getAsDouble(0, 0), 0.001);
		assertEquals(getLabel(), 0.2879, m2.getAsDouble(1, 0), 0.001);
		assertEquals(getLabel(), 0.0152, m2.getAsDouble(2, 0), 0.001);
		assertEquals(getLabel(), 0.0303, m2.getAsDouble(0, 1), 0.001);
		assertEquals(getLabel(), -0.1212, m2.getAsDouble(1, 1), 0.001);
		assertEquals(getLabel(), 0.1515, m2.getAsDouble(2, 1), 0.001);
		assertEquals(getLabel(), 0.3788, m2.getAsDouble(0, 2), 0.001);
		assertEquals(getLabel(), -0.0152, m2.getAsDouble(1, 2), 0.001);
		assertEquals(getLabel(), -0.1061, m2.getAsDouble(2, 2), 0.001);

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}

		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
	}

	@Test
	public final void testGinvFixedSmall() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(3, 3);
		m1.setAsDouble(1.0, 0, 0);
		m1.setAsDouble(2.0, 1, 0);
		m1.setAsDouble(3.0, 2, 0);
		m1.setAsDouble(4.0, 0, 1);
		m1.setAsDouble(1.0, 1, 1);
		m1.setAsDouble(2.0, 2, 1);
		m1.setAsDouble(3.0, 0, 2);
		m1.setAsDouble(7.0, 1, 2);
		m1.setAsDouble(1.0, 2, 2);

		Matrix m2 = m1.ginv();

		assertEquals(getLabel(), -0.1970, m2.getAsDouble(0, 0), 0.001);
		assertEquals(getLabel(), 0.2879, m2.getAsDouble(1, 0), 0.001);
		assertEquals(getLabel(), 0.0152, m2.getAsDouble(2, 0), 0.001);
		assertEquals(getLabel(), 0.0303, m2.getAsDouble(0, 1), 0.001);
		assertEquals(getLabel(), -0.1212, m2.getAsDouble(1, 1), 0.001);
		assertEquals(getLabel(), 0.1515, m2.getAsDouble(2, 1), 0.001);
		assertEquals(getLabel(), 0.3788, m2.getAsDouble(0, 2), 0.001);
		assertEquals(getLabel(), -0.0152, m2.getAsDouble(1, 2), 0.001);
		assertEquals(getLabel(), -0.1061, m2.getAsDouble(2, 2), 0.001);

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}

		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
	}

	@Test
	public final void testEigRandSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(10, 10);

		if (!isSupported(a, MatrixLibraries.EIG, MatrixLayout.SQUARE, EntryType.RANDN)) {
			return;
		}

		a.randn(Ret.ORIG);
		Matrix[] eig = a.eig();
		Matrix prod1 = a.mtimes(eig[0]);
		Matrix prod2 = eig[0].mtimes(eig[1]);

		assertEquals(getLabel(), 0.0, prod1.minus(prod2).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	@Test
	public final void testEigRandLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(110, 110);

		if (!isSupported(a, MatrixLibraries.EIG, MatrixLayout.SQUARE, null)) {
			return;
		}

		a.randn(Ret.ORIG);
		Matrix[] eig = a.eig();
		Matrix prod1 = a.mtimes(eig[0]);
		Matrix prod2 = eig[0].mtimes(eig[1]);

		assertEquals(getLabel(), 0.0, prod1.minus(prod2).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod1 instanceof Erasable) {
			((Erasable) prod1).erase();
		}
		if (prod2 instanceof Erasable) {
			((Erasable) prod2).erase();
		}
	}

	@Test
	public final void testEigSymmSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(10, 10);

		setRandSymmetric(a);

		Matrix[] eig = a.eig();
		Matrix prod1 = a.mtimes(eig[0]);
		Matrix prod2 = eig[0].mtimes(eig[1]);

		assertEquals(getLabel(), 0.0, prod1.minus(prod2).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod1 instanceof Erasable) {
			((Erasable) prod1).erase();
		}
		if (prod2 instanceof Erasable) {
			((Erasable) prod2).erase();
		}
	}

	@Test
	public final void testEigSymmLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(111, 111);

		setRandSymmetric(a);

		Matrix[] eig = a.eig();
		Matrix prod1 = a.mtimes(eig[0]);
		Matrix prod2 = eig[0].mtimes(eig[1]);

		assertEquals(getLabel(), 0.0, prod1.minus(prod2).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod1 instanceof Erasable) {
			((Erasable) prod1).erase();
		}
		if (prod2 instanceof Erasable) {
			((Erasable) prod2).erase();
		}
	}

	@Test
	public final void testLUSquareSingularSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(5, 5);

		if (!isSupported(a, MatrixLibraries.LU, MatrixLayout.SQUARE, EntryType.SINGULAR)) {
			return;
		}

		setAscending(a);

		Matrix[] lu = a.lu();
		Matrix prod = lu[0].mtimes(lu[1]);
		Matrix aperm = lu[2].mtimes(a);

		assertEquals(getLabel(), 0.0, prod.minus(aperm).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
		if (aperm instanceof Erasable) {
			((Erasable) aperm).erase();
		}
	}

	public final static void setAscending(Matrix source) {
		for (int r = 0, v = 1; r < source.getRowCount(); r++) {
			for (int c = 0; c < source.getColumnCount(); c++) {
				source.setAsDouble(v++, r, c);
			}
		}
	}

	public final static void setRandSymmetric(Matrix a) {
		Random random = new Random();
		int rows = (int) a.getRowCount();
		int cols = (int) a.getColumnCount();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols && c <= r; c++) {
				double f = random.nextDouble();
				a.setAsDouble(f, r, c);
				a.setAsDouble(f, c, r);
			}
		}
	}

	public final static void setRandnSymmetric(Matrix a) {
		Random random = new Random();
		int rows = (int) a.getRowCount();
		int cols = (int) a.getColumnCount();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols && c <= r; c++) {
				double f = random.nextGaussian();
				a.setAsDouble(f, r, c);
				a.setAsDouble(f, c, r);
			}
		}
	}

	@Test
	public final void testLUSquareSingularLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(116, 116);

		if (!isSupported(a, MatrixLibraries.LU, MatrixLayout.SQUARE, EntryType.SINGULAR)) {
			return;
		}

		setAscending(a);
		Matrix[] lu = a.lu();
		Matrix prod = lu[0].mtimes(lu[1]);
		Matrix aperm = lu[2].mtimes(a);

		assertEquals(getLabel(), 0.0, prod.minus(aperm).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
		if (aperm instanceof Erasable) {
			((Erasable) aperm).erase();
		}
	}

	@Test
	public final void testSolveRandSquareSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(2, 2);

		a.randn(Ret.ORIG);
		Matrix x = createMatrixWithAnnotation(2, 4);
		x.randn(Ret.ORIG);
		Matrix b = a.mtimes(x);

		Matrix x2 = a.solve(b);
		Matrix prod = a.mtimes(x2);

		assertEquals(getLabel(), 0.0, prod.minus(b).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
		if (x instanceof Erasable) {
			((Erasable) x).erase();
		}
		if (x2 instanceof Erasable) {
			((Erasable) x2).erase();
		}
	}

	@Test
	public final void testSolveRandSquareLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(125, 125);

		a.randn(Ret.ORIG);
		Matrix x = createMatrixWithAnnotation(125, 142);
		x.randn(Ret.ORIG);
		Matrix b = a.mtimes(x);

		Matrix x2 = a.solve(b);
		Matrix prod = a.mtimes(x2);

		assertEquals(getLabel(), 0.0, prod.minus(b).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
		if (x instanceof Erasable) {
			((Erasable) x).erase();
		}
		if (x2 instanceof Erasable) {
			((Erasable) x2).erase();
		}
	}

	@Test
	public final void testSolveRandSPDLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(125, 125);

		BenchmarkUtil.randPositiveDefinite(System.currentTimeMillis(), 0, 0, a);
		Matrix x = createMatrixWithAnnotation(125, 125);
		x.randn(Ret.ORIG);
		Matrix b = a.mtimes(x);

		Matrix x2 = a.solveSPD(b);
		Matrix prod = a.mtimes(x2);

		assertEquals(getLabel(), 0.0, prod.minus(b).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
		if (x instanceof Erasable) {
			((Erasable) x).erase();
		}
		if (x2 instanceof Erasable) {
			((Erasable) x2).erase();
		}
	}

	@Test
	public final void testSolveRandSPDSmall() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(15, 15);

		BenchmarkUtil.randPositiveDefinite(System.currentTimeMillis(), 0, 0, a);
		Matrix x = createMatrixWithAnnotation(15, 15);
		x.randn(Ret.ORIG);
		Matrix b = a.mtimes(x);

		Matrix x2 = a.solveSPD(b);
		Matrix prod = a.mtimes(x2);

		assertEquals(getLabel(), 0.0, prod.minus(b).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
		if (x instanceof Erasable) {
			((Erasable) x).erase();
		}
		if (x2 instanceof Erasable) {
			((Erasable) x2).erase();
		}
	}

	@Test
	public final void testSolveRandTallSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(6, 2);

		if (!isSupported(a, MatrixLibraries.SOLVE, MatrixLayout.TALL, EntryType.RANDN)) {
			return;
		}

		a.randn(Ret.ORIG);
		Matrix x = createMatrixWithAnnotation(2, 4);
		x.randn(Ret.ORIG);
		Matrix b = a.mtimes(x);

		Matrix x2 = a.solve(b);
		Matrix prod = a.mtimes(x2);

		assertEquals(getLabel(), 0.0, prod.minus(b).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
		if (x instanceof Erasable) {
			((Erasable) x).erase();
		}
		if (x2 instanceof Erasable) {
			((Erasable) x2).erase();
		}
	}

	@Test
	public final void testSolveRandTallLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(169, 121);

		if (!isSupported(a, MatrixLibraries.SOLVE, MatrixLayout.TALL, EntryType.RANDN)) {
			return;
		}

		a.randn(Ret.ORIG);
		Matrix x = createMatrixWithAnnotation(121, 143);
		x.randn(Ret.ORIG);
		Matrix b = a.mtimes(x);

		Matrix x2 = a.solve(b);
		Matrix prod = a.mtimes(x2);

		assertEquals(getLabel(), 0.0, prod.minus(b).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
		if (x instanceof Erasable) {
			((Erasable) x).erase();
		}
		if (x2 instanceof Erasable) {
			((Erasable) x2).erase();
		}
	}

	@Test
	public final void testLUSquareRandSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(10, 10);

		if (!isSupported(a, MatrixLibraries.LU, MatrixLayout.SQUARE, EntryType.RANDN)) {
			return;
		}
		if (!isSupported(a, MatrixLibraries.LU, MatrixLayout.SQUARE, EntryType.SINGULAR)) {
			return;
		}

		a.randn(Ret.ORIG);
		Matrix[] lu = a.lu();
		Matrix prod = lu[0].mtimes(lu[1]);
		Matrix aperm = lu[2].mtimes(a);

		assertEquals(getLabel(), 0.0, prod.minus(aperm).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
		if (aperm instanceof Erasable) {
			((Erasable) aperm).erase();
		}
	}

	@Test
	public final void testLUSquareRandLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(112, 112);

		if (!isSupported(a, MatrixLibraries.LU, MatrixLayout.SQUARE, EntryType.RANDN)) {
			return;
		}

		a.randn(Ret.ORIG);
		Matrix[] lu = a.lu();
		Matrix prod = lu[0].mtimes(lu[1]);
		Matrix aperm = lu[2].mtimes(a);

		assertEquals(getLabel(), 0.0, prod.minus(aperm).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
		if (aperm instanceof Erasable) {
			((Erasable) aperm).erase();
		}
	}

	@Test
	public final void testLUTallFixedSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(6, 4);

		if (!isSupported(a, MatrixLibraries.LU, MatrixLayout.TALL, null)) {
			return;
		}

		setAscending(a);
		Matrix[] lu = a.lu();
		Matrix prod = lu[0].mtimes(lu[1]);
		Matrix aperm = lu[2].mtimes(a);

		assertEquals(0.0, prod.minus(aperm).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
		if (aperm instanceof Erasable) {
			((Erasable) aperm).erase();
		}
	}

	@Test
	public final void testLUTallFixedLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(161, 142);

		if (!isSupported(a, MatrixLibraries.LU, MatrixLayout.TALL, null)) {
			return;
		}

		setAscending(a);
		Matrix[] lu = a.lu();
		Matrix prod = lu[0].mtimes(lu[1]);
		Matrix aperm = lu[2].mtimes(a);

		assertEquals(0.0, prod.minus(aperm).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
		if (aperm instanceof Erasable) {
			((Erasable) aperm).erase();
		}
	}

	@Test
	public final void testLUFatFixedSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(4, 6);

		if (!isSupported(a, MatrixLibraries.LU, MatrixLayout.FAT, null)) {
			return;
		}

		setAscending(a);
		Matrix[] lu = a.lu();
		Matrix prod = lu[0].mtimes(lu[1]);
		Matrix aperm = lu[2].mtimes(a);

		assertEquals(0.0, prod.minus(aperm).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
		if (aperm instanceof Erasable) {
			((Erasable) aperm).erase();
		}
	}

	@Test
	public final void testLUFatFixedLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}

		Matrix a = createMatrixWithAnnotation(141, 162);

		if (!isSupported(a, MatrixLibraries.LU, MatrixLayout.FAT, null)) {
			return;
		}

		setAscending(a);
		Matrix[] lu = a.lu();
		Matrix prod = lu[0].mtimes(lu[1]);
		Matrix aperm = lu[2].mtimes(a);

		assertEquals(0.0, prod.minus(aperm).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
		if (aperm instanceof Erasable) {
			((Erasable) aperm).erase();
		}
	}

	@Test
	public final void testQRFixedSquareSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(5, 5);
		setAscending(a);
		Matrix[] qr = a.qr();
		Matrix prod = qr[0].mtimes(qr[1]);

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
	}

	@Test
	public final void testQRFixedSquareLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(151, 151);
		setAscending(a);
		Matrix[] qr = a.qr();
		Matrix prod = qr[0].mtimes(qr[1]);

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
	}

	@Test
	public final void testQRRandSquareSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(7, 7);
		a.randn(Ret.ORIG);
		Matrix[] qr = a.qr();
		Matrix prod = qr[0].mtimes(qr[1]);

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
	}

	@Test
	public final void testQR() throws Exception {
		Matrix a = createMatrixWithAnnotation(1, 1);

		List<MatrixLayout> layouts = new LinkedList<MatrixLayout>();
		layouts.add(MatrixLayout.SQUARE);
		layouts.add(MatrixLayout.FAT);
		layouts.add(MatrixLayout.TALL);

		List<Size> sizes = new LinkedList<Size>();
		sizes.add(Size.SINGLEENTRY);
		sizes.add(Size.SMALL);
		if (isTestLarge()) {
			sizes.add(Size.LARGE);
		}

		List<EntryType> generators = new LinkedList<EntryType>();
		generators.add(EntryType.SINGULAR);
		generators.add(EntryType.RAND);
		generators.add(EntryType.RANDN);
		generators.add(EntryType.RANDSYMM);
		generators.add(EntryType.RANDNSYMM);
		generators.add(EntryType.SPD);

		for (MatrixLayout layout : layouts) {
			for (Size size : sizes) {
				for (EntryType generator : generators) {
					String label = getLabel() + "-" + layout + "-" + size + "-" + generator;

					// symmetric only for square matrices
					if (!MatrixLayout.SQUARE.equals(layout)) {
						if (EntryType.RANDSYMM.equals(generator)) {
							continue;
						} else if (EntryType.RANDNSYMM.equals(generator)) {
							continue;
						} else if (EntryType.SPD.equals(generator)) {
							continue;
						}
					}

					try {
						a = createMatrixWithAnnotation(layout, size, generator);
						Matrix[] qr = a.qr();
						Matrix prod = qr[0].mtimes(qr[1]);
						Matrix diff = prod.minus(a);
						assertEquals(label, 0.0, diff.getRMS(), TOLERANCE);

						if (a instanceof Erasable) {
							((Erasable) a).erase();
						}
						if (prod instanceof Erasable) {
							((Erasable) prod).erase();
						}
						if (diff instanceof Erasable) {
							((Erasable) diff).erase();
						}
					} catch (Exception e) {
						// catch known errors
						// i.e. when this feature is not supported
						if (!isSupported(a, MatrixLibraries.QR, layout, generator)) {
							continue;
						}

						throw new Exception(label, e);
					}
				}
			}
		}
	}

	private final boolean supportsSingular(Matrix a, long feature) {
		long col = getMatrixLibraryId();
		String supported = LIBRARIES.getAsString(feature, col);
		if (supported.contains(MatrixLibraries.NONSINGULARTEXT)) {
			return false;
		} else {
			return true;
		}
	}

	private final boolean isSupported(Matrix a, long feature, MatrixLayout layout,
			EntryType generator) {
		long col = getMatrixLibraryId();
		String supported = LIBRARIES.getAsString(feature, col);

		if (MatrixLayout.FAT.equals(layout)) {
			if (!supported.startsWith(ALL) && !supported.contains(FAT)) {
				return false;
			}
		}
		if (MatrixLayout.TALL.equals(layout)) {
			if (!supported.startsWith(ALL) && !supported.contains(TALL)) {
				return false;
			}
		}
		if (MatrixLayout.SQUARE.equals(layout)) {
			if (!supported.startsWith(ALL) && !supported.contains(SQUARE)) {
				return false;
			} else if (EntryType.SINGULAR.equals(generator)
					&& supported.contains(MatrixLibraries.NONSINGULARTEXT)) {
				return false;
			} else if (!EntryType.RANDNSYMM.equals(generator)
					&& !EntryType.RANDSYMM.equals(generator)
					&& supported.contains(MatrixLibraries.SYMMETRICTEXT)) {
				return false;
			}
		}

		if (supported.contains(MatrixLibraries.ERRORTEXT)) {
			return false;
		}

		return true;
	}

	@Test
	public final void testLU() throws Exception {
		Matrix a = createMatrixWithAnnotation(1, 1);

		List<MatrixLayout> layouts = new LinkedList<MatrixLayout>();
		layouts.add(MatrixLayout.SQUARE);
		layouts.add(MatrixLayout.FAT);
		layouts.add(MatrixLayout.TALL);

		List<Size> sizes = new LinkedList<Size>();
		sizes.add(Size.SINGLEENTRY);
		sizes.add(Size.SMALL);
		if (isTestLarge()) {
			sizes.add(Size.LARGE);
		}

		List<EntryType> generators = new LinkedList<EntryType>();
		generators.add(EntryType.SINGULAR);
		generators.add(EntryType.RAND);
		generators.add(EntryType.RANDN);
		generators.add(EntryType.RANDSYMM);
		generators.add(EntryType.RANDNSYMM);
		generators.add(EntryType.SPD);

		for (MatrixLayout layout : layouts) {
			for (Size size : sizes) {
				for (EntryType generator : generators) {
					String label = getLabel() + "-" + layout + "-" + size + "-" + generator;

					if (!isSupported(a, MatrixLibraries.LU, layout, generator)) {
						continue;
					}
					if (a.isSingular() && !supportsSingular(a, MatrixLibraries.LU)) {
						continue;
					}

					// symmetric only for square matrices
					if (!MatrixLayout.SQUARE.equals(layout)) {
						if (EntryType.RANDSYMM.equals(generator)) {
							continue;
						} else if (EntryType.RANDNSYMM.equals(generator)) {
							continue;
						} else if (EntryType.SPD.equals(generator)) {
							continue;
						}
					}

					try {

						a = createMatrixWithAnnotation(layout, size, generator);
						Matrix[] lu = a.lu();
						Matrix prod = lu[0].mtimes(lu[1]);
						Matrix aperm = lu[2].mtimes(a);
						Matrix diff = prod.minus(aperm);
						assertEquals(label, 0.0, diff.getRMS(), TOLERANCE);

						if (a instanceof Erasable) {
							((Erasable) a).erase();
						}
						if (prod instanceof Erasable) {
							((Erasable) prod).erase();
						}
						if (aperm instanceof Erasable) {
							((Erasable) aperm).erase();
						}
						if (diff instanceof Erasable) {
							((Erasable) diff).erase();
						}
					} catch (Exception e) {
						// catch known errors
						// i.e. when this feature is not supported
						if (!isSupported(a, MatrixLibraries.LU, layout, generator)) {
							continue;
						}

						throw new Exception(label, e);
					}
				}
			}
		}
	}

	@Test
	public final void testSVD() throws Exception {
		Matrix a = createMatrixWithAnnotation(1, 1);

		List<MatrixLayout> layouts = new LinkedList<MatrixLayout>();
		layouts.add(MatrixLayout.SQUARE);
		layouts.add(MatrixLayout.FAT);
		layouts.add(MatrixLayout.TALL);

		List<Size> sizes = new LinkedList<Size>();
		sizes.add(Size.SINGLEENTRY);
		sizes.add(Size.SMALL);
		if (isTestLarge()) {
			sizes.add(Size.LARGE);
		}

		List<EntryType> generators = new LinkedList<EntryType>();
		generators.add(EntryType.SINGULAR);
		generators.add(EntryType.RAND);
		generators.add(EntryType.RANDN);
		generators.add(EntryType.RANDSYMM);
		generators.add(EntryType.RANDNSYMM);
		generators.add(EntryType.SPD);

		for (MatrixLayout layout : layouts) {
			for (Size size : sizes) {
				for (EntryType generator : generators) {
					String label = getLabel() + "-" + layout + "-" + size + "-" + generator;

					if (!isSupported(a, MatrixLibraries.SVD, layout, generator)) {
						continue;
					}
					if (a.isSingular() && !supportsSingular(a, MatrixLibraries.SVD)) {
						continue;
					}

					// symmetric only for square matrices
					if (!MatrixLayout.SQUARE.equals(layout)) {
						if (EntryType.RANDSYMM.equals(generator)) {
							continue;
						} else if (EntryType.RANDNSYMM.equals(generator)) {
							continue;
						} else if (EntryType.SPD.equals(generator)) {
							continue;
						}
					}

					try {

						a = createMatrixWithAnnotation(layout, size, generator);
						Matrix[] svd = a.svd();
						Matrix prod = svd[0].mtimes(svd[1]).mtimes(svd[2].transpose());
						Matrix diff = prod.minus(a);
						assertEquals(label, 0.0, diff.getRMS(), TOLERANCE);

						if (a instanceof Erasable) {
							((Erasable) a).erase();
						}
						if (prod instanceof Erasable) {
							((Erasable) prod).erase();
						}
						if (diff instanceof Erasable) {
							((Erasable) diff).erase();
						}
					} catch (Exception e) {
						// catch known errors
						// i.e. when this feature is not supported
						if (!isSupported(a, MatrixLibraries.SVD, layout, generator)) {
							continue;
						}

						throw new Exception(label, e);
					}
				}
			}
		}
	}

	private final Matrix createMatrixWithAnnotation(MatrixLayout layout, Size size,
			EntryType generator) throws Exception {
		int rows = 0, cols = 0;

		switch (layout) {
		case SQUARE:
			if (Size.SMALL.equals(size)) {
				rows = MathUtil.nextInteger(2, 10);
				cols = rows;
			} else if (Size.LARGE.equals(size)) {
				rows = MathUtil.nextInteger(102, 110);
				cols = rows;
			} else if (Size.SINGLEENTRY.equals(size)) {
				rows = 1;
				cols = 1;
			}
			break;
		case FAT:
			if (Size.SMALL.equals(size)) {
				rows = MathUtil.nextInteger(2, 10);
				cols = MathUtil.nextInteger(12, 20);
			} else if (Size.LARGE.equals(size)) {
				rows = MathUtil.nextInteger(102, 110);
				cols = MathUtil.nextInteger(122, 140);
			} else if (Size.SINGLEENTRY.equals(size)) {
				rows = 1;
				cols = 2;
			}
			break;
		case TALL:
			if (Size.SMALL.equals(size)) {
				rows = MathUtil.nextInteger(12, 20);
				cols = MathUtil.nextInteger(2, 10);
			} else if (Size.LARGE.equals(size)) {
				rows = MathUtil.nextInteger(122, 140);
				cols = MathUtil.nextInteger(102, 110);
			} else if (Size.SINGLEENTRY.equals(size)) {
				rows = 2;
				cols = 1;
			}
			break;
		default:
			throw new Exception("unknown layout: " + layout);
		}

		Matrix a = createMatrixWithAnnotation(rows, cols);

		switch (generator) {
		case SINGULAR:
			setAscending(a);
			break;
		case RAND:
			a.rand(Ret.ORIG);
			break;
		case RANDN:
			a.randn(Ret.ORIG);
			break;
		case RANDSYMM:
			setRandSymmetric(a);
			break;
		case RANDNSYMM:
			setRandnSymmetric(a);
			break;
		case SPD:
			BenchmarkUtil.randPositiveDefinite(System.currentTimeMillis(), 0, 0, a);
			break;
		default:
			throw new Exception("unknown entry generator: " + generator);
		}
		return a;
	}

	@Test
	public final void testQRRandLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(123, 123);
		a.randn(Ret.ORIG);
		Matrix[] qr = a.qr();
		Matrix prod = qr[0].mtimes(qr[1]);

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
	}

	@Test
	public final void testQRFatSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(4, 6);

		if (!isSupported(a, MatrixLibraries.QR, MatrixLayout.FAT, null)) {
			return;
		}

		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}
		Matrix[] qr = a.qr();
		Matrix prod = qr[0].mtimes(qr[1]);

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
	}

	@Test
	public final void testQRFatLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(140, 160);

		if (!isSupported(a, MatrixLibraries.QR, MatrixLayout.FAT, null)) {
			return;
		}

		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}
		Matrix[] qr = a.qr();
		Matrix prod = qr[0].mtimes(qr[1]);

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
	}

	@Test
	public final void testQRTallSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(6, 4);

		if (!isSupported(a, MatrixLibraries.QR, MatrixLayout.TALL, null)) {
			return;
		}

		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}
		Matrix[] qr = a.qr();
		Matrix prod = qr[0].mtimes(qr[1]);

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
	}

	@Test
	public final void testQRTallLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(168, 143);

		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}
		Matrix[] qr = a.qr();
		Matrix prod = qr[0].mtimes(qr[1]);

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
	}

	@Test
	public final void testCholPascalSmall() throws Exception {
		Matrix pascal = Matrix.Factory.pascal(5, 5);
		Matrix a = createMatrixWithAnnotation(pascal);

		if (!isSupported(a, MatrixLibraries.CHOL, MatrixLayout.SQUARE, null)) {
			return;
		}

		Matrix chol = a.chol();
		Matrix cholTrans = chol.transpose();
		Matrix prod = chol.mtimes(cholTrans);
		Matrix diff = prod.minus(a);

		assertEquals(getLabel(), 0.0, diff.getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
		if (prod instanceof Erasable) {
			((Erasable) prod).erase();
		}
		if (diff instanceof Erasable) {
			((Erasable) diff).erase();
		}
	}

	@Test
	public final void testCholRandSmall() throws Exception {
		Random random = new Random(System.nanoTime());
		DenseDoubleMatrix2D temp = new DefaultDenseDoubleMatrix2D(10, 10);
		int rows = (int) temp.getRowCount();
		int cols = (int) temp.getColumnCount();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				temp.setDouble(random.nextDouble(), r, c);
			}
		}
		Matrix result = createMatrixWithAnnotation(temp.mtimes(temp.transpose()));

		if (!isSupported(result, MatrixLibraries.CHOL, MatrixLayout.SQUARE, null)) {
			return;
		}

		Matrix chol = result.chol();
		Matrix prod = chol.mtimes(chol.transpose());

		assertEquals(getLabel(), 0.0, prod.minus(result).getRMS(), TOLERANCE);

		if (result instanceof Erasable) {
			((Erasable) result).erase();
		}
	}

	@Test
	public final void testCholRandVerySmall() throws Exception {
		Random random = new Random(System.nanoTime());
		DenseDoubleMatrix2D temp = new DefaultDenseDoubleMatrix2D(2, 2);
		int rows = (int) temp.getRowCount();
		int cols = (int) temp.getColumnCount();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				temp.setDouble(random.nextDouble(), r, c);
			}
		}
		Matrix result = createMatrixWithAnnotation(temp.mtimes(temp.transpose()));

		if (!isSupported(result, MatrixLibraries.CHOL, MatrixLayout.SQUARE, null)) {
			return;
		}

		Matrix chol = result.chol();
		Matrix prod = chol.mtimes(chol.transpose());

		assertEquals(getLabel(), 0.0, prod.minus(result).getRMS(), TOLERANCE);

		if (result instanceof Erasable) {
			((Erasable) result).erase();
		}
	}

	@Test
	public final void testCholRandLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Random random = new Random(System.nanoTime());
		DenseDoubleMatrix2D temp = new DefaultDenseDoubleMatrix2D(102, 102);
		int rows = (int) temp.getRowCount();
		int cols = (int) temp.getColumnCount();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				temp.setDouble(random.nextDouble(), r, c);
			}
		}
		Matrix result = createMatrixWithAnnotation(temp.mtimes(temp.transpose()));

		if (!isSupported(result, MatrixLibraries.CHOL, MatrixLayout.SQUARE, null)) {
			return;
		}

		Matrix chol = result.chol();
		Matrix prod = chol.mtimes(chol.transpose());

		assertEquals(0.0, prod.minus(result).getRMS(), TOLERANCE);

		if (result instanceof Erasable) {
			((Erasable) result).erase();
		}
	}

	// test example from wikipedia
	@Test
	public final void testSVDWikipedia() throws Exception {
		Matrix a = createMatrixWithAnnotation(4, 5);

		if (!isSupported(a, MatrixLibraries.SVD, MatrixLayout.FAT, null)) {
			return;
		}

		a.setAsDouble(1, 0, 0);
		a.setAsDouble(2, 0, 4);
		a.setAsDouble(3, 1, 2);
		a.setAsDouble(4, 3, 1);

		Matrix[] svd = a.svd();

		Matrix prod = svd[0].mtimes(svd[1]).mtimes(svd[2].transpose());

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	@Test
	public final void testSVDSquareSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(5, 5);

		if (!isSupported(a, MatrixLibraries.SVD, MatrixLayout.SQUARE, null)) {
			return;
		}

		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}

		Matrix[] svd = a.svd();

		Matrix prod = svd[0].mtimes(svd[1]).mtimes(svd[2].transpose());

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	@Test
	public final void testSVDSquareLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(107, 107);

		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}

		Matrix[] svd = a.svd();

		Matrix prod = svd[0].mtimes(svd[1]).mtimes(svd[2].transpose());

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	@Test
	public final void testSVDSquareRandSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(10, 10);

		if (!isSupported(a, MatrixLibraries.SVD, MatrixLayout.SQUARE, EntryType.RANDN)) {
			return;
		}

		a.randn(Ret.ORIG);

		Matrix[] svd = a.svd();

		Matrix prod = svd[0].mtimes(svd[1]).mtimes(svd[2].transpose());

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	@Test
	public final void testSVDSquareRandLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(109, 109);

		a.randn(Ret.ORIG);

		Matrix[] svd = a.svd();

		Matrix prod = svd[0].mtimes(svd[1]).mtimes(svd[2].transpose());

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	@Test
	public final void testSVDFatSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(4, 6);

		if (!isSupported(a, MatrixLibraries.SVD, MatrixLayout.FAT, null)) {
			return;
		}

		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}

		Matrix[] svd = a.svd();

		Matrix prod = svd[0].mtimes(svd[1]).mtimes(svd[2].transpose());

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	@Test
	public final void testSVDFatLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(123, 142);

		if (!isSupported(a, MatrixLibraries.SVD, MatrixLayout.FAT, null)) {
			return;
		}

		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}

		Matrix[] svd = a.svd();

		Matrix prod = svd[0].mtimes(svd[1]).mtimes(svd[2].transpose());

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	@Test
	public final void testSVDTallSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(6, 4);

		if (!isSupported(a, MatrixLibraries.SVD, MatrixLayout.TALL, null)) {
			return;
		}

		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}

		Matrix[] svd = a.svd();

		Matrix prod = svd[0].mtimes(svd[1]).mtimes(svd[2].transpose());

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	@Test
	public final void testSVDTallLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(140, 121);

		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}

		Matrix[] svd = a.svd();

		Matrix prod = svd[0].mtimes(svd[1]).mtimes(svd[2].transpose());

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	public final static void setAnnotation(Matrix m) {
		m.setLabel("label");
		m.setDimensionLabel(Matrix.ROW, "rows");
		m.setDimensionLabel(Matrix.COLUMN, "columns");

		for (int r = 0; r < m.getRowCount(); r++) {
			if (r == 0) {
				continue;
			}
			m.setRowLabel(r, "row" + r);
		}

		for (int c = 0; c < m.getColumnCount(); c++) {
			if (c == 1) {
				continue;
			}
			m.setColumnLabel(c, "col" + c);
		}
	}

	public final static void compareAnnotation(Matrix m) {
		for (int r = 0; r < m.getRowCount(); r++) {
			if (r == 0) {
				assertEquals(null, m.getRowLabel(r));
			} else {
				assertEquals("row" + r, m.getRowLabel(r));
			}
		}

		for (int c = 0; c < m.getColumnCount(); c++) {
			if (c == 1) {
				assertEquals(null, m.getColumnLabel(c));
			} else {
				assertEquals("col" + c, m.getColumnLabel(c));
			}
		}

		assertEquals("label", m.getLabel());
	}

}

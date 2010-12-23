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
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.benchmark.BenchmarkUtil;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.ArrayDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrixMultiD;
import org.ujmp.core.doublematrix.stub.AbstractDoubleMatrix;
import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.matrix.DenseMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.SerializationUtil;
import org.ujmp.core.util.matrices.MatrixLibraries;

public abstract class AbstractMatrixTest {

	private static final MatrixLibraries LIBRARIES = new MatrixLibraries();

	private static final String SQUARE = LIBRARIES.square();

	private static final String TALL = LIBRARIES.tall();

	private static final String FAT = LIBRARIES.fat();

	public enum MatrixLayout {
		SQUARE, FAT, TALL
	};

	public enum EntryGenerator {
		RANDN, RAND, RANDSYMM, RANDNSYMM, SINGULAR, SPD
	};

	public enum Size {
		SINGLEENTRY, LARGE, SMALL
	};

	public static final double TOLERANCE = 1e-3;

	public abstract Matrix createMatrix(long... size) throws Exception;

	public abstract Matrix createMatrix(Matrix source) throws Exception;

	public abstract boolean isTestLarge();

	public String getLabel() {
		return this.getClass().getSimpleName();
	}

	public Matrix getTestMatrix() throws Exception {
		Matrix m = createMatrixWithAnnotation(3, 3);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		m.setAsDouble(2.0, 2, 1);
		m.setAsDouble(-2.0, 1, 2);
		return m;
	}

	private Matrix createMatrixWithAnnotation(long... size) throws Exception {
		Matrix m = createMatrix(size);
		setAnnotation(m);
		return m;
	}

	private Matrix createMatrixWithAnnotation(Matrix matrix) throws Exception {
		Matrix m = createMatrix(matrix);
		setAnnotation(m);
		return m;
	}

	@Test
	public void testExtractAnnotation() throws Exception {
		Matrix m1 = DenseMatrix.factory.randn(5, 5);
		Matrix m2 = m1.extractAnnotation(Ret.NEW, Matrix.ROW);
		assertEquals(getLabel(), 4, m2.getRowCount());
		Matrix m3 = m2.includeAnnotation(Ret.NEW, Matrix.ROW);
		m3.setAnnotation(null);
		assertEquals(getLabel(), m1, m3);

		m1 = DenseMatrix.factory.randn(5, 5);
		m2 = m1.extractAnnotation(Ret.LINK, Matrix.ROW);
		assertEquals(getLabel(), 4, m2.getRowCount());
		m3 = m2.includeAnnotation(Ret.LINK, Matrix.ROW);
		m3.setAnnotation(null);
		assertEquals(getLabel(), m1, m3);

		m1 = DenseMatrix.factory.randn(5, 5);
		m2 = m1.extractAnnotation(Ret.NEW, Matrix.COLUMN);
		assertEquals(getLabel(), 4, m2.getColumnCount());
		m3 = m2.includeAnnotation(Ret.NEW, Matrix.COLUMN);
		m3.setAnnotation(null);
		assertEquals(getLabel(), m1, m3);

		m1 = DenseMatrix.factory.randn(5, 5);
		m2 = m1.extractAnnotation(Ret.LINK, Matrix.COLUMN);
		assertEquals(getLabel(), 4, m2.getColumnCount());
		m3 = m2.includeAnnotation(Ret.LINK, Matrix.COLUMN);
		m3.setAnnotation(null);
		assertEquals(getLabel(), m1, m3);
	}

	// Test interface CoordinateFunctions
	@Test
	public void testCoordinateIterator2D() throws Exception {
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
			clist.add(new Coordinates(c));
		}

		if (m.isSparse()) {
			assertEquals(getLabel(), 5, clist.size());
			assertTrue(getLabel(), clist.contains(new Coordinates(0, 0)));
			assertTrue(getLabel(), clist.contains(new Coordinates(1, 0)));
			assertTrue(getLabel(), clist.contains(new Coordinates(1, 1)));
			assertTrue(getLabel(), clist.contains(new Coordinates(2, 1)));
			assertTrue(getLabel(), clist.contains(new Coordinates(1, 2)));
		} else {
			assertEquals(getLabel(), 9, clist.size());
			assertTrue(getLabel(), clist.contains(new Coordinates(0, 0)));
			assertTrue(getLabel(), clist.contains(new Coordinates(0, 1)));
			assertTrue(getLabel(), clist.contains(new Coordinates(0, 2)));
			assertTrue(getLabel(), clist.contains(new Coordinates(1, 0)));
			assertTrue(getLabel(), clist.contains(new Coordinates(1, 1)));
			assertTrue(getLabel(), clist.contains(new Coordinates(1, 2)));
			assertTrue(getLabel(), clist.contains(new Coordinates(2, 0)));
			assertTrue(getLabel(), clist.contains(new Coordinates(2, 1)));
			assertTrue(getLabel(), clist.contains(new Coordinates(2, 2)));
		}

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}

	}

	@Test
	public void testSelectedCoordinatesString() throws Exception {
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
			clist.add(new Coordinates(c));
		}

		assertEquals(getLabel(), 6, clist.size());
		assertTrue(getLabel(), clist.contains(new Coordinates(0, 0)));
		assertTrue(getLabel(), clist.contains(new Coordinates(0, 1)));
		assertTrue(getLabel(), clist.contains(new Coordinates(0, 2)));
		assertTrue(getLabel(), clist.contains(new Coordinates(1, 0)));
		assertTrue(getLabel(), clist.contains(new Coordinates(1, 1)));
		assertTrue(getLabel(), clist.contains(new Coordinates(1, 2)));

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public void testRowMajorDoubleArray2DConstructor() throws Exception {
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
	public void testColumnMajorDoubleArray1DConstructor() throws Exception {
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
	public void testConstructorWithAnnotation() throws Exception {
		Matrix m = createMatrixWithAnnotation(3, 3);
		Matrix m2 = createMatrix(m);
		assertEquals(getLabel(), m, m2);
	}

	@Test
	public void testOtherConstructor() throws Exception {
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
	public void testGetCoordinatesOfMaximum() throws Exception {
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
	public void testGetCoordinatesOfMininim() throws Exception {
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
	public void testContains() throws Exception {
		Matrix m = getTestMatrix();
		assertTrue(m.contains(0, 0));
		if (m.isSparse())
			assertFalse(m.contains(0, 1));
		else
			assertTrue(m.contains(0, 1));
		if (m.isSparse())
			assertFalse(m.contains(0, 2));
		else
			assertTrue(m.contains(0, 2));
		assertTrue(m.contains(1, 0));
		assertTrue(m.contains(1, 1));
		if (m.isSparse())
			assertFalse(m.contains(0, 1));
		else
			assertTrue(m.contains(1, 2));
		if (m.isSparse())
			assertFalse(m.contains(0, 1));
		else
			assertTrue(m.contains(2, 0));
		assertTrue(m.contains(2, 1));
		if (m.isSparse())
			assertFalse(m.contains(0, 1));
		else
			assertTrue(m.contains(2, 2));
		assertFalse(m.contains(7, 7));

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public void testSize() throws Exception {
		Matrix m = createMatrixWithAnnotation(21, 12);
		m.setAsDouble(1.0, 20, 11);
		assertEquals(getLabel(), 21, m.getRowCount());
		assertEquals(getLabel(), 12, m.getColumnCount());

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public void testZeros() throws Exception {
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
	public void testNaN() throws Exception {
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
	public void testClone() throws Exception {
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
	public void testAnnotation() throws Exception {
		Matrix m = createMatrixWithAnnotation(2, 2);
		compareAnnotation(m);

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public void testCountMissingValues() throws Exception {
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
	public void testSerialize() throws Exception {
		Matrix m = createMatrixWithAnnotation(2, 2);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		byte[] data = SerializationUtil.serialize(m);
		Matrix m2 = (Matrix) SerializationUtil.deserialize(data);
		if (m2.isTransient()) {
			Matrix m0 = Matrix.factory.zeros(2, 2);
			assertEquals(getLabel(), m0, m2);
		} else {
			assertEquals(getLabel(), m, m2);
		}

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public void testToDoubleArray() throws Exception {
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
	public void testSetAndGet() throws Exception {
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
	public void testPlusScalarSmall() throws Exception {
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
	public void testPlusScalarLarge() throws Exception {
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
	public void testPlusMatrixSmall() throws Exception {
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
	public void testPlusMatrixLarge() throws Exception {
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
	public void testTransposeSmall() throws Exception {
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
	public void testTransposeLarge() throws Exception {
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
	public void testTransposeNewSmall() throws Exception {
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
	public void testTransposeLinkSmall() throws Exception {
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
	public void testEmpty() throws Exception {
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
	public void testMinusScalarSmall() throws Exception {
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
	public void testTimesScalarLarge() throws Exception {
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
	public void testDivideScalarSmall() throws Exception {
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
	public void testDivideScalarLarge() throws Exception {
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
	public void testMinusMatrixSmall() throws Exception {
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
	public void testMinusMatrixLarge() throws Exception {
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
	public void test0PlusXMatrix() throws Exception {
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
	public void testXPlus0MatrixMatrix() throws Exception {
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
	public void test0Plus0Matrix() throws Exception {
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
	public void test0Minus0Matrix() throws Exception {
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
	public void testToDoubleArraySmall() throws Exception {
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
	public void testToDoubleArrayLarge() throws Exception {
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
	public void testXMinus0Matrix() throws Exception {
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
	public void test0MinusXMatrix() throws Exception {
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
	public void test0TimesXMatrix() throws Exception {
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
	public void testXTimes0Matrix() throws Exception {
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
	public void test0Times0Matrix() throws Exception {
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
	public void test0Divide0Matrix() throws Exception {
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
	public void testXDivide0Matrix() throws Exception {
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
	public void test0DivideXMatrix() throws Exception {
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
	public void testMTimesFixedSmall() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(2, 2);

		if (m1.getClass().getName().startsWith("org.ujmp.jblas.")
				&& System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")) {
			// not working on 64 bit windows
			return;
		}

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
	public void testMTimesFixedLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix m1 = createMatrixWithAnnotation(109, 111);

		if (m1.getClass().getName().startsWith("org.ujmp.jblas.")
				&& System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")) {
			// not working on 64 bit windows
			return;
		}

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
	public void testInvRandSmall() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(10, 10);

		if (m1.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}
		if (m1.getClass().getName().startsWith("org.ujmp.jblas.")
				&& System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")) {
			// not working on 64 bit windows
			return;
		}

		do {
			m1.randn(Ret.ORIG);
		} while (m1.isSingular());

		Matrix m2 = m1.inv();
		Matrix m3 = m1.mtimes(m2);
		Matrix eye = DenseMatrix.factory.eye(m1.getSize());
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
	public void testInvSPDRandSmall() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(10, 10);

		if (m1.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}
		if (m1.getClass().getName().startsWith("org.ujmp.jblas.")
				&& System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")) {
			// not working on 64 bit windows
			return;
		}

		BenchmarkUtil.randPositiveDefinite(System.currentTimeMillis(), 0, 0, m1);

		Matrix m2 = m1.invSPD();
		Matrix m3 = m1.mtimes(m2);
		Matrix eye = DenseMatrix.factory.eye(m1.getSize());
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
	public void testInvRandLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix m1 = createMatrixWithAnnotation(128, 128);

		if (m1.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}
		if (m1.getClass().getName().startsWith("org.ujmp.jblas.")
				&& System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")) {
			// not working on 64 bit windows
			return;
		}

		do {
			m1.randn(Ret.ORIG);
		} while (m1.isSingular());

		Matrix m2 = m1.inv();
		Matrix m3 = m1.mtimes(m2);
		Matrix eye = DenseMatrix.factory.eye(m1.getSize());
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
	public void testInvSPDRandLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix m1 = createMatrixWithAnnotation(128, 128);

		if (m1.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}
		if (m1.getClass().getName().startsWith("org.ujmp.jblas.")
				&& System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")) {
			// not working on 64 bit windows
			return;
		}

		BenchmarkUtil.randPositiveDefinite(System.currentTimeMillis(), 0, 0, m1);

		Matrix m2 = m1.invSPD();
		Matrix m3 = m1.mtimes(m2);
		Matrix eye = DenseMatrix.factory.eye(m1.getSize());
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

	public void testInvFixedSmall() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(3, 3);

		if (m1.getClass().getName().startsWith("org.ujmp.owlpack.")) {
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
	public void testPinvFixedSmall() throws Exception {
		Matrix m1 = createMatrixWithAnnotation(3, 3);

		if (m1.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}
		if (m1.getClass().getName().startsWith("org.ujmp.vecmath.")) {
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
	public void testGinvFixedSmall() throws Exception {
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
	public void testEigRandSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(10, 10);

		if (a.getClass().getName().startsWith("org.ujmp.commonsmath.")) {
			// only symmetric matrices
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.mtj.")) {
			// only symmetric matrices
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jblas.")) {
			// only symmetric matrices
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
	public void testEigRandLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(110, 110);

		if (a.getClass().getName().startsWith("org.ujmp.commonsmath.")) {
			// only symmetric matrices
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.mtj.")) {
			// only symmetric matrices
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jblas.")) {
			// only symmetric matrices
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.core.")) {
			try {
				// only symmetric matrices when JBlas is used
				Class.forName("org.ujmp.jblas.Plugin");
				return;
			} catch (Throwable t) {
			}
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jscience.")) {
			// doesn't provide eig function
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
	public void testEigSymmSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(10, 10);

		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
			return;
		}

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
	public void testEigSymmLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(111, 111);

		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
			return;
		}

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
	public void testLUSquareSingularSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(5, 5);

		// skip libraries which do not support singular matrices
		if (a.getClass().getName().startsWith("org.ujmp.commonsmath.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jsci.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jscience.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.mtj.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.orbital.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
			return;
		}
		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
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

	public static void setAscending(Matrix source) {
		for (int r = 0, v = 1; r < source.getRowCount(); r++) {
			for (int c = 0; c < source.getColumnCount(); c++) {
				source.setAsDouble(v++, r, c);
			}
		}
	}

	public static void setRandSymmetric(Matrix a) {
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

	public static void setRandnSymmetric(Matrix a) {
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
	public void testLUSquareSingularLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(116, 116);

		// skip libraries which do not support square matrices
		if (a.getClass().getName().startsWith("org.ujmp.commonsmath.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jsci.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jscience.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.mtj.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.orbital.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
			return;
		}
		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
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
	public void testSolveRandSquareSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(2, 2);

		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
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
	public void testSolveRandSquareLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(125, 125);

		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
			return;
		}

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
	public void testSolveRandSPDLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(125, 125);

		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
			return;
		}

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
	public void testSolveRandSPDSmall() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(15, 15);

		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
			return;
		}

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
	public void testSolveRandTallSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(6, 2);

		if (a.getClass().getName().startsWith("org.ujmp.jscience.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jblas.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jampack.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.mantissa.")) {
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
	public void testSolveRandTallLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(169, 121);

		if (a.getClass().getName().startsWith("org.ujmp.jscience.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jblas.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.mantissa.")) {
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
	public void testLUSquareRandSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(10, 10);

		if (a.getClass().getName().startsWith("org.ujmp.mtj.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
			return;
		}
		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
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
	public void testLUSquareRandLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(112, 112);

		if (a.getClass().getName().startsWith("org.ujmp.mtj.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
			return;
		}
		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
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
	public void testLUTallFixedSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(6, 4);

		// skip libraries which do not support fat matrices
		if (a.getClass().getName().startsWith("org.ujmp.commonsmath.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jsci.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jscience.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.mtj.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.orbital.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
			return;
		}
		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
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
	public void testLUTallFixedLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(161, 142);

		// skip libraries which do not support fat matrices
		if (a.getClass().getName().startsWith("org.ujmp.commonsmath.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jsci.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jscience.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.mtj.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.orbital.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
			return;
		}
		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
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
	public void testLUFatFixedSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(4, 6);

		// skip libraries which do not support fat matrices
		if (a.getClass().getName().startsWith("org.ujmp.colt.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.commonsmath.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jama.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jmatharray.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jmatrices.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jsci.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jscience.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.mtj.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.orbital.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.parallelcolt.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
			return;
		}
		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
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
	public void testLUFatFixedLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(141, 162);

		// skip libraries which do not support fat matrices
		if (a.getClass().getName().startsWith("org.ujmp.colt.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.commonsmath.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jama.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jmatharray.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jmatrices.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jsci.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jscience.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.mtj.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.orbital.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.parallelcolt.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
			return;
		}
		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
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
	public void testQRFixedSquareSmall() throws Exception {
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
	public void testQRFixedSquareLarge() throws Exception {
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
	public void testQRRandSquareSmall() throws Exception {
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
	public void testQR() throws Exception {
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

		List<EntryGenerator> generators = new LinkedList<EntryGenerator>();
		generators.add(EntryGenerator.SINGULAR);
		generators.add(EntryGenerator.RAND);
		generators.add(EntryGenerator.RANDN);
		generators.add(EntryGenerator.RANDSYMM);
		generators.add(EntryGenerator.RANDNSYMM);
		generators.add(EntryGenerator.SPD);

		// skip libraries which do not support QR
		if (a.getClass().getName().startsWith("org.ujmp.jblas.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jlinalg.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jsci.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jscience.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.orbital.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.sst.")) {
			return;
		}

		for (MatrixLayout layout : layouts) {
			for (Size size : sizes) {
				for (EntryGenerator generator : generators) {
					String label = getLabel() + "-" + layout + "-" + size + "-" + generator;
					System.out.println(label);

					// symmetric only for square matrices
					if (!MatrixLayout.SQUARE.equals(layout)) {
						if (EntryGenerator.RANDSYMM.equals(generator)) {
							continue;
						} else if (EntryGenerator.RANDNSYMM.equals(generator)) {
							continue;
						} else if (EntryGenerator.SPD.equals(generator)) {
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

						throw e;
					}
				}
			}
		}
	}

	private static boolean isSupported(Matrix a, long feature, MatrixLayout layout,
			EntryGenerator generator) {

		// figure out what should be supported by this library
		String[] p = a.getClass().getPackage().getName().split("\\.");
		String packageName = p[0] + "." + p[1] + "." + p[2];
		if (packageName.contains("jdmp")) {
			packageName = "org.ujmp.core";
		}
		if (packageName.contains("jmatio")) {
			packageName = "org.ujmp.core";
		}
		if (packageName.contains("lucene")) {
			packageName = "org.ujmp.core";
		}
		if (packageName.contains("ehcache")) {
			packageName = "org.ujmp.core";
		}
		if (packageName.contains("jdbc")) {
			packageName = "org.ujmp.core";
		}
		long col = LIBRARIES.getColumnForPackage(packageName);
		if (col < 0) {
			System.out.println(col);
		}
		String supported = LIBRARIES.getAsString(feature, col);

		if (MatrixLayout.FAT.equals(layout)) {
			if (!supported.contains(FAT)) {
				return false;
			}
		}
		if (MatrixLayout.TALL.equals(layout)) {
			if (!supported.contains(TALL)) {
				return false;
			}
		}
		if (MatrixLayout.SQUARE.equals(layout)) {
			if (!supported.contains(SQUARE)) {
				return false;
			}
			if (EntryGenerator.SINGULAR.equals(generator)
					&& supported.contains(MatrixLibraries.NONSINGULARTEXT)) {
				return false;
			}
		}

		if (supported.contains(MatrixLibraries.ERRORTEXT)) {
			return false;
		}

		return true;
	}

	@Test
	public void testLU() throws Exception {
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

		List<EntryGenerator> generators = new LinkedList<EntryGenerator>();
		generators.add(EntryGenerator.SINGULAR);
		generators.add(EntryGenerator.RAND);
		generators.add(EntryGenerator.RANDN);
		generators.add(EntryGenerator.RANDSYMM);
		generators.add(EntryGenerator.RANDNSYMM);
		generators.add(EntryGenerator.SPD);

		// skip libraries which do not support LU
		if (a.getClass().getName().startsWith("org.ujmp.jlinalg.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.sst.")) {
			return;
		}

		// vecmath has some error
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
			return;
		}

		// mtj has some error
		if (a.getClass().getName().startsWith("org.ujmp.mtj.")) {
			return;
		}

		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
			return;
		}

		// gives wrong result for singular matrices
		if (a.getClass().getName().startsWith("org.ujmp.jscience.")) {
			generators.remove(EntryGenerator.SINGULAR);
		}

		// gives wrong result for singular matrices
		if (a.getClass().getName().startsWith("org.ujmp.jsci.")) {
			generators.remove(EntryGenerator.SINGULAR);
		}

		// gives wrong result for singular matrices
		if (a.getClass().getName().startsWith("org.ujmp.orbital.")) {
			generators.remove(EntryGenerator.SINGULAR);
		}

		for (MatrixLayout layout : layouts) {
			for (Size size : sizes) {
				for (EntryGenerator generator : generators) {
					String label = getLabel() + "-" + layout + "-" + size + "-" + generator;
					System.out.println(label);

					// symmetric only for square matrices
					if (!MatrixLayout.SQUARE.equals(layout)) {
						if (EntryGenerator.RANDSYMM.equals(generator)) {
							continue;
						} else if (EntryGenerator.RANDNSYMM.equals(generator)) {
							continue;
						} else if (EntryGenerator.SPD.equals(generator)) {
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

						throw e;
					}
				}
			}
		}
	}

	@Test
	public void testSVD() throws Exception {
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

		List<EntryGenerator> generators = new LinkedList<EntryGenerator>();
		generators.add(EntryGenerator.SINGULAR);
		generators.add(EntryGenerator.RAND);
		generators.add(EntryGenerator.RANDN);
		generators.add(EntryGenerator.RANDSYMM);
		generators.add(EntryGenerator.RANDNSYMM);
		generators.add(EntryGenerator.SPD);

		// owlpack has some error
		if (a.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}

		// vecmath has some error
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
			return;
		}

		// problem with very small matrices
		if (a.getClass().getName().startsWith("org.ujmp.jsci.")) {
			sizes.remove(Size.SINGLEENTRY);
		}

		for (MatrixLayout layout : layouts) {
			for (Size size : sizes) {
				for (EntryGenerator generator : generators) {
					String label = getLabel() + "-" + layout + "-" + size + "-" + generator;
					System.out.println(label);

					// symmetric only for square matrices
					if (!MatrixLayout.SQUARE.equals(layout)) {
						if (EntryGenerator.RANDSYMM.equals(generator)) {
							continue;
						} else if (EntryGenerator.RANDNSYMM.equals(generator)) {
							continue;
						} else if (EntryGenerator.SPD.equals(generator)) {
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

						throw e;
					}
				}
			}
		}
	}

	private Matrix createMatrixWithAnnotation(MatrixLayout layout, Size size,
			EntryGenerator generator) throws Exception {
		int rows = 0, cols = 0;

		switch (layout) {
		case SQUARE:
			if (Size.SMALL.equals(size)) {
				rows = MathUtil.nextInteger(2, 9);
				cols = rows;
			} else if (Size.LARGE.equals(size)) {
				rows = MathUtil.nextInteger(102, 109);
				cols = rows;
			} else if (Size.SINGLEENTRY.equals(size)) {
				rows = 1;
				cols = 1;
			}
			break;
		case FAT:
			if (Size.SMALL.equals(size)) {
				rows = MathUtil.nextInteger(2, 9);
				cols = MathUtil.nextInteger(12, 19);
			} else if (Size.LARGE.equals(size)) {
				rows = MathUtil.nextInteger(102, 109);
				cols = MathUtil.nextInteger(122, 139);
			} else if (Size.SINGLEENTRY.equals(size)) {
				rows = 1;
				cols = 2;
			}
			break;
		case TALL:
			if (Size.SMALL.equals(size)) {
				rows = MathUtil.nextInteger(12, 19);
				cols = MathUtil.nextInteger(2, 9);
			} else if (Size.LARGE.equals(size)) {
				rows = MathUtil.nextInteger(122, 139);
				cols = MathUtil.nextInteger(102, 109);
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
	public void testQRRandLarge() throws Exception {
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
	public void testQRFatSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(4, 6);

		// skip libraries which do not support fat matrices
		if (a.getClass().getName().startsWith("org.ujmp.core.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.colt.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.ejml.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.ehcache.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jama.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jblas.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jlinalg.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jmatharray.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jmatio.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jmatrices.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jsci.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jscience.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.lucene.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.mantissa.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.mtj.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.orbital.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.parallelcolt.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.sst.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.jdmp.ehcache.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jdbc.")) {
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
	public void testQRFatLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(140, 160);

		// skip libraries which do not support fat matrices
		if (a.getClass().getName().startsWith("org.ujmp.core.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.colt.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.ejml.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jama.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jlinalg.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jblas.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jmatharray.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jmatio.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jmatrices.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jsci.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jscience.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.lucene.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.mantissa.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.mtj.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.orbital.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.parallelcolt.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.sst.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.jdmp.ehcache.")) {
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
	public void testQRTallSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(6, 4);

		if (a.getClass().getName().startsWith("org.ujmp.jsci.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.ejml.")) {
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
	public void testQRTallLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(168, 143);

		if (a.getClass().getName().startsWith("org.ujmp.jsci.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.ejml.")) {
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
	public void testCholPascalSmall() throws Exception {
		Matrix pascal = MatrixFactory.pascal(5, 5);
		Matrix a = createMatrixWithAnnotation(pascal);

		// some error?
		if (a.getClass().getName().startsWith("org.ujmp.mtj.")) {
			return;
		}

		// some error?
		if (a.getClass().getName().startsWith("org.ujmp.jampack.")) {
			return;
		}

		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
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
	public void testCholRandSmall() throws Exception {
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

		// some error?
		if (result.getClass().getName().startsWith("org.ujmp.mtj.")) {
			return;
		}

		// some error?
		if (result.getClass().getName().startsWith("org.ujmp.jampack.")) {
			return;
		}

		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& result.getClass().getName().startsWith("org.ujmp.jblas")) {
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
	public void testCholRandVerySmall() throws Exception {
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

		// some error?
		if (result.getClass().getName().startsWith("org.ujmp.mtj.")) {
			return;
		}

		// some error?
		if (result.getClass().getName().startsWith("org.ujmp.jampack.")) {
			return;
		}

		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& result.getClass().getName().startsWith("org.ujmp.jblas")) {
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
	public void testCholRandLarge() throws Exception {
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

		// some error?
		if (result.getClass().getName().startsWith("org.ujmp.mtj.")) {
			return;
		}

		// some error?
		if (result.getClass().getName().startsWith("org.ujmp.jampack.")) {
			return;
		}

		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& result.getClass().getName().startsWith("org.ujmp.jblas")) {
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
	public void testSVDWikipedia() throws Exception {
		Matrix a = createMatrixWithAnnotation(4, 5);

		// skip libraries which do not support fat matrices

		if (a.getClass().getName().startsWith("org.ujmp.jampack.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jmatrices.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jsci.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
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
	public void testSVDSquareSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(5, 5);

		// skip libraries which do not support fat matrices
		if (a.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
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
	public void testSVDSquareLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(107, 107);

		// skip libraries which do not support fat matrices
		if (a.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
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
	public void testSVDSquareRandSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(10, 10);

		// skip libraries which do not support fat matrices
		if (a.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
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
	public void testSVDSquareRandLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(109, 109);

		// skip libraries which do not support fat matrices
		if (a.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
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
	public void testSVDFatSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(4, 6);

		if (a.getClass().getName().startsWith("org.ujmp.jampack.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jmatrices.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jsci.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
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
	public void testSVDFatLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(123, 142);

		if (a.getClass().getName().startsWith("org.ujmp.jampack.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jmatrices.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jsci.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
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
	public void testSVDTallSmall() throws Exception {
		Matrix a = createMatrixWithAnnotation(6, 4);

		if (a.getClass().getName().startsWith("org.ujmp.jampack.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jmatrices.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jsci.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
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
	public void testSVDTallLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrixWithAnnotation(140, 121);

		if (a.getClass().getName().startsWith("org.ujmp.jampack.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jmatrices.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.jsci.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
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

	public static void setAnnotation(Matrix m) {
		m.setLabel("label");
		m.setAxisLabel(Matrix.ROW, "rows");
		m.setAxisLabel(Matrix.COLUMN, "columns");

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

	public static void compareAnnotation(Matrix m) {
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

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

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.benchmark.BenchmarkUtil;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.ArrayDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrixMultiD;
import org.ujmp.core.doublematrix.stub.AbstractDoubleMatrix;
import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.matrix.DenseMatrix;
import org.ujmp.core.util.SerializationUtil;

public abstract class AbstractMatrixTest {

	public static final double TOLERANCE = 1e-3;

	public abstract Matrix createMatrix(long... size) throws Exception;

	public abstract Matrix createMatrix(Matrix source) throws Exception;

	public abstract boolean isTestLarge();

	public String getLabel() {
		return this.getClass().getSimpleName();
	}

	public Matrix getTestMatrix() throws Exception {
		Matrix m = createMatrix(3, 3);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		m.setAsDouble(2.0, 2, 1);
		m.setAsDouble(-2.0, 1, 2);
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
		Matrix m = createMatrix(3, 3);
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

		Matrix mTest = createMatrix(2, 3);
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
		m.randn(Ret.ORIG);
		Matrix m2 = createMatrix(m);
		assertEquals(getLabel(), m, m2);
	}

	@Test
	public void testColumnMajorDoubleArray1DConstructor() throws Exception {
		Matrix m = new DefaultDenseDoubleMatrix2D(23, 17);
		m.randn(Ret.ORIG);
		Matrix m2 = createMatrix(m);
		assertEquals(getLabel(), m, m2);
	}

	@Test
	public void testOtherConstructor() throws Exception {
		Matrix m = new DefaultDenseDoubleMatrixMultiD(23, 17);
		m.randn(Ret.ORIG);
		Matrix m2 = createMatrix(m);
		assertEquals(getLabel(), m, m2);
	}

	@Test
	public void testSelectedCoordinates() throws Exception {
		Matrix m = getTestMatrix();

		Matrix mTest = createMatrix(2, 3);
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

		m = createMatrix(2, 2);
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

		m = createMatrix(2, 2);
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
		Matrix m = createMatrix(21, 12);
		assertEquals(getLabel(), 21, m.getRowCount());
		assertEquals(getLabel(), 12, m.getColumnCount());

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public void testZeros() throws Exception {
		Matrix m = createMatrix(21, 12);

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
		Matrix m = createMatrix(21, 12);

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
		Matrix m = createMatrix(2, 2);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		m.setMatrixAnnotation("annotation");
		m.setAxisAnnotation(Matrix.ROW, "row");
		m.setAxisAnnotation(Matrix.COLUMN, "column");
		m.setRowLabel(0, "row0");
		m.setRowLabel(1, "row1");
		m.setColumnLabel(0, "column0");
		m.setColumnLabel(1, "column1");
		Matrix m2 = m.clone();
		assertTrue(getLabel(), m.equalsContent(m2));
		assertTrue(getLabel(), m.equalsAnnotation(m2));

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public void testAnnotation() throws Exception {
		Matrix m = createMatrix(2, 2);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		m.setMatrixAnnotation("annotation");
		m.setAxisAnnotation(Matrix.ROW, "row");
		m.setAxisAnnotation(Matrix.COLUMN, "column");
		m.setRowLabel(0, "row0");
		m.setRowLabel(1, "row1");
		m.setColumnLabel(0, "column0");
		m.setColumnLabel(1, "column1");
		assertEquals(getLabel(), "annotation", m.getMatrixAnnotation());
		assertEquals(getLabel(), "row", m.getAxisAnnotation(Matrix.ROW));
		assertEquals(getLabel(), "column", m.getAxisAnnotation(Matrix.COLUMN));
		assertEquals(getLabel(), "row0", m.getRowLabel(0));
		assertEquals(getLabel(), "row1", m.getRowLabel(1));
		assertEquals(getLabel(), "column0", m.getColumnLabel(0));
		assertEquals(getLabel(), "column1", m.getColumnLabel(1));

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	@Test
	public void testCountMissingValues() throws Exception {
		Matrix m = createMatrix(4, 4);
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
		Matrix m = createMatrix(2, 2);
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
		Matrix m = createMatrix(2, 2);
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
		Matrix m = createMatrix(2, 2);
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
		Matrix m = createMatrix(2, 2);
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
		Matrix m = createMatrix(119, 119);
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
		Matrix m1 = createMatrix(2, 2);
		Matrix m2 = createMatrix(2, 2);
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
		Matrix m1 = createMatrix(121, 111);
		Matrix m2 = createMatrix(121, 111);
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
		Matrix m = createMatrix(2, 3);
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
		Matrix m = createMatrix(111, 101);
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
		Matrix m = createMatrix(2, 3);
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
		Matrix m = createMatrix(2, 3);
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
		Matrix m = createMatrix(2, 2);
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
		Matrix m = createMatrix(2, 2);
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
		Matrix m = createMatrix(124, 114);
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
		Matrix m = createMatrix(2, 2);
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
		Matrix m = createMatrix(128, 113);
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
		Matrix m = createMatrix(2, 2);
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
		Matrix m = createMatrix(2, 2);
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
		Matrix m1 = createMatrix(2, 2);
		Matrix m2 = createMatrix(2, 2);
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
		Matrix m1 = createMatrix(123, 109);
		Matrix m2 = createMatrix(123, 109);
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
		Matrix m1 = createMatrix(5, 7);
		Matrix m2 = createMatrix(5, 7);
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
		Matrix m1 = createMatrix(5, 7);
		Matrix m2 = createMatrix(5, 7);
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
		Matrix m1 = createMatrix(5, 7);
		Matrix m2 = createMatrix(5, 7);
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
		Matrix m1 = createMatrix(5, 7);
		Matrix m2 = createMatrix(5, 7);
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
		Matrix m = createMatrix(6, 7);
		m.randn(Ret.ORIG);

		double[][] array = m.toDoubleArray();

		for (int r = 0; r < m.getRowCount(); r++) {
			for (int c = 0; c < m.getColumnCount(); c++) {
				assertEquals(getLabel(), m.getAsDouble(r, c), array[r][c], 0.0);
			}
		}
	}

	@Test
	public void testToDoubleArrayLarge() throws Exception {
		Matrix m = createMatrix(106, 117);
		m.randn(Ret.ORIG);

		double[][] array = m.toDoubleArray();

		for (int r = 0; r < m.getRowCount(); r++) {
			for (int c = 0; c < m.getColumnCount(); c++) {
				assertEquals(getLabel(), m.getAsDouble(r, c), array[r][c], 0.0);
			}
		}
	}

	@Test
	public void testXMinus0Matrix() throws Exception {
		Matrix m1 = createMatrix(5, 7);
		Matrix m2 = createMatrix(5, 7);
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
		Matrix m1 = createMatrix(5, 7);
		Matrix m2 = createMatrix(5, 7);
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
		Matrix m1 = createMatrix(5, 7);
		Matrix m2 = createMatrix(5, 7);
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
		Matrix m1 = createMatrix(5, 7);
		Matrix m2 = createMatrix(5, 7);
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
		Matrix m1 = createMatrix(5, 7);
		Matrix m2 = createMatrix(5, 7);
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
		Matrix m1 = createMatrix(5, 7);
		Matrix m2 = createMatrix(5, 7);
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
		Matrix m1 = createMatrix(5, 7);
		Matrix m2 = createMatrix(5, 7);
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
		Matrix m1 = createMatrix(5, 7);
		Matrix m2 = createMatrix(5, 7);
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
	public void testMTimesSmall() throws Exception {
		Matrix m1 = createMatrix(2, 2);

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
		Matrix m2 = createMatrix(2, 3);
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
	public void testMTimesLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix m1 = createMatrix(109, 111);

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
		Matrix m2 = createMatrix(111, 127);
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
		Matrix m1 = createMatrix(10, 10);

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
			m1.rand(Ret.ORIG);
		} while (m1.isSingular());

		Matrix m2 = m1.inv();
		Matrix m3 = m1.mtimes(m2);
		Matrix eye = DenseMatrix.factory.eye(m1.getSize());
		assertEquals(getLabel(), 0.0, eye.minus(m3).getEuklideanValue(), TOLERANCE);
	}

	@Test
	public void testInvSPDRandSmall() throws Exception {
		Matrix m1 = createMatrix(10, 10);

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
	}

	@Test
	public void testInvRandLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix m1 = createMatrix(128, 128);

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
			m1.rand(Ret.ORIG);
		} while (m1.isSingular());

		Matrix m2 = m1.inv();
		Matrix m3 = m1.mtimes(m2);
		Matrix eye = DenseMatrix.factory.eye(m1.getSize());
		assertEquals(getLabel(), 0.0, eye.minus(m3).getEuklideanValue(), TOLERANCE);
	}

	@Test
	public void testInvSPDRandLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix m1 = createMatrix(128, 128);

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
	}

	public void testInvSmall() throws Exception {
		Matrix m1 = createMatrix(3, 3);

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
	public void testPinvSmall() throws Exception {
		Matrix m1 = createMatrix(3, 3);

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
	public void testGinvSmall() throws Exception {
		Matrix m1 = createMatrix(3, 3);
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
		Matrix a = createMatrix(10, 10);

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
	}

	@Test
	public void testEigRandLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrix(110, 110);

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
			// doesn't habe own eig function
			return;
		}

		a.randn(Ret.ORIG);
		Matrix[] eig = a.eig();
		Matrix prod1 = a.mtimes(eig[0]);
		Matrix prod2 = eig[0].mtimes(eig[1]);

		assertEquals(getLabel(), 0.0, prod1.minus(prod2).getRMS(), TOLERANCE);
	}

	@Test
	public void testEigSymmSmall() throws Exception {
		Matrix a = createMatrix(10, 10);

		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
			return;
		}

		Random random = new Random();
		int rows = (int) a.getRowCount();
		int cols = (int) a.getColumnCount();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols && c <= r; c++) {
				double f = random.nextDouble() - 0.5;
				a.setAsDouble(f - 0.5, r, c);
				a.setAsDouble(f - 0.5, c, r);
			}
		}

		Matrix[] eig = a.eig();
		Matrix prod1 = a.mtimes(eig[0]);
		Matrix prod2 = eig[0].mtimes(eig[1]);

		// tolerance for EJML must be set to larger value
		assertEquals(getLabel(), 0.0, prod1.minus(prod2).getRMS(), TOLERANCE);
	}

	@Test
	public void testEigSymmLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrix(111, 111);

		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
			return;
		}

		Random random = new Random();
		int rows = (int) a.getRowCount();
		int cols = (int) a.getColumnCount();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols && c <= r; c++) {
				double f = random.nextDouble() - 0.5;
				a.setAsDouble(f - 0.5, r, c);
				a.setAsDouble(f - 0.5, c, r);
			}
		}

		Matrix[] eig = a.eig();
		Matrix prod1 = a.mtimes(eig[0]);
		Matrix prod2 = eig[0].mtimes(eig[1]);

		// tolerance for EJML must be set to larger value
		assertEquals(getLabel(), 0.0, prod1.minus(prod2).getRMS(), TOLERANCE);
	}

	@Test
	public void testLUSquareSingularSmall() throws Exception {
		Matrix a = createMatrix(5, 5);

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

		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}

		Matrix[] lu = a.lu();
		Matrix prod = lu[0].mtimes(lu[1]);
		Matrix aperm = lu[2].mtimes(a);

		assertEquals(getLabel(), 0.0, prod.minus(aperm).getRMS(), TOLERANCE);
	}

	@Test
	public void testLUSquareSingularLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrix(116, 116);

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

		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}
		Matrix[] lu = a.lu();
		Matrix prod = lu[0].mtimes(lu[1]);
		Matrix aperm = lu[2].mtimes(a);

		assertEquals(getLabel(), 0.0, prod.minus(aperm).getRMS(), TOLERANCE);
	}

	@Test
	public void testSolveRandSquareSmall() throws Exception {
		Matrix a = createMatrix(2, 2);

		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
			return;
		}

		a.randn(Ret.ORIG);
		Matrix x = createMatrix(2, 4);
		x.randn(Ret.ORIG);
		Matrix b = a.mtimes(x);

		Matrix x2 = a.solve(b);
		Matrix prod = a.mtimes(x2);

		assertEquals(getLabel(), 0.0, prod.minus(b).getRMS(), TOLERANCE);
	}

	@Test
	public void testSolveRandSquareLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrix(125, 125);

		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
			return;
		}

		a.randn(Ret.ORIG);
		Matrix x = createMatrix(125, 142);
		x.randn(Ret.ORIG);
		Matrix b = a.mtimes(x);

		Matrix x2 = a.solve(b);
		Matrix prod = a.mtimes(x2);

		assertEquals(getLabel(), 0.0, prod.minus(b).getRMS(), TOLERANCE);
	}

	@Test
	public void testSolveRandSPDLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrix(125, 125);

		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
			return;
		}

		BenchmarkUtil.randPositiveDefinite(System.currentTimeMillis(), 0, 0, a);
		Matrix x = createMatrix(125, 125);
		x.randn(Ret.ORIG);
		Matrix b = a.mtimes(x);

		Matrix x2 = a.solveSPD(b);
		Matrix prod = a.mtimes(x2);

		assertEquals(getLabel(), 0.0, prod.minus(b).getRMS(), TOLERANCE);
	}

	@Test
	public void testSolveRandSPDSmall() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrix(15, 15);

		// JBlas not supported for 64 bit on windows
		if (System.getProperty("os.name").toLowerCase().contains("windows")
				&& System.getProperty("java.vm.name").contains("64")
				&& a.getClass().getName().startsWith("org.ujmp.jblas")) {
			return;
		}

		BenchmarkUtil.randPositiveDefinite(System.currentTimeMillis(), 0, 0, a);
		Matrix x = createMatrix(15, 15);
		x.randn(Ret.ORIG);
		Matrix b = a.mtimes(x);

		Matrix x2 = a.solveSPD(b);
		Matrix prod = a.mtimes(x2);

		assertEquals(getLabel(), 0.0, prod.minus(b).getRMS(), TOLERANCE);
	}

	@Test
	public void testSolveRandTallSmall() throws Exception {
		Matrix a = createMatrix(6, 2);

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
		Matrix x = createMatrix(2, 4);
		x.randn(Ret.ORIG);
		Matrix b = a.mtimes(x);

		Matrix x2 = a.solve(b);
		Matrix prod = a.mtimes(x2);

		assertEquals(getLabel(), 0.0, prod.minus(b).getRMS(), TOLERANCE);
	}

	@Test
	public void testSolveRandTallLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrix(169, 121);

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
		Matrix x = createMatrix(121, 143);
		x.randn(Ret.ORIG);
		Matrix b = a.mtimes(x);

		Matrix x2 = a.solve(b);
		Matrix prod = a.mtimes(x2);

		assertEquals(getLabel(), 0.0, prod.minus(b).getRMS(), TOLERANCE);
	}

	@Test
	public void testLUSquareRandSmall() throws Exception {
		Matrix a = createMatrix(10, 10);

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

		a.rand(Ret.ORIG);
		Matrix[] lu = a.lu();
		Matrix prod = lu[0].mtimes(lu[1]);
		Matrix aperm = lu[2].mtimes(a);

		assertEquals(getLabel(), 0.0, prod.minus(aperm).getRMS(), TOLERANCE);
	}

	@Test
	public void testLUSquareRandLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrix(112, 112);

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

		a.rand(Ret.ORIG);
		Matrix[] lu = a.lu();
		Matrix prod = lu[0].mtimes(lu[1]);
		Matrix aperm = lu[2].mtimes(a);

		assertEquals(getLabel(), 0.0, prod.minus(aperm).getRMS(), TOLERANCE);
	}

	@Test
	public void testLUTallFixedSmall() throws Exception {
		Matrix a = createMatrix(6, 4);

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

		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}
		Matrix[] lu = a.lu();
		Matrix prod = lu[0].mtimes(lu[1]);
		Matrix aperm = lu[2].mtimes(a);

		assertEquals(0.0, prod.minus(aperm).getRMS(), TOLERANCE);
	}

	@Test
	public void testLUTallFixedLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrix(161, 142);

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

		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}
		Matrix[] lu = a.lu();
		Matrix prod = lu[0].mtimes(lu[1]);
		Matrix aperm = lu[2].mtimes(a);

		assertEquals(0.0, prod.minus(aperm).getRMS(), TOLERANCE);
	}

	@Test
	public void testLUFatFixedSmall() throws Exception {
		Matrix a = createMatrix(4, 6);

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

		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}
		Matrix[] lu = a.lu();
		Matrix prod = lu[0].mtimes(lu[1]);
		Matrix aperm = lu[2].mtimes(a);

		assertEquals(0.0, prod.minus(aperm).getRMS(), TOLERANCE);
	}

	@Test
	public void testLUFatFixedLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrix(141, 162);

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

		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}
		Matrix[] lu = a.lu();
		Matrix prod = lu[0].mtimes(lu[1]);
		Matrix aperm = lu[2].mtimes(a);

		assertEquals(0.0, prod.minus(aperm).getRMS(), TOLERANCE);
	}

	@Test
	public void testQRSquareSmall() throws Exception {
		Matrix a = createMatrix(5, 5);
		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}
		Matrix[] qr = a.qr();
		Matrix prod = qr[0].mtimes(qr[1]);

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);
	}

	@Test
	public void testQRSquareLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrix(151, 151);
		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}
		Matrix[] qr = a.qr();
		Matrix prod = qr[0].mtimes(qr[1]);

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);
	}

	@Test
	public void testQRRandSmall() throws Exception {
		Matrix a = createMatrix(7, 7);
		a.rand(Ret.ORIG);
		Matrix[] qr = a.qr();
		Matrix prod = qr[0].mtimes(qr[1]);

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);
	}

	@Test
	public void testQRRandLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrix(123, 123);
		a.rand(Ret.ORIG);
		Matrix[] qr = a.qr();
		Matrix prod = qr[0].mtimes(qr[1]);

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);
	}

	@Test
	public void testQRFatSmall() throws Exception {
		Matrix a = createMatrix(4, 6);

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

		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}
		Matrix[] qr = a.qr();
		Matrix prod = qr[0].mtimes(qr[1]);

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);
	}

	@Test
	public void testQRFatLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrix(140, 160);

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
	}

	@Test
	public void testQRTallSmall() throws Exception {
		Matrix a = createMatrix(6, 4);

		if (a.getClass().getName().startsWith("org.ujmp.jsci.")) {
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
	}

	@Test
	public void testQRTallLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrix(168, 143);

		if (a.getClass().getName().startsWith("org.ujmp.jsci.")) {
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
	}

	@Test
	public void testCholSmall() throws Exception {
		Matrix pascal = MatrixFactory.pascal(5, 5);
		Matrix a = createMatrix(pascal);

		// only SPD
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

		assertEquals(getLabel(), 0.0, diff.doubleValue(), TOLERANCE);

		if (a instanceof Erasable) {
			((Erasable) a).erase();
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
		Matrix result = createMatrix(temp.mtimes(temp.transpose()));

		// only SPD
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

		assertEquals(getLabel(), 0.0, prod.minus(result).doubleValue(), TOLERANCE);

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
		Matrix result = createMatrix(temp.mtimes(temp.transpose()));

		// only SPD
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

		assertEquals(getLabel(), 0.0, prod.minus(result).doubleValue(), TOLERANCE);

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
		DenseDoubleMatrix2D temp = new DefaultDenseDoubleMatrix2D(100, 100);
		int rows = (int) temp.getRowCount();
		int cols = (int) temp.getColumnCount();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				temp.setDouble(random.nextDouble(), r, c);
			}
		}
		Matrix result = createMatrix(temp.mtimes(temp.transpose()));

		// only SPD
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

		assertEquals(0.0, prod.minus(result).doubleValue(), TOLERANCE);

		if (result instanceof Erasable) {
			((Erasable) result).erase();
		}
	}

	// test example from wikipedia
	@Test
	public void testSVDWikipedia() throws Exception {
		Matrix a = createMatrix(4, 5);

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

		if (a instanceof Closeable) {
			((Closeable) a).close();
		}

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	@Test
	public void testSVDSquareSmall() throws Exception {
		Matrix a = createMatrix(5, 5);

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

		if (a instanceof Closeable) {
			((Closeable) a).close();
		}

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	@Test
	public void testSVDSquareLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrix(107, 107);

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

		if (a instanceof Closeable) {
			((Closeable) a).close();
		}

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	@Test
	public void testSVDSquareRandSmall() throws Exception {
		Matrix a = createMatrix(10, 10);

		// skip libraries which do not support fat matrices
		if (a.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
			return;
		}

		a.rand(Ret.ORIG);

		Matrix[] svd = a.svd();

		Matrix prod = svd[0].mtimes(svd[1]).mtimes(svd[2].transpose());

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);

		if (a instanceof Closeable) {
			((Closeable) a).close();
		}

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	@Test
	public void testSVDSquareRandLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrix(109, 109);

		// skip libraries which do not support fat matrices
		if (a.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
			return;
		}

		a.rand(Ret.ORIG);

		Matrix[] svd = a.svd();

		Matrix prod = svd[0].mtimes(svd[1]).mtimes(svd[2].transpose());

		assertEquals(0.0, prod.minus(a).getRMS(), TOLERANCE);

		if (a instanceof Closeable) {
			((Closeable) a).close();
		}

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	@Test
	public void testSVDFatSmall() throws Exception {
		Matrix a = createMatrix(4, 6);

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

		if (a instanceof Closeable) {
			((Closeable) a).close();
		}

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	@Test
	public void testSVDFatLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrix(123, 142);

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

		if (a instanceof Closeable) {
			((Closeable) a).close();
		}

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	@Test
	public void testSVDTallSmall() throws Exception {
		Matrix a = createMatrix(6, 4);

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

		if (a instanceof Closeable) {
			((Closeable) a).close();
		}

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	@Test
	public void testSVDTallLarge() throws Exception {
		if (!isTestLarge()) {
			return;
		}
		Matrix a = createMatrix(140, 121);

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

		if (a instanceof Closeable) {
			((Closeable) a).close();
		}

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

}

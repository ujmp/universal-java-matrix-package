/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.DoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.stub.AbstractDoubleMatrix;
import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.util.SerializationUtil;
import org.ujmp.core.util.UJMPSettings;

public abstract class AbstractMatrixTest extends TestCase {

	public abstract Matrix createMatrix(long... size) throws Exception;

	public abstract Matrix createMatrix(Matrix source) throws Exception;

	public String getLabel() {
		return this.getClass().getSimpleName();
	}

	public Matrix getZeroSizeMatrix() throws Exception {
		Matrix m = createMatrix(0, 0);
		return m;
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

	public void testExtractAnnotation() throws Exception {
		Matrix m1 = MatrixFactory.randn(5, 5);
		Matrix m2 = m1.extractAnnotation(Ret.NEW, Matrix.ROW);
		assertEquals(getLabel(), 4, m2.getRowCount());
		Matrix m3 = m2.includeAnnotation(Ret.NEW, Matrix.ROW);
		m3.setAnnotation(null);
		assertEquals(getLabel(), m1, m3);

		m1 = MatrixFactory.randn(5, 5);
		m2 = m1.extractAnnotation(Ret.LINK, Matrix.ROW);
		assertEquals(getLabel(), 4, m2.getRowCount());
		m3 = m2.includeAnnotation(Ret.LINK, Matrix.ROW);
		m3.setAnnotation(null);
		assertEquals(getLabel(), m1, m3);

		m1 = MatrixFactory.randn(5, 5);
		m2 = m1.extractAnnotation(Ret.NEW, Matrix.COLUMN);
		assertEquals(getLabel(), 4, m2.getColumnCount());
		m3 = m2.includeAnnotation(Ret.NEW, Matrix.COLUMN);
		m3.setAnnotation(null);
		assertEquals(getLabel(), m1, m3);

		m1 = MatrixFactory.randn(5, 5);
		m2 = m1.extractAnnotation(Ret.LINK, Matrix.COLUMN);
		assertEquals(getLabel(), 4, m2.getColumnCount());
		m3 = m2.includeAnnotation(Ret.LINK, Matrix.COLUMN);
		m3.setAnnotation(null);
		assertEquals(getLabel(), m1, m3);
	}

	// Test interface CoordinateFunctions
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

	public void testZeroSize() throws Exception {
		Matrix m = getZeroSizeMatrix();
		assertTrue(getLabel(), Coordinates.equals(m.getSize(), new long[] { 0, 0 }));
		assertEquals(getLabel(), 0, m.getRowCount());
		assertEquals(getLabel(), 0, m.getColumnCount());
		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

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

	public void testSize() throws Exception {
		Matrix m = createMatrix(20, 10);
		assertEquals(getLabel(), 20, m.getRowCount());
		assertEquals(getLabel(), 10, m.getColumnCount());

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

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
		Matrix m2 = m.copy();
		assertTrue(getLabel(), m.equalsContent(m2));
		assertTrue(getLabel(), m.equalsAnnotation(m2));

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

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

		assertEquals(getLabel(), 2.0, m1.getAsDouble(0, 0));
		assertEquals(getLabel(), 3.0, m1.getAsDouble(0, 1));
		assertEquals(getLabel(), 1.0, m1.getAsDouble(0, 2));
		assertEquals(getLabel(), 0.0, m1.getAsDouble(0, 3));

		assertEquals(getLabel(), 0.0, m2.getAsDouble(0, 0));
		assertEquals(getLabel(), 3.0, m2.getAsDouble(1, 0));
		assertEquals(getLabel(), 1.0, m2.getAsDouble(2, 0));
		assertEquals(getLabel(), 2.0, m2.getAsDouble(3, 0));

		assertEquals(getLabel(), 6.0, m3.getAsDouble(0, 0));

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	public void testSerialize() throws Exception {
		Matrix m = createMatrix(2, 2);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		byte[] data = SerializationUtil.serialize(m);
		Matrix m2 = (Matrix) SerializationUtil.deserialize(data);
		if (m2.isTransient()) {
			Matrix m0 = MatrixFactory.zeros(2, 2);
			assertEquals(getLabel(), m0, m2);
		} else {
			assertEquals(getLabel(), m, m2);
		}

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	public void testToDoubleArray() throws Exception {
		Matrix m = createMatrix(2, 2);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		double[][] values = m.toDoubleArray();
		assertEquals(getLabel(), 1.0, values[0][0]);
		assertEquals(getLabel(), 2.0, values[0][1]);
		assertEquals(getLabel(), 3.0, values[1][0]);
		assertEquals(getLabel(), 4.0, values[1][1]);

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	public void testSetAndGet() throws Exception {
		Matrix m = createMatrix(2, 2);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		assertEquals(getLabel(), 1.0, m.getAsDouble(0, 0));
		assertEquals(getLabel(), 2.0, m.getAsDouble(0, 1));
		assertEquals(getLabel(), 3.0, m.getAsDouble(1, 0));
		assertEquals(getLabel(), 4.0, m.getAsDouble(1, 1));

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	public void testPlus() throws Exception {
		Matrix m = createMatrix(2, 2);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		m = m.plus(1.0);
		assertEquals(getLabel(), 2.0, m.getAsDouble(0, 0));
		assertEquals(getLabel(), 3.0, m.getAsDouble(0, 1));
		assertEquals(getLabel(), 4.0, m.getAsDouble(1, 0));
		assertEquals(getLabel(), 5.0, m.getAsDouble(1, 1));

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	public void testTranspose() throws Exception {
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
		assertEquals(getLabel(), 1.0, r.getAsDouble(0, 0));
		assertEquals(getLabel(), 4.0, r.getAsDouble(0, 1));
		assertEquals(getLabel(), 2.0, r.getAsDouble(1, 0));
		assertEquals(getLabel(), 5.0, r.getAsDouble(1, 1));
		assertEquals(getLabel(), 3.0, r.getAsDouble(2, 0));
		assertEquals(getLabel(), 6.0, r.getAsDouble(2, 1));

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	public void testTransposeLarge() throws Exception {
		// TODO: check labels
		Matrix m = createMatrix(101, 100);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 0, 2);
		m.setAsDouble(4.0, 1, 0);
		m.setAsDouble(5.0, 1, 1);
		m.setAsDouble(6.0, 1, 2);
		Matrix r = m.transpose();
		assertEquals(getLabel(), m.getRowCount(), r.getColumnCount());
		assertEquals(getLabel(), m.getColumnCount(), r.getRowCount());
		assertEquals(getLabel(), 1.0, r.getAsDouble(0, 0));
		assertEquals(getLabel(), 4.0, r.getAsDouble(0, 1));
		assertEquals(getLabel(), 2.0, r.getAsDouble(1, 0));
		assertEquals(getLabel(), 5.0, r.getAsDouble(1, 1));
		assertEquals(getLabel(), 3.0, r.getAsDouble(2, 0));
		assertEquals(getLabel(), 6.0, r.getAsDouble(2, 1));

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	public void testTransposeNew() throws Exception {
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
		assertEquals(getLabel(), 1.0, r.getAsDouble(0, 0));
		assertEquals(getLabel(), 4.0, r.getAsDouble(0, 1));
		assertEquals(getLabel(), 2.0, r.getAsDouble(1, 0));
		assertEquals(getLabel(), 5.0, r.getAsDouble(1, 1));
		assertEquals(getLabel(), 3.0, r.getAsDouble(2, 0));
		assertEquals(getLabel(), 6.0, r.getAsDouble(2, 1));
		assertEquals(getLabel(), "label", r.getLabel());
		assertEquals(getLabel(), "row1", r.getColumnLabel(1));
		assertEquals(getLabel(), "col2", r.getRowLabel(2));

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	public void testTransposeLink() throws Exception {
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
		assertEquals(getLabel(), 1.0, r.getAsDouble(0, 0));
		assertEquals(getLabel(), 4.0, r.getAsDouble(0, 1));
		assertEquals(getLabel(), 2.0, r.getAsDouble(1, 0));
		assertEquals(getLabel(), 5.0, r.getAsDouble(1, 1));
		assertEquals(getLabel(), 3.0, r.getAsDouble(2, 0));
		assertEquals(getLabel(), 6.0, r.getAsDouble(2, 1));
		assertEquals(getLabel(), "label", r.getLabel());
		assertEquals(getLabel(), "row1", r.getColumnLabel(1));
		assertEquals(getLabel(), "col2", r.getRowLabel(2));

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	public void testEmpty() throws Exception {
		Matrix m = createMatrix(2, 2);
		if (m instanceof AbstractDoubleMatrix) {
			assertEquals(getLabel(), 0.0, m.getAsDouble(0, 0));
			assertEquals(getLabel(), 0.0, m.getAsDouble(0, 1));
			assertEquals(getLabel(), 0.0, m.getAsDouble(1, 0));
			assertEquals(getLabel(), 0.0, m.getAsDouble(1, 1));
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

	public void testMinus() throws Exception {
		Matrix m = createMatrix(2, 2);
		m.setAsDouble(1.0, 0, 0);
		m.setAsDouble(2.0, 0, 1);
		m.setAsDouble(3.0, 1, 0);
		m.setAsDouble(4.0, 1, 1);
		m = m.minus(1.0);
		assertEquals(getLabel(), 0.0, m.getAsDouble(0, 0));
		assertEquals(getLabel(), 1.0, m.getAsDouble(0, 1));
		assertEquals(getLabel(), 2.0, m.getAsDouble(1, 0));
		assertEquals(getLabel(), 3.0, m.getAsDouble(1, 1));

		if (m instanceof Erasable) {
			((Erasable) m).erase();
		}
	}

	public void testMTimes() throws Exception {
		Matrix m1 = createMatrix(2, 2);
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
		assertEquals(getLabel(), -9.0, m3.getAsDouble(0, 0));
		assertEquals(getLabel(), 12.0, m3.getAsDouble(0, 1));
		assertEquals(getLabel(), -15.0, m3.getAsDouble(0, 2));
		assertEquals(getLabel(), -19.0, m3.getAsDouble(1, 0));
		assertEquals(getLabel(), 26.0, m3.getAsDouble(1, 1));
		assertEquals(getLabel(), -33.0, m3.getAsDouble(1, 2));

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}

		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
	}

	public void testMTimesLarge() throws Exception {
		Matrix m1 = createMatrix(100, 101);
		m1.setAsDouble(-1.0, 0, 0);
		m1.setAsDouble(2.0, 0, 1);
		m1.setAsDouble(-3.0, 1, 0);
		m1.setAsDouble(4.0, 1, 1);
		Matrix m2 = createMatrix(101, 102);
		m2.setAsDouble(1.0, 0, 0);
		m2.setAsDouble(-2.0, 0, 1);
		m2.setAsDouble(3.0, 0, 2);
		m2.setAsDouble(-4.0, 1, 0);
		m2.setAsDouble(5.0, 1, 1);
		m2.setAsDouble(-6.0, 1, 2);
		Matrix m3 = m1.mtimes(m2);
		assertEquals(getLabel(), 100, m3.getRowCount());
		assertEquals(getLabel(), 102, m3.getColumnCount());
		assertEquals(getLabel(), -9.0, m3.getAsDouble(0, 0));
		assertEquals(getLabel(), 12.0, m3.getAsDouble(0, 1));
		assertEquals(getLabel(), -15.0, m3.getAsDouble(0, 2));
		assertEquals(getLabel(), -19.0, m3.getAsDouble(1, 0));
		assertEquals(getLabel(), 26.0, m3.getAsDouble(1, 1));
		assertEquals(getLabel(), -33.0, m3.getAsDouble(1, 2));

		if (m1 instanceof Erasable) {
			((Erasable) m1).erase();
		}

		if (m2 instanceof Erasable) {
			((Erasable) m2).erase();
		}
	}

	public void testInvRand() throws Exception {
		Matrix m1 = createMatrix(10, 10);

		if (m1.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}

		do {
			m1.rand(Ret.ORIG);
		} while (m1.isSingular());

		Matrix m2 = m1.inv();
		Matrix m3 = m1.mtimes(m2);
		Matrix eye = MatrixFactory.eye(m1.getSize());
		assertEquals(getLabel(), 0.0, eye.minus(m3).getEuklideanValue(), UJMPSettings
				.getTolerance());
	}

	public void testInv() throws Exception {
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

	public void testPinv() throws Exception {
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

	public void testGinv() throws Exception {
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

	public void testEig() throws Exception {
		Matrix a = createMatrix(5, 5);

		// skip libraries which do not support all matrices
		if (a.getClass().getName().startsWith("org.ujmp.commonsmath.")) {
			// only symmetric matrices
			return;
		}

		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}
		Matrix[] eig = a.eig();
		Matrix prod1 = a.mtimes(eig[0]);
		Matrix prod2 = eig[0].mtimes(eig[1]);

		assertEquals(0.0, prod1.minus(prod2).getRMS(), UJMPSettings.getTolerance());
	}

	public void testEigRand() throws Exception {
		Matrix a = createMatrix(10, 10);

		// skip libraries which do not support all matrices
		if (a.getClass().getName().startsWith("org.ujmp.commonsmath.")) {
			// only symmetric matrices
			return;
		}

		a.rand(Ret.ORIG);
		Matrix[] eig = a.eig();
		Matrix prod1 = a.mtimes(eig[0]);
		Matrix prod2 = eig[0].mtimes(eig[1]);

		assertEquals(0.0, prod1.minus(prod2).getRMS(), UJMPSettings.getTolerance());
	}

	public void testLUSquare() throws Exception {
		Matrix a = createMatrix(5, 5);

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

		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}
		Matrix[] lu = a.lu();
		Matrix prod = lu[0].mtimes(lu[1]);
		Matrix aperm = lu[2].mtimes(a);

		assertEquals(getLabel(), 0.0, prod.minus(aperm).getRMS(), UJMPSettings.getTolerance());
	}

	public void testLURand() throws Exception {
		Matrix a = createMatrix(10, 10);

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

		a.rand(Ret.ORIG);
		Matrix[] lu = a.lu();
		Matrix prod = lu[0].mtimes(lu[1]);
		Matrix aperm = lu[2].mtimes(a);

		assertEquals(getLabel(), 0.0, prod.minus(aperm).getRMS(), UJMPSettings.getTolerance());
	}

	public void testLUTall() throws Exception {
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

		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}
		Matrix[] lu = a.lu();
		Matrix prod = lu[0].mtimes(lu[1]);
		Matrix aperm = lu[2].mtimes(a);

		assertEquals(0.0, prod.minus(aperm).getRMS(), UJMPSettings.getTolerance());
	}

	public void testLUFat() throws Exception {
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

		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}
		Matrix[] lu = a.lu();
		Matrix prod = lu[0].mtimes(lu[1]);
		Matrix aperm = lu[2].mtimes(a);

		assertEquals(0.0, prod.minus(aperm).getRMS(), UJMPSettings.getTolerance());
	}

	public void testQRSquare() throws Exception {
		Matrix a = createMatrix(5, 5);
		for (int r = 0, v = 1; r < a.getRowCount(); r++) {
			for (int c = 0; c < a.getColumnCount(); c++) {
				a.setAsDouble(v++, r, c);
			}
		}
		Matrix[] qr = a.qr();
		Matrix prod = qr[0].mtimes(qr[1]);

		assertEquals(0.0, prod.minus(a).getRMS(), UJMPSettings.getTolerance());
	}

	public void testQRRand() throws Exception {
		Matrix a = createMatrix(10, 10);
		a.rand(Ret.ORIG);
		Matrix[] qr = a.qr();
		Matrix prod = qr[0].mtimes(qr[1]);

		assertEquals(0.0, prod.minus(a).getRMS(), UJMPSettings.getTolerance());
	}

	public void testQRFat() throws Exception {
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

		assertEquals(0.0, prod.minus(a).getRMS(), UJMPSettings.getTolerance());
	}

	public void testQRTall() throws Exception {
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

		assertEquals(0.0, prod.minus(a).getRMS(), UJMPSettings.getTolerance());
	}

	public void testChol() throws Exception {
		Matrix a = MatrixFactory.pascal(5, 5);
		Matrix chol = a.chol();
		Matrix prod = chol.transpose().mtimes(chol);

		assertEquals(0.0, prod.minus(a).doubleValue(), UJMPSettings.getTolerance());
	}

	public void testCholRand() throws Exception {
		Random random = new Random(System.nanoTime());
		DenseDoubleMatrix2D temp = new DefaultDenseDoubleMatrix2D(10, 10);
		int rows = (int) temp.getRowCount();
		int cols = (int) temp.getColumnCount();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				temp.setDouble(random.nextDouble(), r, c);
			}
		}
		DenseDoubleMatrix2D result = (DenseDoubleMatrix2D) temp.mtimes(temp.transpose());

		Matrix chol = result.chol();
		Matrix prod = chol.transpose().mtimes(chol);

		assertEquals(0.0, prod.minus(result).doubleValue(), UJMPSettings.getTolerance());
	}

	public void randPositiveDefinit(long seed, DoubleMatrix2D matrix) {

	}

	// test example from wikipedia
	public void testSVDWikipedia() throws Exception {
		Matrix a = createMatrix(4, 5);

		// skip libraries which do not support fat matrices
		if (a.getClass().getName().startsWith("org.ujmp.commonsmath.")) {
			return;
		}
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

		assertEquals(0.0, prod.minus(a).getRMS(), UJMPSettings.getTolerance());

		if (a instanceof Closeable) {
			((Closeable) a).close();
		}

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	public void testSVDSquare() throws Exception {
		Matrix a = createMatrix(5, 5);

		// skip libraries which do not support fat matrices
		if (a.getClass().getName().startsWith("org.ujmp.commonsmath.")) {
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

		assertEquals(0.0, prod.minus(a).getRMS(), UJMPSettings.getTolerance());

		if (a instanceof Closeable) {
			((Closeable) a).close();
		}

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	public void testSVDRand() throws Exception {
		Matrix a = createMatrix(10, 10);

		// skip libraries which do not support fat matrices
		if (a.getClass().getName().startsWith("org.ujmp.commonsmath.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.owlpack.")) {
			return;
		}
		if (a.getClass().getName().startsWith("org.ujmp.vecmath.")) {
			return;
		}

		a.rand(Ret.ORIG);

		Matrix[] svd = a.svd();

		Matrix prod = svd[0].mtimes(svd[1]).mtimes(svd[2].transpose());

		assertEquals(0.0, prod.minus(a).getRMS(), UJMPSettings.getTolerance());

		if (a instanceof Closeable) {
			((Closeable) a).close();
		}

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	public void testSVDFat() throws Exception {
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

		assertEquals(0.0, prod.minus(a).getRMS(), UJMPSettings.getTolerance());

		if (a instanceof Closeable) {
			((Closeable) a).close();
		}

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

	public void testSVDTall() throws Exception {
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

		assertEquals(0.0, prod.minus(a).getRMS(), UJMPSettings.getTolerance());

		if (a instanceof Closeable) {
			((Closeable) a).close();
		}

		if (a instanceof Erasable) {
			((Erasable) a).erase();
		}
	}

}

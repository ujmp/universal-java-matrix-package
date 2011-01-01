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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.enums.ValueType;

public class TestSortrows {

	private Matrix getMatrix(ValueType valueType) {
		Matrix m = MatrixFactory.zeros(valueType, 4, 4);
		m.setRowLabel(0, "row0");
		m.setRowLabel(1, "row1");
		m.setRowLabel(2, "row2");
		m.setRowLabel(3, "row3");
		m.setColumnLabel(0, "col0");
		m.setColumnLabel(1, "col1");
		m.setColumnLabel(2, "col2");
		m.setColumnLabel(3, "col3");
		m.setAsDouble(1, 0, 0);
		m.setAsDouble(2, 0, 1);
		m.setAsDouble(30, 0, 2);
		m.setAsDouble(4, 0, 3);
		m.setAsDouble(5, 1, 0);
		m.setAsDouble(6, 1, 1);
		m.setAsDouble(-700, 1, 2);
		m.setAsDouble(8, 1, 3);
		m.setAsDouble(9, 2, 0);
		m.setAsDouble(1, 2, 1);
		m.setAsDouble(-2, 2, 2);
		m.setAsDouble(3, 2, 3);
		m.setAsDouble(-4, 3, 0);
		m.setAsDouble(5, 3, 1);
		m.setAsDouble(6, 3, 2);
		m.setAsDouble(7, 3, 3);
		return m;
	}

	private Matrix getResult(ValueType valueType) {
		Matrix m = MatrixFactory.zeros(valueType, 4, 4);
		if (ValueType.STRING.equals(valueType) || ValueType.OBJECT.equals(valueType)) {
			m.setRowLabel(0, "row2");
			m.setRowLabel(1, "row1");
			m.setRowLabel(2, "row0");
			m.setRowLabel(3, "row3");
		} else {
			m.setRowLabel(0, "row1");
			m.setRowLabel(1, "row2");
			m.setRowLabel(2, "row3");
			m.setRowLabel(3, "row0");
		}
		m.setColumnLabel(0, "col0");
		m.setColumnLabel(1, "col1");
		m.setColumnLabel(2, "col2");
		m.setColumnLabel(3, "col3");

		if (ValueType.STRING.equals(valueType) || ValueType.OBJECT.equals(valueType)) {
			m.setAsDouble(9, 0, 0);
			m.setAsDouble(1, 0, 1);
			m.setAsDouble(-2, 0, 2);
			m.setAsDouble(3, 0, 3);
			m.setAsDouble(5, 1, 0);
			m.setAsDouble(6, 1, 1);
			m.setAsDouble(-700, 1, 2);
			m.setAsDouble(8, 1, 3);
			m.setAsDouble(1, 2, 0);
			m.setAsDouble(2, 2, 1);
			m.setAsDouble(30, 2, 2);
			m.setAsDouble(4, 2, 3);
			m.setAsDouble(-4, 3, 0);
			m.setAsDouble(5, 3, 1);
			m.setAsDouble(6, 3, 2);
			m.setAsDouble(7, 3, 3);
		} else {
			m.setAsDouble(5, 0, 0);
			m.setAsDouble(6, 0, 1);
			m.setAsDouble(-700, 0, 2);
			m.setAsDouble(8, 0, 3);
			m.setAsDouble(9, 1, 0);
			m.setAsDouble(1, 1, 1);
			m.setAsDouble(-2, 1, 2);
			m.setAsDouble(3, 1, 3);
			m.setAsDouble(-4, 2, 0);
			m.setAsDouble(5, 2, 1);
			m.setAsDouble(6, 2, 2);
			m.setAsDouble(7, 2, 3);
			m.setAsDouble(1, 3, 0);
			m.setAsDouble(2, 3, 1);
			m.setAsDouble(30, 3, 2);
			m.setAsDouble(4, 3, 3);
		}
		return m;
	}

	private Matrix getResultReverse(ValueType valueType) {
		Matrix m = MatrixFactory.zeros(valueType, 4, 4);
		if (ValueType.STRING.equals(valueType) || ValueType.OBJECT.equals(valueType)) {
			m.setRowLabel(0, "row3");
			m.setRowLabel(1, "row0");
			m.setRowLabel(2, "row1");
			m.setRowLabel(3, "row2");
		} else {
			m.setRowLabel(0, "row0");
			m.setRowLabel(1, "row3");
			m.setRowLabel(2, "row2");
			m.setRowLabel(3, "row1");
		}
		m.setColumnLabel(0, "col0");
		m.setColumnLabel(1, "col1");
		m.setColumnLabel(2, "col2");
		m.setColumnLabel(3, "col3");

		if (ValueType.STRING.equals(valueType) || ValueType.OBJECT.equals(valueType)) {
			m.setAsDouble(-4, 0, 0);
			m.setAsDouble(5, 0, 1);
			m.setAsDouble(6, 0, 2);
			m.setAsDouble(7, 0, 3);
			m.setAsDouble(1, 1, 0);
			m.setAsDouble(2, 1, 1);
			m.setAsDouble(30, 1, 2);
			m.setAsDouble(4, 1, 3);
			m.setAsDouble(5, 2, 0);
			m.setAsDouble(6, 2, 1);
			m.setAsDouble(-700, 2, 2);
			m.setAsDouble(8, 2, 3);
			m.setAsDouble(9, 3, 0);
			m.setAsDouble(1, 3, 1);
			m.setAsDouble(-2, 3, 2);
			m.setAsDouble(3, 3, 3);
		} else {
			m.setAsDouble(1, 0, 0);
			m.setAsDouble(2, 0, 1);
			m.setAsDouble(30, 0, 2);
			m.setAsDouble(4, 0, 3);
			m.setAsDouble(-4, 1, 0);
			m.setAsDouble(5, 1, 1);
			m.setAsDouble(6, 1, 2);
			m.setAsDouble(7, 1, 3);
			m.setAsDouble(9, 2, 0);
			m.setAsDouble(1, 2, 1);
			m.setAsDouble(-2, 2, 2);
			m.setAsDouble(3, 2, 3);
			m.setAsDouble(5, 3, 0);
			m.setAsDouble(6, 3, 1);
			m.setAsDouble(-700, 3, 2);
			m.setAsDouble(8, 3, 3);
		}
		return m;
	}

	@Test
	public void testSortDoubleNew() throws Exception {
		Matrix m1 = getMatrix(ValueType.DOUBLE);
		Matrix m2 = m1.sortrows(Ret.NEW, 2, false);
		assertEquals(ValueType.DOUBLE, m2.getValueType());
		assertEquals(getResult(ValueType.DOUBLE), m2);
	}

	@Test
	public void testSortStringNew() throws Exception {
		Matrix m1 = getMatrix(ValueType.STRING);
		Matrix m2 = m1.sortrows(Ret.NEW, 2, false);
		assertEquals(ValueType.STRING, m2.getValueType());
		assertEquals(getResult(ValueType.STRING), m2);
	}

	// TODO
	// public void testSortStringLink() throws Exception {
	// Matrix m1 = getMatrix(ValueType.STRING);
	// Matrix m2 = m1.sortrows(Ret.LINK, 2, false);
	// assertEquals(ValueType.STRING, m2.getValueType());
	// assertEquals(getResult(ValueType.STRING), m2);
	// }

	@Test
	public void testSortBigDecimalNew() throws Exception {
		Matrix m1 = getMatrix(ValueType.BIGDECIMAL);
		Matrix m2 = m1.sortrows(Ret.NEW, 2, false);
		assertEquals(ValueType.BIGDECIMAL, m2.getValueType());
		assertEquals(getResult(ValueType.BIGDECIMAL), m2);
	}

	// TODO
	// public void testSortDoubleLink() throws Exception {
	// Matrix m1 = getMatrix(ValueType.DOUBLE);
	// Matrix m2 = m1.sortrows(Ret.LINK, 2, false);
	// assertEquals(ValueType.DOUBLE, m2.getValueType());
	// assertEquals(getResult(ValueType.DOUBLE), m2);
	// }

	// TODO
	// public void testSortBigDecimalLink() throws Exception {
	// Matrix m1 = getMatrix(ValueType.BIGDECIMAL);
	// Matrix m2 = m1.sortrows(Ret.LINK, 2, false);
	// assertEquals(ValueType.BIGDECIMAL, m2.getValueType());
	// assertEquals(getResult(ValueType.BIGDECIMAL), m2);
	// }

	@Test
	public void testSortDoubleReverseNew() throws Exception {
		Matrix m1 = getMatrix(ValueType.DOUBLE);
		Matrix m2 = m1.sortrows(Ret.NEW, 2, true);
		assertEquals(ValueType.DOUBLE, m2.getValueType());
		assertEquals(getResultReverse(ValueType.DOUBLE), m2);
	}

	@Test
	public void testSortStringReverseNew() throws Exception {
		Matrix m1 = getMatrix(ValueType.STRING);
		Matrix m2 = m1.sortrows(Ret.NEW, 2, true);
		assertEquals(ValueType.STRING, m2.getValueType());
		assertEquals(getResultReverse(ValueType.STRING), m2);
	}

	// TODO
	// public void testSortStringReverseLink() throws Exception {
	// Matrix m1 = getMatrix(ValueType.STRING);
	// Matrix m2 = m1.sortrows(Ret.LINK, 2, true);
	// assertEquals(ValueType.STRING, m2.getValueType());
	// assertEquals(getResultReverse(ValueType.STRING), m2);
	// }

	@Test
	public void testSortBigDecimalReverseNew() throws Exception {
		Matrix m1 = getMatrix(ValueType.BIGDECIMAL);
		Matrix m2 = m1.sortrows(Ret.NEW, 2, true);
		assertEquals(ValueType.BIGDECIMAL, m2.getValueType());
		assertEquals(getResultReverse(ValueType.BIGDECIMAL), m2);
	}

	// TODO
	// public void testSortDoubleReverseLink() throws Exception {
	// Matrix m1 = getMatrix(ValueType.DOUBLE);
	// Matrix m2 = m1.sortrows(Ret.LINK, 2, true);
	// assertEquals(ValueType.DOUBLE, m2.getValueType());
	// assertEquals(getResultReverse(ValueType.DOUBLE), m2);
	// }

	// TODO
	// public void testSortBigDecimalReverseLink() throws Exception {
	// Matrix m1 = getMatrix(ValueType.BIGDECIMAL);
	// Matrix m2 = m1.sortrows(Ret.LINK, 2, true);
	// assertEquals(ValueType.BIGDECIMAL, m2.getValueType());
	// assertEquals(getResultReverse(ValueType.BIGDECIMAL), m2);
	// }
}

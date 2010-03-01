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

package org.ujmp.core.annotation;

import junit.framework.TestCase;

import org.ujmp.core.Matrix;

public class TestAnnotation extends TestCase {

	public void testReverseAnnotation() throws Exception {
		Matrix m = Matrix.factory.dense(10, 10);
		m.setColumnLabel(3, "col3");
		m.setRowLabel(3, "row3");
		m.setColumnLabel(5, "col5");
		m.setRowLabel(5, "row5");

		assertEquals(3, m.getRowForLabel("row3"));
		assertEquals(3, m.getColumnForLabel("col3"));
		assertEquals(5, m.getRowForLabel("row5"));
		assertEquals(5, m.getColumnForLabel("col5"));
		assertEquals(-1, m.getColumnForLabel("col1"));
		assertEquals(-1, m.getRowForLabel("col1"));

	}

}

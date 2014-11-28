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

package org.ujmp.core.calculation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

public class TestDeletion {

	@Test
	public void testDeleteRow0() {
		Matrix m = getMatrix();
		System.out.println(m);
		m.deleteRows(Ret.NEW, 0);
		assertEquals(4, m.getRowCount());
		assertEquals(5, m.getColumnCount());
		assertEquals(1, m.getAsLong(0, 0));
		assertEquals(2, m.getAsLong(0, 1));
		assertEquals(1, m.getAsLong(1, 0));
		System.out.println(m);
	}

	private Matrix getMatrix() {
		Matrix m = Matrix.Factory.pascal(5, 5);
		return m;
	}

}

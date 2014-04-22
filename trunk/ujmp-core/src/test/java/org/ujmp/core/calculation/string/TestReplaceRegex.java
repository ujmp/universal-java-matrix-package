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

package org.ujmp.core.calculation.string;

import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.stringmatrix.calculation.ReplaceRegex;

public class TestReplaceRegex {

	private static Matrix getTestMatrix() {
		Matrix m = Matrix.Factory.zeros(ValueType.STRING, 2, 2);
		m.setAsString("aabbcabd", 0, 0);
		m.setAsString(null, 0, 1);
		m.setAsString("ad", 1, 0);
		m.setAsString("aab", 1, 1);
		return m;
	}

	private static Matrix getResultMatrix() {
		Matrix m = Matrix.Factory.zeros(ValueType.STRING, 2, 2);
		m.setAsString("afgrbcfgrd", 0, 0);
		m.setAsString(null, 0, 1);
		m.setAsString("ad", 1, 0);
		m.setAsString("afgr", 1, 1);
		return m;
	}

	@Test
	public void testConstructor1() {
		Matrix matrix = getTestMatrix();
		ReplaceRegex ra = new ReplaceRegex(matrix, "ab", "fgr");
		Matrix resultMatrix = ra.calc(Ret.NEW);
		Assert.assertEquals(getResultMatrix(), resultMatrix);
	}

	@Test
	public void testConstructor2() {
		Matrix matrix = getTestMatrix();
		ReplaceRegex ra = new ReplaceRegex(matrix, Pattern.compile("ab"), "fgr");
		Matrix resultMatrix = ra.calc(Ret.NEW);
		Assert.assertEquals(getResultMatrix(), resultMatrix);
	}

	@Test
	public void testCalc1() {
		Matrix matrix = getTestMatrix();
		Matrix resultMatrix = ReplaceRegex.calc(matrix, "ab", "fgr");
		Assert.assertEquals(getResultMatrix(), resultMatrix);
	}

	@Test
	public void testCalc2() {
		Matrix matrix = getTestMatrix();
		Matrix resultMatrix = ReplaceRegex.calc(matrix, Pattern.compile("ab"), "fgr");
		Assert.assertEquals(getResultMatrix(), resultMatrix);
	}

}

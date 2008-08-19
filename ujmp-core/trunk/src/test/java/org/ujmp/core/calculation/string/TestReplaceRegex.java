/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

import junit.framework.Assert;
import junit.framework.TestCase;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublecalculation.entrywise.replace.ReplaceRegex;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;

public class TestReplaceRegex extends TestCase {

	private static Matrix getTestMatrix() throws MatrixException {
		Matrix m = MatrixFactory.zeros(ValueType.STRING, 2, 2);
		m.setAsString("aabbcabd", 0, 0);
		m.setAsString(null, 0, 1);
		m.setAsString("ad", 1, 0);
		m.setAsString("aab", 1, 1);
		return m;
	}

	private static Matrix getResultMatrix() throws MatrixException {
		Matrix m = MatrixFactory.zeros(ValueType.STRING, 2, 2);
		m.setAsString("afgrbcfgrd", 0, 0);
		m.setAsString(null, 0, 1);
		m.setAsString("ad", 1, 0);
		m.setAsString("afgr", 1, 1);
		return m;
	}

	public void testConstructor1() throws MatrixException {
		Matrix matrix = getTestMatrix();
		ReplaceRegex ra = new ReplaceRegex(matrix, "ab", "fgr");
		Matrix resultMatrix = ra.calc(Ret.NEW);
		Assert.assertEquals(getResultMatrix(), resultMatrix);
	}

	public void testConstructor2() throws MatrixException {
		Matrix matrix = getTestMatrix();
		ReplaceRegex ra = new ReplaceRegex(matrix, Pattern.compile("ab"), "fgr");
		Matrix resultMatrix = ra.calc(Ret.NEW);
		Assert.assertEquals(getResultMatrix(), resultMatrix);
	}

	public void testCalc1() throws MatrixException {
		Matrix matrix = getTestMatrix();
		Matrix resultMatrix = ReplaceRegex.calc(matrix, "ab", "fgr");
		Assert.assertEquals(getResultMatrix(), resultMatrix);
	}

	public void testCalc2() throws MatrixException {
		Matrix matrix = getTestMatrix();
		Matrix resultMatrix = ReplaceRegex.calc(matrix, Pattern.compile("ab"), "fgr");
		Assert.assertEquals(getResultMatrix(), resultMatrix);
	}

	public static void main(String[] args) throws MatrixException {
		TestReplaceRegex test = new TestReplaceRegex();
		test.testConstructor1();
	}

}

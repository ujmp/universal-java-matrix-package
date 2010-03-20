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

package org.ujmp.core;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTestSuite(MatrixFactoryTest.class);
		suite.addTest(org.ujmp.core.doublematrix.impl.AllTests.suite());
		suite.addTest(org.ujmp.core.calculation.AllTests.suite());
		suite.addTest(org.ujmp.core.collections.AllTests.suite());
		suite.addTest(org.ujmp.core.implementations.AllTests.suite());
		suite.addTest(org.ujmp.core.io.AllTests.suite());
		suite.addTest(org.ujmp.core.util.AllTests.suite());
		suite.addTest(org.ujmp.core.annotation.AllTests.suite());
		return suite;
	}

}

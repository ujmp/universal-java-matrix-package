/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.ujmp.complete;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTest(org.ujmp.core.AllTests.suite());
		suite.addTest(org.ujmp.commonsmath.AllTests.suite());
		suite.addTest(org.ujmp.bpca.AllTests.suite());
		suite.addTest(org.ujmp.lsimpute.AllTests.suite());
		suite.addTest(org.ujmp.colt.AllTests.suite());
		suite.addTest(org.ujmp.jama.AllTests.suite());
		suite.addTest(org.ujmp.jmatrices.AllTests.suite());
		suite.addTest(org.ujmp.mtj.AllTests.suite());
		suite.addTest(org.ujmp.vecmath.AllTests.suite());
		suite.addTest(org.ujmp.jackcess.AllTests.suite());
		suite.addTest(org.ujmp.jung.AllTests.suite());
		suite.addTest(org.ujmp.jexcelapi.AllTests.suite());
		suite.addTest(org.ujmp.jmatio.AllTests.suite());
		suite.addTest(org.ujmp.itext.AllTests.suite());
		suite.addTest(org.ujmp.mail.AllTests.suite());
		suite.addTest(org.ujmp.sst.AllTests.suite());
		suite.addTest(org.ujmp.parallelcolt.AllTests.suite());
		suite.addTest(org.ujmp.ojalgo.AllTests.suite());
		suite.addTest(org.ujmp.mantissa.AllTests.suite());
		suite.addTest(org.ujmp.jscience.AllTests.suite());
		suite.addTest(org.ujmp.jsci.AllTests.suite());
		return suite;
	}

}

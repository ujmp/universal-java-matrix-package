package org.ujmp.jackcess;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTestSuite(TestExportMatrixMDB.class);
		return suite;
	}

}
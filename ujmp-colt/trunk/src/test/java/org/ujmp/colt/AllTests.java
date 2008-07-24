package org.ujmp.colt;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTestSuite(TestColtDenseDoubleMatrix2D.class);
		suite.addTestSuite(TestColtSparseDoubleMatrix2D.class);
		return suite;
	}

}

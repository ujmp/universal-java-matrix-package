package org.ujmp.parallelcolt;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTestSuite(TestParallelColtDenseDoubleMatrix2D.class);
		suite.addTestSuite(TestParallelColtSparseDoubleMatrix2D.class);
		return suite;
	}

}

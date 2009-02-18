package org.ujmp.complete.benchmark;

import org.ujmp.colt.benchmark.ColtDenseDoubleMatrix2DBenchmark;
import org.ujmp.core.benchmark.DefaultDenseDoubleMatrix2DBenchmark;

public class MatrixBenchmark {

	public static void main(String[] args) throws Exception {
		new DefaultDenseDoubleMatrix2DBenchmark().run();
		new ColtDenseDoubleMatrix2DBenchmark().run();
	}

}

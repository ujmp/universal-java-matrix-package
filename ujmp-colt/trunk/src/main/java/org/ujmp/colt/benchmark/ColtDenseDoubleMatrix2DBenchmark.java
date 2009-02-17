package org.ujmp.colt.benchmark;

import org.ujmp.colt.ColtDenseDoubleMatrix2D;
import org.ujmp.core.Matrix;
import org.ujmp.core.benchmark.AbstractMatrix2DBenchmark;
import org.ujmp.core.exceptions.MatrixException;

public class ColtDenseDoubleMatrix2DBenchmark extends AbstractMatrix2DBenchmark {

	@Override
	public Matrix createMatrix(long... size) throws MatrixException {
		return new ColtDenseDoubleMatrix2D(size);
	}

	@Override
	public Matrix createMatrix(Matrix source) throws MatrixException {
		return new ColtDenseDoubleMatrix2D(source);
	}

	public static void main(String[] args) throws Exception {
		new ColtDenseDoubleMatrix2DBenchmark().run();
	}

}

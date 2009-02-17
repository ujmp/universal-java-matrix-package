package org.ujmp.core.benchmark;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;

public class DefaultDenseDoubleMatrix2DBenchmark extends AbstractMatrix2DBenchmark {

	@Override
	public Matrix createMatrix(long... size) throws MatrixException {
		return new DefaultDenseDoubleMatrix2D(size);
	}

	@Override
	public Matrix createMatrix(Matrix source) throws MatrixException {
		return new DefaultDenseDoubleMatrix2D(source);
	}

	public static void main(String[] args) throws Exception {
		new DefaultDenseDoubleMatrix2DBenchmark().run();
	}

}

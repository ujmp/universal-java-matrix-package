package org.ujmp.parallelcolt;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.implementations.AbstractMatrixTest;

public class TestParallelColtSparseDoubleMatrix2D extends AbstractMatrixTest {

	@Override
	public Matrix createMatrix(long... size) throws MatrixException {
		return new ParallelColtSparseDoubleMatrix2D(size);
	}

	@Override
	public Matrix createMatrix(Matrix source) throws MatrixException {
		return new ParallelColtSparseDoubleMatrix2D(source);
	}

}

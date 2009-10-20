package org.ujmp.parallelcolt;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.implementations.AbstractMatrixTest;

public class TestParallelColtDenseDoubleMatrix2D extends AbstractMatrixTest {

	
	public Matrix createMatrix(long... size) throws MatrixException {
		return new ParallelColtDenseDoubleMatrix2D(size);
	}

	
	public Matrix createMatrix(Matrix source) throws MatrixException {
		return new ParallelColtDenseDoubleMatrix2D(source);
	}

}
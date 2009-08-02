package org.ujmp.core.implementations;

import java.io.IOException;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DenseFileMatrix2D;
import org.ujmp.core.exceptions.MatrixException;

public class TestDenseFileMatrix2D extends AbstractMatrixTest {

	@Override
	public Matrix createMatrix(long... size) throws MatrixException, IOException {
		return new DenseFileMatrix2D(size);
	}

	@Override
	public Matrix createMatrix(Matrix source) throws MatrixException, IOException {
		return new DenseFileMatrix2D(source);
	}

	public static void main(String[] args) throws Exception {
		Matrix m = new DenseFileMatrix2D(10, 10);
		m.setAsDouble(5, 5, 5);
		System.out.println(m.getAsDouble(5, 5));
	}

}

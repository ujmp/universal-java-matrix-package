package org.ujmp.core.implementations;

import java.io.IOException;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.impl.DenseFileMatrix2D;
import org.ujmp.core.exceptions.MatrixException;

public class TestDenseFileMatrix2D extends AbstractMatrixTest {

	public Matrix createMatrix(long... size) throws MatrixException, IOException {
		return new DenseFileMatrix2D(size);
	}

	public Matrix createMatrix(Matrix source) throws MatrixException, IOException {
		return new DenseFileMatrix2D(source);
	}

	public boolean isTestLarge() {
		return false;
	}

}

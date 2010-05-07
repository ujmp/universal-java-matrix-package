package org.ujmp.core.implementations;

import java.io.IOException;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.impl.DenseFileMatrix;
import org.ujmp.core.exceptions.MatrixException;

public class TestDenseFileMatrix extends AbstractMatrixTest {

	public Matrix createMatrix(long... size) throws MatrixException, IOException {
		return new DenseFileMatrix(size);
	}

	public Matrix createMatrix(Matrix source) throws MatrixException, IOException {
		return new DenseFileMatrix(source);
	}

	public boolean isTestLarge() {
		return false;
	}

}

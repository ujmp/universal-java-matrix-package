package org.ujmp.core.util;

import org.ujmp.core.Matrix;
import org.ujmp.core.collections.MatrixList;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasMatrixList;
import org.ujmp.core.interfaces.Wrapper;

public class MatrixListToMatrixWrapper extends AbstractDenseDoubleMatrix2D implements
		Wrapper<MatrixList> {
	private static final long serialVersionUID = 3023715319099124633L;

	private MatrixList matrixList = null;

	public MatrixListToMatrixWrapper(HasMatrixList hasMatrixList) {
		this(hasMatrixList.getMatrixList());
	}

	private MatrixListToMatrixWrapper(MatrixList matrixList) {
		this.matrixList = matrixList;
	}

	public long[] getSize() {
		if (matrixList.isEmpty()) {
			return Coordinates.ZERO2D;
		} else {
			return new long[] { matrixList.size(), matrixList.getLast().getColumnCount() };
		}
	}

	public double getDouble(long row, long column) throws MatrixException {
		Matrix m = matrixList.get((int) row);
		if (m != null) {
			return m.getAsDouble(0, column);
		}
		return 0.0;
	}

	public void setDouble(double value, long row, long column) throws MatrixException {
		Matrix m = matrixList.get((int) row);
		if (m != null) {
			m.setAsDouble(value, 0, column);
		}
	}

	public MatrixList getWrappedObject() {
		return matrixList;
	}

	public void setWrappedObject(MatrixList object) {
		this.matrixList = object;
	}

}

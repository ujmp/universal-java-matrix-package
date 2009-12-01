package org.ujmp.core.annotation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;

public class DefaultAnnotation extends AbstractAnnotation {
	private static final long serialVersionUID = -7988756144808776868L;

	private Object matrixAnnotation = null;

	private Map<Integer, Matrix> dimensionMatrices = null;

	public DefaultAnnotation(long... size) {
		super(size);
	}

	public Matrix getDimensionMatrix(int dimension) {
		if (dimensionMatrices == null) {
			dimensionMatrices = new HashMap<Integer, Matrix>(getDimensionCount());
		}
		Matrix m = dimensionMatrices.get(dimension);
		if (m == null) {
			long[] t = Coordinates.copyOf(getSize());
			t[dimension] = 1;
			m = MatrixFactory.sparse(ValueType.OBJECT, t);
			dimensionMatrices.put(dimension, m);
		}
		return m;
	}

	public Object getMatrixAnnotation() {
		return matrixAnnotation;
	}

	public void setMatrixAnnotation(Object matrixAnnotation) {
		this.matrixAnnotation = matrixAnnotation;
	}

	public static void main(String[] args) throws Exception {
		Matrix m = MatrixFactory.randn(4, 5);
		m.setLabel("test");
		m.setColumnLabel(2, "col2");
		m.setRowLabel(1, "row1");
		System.out.println(m.plus(Ret.LINK, true, -2));
	}

	@Override
	public Annotation clone() {
		Annotation a = new DefaultAnnotation(getSize());
		a.setMatrixAnnotation(getMatrixAnnotation());
		for (int i = 0; i < getDimensionCount(); i++) {
			a.setDimensionMatrix(i, getDimensionMatrix(i).copy());
		}
		return a;
	}

	public void clear() {
		matrixAnnotation = null;
		dimensionMatrices = null;
	}

	public Object getAxisAnnotation(int dimension, long... position) {
		Matrix m = getDimensionMatrix(dimension);
		long old = position[dimension];
		position[dimension] = 0;
		Object o = m.getAsObject(position);
		position[dimension] = old;
		return o;
	}

	public long[] getPositionForLabel(int dimension, Object label) {
		if (label == null) {
			throw new MatrixException("label is null");
		}
		Matrix m = getDimensionMatrix(dimension);
		for (long[] c : m.availableCoordinates()) {
			Object o = m.getAsObject(c);
			if (label.equals(o)) {
				return c;
			}
		}
		long[] t = new long[getDimensionCount()];
		Arrays.fill(t, -1);
		return t;
	}

	public void setAxisAnnotation(int dimension, Object label, long... position) {
		Matrix m = getDimensionMatrix(dimension);
		long old = position[dimension];
		position[dimension] = 0;
		m.setAsObject(label, position);
		position[dimension] = old;
	}

	public void setDimensionMatrix(int dimension, Matrix matrix) {
		if (dimensionMatrices == null) {
			dimensionMatrices = new HashMap<Integer, Matrix>(getDimensionCount());
		}
		if (matrix == null) {
			dimensionMatrices.put(dimension, null);
		} else {
			long[] t = Coordinates.copyOf(getSize());
			t[dimension] = 1;
			if (!Coordinates.equals(t, matrix.getSize())) {
				throw new MatrixException("matrix for labels must have size "
						+ Coordinates.toString(t) + " instead of "
						+ Coordinates.toString(matrix.getSize()));
			}
			dimensionMatrices.put(dimension, matrix);
		}
	}

}

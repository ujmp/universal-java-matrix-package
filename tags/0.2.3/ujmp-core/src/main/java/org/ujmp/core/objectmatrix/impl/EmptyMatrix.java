package org.ujmp.core.objectmatrix.impl;

import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.objectmatrix.stub.AbstractDenseObjectMatrix2D;

public class EmptyMatrix extends AbstractDenseObjectMatrix2D {
	private static final long serialVersionUID = 1226331953770561766L;

	@Override
	public Object getObject(long row, long column) {
		return null;
	}

	@Override
	public void setObject(Object value, long row, long column) {
	}

	@Override
	public Object getObject(int row, int column) {
		return null;
	}

	@Override
	public void setObject(Object value, int row, int column) {
	}

	@Override
	public long[] getSize() {
		return Coordinates.ZERO2D;
	}

}

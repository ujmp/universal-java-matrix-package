package org.ujmp.core.objectmatrix;

import org.ujmp.core.coordinates.Coordinates;

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
	public long[] getSize() {
		return Coordinates.ZERO2D;
	}

}

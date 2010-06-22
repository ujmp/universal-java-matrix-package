package org.ujmp.core.objectmatrix.impl;

import org.ujmp.core.Coordinates;
import org.ujmp.core.objectmatrix.stub.AbstractDenseObjectMatrix2D;

public class EmptyMatrix extends AbstractDenseObjectMatrix2D {
	private static final long serialVersionUID = 1226331953770561766L;

	
	public Object getObject(long row, long column) {
		return null;
	}

	
	public void setObject(Object value, long row, long column) {
	}

	
	public Object getObject(int row, int column) {
		return null;
	}

	
	public void setObject(Object value, int row, int column) {
	}

	
	public long[] getSize() {
		return Coordinates.ZERO2D;
	}

}

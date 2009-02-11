package org.ujmp.sst;

import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.CoordinateIterator;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.genericmatrix.AbstractDenseGenericMatrix;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.util.MathUtil;

import shared.array.ObjectArray;

public class SSTDenseObjectMatrix<A> extends AbstractDenseGenericMatrix<A>
		implements Wrapper<ObjectArray<A>> {
	private static final long serialVersionUID = 2319673263310965476L;

	private transient ObjectArray<A> data = null;

	public SSTDenseObjectMatrix(ObjectArray<A> data) {
		this.data = data;
	}

	public SSTDenseObjectMatrix(long... size) {
		data = new ObjectArray<A>(MathUtil.toIntArray(size));
	}

	public SSTDenseObjectMatrix(Matrix source) {
		data = new ObjectArray<A>(MathUtil.toIntArray(source.getSize()));
		for (long[] c : source.availableCoordinates()) {
			setObject(source.getObject(c), c);
		}
	}

	@Override
	public A getObject(long... coordinates) throws MatrixException {
		return data.get(MathUtil.toIntArray(coordinates));
	}

	public void setObject(Object value, long... coordinates)
			throws MatrixException {
		data.set((A) value, MathUtil.toIntArray(coordinates));

	}

	public long[] getSize() {
		return MathUtil.toLongArray(data.dimensions());
	}

	@Override
	public Iterable<long[]> allCoordinates() throws MatrixException {
		return new CoordinateIterator(this.getSize());
	}

	@Override
	public ObjectArray<A> getWrappedObject() {
		return data;
	}

	@Override
	public void setWrappedObject(ObjectArray<A> object) {
		this.data = object;
	}

}

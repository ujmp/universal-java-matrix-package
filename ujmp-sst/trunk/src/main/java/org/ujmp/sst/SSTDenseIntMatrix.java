package org.ujmp.sst;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.CoordinateIterator;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.intmatrix.AbstractDenseIntMatrix;
import org.ujmp.core.util.MathUtil;

import shared.array.IntegerArray;

public class SSTDenseIntMatrix extends AbstractDenseIntMatrix implements
		Wrapper<IntegerArray> {
	private static final long serialVersionUID = 2319673263310965476L;

	private transient IntegerArray data = null;

	public SSTDenseIntMatrix(IntegerArray data) {
		this.data = data;
	}

	public SSTDenseIntMatrix(long... size) {
		data = new IntegerArray(MathUtil.toIntArray(size));
	}

	public SSTDenseIntMatrix(Matrix source) {
		data = new IntegerArray(MathUtil.toIntArray(source.getSize()));
		for (long[] c : source.availableCoordinates()) {
			setInt(source.getAsInt(c), c);
		}
	}

	public int getInt(long... coordinates) throws MatrixException {
		return data.get(MathUtil.toIntArray(coordinates));
	}

	public void setInt(int value, long... coordinates) throws MatrixException {
		data.set(value, MathUtil.toIntArray(coordinates));

	}

	public long[] getSize() {
		return MathUtil.toLongArray(data.dimensions());
	}

	@Override
	public Iterable<long[]> allCoordinates() throws MatrixException {
		return new CoordinateIterator(this.getSize());
	}

	private void readObject(ObjectInputStream s) throws IOException,
			ClassNotFoundException {
		s.defaultReadObject();
		byte[] bytes = (byte[]) s.readObject();
		data = IntegerArray.parse(bytes);
	}

	private void writeObject(ObjectOutputStream s) throws IOException,
			MatrixException {
		s.defaultWriteObject();
		s.writeObject(data.getBytes());
	}

	@Override
	public IntegerArray getWrappedObject() {
		return data;
	}

	@Override
	public void setWrappedObject(IntegerArray object) {
		this.data = object;
	}

}

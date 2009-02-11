package org.ujmp.sst;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.CoordinateIterator;
import org.ujmp.core.doublematrix.AbstractDenseDoubleMatrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.util.MathUtil;

import shared.array.RealArray;

public class SSTDenseDoubleMatrix extends AbstractDenseDoubleMatrix implements
		Wrapper<RealArray> {
	private static final long serialVersionUID = 2319673263310965476L;

	private transient RealArray data = null;

	public SSTDenseDoubleMatrix(RealArray data) {
		this.data = data;
	}

	public SSTDenseDoubleMatrix(long... size) {
		data = new RealArray(MathUtil.toIntArray(size));
	}

	public SSTDenseDoubleMatrix(Matrix source) {
		data = new RealArray(MathUtil.toIntArray(source.getSize()));
		for (long[] c : source.availableCoordinates()) {
			setDouble(source.getAsDouble(c), c);
		}
	}

	public double getDouble(long... coordinates) throws MatrixException {
		return data.get(MathUtil.toIntArray(coordinates));
	}

	public void setDouble(double value, long... coordinates)
			throws MatrixException {
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
		data = RealArray.parse(bytes);
	}

	private void writeObject(ObjectOutputStream s) throws IOException,
			MatrixException {
		s.defaultWriteObject();
		s.writeObject(data.getBytes());
	}

	@Override
	public RealArray getWrappedObject() {
		return data;
	}

	@Override
	public void setWrappedObject(RealArray object) {
		this.data = object;
	}

	@Override
	public Matrix transpose() {
		return new SSTDenseDoubleMatrix(data.mTranspose());
	}

	@Override
	public Matrix mtimes(Matrix m) {
		if (m instanceof SSTDenseDoubleMatrix) {
			return new SSTDenseDoubleMatrix(data
					.mMul(((SSTDenseDoubleMatrix) m).getWrappedObject()));
		} else {
			return super.mtimes(m);
		}
	}

	@Override
	public Matrix plus(double v) {
		return new SSTDenseDoubleMatrix(data.uAdd(v));
	}

	@Override
	public Matrix minus(double v) {
		return new SSTDenseDoubleMatrix(data.uAdd(-v));
	}

	@Override
	public Matrix times(double v) {
		return new SSTDenseDoubleMatrix(data.uMul(v));
	}

	@Override
	public Matrix divide(double v) {
		return new SSTDenseDoubleMatrix(data.uMul(1 / v));
	}

}

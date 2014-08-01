/*
 * Copyright (C) 2008-2014 by Holger Arndt
 *
 * This file is part of the Universal Java Matrix Package (UJMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * UJMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * UJMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with UJMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.ujmp.jmatio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrixMultiD;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.matrix.factory.BaseMatrixFactory;
import org.ujmp.core.util.MathUtil;

import com.jmatio.types.MLDouble;

public class MLDenseDoubleMatrix extends AbstractDenseDoubleMatrixMultiD implements
		Wrapper<MLDouble> {
	private static final long serialVersionUID = 5687213209146399315L;

	public static final MLDenseDoubleMatrixFactory Factory = new MLDenseDoubleMatrixFactory();

	private transient MLDouble matrix = null;

	private int[] pack = null;

	public MLDenseDoubleMatrix(Matrix m) {
		super(m.getSize());
		if (m.getMetaData() != null) {
			setMetaData(m.getMetaData().clone());
			this.matrix = new MLDouble(m.getLabel(), MathUtil.toIntArray(m.getSize()));
		} else {
			this.matrix = new MLDouble("matrix" + System.nanoTime(), MathUtil.toIntArray(m
					.getSize()));
		}
		init();
		for (long[] c : m.availableCoordinates()) {
			setAsDouble(m.getAsDouble(c), c);
		}
	}

	public MLDenseDoubleMatrix(long... size) {
		super(size);
		if (Coordinates.product(size) > 0) {
			this.matrix = new MLDouble("matrix" + System.nanoTime(), MathUtil.toIntArray(size));
			init();
		}
	}

	private void init() {
		int[] dims = matrix.getDimensions();
		pack = new int[matrix.getNDimensions()];
		pack[0] = 1;
		for (int i = 1; i < pack.length; i++) {
			pack[i] = dims[i - 1] * pack[i - 1];
		}
	}

	int getIndex(long... coords) {
		int index = 0;
		for (int x = 0; x < coords.length; x++) {
			index += coords[x] * pack[x];
		}
		return index;
	}

	public MLDenseDoubleMatrix(MLDouble matrix) {
		super(MathUtil.toLongArray(matrix.getDimensions()));
		this.matrix = matrix;
		setLabel(matrix.getName());
		init();
	}

	public long[] getSize() {
		return matrix == null ? Coordinates.ZERO2D : MathUtil.toLongArray(matrix.getDimensions());
	}

	// access to matrix data must be synchronized
	public synchronized double getDouble(long... coordinates) {
		return matrix.get(getIndex(coordinates));
	}

	// access to matrix data must be synchronized
	public synchronized void setDouble(double value, long... coordinates) {
		matrix.set(value, getIndex(coordinates));
	}

	public MLDouble getWrappedObject() {
		return matrix;
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		String name = (String) s.readObject();
		double[][] values = (double[][]) s.readObject();
		matrix = new MLDouble(name, values);
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeObject(matrix.name);
		s.writeObject(this.toDoubleArray());
	}

	public BaseMatrixFactory<? extends Matrix> getFactory() {
		return Matrix.Factory;
	}

}

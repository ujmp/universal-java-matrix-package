/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

import com.jmatio.types.MLDouble;

public class MLDoubleMatrix extends AbstractDenseDoubleMatrix2D implements Wrapper<MLDouble> {
	private static final long serialVersionUID = 5687213209146399315L;

	private transient MLDouble matrix = null;

	public MLDoubleMatrix(Matrix m) {
		this(m.getSize());
		if (m.getLabel() != null) {
			setLabel(m.getLabel());
		}
		for (long[] c : m.availableCoordinates()) {
			setAsDouble(m.getAsDouble(c), c);
		}
	}

	public MLDoubleMatrix(long... size) {
		this.matrix = new MLDouble("matrix" + System.nanoTime(),
				new double[(int) size[ROW]][(int) size[COLUMN]]);
	}

	public MLDoubleMatrix(MLDouble matrix) {
		this.matrix = matrix;
	}

	public long[] getSize() {
		return new long[] { matrix.getM(), matrix.getN() };
	}

	public double getDouble(long row, long column) {
		return matrix.get((int) row, (int) column);
	}

	public void setDouble(double value, long row, long column) {
		matrix.set(value, (int) row, (int) column);
	}

	public MLDouble getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(MLDouble object) {
		this.matrix = object;
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		String name = (String) s.readObject();
		double[][] values = (double[][]) s.readObject();
		matrix = new MLDouble(name, values);
	}

	private void writeObject(ObjectOutputStream s) throws IOException, MatrixException {
		s.defaultWriteObject();
		s.writeObject(matrix.name);
		s.writeObject(this.toDoubleArray());
	}

}

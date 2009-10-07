/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.ujmp.jampack;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

import Jampack.H;
import Jampack.Inv;
import Jampack.JampackException;
import Jampack.Parameters;
import Jampack.Times;
import Jampack.Z;
import Jampack.Zmat;
import Jampack.Zsvd;

public class JampackDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D
		implements Wrapper<Zmat> {
	private static final long serialVersionUID = 4929284378405884509L;

	static {
		try {
			Parameters.setBaseIndex(0);
		} catch (JampackException e) {
			e.printStackTrace();
		}
	}

	private transient Zmat matrix = null;

	public JampackDenseDoubleMatrix2D(long... size) {
		this.matrix = new Zmat((int) size[ROW], (int) size[COLUMN]);
	}

	public JampackDenseDoubleMatrix2D(Zmat matrix) {
		this.matrix = matrix;
	}

	public JampackDenseDoubleMatrix2D(Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setAsDouble(source.getAsDouble(c), c);
		}
	}

	public Matrix inv() throws MatrixException {
		try {
			return new JampackDenseDoubleMatrix2D(Inv.o(matrix));
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public double getDouble(long row, long column) {
		return matrix.get((int) row, (int) column).re;
	}

	public double getDouble(int row, int column) {
		return matrix.get(row, column).re;
	}

	public long[] getSize() {
		return new long[] { matrix.nr, matrix.nc };
	}

	public void setDouble(double value, long row, long column) {
		matrix.put((int) row, (int) column, new Z(value, 0));
	}

	public void setDouble(double value, int row, int column) {
		matrix.put(row, column, new Z(value, 0));
	}

	public Zmat getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(Zmat object) {
		this.matrix = object;
	}

	public final Matrix copy() throws MatrixException {
		Matrix m = new JampackDenseDoubleMatrix2D(new Zmat(matrix));
		if (getAnnotation() != null) {
			m.setAnnotation(getAnnotation().clone());
		}
		return m;
	}

	public Matrix transpose() {
		return new JampackDenseDoubleMatrix2D(H.trans(matrix));
	}

	// SVD has errors and does not work on the test case
	public Matrix[] svd() {
		if (isSquare()) {
			try {
				Zsvd svd = new Zsvd(matrix);
				Matrix u = new JampackDenseDoubleMatrix2D(svd.U);
				Matrix s = new JampackDenseDoubleMatrix2D(new Zmat(svd.S));
				Matrix v = new JampackDenseDoubleMatrix2D(svd.V);
				return new Matrix[] { u, s, v };
			} catch (Exception e) {
				throw new MatrixException(e);
			}
		} else {
			return super.svd();
		}
	}

	public Matrix mtimes(Matrix m) {
		try {
			if (m instanceof JampackDenseDoubleMatrix2D) {
				return new JampackDenseDoubleMatrix2D(Times.o(matrix,
						((JampackDenseDoubleMatrix2D) m).matrix));
			} else {
				return super.mtimes(m);
			}
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public Matrix times(double value) {
		try {
			return new JampackDenseDoubleMatrix2D(Times.o(new Z(value, 0),
					matrix));

		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public Matrix divide(double value) {
		try {
			return new JampackDenseDoubleMatrix2D(Times.o(
					new Z(1.0 / value, 0), matrix));

		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException,
			ClassNotFoundException {
		s.defaultReadObject();
		double[][] values = (double[][]) s.readObject();
		matrix = new Zmat(values);
	}

	private void writeObject(ObjectOutputStream s) throws IOException,
			MatrixException {
		s.defaultWriteObject();
		s.writeObject(matrix.getRe());
	}

}

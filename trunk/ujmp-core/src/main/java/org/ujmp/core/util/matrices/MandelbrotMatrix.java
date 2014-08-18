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

package org.ujmp.core.util.matrices;

import org.ujmp.core.doublematrix.stub.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.util.Complex;

public class MandelbrotMatrix extends AbstractDenseDoubleMatrix2D {
	private static final long serialVersionUID = -1420083425681944756L;

	private final long[] size;
	private final double xoffset;
	private final double yoffset;
	private final double xsize;
	private final double ysize;
	private final int iterations;

	public MandelbrotMatrix() {
		this(500, 500);
	}

	public MandelbrotMatrix(int rows, int columns) {
		this(rows, columns, 32);
	}

	public MandelbrotMatrix(int rows, int columns, int iterations) {
		this(rows, columns, iterations, -0.5, 0.0, 2.0, 2.0);
	}

	public MandelbrotMatrix(int rows, int columns, int iterations, double xoffset, double yoffset,
			double xsize, double ysize) {
		super(rows, columns);
		this.size = new long[] { rows, columns };
		this.iterations = iterations;
		this.xoffset = xoffset;
		this.yoffset = yoffset;
		this.xsize = xsize;
		this.ysize = ysize;
	}

	public double getDouble(long row, long column) {
		final double x0 = xoffset - xsize / 2.0 + xsize * column / size[COLUMN];
		final double y0 = yoffset - ysize / 2.0 + ysize * row / size[ROW];
		Complex z0 = new Complex(x0, y0);
		final double gray = iterations - calc(z0, iterations);
		return (gray - (iterations / 2.0)) / (iterations / 2.0);
	}

	public void setDouble(double value, long row, long column) {
		throw new UnsupportedOperationException();
	}

	public double getDouble(int row, int column) {
		return getDouble((long) row, (long) column);
	}

	public void setDouble(double value, int row, int column) {
		throw new UnsupportedOperationException();

	}

	public long[] getSize() {
		return size;
	}

	public static final int calc(Complex c, int iterations) {
		Complex z = c;
		for (int i = 0; i < iterations; i++) {
			if (z.abs() > 2.0) {
				return i;
			}
			z = z.times(z).plus(c);
		}
		return iterations;
	}

}

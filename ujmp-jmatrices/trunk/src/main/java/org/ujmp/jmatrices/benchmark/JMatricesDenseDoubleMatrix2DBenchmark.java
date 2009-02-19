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

package org.ujmp.jmatrices.benchmark;

import org.ujmp.core.Matrix;
import org.ujmp.core.benchmark.AbstractMatrix2DBenchmark;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.jmatrices.JMatricesDenseDoubleMatrix2D;

public class JMatricesDenseDoubleMatrix2DBenchmark extends
		AbstractMatrix2DBenchmark {

	@Override
	public Matrix createMatrix(long... size) throws MatrixException {
		if (Coordinates.product(size) > 10000) {
			throw new MatrixException("size too large, it will take forever");
		}
		return new JMatricesDenseDoubleMatrix2D(size);
	}

	@Override
	public Matrix createMatrix(Matrix source) throws MatrixException {
		return new JMatricesDenseDoubleMatrix2D(source);
	}

	public static void main(String[] args) throws Exception {
		new JMatricesDenseDoubleMatrix2DBenchmark().run();
	}

}

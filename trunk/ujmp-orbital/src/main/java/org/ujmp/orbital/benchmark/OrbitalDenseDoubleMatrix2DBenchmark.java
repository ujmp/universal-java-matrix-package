/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

package org.ujmp.orbital.benchmark;

import org.ujmp.core.Matrix;
import org.ujmp.core.benchmark.AbstractMatrix2DBenchmark;
import org.ujmp.core.benchmark.MatrixBenchmark;
import org.ujmp.core.doublematrix.DoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.orbital.OrbitalDenseDoubleMatrix2D;

public class OrbitalDenseDoubleMatrix2DBenchmark extends
		AbstractMatrix2DBenchmark {

	public DoubleMatrix2D createMatrix(long... size) throws MatrixException {
		return new OrbitalDenseDoubleMatrix2D(size);
	}

	public DoubleMatrix2D createMatrix(Matrix source) throws MatrixException {
		return new OrbitalDenseDoubleMatrix2D(source);
	}

	public static void main(String[] args) throws Exception {
		MatrixBenchmark benchmark = new OrbitalDenseDoubleMatrix2DBenchmark();
		benchmark.run();
	}

}

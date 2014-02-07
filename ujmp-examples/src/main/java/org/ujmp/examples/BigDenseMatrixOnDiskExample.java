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

package org.ujmp.examples;

import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.util.io.DenseTiledFileDoubleMatrix2D;

public class BigDenseMatrixOnDiskExample {

	public static void main(String[] args) throws Exception {

		// create matrix on disk in a temporary file
		DenseTiledFileDoubleMatrix2D m = new DenseTiledFileDoubleMatrix2D(8192, 8192);

		// fill an area with random values between -1 and 1
		m.subMatrix(Ret.LINK, 1000, 1000, 2000, 2000).randn(Ret.ORIG);

		// fill an area with random values between 0 and 1
		m.subMatrix(Ret.LINK, 4000, 7000, 6000, 8000).rand(Ret.ORIG);

		// set an area to a constant value of 10
		m.subMatrix(Ret.LINK, 3000, 4000, 4000, 5000).fill(Ret.ORIG, 10);

		// let's see the result
		m.showGUI();

		// Try to set the matrix size to larger values and see that you can
		// handle several gigabytes easily with UJMP. Don't forget to delete the
		// matrix in your temporary folder to save space
	}

}

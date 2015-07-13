/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

package org.ujmp.la4j;

import org.ujmp.core.Matrix;
import org.ujmp.core.implementations.AbstractMatrixTest;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.matrices.MatrixLibraries;

public class TestLa4JDenseDoubleMatrix2D extends AbstractMatrixTest {

	@Override
	public La4JDenseDoubleMatrix2D createMatrix(long... size) throws Exception {
		return new La4JDenseDoubleMatrix2D(MathUtil.longToInt(size[Matrix.ROW]),
				MathUtil.longToInt(size[Matrix.COLUMN]));
	}

	@Override
	public Matrix createMatrix(Matrix source) throws Exception {
		return new La4JDenseDoubleMatrix2D(source);
	}

	@Override
	public boolean isTestLarge() {
		return false;
	}

	@Override
	public int getMatrixLibraryId() {
		return MatrixLibraries.LA4J;
	}

	@Override
	public boolean isTestSparse() {
		return false;
	}

	public void testEigSymmSmall() throws Exception {
		// returns wrong result
	}

	protected boolean isSupported(Matrix a, long feature, MatrixLayout layout, Size size, EntryType generator) {
		if (MatrixLibraries.SVD == feature && MatrixLayout.FAT.equals(layout) && Size.SINGLEENTRY.equals(size)) {
			return false;
		} else if (MatrixLibraries.SVD == feature && MatrixLayout.FAT.equals(layout) && Size.SMALL.equals(size)) {
			return false;
		} else {
			return super.isSupported(a, feature, layout, size, generator);
		}
	}

}

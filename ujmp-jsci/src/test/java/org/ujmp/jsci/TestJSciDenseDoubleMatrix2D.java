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

package org.ujmp.jsci;

import org.ujmp.core.Matrix;
import org.ujmp.core.implementations.AbstractMatrixTest;

public class TestJSciDenseDoubleMatrix2D extends AbstractMatrixTest {

	public Matrix createMatrix(long... size) {
		return new JSciDenseDoubleMatrix2D(size);
	}

	public Matrix createMatrix(Matrix source) {
		return new JSciDenseDoubleMatrix2D(source);
	}

	public boolean isTestLarge() {
		return false;
	}

	public void testLUSquareSingularSmall() throws Exception {
		// error for singular matrix
	}

	public void testLUSquareSingularLarge() throws Exception {
		// error for singular matrix
	}

	public void testLUFatFixedSmall() throws Exception {
		// only square matrices
	}

	public void testLUTallFixedSmall() throws Exception {
		// only square matrices
	}

	public void testSVD() throws Exception {
		// some error
	}

	public void testSVDTallSmall() throws Exception {
		// only square matrices
	}

	public void testSVDWikipedia() throws Exception {
		// only square matrices
	}

	public void testSVDFatSmall() throws Exception {
		// only square matrices
	}

	public void testQRTallSmall() throws Exception {
		// only square matrices
	}

	public void testQRFatSmall() throws Exception {
		// only square matrices
	}

	public void testLU() throws Exception {
		// some error
	}

}

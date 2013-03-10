/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

package org.ujmp.ehcache;

import org.junit.Test;
import org.ujmp.core.Matrix;
import org.ujmp.core.implementations.AbstractMatrixTest;

//TODO: does not work?
public class TestTiledEhcacheMatrix2D { // extends AbstractMatrixTest {

	public Matrix createMatrix(long... size) throws Exception {
		return new EhcacheTiledObjectMatrix2D(size);
	}

	public Matrix createMatrix(Matrix source) throws Exception {
		return new EhcacheTiledObjectMatrix2D(source);
	}

	public boolean isTestLarge() {
		return false;
	}

	@Test
	public void test() {
	}
	
}

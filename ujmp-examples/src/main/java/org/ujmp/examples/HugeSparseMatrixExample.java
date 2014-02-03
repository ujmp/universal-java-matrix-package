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

import java.util.Random;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.ValueType;

public class HugeSparseMatrixExample {

	public static void main(String[] args) throws Exception {
		Matrix m = Matrix.Factory.sparse(ValueType.DOUBLE, 100000000, 100000000);
		Random random = new Random();
		for (int i = 0; i < 10000; i++) {
			m.setAsDouble(1, random.nextInt(100000), random.nextInt(100000));
		}
		m.showGUI();
	}

}

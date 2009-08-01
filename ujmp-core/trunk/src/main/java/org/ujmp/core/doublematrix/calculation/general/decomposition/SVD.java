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

package org.ujmp.core.doublematrix.calculation.general.decomposition;

import java.lang.reflect.Constructor;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public class SVD {

	public static String CLASSNAME = "org.ujmp.mtj.MTJDenseDoubleMatrix2D";

	public static boolean isAvailable() {
		try {
			Class.forName("no.uib.cipr.matrix.DenseMatrix");
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public static Matrix[] calcNew(Matrix m) throws MatrixException {
		try {
			Class<? extends Matrix> mtjc = (Class<? extends Matrix>) Class.forName(CLASSNAME);
			Constructor<? extends Matrix> con = mtjc.getConstructor(Matrix.class);
			Matrix mtjm = con.newInstance(m);

			return mtjm.svd();
		} catch (Exception e) {
			throw new MatrixException("cannot calculate SVD: add ujmp-mtj and mtj to classpath");
		}
	}

}

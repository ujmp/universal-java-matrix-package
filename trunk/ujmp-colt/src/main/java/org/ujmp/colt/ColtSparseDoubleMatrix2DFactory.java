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

package org.ujmp.colt;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.factory.AbstractSparseDoubleMatrix2DFactory;
import org.ujmp.core.doublematrix.factory.SparseDoubleMatrix2DFactory;
import org.ujmp.core.util.MathUtil;

public class ColtSparseDoubleMatrix2DFactory extends AbstractSparseDoubleMatrix2DFactory<ColtSparseDoubleMatrix2D>
		implements SparseDoubleMatrix2DFactory<ColtSparseDoubleMatrix2D> {

	public ColtSparseDoubleMatrix2D zeros(long rows, long columns) {
		return new ColtSparseDoubleMatrix2D(MathUtil.longToInt(rows), MathUtil.longToInt(columns));
	}

	public ColtSparseDoubleMatrix2D zeros(long... size) {
		return new ColtSparseDoubleMatrix2D(MathUtil.longToInt(size[Matrix.ROW]),
				MathUtil.longToInt(size[Matrix.COLUMN]));
	}

}

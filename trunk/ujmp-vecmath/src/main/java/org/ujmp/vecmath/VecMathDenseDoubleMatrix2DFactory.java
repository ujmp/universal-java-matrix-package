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

package org.ujmp.vecmath;

import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.factory.AbstractDenseDoubleMatrix2DFactory;
import org.ujmp.core.exceptions.MatrixException;

public class VecMathDenseDoubleMatrix2DFactory extends
		AbstractDenseDoubleMatrix2DFactory {
	private static final long serialVersionUID = 8234576107068440158L;

	public DenseDoubleMatrix2D zeros(long rows, long columns)
			throws MatrixException {
		return new VecMathDenseDoubleMatrix2D(rows, columns);
	}

}

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

package org.ujmp.core.booleanmatrix.calculation;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

public interface BooleanCalculations {

	public Matrix le(Ret returnType, Matrix matrix) ;

	public Matrix ge(Ret returnType, Matrix matrix) ;

	public Matrix gt(Ret returnType, Matrix matrix) ;

	public Matrix lt(Ret returnType, Matrix matrix) ;

	public Matrix eq(Ret returnType, Matrix matrix) ;

	public Matrix ne(Ret returnType, Matrix matrix) ;

	public Matrix and(Ret returnType, Matrix matrix) ;

	public Matrix or(Ret returnType, Matrix matrix) ;

	public Matrix xor(Ret returnType, Matrix matrix) ;

	public Matrix not(Ret returnType) ;

	public Matrix le(Ret returnType, double value) ;

	public Matrix ge(Ret returnType, double value) ;

	public Matrix gt(Ret returnType, double value) ;

	public Matrix lt(Ret returnType, double value) ;

	public Matrix eq(Ret returnType, Object value) ;

	public Matrix ne(Ret returnType, Object value) ;

	public Matrix and(Ret returnType, boolean value) ;

	public Matrix or(Ret returnType, boolean value) ;

	public Matrix xor(Ret returnType, boolean value) ;

}

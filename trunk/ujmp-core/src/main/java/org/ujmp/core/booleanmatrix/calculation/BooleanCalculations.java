/*
 * Copyright (C) 2008-2010 by Holger Arndt
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
import org.ujmp.core.exceptions.MatrixException;

public interface BooleanCalculations {

	public Matrix le(Ret returnType, Matrix matrix) throws MatrixException;

	public Matrix ge(Ret returnType, Matrix matrix) throws MatrixException;

	public Matrix gt(Ret returnType, Matrix matrix) throws MatrixException;

	public Matrix lt(Ret returnType, Matrix matrix) throws MatrixException;

	public Matrix eq(Ret returnType, Matrix matrix) throws MatrixException;

	public Matrix ne(Ret returnType, Matrix matrix) throws MatrixException;

	public Matrix and(Ret returnType, Matrix matrix) throws MatrixException;

	public Matrix or(Ret returnType, Matrix matrix) throws MatrixException;

	public Matrix xor(Ret returnType, Matrix matrix) throws MatrixException;

	public Matrix not(Ret returnType) throws MatrixException;

	public Matrix le(Ret returnType, double value) throws MatrixException;

	public Matrix ge(Ret returnType, double value) throws MatrixException;

	public Matrix gt(Ret returnType, double value) throws MatrixException;

	public Matrix lt(Ret returnType, double value) throws MatrixException;

	public Matrix eq(Ret returnType, Object value) throws MatrixException;

	public Matrix ne(Ret returnType, Object value) throws MatrixException;

	public Matrix and(Ret returnType, boolean value) throws MatrixException;

	public Matrix or(Ret returnType, boolean value) throws MatrixException;

	public Matrix xor(Ret returnType, boolean value) throws MatrixException;

}

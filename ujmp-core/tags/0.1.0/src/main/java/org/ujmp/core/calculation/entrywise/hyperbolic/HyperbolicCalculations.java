/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

package org.ujmp.core.calculation.entrywise.hyperbolic;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;

public interface HyperbolicCalculations {

	// sinh, cosh, tanh, cotanh, sech, cosech
	// asinh, acosh, atanh, acotanh, asech, acosech

	/**
	 * Calculates the hyperbolic sinus of all entries in the matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with hyperbolic sinus values
	 */
	public Matrix sinh(Ret returnType) throws MatrixException;

	/**
	 * Calculates the hyperbolic cosinus of all entries in the matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with hyperbolic cosinus values
	 */
	public Matrix cosh(Ret returnType) throws MatrixException;

	/**
	 * Calculates the hyperbolic tangens of all entries in the matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with hyperbolic tangens values
	 */
	public Matrix tanh(Ret returnType) throws MatrixException;

}

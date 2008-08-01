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

package org.ujmp.core.doublecalculation.entrywise.trigonometric;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublecalculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;

/**
 * This interface lists all trigonometric functions that can be calculated on a
 * Matrix.
 */
public interface TrigonometricDoubleCalculations {

	// sin, cos, tan, cot, sec, cosec
	// asin, acos, atan, acot, asec, acosec

	/**
	 * Calculates the sinus of all entries in the matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with sinus values
	 */
	public Matrix sin(Ret returnType) throws MatrixException;

	/**
	 * Calculates the cosinus of all entries in the matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with cosinus values
	 */
	public Matrix cos(Ret returnType) throws MatrixException;

	/**
	 * Calculates the tangens of all entries in the matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with tangens values
	 */
	public Matrix tan(Ret returnType) throws MatrixException;

}

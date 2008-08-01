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

package org.ujmp.core.doublecalculation.entrywise.basic;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublecalculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;

public interface BasicEntrywiseCalculations {

	/**
	 * Calculates the absolute values of all entries in a Matrix. Positive
	 * values stay the same, negative values change sign.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return A new Matrix with absolute values.
	 */
	public Matrix abs(Ret returnType) throws MatrixException;

	/**
	 * Calculates the logarithm with basis 2 of all entries in the Matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with logarithm values.
	 */
	public Matrix log2(Ret returnType) throws MatrixException;

	/**
	 * Calculates the logarithm with basis 10 of all entries in the Matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with logarithm values.
	 */
	public Matrix log10(Ret returnType) throws MatrixException;

	/**
	 * Calculates the natural logarithm of all entries in the Matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with logarithm values.
	 */
	public Matrix log(Ret returnType) throws MatrixException;

	/**
	 * Calculates the sign of the entries in a Matrix. For values greater than
	 * zero, 1.0 is returned. Negative values will give -1.0 as return value.
	 * 0.0 is returned for entries equal to zero.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with signum values
	 */
	public Matrix sign(Ret returnType) throws MatrixException;

	/**
	 * Calculates the square root of all the entries in a Matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix containing the square roots of all entries
	 */
	public Matrix sqrt(Ret returnType) throws MatrixException;

	/**
	 * Calculates this matrix to the power of the given matrix (entrywise).
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param matrix
	 *            the second matrix
	 * @return matrix with all entries to the power of the second matrix's
	 *         entry.
	 */
	public Matrix power(Ret returnType, Matrix power) throws MatrixException;

	/**
	 * Calculates this matrix to the power of the given value (entrywise).
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param value
	 *            power factor
	 * @return Matrix with all entries to the power of factor.
	 */
	public Matrix power(Ret returnType, double power) throws MatrixException;

}

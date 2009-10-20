/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

package org.ujmp.core.doublematrix.calculation.general.missingvalues;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.calculation.general.missingvalues.Impute.ImputationMethod;
import org.ujmp.core.exceptions.MatrixException;

public interface MissingValueDoubleCalculations {

	/**
	 * Adds a specified amount of missing values (Double.NaN) to the Matrix
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @param percentMissing
	 *            defines how many values are missing 0.0 to 1.0
	 * @return Matrix with missing values
	 * @throws MatrixException
	 */
	public Matrix addMissing(Ret returnType, int dimension, double... percentMissing)
			throws MatrixException;

	/**
	 * Counts the missing values within the matrix, i.e. Infinity or NaN
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @return Matrix with counts of missing values
	 * @throws MatrixException
	 */
	public Matrix countMissing(Ret returnType, int dimension) throws MatrixException;

	/**
	 * Replaces missing values with various methods
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param method
	 *            the imputation method to use
	 * @param parameters
	 *            specify a set of parameters if needed
	 * @return Matrix with missing values replaced
	 * @throws MatrixException
	 */
	public Matrix impute(Ret returnType, ImputationMethod method, Object... parameters)
			throws MatrixException;

	public Matrix deleteColumnsWithMissingValues(Ret returnType) throws MatrixException;

	public Matrix deleteRowsWithMissingValues(Ret returnType, long threshold)
			throws MatrixException;

}

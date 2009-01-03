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

package org.ujmp.core.doublematrix.calculation.general.missingvalues;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
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
	 * Replaces missing values by row mean or column mean
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @return Matrix with missing values replaced
	 * @throws MatrixException
	 */
	public Matrix imputeMean(Ret returnType, int dimension) throws MatrixException;

	/**
	 * Replaces missing values by zero
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with missing values replaced
	 * @throws MatrixException
	 */
	public Matrix imputeZero(Ret returnType) throws MatrixException;

	/**
	 * Replaces missing values by the K nearest neighbors
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param k
	 *            number of neighbors to use
	 * @return Matrix with missing values replaced
	 * @throws MatrixException
	 */
	public Matrix imputeKNN(Ret returnType, int k) throws MatrixException;

	/**
	 * Replaces missing values by regression on other variables
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with missing values replaced
	 * @throws MatrixException
	 */
	public Matrix imputeRegression(Ret returnType) throws MatrixException;

	/**
	 * Replaces missing values by regression on other variables
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param firstGuess
	 *            initial guess to replace missing values, e.g. by mean
	 * @return Matrix with missing values replaced
	 * @throws MatrixException
	 */
	public Matrix imputeRegression(Ret returnType, Matrix firstGuess) throws MatrixException;

	/**
	 * Replaces missing values by regression on other variables. Missing values
	 * are initially replaced by mean. After that they are re-estimated several
	 * times until the matrix does not change anymore.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with missing values replaced
	 * @throws MatrixException
	 */
	public Matrix imputeEM(Ret returnType) throws MatrixException;

	public Matrix deleteColumnsWithMissingValues(Ret returnType) throws MatrixException;

	public Matrix deleteRowsWithMissingValues(Ret returnType, long threshold)
			throws MatrixException;

}

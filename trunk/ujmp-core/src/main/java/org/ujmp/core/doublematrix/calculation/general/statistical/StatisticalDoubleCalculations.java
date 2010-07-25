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

package org.ujmp.core.doublematrix.calculation.general.statistical;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;

public interface StatisticalDoubleCalculations {

	public Matrix diff(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException;

	public Matrix prod(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException;

	public Matrix cumsum(boolean ignoreNaN) throws MatrixException;

	public Matrix cumprod(boolean ignoreNaN) throws MatrixException;

	/**
	 * Calculates the mimimum of the values in the matrix either rowwise,
	 * columnwise, or global.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            the axis along which should be calculated, e.g. ROW=0,
	 *            COLUMN=1 or ALL
	 * @return A new matrix containing the minimum values
	 */
	public Matrix min(Ret returnType, int dimension) throws MatrixException;

	/**
	 * Calculates the maximum of the values in the matrix either rowwise,
	 * columnwise, or global.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            the axis along which should be calculated, e.g. ROW=0,
	 *            COLUMN=1 or ALL
	 * @return A new matrix containing the maximum values
	 */
	public Matrix max(Ret returnType, int dimension) throws MatrixException;

	/**
	 * Calculates the sum of all entries in the Matrix either per row, per
	 * column, or global.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @return Matrix with the sum of the values along the desired axis
	 */
	public Matrix sum(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException;

	/**
	 * Calculates the mean of all entries in the Matrix either per row, per
	 * column, or global.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @return Matrix with the sum of the values along the desired axis
	 */
	public Matrix mean(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException;

	/**
	 * Calculates the variance of all entries in the Matrix either per row, per
	 * column, or global.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @param besselsCorrection
	 *            use (n-1) instead of n
	 * @return Matrix with the sum of the values along the desired axis
	 */
	public Matrix var(Ret returnType, int dimension, boolean ignoreNaN, boolean besselsCorrection)
			throws MatrixException;

	/**
	 * Calculates the standard deviation of all entries in the Matrix either per
	 * row, per column, or global.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @param besselsCorrection
	 *            use (n-1) instead of n
	 * @return Matrix with the sum of the values along the desired axis
	 */
	public Matrix std(Ret returnType, int dimension, boolean ignoreNaN, boolean besselsCorrection)
			throws MatrixException;

	/**
	 * Calculates the covariance Matrix. Each row is an observation and each
	 * column is a variable.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @param besselsCorrection
	 *            use (n-1) instead of n
	 * @return Covariance Matrix
	 */
	public Matrix cov(Ret returnType, boolean ignoreNaN, boolean besselsCorrection)
			throws MatrixException;

	/**
	 * Calculates the Pearson correlation. Each row is an observation and each
	 * column is a variable.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @param besselsCorrection
	 *            use (n-1) instead of n
	 * @return Covariance Matrix
	 */
	public Matrix corrcoef(Ret returnType, boolean ignoreNaN, boolean besselsCorrection)
			throws MatrixException;

	/**
	 * Calculates the mutual information. Each row is an observation and each
	 * column is a variable.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with mutual information
	 */
	public Matrix mutualInf(Ret returnType) throws MatrixException;

	/**
	 * Calculates a paired T-Test on the matrix. Each row is an observation and
	 * each column is a variable.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with p-values of the T-Test
	 */
	public Matrix pairedTTest(Ret returnType) throws MatrixException;

	/**
	 * Finds the index of the maximum value in the matrix
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @return Matrix containing the coordinates of the Maximum
	 */
	public Matrix indexOfMax(Ret returnType, int dimension) throws MatrixException;

	/**
	 * Finds the index of the minimum value in the matrix
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @return Matrix containing the coordinates of the Minimum
	 */
	public Matrix indexOfMin(Ret returnType, int dimension) throws MatrixException;

}

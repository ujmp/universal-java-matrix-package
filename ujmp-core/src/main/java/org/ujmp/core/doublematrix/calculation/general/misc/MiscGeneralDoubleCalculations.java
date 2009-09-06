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

package org.ujmp.core.doublematrix.calculation.general.misc;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;

public interface MiscGeneralDoubleCalculations {

	/**
	 * Subtracts the mean from the matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @return Matrix with zero mean.
	 */
	public Matrix center(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException;

	/**
	 * Subtracts the mean from the matrix and divides by the standard deviation.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @return Matrix with zero mean and unit variance.
	 */
	public Matrix standardize(Ret returnType, int dimension, boolean ignoreNaN)
			throws MatrixException;

	public Matrix replaceMissingBy(Matrix matrix) throws MatrixException;

	public Matrix fadeIn(Ret ret, int dimension) throws MatrixException;

	public Matrix fadeOut(Ret ret, int dimensions) throws MatrixException;

	public Matrix appendHorizontally(Matrix m) throws MatrixException;

	public Matrix appendVertically(Matrix m) throws MatrixException;

	public Matrix append(int dimension, Matrix m) throws MatrixException;

}

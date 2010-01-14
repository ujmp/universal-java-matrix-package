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

package org.ujmp.core.doublematrix.calculation.general.decomposition;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public interface DecompositionDoubleCalculations {

	/**
	 * Calculates the inverse of the Matrix using either LUDecomposition (for
	 * square matrices) or QRDecomposition (otherwise).
	 * 
	 * @return Inverse of the matrix
	 */
	public Matrix inv() throws MatrixException;

	/**
	 * Solve A*X = B
	 * 
	 * @param b
	 *            right hand side
	 * @return solution for X if A is square, least squares solution otherwise
	 */
	public Matrix solve(Matrix b) throws MatrixException;

	/**
	 * Calculates the pseudo inverse of the Matrix using Singular Value
	 * Decomposition.
	 * 
	 * @return Pseudo inverse of the Matrix
	 */
	public Matrix pinv() throws MatrixException;

	/**
	 * Calculates a generalized inverse of the Matrix
	 * 
	 * @return Pseudo inverse of the Matrix
	 */
	public Matrix ginv() throws MatrixException;

	/**
	 * Projects the matrix into the space of the principal components.
	 * 
	 * @return Matrix projected on principal components.
	 */
	public Matrix princomp() throws MatrixException;

	/**
	 * Calculates the singular value decomposition of the matrix.
	 * 
	 * @return Singular value decomposition of the matrix.
	 */
	public Matrix[] svd() throws MatrixException;

	/**
	 * Calculates the Eigen decomposition of the matrix.
	 * 
	 * @return Eigen decomposition of the matrix.
	 */
	public Matrix[] eig() throws MatrixException;

	/**
	 * Calculates a QR decomposition of the matrix.
	 * 
	 * @return QR decomposition of the matrix.
	 */
	public Matrix[] qr() throws MatrixException;

	/**
	 * Calculates a LU decomposition of the matrix.
	 * 
	 * @return LU decomposition of the matrix.
	 */
	public Matrix[] lu() throws MatrixException;

	/**
	 * Calculates a Cholesky decomposition of the matrix.
	 * 
	 * @return Cholesky decomposition of the matrix.
	 */
	public Matrix chol() throws MatrixException;

}

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

package org.ujmp.core.doublematrix.calculation.general.decomposition;

import org.ujmp.core.Matrix;

public interface DecompositionDoubleCalculations {

	/**
	 * Calculates the inverse of the Matrix using either LUDecomposition (for
	 * square matrices) or QRDecomposition (otherwise).
	 * 
	 * @return Inverse of the matrix
	 */
	public Matrix inv() ;

	/**
	 * Calculates the inverse of the Matrix using either LUDecomposition (for
	 * square matrices) or QRDecomposition (otherwise).
	 * 
	 * @return Inverse of the matrix
	 */
	public Matrix invSymm() ;

	/**
	 * Calculates the inverse of a symmetric positive definite Matrix using
	 * Cholesky Decomposition.
	 * 
	 * @return Inverse of the matrix
	 */
	public Matrix invSPD() ;

	/**
	 * Solve A*X = B
	 * 
	 * @param b
	 *            right hand side
	 * @return solution for X if A is square, least squares solution otherwise
	 */
	public Matrix solve(Matrix b) ;

	/**
	 * Solve A*X = B
	 * 
	 * @param b
	 *            right hand side
	 * @return solution for X if A is square, least squares solution otherwise
	 */
	public Matrix solveSymm(Matrix b) ;

	/**
	 * Solve A*X = B
	 * 
	 * @param b
	 *            right hand side
	 * @return solution for X if A is square, least squares solution otherwise
	 */
	public Matrix solveSPD(Matrix b) ;

	/**
	 * Calculates the pseudo inverse of the Matrix using Singular Value
	 * Decomposition.
	 * 
	 * @return Pseudo inverse of the Matrix
	 */
	public Matrix pinv() ;

	/**
	 * Calculates a generalized inverse of the Matrix
	 * 
	 * @return Pseudo inverse of the Matrix
	 */
	public Matrix ginv() ;

	/**
	 * Projects the matrix into the space of the principal components.
	 * 
	 * @return Matrix projected on principal components.
	 */
	public Matrix princomp() ;

	/**
	 * Calculates the singular value decomposition of the matrix: A = U*S*V'
	 * 
	 * @return Singular value decomposition of the matrix.
	 */
	public Matrix[] svd() ;

	/**
	 * Calculates the Eigen decomposition of the matrix.
	 * 
	 * @return Eigen decomposition of the matrix.
	 */
	public Matrix[] eig() ;

	/**
	 * Calculates the Eigen decomposition of a symmetric matrix.
	 * 
	 * @return Eigen decomposition of the matrix.
	 */
	public Matrix[] eigSymm() ;

	/**
	 * Calculates a QR decomposition of the matrix.
	 * 
	 * @return QR decomposition of the matrix.
	 */
	public Matrix[] qr() ;

	/**
	 * Calculates a LU decomposition of the matrix.
	 * 
	 * @return LU decomposition of the matrix.
	 */
	public Matrix[] lu() ;

	/**
	 * Calculates a Cholesky decomposition of the matrix.
	 * 
	 * @return Cholesky decomposition of the matrix.
	 */
	public Matrix chol() ;

}

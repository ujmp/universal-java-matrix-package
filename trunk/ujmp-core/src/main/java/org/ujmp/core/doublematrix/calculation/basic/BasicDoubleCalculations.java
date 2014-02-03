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

package org.ujmp.core.doublematrix.calculation.basic;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

public interface BasicDoubleCalculations {

	/**
	 * Adds a specified value to all entries in the matrix.
	 * 
	 * @param value
	 *            the value to add
	 * @return Matrix with the entries plus the value
	 */
	public Matrix plus(double value) ;

	/**
	 * Calculates the sum of the entries in both matrices
	 * 
	 * @param matrix
	 *            The matrix to add
	 * @return matrix with sum values
	 */
	public Matrix plus(Matrix matrix) ;

	/**
	 * Subtracts a specified value from all entries in the matrix.
	 * 
	 * @param value
	 *            the value to subtract
	 * @return Matrix with the entries minus the value
	 */
	public Matrix minus(double value) ;

	/**
	 * Subtracts a specified value from all entries in the matrix.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @param value
	 *            the value to subtract
	 * @return Matrix with the entries minus the value
	 */
	public Matrix minus(Ret returnType, boolean ignoreNaN, double value) ;

	/**
	 * Calculates the difference of the entries in both matrices
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @param matrix
	 *            The matrix to subtract
	 * @return matrix with difference values
	 */
	public Matrix minus(Ret returnType, boolean ignoreNaN, Matrix matrix) ;

	/**
	 * Adds a specified value from all entries in the matrix.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @param value
	 *            the value to subtract
	 * @return Matrix with the entries plus the value
	 */
	public Matrix plus(Ret returnType, boolean ignoreNaN, double value) ;

	/**
	 * Calculates the sum of the entries in both matrices
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @param matrix
	 *            The matrix to subtract
	 * @return matrix with sum values
	 */
	public Matrix plus(Ret returnType, boolean ignoreNaN, Matrix matrix) ;

	/**
	 * Calculates the difference of the entries in both matrices
	 * 
	 * @param matrix
	 *            The matrix to subtract
	 * @return matrix with difference values
	 */
	public Matrix minus(Matrix matrix) ;

	/**
	 * Calculates the entrywise product of the two matrices.
	 * 
	 * @param matrix
	 *            the second matrix
	 * @return matrix with product of all entries
	 */
	public Matrix times(Matrix matrix) ;

	/**
	 * Multiplies every entry in the matrix with a scalar.
	 * 
	 * @param value
	 *            factor to multiply with
	 * @return Matrix with all entries multiplied by a factor.
	 */
	public Matrix times(double value) ;

	/**
	 * Multiplies every entry in the matrix with a scalar.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @param value
	 *            factor to multiply with
	 * @return Matrix with all entries multiplied by a factor.
	 */
	public Matrix times(Ret returnType, boolean ignoreNaN, double value) ;

	/**
	 * Multiplies every entry in the matrix with the entries of another Matrix.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @param value
	 *            factor to multiply with
	 * @return Matrix with all entries multiplied by a factor.
	 */
	public Matrix times(Ret returnType, boolean ignoreNaN, Matrix value) ;

	/**
	 * Calculates an entrywise division of the two matrices.
	 * 
	 * @param matrix
	 *            the second matrix
	 * @return matrix with all entries divided by the second matrix's entry.
	 */
	public Matrix divide(Matrix matrix) ;

	/**
	 * Divides every entry in the matrix by a scalar.
	 * 
	 * @param value
	 *            factor by which to divide
	 * @return Matrix with all entries divided by a factor.
	 */
	public Matrix divide(double value) ;

	/**
	 * Divides every entry in the matrix by a scalar.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @param value
	 *            factor by which to divide
	 * 
	 * @return Matrix with all entries divided by a factor.
	 */
	public Matrix divide(Ret returnType, boolean ignoreNaN, double value) ;

	/**
	 * Divides every entry in the matrix by the entries of another matrix.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @param value
	 *            factor by which to divide
	 * 
	 * @return Matrix with all entries divided by a factor.
	 */
	public Matrix divide(Ret returnType, boolean ignoreNaN, Matrix value) ;

	/**
	 * Performs a matrix multiplication on the two matrices. The matrices must
	 * be 2-dimensional and have the correct size.
	 * 
	 * @param matrix
	 *            the second matrix
	 * @return Matrix product
	 */
	public Matrix mtimes(Matrix matrix) ;

	/**
	 * Performs a matrix multiplication on the two matrices. The matrices must
	 * be 2-dimensional and have the correct size.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @param matrix
	 *            the second matrix
	 * @return Matrix product
	 */
	public Matrix mtimes(Ret returnType, boolean ignoreNaN, Matrix matrix) ;

	/**
	 * Equal to times()
	 * 
	 * @param value
	 *            the value
	 * @return Matrix product
	 */
	public Matrix mtimes(double value) ;

	/**
	 * Equal to times()
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @param value
	 *            the value to multiply
	 * @return Matrix product
	 */
	public Matrix mtimes(Ret returnType, boolean ignoreNaN, double value) ;

	/**
	 * Performs an averaging matrix multiplication on the two matrices. The
	 * matrices must be 2-dimensional and have the correct size.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @param matrix
	 *            the second matrix
	 * @return Matrix product
	 */
	public Matrix atimes(Ret returnType, boolean ignoreNaN, Matrix matrix) ;

}

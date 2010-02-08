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

package org.ujmp.core.doublematrix.calculation.entrywise.creators;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;

public interface CreatorDoubleCalculations {

	/**
	 * Returns a matrix with equal size, where all entries are set to zero.
	 * 
	 * @return Matrix with zeros.
	 */
	public Matrix zeros(Ret ret) throws MatrixException;

	/**
	 * Returns a matrix with equal size, where all entries are set to 1.0.
	 * 
	 * @return Matrix with ones.
	 */
	public Matrix ones(Ret ret) throws MatrixException;

	/**
	 * Returns a matrix with equal size, where all entries are set to uniform
	 * random values between 0.0 and 1.0.
	 * 
	 * @return Matrix with uniformly distributed values.
	 */
	public Matrix rand(Ret ret) throws MatrixException;

	/**
	 * Returns a matrix with equal size, where all entries are set to random
	 * values which are normally distributed with 0.0 mean and 1.0 standard
	 * deviation.
	 * 
	 * @return Matrix with normally distributed values.
	 */
	public Matrix randn(Ret ret) throws MatrixException;

	/**
	 * Returns a matrix with ones at the diagonal.
	 * 
	 * @return Eye matrix with ones at the diagonal
	 */
	public Matrix eye(Ret ret) throws MatrixException;

}

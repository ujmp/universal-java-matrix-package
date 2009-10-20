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

package org.ujmp.core.booleanmatrix;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.genericmatrix.GenericMatrix;

public interface BooleanMatrix extends GenericMatrix<Boolean> {

	/**
	 * Returns a byte representation of an entry in the matrix. The stored value
	 * will be converted to a boolean as good as possible.
	 * 
	 * @param coordinates
	 *            location of the entry
	 * @return a boolean representation of the entry
	 * @throws MatrixException
	 */
	public boolean getBoolean(long... coordinates) throws MatrixException;

	/**
	 * Sets an entry in the matrix to a boolean value. If the matrix cannot
	 * store byte values, the value will be represented as good as possible.
	 * 
	 * @param value
	 *            boolean value
	 * @param coordinates
	 *            location of the entry
	 * @throws MatrixException
	 */
	public void setBoolean(boolean value, long... coordinates) throws MatrixException;

}

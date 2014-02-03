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

package org.ujmp.core.floatmatrix;

import org.ujmp.core.genericmatrix.GenericMatrix;

public interface FloatMatrix extends GenericMatrix<Float> {

	/**
	 * Returns a float representation of an entry in the matrix. The stored
	 * value will be converted to a float as good as possible.
	 * 
	 * @param coordinates
	 *            location of the entry
	 * @return a float representation of the entry
	 * @
	 */
	public float getFloat(long... coordinates) ;

	/**
	 * Sets an entry in the matrix to a float value. If the matrix cannot store
	 * float values, the value will be represented as good as possible.
	 * 
	 * @param value
	 *            float value
	 * @param coordinates
	 *            location of the entry
	 * @
	 */
	public void setFloat(float value, long... coordinates) ;

}

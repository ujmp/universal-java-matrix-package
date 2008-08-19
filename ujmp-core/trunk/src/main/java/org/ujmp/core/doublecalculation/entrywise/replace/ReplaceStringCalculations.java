/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

package org.ujmp.core.doublecalculation.entrywise.replace;

import java.util.regex.Pattern;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublecalculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;

public interface ReplaceStringCalculations {

	/**
	 * Replaces matching values in the matrix with another value
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param search
	 *            Object to search for
	 * @param replacement
	 *            Object used to replace the original value
	 * @return matrix with modified entries
	 * @throws MatrixException
	 */
	public Matrix replace(Ret returnType, Object search, Object replacement) throws MatrixException;

	/**
	 * Replaces matching text in every entry of the matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param search
	 *            Regular expression to search for
	 * @param replacement
	 *            Replacement String
	 * @return matrix with modified entries
	 */
	public Matrix replaceRegex(Ret returnType, String search, String replacement)
			throws MatrixException;

	/**
	 * Replaces matching text in every entry of the matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param search
	 *            Regular expression pattern to search for
	 * @param replacement
	 *            Replacement String
	 * @return matrix with modified entries
	 */
	public Matrix replaceRegex(Ret returnType, Pattern search, String replacement)
			throws MatrixException;

}

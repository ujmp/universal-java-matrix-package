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

package org.ujmp.core.stringmatrix.calculation;

import java.util.Collection;
import java.util.regex.Pattern;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;

public interface StringCalculations {

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

	public Matrix lowerCase(Ret returnType) throws MatrixException;

	public Matrix upperCase(Ret returnType) throws MatrixException;

	public Matrix tfIdf(boolean calculateTf, boolean calculateIdf, boolean normalize)
			throws MatrixException;

	public Matrix removePunctuation(Ret ret) throws MatrixException;

	public Matrix stem(Ret ret) throws MatrixException;

	public Matrix removeWords(Ret ret, Collection<String> words) throws MatrixException;

}

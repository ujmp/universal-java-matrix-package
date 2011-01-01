/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

import java.util.regex.Pattern;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;

public class ReplaceRegex extends AbstractStringCalculation {
	private static final long serialVersionUID = 8158807887609103123L;

	private Pattern searchPattern = null;

	private String replaceString = null;

	public ReplaceRegex(Matrix matrix, String searchString, String replaceString) {
		this(matrix, Pattern.compile(searchString), replaceString);
	}

	public ReplaceRegex(Matrix matrix, Pattern searchPattern, String replaceString) {
		super(matrix);
		this.searchPattern = searchPattern;
		this.replaceString = replaceString;
	}

	
	public String getString(long... coordinates) throws MatrixException {
		String src = getSource().getAsString(coordinates);

		return (src == null) ? null : searchPattern.matcher(src).replaceAll(replaceString);
	}

	public static Matrix calc(Matrix source, Pattern search, String replacement)
			throws MatrixException {
		Matrix ret = MatrixFactory.zeros(ValueType.STRING, source.getSize());
		for (long[] c : source.availableCoordinates()) {
			String src = source.getAsString(c);
			ret.setAsString((src == null) ? null : search.matcher(src).replaceAll(replacement), c);
		}
		return ret;
	}

	public static Matrix calc(Matrix source, String searchString, String replacement)
			throws MatrixException {
		return calc(source, Pattern.compile(searchString), replacement);
	}

}

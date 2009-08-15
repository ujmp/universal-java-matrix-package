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

package org.ujmp.core.stringmatrix.calculation;

import java.util.HashSet;
import java.util.Set;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.StringUtil;

public class RemovePunctuation extends AbstractStringCalculation {
	private static final long serialVersionUID = 4734721685667215634L;

	public static Set<Character> allowedChars = null;

	static {
		allowedChars = new HashSet<Character>();
		allowedChars.add(' ');
		allowedChars.add('ü');
		allowedChars.add('ä');
		allowedChars.add('ö');
		allowedChars.add('Ü');
		allowedChars.add('Ä');
		allowedChars.add('Ö');
		for (char c = 'a'; c <= 'z'; c++) {
			allowedChars.add(c);
		}
		for (char c = 'A'; c <= 'Z'; c++) {
			allowedChars.add(c);
		}
	}

	public RemovePunctuation(Matrix m) {
		super(m);
	}

	@Override
	public String getString(long... coordinates) throws MatrixException {
		String s = getSource().getAsString(coordinates);
		s = StringUtil.retainChars(s, allowedChars, ' ');
		s = s.replaceAll("\\s+", " ");
		return s;
	}

}

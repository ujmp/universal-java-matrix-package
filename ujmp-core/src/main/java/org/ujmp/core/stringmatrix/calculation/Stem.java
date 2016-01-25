/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

import org.ujmp.core.Matrix;
import org.ujmp.core.util.PorterStemmer;

public class Stem extends AbstractStringCalculation {
	private static final long serialVersionUID = 1221910899287177556L;

	private PorterStemmer stemmer = new PorterStemmer();

	public Stem(Matrix m) {
		super(m);
	}

	public String getString(long... coordinates) {
		String s = getSource().getAsString(coordinates).toLowerCase();
		StringBuilder result = new StringBuilder(s.length());
		String[] words = s.split("\\s+");
		for (int i = 0; i < words.length; i++) {
			String w = words[i];
			if (w.length() == 0) {
				continue;
			}
			result.append(stemmer.stem(w));
			if (i < words.length - 1) {
				result.append(" ");
			}
		}
		return result.toString();
	}

}

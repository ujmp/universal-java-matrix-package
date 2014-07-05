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

package org.ujmp.core.text;

import java.text.BreakIterator;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultSparseDoubleMatrix;

public abstract class TextUtil {

	public static final DenseDoubleMatrix2D getCharacterFrequencies(final String s,
			char... validCharacters) {
		final String copy = s.toLowerCase();
		final boolean[] isValidCharacter = createCharacterVector(validCharacters);
		final double[] freq = new double[256];
		for (int i = copy.length(); --i != -1;) {
			final char c = copy.charAt(i);
			if (c < 256 && isValidCharacter[c]) {
				freq[c]++;
			}
		}
		return Matrix.Factory.linkToArray(freq);
	}

	private static final boolean[] createCharacterVector(char... chars) {
		final boolean[] characterVector = new boolean[256];
		if (chars.length == 0) {
			Arrays.fill(characterVector, true);
		} else {
			for (char c : chars) {
				if (c < 256) {
					characterVector[c] = true;
				}
			}
		}
		return characterVector;
	}

	public static final Matrix getCharacterBigramFrequencies(final String s,
			char... validCharacters) {
		final String copy = " " + s.toLowerCase() + " ";
		final boolean[] isValidCharacter = createCharacterVector(validCharacters);
		final Matrix m = new DefaultSparseDoubleMatrix(65536, 1);
		for (int i = copy.length(); --i != 0;) {
			final char c1 = copy.charAt(i - 1);
			final char c2 = copy.charAt(i);
			if (c1 < 256 && c2 < 256 && isValidCharacter[c1] && isValidCharacter[c2]) {
				final int index = c1 * 256 + c2;
				m.setAsDouble(m.getAsDouble(index, 0) + 1, index, 0);
			}
		}
		return m;
	}

	public static final Matrix getCharacterTrigramFrequencies(final String s,
			char... validCharacters) {
		final String copy = " " + s.toLowerCase() + " ";
		final boolean[] isValidCharacter = createCharacterVector(validCharacters);
		final Matrix m = new DefaultSparseDoubleMatrix(16777216, 1);
		for (int i = copy.length(); --i != 0;) {
			final char c1 = copy.charAt(i - 2);
			final char c2 = copy.charAt(i - 1);
			final char c3 = copy.charAt(i);
			if (c1 < 256 && c2 < 256 && c3 < 256 && isValidCharacter[c1] && isValidCharacter[c2]
					&& isValidCharacter[c3]) {
				final int index = c1 * 65536 + c2 * 256 + c3;
				m.setAsDouble(m.getAsDouble(index, 0) + 1, index, 0);
			}
		}
		return m;
	}

	public static final Matrix createBagOfWordsVector(String string, List<String> dictionary) {
		Matrix m = Matrix.Factory.zeros(dictionary.size(), 1);
		StringTokenizer st = new StringTokenizer(string, " \t\n\r\f,.:;?![]'");
		while (st.hasMoreElements()) {
			long index = dictionary.indexOf(st.nextElement());
			m.setAsDouble(m.getAsDouble(index, 0) + 1, index, 0);
		}
		return m;
	}
}

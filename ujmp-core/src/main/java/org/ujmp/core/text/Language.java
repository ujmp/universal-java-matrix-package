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

package org.ujmp.core.text;

import java.io.File;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.util.io.IntelligentFileReader;

public enum Language {
	ENGLISH(English.ALPHABET, English.ALLOWEDCHARACTERS, English.CHARFREQUENCIES,
			English.CHARBIGRAMFREQUENCIES, English.AVERAGE_WORD_LENGTH), //
	GERMAN(German.ALPHABET, German.ALLOWEDCHARACTERS, German.CHARFREQUENCIES,
			German.CHARBIGRAMFREQUENCIES, German.AVERAGE_WORD_LENGTH);

	private final char[] alphabet;
	private final char[] allowedCharacters;
	private final Matrix charFrequencies;
	private final Matrix charBigramFrequencies;
	private final double averageWordLength;

	private Language(char[] alphabet, char[] allowedCharacters, Matrix charFrequencies,
			Matrix charBigramFrequencies, double averageWordLength) {
		this.alphabet = alphabet;
		this.allowedCharacters = allowedCharacters;
		this.charFrequencies = charFrequencies;
		this.charBigramFrequencies = charBigramFrequencies;
		this.averageWordLength = averageWordLength;
	}

	public char[] getAlphabet() {
		return alphabet;
	}

	public char[] getAllowedCharacters() {
		return allowedCharacters;
	}

	public static final Language guess(File file) {
		return guess(IntelligentFileReader.load(file));
	}

	public double getAverageWordLength() {
		return averageWordLength;
	}

	public static final Language guess(String string) {
		double bestSim = 0;
		Language bestLanguage = null;
		for (Language lang : values()) {
			Matrix count = TextUtil.getCharacterBigramFrequencies(string, lang.getAlphabet());
			double sum = count.getValueSum();
			Matrix freq = count.divide(Ret.NEW, true, sum).log(Ret.NEW);
			// add NaN where value is 0 to compare only found letters
			// for (long[] c : freq.allCoordinates()) {
			// if (freq.getAsDouble(c) == 0) {
			// freq.setAsDouble(Double.NaN, c);
			// } else {
			// System.out.println(freq.getAsDouble(c));
			// }
			// }

			double sim = freq.cosineSimilarityTo(lang.getCharacterBigramFrequencies().log(Ret.NEW),
					true);
			System.out.println(lang + ": " + sim);
			if (sim > bestSim) {
				bestSim = sim;
				bestLanguage = lang;
			}
		}
		return bestLanguage;
	}

	public Matrix getCharacterFrequencies() {
		return charFrequencies;
	}

	public Matrix getCharacterBigramFrequencies() {
		return charBigramFrequencies;
	}
}

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.ujmp.core.Matrix;
import org.ujmp.core.collections.list.FastArrayList;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultSparseDoubleMatrix;
import org.ujmp.core.util.VerifyUtil;

public abstract class TextUtil {

	public static final String ALPHA_NUMERIC_REGEX = "^[A-Za-z0-9]+$";
	public static final String HAS_DASH_REGEX = "^.+-.+$";
	public static final String INIT_DASH_REGEX = "^-.+$";
	public static final String END_DASH_REGEX = "^-.+$";
	public static final String PUNCTUATION_REGEX = "^[,.:;!?]$";
	public static final String ONE_QUESTION_MARK_REGEX = "^[?]$";
	public static final String TWO_QUESTION_MARKS_REGEX = "^[??]$";
	public static final String THREE_QUESTION_MARKS_REGEX = "^[???]$";
	public static final String MULTIPLE_QUESTION_MARKS_REGEX = "^[?][?]+$";
	public static final String ONE_EXCLAMATION_MARK_REGEX = "^[!]$";
	public static final String TWO_EXCLAMATION_MARKS_REGEX = "^[!!]$";
	public static final String THREE_EXCLAMATION_MARKS_REGEX = "^[!!!]$";
	public static final String MULTIPLE_EXCLAMATION_MARKS_REGEX = "^[!][!]+$";
	public static final String QUESTION_EXCLAMATION_MARK_REGEX = "^[?][!]$";
	public static final String EXCLAMATION_QUESTION_MARK_REGEX = "^[!][?]$";
	public static final String INIT_CAPS_REGEX = "^[A-Z].+$";
	public static final String INIT_CAPS_ALPHA_REGEX = "^[A-Z][a-z]+$";
	public static final String ONE_CAP_REGEX = "^[A-Z]$";
	public static final String TWO_CAPS_REGEX = "^[A-Z][A-Z]$";
	public static final String THREE_CAPS_REGEX = "^[A-Z][A-Z][A-Z]$";
	public static final String FOUR_CAPS_REGEX = "^[A-Z][A-Z][A-Z][A-Z]$";
	public static final String ALL_CAPS_REGEX = "^[A-Z]+$";
	public static final String CAPS_MIX_REGEX = "^[A-Za-z]+$";
	public static final String ONE_DIGIT_REGEX = "^[0-9]$";
	public static final String TWO_DIGITS_REGEX = "^[0-9][0-9]$";
	public static final String THREE_DIGITS_REGEX = "^[0-9][0-9][0-9]$";
	public static final String FOUR_DIGITS_REGEX = "^[0-9][0-9][0-9][0-9]$";
	public static final String HAS_DIGIT_REGEX = "^.+[0-9].+$";
	public static final String POSITIVE_INTEGER_REGEX = "^[0-9]+$";
	public static final String NEGATIVE_INTEGER_REGEX = "^-[0-9]+$";
	public static final String FLOATING_POINT_NUMBER_REGEX = "^[-+]?[0-9]*\\.?[0-9]+$";
	public static final String EXP_NUMBER_REGEX = "^[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?$";
	public static final String ROMAN_NUMBER_SMALL_REGEX = "^[ivxdlcm]+$";
	public static final String ROMAN_NUMBER_CAPITAL_REGEX = "^[IVXDLCM]+$";
	public static final String SINGLE_INITIAL_REGEX = "^[a-zA-Z]\\.$";
	public static final String IN_PARENTHESES_REGEX = "^(.+)$";
	public static final String OBD_REGEX = "^[PBCU][0-9A-F][0-9A-F][0-9A-F][0-9A-F]$";
	public static final String YEAR_REGEX = "^[12][0-9][0-9][0-9]$";
	public static final String HEX_REGEX = "^[0-9A-Fa-f][0-9A-Fa-f]+$";
	public static final String EMAIL_REGEX = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
	public static final String IP_REGEX = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
	public static final String HTML_REGEX = "^<([a-z]+)([^<]+)*(?:>(.*)<\\/\\1>|\\s+\\/>)$";
	public static final String URL_REGEX = "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$";

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

	public static final List<String> splitLineIntoSentences(final String line) {
		VerifyUtil.verifyNotNull(line, "text cannot be null");
		VerifyUtil.verifyTrue(line.split("\n").length == 1, "text must be in one line");
		StringTokenizer tokenizer = new StringTokenizer(line, ".:;!?", true);
		final List<String> tokens = new FastArrayList<String>();
		String token1 = null;
		String token2 = null;
		String token3 = null;
		while (tokenizer.hasMoreTokens()) {
			token1 = token2;
			token2 = token3;
			token3 = tokenizer.nextToken();

			if (token2 != null && token2.trim().isEmpty()) {
				continue;
			}

			if (token1 != null && token2 != null && token3 != null) {

				if (".".equals(token2)) {
					if (tokens.isEmpty()) {
						tokens.add(token1 + token2);
					} else {
						tokens.set(tokens.size() - 1, tokens.get(tokens.size() - 1) + token2);
					}
				} else if (":".equals(token2)) {
					if (tokens.isEmpty()) {
						tokens.add(token1 + token2);
					} else {
						tokens.set(tokens.size() - 1, tokens.get(tokens.size() - 1) + token2);
					}
				} else if (";".equals(token2)) {
					if (tokens.isEmpty()) {
						tokens.add(token1 + token2);
					} else {
						tokens.set(tokens.size() - 1, tokens.get(tokens.size() - 1) + token2);
					}
				} else if ("!".equals(token2)) {
					if (tokens.isEmpty()) {
						tokens.add(token1 + token2);
					} else {
						tokens.set(tokens.size() - 1, tokens.get(tokens.size() - 1) + token2);
					}
				} else if ("?".equals(token2)) {
					if (tokens.isEmpty()) {
						tokens.add(token1 + token2);
					} else {
						tokens.set(tokens.size() - 1, tokens.get(tokens.size() - 1) + token2);
					}
				} else if (tokens.isEmpty()) {
					tokens.add(token2);
				} else if (endsWithAbbreviation(tokens.get(tokens.size() - 1))) {
					tokens.set(tokens.size() - 1, tokens.get(tokens.size() - 1) + token2);
				} else {
					tokens.add(token2);
				}

			}

		}

		if (token1 != null && token2 != null && token3 != null) {
			if (".".equals(token3)) {
				tokens.set(tokens.size() - 1, tokens.get(tokens.size() - 1) + token3);
			} else if (":".equals(token3)) {
				tokens.set(tokens.size() - 1, tokens.get(tokens.size() - 1) + token3);
			} else if (";".equals(token3)) {
				tokens.set(tokens.size() - 1, tokens.get(tokens.size() - 1) + token3);
			} else if ("?".equals(token3)) {
				tokens.set(tokens.size() - 1, tokens.get(tokens.size() - 1) + token3);
			} else if ("!".equals(token3)) {
				tokens.set(tokens.size() - 1, tokens.get(tokens.size() - 1) + token3);
			}

		} else if (token1 != null && token2 != null && token3 == null) {
			// handle short sentences
			tokens.add(token1);
			tokens.add(token2);
		} else if (token1 != null && token2 == null && token3 == null) {
			// only one token
			tokens.add(token1);
		}

		return tokens;
	}

	public static final DefaultTextBlock splitTextIntoObjects(String text) {
		DefaultTextBlock textBlock = new DefaultTextBlock();
		text = text.replaceAll("\r\n", " ").replaceAll("\n", " ");
		List<String> sentenceTexts = splitLineIntoSentences(text);
		int s = 0;
		for (String sentenceText : sentenceTexts) {
			TextSentence sentence = new DefaultTextSentence();
			sentence.setMetaData("Id", s++);
			List<String> tokenTexts = splitSentenceIntoTokens(sentenceText, 0);
			int i = 0;
			for (String tokenText : tokenTexts) {
				TextToken token = new DefaultTextToken(tokenText);
				token.put("Id", i++);
				sentence.add(token);
			}
			textBlock.add(sentence);
		}
		return textBlock;
	}

	public static final List<List<String>> createWordTrigrams(String text) {
		List<List<String>> wordTrigrams = new FastArrayList<List<String>>();
		List<String> lines = splitTextIntoLines(text);
		for (String line : lines) {
			List<String> sentences = splitLineIntoSentences(line);
			for (String sentence : sentences) {
				wordTrigrams.addAll(createWordTrigrams(splitSentenceIntoTokens(sentence, 3)));
			}
		}
		return wordTrigrams;
	}

	public static final List<String> splitTextIntoLines(String text) {
		VerifyUtil.verifyNotNull(text, "text cannot be null");
		return Arrays.asList(text.split("\n"));
	}

	public static final List<List<String>> createWordBigrams(String text) {
		List<List<String>> wordBigrams = new FastArrayList<List<String>>();
		List<String> lines = splitTextIntoLines(text);
		for (String line : lines) {
			List<String> sentences = splitLineIntoSentences(line);
			for (String sentence : sentences) {
				wordBigrams.addAll(createWordBigrams(splitSentenceIntoTokens(sentence, 2)));
			}
		}
		return wordBigrams;
	}

	public static final List<String> createWordUnigrams(String text, int ngramSize) {
		List<String> wordUnigrams = new FastArrayList<String>();
		List<String> lines = splitTextIntoLines(text);
		for (String line : lines) {
			List<String> sentences = splitLineIntoSentences(line);
			for (String sentence : sentences) {
				wordUnigrams.addAll(splitSentenceIntoTokens(sentence, ngramSize));
			}
		}
		return wordUnigrams;
	}

	public static Map<List<String>, Integer> getWordBigramCounts(String text) {
		List<List<String>> wordBigrams = createWordBigrams(text);
		Map<List<String>, Integer> wordBigramCounts = new HashMap<List<String>, Integer>();
		for (List<String> wordBigram : wordBigrams) {
			Integer count = wordBigramCounts.get(wordBigram);
			count = count == null ? 1 : count + 1;
			wordBigramCounts.put(wordBigram, count);
		}
		return wordBigramCounts;
	}

	public static Map<String, Integer> getWordUnigramCounts(String text, int ngramSize) {
		List<String> wordUnigrams = createWordUnigrams(text, ngramSize);
		Map<String, Integer> wordUnigramCounts = new HashMap<String, Integer>();
		for (String wordUnigram : wordUnigrams) {
			Integer count = wordUnigramCounts.get(wordUnigram);
			count = count == null ? 1 : count + 1;
			wordUnigramCounts.put(wordUnigram, count);
		}
		return wordUnigramCounts;
	}

	public static final List<String> splitSentenceIntoTokens(final String sentence, int ngramSize) {
		VerifyUtil.verifyNotNull(sentence, "text cannot be null");
		VerifyUtil.verifyTrue(sentence.split("\n").length == 1, "text must be in one line");
		sentence.replace((char) 160, ' ');
		StringTokenizer tokenizer = new StringTokenizer(sentence,
				" \u00A0.;,、،:&\\⁄/”“‘\"―—–‒‐-!?{}()[]", true);
		final List<String> tokens = new FastArrayList<String>();
		for (int i = 1; i < ngramSize; i++) {
			tokens.add("".intern());
		}
		while (tokenizer.hasMoreTokens()) {
			final String token = tokenizer.nextToken().trim();
			if (!token.isEmpty() && !token.equals("\u00A0")) {
				tokens.add(token.intern());
			}
		}
		for (int i = 1; i < ngramSize; i++) {
			tokens.add("".intern());
		}
		return tokens;
	}

	public static final List<List<String>> createWordBigrams(List<String> words) {
		List<List<String>> wordBigrams = new FastArrayList<List<String>>();
		for (int i = 0; i < words.size() - 1; i++) {
			String word1 = words.get(i);
			String word2 = words.get(i + 1);
			List<String> bigram = new FastArrayList<String>(2);
			bigram.add(word1);
			bigram.add(word2);
			wordBigrams.add(bigram);
		}
		return wordBigrams;
	}

	public static final List<List<String>> createWordTrigrams(List<String> words) {
		List<List<String>> wordTrigrams = new FastArrayList<List<String>>();
		for (int i = 0; i < words.size() - 2; i++) {
			String word1 = words.get(i);
			String word2 = words.get(i + 1);
			String word3 = words.get(i + 2);
			List<String> trigram = new FastArrayList<String>(3);
			trigram.add(word1);
			trigram.add(word2);
			trigram.add(word3);
			wordTrigrams.add(trigram);
		}
		return wordTrigrams;
	}

	public static boolean endsWithAbbreviation(String string) {
		string = string.toLowerCase();
		if (string.endsWith(" 0.")) {
			return true;
		} else if (string.endsWith(" 1.")) {
			return true;
		} else if (string.endsWith(" 2.")) {
			return true;
		} else if (string.endsWith(" 3.")) {
			return true;
		} else if (string.endsWith(" 4.")) {
			return true;
		} else if (string.endsWith(" 5.")) {
			return true;
		} else if (string.endsWith(" 6.")) {
			return true;
		} else if (string.endsWith(" 7.")) {
			return true;
		} else if (string.endsWith(" 8.")) {
			return true;
		} else if (string.endsWith(" 9.")) {
			return true;
		} else if (string.endsWith("10.")) {
			return true;
		} else if (string.endsWith("11.")) {
			return true;
		} else if (string.endsWith("12.")) {
			return true;
		} else if (string.endsWith("13.")) {
			return true;
		} else if (string.endsWith("14.")) {
			return true;
		} else if (string.endsWith("15.")) {
			return true;
		} else if (string.endsWith("16.")) {
			return true;
		} else if (string.endsWith("17.")) {
			return true;
		} else if (string.endsWith("18.")) {
			return true;
		} else if (string.endsWith("19.")) {
			return true;
		} else if (string.endsWith("20.")) {
			return true;
		} else if (string.endsWith("21.")) {
			return true;
		} else if (string.endsWith("22.")) {
			return true;
		} else if (string.endsWith("23.")) {
			return true;
		} else if (string.endsWith("24.")) {
			return true;
		} else if (string.endsWith("25.")) {
			return true;
		} else if (string.endsWith("26.")) {
			return true;
		} else if (string.endsWith("27.")) {
			return true;
		} else if (string.endsWith("28.")) {
			return true;
		} else if (string.endsWith("29.")) {
			return true;
		} else if (string.endsWith("30.")) {
			return true;
		} else if (string.endsWith("31.")) {
			return true;
		} else if (string.endsWith("32.")) {
			return true;
		} else if (string.endsWith("33.")) {
			return true;
		} else if (string.endsWith("34.")) {
			return true;
		} else if (string.endsWith("35.")) {
			return true;
		} else if (string.endsWith("36.")) {
			return true;
		} else if (string.endsWith("37.")) {
			return true;
		} else if (string.endsWith("38.")) {
			return true;
		} else if (string.endsWith("39.")) {
			return true;
		} else if (string.endsWith(" 0:")) {
			return true;
		} else if (string.endsWith(" 1:")) {
			return true;
		} else if (string.endsWith(" 2:")) {
			return true;
		} else if (string.endsWith(" 3:")) {
			return true;
		} else if (string.endsWith(" 4:")) {
			return true;
		} else if (string.endsWith(" 5:")) {
			return true;
		} else if (string.endsWith(" 6:")) {
			return true;
		} else if (string.endsWith(" 7:")) {
			return true;
		} else if (string.endsWith(" 8:")) {
			return true;
		} else if (string.endsWith(" 9:")) {
			return true;
		} else if (string.endsWith(" a.")) {
			return true;
		} else if (string.endsWith(" b.")) {
			return true;
		} else if (string.endsWith(" c.")) {
			return true;
		} else if (string.endsWith(" d.")) {
			return true;
		} else if (string.endsWith(" e.")) {
			return true;
		} else if (string.endsWith(" f.")) {
			return true;
		} else if (string.endsWith(" g.")) {
			return true;
		} else if (string.endsWith(" h.")) {
			return true;
		} else if (string.endsWith(" i.")) {
			return true;
		} else if (string.endsWith(" j.")) {
			return true;
		} else if (string.endsWith(" k.")) {
			return true;
		} else if (string.endsWith(" l.")) {
			return true;
		} else if (string.endsWith(" m.")) {
			return true;
		} else if (string.endsWith(" n.")) {
			return true;
		} else if (string.endsWith(" o.")) {
			return true;
		} else if (string.endsWith(" p.")) {
			return true;
		} else if (string.endsWith(" q.")) {
			return true;
		} else if (string.endsWith(" r.")) {
			return true;
		} else if (string.endsWith(" s.")) {
			return true;
		} else if (string.endsWith(" t.")) {
			return true;
		} else if (string.endsWith(" u.")) {
			return true;
		} else if (string.endsWith(" v.")) {
			return true;
		} else if (string.endsWith(" w.")) {
			return true;
		} else if (string.endsWith(" x.")) {
			return true;
		} else if (string.endsWith(" y.")) {
			return true;
		} else if (string.endsWith(" z.")) {
			return true;
		} else if (string.endsWith(" ä.")) {
			return true;
		} else if (string.endsWith(" ö.")) {
			return true;
		} else if (string.endsWith(" ü.")) {
			return true;
		} else if (string.endsWith(" ß.")) {
			return true;
		} else if (string.endsWith(".a.")) {
			return true;
		} else if (string.endsWith(".b.")) {
			return true;
		} else if (string.endsWith(".c.")) {
			return true;
		} else if (string.endsWith(".d.")) {
			return true;
		} else if (string.endsWith(".e.")) {
			return true;
		} else if (string.endsWith(".f.")) {
			return true;
		} else if (string.endsWith(".g.")) {
			return true;
		} else if (string.endsWith(".h.")) {
			return true;
		} else if (string.endsWith(".i.")) {
			return true;
		} else if (string.endsWith(".j.")) {
			return true;
		} else if (string.endsWith(".k.")) {
			return true;
		} else if (string.endsWith(".l.")) {
			return true;
		} else if (string.endsWith(".m.")) {
			return true;
		} else if (string.endsWith(".n.")) {
			return true;
		} else if (string.endsWith(".o.")) {
			return true;
		} else if (string.endsWith(".p.")) {
			return true;
		} else if (string.endsWith(".q.")) {
			return true;
		} else if (string.endsWith(".r.")) {
			return true;
		} else if (string.endsWith(".s.")) {
			return true;
		} else if (string.endsWith(".t.")) {
			return true;
		} else if (string.endsWith(".u.")) {
			return true;
		} else if (string.endsWith(".v.")) {
			return true;
		} else if (string.endsWith(".w.")) {
			return true;
		} else if (string.endsWith(".x.")) {
			return true;
		} else if (string.endsWith(".y.")) {
			return true;
		} else if (string.endsWith(".z.")) {
			return true;
		} else if (string.endsWith(".ä.")) {
			return true;
		} else if (string.endsWith(".ö.")) {
			return true;
		} else if (string.endsWith(".ü.")) {
			return true;
		} else if (string.endsWith(".ß.")) {
			return true;
		} else if (string.endsWith(" ca.")) {
			return true;
		} else if (string.endsWith(" vs.")) {
			return true;
		} else if (string.endsWith(" rep.")) {
			return true;
		} else if (string.endsWith(" etc.")) {
			return true;
		} else if (string.endsWith(" usw.")) {
			return true;
		} else if (string.endsWith(" resp.")) {
			return true;
		} else if (string.endsWith(" incl.")) {
			return true;
		} else if (string.endsWith(" inkl.")) {
			return true;
		} else if (string.endsWith(" insges.")) {
			return true;
		} else if (string.endsWith(" zyl.")) {
			return true;
		} else if (string.endsWith(" cyl.")) {
			return true;
		} else if (string.endsWith(" dr.")) {
			return true;
		} else if (string.endsWith(" prof.")) {
			return true;
		} else if (string.endsWith(" gr.")) {
			return true;
		} else if (string.endsWith(" ppm.")) {
			return true;
		} else if (string.endsWith(" ggf.")) {
			return true;
		} else
			return false;
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

	public static Collection<TextToken> convertSentenceToTextTokens(String sentence) {
		List<String> strings = TextUtil.splitSentenceIntoTokens(sentence, 1);
		List<TextToken> tokens = new ArrayList<TextToken>();
		for (String string : strings) {
			TextToken textToken = new DefaultTextToken(string);
			tokens.add(textToken);
		}
		return tokens;
	}

	public static Collection<TextSentence> convertToTextBlockToSentences(String text) {
		List<String> strings = TextUtil.splitTextIntoSentences(text);
		List<TextSentence> sentences = new ArrayList<TextSentence>();
		for (String string : strings) {
			TextSentence textToken = new DefaultTextSentence(string);
			sentences.add(textToken);
		}
		return sentences;
	}

	public static List<String> splitTextIntoSentences(String text) {
		text = text.replaceAll("\n", " ");
		return splitLineIntoSentences(text);
	}

	public static Matrix stringToVector(String string) {
		return stringToVector(string, 131072);
	}

	public static Matrix stringToVector(String string, int size) {
		Matrix m = Matrix.Factory.zeros(size, 1);
		StringTokenizer st = new StringTokenizer(string, " \t\n\r\f,.:;?![]'");
		while (st.hasMoreElements()) {
			long index = Math.abs(st.nextElement().toString().toLowerCase().hashCode()) % size;
			m.setAsDouble(m.getAsDouble(index, 0) + 1, index, 0);
		}
		return m;
	}
}

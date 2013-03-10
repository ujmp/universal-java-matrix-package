/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

package org.ujmp.core.util;

import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public abstract class StringUtil {

	private static final NumberFormat DefaultNF = NumberFormat.getNumberInstance(Locale.US);

	public static final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
			'c', 'd', 'e', 'f' };

	public static final String BRACKETS = "[\\[\\]\\(\\)\\{\\}]";

	public static final String SEMICOLONORNEWLINE = "\\s*[;\\n]\\s*";

	public static final String COLONORSPACES = "[,\\s*]";

	static {
		DefaultNF.setMinimumFractionDigits(4);
		DefaultNF.setMaximumFractionDigits(4);
	}

	public static final String stripTags(String string) {
		return Pattern.compile("\\<[^\\>]*\\>", Pattern.DOTALL + Pattern.MULTILINE).matcher(string)
				.replaceAll("");
	}

	public static final String format(String s) {
		return (s == null) ? "" : s;
	}

	public static final String format(Object o) {
		if (o == null) {
			return "";
		}
		if (o instanceof String) {
			return (String) o;
		}
		if (o instanceof Matrix) {
			Matrix m = (Matrix) o;
			if (m.getLabel() != null) {
				return "[" + m.getLabel() + "]";
			} else {
				return "[Matrix]";
			}
		}
		if (o instanceof Number) {
			return format((Number) o);
		}
		return o.toString();
	}

	public static final String encodeToHex(Serializable o) throws IOException {
		return encodeToHex(SerializationUtil.serialize(o));
	}

	public static final String encodeToHex(byte[] data) {
		final StringBuilder s = new StringBuilder(data.length * 2);
		final int length = data.length;
		for (int i = 0; i < length; i++) {
			s.append(HEX[(data[i] + 128) / 16]).append(HEX[(data[i] + 128) % 16]);
		}
		return s.toString();
	}

	public static final int hexToInt(char c) {
		switch (c) {
		case '0':
			return 0;
		case '1':
			return 1;
		case '2':
			return 2;
		case '3':
			return 3;
		case '4':
			return 4;
		case '5':
			return 5;
		case '6':
			return 6;
		case '7':
			return 7;
		case '8':
			return 8;
		case '9':
			return 9;
		case 'A':
			return 10;
		case 'B':
			return 11;
		case 'C':
			return 12;
		case 'D':
			return 13;
		case 'E':
			return 14;
		case 'F':
			return 15;
		case 'a':
			return 10;
		case 'b':
			return 11;
		case 'c':
			return 12;
		case 'd':
			return 13;
		case 'e':
			return 14;
		case 'f':
			return 15;
		default:
			throw new RuntimeException("this is not a hex string");
		}
	}

	public static final byte[] decodeFromHex(String data) {
		byte[] bytes = new byte[data.length() / 2];
		for (int i = 0; i < data.length(); i += 2) {
			char c1 = data.charAt(i);
			char c2 = data.charAt(i + 1);
			int i1 = hexToInt(c1);
			int i2 = hexToInt(c2);
			bytes[i / 2] = (byte) (i1 * 16 + i2 - 128);
		}
		return bytes;
	}

	public static final String reverse(String s) {
		return new StringBuilder(s).reverse().toString();
	}

	public static final String convert(Object o) {
		if (o == null) {
			return null;
		}
		if (o instanceof String) {
			return (String) o;
		}
		if (o instanceof Matrix) {
			return ((Matrix) o).stringValue();
		}
		if (o instanceof Number) {
			return String.valueOf(((Number) o).doubleValue());
		}
		return o.toString();
	}

	public static final String format(Double value) {
		if (value == null)
			return "";
		if (Double.isNaN(value))
			return "NaN";
		if (Double.POSITIVE_INFINITY == value)
			return "Inf";
		if (Double.NEGATIVE_INFINITY == value)
			return "-Inf";
		return DefaultNF.format(value);
	}

	public static final String format(Number value) {
		if (value == null)
			return "";
		if (value instanceof Double)
			return format((Double) value);
		return DefaultNF.format(value);
	}

	public void setDefaultMaximumFractionDigits(int n) {
		DefaultNF.setMaximumFractionDigits(n);
	}

	public void setDefaultMinimumFractionDigits(int n) {
		DefaultNF.setMinimumFractionDigits(n);
	}

	public static final String deleteChar(String s, char ch) {
		return deleteChar(s, ch, 0);
	}

	public static final String deleteChar(String s, char ch, int startIndex) {
		int i = s.indexOf(ch, startIndex);
		if (i == -1) {
			return s;
		} else if (i == 0) {
			s = s.substring(1);
			return deleteChar(s, ch, 0);
		} else if (i == s.length() - 1) {
			s = s.substring(0, s.length() - 1);
			return s;
		} else {
			new String();
			s = s.substring(0, i) + s.substring(i + 1);
			return deleteChar(s, ch, i);
		}
	}

	public static long[][] parseSelection(String selectionString, long[] size) {
		if (selectionString.contains(";")) {
			return parseSelectionSemicolon(selectionString, size);
		} else {
			return parseSelectionComma(selectionString, size);
		}
	}

	private static long[][] parseSelectionComma(String selectionString, long[] size) {
		throw new MatrixException(
				"Matlab style is not supported yet. Please use a semicolon (;) to separate rows and columns");
	}

	private static long[][] parseSelectionSemicolon(String selectionString, long[] size) {
		String[] fields = selectionString.replaceAll(BRACKETS, "").split(";");
		long[][] selection = new long[fields.length][];

		for (int i = 0; i < fields.length; i++) {

			String dimension = fields[i].trim();

			if (dimension.contains("*")) {
				selection[i] = MathUtil.sequenceLong(0, size[i] - 1);
			} else {
				List<Long> list = new ArrayList<Long>();
				String[] dimselections = dimension.split("\\D*[ \\s,]\\D*");
				for (int j = 0; j < dimselections.length; j++) {
					String dimsel = dimselections[j];
					if (dimsel.contains(":") || dimsel.contains("-")) {
						String[] range = dimsel.split("[:-]");
						if (range.length == 0) {
							// all coordinates in this dimension
							list.addAll(MathUtil.sequenceListLong(0, size[i] - 1));
						} else if (range.length == 2) {
							// from lower bound to upper bound
							String startS = range[0];
							String endS = range[1];
							long start = Long.parseLong(startS.replaceAll("\\D", ""));
							long end = Long.parseLong(endS.replaceAll("\\D", ""));

							if (end > size[i]) {
								throw new MatrixException("Selection is bigger than size");
							}

							list.addAll(MathUtil.sequenceListLong(start, end));
						} else {
							throw new MatrixException("Selection not supported: " + dimsel);
						}

					} else {
						dimsel = dimsel.replaceAll("\\D", "");
						if (dimsel.length() > 0) {
							list.add(Long.parseLong(dimsel));
						}
					}
				}
				selection[i] = MathUtil.collectionToLong(list);
			}

		}

		return selection;
	}

	public static String duration(long time) {
		StringBuilder s = new StringBuilder();

		int days = (int) (time / (24 * 60 * 60 * 1000));
		time = time % (24 * 60 * 60 * 1000);
		int hours = (int) (time / (60 * 60 * 1000));
		time = time % (60 * 60 * 1000);
		int minutes = (int) (time / (60 * 1000));
		time = time % (60 * 1000);
		int seconds = (int) (time / (1000));

		if (days > 0) {
			s.append(days + "d");
		}
		if (hours > 0) {
			s.append(hours + "h");
		}
		if (minutes > 0) {
			s.append(minutes + "m");
		}
		s.append(seconds + "s");
		return s.toString();
	}

	public static String getAllAsString(Collection<Matrix> collection) {
		StringBuffer s = new StringBuffer();
		int i = 0;
		for (Matrix m : collection) {
			if (m != null && !m.isEmpty()) {
				s.append(StringUtil.format(m.stringValue()));
			}
			if (i < collection.size() - 1) {
				s.append(", ");
			}
			i++;
		}
		return s.toString();
	}

	public static String deleteChars(String s, Set<Character> ignoredChars, char replacement) {
		char[] result = new char[s.length()];
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (!ignoredChars.contains(c)) {
				result[i] = c;
			} else {
				result[i] = replacement;
			}
		}
		return new String(result);
	}

	public static String retainChars(String s, Set<Character> allowedChars, char replacement) {
		char[] result = new char[s.length()];
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (allowedChars.contains(c)) {
				result[i] = c;
			} else {
				result[i] = replacement;
			}
		}
		return new String(result);
	}

	public static String toString(Matrix m, Object... parameters) {
		int width = 10;
		long maxRows = UJMPSettings.getMaxRowsToPrint();
		long maxColumns = UJMPSettings.getMaxColumnsToPrint();

		StringBuilder s = new StringBuilder();

		final String EOL = System.getProperty("line.separator");

		long rowCount = m.getRowCount();
		long columnCount = m.getColumnCount();
		for (int row = 0; row < rowCount && row < maxRows; row++) {
			for (int col = 0; col < columnCount && col < maxColumns; col++) {
				Object o = m.getAsObject(row, col);
				String v = StringUtil.format(o);
				v = StringUtil.pad(v, width);
				s.append(v);
			}
			s.append(EOL);
		}

		if (rowCount == 0 || columnCount == 0) {
			s.append("[" + rowCount + "x" + columnCount + "]" + EOL);
		} else if (rowCount > UJMPSettings.getMaxRowsToPrint()
				|| columnCount > UJMPSettings.getMaxColumnsToPrint()) {
			s.append("[...]");
		}

		return s.toString();

	}

	public static final String pad(String s, int length) {
		length = length - s.length();
		StringBuilder r = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			r.append(" ");
		}
		r.append(s);
		return r.toString();
	}

	public static String getString(Object object) {
		if (object == null) {
			return null;
		} else {
			return format(object);
		}
	}

	public static final boolean isASCII(char c) {
		return c < 128;
	}

	public static final boolean isAlphanumeric(char c) {
		return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}

	public static final boolean isAlphanumeric(String s) {
		if (s == null) {
			return false;
		}
		for (int i = s.length(); --i != -1;) {
			if (!isAlphanumeric(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static final boolean isLetter(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}

	public static final boolean isControl(char c) {
		return c < 32 || c == 127;
	}

	public static final boolean isNumber(char c) {
		return c >= '0' && c <= '9';
	}

	public static final boolean isUmlaut(char c) {
		return c == 228 || c == 252 || c == 246 || c == 196 || c == 220 || c == 214;
	}

	public static final boolean isPrintable(char c) {
		return (c >= 32 && c < 127) || (c >= 161 || c <= 255);
	}

	public static final boolean isGerman(char c) {
		return (c >= 32 && c < 127) || isUmlaut(c) || c == 223;
	}

	public static final boolean isLower(char c) {
		return c >= 'a' && c <= 'z';
	}

	public static final boolean isUpper(char c) {
		return c >= 'A' && c <= 'Z';
	}

	public static boolean isPrintable(String s) {
		if (s == null) {
			return false;
		}
		for (int i = s.length(); --i != -1;) {
			if (!isPrintable(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isSuitedAsFilename(String s) {
		if (!StringUtil.isPrintable(s)) {
			return false;
		}
		for (int i = s.length(); --i != -1;) {
			char c = s.charAt(i);
			if (c == '/') {
				return false;
			} else if (c == '\\') {
				return false;
			} else if (c == ':') {
				return false;
			} else if (c == ' ') {
				return false;
			} else if (c == '*') {
				return false;
			} else if (c == '<') {
				return false;
			} else if (c == '>') {
				return false;
			} else if (c == '?') {
				return false;
			} else if (c == '|') {
				return false;
			}
		}
		return true;
	}

	public static String upperCaseWords(String string) {

		string = string.replaceAll(" a", " A");
		string = string.replaceAll(" b", " B");
		string = string.replaceAll(" c", " C");
		string = string.replaceAll(" d", " D");
		string = string.replaceAll(" e", " E");
		string = string.replaceAll(" f", " F");
		string = string.replaceAll(" g", " G");
		string = string.replaceAll(" h", " H");
		string = string.replaceAll(" i", " I");
		string = string.replaceAll(" j", " J");
		string = string.replaceAll(" k", " K");
		string = string.replaceAll(" l", " L");
		string = string.replaceAll(" m", " M");
		string = string.replaceAll(" n", " N");
		string = string.replaceAll(" o", " O");
		string = string.replaceAll(" p", " P");
		string = string.replaceAll(" q", " Q");
		string = string.replaceAll(" r", " R");
		string = string.replaceAll(" s", " S");
		string = string.replaceAll(" t", " T");
		string = string.replaceAll(" u", " U");
		string = string.replaceAll(" v", " V");
		string = string.replaceAll(" w", " W");
		string = string.replaceAll(" x", " X");
		string = string.replaceAll(" y", " Y");
		string = string.replaceAll(" z", " Z");

		string = string.replaceAll("\\(a", "(A");
		string = string.replaceAll("\\(b", "(B");
		string = string.replaceAll("\\(c", "(C");
		string = string.replaceAll("\\(d", "(D");
		string = string.replaceAll("\\(e", "(E");
		string = string.replaceAll("\\(f", "(F");
		string = string.replaceAll("\\(g", "(G");
		string = string.replaceAll("\\(h", "(H");
		string = string.replaceAll("\\(i", "(I");
		string = string.replaceAll("\\(j", "(J");
		string = string.replaceAll("\\(k", "(K");
		string = string.replaceAll("\\(l", "(L");
		string = string.replaceAll("\\(m", "(M");
		string = string.replaceAll("\\(n", "(N");
		string = string.replaceAll("\\(o", "(O");
		string = string.replaceAll("\\(p", "(P");
		string = string.replaceAll("\\(q", "(Q");
		string = string.replaceAll("\\(r", "(R");
		string = string.replaceAll("\\(s", "(S");
		string = string.replaceAll("\\(t", "(T");
		string = string.replaceAll("\\(u", "(U");
		string = string.replaceAll("\\(v", "(V");
		string = string.replaceAll("\\(w", "(W");
		string = string.replaceAll("\\(x", "(X");
		string = string.replaceAll("\\(y", "(Y");
		string = string.replaceAll("\\(z", "(Z");

		string = string.replaceAll("-a", "-A");
		string = string.replaceAll("-b", "-B");
		string = string.replaceAll("-c", "-C");
		string = string.replaceAll("-d", "-D");
		string = string.replaceAll("-e", "-E");
		string = string.replaceAll("-f", "-F");
		string = string.replaceAll("-g", "-G");
		string = string.replaceAll("-h", "-H");
		string = string.replaceAll("-i", "-I");
		string = string.replaceAll("-j", "-J");
		string = string.replaceAll("-k", "-K");
		string = string.replaceAll("-l", "-L");
		string = string.replaceAll("-m", "-M");
		string = string.replaceAll("-n", "-N");
		string = string.replaceAll("-o", "-O");
		string = string.replaceAll("-p", "-P");
		string = string.replaceAll("-q", "-Q");
		string = string.replaceAll("-r", "-R");
		string = string.replaceAll("-s", "-S");
		string = string.replaceAll("-t", "-T");
		string = string.replaceAll("-u", "-U");
		string = string.replaceAll("-v", "-V");
		string = string.replaceAll("-w", "-W");
		string = string.replaceAll("-x", "-X");
		string = string.replaceAll("-y", "-Y");
		string = string.replaceAll("-z", "-Z");

		string = string.replaceAll("\\[a", "[A");
		string = string.replaceAll("\\[b", "[B");
		string = string.replaceAll("\\[c", "[C");
		string = string.replaceAll("\\[d", "[D");
		string = string.replaceAll("\\[e", "[E");
		string = string.replaceAll("\\[f", "[F");
		string = string.replaceAll("\\[g", "[G");
		string = string.replaceAll("\\[h", "[H");
		string = string.replaceAll("\\[i", "[I");
		string = string.replaceAll("\\[j", "[J");
		string = string.replaceAll("\\[k", "[K");
		string = string.replaceAll("\\[l", "[L");
		string = string.replaceAll("\\[m", "[M");
		string = string.replaceAll("\\[n", "[N");
		string = string.replaceAll("\\[o", "[O");
		string = string.replaceAll("\\[p", "[P");
		string = string.replaceAll("\\[q", "[Q");
		string = string.replaceAll("\\[r", "[R");
		string = string.replaceAll("\\[s", "[S");
		string = string.replaceAll("\\[t", "[T");
		string = string.replaceAll("\\[u", "[U");
		string = string.replaceAll("\\[v", "[V");
		string = string.replaceAll("\\[w", "[W");
		string = string.replaceAll("\\[x", "[X");
		string = string.replaceAll("\\[y", "[Y");
		string = string.replaceAll("\\[z", "[Z");

		string = string.replaceFirst("^a", "A");
		string = string.replaceFirst("^b", "B");
		string = string.replaceFirst("^c", "C");
		string = string.replaceFirst("^d", "D");
		string = string.replaceFirst("^e", "E");
		string = string.replaceFirst("^f", "F");
		string = string.replaceFirst("^g", "G");
		string = string.replaceFirst("^h", "H");
		string = string.replaceFirst("^i", "I");
		string = string.replaceFirst("^j", "J");
		string = string.replaceFirst("^k", "K");
		string = string.replaceFirst("^l", "L");
		string = string.replaceFirst("^m", "M");
		string = string.replaceFirst("^n", "N");
		string = string.replaceFirst("^o", "O");
		string = string.replaceFirst("^p", "P");
		string = string.replaceFirst("^q", "Q");
		string = string.replaceFirst("^r", "R");
		string = string.replaceFirst("^s", "S");
		string = string.replaceFirst("^t", "T");
		string = string.replaceFirst("^u", "U");
		string = string.replaceFirst("^v", "V");
		string = string.replaceFirst("^w", "W");
		string = string.replaceFirst("^x", "X");
		string = string.replaceFirst("^y", "Y");
		string = string.replaceFirst("^z", "Z");

		return string;
	}
}

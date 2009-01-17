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

package org.ujmp.core.util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public abstract class StringUtil {

	private static final NumberFormat DefaultNF = NumberFormat.getNumberInstance(Locale.US);

	public static final String BRACKETS = "[\\[\\]\\(\\)\\{\\}]";

	public static final String SEMICOLONORNEWLINE = "\\s*[;\\n]\\s*";

	public static final String COLONORSPACES = "[,\\s*]";

	static {
		DefaultNF.setMinimumFractionDigits(4);
		DefaultNF.setMaximumFractionDigits(4);
	}

	public static final String stripTags(String string) {
		return string.replaceAll("\\<.*?>", "");
	}

	public static final String format(Object o) {
		if (o == null) {
			return "";
		}
		if (o instanceof String) {
			return (String) o;
		}
		if (o instanceof Number) {
			return format(((Number) o).doubleValue());
		}
		if (o instanceof Matrix) {
			return ((Matrix) o).stringValue();
		}
		return o.toString();
	}

	public static final String convert(Object o) {
		if (o == null) {
			return "";
		}
		if (o instanceof String) {
			return (String) o;
		}
		if (o instanceof Matrix) {
			return ((Matrix) o).stringValue();
		}
		if (o instanceof Number) {
			return "" + (((Number) o).doubleValue());
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

	public void setDefaultMaximumFractionDigits(int n) {
		DefaultNF.setMaximumFractionDigits(n);
	}

	public void setDefaultMinimumFractionDigits(int n) {
		DefaultNF.setMinimumFractionDigits(n);
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

							if (end > size[j]) {
								throw new MatrixException("Selection is bigger than size");
							}

							list.addAll(MathUtil.sequenceListLong(start, end));
						} else {
							throw new MatrixException("Selection not supported: " + dimsel);
						}

					} else {
						list.add(Long.parseLong(dimsel.replaceAll("\\D", "")));
					}
				}
				selection[i] = MathUtil.collectionToLong(list);
			}

		}

		return selection;
	}

	public static String duration(long time) {
		StringBuffer s = new StringBuffer();

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
}

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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;

public abstract class MathUtil {

	private static final Logger logger = Logger.getLogger(MathUtil.class.getName());

	private static final double ROOT2PI = Math.sqrt(2 * Math.PI);

	private static long seed = System.nanoTime();

	private static Random random = new Random();

	static {
		random.setSeed(seed);
	}

	public static String getMD5Sum(String text) {
		MessageDigest mdAlgorithm;
		StringBuilder hexString = new StringBuilder();

		try {
			mdAlgorithm = MessageDigest.getInstance("MD5");
			mdAlgorithm.update(text.getBytes());
			byte[] digest = mdAlgorithm.digest();

			for (int i = 0; i < digest.length; i++) {
				text = Integer.toHexString(0xFF & digest[i]);

				if (text.length() < 2) {
					text = "0" + text;
				}

				hexString.append(text);
			}
		} catch (NoSuchAlgorithmException e) {
			logger.log(Level.WARNING, "algorithm not found", e);
		}

		return hexString.toString();
	}

	public static final Random getRandom() {
		return random;
	}

	public static final boolean xor(boolean b1, boolean b2) {
		if (b1 == false && b2 == false) {
			return false;
		} else if (b1 == false && b2 == true) {
			return true;
		} else if (b1 == true && b2 == false) {
			return true;
		} else {
			return false;
		}
	}

	public static double[] logToProbs(double[] logs) {
		double[] probs = new double[logs.length];
		double sum = 0.0;
		for (int i = 0; i < probs.length; i++) {
			probs[i] = Math.exp(logs[i]);
			sum += probs[i];
		}
		for (int i = 0; i < probs.length; i++) {
			probs[i] = probs[i] / sum;
		}
		return probs;
	}

	public static final long getSeed() {
		return seed;
	}

	public static void setSeed(long seed) {
		MathUtil.seed = seed;
		random.setSeed(seed);
	}

	public static double log2(double d) {
		return Math.log(d) / Math.log(2.0);
	}

	public static double log10(double d) {
		return Math.log(d) / Math.log(10.0);
	}

	public static int hash(int h) {
		// This function ensures that hashCodes that differ only by
		// constant multiples at each bit position have a bounded
		// number of collisions (approximately 8 at default load factor).
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	public static final double gauss(double mean, double sigma, double x) {
		return Math.exp(-0.5 * Math.pow((x - mean) / sigma, 2.0)) / (sigma * ROOT2PI);
	}

	public static final double artanh(double x) {
		return 0.5 * Math.log((1 + x) / (1 - x));
	}

	public static final double nextGaussian(double mean, double sigma) {
		return sigma <= 0.0 ? 0.0 : sigma * random.nextGaussian() + mean;
	}

	public static final double nextUniform(double min, double max) {
		if (min == max) {
			max += Double.MIN_VALUE;
		}
		double r = random.nextDouble();
		while (r <= 0.0) {
			r = random.nextDouble();
		}

		return min + r * (max - min);
	}

	/**
	 * Returns a random value in the desired interval
	 * 
	 * @param min
	 *            minimum value (inclusive)
	 * @param max
	 *            maximum value (inclusive)
	 * @return a random value
	 */
	public static final int nextInteger(int min, int max) {
		if (min == max) {
			return min;
		}
		double r = random.nextDouble();
		return (int) ((r * max) + ((1.0 - r) * min) + r);
	}

	public static boolean isEventHappening(double probability) {
		return nextUniform(0.0, 1.0) < probability;
	}

	public static boolean nextBoolean() {
		return nextGaussian(0.0, 1.0) > 0;
	}

	public static double nextDouble() {
		return random.nextDouble();
	}

	public static double ignoreNaN(double v) {
		return (v != v) || (v == Double.POSITIVE_INFINITY) || (v == Double.NEGATIVE_INFINITY) ? 0.0
				: v;
	}

	public static boolean isNaNOrInfinite(double v) {
		return (v != v) || (v == Double.POSITIVE_INFINITY) || (v == Double.NEGATIVE_INFINITY);
	}

	public static boolean isNaNOrInfinite(Object o) {
		return Double.valueOf(Double.NaN).equals(o)
				|| Double.valueOf(Double.POSITIVE_INFINITY).equals(o)
				|| Double.valueOf(Double.NEGATIVE_INFINITY).equals(o);
	}

	public static final Matrix getMatrix(Object o) {
		if (o == null) {
			return MatrixFactory.EMPTYMATRIX;
		}
		if (o instanceof Matrix) {
			return (Matrix) o;
		} else {
			return MatrixFactory.linkToValue(o);
		}
	}

	public static final double getDouble(Object o) {
		if (o == null) {
			return 0.0;
		} else if (o instanceof Double) {
			return (Double) o;
		} else if (o instanceof Date) {
			return ((Date) o).getTime();
		} else if (o instanceof Matrix) {
			return ((Matrix) o).doubleValue();
		} else {
			try {
				return Double.parseDouble(o.toString());
			} catch (Exception e) {
			}
		}
		return Double.NaN;
	}

	public static long[] collectionToLong(Collection<? extends Number> numbers) {
		long[] ret = new long[numbers.size()];
		int i = 0;
		for (Number n : numbers) {
			ret[i++] = n.longValue();
		}
		return ret;
	}

	public static double[] collectionToDouble(Collection<? extends Number> numbers) {
		double[] ret = new double[numbers.size()];
		int i = 0;
		for (Number n : numbers) {
			ret[i++] = n.doubleValue();
		}
		return ret;
	}

	public static int[] collectionToInt(Collection<? extends Number> numbers) {
		int[] ret = new int[numbers.size()];
		int i = 0;
		for (Number n : numbers) {
			ret[i++] = n.intValue();
		}
		return ret;
	}

	public static List<Long> toLongList(long[] numbers) {
		List<Long> ret = new ArrayList<Long>(numbers.length);
		for (int i = 0; i < numbers.length; i++) {
			ret.add(numbers[i]);
		}
		return ret;
	}

	public static List<Long> toLongList(int[] numbers) {
		List<Long> ret = new ArrayList<Long>(numbers.length);
		for (int i = 0; i < numbers.length; i++) {
			ret.add((long) numbers[i]);
		}
		return ret;
	}

	public static List<Double> toDoubleList(double[] numbers) {
		List<Double> ret = new ArrayList<Double>(numbers.length);
		for (int i = 0; i < numbers.length; i++) {
			ret.add(numbers[i]);
		}
		return ret;
	}

	public static List<Double> toDoubleList(int[] numbers) {
		List<Double> ret = new ArrayList<Double>(numbers.length);
		for (int i = 0; i < numbers.length; i++) {
			ret.add((double) numbers[i]);
		}
		return ret;
	}

	public static List<Double> toDoubleList(long[] numbers) {
		List<Double> ret = new ArrayList<Double>(numbers.length);
		for (int i = 0; i < numbers.length; i++) {
			ret.add((double) numbers[i]);
		}
		return ret;
	}

	public static double[] toDoubleArray(int... intArray) {
		int nmb = intArray.length;
		double[] ret = new double[nmb];
		for (int i = 0; i < nmb; i++)
			ret[i] = intArray[i];
		return ret;
	}

	public static double[][] toDoubleArray(int[]... intArray) {
		int rows = intArray.length;
		if (rows <= 0)
			return new double[0][0];
		int cols = intArray[0].length;
		double[][] ret = new double[rows][cols];
		for (int i = rows - 1; i >= 0; i--) {
			for (int j = cols - 1; j >= 0; j--) {
				ret[i][j] = intArray[i][j];
			}
		}
		return ret;
	}

	public static List<Long> sequenceListLong(long start, long end) {
		List<Long> list = new ArrayList<Long>();

		if (start < end) {
			for (long l = start; l <= end; l++) {
				list.add(l);
			}
		} else {
			for (long l = start; l >= end; l--) {
				list.add(l);
			}
		}
		return list;
	}

	public static List<Double> sequenceListDouble(double start, double end) {
		List<Double> list = new ArrayList<Double>();

		if (start < end) {
			for (double l = start; l <= end; l++) {
				list.add(l);
			}
		} else {
			for (double l = start; l >= end; l--) {
				list.add(l);
			}
		}
		return list;
	}

	public static List<Integer> sequenceListInt(int start, int end) {
		List<Integer> list = new ArrayList<Integer>();

		if (start < end) {
			for (int l = start; l <= end; l++) {
				list.add(l);
			}
		} else {
			for (int l = start; l >= end; l--) {
				list.add(l);
			}
		}
		return list;
	}

	public static long[] sequenceLong(long start, long end) {
		return collectionToLong(sequenceListLong(start, end));
	}

	public static double[] sequenceDouble(double start, double end) {
		return collectionToDouble(sequenceListDouble(start, end));
	}

	public static int[] sequenceInt(int start, int end) {
		return collectionToInt(sequenceListInt(start, end));
	}

	public static List<Long> randPerm(long start, long end) {
		List<Long> list = sequenceListLong(start, end);
		Collections.shuffle(list);
		return list;
	}

	public static boolean equals(Object o1, Object o2) {
		if ((o1 instanceof Number) || (o2 instanceof Number)) {
			return getDouble(o1) == getDouble(o2);
		} else if ((o1 instanceof String) || (o2 instanceof String)) {
			return StringUtil.format(o1).equals(StringUtil.format(o2));
		} else if ((o1 instanceof Boolean) || (o2 instanceof Boolean)) {
			return ((Boolean) o1).equals(o2);
		} else {
			return false;
		}
	}

	public static double sensitivity(double tp, double fn) {
		return tp / (tp + fn);
	}

	public static double specificity(double tn, double fp) {
		return tn / (tn + fp);
	}

	public static double positivePredictiveValue(double tp, double fp) {
		return tp / (tp + fp);
	}

	public static double negativePredictiveValue(double tn, double fn) {
		return tn / (tn + fn);
	}

	public static double falsePositiveRate(double fp, double tn) {
		return fp / (fp + tn);
	}

	public static double falseNegativeRate(double fn, double tp) {
		return fn / (fn + tp);
	}

	public static double recall(double tp, double fn) {
		return tp / (tp + fn);
	}

	public static double precision(double tp, double fp) {
		return tp / (tp + fp);
	}

	public static double fallout(double tn, double fp) {
		return tn / (fp + tn);
	}

	public static double f1Measure(double precision, double recall) {
		return (2.0 * precision * recall) / (precision + recall);
	}

	public static double fBetaMeasure(double beta, double precision, double recall) {
		return ((1 + beta * beta) * precision * recall) / (beta * beta * precision + recall);
	}

	public static Object getPreferredObject(Object o) {
		if (o == null) {
			return null;
		}
		if (o instanceof Number) {
			return o;
		}
		if (o instanceof String) {
			Double d = getDouble(o);
			if (isNaNOrInfinite(d)) {
				return o;
			}
			return d;
		}
		return o;
	}

	public static boolean getBoolean(Object o) {
		if (o == null) {
			return false;
		}
		if (o instanceof Boolean) {
			return (Boolean) o;
		}
		if (o instanceof Number) {
			return ((Number) o).doubleValue() != 0;
		}
		if (o instanceof Matrix) {
			return ((Matrix) o).booleanValue();
		}
		if (o instanceof String) {
			try {
				return Boolean.parseBoolean(((String) o));
			} catch (Exception e) {
			}
		}
		return false;
	}

	public static byte getByte(Object o) {
		if (o == null) {
			return 0;
		}
		if (o instanceof Byte) {
			return (Byte) o;
		}
		if (o instanceof Number) {
			return ((Number) o).byteValue();
		}
		if (o instanceof Matrix) {
			return ((Matrix) o).byteValue();
		}
		if (o instanceof String) {
			try {
				return Byte.parseByte(((String) o));
			} catch (Exception e) {
			}
		}
		return 0;
	}

	public static char getChar(Object o) {
		if (o == null) {
			return 0;
		}
		if (o instanceof Character) {
			return (Character) o;
		}
		if (o instanceof Number) {
			return (char) ((Number) o).byteValue();
		}
		if (o instanceof Matrix) {
			return ((Matrix) o).charValue();
		}
		if (o instanceof String) {
			try {
				return (char) Byte.parseByte(((String) o));
			} catch (Exception e) {
			}
		}
		return 0;
	}

	public static float getFloat(Object o) {
		if (o == null) {
			return 0;
		}
		if (o instanceof Float) {
			return (Float) o;
		}
		if (o instanceof Number) {
			return ((Number) o).floatValue();
		}
		if (o instanceof Matrix) {
			return ((Matrix) o).floatValue();
		}
		if (o instanceof String) {
			try {
				return Float.parseFloat(((String) o));
			} catch (Exception e) {
			}
		}
		return 0;
	}

	public static int getInt(Object o) {
		if (o == null) {
			return 0;
		}
		if (o instanceof Integer) {
			return (Integer) o;
		}
		if (o instanceof Number) {
			return ((Number) o).intValue();
		}
		if (o instanceof Matrix) {
			return ((Matrix) o).intValue();
		}
		if (o instanceof String) {
			try {
				return Integer.parseInt(((String) o));
			} catch (Exception e) {
			}
		}
		return 0;
	}

	public static long getLong(Object o) {
		if (o == null) {
			return 0;
		}
		if (o instanceof Long) {
			return (Long) o;
		}
		if (o instanceof Number) {
			return ((Number) o).longValue();
		}
		if (o instanceof Matrix) {
			return ((Matrix) o).longValue();
		}
		if (o instanceof String) {
			try {
				return Long.parseLong(((String) o));
			} catch (Exception e) {
			}
		}
		return 0;
	}

	public static short getShort(Object o) {
		if (o == null) {
			return 0;
		}
		if (o instanceof Short) {
			return (Short) o;
		}
		if (o instanceof Number) {
			return ((Number) o).shortValue();
		}
		if (o instanceof Matrix) {
			return ((Matrix) o).shortValue();
		}
		if (o instanceof String) {
			try {
				return Short.parseShort(((String) o));
			} catch (Exception e) {
			}
		}
		return 0;
	}

	public static int[] toIntArray(long... coordinates) {
		int[] result = new int[coordinates.length];
		for (int i = coordinates.length; --i != -1;) {
			result[i] = (int) coordinates[i];
		}
		return result;
	}

	public static long[] toLongArray(int... coordinates) {
		long[] result = new long[coordinates.length];
		for (int i = coordinates.length; --i != -1;) {
			result[i] = coordinates[i];
		}
		return result;
	}
}

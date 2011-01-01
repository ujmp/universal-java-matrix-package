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

package org.ujmp.core.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;

public abstract class MathUtil {

	private static List<DateFormat> dateFormats = null;

	private static final double ROOT2PI = Math.sqrt(2 * Math.PI);

	private static final double LOG10 = Math.log(10.0);

	private static final double LOG2 = Math.log(2.0);

	private static final double COFGAMMALN[] = { 76.18009172947146, -86.50532032941677,
			24.01409824083091, -1.231739572450155, 0.1208650973866179e-2, -0.5395239384953e-5 };

	private static long seed = System.nanoTime();

	private static Random random = new Random();

	static {
		try {
			random.setSeed(seed);
			dateFormats = new ArrayList<DateFormat>();
			dateFormats.add(DateFormat.getDateInstance(DateFormat.SHORT, Locale.US));
			dateFormats.add(DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US));
			dateFormats.add(DateFormat.getDateInstance(DateFormat.LONG, Locale.US));
			dateFormats.add(DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN));
			dateFormats.add(DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN));
			dateFormats.add(DateFormat.getDateInstance(DateFormat.LONG, Locale.GERMAN));
			dateFormats.add(DateFormat.getTimeInstance(DateFormat.SHORT, Locale.US));
			dateFormats.add(DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.US));
			dateFormats.add(DateFormat.getTimeInstance(DateFormat.LONG, Locale.US));
			dateFormats.add(DateFormat.getTimeInstance(DateFormat.SHORT, Locale.GERMAN));
			dateFormats.add(DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.GERMAN));
			dateFormats.add(DateFormat.getTimeInstance(DateFormat.LONG, Locale.GERMAN));
		} catch (Throwable t) {
		}
	}

	public static MathContext getDefaultMathContext() {
		return UJMPSettings.getDefaultMathContext();
	}

	public static String md5(String text) throws NoSuchAlgorithmException {
		return md5(text.getBytes());
	}

	public static String md5(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest mdAlgorithm;
		StringBuilder hexString = new StringBuilder();

		mdAlgorithm = MessageDigest.getInstance("MD5");
		mdAlgorithm.update(data);
		byte[] digest = mdAlgorithm.digest();

		for (int i = 0; i < digest.length; i++) {
			String text = Integer.toHexString(0xFF & digest[i]);

			if (text.length() < 2) {
				text = "0" + text;
			}

			hexString.append(text);
		}
		hexString.trimToSize();
		return hexString.toString();
	}

	public static String md5(File file) throws NoSuchAlgorithmException, IOException {
		MessageDigest mdAlgorithm;
		StringBuilder hexString = new StringBuilder();

		mdAlgorithm = MessageDigest.getInstance("MD5");
		FileInputStream fi = new FileInputStream(file);
		BufferedInputStream bi = new BufferedInputStream(fi);
		byte[] data = new byte[8192];
		for (int length = bi.read(data); length != -1; length = bi.read(data)) {
			mdAlgorithm.update(data, 0, length);
		}
		bi.close();
		fi.close();
		byte[] digest = mdAlgorithm.digest();

		for (int i = 0; i < digest.length; i++) {
			String text = Integer.toHexString(0xFF & digest[i]);

			if (text.length() < 2) {
				text = "0" + text;
			}

			hexString.append(text);
		}
		hexString.trimToSize();
		return hexString.toString();
	}

	public static String md5(Serializable o) throws NoSuchAlgorithmException, IOException {
		return md5(SerializationUtil.serialize(o));
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

	public static final double round(double value, int decimals) {
		return Math.round(value * Math.pow(10, decimals)) / Math.pow(10, decimals);

	}

	public static void setSeed(long seed) {
		MathUtil.seed = seed;
		random.setSeed(seed);
	}

	public static final double log2(final double d) {
		return Math.log(d) / LOG2;
	}

	public static final double log10(final double d) {
		return Math.log(d) / LOG10;
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

	public static final double ignoreNaN(final double v) {
		return Double.isNaN(v) || (v == Double.POSITIVE_INFINITY)
				|| (v == Double.NEGATIVE_INFINITY) ? 0.0 : v;
	}

	public static final boolean isNaNOrInfinite(final double v) {
		return Double.isNaN(v) || (v == Double.POSITIVE_INFINITY)
				|| (v == Double.NEGATIVE_INFINITY);
	}

	public static final boolean isNaNOrInfinite(final Object o) {
		return Double.valueOf(Double.NaN).equals(o)
				|| Double.valueOf(Double.POSITIVE_INFINITY).equals(o)
				|| Double.valueOf(Double.NEGATIVE_INFINITY).equals(o);
	}

	public static final Matrix getMatrix(Object o) {
		if (o == null) {
			return MatrixFactory.EMPTYMATRIX;
		}
		if (o instanceof Matrix) {
			Matrix m = (Matrix) o;
			if (m.isScalar() && m.getAsObject(0, 0) instanceof Matrix) {
				return getMatrix(m.getAsObject(0, 0));
			} else {
				return m;
			}
		} else {
			return MatrixFactory.linkToValue(o);
		}
	}

	public static final Date getDate(Object o) {
		if (o == null) {
			return null;
		}
		if (o instanceof Date) {
			return (Date) o;
		}
		if (o instanceof Long) {
			return new Date((Long) o);
		}
		if (o instanceof String) {
			for (DateFormat df : dateFormats) {
				try {
					return df.parse((String) o);
				} catch (Exception e) {
				}
			}
		}
		return new Date(getLong(o));
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
			if ("true".equalsIgnoreCase(o.toString())) {
				return 1.0;
			}
			if ("false".equalsIgnoreCase(o.toString())) {
				return 0.0;
			}
			try {
				return Double.parseDouble(o.toString());
			} catch (Exception e) {
			}
		}
		return Double.NaN;
	}

	/** sqrt(a^2 + b^2) without under/overflow. **/
	public static final double hypot(double a, double b) {
		double r;
		if (Math.abs(a) > Math.abs(b)) {
			r = b / a;
			r = Math.abs(a) * Math.sqrt(1.0 + r * r);
		} else if (b != 0) {
			r = a / b;
			r = Math.abs(b) * Math.sqrt(1.0 + r * r);
		} else {
			r = 0.0;
		}
		return r;
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
		return sequenceListLong(start, end, 1);
	}

	public static List<Long> sequenceListLong(long start, long end, long stepsize) {
		List<Long> list = new ArrayList<Long>();

		if (start < end) {
			stepsize = Math.abs(stepsize);
			for (long l = start; l <= end; l += stepsize) {
				list.add(l);
			}
		} else {
			stepsize = -Math.abs(stepsize);
			for (long l = start; l >= end; l += stepsize) {
				list.add(l);
			}
		}
		return list;
	}

	public static List<Double> sequenceListDouble(double start, double end) {
		return sequenceListDouble(start, end, 1);
	}

	public static List<Double> sequenceListDouble(double start, double end, double stepsize) {
		List<Double> list = new ArrayList<Double>();

		if (start < end) {
			stepsize = Math.abs(stepsize);
			for (double l = start; l <= end; l += stepsize) {
				list.add(l);
			}
		} else {
			stepsize = -Math.abs(stepsize);
			for (double l = start; l >= end; l += stepsize) {
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

	public static long[] sequenceLong(long start, long end, long stepsize) {
		return collectionToLong(sequenceListLong(start, end, stepsize));
	}

	public static double[] sequenceDouble(double start, double end) {
		return collectionToDouble(sequenceListDouble(start, end));
	}

	public static double[] sequenceDouble(double start, double end, double stepsize) {
		return collectionToDouble(sequenceListDouble(start, end, stepsize));
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
		if (o1 == o2) {
			return true;
		} else if ((o1 instanceof Number) || (o2 instanceof Number)) {
			return getDouble(o1) == getDouble(o2);
		} else if ((o1 instanceof String) || (o2 instanceof String)) {
			return UJMPFormat.getSingleLineInstance().format(o1).equals(
					UJMPFormat.getSingleLineInstance().format(o2));
		} else if ((o1 instanceof Boolean) || (o2 instanceof Boolean)) {
			return ((Boolean) o1).equals(o2);
		} else {
			return false;
		}
	}

	public static double sensitivity(double tp, double fn) {
		double r = tp / (tp + fn);
		return MathUtil.isNaNOrInfinite(r) ? 0.0 : r;
	}

	public static double specificity(double tn, double fp) {
		double r = tn / (tn + fp);
		return MathUtil.isNaNOrInfinite(r) ? 0.0 : r;
	}

	public static double positivePredictiveValue(double tp, double fp) {
		double r = tp / (tp + fp);
		return MathUtil.isNaNOrInfinite(r) ? 0.0 : r;
	}

	public static double negativePredictiveValue(double tn, double fn) {
		double r = tn / (tn + fn);
		return MathUtil.isNaNOrInfinite(r) ? 0.0 : r;
	}

	public static double falsePositiveRate(double fp, double tn) {
		double r = fp / (fp + tn);
		return MathUtil.isNaNOrInfinite(r) ? 0.0 : r;
	}

	public static double falseNegativeRate(double fn, double tp) {
		double r = fn / (fn + tp);
		return MathUtil.isNaNOrInfinite(r) ? 0.0 : r;
	}

	public static double recall(double tp, double fn) {
		double r = tp / (tp + fn);
		return MathUtil.isNaNOrInfinite(r) ? 0.0 : r;
	}

	public static double precision(double tp, double fp) {
		double r = tp / (tp + fp);
		return MathUtil.isNaNOrInfinite(r) ? 0.0 : r;
	}

	public static double fallout(double tn, double fp) {
		double r = tn / (fp + tn);
		return MathUtil.isNaNOrInfinite(r) ? 0.0 : r;
	}

	public static double trueNegativeRate(double tn, double fp) {
		double r = tn / (fp + tn);
		return MathUtil.isNaNOrInfinite(r) ? 0.0 : r;
	}

	public static double f1Measure(double precision, double recall) {
		double r = (2.0 * precision * recall) / (precision + recall);
		return MathUtil.isNaNOrInfinite(r) ? 0.0 : r;
	}

	public static double fBetaMeasure(double beta, double precision, double recall) {
		double r = ((1 + beta * beta) * precision * recall) / (beta * beta * precision + recall);
		return MathUtil.isNaNOrInfinite(r) ? 0.0 : r;
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

	public static BigInteger getBigInteger(Object o) {
		if (o == null) {
			return BigInteger.ZERO;
		}
		if (o instanceof BigInteger) {
			return (BigInteger) o;
		}
		if (o instanceof Number) {
			if (MathUtil.isNaNOrInfinite(o)) {
				throw new IllegalArgumentException("NaN, Inf and -Inf not allowed for BigInteger");
			} else {
				return BigInteger.valueOf(((Number) o).longValue());
			}
		}
		if (o instanceof Matrix) {
			return ((Matrix) o).bigIntegerValue();
		}
		if (o instanceof String) {
			return new BigInteger((String) o);
		}
		return BigInteger.ZERO;
	}

	public static BigDecimal getBigDecimal(Object o) {
		if (o == null) {
			return BigDecimal.ZERO;
		}
		if (o instanceof BigDecimal) {
			return (BigDecimal) o;
		}
		if (o instanceof Number) {
			double val = ((Number) o).doubleValue();
			if (MathUtil.isNaNOrInfinite(val)) {
				return null;
			} else if (val == 0) {
				return BigDecimal.ZERO;
			} else if (val == 1) {
				return BigDecimal.ONE;
			} else if (val == 10) {
				return BigDecimal.TEN;
			} else {
				return BigDecimal.valueOf(val);
			}
		}
		if (o instanceof Matrix) {
			return ((Matrix) o).bigDecimalValue();
		}
		if (o instanceof String) {
			try {
				return new BigDecimal((String) o);
			} catch (Exception e) {
				return BigDecimal.ZERO;
			}
		}
		return BigDecimal.ZERO;
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

	public static Object getObject(Object o) {
		if (o == null) {
			return null;
		}
		if (o instanceof Matrix) {
			Matrix m = (Matrix) o;
			if (m.getValueCount() == 1) {
				return m.getAsObject(0, 0);
			}
		}
		return o;
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

	public static boolean isNull(Object value) {
		if (value == null) {
			return true;
		}
		if (value instanceof Number) {
			if (((Number) value).doubleValue() == 0.0) {
				return true;
			}
		}
		return false;
	}

	public static double norminv(double p, double mu, double sigma) {
		if (sigma <= 0) {
			return Double.NaN;
		}

		if (MathUtil.isNaNOrInfinite(p)) {
			return Double.NaN;
		}

		if (p == 1) {
			return Double.POSITIVE_INFINITY;
		}

		if (p == 0) {
			return Double.NEGATIVE_INFINITY;
		}

		if (p < 0 || p > 1) {
			return Double.NaN;
		}

		double r, val;
		double p_ = p;
		double q = p_ - 0.5;

		if (Math.abs(q) <= .425) {
			r = .180625 - q * q;
			val = q
					* (((((((r * 2509.0809287301226727 + 33430.575583588128105) * r + 67265.770927008700853)
							* r + 45921.953931549871457)
							* r + 13731.693765509461125)
							* r + 1971.5909503065514427)
							* r + 133.14166789178437745)
							* r + 3.387132872796366608)
					/ (((((((r * 5226.495278852854561 + 28729.085735721942674) * r + 39307.89580009271061)
							* r + 21213.794301586595867)
							* r + 5394.1960214247511077)
							* r + 687.1870074920579083)
							* r + 42.313330701600911252)
							* r + 1.);
		} else {
			if (q > 0) {
				r = 1 - p;
			} else {
				r = p_;
			}

			r = Math.sqrt(-Math.log(r));

			if (r <= 5.) {
				r += -1.6;
				val = (((((((r * 7.7454501427834140764e-4 + 0.0227238449892691845833) * r + 0.24178072517745061177)
						* r + 1.27045825245236838258)
						* r + 3.64784832476320460504)
						* r + 5.7694972214606914055)
						* r + 4.6303378461565452959)
						* r + 1.42343711074968357734)
						/ (((((((r * 1.05075007164441684324e-9 + 5.475938084995344946e-4) * r + 0.0151986665636164571966)
								* r + 0.14810397642748007459)
								* r + 0.68976733498510000455)
								* r + 1.6763848301838038494)
								* r + 2.05319162663775882187)
								* r + 1.0);
			} else {
				r += -5.;
				val = (((((((r * 2.01033439929228813265e-7 + 2.71155556874348757815e-5) * r + 0.0012426609473880784386)
						* r + 0.026532189526576123093)
						* r + 0.29656057182850489123)
						* r + 1.7848265399172913358)
						* r + 5.4637849111641143699)
						* r + 6.6579046435011037772)
						/ (((((((r * 2.04426310338993978564e-15 + 1.4215117583164458887e-7) * r + 1.8463183175100546818e-5)
								* r + 7.868691311456132591e-4)
								* r + 0.0148753612908506148525)
								* r + 0.13692988092273580531)
								* r + 0.59983220655588793769)
								* r + 1.0);
			}

			if (q < 0.0) {
				val = -val;
			}

		}
		return mu + sigma * val;
	}

	public static double f1Measure(double tp, double fp, double fn) {
		double precision = precision(tp, fp);
		double recall = recall(tp, fn);
		return f1Measure(precision, recall);
	}

	public static final long factorial(int n) {
		long r = 1;
		for (int i = 2; i <= n; i++) {
			r *= i;
		}
		return r;
	}

	public static final BigInteger factorialBig(int n) {
		BigInteger r = BigInteger.ONE;
		for (int i = 2; i <= n; i++) {
			r = r.multiply(BigInteger.valueOf(i));
		}
		return r;
	}

	public static final long binomialCoefficient(int n, int k) {
		if (k > n || k < 0) {
			return 0;
		} else {
			return factorial(n) / factorial(k) / factorial(n - k);
		}
	}

	public static final BigInteger binomialCoefficientBig(int n, int k) {
		if (k > n || k < 0) {
			return BigInteger.ZERO;
		} else {
			return factorialBig(n).divide(factorialBig(k)).divide(factorialBig(n - k));
		}
	}

	public static final boolean greater(BigInteger i1, BigInteger i2) {
		return i1.subtract(i2).signum() > 0;
	}

	public static final boolean smaller(BigInteger i1, BigInteger i2) {
		return i1.subtract(i2).signum() < 0;
	}

	public static int max(int[] values) {
		int max = -Integer.MAX_VALUE;
		for (int i = values.length - 1; i != -1; i--) {
			max = values[i] > max ? values[i] : max;
		}
		return max;
	}

	public static int min(int[] values) {
		int min = Integer.MAX_VALUE;
		for (int i = values.length - 1; i != -1; i--) {
			min = values[i] < min ? values[i] : min;
		}
		return min;
	}

	public static final BigDecimal plus(BigDecimal v1, BigDecimal v2) {
		if (v1 != null && v2 != null) {
			return v1.add(v2, getDefaultMathContext());
		} else {
			return null;
		}
	}

	public static final BigDecimal minus(BigDecimal v1, BigDecimal v2) {
		if (v1 != null && v2 != null) {
			return v1.subtract(v2, getDefaultMathContext());
		} else {
			return null;
		}
	}

	public static final BigDecimal times(BigDecimal v1, BigDecimal v2) {
		if (v1 != null && v2 != null) {
			return v1.multiply(v2, getDefaultMathContext());
		} else {
			return null;
		}
	}

	public static final BigDecimal divide(BigDecimal v1, BigDecimal v2) {
		if (BigDecimal.ZERO.equals(v2)) {
			return null;
		} else if (v1 != null && v2 != null) {
			return v1.divide(v2, getDefaultMathContext());
		} else {
			return null;
		}
	}

	public static boolean isGreater(BigDecimal v1, BigDecimal v2) {
		return v1.compareTo(v2) > 0;
	}

	public static boolean isSmaller(BigDecimal v1, BigDecimal v2) {
		return v1.compareTo(v2) < 0;
	}

	public static boolean isEqual(BigDecimal v1, BigDecimal v2) {
		return v1.compareTo(v2) == 0;
	}

	// Compute square root of large numbers using Heron's method
	// adapted from http://www.merriampark.com/bigsqrt.htm
	public static final BigDecimal sqrt(BigDecimal n) {
		if (n.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException();
		}

		final BigDecimal TWO = new BigDecimal("2");
		BigDecimal error;
		BigDecimal lastGuess = BigDecimal.ZERO;
		BigDecimal guess = BigDecimal.ONE.movePointRight(n.toBigInteger().toString().length() / 2);
		int maxIterations = 50;
		int iterations = 0;
		boolean more = true;
		while (more) {
			lastGuess = guess;
			guess = n.divide(guess, UJMPSettings.getDefaultMathContext());
			guess = guess.add(lastGuess);
			guess = guess.divide(TWO, UJMPSettings.getDefaultMathContext());
			error = n.subtract(guess.multiply(guess));
			if (++iterations >= maxIterations) {
				more = false;
			} else if (lastGuess.equals(guess)) {
				more = error.abs().compareTo(BigDecimal.ONE) >= 0;
			}
		}
		return guess;
	}

	/**
	 * from numerical recipes in c (p. 214)
	 */
	public static final double gammaln(double x) {
		double ser = 1.00000000090015;
		double y = x;
		double tmp = x + 5.5;
		tmp -= (x + 0.5) * Math.log(tmp);
		for (int j = 0; j < +5; j++) {
			ser += COFGAMMALN[j] / ++y;
		}
		return -tmp + Math.log(ser * 2.5066282751072975 / x);
	}

	public static final long pos2IndexRowMajor(long[] size, long[] pos) {
		long sum = 0;
		long prod = 1;
		final int length = pos.length;
		for (int k = length - 1; k >= 0; k--) {
			sum += prod * pos[k];
			prod *= size[k];
		}
		return sum;
	}

	public static final long pos2IndexColumnMajor(long[] size, long[] pos) {
		long sum = 0;
		long prod = 1;
		final int length = pos.length;
		for (int k = 0; k < length; k++) {
			sum += prod * pos[k];
			prod *= size[k];
		}
		return sum;
	}

	public static final long[] index2PosRowMajor(long[] size, long index) {
		final long[] pos = new long[size.length];
		long res = index;
		for (int k = size.length; --k != -1;) {
			pos[k] = res % size[k];
			res /= size[k];
		}
		return pos;
	}

	public static final long[] index2PosColumnMajor(long[] size, long index) {
		final int length = size.length;
		final long[] pos = new long[length];
		long res = index;
		for (int k = 0; k < length; k++) {
			pos[k] = res % size[k];
			res /= size[k];
		}
		return pos;
	}

	public static final long totalCorrect(Matrix confusionMatrix) {
		VerifyUtil.assertSquare(confusionMatrix);
		return (long) confusionMatrix.trace();
	}

	public static final double accuracy(Matrix confusionMatrix) {
		VerifyUtil.assertSquare(confusionMatrix);
		return totalCorrect(confusionMatrix) / confusionMatrix.getValueSum();
	}

	public static double precisionMacro(Matrix confusionMatrix) {
		VerifyUtil.assertSquare(confusionMatrix);
		double sum = 0;
		for (int catIndex = 0; catIndex < confusionMatrix.getRowCount(); catIndex++) {
			sum += precision(confusionMatrix, catIndex);
		}
		return sum / confusionMatrix.getRowCount();
	}

	public static double recallMacro(Matrix confusionMatrix) {
		VerifyUtil.assertSquare(confusionMatrix);
		double sum = 0;
		for (int catIndex = 0; catIndex < confusionMatrix.getRowCount(); catIndex++) {
			sum += recall(confusionMatrix, catIndex);
		}
		return sum / confusionMatrix.getRowCount();
	}

	public static double f1MeasureMacro(Matrix confusionMatrix) {
		VerifyUtil.assertSquare(confusionMatrix);
		double sum = 0;
		for (int catIndex = 0; catIndex < confusionMatrix.getRowCount(); catIndex++) {
			sum += f1Measure(confusionMatrix, catIndex);
		}
		return sum / confusionMatrix.getRowCount();
	}

	public static double precision(Matrix confusionMatrix, long catIndex) {
		VerifyUtil.assertSquare(confusionMatrix);
		double tp = confusionMatrix.getAsDouble(catIndex, catIndex);
		double fp = 0;
		for (int c = 0; c < confusionMatrix.getRowCount(); c++) {
			if (c == catIndex) {
				continue;
			}
			fp += confusionMatrix.getAsDouble(catIndex, c);
		}
		return precision(tp, fp);
	}

	public static double recall(Matrix confusionMatrix, long catIndex) {
		VerifyUtil.assertSquare(confusionMatrix);
		double tp = confusionMatrix.getAsDouble(catIndex, catIndex);
		double fn = 0;
		for (int r = 0; r < confusionMatrix.getRowCount(); r++) {
			if (r == catIndex) {
				continue;
			}
			fn += confusionMatrix.getAsDouble(r, catIndex);
		}
		return recall(tp, fn);
	}

	public static double f1Measure(Matrix confusionMatrix, long catIndex) {
		VerifyUtil.assertSquare(confusionMatrix);
		return f1Measure(precision(confusionMatrix, catIndex), recall(confusionMatrix, catIndex));
	}

	public static double precisionMicro(Matrix confusionMatrix) {
		double tp = 0;
		double fp = 0;
		for (int catIndex = 0; catIndex < confusionMatrix.getRowCount(); catIndex++) {
			tp += confusionMatrix.getAsDouble(catIndex, catIndex);
			for (int c = 0; c < confusionMatrix.getRowCount(); c++) {
				if (c == catIndex) {
					continue;
				}
				fp += confusionMatrix.getAsDouble(catIndex, c);
			}
		}
		return precision(tp, fp);
	}

	public static double recallMicro(Matrix confusionMatrix) {
		double tp = 0;
		double fn = 0;
		for (int catIndex = 0; catIndex < confusionMatrix.getRowCount(); catIndex++) {
			tp += confusionMatrix.getAsDouble(catIndex, catIndex);
			for (int r = 0; r < confusionMatrix.getRowCount(); r++) {
				if (r == catIndex) {
					continue;
				}
				fn += confusionMatrix.getAsDouble(r, catIndex);
			}
		}
		return recall(tp, fn);
	}

	public static double f1MeasureMicro(Matrix confusionMatrix) {
		double tp = 0;
		double fn = 0;
		double fp = 0;
		for (int catIndex = 0; catIndex < confusionMatrix.getRowCount(); catIndex++) {
			tp += confusionMatrix.getAsDouble(catIndex, catIndex);
			for (int r = 0; r < confusionMatrix.getRowCount(); r++) {
				if (r == catIndex) {
					continue;
				}
				fn += confusionMatrix.getAsDouble(r, catIndex);
			}
			for (int c = 0; c < confusionMatrix.getRowCount(); c++) {
				if (c == catIndex) {
					continue;
				}
				fp += confusionMatrix.getAsDouble(catIndex, c);
			}
		}
		return f1Measure(tp, fp, fn);
	}

}

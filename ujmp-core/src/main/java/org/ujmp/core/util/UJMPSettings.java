/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

import java.math.MathContext;
import java.util.Locale;

public abstract class UJMPSettings {

	private static double tolerance = 1.0e-12;

	private static int numberOfThreads = 1;

	private static boolean useJBlas = true;

	private static boolean useOjalgo = true;

	private static boolean useEJML = true;

	private static boolean useParallelColt = true;

	private static boolean useMTJ = true;

	private static boolean useCommonsMath = true;

	private static boolean useBlockMatrixMultiply = true;

	private static int defaultBlockSize = 100;

	private static MathContext mathContext = MathContext.DECIMAL128;

	public static MathContext getDefaultMathContext() {
		return mathContext;
	}

	public static void setDefaultMathContext(MathContext mc) {
		mathContext = mc;
	}

	/**
	 * How many rows should be returned maximally for <code>toString()</code> If
	 * the <code>Matrix</code> is bigger, three dots (<code>...</code>) will be
	 * returned for the remaining rows.
	 * 
	 * 
	 * @default 1000
	 */
	private static long maxRowsToPrint = 100;

	/**
	 * How many columns should be returned maximally for <code>toString()</code>
	 * . If the <code>Matrix</code> is bigger, three dots (<code>...</code>)
	 * will be returned for the remaining columns.
	 * 
	 * 
	 * @default 1000
	 */
	private static long maxColumnsToPrint = 100;

	/**
	 * How many rows should be returned maximally for
	 * <code>getToolTipText()</code>. If the <code>Matrix</code> is bigger,
	 * three dots (<code>...</code>) will be returned for the remaining rows.
	 * 
	 * 
	 * @default 10
	 */
	private static long maxToolTipRows = 10;

	/**
	 * How many columns should be returned maximally for
	 * <code>getToolTipText()</code>. If the <code>Matrix</code> is bigger,
	 * three dots (<code>...</code>) will be returned for the remaining columns.
	 * 
	 * @default 10
	 */
	private static long maxToolTipCols = 10;

	static {
		// Set the number of threads to use for expensive calculations. If the
		// machine has only one cpu, only one thread is used. For more than one
		// core, the number of threads is higher.
		try {
			numberOfThreads = Runtime.getRuntime().availableProcessors();
		} catch (Throwable e) {
		}

		try {
			setLocale(Locale.US);
		} catch (Throwable e) {
		}
	}

	public static void initialize() {
		try {
			System.setProperty("file.encoding", "UTF-8");
			System.setProperty("sun.jnu.encoding", "UTF-8");
		} catch (Throwable e) {
		}
	}

	public static int getNumberOfThreads() {
		return numberOfThreads;
	}

	public static void setNumberOfThreads(int numberOfThreads) {
		UJMPSettings.numberOfThreads = numberOfThreads;
	}

	public static double getTolerance() {
		return tolerance;
	}

	public static void setTolerance(double tolerance) {
		UJMPSettings.tolerance = tolerance;
	}

	public static long getMaxColumnsToPrint() {
		return maxColumnsToPrint;
	}

	public static void setMaxColumnsToPrint(long maxColumnsToPrint) {
		UJMPSettings.maxColumnsToPrint = maxColumnsToPrint;
	}

	public static long getMaxRowsToPrint() {
		return maxRowsToPrint;
	}

	public static void setMaxRowsToPrint(long maxRowsToPrint) {
		UJMPSettings.maxRowsToPrint = maxRowsToPrint;
	}

	public static long getMaxToolTipCols() {
		return maxToolTipCols;
	}

	public static void setMaxToolTipCols(long maxToolTipCols) {
		UJMPSettings.maxToolTipCols = maxToolTipCols;
	}

	public static long getMaxToolTipRows() {
		return maxToolTipRows;
	}

	public static void setMaxToolTipRows(long maxToolTipRows) {
		UJMPSettings.maxToolTipRows = maxToolTipRows;
	}

	public static Locale getLocale() {
		return Locale.getDefault();
	}

	public static void setLocale(Locale locale) {
		Locale.setDefault(locale);
	}

	public static void setUseCommonsMath(boolean useCommonsMath) {
		UJMPSettings.useCommonsMath = useCommonsMath;
	}

	public static boolean isUseCommonsMath() {
		return useCommonsMath;
	}

	/**
	 * @param useJBlas
	 *            the useJBlas to set
	 */
	public static void setUseJBlas(boolean useJBlas) {
		UJMPSettings.useJBlas = useJBlas;
	}

	/**
	 * @return useJBlas
	 */
	public static boolean isUseJBlas() {
		return useJBlas;
	}

	/**
	 * @param useOjalgo
	 *            the useOjalgo to set
	 */
	public static void setUseOjalgo(boolean useOjalgo) {
		UJMPSettings.useOjalgo = useOjalgo;
	}

	/**
	 * @return useOjalgo
	 */
	public static boolean isUseOjalgo() {
		return useOjalgo;
	}

	/**
	 * @param useEJML
	 *            the useEJML to set
	 */
	public static void setUseEJML(boolean useEJML) {
		UJMPSettings.useEJML = useEJML;
	}

	/**
	 * @return useEJML
	 */
	public static boolean isUseEJML() {
		return useEJML;
	}

	/**
	 * @param useParallelColt
	 *            the useParallelColt to set
	 */
	public static void setUseParallelColt(boolean useParallelColt) {
		UJMPSettings.useParallelColt = useParallelColt;
	}

	/**
	 * @return useParallelColt
	 */
	public static boolean isUseParallelColt() {
		return useParallelColt;
	}

	/**
	 * @param useMTJ
	 *            the useMTJ to set
	 */
	public static void setUseMTJ(boolean useMTJ) {
		UJMPSettings.useMTJ = useMTJ;
	}

	/**
	 * @return useMTJ
	 */
	public static boolean isUseMTJ() {
		return useMTJ;
	}

	public static boolean isUseBlockMatrixMultiply() {
		return useBlockMatrixMultiply;
	}

	public static void setUseBlockMatrixMultiply(boolean useBlockMatrix) {
		UJMPSettings.useBlockMatrixMultiply = useBlockMatrix;
	}

	public static int getDefaultBlockSize() {
		return defaultBlockSize;
	}

	public static void setDefaultBlockSize(int defaultBlockSize) {
		UJMPSettings.defaultBlockSize = defaultBlockSize;
	}

	public static String getLineEnd() {
		return System.getProperty("line.separator");
	}

}

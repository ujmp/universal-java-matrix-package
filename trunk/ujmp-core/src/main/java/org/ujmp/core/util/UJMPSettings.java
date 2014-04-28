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

package org.ujmp.core.util;

import java.math.MathContext;
import java.util.Locale;

import org.ujmp.core.mapmatrix.DefaultMapMatrix;

public class UJMPSettings extends DefaultMapMatrix<String, Object> {
	private static final long serialVersionUID = -4677534766141735270L;

	public static final String USEBLOCKMATRIXMULTIPLY = "UseBlockMatrixMultiply";
	public static final String USEMULTITHREADEDRANDOM = "UseMultThreadedRandom";
	public static final String DEFAULTBLOCKSIZE = "DefaultBlockSize";
	public static final String MATHCONTEXT = "MathContext";
	public static final String DEFAULTTOLERANCE = "DefaultTolerance";
	public static final String NUMBEROFTHREADS = "NumberOfThreads";

	public static final String USEJBLAS = "UseJBlas";
	public static final String USEOJALGO = "UseOjalgo";
	public static final String USEEJML = "UseEJML";
	public static final String USEPARALLELCOLT = "UseParallelColt";
	public static final String USECOMMONSMATH = "UseCommonsMath";
	public static final String USEMTJ = "UseMTJ";

	public static final String MAXROWSTOPRINT = "MaxRowsToPrint";
	public static final String MAXCOLSTOPRINT = "MaxColsToPrint";
	public static final String MAXTOOLTIPROWS = "MaxTooltipRows";
	public static final String MAXTOOLTIPCOLS = "MaxTooltipCols";

	public static final String USERAGENT = "UserAgent";

	private static UJMPSettings instance = null;

	private UJMPSettings() {
		put(DEFAULTBLOCKSIZE, 100);
		put(MATHCONTEXT, MathContext.DECIMAL128);
		put(USEMULTITHREADEDRANDOM, true);
		put(USEBLOCKMATRIXMULTIPLY, true);
		put(DEFAULTTOLERANCE, 1.0e-12);

		put(USEJBLAS, true);
		put(USEEJML, true);
		put(USEOJALGO, true);
		put(USEPARALLELCOLT, true);
		put(USEMTJ, true);
		put(USECOMMONSMATH, true);

		put(MAXROWSTOPRINT, 100);
		put(MAXCOLSTOPRINT, 100);
		put(MAXTOOLTIPROWS, 10);
		put(MAXTOOLTIPCOLS, 10);

		put(USERAGENT, "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:28.0) Gecko/20100101 Firefox/28.0");

		put(NUMBEROFTHREADS, Runtime.getRuntime().availableProcessors());
		Locale.setDefault(Locale.US);
		System.setProperty("file.encoding", "UTF-8");
		System.setProperty("sun.jnu.encoding", "UTF-8");
	}

	public static final UJMPSettings getInstance() {
		if (instance == null) {
			synchronized (instance) {
				if (instance == null) {
					instance = new UJMPSettings();
				}
			}
		}
		return instance;
	}

	public MathContext getMathContext() {
		return (MathContext) get(MATHCONTEXT);
	}

	public void setMathContext(MathContext mc) {
		put(MATHCONTEXT, mc);
	}

	public int getNumberOfThreads() {
		return MathUtil.getInt(get(NUMBEROFTHREADS));
	}

	public void setNumberOfThreads(int numberOfThreads) {
		put(NUMBEROFTHREADS, numberOfThreads);
	}

	public double getTolerance() {
		return MathUtil.getDouble(get(DEFAULTTOLERANCE));
	}

	public void setTolerance(double tolerance) {
		put(DEFAULTTOLERANCE, tolerance);
	}

	public int getMaxColumnsToPrint() {
		return MathUtil.getInt(get(MAXCOLSTOPRINT));
	}

	public void setMaxColumnsToPrint(int maxColumnsToPrint) {
		put(MAXCOLSTOPRINT, maxColumnsToPrint);
	}

	public int getMaxRowsToPrint() {
		return MathUtil.getInt(get(MAXROWSTOPRINT));
	}

	public void setMaxRowsToPrint(int maxRowsToPrint) {
		put(MAXROWSTOPRINT, maxRowsToPrint);
	}

	public int getMaxToolTipCols() {
		return MathUtil.getInt(get(MAXTOOLTIPCOLS));
	}

	public void setMaxToolTipCols(int maxToolTipCols) {
		put(MAXTOOLTIPCOLS, maxToolTipCols);
	}

	public int getMaxToolTipRows() {
		return MathUtil.getInt(get(MAXTOOLTIPROWS));
	}

	public void setMaxToolTipRows(int maxToolTipRows) {
		put(MAXTOOLTIPROWS, maxToolTipRows);
	}

	public static Locale getLocale() {
		return Locale.getDefault();
	}

	public static void setLocale(Locale locale) {
		Locale.setDefault(locale);
	}

	public void setUseCommonsMath(boolean useCommonsMath) {
		put(USECOMMONSMATH, useCommonsMath);
	}

	public boolean isUseCommonsMath() {
		return MathUtil.getBoolean(get(USECOMMONSMATH));
	}

	public void setUseJBlas(boolean useJBlas) {
		put(USEJBLAS, useJBlas);
	}

	public boolean isUseJBlas() {
		return MathUtil.getBoolean(get(USEJBLAS));
	}

	public void setUseOjalgo(boolean useOjalgo) {
		put(USEOJALGO, useOjalgo);
	}

	public boolean isUseOjalgo() {
		return MathUtil.getBoolean(get(USEOJALGO));
	}

	public void setUseEJML(boolean useEJML) {
		put(USEEJML, useEJML);
	}

	public boolean isUseEJML() {
		return MathUtil.getBoolean(get(USEEJML));
	}

	public void setUseParallelColt(boolean useParallelColt) {
		put(USEPARALLELCOLT, useParallelColt);
	}

	public boolean isUseParallelColt() {
		return MathUtil.getBoolean(get(USEPARALLELCOLT));
	}

	public void setUseMTJ(boolean useMTJ) {
		put(USEMTJ, useMTJ);
	}

	public boolean isUseMTJ() {
		return MathUtil.getBoolean(get(USEMTJ));
	}

	public boolean isUseBlockMatrixMultiply() {
		return MathUtil.getBoolean(get(USEBLOCKMATRIXMULTIPLY));
	}

	public void setUseBlockMatrixMultiply(boolean useBlockMatrix) {
		put(USEBLOCKMATRIXMULTIPLY, useBlockMatrix);
	}

	public boolean isUseMultiThreadedRandom() {
		return MathUtil.getBoolean(USEMULTITHREADEDRANDOM);
	}

	public void setUseMultiThreadedRandom(boolean value) {
		put(USEMULTITHREADEDRANDOM, value);
	}

	public int getDefaultBlockSize() {
		return MathUtil.getInt(get(DEFAULTBLOCKSIZE));
	}

	public void setDefaultBlockSize(int defaultBlockSize) {
		put(DEFAULTBLOCKSIZE, defaultBlockSize);
	}

	public String getUserAgent() {
		return StringUtil.getString(get(USERAGENT));
	}

	public void setUserAgent(String value) {
		put(USERAGENT, value);
	}

}

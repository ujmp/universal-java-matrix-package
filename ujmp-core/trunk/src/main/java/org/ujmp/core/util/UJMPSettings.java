/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

import java.io.File;

import org.ujmp.core.util.io.RingBufferOutputStream;
import org.ujmp.core.util.io.TeeStream;

public abstract class UJMPSettings {

	private static double tolerance = Math.pow(10.0, -6.0);

	private static int systemOutBufferSize = 1024 * 1024;

	private static int systemErrBufferSize = 1024 * 1024;

	private static String datasetFolder = System.getProperty("user.home") + File.separator + "datasets";

	private static int numberOfThreads = 1;

	/**
	 * How many rows should be returned maximally for <code>toString()</code>
	 * If the <code>Matrix</code> is bigger, three dots (<code>...</code>)
	 * will be returned for the remaining rows.
	 * 
	 * 
	 * @default 1000
	 */
	private static long maxRowsToPrint = 1000;

	/**
	 * How many columns should be returned maximally for <code>toString()</code>.
	 * If the <code>Matrix</code> is bigger, three dots (<code>...</code>)
	 * will be returned for the remaining columns.
	 * 
	 * 
	 * @default 1000
	 */
	private static long maxColumnsToPrint = 1000;

	/**
	 * How many rows should be returned maximally for
	 * <code>getToolTipText()</code>. If the <code>Matrix</code> is bigger,
	 * three dots (<code>...</code>) will be returned for the remaining
	 * rows.
	 * 
	 * 
	 * @default 10
	 */
	private static long maxToolTipRows = 10;

	/**
	 * How many columns should be returned maximally for
	 * <code>getToolTipText()</code>. If the <code>Matrix</code> is bigger,
	 * three dots (<code>...</code>) will be returned for the remaining
	 * columns.
	 * 
	 * @default 10
	 */
	private static long maxToolTipCols = 10;

	private static RingBufferOutputStream out = null;

	private static RingBufferOutputStream err = null;

	static {
		out = new RingBufferOutputStream(systemOutBufferSize);
		err = new RingBufferOutputStream(systemErrBufferSize);

		System.setOut(new TeeStream(System.out, out));
		System.setErr(new TeeStream(System.err, err));

		// Set the number of threads to use for expensive calculations. If the
		// machine has only one cpu, only one thread is used. For more than one
		// core, the number of threads is higher.
		numberOfThreads = Runtime.getRuntime().availableProcessors();

		/**
		 * Registers handlers for the signals SIGCONT (18), SIGCHLD (17) and
		 * SIGUSR2 (12). When one of these signals is sent to the application,
		 * the graphical user interface will be displayed.
		 */
		UJMPSignalHandler.initialize();
	}

	public static void initialize() {
	}

	public static String getSystemOut() {
		return out.toString();
	}

	public static String getSystemErr() {
		return err.toString();
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

	public static String getDataSetFolder() {
		return datasetFolder;
	}
}

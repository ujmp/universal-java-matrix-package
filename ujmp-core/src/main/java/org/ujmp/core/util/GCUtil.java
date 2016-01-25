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

package org.ujmp.core.util;

public abstract class GCUtil {

	private static final Runtime runtime = Runtime.getRuntime();

	private static final long maxMemory = runtime.maxMemory();

	public static void gc() {
		try {
			long tmpIsFree = runtime.freeMemory();
			long tmpWasFree;

			do {
				tmpWasFree = tmpIsFree;
				runtime.runFinalization();
				runtime.gc();
				Thread.sleep(10);
				tmpIsFree = runtime.freeMemory();
			} while (tmpIsFree > tmpWasFree);

			runtime.runFinalization();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void purgeMemory() {
		try {
			long tmpIsFree = runtime.freeMemory();
			long tmpWasFree;
			byte[] a1 = null;
			byte[] a2 = null;
			byte[] a3 = null;
			byte[] a4 = null;
			byte[] a5 = null;
			byte[] a6 = null;
			byte[] a7 = null;
			byte[] a8 = null;
			byte[] a9 = null;
			try {
				a1 = new byte[(int) (maxMemory * 0.1)];
				a2 = new byte[(int) (maxMemory * 0.1)];
				a3 = new byte[(int) (maxMemory * 0.1)];
				a4 = new byte[(int) (maxMemory * 0.1)];
				a5 = new byte[(int) (maxMemory * 0.1)];
				a6 = new byte[(int) (maxMemory * 0.1)];
				a7 = new byte[(int) (maxMemory * 0.1)];
				a8 = new byte[(int) (maxMemory * 0.1)];
				a9 = new byte[(int) (maxMemory * 0.1)];
			} catch (Throwable t1) {
			}
			runtime.runFinalization();
			runtime.gc();
			Thread.sleep(10);
			a1 = a1 == null ? null : null;
			a2 = a2 == null ? null : null;
			a3 = a3 == null ? null : null;
			a4 = a4 == null ? null : null;
			a5 = a5 == null ? null : null;
			a6 = a6 == null ? null : null;
			a7 = a7 == null ? null : null;
			a8 = a8 == null ? null : null;
			a9 = a9 == null ? null : null;
			do {
				tmpWasFree = tmpIsFree;
				runtime.runFinalization();
				runtime.gc();
				Thread.sleep(10);
				tmpIsFree = runtime.freeMemory();
			} while (tmpIsFree > tmpWasFree);
			runtime.runFinalization();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

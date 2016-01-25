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

import java.io.PrintStream;

import org.ujmp.core.util.io.RingBufferOutputStream;
import org.ujmp.core.util.io.TeeStream;

public class ConsoleUtil {

	private static int systemOutBufferSize = 1024 * 1024;

	private static int systemErrBufferSize = 1024 * 1024;

	private static final PrintStream systemOut = System.out;

	private static final PrintStream systemErr = System.err;

	private static RingBufferOutputStream out = new RingBufferOutputStream(systemOutBufferSize);

	private static RingBufferOutputStream err = new RingBufferOutputStream(systemErrBufferSize);

	public static void startRecordSystemOut() {
		System.setOut(new TeeStream(System.out, out));
	}

	public static void startRecordSystemErr() {
		System.setErr(new TeeStream(System.err, err));
	}

	public static void stopRecordSystemOut() {
		System.setOut(systemOut);
	}

	public static void stopRecordSystemErr() {
		System.setErr(systemErr);
	}

	public static String getSystemOut() {
		return out.toString();
	}

	public static String getSystemErr() {
		return err.toString();
	}
}

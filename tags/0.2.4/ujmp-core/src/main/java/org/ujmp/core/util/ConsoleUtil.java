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

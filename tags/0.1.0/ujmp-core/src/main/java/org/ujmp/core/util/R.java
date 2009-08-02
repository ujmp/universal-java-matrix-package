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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.Matrix.Format;

public class R {

	public static final String[] SEARCH = new String[] { System.getProperty("R"), "/usr/bin/R", "/opt/R/bin/R" };

	public static final int POLLINTERVAL = 100;

	public static final int MAXPOLLS = 10;

	private BufferedReader input = null;

	private BufferedWriter output = null;

	private BufferedReader error = null;

	private Process rProcess = null;

	private boolean running = false;

	private static R r = null;

	private static String pathToR = null;

	public static synchronized R getInstance() throws Exception {
		if (r == null) {
			r = getInstance(findR());
		}
		return r;
	}

	public static synchronized R getInstance(String pathToOctave) throws Exception {
		if (r == null) {
			r = new R(pathToR);
		}
		return r;
	}

	private R(String pathToR) throws Exception {
		rProcess = Runtime.getRuntime().exec(pathToR + " --no-save --no-readline");
		output = new BufferedWriter(new OutputStreamWriter(rProcess.getOutputStream()));
		input = new BufferedReader(new InputStreamReader(rProcess.getInputStream()));
		error = new BufferedReader(new InputStreamReader(rProcess.getErrorStream()));
		String startMessage = getFromR();
		if (startMessage != null && startMessage.length() > 0) {
			running = true;
			return;
		}
		throw new Exception("could not start R");
	}

	private synchronized String getFromR() throws Exception {
		boolean lfSeen = false;
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < MAXPOLLS; i++) {
			if (!input.ready()) {
				Thread.sleep(POLLINTERVAL);
			} else {
				break;
			}
		}

		while (input.ready()) {

			char c = (char) input.read();
			sb.append(c);

			if (lfSeen) {
				if (c == '>') {
					return sb.toString();
				} else {
					lfSeen = false;
				}
			}
			if (c == '\n') {
				lfSeen = true;
			}

		}
		return sb.toString();
	}

	public String execute(String command) throws Exception {
		sendToR(command);
		String cur = "";
		String last = "";

		cur = getFromR();

		while (cur != null && cur.length() > 0) {
			last = cur;
			cur = getFromR();
		}

		return last;
	}

	public synchronized void shutdown() throws Exception {
		r = null;
		sendToR("q()");
		rProcess.waitFor();
		output.close();
		input.close();
	}

	private synchronized void sendToR(String command) throws Exception {
		if (r != null) {
			try {
				if (!command.endsWith("\n")) {
					command += "\n";
				}
				output.write(command, 0, command.length());
				output.flush();
			} catch (IOException e) {
				shutdown();
			}
		}
	}

	public void setMatrix(String label, Matrix matrix) throws Exception {
		execute(label + " <- " + matrix.exportToString(Format.R));
	}

	private static String findR() {
		if (pathToR == null) {
			File file = null;
			for (String s : SEARCH) {
				if (s != null) {
					file = new File(s);
					if (file.exists()) {
						pathToR = file.getAbsolutePath();
						return pathToR;
					}
				}
			}
		}
		return pathToR;
	}

	public Matrix getMatrix(String label) throws Exception {
		try {

			String rawRows = execute("cat(nrow(" + label + "))");
			int rows = Integer.parseInt(rawRows.split("\n")[1]);
			String rawCols = execute("cat(ncol(" + label + "))");
			int cols = Integer.parseInt(rawCols.split("\n")[1]);

			String rawText = execute("cat(" + label + ")");
			String[] rawValues = rawText.split("\n")[1].split("[\\s]+");

			Matrix matrix = MatrixFactory.zeros(rows, cols);

			int i = 0;
			for (int r = 0; r < rows; r++) {
				for (int c = 0; c < cols; c++) {
					matrix.setAsDouble(Double.parseDouble(rawValues[i++]), r, c);
				}
			}

			return matrix;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isAvailable() {
		return findR() != null;
	}

	public static String toString(String[] strings) {
		if (strings.length != 0) {
			return "," + strings[0];
		} else {
			return "";
		}
	}

	public void plot(Matrix matrix, String... format) throws Exception {
		execute("X11()");
		setMatrix("ujmpmatrix", matrix);
		execute("plot(ujmpmatrix" + toString(format) + ")");
	}

	public void pairs(Matrix matrix, String... format) throws Exception {
		execute("X11()");
		setMatrix("ujmpmatrix", matrix);
		execute("pairs(ujmpmatrix" + toString(format) + ")");
	}

	public void qqnorm(Matrix matrix, String... format) throws Exception {
		execute("X11()");
		setMatrix("ujmpmatrix", matrix);
		execute("qqnorm(ujmpmatrix" + toString(format) + ")");
	}

	public void hist(Matrix matrix, String... format) throws Exception {
		execute("X11()");
		setMatrix("ujmpmatrix", matrix);
		execute("hist(ujmpmatrix" + toString(format) + ")");
	}

	public void image(Matrix matrix, String... format) throws Exception {
		execute("X11()");
		setMatrix("ujmpmatrix", matrix);
		execute("image(ujmpmatrix" + toString(format) + ")");
	}

	public void boxplot(Matrix matrix, String... format) throws Exception {
		execute("X11()");
		setMatrix("ujmpmatrix", matrix);
		execute("boxplot(ujmpmatrix" + toString(format) + ")");
	}

	public void closeLastFigure() throws Exception {
		execute("dev.off()");
	}

}

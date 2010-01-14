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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.FileFormat;

public class Matlab {

	public static String[] SEARCH = new String[] {};

	static {
		try {
			SEARCH = new String[] { System.getProperty("Matlab"),
					System.getProperty("user.home") + "/matlab/bin/matlab", "/usr/bin/matlab",
					"/opt/matlab/bin/matlab" };
		} catch (Exception e) {
		}
	}

	public static final String MATLABPARAMETERS = "-nosplash -nojvm";

	private static String pathToMatlab = null;

	private BufferedReader input = null;

	private BufferedWriter output = null;

	private BufferedReader error = null;

	private Process matlabProcess = null;

	private boolean running = false;

	private static Matlab matlab = null;

	public static synchronized Matlab getInstance() throws Exception {
		if (matlab == null) {
			matlab = getInstance(findMatlab());
		}
		return matlab;
	}

	private static String findMatlab() {
		if (pathToMatlab == null) {
			File file = null;
			for (String s : SEARCH) {
				if (s != null) {
					file = new File(s);
					if (file.exists()) {
						pathToMatlab = file.getAbsolutePath();
						return pathToMatlab;
					}
				}
			}
		}
		return pathToMatlab;
	}

	public static synchronized Matlab getInstance(String pathToMatlab) throws Exception {
		if (matlab == null) {
			matlab = new Matlab(pathToMatlab);
		}
		return matlab;
	}

	private Matlab(String pathToMatlab) throws Exception {
		matlabProcess = Runtime.getRuntime().exec(pathToMatlab + " " + MATLABPARAMETERS);
		output = new BufferedWriter(new OutputStreamWriter(matlabProcess.getOutputStream()));
		input = new BufferedReader(new InputStreamReader(matlabProcess.getInputStream()));
		error = new BufferedReader(new InputStreamReader(matlabProcess.getErrorStream()));
		String startMessage = getFromMatlab();
		if (startMessage != null && startMessage.length() > 0) {
			running = true;
			return;
		}
		throw new Exception("could not start Matlab");
	}

	private synchronized String getFromMatlab() throws Exception {
		boolean endSeen = false;
		StringBuilder sb = new StringBuilder();

		while (true) {
			while (!input.ready()) {
				Thread.yield();
			}
			while (input.ready()) {

				char c = (char) input.read();
				sb.append(c);

				if (c == '>') {
					if (endSeen) {
						return sb.substring(0, sb.length() - 2);
					}
					endSeen = true;
				} else {
					endSeen = false;
				}
			}
		}
	}

	public String execute(String command) throws Exception {
		sendToMatlab(command);
		return getFromMatlab();
	}

	public synchronized void shutdown() throws Exception {
		sendToMatlab("exit");
		matlabProcess.waitFor();
		output.close();
		input.close();
	}

	private synchronized void sendToMatlab(String command) throws Exception {
		if (!command.endsWith("\n")) {
			command = command + "\n";
		}
		output.write(command, 0, command.length());
		output.flush();
	}

	public void setMatrix(String label, Matrix matrix) throws Exception {
		execute(label + "=" + matrix.exportToString(FileFormat.M));
	}

	public Matrix getMatrix(String label) throws Exception {
		try {
			String rawRows = execute("fprintf(1,'%d\\n',size(" + label + ",1));");
			int rows = Integer.parseInt(rawRows.trim());
			String rawCols = execute("fprintf(1,'%d\\n',size(" + label + ",2));");
			int cols = Integer.parseInt(rawCols.trim());

			String rawText = execute("fprintf(1,'%55.55f\\n'," + label + ")");
			String[] rawValues = rawText.split("\n");

			Matrix matrix = MatrixFactory.zeros(rows, cols);

			int i = 0;
			for (int c = 0; c < cols; c++) {
				for (int r = 0; r < rows; r++) {
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
		return findMatlab() != null;
	}

	public void plot(Matrix matrix, String... format) throws Exception {
		setMatrix("ujmpmatrix", matrix);
		execute("figure;");
		execute("plot(ujmpmatrix" + toString(format) + ");");
	}

	public void hist(Matrix matrix, String... format) throws Exception {
		setMatrix("ujmpmatrix", matrix);
		execute("figure;");
		execute("hist(ujmpmatrix" + toString(format) + ");");
	}

	public void surf(Matrix matrix, String... format) throws Exception {
		setMatrix("ujmpmatrix", matrix);
		execute("figure;");
		execute("surf(ujmpmatrix" + toString(format) + ");");
	}

	public void imagesc(Matrix matrix, String... format) throws Exception {
		setMatrix("ujmpmatrix", matrix);
		execute("figure;");
		execute("imagesc(ujmpmatrix" + toString(format) + ");");
	}

	public void bar(Matrix matrix, String... format) throws Exception {
		setMatrix("ujmpmatrix", matrix);
		execute("figure;");
		execute("bar(ujmpmatrix" + toString(format) + ");");
	}

	public void errorbar(Matrix x, Matrix y, Matrix e, String... format) throws Exception {
		setMatrix("ujmpmatrix_x", x);
		setMatrix("ujmpmatrix_y", y);
		setMatrix("ujmpmatrix_e", e);
		execute("figure;");
		execute("errorbar(ujmpmatrix_x,ujmpmatrix_y,ujmpmatrix_e" + toString(format) + ");");
	}

	public void barh(Matrix matrix, String... format) throws Exception {
		setMatrix("ujmpmatrix", matrix);
		execute("figure;");
		execute("barh(ujmpmatrix" + toString(format) + ");");
	}

	public void stem(Matrix matrix, String... format) throws Exception {
		setMatrix("ujmpmatrix", matrix);
		execute("figure;");
		execute("stem(ujmpmatrix" + toString(format) + ");");
	}

	public void pie(Matrix matrix, String... format) throws Exception {
		setMatrix("ujmpmatrix", matrix);
		execute("figure;");
		execute("pie(ujmpmatrix" + toString(format) + ");");
	}

	public void pie3(Matrix matrix, String... format) throws Exception {
		setMatrix("ujmpmatrix", matrix);
		execute("figure;");
		execute("pie3(ujmpmatrix" + toString(format) + ");");
	}

	public void plotmatrix(Matrix matrix, String... format) throws Exception {
		setMatrix("ujmpmatrix", matrix);
		execute("figure;");
		execute("plotmatrix(ujmpmatrix" + toString(format) + ");");
	}

	public void plot(Matrix x, Matrix y, String... format) throws Exception {
		setMatrix("ujmpmatrix_x", x);
		setMatrix("ujmpmatrix_y", y);
		execute("figure;");
		execute("plot(ujmpmatrix_x,ujmpmatrix_y" + toString(format) + ");");
	}

	public static String toString(String[] strings) {
		if (strings.length != 0) {
			return ",'" + strings[0] + "'";
		} else {
			return "";
		}
	}
}

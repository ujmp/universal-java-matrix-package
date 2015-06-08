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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.ujmp.core.Matrix;

public class Octave {

	public static String[] SEARCH = new String[] {};

	static {
		try {
			SEARCH = new String[] { System.getProperty("Octave"), "/usr/bin/octave",
					"/opt/octave/bin/octave",
					"c:/Program Files (x86)/Octave/3.2.3_gcc-4.4.0/bin/octave.exe",
					"c:/Users/arndt/Octave-4.0.0/bin/octave-cli.exe" };
		} catch (Exception e) {
		}
	}

	public static final int POLLINTERVAL = 500;

	public static final int MAXPOLLS = 10;

	private BufferedReader input = null;

	private BufferedWriter output = null;

	private BufferedReader error = null;

	private Process octaveProcess = null;

	private boolean running = false;

	private static Octave octave = null;

	private static String pathToOctave = null;

	public static synchronized Octave getInstance() throws Exception {
		if (octave == null) {
			octave = getInstance(findOctave());
		}
		return octave;
	}

	public static synchronized Octave getInstance(String pathToOctave) throws Exception {
		if (octave == null) {
			octave = new Octave(pathToOctave);
		}
		return octave;
	}

	private Octave(String pathToOctave) throws Exception {
		octaveProcess = Runtime.getRuntime().exec(pathToOctave);
		output = new BufferedWriter(new OutputStreamWriter(octaveProcess.getOutputStream()));
		input = new BufferedReader(new InputStreamReader(octaveProcess.getInputStream()));
		error = new BufferedReader(new InputStreamReader(octaveProcess.getErrorStream()));
		String startMessage = getFromOctave();
		if (startMessage != null && startMessage.length() > 0) {
			running = true;
			return;
		}
		throw new Exception("could not start Octave");
	}

	private synchronized String getFromOctave() throws Exception {
		boolean colonSeen = false;
		boolean numberSeen = false;
		StringBuilder sb = new StringBuilder();

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

			if (numberSeen) {
				if (c == '>') {
					return sb.toString();
				} else {
					colonSeen = false;
					numberSeen = false;
				}
			} else if (colonSeen) {
				if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5'
						|| c == '6' || c == '7' || c == '8' || c == '9') {
					numberSeen = true;
				} else {
					colonSeen = false;
					numberSeen = false;
				}
			} else {
				if (c == ':') {
					colonSeen = true;
				}
			}
		}
		return sb.toString();
	}

	public String execute(String command) throws Exception {
		sendToOctave(command);
		return getFromOctave();
	}

	public synchronized void shutdown() throws Exception {
		sendToOctave("exit");
		octaveProcess.waitFor();
		output.close();
		input.close();
	}

	private synchronized void sendToOctave(String command) throws Exception {
		if (!command.endsWith("\n")) {
			command = command + "\n";
		}
		output.write(command, 0, command.length());
		output.flush();
	}

	public void setMatrix(String label, Matrix matrix) throws Exception {
		execute(matrix.exportTo().string().asMatlabScript(label));
	}

	private static String findOctave() {
		if (pathToOctave == null) {
			File file = null;
			for (String s : SEARCH) {
				if (s != null) {
					file = new File(s);
					if (file.exists()) {
						pathToOctave = file.getAbsolutePath();
						return pathToOctave;
					}
				}
			}
		}
		return pathToOctave;
	}

	public Matrix getMatrix(String label) throws Exception {
		try {
			String rawRows = execute("fprintf(1,'%d\\n',size(" + label + ",1));");
			int rows = Integer.parseInt(rawRows.trim());
			String rawCols = execute("fprintf(1,'%d\\n',size(" + label + ",2));");
			int cols = Integer.parseInt(rawCols.trim());

			String rawText = execute("fprintf(1,'%55.55f\\n'," + label + ")");
			String[] rawValues = rawText.split("\n");

			Matrix matrix = Matrix.Factory.zeros(rows, cols);

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
		return findOctave() != null;
	}

	public void plot(Matrix matrix, String... format) throws Exception {
		setMatrix("ujmpmatrix", matrix);
		execute("figure;");
		execute("plot(ujmpmatrix" + toString(format) + ");");
	}

	public void loglog(Matrix matrix, String... format) throws Exception {
		setMatrix("ujmpmatrix", matrix);
		execute("figure;");
		execute("loglog(ujmpmatrix" + toString(format) + ");");
	}

	public void semilogx(Matrix matrix, String... format) throws Exception {
		setMatrix("ujmpmatrix", matrix);
		execute("figure;");
		execute("semilogx(ujmpmatrix" + toString(format) + ");");
	}

	public void semilogy(Matrix matrix, String... format) throws Exception {
		setMatrix("ujmpmatrix", matrix);
		execute("figure;");
		execute("semilogy(ujmpmatrix" + toString(format) + ");");
	}

	public void bar(Matrix matrix, String... format) throws Exception {
		setMatrix("ujmpmatrix", matrix);
		execute("figure;");
		execute("bar(ujmpmatrix" + toString(format) + ");");
	}

	public void stairs(Matrix matrix, String... format) throws Exception {
		setMatrix("ujmpmatrix", matrix);
		execute("figure;");
		execute("stairs(ujmpmatrix" + toString(format) + ");");
	}

	public void hist(Matrix matrix, String... format) throws Exception {
		setMatrix("ujmpmatrix", matrix);
		execute("figure;");
		execute("hist(ujmpmatrix" + toString(format) + ");");
	}

	public void plot(Matrix x, Matrix y, String... format) throws Exception {
		setMatrix("ujmpmatrix_x", x);
		setMatrix("ujmpmatrix_y", y);
		execute("figure;");
		execute("plot(ujmpmatrix_x,ujmpmatrix_y" + toString(format) + ");");
	}

	public void loglog(Matrix x, Matrix y, String... format) throws Exception {
		setMatrix("ujmpmatrix_x", x);
		setMatrix("ujmpmatrix_y", y);
		execute("figure;");
		execute("loglog(ujmpmatrix_x,ujmpmatrix_y" + toString(format) + ");");
	}

	public void semilogx(Matrix x, Matrix y, String... format) throws Exception {
		setMatrix("ujmpmatrix_x", x);
		setMatrix("ujmpmatrix_y", y);
		execute("figure;");
		execute("semilogx(ujmpmatrix_x,ujmpmatrix_y" + toString(format) + ");");
	}

	public void semilogy(Matrix x, Matrix y, String... format) throws Exception {
		setMatrix("ujmpmatrix_x", x);
		setMatrix("ujmpmatrix_y", y);
		execute("figure;");
		execute("semilogy(ujmpmatrix_x,ujmpmatrix_y" + toString(format) + ");");
	}

	public void bar(Matrix x, Matrix y, String... format) throws Exception {
		setMatrix("ujmpmatrix_x", x);
		setMatrix("ujmpmatrix_y", y);
		execute("figure;");
		execute("bar(ujmpmatrix_x,ujmpmatrix_y" + toString(format) + ");");
	}

	public void stairs(Matrix x, Matrix y, String... format) throws Exception {
		setMatrix("ujmpmatrix_x", x);
		setMatrix("ujmpmatrix_y", y);
		execute("figure;");
		execute("stairs(ujmpmatrix_x,ujmpmatrix_y" + toString(format) + ");");
	}

	public void hist(Matrix x, Matrix y, String... format) throws Exception {
		setMatrix("ujmpmatrix_x", x);
		setMatrix("ujmpmatrix_y", y);
		execute("figure;");
		execute("hist(ujmpmatrix_x,ujmpmatrix_y" + toString(format) + ");");
	}

	public void polar(Matrix theta, Matrix rho, String... format) throws Exception {
		setMatrix("ujmpmatrix_theta", theta);
		setMatrix("ujmpmatrix_rho", rho);
		execute("figure;");
		execute("hist(ujmpmatrix_theta,ujmpmatrix_rho" + toString(format) + ");");
	}

	public void contour(Matrix z, Matrix n, Matrix x, Matrix y, String... format) throws Exception {
		setMatrix("ujmpmatrix_z", z);
		setMatrix("ujmpmatrix_n", n);
		setMatrix("ujmpmatrix_x", x);
		setMatrix("ujmpmatrix_y", y);
		execute("figure;");
		execute("contour(ujmpmatrix_z,ujmpmatrix_n,ujmpmatrix_x,ujmpmatrix_y" + toString(format)
				+ ");");
	}

	public void mesh(Matrix x, Matrix y, Matrix z, String... format) throws Exception {
		setMatrix("ujmpmatrix_x", x);
		setMatrix("ujmpmatrix_y", y);
		setMatrix("ujmpmatrix_z", z);
		execute("figure;");
		execute("mesh(ujmpmatrix_x,ujmpmatrix_y,ujmpmatrix_z" + toString(format) + ");");
	}

	public static String toString(String[] strings) {
		if (strings.length != 0) {
			return ",'" + strings[0] + "'";
		} else {
			return "";
		}
	}

	public double getDouble(String label) throws Exception {
		Matrix m = getMatrix(label);
		VerifyUtil.verifySingleValue(m);
		return m.doubleValue();
	}

	public long getLong(String label) throws Exception {
		Matrix m = getMatrix(label);
		VerifyUtil.verifySingleValue(m);
		return m.longValue();
	}

	public int getInt(String label) throws Exception {
		Matrix m = getMatrix(label);
		VerifyUtil.verifySingleValue(m);
		return m.intValue();
	}

	public float getFloat(String label) throws Exception {
		Matrix m = getMatrix(label);
		VerifyUtil.verifySingleValue(m);
		return m.floatValue();
	}

	public void setDouble(String label, double value) throws Exception {
		setMatrix(label, Matrix.Factory.linkToValue(value));
	}

	public void setFloat(String label, float value) throws Exception {
		setMatrix(label, Matrix.Factory.linkToValue(value));
	}

	public void setInt(String label, int value) throws Exception {
		setMatrix(label, Matrix.Factory.linkToValue(value));
	}

	public void setLong(String label, long value) throws Exception {
		setMatrix(label, Matrix.Factory.linkToValue(value));
	}

}

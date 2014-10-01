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

public class GnuPlot {

	public static String[] SEARCH = new String[] {};

	static {
		try {
			SEARCH = new String[] { System.getProperty("GnuPlot"),
					System.getProperty("user.home") + "/gnuplot/bin/gnuplot", "/usr/bin/gnuplot" };
		} catch (Exception e) {
		}
	}

	private static String pathToGnuPlot = null;

	private BufferedReader input = null;

	private BufferedWriter output = null;

	private BufferedReader error = null;

	private Process gnuPlotProcess = null;

	private boolean running = false;

	private static GnuPlot gnuPlot = null;

	private static File matrixFile = null;

	public static GnuPlot getInstance() throws Exception {
		if (gnuPlot == null) {
			gnuPlot = getInstance(findGnuPlot());
		}
		return gnuPlot;
	}

	private static String findGnuPlot() {
		if (pathToGnuPlot == null) {
			File file = null;
			for (String s : SEARCH) {
				if (s != null) {
					file = new File(s);
					if (file.exists()) {
						pathToGnuPlot = file.getAbsolutePath();
						return pathToGnuPlot;
					}
				}
			}
		}
		return pathToGnuPlot;
	}

	public static synchronized GnuPlot getInstance(String pathToGnuPlot) throws Exception {
		if (gnuPlot == null) {
			gnuPlot = new GnuPlot(pathToGnuPlot);
		}
		return gnuPlot;
	}

	private GnuPlot(String pathToGnuPlot) throws Exception {
		matrixFile = new File(System.getProperty("java.io.tmpdir") + File.separator + "gnuplot.csv");
		matrixFile.deleteOnExit();
		gnuPlotProcess = Runtime.getRuntime().exec(pathToGnuPlot);
		output = new BufferedWriter(new OutputStreamWriter(gnuPlotProcess.getOutputStream()));
		input = new BufferedReader(new InputStreamReader(gnuPlotProcess.getInputStream()));
		error = new BufferedReader(new InputStreamReader(gnuPlotProcess.getErrorStream()));
		// String startMessage = getFromGnuPlot();
		// if (startMessage != null && startMessage.length() > 0) {
		running = true;
		return;
		// }
		// throw new Exception("could not start GnuPlot");
	}

	private synchronized String getFromGnuPlot() throws Exception {
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

	public void execute(String command) throws Exception {
		sendToGnuPlot(command);
		// return getFromGnuPlot();
	}

	public synchronized void shutdown() throws Exception {
		sendToGnuPlot("exit");
		gnuPlotProcess.waitFor();
		output.close();
		input.close();
	}

	private synchronized void sendToGnuPlot(String command) throws Exception {
		if (!command.endsWith("\n")) {
			command = command + "\n";
		}
		output.write(command, 0, command.length());
		output.flush();
	}

	public void setMatrix(String label, Matrix matrix) throws Exception {
		String script = matrix.exportTo().string().asMatlabScript(label);
		execute(script);
	}

	public static boolean isAvailable() {
		return findGnuPlot() != null;
	}

	public void plot(Matrix matrix, String... format) throws Exception {
		matrix.exportTo().file(matrixFile).asDenseCSV();
		execute(getPlotCommand(matrix, true, true));
	}

	public static String getPlotCommand(Matrix matrix, boolean withlines, boolean withpoints) {
		String command = "";
		command += "set autoscale; ";
		command += "unset log; ";
		command += "unset label; ";
		command += "set xtic auto; ";
		command += "set ytic auto; ";
		command += "set xlabel 'Column 0'; ";
		command += "plot";
		for (int c = 1; c < matrix.getColumnCount(); c++) {
			command += " \"" + matrixFile + "\" using 1:" + (c + 1) + " title 'Column " + c + "' ";

			if (withlines && !withpoints) {
				command += " with lines ";
			} else if (withlines && withpoints) {
				command += " with linespoints ";
			}

			if (c < matrix.getColumnCount() - 1) {
				command += ",";
			}
		}
		return command;
	}

	public void scatterPlot(Matrix matrix, String... format) throws Exception {
		matrix.exportTo().file(matrixFile).asDenseCSV();
		execute(getPlotCommand(matrix, false, false));
	}

	public static String toString(String[] strings) {
		if (strings.length != 0) {
			return ",'" + strings[0] + "'";
		} else {
			return "";
		}
	}

	public void exportToPS(File file, Object... parameters) throws Exception {
		String command = "";
		command += "set terminal postscript landscape;";
		command += "set output \"" + file + "\";";
		command += "replot;";
		if ("Linux".equals(System.getProperty("os.name"))) {
			command += "set terminal x11;";
		} else {
			command += "set terminal windows;";
		}
		execute(command);
	}

	public void exportToPNG(File file, Object... parameters) throws Exception {
		String command = "";
		command += "set terminal png;";
		command += "set output \"" + file + "\";";
		command += "replot;";
		if ("Linux".equals(System.getProperty("os.name"))) {
			command += "set terminal x11;";
		} else {
			command += "set terminal windows;";
		}
		execute(command);
	}

	public void exportToFIG(File file, Object... parameters) throws Exception {
		String command = "";
		command += "set terminal fig;";
		command += "set output \"" + file + "\";";
		command += "replot;";
		if ("Linux".equals(System.getProperty("os.name"))) {
			command += "set terminal x11;";
		} else {
			command += "set terminal windows;";
		}
		execute(command);
	}

	public void exportToSVG(File file, Object... parameters) throws Exception {
		String command = "";
		command += "set terminal svg;";
		command += "set output \"" + file + "\";";
		command += "replot;";
		if ("Linux".equals(System.getProperty("os.name"))) {
			command += "set terminal x11;";
		} else {
			command += "set terminal windows;";
		}
		execute(command);
	}
}

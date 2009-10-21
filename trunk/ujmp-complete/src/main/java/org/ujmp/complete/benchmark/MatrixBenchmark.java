/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

package org.ujmp.complete.benchmark;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.ujmp.colt.benchmark.ColtDenseDoubleMatrix2DBenchmark;
import org.ujmp.commonsmath.benchmark.CommonsMathArrayDenseDoubleMatrix2DBenchmark;
import org.ujmp.commonsmath.benchmark.CommonsMathBlockDenseDoubleMatrix2DBenchmark;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.benchmark.AbstractMatrix2DBenchmark;
import org.ujmp.core.benchmark.ArrayDenseDoubleMatrix2DBenchmark;
import org.ujmp.core.benchmark.DefaultDenseDoubleMatrix2DBenchmark;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.objectmatrix.impl.EmptyMatrix;
import org.ujmp.jama.benchmark.JamaDenseDoubleMatrix2DBenchmark;
import org.ujmp.jampack.benchmark.JampackDenseDoubleMatrix2DBenchmark;
import org.ujmp.jmatharray.benchmark.JMathArrayDenseDoubleMatrix2DBenchmark;
import org.ujmp.jmatrices.benchmark.JMatricesDenseDoubleMatrix2DBenchmark;
import org.ujmp.jsci.benchmark.JSciDenseDoubleMatrix2DBenchmark;
import org.ujmp.jscience.benchmark.JScienceDenseDoubleMatrix2DBenchmark;
import org.ujmp.mantissa.benchmark.MantissaDenseDoubleMatrix2DBenchmark;
import org.ujmp.mtj.benchmark.MTJDenseDoubleMatrix2DBenchmark;
import org.ujmp.ojalgo.benchmark.OjalgoDenseDoubleMatrix2DBenchmark;
import org.ujmp.orbital.benchmark.OrbitalDenseDoubleMatrix2DBenchmark;
import org.ujmp.owlpack.benchmark.OwlpackDenseDoubleMatrix2DBenchmark;
import org.ujmp.parallelcolt.benchmark.ParallelColtDenseDoubleMatrix2DBenchmark;
import org.ujmp.sst.benchmark.SSTDenseDoubleMatrix2DBenchmark;
import org.ujmp.vecmath.benchmark.VecMathDenseDoubleMatrix2DBenchmark;

public class MatrixBenchmark {

	public MatrixBenchmark() {
	}

	public List<AbstractMatrix2DBenchmark> getDenseBenchmarks() {
		List<AbstractMatrix2DBenchmark> list = new ArrayList<AbstractMatrix2DBenchmark>();

		if (isRunVecMathDenseDoubleMatrix2DBenchmark()) {
			list.add(new VecMathDenseDoubleMatrix2DBenchmark());
		}
		if (isRunDefaultDenseDoubleMatrix2DBenchmark()) {
			list.add(new DefaultDenseDoubleMatrix2DBenchmark());
		}
		if (isRunArrayDenseDoubleMatrix2DBenchmark()) {
			list.add(new ArrayDenseDoubleMatrix2DBenchmark());
		}
		if (isRunSSTDenseDoubleMatrix2DBenchmark()) {
			list.add(new SSTDenseDoubleMatrix2DBenchmark());
		}
		if (isRunParallelColtDenseDoubleMatrix2DBenchmark()) {
			list.add(new ParallelColtDenseDoubleMatrix2DBenchmark());
		}
		if (isRunOwlpackDenseDoubleMatrix2DBenchmark()) {
			list.add(new OwlpackDenseDoubleMatrix2DBenchmark());
		}
		if (isRunOrbitalDenseDoubleMatrix2DBenchmark()) {
			list.add(new OrbitalDenseDoubleMatrix2DBenchmark());
		}
		if (isRunOjalgoDenseDoubleMatrix2DBenchmark()) {
			list.add(new OjalgoDenseDoubleMatrix2DBenchmark());
		}
		if (isRunMTJDenseDoubleMatrix2DBenchmark()) {
			list.add(new MTJDenseDoubleMatrix2DBenchmark());
		}
		if (isRunMantissaDenseDoubleMatrix2DBenchmark()) {
			list.add(new MantissaDenseDoubleMatrix2DBenchmark());
		}
		if (isRunJScienceDenseDoubleMatrix2DBenchmark()) {
			list.add(new JScienceDenseDoubleMatrix2DBenchmark());
		}
		if (isRunJSciDenseDoubleMatrix2DBenchmark()) {
			list.add(new JSciDenseDoubleMatrix2DBenchmark());
		}
		if (isRunJMatricesDenseDoubleMatrix2DBenchmark()) {
			list.add(new JMatricesDenseDoubleMatrix2DBenchmark());
		}
		if (isRunJMathArrayDenseDoubleMatrix2DBenchmark()) {
			list.add(new JMathArrayDenseDoubleMatrix2DBenchmark());
		}
		if (isRunJampackDenseDoubleMatrix2DBenchmark()) {
			list.add(new JampackDenseDoubleMatrix2DBenchmark());
		}
		if (isRunJamaDenseDoubleMatrix2DBenchmark()) {
			list.add(new JamaDenseDoubleMatrix2DBenchmark());
		}
		if (isRunCommonsMathBlockDenseDoubleMatrix2DBenchmark()) {
			list.add(new CommonsMathBlockDenseDoubleMatrix2DBenchmark());
		}
		if (isRunCommonsMathArrayDenseDoubleMatrix2DBenchmark()) {
			list.add(new CommonsMathArrayDenseDoubleMatrix2DBenchmark());
		}
		if (isRunColtDenseDoubleMatrix2DBenchmark()) {
			list.add(new ColtDenseDoubleMatrix2DBenchmark());
		}

		return list;
	}

	public void configureDefault() throws Exception {
		setRunDefaultDenseDoubleMatrix2DBenchmark(true);
		setRunArrayDenseDoubleMatrix2DBenchmark(true);
		setRunMTJDenseDoubleMatrix2DBenchmark(true);
		setRunOjalgoDenseDoubleMatrix2DBenchmark(true);
		setRunJamaDenseDoubleMatrix2DBenchmark(true);
		setRunOrbitalDenseDoubleMatrix2DBenchmark(true);
		setRunOwlpackDenseDoubleMatrix2DBenchmark(true);
		setRunJampackDenseDoubleMatrix2DBenchmark(true);
		setRunJMathArrayDenseDoubleMatrix2DBenchmark(true);
		setRunJScienceDenseDoubleMatrix2DBenchmark(true);
		setRunJSciDenseDoubleMatrix2DBenchmark(true);
		setRunParallelColtDenseDoubleMatrix2DBenchmark(true);
		setRunColtDenseDoubleMatrix2DBenchmark(true);
		setRunSSTDenseDoubleMatrix2DBenchmark(true);
		setRunCommonsMathArrayDenseDoubleMatrix2DBenchmark(true);
		setRunCommonsMathBlockDenseDoubleMatrix2DBenchmark(true);
		setRunMantissaDenseDoubleMatrix2DBenchmark(true);
		setRunJMatricesDenseDoubleMatrix2DBenchmark(true);
		setRunVecMathDenseDoubleMatrix2DBenchmark(true);

		AbstractMatrix2DBenchmark.setSkipSlowLibraries(true);

		AbstractMatrix2DBenchmark.setBurnInRuns(1);
		AbstractMatrix2DBenchmark.setRunsPerMatrix(3);

		AbstractMatrix2DBenchmark.setRunTransposeNew(false);
		AbstractMatrix2DBenchmark.setRunMtimesNew(false);
		AbstractMatrix2DBenchmark.setRunInv(false);
		AbstractMatrix2DBenchmark.setRunSVD(false);
		AbstractMatrix2DBenchmark.setRunEVD(false);
		AbstractMatrix2DBenchmark.setRunQR(false);
		AbstractMatrix2DBenchmark.setRunLU(false);
		AbstractMatrix2DBenchmark.setRunChol(true);
	}

	public void setRunDefaultDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runDefaultDenseDoubleMatrix2DBenchmark", "" + b);
	}

	public void setRunArrayDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runArrayDenseDoubleMatrix2DBenchmark", "" + b);
	}

	public void setRunMTJDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runMTJDenseDoubleMatrix2DBenchmark", "" + b);
	}

	public void setRunOjalgoDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runOjalgoDenseDoubleMatrix2DBenchmark", "" + b);
	}

	public void setRunOrbitalDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runOrbitalDenseDoubleMatrix2DBenchmark", "" + b);
	}

	public void setRunOwlpackDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runOwlpackDenseDoubleMatrix2DBenchmark", "" + b);
	}

	public void setRunJScienceDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runJScienceDenseDoubleMatrix2DBenchmark", "" + b);
	}

	public void setRunJMathArrayDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runJMathArrayDenseDoubleMatrix2DBenchmark", "" + b);
	}

	public void setRunJSciDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runJSciDenseDoubleMatrix2DBenchmark", "" + b);
	}

	public void setRunParallelColtDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runParallelColtDenseDoubleMatrix2DBenchmark", "" + b);
	}

	public void setRunColtDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runColtDenseDoubleMatrix2DBenchmark", "" + b);
	}

	public void setRunJamaDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runJamaDenseDoubleMatrix2DBenchmark", "" + b);
	}

	public void setRunJampackDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runJampackDenseDoubleMatrix2DBenchmark", "" + b);
	}

	public void setRunMantissaDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runMantissaDenseDoubleMatrix2DBenchmark", "" + b);
	}

	public void setRunCommonsMathArrayDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runCommonsMathArrayDenseDoubleMatrix2DBenchmark", "" + b);
	}

	public void setRunCommonsMathBlockDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runCommonsMathBlockDenseDoubleMatrix2DBenchmark", "" + b);
	}

	public void setRunSSTDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runSSTDenseDoubleMatrix2DBenchmark", "" + b);
	}

	public void setRunJMatricesDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runJMatricesDenseDoubleMatrix2DBenchmark", "" + b);
	}

	public void setRunVecMathDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runVecMathDenseDoubleMatrix2DBenchmark", "" + b);
	}

	public boolean isRunDefaultDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runDefaultDenseDoubleMatrix2DBenchmark"));
	}

	public boolean isRunArrayDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runArrayDenseDoubleMatrix2DBenchmark"));
	}

	public boolean isRunMTJDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runMTJDenseDoubleMatrix2DBenchmark"));
	}

	public boolean isRunOjalgoDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runOjalgoDenseDoubleMatrix2DBenchmark"));
	}

	public boolean isRunOrbitalDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runOrbitalDenseDoubleMatrix2DBenchmark"));
	}

	public boolean isRunOwlpackDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runOwlpackDenseDoubleMatrix2DBenchmark"));
	}

	public boolean isRunJScienceDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runJScienceDenseDoubleMatrix2DBenchmark"));
	}

	public boolean isRunJSciDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runJSciDenseDoubleMatrix2DBenchmark"));
	}

	public boolean isRunJMathArrayDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runJMathArrayDenseDoubleMatrix2DBenchmark"));
	}

	public boolean isRunParallelColtDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runParallelColtDenseDoubleMatrix2DBenchmark"));
	}

	public boolean isRunColtDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runColtDenseDoubleMatrix2DBenchmark"));
	}

	public boolean isRunSSTDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runSSTDenseDoubleMatrix2DBenchmark"));
	}

	public boolean isRunCommonsMathArrayDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runCommonsMathArrayDenseDoubleMatrix2DBenchmark"));
	}

	public boolean isRunCommonsMathBlockDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runCommonsMathBlockDenseDoubleMatrix2DBenchmark"));
	}

	public boolean isRunJamaDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runJamaDenseDoubleMatrix2DBenchmark"));
	}

	public boolean isRunJampackDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runJampackDenseDoubleMatrix2DBenchmark"));
	}

	public boolean isRunJMatricesDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runJMatricesDenseDoubleMatrix2DBenchmark"));
	}

	public boolean isRunMantissaDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runMantissaDenseDoubleMatrix2DBenchmark"));
	}

	public boolean isRunVecMathDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runVecMathDenseDoubleMatrix2DBenchmark"));
	}

	public void run() throws Exception {
		List<AbstractMatrix2DBenchmark> benchmarks = getDenseBenchmarks();

		List<Matrix> results = new ArrayList<Matrix>();

		for (int j = 0; j < benchmarks.size(); j++) {
			AbstractMatrix2DBenchmark benchmark = benchmarks.get(j);
			List<Matrix> l = benchmark.run();
			if (results.isEmpty()) {
				for (int i = 0; i < l.size(); i++) {
					results.add(MatrixFactory.emptyMatrix());
				}
			}
			for (int i = 0; i < l.size(); i++) {
				Matrix m = l.get(i).appendVertically(results.get(i));
				m.setLabel(l.get(i).getLabel());
				for (int c = 0; c < l.get(i).getColumnCount(); c++) {
					m.setColumnLabel(c, l.get(i).getColumnLabel(c));
				}
				results.set(i, m);
			}
		}

		for (int i = 0; i < results.size(); i++) {
			Matrix m = results.get(i);
			for (int j = 0; j < benchmarks.size(); j++) {
				m.setRowLabel(benchmarks.size() - 1 - j, benchmarks.get(j).getClass()
						.getSimpleName());
			}
		}

		for (Matrix m : results) {
			if (m != null && !(m instanceof EmptyMatrix)) {
				try {
					String name = "results" + File.separator;
					name += InetAddress.getLocalHost().getHostName();
					name += "-" + System.getProperty("os.name");
					name += "-" + System.getProperty("java.version");
					name += "-" + m.getLabel();
					name += ".csv";
					m.exportToFile(FileFormat.CSV, new File(name));
				} catch (Exception e) {
				}
				try {
					m.showGUI();
				} catch (Exception e) {
				}
			}
		}

		System.out.println();
		System.out.println("Finished");
	}

	public static void main(String[] args) throws Exception {
		if (Runtime.getRuntime().maxMemory() < 980 * 1024 * 1024) {
			throw new Exception("You must start Java with more memory: -Xmx1024M");
		}
		MatrixBenchmark mb = new MatrixBenchmark();
		mb.configureDefault();
		mb.run();
	}
}

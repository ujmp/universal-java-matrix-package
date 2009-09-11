/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.benchmark.AbstractMatrix2DBenchmark;
import org.ujmp.core.benchmark.ArrayDenseDoubleMatrix2DBenchmark;
import org.ujmp.core.benchmark.DefaultDenseDoubleMatrix2DBenchmark;
import org.ujmp.core.objectmatrix.impl.EmptyMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.jama.benchmark.JamaDenseDoubleMatrix2DBenchmark;
import org.ujmp.mtj.benchmark.MTJDenseDoubleMatrix2DBenchmark;
import org.ujmp.ojalgo.benchmark.OjalgoDenseDoubleMatrix2DBenchmark;

public class MatrixBenchmark {

	public MatrixBenchmark() {

	}

	public List<AbstractMatrix2DBenchmark> getDenseBenchmarks() {
		List<AbstractMatrix2DBenchmark> list = new ArrayList<AbstractMatrix2DBenchmark>();
		if (isRunDefaultDenseDoubleMatrix2DBenchmark()) {
			list.add(new DefaultDenseDoubleMatrix2DBenchmark());
		}
		if (isRunArrayDenseDoubleMatrix2DBenchmark()) {
			list.add(new ArrayDenseDoubleMatrix2DBenchmark());
		}
		if (isRunMTJDenseDoubleMatrix2DBenchmark()) {
			list.add(new MTJDenseDoubleMatrix2DBenchmark());
		}
		if (isRunOjalgoDenseDoubleMatrix2DBenchmark()) {
			list.add(new OjalgoDenseDoubleMatrix2DBenchmark());
		}
		// list.add(new JScienceDenseDoubleMatrix2DBenchmark());
		// list.add(new ParallelColtDenseDoubleMatrix2DBenchmark());
		// list.add(new ColtDenseDoubleMatrix2DBenchmark());
		// list.add(new SSTDenseDoubleMatrixBenchmark());
		if (isRunJamaDenseDoubleMatrix2DBenchmark()) {
			list.add(new JamaDenseDoubleMatrix2DBenchmark());
		}
		// list.add(new JSciDenseDoubleMatrix2DBenchmark());
		// list.add(new CommonsMathDenseDoubleMatrix2DBenchmark());
		// list.add(new MantissaDenseDoubleMatrix2DBenchmark());
		// list.add(new JMatricesDenseDoubleMatrix2DBenchmark());
		// list.add(new VecMathDenseDoubleMatrix2DBenchmark());
		Collections.reverse(list);
		return list;
	}

	public void configureDefault() throws Exception {
		setBenchmarkRuns(1);
		setRunsPerMatrix(2);

		setRunDefaultDenseDoubleMatrix2DBenchmark(true);
		// setRunArrayDenseDoubleMatrix2DBenchmark(true);
		setRunMTJDenseDoubleMatrix2DBenchmark(true);
		setRunOjalgoDenseDoubleMatrix2DBenchmark(true);
		setRunJamaDenseDoubleMatrix2DBenchmark(true);

		// getDenseBenchmarks().get(0).setRunTransposeNew(true);
		// getDenseBenchmarks().get(0).setRunMtimesNew(true);
		getDenseBenchmarks().get(0).setRunInv(true);
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

	public void setRunJamaDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runJamaDenseDoubleMatrix2DBenchmark", "" + b);
	}

	public void setRunsPerMatrix(int runs) {
		System.setProperty("runsPerMatrix", "" + runs);
	}

	public void setBenchmarkRuns(int runs) {
		System.setProperty("benchmarkRuns", "" + runs);
	}

	public boolean isRunDefaultDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runDefaultDenseDoubleMatrix2DBenchmark"));
	}

	public int getRunsPerMatrix() {
		return MathUtil.getInt(System.getProperty("runsPerMatrix"));
	}

	public int getBenchmarkRuns() {
		return MathUtil.getInt(System.getProperty("benchmarkRuns"));
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

	public boolean isRunJamaDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runJamaDenseDoubleMatrix2DBenchmark"));
	}

	public void run() throws Exception {
		System.out.println("Running complete benchmark " + getBenchmarkRuns() + " times");
		System.out.println();

		for (int r = 0; r < getBenchmarkRuns(); r++) {
			System.out.println("This is run number " + (r + 1) + " of " + getBenchmarkRuns());
			System.out.println();

			List<AbstractMatrix2DBenchmark> benchmarks = getDenseBenchmarks();

			List<Matrix> results = new ArrayList<Matrix>();
			for (int i = 0; i < 10; i++) {
				results.add(MatrixFactory.emptyMatrix());
			}

			for (int j = 0; j < benchmarks.size(); j++) {
				AbstractMatrix2DBenchmark benchmark = benchmarks.get(j);
				List<Matrix> l = benchmark.run();
				for (int i = 0; i < l.size(); i++) {
					Matrix m = l.get(i).appendVertically(results.get(i));
					m.setLabel(l.get(i).getLabel());
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
					m.showGUI();
				}
			}
		}
		System.out.println();
		System.out.println("Finished");
	}

	public static void main(String[] args) throws Exception {
		if (Runtime.getRuntime().maxMemory() < 500 * 1024 * 1024) {
			throw new Exception("You must start Java with more memory: -Xmx512M");
		}
		MatrixBenchmark mb = new MatrixBenchmark();
		mb.configureDefault();
		mb.run();
	}
}

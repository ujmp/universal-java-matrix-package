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

package org.ujmp.complete.benchmark;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ujmp.colt.benchmark.ColtDenseDoubleMatrix2DBenchmark;
import org.ujmp.commonsmath.benchmark.CommonsMathArrayDenseDoubleMatrix2DBenchmark;
import org.ujmp.commonsmath.benchmark.CommonsMathBlockDenseDoubleMatrix2DBenchmark;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.benchmark.AbstractMatrix2DBenchmark;
import org.ujmp.core.benchmark.ArrayDenseDoubleMatrix2DBenchmark;
import org.ujmp.core.benchmark.DefaultDenseDoubleMatrix2DBenchmark;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.DoubleMatrix2D;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.ejml.benchmark.EJMLDenseDoubleMatrix2DBenchmark;
import org.ujmp.jama.benchmark.JamaDenseDoubleMatrix2DBenchmark;
import org.ujmp.jampack.benchmark.JampackDenseDoubleMatrix2DBenchmark;
import org.ujmp.jlinalg.benchmark.JLinAlgDenseDoubleMatrix2DBenchmark;
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

public class MatrixBenchmark extends AbstractMatrix2DBenchmark {

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
		if (isRunJLinAlgDenseDoubleMatrix2DBenchmark()) {
			list.add(new JLinAlgDenseDoubleMatrix2DBenchmark());
		}
		if (isRunJampackDenseDoubleMatrix2DBenchmark()) {
			list.add(new JampackDenseDoubleMatrix2DBenchmark());
		}
		if (isRunJamaDenseDoubleMatrix2DBenchmark()) {
			list.add(new JamaDenseDoubleMatrix2DBenchmark());
		}
		if (isRunEJMLDenseDoubleMatrix2DBenchmark()) {
			list.add(new EJMLDenseDoubleMatrix2DBenchmark());
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
		if (isRunParallelColtDenseDoubleMatrix2DBenchmark()) {
			list.add(new ParallelColtDenseDoubleMatrix2DBenchmark());
		}

		return list;
	}

	public void setRunAllLibraries() throws Exception {
		setSkipSlowLibraries(true);

		setRunColtDenseDoubleMatrix2DBenchmark(true);
		setRunCommonsMathArrayDenseDoubleMatrix2DBenchmark(true);
		setRunCommonsMathBlockDenseDoubleMatrix2DBenchmark(true);
		setRunEJMLDenseDoubleMatrix2DBenchmark(true);
		setRunJamaDenseDoubleMatrix2DBenchmark(true);
		setRunJampackDenseDoubleMatrix2DBenchmark(true);
		setRunJLinAlgDenseDoubleMatrix2DBenchmark(true);
		setRunJMathArrayDenseDoubleMatrix2DBenchmark(true);
		setRunJMatricesDenseDoubleMatrix2DBenchmark(true);
		setRunJSciDenseDoubleMatrix2DBenchmark(true);
		setRunJScienceDenseDoubleMatrix2DBenchmark(true);
		setRunMantissaDenseDoubleMatrix2DBenchmark(true);
		setRunMTJDenseDoubleMatrix2DBenchmark(true);
		setRunOjalgoDenseDoubleMatrix2DBenchmark(true);
		setRunOrbitalDenseDoubleMatrix2DBenchmark(true);
		setRunOwlpackDenseDoubleMatrix2DBenchmark(true);
		setRunParallelColtDenseDoubleMatrix2DBenchmark(true);
		setRunSSTDenseDoubleMatrix2DBenchmark(true);
		setRunDefaultDenseDoubleMatrix2DBenchmark(true);
		setRunArrayDenseDoubleMatrix2DBenchmark(true);
		setRunVecMathDenseDoubleMatrix2DBenchmark(true);
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

	public void setRunJLinAlgDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runJLinAlgDenseDoubleMatrix2DBenchmark", "" + b);
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

	public void setRunEJMLDenseDoubleMatrix2DBenchmark(boolean b) {
		System.setProperty("runEJMLDenseDoubleMatrix2DBenchmark", "" + b);
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

	public boolean isRunJLinAlgDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runJLinAlgDenseDoubleMatrix2DBenchmark"));
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

	public boolean isRunEJMLDenseDoubleMatrix2DBenchmark() {
		return "true".equals(System.getProperty("runEJMLDenseDoubleMatrix2DBenchmark"));
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

	public void runAll() throws Exception {
		List<AbstractMatrix2DBenchmark> benchmarks = getDenseBenchmarks();

		for (int j = 0; j < benchmarks.size(); j++) {
			AbstractMatrix2DBenchmark benchmark = benchmarks.get(j);
			benchmark.run();
		}

		System.out.println();
		System.out.println("Finished");
		System.out.println();
		System.out.println();
	}

	public static void main(String[] args) {
		try {
			MatrixBenchmark mb = new MatrixBenchmark();
			mb.setRunAllLibraries();
			mb.setRunAllTests();
			mb.runAll();
			mb.evaluate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void evaluate() throws Exception {
		System.out.println("Evaluation");
		System.out.println("==========");
		System.out.println();
		File dir = new File(getResultDir());
		if (!dir.exists()) {
			throw new MatrixException("no results found");
		}
		Map<String, List<Matrix>> statistics = new HashMap<String, List<Matrix>>();
		File[] files = dir.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				String matrixName = f.getName();
				File[] results = f.listFiles();
				for (File r : results) {
					String benchmarkName = r.getName().replaceAll(".csv", "");
					Matrix data = MatrixFactory.importFromFile(FileFormat.CSV, r, "\t");
					data.setLabel(matrixName);
					List<Matrix> list = statistics.get(benchmarkName);
					if (list == null) {
						list = new ArrayList<Matrix>();
						statistics.put(benchmarkName, list);
					}
					list.add(data);
				}
			}
		}

		for (String benchmarkName : statistics.keySet()) {
			List<Matrix> list = statistics.get(benchmarkName);
			List<Matrix> means = new ArrayList<Matrix>();
			List<Matrix> stds = new ArrayList<Matrix>();
			List<Matrix> mins = new ArrayList<Matrix>();
			List<Matrix> maxs = new ArrayList<Matrix>();

			for (Matrix m : list) {
				Matrix data = m.deleteRows(Ret.NEW, 0); // remove label
				Matrix columnLabels = m.selectRows(Ret.NEW, 0); // extract label

				Matrix mean = data.mean(Ret.NEW, Matrix.ROW, true);
				mean.setLabel(m.getLabel() + "-" + benchmarkName + "-mean");
				mean.getAnnotation().setDimensionMatrix(Matrix.ROW, columnLabels);
				means.add(mean);

				Matrix std = data.std(Ret.NEW, Matrix.ROW, true);
				std.setLabel(m.getLabel() + "-" + benchmarkName + "-std");
				std.getAnnotation().setDimensionMatrix(Matrix.ROW, columnLabels);
				stds.add(std);

				Matrix min = data.min(Ret.NEW, Matrix.ROW);
				min.setLabel(m.getLabel() + "-" + benchmarkName + "-min");
				min.getAnnotation().setDimensionMatrix(Matrix.ROW, columnLabels);
				mins.add(min);

				Matrix max = data.max(Ret.NEW, Matrix.ROW);
				max.setLabel(m.getLabel() + "-" + benchmarkName + "-max");
				max.getAnnotation().setDimensionMatrix(Matrix.ROW, columnLabels);
				maxs.add(max);
			}

			Matrix allmeans = null;
			try {
				allmeans = MatrixFactory.vertCat(means);
				allmeans.setLabel(benchmarkName + "-mean");
				ListMatrix<String> matrixLabels = new DefaultListMatrix<String>();
				for (Matrix m : means) {
					matrixLabels.add(m.getLabel().split("-")[0]);
				}
				allmeans.getAnnotation().setDimensionMatrix(Matrix.COLUMN, matrixLabels);
			} catch (Exception e) {
				System.err.println("could not evaluate mean results for " + benchmarkName + ": "
						+ e);
			}
			try {
				allmeans.showGUI();
			} catch (Exception e) {
			}
			Matrix firstMean = MatrixFactory.horCat(MatrixFactory.linkToValue(allmeans.getLabel()),
					allmeans.getAnnotation().getDimensionMatrix(Matrix.ROW));
			Matrix lastMean = MatrixFactory.horCat(allmeans.getAnnotation().getDimensionMatrix(
					Matrix.COLUMN), allmeans);
			Matrix matrixMean = MatrixFactory.vertCat(firstMean, lastMean);
			matrixMean.exportToFile(FileFormat.CSV, new File(getResultDir() + benchmarkName
					+ "-mean.csv"));

			Matrix allstds = null;
			try {
				allstds = MatrixFactory.vertCat(stds);
				allstds.setLabel(benchmarkName + "-std");
				ListMatrix<String> stdLabels = new DefaultListMatrix<String>();
				for (Matrix m : stds) {
					stdLabels.add(m.getLabel().split("-")[0]);
				}
				allstds.getAnnotation().setDimensionMatrix(Matrix.COLUMN, stdLabels);
			} catch (Exception e) {
				System.err
						.println("could not evaluate std results for " + benchmarkName + ": " + e);
			}
			try {
				allstds.showGUI();
			} catch (Exception e) {
			}
			Matrix firstStd = MatrixFactory.horCat(MatrixFactory.linkToValue(allstds.getLabel()),
					allstds.getAnnotation().getDimensionMatrix(Matrix.ROW));
			Matrix lastStd = MatrixFactory.horCat(allstds.getAnnotation().getDimensionMatrix(
					Matrix.COLUMN), allstds);
			Matrix matrixStd = MatrixFactory.vertCat(firstStd, lastStd);
			matrixStd.exportToFile(FileFormat.CSV, new File(getResultDir() + benchmarkName
					+ "-std.csv"));

			System.out.println(allmeans);
			System.out.println();
		}
	}

	@Override
	public DoubleMatrix2D createMatrix(long... size) throws MatrixException {
		return null;
	}

	@Override
	public DoubleMatrix2D createMatrix(Matrix source) throws MatrixException {
		return null;
	}
}

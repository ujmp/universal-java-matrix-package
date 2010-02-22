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
import java.util.Arrays;
import java.util.Collections;
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
import org.ujmp.core.benchmark.BenchmarkUtil;
import org.ujmp.core.benchmark.DefaultDenseDoubleMatrix2DBenchmark;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.DoubleMatrix2D;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.util.StringUtil;
import org.ujmp.core.util.UJMPSettings;
import org.ujmp.core.util.matrices.MatrixSystemEnvironment;
import org.ujmp.core.util.matrices.MatrixSystemProperties;
import org.ujmp.ejml.benchmark.EJMLDenseDoubleMatrix2DBenchmark;
import org.ujmp.jama.JamaDenseDoubleMatrix2D;
import org.ujmp.jama.benchmark.JamaDenseDoubleMatrix2DBenchmark;
import org.ujmp.jampack.benchmark.JampackDenseDoubleMatrix2DBenchmark;
import org.ujmp.jfreechart.ChartConfiguration;
import org.ujmp.jfreechart.MatrixChartPanel;
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

public class CompleteMatrixBenchmark extends AbstractMatrix2DBenchmark {

	public CompleteMatrixBenchmark() {
	}

	public List<AbstractMatrix2DBenchmark> getDenseBenchmarks() {
		List<AbstractMatrix2DBenchmark> list = new ArrayList<AbstractMatrix2DBenchmark>();

		if (getConfig().isRunVecMathDenseDoubleMatrix2D()) {
			list.add(new VecMathDenseDoubleMatrix2DBenchmark());
		}
		if (getConfig().isRunDefaultDenseDoubleMatrix2D()) {
			list.add(new DefaultDenseDoubleMatrix2DBenchmark());
		}
		if (getConfig().isRunArrayDenseDoubleMatrix2D()) {
			list.add(new ArrayDenseDoubleMatrix2DBenchmark());
		}
		if (getConfig().isRunSSTDenseDoubleMatrix2D()) {
			list.add(new SSTDenseDoubleMatrix2DBenchmark());
		}
		if (getConfig().isRunOwlpackDenseDoubleMatrix2D()) {
			list.add(new OwlpackDenseDoubleMatrix2DBenchmark());
		}
		if (getConfig().isRunOrbitalDenseDoubleMatrix2D()) {
			list.add(new OrbitalDenseDoubleMatrix2DBenchmark());
		}
		if (getConfig().isRunOjalgoDenseDoubleMatrix2D()) {
			list.add(new OjalgoDenseDoubleMatrix2DBenchmark());
		}
		if (getConfig().isRunMTJDenseDoubleMatrix2D()) {
			list.add(new MTJDenseDoubleMatrix2DBenchmark());
		}
		if (getConfig().isRunMantissaDenseDoubleMatrix2D()) {
			list.add(new MantissaDenseDoubleMatrix2DBenchmark());
		}
		if (getConfig().isRunJScienceDenseDoubleMatrix2D()) {
			list.add(new JScienceDenseDoubleMatrix2DBenchmark());
		}
		if (getConfig().isRunJSciDenseDoubleMatrix2D()) {
			list.add(new JSciDenseDoubleMatrix2DBenchmark());
		}
		if (getConfig().isRunJMatricesDenseDoubleMatrix2D()) {
			list.add(new JMatricesDenseDoubleMatrix2DBenchmark());
		}
		if (getConfig().isRunJMathArrayDenseDoubleMatrix2D()) {
			list.add(new JMathArrayDenseDoubleMatrix2DBenchmark());
		}
		if (getConfig().isRunJLinAlgDenseDoubleMatrix2D()) {
			list.add(new JLinAlgDenseDoubleMatrix2DBenchmark());
		}
		if (getConfig().isRunJampackDenseDoubleMatrix2D()) {
			list.add(new JampackDenseDoubleMatrix2DBenchmark());
		}
		if (getConfig().isRunJamaDenseDoubleMatrix2D()) {
			list.add(new JamaDenseDoubleMatrix2DBenchmark());
		}
		if (getConfig().isRunEJMLDenseDoubleMatrix2D()) {
			list.add(new EJMLDenseDoubleMatrix2DBenchmark());
		}
		if (getConfig().isRunCommonsMathBlockDenseDoubleMatrix2D()) {
			list.add(new CommonsMathBlockDenseDoubleMatrix2DBenchmark());
		}
		if (getConfig().isRunCommonsMathArrayDenseDoubleMatrix2D()) {
			list.add(new CommonsMathArrayDenseDoubleMatrix2DBenchmark());
		}
		if (getConfig().isRunColtDenseDoubleMatrix2D()) {
			list.add(new ColtDenseDoubleMatrix2DBenchmark());
		}
		if (getConfig().isRunParallelColtDenseDoubleMatrix2D()) {
			list.add(new ParallelColtDenseDoubleMatrix2DBenchmark());
		}

		return list;
	}

	public void runAll() throws Exception {
		List<AbstractMatrix2DBenchmark> benchmarks = getDenseBenchmarks();

		if (getConfig().isSingleThreaded()) {
			UJMPSettings.setNumberOfThreads(1);
		}

		if (getConfig().isShuffle()) {
			Collections.shuffle(benchmarks);
		}
		if (getConfig().isReverse()) {
			Collections.reverse(benchmarks);
		}

		long t0 = System.currentTimeMillis();

		for (int j = 0; j < benchmarks.size(); j++) {
			AbstractMatrix2DBenchmark benchmark = benchmarks.get(j);
			benchmark.run();
		}

		long t1 = System.currentTimeMillis();

		System.out.println();
		System.out.println("Finished.");
		System.out.println("Total Time: " + StringUtil.duration(t1 - t0));
		System.out.println();
		System.out.println();
	}

	public static void main(String[] args) throws Exception {
		String name = null;
		boolean shuffle = false;
		boolean reverse = false;
		boolean singleThreaded = false;
		boolean largeMatrices = false;

		if (args != null) {
			List<String> arglist = new ArrayList<String>(Arrays.asList(args));
			arglist.remove("--benchmark");
			if (arglist.contains("--shuffle")) {
				shuffle = true;
				arglist.remove("--shuffle");
			}
			if (arglist.contains("--reverse")) {
				reverse = true;
				arglist.remove("--reverse");
			}
			if (arglist.contains("--singleThreaded")) {
				singleThreaded = true;
				arglist.remove("--singleThreaded");
			}
			if (arglist.contains("--largeMatrices")) {
				largeMatrices = true;
				arglist.remove("--largeMatrices");
			}
			if (!arglist.isEmpty()) {
				name = arglist.get(0);
			}
		}

		CompleteMatrixBenchmark mb = new CompleteMatrixBenchmark();
		mb.getConfig().setShuffle(shuffle);
		mb.getConfig().setReverse(reverse);
		mb.getConfig().setSingleThreaded(singleThreaded);
		mb.getConfig().setLargeMatrices(largeMatrices);
		mb.setName(name);
		mb.runAll();
		mb.evaluate();
	}

	public void setShuffle(boolean shuffle) {
		getConfig().setShuffle(shuffle);
	}

	public void setReverse(boolean reverse) {
		getConfig().setReverse(reverse);
	}

	public void evaluate() throws Exception {
		System.out.println("Evaluation");
		System.out.println("==========");
		System.out.println();
		File dir = new File(BenchmarkUtil.getResultDir(getConfig()));
		if (!dir.exists()) {
			throw new MatrixException("no results found");
		}

		if (!new File(dir, "properties.csv").exists()) {
			new MatrixSystemProperties().exportToFile(FileFormat.CSV, new File(dir,
					"properties.csv"));
		}
		if (!new File(dir, "environment.csv").exists()) {
			new MatrixSystemEnvironment().exportToFile(FileFormat.CSV, new File(dir,
					"environment.csv"));
		}

		Map<String, List<Matrix>> statistics = new HashMap<String, List<Matrix>>();
		List<File> dirs = Arrays.asList(dir.listFiles());
		Collections.sort(dirs);
		for (File f : dirs) {
			if (f.isDirectory()) {
				String matrixName = f.getName();
				List<File> results = Arrays.asList(f.listFiles());
				Collections.sort(results);
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
			if (!allmeans.getLabel().contains("diff")) {
				try {
					long jamaRow = allmeans.getRowForLabel(JamaDenseDoubleMatrix2D.class
							.getSimpleName());
					Matrix row = allmeans.selectRows(Ret.NEW, jamaRow);
					Matrix m = MatrixFactory.vertCat(row, allmeans.getRowCount());
					Matrix scaled = allmeans.divide(Ret.NEW, false, m).power(Ret.NEW, -1)
							.transpose(Ret.NEW);
					for (int r = 0; r < scaled.getRowCount(); r++) {
						scaled.setRowLabel(r, scaled.getRowLabel(r).split("x")[0]);
					}
					scaled.showGUI();
					ChartConfiguration config = new ChartConfiguration();
					config.setLogScaleRange(true);
					config.setLogScaleDomain(true);
					MatrixChartPanel cp = new MatrixChartPanel(scaled, config);
					cp.export(FileFormat.PDF, new File(BenchmarkUtil.getResultDir(getConfig())
							+ benchmarkName + "-scaled.pdf"));
					cp.export(FileFormat.JPG, new File(BenchmarkUtil.getResultDir(getConfig())
							+ benchmarkName + "-scaled.jpg"));
					Matrix firstScaled = MatrixFactory.horCat(MatrixFactory.linkToValue(scaled
							.getLabel()), scaled.getAnnotation().getDimensionMatrix(Matrix.ROW));
					Matrix lastScaled = MatrixFactory.horCat(scaled.getAnnotation()
							.getDimensionMatrix(Matrix.COLUMN), scaled);
					Matrix matrixScaled = MatrixFactory.vertCat(firstScaled, lastScaled);
					matrixScaled.exportToFile(FileFormat.CSV, new File(BenchmarkUtil
							.getResultDir(getConfig())
							+ benchmarkName + "-scaled.csv"));
					try {
						matrixScaled.exportToFile(FileFormat.XLS, new File(BenchmarkUtil
								.getResultDir(getConfig())
								+ benchmarkName + "-scaled.xls"));
					} catch (Exception e) {
					}
				} catch (Exception e) {
				}
			} else {
				allmeans.transpose(Ret.NEW).showGUI();
			}

			Matrix firstMean = MatrixFactory.horCat(MatrixFactory.linkToValue(allmeans.getLabel()),
					allmeans.getAnnotation().getDimensionMatrix(Matrix.ROW));
			Matrix lastMean = MatrixFactory.horCat(allmeans.getAnnotation().getDimensionMatrix(
					Matrix.COLUMN), allmeans);
			Matrix matrixMean = MatrixFactory.vertCat(firstMean, lastMean);
			matrixMean.exportToFile(FileFormat.CSV, new File(BenchmarkUtil
					.getResultDir(getConfig())
					+ benchmarkName + "-mean.csv"));
			try {
				matrixMean.exportToFile(FileFormat.XLS, new File(BenchmarkUtil
						.getResultDir(getConfig())
						+ benchmarkName + "-mean.xls"));
			} catch (Exception e) {
			}

			Matrix allstds = null;
			Matrix stdPercent = null;
			try {
				allstds = MatrixFactory.vertCat(stds);
				stdPercent = allstds.divide(allmeans).times(100);
				allstds.setLabel(benchmarkName + "-std");
				stdPercent.setLabel(benchmarkName + "-stdpercent");
				ListMatrix<String> stdLabels = new DefaultListMatrix<String>();
				for (Matrix m : stds) {
					stdLabels.add(m.getLabel().split("-")[0]);
				}
				allstds.getAnnotation().setDimensionMatrix(Matrix.COLUMN, stdLabels);
				stdPercent.getAnnotation().setDimensionMatrix(Matrix.COLUMN, stdLabels);
			} catch (Exception e) {
				System.err
						.println("could not evaluate std results for " + benchmarkName + ": " + e);
			}
			Matrix firstStd = MatrixFactory.horCat(MatrixFactory.linkToValue(allstds.getLabel()),
					allstds.getAnnotation().getDimensionMatrix(Matrix.ROW));
			Matrix lastStd = MatrixFactory.horCat(allstds.getAnnotation().getDimensionMatrix(
					Matrix.COLUMN), allstds);
			Matrix lastStdPercent = MatrixFactory.horCat(allstds.getAnnotation()
					.getDimensionMatrix(Matrix.COLUMN), stdPercent);
			Matrix matrixStd = MatrixFactory.vertCat(firstStd, lastStd);
			matrixStd.exportToFile(FileFormat.CSV, new File(BenchmarkUtil.getResultDir(getConfig())
					+ benchmarkName + "-std.csv"));
			try {
				matrixStd.exportToFile(FileFormat.XLS, new File(BenchmarkUtil
						.getResultDir(getConfig())
						+ benchmarkName + "-std.xls"));
			} catch (Exception e) {
			}
			Matrix matrixStdPercent = MatrixFactory.vertCat(firstStd, lastStdPercent);
			matrixStdPercent.exportToFile(FileFormat.CSV, new File(BenchmarkUtil
					.getResultDir(getConfig())
					+ benchmarkName + "-stdpercent.csv"));
			try {
				matrixStdPercent.exportToFile(FileFormat.XLS, new File(BenchmarkUtil
						.getResultDir(getConfig())
						+ benchmarkName + "-stdpercent.xls"));
			} catch (Exception e) {
			}

			Matrix allmins = null;
			try {
				allmins = MatrixFactory.vertCat(mins);
				allmins.setLabel(benchmarkName + "-min");
				ListMatrix<String> minLabels = new DefaultListMatrix<String>();
				for (Matrix m : mins) {
					minLabels.add(m.getLabel().split("-")[0]);
				}
				allmins.getAnnotation().setDimensionMatrix(Matrix.COLUMN, minLabels);
			} catch (Exception e) {
				System.err
						.println("could not evaluate min results for " + benchmarkName + ": " + e);
			}
			Matrix firstMin = MatrixFactory.horCat(MatrixFactory.linkToValue(allmins.getLabel()),
					allmins.getAnnotation().getDimensionMatrix(Matrix.ROW));
			Matrix lastMin = MatrixFactory.horCat(allmins.getAnnotation().getDimensionMatrix(
					Matrix.COLUMN), allmins);
			Matrix matrixMin = MatrixFactory.vertCat(firstMin, lastMin);
			matrixMin.exportToFile(FileFormat.CSV, new File(BenchmarkUtil.getResultDir(getConfig())
					+ benchmarkName + "-min.csv"));
			try {
				matrixMin.exportToFile(FileFormat.XLS, new File(BenchmarkUtil
						.getResultDir(getConfig())
						+ benchmarkName + "-min.xls"));
			} catch (Exception e) {
			}

			Matrix allmaxs = null;
			try {
				allmaxs = MatrixFactory.vertCat(maxs);
				allmaxs.setLabel(benchmarkName + "-max");
				ListMatrix<String> maxLabels = new DefaultListMatrix<String>();
				for (Matrix m : maxs) {
					maxLabels.add(m.getLabel().split("-")[0]);
				}
				allmaxs.getAnnotation().setDimensionMatrix(Matrix.COLUMN, maxLabels);
			} catch (Exception e) {
				System.err
						.println("could not evaluate max results for " + benchmarkName + ": " + e);
			}
			Matrix firstMax = MatrixFactory.horCat(MatrixFactory.linkToValue(allmaxs.getLabel()),
					allmaxs.getAnnotation().getDimensionMatrix(Matrix.ROW));
			Matrix lastMax = MatrixFactory.horCat(allmins.getAnnotation().getDimensionMatrix(
					Matrix.COLUMN), allmaxs);
			Matrix matrixMax = MatrixFactory.vertCat(firstMax, lastMax);
			matrixMax.exportToFile(FileFormat.CSV, new File(BenchmarkUtil.getResultDir(getConfig())
					+ benchmarkName + "-max.csv"));
			try {
				matrixMax.exportToFile(FileFormat.XLS, new File(BenchmarkUtil
						.getResultDir(getConfig())
						+ benchmarkName + "-max.xls"));
			} catch (Exception e) {
			}

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

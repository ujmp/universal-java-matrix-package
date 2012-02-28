/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

import org.ojalgo.concurrent.ProcessorCount;
import org.ujmp.colt.benchmark.ColtDenseDoubleMatrix2DBenchmark;
import org.ujmp.commonsmath.benchmark.CommonsMathArrayDenseDoubleMatrix2DBenchmark;
import org.ujmp.commonsmath.benchmark.CommonsMathBlockDenseDoubleMatrix2DBenchmark;
import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.benchmark.AbstractMatrix2DBenchmark;
import org.ujmp.core.benchmark.ArrayDenseDoubleMatrix2DBenchmark;
import org.ujmp.core.benchmark.BenchmarkUtil;
import org.ujmp.core.benchmark.BlockDenseDoubleMatrix2DBenchmark;
import org.ujmp.core.benchmark.DefaultDenseDoubleMatrix2DBenchmark;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.DoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrix2D;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.util.CommandLineUtil;
import org.ujmp.core.util.StringUtil;
import org.ujmp.core.util.UJMPSettings;
import org.ujmp.core.util.matrices.MatrixLibraries;
import org.ujmp.core.util.matrices.MatrixSystemEnvironment;
import org.ujmp.core.util.matrices.MatrixSystemProperties;
import org.ujmp.ejml.benchmark.EJMLDenseDoubleMatrix2DBenchmark;
import org.ujmp.jama.JamaDenseDoubleMatrix2D;
import org.ujmp.jama.benchmark.JamaDenseDoubleMatrix2DBenchmark;
import org.ujmp.jampack.benchmark.JampackDenseDoubleMatrix2DBenchmark;
import org.ujmp.jblas.benchmark.JBlasDenseDoubleMatrix2DBenchmark;
import org.ujmp.jlinalg.benchmark.JLinAlgDenseDoubleMatrix2DBenchmark;
import org.ujmp.jmatharray.benchmark.JMathArrayDenseDoubleMatrix2DBenchmark;
import org.ujmp.jmatrices.benchmark.JMatricesDenseDoubleMatrix2DBenchmark;
import org.ujmp.jsci.benchmark.JSciDenseDoubleMatrix2DBenchmark;
import org.ujmp.jscience.benchmark.JScienceDenseDoubleMatrix2DBenchmark;
import org.ujmp.mantissa.benchmark.MantissaDenseDoubleMatrix2DBenchmark;
import org.ujmp.mtj.benchmark.MTJDenseDoubleMatrix2DBenchmark;
import org.ujmp.ojalgo.benchmark.OjalgoDenseDoubleMatrix2DBenchmark;
import org.ujmp.orbital.benchmark.OrbitalDenseDoubleMatrix2DBenchmark;
import org.ujmp.parallelcolt.benchmark.ParallelColtDenseDoubleMatrix2DBenchmark;
import org.ujmp.sst.benchmark.SSTDenseDoubleMatrix2DBenchmark;
import org.ujmp.vecmath.benchmark.VecMathDenseDoubleMatrix2DBenchmark;

import edu.emory.mathcs.utils.ConcurrencyUtils;

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
		if (getConfig().isRunBlockDenseDoubleMatrix2D()) {
			list.add(new BlockDenseDoubleMatrix2DBenchmark());
		}
		if (getConfig().isRunSSTDenseDoubleMatrix2D()) {
			list.add(new SSTDenseDoubleMatrix2DBenchmark());
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
		if (getConfig().isRunJBlasDenseDoubleMatrix2D()) {
			list.add(new JBlasDenseDoubleMatrix2DBenchmark());
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

		UJMPSettings.setNumberOfThreads(getConfig().getNumberOfThreads());
		ProcessorCount.RUNTIME = getConfig().getNumberOfThreads();
		ConcurrencyUtils.setNumberOfThreads(getConfig().getNumberOfThreads());
		System.setProperty("ATLAS_NUM_THREADS", "" + getConfig().getNumberOfThreads());

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
		CompleteMatrixBenchmark mb = new CompleteMatrixBenchmark();
		CommandLineUtil.parse(mb.getConfig(), args);
		mb.saveSettings();
		mb.runAll();
		mb.evaluate();
	}

	public void setShuffle(boolean shuffle) {
		getConfig().setShuffle(shuffle);
	}

	public void setReverse(boolean reverse) {
		getConfig().setReverse(reverse);
	}

	public void saveSettings() throws Exception {
		String resultDir = BenchmarkUtil.getResultDir(getConfig());
		File envFile = new File(resultDir + File.separator + "env.csv");
		File propFile = new File(resultDir + File.separator + "props.csv");
		File confFile = new File(resultDir + File.separator + "conf.csv");
		File versionFile = new File(resultDir + File.separator + "versions.csv");
		new MatrixSystemEnvironment().exportToFile(FileFormat.CSV, envFile);
		Matrix props = new MatrixSystemProperties().replaceRegex(Ret.NEW, "\r\n", " ");
		props = props.replaceRegex(Ret.NEW, "\n", " ");
		props.exportToFile(FileFormat.CSV, propFile);
		getConfig().exportToFile(FileFormat.CSV, confFile);
		Matrix libraries = new MatrixLibraries();
		System.out.println(libraries);
		Matrix versions = libraries.selectRows(Ret.NEW, 0, 1).transpose();
		versions.exportToFile(FileFormat.CSV, versionFile);
	}

	public void evaluate() throws Exception {
		System.out.println("Evaluation");
		System.out.println("==========");
		System.out.println();
		File dir = new File(BenchmarkUtil.getResultDir(getConfig()));
		if (!dir.exists()) {
			throw new MatrixException("no results found");
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

				Matrix std = data.std(Ret.NEW, Matrix.ROW, true, true);
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
				e.printStackTrace();
			}
			if (!allmeans.getLabel().contains("diff")) {
				try {
					long jamaRow = allmeans.getRowForLabel(JamaDenseDoubleMatrix2D.class
							.getSimpleName());
					if (jamaRow < 0) {
						jamaRow = allmeans.getRowForLabel(DefaultDenseDoubleMatrix2D.class
								.getSimpleName());
					}
					Matrix valueCount = MatrixFactory.zeros(1, allmeans.getColumnCount());
					for (int c = 0; c < valueCount.getColumnCount(); c++) {
						int s = extractSize(allmeans.getColumnLabel(c));
						if (allmeans.getLabel().contains("tall")) {
							valueCount.setAsInt(s * s / 2, 0, c);
						} else {
							valueCount.setAsInt(s * s, 0, c);
						}
					}
					valueCount = MatrixFactory.vertCat(valueCount, allmeans.getRowCount());
					Matrix perCell = allmeans.divide(Ret.NEW, false, valueCount).transpose(Ret.NEW);
					perCell.setLabel(allmeans.getLabel() + "-percell");
					for (int r = 0; r < perCell.getRowCount(); r++) {
						perCell.setRowLabel(r, perCell.getRowLabel(r).split("x")[0]);
					}
					export(perCell);
					Matrix row = allmeans.selectRows(Ret.NEW, jamaRow);
					Matrix m = MatrixFactory.vertCat(row, allmeans.getRowCount());
					Matrix scaled = allmeans.divide(Ret.NEW, false, m).power(Ret.NEW, -1)
							.transpose(Ret.NEW);
					scaled.setLabel(allmeans.getLabel() + "-scaled");
					for (int r = 0; r < scaled.getRowCount(); r++) {
						scaled.setRowLabel(r, scaled.getRowLabel(r).split("x")[0]);
					}
					export(scaled);
				} catch (Exception e) {
				}
			}

			export(allmeans.transpose(Ret.NEW));

			Matrix allstds = null;
			Matrix stdPercent = null;
			try {
				allstds = MatrixFactory.vertCat(stds);
				stdPercent = allstds.divide(Ret.NEW, false, allmeans).times(Ret.NEW, false, 100);
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
			export(allstds.transpose(Ret.NEW));
			export(stdPercent.transpose(Ret.NEW));

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
			export(allmins.transpose(Ret.NEW));

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
			export(allmaxs.transpose(Ret.NEW));

			System.out.println(allmeans);
			System.out.println();
		}
	}

	private void export(Matrix matrix) {
		String name = matrix.getLabel();
		for (int r = 0; r < matrix.getRowCount(); r++) {
			matrix.setRowLabel(r, String.valueOf(extractSize(matrix.getRowLabel(r))));
		}
		Matrix firstPart = MatrixFactory.horCat(MatrixFactory.linkToValue(matrix.getLabel()),
				matrix.getAnnotation().getDimensionMatrix(Matrix.ROW));
		Matrix lastPart = MatrixFactory.horCat(
				matrix.getAnnotation().getDimensionMatrix(Matrix.COLUMN), matrix);
		Matrix complete = MatrixFactory.vertCat(firstPart, lastPart);
		try {
			complete.exportToFile(FileFormat.CSV, new File(BenchmarkUtil.getResultDir(getConfig())
					+ name + ".csv"));
		} catch (Exception e) {
		}
		try {
			complete.exportToFile(FileFormat.XLS, new File(BenchmarkUtil.getResultDir(getConfig())
					+ name + ".xls"));
		} catch (Exception e) {
		}
		try {
			Matrix plt = complete.deleteRows(Ret.NEW, 0);
			plt.setColumnLabel(0, "matrix size");
			for (int c = 1; c < plt.getColumnCount(); c++) {
				plt.setColumnLabel(c, matrix.getColumnLabel(c - 1));
			}
			plt.setLabel(matrix.getLabel());
			Object[] params = null;
			if (matrix.getLabel().contains("stdpercent")) {
				params = new Object[] { "xy", "logx", };
			} else {
				params = new Object[] { "xy", "logx", "logy" };
			}
			plt.exportToFile(FileFormat.PLT, new File(BenchmarkUtil.getResultDir(getConfig())
					+ name + ".plt"), params);
		} catch (Exception e) {
		}
		// ChartConfiguration config = new ChartConfiguration();
		// config.setLogScaleRange(true);
		// config.setLogScaleDomain(true);
		// MatrixChartPanel cp = new MatrixChartPanel(matrix, config);
		// try {
		// cp.export(FileFormat.PDF, new
		// File(BenchmarkUtil.getResultDir(getConfig()) + name
		// + ".pdf"));
		// } catch (Exception e) {
		// }
		// try {
		// cp.export(FileFormat.JPG, new
		// File(BenchmarkUtil.getResultDir(getConfig()) + name
		// + ".jpg"));
		// } catch (Exception e) {
		// }
	}

	@Override
	public DoubleMatrix2D createMatrix(long... size) throws MatrixException {
		return null;
	}

	@Override
	public DoubleMatrix2D createMatrix(Matrix source) throws MatrixException {
		return null;
	}

	private int extractSize(String s) {
		if (s != null && !"null".equals(s)) {
			if (s.contains("x")) {
				return (int) Coordinates.parseString(s)[0];
			} else {
				return Integer.parseInt(s);
			}
		} else {
			return 0;
		}
	}
}
